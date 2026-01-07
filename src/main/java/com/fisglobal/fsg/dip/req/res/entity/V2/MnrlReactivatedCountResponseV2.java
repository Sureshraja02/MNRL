package com.fisglobal.fsg.dip.req.res.entity.V2;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;


public class MnrlReactivatedCountResponseV2 extends Base_VO {

@SerializedName("MNRLReactiveNum_Response")
private MNRLReactiveNumResponseV2 mNRLReactiveNumResponse;

@SerializedName("MNRLReactiveNum_Response")
public MNRLReactiveNumResponseV2 getMNRLReactiveNumResponse() {
return mNRLReactiveNumResponse;
}

@SerializedName("MNRLReactiveNum_Response")
public void setMNRLReactiveNumResponse(MNRLReactiveNumResponseV2 mNRLReactiveNumResponse) {
this.mNRLReactiveNumResponse = mNRLReactiveNumResponse;
}

}
