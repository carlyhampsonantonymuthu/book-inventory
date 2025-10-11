package com.example.bookinventory.controller;

import com.example.bookinventory.entity.PermRole;
import com.example.bookinventory.service.PermRoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/permrole")
@CrossOrigin(origins = "*")


public class PermRoleController {

    private final PermRoleService permRoleService;

    public PermRoleController(PermRoleService permRoleService) {
        this.permRoleService = permRoleService;
    }

    // ➕ Add new PermRole
    @PostMapping("/post")
    public ResponseEntity<?> addPermRole(@RequestBody PermRole role) {
        try {
            PermRole saved = permRoleService.addPermRole(role);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "code", "POSTSUCCESS",
                    "message", "Perm Role added successfully",
                    "roleNumber", saved.getRoleNumber()
            ));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest()
                    .body(Map.of("code", "BADREQUEST", "message", ex.getMessage()));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("code", "ADDFAILS", "message", ex.getMessage()));
        }
    }

    // 🔍 Get PermRole by role number
    @GetMapping("/{roleNumber}")
    public ResponseEntity<?> getPermRole(@PathVariable Integer roleNumber) {
        return permRoleService.getPermRoleByRoleNumber(roleNumber)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("code", "NOTFOUND", "message", "Perm Role not found")));
    }

    // ✏️ Update permission role name
    @PutMapping("/{roleNumber}")
    public ResponseEntity<?> updatePermRole(@PathVariable Integer roleNumber, @RequestBody Map<String, String> body) {
        String permRole = body.get("permRole");
        if (permRole == null || permRole.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("code", "BADREQUEST", "message", "permRole is required"));
        }

        try {
            PermRole updated = permRoleService.updatePermRole(roleNumber, permRole);
            return ResponseEntity.ok(Map.of(
                    "code", "UPDATESUCCESS",
                    "message", "Perm Role updated successfully",
                    "updatedPermRole", updated
            ));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("code", "NOTFOUND", "message", ex.getMessage()));
        }
    }
}
