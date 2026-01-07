
package com.fisglobal.fsg.dip.core.cbs.entity;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Payload extends Base_VO {

	@SerializedName("Account_Number")
	private String accountNumber;

	@SerializedName("CustomerDetails")
	private List<CustomerDetail> customerDetails;
	
	@SerializedName("Status")
	private String status;
	
	@SerializedName("Journal_Number")
	private String journalNumber;

	@SerializedName("Journal_Number")
	public String getJournalNumber() {
		return journalNumber;
	}

	@SerializedName("Journal_Number")
	public void setJournalNumber(String journalNumber) {
		this.journalNumber = journalNumber;
	}

	@SerializedName("Status")
	public String getStatus() {
	return status;
	}

	@SerializedName("Status")
	public void setStatus(String status) {
	this.status = status;
	}

	@SerializedName("Account_Number")
	public String getAccountNumber() {
		return accountNumber;
	}

	@SerializedName("Account_Number")
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	@SerializedName("CustomerDetails")
	public List<CustomerDetail> getCustomerDetails() {
		return customerDetails;
	}

	@SerializedName("CustomerDetails")
	public void setCustomerDetails(List<CustomerDetail> customerDetails) {
		this.customerDetails = customerDetails;
	}

	@SerializedName("CIF_Number")
	private String cIFNumber;
	@SerializedName("Cust_Tier_Type")
	private String custTierType;
	@SerializedName("Cust_Tier_Type_Desc")
	private String custTierTypeDesc;
	@SerializedName("CIF_Status")
	private String cIFStatus;
	@SerializedName("Resident_Type")
	private String residentType;
	@SerializedName("Domicile_Status")
	private String domicileStatus;
	@SerializedName("CIF_open_Date")
	private String cIFOpenDate;
	@SerializedName("Gender")
	private String gender;
	@SerializedName("Title_Code")
	private String titleCode;
	@SerializedName("First_Name")
	private String firstName;
	@SerializedName("Middle_Name")
	private String middleName;
	@SerializedName("Last_Name")
	private String lastName;
	@SerializedName("Address_Line_1")
	private String addressLine1;
	@SerializedName("Address_Line_2")
	private String addressLine2;
	@SerializedName("Address_Line_3")
	private String addressLine3;
	@SerializedName("Address_line_4")
	private String addressLine4;
	@SerializedName("PinCode")
	private String pinCode;
	@SerializedName("City")
	private String city;
	@SerializedName("State")
	private String state;
	@SerializedName("Country")
	private String country;
	@SerializedName("Date_of_Birth")
	private String dateOfBirth;
	@SerializedName("Nationality")
	private String nationality;
	@SerializedName("Domestic_Risk")
	private String domesticRisk;
	@SerializedName("Country_Code")
	private String countryCode;
	@SerializedName("Mobile_No")
	private String mobileNo;
	@SerializedName("Email")
	private String email;
	@SerializedName("PAN_No")
	private String pANNo;
	@SerializedName("PAN_Verified")
	private String pANVerified;
	@SerializedName("Aadhar")
	private String aadhar;
	@SerializedName("Aadhar_Status")
	private String aadharStatus;
	@SerializedName("KYC_Flag")
	private String kYCFlag;
	@SerializedName("KYC_Date")
	private String kYCDate;
	@SerializedName("Present_Risk")
	private String presentRisk;
	@SerializedName("Risk_Score")
	private String riskScore;
	@SerializedName("CAPC_Verified")
	private String cAPCVerified;
	@SerializedName("CKYCR_ID")
	private String ckycrId;
	@SerializedName("Home_Branch")
	private String homeBranch;
	@SerializedName("LRS_Limit")
	private String lRSLimit;
	@SerializedName("SPC_Flag")
	private String sPCFlag;
	@SerializedName("Trade_Customer")
	private String tradeCustomer;
	@SerializedName("Occupation_Code")
	private String occupationCode;
	@SerializedName("REL_Indicator")
	private String rELIndicator;
	@SerializedName("Father_Spouse_Name")
	private String fatherSpouseName;
	@SerializedName("Mother_Name")
	private String motherName;
	@SerializedName("Form_60_Date")
	private String form60Date;
	@SerializedName("GSTN_NO")
	private String gstnNo;
	@SerializedName("Village_Code")
	private String villageCode;
	@SerializedName("Education_Code")
	private String educationCode;
	@SerializedName("Cust_Communication_Address_Line_1")
	private String custCommunicationAddressLine1;
	@SerializedName("Cust_Communication_Address_Line_2")
	private String custCommunicationAddressLine2;
	@SerializedName("Cust_Communication_Address_Line_3")
	private String custCommunicationAddressLine3;
	@SerializedName("Cust_Communication_Address_Line_4")
	private String custCommunicationAddressLine4;
	@SerializedName("Post_Code")
	private String postCode;
	@SerializedName("Cust_Comm_Addr_Effective_Date")
	private String custCommAddrEffectiveDate;
	@SerializedName("Cust_Comm_Addr_Expiry_Date")
	private String custCommAddrExpiryDate;
	@SerializedName("IB_Enabled")
	private String iBEnabled;
	@SerializedName("MB_Enabled")
	private String mBEnabled;
	@SerializedName("VIP_Code")
	private String vIPCode;
	@SerializedName("Legal_Entity_Identifier")
	private String legalEntityIdentifier;
	@SerializedName("LEI_Expiry_Date")
	private String lEIExpiryDate;
	@SerializedName("CIS_Org_Code")
	private String cISOrgCode;
	@SerializedName("BSR_Org_Code")
	private String bSROrgCode;
	@SerializedName("Segment_Code")
	private String segmentCode;
	@SerializedName("Locker_Holder")
	private String lockerHolder;
	@SerializedName("ID_Type")
	private String iDType;
	@SerializedName("ID_Number")
	private String iDNumber;
	@SerializedName("Marital_Status")
	private String maritalStatus;
	@SerializedName("HNI_Flag")
	private String hNIFlag;
	@SerializedName("Income_Details")
	private String incomeDetails;
	@SerializedName("Income_Freq")
	private String incomeFreq;
	@SerializedName("Threshold_Limit")
	private String thresholdLimit;
	@SerializedName("District_Name")
	private String districtName;
	@SerializedName("EKYC_Flag")
	private String eKYCFlag;
	@SerializedName("Date_Of_Establishment")
	private String dateOfEstablishment;
	@SerializedName("GST_Applicability")
	private String gSTApplicability;
	@SerializedName("Listed_In_Stock_Exchange")
	private String listedInStockExchange;

	

	@SerializedName("CIF_Number")
	public String getCIFNumber() {
		return cIFNumber;
	}

	@SerializedName("CIF_Number")
	public void setCIFNumber(String cIFNumber) {
		this.cIFNumber = cIFNumber;
	}

	@SerializedName("Cust_Tier_Type")
	public String getCustTierType() {
		return custTierType;
	}

	@SerializedName("Cust_Tier_Type")
	public void setCustTierType(String custTierType) {
		this.custTierType = custTierType;
	}

	@SerializedName("Cust_Tier_Type_Desc")
	public String getCustTierTypeDesc() {
		return custTierTypeDesc;
	}

	@SerializedName("Cust_Tier_Type_Desc")
	public void setCustTierTypeDesc(String custTierTypeDesc) {
		this.custTierTypeDesc = custTierTypeDesc;
	}

	@SerializedName("CIF_Status")
	public String getCIFStatus() {
		return cIFStatus;
	}

	@SerializedName("CIF_Status")
	public void setCIFStatus(String cIFStatus) {
		this.cIFStatus = cIFStatus;
	}

	@SerializedName("Resident_Type")
	public String getResidentType() {
		return residentType;
	}

	@SerializedName("Resident_Type")
	public void setResidentType(String residentType) {
		this.residentType = residentType;
	}

	@SerializedName("Domicile_Status")
	public String getDomicileStatus() {
		return domicileStatus;
	}

	@SerializedName("Domicile_Status")
	public void setDomicileStatus(String domicileStatus) {
		this.domicileStatus = domicileStatus;
	}

	@SerializedName("CIF_open_Date")
	public String getCIFOpenDate() {
		return cIFOpenDate;
	}

	@SerializedName("CIF_open_Date")
	public void setCIFOpenDate(String cIFOpenDate) {
		this.cIFOpenDate = cIFOpenDate;
	}

	@SerializedName("Gender")
	public String getGender() {
		return gender;
	}

	@SerializedName("Gender")
	public void setGender(String gender) {
		this.gender = gender;
	}

	@SerializedName("Title_Code")
	public String getTitleCode() {
		return titleCode;
	}

	@SerializedName("Title_Code")
	public void setTitleCode(String titleCode) {
		this.titleCode = titleCode;
	}

	@SerializedName("First_Name")
	public String getFirstName() {
		return firstName;
	}

	@SerializedName("First_Name")
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@SerializedName("Middle_Name")
	public String getMiddleName() {
		return middleName;
	}

	@SerializedName("Middle_Name")
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	@SerializedName("Last_Name")
	public String getLastName() {
		return lastName;
	}

	@SerializedName("Last_Name")
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@SerializedName("Address_Line_1")
	public String getAddressLine1() {
		return addressLine1;
	}

	@SerializedName("Address_Line_1")
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	@SerializedName("Address_Line_2")
	public String getAddressLine2() {
		return addressLine2;
	}

	@SerializedName("Address_Line_2")
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	@SerializedName("Address_Line_3")
	public String getAddressLine3() {
		return addressLine3;
	}

	@SerializedName("Address_Line_3")
	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}

	@SerializedName("Address_line_4")
	public String getAddressLine4() {
		return addressLine4;
	}

	@SerializedName("Address_line_4")
	public void setAddressLine4(String addressLine4) {
		this.addressLine4 = addressLine4;
	}

	@SerializedName("PinCode")
	public String getPinCode() {
		return pinCode;
	}

	@SerializedName("PinCode")
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	@SerializedName("City")
	public String getCity() {
		return city;
	}

	@SerializedName("City")
	public void setCity(String city) {
		this.city = city;
	}

	@SerializedName("State")
	public String getState() {
		return state;
	}

	@SerializedName("State")
	public void setState(String state) {
		this.state = state;
	}

	@SerializedName("Country")
	public String getCountry() {
		return country;
	}

	@SerializedName("Country")
	public void setCountry(String country) {
		this.country = country;
	}

	@SerializedName("Date_of_Birth")
	public String getDateOfBirth() {
		return dateOfBirth;
	}

	@SerializedName("Date_of_Birth")
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	@SerializedName("Nationality")
	public String getNationality() {
		return nationality;
	}

	@SerializedName("Nationality")
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	@SerializedName("Domestic_Risk")
	public String getDomesticRisk() {
		return domesticRisk;
	}

	@SerializedName("Domestic_Risk")
	public void setDomesticRisk(String domesticRisk) {
		this.domesticRisk = domesticRisk;
	}

	@SerializedName("Country_Code")
	public String getCountryCode() {
		return countryCode;
	}

	@SerializedName("Country_Code")
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	@SerializedName("Mobile_No")
	public String getMobileNo() {
		return mobileNo;
	}

	@SerializedName("Mobile_No")
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	@SerializedName("Email")
	public String getEmail() {
		return email;
	}

	@SerializedName("Email")
	public void setEmail(String email) {
		this.email = email;
	}

	@SerializedName("PAN_No")
	public String getPANNo() {
		return pANNo;
	}

	@SerializedName("PAN_No")
	public void setPANNo(String pANNo) {
		this.pANNo = pANNo;
	}

	@SerializedName("PAN_Verified")
	public String getPANVerified() {
		return pANVerified;
	}

	@SerializedName("PAN_Verified")
	public void setPANVerified(String pANVerified) {
		this.pANVerified = pANVerified;
	}

	@SerializedName("Aadhar")
	public String getAadhar() {
		return aadhar;
	}

	@SerializedName("Aadhar")
	public void setAadhar(String aadhar) {
		this.aadhar = aadhar;
	}

	@SerializedName("Aadhar_Status")
	public String getAadharStatus() {
		return aadharStatus;
	}

	@SerializedName("Aadhar_Status")
	public void setAadharStatus(String aadharStatus) {
		this.aadharStatus = aadharStatus;
	}

	@SerializedName("KYC_Flag")
	public String getKYCFlag() {
		return kYCFlag;
	}

	@SerializedName("KYC_Flag")
	public void setKYCFlag(String kYCFlag) {
		this.kYCFlag = kYCFlag;
	}

	@SerializedName("KYC_Date")
	public String getKYCDate() {
		return kYCDate;
	}

	@SerializedName("KYC_Date")
	public void setKYCDate(String kYCDate) {
		this.kYCDate = kYCDate;
	}

	@SerializedName("Present_Risk")
	public String getPresentRisk() {
		return presentRisk;
	}

	@SerializedName("Present_Risk")
	public void setPresentRisk(String presentRisk) {
		this.presentRisk = presentRisk;
	}

	@SerializedName("Risk_Score")
	public String getRiskScore() {
		return riskScore;
	}

	@SerializedName("Risk_Score")
	public void setRiskScore(String riskScore) {
		this.riskScore = riskScore;
	}

	@SerializedName("CAPC_Verified")
	public String getCAPCVerified() {
		return cAPCVerified;
	}

	@SerializedName("CAPC_Verified")
	public void setCAPCVerified(String cAPCVerified) {
		this.cAPCVerified = cAPCVerified;
	}

	@SerializedName("CKYCR_ID")
	public String getCkycrId() {
		return ckycrId;
	}

	@SerializedName("CKYCR_ID")
	public void setCkycrId(String ckycrId) {
		this.ckycrId = ckycrId;
	}

	@SerializedName("Home_Branch")
	public String getHomeBranch() {
		return homeBranch;
	}

	@SerializedName("Home_Branch")
	public void setHomeBranch(String homeBranch) {
		this.homeBranch = homeBranch;
	}

	@SerializedName("LRS_Limit")
	public String getLRSLimit() {
		return lRSLimit;
	}

	@SerializedName("LRS_Limit")
	public void setLRSLimit(String lRSLimit) {
		this.lRSLimit = lRSLimit;
	}

	@SerializedName("SPC_Flag")
	public String getSPCFlag() {
		return sPCFlag;
	}

	@SerializedName("SPC_Flag")
	public void setSPCFlag(String sPCFlag) {
		this.sPCFlag = sPCFlag;
	}

	@SerializedName("Trade_Customer")
	public String getTradeCustomer() {
		return tradeCustomer;
	}

	@SerializedName("Trade_Customer")
	public void setTradeCustomer(String tradeCustomer) {
		this.tradeCustomer = tradeCustomer;
	}

	@SerializedName("Occupation_Code")
	public String getOccupationCode() {
		return occupationCode;
	}

	@SerializedName("Occupation_Code")
	public void setOccupationCode(String occupationCode) {
		this.occupationCode = occupationCode;
	}

	@SerializedName("REL_Indicator")
	public String getRELIndicator() {
		return rELIndicator;
	}

	@SerializedName("REL_Indicator")
	public void setRELIndicator(String rELIndicator) {
		this.rELIndicator = rELIndicator;
	}

	@SerializedName("Father_Spouse_Name")
	public String getFatherSpouseName() {
		return fatherSpouseName;
	}

	@SerializedName("Father_Spouse_Name")
	public void setFatherSpouseName(String fatherSpouseName) {
		this.fatherSpouseName = fatherSpouseName;
	}

	@SerializedName("Mother_Name")
	public String getMotherName() {
		return motherName;
	}

	@SerializedName("Mother_Name")
	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	@SerializedName("Form_60_Date")
	public String getForm60Date() {
		return form60Date;
	}

	@SerializedName("Form_60_Date")
	public void setForm60Date(String form60Date) {
		this.form60Date = form60Date;
	}

	@SerializedName("GSTN_NO")
	public String getGstnNo() {
		return gstnNo;
	}

	@SerializedName("GSTN_NO")
	public void setGstnNo(String gstnNo) {
		this.gstnNo = gstnNo;
	}

	@SerializedName("Village_Code")
	public String getVillageCode() {
		return villageCode;
	}

	@SerializedName("Village_Code")
	public void setVillageCode(String villageCode) {
		this.villageCode = villageCode;
	}

	@SerializedName("Education_Code")
	public String getEducationCode() {
		return educationCode;
	}

	@SerializedName("Education_Code")
	public void setEducationCode(String educationCode) {
		this.educationCode = educationCode;
	}

	@SerializedName("Cust_Communication_Address_Line_1")
	public String getCustCommunicationAddressLine1() {
		return custCommunicationAddressLine1;
	}

	@SerializedName("Cust_Communication_Address_Line_1")
	public void setCustCommunicationAddressLine1(String custCommunicationAddressLine1) {
		this.custCommunicationAddressLine1 = custCommunicationAddressLine1;
	}

	@SerializedName("Cust_Communication_Address_Line_2")
	public String getCustCommunicationAddressLine2() {
		return custCommunicationAddressLine2;
	}

	@SerializedName("Cust_Communication_Address_Line_2")
	public void setCustCommunicationAddressLine2(String custCommunicationAddressLine2) {
		this.custCommunicationAddressLine2 = custCommunicationAddressLine2;
	}

	@SerializedName("Cust_Communication_Address_Line_3")
	public String getCustCommunicationAddressLine3() {
		return custCommunicationAddressLine3;
	}

	@SerializedName("Cust_Communication_Address_Line_3")
	public void setCustCommunicationAddressLine3(String custCommunicationAddressLine3) {
		this.custCommunicationAddressLine3 = custCommunicationAddressLine3;
	}

	@SerializedName("Cust_Communication_Address_Line_4")
	public String getCustCommunicationAddressLine4() {
		return custCommunicationAddressLine4;
	}

	@SerializedName("Cust_Communication_Address_Line_4")
	public void setCustCommunicationAddressLine4(String custCommunicationAddressLine4) {
		this.custCommunicationAddressLine4 = custCommunicationAddressLine4;
	}

	@SerializedName("Post_Code")
	public String getPostCode() {
		return postCode;
	}

	@SerializedName("Post_Code")
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	@SerializedName("Cust_Comm_Addr_Effective_Date")
	public String getCustCommAddrEffectiveDate() {
		return custCommAddrEffectiveDate;
	}

	@SerializedName("Cust_Comm_Addr_Effective_Date")
	public void setCustCommAddrEffectiveDate(String custCommAddrEffectiveDate) {
		this.custCommAddrEffectiveDate = custCommAddrEffectiveDate;
	}

	@SerializedName("Cust_Comm_Addr_Expiry_Date")
	public String getCustCommAddrExpiryDate() {
		return custCommAddrExpiryDate;
	}

	@SerializedName("Cust_Comm_Addr_Expiry_Date")
	public void setCustCommAddrExpiryDate(String custCommAddrExpiryDate) {
		this.custCommAddrExpiryDate = custCommAddrExpiryDate;
	}

	@SerializedName("IB_Enabled")
	public String getIBEnabled() {
		return iBEnabled;
	}

	@SerializedName("IB_Enabled")
	public void setIBEnabled(String iBEnabled) {
		this.iBEnabled = iBEnabled;
	}

	@SerializedName("MB_Enabled")
	public String getMBEnabled() {
		return mBEnabled;
	}

	@SerializedName("MB_Enabled")
	public void setMBEnabled(String mBEnabled) {
		this.mBEnabled = mBEnabled;
	}

	@SerializedName("VIP_Code")
	public String getVIPCode() {
		return vIPCode;
	}

	@SerializedName("VIP_Code")
	public void setVIPCode(String vIPCode) {
		this.vIPCode = vIPCode;
	}

	@SerializedName("Legal_Entity_Identifier")
	public String getLegalEntityIdentifier() {
		return legalEntityIdentifier;
	}

	@SerializedName("Legal_Entity_Identifier")
	public void setLegalEntityIdentifier(String legalEntityIdentifier) {
		this.legalEntityIdentifier = legalEntityIdentifier;
	}

	@SerializedName("LEI_Expiry_Date")
	public String getLEIExpiryDate() {
		return lEIExpiryDate;
	}

	@SerializedName("LEI_Expiry_Date")
	public void setLEIExpiryDate(String lEIExpiryDate) {
		this.lEIExpiryDate = lEIExpiryDate;
	}

	@SerializedName("CIS_Org_Code")
	public String getCISOrgCode() {
		return cISOrgCode;
	}

	@SerializedName("CIS_Org_Code")
	public void setCISOrgCode(String cISOrgCode) {
		this.cISOrgCode = cISOrgCode;
	}

	@SerializedName("BSR_Org_Code")
	public String getBSROrgCode() {
		return bSROrgCode;
	}

	@SerializedName("BSR_Org_Code")
	public void setBSROrgCode(String bSROrgCode) {
		this.bSROrgCode = bSROrgCode;
	}

	@SerializedName("Segment_Code")
	public String getSegmentCode() {
		return segmentCode;
	}

	@SerializedName("Segment_Code")
	public void setSegmentCode(String segmentCode) {
		this.segmentCode = segmentCode;
	}

	@SerializedName("Locker_Holder")
	public String getLockerHolder() {
		return lockerHolder;
	}

	@SerializedName("Locker_Holder")
	public void setLockerHolder(String lockerHolder) {
		this.lockerHolder = lockerHolder;
	}

	@SerializedName("ID_Type")
	public String getIDType() {
		return iDType;
	}

	@SerializedName("ID_Type")
	public void setIDType(String iDType) {
		this.iDType = iDType;
	}

	@SerializedName("ID_Number")
	public String getIDNumber() {
		return iDNumber;
	}

	@SerializedName("ID_Number")
	public void setIDNumber(String iDNumber) {
		this.iDNumber = iDNumber;
	}

	@SerializedName("Marital_Status")
	public String getMaritalStatus() {
		return maritalStatus;
	}

	@SerializedName("Marital_Status")
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	@SerializedName("HNI_Flag")
	public String getHNIFlag() {
		return hNIFlag;
	}

	@SerializedName("HNI_Flag")
	public void setHNIFlag(String hNIFlag) {
		this.hNIFlag = hNIFlag;
	}

	@SerializedName("Income_Details")
	public String getIncomeDetails() {
		return incomeDetails;
	}

	@SerializedName("Income_Details")
	public void setIncomeDetails(String incomeDetails) {
		this.incomeDetails = incomeDetails;
	}

	@SerializedName("Income_Freq")
	public String getIncomeFreq() {
		return incomeFreq;
	}

	@SerializedName("Income_Freq")
	public void setIncomeFreq(String incomeFreq) {
		this.incomeFreq = incomeFreq;
	}

	@SerializedName("Threshold_Limit")
	public String getThresholdLimit() {
		return thresholdLimit;
	}

	@SerializedName("Threshold_Limit")
	public void setThresholdLimit(String thresholdLimit) {
		this.thresholdLimit = thresholdLimit;
	}

	@SerializedName("District_Name")
	public String getDistrictName() {
		return districtName;
	}

	@SerializedName("District_Name")
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	@SerializedName("EKYC_Flag")
	public String getEKYCFlag() {
		return eKYCFlag;
	}

	@SerializedName("EKYC_Flag")
	public void setEKYCFlag(String eKYCFlag) {
		this.eKYCFlag = eKYCFlag;
	}

	@SerializedName("Date_Of_Establishment")
	public String getDateOfEstablishment() {
		return dateOfEstablishment;
	}

	@SerializedName("Date_Of_Establishment")
	public void setDateOfEstablishment(String dateOfEstablishment) {
		this.dateOfEstablishment = dateOfEstablishment;
	}

	@SerializedName("GST_Applicability")
	public String getGSTApplicability() {
		return gSTApplicability;
	}

	@SerializedName("GST_Applicability")
	public void setGSTApplicability(String gSTApplicability) {
		this.gSTApplicability = gSTApplicability;
	}

	@SerializedName("Listed_In_Stock_Exchange")
	public String getListedInStockExchange() {
		return listedInStockExchange;
	}

	@SerializedName("Listed_In_Stock_Exchange")
	public void setListedInStockExchange(String listedInStockExchange) {
		this.listedInStockExchange = listedInStockExchange;
	}

	@SerializedName("Collection")
	private List<Collection> collection;

	@SerializedName("Collection")
	public List<Collection> getCollection() {
		return collection;
	}

	@SerializedName("Collection")
	public void setCollection(List<Collection> collection) {
		this.collection = collection;
	}
	
	@SerializedName("Debit_Bal_LON")
	private String debitBalLON;

	@SerializedName("Credit_Bal_LON")
	private String creditBalLON;

	@SerializedName("Debit_Bal_DEP")
	private String debitBalDEP;

	@SerializedName("Credit_Bal_DEP")
	private String creditBalDEP;

	@SerializedName("Debit_Bal_CTA")
	private String debitBalCTA;

	@SerializedName("Credit_Bal_CTA")
	private String creditBalCTA;

	@SerializedName("Debit_Bal_LON_DEP")
	private String debitBalLONDEP;

	@SerializedName("Credit_Bal_LON_DEP")
	private String creditBalLONDEP;

	@SerializedName("Net_Worth")
	private String netWorth;

	@SerializedName("INCA")
	private String inca;

	@SerializedName("Total_Balance")
	private String totalBalance;

	@SerializedName("Debit_Bal_LON")
	public String getDebitBalLON() {
		return debitBalLON;
	}

	@SerializedName("Debit_Bal_LON")
	public void setDebitBalLON(String debitBalLON) {
		this.debitBalLON = debitBalLON;
	}

	@SerializedName("Credit_Bal_LON")
	public String getCreditBalLON() {
		return creditBalLON;
	}

	@SerializedName("Credit_Bal_LON")
	public void setCreditBalLON(String creditBalLON) {
		this.creditBalLON = creditBalLON;
	}

	@SerializedName("Debit_Bal_DEP")
	public String getDebitBalDEP() {
		return debitBalDEP;
	}

	@SerializedName("Debit_Bal_DEP")
	public void setDebitBalDEP(String debitBalDEP) {
		this.debitBalDEP = debitBalDEP;
	}

	@SerializedName("Credit_Bal_DEP")
	public String getCreditBalDEP() {
		return creditBalDEP;
	}

	@SerializedName("Credit_Bal_DEP")
	public void setCreditBalDEP(String creditBalDEP) {
		this.creditBalDEP = creditBalDEP;
	}

	@SerializedName("Debit_Bal_CTA")
	public String getDebitBalCTA() {
		return debitBalCTA;
	}

	@SerializedName("Debit_Bal_CTA")
	public void setDebitBalCTA(String debitBalCTA) {
		this.debitBalCTA = debitBalCTA;
	}

	@SerializedName("Credit_Bal_CTA")
	public String getCreditBalCTA() {
		return creditBalCTA;
	}

	@SerializedName("Credit_Bal_CTA")
	public void setCreditBalCTA(String creditBalCTA) {
		this.creditBalCTA = creditBalCTA;
	}

	@SerializedName("Debit_Bal_LON_DEP")
	public String getDebitBalLONDEP() {
		return debitBalLONDEP;
	}

	@SerializedName("Debit_Bal_LON_DEP")
	public void setDebitBalLONDEP(String debitBalLONDEP) {
		this.debitBalLONDEP = debitBalLONDEP;
	}

	@SerializedName("Credit_Bal_LON_DEP")
	public String getCreditBalLONDEP() {
		return creditBalLONDEP;
	}

	@SerializedName("Credit_Bal_LON_DEP")
	public void setCreditBalLONDEP(String creditBalLONDEP) {
		this.creditBalLONDEP = creditBalLONDEP;
	}

	@SerializedName("Net_Worth")
	public String getNetWorth() {
		return netWorth;
	}

	@SerializedName("Net_Worth")
	public void setNetWorth(String netWorth) {
		this.netWorth = netWorth;
	}

	@SerializedName("INCA")
	public String getInca() {
		return inca;
	}

	@SerializedName("INCA")
	public void setInca(String inca) {
		this.inca = inca;
	}

	@SerializedName("Total_Balance")
	public String getTotalBalance() {
		return totalBalance;
	}

	@SerializedName("Total_Balance")
	public void setTotalBalance(String totalBalance) {
		this.totalBalance = totalBalance;
	}

}
