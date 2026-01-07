package com.fisglobal.fsg.dip.core.cbs.entity;

import java.util.List;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;

public class Collection extends Base_VO {

	@SerializedName("CIF_No")
	private List<String> cIFNo;
	
	@SerializedName("Account_No")
	private List<String> accountNo;
	
	@SerializedName("Acct_Number")
	private String acctNumber;
	
	@SerializedName("Acct_Name")
	private String acctName;
	
	@SerializedName("Acct_Status")
	private String acctStatus;
	
	@SerializedName("Acct_Type")
	private String acctType;
	
	@SerializedName("Acct_Category")
	private String acctCategory;
	
	@SerializedName("Acct_Description")
	private String acctDescription;
	
	@SerializedName("Acct_Currency")
	private String acctCurrency;
	
	@SerializedName("Total_Bal")
	private String totalBal;
	
	@SerializedName("Sanctioned_Amt")
	private String sanctionedAmt;
	
	@SerializedName("Available_Bal")
	private String availableBal;
	
	@SerializedName("Advance_Value")
	private String advanceValue;
	
	@SerializedName("Hold_Lein")
	private String holdLein;
	
	@SerializedName("Mode_of_Operation")
	private String modeOfOperation;
	
	@SerializedName("Account_Holder")
	private String accountHolder;
	
	@SerializedName("Aadhar_Seed_Flag")
	private String aadharSeedFlag;
	
	@SerializedName("Acct_Home_Branch_Number")
	private String acctHomeBranchNumber;
	
	@SerializedName("Link_Type")
	private String linkType;
	
	@SerializedName("Nominee_Flag")
	private String nomineeFlag;
	
	@SerializedName("Maturity_Date")
	private String maturityDate;
	
	@SerializedName("Maturity_Value")
	private String maturityValue;
	
	@SerializedName("ETDA_Flag")
	private String eTDAFlag;
	
	@SerializedName("TTL_Breach_Flag")
	private String tTLBreachFlag;
	
	@SerializedName("Actual_Advance_Amount")
	private String actualAdvanceAmount;
	
	@SerializedName("Additional_Approved_Amount")
	private String additionalApprovedAmount;
	
	@SerializedName("Additional_Advanced_Amount")
	private String additionalAdvancedAmount;
	
	@SerializedName("Account_Open_Date")
	private String accountOpenDate;
	
	@SerializedName("Loan_Term")
	private String loanTerm;
	
	@SerializedName("Limit_Expiry_Date")
	private String limitExpiryDate;
	
	@SerializedName("Mode_Of_Delivery")
	private String modeOfDelivery;

	@SerializedName("Acct_Number")
	public String getAcctNumber() {
	return acctNumber;
	}

	@SerializedName("Acct_Number")
	public void setAcctNumber(String acctNumber) {
	this.acctNumber = acctNumber;
	}

	@SerializedName("Acct_Name")
	public String getAcctName() {
	return acctName;
	}

	@SerializedName("Acct_Name")
	public void setAcctName(String acctName) {
	this.acctName = acctName;
	}

	@SerializedName("Acct_Status")
	public String getAcctStatus() {
	return acctStatus;
	}

	@SerializedName("Acct_Status")
	public void setAcctStatus(String acctStatus) {
	this.acctStatus = acctStatus;
	}

	@SerializedName("Acct_Type")
	public String getAcctType() {
	return acctType;
	}

	@SerializedName("Acct_Type")
	public void setAcctType(String acctType) {
	this.acctType = acctType;
	}

	@SerializedName("Acct_Category")
	public String getAcctCategory() {
	return acctCategory;
	}

	@SerializedName("Acct_Category")
	public void setAcctCategory(String acctCategory) {
	this.acctCategory = acctCategory;
	}
	
	@SerializedName("Acct_Description")
	public String getAcctDescription() {
	return acctDescription;
	}

	@SerializedName("Acct_Description")
	public void setAcctDescription(String acctDescription) {
	this.acctDescription = acctDescription;
	}

	@SerializedName("Acct_Currency")
	public String getAcctCurrency() {
	return acctCurrency;
	}

	@SerializedName("Acct_Currency")
	public void setAcctCurrency(String acctCurrency) {
	this.acctCurrency = acctCurrency;
	}

	@SerializedName("Total_Bal")
	public String getTotalBal() {
	return totalBal;
	}

	@SerializedName("Total_Bal")
	public void setTotalBal(String totalBal) {
	this.totalBal = totalBal;
	}

	@SerializedName("Sanctioned_Amt")
	public String getSanctionedAmt() {
	return sanctionedAmt;
	}

	@SerializedName("Sanctioned_Amt")
	public void setSanctionedAmt(String sanctionedAmt) {
	this.sanctionedAmt = sanctionedAmt;
	}

	@SerializedName("Available_Bal")
	public String getAvailableBal() {
	return availableBal;
	}

	@SerializedName("Available_Bal")
	public void setAvailableBal(String availableBal) {
	this.availableBal = availableBal;
	}

	@SerializedName("Advance_Value")
	public String getAdvanceValue() {
	return advanceValue;
	}

	@SerializedName("Advance_Value")
	public void setAdvanceValue(String advanceValue) {
	this.advanceValue = advanceValue;
	}

	@SerializedName("Hold_Lein")
	public String getHoldLein() {
	return holdLein;
	}
	
	@SerializedName("Hold_Lein")
	public void setHoldLein(String holdLein) {
	this.holdLein = holdLein;
	}

