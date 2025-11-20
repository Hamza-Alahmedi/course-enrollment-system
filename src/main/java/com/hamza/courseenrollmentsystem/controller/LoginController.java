package com.hamza.courseenrollmentsystem.controller;

import com.hamza.courseenrollmentsystem.entity.User;
import com.hamza.courseenrollmentsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    // Show login page
    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }
    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        Model model) {

        var userOpt = userService.findByEmail(email);
        if (userOpt.isEmpty()) {
            model.addAttribute("error", "User not found!");
            return "login";
        }

        var user = userOpt.get();
        if (userService.checkPassword(password, user.getPassword())) {
            model.addAttribute("success", "Login successful! Welcome " + user.getUsername());
            return "login"; // later can redirect to dashboard
        } else {
            model.addAttribute("error", "Invalid password!");
            return "login";
        }
    }

    // Show registration page
    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String email,
                           @RequestParam String password,
                           Model model) {

        if (userService.findByEmail(email).isPresent()) {
            model.addAttribute("error", "Email already exists!");
            return "register";
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        userService.registerUser(user);

        model.addAttribute("success", "Registration successful! You can now log in.");
        return "register";
    }
}
