package com.fisglobal.fsg.dip.req.res.entity.V2;

import com.fisglobal.fsg.dip.core.cbs.entity.Payload;
import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;

public class MnrlCountResponseBody extends Base_VO{
	@SerializedName("Payload")
	private MnrlCountResponsePayload payload;

	@SerializedName("Payload")
	public MnrlCountResponsePayload getPayload() {
	return payload;
	}

	@SerializedName("Payload")
	public void setPayload(MnrlCountResponsePayload payload) {
	this.payload = payload;
	}

}
