package com.example.onlinecourseplatform.controller.baseController;

import com.example.onlinecourseplatform.dto.baseDTO.CartItemDTO;
import com.example.onlinecourseplatform.exception.ResourceNotFoundException;
import com.example.onlinecourseplatform.model.baseEntity.CartItem;
import com.example.onlinecourseplatform.service.baseService.CartItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;


import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CartItemControllerTest {

    @InjectMocks
    private CartItemController cartItemController;

    @Mock
    private CartItemService cartItemService;

    private CartItemDTO cartItemDTO;
    private UUID cartItemId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cartItemId = UUID.randomUUID();
        cartItemDTO = new CartItemDTO();
        cartItemDTO.setId(cartItemId);
        
    }

    @Test
    @WithMockUser(roles = "USER")
    void addCartItem_ShouldReturnCreatedCartItem() {
        when(cartItemService.save(any(CartItemDTO.class))).thenReturn(cartItemDTO);

        ResponseEntity<CartItemDTO> response = cartItemController.addCartItem(cartItemDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(cartItemDTO, response.getBody());
        verify(cartItemService, times(1)).save(cartItemDTO);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateCartItem_ShouldReturnUpdatedCartItem() throws ResourceNotFoundException {
        when(cartItemService.update(eq(cartItemId), any(CartItemDTO.class))).thenReturn(cartItemDTO);

        ResponseEntity<CartItemDTO> response = cartItemController.update(cartItemId, cartItemDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cartItemDTO, response.getBody());
        verify(cartItemService, times(1)).update(eq(cartItemId), any(CartItemDTO.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteCartItem_ShouldReturnNoContent() {
        ResponseEntity<Void> response = cartItemController.delete(cartItemId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(cartItemService, times(1)).deleteById(cartItemId);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateCartItem_ShouldThrowException_WhenResourceNotFound() throws ResourceNotFoundException {
        when(cartItemService.update(eq(cartItemId), any(CartItemDTO.class))).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> {
            cartItemController.update(cartItemId, cartItemDTO);
        });
        verify(cartItemService, times(1)).update(eq(cartItemId), any(CartItemDTO.class));
    }
}
