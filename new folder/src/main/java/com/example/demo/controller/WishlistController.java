package com.example.demo.controller;

import com.example.demo.dto.WishlistRequestDTO;
import com.example.demo.model.Wishlist;
import com.example.demo.service.WishlistService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    private final WishlistService service;

    public WishlistController(
            WishlistService service) {

        this.service = service;
    }

    @PostMapping
    public Wishlist addWishlist(
            @RequestBody WishlistRequestDTO dto) {

        return service.addWishlist(dto);
    }

    @GetMapping("/{userId}")
    public List<Wishlist> getWishlist(
            @PathVariable Integer userId) {

        return service.getWishlist(userId);
    }

    @DeleteMapping("/{id}")
    public String removeWishlist(
            @PathVariable Integer id) {

        return service.removeWishlist(id);
    }
}