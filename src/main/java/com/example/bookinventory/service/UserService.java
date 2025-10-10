package com.example.bookinventory.service;

import com.example.bookinventory.entity.User;

import java.util.Optional;

public interface UserService {
    User addUser(User user);
    Optional<User> getUserById(Integer userId);
    User updatePhoneNumber(Integer userId, String phoneNumber);
    User updateFirstName(Integer userId, String firstName);
    User updateLastName(Integer userId, String lastName);
}