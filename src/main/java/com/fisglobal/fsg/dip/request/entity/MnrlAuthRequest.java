package com.fisglobal.fsg.dip.request.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fisglobal.fsg.dip.entity.BaseDTO;

public class MnrlAuthRequest extends BaseDTO {
	

	@JsonProperty("email")
	private String email;

	@JsonProperty("password")
	private String password;

	@JsonProperty("email")
	public String getEmail() {
		return email;
	}

	@JsonProperty("email")
	public void setEmail(String email) {
		this.email = email;
	}

	@JsonProperty("password")
	public String getPassword() {
		return password;
	}

	@JsonProperty("password")
	public void setPassword(String password) {
		this.password = password;
	}

}
