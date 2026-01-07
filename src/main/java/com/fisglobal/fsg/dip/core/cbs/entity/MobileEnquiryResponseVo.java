package com.fisglobal.fsg.dip.core.cbs.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MobileEnquiryResponseVo extends Base_VO {

	@JsonProperty("CIF_Account_Enq_Response")
	private CIFAccountEnqResponse CIF_Account_Enq_Response;
	
	@JsonProperty("ErrorResponse")
	private MobileEnquiryErrorResponse ErrorResponse;
	
	private String errorMessage;

	@JsonProperty("ErrorResponse")
	public MobileEnquiryErrorResponse getErrorResponse() {
		return ErrorResponse;
	}

	@JsonProperty("ErrorResponse")
	public void setErrorResponse(MobileEnquiryErrorResponse errorResponse) {
		this.ErrorResponse = errorResponse;
	}

	@JsonProperty("CIF_Account_Enq_Response")
	public CIFAccountEnqResponse getCIFAccountEnqResponse() {
		return CIF_Account_Enq_Response;
	}

	@JsonProperty("CIF_Account_Enq_Response")
	public void setCIFAccountEnqResponse(CIFAccountEnqResponse cIFAccountEnqResponse) {
		this.CIF_Account_Enq_Response = cIFAccountEnqResponse;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	

}
