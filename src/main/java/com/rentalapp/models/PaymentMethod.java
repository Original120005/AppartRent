package com.rentalapp.models;

public class PaymentMethod {
	private String apiKey;
	
	public PaymentMethod(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
}
