package com.example.onlinecourseplatform.controller.baseController;

import com.example.onlinecourseplatform.controller.BaseController;
import com.example.onlinecourseplatform.dto.baseDTO.CartDTO;
import com.example.onlinecourseplatform.exception.ResourceNotFoundException;
import com.example.onlinecourseplatform.model.baseEntity.Cart;
import com.example.onlinecourseplatform.service.baseService.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/carts")
public class CartController extends BaseController<Cart, UUID, CartDTO> {

    private final CartService cartService;

    public CartController(CartService cartService) {
        super(cartService);
        this.cartService = cartService;
    }

    
    @PostMapping("/{userId}/courses/{courseId}")
    public ResponseEntity<Void> addCourseToCart(@PathVariable UUID userId, @PathVariable UUID courseId) {
        try {
            cartService.addCourseToCart(userId, courseId);
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).build();
        }
    }

    
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{userId}/purchase")
    public ResponseEntity<Void> purchaseAllCourses(@PathVariable UUID userId) {
        try {
            cartService.purchaseAllCoursesInCart(userId);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body(null);
        }
    }

    
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    @Override
    public ResponseEntity<CartDTO> update(@PathVariable UUID id, @RequestBody CartDTO dto) throws ResourceNotFoundException {
        return super.update(id, dto);
    }

    
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        return super.delete(id);
    }

    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    @Override
    public ResponseEntity<CartDTO> create(@RequestBody CartDTO dto) {
        return super.create(dto);
    }
}
