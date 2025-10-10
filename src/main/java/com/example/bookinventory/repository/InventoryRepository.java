package com.example.bookinventory.repository;


import com.example.bookinventory.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {
    List<Inventory> findByBook_Isbn(String isbn);
}

