package com.example.onlinecourseplatform.service.baseService;

import com.example.onlinecourseplatform.converter.UserMapper;
import com.example.onlinecourseplatform.dto.baseDTO.UserDTO;
import com.example.onlinecourseplatform.exception.ResourceNotFoundException;
import com.example.onlinecourseplatform.model.baseEntity.User;
import com.example.onlinecourseplatform.model.enumEntity.UserRole;
import com.example.onlinecourseplatform.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UUID userId;
    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userId = UUID.randomUUID();
        user = new User();
        user.setId(userId);
        userDTO = new UserDTO();
        userDTO.setId(userId);
        userDTO.setPassword("plainPassword");
    }

    @Test
    void testSave() {
        
        when(userMapper.toEntity(userDTO)).thenReturn(user);
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDTO(user)).thenReturn(userDTO);

        
        UserDTO savedUserDTO = userService.save(userDTO);

        
        assertNotNull(savedUserDTO);
        assertEquals(userId, savedUserDTO.getId());
        verify(userRepository).save(user);
        verify(passwordEncoder).encode(user.getPassword());
    }

    @Test
    void testFindByName_Success() {
        String userName = "Test User";
        user.setName(userName);
        userDTO.setName(userName);

        
        when(userRepository.findByName(userName)).thenReturn(Optional.of(user));
        when(userMapper.toDTO(user)).thenReturn(userDTO);

        
        Optional<UserDTO> foundUserDTO = userService.findByName(userName);

        
        assertTrue(foundUserDTO.isPresent());
        assertEquals(userName, foundUserDTO.get().getName());
        verify(userRepository).findByName(userName);
    }

    @Test
    void testFindByName_NotFound() {
        String userName = "Non-existent User";

        
        when(userRepository.findByName(userName)).thenReturn(Optional.empty());

        
        Optional<UserDTO> foundUserDTO = userService.findByName(userName);

        
        assertFalse(foundUserDTO.isPresent());
        verify(userRepository).findByName(userName);
    }

    @Test
    void testFindByRole_Success() {
        UserRole role = UserRole.USER;
        user.setRole(role);
        userDTO.setRole(String.valueOf(role));

        
        when(userRepository.findAllByRole(role)).thenReturn(List.of(user));
        when(userMapper.toDTO(user)).thenReturn(userDTO);

        
        List<UserDTO> usersByRole = userService.findByRole(role);

        
        assertNotNull(usersByRole);
        assertEquals(1, usersByRole.size());
        assertEquals(role, usersByRole.get(0).getRole());
        verify(userRepository).findAllByRole(role);
    }

    @Test
    void testUpdatePassword_Success() {
        String newPassword = "newPassword";

        
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(newPassword)).thenReturn("encodedNewPassword");

        
        UserDTO updatedUserDTO = userService.updatePassword(userId, newPassword);

        
        assertNotNull(updatedUserDTO);
        assertEquals(userId, updatedUserDTO.getId());
        verify(userRepository).save(user);
        assertEquals("encodedNewPassword", user.getPassword());
    }

    @Test
    void testUpdatePassword_NotFound() {
        String newPassword = "newPassword";

        
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        
        assertThrows(ResourceNotFoundException.class, () -> userService.updatePassword(userId, newPassword));
        verify(userRepository, never()).save(any());
    }
}
