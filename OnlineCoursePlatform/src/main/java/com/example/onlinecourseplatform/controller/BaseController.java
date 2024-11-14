package com.example.onlinecourseplatform.controller;

import com.example.onlinecourseplatform.exception.ResourceNotFoundException;
import com.example.onlinecourseplatform.service.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

public abstract class BaseController<T, ID, DTO> {

    protected final BaseService<T, ID, DTO> baseService;

    protected BaseController(BaseService<T, ID, DTO> baseService) {
        this.baseService = baseService;
    }

    @GetMapping
    public ResponseEntity<List<DTO>> getAll(Pageable pageable) {
        Page<DTO> entities = baseService.findAll(pageable);
        return ResponseEntity.ok(entities.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DTO> getById(@PathVariable ID id) {
        Optional<DTO> entity = baseService.findById(id);
        return entity.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<DTO> create(@RequestBody DTO dto) {
        DTO savedEntity = baseService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEntity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DTO> update(@PathVariable ID id, @RequestBody DTO dto) throws ResourceNotFoundException {
        DTO updatedEntity = baseService.update(id, dto);
        return ResponseEntity.ok(updatedEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable ID id) {
        baseService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
