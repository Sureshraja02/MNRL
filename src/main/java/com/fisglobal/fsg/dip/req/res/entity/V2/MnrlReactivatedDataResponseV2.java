package com.fisglobal.fsg.dip.req.res.entity.V2;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;


public class MnrlReactivatedDataResponseV2 extends Base_VO {

@SerializedName("MNRLReactiveData_Response")
private MNRLReactiveDataResponseV2 mNRLReactiveDataResponse;

@SerializedName("MNRLReactiveData_Response")
public MNRLReactiveDataResponseV2 getMNRLReactiveDataResponse() {
return mNRLReactiveDataResponse;
}

@SerializedName("MNRLReactiveData_Response")
public void setMNRLReactiveDataResponse(MNRLReactiveDataResponseV2 mNRLReactiveDataResponse) {
this.mNRLReactiveDataResponse = mNRLReactiveDataResponse;
}

}
