package com.rentalapp.validators;

import com.rentalapp.models.User;
import com.rentalapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
@RequiredArgsConstructor
public class UserValidator implements org.springframework.validation.Validator {

    private final UserRepository userRepository;

    // Определяет, поддерживает ли валидатор класс User
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    // Основной метод валидации объекта User
    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        // Проверка, существует ли пользователь с таким email
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            errors.rejectValue("email", "error.user",
                    "Користувач з такою електронною поштою вже існує");
        }

        // Проверка длины пароля
        if (user.getPassword().length() < 8) {
            errors.rejectValue("password", "error.user",
                    "Пароль повинен містити не менше 8 символів");
        }

        // Проверка длины имени пользователя
        if (user.getFirstName().length() < 2) {
            errors.rejectValue("firstName", "error.user",
                    "Ім'я повинно містити не менше 2 символів");
        }

        // Проверка длины фамилии пользователя
        if (user.getLastName().length() < 2) {
            errors.rejectValue("lastName", "error.user",
                    "Прізвище повинно містити не менше 2 символів");
        }
    }
}