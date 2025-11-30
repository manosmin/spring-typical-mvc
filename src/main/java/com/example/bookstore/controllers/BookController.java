package com.example.bookstore.controllers;

import com.example.bookstore.entities.User;
import com.example.bookstore.models.BookDTO;
import com.example.bookstore.services.BookService;
import com.example.bookstore.services.RentalService;
import com.example.bookstore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final UserService userService;
    private final RentalService rentalService;

    @Autowired
    public BookController(BookService bookService, UserService userService, RentalService rentalService) {
        this.bookService = bookService;
        this.userService = userService;
        this.rentalService = rentalService;
    }

    @GetMapping
    public String listBooks(
            @RequestParam(required = false, defaultValue = "all") String view,
            @AuthenticationPrincipal OidcUser principal,
            Model model) {
        User currentUser = userService.getOrCreateUser(principal);
        List<BookDTO> books = view.equals("user") ? bookService.getMyBooks(currentUser) : bookService.getAllBooks();

        Map<Long, Boolean> rentedBooks = new HashMap<>();
        for (BookDTO book : books) {
            rentedBooks.put(book.getId(), rentalService.isBookRented(book.getId()));
        }

        model.addAttribute("books", books);
        model.addAttribute("rentedBooks", rentedBooks);
        model.addAttribute("currentUserId", currentUser.getId());
        model.addAttribute("isMyBooks", view.equals("user"));
        return "books";
    }

    @GetMapping("/new")
    public String newBookForm(Model model) {
        model.addAttribute("book", new BookDTO());
        model.addAttribute("isEdit", false);
        return "form";
    }

    @GetMapping("/edit/{id}")
    public String editBookForm(
            @AuthenticationPrincipal OidcUser principal,
            @PathVariable Long id,
            Model model) {
        User currentUser = userService.getOrCreateUser(principal);
        BookDTO book = bookService.getMyBooks(currentUser).stream()
                .filter(b -> b.getId().equals(id))
                .findFirst()
                .orElseThrow();
        model.addAttribute("book", book);
        model.addAttribute("isEdit", true);
        return "form";
    }

    @PostMapping("/save")
    public String saveBook(
            @AuthenticationPrincipal OidcUser principal,
            @ModelAttribute BookDTO bookDTO) {
        User currentUser = userService.getOrCreateUser(principal);
        if (bookDTO.getId() == null) {
            bookService.createBook(bookDTO, currentUser);
        } else {
            bookService.updateBook(bookDTO.getId(), bookDTO, currentUser);
        }
        return "redirect:/books?view=user";
    }

    @PostMapping("/delete/{id}")
    public String deleteBook(
            @AuthenticationPrincipal OidcUser principal,
            @PathVariable Long id) {
        User currentUser = userService.getOrCreateUser(principal);
        bookService.deleteBook(id, currentUser);
        return "redirect:/books?view=user";
    }
}
