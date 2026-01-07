package com.fisglobal.fsg.dip.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "REACTIVATED_DATA")
@Entity
public class ReactivatedData_DAO {

	@Column(name = "REACTIVATED_REQ_DATA_ID")
	private String reactivatedReqDataId;

	//@Id
	@Column(name = "MOBILE_NO")
	private String mobileNo;
	
	@Column(name = "DATE_OF_REACTIVATION")
	private String dateOfReactivation;

	@Column(name = "REACTIVATION_DESCRIPTION")
	private String reactivationDescription;

	@Column(name = "REACTIVATION_ID")
	private int reactivationId;

	@Column(name = "PROCESS_FLAG")
	private String processFlag;
	
	@Column(name = "FETCH_DATE")
	private String fetchDate;
	
	@Id
	@Column(name = "UUID")
	private String uuid;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getFetchDate() {
		return fetchDate;
	}

	public void setFetchDate(String fetchDate) {
		this.fetchDate = fetchDate;
	}

	public String getReactivatedReqDataId() {
		return reactivatedReqDataId;
	}

	public void setReactivatedReqDataId(String reactivatedReqDataId) {
		this.reactivatedReqDataId = reactivatedReqDataId;
	}

	public String getProcessFlag() {
		return processFlag;
	}

	public void setProcessFlag(String processFlag) {
		this.processFlag = processFlag;
	}

	
	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getDateOfReactivation() {
		return dateOfReactivation;
	}

	public void setDateOfReactivation(String dateOfReactivation) {
		this.dateOfReactivation = dateOfReactivation;
	}

	public String getReactivationDescription() {
		return reactivationDescription;
	}

	public void setReactivationDescription(String reactivationDescription) {
		this.reactivationDescription = reactivationDescription;
	}

	public int getReactivationId() {
		return reactivationId;
	}

	public void setReactivationId(int reactivationId) {
		this.reactivationId = reactivationId;
	}

	

}
