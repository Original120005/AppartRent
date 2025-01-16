package com.rentalapp.services;


import com.rentalapp.models.User;
import com.rentalapp.models.UserRole;
import com.rentalapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationService {

    private final UserRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(UserRepository peopleRepository, PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Метод для регистрации нового пользователя
    @Transactional
    public void register(User person) {
        // Хеширование пароля перед сохранением
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        // Установка начального рейтинга пользователя
        person.setRating(0.0f);
        // Назначение роли "Tenant" по умолчанию
        person.setRole(UserRole.ROLE_TENANT);

        // Сохранение пользователя в базе данных
        peopleRepository.save(person);
    }
}

