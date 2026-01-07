package com.fisglobal.fsg.dip.request.entity;

import com.fisglobal.fsg.dip.core.utils.Base_VO;

public class MNRLAtrRequest extends Base_VO {
	
	private MNRLAtrBody Body;
	
	private MNRLHeaders Headers;

	public MNRLAtrBody getBody() {
		return Body;
	}

	public void setBody(MNRLAtrBody body) {
		Body = body;
	}

	public MNRLHeaders getHeaders() {
		return Headers;
	}

	public void setHeaders(MNRLHeaders headers) {
		Headers = headers;
	}

}
