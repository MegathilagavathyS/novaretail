package com.example.demo.controller;

import com.example.demo.dto.OrderResponseDTO;
import com.example.demo.model.Order;
import com.example.demo.service.OrderService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(
            OrderService service) {

        this.service = service;
    }

    @PostMapping("/{userId}")
    public OrderResponseDTO placeOrder(
            @PathVariable Integer userId,

            @RequestParam(required = false)
            String couponCode) {

        return service.placeOrder(
                userId,
                couponCode);
    }

    @GetMapping("/{userId}")
    public List<Order> getOrders(
            @PathVariable Integer userId) {

        return service.getOrdersByUser(userId);
    }
}