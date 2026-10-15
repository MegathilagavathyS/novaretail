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

    // CREATE
    public Category createCategory(
            Category category) {

        return repository.save(category);
    }

    // GET ALL
    public List<Category> getAllCategories() {

        return repository.findAll();
    }

    // DELETE
    public void deleteCategory(
            Integer id) {

        repository.deleteById(id);
    }
}