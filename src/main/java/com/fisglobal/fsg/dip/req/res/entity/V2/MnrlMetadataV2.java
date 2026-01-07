package com.fisglobal.fsg.dip.req.res.entity.V2;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;

public class MnrlMetadataV2 extends Base_VO{
	
	@SerializedName("status")
	private MnrlStatusV2 status;

	@SerializedName("status")
	public MnrlStatusV2 getStatus() {
	return status;
	}

	@SerializedName("status")
	public void setStatus(MnrlStatusV2 status) {
	this.status = status;
	}


}
