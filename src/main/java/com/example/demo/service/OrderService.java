package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repository.*;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;

    public OrderService(
            CartRepository cartRepository,
            OrderRepository orderRepository) {

        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public Order checkout(Integer userId) {

        Cart cart =
                cartRepository.findByUserId(userId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Cart not found"));

        Order order = new Order();

        order.setUser(cart.getUser());
        order.setOrderDate(
                java.time.LocalDateTime.now());
        order.setStatus(
                OrderStatus.PENDING);

        for (CartItem cartItem :
                cart.getItems()) {

            OrderItem orderItem =
                    new OrderItem();

            orderItem.setProduct(
                    cartItem.getProduct());

            orderItem.setQuantity(
                    cartItem.getQuantity());

            orderItem.setOrder(order);

            order.getItems().add(orderItem);
        }

        Order savedOrder =
                orderRepository.save(order);

        cart.getItems().clear();

        cartRepository.save(cart);

        return savedOrder;
    }
}