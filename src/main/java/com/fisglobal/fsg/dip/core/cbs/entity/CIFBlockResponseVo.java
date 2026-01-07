package com.fisglobal.fsg.dip.core.cbs.entity;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;

public class CIFBlockResponseVo extends Base_VO{
	
	@SerializedName("CIFBlocking_Response")
	private CIFBlockingResponse cIFBlockingResponse;
	
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

	@SerializedName("CIFBlocking_Response")
	public CIFBlockingResponse getCIFBlockingResponse() {
	return cIFBlockingResponse;
	}
	
	@SerializedName("CIFBlocking_Response")
	public void setCIFBlockingResponse(CIFBlockingResponse cIFBlockingResponse) {
	this.cIFBlockingResponse = cIFBlockingResponse;
	}


}
