package com.example.demo.service;

import com.example.demo.dto.CartRequestDTO;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;

    public CartService(
            CartRepository cartRepository,
            ProductRepository productRepository,
            UserRepository userRepository,
            CartItemRepository cartItemRepository) {

        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.cartItemRepository = cartItemRepository;
    }

    public Cart addToCart(CartRequestDTO dto) {

        User user =
                userRepository.findById(dto.getUserId())
                        .orElseThrow();

        Product product =
                productRepository.findById(dto.getProductId())
                        .orElseThrow();

        Cart cart =
                cartRepository.findByUserId(user.getId())
                        .orElseGet(() -> {

                            Cart newCart = new Cart();
                            newCart.setUser(user);

                            return cartRepository.save(newCart);
                        });

        CartItem item = new CartItem();

        item.setCart(cart);
        item.setProduct(product);
        item.setQuantity(dto.getQuantity());

        cartItemRepository.save(item);

        return cartRepository.findById(cart.getId()).orElseThrow();
    }

    public Cart getCart(Integer userId) {

        return cartRepository.findByUserId(userId)
                .orElseThrow();
    }
}