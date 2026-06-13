package com.example.demo.dto;

public class ProductResponseDTO {

    private Integer id;
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private String categoryName;

    public ProductResponseDTO() {
    }

    public ProductResponseDTO(
            Integer id,
            String name,
            String description,
            Double price,
            Integer stock,
            String categoryName) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.categoryName = categoryName;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getStock() {
        return stock;
    }

    public String getCategoryName() {
        return categoryName;
    }
}