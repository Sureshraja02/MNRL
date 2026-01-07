package com.fisglobal.fsg.dip.req.res.entity.V2;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;

public class MnrlReactivatedCountPayloadV2 extends Base_VO{

@SerializedName("date")
private String date;

@SerializedName("date")
public String getDate() {
return date;
}

@SerializedName("date")
public void setDate(String date) {
this.date = date;
}

}
