package com.hamza.courseenrollmentsystem.config;

import com.hamza.courseenrollmentsystem.entity.User;
import com.hamza.courseenrollmentsystem.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        // Check if user has ADMIN role
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            // Redirect to admin dashboard
            response.sendRedirect("/admin/home");
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_STUDENT"))) {
            // Get student ID and redirect to React dashboard with ID in URL
            String email = authentication.getName();
            User student = userRepository.findByEmail(email).orElse(null);
            if (student != null) {
                // Redirect to React frontend with student ID as query parameter
                response.sendRedirect("http://localhost:5173?studentId=" + student.getId());
            } else {
                response.sendRedirect("/student/dashboard");
            }
        } else {
            // Default redirect
            response.sendRedirect("/");
        }
    }
}

