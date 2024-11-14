package com.example.onlinecourseplatform.controller.baseController;

import com.example.onlinecourseplatform.controller.BaseController;
import com.example.onlinecourseplatform.dto.baseDTO.CartItemDTO;
import com.example.onlinecourseplatform.exception.ResourceNotFoundException;
import com.example.onlinecourseplatform.model.baseEntity.CartItem;
import com.example.onlinecourseplatform.service.baseService.CartItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/cart-items")
public class CartItemController extends BaseController<CartItem, UUID, CartItemDTO> {

    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        super(cartItemService);
        this.cartItemService = cartItemService;
    }

    
    
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/add")
    public ResponseEntity<CartItemDTO> addCartItem(@RequestBody CartItemDTO cartItemDTO) {
        CartItemDTO savedCartItem = cartItemService.save(cartItemDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCartItem);
    }

    
    
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    @Override
    public ResponseEntity<CartItemDTO> update(@PathVariable UUID id, @RequestBody CartItemDTO dto) throws ResourceNotFoundException {
        return super.update(id, dto);
    }

    
    
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        return super.delete(id);
    }
}
