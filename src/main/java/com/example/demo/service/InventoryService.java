package com.example.demo.service;

import com.example.demo.model.InventoryHistory;
import com.example.demo.model.Product;
import com.example.demo.repository.InventoryHistoryRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class InventoryService {

    private final ProductRepository productRepository;
    private final InventoryHistoryRepository historyRepository;

    public InventoryService(
            ProductRepository productRepository,
            InventoryHistoryRepository historyRepository) {

        this.productRepository = productRepository;
        this.historyRepository = historyRepository;
    }

    public Product addStock(
            Integer productId,
            Integer quantity) {

        Product product =
                productRepository.findById(productId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Product not found"));

        product.setStock(
                product.getStock() + quantity);

        InventoryHistory history =
                new InventoryHistory();

        history.setProduct(product);
        history.setQuantityChanged(quantity);
        history.setAction("RESTOCK");
        history.setCreatedAt(
                LocalDateTime.now());

        historyRepository.save(history);

        return productRepository.save(product);
    }

    public List<InventoryHistory>
    getHistory(Integer productId) {

        return historyRepository
                .findByProductId(productId);
    }
}