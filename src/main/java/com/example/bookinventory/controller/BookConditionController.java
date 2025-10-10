package com.example.bookinventory.controller;


import com.example.bookinventory.entity.BookCondition;
import com.example.bookinventory.service.BookConditionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

/**
 * Endpoints:
 * POST /api/bookcondition/post
 * GET  /api/bookcondition/update/price/{ranks}
 * PUT  /api/bookcondition/update/description/{ranks}
 * PUT  /api/bookcondition/update/fulldescription/{ranks}
 * PUT  /api/bookcondition/{ranks}
 */
@RestController
@RequestMapping("/api/bookcondition")
public class BookConditionController {

    private final BookConditionService bookConditionService;
    public BookConditionController(BookConditionService bookConditionService) {
        this.bookConditionService = bookConditionService;
    }

    // Add new BookCondition
    @PostMapping("/post")
    public ResponseEntity<Map<String, String>> addBookCondition(@RequestBody BookCondition condition) {
        try {
            BookCondition saved = bookConditionService.addBookCondition(condition);
            Map<String, String> body = Map.of(
                    "code", "POSTSUCCESS",
                    "message", "Book Condition added successfully",
                    "ranks", String.valueOf(saved.getRanks())
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

    // Get condition by rank
    @GetMapping("/update/price/{ranks}")
    public ResponseEntity<?> getConditionByRanks(@PathVariable Integer ranks) {
        return bookConditionService.getBookConditionByRank(ranks)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("code", "NOTFOUND", "message", "Book condition not found")));
    }

    // Update description
    @PutMapping("/update/description/{ranks}")
    public ResponseEntity<?> updateDescription(@PathVariable Integer ranks, @RequestBody Map<String, String> body) {
        String description = body.get("description");
        if (description == null)
            return ResponseEntity.badRequest().body(Map.of("code", "BADREQUEST", "message", "description is required"));

        try {
            BookCondition updated = bookConditionService.updateDescription(ranks, description);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("code", "NOTFOUND", "message", ex.getMessage()));
        }
    }

    // Update full description
    @PutMapping("/update/fulldescription/{ranks}")
    public ResponseEntity<?> updateFullDescription(@PathVariable Integer ranks, @RequestBody Map<String, String> body) {
        String fullDesc = body.get("fullDescription");
        if (fullDesc == null)
            return ResponseEntity.badRequest().body(Map.of("code", "BADREQUEST", "message", "fullDescription is required"));

        try {
            BookCondition updated = bookConditionService.updateFullDescription(ranks, fullDesc);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("code", "NOTFOUND", "message", ex.getMessage()));
        }
    }

    // Update price
    @PutMapping("/{ranks}")
    public ResponseEntity<?> updatePrice(@PathVariable Integer ranks, @RequestBody Map<String, Double> body) {
        Double price = body.get("price");
        if (price == null)
            return ResponseEntity.badRequest().body(Map.of("code", "BADREQUEST", "message", "price is required"));

        try {
            BookCondition updated = bookConditionService.updatePrice(ranks, price);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("code", "NOTFOUND", "message", ex.getMessage()));
        }
    }
}
