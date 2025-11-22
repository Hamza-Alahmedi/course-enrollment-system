package com.hamza.courseenrollmentsystem.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DebugController {

    @GetMapping("/admin/debug")
    @ResponseBody
    public String debug() {
        return "Debug route works! If you see this, Spring MVC is working. Issue might be with Thymeleaf templates.";
    }

    @GetMapping("/admin/categories-test")
    @ResponseBody
    public String categoriesTest() {
        return "Categories route mapping works! The issue is with template resolution.";
    }
}

