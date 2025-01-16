package com.rentalapp.services;

import com.rentalapp.security.PersonDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public boolean isUserAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            System.out.println("Authentication is null");
            return false;
        }
        System.out.println("Authentication: " + authentication);
        return authentication.isAuthenticated() &&
                !(authentication.getPrincipal() instanceof String && authentication.getPrincipal().equals("anonymousUser"));
    }


    public Long getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                // Припустимо, що UserDetails реалізовано у вашій системі та містить ID.
                return ((PersonDetails) principal).getPerson().getId();
            }
        }
        return null;
    }
//    public boolean isUserAuthenticated() {
//        return true; // Для тестування повертає, що користувач увійшов
//    }
//
//    public Long getAuthenticatedUserId() {
//        return 1L; // Тестовий ID користувача
//    }
}
