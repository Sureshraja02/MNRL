package com.fisglobal.fsg.dip.req.res.entity.V2;

import java.util.Map;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;

public class MnrlAtrResponsePayloadV2 extends Base_VO {

	@SerializedName("response_code")
	private String responseCode;

	@SerializedName("message")
	private String message;

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

	@SerializedName("ErrorIn")
	public void setErrorIn(Map<String, String> errorIn) {
		this.errorIn = errorIn;
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
