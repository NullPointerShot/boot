package com.example.onlinecourseplatform.service.baseService;

import com.example.onlinecourseplatform.converter.UserMapper;
import com.example.onlinecourseplatform.dto.baseDTO.UserDTO;
import com.example.onlinecourseplatform.exception.ResourceNotFoundException;
import com.example.onlinecourseplatform.model.baseEntity.User;
import com.example.onlinecourseplatform.model.enumEntity.UserRole;
import com.example.onlinecourseplatform.repository.UserRepository;
import com.example.onlinecourseplatform.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService extends BaseService<User, UUID, UserDTO> {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        super(userRepository, userMapper);
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserDTO save(UserDTO dto) {
        User user = entityMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = jpaRepository.save(user);
        return entityMapper.toDTO(savedUser);
    }

    public Optional<UserDTO> findByName(String name) {
        return userRepository.findByName(name)
                .map(entityMapper::toDTO);
    }

    public List<UserDTO> findByRole(UserRole role) {
        return userRepository.findAllByRole(role).stream()
                .map(userMapper::toDTO) 
                .collect(Collectors.toList());
    }

    public UserDTO updatePassword(UUID userId, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return userMapper.toDTO(user);
    }

}
