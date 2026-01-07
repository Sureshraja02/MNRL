package com.fisglobal.fsg.dip.req.res.entity.V2;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;

public class MnrlAuthResponseV2 extends Base_VO {
	
	@SerializedName("MNRLToken_Response")
	private MNRLTokenResponse mNRLTokenResponse;

	@SerializedName("MNRLToken_Response")
	public MNRLTokenResponse getMNRLTokenResponse() {
	return mNRLTokenResponse;
	}

	@SerializedName("MNRLToken_Response")
	public void setMNRLTokenResponse(MNRLTokenResponse mNRLTokenResponse) {
	this.mNRLTokenResponse = mNRLTokenResponse;
	}

}
