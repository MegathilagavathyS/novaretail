package com.example.demo.controller;

import com.example.demo.dto.ProductRequestDTO;
import com.example.demo.dto.ProductResponseDTO;
import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(
            ProductService service) {

        this.service = service;
    }

    @PostMapping
    public ProductResponseDTO createProduct(
            @RequestBody ProductRequestDTO dto) {

        return service.createProduct(dto);
    }

    @GetMapping
    public List<ProductResponseDTO> getAllProducts() {

        return service.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductResponseDTO getProductById(
            @PathVariable Integer id) {

        return service.getProductById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(
            @PathVariable Integer id) {

        service.deleteProduct(id);

        return "Product deleted successfully";
    }

    @GetMapping("/category/{categoryId}")
    public List<Product> getProductsByCategory(
            @PathVariable Integer categoryId) {

        return service.getProductsByCategory(categoryId);
    }
}