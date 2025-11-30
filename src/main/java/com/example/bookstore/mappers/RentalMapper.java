package com.example.bookstore.mappers;

import com.example.bookstore.entities.Rental;
import com.example.bookstore.models.RentalDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {BookMapper.class, UserMapper.class})
public interface RentalMapper {

    @Mapping(source = "book", target = "book")
    @Mapping(source = "owner", target = "owner")
    @Mapping(source = "renter", target = "renter")
    RentalDTO toDTO(Rental rental);

    @Mapping(source = "book", target = "book")
    @Mapping(source = "owner", target = "owner")
    @Mapping(source = "renter", target = "renter")
    Rental toEntity(RentalDTO rentalDTO);
}
