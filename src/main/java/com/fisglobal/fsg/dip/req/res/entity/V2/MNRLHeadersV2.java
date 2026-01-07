package com.fisglobal.fsg.dip.req.res.entity.V2;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;

public class MNRLHeadersV2 extends Base_VO {

	@SerializedName("Authorization")
	private String Authorization;

	@SerializedName("Authorization")
	public String getAuthorization() {
		return Authorization;
	}

	@SerializedName("Authorization")
	public void setAuthorization(String authorization) {
		Authorization = authorization;
	}

}
