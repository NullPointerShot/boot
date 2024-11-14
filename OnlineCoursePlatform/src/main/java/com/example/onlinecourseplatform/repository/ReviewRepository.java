package com.example.onlinecourseplatform.repository;

import com.example.onlinecourseplatform.model.baseEntity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {
    List<Review> findByCourseId(UUID courseId); 
}
