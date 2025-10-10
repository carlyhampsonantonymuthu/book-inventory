package com.example.bookinventory;

import com.example.bookinventory.entity.PermRole;
import com.example.bookinventory.repository.PermRoleRepository;
import com.example.bookinventory.service.impl.PermRoleServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PermRoleServiceImplTest {

    private PermRoleRepository permRoleRepository;
    private PermRoleServiceImpl permRoleService;

    @BeforeEach
    void setUp() {
        permRoleRepository = mock(PermRoleRepository.class);
        permRoleService = new PermRoleServiceImpl(permRoleRepository);
    }

    @Test
    void testAddPermRoleSuccess() {
        PermRole role = new PermRole();
        role.setRoleNumber(1);
        role.setPermRole("ADMIN");

        when(permRoleRepository.save(role)).thenReturn(role);

        PermRole result = permRoleService.addPermRole(role);

        assertNotNull(result);
        assertEquals("ADMIN", result.getPermRole());
        verify(permRoleRepository).save(role);
    }

    @Test
    void testAddPermRoleThrowsExceptionForEmptyRole() {
        PermRole role = new PermRole();
        role.setRoleNumber(2);
        role.setPermRole("  "); // empty after trim

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                permRoleService.addPermRole(role));

        assertEquals("permRole name cannot be empty", exception.getMessage());
        verify(permRoleRepository, never()).save(any());
    }

    @Test
    void testGetPermRoleByRoleNumber() {
        PermRole role = new PermRole();
        role.setRoleNumber(3);
        role.setPermRole("USER");

        when(permRoleRepository.findById(3)).thenReturn(Optional.of(role));

        Optional<PermRole> result = permRoleService.getPermRoleByRoleNumber(3);

        assertTrue(result.isPresent());
        assertEquals("USER", result.get().getPermRole());
        verify(permRoleRepository).findById(3);
    }

    @Test
    void testUpdatePermRoleSuccess() {
        PermRole role = new PermRole();
        role.setRoleNumber(4);
        role.setPermRole("VIEWER");

        when(permRoleRepository.findById(4)).thenReturn(Optional.of(role));
        when(permRoleRepository.save(any(PermRole.class))).thenReturn(role);

        PermRole updated = permRoleService.updatePermRole(4, "EDITOR");

        assertEquals("EDITOR", updated.getPermRole());
        verify(permRoleRepository).save(role);
    }

    @Test
    void testUpdatePermRoleThrowsExceptionIfNotFound() {
        when(permRoleRepository.findById(99)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                permRoleService.updatePermRole(99, "MODERATOR"));

        assertEquals("PermRole not found for roleNumber: 99", exception.getMessage());
        verify(permRoleRepository, never()).save(any());
    }
}

