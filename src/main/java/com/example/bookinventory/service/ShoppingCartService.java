package com.example.bookinventory.service;

import com.example.bookinventory.entity.ShoppingCart;
import java.util.List;

public interface ShoppingCartService {
    ShoppingCart addToCart(ShoppingCart cart);
    List<ShoppingCart> getCartByUser(Integer userId);
    ShoppingCart updateBookInCart(Integer userId, String oldIsbn, String newIsbn);
}
