package org.example.capstoneproject;


import org.example.capstoneproject.entity.Task;
import org.example.capstoneproject.repository.TaskRepository;
import org.example.capstoneproject.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class TaskServiceTest {

    private TaskRepository taskRepository;
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        taskRepository = mock(TaskRepository.class);
        taskService = new TaskService(taskRepository);
    }

    @Test
    void testGetAllTasks() {
        List<Task> tasks = Arrays.asList(new Task(), new Task());
        when(taskRepository.getAllTasks()).thenReturn(tasks);

        List<Task> result = taskService.getAllTasks();

        assertEquals(2, result.size());
        verify(taskRepository).getAllTasks();
    }

    @Test
    void testGetTaskById() {
        Task task = new Task();
        when(taskRepository.getTaskById(1)).thenReturn(Optional.of(task));

        Optional<Task> result = taskService.getTaskById(1);

        assertTrue(result.isPresent());
        assertEquals(task, result.get());
        verify(taskRepository).getTaskById(1);
    }

    @Test
    void testGetTasksByAssignedTo() {
        List<Task> tasks = Arrays.asList(new Task(), new Task());
        when(taskRepository.getTasksByAssignedTo(10)).thenReturn(tasks);

        List<Task> result = taskService.getTasksByAssignedTo(10);

        assertEquals(2, result.size());
        verify(taskRepository).getTasksByAssignedTo(10);
    }

    @Test
    void testDeleteTask_Success() {
        when(taskRepository.deleteTask(1)).thenReturn(1);

        boolean result = taskService.deleteTask(1);

        assertTrue(result);
        verify(taskRepository).deleteTask(1);
    }

    @Test
    void testDeleteTask_Failure() {
        when(taskRepository.deleteTask(1)).thenReturn(0);

        boolean result = taskService.deleteTask(1);

        assertFalse(result);
        verify(taskRepository).deleteTask(1);
    }

    @Test
    void testUpdateTask_Success() {
        Task task = new Task();
        when(taskRepository.updateTask(task)).thenReturn(1);

        boolean result = taskService.updateTask(task);

        assertTrue(result);
        verify(taskRepository).updateTask(task);
    }

    @Test
    void testUpdateTask_Failure() {
        Task task = new Task();
        when(taskRepository.updateTask(task)).thenReturn(0);

        boolean result = taskService.updateTask(task);

        assertFalse(result);
        verify(taskRepository).updateTask(task);
    }

    @Test
    void testSaveTask() {
        Task task = new Task();

        taskService.saveTask(task);

        verify(taskRepository).saveTask(task);
    }
}

