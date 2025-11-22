package com.hamza.courseenrollmentsystem.controller.admin;

import com.hamza.courseenrollmentsystem.entity.Course;
import com.hamza.courseenrollmentsystem.repository.CategoryRepository;
import com.hamza.courseenrollmentsystem.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/courses")
public class AdminCourseController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CategoryRepository categoryRepository;


    @GetMapping("/test")
    public @ResponseBody String test() {
        return "AdminCourseController is working!";
    }

    @GetMapping
    public String list(Model model) {
        System.out.println("AdminCourseController.list() called");
        var courses = courseRepository.findAll();
        System.out.println("Found " + courses.size() + " courses");
        model.addAttribute("courses", courses);
        System.out.println("Returning view: admin/courses");
        return "admin/courses";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("course", new Course());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("isEdit", false);
        return "admin/course_form";
    }

    @PostMapping("/add")
    public String save(@ModelAttribute Course course,
                      @RequestParam Long categoryId,
                      Model model,
                      RedirectAttributes redirectAttributes) {
        var category = categoryRepository.findById(categoryId).orElse(null);
        if (category == null) {
            redirectAttributes.addFlashAttribute("error", "Invalid category selected!");
            return "redirect:/admin/courses/add";
        }
        course.setCategory(category);
        courseRepository.save(course);
        model.addAttribute("courseTitle", course.getTitle());
        model.addAttribute("categoryName", category.getName());
        return "admin/course_success";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Course course = courseRepository.findById(id).orElse(null);
        if (course == null) {
            redirectAttributes.addFlashAttribute("error", "Course not found!");
            return "redirect:/admin/courses";
        }
        model.addAttribute("course", course);
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("isEdit", true);
        return "admin/course_form";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long id,
                        @ModelAttribute Course course,
                        @RequestParam Long categoryId,
                        RedirectAttributes redirectAttributes) {
        var category = categoryRepository.findById(categoryId).orElse(null);
        if (category == null) {
            redirectAttributes.addFlashAttribute("error", "Invalid category selected!");
            return "redirect:/admin/courses/edit/" + id;
        }
        course.setId(id);
        course.setCategory(category);
        courseRepository.save(course);
        redirectAttributes.addFlashAttribute("success", "Course updated successfully!");
        return "redirect:/admin/courses";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            courseRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Course deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Cannot delete course. It may have enrollments or feedback.");
        }
        return "redirect:/admin/courses";
    }
}

