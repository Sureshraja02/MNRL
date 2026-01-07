package com.fisglobal.fsg.dip.req.res.entity.V2;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;

public class MnrlReactivatedCountResponseBodyV2 extends Base_VO {

@SerializedName("Payload")
private MnrlReactivatedCountResponsePayloadV2 payload;

@SerializedName("Payload")
public MnrlReactivatedCountResponsePayloadV2 getPayload() {
return payload;
}

@SerializedName("Payload")
public void setPayload(MnrlReactivatedCountResponsePayloadV2 payload) {
this.payload = payload;
}

}
