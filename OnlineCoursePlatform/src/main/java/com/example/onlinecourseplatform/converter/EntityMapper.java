package com.example.onlinecourseplatform.converter;

import org.mapstruct.Mapper;

public interface EntityMapper<T, DTO> {
    DTO toDTO(T entity);
    T toEntity(DTO dto);
}
