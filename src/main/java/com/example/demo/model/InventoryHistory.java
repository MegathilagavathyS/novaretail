package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventory_history")
public class InventoryHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer quantityChanged;

    private String action;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "product_id")

    @JsonIgnore
    private Product product;

    public InventoryHistory() {
    }

    public Integer getId() {
        return id;
    }

    public Integer getQuantityChanged() {
        return quantityChanged;
    }

    public void setQuantityChanged(
            Integer quantityChanged) {

        this.quantityChanged = quantityChanged;
    }

    public String getAction() {
        return action;
    }

    public void setAction(
            String action) {

        this.action = action;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(
            LocalDateTime createdAt) {

        this.createdAt = createdAt;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(
            Product product) {

        this.product = product;
    }
}