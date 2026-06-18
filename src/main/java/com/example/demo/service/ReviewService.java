package com.example.demo.service;

import com.example.demo.dto.ReviewRequestDTO;
import com.example.demo.dto.ReviewResponseDTO;
import com.example.demo.model.Product;
import com.example.demo.model.Review;
import com.example.demo.model.User;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public ReviewService(
            ReviewRepository reviewRepository,
            UserRepository userRepository,
            ProductRepository productRepository) {

        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    // ADD REVIEW
    public ReviewResponseDTO addReview(
            ReviewRequestDTO dto) {

        User user =
                userRepository.findById(
                                dto.getUserId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"));

        Product product =
                productRepository.findById(
                                dto.getProductId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Product not found"));

        reviewRepository
                .findByUserIdAndProductId(
                        dto.getUserId(),
                        dto.getProductId())
                .ifPresent(review -> {
                    throw new RuntimeException(
                            "Review already exists");
                });

        Review review = new Review();

        review.setUser(user);
        review.setProduct(product);
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());

        // IMPORTANT
        review.setReviewDate(
                LocalDateTime.now());

        Review saved =
                reviewRepository.save(review);

        updateAverageRating(product);

        return map(saved);
    }

    // GET REVIEWS OF PRODUCT
    public List<ReviewResponseDTO>
    getReviewsByProduct(
            Integer productId) {

        return reviewRepository
                .findByProductId(productId)
                .stream()
                .map(this::map)
                .toList();
    }

    // GET REVIEWS OF USER
    public List<ReviewResponseDTO>
    getReviewsByUser(
            Integer userId) {

        return reviewRepository
                .findByUserId(userId)
                .stream()
                .map(this::map)
                .toList();
    }

    // UPDATE REVIEW
    public ReviewResponseDTO updateReview(
            Integer reviewId,
            ReviewRequestDTO dto) {

        Review review =
                reviewRepository.findById(reviewId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Review not found"));

        review.setRating(dto.getRating());
        review.setComment(dto.getComment());

        Review updated =
                reviewRepository.save(review);

        updateAverageRating(
                review.getProduct());

        return map(updated);
    }

    // DELETE REVIEW
    public String deleteReview(
            Integer reviewId) {

        Review review =
                reviewRepository.findById(reviewId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Review not found"));

        Product product =
                review.getProduct();

        reviewRepository.delete(review);

        updateAverageRating(product);

        return "Review Deleted Successfully";
    }

    // AVERAGE RATING
    public Double getAverageRating(
            Integer productId) {

        List<Review> reviews =
                reviewRepository
                        .findByProductId(productId);

        if (reviews.isEmpty()) {
            return 0.0;
        }

        return reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);
    }

    // UPDATE PRODUCT TABLE AVG RATING
    private void updateAverageRating(
            Product product) {

        Double avg =
                getAverageRating(
                        product.getId());

        product.setAverageRating(avg);

        productRepository.save(product);
    }

    private ReviewResponseDTO map(
            Review review) {

        return new ReviewResponseDTO(
                review.getId(),
                review.getUser().getId(),
                review.getProduct().getId(),
                review.getRating(),
                review.getComment(),
                review.getReviewDate()
        );
    }
}