package com.fisglobal.fsg.dip.fri.entity;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;

public class FRIAtrRequest extends Base_VO {

	@SerializedName("FRIAtrUpdate_Request")
	private FRIAtrUpdateRequest fRIAtrUpdateRequest;

	@SerializedName("FRIAtrUpdate_Request")
	public FRIAtrUpdateRequest getFRIAtrUpdateRequest() {
		return fRIAtrUpdateRequest;
	}

	@SerializedName("FRIAtrUpdate_Request")
	public void setFRIAtrUpdateRequest(FRIAtrUpdateRequest fRIAtrUpdateRequest) {
		this.fRIAtrUpdateRequest = fRIAtrUpdateRequest;
	}

}
