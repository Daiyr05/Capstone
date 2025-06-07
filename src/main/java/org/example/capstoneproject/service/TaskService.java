package org.example.capstoneproject.service;

import org.example.capstoneproject.entity.Task;
import org.example.capstoneproject.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.getAllTasks();
    }

    public Optional<Task> getTaskById(int id) {
        return taskRepository.getTaskById(id);
    }

    public List<Task> getTasksByAssignedTo(int userId) {
        return taskRepository.getTasksByAssignedTo(userId);
    }

    public boolean deleteTask(int id) {
        return taskRepository.deleteTask(id) == 1;
    }

    public boolean updateTask(Task task) {
        return taskRepository.updateTask(task) == 1;
    }

    public void saveTask(Task task){
        taskRepository.saveTask(task);
    }
}
