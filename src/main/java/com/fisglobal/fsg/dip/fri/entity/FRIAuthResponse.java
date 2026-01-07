package com.fisglobal.fsg.dip.fri.entity;

import javax.annotation.Generated;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;

public class FRIAuthResponse extends Base_VO {

	@SerializedName("token")
	private String token;

	@SerializedName("token")
	public String getToken() {
		return token;
	}

	@SerializedName("token")
	public void setToken(String token) {
		this.token = token;
	}

}
