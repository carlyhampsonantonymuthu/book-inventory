package com.example.bookinventory.repository;


import com.example.bookinventory.entity.Publisher;
import com.example.bookinventory.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PublisherRepository extends JpaRepository<Publisher, Integer> {
    Optional<Publisher> findByName(String name);
    List<Publisher> findByCity(String city);
    List<Publisher> findByState(State state);
    boolean existsByName(String name);
}
