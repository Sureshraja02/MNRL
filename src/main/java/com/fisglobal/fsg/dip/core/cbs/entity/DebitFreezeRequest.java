package com.fisglobal.fsg.dip.core.cbs.entity;

import java.math.BigInteger;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;


public class DebitFreezeRequest extends Base_VO {

@SerializedName("Account_Number")
private BigInteger accountNumber;

@SerializedName("Stop_Reason")
private String stopReason;

@SerializedName("Account_Condition")
private String accountCondition;

@SerializedName("Additional_Stop_Details")
private String additionalStopDetails;

@SerializedName("Account_Number")
public BigInteger getAccountNumber() {
return accountNumber;
}

@SerializedName("Account_Number")
public void setAccountNumber(BigInteger accountNumber) {
this.accountNumber = accountNumber;
}

@SerializedName("Additional_Stop_Details")
public String getAdditionalStopDetails() {
	return additionalStopDetails;
}

@SerializedName("Additional_Stop_Details")
public void setAdditionalStopDetails(String additionalStopDetails) {
	this.additionalStopDetails = additionalStopDetails;
}

@SerializedName("Stop_Reason")
public String getStopReason() {
return stopReason;
}

@SerializedName("Stop_Reason")
public void setStopReason(String stopReason) {
this.stopReason = stopReason;
}

@SerializedName("Account_Condition")
public String getAccountCondition() {
return accountCondition;
}

@SerializedName("Account_Condition")
public void setAccountCondition(String accountCondition) {
this.accountCondition = accountCondition;
}

}
