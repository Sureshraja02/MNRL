package com.fisglobal.fsg.dip.core.cbs.entity;

import java.math.BigInteger;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;


public class AccountEnquiryRequestVo extends Base_VO{

	@SerializedName("CIF_Account_Number")
	private BigInteger cIFAccountNumber;
	

	@SerializedName("CIF_Account_Number")
	public BigInteger getCIFAccountNumber() {
		return cIFAccountNumber;
	}

	@SerializedName("CIF_Account_Number")
	public void setCIFAccountNumber(BigInteger accountNumber) {
		this.cIFAccountNumber = accountNumber;
	}

	

}
