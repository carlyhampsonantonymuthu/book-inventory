package com.example.bookinventory;

import com.example.bookinventory.entity.BookCondition;
import com.example.bookinventory.repository.BookConditionRepository;
import com.example.bookinventory.service.impl.BookConditionServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookConditionServiceImplTest {

    private BookConditionRepository bookConditionRepository;
    private BookConditionServiceImpl bookConditionService;

    @BeforeEach
    void setUp() {
        bookConditionRepository = mock(BookConditionRepository.class);
        bookConditionService = new BookConditionServiceImpl(bookConditionRepository);
    }

    @Test
    void testAddBookCondition() {
        BookCondition condition = new BookCondition();
        condition.setRanks(1);
        condition.setPrice(100.0);

        when(bookConditionRepository.save(condition)).thenReturn(condition);

        BookCondition result = bookConditionService.addBookCondition(condition);

        assertEquals(condition, result);
        verify(bookConditionRepository).save(condition);
    }

    @Test
    void testGetBookConditionByRank() {
        BookCondition condition = new BookCondition();
        condition.setRanks(2);

        when(bookConditionRepository.findById(2)).thenReturn(Optional.of(condition));

        Optional<BookCondition> result = bookConditionService.getBookConditionByRank(2);

        assertTrue(result.isPresent());
        assertEquals(2, result.get().getRanks());
        verify(bookConditionRepository).findById(2);
    }

    @Test
    void testUpdatePrice() {
        BookCondition condition = new BookCondition();
        condition.setRanks(3);
        condition.setPrice(50.0);

        when(bookConditionRepository.findById(3)).thenReturn(Optional.of(condition));
        when(bookConditionRepository.save(any(BookCondition.class))).thenReturn(condition);

        BookCondition result = bookConditionService.updatePrice(3, 75.0);

        assertEquals(75.0, result.getPrice());
        verify(bookConditionRepository).save(condition);
    }

    @Test
    void testUpdateDescription() {
        BookCondition condition = new BookCondition();
        condition.setRanks(4);
        condition.setDescription("Old description");

        when(bookConditionRepository.findById(4)).thenReturn(Optional.of(condition));
        when(bookConditionRepository.save(any(BookCondition.class))).thenReturn(condition);

        BookCondition result = bookConditionService.updateDescription(4, "New description");

        assertEquals("New description", result.getDescription());
        verify(bookConditionRepository).save(condition);
    }

    @Test
    void testUpdateFullDescription() {
        BookCondition condition = new BookCondition();
        condition.setRanks(5);
        condition.setFullDescription("Old full description");

        when(bookConditionRepository.findById(5)).thenReturn(Optional.of(condition));
        when(bookConditionRepository.save(any(BookCondition.class))).thenReturn(condition);

        BookCondition result = bookConditionService.updateFullDescription(5, "Updated full description");

        assertEquals("Updated full description", result.getFullDescription());
        verify(bookConditionRepository).save(condition);
    }

    @Test
    void testUpdatePriceThrowsException() {
        when(bookConditionRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                bookConditionService.updatePrice(99, 100.0));

        assertEquals("Condition not found", exception.getMessage());
    }
}
