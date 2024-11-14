package com.example.onlinecourseplatform.repository;

import com.example.onlinecourseplatform.model.baseEntity.Cart;
import com.example.onlinecourseplatform.model.baseEntity.CartItem;
import com.example.onlinecourseplatform.model.baseEntity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
    boolean existsByCartAndCourse(Cart cart, Course course);

    Optional<CartItem> findByCartAndCourse(Cart cart, Course course);

    void deleteByCartAndCourse(Cart cart, Course course); 
}
