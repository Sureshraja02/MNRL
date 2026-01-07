package com.fisglobal.fsg.dip.core.cbs.entity;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;

public class CIFAccountEnqRequest extends Base_VO {

	@SerializedName("Body")
	private Body body;

	@SerializedName("Body")
	public Body getBody() {
		return body;
	}

	@SerializedName("Body")
	public void setBody(Body body) {
		this.body = body;
	}

}
