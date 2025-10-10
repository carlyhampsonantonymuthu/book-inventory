package com.example.bookinventory.service;

import com.example.bookinventory.entity.Inventory;
import java.util.List;
import java.util.Optional;

public interface InventoryService {
    Inventory addInventory(Inventory inventory);
    Optional<Inventory> getInventoryById(Integer id);
    List<Inventory> getInventoriesByBook(String isbn);
    Inventory updatePurchased(Integer id, Integer purchased); // ✅ changed boolean → Integer
}
