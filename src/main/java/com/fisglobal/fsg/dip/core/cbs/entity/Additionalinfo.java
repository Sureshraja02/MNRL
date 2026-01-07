package com.fisglobal.fsg.dip.core.cbs.entity;

import javax.annotation.Generated;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Additionalinfo extends Base_VO{

	@SerializedName("excepCode")
	private String excepCode;
	@SerializedName("excepText")
	private String excepText;
	@SerializedName("excepMetaData")
	private String excepMetaData;

	@SerializedName("excepCode")
	public String getExcepCode() {
		return excepCode;
	}
	@SerializedName("excepCode")
	public void setExcepCode(String excepCode) {
		this.excepCode = excepCode;
	}
	@SerializedName("excepText")
	public String getExcepText() {
		return excepText;
	}
	@SerializedName("excepText")
	public void setExcepText(String excepText) {
		this.excepText = excepText;
	}
	@SerializedName("excepMetaData")
	public String getExcepMetaData() {
		return excepMetaData;
	}
	@SerializedName("excepMetaData")
	public void setExcepMetaData(String excepMetaData) {
		this.excepMetaData = excepMetaData;
	}

}
