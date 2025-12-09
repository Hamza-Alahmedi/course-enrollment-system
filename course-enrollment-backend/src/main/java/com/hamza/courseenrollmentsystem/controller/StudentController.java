package com.hamza.courseenrollmentsystem.controller;

import com.hamza.courseenrollmentsystem.entity.Enrollment;
import com.hamza.courseenrollmentsystem.entity.User;
import com.hamza.courseenrollmentsystem.repository.EnrollmentRepository;
import com.hamza.courseenrollmentsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @GetMapping("/dashboard")
    public String studentDashboard(Authentication authentication, Model model) {
        // Get the logged-in user's email
        String email = authentication.getName();
        model.addAttribute("email", email);

        // Get user's enrollments
        User user = userRepository.findByEmail(email).orElse(null);
        if (user != null) {
            List<Enrollment> enrollments = enrollmentRepository.findByUser(user);
            model.addAttribute("enrollments", enrollments);
        }

        return "student/student_dashboard";
    }
}

