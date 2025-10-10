package com.example.bookinventory.service.impl;

import com.example.bookinventory.entity.ShoppingCart;
import com.example.bookinventory.entity.ShoppingCartId;
import com.example.bookinventory.repository.ShoppingCartRepository;
import com.example.bookinventory.service.ShoppingCartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;

    public ShoppingCartServiceImpl(ShoppingCartRepository repo) {
        this.shoppingCartRepository = repo;
    }

    @Override
    public ShoppingCart addToCart(ShoppingCart cart) {
        return shoppingCartRepository.save(cart);
    }

    @Override
    public List<ShoppingCart> getCartByUser(Integer userId) {
        return shoppingCartRepository.findByUser_UserId(userId);
    }

    @Override
    public ShoppingCart updateBookInCart(Integer userId, String oldIsbn, String newIsbn) {
        ShoppingCartId oldId = new ShoppingCartId(userId, oldIsbn);
        ShoppingCart existingCart = shoppingCartRepository.findById(oldId)
                .orElseThrow(() -> new RuntimeException("Cart item not found for userId " + userId + " and ISBN " + oldIsbn));

        // Remove old item
        shoppingCartRepository.delete(existingCart);

        // Add new item
        ShoppingCartId newId = new ShoppingCartId(userId, newIsbn);
        existingCart.setId(newId);
        return shoppingCartRepository.save(existingCart);
    }
}
