package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String razorpayOrderId;

    private String transactionId;

    private Double amount;

    private String paymentStatus;

    private LocalDateTime paymentDate;

    private String razorpayPaymentId;

    private String razorpaySignature;

    @OneToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Order order;

    public Payment() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRazorpayOrderId() {
        return razorpayOrderId;
    }

    public void setRazorpayOrderId(
            String razorpayOrderId) {

        this.razorpayOrderId = razorpayOrderId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(
            String transactionId) {

        this.transactionId = transactionId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(
            Double amount) {

        this.amount = amount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(
            String paymentStatus) {

        this.paymentStatus = paymentStatus;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(
            LocalDateTime paymentDate) {

        this.paymentDate = paymentDate;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(
            Order order) {

        this.order = order;
    }

    public String getRazorpayPaymentId() {
        return razorpayPaymentId;
    }

    public void setRazorpayPaymentId(
            String razorpayPaymentId) {

        this.razorpayPaymentId =
                razorpayPaymentId;
    }

    public String getRazorpaySignature() {
        return razorpaySignature;
    }

    public void setRazorpaySignature(
            String razorpaySignature) {

        this.razorpaySignature =
                razorpaySignature;
    }
}