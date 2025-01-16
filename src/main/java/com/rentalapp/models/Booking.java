package com.rentalapp.models;

import java.time.LocalDate;

public class Booking {
	private int id; // Уникальный идентификатор бронирования
	private int tenantId; // Идентификатор арендатора, который сделал бронирование
	private int propertyId; // Идентификатор объекта недвижимости
	private LocalDate startDate; // Дата начала бронирования
	private LocalDate endDate; // Дата окончания бронирования
	private String status; // Статус бронирования (например, "Pending", "Approved", "Cancelled")

	// Конструктор для создания объекта Booking с указанием всех параметров
	public Booking(int id, int tenantId, int propertyId, LocalDate startDate, LocalDate endDate, String status) {
		this.id = id;
		this.tenantId = tenantId;
		this.propertyId = propertyId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
	}

	// Геттеры и сеттеры для доступа и изменения свойств бронирования

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTenantId() {
		return tenantId;
	}

	public void setTenantId(int tenantId) {
		this.tenantId = tenantId;
	}

	public int getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(int propertyId) {
		this.propertyId = propertyId;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
