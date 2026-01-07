package com.fisglobal.fsg.dip.req.res.entity.V2;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;


public class MnrlDataResponseV2 extends Base_VO{

@SerializedName("MNRLFetchData_Response")
private MNRLFetchDataResponseV2 mNRLFetchDataResponse;

@SerializedName("MNRLFetchData_Response")
public MNRLFetchDataResponseV2 getMNRLFetchDataResponse() {
return mNRLFetchDataResponse;
}

@SerializedName("MNRLFetchData_Response")
public void setMNRLFetchDataResponse(MNRLFetchDataResponseV2 mNRLFetchDataResponse) {
this.mNRLFetchDataResponse = mNRLFetchDataResponse;
}

}
