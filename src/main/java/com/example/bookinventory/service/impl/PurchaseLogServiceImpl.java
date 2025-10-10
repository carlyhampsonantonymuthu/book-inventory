package com.example.bookinventory.service.impl;

import com.example.bookinventory.entity.Inventory;
import com.example.bookinventory.entity.PurchaseLog;
import com.example.bookinventory.repository.InventoryRepository;
import com.example.bookinventory.repository.PurchaseLogRepository;
import com.example.bookinventory.service.PurchaseLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PurchaseLogServiceImpl implements PurchaseLogService {

    private final PurchaseLogRepository purchaseLogRepository;
    private final InventoryRepository inventoryRepository;

    public PurchaseLogServiceImpl(PurchaseLogRepository purchaseLogRepository, InventoryRepository inventoryRepository) {
        this.purchaseLogRepository = purchaseLogRepository;
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public PurchaseLog addPurchaseLog(PurchaseLog purchaseLog) {
        return purchaseLogRepository.save(purchaseLog);
    }

    @Override
    public List<PurchaseLog> getPurchaseLogsByUser(Integer userId) {
        return purchaseLogRepository.findByUser_UserId(userId);
    }

    @Override
    public PurchaseLog updateInventoryId(Integer purchaseLogId, Integer inventoryId) {
        PurchaseLog log = purchaseLogRepository.findById(purchaseLogId)
                .orElseThrow(() -> new RuntimeException("Purchase log not found with ID: " + purchaseLogId));

        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new RuntimeException("Inventory not found with ID: " + inventoryId));

        log.setInventory(inventory);
        return purchaseLogRepository.save(log);
    }
}
