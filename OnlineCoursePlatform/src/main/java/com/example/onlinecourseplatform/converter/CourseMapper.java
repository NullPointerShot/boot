package com.example.onlinecourseplatform.converter;

import com.example.onlinecourseplatform.dto.baseDTO.CourseDTO;
import com.example.onlinecourseplatform.model.baseEntity.Course;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseMapper extends EntityMapper<Course, CourseDTO> {
}
