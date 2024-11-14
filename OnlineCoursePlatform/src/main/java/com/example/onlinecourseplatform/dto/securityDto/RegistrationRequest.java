package com.example.onlinecourseplatform.dto.securityDto;

import lombok.Data;

@Data
public class RegistrationRequest {
    private String name;
    private String email;
    private String password;
}
