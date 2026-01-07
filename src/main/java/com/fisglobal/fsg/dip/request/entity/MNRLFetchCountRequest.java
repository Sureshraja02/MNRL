package com.fisglobal.fsg.dip.request.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fisglobal.fsg.dip.core.utils.Base_VO;

public class MNRLFetchCountRequest extends Base_VO{
	
	private MNRLCountBody Body;
	
	
	private MNRLHeaders Headers;


	public MNRLCountBody getBody() {
		return Body;
	}


	public void setBody(MNRLCountBody body) {
		Body = body;
	}


	public MNRLHeaders getHeaders() {
		return Headers;
	}


	public void setHeaders(MNRLHeaders headers) {
		Headers = headers;
	}
	
	
	
	

}
