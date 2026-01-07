package com.fisglobal.fsg.dip.response.entity;

import com.fisglobal.fsg.dip.core.cbs.entity.ErrorResponse;
import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;


public class MnrlAtrResponse extends Base_VO {

	@SerializedName("response_code")
	private String responseCode;
	
	@SerializedName("message")
	private String message;
	
	@SerializedName("ErrorResponse")
	private ErrorResponse errorResponse;

	@SerializedName("ErrorResponse")
	public ErrorResponse getErrorResponse() {
		return errorResponse;
	}

	@SerializedName("ErrorResponse")
	public void setErrorResponse(ErrorResponse errorResponse) {
		this.errorResponse = errorResponse;
	}

	@SerializedName("response_code")
	public String getResponseCode() {
		return responseCode;
	}

	@SerializedName("response_code")
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	@SerializedName("message")
	public String getMessage() {
		return message;
	}

	@SerializedName("message")
	public void setMessage(String message) {
		this.message = message;
	}

}
