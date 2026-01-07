package com.fisglobal.fsg.dip.request.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fisglobal.fsg.dip.core.utils.Base_VO;

public class MNRLCountBody extends Base_VO{
	
	private MNRLCountPayload Payload;

	public MNRLCountPayload getPayload() {
		return Payload;
	}

	public void setPayload(MNRLCountPayload payload) {
		Payload = payload;
	}

	
}
