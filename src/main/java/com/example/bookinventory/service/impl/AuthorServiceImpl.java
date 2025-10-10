package com.example.bookinventory.service.impl;

import com.example.bookinventory.entity.Author;
import com.example.bookinventory.entity.Book;
import com.example.bookinventory.entity.BookAuthor;
import com.example.bookinventory.repository.AuthorRepository;
import com.example.bookinventory.repository.BookRepository;
import com.example.bookinventory.service.AuthorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository,
                             BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public Author addAuthor(Author author) {
        if (authorRepository.existsByFirstNameAndLastName(author.getFirstName(), author.getLastName())) {
            throw new RuntimeException("Author already exists");
        }
        return authorRepository.save(author);
    }

    @Override
    public Optional<Author> getAuthorById(Integer authorId) {
        return authorRepository.findById(authorId);
    }

    @Override
    public Optional<Author> getAuthorByFirstName(String firstName) {
        return authorRepository.findByFirstName(firstName);
    }

    @Override
    public Optional<Author> getAuthorByLastName(String lastName) {
        return authorRepository.findByLastName(lastName);
    }

    @Override
    public Author updateFirstName(Integer authorId, String newFirstName) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("Author not found"));
        author.setFirstName(newFirstName);
        return authorRepository.save(author);
    }

    @Override
    public Author updateLastName(Integer authorId, String newLastName) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("Author not found"));
        author.setLastName(newLastName);
        return authorRepository.save(author);
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public List<Book> getBooksByAuthor(Integer authorId) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("Author not found"));

        // ✅ Extract books from join entity
        List<Book> books = new ArrayList<>();
        for (BookAuthor ba : author.getBookAuthors()) {
            books.add(ba.getBook());
        }
        return books;
    }
}
