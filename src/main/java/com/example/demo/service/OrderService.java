package com.example.demo.service;

import com.example.demo.dto.OrderResponseDTO;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.*;
import com.example.demo.repository.*;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final InventoryHistoryRepository inventoryHistoryRepository;
    private final CouponRepository couponRepository;

    public OrderService(
            OrderRepository orderRepository,
            UserRepository userRepository,
            CartRepository cartRepository,
            ProductRepository productRepository,
            InventoryHistoryRepository inventoryHistoryRepository,
            CouponRepository couponRepository) {

        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.inventoryHistoryRepository =
                inventoryHistoryRepository;
        this.couponRepository =
                couponRepository;
    }

    // PLACE ORDER
    public OrderResponseDTO placeOrder(
            Integer userId,
            String couponCode) {

        User user =
                userRepository.findById(userId)
                        .orElseThrow(() ->
                                new UserNotFoundException(
                                        "User not found"));

        Cart cart =
                cartRepository.findByUserId(userId)
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

            Integer orderedQuantity =
                    cartItem.getQuantity();

            // STOCK CHECK
            if (product.getStock()
                    < orderedQuantity) {

                throw new RuntimeException(
                        "Insufficient stock for product: "
                                + product.getName());
            }

            // REDUCE STOCK
            product.setStock(
                    product.getStock()
                            - orderedQuantity);

            productRepository.save(product);

            // INVENTORY HISTORY
            InventoryHistory history =
                    new InventoryHistory();

            history.setProduct(product);

            history.setQuantityChanged(
                    -orderedQuantity);

            history.setAction(
                    "ORDER_PLACED");

            history.setCreatedAt(
                    LocalDateTime.now());

            inventoryHistoryRepository
                    .save(history);

            OrderItem orderItem =
                    new OrderItem();

            orderItem.setOrder(order);

            orderItem.setProduct(product);

            orderItem.setQuantity(
                    orderedQuantity);

            orderItem.setPrice(
                    product.getPrice());

            totalAmount +=
                    product.getPrice()
                            * orderedQuantity;

            order.getItems()
                    .add(orderItem);
        }

        // APPLY COUPON
        if (couponCode != null &&
                !couponCode.isBlank()) {

            totalAmount =
                    applyCoupon(
                            totalAmount,
                            couponCode);
        }

        order.setTotalAmount(
                totalAmount);

        Order savedOrder =
                orderRepository.save(order);

        // CLEAR CART
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

    // APPLY COUPON
    public Double applyCoupon(
            Double amount,
            String couponCode) {

        Coupon coupon =
                couponRepository
                        .findByCode(couponCode)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Coupon not found"));

        if (!coupon.getActive()) {

            throw new RuntimeException(
                    "Coupon inactive");
        }

        if (coupon.getExpiryDate()
                .isBefore(LocalDateTime.now())) {

            throw new RuntimeException(
                    "Coupon expired");
        }

        double discount =
                amount *
                        coupon.getDiscountPercentage()
                        / 100;

        return amount - discount;
    }
}
