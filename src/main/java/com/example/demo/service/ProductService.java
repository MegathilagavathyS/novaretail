package com.example.demo.service;

import com.example.demo.dto.ProductRequestDTO;
import com.example.demo.dto.ProductResponseDTO;
import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(
            ProductRepository productRepository,
            CategoryRepository categoryRepository) {

        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    // CREATE PRODUCT

    public ProductResponseDTO createProduct(
            ProductRequestDTO dto) {

        Category category =
                categoryRepository.findById(
                                dto.getCategoryId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Category not found"));

        Product product = new Product();

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setCategory(category);

        Product saved =
                productRepository.save(product);

        return map(saved);
    }

    // GET ALL PRODUCTS

    public List<ProductResponseDTO> getAllProducts() {

        return productRepository.findAll()
                .stream()
                .map(this::map)
                .toList();
    }

    // GET PRODUCT BY ID

    public ProductResponseDTO getProductById(
            Integer id) {

        Product product =
                productRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Product not found"));

        return map(product);
    }

    // DELETE PRODUCT

    public void deleteProduct(
            Integer id) {

        Product product =
                productRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Product not found"));

        productRepository.delete(product);
    }

    // CATEGORY FILTER

    public List<Product> getProductsByCategory(
            Integer categoryId) {

        return productRepository
                .findByCategoryId(categoryId);
    }

    // SEARCH

    public List<Product> searchProducts(
            String keyword) {

        return productRepository
                .findByNameContainingIgnoreCase(
                        keyword);
    }

    // PRICE FILTER

    public List<Product> filterByPrice(
            Double min,
            Double max) {

        return productRepository
                .findByPriceBetween(
                        min,
                        max);
    }

    // SORT

    public List<Product> sortProducts(
            String field) {

        return productRepository.findAll(
                Sort.by(field));
    }

    // DTO MAPPER

    private ProductResponseDTO map(
            Product product) {

        Double averageRating = 0.0;
        Integer reviewCount = 0;

        if (product.getReviews() != null &&
                !product.getReviews().isEmpty()) {

            reviewCount =
                    product.getReviews().size();

            averageRating =
                    product.getReviews()
                            .stream()
                            .mapToDouble(r -> r.getRating())
                            .average()
                            .orElse(0.0);
        }

        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getCategory().getName(),
                averageRating,
                reviewCount
        );
    }
}