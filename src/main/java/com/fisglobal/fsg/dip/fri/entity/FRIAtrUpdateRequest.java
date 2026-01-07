package com.fisglobal.fsg.dip.fri.entity;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;


public class FRIAtrUpdateRequest extends Base_VO {

	@SerializedName("Body")
	private FRIAtrBody body;
	
	@SerializedName("Headers")
	private FRIHeaders headers;

	@SerializedName("Body")
	public FRIAtrBody getBody() {
		return body;
	}

	@SerializedName("Body")
	public void setBody(FRIAtrBody body) {
		this.body = body;
	}

	@SerializedName("Headers")
	public FRIHeaders getHeaders() {
		return headers;
	}

	@SerializedName("Headers")
	public void setHeaders(FRIHeaders headers) {
		this.headers = headers;
	}

}
