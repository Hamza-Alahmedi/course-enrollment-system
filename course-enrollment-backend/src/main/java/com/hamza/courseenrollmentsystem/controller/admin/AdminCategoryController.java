package com.hamza.courseenrollmentsystem.controller.admin;

import com.hamza.courseenrollmentsystem.entity.Category;
import com.hamza.courseenrollmentsystem.repository.CategoryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoryController {

    private final CategoryRepository repo;

    public AdminCategoryController(CategoryRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public String list(Model model) {
        System.out.println("AdminCategoryController.list() called");
        var categories = repo.findAll();
        System.out.println("Found " + categories.size() + " categories");
        model.addAttribute("categories", categories);
        System.out.println("Returning view: admin/categories");
        return "admin/categories";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("isEdit", false);
        return "admin/category_form";
    }

    @PostMapping("/add")
    public String save(@ModelAttribute Category category, Model model) {
        repo.save(category);
        model.addAttribute("categoryName", category.getName());
        return "admin/category_success";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Category category = repo.findById(id).orElse(null);
        if (category == null) {
            redirectAttributes.addFlashAttribute("error", "Category not found!");
            return "redirect:/admin/categories";
        }
        model.addAttribute("category", category);
        model.addAttribute("isEdit", true);
        return "admin/category_form";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Category category, RedirectAttributes redirectAttributes) {
        category.setId(id);
        repo.save(category);
        redirectAttributes.addFlashAttribute("success", "Category updated successfully!");
        return "redirect:/admin/categories";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            repo.deleteById(id);
            redirectAttributes.addFlashAttribute("success", "Category deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Cannot delete category. It may have associated courses.");
        }
        return "redirect:/admin/categories";
    }
}
