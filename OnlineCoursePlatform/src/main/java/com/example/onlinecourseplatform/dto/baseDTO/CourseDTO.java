package com.example.onlinecourseplatform.dto.baseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseDTO {
    private UUID id;
    private String title;
    private String description;
    private String subject;
    private BigDecimal price;
    private BigDecimal discount;
    private String status;
    private BigDecimal rating;
    private UUID companyId;
    private UUID teacherId;
}
