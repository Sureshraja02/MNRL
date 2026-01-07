package com.fisglobal.fsg.dip.req.res.entity.V2;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;


public class MnrlReactivatedDataResponseBodyV2 extends Base_VO{

@SerializedName("Payload")
private MnrlReactivatedDataResponsePayloadV2 payload;

@SerializedName("Payload")
public MnrlReactivatedDataResponsePayloadV2 getPayload() {
return payload;
}

@SerializedName("Payload")
public void setPayload(MnrlReactivatedDataResponsePayloadV2 payload) {
this.payload = payload;
}

}
