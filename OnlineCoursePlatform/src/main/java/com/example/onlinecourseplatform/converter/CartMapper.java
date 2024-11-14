package com.example.onlinecourseplatform.converter;

import com.example.onlinecourseplatform.dto.baseDTO.CartDTO;
import com.example.onlinecourseplatform.model.baseEntity.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper extends EntityMapper<Cart, CartDTO> {
}
