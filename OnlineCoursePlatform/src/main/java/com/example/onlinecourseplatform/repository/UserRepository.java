package com.example.onlinecourseplatform.repository;

import com.example.onlinecourseplatform.model.baseEntity.User;
import com.example.onlinecourseplatform.model.enumEntity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByName(String name);

    List<User> findAllByRole(UserRole role);

    Optional<User> findByEmail(String email);

    void deleteByEmail(String email); 
}
