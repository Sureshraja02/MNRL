package com.fisglobal.fsg.dip.req.res.entity.V2;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;

public class MnrlDataResponseBodyV2 extends Base_VO{

@SerializedName("Payload")
private MnrlDataResponsePayloadV2 payload;

@SerializedName("Payload")
public MnrlDataResponsePayloadV2 getPayload() {
return payload;
}

@SerializedName("Payload")
public void setPayload(MnrlDataResponsePayloadV2 payload) {
this.payload = payload;
}

}
