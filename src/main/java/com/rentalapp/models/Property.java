package com.rentalapp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "properties")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Property {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "landlord_id", nullable = false)
	private User landlord;

	@Column(nullable = false, name = "title")
	private String title;

	@Column(nullable = false, columnDefinition = "TEXT", name = "description")
	private String description;

	@Column(nullable = false, name = "price")
	private float price;

	@Column(name = "residential_complex")  // ЖК
	private String residentialComplex;

	@Column(name = "district")  // Район (Приморский)
	private String district;

	@Column(name = "area_name")  // Мікрорайон (Аркадія)
	private String areaName; //TODO Добавити вивід цих полів і добавлення нових полів у форму

	@Column(nullable = false, name = "location")
	private String location;

	@Column(nullable = false, name = "status")
	private String status;

	@Column(nullable = false, name = "timePeriod")
	private String timePeriod;


	@Column(nullable = false, name = "housingType")
	private String housingType;

	@Column(nullable = false, name = "area")
	private float area;

	@Column(nullable = false, name = "floor")
	private int floor;

	@Column(nullable = false, name = "roomCount")
	private int roomCount;

	@Column(nullable = false, name = "rating")
	private float rating;

	@Column(nullable = false, name = "publicationDate")
	private LocalDate publicationDate;

	@OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PropertyImage> images = new ArrayList<>();
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(
			name = "property_tags",
			joinColumns = @JoinColumn(name = "property_id"),
			inverseJoinColumns = @JoinColumn(name = "tag_id")
	)
	private List<Tag> tags = new ArrayList<>();

	// Зручний метод для додавання тегів
	public void addTag(Tag tag) {
		tags.add(tag);
		tag.getProperties().add(this);
	}

	public void removeTag(Tag tag) {
		tags.remove(tag);
		tag.getProperties().remove(this);
	}
}
