package com.fisglobal.fsg.dip.req.res.entity.V2;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;

public class MNRLFetchCountRequestV2 extends Base_VO{
	
	@SerializedName("Body")
	private MNRLCountBodyV2 Body;
	
	@SerializedName("Headers")
	private MNRLHeadersV2 Headers;

	@SerializedName("Body")
	public MNRLCountBodyV2 getBody() {
		return Body;
	}

	@SerializedName("Body")
	public void setBody(MNRLCountBodyV2 body) {
		Body = body;
	}

	@SerializedName("Headers")
	public MNRLHeadersV2 getHeaders() {
		return Headers;
	}

	@SerializedName("Headers")
	public void setHeaders(MNRLHeadersV2 headers) {
		Headers = headers;
	}
	
	
	
	

}
