package com.fisglobal.fsg.dip.req.res.entity.V2;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;


public class MnrlDataBodyV2 extends Base_VO{

@SerializedName("Payload")
private MnrlDataPayloadV2 payload;

@SerializedName("Payload")
public MnrlDataPayloadV2 getPayload() {
return payload;
}

@SerializedName("Payload")
public void setPayload(MnrlDataPayloadV2 payload) {
this.payload = payload;
}

}
