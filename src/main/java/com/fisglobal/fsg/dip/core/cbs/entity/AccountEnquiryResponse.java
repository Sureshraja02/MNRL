package com.fisglobal.fsg.dip.core.cbs.entity;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;

public class AccountEnquiryResponse extends Base_VO {
	@SerializedName("CIFAssociatedAccountEnquiry_Response")
	private CIFAssociatedAccountEnquiryResponse cIFAssociatedAccountEnquiryResponse;
	
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

	@SerializedName("CIFAssociatedAccountEnquiry_Response")
	public CIFAssociatedAccountEnquiryResponse getcIFAssociatedAccountEnquiryResponse() {
		return cIFAssociatedAccountEnquiryResponse;
	}

	@SerializedName("CIFAssociatedAccountEnquiry_Response")
	public void setcIFAssociatedAccountEnquiryResponse(
			CIFAssociatedAccountEnquiryResponse cIFAssociatedAccountEnquiryResponse) {
		this.cIFAssociatedAccountEnquiryResponse = cIFAssociatedAccountEnquiryResponse;
	}

}
