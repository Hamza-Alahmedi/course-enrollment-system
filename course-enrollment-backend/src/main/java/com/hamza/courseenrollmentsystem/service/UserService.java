package com.hamza.courseenrollmentsystem.service;

import com.hamza.courseenrollmentsystem.entity.User;
import com.hamza.courseenrollmentsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(User user) {
        // Encode password with BCrypt
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("STUDENT"); // default role
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        // Check if password is already BCrypt encoded (starts with $2a$ or $2b$)
        if (encodedPassword.startsWith("$2a$") || encodedPassword.startsWith("$2b$")) {
            // Use BCrypt to check password
            return passwordEncoder.matches(rawPassword, encodedPassword);
        } else {
            // Legacy plain text password - compare directly
            return rawPassword.equals(encodedPassword);
        }
    }

    /**
     * Upgrade plain text password to BCrypt hash
     * Call this after successful plain text authentication
     */
    public void upgradePasswordToBCrypt(User user, String plainPassword) {
        if (!user.getPassword().startsWith("$2a$") && !user.getPassword().startsWith("$2b$")) {
            user.setPassword(passwordEncoder.encode(plainPassword));
            userRepository.save(user);
            System.out.println("✅ Upgraded password to BCrypt for user: " + user.getEmail());
        }
    }
}
