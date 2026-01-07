package com.fisglobal.fsg.dip.request.entity;

public class MNRLFetchDataRequest {
	
	private MNRLDataBody Body;
	private MNRLHeaders Headers;
	public MNRLDataBody getBody() {
		return Body;
	}
	public void setBody(MNRLDataBody body) {
		Body = body;
	}
	public MNRLHeaders getHeaders() {
		return Headers;
	}
	public void setHeaders(MNRLHeaders headers) {
		Headers = headers;
	}

}
