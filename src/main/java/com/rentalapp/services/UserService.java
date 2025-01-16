package com.rentalapp.services;

import com.rentalapp.security.PersonDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    // Метод для проверки, авторизован ли текущий пользователь
    public boolean isUserAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            System.out.println("Authentication is null"); // Логирование при отсутствии аутентификации
            return false;
        }
        System.out.println("Authentication: " + authentication); // Логирование текущей аутентификации
        // Проверяем, что пользователь аутентифицирован и не является "anonymousUser"
        return authentication.isAuthenticated() &&
                !(authentication.getPrincipal() instanceof String && authentication.getPrincipal().equals("anonymousUser"));
    }

    // Метод для получения ID текущего аутентифицированного пользователя
    public Long getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                // Предполагается, что UserDetails реализует PersonDetails с доступом к ID пользователя
                return ((PersonDetails) principal).getPerson().getId();
            }
        }
        return null; // Возвращает null, если пользователь не аутентифицирован
    }

    // Закомментированные методы для тестирования
    // public boolean isUserAuthenticated() {
    //     return true; // Для тестирования возвращает, что пользователь аутентифицирован
    // }
    //
    // public Long getAuthenticatedUserId() {
    //     return 1L; // Тестовый ID пользователя
    // }
}
