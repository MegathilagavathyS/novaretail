package com.example.demo.repository;

import com.example.demo.model.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository
        extends JpaRepository<Order,Integer> {

    List<Order> findByUserId(
            Integer userId);

    @Query("""
    SELECT COALESCE(
    SUM(o.totalAmount),0)
    FROM Order o
    WHERE o.status =
    com.example.demo.model.OrderStatus.DELIVERED
    """)
    Double getTotalRevenue();

    @Query("""
    SELECT o.status,
    COUNT(o)
    FROM Order o
    GROUP BY o.status
    """)
    List<Object[]> countOrdersByStatus();

    @Query("""
    SELECT MONTH(o.orderDate),
    SUM(o.totalAmount)
    FROM Order o
    GROUP BY MONTH(o.orderDate)
    ORDER BY MONTH(o.orderDate)
    """)
    List<Object[]> monthlyRevenue();
}