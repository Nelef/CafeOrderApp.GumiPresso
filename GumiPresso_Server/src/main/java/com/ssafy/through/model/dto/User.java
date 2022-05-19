package com.ssafy.through.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "User : 유저 정보", description = "유저 상세 정보")
public class User {
	@ApiModelProperty(value="아이디")
	private String id;
	@ApiModelProperty(value="이름")
	private String name;
	@ApiModelProperty(value="암호")
	private String pass;
	@ApiModelProperty(value="스탬프")
	private Integer stamps;

	public User() {
	}
	
	

	
	public User(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}




	public User(String id, Integer stamps) {
		super();
		this.id = id;
		this.stamps = stamps;
	}


	public User(String id, String name, String pass, Integer stamps) {
		super();
		this.id = id;
		this.name = name;
		this.pass = pass;
		this.stamps = stamps;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public Integer getStamps() {
		return stamps;
	}

	public void setStamps(Integer stamps) {
		this.stamps = stamps;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", pass=");
		builder.append(pass);
		builder.append(", stamps=");
		builder.append(stamps);
		builder.append("]");
		return builder.toString();
	}
	
}
