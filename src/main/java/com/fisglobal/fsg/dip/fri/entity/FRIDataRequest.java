package com.fisglobal.fsg.dip.fri.entity;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;


public class FRIDataRequest extends Base_VO {

@SerializedName("FRIFetchData_Request")
private FRIFetchDataRequest fRIFetchDataRequest;

@SerializedName("FRIFetchData_Request")
public FRIFetchDataRequest getFRIFetchDataRequest() {
return fRIFetchDataRequest;
}

@SerializedName("FRIFetchData_Request")
public void setFRIFetchDataRequest(FRIFetchDataRequest fRIFetchDataRequest) {
this.fRIFetchDataRequest = fRIFetchDataRequest;
}

}
