package com.example.bookinventory.controller;

import com.example.bookinventory.entity.BookAuthor;
import com.example.bookinventory.service.BookAuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/book-author")
public class BookAuthorController {

    private final BookAuthorService service;

    public BookAuthorController(BookAuthorService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addBookAuthor(@RequestBody Map<String, Object> body) {
        String isbn = (String) body.get("isbn");
        Integer authorId = (Integer) body.get("authorId");
        boolean primaryAuthor = (boolean) body.getOrDefault("primaryAuthor", false);

        BookAuthor saved = service.addBookAuthor(isbn, authorId, primaryAuthor);
        return ResponseEntity.ok(Map.of(
                "code", "POSTSUCCESS",
                "message", "Book-Author link created successfully",
                "bookIsbn", saved.getBook().getIsbn(),
                "authorId", saved.getAuthor().getAuthorId()
        ));
    }

    @GetMapping("/book/{isbn}")
    public ResponseEntity<List<BookAuthor>> getAuthorsByBook(@PathVariable String isbn) {
        return ResponseEntity.ok(service.getAuthorsByBook(isbn));
    }

    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<BookAuthor>> getBooksByAuthor(@PathVariable Integer authorId) {
        return ResponseEntity.ok(service.getBooksByAuthor(authorId));
    }
}
