package org.example.capstoneproject.repository;

import org.example.capstoneproject.dao.UserDao;
import org.example.capstoneproject.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public class UserRepository implements UserDao {

    private final JdbcTemplate jdbcTemplate;
    private final int CLIENT_ROLE_ID = 2;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> selectUsers() {
        var sql = """
                SELECT * FROM users;
                """;
        return jdbcTemplate.query(sql,(resultSet,i)->{
            return new User(resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("email"),
                            resultSet.getString("password"),
                            resultSet.getInt("role_id"));
        });
    }

    @Override
    public Optional<User> selectUserById(Integer id) {
        var sql = """
                SELECT * FROM users WHERE id = ?; 
                """;
        try {
            User user = jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) ->
                    new User(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getInt("role_id")
                    )
            );
            return Optional.of(user);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Integer insertUser(User user) {
        var sql = """
                INSERT INTO users (name,email,password,role_id) VALUES (?,?,?,?);
                """;
        return jdbcTemplate.update(sql,user.getName(),user.getEmail(),user.getPassword(),CLIENT_ROLE_ID);
    }

    @Override
    public Integer deleteUser(Integer id) {
        var sql = """
                DELETE FROM users  WHERE id = ?
                """;
        return jdbcTemplate.update(sql,id);
    }

    @Override
    public Integer updateUser(User user) {
        var sql = """
        UPDATE users
        SET name = ?, email = ?, password = ? WHERE id = ?
        """;

        return jdbcTemplate.update(
                sql,
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getId()
        );
    }
}
