package com.example.bookinventory.controller;


import com.example.bookinventory.entity.Inventory;
import com.example.bookinventory.service.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

/**
 * Endpoints:
 * POST /api/inventory/post
 * GET  /api/inventory/{inventoryId}
 * PUT  /api/inventory/{inventoryId}
 */


@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    // Add new Inventory
    @PostMapping("/post")
    public ResponseEntity<Map<String, String>> addInventory(@RequestBody Inventory inventory) {
        try {
            Inventory saved = inventoryService.addInventory(inventory);
            Map<String, String> body = Map.of(
                    "code", "POSTSUCCESS",
                    "message", "Inventory added successfully",
                    "inventoryId", String.valueOf(saved.getInventoryId())
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(body);
        } catch (RuntimeException ex) {
            Map<String, String> body = Map.of(
                    "code", "ADDFAILS",
                    "message", ex.getMessage()
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
        }
    }

    // Get Inventory by ID
    @GetMapping("/{inventoryId}")
    public ResponseEntity<?> getInventoryById(@PathVariable Integer inventoryId) {
        return inventoryService.getInventoryById(inventoryId)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("code", "NOTFOUND", "message", "Inventory not found")));
    }

    // Update purchased count
    @PutMapping("/{inventoryId}")
    public ResponseEntity<?> updatePurchased(@PathVariable Integer inventoryId, @RequestBody Map<String, Integer> body) {
        Integer purchased = body.get("purchased");
        if (purchased == null)
            return ResponseEntity.badRequest().body(Map.of("code", "BADREQUEST", "message", "purchased is required"));

        try {
            Inventory updated = inventoryService.updatePurchased(inventoryId, purchased);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("code", "NOTFOUND", "message", ex.getMessage()));
        }
    }
}
