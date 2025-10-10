package com.example.bookinventory;


import com.example.bookinventory.entity.State;
import com.example.bookinventory.repository.StateRepository;
import com.example.bookinventory.service.impl.StateServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StateServiceImplTest {

    private StateRepository stateRepository;
    private StateServiceImpl stateService;

    @BeforeEach
    void setUp() {
        stateRepository = mock(StateRepository.class);
        stateService = new StateServiceImpl(stateRepository);
    }

    @Test
    void testAddStateSuccess() {
        State state = new State();
        state.setStateCode("TN");
        state.setStateName("Tamil Nadu");

        when(stateRepository.existsById("TN")).thenReturn(false);
        when(stateRepository.save(state)).thenReturn(state);

        State result = stateService.addState(state);

        assertNotNull(result);
        assertEquals("Tamil Nadu", result.getStateName());
        verify(stateRepository).save(state);
    }

    @Test
    void testAddStateThrowsIfExists() {
        State state = new State();
        state.setStateCode("KA");

        when(stateRepository.existsById("KA")).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                stateService.addState(state));

        assertEquals("State already exists with code: KA", exception.getMessage());
        verify(stateRepository, never()).save(any());
    }

    @Test
    void testGetAllStates() {
        List<State> states = List.of(new State(), new State());
        when(stateRepository.findAll()).thenReturn(states);

        List<State> result = stateService.getAllStates();

        assertEquals(2, result.size());
        verify(stateRepository).findAll();
    }

    @Test
    void testGetStateByCodeFound() {
        State state = new State();
        state.setStateCode("MH");
        state.setStateName("Maharashtra");

        when(stateRepository.findById("MH")).thenReturn(Optional.of(state));

        Optional<State> result = stateService.getStateByCode("MH");

        assertTrue(result.isPresent());
        assertEquals("Maharashtra", result.get().getStateName());
    }

    @Test
    void testGetStateByCodeNotFound() {
        when(stateRepository.findById("XYZ")).thenReturn(Optional.empty());

        Optional<State> result = stateService.getStateByCode("XYZ");

        assertTrue(result.isEmpty());
    }

    @Test
    void testUpdateStateNameSuccess() {
        State state = new State();
        state.setStateCode("KL");
        state.setStateName("Old Name");

        when(stateRepository.findById("KL")).thenReturn(Optional.of(state));
        when(stateRepository.save(state)).thenReturn(state);

        State updated = stateService.updateStateName("KL", "Kerala");

        assertEquals("Kerala", updated.getStateName());
        verify(stateRepository).save(state);
    }

    @Test
    void testUpdateStateNameThrowsIfNotFound() {
        when(stateRepository.findById("XX")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                stateService.updateStateName("XX", "Unknown"));

        assertEquals("State not found with code: XX", exception.getMessage());
        verify(stateRepository, never()).save(any());
    }
}