package com.fisglobal.fsg.dip.request.entity;

import com.fisglobal.fsg.dip.core.utils.Base_VO;

public class MnrlReactivatedCountRequest extends Base_VO {

	private MNRLFetchCountRequest MNRLReactiveNum_Request;

	public MNRLFetchCountRequest getMNRLReactiveNum_Request() {
		return MNRLReactiveNum_Request;
	}

	public void setMNRLReactiveNum_Request(MNRLFetchCountRequest mNRLReactiveNum_Request) {
		MNRLReactiveNum_Request = mNRLReactiveNum_Request;
	}

}
