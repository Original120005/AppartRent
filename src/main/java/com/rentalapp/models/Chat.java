package com.rentalapp.models;

import java.util.List;

public class Chat {
	private int id;
	private int firstUserId;
	private int secondUserId;
	private List<Message> messages;

	public Chat(int id, int firstUserId, int secondUserId, List<Message> messages) {
		this.id = id;
		this.firstUserId = firstUserId;
		this.secondUserId = secondUserId;
		this.messages = messages;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFirstUserId() {
		return firstUserId;
	}

	public void setFirstUserId(int firstUserId) {
		this.firstUserId = firstUserId;
	}

	public int getSecondUserId() {
		return secondUserId;
	}

	public void setSecondUserId(int secondUserId) {
		this.secondUserId = secondUserId;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
}
