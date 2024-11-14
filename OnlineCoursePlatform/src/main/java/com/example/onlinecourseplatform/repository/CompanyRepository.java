package com.example.onlinecourseplatform.repository;

import com.example.onlinecourseplatform.model.baseEntity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CompanyRepository extends JpaRepository<Company, UUID> {
    Optional<Company> findByName(String name);

    Optional<Company> findById(UUID id); 
}
