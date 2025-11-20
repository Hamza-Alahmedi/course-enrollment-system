package com.hamza.courseenrollmentsystem.controller;

import com.hamza.courseenrollmentsystem.entity.User;
import com.hamza.courseenrollmentsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        if (userService.findByEmail(user.getEmail()).isPresent()) {
            return "Email already exists!";
        }
        userService.registerUser(user);
        return "User registered successfully!";
    }

    @PostMapping("/login")
    public String login(@RequestBody User loginRequest) {
        Optional<User> userOptional = userService.findByEmail(loginRequest.getEmail());
        if (userOptional.isEmpty()) {
            return "User not found!";
        }
        User user = userOptional.get();
        if (userService.checkPassword(loginRequest.getPassword(), user.getPassword())) {
            return "Login successful! Welcome " + user.getUsername();
        } else {
            return "Invalid password!";
        }
    }
}
