package com.example.bookstore.repositories;

import com.example.bookstore.entities.Book;
import com.example.bookstore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByUser(User user);

}
