package com.fisglobal.fsg.dip.core.cbs.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fisglobal.fsg.dip.core.utils.Base_VO;

public class MobileEnquiryErrorResponse extends Base_VO{

	@JsonProperty("metadata")
	private MobileEnquiryMetaData metadata;
	@JsonProperty("additionalinfo")
	private MobileEnquiryAdditionalinfo additionalinfo;
	@JsonProperty("metadata")
	public MobileEnquiryMetaData getMetadata() {
		return metadata;
	}
	@JsonProperty("metadata")
	public void setMetadata(MobileEnquiryMetaData metadata) {
		this.metadata = metadata;
	}
	@JsonProperty("additionalinfo")
	public MobileEnquiryAdditionalinfo getAdditionalinfo() {
		return additionalinfo;
	}
	@JsonProperty("additionalinfo")
	public void setAdditionalinfo(MobileEnquiryAdditionalinfo additionalinfo) {
		this.additionalinfo = additionalinfo;
	}

}
