package com.hamza.courseenrollmentsystem.service;

import com.hamza.courseenrollmentsystem.entity.User;
import com.hamza.courseenrollmentsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Create a local instance instead of injecting to avoid circular dependency
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User registerUser(User user) {
        // Encrypt password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("STUDENT"); // default role
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean checkPassword(String rawPassword, String storedPassword) {
        // If password doesn't start with $2a$ (BCrypt prefix), it's plain text
        // This allows backward compatibility with existing plain text passwords
        if (!storedPassword.startsWith("$2a$") && !storedPassword.startsWith("$2b$")) {
            return rawPassword.equals(storedPassword);
        }
        // Use BCrypt to check encrypted passwords
        return passwordEncoder.matches(rawPassword, storedPassword);
    }
}
