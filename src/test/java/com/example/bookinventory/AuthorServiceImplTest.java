package com.example.bookinventory;

import com.example.bookinventory.entity.Author;
import com.example.bookinventory.entity.Book;
import com.example.bookinventory.entity.BookAuthor;
import com.example.bookinventory.repository.AuthorRepository;
import com.example.bookinventory.repository.BookRepository;
import com.example.bookinventory.service.impl.AuthorServiceImpl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthorServiceImplTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private AuthorServiceImpl authorService;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddAuthor_Success() {
        Author author = new Author();
        author.setFirstName("John");
        author.setLastName("Doe");

        when(authorRepository.existsByFirstNameAndLastName("John", "Doe")).thenReturn(false);
        when(authorRepository.save(author)).thenReturn(author);

        Author saved = authorService.addAuthor(author);
        assertEquals("John", saved.getFirstName());
        verify(authorRepository).save(author);
    }

    @Test
    void testAddAuthor_AlreadyExists() {
        Author author = new Author();
        author.setFirstName("John");
        author.setLastName("Doe");

        when(authorRepository.existsByFirstNameAndLastName("John", "Doe")).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> authorService.addAuthor(author));
        assertEquals("Author already exists", ex.getMessage());
    }

    @Test
    void testGetAuthorById_Found() {
        Author author = new Author();
        author.setAuthorId(1);
        when(authorRepository.findById(1)).thenReturn(Optional.of(author));

        Optional<Author> result = authorService.getAuthorById(1);
        assertTrue(result.isPresent());
        assertEquals(1, result.get().getAuthorId());
    }

    @Test
    void testGetAuthorById_NotFound() {
        when(authorRepository.findById(99)).thenReturn(Optional.empty());
        Optional<Author> result = authorService.getAuthorById(99);
        assertFalse(result.isPresent());
    }

    @Test
    void testUpdateFirstName_Success() {
        Author author = new Author();
        author.setAuthorId(1);
        author.setFirstName("Old");

        when(authorRepository.findById(1)).thenReturn(Optional.of(author));
        when(authorRepository.save(any())).thenReturn(author);

        Author updated = authorService.updateFirstName(1, "New");
        assertEquals("New", updated.getFirstName());
    }

    @Test
    void testUpdateLastName_Success() {
        Author author = new Author();
        author.setAuthorId(1);
        author.setLastName("Old");

        when(authorRepository.findById(1)).thenReturn(Optional.of(author));
        when(authorRepository.save(any())).thenReturn(author);

        Author updated = authorService.updateLastName(1, "New");
        assertEquals("New", updated.getLastName());
    }

    @Test
    void testGetAllAuthors() {
        List<Author> authors = List.of(new Author(), new Author());
        when(authorRepository.findAll()).thenReturn(authors);

        List<Author> result = authorService.getAllAuthors();
        assertEquals(2, result.size());
    }

    @Test
    void testGetBooksByAuthor() {
        Author author = new Author();
        Book book1 = new Book();
        Book book2 = new Book();

        BookAuthor ba1 = new BookAuthor();
        ba1.setBook(book1);
        BookAuthor ba2 = new BookAuthor();
        ba2.setBook(book2);

        author.setBookAuthors(Set.of(ba1, ba2));

        when(authorRepository.findById(1)).thenReturn(Optional.of(author));

        List<Book> books = authorService.getBooksByAuthor(1);
        assertEquals(2, books.size());
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }
}

