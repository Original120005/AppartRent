package com.rentalapp.controllers;

import com.rentalapp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalModelAdvice {

    private final UserService userService;

    // Добавляет глобальный атрибут "isAuthenticated" для всех моделей
    @ModelAttribute("isAuthenticated")
    public boolean isAuthenticated() {
        // Проверяет, авторизован ли пользователь
        return userService.isUserAuthenticated();
    }

    // Добавляет глобальный атрибут "userId" для всех моделей
    @ModelAttribute("userId")
    public Long userId() {
        // Возвращает ID текущего авторизованного пользователя
        return userService.getAuthenticatedUserId();
    }
}
