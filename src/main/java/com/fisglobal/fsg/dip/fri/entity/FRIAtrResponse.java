package com.fisglobal.fsg.dip.fri.entity;

import java.util.Map;

import javax.annotation.Generated;

import com.fisglobal.fsg.dip.core.cbs.entity.ErrorResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class FRIAtrResponse {

	@SerializedName("response_code")
	private String responseCode;
	
	@SerializedName("message")
	private String message;
	
	@SerializedName("ErrorResponse")
	private ErrorResponse errorResponse;
	
	@SerializedName("RequestId")
	private String requestId;

	@SerializedName("ErrorIn")
	private Map<String, String> errorIn;

	@SerializedName("RequestId")
	public String getRequestId() {
		return requestId;
	}

	@SerializedName("RequestId")
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	@SerializedName("ErrorIn")
	public Map<String, String> getErrorIn() {
		return errorIn;
	}

	@SerializedName("ErrorResponse")
	public ErrorResponse getErrorResponse() {
		return errorResponse;
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
