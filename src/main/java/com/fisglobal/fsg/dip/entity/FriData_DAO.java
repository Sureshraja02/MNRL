package com.fisglobal.fsg.dip.entity;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Table(name = "FRI_DATA")
@Entity
public class FriData_DAO {
	
	@Column(name = "FRI_REQ_DATA_ID")
	private String mnrlReqDataId;

	//@Id
	@Column(name = "MOBILE_NO")
	private String mobileNo;

	@Column(name = "LSAID")
	private String lsaId;
	
	@Column(name = "LSANAME")
	private String lsaName;

	@Column(name = "TSPID")
	private String tspId;
	
	@Column(name = "TSPNAME")
	private String tspName;
	
	@Column(name = "FRI_INDICATOR")
	private String friIndicator;
	
	@Column(name = "PROCESS_FLAG")
	private String processFlag;
	
	@Column(name = "FETCH_DATE")
	private String fetchDate;
	
	@Column(name = "FRI_INDICATOR_DESC")
	private String friIndicatorDesc;
	
	@Column(name="status")
	private String status;
	
	@Id
	@Column(name = "UUID")
	private String uuid;
	
	@Column(name = "MBL_ENQ_RESPONSE")
	private String mobileEnquiryResponse;
	
	//@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MBL_ENQ_DATETIME",columnDefinition = "TIMESTAMP")
	private LocalDateTime mobileEnquiryDateTime;

	public LocalDateTime getMobileEnquiryDateTime() {
		return mobileEnquiryDateTime;
	}

	public void setMobileEnquiryDateTime(LocalDateTime mobileEnquiryDateTime) {
		this.mobileEnquiryDateTime = mobileEnquiryDateTime;
	}

	public String getMobileEnquiryResponse() {
		return mobileEnquiryResponse;
	}

	public void setMobileEnquiryResponse(String mobileEnquiryResponse) {
		this.mobileEnquiryResponse = mobileEnquiryResponse;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getFriIndicatorDesc() {
		return friIndicatorDesc;
	}

	public void setFriIndicatorDesc(String friIndicatorDesc) {
		this.friIndicatorDesc = friIndicatorDesc;
	}

	public String getProcessFlag() {
		return processFlag;
	}

	public void setProcessFlag(String processFlag) {
		this.processFlag = processFlag;
	}

	public String getLsaName() {
		return lsaName;
	}

	public void setLsaName(String lsaName) {
		this.lsaName = lsaName;
	}

	public String getTspName() {
		return tspName;
	}

	public void setTspName(String tspName) {
		this.tspName = tspName;
	}


	public String getMnrlReqDataId() {
		return mnrlReqDataId;
	}

	public void setMnrlReqDataId(String mnrlReqDataId) {
		this.mnrlReqDataId = mnrlReqDataId;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getLsaId() {
		return lsaId;
	}

	public void setLsaId(String lsaId) {
		this.lsaId = lsaId;
	}

	public String getTspId() {
		return tspId;
	}

	public void setTspId(String tspId) {
		this.tspId = tspId;
	}

	public String getFriIndicator() {
		return friIndicator;
	}

	public void setFriIndicator(String friIndicator) {
		this.friIndicator = friIndicator;
	}

	public String getFetchDate() {
		return fetchDate;
	}

	public void setFetchDate(String fetchDate) {
		this.fetchDate = fetchDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}




}
