package com.example.demo.service;

import com.example.demo.model.Category;
import com.example.demo.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(
            CategoryRepository repository) {

        this.repository = repository;
    }

    public Category createCategory(
            Category category) {

        return repository.save(category);
    }

    public List<Category> getAllCategories() {

        return repository.findAll();
    }

    public Category getCategoryById(
            Integer id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Category not found"));
    }
}