package com.fisglobal.fsg.dip.core.cbs.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fisglobal.fsg.dip.core.utils.Base_VO;

public class MobileEnquiryAdditionalinfo extends Base_VO{

	@JsonProperty("excepCode")
	private String excepCode;
	@JsonProperty("excepText")
	private String excepText;
	@JsonProperty("excepMetaData")
	private String excepMetaData;

	@JsonProperty("excepCode")
	public String getExcepCode() {
		return excepCode;
	}
	@JsonProperty("excepCode")
	public void setExcepCode(String excepCode) {
		this.excepCode = excepCode;
	}
	@JsonProperty("excepText")
	public String getExcepText() {
		return excepText;
	}
	@JsonProperty("excepText")
	public void setExcepText(String excepText) {
		this.excepText = excepText;
	}
	@JsonProperty("excepMetaData")
	public String getExcepMetaData() {
		return excepMetaData;
	}
	@JsonProperty("excepMetaData")
	public void setExcepMetaData(String excepMetaData) {
		this.excepMetaData = excepMetaData;
	}

}
