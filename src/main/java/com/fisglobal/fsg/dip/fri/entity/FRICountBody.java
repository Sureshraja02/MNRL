package com.fisglobal.fsg.dip.fri.entity;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FRICountBody extends Base_VO {

	@SerializedName("Payload")
	private FRICountPayload payload;

	@SerializedName("Payload")
	public FRICountPayload getPayload() {
		return payload;
	}

	@SerializedName("Payload")
	public void setPayload(FRICountPayload payload) {
		this.payload = payload;
	}

}
