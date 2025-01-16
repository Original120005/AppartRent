package com.rentalapp.models;

public class MapService {
	private String apiKey;
	
	public MapService(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
}
