package com.fisglobal.fsg.dip.core.cbs.entity;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;

public class MobileRemovalResponseVo extends Base_VO {

	@SerializedName("KYCUpdate_Response")
	private KYCUpdateResponse kYCUpdateResponse;
	
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

	@SerializedName("KYCUpdate_Response")
	public KYCUpdateResponse getKYCUpdateResponse() {
		return kYCUpdateResponse;
	}

	@SerializedName("KYCUpdate_Response")
	public void setKYCUpdateResponse(KYCUpdateResponse kYCUpdateResponse) {
		this.kYCUpdateResponse = kYCUpdateResponse;
	}

}
