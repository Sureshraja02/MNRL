package com.fisglobal.fsg.dip.req.res.entity.V2;

import javax.annotation.Generated;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class MnrlReactivatedDataRequestV2 extends Base_VO{

@SerializedName("MNRLReactiveData_Request")
private MNRLFetchDataRequestV2 mNRLReactiveDataRequest;

@SerializedName("MNRLReactiveData_Request")
public MNRLFetchDataRequestV2 getMNRLReactiveDataRequest() {
return mNRLReactiveDataRequest;
}

@SerializedName("MNRLReactiveData_Request")
public void setMNRLReactiveDataRequest(MNRLFetchDataRequestV2 mNRLReactiveDataRequest) {
this.mNRLReactiveDataRequest = mNRLReactiveDataRequest;
}

}
