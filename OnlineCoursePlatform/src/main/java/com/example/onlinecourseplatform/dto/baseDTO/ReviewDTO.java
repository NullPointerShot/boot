package com.example.onlinecourseplatform.dto.baseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDTO {
    private UUID id;
    private UUID courseId;
    private UUID userId;
    private int rating;
    private String text;
}
