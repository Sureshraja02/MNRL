package com.fisglobal.fsg.dip.req.res.entity.V2;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;


public class MnrlAtrRequestV2 extends Base_VO{

@SerializedName("MNRLATRUpdate_Request")
private MNRLATRUpdateRequestV2 mNRLATRUpdateRequest;

@SerializedName("MNRLATRUpdate_Request")
public MNRLATRUpdateRequestV2 getMNRLATRUpdateRequest() {
return mNRLATRUpdateRequest;
}

@SerializedName("MNRLATRUpdate_Request")
public void setMNRLATRUpdateRequest(MNRLATRUpdateRequestV2 mNRLATRUpdateRequest) {
this.mNRLATRUpdateRequest = mNRLATRUpdateRequest;
}

}
