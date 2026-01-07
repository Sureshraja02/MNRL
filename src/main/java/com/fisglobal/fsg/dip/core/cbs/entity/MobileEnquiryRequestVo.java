package com.fisglobal.fsg.dip.core.cbs.entity;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;

public class MobileEnquiryRequestVo extends Base_VO {

	@SerializedName("CIF_Account_Enq_Request")
	private CIFAccountEnqRequest cIFAccountEnqRequest;

	@SerializedName("CIF_Account_Enq_Request")
	public CIFAccountEnqRequest getCIFAccountEnqRequest() {
		return cIFAccountEnqRequest;
	}

	@SerializedName("CIF_Account_Enq_Request")
	public void setCIFAccountEnqRequest(CIFAccountEnqRequest cIFAccountEnqRequest) {
		this.cIFAccountEnqRequest = cIFAccountEnqRequest;
	}

}
