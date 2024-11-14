package com.example.onlinecourseplatform.repository;

import com.example.onlinecourseplatform.model.baseEntity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<Course, UUID> {
    Optional<Course> findByTitle(String title);

    void deleteById(UUID id); 
}
