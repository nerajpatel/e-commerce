package com.microshop.inventory.service;

import com.microshop.inventory.dto.InventoryRequest;
import com.microshop.inventory.dto.InventoryResponse;
import com.microshop.inventory.dto.StockMovementResponse;
import com.microshop.inventory.model.Inventory;
import com.microshop.inventory.model.StockMovement;
import com.microshop.inventory.repository.InventoryRepository;
import com.microshop.inventory.repository.StockMovementRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final StockMovementRepository stockMovementRepository;

    public InventoryService(InventoryRepository inventoryRepository, StockMovementRepository stockMovementRepository) {
        this.inventoryRepository = inventoryRepository;
        this.stockMovementRepository = stockMovementRepository;
    }

    public InventoryResponse addStock(InventoryRequest request) {
        Inventory inventory = inventoryRepository.findById(request.getProductId())
                .orElse(Inventory.builder()
                        .productId(request.getProductId())
                        .availableQuantity(0)
                        .reservedQuantity(0)
                        .build());

        inventory.setAvailableQuantity(inventory.getAvailableQuantity() + request.getQuantity());
        inventoryRepository.save(inventory);

        logStockMovement(request.getProductId(), request.getQuantity(), "Stock Added");

        return toResponse(inventory);
    }

    @Transactional
    public InventoryResponse decreaseStock(Long productId, Integer qty) {
        Inventory inventory = getInventory(productId);

        if (inventory.getAvailableQuantity() < qty) {
            throw new IllegalArgumentException("Not enough stock for product " + productId);
        }

        inventory.setAvailableQuantity(inventory.getAvailableQuantity() - qty);
        inventoryRepository.save(inventory);

        logStockMovement(productId, -qty, "Stock Decreased");

        return toResponse(inventory);
    }

    @Transactional
    public InventoryResponse reserveStock(Long productId, Integer qty) {
        Inventory inventory = getInventory(productId);

        if (inventory.getAvailableQuantity() < qty) {
            throw new IllegalArgumentException("Not enough stock to reserve for product " + productId);
        }

        inventory.setAvailableQuantity(inventory.getAvailableQuantity() - qty);
        inventory.setReservedQuantity(inventory.getReservedQuantity() + qty);
        inventoryRepository.save(inventory);

        logStockMovement(productId, -qty, "Stock Reserved");

        return toResponse(inventory);
    }

    @Transactional
    public InventoryResponse releaseStock(Long productId, Integer qty) {
        Inventory inventory = getInventory(productId);

        if (inventory.getReservedQuantity() < qty) {
            throw new IllegalArgumentException("Not enough reserved stock to release");
        }

        inventory.setReservedQuantity(inventory.getReservedQuantity() - qty);
        inventory.setAvailableQuantity(inventory.getAvailableQuantity() + qty);
        inventoryRepository.save(inventory);

        logStockMovement(productId, qty, "Stock Released");

        return toResponse(inventory);
    }

    public InventoryResponse getStock(Long productId) {
        Inventory inventory = getInventory(productId);
        return toResponse(inventory);
    }

    public List<StockMovementResponse> getStockMovements(Long productId) {
        return stockMovementRepository.findByProductId(productId).stream()
                .map(this::toMovementResponse)
                .collect(Collectors.toList());
    }

    private void logStockMovement(Long productId, Integer qty, String reason) {
        StockMovement movement = StockMovement.builder()
                .productId(productId)
                .changeQuantity(qty)
                .reason(reason)
                .createdAt(LocalDateTime.now())
                .build();
        stockMovementRepository.save(movement);
    }

    private Inventory getInventory(Long productId) {
        return inventoryRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("No inventory found for product " + productId));
    }

    private InventoryResponse toResponse(Inventory inv) {
        return InventoryResponse.builder()
                .productId(inv.getProductId())
                .availableQuantity(inv.getAvailableQuantity())
                .reservedQuantity(inv.getReservedQuantity())
                .build();
    }

    private StockMovementResponse toMovementResponse(StockMovement movement) {
        return StockMovementResponse.builder()
                .id(movement.getId())
                .productId(movement.getProductId())
                .changeQuantity(movement.getChangeQuantity())
                .reason(movement.getReason())
                .createdAt(movement.getCreatedAt())
                .build();
    }
}
