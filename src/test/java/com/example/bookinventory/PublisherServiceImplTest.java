package com.example.bookinventory;



import com.example.bookinventory.entity.Publisher;
import com.example.bookinventory.entity.State;
import com.example.bookinventory.repository.PublisherRepository;
import com.example.bookinventory.repository.StateRepository;
import com.example.bookinventory.service.impl.PublisherServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PublisherServiceImplTest {

    private PublisherRepository publisherRepository;
    private StateRepository stateRepository;
    private PublisherServiceImpl publisherService;

    @BeforeEach
    void setUp() {
        publisherRepository = mock(PublisherRepository.class);
        stateRepository = mock(StateRepository.class);
        publisherService = new PublisherServiceImpl(publisherRepository, stateRepository);
    }

    @Test
    void testAddPublisherSuccess() {
        Publisher publisher = new Publisher();
        publisher.setName("Pearson");

        when(publisherRepository.existsByName("Pearson")).thenReturn(false);
        when(publisherRepository.save(publisher)).thenReturn(publisher);

        Publisher result = publisherService.addPublisher(publisher);

        assertEquals("Pearson", result.getName());
        verify(publisherRepository).save(publisher);
    }

    @Test
    void testAddPublisherThrowsIfExists() {
        Publisher publisher = new Publisher();
        publisher.setName("O'Reilly");

        when(publisherRepository.existsByName("O'Reilly")).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                publisherService.addPublisher(publisher));

        assertEquals("Publisher already exists", ex.getMessage());
        verify(publisherRepository, never()).save(any());
    }

    @Test
    void testGetAllPublishers() {
        List<Publisher> publishers = List.of(new Publisher(), new Publisher());
        when(publisherRepository.findAll()).thenReturn(publishers);

        List<Publisher> result = publisherService.getAllPublishers();

        assertEquals(2, result.size());
        verify(publisherRepository).findAll();
    }

    @Test
    void testGetPublisherById() {
        Publisher publisher = new Publisher();
        publisher.setPublisherId(1);

        when(publisherRepository.findById(1)).thenReturn(Optional.of(publisher));

        Optional<Publisher> result = publisherService.getPublisherById(1);

        assertTrue(result.isPresent());
        assertEquals(1, result.get().getPublisherId());
    }

    @Test
    void testGetPublisherByName() {
        Publisher publisher = new Publisher();
        publisher.setName("Springer");

        when(publisherRepository.findByName("Springer")).thenReturn(Optional.of(publisher));

        Optional<Publisher> result = publisherService.getPublisherByName("Springer");

        assertTrue(result.isPresent());
        assertEquals("Springer", result.get().getName());
    }

    @Test
    void testGetPublishersByCity() {
        List<Publisher> publishers = List.of(new Publisher());
        when(publisherRepository.findByCity("New York")).thenReturn(publishers);

        List<Publisher> result = publisherService.getPublishersByCity("New York");

        assertEquals(1, result.size());
        verify(publisherRepository).findByCity("New York");
    }

    @Test
    void testGetPublishersByStateFound() {
        State state = new State();
        state.setStateName("California");

        Publisher publisher = new Publisher();
        List<State> states = List.of(state);
        List<Publisher> publishers = List.of(publisher);

        when(stateRepository.findAll()).thenReturn(states);
        when(publisherRepository.findByState(state)).thenReturn(publishers);

        List<Publisher> result = publisherService.getPublishersByState("California");

        assertEquals(1, result.size());
        verify(publisherRepository).findByState(state);
    }

    @Test
    void testGetPublishersByStateNotFound() {
        when(stateRepository.findAll()).thenReturn(List.of());

        List<Publisher> result = publisherService.getPublishersByState("Unknown");

        assertTrue(result.isEmpty());
    }

    @Test
    void testUpdatePublisherCity() {
        Publisher publisher = new Publisher();
        publisher.setPublisherId(1);
        publisher.setCity("Old City");

        when(publisherRepository.findById(1)).thenReturn(Optional.of(publisher));
        when(publisherRepository.save(publisher)).thenReturn(publisher);

        Publisher result = publisherService.updatePublisherCity(1, "New City");

        assertEquals("New City", result.getCity());
    }

    @Test
    void testUpdatePublisherName() {
        Publisher publisher = new Publisher();
        publisher.setPublisherId(2);
        publisher.setName("Old Name");

        when(publisherRepository.findById(2)).thenReturn(Optional.of(publisher));
        when(publisherRepository.save(publisher)).thenReturn(publisher);

        Publisher result = publisherService.updatePublisherName(2, "New Name");

        assertEquals("New Name", result.getName());
    }

    @Test
    void testUpdatePublisherStateSuccess() {
        Publisher publisher = new Publisher();
        publisher.setPublisherId(3);

        State state = new State();
        state.setStateName("Texas");

        when(publisherRepository.findById(3)).thenReturn(Optional.of(publisher));
        when(stateRepository.findAll()).thenReturn(List.of(state));
        when(publisherRepository.save(publisher)).thenReturn(publisher);

        Publisher result = publisherService.updatePublisherState(3, "Texas");

        assertEquals(state, result.getState());
    }

    @Test
    void testUpdatePublisherStateThrowsIfStateNotFound() {
        Publisher publisher = new Publisher();
        publisher.setPublisherId(4);

        when(publisherRepository.findById(4)).thenReturn(Optional.of(publisher));
        when(stateRepository.findAll()).thenReturn(List.of());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                publisherService.updatePublisherState(4, "Unknown"));

        assertEquals("State not found", ex.getMessage());
    }

    @Test
    void testUpdatePublisherThrowsIfPublisherNotFound() {
        when(publisherRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                publisherService.updatePublisherCity(99, "City"));

        assertEquals("Publisher not found", ex.getMessage());
    }
}