	@SerializedName("Mode_of_Operation")
	public String getModeOfOperation() {
	return modeOfOperation;
	}

	@SerializedName("Mode_of_Operation")
	public void setModeOfOperation(String modeOfOperation) {
	this.modeOfOperation = modeOfOperation;
	}

	@SerializedName("Account_Holder")
	public String getAccountHolder() {
	return accountHolder;
	}
	
	@SerializedName("Account_Holder")
	public void setAccountHolder(String accountHolder) {
	this.accountHolder = accountHolder;
	}

	@SerializedName("Aadhar_Seed_Flag")
	public String getAadharSeedFlag() {
	return aadharSeedFlag;
	}

	@SerializedName("Aadhar_Seed_Flag")
	public void setAadharSeedFlag(String aadharSeedFlag) {
	this.aadharSeedFlag = aadharSeedFlag;
	}

	@SerializedName("Acct_Home_Branch_Number")
	public String getAcctHomeBranchNumber() {
	return acctHomeBranchNumber;
	}

	@SerializedName("Acct_Home_Branch_Number")
	public void setAcctHomeBranchNumber(String acctHomeBranchNumber) {
	this.acctHomeBranchNumber = acctHomeBranchNumber;
	}

	@SerializedName("Link_Type")
	public String getLinkType() {
	return linkType;
	}

	@SerializedName("Link_Type")
	public void setLinkType(String linkType) {
	this.linkType = linkType;
	}

	@SerializedName("Nominee_Flag")
	public String getNomineeFlag() {
	return nomineeFlag;
	}

	@SerializedName("Nominee_Flag")
	public void setNomineeFlag(String nomineeFlag) {
	this.nomineeFlag = nomineeFlag;
	}

	@SerializedName("Maturity_Date")
	public String getMaturityDate() {
	return maturityDate;
	}

	@SerializedName("Maturity_Date")
	public void setMaturityDate(String maturityDate) {
	this.maturityDate = maturityDate;
	}

	@SerializedName("Maturity_Value")
	public String getMaturityValue() {
	return maturityValue;
	}

	@SerializedName("Maturity_Value")
	public void setMaturityValue(String maturityValue) {
	this.maturityValue = maturityValue;
	}

	@SerializedName("ETDA_Flag")
	public String getETDAFlag() {
	return eTDAFlag;
	}

	@SerializedName("ETDA_Flag")
	public void setETDAFlag(String eTDAFlag) {
	this.eTDAFlag = eTDAFlag;
	}

	@SerializedName("TTL_Breach_Flag")
	public String getTTLBreachFlag() {
	return tTLBreachFlag;
	}

	@SerializedName("TTL_Breach_Flag")
	public void setTTLBreachFlag(String tTLBreachFlag) {
	this.tTLBreachFlag = tTLBreachFlag;
	}

	@SerializedName("Actual_Advance_Amount")
	public String getActualAdvanceAmount() {
	return actualAdvanceAmount;
	}

	@SerializedName("Actual_Advance_Amount")
	public void setActualAdvanceAmount(String actualAdvanceAmount) {
	this.actualAdvanceAmount = actualAdvanceAmount;
	}

	@SerializedName("Additional_Approved_Amount")
	public String getAdditionalApprovedAmount() {
	return additionalApprovedAmount;
	}

	@SerializedName("Additional_Approved_Amount")
	public void setAdditionalApprovedAmount(String additionalApprovedAmount) {
	this.additionalApprovedAmount = additionalApprovedAmount;
	}

	@SerializedName("Additional_Advanced_Amount")
	public String getAdditionalAdvancedAmount() {
	return additionalAdvancedAmount;
	}

	@SerializedName("Additional_Advanced_Amount")
	public void setAdditionalAdvancedAmount(String additionalAdvancedAmount) {
	this.additionalAdvancedAmount = additionalAdvancedAmount;
	}

	@SerializedName("Account_Open_Date")
	public String getAccountOpenDate() {
	return accountOpenDate;
	}

	@SerializedName("Account_Open_Date")
	public void setAccountOpenDate(String accountOpenDate) {
	this.accountOpenDate = accountOpenDate;
	}

	@SerializedName("Loan_Term")
	public String getLoanTerm() {
	return loanTerm;
	}

	@SerializedName("Loan_Term")
	public void setLoanTerm(String loanTerm) {
	this.loanTerm = loanTerm;
	}

	@SerializedName("Limit_Expiry_Date")
	public String getLimitExpiryDate() {
	return limitExpiryDate;
	}

	@SerializedName("Limit_Expiry_Date")
	public void setLimitExpiryDate(String limitExpiryDate) {
	this.limitExpiryDate = limitExpiryDate;
	}

	@SerializedName("Mode_Of_Delivery")
	public String getModeOfDelivery() {
	return modeOfDelivery;
	}

	@SerializedName("Mode_Of_Delivery")
	public void setModeOfDelivery(String modeOfDelivery) {
	this.modeOfDelivery = modeOfDelivery;
	}

	

	@SerializedName("CIF_No")
	public List<String> getCIFNo() {
		return cIFNo;
	}

	@SerializedName("CIF_No")
	public void setCIFNo(List<String> cIFNo) {
		this.cIFNo = cIFNo;
	}

	@SerializedName("Account_No")
	public List<String> getAccountNo() {
		return accountNo;
	}

	@SerializedName("Account_No")
	public void setAccountNo(List<String> accountNo) {
		this.accountNo = accountNo;
	}

}
