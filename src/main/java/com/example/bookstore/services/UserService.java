package com.example.bookstore.services;

import com.example.bookstore.entities.User;
import com.example.bookstore.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getOrCreateUser(OidcUser principal) {
        String subjectId = principal.getSubject();
        return userRepository.findByAuth0SubjectId(subjectId)
            .map(user -> getUserByAuth0SubjectId(subjectId))
            .orElseGet(() -> createUserFromPrincipal(principal));
    }

    private User createUserFromPrincipal(OidcUser principal) {
        User user = new User();
        user.setAuth0SubjectId(principal.getSubject());
        user.setName(principal.getFullName() != null ? principal.getFullName() : principal.getEmail());
        user.setEmail(principal.getEmail());
        user.setPicture(principal.getPicture());
        User savedUser = userRepository.save(user);
        log.info("Created new user with id {} for auth0 subject {}", savedUser.getId(), savedUser.getAuth0SubjectId());
        return savedUser;
    }

    public User getUserByAuth0SubjectId(String subjectId) {
        return userRepository.findByAuth0SubjectId(subjectId)
            .orElseThrow(() -> new RuntimeException("User not found for subject: " + subjectId));
    }
}
