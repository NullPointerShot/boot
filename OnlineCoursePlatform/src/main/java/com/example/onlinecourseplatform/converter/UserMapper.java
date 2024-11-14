package com.example.onlinecourseplatform.converter;

import com.example.onlinecourseplatform.dto.baseDTO.UserDTO;
import com.example.onlinecourseplatform.model.baseEntity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<User, UserDTO> {
}
