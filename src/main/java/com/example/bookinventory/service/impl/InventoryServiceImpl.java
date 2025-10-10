package com.example.bookinventory.service.impl;

import com.example.bookinventory.entity.Inventory;
import com.example.bookinventory.repository.InventoryRepository;
import com.example.bookinventory.service.InventoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public Inventory addInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    @Override
    public Optional<Inventory> getInventoryById(Integer id) {
        return inventoryRepository.findById(id);
    }

    @Override
    public List<Inventory> getInventoriesByBook(String isbn) {
        return inventoryRepository.findByBook_Isbn(isbn);
    }

    @Override
    public Inventory updatePurchased(Integer id, Integer purchased) {
        Inventory inv = inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory not found with ID: " + id));

        inv.setPurchased(purchased); // ✅ Make sure `purchased` field in entity is Integer
        return inventoryRepository.save(inv);
    }
}
