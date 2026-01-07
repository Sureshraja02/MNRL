package com.fisglobal.fsg.dip.fri.entity;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;

public class FRICountRequest extends Base_VO {

	@SerializedName("FRIFetchCount_Request")
	private FRIFetchCountRequest fRIFetchCountRequest;

	@SerializedName("FRIFetchCount_Request")
	public FRIFetchCountRequest getFRIFetchCountRequest() {
		return fRIFetchCountRequest;
	}

	@SerializedName("FRIFetchCount_Request")
	public void setFRIFetchCountRequest(FRIFetchCountRequest fRIFetchCountRequest) {
		this.fRIFetchCountRequest = fRIFetchCountRequest;
	}

}
