package com.example.onlinecourseplatform.controller.baseController;

import com.example.onlinecourseplatform.controller.BaseController;
import com.example.onlinecourseplatform.dto.baseDTO.ReviewDTO;
import com.example.onlinecourseplatform.model.baseEntity.Review;
import com.example.onlinecourseplatform.service.baseService.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/reviews")
public class ReviewController extends BaseController<Review, UUID, ReviewDTO> {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        super(reviewService);
        this.reviewService = reviewService;
    }

    
    @PreAuthorize("hasRole('USER') or hasRole('COMPANY_REP') or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ReviewDTO> createReview(@RequestBody ReviewDTO reviewDTO) {
        ReviewDTO savedReview = reviewService.save(reviewDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedReview);
    }

    
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and @reviewService.isReviewAuthor(#id, authentication))")
    @PutMapping("/{id}")
    public ResponseEntity<ReviewDTO> updateReview(@PathVariable UUID id, @RequestBody ReviewDTO reviewDTO) {
        ReviewDTO updatedReview = reviewService.update(id, reviewDTO);
        return ResponseEntity.ok(updatedReview);
    }

    
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and @reviewService.isReviewAuthor(#id, authentication))")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable UUID id) {
        reviewService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    
    
    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('COMPANY_REP') or hasRole('ADMIN')")
    public ResponseEntity<List<ReviewDTO>> getAllReviews(Pageable pageable) {
        Page<ReviewDTO> reviews = reviewService.findAll(pageable);
        return ResponseEntity.ok(reviews.getContent());
    }


    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('COMPANY_REP') or hasRole('ADMIN')")
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable UUID id) {
        Optional<ReviewDTO> reviewDTO = reviewService.findById(id);
        return reviewDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
