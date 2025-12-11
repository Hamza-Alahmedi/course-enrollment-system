package com.hamza.courseenrollmentsystem.controller;

import com.hamza.courseenrollmentsystem.entity.User;
import com.hamza.courseenrollmentsystem.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "https://course-enrollment-frontend-c9mr.onrender.com", allowCredentials = "true")
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
    public ResponseEntity<Map<String, Object>> login(@RequestBody User loginRequest, HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        Optional<User> userOptional = userService.findByEmail(loginRequest.getEmail());

        if (userOptional.isEmpty()) {
            response.put("success", false);
            response.put("message", "User not found!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        User user = userOptional.get();
        if (userService.checkPassword(loginRequest.getPassword(), user.getPassword())) {
            // Create Spring Security authentication
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    user.getEmail(),
                    user.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
            );

            // Set authentication in SecurityContext
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authToken);

            // Create session and store SecurityContext
            HttpSession session = request.getSession(true);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);

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

    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout(HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();

        // Invalidate session
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // Clear security context
        SecurityContextHolder.clearContext();

        response.put("success", true);
        response.put("message", "Logged out successfully");
        return ResponseEntity.ok(response);
    }
}
