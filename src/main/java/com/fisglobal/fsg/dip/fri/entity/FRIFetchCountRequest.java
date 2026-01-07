package com.fisglobal.fsg.dip.fri.entity;

import javax.annotation.Generated;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class FRIFetchCountRequest extends Base_VO{

	@SerializedName("Body")
	private FRICountBody body;
	
	@SerializedName("Headers")
	private FRIHeaders headers;

	@SerializedName("Body")
	public FRICountBody getBody() {
		return body;
	}

	@SerializedName("Body")
	public void setBody(FRICountBody body) {
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
