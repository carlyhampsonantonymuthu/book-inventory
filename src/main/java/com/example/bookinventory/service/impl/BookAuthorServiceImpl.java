package com.example.bookinventory.service.impl;

import com.example.bookinventory.entity.*;
import com.example.bookinventory.repository.*;
import com.example.bookinventory.service.BookAuthorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class BookAuthorServiceImpl implements BookAuthorService {

    private final BookAuthorRepository bookAuthorRepository;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookAuthorServiceImpl(BookAuthorRepository bookAuthorRepository,
                                 BookRepository bookRepository,
                                 AuthorRepository authorRepository) {
        this.bookAuthorRepository = bookAuthorRepository;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public BookAuthor addBookAuthor(String isbn, Integer authorId, boolean primaryAuthor) {
        Book book = bookRepository.findById(isbn)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("Author not found"));

        BookAuthorId id = new BookAuthorId(isbn, authorId);
        BookAuthor ba = new BookAuthor();
        ba.setId(id);
        ba.setBook(book);
        ba.setAuthor(author);
        ba.setPrimaryAuthor(primaryAuthor);

        return bookAuthorRepository.save(ba);
    }

    @Override
    public List<BookAuthor> getAuthorsByBook(String isbn) {
        return bookAuthorRepository.findByBookIsbn(isbn);
    }

    @Override
    public List<BookAuthor> getBooksByAuthor(Integer authorId) {
        return bookAuthorRepository.findByAuthorAuthorId(authorId);
    }
}


