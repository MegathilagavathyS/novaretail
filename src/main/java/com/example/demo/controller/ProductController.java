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

    // CREATE PRODUCT

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ProductResponseDTO createProduct(
            @RequestBody ProductRequestDTO dto) {

        return productService.createProduct(dto);
    }

    // GET ALL PRODUCTS

    @GetMapping
    public List<ProductResponseDTO> getAllProducts() {

        return productService.getAllProducts();
    }

    // GET PRODUCT

    @GetMapping("/{id}")
    public ProductResponseDTO getProduct(
            @PathVariable Integer id) {

        return productService.getProductById(id);
    }

    // DELETE PRODUCT

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String deleteProduct(
            @PathVariable Integer id) {

        productService.deleteProduct(id);

        return "Product deleted";
    }

    // CATEGORY FILTER

    @GetMapping("/category/{categoryId}")
    public List<Product> getProductsByCategory(
            @PathVariable Integer categoryId) {

        return productService
                .getProductsByCategory(categoryId);
    }

    // SEARCH

    @GetMapping("/search")
    public List<Product> searchProducts(
            @RequestParam String keyword) {

        return productService
                .searchProducts(keyword);
    }

    // PRICE FILTER

    @GetMapping("/price")
    public List<Product> filterByPrice(

            @RequestParam Double min,

            @RequestParam Double max) {

        return productService
                .filterByPrice(min, max);
    }

    // SORT

    @GetMapping("/sort")
    public List<Product> sortProducts(
            @RequestParam String field) {

        return productService
                .sortProducts(field);
    }
}