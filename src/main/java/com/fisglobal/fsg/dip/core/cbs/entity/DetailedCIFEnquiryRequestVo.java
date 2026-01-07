package com.fisglobal.fsg.dip.core.cbs.entity;

import java.math.BigInteger;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;

public class DetailedCIFEnquiryRequestVo extends Base_VO {

	@SerializedName("CIF_Number")
	private BigInteger cIFNumber;

	@SerializedName("CIF_Number")
	public BigInteger getcIFNumber() {
		return cIFNumber;
	}

	@SerializedName("CIF_Number")
	public void setcIFNumber(BigInteger cIFNumber) {
		this.cIFNumber = cIFNumber;
	}

}
