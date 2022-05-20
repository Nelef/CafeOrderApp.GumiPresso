package com.ssafy.through.model.dto;

public class Table {
	private int id;
	private boolean state;
	
	public Table() {
		super();
	}

	public Table(int id, boolean state) {
		super();
		this.id = id;
		this.state = state;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Table [id=");
		builder.append(id);
		builder.append(", state=");
		builder.append(state);
		builder.append("]");
		return builder.toString();
	}
	
	
}
