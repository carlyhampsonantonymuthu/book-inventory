package com.example.bookinventory.repository;

import com.example.bookinventory.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
    boolean existsByFirstNameAndLastName(String firstName, String lastName);
    Optional<Author> findByFirstName(String firstName);
    Optional<Author> findByLastName(String lastName);
}

