package com.rentalapp.controllers;

import com.rentalapp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalModelAdvice {

    private final UserService userService;

    @ModelAttribute("isAuthenticated")
    public boolean isAuthenticated() {
        return userService.isUserAuthenticated();
    }

    @ModelAttribute("userId")
    public Long userId() {
        return userService.getAuthenticatedUserId();
    }
}
