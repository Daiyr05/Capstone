package org.example.capstoneproject.controller;

import org.example.capstoneproject.entity.Status;
import org.example.capstoneproject.entity.Task;
import org.example.capstoneproject.entity.User;
import org.example.capstoneproject.service.TaskService;
import org.example.capstoneproject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/client")
public class ClientController {

    private final TaskService taskService;
    private final UserService userService;

    public ClientController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }
    @GetMapping("/tasks")
    public String viewClientTasks(Model model, Principal principal) {
        Integer id  = userService.selectUserByEmail(principal.getName()).getId();
        List<Task> tasks = taskService.getTasksByAssignedTo(id);
        model.addAttribute("tasks", tasks);
        return "home";
    }

    @GetMapping("/task/{id}")
    public String showClientTaskDetail(@PathVariable int id, Model model) {
        Task task = taskService.getTaskById(id).orElseThrow();
        model.addAttribute("task", task);
        System.out.println(task.getTitle());

        return "client-task-detail";
    }

    @PostMapping("/task/{id}/status")
    public String updateTaskStatus(@PathVariable int id,
                                   @RequestParam("status") String status) {
        Task task = taskService.getTaskById(id).orElseThrow();
        task.setStatus(Status.valueOf(status));
        taskService.updateTask(task);

        return "redirect:/client/tasks";
    }
}
