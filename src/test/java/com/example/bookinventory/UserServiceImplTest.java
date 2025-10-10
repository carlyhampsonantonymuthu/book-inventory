package com.example.bookinventory;



import com.example.bookinventory.entity.User;
import com.example.bookinventory.repository.UserRepository;
import com.example.bookinventory.service.impl.UserServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    private UserRepository userRepository;
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    void testAddUserSuccess() {
        User user = new User();
        user.setUserName("john_doe");

        when(userRepository.existsByUserName("john_doe")).thenReturn(false);
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.addUser(user);

        assertNotNull(result);
        assertEquals("john_doe", result.getUserName());
        verify(userRepository).save(user);
    }

    @Test
    void testAddUserThrowsIfExists() {
        User user = new User();
        user.setUserName("jane_doe");

        when(userRepository.existsByUserName("jane_doe")).thenReturn(true);

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                userService.addUser(user));

        assertEquals("User already exists", exception.getMessage());
        verify(userRepository, never()).save(any());
    }

    @Test
    void testGetUserByIdFound() {
        User user = new User();
        user.setUserId(1);
        user.setUserName("alice");

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserById(1);

        assertTrue(result.isPresent());
        assertEquals("alice", result.get().getUserName());
    }

    @Test
    void testGetUserByIdNotFound() {
        when(userRepository.findById(99)).thenReturn(Optional.empty());

        Optional<User> result = userService.getUserById(99);

        assertTrue(result.isEmpty());
    }

    @Test
    void testUpdatePhoneNumberSuccess() {
        User user = new User();
        user.setUserId(2);
        user.setPhoneNumber("1234567890");

        when(userRepository.findById(2)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.updatePhoneNumber(2, "9876543210");

        assertEquals("9876543210", result.getPhoneNumber());
        verify(userRepository).save(user);
    }

    @Test
    void testUpdateFirstNameSuccess() {
        User user = new User();
        user.setUserId(3);
        user.setFirstName("OldName");

        when(userRepository.findById(3)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.updateFirstName(3, "NewName");

        assertEquals("NewName", result.getFirstName());
        verify(userRepository).save(user);
    }

    @Test
    void testUpdateLastNameSuccess() {
        User user = new User();
        user.setUserId(4);
        user.setLastName("Smith");

        when(userRepository.findById(4)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.updateLastName(4, "Johnson");

        assertEquals("Johnson", result.getLastName());
        verify(userRepository).save(user);
    }

    @Test
    void testUpdatePhoneNumberThrowsIfNotFound() {
        when(userRepository.findById(100)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                userService.updatePhoneNumber(100, "0000000000"));

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, never()).save(any());
    }

    @Test
    void testUpdateFirstNameThrowsIfNotFound() {
        when(userRepository.findById(101)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                userService.updateFirstName(101, "Ghost"));

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, never()).save(any());
    }

    @Test
    void testUpdateLastNameThrowsIfNotFound() {
        when(userRepository.findById(102)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                userService.updateLastName(102, "Phantom"));

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, never()).save(any());
    }
}