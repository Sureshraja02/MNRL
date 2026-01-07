package com.fisglobal.fsg.dip.entity;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//@IdClass(FriCbsDataPK.class)
@Table(name = "FRI_CBS_DATA")
@Entity
public class FriCbsData_DAO {
	
	//@Id
	@Column(name = "MOBILE_NO")
	private String mobileNo;

	//@Id
	@Column(name = "CIF")
	private String cif;

	//@Id
	@Column(name = "ACC_NO")
	private String accountNo;

	@Column(name = "CUSTOMER_NAME")
	private String customerName;

	@Column(name = "OWNER_PERCENTAGE")
	private String owner_percentage;

	@Column(name = "MODES")
	private String mode;

	@Column(name = "AADHAR_REF_NO")
	private String aadharRefNo;

	@Column(name = "CIF_BLOCK_FLAG")
	private String cifBlockFlag;

	@Column(name = "DEBIT_FREEZE_FLAG")
	private String debitFreezeFlag;

	@Column(name = "MOBILE_REMOVAL_FLAG")
	private String mobileRemovalFlag;

	@Column(name = "CIF_ENQUIRY_FLAG")
	private String cifEnquiryFlag;

	@Column(name = "ACC_ENQUIRY_FLAG")
	private String accEnquiryFlag;

	@Column(name = "PROCESS_FLAG")
	private String processFlag;

	@Column(name = "PRIMARY_ACC_FLAG")
	private String primaryAccountFlag;

	@Column(name = "FRI_REASON")
	private String friReason;
	
	@Column(name = "ACTION_FLAG")
	private String actionFlag;
	
	@Column(name = "ATR_UPLOAD_FLAG")
	private String atrUploadFlag;
	
	@Column(name = "DEBIT_RESPONSE")
	private String debitResponse;
	
	@Column(name = "CIFBLK_RESPONSE")
	private String cifBlockResponse;
	
	@Column(name = "MBRVL_RESPONSE")
	private String mobileRemovalResponse;
	
	@Column(name = "ATR_RESPONSE")
	private String friAtrResponse;

	@Id
	@Column(name = "UUID")
	private String uuid;
	
	@Column(name = "DATA_UUID")
	private String dataUuid;
	
	//@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CIFBLK_DATETIME",columnDefinition = "TIMESTAMP")
	private LocalDateTime cifBlockDateTime;
	
	
	//@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ATR_DATETIME",columnDefinition = "TIMESTAMP")
	private LocalDateTime atrDateTime;

	public LocalDateTime getCifBlockDateTime() {
		return cifBlockDateTime;
	}

	public void setCifBlockDateTime(LocalDateTime cifBlockDateTime) {
		this.cifBlockDateTime = cifBlockDateTime;
	}

	public LocalDateTime getAtrDateTime() {
		return atrDateTime;
	}

	public void setAtrDateTime(LocalDateTime atrDateTime) {
		this.atrDateTime = atrDateTime;
	}

	public String getDataUuid() {
		return dataUuid;
	}

	public void setDataUuid(String dataUuid) {
		this.dataUuid = dataUuid;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getFriAtrResponse() {
		return friAtrResponse;
	}

	public void setFriAtrResponse(String friAtrResponse) {
		this.friAtrResponse = friAtrResponse;
	}

	public String getCifBlockResponse() {
		return cifBlockResponse;
	}

	public void setCifBlockResponse(String cifBlockResponse) {
		this.cifBlockResponse = cifBlockResponse;
	}

	public String getMobileRemovalResponse() {
		return mobileRemovalResponse;
	}

	public void setMobileRemovalResponse(String mobileRemovalResponse) {
		this.mobileRemovalResponse = mobileRemovalResponse;
	}

	public String getDebitResponse() {
		return debitResponse;
	}

	public void setDebitResponse(String debitResponse) {
		this.debitResponse = debitResponse;
	}

	public String getActionFlag() {
		return actionFlag;
	}

	public void setActionFlag(String actionFlag) {
		this.actionFlag = actionFlag;
	}

	public String getAtrUploadFlag() {
		return atrUploadFlag;
	}

	public void setAtrUploadFlag(String atrUploadFlag) {
		this.atrUploadFlag = atrUploadFlag;
	}

	
	public String getFriReason() {
		return friReason;
	}

	public void setFriReason(String friReason) {
		this.friReason = friReason;
	}

	public String getCifEnquiryFlag() {
		return cifEnquiryFlag;
	}

	public void setCifEnquiryFlag(String cifEnquiryFlag) {
		this.cifEnquiryFlag = cifEnquiryFlag;
	}

	public String getAccEnquiryFlag() {
		return accEnquiryFlag;
	}

	public void setAccEnquiryFlag(String accEnquiryFlag) {
		this.accEnquiryFlag = accEnquiryFlag;
	}

	public String getProcessFlag() {
		return processFlag;
	}

	public void setProcessFlag(String processFlag) {
		this.processFlag = processFlag;
	}

	public String getPrimaryAccountFlag() {
		return primaryAccountFlag;
	}

	public void setPrimaryAccountFlag(String primaryAccountFlag) {
		this.primaryAccountFlag = primaryAccountFlag;
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

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getOwner_percentage() {
		return owner_percentage;
	}

	public void setOwner_percentage(String owner_percentage) {
		this.owner_percentage = owner_percentage;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getAadharRefNo() {
		return aadharRefNo;
	}

	public void setAadharRefNo(String aadharRefNo) {
		this.aadharRefNo = aadharRefNo;
	}

	public String getCifBlockFlag() {
		return cifBlockFlag;
	}

	public void setCifBlockFlag(String cifBlockFlag) {
		this.cifBlockFlag = cifBlockFlag;
	}

	public String getDebitFreezeFlag() {
		return debitFreezeFlag;
	}

	public void setDebitFreezeFlag(String debitFreezeFlag) {
		this.debitFreezeFlag = debitFreezeFlag;
	}

	public String getMobileRemovalFlag() {
		return mobileRemovalFlag;
	}

	public void setMobileRemovalFlag(String mobileRemovalFlag) {
		this.mobileRemovalFlag = mobileRemovalFlag;
	}

}
