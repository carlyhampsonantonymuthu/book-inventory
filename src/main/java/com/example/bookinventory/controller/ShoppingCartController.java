package com.example.bookinventory.controller;

import com.example.bookinventory.entity.Book;
import com.example.bookinventory.entity.ShoppingCart;
import com.example.bookinventory.entity.User;
import com.example.bookinventory.repository.BookRepository;
import com.example.bookinventory.repository.ShoppingCartRepository;
import com.example.bookinventory.repository.UserRepository;
import com.example.bookinventory.service.ShoppingCartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/shoppingcart")
@CrossOrigin(origins = "*")
public class ShoppingCartController {
	

    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    

    /**
     * ➕ Add new shopping cart entry
     */
    @PostMapping("/post")
    public ResponseEntity<?> addToCart(@RequestBody ShoppingCart cart) {
        Optional<User> userOpt = userRepository.findById(cart.getUser().getUserId());
        Optional<Book> bookOpt = bookRepository.findById(cart.getBook().getIsbn());

        if(userOpt.isEmpty() || bookOpt.isEmpty()){
            return ResponseEntity.badRequest()
                .body(Map.of("code", "ADDFAILS", "message", "User or Book not found"));
        }

        cart.setUser(userOpt.get());
        cart.setBook(bookOpt.get());

        shoppingCartRepository.save(cart);

        return ResponseEntity.ok(cart);
    }


    /**
     * 🔍 Get all cart items by userId
     */
    @GetMapping("/{userId}")
    public ResponseEntity<?> getShoppingCartByUser(@PathVariable Integer userId) {
        List<ShoppingCart> carts = shoppingCartService.getCartByUser(userId);
        if (carts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("code", "NOTFOUND", "message", "Shopping cart is empty for userId " + userId));
        }
        return ResponseEntity.ok(carts);
    }

    /**
     * ✏️ Update a book in the user's shopping cart
     */
    @PutMapping("/update/book/{userId}")
    public ResponseEntity<?> updateCartBook(@PathVariable Integer userId, @RequestBody Map<String, String> body) {
        String oldIsbn = body.get("oldIsbn");
        String newIsbn = body.get("newIsbn");

        if (oldIsbn == null || newIsbn == null) {
            return ResponseEntity.badRequest().body(Map.of(
                    "code", "BADREQUEST",
                    "message", "Both oldIsbn and newIsbn are required"
            ));
        }

        try {
            ShoppingCart updated = shoppingCartService.updateBookInCart(userId, oldIsbn, newIsbn);
            return ResponseEntity.ok(Map.of(
                    "code", "UPDATESUCCESS",
                    "message", "Book in shopping cart updated successfully",
                    "updatedCart", updated
            ));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "code", "NOTFOUND",
                    "message", ex.getMessage()
            ));
        }
    }
}
