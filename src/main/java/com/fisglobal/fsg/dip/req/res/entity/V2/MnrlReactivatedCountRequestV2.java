package com.fisglobal.fsg.dip.req.res.entity.V2;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;

public class MnrlReactivatedCountRequestV2 extends Base_VO{

@SerializedName("MNRLReactiveNum_Request")
private MNRLFetchCountRequestV2 mNRLReactiveNumRequest;

@SerializedName("MNRLReactiveNum_Request")
public MNRLFetchCountRequestV2 getMNRLReactiveNumRequest() {
return mNRLReactiveNumRequest;
}

@SerializedName("MNRLReactiveNum_Request")
public void setMNRLReactiveNumRequest(MNRLFetchCountRequestV2 mNRLReactiveNumRequest) {
this.mNRLReactiveNumRequest = mNRLReactiveNumRequest;
}

}
