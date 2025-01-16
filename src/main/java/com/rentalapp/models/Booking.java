package com.rentalapp.models;

import java.time.LocalDate;

public class Booking {
	private int id;
	private int tenantId;
	private int propertyId;
	private LocalDate startDate;
	private LocalDate endDate;
	private String status;

	public Booking(int id, int tenantId, int propertyId, LocalDate startDate, LocalDate endDate, String status) {
		this.id = id;
		this.tenantId = tenantId;
		this.propertyId = propertyId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
	}

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
