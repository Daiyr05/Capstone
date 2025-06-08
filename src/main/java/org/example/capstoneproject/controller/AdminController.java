package org.example.capstoneproject.controller;


import org.example.capstoneproject.entity.Status;
import org.example.capstoneproject.entity.Task;
import org.example.capstoneproject.entity.User;
import org.example.capstoneproject.service.TaskService;
import org.example.capstoneproject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AdminController {
    private final UserService userService;
    private final TaskService taskService;

    public AdminController(UserService userService, TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
    }

    @GetMapping("/admin/client/{id}")
    public String viewClientTasks(@PathVariable int id, Model model) {
        User client = userService.selectUserById(id).get();
        List<Task> tasks = taskService.getTasksByAssignedTo(id);
        model.addAttribute("client", client);
        model.addAttribute("tasks", tasks);
        return "client-tasks";
    }

    @GetMapping("/admin/client/{id}/task/add")
    public String showAddTaskForm(@PathVariable int id, Model model) {
        model.addAttribute("task", new Task());
        model.addAttribute("client", userService.selectUserById(id).get());
        return "add-task";
    }

    @PostMapping("/admin/client/{id}/task/add")
    public String addTaskToClient(@PathVariable int id, @ModelAttribute Task task) {
        User client = userService.selectUserById(id).get();
        task.setExecutor(client);
        task.setStatus(Status.PENDING);
        taskService.saveTask(task);
        return "redirect:/admin/client/" + id;
    }

    @GetMapping("/admin/client/{clientId}/task/{taskId}/edit")
    public String showEditTaskForm(@PathVariable int clientId,
                                   @PathVariable int taskId,
                                   Model model) {
        Task task = taskService.getTaskById(taskId).orElseThrow();
        model.addAttribute("task", task);
        model.addAttribute("clientId", clientId);
        return "edit-task"; // Thymeleaf template to be created
    }

    @PostMapping("/admin/client/{clientId}/task/{taskId}/edit")
    public String editTask(@PathVariable int clientId,
                           @PathVariable int taskId,
                           @ModelAttribute Task task) {
        Task existing = taskService.getTaskById(taskId).orElseThrow();

        existing.setTitle(task.getTitle());
        existing.setDescription(task.getDescription());
        existing.setDueDate(task.getDueDate());
        existing.setStatus(task.getStatus());

        taskService.updateTask(existing);
        return "redirect:/admin/client/" + clientId;
    }

    @PostMapping("/admin/client/{clientId}/task/{taskId}/delete")
    public String deleteTask(@PathVariable int clientId,
                             @PathVariable int taskId) {
        taskService.deleteTask(taskId);
        return "redirect:/admin/client/" + clientId;
    }


}
