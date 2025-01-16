package com.rentalapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rentalapp.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String username);
}
