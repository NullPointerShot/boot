package com.example.onlinecourseplatform.service;

import com.example.onlinecourseplatform.converter.EntityMapper;
import com.example.onlinecourseplatform.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public abstract class BaseService<T, ID, DTO> {

    protected final JpaRepository<T, ID> jpaRepository;
    protected final EntityMapper<T, DTO> entityMapper;

    protected BaseService(JpaRepository<T, ID> jpaRepository, EntityMapper<T, DTO> entityMapper) {
        this.jpaRepository = jpaRepository;
        this.entityMapper = entityMapper;
    }

    public Page<DTO> findAll(Pageable pageable) {
        return jpaRepository.findAll(pageable)
                .map(entityMapper::toDTO);
    }

    public Optional<DTO> findById(ID id) {
        return jpaRepository.findById(id)
                .map(entityMapper::toDTO);
    }

    public DTO save(DTO dto) {
        T entity = entityMapper.toEntity(dto);
        T savedEntity = jpaRepository.save(entity);
        return entityMapper.toDTO(savedEntity);
    }

    public DTO update(ID id, DTO dto) throws ResourceNotFoundException {
        if (!jpaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Entity not found with ID: " + id);
        }
        T entityToUpdate = entityMapper.toEntity(dto);
        
        jpaRepository.save(entityToUpdate);
        return entityMapper.toDTO(entityToUpdate);
    }



    public void deleteById(ID id) throws ResourceNotFoundException {
        if (!jpaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Entity not found with ID: " + id);
        }
        jpaRepository.deleteById(id);
    }
}
