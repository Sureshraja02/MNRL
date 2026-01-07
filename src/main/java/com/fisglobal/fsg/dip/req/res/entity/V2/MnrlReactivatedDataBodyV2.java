package com.fisglobal.fsg.dip.req.res.entity.V2;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;


public class MnrlReactivatedDataBodyV2 extends Base_VO {

@SerializedName("Payload")
private MnrlReactivatedDataPayloadV2 payload;

@SerializedName("Payload")
public MnrlReactivatedDataPayloadV2 getPayload() {
return payload;
}

@SerializedName("Payload")
public void setPayload(MnrlReactivatedDataPayloadV2 payload) {
this.payload = payload;
}

}
