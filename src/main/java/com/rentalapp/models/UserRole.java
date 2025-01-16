package com.rentalapp.models;

import lombok.Getter;

@Getter
public enum UserRole {
	// Роль арендатора (TENANT)
	ROLE_TENANT("TENANT"),
	// Роль арендодателя (LANDLORD)
	ROLE_LANDLORD("LANDLORD"),
	// Роль администратора (ADMIN)
	ROLE_ADMIN("ADMIN");

	// Значение роли в строковом формате
	private final String value;

	// Конструктор для установки строкового значения роли
	UserRole(String value) {
		this.value = value;
	}
}
