package com.fisglobal.fsg.dip.req.res.entity.V2;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;

public class MNRLReactiveNumRequestV2 extends Base_VO{

@SerializedName("Body")
private MnrlReactivatedCountBodyV2 body;

@SerializedName("Headers")
private MNRLHeadersV2 headers;

@SerializedName("Body")
public MnrlReactivatedCountBodyV2 getBody() {
return body;
}

@SerializedName("Body")
public void setBody(MnrlReactivatedCountBodyV2 body) {
this.body = body;
}

@SerializedName("Headers")
public MNRLHeadersV2 getHeaders() {
return headers;
}

@SerializedName("Headers")
public void setHeaders(MNRLHeadersV2 headers) {
this.headers = headers;
}

}
