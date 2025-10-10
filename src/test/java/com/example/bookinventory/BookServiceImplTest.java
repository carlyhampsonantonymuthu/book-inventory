package com.example.bookinventory;


import com.example.bookinventory.entity.Book;
import com.example.bookinventory.entity.Category;
import com.example.bookinventory.entity.Publisher;
import com.example.bookinventory.repository.BookRepository;
import com.example.bookinventory.repository.CategoryRepository;
import com.example.bookinventory.repository.PublisherRepository;
import com.example.bookinventory.service.impl.BookServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceImplTest {

    private BookRepository bookRepository;
    private CategoryRepository categoryRepository;
    private PublisherRepository publisherRepository;
    private BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        bookRepository = mock(BookRepository.class);
        categoryRepository = mock(CategoryRepository.class);
        publisherRepository = mock(PublisherRepository.class);
        bookService = new BookServiceImpl(bookRepository, categoryRepository, publisherRepository);
    }

    @Test
    void testAddBookWithValidCategoryAndPublisher() {
        Category category = new Category();
        category.setCatId(1);
        Publisher publisher = new Publisher();
        publisher.setPublisherId(2);

        Book book = new Book();
        book.setCategory(category);
        book.setPublisher(publisher);

        when(categoryRepository.findById(1)).thenReturn(Optional.of(category));
        when(publisherRepository.findById(2)).thenReturn(Optional.of(publisher));
        when(bookRepository.save(book)).thenReturn(book);

        Book result = bookService.addBook(book);

        assertNotNull(result);
        verify(bookRepository).save(book);
    }

    @Test
    void testAddBookThrowsIfCategoryNotFound() {
        Category category = new Category();
        category.setCatId(1);
        Book book = new Book();
        book.setCategory(category);

        when(categoryRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> bookService.addBook(book));
        assertEquals("Category not found", ex.getMessage());
    }

    @Test
    void testAddBookThrowsIfPublisherNotFound() {
        Publisher publisher = new Publisher();
        publisher.setPublisherId(2);
        Book book = new Book();
        book.setPublisher(publisher);

        when(publisherRepository.findById(2)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> bookService.addBook(book));
        assertEquals("Publisher not found", ex.getMessage());
    }

    @Test
    void testGetAllBooks() {
        List<Book> books = List.of(new Book(), new Book());
        when(bookRepository.findAll()).thenReturn(books);

        List<Book> result = bookService.getAllBooks();

        assertEquals(2, result.size());
    }

    @Test
    void testGetBookByIsbn() {
        Book book = new Book();
        book.setIsbn("123");
        when(bookRepository.findById("123")).thenReturn(Optional.of(book));

        Optional<Book> result = bookService.getBookByIsbn("123");

        assertTrue(result.isPresent());
        assertEquals("123", result.get().getIsbn());
    }

    @Test
    void testGetBookByTitle() {
        Book book = new Book();
        book.setTitle("Java Basics");
        when(bookRepository.findByTitle("Java Basics")).thenReturn(Optional.of(book));

        Optional<Book> result = bookService.getBookByTitle("Java Basics");

        assertTrue(result.isPresent());
        assertEquals("Java Basics", result.get().getTitle());
    }

    @Test
    void testGetBooksByCategoryFound() {
        Category category = new Category();
        category.setCatDescription("Programming");
        List<Category> categories = List.of(category);
        List<Book> books = List.of(new Book());

        when(categoryRepository.findAll()).thenReturn(categories);
        when(bookRepository.findByCategory(category)).thenReturn(books);

        List<Book> result = bookService.getBooksByCategory("Programming");

        assertEquals(1, result.size());
    }

    @Test
    void testGetBooksByCategoryNotFound() {
        when(categoryRepository.findAll()).thenReturn(List.of());

        List<Book> result = bookService.getBooksByCategory("Unknown");

        assertTrue(result.isEmpty());
    }

    @Test
    void testGetBooksByPublisherFound() {
        Publisher publisher = new Publisher();
        publisher.setPublisherId(5);
        List<Book> books = List.of(new Book());

        when(publisherRepository.findById(5)).thenReturn(Optional.of(publisher));
        when(bookRepository.findByPublisher(publisher)).thenReturn(books);

        List<Book> result = bookService.getBooksByPublisher(5);

        assertEquals(1, result.size());
    }

    @Test
    void testGetBooksByPublisherThrowsIfNotFound() {
        when(publisherRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> bookService.getBooksByPublisher(99));
        assertEquals("Publisher not found", ex.getMessage());
    }

    @Test
    void testUpdateBookTitleSuccess() {
        Book book = new Book();
        book.setIsbn("abc");

        when(bookRepository.findById("abc")).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);

        Book result = bookService.updateBookTitle("abc", "New Title");

        assertEquals("New Title", result.getTitle());
    }

    @Test
    void testUpdateBookDescriptionSuccess() {
        Book book = new Book();
        book.setIsbn("xyz");

        when(bookRepository.findById("xyz")).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);

        Book result = bookService.updateBookDescription("xyz", "New Description");

        assertEquals("New Description", result.getDescription());
    }

    @Test
    void testUpdateBookCategorySuccess() {
        Book book = new Book();
        book.setIsbn("123");
        Category category = new Category();
        category.setCatDescription("Science");

        when(bookRepository.findById("123")).thenReturn(Optional.of(book));
        when(categoryRepository.findAll()).thenReturn(List.of(category));
        when(bookRepository.save(book)).thenReturn(book);

        Book result = bookService.updateBookCategory("123", "Science");

        assertEquals(category, result.getCategory());
    }

    @Test
    void testUpdateBookEditionSuccess() {
        Book book = new Book();
        book.setIsbn("456");

        when(bookRepository.findById("456")).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);

        Book result = bookService.updateBookEdition("456", "2nd Edition");

        assertEquals("2nd Edition", result.getEdition());
    }

    @Test
    void testUpdateBookPublisherSuccess() {
        Book book = new Book();
        book.setIsbn("789");
        Publisher publisher = new Publisher();
        publisher.setPublisherId(10);

        when(bookRepository.findById("789")).thenReturn(Optional.of(book));
        when(publisherRepository.findById(10)).thenReturn(Optional.of(publisher));
        when(bookRepository.save(book)).thenReturn(book);

        Book result = bookService.updateBookPublisher("789", 10);

        assertEquals(publisher, result.getPublisher());
    }

    @Test
    void testUpdateBookThrowsIfBookNotFound() {
        when(bookRepository.findById("missing")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                bookService.updateBookTitle("missing", "Title"));

        assertEquals("Book not found", ex.getMessage());
    }
}