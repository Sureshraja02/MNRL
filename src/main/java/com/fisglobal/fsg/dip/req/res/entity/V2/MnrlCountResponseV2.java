package com.fisglobal.fsg.dip.req.res.entity.V2;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;

public class MnrlCountResponseV2 extends Base_VO{
	
	@SerializedName("MNRLFetchCount_Response")
	private MNRLFetchCountResponse mNRLFetchCountResponse;

	@SerializedName("MNRLFetchCount_Response")
	public MNRLFetchCountResponse getMNRLFetchCountResponse() {
	return mNRLFetchCountResponse;
	}

	@SerializedName("MNRLFetchCount_Response")
	public void setMNRLFetchCountResponse(MNRLFetchCountResponse mNRLFetchCountResponse) {
	this.mNRLFetchCountResponse = mNRLFetchCountResponse;
	}

}
