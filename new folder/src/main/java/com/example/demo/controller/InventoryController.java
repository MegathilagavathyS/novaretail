package com.example.demo.controller;

import com.example.demo.model.InventoryHistory;
import com.example.demo.model.Product;
import com.example.demo.service.InventoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService service;

    public InventoryController(
            InventoryService service) {

        this.service = service;
    }

    @PostMapping("/restock")
    public Product restockProduct(

            @RequestParam Integer productId,

            @RequestParam Integer quantity) {

        return service.addStock(
                productId,
                quantity);
    }

    @GetMapping("/history/{productId}")
    public List<InventoryHistory> history(
            @PathVariable Integer productId) {

        return service.getHistory(productId);
    }
}