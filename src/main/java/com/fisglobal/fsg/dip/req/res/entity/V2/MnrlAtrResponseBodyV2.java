package com.fisglobal.fsg.dip.req.res.entity.V2;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;

public class MnrlAtrResponseBodyV2 extends Base_VO {

@SerializedName("Payload")
private MnrlAtrResponsePayloadV2 payload;

@SerializedName("Payload")
public MnrlAtrResponsePayloadV2 getPayload() {
return payload;
}

@SerializedName("Payload")
public void setPayload(MnrlAtrResponsePayloadV2 payload) {
this.payload = payload;
}

}
