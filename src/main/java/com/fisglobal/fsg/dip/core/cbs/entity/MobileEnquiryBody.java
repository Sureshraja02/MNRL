package com.fisglobal.fsg.dip.core.cbs.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;

public class MobileEnquiryBody extends Base_VO{
	
	@JsonProperty("Payload")
    private MobileEnquiryPayload Payload;

	@JsonProperty("Payload")
	public MobileEnquiryPayload getPayload() {
		return Payload;
	}

	@JsonProperty("Payload")
	public void setPayload(MobileEnquiryPayload payload) {
		this.Payload = payload;
	}

}
