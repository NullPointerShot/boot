package com.example.onlinecourseplatform.dto.baseDTO;

import com.example.onlinecourseplatform.model.baseEntity.Company;
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
public class UserDTO {
    private UUID id;
    private String name;
    private String email;
    private String password;
    private String role;
    private BigDecimal balance;
    private Company company;
}
