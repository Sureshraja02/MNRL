package com.fisglobal.fsg.dip.request.entity;

public class MnrlAtrData_VO {
	private String mobile_no;
	private String action_taken;
	private String investigation_details;
	private String date_of_action;
	private String disconnectionreason_id;
	private String grievance_received;

	public String getMobile_no() {
		return mobile_no;
	}

	public void setMobile_no(String mobile_no) {
		this.mobile_no = mobile_no;
	}

	public String getAction_taken() {
		return action_taken;
	}

	public void setAction_taken(String action_taken) {
		this.action_taken = action_taken;
	}

	public String getInvestigation_details() {
		return investigation_details;
	}

	public void setInvestigation_details(String investigation_details) {
		this.investigation_details = investigation_details;
	}

	public String getDate_of_action() {
		return date_of_action;
	}

	public void setDate_of_action(String date_of_action) {
		this.date_of_action = date_of_action;
	}

	public String getDisconnectionreason_id() {
		return disconnectionreason_id;
	}

	public void setDisconnectionreason_id(String disconnectionreason_id) {
		this.disconnectionreason_id = disconnectionreason_id;
	}

	public String getGrievance_received() {
		return grievance_received;
	}

	public void setGrievance_received(String grievance_received) {
		this.grievance_received = grievance_received;
	}

}
