
package com.fisglobal.fsg.dip.core.cbs.entity;

import java.util.LinkedHashMap;
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


public class CustomerDetail extends Base_VO {

	@SerializedName("No")
    private String no;
	@SerializedName("Customer_No")
    private String customerNo;
	@SerializedName("Customer_Name")
    private String customerName;
	@SerializedName("Owner_%")
    private String owner;
	@SerializedName("Mode")
    private String mode;
	@SerializedName("Aadhar_Ref_No")
    private String aadharRefNo;
    

	@SerializedName("No")
    public String getNo() {
        return no;
    }

	@SerializedName("No")
    public void setNo(String no) {
        this.no = no;
    }

	@SerializedName("Customer_No")
    public String getCustomerNo() {
        return customerNo;
    }

	@SerializedName("Customer_No")
    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

	@SerializedName("Customer_Name")
    public String getCustomerName() {
        return customerName;
    }

	@SerializedName("Customer_Name")
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

	@SerializedName("Owner_%")
    public String getOwner() {
        return owner;
    }

	@SerializedName("Owner_%")
    public void setOwner(String owner) {
        this.owner = owner;
    }

	@SerializedName("Mode")
    public String getMode() {
        return mode;
    }

    @SerializedName("Mode")
    public void setMode(String mode) {
        this.mode = mode;
    }

    @SerializedName("Aadhar_Ref_No")
    public String getAadharRefNo() {
        return aadharRefNo;
    }

    @SerializedName("Aadhar_Ref_No")
    public void setAadharRefNo(String aadharRefNo) {
        this.aadharRefNo = aadharRefNo;
    }

    

}
