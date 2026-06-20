package com.example.demo.repository;

import com.example.demo.model.Product;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository
        extends JpaRepository<Product, Integer> {

    List<Product> findByCategoryId(
            Integer categoryId);

    List<Product> findByNameContainingIgnoreCase(
            String keyword);

    List<Product> findByPriceBetween(
            Double minPrice,
            Double maxPrice);

    List<Product> findByStockLessThan(
            Integer stock);

    List<Product> findAll(
            Sort sort);
}