package com.example.demo.controller;

import com.example.demo.dto.ReviewRequestDTO;
import com.example.demo.dto.ReviewResponseDTO;
import com.example.demo.service.ReviewService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService service;

    public ReviewController(
            ReviewService service) {

        this.service = service;
    }

    // ADD REVIEW
    @PostMapping
    public ReviewResponseDTO addReview(
            @RequestBody ReviewRequestDTO dto) {

        return service.addReview(dto);
    }

    // GET REVIEWS OF PRODUCT
    @GetMapping("/product/{productId}")
    public List<ReviewResponseDTO>
    getReviewsByProduct(
            @PathVariable Integer productId) {

        return service.getReviewsByProduct(
                productId);
    }

    // GET REVIEWS OF USER
    @GetMapping("/user/{userId}")
    public List<ReviewResponseDTO>
    getReviewsByUser(
            @PathVariable Integer userId) {

        return service.getReviewsByUser(
                userId);
    }

    // UPDATE REVIEW
    @PutMapping("/{reviewId}")
    public ReviewResponseDTO updateReview(
            @PathVariable Integer reviewId,
            @RequestBody ReviewRequestDTO dto) {

        return service.updateReview(
                reviewId,
                dto);
    }

    // DELETE REVIEW
    @DeleteMapping("/{reviewId}")
    public String deleteReview(
            @PathVariable Integer reviewId) {

        return service.deleteReview(
                reviewId);
    }

    // PRODUCT AVERAGE RATING
    @GetMapping("/rating/{productId}")
    public Double averageRating(
            @PathVariable Integer productId) {

        return service.getAverageRating(
                productId);
    }
}