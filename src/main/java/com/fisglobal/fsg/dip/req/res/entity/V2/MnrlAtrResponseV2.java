package com.fisglobal.fsg.dip.req.res.entity.V2;

import com.fisglobal.fsg.dip.core.cbs.entity.ErrorResponse;
import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;


public class MnrlAtrResponseV2 extends Base_VO {

@SerializedName("MNRLATRUpdate_Response")
private MNRLATRUpdateResponseV2 mNRLATRUpdateResponse;

@SerializedName("MNRLATRUpdate_Response")
public MNRLATRUpdateResponseV2 getMNRLATRUpdateResponse() {
return mNRLATRUpdateResponse;
}
@SerializedName("MNRLATRUpdate_Response")
public void setMNRLATRUpdateResponse(MNRLATRUpdateResponseV2 mNRLATRUpdateResponse) {
this.mNRLATRUpdateResponse = mNRLATRUpdateResponse;
}

@SerializedName("ErrorResponse")
private ErrorResponse errorResponse;

@SerializedName("ErrorResponse")
public ErrorResponse getErrorResponse() {
	return errorResponse;
}

@SerializedName("ErrorResponse")
public void setErrorResponse(ErrorResponse errorResponse) {
	this.errorResponse = errorResponse;
}
}
