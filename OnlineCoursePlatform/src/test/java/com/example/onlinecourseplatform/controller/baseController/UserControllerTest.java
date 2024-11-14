package com.example.onlinecourseplatform.controller.baseController;

import com.example.onlinecourseplatform.dto.baseDTO.UserDTO;
import com.example.onlinecourseplatform.exception.ResourceNotFoundException;
import com.example.onlinecourseplatform.model.enumEntity.UserRole;
import com.example.onlinecourseplatform.service.baseService.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private UserDTO userDTO;
    private UUID userId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userId = UUID.randomUUID();
        userDTO = new UserDTO();
        userDTO.setId(userId);
        
    }

    @Test
    void findByName_ShouldReturnUser_WhenUserExists() {
        when(userService.findByName(anyString())).thenReturn(Optional.of(userDTO));

        ResponseEntity<UserDTO> response = userController.findByName("existingUser");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDTO, response.getBody());
        verify(userService, times(1)).findByName("existingUser");
    }

    @Test
    void findByName_ShouldReturnNotFound_WhenUserDoesNotExist() {
        when(userService.findByName(anyString())).thenReturn(Optional.empty());

        ResponseEntity<UserDTO> response = userController.findByName("nonexistentUser");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userService, times(1)).findByName("nonexistentUser");
    }

    @Test
    void findByRole_ShouldReturnUsers_WhenUsersExist() {
        List<UserDTO> users = new ArrayList<>();
        users.add(userDTO);
        when(userService.findByRole(UserRole.ADMIN)).thenReturn(users);

        ResponseEntity<List<UserDTO>> response = userController.findByRole(UserRole.ADMIN);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
        verify(userService, times(1)).findByRole(UserRole.ADMIN);
    }

    @Test
    void findByRole_ShouldReturnNotFound_WhenNoUsersExist() {
        when(userService.findByRole(UserRole.USER)).thenReturn(new ArrayList<>());

        ResponseEntity<List<UserDTO>> response = userController.findByRole(UserRole.USER);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userService, times(1)).findByRole(UserRole.USER);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void create_ShouldReturnCreatedUser() {
        when(userService.save(any(UserDTO.class))).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = userController.create(userDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(userDTO, response.getBody());
        verify(userService, times(1)).save(userDTO);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void update_ShouldReturnUpdatedUser() throws ResourceNotFoundException {
        when(userService.update(any(UUID.class), any(UserDTO.class))).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = userController.update(userId, userDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDTO, response.getBody());
        verify(userService, times(1)).update(userId, userDTO);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void delete_ShouldReturnNoContent() {
        ResponseEntity<Void> response = userController.delete(userId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userService, times(1)).deleteById(userId);
    }
}
