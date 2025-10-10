package com.example.bookinventory.repository;

import com.example.bookinventory.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<State, String> { }
