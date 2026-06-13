package com.example.demo.controller;

import com.example.demo.model.Category;
import com.example.demo.service.CategoryService;

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

    @PostMapping
    public Category createCategory(
            @RequestBody Category category) {

        return service.createCategory(category);
    }

    @GetMapping
    public List<Category> getAllCategories() {

        return service.getAllCategories();
    }

    @GetMapping("/{id}")
    public Category getCategory(
            @PathVariable Integer id) {

        return service.getCategoryById(id);
    }
}