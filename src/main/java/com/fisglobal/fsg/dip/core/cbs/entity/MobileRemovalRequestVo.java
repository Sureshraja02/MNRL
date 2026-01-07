package com.fisglobal.fsg.dip.core.cbs.entity;

import java.math.BigInteger;

import com.google.gson.annotations.SerializedName;

public class MobileRemovalRequestVo {

	@SerializedName("Customer_Number")
	private BigInteger customerNumber;

	@SerializedName("Country_Code")
	private String countryCode;

	@SerializedName("Mobile_Number")
	private String mobileNumber;

	@SerializedName("Mobile_Delete_Flag")
	private String mobileDeleteFlag;

	@SerializedName("Reason")
	private String reason;

	@SerializedName("Customer_Number")
	public BigInteger getCustomerNumber() {
		return customerNumber;
	}

	@SerializedName("Customer_Number")
	public void setCustomerNumber(BigInteger customerNumber) {
		this.customerNumber = customerNumber;
	}

	@SerializedName("Country_Code")
	public String getCountryCode() {
		return countryCode;
	}

	@SerializedName("Country_Code")
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	@SerializedName("Mobile_Number")
	public String getMobileNumber() {
		return mobileNumber;
	}

	@SerializedName("Mobile_Number")
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	@SerializedName("Mobile_Delete_Flag")
	public String getMobileDeleteFlag() {
		return mobileDeleteFlag;
	}

	@SerializedName("Mobile_Delete_Flag")
	public void setMobileDeleteFlag(String mobileDeleteFlag) {
		this.mobileDeleteFlag = mobileDeleteFlag;
	}

	@SerializedName("Reason")
	public String getReason() {
		return reason;
	}

	@SerializedName("Reason")
	public void setReason(String reason) {
		this.reason = reason;
	}

}
