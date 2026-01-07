package com.fisglobal.fsg.dip.fri.entity;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;

public class FRIFetchDataRequest extends Base_VO {

	@SerializedName("Body")
	private FRIBody body;

	@SerializedName("Headers")
	private FRIHeaders headers;

	@SerializedName("Body")
	public FRIBody getBody() {
		return body;
	}

	@SerializedName("Body")
	public void setBody(FRIBody body) {
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
