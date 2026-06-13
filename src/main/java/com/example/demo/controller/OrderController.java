package com.example.demo.controller;

import com.example.demo.model.Order;
import com.example.demo.service.OrderService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(
            OrderService service) {

        this.service = service;
    }

    @PostMapping("/checkout/{userId}")
    public Order checkout(
            @PathVariable Integer userId) {

        return service.checkout(userId);
    }
}