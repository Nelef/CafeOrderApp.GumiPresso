package com.ssafy.through.model.dto;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Product : 상품 정보", description = "상품 상세 정보")
public class Product {
	@ApiModelProperty(value="상품아이디")
	private Integer id;
	@ApiModelProperty(value="이름")
	private String name;
	@ApiModelProperty(value="타입")
	private String type;
	@ApiModelProperty(value="가격")
	private Integer price;
	@ApiModelProperty(value="이미지")
	private String img;	
	private List<Comment> comment;
	
	public Product() {
	}
	
	public Product(Integer id, String name, String type, Integer price, String img, List<Comment> comment) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.price = price;
		this.img = img;
		this.comment = comment;
	}

	public Product(Integer id, String name, String type, Integer price, String img) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.price = price;
		this.img = img;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Product [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", type=");
		builder.append(type);
		builder.append(", price=");
		builder.append(price);
		builder.append(", img=");
		builder.append(img);
		builder.append("]");
		return builder.toString();
	}
	
}
