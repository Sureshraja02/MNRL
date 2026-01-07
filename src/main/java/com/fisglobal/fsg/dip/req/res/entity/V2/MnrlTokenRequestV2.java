package com.fisglobal.fsg.dip.req.res.entity.V2;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;

public class MnrlTokenRequestV2 extends Base_VO {

	@SerializedName("Body")
	private MnrlAuthBodyV2 body;

	@SerializedName("Body")
	public MnrlAuthBodyV2 getBody() {
		return body;
	}

	@SerializedName("Body")
	public void setBody(MnrlAuthBodyV2 body) {
		this.body = body;
	}

}
