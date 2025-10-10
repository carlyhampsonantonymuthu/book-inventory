package com.example.bookinventory;


import com.example.bookinventory.entity.Reviewer;
import com.example.bookinventory.repository.ReviewerRepository;
import com.example.bookinventory.service.impl.ReviewerServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReviewerServiceImplTest {

    private ReviewerRepository reviewerRepository;
    private ReviewerServiceImpl reviewerService;

    @BeforeEach
    void setUp() {
        reviewerRepository = mock(ReviewerRepository.class);
        reviewerService = new ReviewerServiceImpl(reviewerRepository);
    }

    @Test
    void testAddReviewerSuccess() {
        Reviewer reviewer = new Reviewer();
        reviewer.setName("Alice");

        when(reviewerRepository.existsByName("Alice")).thenReturn(false);
        when(reviewerRepository.save(reviewer)).thenReturn(reviewer);

        Reviewer result = reviewerService.addReviewer(reviewer);

        assertNotNull(result);
        assertEquals("Alice", result.getName());
        verify(reviewerRepository).save(reviewer);
    }

    @Test
    void testAddReviewerThrowsIfExists() {
        Reviewer reviewer = new Reviewer();
        reviewer.setName("Bob");

        when(reviewerRepository.existsByName("Bob")).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                reviewerService.addReviewer(reviewer));

        assertEquals("Reviewer already exists", ex.getMessage());
        verify(reviewerRepository, never()).save(any());
    }

    @Test
    void testGetReviewerByIdFound() {
        Reviewer reviewer = new Reviewer();
        reviewer.setReviewerId(1);
        reviewer.setName("Charlie");

        when(reviewerRepository.findById(1)).thenReturn(Optional.of(reviewer));

        Optional<Reviewer> result = reviewerService.getReviewerById(1);

        assertTrue(result.isPresent());
        assertEquals("Charlie", result.get().getName());
    }

    @Test
    void testGetReviewerByIdNotFound() {
        when(reviewerRepository.findById(99)).thenReturn(Optional.empty());

        Optional<Reviewer> result = reviewerService.getReviewerById(99);

        assertTrue(result.isEmpty());
    }

    @Test
    void testUpdateNameSuccess() {
        Reviewer reviewer = new Reviewer();
        reviewer.setReviewerId(2);
        reviewer.setName("Old Name");

        when(reviewerRepository.findById(2)).thenReturn(Optional.of(reviewer));
        when(reviewerRepository.save(reviewer)).thenReturn(reviewer);

        Reviewer result = reviewerService.updateName(2, "New Name");

        assertEquals("New Name", result.getName());
        verify(reviewerRepository).save(reviewer);
    }

    @Test
    void testUpdateNameThrowsIfNotFound() {
        when(reviewerRepository.findById(3)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                reviewerService.updateName(3, "Someone"));

        assertEquals("Reviewer not found", ex.getMessage());
        verify(reviewerRepository, never()).save(any());
    }

    @Test
    void testUpdateEmployedBySuccess() {
        Reviewer reviewer = new Reviewer();
        reviewer.setReviewerId(4);
        reviewer.setEmployedBy("Old Company");

        when(reviewerRepository.findById(4)).thenReturn(Optional.of(reviewer));
        when(reviewerRepository.save(reviewer)).thenReturn(reviewer);

        Reviewer result = reviewerService.updateEmployedBy(4, "New Company");

        assertEquals("New Company", result.getEmployedBy());
        verify(reviewerRepository).save(reviewer);
    }

    @Test
    void testUpdateEmployedByThrowsIfNotFound() {
        when(reviewerRepository.findById(5)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                reviewerService.updateEmployedBy(5, "Unknown"));

        assertEquals("Reviewer not found", ex.getMessage());
        verify(reviewerRepository, never()).save(any());
    }
}