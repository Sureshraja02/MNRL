package com.fisglobal.fsg.dip.fri.entity;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FRIBody extends Base_VO {

	@SerializedName("Payload")
	private FRIPayload payload;

	@SerializedName("Payload")
	public FRIPayload getPayload() {
		return payload;
	}

	@SerializedName("Payload")
	public void setPayload(FRIPayload payload) {
		this.payload = payload;
	}

}
