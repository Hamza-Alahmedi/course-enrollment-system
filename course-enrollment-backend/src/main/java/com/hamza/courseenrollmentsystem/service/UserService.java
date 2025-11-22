package com.hamza.courseenrollmentsystem.service;

import com.hamza.courseenrollmentsystem.entity.User;
import com.hamza.courseenrollmentsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder encoder;

    @Autowired
    public UserService(@Lazy PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    public User registerUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole("STUDENT"); // default role
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}
