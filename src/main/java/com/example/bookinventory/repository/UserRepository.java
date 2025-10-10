package com.example.bookinventory.repository;

import com.example.bookinventory.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserName(String userName);
    boolean existsByUserName(String userName);
}