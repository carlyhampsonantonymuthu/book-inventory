package com.example.bookinventory.repository;

import com.example.bookinventory.entity.ShoppingCart;
import com.example.bookinventory.entity.ShoppingCartId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, ShoppingCartId> {
    List<ShoppingCart> findByUser_UserId(Integer userId);
}