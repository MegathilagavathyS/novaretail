package com.example.demo.controller;

import com.example.demo.model.Category;
import com.example.demo.service.CategoryService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(
            CategoryService service) {

        this.service = service;
    }

    // ADMIN ONLY
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Category createCategory(
            @RequestBody Category category) {

        return service.createCategory(category);
    }

    // EVERY LOGGED-IN USER
    @GetMapping
    public List<Category> getAllCategories() {

        return service.getAllCategories();
    }

    // ADMIN ONLY
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String deleteCategory(
            @PathVariable Integer id) {

        service.deleteCategory(id);

        return "Category deleted successfully";
    }
}