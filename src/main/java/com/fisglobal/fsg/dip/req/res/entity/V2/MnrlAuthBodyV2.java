package com.fisglobal.fsg.dip.req.res.entity.V2;

import com.fisglobal.fsg.dip.core.cbs.entity.Payload;
import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;

public class MnrlAuthBodyV2 extends Base_VO{
	
	@SerializedName("Payload")
	private MnrlAuthPayloadV2 payload;

	@SerializedName("Payload")
	public MnrlAuthPayloadV2 getPayload() {
	return payload;
	}

	@SerializedName("Payload")
	public void setPayload(MnrlAuthPayloadV2 payload) {
	this.payload = payload;
	}


}
