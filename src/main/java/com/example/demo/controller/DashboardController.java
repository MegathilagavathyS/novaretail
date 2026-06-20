package com.example.demo.controller;

import com.example.demo.dto.DashboardResponseDTO;
import com.example.demo.model.Product;
import com.example.demo.service.DashboardService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardService service;

    public DashboardController(
            DashboardService service) {

        this.service = service;
    }

    // MAIN DASHBOARD

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public DashboardResponseDTO dashboard() {

        return service.getDashboard();
    }

    // ORDERS BY STATUS

    @PreAuthorize("hasRole(" +
            "'ADMIN')")
    @GetMapping("/orders/status")
    public List<Object[]> ordersByStatus() {

        return service.getOrdersByStatus();
    }

    // LOW STOCK PRODUCTS

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/low-stock")
    public List<Product> lowStock() {

        return service.lowStock();
    }

    // TOP RATED PRODUCTS

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/top-rated")
    public List<Object[]> topRatedProducts() {

        return service.topRatedProducts();
    }

    // MONTHLY REVENUE

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/monthly-revenue")
    public List<Object[]> monthlyRevenue() {

        return service.monthlyRevenue();
    }
}