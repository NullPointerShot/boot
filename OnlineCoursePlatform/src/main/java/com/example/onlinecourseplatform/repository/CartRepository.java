package com.example.onlinecourseplatform.repository;

import com.example.onlinecourseplatform.model.baseEntity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {
    Optional<Cart> findByUserId(UUID userId);

    void deleteByUserId(UUID userId); 
}