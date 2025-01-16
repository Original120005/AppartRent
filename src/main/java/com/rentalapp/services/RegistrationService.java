package com.rentalapp.services;


import com.rentalapp.models.User;
import com.rentalapp.models.UserRole;
import com.rentalapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class RegistrationService {

    private final UserRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(UserRepository peopleRepository, PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Transactional
    public void register(User person){
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRating(0.0f);
        person.setRole(UserRole.ROLE_TENANT);

        peopleRepository.save(person);
    }
}
