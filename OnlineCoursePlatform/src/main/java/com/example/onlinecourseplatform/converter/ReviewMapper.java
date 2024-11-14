package com.example.onlinecourseplatform.converter;

import com.example.onlinecourseplatform.dto.baseDTO.ReviewDTO;
import com.example.onlinecourseplatform.model.baseEntity.Review;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper extends EntityMapper<Review, ReviewDTO> {
}

