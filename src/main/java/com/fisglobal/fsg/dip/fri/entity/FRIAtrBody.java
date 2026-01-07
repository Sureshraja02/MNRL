package com.fisglobal.fsg.dip.fri.entity;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;

public class FRIAtrBody extends Base_VO {
	
	@SerializedName("Payload")
	private FRIAtrPayload payload;

	@SerializedName("Payload")
	public FRIAtrPayload getPayload() {
		return payload;
	}

	@SerializedName("Payload")
	public void setPayload(FRIAtrPayload payload) {
		this.payload = payload;
	}

}
