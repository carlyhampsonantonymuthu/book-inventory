package com.example.bookinventory;


import com.example.bookinventory.entity.Book;
import com.example.bookinventory.entity.BookReview;
import com.example.bookinventory.entity.BookReviewId;
import com.example.bookinventory.entity.Reviewer;
import com.example.bookinventory.repository.BookRepository;
import com.example.bookinventory.repository.BookReviewRepository;
import com.example.bookinventory.repository.ReviewerRepository;
import com.example.bookinventory.service.impl.BookReviewServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookReviewServiceImplTest {

    private BookReviewRepository bookReviewRepository;
    private BookRepository bookRepository;
    private ReviewerRepository reviewerRepository;
    private BookReviewServiceImpl bookReviewService;

    @BeforeEach
    void setUp() {
        bookReviewRepository = mock(BookReviewRepository.class);
        bookRepository = mock(BookRepository.class);
        reviewerRepository = mock(ReviewerRepository.class);
        bookReviewService = new BookReviewServiceImpl(bookReviewRepository, bookRepository, reviewerRepository);
    }

    @Test
    void testAddBookReviewSuccess() {
        Book book = new Book();
        book.setIsbn("123456");

        Reviewer reviewer = new Reviewer();
        reviewer.setReviewerId(1);

        BookReview review = new BookReview();
        review.setBook(book);
        review.setReviewer(reviewer);
        review.setRating(4.5);
        review.setComments("Great book!");

        BookReviewId id = new BookReviewId("123456", 1);

        when(bookReviewRepository.existsById(id)).thenReturn(false);
        when(bookReviewRepository.save(review)).thenReturn(review);

        BookReview result = bookReviewService.addBookReview(review);

        assertNotNull(result);
        assertEquals(4.5, result.getRating());
        verify(bookReviewRepository).save(review);
    }

    @Test
    void testAddBookReviewThrowsIfBookMissing() {
        BookReview review = new BookReview();
        review.setReviewer(new Reviewer());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                bookReviewService.addBookReview(review));

        assertEquals("Book ISBN is required", ex.getMessage());
    }

    @Test
    void testAddBookReviewThrowsIfReviewerMissing() {
        BookReview review = new BookReview();
        review.setBook(new Book());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                bookReviewService.addBookReview(review));

        assertEquals("Reviewer ID is required", ex.getMessage());
    }

    @Test
    void testAddBookReviewThrowsIfAlreadyExists() {
        Book book = new Book();
        book.setIsbn("123456");

        Reviewer reviewer = new Reviewer();
        reviewer.setReviewerId(1);

        BookReview review = new BookReview();
        review.setBook(book);
        review.setReviewer(reviewer);

        BookReviewId id = new BookReviewId("123456", 1);

        when(bookReviewRepository.existsById(id)).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                bookReviewService.addBookReview(review));

        assertEquals("Review already exists for this reviewer and book", ex.getMessage());
    }

    @Test
    void testGetBookReviewsByIsbnSuccess() {
        BookReview review = new BookReview();
        review.setRating(5.0);
        when(bookReviewRepository.findByBook_Isbn("123456")).thenReturn(List.of(review));

        List<BookReview> result = bookReviewService.getBookReviewsByIsbn("123456");

        assertEquals(1, result.size());
        assertEquals(5.0, result.get(0).getRating());
    }

    @Test
    void testGetBookReviewsByIsbnThrowsIfEmpty() {
        when(bookReviewRepository.findByBook_Isbn("000000")).thenReturn(List.of());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                bookReviewService.getBookReviewsByIsbn("000000"));

        assertEquals("No reviews found for ISBN: 000000", ex.getMessage());
    }

    @Test
    void testUpdateRatingSuccess() {
        BookReviewId id = new BookReviewId("123456", 1);
        BookReview review = new BookReview();
        review.setRating(3.0);

        when(bookReviewRepository.findById(id)).thenReturn(Optional.of(review));
        when(bookReviewRepository.save(review)).thenReturn(review);

        BookReview result = bookReviewService.updateRating("123456", 1, 4.5);

        assertEquals(4.5, result.getRating());
        verify(bookReviewRepository).save(review);
    }

    @Test
    void testUpdateRatingThrowsIfNotFound() {
        BookReviewId id = new BookReviewId("123456", 1);
        when(bookReviewRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                bookReviewService.updateRating("123456", 1, 4.0));

        assertEquals("Review not found for given ISBN and reviewer", ex.getMessage());
    }

    @Test
    void testUpdateCommentsSuccess() {
        BookReviewId id = new BookReviewId("123456", 1);
        BookReview review = new BookReview();
        review.setComments("Old comment");

        when(bookReviewRepository.findById(id)).thenReturn(Optional.of(review));
        when(bookReviewRepository.save(review)).thenReturn(review);

        BookReview result = bookReviewService.updateComments("123456", 1, "Updated comment");

        assertEquals("Updated comment", result.getComments());
        verify(bookReviewRepository).save(review);
    }

    @Test
    void testUpdateCommentsThrowsIfNotFound() {
        BookReviewId id = new BookReviewId("123456", 1);
        when(bookReviewRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                bookReviewService.updateComments("123456", 1, "New comment"));

        assertEquals("Review not found for given ISBN and reviewer", ex.getMessage());
    }
}