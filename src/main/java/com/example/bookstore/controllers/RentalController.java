package com.example.bookstore.controllers;

import com.example.bookstore.entities.User;
import com.example.bookstore.models.RentalDTO;
import com.example.bookstore.services.RentalService;
import com.example.bookstore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/rentals")
public class RentalController {

    private final RentalService rentalService;
    private final UserService userService;

    @Autowired
    public RentalController(RentalService rentalService, UserService userService) {
        this.rentalService = rentalService;
        this.userService = userService;
    }

    @GetMapping
    public String listRentals(
            @RequestParam(required = false, defaultValue = "rented") String view,
            @AuthenticationPrincipal OidcUser principal,
            Model model) {
        User currentUser = userService.getOrCreateUser(principal);
        List<RentalDTO> rentals;
        if (view.equals("rented-out")) {
            rentals = rentalService.getRentedOutBooks(currentUser);
        } else {
            rentals = rentalService.getRentedBooks(currentUser);
        }
        model.addAttribute("rentals", rentals);
        model.addAttribute("currentView", view);
        model.addAttribute("currentUserId", currentUser.getId());
        return "rentals";
    }

    @PostMapping("/rent/{bookId}")
    public String rentBook(
            @AuthenticationPrincipal OidcUser principal,
            @PathVariable Long bookId) {
        User currentUser = userService.getOrCreateUser(principal);
        rentalService.rentBook(bookId, currentUser);
        return "redirect:/books";
    }

    @PostMapping("/return/{rentalId}")
    public String returnBook(
            @AuthenticationPrincipal OidcUser principal,
            @PathVariable Long rentalId) {
        User currentUser = userService.getOrCreateUser(principal);
        rentalService.returnBook(rentalId, currentUser);
        return "redirect:/rentals";
    }
}
