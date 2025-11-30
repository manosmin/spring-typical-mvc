package com.example.bookstore.mappers;

import com.example.bookstore.models.BookDTO;
import com.example.bookstore.entities.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    uses = {UserMapper.class}
)
public interface BookMapper {

    BookDTO toDto(Book entity);

    @Mapping(target = "user", ignore = true)
    Book toEntity(BookDTO dto);

    @Mapping(target = "user", ignore = true)
    void updateEntityFromDto(BookDTO dto, @MappingTarget Book entity);
}