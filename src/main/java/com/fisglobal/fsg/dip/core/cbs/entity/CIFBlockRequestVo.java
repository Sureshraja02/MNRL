package com.fisglobal.fsg.dip.core.cbs.entity;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;

public class CIFBlockRequestVo extends Base_VO {

	@SerializedName("Mobile_Number")
	private String mobileNumber;

	@SerializedName("Mobile_Number")
	public String getMobileNumber() {
		return mobileNumber;
	}

	@SerializedName("Mobile_Number")
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

}
