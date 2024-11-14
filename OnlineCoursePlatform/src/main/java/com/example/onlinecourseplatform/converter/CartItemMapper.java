package com.example.onlinecourseplatform.converter;

import com.example.onlinecourseplatform.dto.baseDTO.CartItemDTO;
import com.example.onlinecourseplatform.model.baseEntity.CartItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartItemMapper extends EntityMapper<CartItem, CartItemDTO> {
}
