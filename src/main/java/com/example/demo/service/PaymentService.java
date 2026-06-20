package com.example.demo.service;

import com.example.demo.model.Order;
import com.example.demo.model.Payment;
import com.example.demo.model.OrderStatus;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.PaymentRepository;
import com.example.demo.dto.PaymentVerificationRequest;

import com.razorpay.OrderClient;
import com.razorpay.RazorpayClient;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    @Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.key.secret}")
    private String keySecret;

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;

    public PaymentService(
            OrderRepository orderRepository,
            PaymentRepository paymentRepository) {

        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
    }

    public String createPayment(
            Integer orderId)
            throws Exception {

        Order order =
                orderRepository.findById(orderId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Order not found"));

        RazorpayClient razorpay =
                new RazorpayClient(
                        keyId,
                        keySecret);

        JSONObject options =
                new JSONObject();

        options.put(
                "amount",
                order.getTotalAmount() * 100);

        options.put(
                "currency",
                "INR");

        options.put(
                "receipt",
                "order_" + orderId);

        com.razorpay.Order razorOrder =
                razorpay.orders.create(options);

        Payment payment =
                new Payment();

        payment.setOrder(order);

        payment.setAmount(
                order.getTotalAmount());

        payment.setPaymentStatus(
                "PENDING");

        payment.setPaymentDate(
                LocalDateTime.now());

        payment.setRazorpayOrderId(
                razorOrder.get("id"));

        paymentRepository.save(payment);

        return razorOrder.toString();
    }

    public Payment getPaymentByOrder(
            Integer orderId) {

        return paymentRepository
                .findByOrderId(orderId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Payment not found"));
    }
    public String verifyPayment(
            PaymentVerificationRequest request) {

        Payment payment =
                paymentRepository
                        .findByRazorpayOrderId(
                                request.getRazorpayOrderId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Payment not found"));

        payment.setRazorpayPaymentId(
                request.getRazorpayPaymentId());

        payment.setRazorpaySignature(
                request.getRazorpaySignature());

        payment.setPaymentStatus(
                "SUCCESS");

        Order order =
                payment.getOrder();

        // FIXED
        order.setStatus(
                OrderStatus.PAID);

        orderRepository.save(order);

        paymentRepository.save(payment);

        return "Payment Verified Successfully";
    }
}