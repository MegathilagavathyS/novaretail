package com.example.demo.controller;

import com.example.demo.dto.CartRequestDTO;
import com.example.demo.model.Cart;
import com.example.demo.service.CartService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService service;

    public CartController(CartService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public Cart addToCart(
            @RequestBody CartRequestDTO dto) {

        return service.addToCart(dto);
    }

    @GetMapping("/{userId}")
    public Cart getCart(
            @PathVariable Integer userId) {

        return service.getCart(userId);
    }
}