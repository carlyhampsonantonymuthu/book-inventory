package com.example.bookinventory.controller;

import com.example.bookinventory.entity.PurchaseLog;
import com.example.bookinventory.service.PurchaseLogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/purchaselog")
@CrossOrigin(origins = "*")


public class PurchaseLogController {

    private final PurchaseLogService purchaseLogService;

    public PurchaseLogController(PurchaseLogService purchaseLogService) {
        this.purchaseLogService = purchaseLogService;
    }

    /**
     * ➕ Add new Purchase Log
     */
    @PostMapping("/post")
    public ResponseEntity<?> addPurchaseLog(@RequestBody PurchaseLog log) {
        try {
            PurchaseLog saved = purchaseLogService.addPurchaseLog(log);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "code", "POSTSUCCESS",
                    "message", "Purchase Log added successfully",
                    "purchaseLogId", saved.getPurchaseLogId()
            ));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(Map.of(
                    "code", "ADDFAILS",
                    "message", ex.getMessage()
            ));
        }
    }

    /**
     * 🔍 Get all purchase logs by userId
     */
    @GetMapping("/{userId}")
    public ResponseEntity<?> getPurchaseLogsByUser(@PathVariable Integer userId) {
        List<PurchaseLog> logs = purchaseLogService.getPurchaseLogsByUser(userId);

        if (logs.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "code", "NOTFOUND",
                    "message", "No purchase logs found for userId " + userId
            ));
        }

        return ResponseEntity.ok(logs);
    }

    /**
     * ✏️ Update inventoryId for a purchase log
     */
    @PutMapping("/update/inventoryid/{purchaseLogId}")
    public ResponseEntity<?> updateInventoryId(@PathVariable Integer purchaseLogId,
                                               @RequestBody Map<String, Integer> body) {
        Integer inventoryId = body.get("inventoryId");
        if (inventoryId == null) {
            return ResponseEntity.badRequest().body(Map.of(
                    "code", "BADREQUEST",
                    "message", "inventoryId is required"
            ));
        }

        try {
            PurchaseLog updated = purchaseLogService.updateInventoryId(purchaseLogId, inventoryId);
            return ResponseEntity.ok(Map.of(
                    "code", "UPDATESUCCESS",
                    "message", "Inventory ID updated successfully",
                    "updatedPurchaseLog", updated
            ));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "code", "NOTFOUND",
                    "message", ex.getMessage()
            ));
        }
    }
}
