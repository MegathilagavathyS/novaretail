package com.example.demo.dto;

import java.time.LocalDateTime;

public class ReviewResponseDTO {

    private Integer id;

    private Integer userId;

    private Integer productId;

    private Integer rating;

    private String comment;

    private LocalDateTime reviewDate;

    public ReviewResponseDTO() {
    }

    public ReviewResponseDTO(
            Integer id,
            Integer userId,
            Integer productId,
            Integer rating,
            String comment,
            LocalDateTime reviewDate) {

        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(
            String comment) {

        this.comment = comment;
    }

    public LocalDateTime getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(
            LocalDateTime reviewDate) {

        this.reviewDate = reviewDate;
    }
}