package com.fisglobal.fsg.dip.request.entity;

import com.fisglobal.fsg.dip.core.utils.Base_VO;

public class MNRLDataBody extends Base_VO{
	
	private MNRLDataPayload Payload;

	public MNRLDataPayload getPayload() {
		return Payload;
	}

	public void setPayload(MNRLDataPayload payload) {
		Payload = payload;
	}

}
