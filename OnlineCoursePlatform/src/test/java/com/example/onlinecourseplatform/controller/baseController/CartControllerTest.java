package com.example.onlinecourseplatform.controller.baseController;

import com.example.onlinecourseplatform.dto.baseDTO.CartDTO;
import com.example.onlinecourseplatform.exception.ResourceNotFoundException;
import com.example.onlinecourseplatform.model.baseEntity.Cart;
import com.example.onlinecourseplatform.service.baseService.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CartControllerTest {

    @InjectMocks
    private CartController cartController;

    @Mock
    private CartService cartService;

    private CartDTO cartDTO;
    private UUID cartId;
    private UUID userId;
    private UUID courseId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cartId = UUID.randomUUID();
        userId = UUID.randomUUID();
        courseId = UUID.randomUUID();
        cartDTO = new CartDTO();
        cartDTO.setId(cartId);
        
    }

    @Test
    @WithMockUser(roles = "USER")
    void purchaseAllCourses_ShouldReturnOk() {
        ResponseEntity<Void> response = cartController.purchaseAllCourses(userId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(cartService, times(1)).purchaseAllCoursesInCart(userId);
    }

    @Test
    @WithMockUser(roles = "USER")
    void purchaseAllCourses_ShouldReturnNotFound() {
        doThrow(ResourceNotFoundException.class).when(cartService).purchaseAllCoursesInCart(userId);
        ResponseEntity<Void> response = cartController.purchaseAllCourses(userId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @WithMockUser(roles = "USER")
    void purchaseAllCourses_ShouldReturnPaymentRequired() {
        doThrow(IllegalStateException.class).when(cartService).purchaseAllCoursesInCart(userId);
        ResponseEntity<Void> response = cartController.purchaseAllCourses(userId);
        assertEquals(HttpStatus.PAYMENT_REQUIRED, response.getStatusCode());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateCart_ShouldReturnUpdatedCart() throws ResourceNotFoundException {
        when(cartService.update(any(UUID.class), any(CartDTO.class))).thenReturn(cartDTO);

        ResponseEntity<CartDTO> response = cartController.update(cartId, cartDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cartDTO, response.getBody());
        verify(cartService, times(1)).update(cartId, cartDTO);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteCart_ShouldReturnNoContent() {
        ResponseEntity<Void> response = cartController.delete(cartId);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(cartService, times(1)).deleteById(cartId);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void createCart_ShouldReturnCreatedCart() {
        when(cartService.save(any(CartDTO.class))).thenReturn(cartDTO);

        ResponseEntity<CartDTO> response = cartController.create(cartDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(cartDTO, response.getBody());
        verify(cartService, times(1)).save(cartDTO);
    }

    @Test
    @WithMockUser(roles = "USER")
    void addCourseToCart_ShouldReturnOk() {
        ResponseEntity<Void> response = cartController.addCourseToCart(userId, courseId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(cartService, times(1)).addCourseToCart(userId, courseId);
    }
}
