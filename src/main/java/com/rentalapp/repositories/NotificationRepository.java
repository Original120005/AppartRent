package com.rentalapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rentalapp.models.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}