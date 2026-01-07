package com.fisglobal.fsg.dip.core.cbs.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;

public class CIFAccountEnqResponse extends Base_VO {

	@JsonProperty("metadata")
	private MobileEnquiryMetaData metadata;
	
	@JsonProperty("Body")
	private MobileEnquiryBody Body;

	@JsonProperty("metadata")
	public MobileEnquiryMetaData getMetadata() {
		return metadata;
	}

	@JsonProperty("metadata")
	public void setMetadata(MobileEnquiryMetaData metadata) {
		this.metadata = metadata;
	}

	@JsonProperty("Body")
	public MobileEnquiryBody getBody() {
		return Body;
	}

	@JsonProperty("Body")
	public void setBody(MobileEnquiryBody body) {
		this.Body = body;
	}

	

}
