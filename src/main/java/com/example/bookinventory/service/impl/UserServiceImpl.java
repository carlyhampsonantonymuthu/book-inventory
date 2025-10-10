package com.example.bookinventory.service.impl;


import com.example.bookinventory.entity.User;
import com.example.bookinventory.repository.UserRepository;
import com.example.bookinventory.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    public UserServiceImpl(UserRepository userRepository) { this.userRepository = userRepository; }

    @Override
    public User addUser(User user) {
        if (userRepository.existsByUserName(user.getUserName())) {
            throw new RuntimeException("User already exists");
        }
        return userRepository.save(user);
    }

    @Override
    public java.util.Optional<User> getUserById(Integer userId) {
        return userRepository.findById(userId);
    }

    @Override
    public User updatePhoneNumber(Integer userId, String phoneNumber) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setPhoneNumber(phoneNumber);
        return userRepository.save(user);
    }

    @Override
    public User updateFirstName(Integer userId, String firstName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setFirstName(firstName);
        return userRepository.save(user);
    }

    @Override
    public User updateLastName(Integer userId, String lastName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setLastName(lastName);
        return userRepository.save(user);
    }
}

