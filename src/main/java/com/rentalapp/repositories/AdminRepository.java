package com.rentalapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rentalapp.models.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}