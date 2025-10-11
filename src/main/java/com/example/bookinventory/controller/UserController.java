package com.example.bookinventory.controller;


import com.example.bookinventory.entity.User;
import com.example.bookinventory.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

/**
 * Endpoints:
 * POST  /api/user/post
 * GET   /api/user/{userId}
 * PUT   /api/user/update/firstname/{userId}
 * PUT   /api/user/update/lastname/{userId}
 * PUT   /api/user/update/phonenumber/{userId}
 */


@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) { this.userService = userService; }

    // Add new user
    @PostMapping("/post")
    public ResponseEntity<Map<String, String>> addUser(@RequestBody User user) {
        try {
            User saved = userService.addUser(user);
            Map<String, String> body = Map.of(
                    "code", "POSTSUCCESS",
                    "message", "User added successfully",
                    "userId", String.valueOf(saved.getUserId())
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(body);
        } catch (RuntimeException ex) {
            Map<String, String> body = Map.of(
                    "code", "ADDFAILS",
                    "message", ex.getMessage()
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
        }
    }

    // Get user by ID
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Integer userId) {
        return userService.getUserById(userId)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("code", "NOTFOUND", "message", "User not found")));
    }

    // Update first name
    @PutMapping("/update/firstname/{userId}")
    public ResponseEntity<?> updateFirstName(@PathVariable Integer userId, @RequestBody Map<String, String> body) {
        String firstName = body.get("firstName");
        if (firstName == null)
            return ResponseEntity.badRequest().body(Map.of("code", "BADREQUEST", "message", "firstName is required"));

        try {
            User updated = userService.updateFirstName(userId, firstName);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("code", "NOTFOUND", "message", ex.getMessage()));
        }
    }

    // Update last name
    @PutMapping("/update/lastname/{userId}")
    public ResponseEntity<?> updateLastName(@PathVariable Integer userId, @RequestBody Map<String, String> body) {
        String lastName = body.get("lastName");
        if (lastName == null)
            return ResponseEntity.badRequest().body(Map.of("code", "BADREQUEST", "message", "lastName is required"));

        try {
            User updated = userService.updateLastName(userId, lastName);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("code", "NOTFOUND", "message", ex.getMessage()));
        }
    }

    // Update phone number
    @PutMapping("/update/phonenumber/{userId}")
    public ResponseEntity<?> updatePhoneNumber(@PathVariable Integer userId, @RequestBody Map<String, String> body) {
        String phone = body.get("phoneNumber");
        if (phone == null)
            return ResponseEntity.badRequest().body(Map.of("code", "BADREQUEST", "message", "phoneNumber is required"));

        try {
            User updated = userService.updatePhoneNumber(userId, phone);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("code", "NOTFOUND", "message", ex.getMessage()));
        }
    }
}
