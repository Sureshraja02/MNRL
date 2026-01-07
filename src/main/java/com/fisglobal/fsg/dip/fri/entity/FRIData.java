package com.fisglobal.fsg.dip.fri.entity;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;

public class FRIData extends Base_VO {

	@SerializedName("mobile_no")
	private String mobileNo;

	@SerializedName("lsa_id")
	private String lsaId;

	@SerializedName("tsp_id")
	private String tspId;

	@SerializedName("fri")
	private String fri;
	
	@SerializedName("status")
	private String status;
	
	@SerializedName("status")
	public String getStatus() {
		return status;
	}

	@SerializedName("status")
	public void setStatus(String status) {
		this.status = status;
	}

	@SerializedName("mobile_no")
	public String getMobileNo() {
		return mobileNo;
	}

	@SerializedName("mobile_no")
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	@SerializedName("lsa_id")
	public String getLsaId() {
		return lsaId;
	}

	@SerializedName("lsa_id")
	public void setLsaId(String lsaId) {
		this.lsaId = lsaId;
	}

	@SerializedName("tsp_id")
	public String getTspId() {
		return tspId;
	}

	@SerializedName("tsp_id")
	public void setTspId(String tspId) {
		this.tspId = tspId;
	}

	@SerializedName("fri")
	public String getFri() {
		return fri;
	}

	@SerializedName("fri")
	public void setFri(String fri) {
		this.fri = fri;
	}

}
