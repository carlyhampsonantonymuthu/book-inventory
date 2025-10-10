package com.example.bookinventory.repository;

import com.example.bookinventory.entity.PurchaseLog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PurchaseLogRepository extends JpaRepository<PurchaseLog, Integer> {
    List<PurchaseLog> findByUser_UserId(Integer userId);
}
