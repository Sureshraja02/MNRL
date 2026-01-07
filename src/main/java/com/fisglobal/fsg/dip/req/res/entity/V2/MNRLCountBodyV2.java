package com.fisglobal.fsg.dip.req.res.entity.V2;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;

public class MNRLCountBodyV2 extends Base_VO {
	@SerializedName("Payload")
	private MNRLCountPayloadV2 Payload;

	@SerializedName("Payload")
	public MNRLCountPayloadV2 getPayload() {
		return Payload;
	}

	@SerializedName("Payload")
	public void setPayload(MNRLCountPayloadV2 payload) {
		Payload = payload;
	}

}
