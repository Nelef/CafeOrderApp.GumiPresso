package com.ssafy.through.model.dto;

public class DateDTO {
	private String year;
	private String month;
	private String day;
	public DateDTO(String year, String month, String day) {
		super();
		this.year = year;
		this.month = month;
		this.day = day;
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
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DateDTO [year=");
		builder.append(year);
		builder.append(", month=");
		builder.append(month);
		builder.append(", day=");
		builder.append(day);
		builder.append("]");
		return builder.toString();
	}
	
}
