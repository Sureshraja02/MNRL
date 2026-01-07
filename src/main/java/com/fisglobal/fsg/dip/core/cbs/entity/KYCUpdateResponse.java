package com.fisglobal.fsg.dip.core.cbs.entity;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;


public class KYCUpdateResponse extends Base_VO{

	@SerializedName("metaData")
	private MetaData metaData;
	
	@SerializedName("Body")
	private Body body;

	@SerializedName("metaData")
	public MetaData getMetaData() {
		return metaData;
	}

	@SerializedName("metaData")
	public void setMetaData(MetaData metaData) {
		this.metaData = metaData;
	}

	@SerializedName("Body")
	public Body getBody() {
		return body;
	}

	@SerializedName("Body")
	public void setBody(Body body) {
		this.body = body;
	}

}
