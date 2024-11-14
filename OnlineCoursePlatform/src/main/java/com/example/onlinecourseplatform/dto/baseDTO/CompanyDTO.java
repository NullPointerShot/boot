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
public class CompanyDTO {
    private UUID id;
    private String name;
    private String description;
    private String website;
    private BigDecimal capital;
}
