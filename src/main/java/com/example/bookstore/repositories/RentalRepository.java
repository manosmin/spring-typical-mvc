package com.example.bookstore.repositories;

import com.example.bookstore.entities.Rental;
import com.example.bookstore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {

    @Query("SELECT r FROM Rental r WHERE r.owner = :user")
    List<Rental> findRentalsByOwner(@Param("user") User user);

    @Query("SELECT r FROM Rental r WHERE r.renter = :user")
    List<Rental> findRentalsByRenter(@Param("user") User user);

    @Query("SELECT r FROM Rental r WHERE r.book.id = :bookId AND r.status = 'ACTIVE'")
    Optional<Rental> findActiveRentalByBookId(@Param("bookId") Long bookId);
}
