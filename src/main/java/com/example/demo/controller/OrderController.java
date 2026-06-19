package com.example.demo.controller;

import com.example.demo.dto.OrderResponseDTO;
import com.example.demo.dto.OrderStatusRequestDTO;
import com.example.demo.model.Order;
import com.example.demo.service.OrderService;

import org.springframework.security.access.prepost.PreAuthorize;
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

    // PLACE ORDER

    @PostMapping("/{userId}")
    public OrderResponseDTO placeOrder(

            @PathVariable Integer userId,

            @RequestParam(
                    required = false)
            String couponCode) {

        return service.placeOrder(
                userId,
                couponCode);
    }

    // GET USER ORDERS

    @GetMapping("/user/{userId}")
    public List<Order> getOrders(

            @PathVariable Integer userId) {

        return service
                .getOrdersByUser(userId);
    }

    // GET SINGLE ORDER

    @GetMapping("/{orderId}")
    public Order getOrder(

            @PathVariable Integer orderId) {

        return service
                .getOrderById(orderId);
    }

    // ADMIN ONLY

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{orderId}/status")
    public Order updateStatus(

            @PathVariable Integer orderId,

            @RequestBody
            OrderStatusRequestDTO dto) {

        return service
                .updateOrderStatus(
                        orderId,
                        dto.getStatus());
    }
}