package com.fisglobal.fsg.dip.req.res.entity.V2;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;

public class MnrlCountRequestV2 extends Base_VO {
	@SerializedName("MNRLFetchCount_Request")
	private MNRLFetchCountRequestV2 MNRLFetchCount_Request;

	@SerializedName("MNRLFetchCount_Request")
	public MNRLFetchCountRequestV2 getMNRLFetchCount_Request() {
		return MNRLFetchCount_Request;
	}

	@SerializedName("MNRLFetchCount_Request")
	public void setMNRLFetchCount_Request(MNRLFetchCountRequestV2 mNRLFetchCount_Request) {
		MNRLFetchCount_Request = mNRLFetchCount_Request;
	}

	
	
}
