package org.example.capstoneproject.controller;

import org.example.capstoneproject.entity.Task;
import org.example.capstoneproject.entity.User;
import org.example.capstoneproject.service.AuthenticationService;
import org.example.capstoneproject.service.TaskService;
import org.example.capstoneproject.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AuthController {
    private final AuthenticationService authService;
    private final UserService userService;
    private final TaskService taskService;


    public AuthController(AuthenticationService authService, UserService userService, TaskService taskService) {
        this.authService = authService;
        this.userService = userService;
        this.taskService = taskService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        try {
            authService.register(user);
            return "redirect:/login?registered=true";
        } catch (Exception e) {
            model.addAttribute("error", "User already exists or invalid input.");
            return "register";
        }
    }

    @GetMapping("/home")
    public String home(Model model, Authentication authentication) {

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"));

        if (isAdmin) {
            List<User> clients = userService.getAllClients();
            model.addAttribute("clients", clients);
        } else {
            Integer id = userService.selectUserByEmail(authentication.getName()).getId();
            System.out.println(id);
            List<Task> tasks = taskService.getTasksByAssignedTo(id);
            model.addAttribute("tasks", tasks);
        }

        return "home";
    }
}
