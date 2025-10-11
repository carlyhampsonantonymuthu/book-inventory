package com.example.bookinventory.controller;


import com.example.bookinventory.entity.Author;

import com.example.bookinventory.entity.Book;
import com.example.bookinventory.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;



/**
 * Endpoints:
 * POST   /api/author/post
 * GET    /api/author/{authorId}
 * GET    /api/author/firstname/{firstName}
 * GET    /api/author/lastname/{lastName}
 * PUT    /api/author/update/firstname/{authorId}
 * PUT    /api/author/update/lastname/{authorId}
 * GET    /api/author/books/{authorId}
 */


@RestController
@RequestMapping("/api/author")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    // Add new Author
    @PostMapping("/post")
    public ResponseEntity<Map<String, String>> addAuthor(@RequestBody Author author) {
        try {
            Author saved = authorService.addAuthor(author);
            Map<String, String> body = Map.of(
                    "code", "POSTSUCCESS",
                    "message", "Author added successfully",
                    "authorId", String.valueOf(saved.getAuthorId())
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

    // Get author by id
    @GetMapping("/{authorId}")
    public ResponseEntity<?> getAuthorById(@PathVariable Integer authorId) {
        return authorService.getAuthorById(authorId)
                .<ResponseEntity<?>>map(a -> ResponseEntity.ok(a))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("code","NOTFOUND","message","Author not found")));
    }

    // Get by firstName
    @GetMapping("/firstname/{firstName}")
    public ResponseEntity<?> getAuthorByFirstName(@PathVariable String firstName) {
        return authorService.getAuthorByFirstName(firstName)
                .<ResponseEntity<?>>map(a -> ResponseEntity.ok(a))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("code","NOTFOUND","message","Author not found")));
    }

    // Get by lastName
    @GetMapping("/lastname/{lastName}")
    public ResponseEntity<?> getAuthorByLastName(@PathVariable String lastName) {
        return authorService.getAuthorByLastName(lastName)
                .<ResponseEntity<?>>map(a -> ResponseEntity.ok(a))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("code","NOTFOUND","message","Author not found")));
    }

    // Update first name
    @PutMapping("/update/firstname/{authorId}")
    public ResponseEntity<?> updateFirstName(@PathVariable Integer authorId, @RequestBody Map<String,String> body) {
        String newFirst = body.get("firstName");
        if (newFirst == null) {
            return ResponseEntity.badRequest().body(Map.of("code","BADREQUEST","message","firstName is required"));
        }
        try {
            Author updated = authorService.updateFirstName(authorId, newFirst);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("code","NOTFOUND","message",ex.getMessage()));
        }
    }

    // Update last name
    @PutMapping("/update/lastname/{authorId}")
    public ResponseEntity<?> updateLastName(@PathVariable Integer authorId, @RequestBody Map<String,String> body) {
        String newLast = body.get("lastName");
        if (newLast == null) {
            return ResponseEntity.badRequest().body(Map.of("code","BADREQUEST","message","lastName is required"));
        }
        try {
            Author updated = authorService.updateLastName(authorId, newLast);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("code","NOTFOUND","message",ex.getMessage()));
        }
    }

    // Get all books of an author
    @GetMapping("/books/{authorId}")
    public ResponseEntity<?> getBooksByAuthor(@PathVariable Integer authorId) {
        try {
            List<Book> books = (List<Book>) authorService.getBooksByAuthor(authorId);
            return ResponseEntity.ok(books);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("code","NOTFOUND","message", ex.getMessage()));
        }
    }
}
