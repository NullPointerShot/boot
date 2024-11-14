package com.example.onlinecourseplatform.controller.baseController;

import com.example.onlinecourseplatform.dto.baseDTO.ReviewDTO;
import com.example.onlinecourseplatform.exception.ResourceNotFoundException;
import com.example.onlinecourseplatform.service.baseService.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ReviewControllerTest {

    @InjectMocks
    private ReviewController reviewController;

    @Mock
    private ReviewService reviewService;

    private ReviewDTO reviewDTO;
    private UUID reviewId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reviewId = UUID.randomUUID();
        reviewDTO = new ReviewDTO();
        reviewDTO.setId(reviewId);
        
    }

    @Test
    @WithMockUser(roles = "USER")
    void createReview_ShouldReturnCreatedReview() {
        when(reviewService.save(any(ReviewDTO.class))).thenReturn(reviewDTO);

        ResponseEntity<ReviewDTO> response = reviewController.create(reviewDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(reviewDTO, response.getBody());
        verify(reviewService, times(1)).save(reviewDTO);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateReview_ShouldReturnUpdatedReview() throws ResourceNotFoundException {
        when(reviewService.update(any(UUID.class), any(ReviewDTO.class))).thenReturn(reviewDTO);

        ResponseEntity<ReviewDTO> response = reviewController.update(reviewId, reviewDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reviewDTO, response.getBody());
        verify(reviewService, times(1)).update(reviewId, reviewDTO);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteReview_ShouldReturnNoContent() {
        ResponseEntity<Void> response = reviewController.delete(reviewId);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(reviewService, times(1)).deleteById(reviewId);
    }
}
