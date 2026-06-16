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

    public List<ProductResponseDTO> getAllProducts() {

        return productRepository.findAll()
                .stream()
                .map(this::map)
                .toList();
    }

    public ProductResponseDTO getProductById(
            Integer id) {

        Product product =
                productRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Product not found"));

        return map(product);
    }

    public void deleteProduct(
            Integer id) {

        Product product =
                productRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Product not found"));

        productRepository.delete(product);
    }

    public List<Product> getProductsByCategory(
            Integer categoryId) {

        return productRepository
                .findByCategoryId(categoryId);
    }

    public List<Product> searchProducts(
            String keyword) {

        return productRepository
                .findByNameContainingIgnoreCase(
                        keyword);
    }

    public List<Product> filterProducts(
            Double minPrice,
            Double maxPrice) {

        return productRepository
                .findByPriceBetween(
                        minPrice,
                        maxPrice);
    }

    public List<Product> sortProducts(
            String field) {

        return productRepository.findAll(
                Sort.by(field));
    }

    private ProductResponseDTO map(
            Product product) {

        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getCategory().getName()
        );
    }
}