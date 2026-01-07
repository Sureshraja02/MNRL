package com.fisglobal.fsg.dip.request.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fisglobal.fsg.dip.entity.BaseDTO;

public class MnrlDataRequest extends BaseDTO{

	private MNRLFetchDataRequest MNRLFetchData_Request;

	public MNRLFetchDataRequest getMNRLFetchData_Request() {
		return MNRLFetchData_Request;
	}

	public void setMNRLFetchData_Request(MNRLFetchDataRequest mNRLFetchData_Request) {
		MNRLFetchData_Request = mNRLFetchData_Request;
	}
	
	
	

}
