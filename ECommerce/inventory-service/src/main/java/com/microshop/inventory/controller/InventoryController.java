package com.microshop.inventory.controller;

import com.microshop.inventory.dto.InventoryRequest;
import com.microshop.inventory.dto.InventoryResponse;
import com.microshop.inventory.dto.StockMovementResponse;
import com.microshop.inventory.service.InventoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping
    public ResponseEntity<InventoryResponse> addStock(@Valid @RequestBody InventoryRequest request) {
        return ResponseEntity.ok(inventoryService.addStock(request));
    }

    @PatchMapping("/{productId}/decrease")
    public ResponseEntity<InventoryResponse> decreaseStock(
            @PathVariable Long productId,
            @RequestParam @Valid @Min(1) Integer qty) {
        return ResponseEntity.ok(inventoryService.decreaseStock(productId, qty));
    }

    @PatchMapping("/{productId}/reserve")
    public ResponseEntity<InventoryResponse> reserveStock(
            @PathVariable Long productId,
            @RequestParam @Valid @Min(1) Integer qty) {
        return ResponseEntity.ok(inventoryService.reserveStock(productId, qty));
    }

    @PatchMapping("/{productId}/release")
    public ResponseEntity<InventoryResponse> releaseStock(
            @PathVariable Long productId,
            @RequestParam @Valid @Min(1) Integer qty) {
        return ResponseEntity.ok(inventoryService.releaseStock(productId, qty));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<InventoryResponse> getStock(@PathVariable Long productId) {
        return ResponseEntity.ok(inventoryService.getStock(productId));
    }

    @GetMapping("/{productId}/movements")
    public ResponseEntity<List<StockMovementResponse>> getMovements(@PathVariable Long productId) {
        return ResponseEntity.ok(inventoryService.getStockMovements(productId));
    }
}
