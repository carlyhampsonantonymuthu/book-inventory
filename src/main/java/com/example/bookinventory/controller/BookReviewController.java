package com.example.bookinventory.controller;

import com.example.bookinventory.entity.BookReview;
import com.example.bookinventory.service.BookReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookreview")
@CrossOrigin(origins = "*")
public class BookReviewController {

    private final BookReviewService bookReviewService;

    public BookReviewController(BookReviewService bookReviewService) {
        this.bookReviewService = bookReviewService;
    }

    // ➕ Add a new book review
    @PostMapping("/post")
    public ResponseEntity<?> addBookReview(@RequestBody BookReview review) {
        try {
            BookReview saved = bookReviewService.addBookReview(review);
            Map<String, Object> body = Map.of(
                    "code", "POSTSUCCESS",
                    "message", "Book Reviewer added successfully",
                    "reviewId", saved.getId(),
                    "isbn", saved.getBook().getIsbn()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(body);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest()
                    .body(Map.of("code", "BADREQUEST", "message", ex.getMessage()));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("code", "ADDFAILS", "message", ex.getMessage()));
        }
    }

    // 🔍 Get all book reviews by ISBN
    @GetMapping("/{isbn}")
    public ResponseEntity<?> getBookReviewsByIsbn(@PathVariable String isbn) {
        try {
            List<BookReview> reviews = bookReviewService.getBookReviewsByIsbn(isbn);
            return ResponseEntity.ok(reviews);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("code", "NOTFOUND", "message", ex.getMessage()));
        }
    }

    // ✏️ Update rating
    @PutMapping("/update/rating/{isbn}/{reviewerId}")
    public ResponseEntity<?> updateRating(
            @PathVariable String isbn,
            @PathVariable Integer reviewerId,
            @RequestBody Map<String, Object> body) {

        Object ratingValue = body.get("rating");
        if (ratingValue == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("code", "BADREQUEST", "message", "Rating field is required"));
        }

        try {
            Double rating = Double.parseDouble(ratingValue.toString());
            BookReview updated = bookReviewService.updateRating(isbn, reviewerId, rating);
            return ResponseEntity.ok(Map.of(
                    "code", "UPDATESUCCESS",
                    "message", "Rating updated successfully",
                    "updatedReview", updated
            ));
        } catch (NumberFormatException ex) {
            return ResponseEntity.badRequest()
                    .body(Map.of("code", "BADREQUEST", "message", "Invalid rating format"));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("code", "NOTFOUND", "message", ex.getMessage()));
        }
    }

    // 💬 Update comments
    @PutMapping("/update/comments/{isbn}/{reviewerId}")
    public ResponseEntity<?> updateComments(
            @PathVariable String isbn,
            @PathVariable Integer reviewerId,
            @RequestBody Map<String, String> body) {

        String comments = body.get("comments");
        if (comments == null || comments.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("code", "BADREQUEST", "message", "Comments field is required"));
        }

        try {
            BookReview updated = bookReviewService.updateComments(isbn, reviewerId, comments);
            return ResponseEntity.ok(Map.of(
                    "code", "UPDATESUCCESS",
                    "message", "Comments updated successfully",
                    "updatedReview", updated
            ));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("code", "NOTFOUND", "message", ex.getMessage()));
        }
    }
}
