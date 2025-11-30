package com.example.bookstore.models;

import lombok.Data;

@Data
public class BookDTO {
    private Long id;
    private String title;
    private String author;
    private String description;
    private Integer year;
    private UserDTO user;
}
