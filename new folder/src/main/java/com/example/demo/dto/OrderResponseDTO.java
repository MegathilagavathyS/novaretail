package com.example.demo.dto;

import java.time.LocalDateTime;

public class OrderResponseDTO {

    private Integer id;
    private LocalDateTime orderDate;
    private Double totalAmount;

    public OrderResponseDTO(
            Integer id,
            LocalDateTime orderDate,
            Double totalAmount) {

        this.id = id;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
    }

    public Integer getId() {
        return id;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }
}