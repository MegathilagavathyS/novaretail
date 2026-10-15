package com.example.demo.dto;

import com.example.demo.model.OrderStatus;

public class OrderStatusRequestDTO {

    private OrderStatus status;

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(
            OrderStatus status) {

        this.status = status;
    }
}