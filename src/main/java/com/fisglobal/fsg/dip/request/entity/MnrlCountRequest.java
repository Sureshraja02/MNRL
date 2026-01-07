package com.fisglobal.fsg.dip.request.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fisglobal.fsg.dip.entity.BaseDTO;

public class MnrlCountRequest extends BaseDTO {
	
	private MNRLFetchCountRequest MNRLFetchCount_Request;

	public MNRLFetchCountRequest getMNRLFetchCount_Request() {
		return MNRLFetchCount_Request;
	}

	public void setMNRLFetchCount_Request(MNRLFetchCountRequest mNRLFetchCount_Request) {
		MNRLFetchCount_Request = mNRLFetchCount_Request;
	}

	
	
}
