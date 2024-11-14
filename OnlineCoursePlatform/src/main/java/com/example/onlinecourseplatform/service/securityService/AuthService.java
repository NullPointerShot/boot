package com.example.onlinecourseplatform.service.securityService;

import  com.example.onlinecourseplatform.dto.securityDto.LoginRequest;
import com.example.onlinecourseplatform.dto.securityDto.RegistrationRequest;
import com.example.onlinecourseplatform.model.baseEntity.User;
import com.example.onlinecourseplatform.model.enumEntity.UserRole;
import com.example.onlinecourseplatform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String registerUser(RegistrationRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return "Пользователь с таким email уже существует";
        }

        User newUser = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.USER)
                .balance(BigDecimal.ZERO)
                .build();

        userRepository.save(newUser);
        return "Регистрация прошла успешно!";
    }

    public String loginUser(LoginRequest request) {
        
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());

        
        if (userOptional.isEmpty()) {
            return "Неправильный email. Пожалуйста, проверьте корректность и попробуйте снова.";
        }

        
        User user = userOptional.get();
        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return "Вход выполнен успешно!";
        } else {
            return "Неверный пароль. Пожалуйста, попробуйте снова.";
        }
    }

}
