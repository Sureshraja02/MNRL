package com.fisglobal.fsg.dip.req.res.entity.V2;

import com.google.gson.annotations.SerializedName;

public class MnrlAuthRequestV2 {
	
	@SerializedName("MNRLToken_Request")
	private MnrlTokenRequestV2 mNRLTokenRequest;

	@SerializedName("MNRLToken_Request")
	public MnrlTokenRequestV2 getMNRLTokenRequest() {
	return mNRLTokenRequest;
	}

	@SerializedName("MNRLToken_Request")
	public void setMNRLTokenRequest(MnrlTokenRequestV2 mNRLTokenRequest) {
	this.mNRLTokenRequest = mNRLTokenRequest;
	}


}
