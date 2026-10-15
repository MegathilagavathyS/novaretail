package com.example.demo.controller;

import com.example.demo.model.Payment;
import com.example.demo.service.PaymentService;
import com.example.demo.dto.PaymentVerificationRequest;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService service;

    public PaymentController(
            PaymentService service) {

        this.service = service;
    }

    @PostMapping("/create/{orderId}")
    public String createPayment(
            @PathVariable Integer orderId)
            throws Exception {

        return service.createPayment(
                orderId);
    }

    @GetMapping("/{orderId}")
    public Payment getPayment(
            @PathVariable Integer orderId) {

        return service.getPaymentByOrder(
                orderId);
    }

    @PostMapping("/verify")
    public String verifyPayment(
            @RequestBody PaymentVerificationRequest request) {

        return service.verifyPayment(request);
    }
}