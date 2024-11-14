package com.example.onlinecourseplatform.dto.securityDto;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
