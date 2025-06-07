package org.example.capstoneproject.repository;

import org.example.capstoneproject.entity.Task;
import org.example.capstoneproject.entity.User;
import org.example.capstoneproject.entity.Status;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class TaskRepository {
    private final JdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;

    public TaskRepository(JdbcTemplate jdbcTemplate, UserRepository userRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
    }

    private Task mapRowToTask(ResultSet rs, int rowNum) throws SQLException {
        Integer assignedToId = rs.getInt("assigned_to");
        User executor = null;
        if (assignedToId != 0) {
            executor = userRepository.selectUserById(assignedToId).orElse(null);
        }

        return new Task(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("description"),
                Status.valueOf(rs.getString("status")), // assuming Status is enum matching DB values
                rs.getDate("due_date"),
                executor
        );
    }

    public List<Task> getAllTasks() {
        String sql = "SELECT * FROM tasks";
        return jdbcTemplate.query(sql, this::mapRowToTask);
    }

    public Optional<Task> getTaskById(int id) {
        String sql = "SELECT * FROM tasks WHERE id = ?";
        List<Task> tasks = jdbcTemplate.query(sql, new Object[]{id}, this::mapRowToTask);
        if (tasks.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(tasks.get(0));
    }

    public List<Task> getTasksByAssignedTo(int userId) {
        String sql = "SELECT * FROM tasks WHERE assigned_to = ?";
        return jdbcTemplate.query(sql, new Object[]{userId}, this::mapRowToTask);
    }

    public int deleteTask(int id) {
        String sql = "DELETE FROM tasks WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public int updateTask(Task task) {
        String sql = """
                UPDATE tasks SET
                title = ?,
                description = ?,
                status = ?,
                due_date = ?,
                assigned_to = ?
                WHERE id = ?
                """;

        Integer assignedToId = (task.getExecutor() != null) ? task.getExecutor().getId() : null;

        return jdbcTemplate.update(sql,
                task.getTitle(),
                task.getDescription(),
                task.getStatus().name(),
                new java.sql.Date(task.getDueDate().getTime()),
                assignedToId,
                task.getId()
        );
    }

    public void saveTask(Task task) {
        String sql = """
        INSERT INTO tasks (title, description, status, due_date, assigned_to)
        VALUES (?, ?, ?, ?, ?)
    """;
        jdbcTemplate.update(sql,
                task.getTitle(),
                task.getDescription(),
                task.getStatus().toString(),
                new java.sql.Date(task.getDueDate().getTime()),
                task.getExecutor().getId()
        );
    }

}
