package com.example.demo.dto;

public class WishlistRequestDTO {

    private Integer userId;
    private Integer productId;

    public WishlistRequestDTO() {
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
}