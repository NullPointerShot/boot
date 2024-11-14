package com.example.onlinecourseplatform.dto.baseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDTO {
    private UUID id;
    private UUID userId;
    private List<CartItemDTO> items;
}
