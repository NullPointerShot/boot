package com.example.onlinecourseplatform.controller.securityController;

import com.example.onlinecourseplatform.security.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("securityUserController")
@RequestMapping("/user")
public class UserController {

    @GetMapping("/profile")
    public ResponseEntity<CustomUserDetails> getProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(userDetails);
    }
}
