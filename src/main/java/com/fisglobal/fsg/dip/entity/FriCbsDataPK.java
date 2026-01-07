package com.fisglobal.fsg.dip.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

import com.fisglobal.fsg.dip.core.utils.Base_VO;

public class FriCbsDataPK extends Base_VO implements Serializable {
	
	private String mobileNo;
	
	private  String cif;
	
	private String accountNo;
	
	private String uuid;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getCif() {
		return cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	
}
