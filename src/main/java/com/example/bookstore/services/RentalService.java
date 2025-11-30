package com.example.bookstore.services;

import com.example.bookstore.entities.Book;
import com.example.bookstore.entities.Rental;
import com.example.bookstore.entities.User;
import com.example.bookstore.enums.RentalStatus;
import com.example.bookstore.exceptions.RentalException;
import com.example.bookstore.mappers.RentalMapper;
import com.example.bookstore.models.RentalDTO;
import com.example.bookstore.repositories.BookRepository;
import com.example.bookstore.repositories.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final BookRepository bookRepository;
    private final RentalMapper rentalMapper;

    @Autowired
    public RentalService(RentalRepository rentalRepository, BookRepository bookRepository, RentalMapper rentalMapper) {
        this.rentalRepository = rentalRepository;
        this.bookRepository = bookRepository;
        this.rentalMapper = rentalMapper;
    }

    @Transactional
    public void rentBook(Long bookId, User renter) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));
        if (book.getUser().getId().equals(renter.getId())) {
            throw new RentalException("You cannot rent your own book");
        }
        rentalRepository.findActiveRentalByBookId(bookId)
                .ifPresent(rental -> {
                    throw new RentalException("This book is already rented");
                });
        Rental rental = new Rental();
        rental.setBook(book);
        rental.setOwner(book.getUser());
        rental.setRenter(renter);
        rental.setStatus(RentalStatus.ACTIVE);
        rentalRepository.save(rental);
    }

    @Transactional
    public void returnBook(Long rentalId, User user) {
        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new IllegalArgumentException("Rental not found"));
        if (!rental.getOwner().getId().equals(user.getId()) &&
            !rental.getRenter().getId().equals(user.getId())) {
            throw new RentalException("You are not authorized to return this rental");
        }
        if (rental.getStatus() == RentalStatus.RETURNED) {
            throw new RentalException("This book has already been returned");
        }
        rental.setStatus(RentalStatus.RETURNED);
        rental.setReturnDate(LocalDateTime.now());
        rentalRepository.save(rental);
    }

    public List<RentalDTO> getRentedOutBooks(User owner) {
        return rentalRepository.findRentalsByOwner(owner).stream()
                .map(rentalMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<RentalDTO> getRentedBooks(User renter) {
        return rentalRepository.findRentalsByRenter(renter).stream()
                .map(rentalMapper::toDTO)
                .collect(Collectors.toList());
    }

    public boolean isBookRented(Long bookId) {
        return rentalRepository.findActiveRentalByBookId(bookId).isPresent();
    }
}
