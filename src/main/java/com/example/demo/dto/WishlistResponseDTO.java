package com.example.demo.dto;

public class WishlistResponseDTO {

    private Integer wishlistId;
    private Integer productId;
    private String productName;
    private Double price;

    public WishlistResponseDTO(
            Integer wishlistId,
            Integer productId,
            String productName,
            Double price) {

        this.wishlistId = wishlistId;
        this.productId = productId;
        this.productName = productName;
        this.price = price;
    }

    public Integer getWishlistId() {
        return wishlistId;
    }

    public Integer getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public Double getPrice() {
        return price;
    }
}