package com.rentalapp.models;

import java.time.LocalDateTime;

public class Report {
	private int id;
	private int adminId;
	private String content;
	private LocalDateTime reportDate;
	
	public Report(int id, int adminId, String content, LocalDateTime reportDate) {
		this.id = id;
		this.adminId = adminId;
		this.content = content;
		this.reportDate = reportDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getReportDate() {
		return reportDate;
	}

	public void setReportDate(LocalDateTime reportDate) {
		this.reportDate = reportDate;
	}
}
