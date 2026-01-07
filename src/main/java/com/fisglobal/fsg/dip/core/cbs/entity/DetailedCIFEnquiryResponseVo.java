package com.fisglobal.fsg.dip.core.cbs.entity;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;

public class DetailedCIFEnquiryResponseVo extends Base_VO {
	/*@SerializedName("DetailedCIFEnquiry_Response")
	private CIFAssociatedAccountEnquiryResponse cIFAssociatedAccountEnquiryResponse;

	@SerializedName("DetailedCIFEnquiry_Response")
	public CIFAssociatedAccountEnquiryResponse getcIFAssociatedAccountEnquiryResponse() {
		return cIFAssociatedAccountEnquiryResponse;
	}

	@SerializedName("DetailedCIFEnquiry_Response")
	public void setcIFAssociatedAccountEnquiryResponse(
			CIFAssociatedAccountEnquiryResponse cIFAssociatedAccountEnquiryResponse) {
		this.cIFAssociatedAccountEnquiryResponse = cIFAssociatedAccountEnquiryResponse;
	}*/
	
	@SerializedName("CustEnq_Response")
	private CustEnqResponse custEnqResponse;
	
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

	public CustEnqResponse getCustEnqResponse() {
	return custEnqResponse;
	}

	public void setCustEnqResponse(CustEnqResponse custEnqResponse) {
	this.custEnqResponse = custEnqResponse;
	}

}
