package com.rentalapp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")  // Назва колонки в snake_case
	private Long id;

	@Column(name = "first_name", nullable = false)  // Вказуємо nullable = false, якщо поле обов'язкове
	private String firstName;

	@Column(name = "last_name", nullable = false)
	private String lastName;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "password_hash", nullable = false)
	private String password;

	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false)
	private UserRole role;

	@Column(name = "rating")
	private float rating;

	@OneToMany(mappedBy = "landlord", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Property> properties = new ArrayList<>();
}
