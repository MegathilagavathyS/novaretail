package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    private Double price;

    private Integer stock;

    private Double averageRating = 0.0;

    private Integer reviewCount = 0;
    //private Double averageRating = 0.0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @JsonBackReference
    private Category category;


    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
    private List<CartItem> cartItems =
            new ArrayList<>();


    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
    private List<OrderItem> orderItems =
            new ArrayList<>();

    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL
    )
    @JsonIgnore
    private List<Wishlist> wishlists;

    @JsonIgnore
    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )

    private List<InventoryHistory> inventoryHistory =
            new ArrayList<>();

    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonIgnore
    private List<Review> reviews = new ArrayList<>();



    public Product() {
    }

    public Product(
            Integer id,
            String name,
            String description,
            Double price,
            Integer stock,
            Category category) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(
            String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(
            String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(
            Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(
            Integer stock) {
        this.stock = stock;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(
            Category category) {
        this.category = category;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(
            List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(
            List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public List<Wishlist> getWishlists() {
        return wishlists;
    }

    public void setWishlists(
            List<Wishlist> wishlists) {

        this.wishlists = wishlists;
    }

    public List<InventoryHistory> getInventoryHistory() {
        return inventoryHistory;
    }

    public void setInventoryHistory(
            List<InventoryHistory> inventoryHistory) {

        this.inventoryHistory = inventoryHistory;
    }
    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(
            List<Review> reviews) {

        this.reviews = reviews;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(
            Double averageRating) {

        this.averageRating = averageRating;
    }

    public Integer getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(
            Integer reviewCount) {

        this.reviewCount = reviewCount;
    }
}