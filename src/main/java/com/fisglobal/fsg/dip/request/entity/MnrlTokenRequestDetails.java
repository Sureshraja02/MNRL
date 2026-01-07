package com.fisglobal.fsg.dip.request.entity;

import com.fisglobal.fsg.dip.core.utils.Base_VO;

public class MnrlTokenRequestDetails extends Base_VO{
	
	private String email;
	
	private String secureterm;
	
	private String bankId;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSecureterm() {
		return secureterm;
	}

	public void setSecureterm(String secureterm) {
		this.secureterm = secureterm;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	
	

}
