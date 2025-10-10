package com.example.bookinventory.service;

import com.example.bookinventory.entity.PermRole;

import java.util.Optional;

public interface PermRoleService {
    PermRole addPermRole(PermRole role);
    Optional<PermRole> getPermRoleByRoleNumber(Integer roleNumber);
    PermRole updatePermRole(Integer roleNumber, String newRole);
}
