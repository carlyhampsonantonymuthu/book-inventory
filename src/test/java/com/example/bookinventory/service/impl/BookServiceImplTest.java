package com.example.bookinventory.service.impl;

import com.example.bookinventory.entity.Book;
import com.example.bookinventory.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetBookByIsbn() {
        Book book = new Book();
        book.setIsbn("12345");
        when(bookRepository.findById("12345")).thenReturn(Optional.of(book));

        Book found = bookService.getBookByIsbn("12345");
        assertNotNull(found);
        assertEquals("12345", found.getIsbn());
    }
}


