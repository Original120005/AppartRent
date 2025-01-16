package com.rentalapp.controllers;

import com.rentalapp.Exception.UserAlreadyExistsException;
import com.rentalapp.models.User;
import com.rentalapp.services.RegistrationService;
import com.rentalapp.validators.UserValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final RegistrationService registrationService;
    private final UserValidator userValidator;

    // Добавление валидатора для объекта "person" при привязке данных
    @InitBinder("person")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(userValidator);
    }

    // Обработчик для страницы входа
    @GetMapping("/login")
    public String loginPage(@ModelAttribute("person") User person) {
        return "auth/login"; // Возвращает шаблон страницы логина
    }

    // Обработчик для выхода из системы
    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            // Логаут через SecurityContextLogoutHandler
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/home?logout"; // Перенаправление на главную страницу после выхода
    }

    // Обработчик для отображения страницы регистрации
    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("person", new User()); // Добавление пустого объекта User для формы
        return "auth/registration"; // Возвращает шаблон страницы регистрации
    }

    // Обработчик для регистрации нового пользователя
    @PostMapping("/registration")
    public String registerUser(@ModelAttribute("person") @Valid User person,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            // Если есть ошибки валидации, остаемся на странице регистрации
            return "auth/registration";
        }

        try {
            // Попытка регистрации пользователя
            registrationService.register(person);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Реєстрація успішна! Будь ласка, увійдіть в систему.");
            return "redirect:/home"; // Перенаправление на главную страницу
        } catch (UserAlreadyExistsException e) {
            // Если пользователь с таким email уже существует, возвращаем ошибку
            bindingResult.rejectValue("email", "error.user",
                    "Користувач з такою електронною поштою вже існує");
            return "auth/registration"; // Остаемся на странице регистрации
        }
    }
}