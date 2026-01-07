package com.fisglobal.fsg.dip.req.res.entity.V2;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;


public class MnrlDataRequestV2 extends Base_VO{

@SerializedName("MNRLFetchData_Request")
private MNRLFetchDataRequestV2 mNRLFetchDataRequest;

@SerializedName("MNRLFetchData_Request")
public MNRLFetchDataRequestV2 getMNRLFetchDataRequest() {
return mNRLFetchDataRequest;
}

@SerializedName("MNRLFetchData_Request")
public void setMNRLFetchDataRequest(MNRLFetchDataRequestV2 mNRLFetchDataRequest) {
this.mNRLFetchDataRequest = mNRLFetchDataRequest;
}

}
