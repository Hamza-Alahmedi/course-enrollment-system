package com.hamza.courseenrollmentsystem.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Utility to generate BCrypt password hashes for manual database updates
 * Run this class to generate hashes for your test users
 */
public class PasswordHashGenerator {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // Common test passwords
        String[] passwords = {
            "password123",
            "admin123",
            "student123",
            "test123"
        };

        System.out.println("=== BCrypt Password Hashes ===\n");

        for (String password : passwords) {
            String hash = encoder.encode(password);
            System.out.println("Password: " + password);
            System.out.println("BCrypt Hash: " + hash);
            System.out.println("SQL Update Example:");
            System.out.println("UPDATE users SET password = '" + hash + "' WHERE email = 'user@example.com';");
            System.out.println("\n---\n");
        }

        // Example verification
        String testPassword = "password123";
        String testHash = encoder.encode(testPassword);
        boolean matches = encoder.matches(testPassword, testHash);

        System.out.println("=== Verification Test ===");
        System.out.println("Password: " + testPassword);
        System.out.println("Hash: " + testHash);
        System.out.println("Matches: " + matches);
    }
}

