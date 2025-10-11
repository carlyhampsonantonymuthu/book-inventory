package com.example.bookinventory.controller;


import com.example.bookinventory.entity.Reviewer;
import com.example.bookinventory.service.ReviewerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

/**
 * Endpoints:
 * POST /api/reviewer/post
 * GET  /api/reviewer/employedby/{reviewerId}
 * PUT  /api/reviewer/name/{reviewerId}
 * PUT  /api/reviewer/{reviewerId}
 */


@RestController
@RequestMapping("/api/reviewer")
public class ReviewerController {

    private final ReviewerService reviewerService;
    public ReviewerController(ReviewerService reviewerService) { this.reviewerService = reviewerService; }

    // Add new reviewer
    @PostMapping("/post")
    public ResponseEntity<Map<String, String>> addReviewer(@RequestBody Reviewer reviewer) {
        try {
            Reviewer saved = reviewerService.addReviewer(reviewer);
            Map<String, String> body = Map.of(
                    "code", "POSTSUCCESS",
                    "message", "Reviewer added successfully",
                    "reviewerId", String.valueOf(saved.getReviewerId())
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

    // Get reviewer by ID (employedBy)
    @GetMapping("/employedby/{reviewerId}")
    public ResponseEntity<?> getReviewerById(@PathVariable Integer reviewerId) {
        return reviewerService.getReviewerById(reviewerId)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("code", "NOTFOUND", "message", "Reviewer not found")));
    }

    // Update reviewer first name
    @PutMapping("/name/{reviewerId}")
    public ResponseEntity<?> updateReviewerName(@PathVariable Integer reviewerId, @RequestBody Map<String, String> body) {
        String firstName = body.get("firstName");
        if (firstName == null)
            return ResponseEntity.badRequest().body(Map.of("code", "BADREQUEST", "message", "firstName is required"));

        try {
            Reviewer updated = reviewerService.updateName(reviewerId, firstName);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("code", "NOTFOUND", "message", ex.getMessage()));
        }
    }

    // Update employedBy field
    @PutMapping("/{reviewerId}")
    public ResponseEntity<?> updateEmployedBy(@PathVariable Integer reviewerId, @RequestBody Map<String, String> body) {
        String employedBy = body.get("employedBy");
        if (employedBy == null)
            return ResponseEntity.badRequest().body(Map.of("code", "BADREQUEST", "message", "employedBy is required"));

        try {
            Reviewer updated = reviewerService.updateEmployedBy(reviewerId, employedBy);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("code", "NOTFOUND", "message", ex.getMessage()));
        }
    }
}
