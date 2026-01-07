package com.fisglobal.fsg.dip.fri.entity;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;

public class FRIHeaders extends Base_VO {

	@SerializedName("Authorization")
	private String authorization;

	@SerializedName("Authorization")
	public String getAuthorization() {
		return authorization;
	}

	@SerializedName("Authorization")
	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}

}
