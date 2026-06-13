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

    @PostMapping("/place/{userId}")
    public OrderResponseDTO placeOrder(
            @PathVariable Integer userId) {

        return service.placeOrder(userId);
    }

    @GetMapping("/{userId}")
    public List<Order> getOrders(
            @PathVariable Integer userId) {

        return service.getOrdersByUser(userId);
    }
}