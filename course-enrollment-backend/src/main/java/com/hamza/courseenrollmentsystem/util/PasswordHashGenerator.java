package com.hamza.courseenrollmentsystem.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Utility class to generate BCrypt password hashes.
 * Use this to manually hash passwords for database migration.
 *
 * Run this class and copy the generated hash to update user passwords in the database.
 */
public class PasswordHashGenerator {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // Example passwords to hash
        String[] passwords = {
            "admin123",
            "student123",
            "password123"
        };

        System.out.println("=== BCrypt Password Hash Generator ===\n");

        for (String password : passwords) {
            String hash = encoder.encode(password);
            System.out.println("Plain text: " + password);
            System.out.println("BCrypt hash: " + hash);
            System.out.println("SQL Update: UPDATE users SET password = '" + hash + "' WHERE email = 'your@email.com';");
            System.out.println();
        }

        System.out.println("=== Instructions ===");
        System.out.println("1. Copy the BCrypt hash for your password");
        System.out.println("2. Run the SQL UPDATE command on your database");
        System.out.println("3. Replace 'your@email.com' with the actual user email");
        System.out.println("4. Repeat for all users in your system");
    }
}

