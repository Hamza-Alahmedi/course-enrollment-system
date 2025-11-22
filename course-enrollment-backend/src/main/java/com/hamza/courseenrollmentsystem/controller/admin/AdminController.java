package com.hamza.courseenrollmentsystem.controller.admin;

import com.hamza.courseenrollmentsystem.repository.CategoryRepository;
import com.hamza.courseenrollmentsystem.repository.CourseRepository;
import com.hamza.courseenrollmentsystem.repository.EnrollmentRepository;
import com.hamza.courseenrollmentsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @GetMapping("/admin")
    public String adminHome(Model model) {
        addDashboardStats(model);
        return "admin/admin_home";
    }

    @GetMapping("/admin/home")
    public String adminHomePage(Model model) {
        addDashboardStats(model);
        return "admin/admin_home";
    }

    private void addDashboardStats(Model model) {
        model.addAttribute("categoryCount", categoryRepository.count());
        model.addAttribute("courseCount", courseRepository.count());
        model.addAttribute("studentCount", userRepository.count() - 1); // Exclude admin
        model.addAttribute("enrollmentCount", enrollmentRepository.count());
    }
}
