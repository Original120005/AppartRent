package com.rentalapp.models;

public class Review {
	private int id;
	private int tenantId;
	private int propertyId;
	private int rating;
	private String comment;
	
	public Review(int id, int tenantId, int propertyId, int rating, String comment) {
		this.id = id;
		this.tenantId = tenantId;
		this.propertyId = propertyId;
		this.rating = rating;
		this.comment = comment;
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

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}