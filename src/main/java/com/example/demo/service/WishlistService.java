package com.example.demo.service;

import com.example.demo.dto.WishlistRequestDTO;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.model.Wishlist;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.WishlistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public WishlistService(
            WishlistRepository wishlistRepository,
            UserRepository userRepository,
            ProductRepository productRepository) {

        this.wishlistRepository = wishlistRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public Wishlist addWishlist(
            WishlistRequestDTO dto) {

        boolean exists =
                wishlistRepository
                        .findByUserIdAndProductId(
                                dto.getUserId(),
                                dto.getProductId())
                        .isPresent();

        if (exists) {

            throw new RuntimeException(
                    "Product already in wishlist");
        }

        User user =
                userRepository.findById(
                                dto.getUserId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"));

        Product product =
                productRepository.findById(
                                dto.getProductId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Product not found"));

        Wishlist wishlist =
                new Wishlist();

        wishlist.setUser(user);
        wishlist.setProduct(product);

        return wishlistRepository.save(
                wishlist);
    }

    public List<Wishlist> getWishlist(
            Integer userId) {

        return wishlistRepository
                .findByUserId(userId);
    }

    public String removeWishlist(
            Integer id) {

        wishlistRepository.deleteById(id);

        return "Wishlist item removed";
    }
}