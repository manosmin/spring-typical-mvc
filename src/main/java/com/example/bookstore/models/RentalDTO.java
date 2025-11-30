package com.example.bookstore.models;

import com.example.bookstore.enums.RentalStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RentalDTO {
    private Long id;
    private BookDTO book;
    private UserDTO owner;
    private UserDTO renter;
    private LocalDateTime rentalDate;
    private LocalDateTime returnDate;
    private RentalStatus status;
}
