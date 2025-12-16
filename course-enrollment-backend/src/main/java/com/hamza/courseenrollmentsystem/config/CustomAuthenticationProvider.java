package com.hamza.courseenrollmentsystem.config;

import com.hamza.courseenrollmentsystem.entity.User;
import com.hamza.courseenrollmentsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            throw new BadCredentialsException("User not found");
        }

        User user = userOptional.get();

        // Check password (handles both plain text and BCrypt)
        boolean passwordMatches;
        if (user.getPassword().startsWith("$2a$") || user.getPassword().startsWith("$2b$")) {
            // BCrypt password
            passwordMatches = passwordEncoder.matches(password, user.getPassword());
        } else {
            // Plain text password
            passwordMatches = password.equals(user.getPassword());

            // Upgrade to BCrypt if plain text
            if (passwordMatches) {
                user.setPassword(passwordEncoder.encode(password));
                userRepository.save(user);
                System.out.println("✅ Upgraded password to BCrypt for user: " + user.getEmail());
            }
        }

        if (passwordMatches) {
            return new UsernamePasswordAuthenticationToken(
                    email,
                    password,
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
            );
        } else {
            throw new BadCredentialsException("Invalid password");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

