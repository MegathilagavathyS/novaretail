package com.example.demo.controller;

import com.example.demo.dto.ProductRequestDTO;
import com.example.demo.dto.ProductResponseDTO;
import com.example.demo.model.Product;
import com.example.demo.service.ProductService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(
            ProductService productService) {

        this.productService = productService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ProductResponseDTO createProduct(
            @RequestBody ProductRequestDTO dto) {

        return productService.createProduct(dto);
    }

    @GetMapping
    public List<ProductResponseDTO> getAllProducts() {

        return productService.getAllProducts();
    }

    @GetMapping("/category/{categoryId}")
    public List<Product> getProductsByCategory(
            @PathVariable Integer categoryId) {

        return productService
                .getProductsByCategory(categoryId);
    }

    @GetMapping("/search")
    public List<Product> searchProducts(
            @RequestParam String keyword) {

        return productService
                .searchProducts(keyword);
    }

    @GetMapping("/filter")
    public List<Product> filterProducts(
            @RequestParam Double minPrice,
            @RequestParam Double maxPrice) {

        return productService
                .filterProducts(
                        minPrice,
                        maxPrice);
    }

    @GetMapping("/sort")
    public List<Product> sortProducts(
            @RequestParam String field) {

        return productService
                .sortProducts(field);
    }
}