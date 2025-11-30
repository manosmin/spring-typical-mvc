package com.example.bookstore.mappers;

import com.example.bookstore.entities.User;
import com.example.bookstore.models.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    UserDTO toDto(User entity);

    User toEntity(UserDTO dto);
}
