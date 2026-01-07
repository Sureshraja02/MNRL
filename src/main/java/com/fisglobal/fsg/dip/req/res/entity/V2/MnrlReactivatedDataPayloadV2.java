package com.fisglobal.fsg.dip.req.res.entity.V2;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;


public class MnrlReactivatedDataPayloadV2 extends Base_VO{

@SerializedName("date")
private String date;

@SerializedName("offset")
private int offset;

@SerializedName("count")
private int count;

@SerializedName("BankId")
private String bankId;

@SerializedName("date")
public String getDate() {
return date;
}

@SerializedName("date")
public void setDate(String date) {
this.date = date;
}

@SerializedName("offset")
public Integer getOffset() {
return offset;
}

@SerializedName("offset")
public void setOffset(Integer offset) {
this.offset = offset;
}

@SerializedName("count")
public Integer getCount() {
return count;
}

@SerializedName("count")
public void setCount(Integer count) {
this.count = count;
}

@SerializedName("BankId")
public String getBankId() {
return bankId;
}

@SerializedName("BankId")
public void setBankId(String bankId) {
this.bankId = bankId;
}

}
