 
package com.example.bookinventory.service.impl;

import com.example.bookinventory.entity.Book;
import com.example.bookinventory.entity.Category;
import com.example.bookinventory.entity.Publisher;
import com.example.bookinventory.repository.BookRepository;
import com.example.bookinventory.repository.CategoryRepository;
import com.example.bookinventory.repository.PublisherRepository;
import com.example.bookinventory.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final PublisherRepository publisherRepository;

    public BookServiceImpl(BookRepository bookRepository,
                           CategoryRepository categoryRepository,
                           PublisherRepository publisherRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public Book addBook(Book book) {

        // ✅ Fetch existing category
        if (book.getCategory() != null && book.getCategory().getCatId() != null) {
            Category category = categoryRepository.findById(book.getCategory().getCatId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            book.setCategory(category);
        }

        // ✅ Fetch existing publisher
        if (book.getPublisher() != null && book.getPublisher().getPublisherId() != null) {
            Publisher publisher = publisherRepository.findById(book.getPublisher().getPublisherId())
                    .orElseThrow(() -> new RuntimeException("Publisher not found"));
            book.setPublisher(publisher);
        }

        // ❌ No need to handle authors here (handled by BookAuthorService)
        return bookRepository.save(book);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> getBookByIsbn(String isbn) {
        return bookRepository.findById(isbn);
    }

    @Override
    public Optional<Book> getBookByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    @Override
    public List<Book> getBooksByCategory(String categoryName) {
        return categoryRepository.findAll().stream()
                .filter(cat -> cat.getCatDescription().equalsIgnoreCase(categoryName))
                .findFirst()
                .map(bookRepository::findByCategory)
                .orElse(Collections.emptyList());
    }

    @Override
    public List<Book> getBooksByPublisher(Integer publisherId) {
        Publisher pub = publisherRepository.findById(publisherId)
                .orElseThrow(() -> new RuntimeException("Publisher not found"));
        return bookRepository.findByPublisher(pub);
    }

    @Override
    public Book updateBookTitle(String isbn, String title) {
        Book book = bookRepository.findById(isbn)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        book.setTitle(title);
        return bookRepository.save(book);
    }

    @Override
    public Book updateBookDescription(String isbn, String desc) {
        Book book = bookRepository.findById(isbn)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        book.setDescription(desc);
        return bookRepository.save(book);
    }

    @Override
    public Book updateBookCategory(String isbn, String categoryName) {
        Book book = bookRepository.findById(isbn)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        Category category = categoryRepository.findAll().stream()
                .filter(c -> c.getCatDescription().equalsIgnoreCase(categoryName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Category not found"));
        book.setCategory(category);
        return bookRepository.save(book);
    }

    @Override
    public Book updateBookEdition(String isbn, String edition) {
        Book book = bookRepository.findById(isbn)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        book.setEdition(edition);
        return bookRepository.save(book);
    }

    @Override
    public Book updateBookPublisher(String isbn, Integer publisherId) {
        Book book = bookRepository.findById(isbn)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        Publisher publisher = publisherRepository.findById(publisherId)
                .orElseThrow(() -> new RuntimeException("Publisher not found"));
        book.setPublisher(publisher);
        return bookRepository.save(book);
    }
}

