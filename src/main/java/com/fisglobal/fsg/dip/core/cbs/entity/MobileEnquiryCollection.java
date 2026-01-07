package com.fisglobal.fsg.dip.core.cbs.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fisglobal.fsg.dip.core.utils.Base_VO;

public class MobileEnquiryCollection extends Base_VO {

	@JsonProperty("CIF_No")
	private List<String> CIF_No;
	
	@JsonProperty("Account_No")
	private List<String> Account_No;
	

	@JsonProperty("CIF_No")
	public List<String> getCIFNo() {
		return CIF_No;
	}

	@JsonProperty("CIF_No")
	public void setCIFNo(List<String> cIFNo) {
		this.CIF_No = cIFNo;
	}

	@JsonProperty("Account_No")
	public List<String> getAccountNo() {
		return Account_No;
	}

	@JsonProperty("Account_No")
	public void setAccountNo(List<String> accountNo) {
		this.Account_No = accountNo;
	}

}
