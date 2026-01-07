package com.fisglobal.fsg.dip.request.entity;

import com.fisglobal.fsg.dip.core.utils.Base_VO;

public class MNRLAtrBody extends Base_VO{
	
	private MNRLAtrPayload Payload;

	public MNRLAtrPayload getPayload() {
		return Payload;
	}

	public void setPayload(MNRLAtrPayload payload) {
		Payload = payload;
	}

}
