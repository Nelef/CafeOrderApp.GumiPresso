package com.ssafy.through.model.dto;

public class Banner {
	private int id;
	private String img;
	private String url;
	public Banner() {
		super();
	}
	public Banner(int id, String img, String url) {
		super();
		this.id = id;
		this.img = img;
		this.url = url;
	}
	public Banner(String img, String url) {
		super();
		this.img = img;
		this.url = url;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Banner [id=");
		builder.append(id);
		builder.append(", img=");
		builder.append(img);
		builder.append(", url=");
		builder.append(url);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
