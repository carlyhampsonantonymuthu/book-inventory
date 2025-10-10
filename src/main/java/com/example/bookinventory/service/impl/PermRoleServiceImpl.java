package com.example.bookinventory.service.impl;

import com.example.bookinventory.entity.PermRole;
import com.example.bookinventory.repository.PermRoleRepository;
import com.example.bookinventory.service.PermRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class PermRoleServiceImpl implements PermRoleService {

    private final PermRoleRepository permRoleRepository;

    public PermRoleServiceImpl(PermRoleRepository permRoleRepository) {
        this.permRoleRepository = permRoleRepository;
    }

    @Override
    public PermRole addPermRole(PermRole role) {
        if (role.getPermRole() == null || role.getPermRole().trim().isEmpty()) {
            throw new IllegalArgumentException("permRole name cannot be empty");
        }
        return permRoleRepository.save(role);
    }

    @Override
    public Optional<PermRole> getPermRoleByRoleNumber(Integer roleNumber) {
        return permRoleRepository.findById(roleNumber);
    }

    @Override
    public PermRole updatePermRole(Integer roleNumber, String newRole) {
        PermRole existing = permRoleRepository.findById(roleNumber)
                .orElseThrow(() -> new RuntimeException("PermRole not found for roleNumber: " + roleNumber));

        existing.setPermRole(newRole);
        return permRoleRepository.save(existing);
    }
}
