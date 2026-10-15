package com.example.demo.service;

import com.example.demo.dto.DashboardResponseDTO;
import com.example.demo.model.Product;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ReviewRepository;
import com.example.demo.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final ReviewRepository reviewRepository;

    public DashboardService(
            UserRepository userRepository,
            ProductRepository productRepository,
            OrderRepository orderRepository,
            ReviewRepository reviewRepository) {

        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.reviewRepository = reviewRepository;
    }

    // MAIN DASHBOARD

    public DashboardResponseDTO getDashboard() {

        Long totalUsers =
                userRepository.count();

        Long totalProducts =
                productRepository.count();

        Long totalOrders =
                orderRepository.count();

        Double totalRevenue =
                orderRepository.getTotalRevenue();

        return new DashboardResponseDTO(
                totalUsers,
                totalProducts,
                totalOrders,
                totalRevenue
        );
    }

    // ORDERS BY STATUS

    public List<Object[]> getOrdersByStatus() {

        return orderRepository
                .countOrdersByStatus();
    }

    // LOW STOCK PRODUCTS

    public List<Product> lowStock() {

        return productRepository
                .findByStockLessThan(10);
    }

    // TOP RATED PRODUCTS

    public List<Object[]> topRatedProducts() {

        return reviewRepository
                .topRatedProducts();
    }

    // MONTHLY REVENUE

    public List<Object[]> monthlyRevenue() {

        return orderRepository
                .monthlyRevenue();
    }
}