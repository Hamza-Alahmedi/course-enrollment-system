package com.hamza.courseenrollmentsystem.controller;

import com.hamza.courseenrollmentsystem.entity.User;
import com.hamza.courseenrollmentsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();
        if (userService.findByEmail(user.getEmail()).isPresent()) {
            response.put("success", false);
            response.put("message", "Email already exists!");
            return ResponseEntity.badRequest().body(response);
        }
        User registeredUser = userService.registerUser(user);
        response.put("success", true);
        response.put("message", "User registered successfully!");
        response.put("userId", registeredUser.getId());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody User loginRequest) {
        Map<String, Object> response = new HashMap<>();
        Optional<User> userOptional = userService.findByEmail(loginRequest.getEmail());

        if (userOptional.isEmpty()) {
            response.put("success", false);
            response.put("message", "User not found!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        User user = userOptional.get();
        if (userService.checkPassword(loginRequest.getPassword(), user.getPassword())) {
            response.put("success", true);
            response.put("message", "Login successful! Welcome " + user.getUsername());
            response.put("studentId", user.getId());
            response.put("id", user.getId());
            response.put("username", user.getUsername());
            response.put("email", user.getEmail());
            response.put("role", user.getRole());
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Invalid password!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}
