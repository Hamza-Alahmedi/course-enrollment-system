package com.hamza.courseenrollmentsystem.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/student")
public class StudentController {

    @GetMapping("/dashboard")
    public String studentDashboard(Authentication authentication, Model model) {
        // Get the logged-in user's email
        String email = authentication.getName();
        model.addAttribute("email", email);
        return "student/student_dashboard";
    }
}

