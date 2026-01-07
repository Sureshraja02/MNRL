package com.fisglobal.fsg.dip.response.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fisglobal.fsg.dip.entity.BaseDTO;

public class MnrlDecryptDataVo extends BaseDTO{
	
	@JsonProperty("mobile_no")
	private String mobile_no;
	
	@JsonProperty("lsa_id")
	private String lsa_id;
	
	@JsonProperty("tsp_id")
	private String tsp_id;
	
	@JsonProperty("date_of_disconnection")
	private String date_of_disconnection;
	
	@JsonProperty("disconnectionreason_id")
	private String disconnectionreason_id;
	
	@JsonProperty("date_of_reactivation")
	private String date_of_reactivation;
	
	public String getDate_of_reactivation() {
		return date_of_reactivation;
	}

	public void setDate_of_reactivation(String date_of_reactivation) {
		this.date_of_reactivation = date_of_reactivation;
	}

	public int getReactivation_id() {
		return reactivation_id;
	}

	public void setReactivation_id(int reactivation_id) {
		this.reactivation_id = reactivation_id;
	}

	@JsonProperty("reactivation_id")
	private int reactivation_id;

	public String getMobile_no() {
		return mobile_no;
	}

	public void setMobile_no(String mobile_no) {
		this.mobile_no = mobile_no;
	}

	public String getLsa_id() {
		return lsa_id;
	}

	public void setLsa_id(String lsa_id) {
		this.lsa_id = lsa_id;
	}

	public String getTsp_id() {
		return tsp_id;
	}

	public void setTsp_id(String tsp_id) {
		this.tsp_id = tsp_id;
	}

	public String getDate_of_disconnection() {
		return date_of_disconnection;
	}

	public void setDate_of_disconnection(String date_of_disconnection) {
		this.date_of_disconnection = date_of_disconnection;
	}

	public String getDisconnectionreason_id() {
		return disconnectionreason_id;
	}

	public void setDisconnectionreason_id(String disconnectionreason_id) {
		this.disconnectionreason_id = disconnectionreason_id;
	}

	

	
	
}
