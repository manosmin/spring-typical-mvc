package com.example.bookstore.services;

import com.example.bookstore.entities.Book;
import com.example.bookstore.entities.User;
import com.example.bookstore.mappers.BookMapper;
import com.example.bookstore.models.BookDTO;
import com.example.bookstore.repositories.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Autowired
    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Cacheable("books")
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
            .map(bookMapper::toDto)
            .toList();
    }

    @Cacheable(value = "userBooks", key = "#currentUser.id")
    public List<BookDTO> getMyBooks(User currentUser) {
        return bookRepository.findByUser(currentUser).stream()
            .map(bookMapper::toDto)
            .toList();
    }

    @CacheEvict(value = {"books", "userBooks"}, allEntries = true)
    public void createBook(BookDTO bookDTO, User currentUser) {
        Book book = bookMapper.toEntity(bookDTO);
        book.setUser(currentUser);
        Book savedBook = bookRepository.save(book);
        log.info("Book with id {} has been created by user {}", savedBook.getId(), currentUser.getId());
    }

    @CacheEvict(value = {"books", "userBooks"}, allEntries = true)
    public void updateBook(Long id, BookDTO bookDTO, User currentUser) {
        Book existingBook = bookRepository.findById(id).orElseThrow();
        bookMapper.updateEntityFromDto(bookDTO, existingBook);
        bookRepository.save(existingBook);
        log.info("Book with id {} has been updated by user {}", id, currentUser.getId());
    }

    @CacheEvict(value = {"books", "userBooks"}, allEntries = true)
    public void deleteBook(Long id, User currentUser) {
        Book existingBook = bookRepository.findById(id).orElseThrow();
        bookRepository.delete(existingBook);
        log.info("Book with id {} has been deleted by user {}", id, currentUser.getId());
    }
}
