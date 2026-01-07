package com.fisglobal.fsg.dip.req.res.entity.V2;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;


public class MnrlAtrBodyV2 extends Base_VO{

@SerializedName("Payload")
private MnrlAtrPayloadV2 payload;

@SerializedName("Payload")
public MnrlAtrPayloadV2 getPayload() {
return payload;
}

@SerializedName("Payload")
public void setPayload(MnrlAtrPayloadV2 payload) {
this.payload = payload;
}

}
