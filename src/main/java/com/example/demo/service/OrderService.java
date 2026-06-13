package com.example.demo.service;

import com.example.demo.dto.OrderResponseDTO;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.*;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    public OrderService(
            OrderRepository orderRepository,
            UserRepository userRepository,
            CartRepository cartRepository) {

        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
    }

    // PLACE ORDER
    public OrderResponseDTO placeOrder(
            Integer userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found"));

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Cart not found"));

        if (cart.getItems().isEmpty()) {
            throw new RuntimeException(
                    "Cart is empty");
        }

        Order order = new Order();

        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");

        double totalAmount = 0.0;

        for (CartItem cartItem : cart.getItems()) {

            Product product =
                    cartItem.getProduct();

            OrderItem orderItem =
                    new OrderItem();

            orderItem.setOrder(order);

            orderItem.setProduct(product);

            orderItem.setQuantity(
                    cartItem.getQuantity());

            // IMPORTANT
            orderItem.setPrice(
                    product.getPrice());

            totalAmount +=
                    product.getPrice()
                            * cartItem.getQuantity();

            order.getItems().add(orderItem);
        }

        // IMPORTANT
        order.setTotalAmount(totalAmount);

        Order savedOrder =
                orderRepository.save(order);

        // Clear Cart
        cart.getItems().clear();

        cartRepository.save(cart);

        return new OrderResponseDTO(
                savedOrder.getId(),
                savedOrder.getOrderDate(),
                savedOrder.getTotalAmount()
        );
    }

    // GET ORDERS OF USER
    public List<Order> getOrdersByUser(
            Integer userId) {

        return orderRepository.findByUserId(userId);
    }

    // GET ORDER BY ID
    public Order getOrderById(
            Integer orderId) {

        return orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Order not found"));
    }
}