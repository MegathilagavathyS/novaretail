package com.example.demo.repository;

import com.example.demo.model.Review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository
        extends JpaRepository<Review,Integer> {

    List<Review> findByProductId(
            Integer productId);

    List<Review> findByUserId(
            Integer userId);

    Optional<Review> findByUserIdAndProductId(
            Integer userId,
            Integer productId);

    @Query("""
    SELECT r.product.id,
    AVG(r.rating)
    FROM Review r
    GROUP BY r.product.id
    ORDER BY AVG(r.rating) DESC
    """)
    List<Object[]> topRatedProducts();
}