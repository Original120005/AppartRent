package com.rentalapp.validators;

import com.rentalapp.models.User;
import com.rentalapp.repositories.UserRepository;;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class UserValidator implements org.springframework.validation.Validator {
    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        // Валідація email
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            errors.rejectValue("email", "error.user",
                    "Користувач з такою електронною поштою вже існує");
        }

        // Валідація паролю
        if (user.getPassword().length() < 8) {
            errors.rejectValue("password", "error.user",
                    "Пароль повинен містити не менше 8 символів");
        }

        // Валідація імені
        if (user.getFirstName().length() < 2) {
            errors.rejectValue("firstName", "error.user",
                    "Ім'я повинно містити не менше 2 символів");
        }

        // Валідація прізвища
        if (user.getLastName().length() < 2) {
            errors.rejectValue("lastName", "error.user",
                    "Прізвище повинно містити не менше 2 символів");
        }
    }
}