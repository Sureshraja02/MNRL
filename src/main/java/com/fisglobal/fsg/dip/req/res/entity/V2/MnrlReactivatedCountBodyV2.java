package com.fisglobal.fsg.dip.req.res.entity.V2;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;

public class MnrlReactivatedCountBodyV2 extends Base_VO {

@SerializedName("Payload")
private MnrlReactivatedCountPayloadV2 payload;

@SerializedName("Payload")
public MnrlReactivatedCountPayloadV2 getPayload() {
return payload;
}
@SerializedName("Payload")
public void setPayload(MnrlReactivatedCountPayloadV2 payload) {
this.payload = payload;
}

}
