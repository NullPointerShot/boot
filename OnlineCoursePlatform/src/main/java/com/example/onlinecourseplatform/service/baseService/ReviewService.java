package com.example.onlinecourseplatform.service.baseService;

import com.example.onlinecourseplatform.converter.ReviewMapper;
import com.example.onlinecourseplatform.dto.baseDTO.ReviewDTO;
import com.example.onlinecourseplatform.model.baseEntity.Review;
import com.example.onlinecourseplatform.repository.ReviewRepository;
import com.example.onlinecourseplatform.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ReviewService extends BaseService<Review, UUID, ReviewDTO> {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    public ReviewService(ReviewRepository reviewRepository, ReviewMapper reviewMapper) {
        super(reviewRepository, reviewMapper);
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
    }

}
