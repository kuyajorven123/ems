package com.project.ems.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.project.ems.model.UserRepository;

import org.springframework.ui.Model;

@ControllerAdvice
public class GlobalControllerAdvice {

    private final UserRepository userRepository;

    public GlobalControllerAdvice(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @ModelAttribute
    public void addLoggedInUser(Model model, Authentication authentication) {

        if (authentication != null && authentication.isAuthenticated()) {

            userRepository.findByEmail(authentication.getName())
                    .ifPresent(user -> model.addAttribute("loggedInUser", user));
        }
    }
}
