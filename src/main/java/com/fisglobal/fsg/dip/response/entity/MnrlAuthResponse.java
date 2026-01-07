package com.fisglobal.fsg.dip.response.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fisglobal.fsg.dip.entity.BaseDTO;

public class MnrlAuthResponse extends BaseDTO{
	
	@JsonProperty("token")
	private String token;

	@JsonProperty("token")
	public String getToken() {
		return token;
	}
	
	@JsonProperty("token")
	public void setToken(String token) {
		this.token = token;
	}
	
	
	
	

}
