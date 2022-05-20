package com.ssafy.through.model.dto;

import java.util.Date;

public class Sales {
	private String year;
	private String month;
	private String day;
	private String hour;
	private int total;
	private String type;
	public Sales() {
		super();
	}
	public Sales(String year, String month, String day, String hour, int total, String type) {
		super();
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.total = total;
		this.type = type;
	}
	public Sales(String year, String month, String day, String hour, int total) {
		super();
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.total = total;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Sales [year=");
		builder.append(year);
		builder.append(", month=");
		builder.append(month);
		builder.append(", day=");
		builder.append(day);
		builder.append(", hour=");
		builder.append(hour);
		builder.append(", total=");
		builder.append(total);
		builder.append(", type=");
		builder.append(type);
		builder.append("]");
		return builder.toString();
	}

	
	
	
}
