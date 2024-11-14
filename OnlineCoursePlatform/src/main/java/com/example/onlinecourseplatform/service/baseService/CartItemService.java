package com.example.onlinecourseplatform.service.baseService;

import com.example.onlinecourseplatform.converter.CartItemMapper;
import com.example.onlinecourseplatform.dto.baseDTO.CartItemDTO;
import com.example.onlinecourseplatform.model.baseEntity.CartItem;
import com.example.onlinecourseplatform.repository.CartItemRepository;
import com.example.onlinecourseplatform.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CartItemService extends BaseService<CartItem, UUID, CartItemDTO> {

    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;

    public CartItemService(CartItemRepository cartItemRepository, CartItemMapper cartItemMapper) {
        super(cartItemRepository, cartItemMapper);
        this.cartItemRepository = cartItemRepository;
        this.cartItemMapper = cartItemMapper;
    }

    
}
