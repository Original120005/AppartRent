package com.rentalapp.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    // Получает имя текущего пользователя (username) из контекста безопасности
    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        return null; // Возвращает null, если пользователь не аутентифицирован
    }

    // Получает объект PersonDetails текущего пользователя из контекста безопасности
    public PersonDetails getCurrentPersonDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof PersonDetails) {
            return (PersonDetails) authentication.getPrincipal();
        }
        return null; // Возвращает null, если пользователь не аутентифицирован или объект не является PersonDetails
    }
}
