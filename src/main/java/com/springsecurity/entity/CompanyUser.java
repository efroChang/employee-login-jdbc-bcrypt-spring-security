package com.springsecurity.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CompanyUser {

	@NotNull(message = "is required")
	@Size(min = 1, message = "too short")
	private String username;

	@NotNull(message = "is required")
	@Size(min = 1, message = "too short")
	private String password;

	public CompanyUser() {
	}

	public String getUserName() {
		return username;
	}

	public void setUserName(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
