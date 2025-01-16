package com.rentalapp.controllers;

import com.rentalapp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    // Обработчик для отображения профиля пользователя
    @GetMapping("/profile/{id}")
    public String showProfile(@PathVariable("id") int id, Authentication authentication) {
        // Проверяем, авторизован ли пользователь
        if (authentication == null) {
            System.out.println("User not authenticated"); // Логирование при отсутствии авторизации
            return "redirect:/auth/login"; // Перенаправление на страницу логина
        }

        // Логирование ролей и имени текущего пользователя
        System.out.println("User roles: " + authentication.getAuthorities());
        System.out.println("User name: " + authentication.getName());

        return "user/profile"; // Возвращаем шаблон профиля пользователя
    }
}