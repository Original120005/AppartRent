package com.rentalapp.models;

import lombok.Getter;

@Getter
public enum UserRole {
	ROLE_TENANT("TENANT"),
	ROLE_LANDLORD("LANDLORD"),
	ROLE_ADMIN("ADMIN");

	private final String value;

	UserRole(String value) {
		this.value = value;
	}
}