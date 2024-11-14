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
public class CartItemDTO {
    private UUID id;
    private UUID cartId;
    private UUID courseId;
    private int quantity;
}
