package com.example.bookinventory.controller;


import com.example.bookinventory.entity.Book;
import com.example.bookinventory.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Endpoints:
 * POST   /api/book/post
 * GET    /api/books
 * GET    /api/book/{isbn}
 * GET    /api/book/title/{title}
 * GET    /api/book/category/{category}
 * GET    /api/book/publisher/{publisherId}
 * PUT    /api/book/update/title/{isbn}
 * PUT    /api/book/update/description/{isbn}
 * PUT    /api/book/update/category/{isbn}
 * PUT    /api/book/update/edition/{isbn}
 * PUT    /api/book/update/publisher/{isbn}
 */
@RestController
@RequestMapping("/api")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // Add new Book
    @PostMapping("/book/post")
    public ResponseEntity<Map<String, String>> addBook(@RequestBody Book book) {
        try {
            Book saved = bookService.addBook(book);
            Map<String, String> body = Map.of(
                    "code", "POSTSUCCESS",
                    "message", "Book added successfully",
                    "isbn", saved.getIsbn()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(body);
        } catch (RuntimeException ex) {
            Map<String,String> body = Map.of(
                    "code","ADDFAILS",
                    "message", ex.getMessage()
            );
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
        }
    }

    // Get all books
    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    // Get book by ISBN
    @GetMapping("/book/{isbn}")
    public ResponseEntity<?> getBookByIsbn(@PathVariable String isbn) {
        return bookService.getBookByIsbn(isbn)
                .<ResponseEntity<?>>map(b -> ResponseEntity.ok(b))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("code","NOTFOUND","message","Book not found")));
    }

    // Get book by title
    @GetMapping("/book/title/{title}")
    public ResponseEntity<?> getBookByTitle(@PathVariable String title) {
        return bookService.getBookByTitle(title)
                .<ResponseEntity<?>>map(b -> ResponseEntity.ok(b))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("code","NOTFOUND","message","Book not found")));
    }

    // Get books by category name
    @GetMapping("/book/category/{category}")
    public ResponseEntity<List<Book>> getBooksByCategory(@PathVariable String category) {
        List<Book> list = bookService.getBooksByCategory(category);
        return ResponseEntity.ok(list);
    }

    // Get books by publisher id
    @GetMapping("/book/publisher/{publisherId}")
    public ResponseEntity<List<Book>> getBooksByPublisher(@PathVariable Integer publisherId) {
        List<Book> list = bookService.getBooksByPublisher(publisherId);
        return ResponseEntity.ok(list);
    }

    // Update title
    @PutMapping("/book/update/title/{isbn}")
    public ResponseEntity<?> updateTitle(@PathVariable String isbn, @RequestBody Map<String,String> body) {
        String title = body.get("title");
        if (title == null) return ResponseEntity.badRequest().body(Map.of("code","BADREQUEST","message","title is required"));
        try {
            Book updated = bookService.updateBookTitle(isbn, title);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("code","NOTFOUND","message",ex.getMessage()));
        }
    }

    // Update description
    @PutMapping("/book/update/description/{isbn}")
    public ResponseEntity<?> updateDescription(@PathVariable String isbn, @RequestBody Map<String,String> body) {
        String desc = body.get("description");
        if (desc == null) return ResponseEntity.badRequest().body(Map.of("code","BADREQUEST","message","description is required"));
        try {
            Book updated = bookService.updateBookDescription(isbn, desc);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("code","NOTFOUND","message",ex.getMessage()));
        }
    }

    // Update category (by category name)
    @PutMapping("/book/update/category/{isbn}")
    public ResponseEntity<?> updateCategory(@PathVariable String isbn, @RequestBody Map<String,String> body) {
        String category = body.get("category");
        if (category == null) return ResponseEntity.badRequest().body(Map.of("code","BADREQUEST","message","category is required"));
        try {
            Book updated = bookService.updateBookCategory(isbn, category);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("code","NOTFOUND","message",ex.getMessage()));
        }
    }

    // Update edition
    @PutMapping("/book/update/edition/{isbn}")
    public ResponseEntity<?> updateEdition(@PathVariable String isbn, @RequestBody Map<String,String> body) {
        String edition = body.get("edition");
        if (edition == null) return ResponseEntity.badRequest().body(Map.of("code","BADREQUEST","message","edition is required"));
        try {
            Book updated = bookService.updateBookEdition(isbn, edition);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("code","NOTFOUND","message",ex.getMessage()));
        }
    }

    // Update publisher
    @PutMapping("/book/update/publisher/{isbn}")
    public ResponseEntity<?> updatePublisher(@PathVariable String isbn, @RequestBody Map<String,Integer> body) {
        Integer publisherId = body.get("publisherId");
        if (publisherId == null) return ResponseEntity.badRequest().body(Map.of("code","BADREQUEST","message","publisherId is required"));
        try {
            Book updated = bookService.updateBookPublisher(isbn, publisherId);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("code","NOTFOUND","message",ex.getMessage()));
        }
    }
}
