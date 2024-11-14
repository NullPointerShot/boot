package com.example.onlinecourseplatform.service.securityService;

import com.example.onlinecourseplatform.model.baseEntity.User;
import com.example.onlinecourseplatform.dto.securityDto.RegistrationRequest;
import com.example.onlinecourseplatform.repository.UserRepository;
import com.example.onlinecourseplatform.service.securityService.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterUser_Success() {
        
        RegistrationRequest request = new RegistrationRequest();
        request.setEmail("test@example.com");
        request.setPassword("password");

        
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");

        
        String result = authService.registerUser(request);

        
        assertEquals("Регистрация прошла успешно!", result); 
        verify(userRepository, times(1)).save(any(User.class));
    }


    @Test
    public void testRegisterUser_EmailAlreadyExists() {
        
        RegistrationRequest request = new RegistrationRequest();
        request.setEmail("test@example.com");
        request.setPassword("password");

        
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(new User()));

        
        try {
            authService.registerUser(request);
        } catch (Exception e) {
            assertEquals("Email is already taken", e.getMessage());
        }

        verify(userRepository, never()).save(any(User.class));
    }

    
}
