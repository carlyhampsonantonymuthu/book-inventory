package com.example.bookinventory.service;

import com.example.bookinventory.entity.PurchaseLog;
import java.util.List;

public interface PurchaseLogService {
    PurchaseLog addPurchaseLog(PurchaseLog purchaseLog);
    List<PurchaseLog> getPurchaseLogsByUser(Integer userId);
    PurchaseLog updateInventoryId(Integer purchaseLogId, Integer inventoryId);
}
