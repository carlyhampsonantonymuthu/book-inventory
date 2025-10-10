package com.example.bookinventory.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "permrole")
public class PermRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleNumber;

    @Column(nullable = false)
    private String permRole;

    // Getters and Setters
    public Integer getRoleNumber() {
        return roleNumber;
    }

    public void setRoleNumber(Integer roleNumber) {
        this.roleNumber = roleNumber;
    }

    public String getPermRole() {
        return permRole;
    }

    public void setPermRole(String permRole) {
        this.permRole = permRole;
    }
}
