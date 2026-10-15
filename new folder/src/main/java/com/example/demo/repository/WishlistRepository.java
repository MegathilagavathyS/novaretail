package com.example.demo.repository;

import com.example.demo.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishlistRepository
        extends JpaRepository<Wishlist, Integer> {

    List<Wishlist> findByUserId(
            Integer userId);

    Optional<Wishlist>
    findByUserIdAndProductId(
            Integer userId,
            Integer productId);
}