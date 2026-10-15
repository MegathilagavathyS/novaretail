package com.example.demo.repository;

import com.example.demo.model.InventoryHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryHistoryRepository
        extends JpaRepository<InventoryHistory,Integer> {

    List<InventoryHistory>
    findByProductId(Integer productId);
}