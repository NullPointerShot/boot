package com.example.onlinecourseplatform.controller.securityController;

import com.example.onlinecourseplatform.dto.securityDto.LoginRequest;
import com.example.onlinecourseplatform.dto.securityDto.RegistrationRequest;
import com.example.onlinecourseplatform.security.CustomUserDetails;
import com.example.onlinecourseplatform.service.securityService.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegistrationRequest request) {
        return ResponseEntity.ok(authService.registerUser(request));
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.loginUser(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok("Вы успешно вышли из системы.");
    }

    @GetMapping("/profile")
    public ResponseEntity<CustomUserDetails> getProfile(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(userDetails);
    }
}
