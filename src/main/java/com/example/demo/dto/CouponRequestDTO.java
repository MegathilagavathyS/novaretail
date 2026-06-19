package com.example.demo.dto;

public class CouponRequestDTO {

    private String code;

    private Double discountPercentage;

    private String expiryDate;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(
            Double discountPercentage) {

        this.discountPercentage = discountPercentage;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(
            String expiryDate) {

        this.expiryDate = expiryDate;
    }
}