package com.fisglobal.fsg.dip.req.res.entity.V2;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;


public class MNRLReactiveDataResponseV2 extends Base_VO{

@SerializedName("metadata")
private MnrlMetadataV2 metadata;

@SerializedName("Body")
private MnrlReactivatedDataResponseBodyV2 body;

@SerializedName("metadata")
public MnrlMetadataV2 getMetadata() {
return metadata;
}

@SerializedName("metadata")
public void setMetadata(MnrlMetadataV2 metadata) {
this.metadata = metadata;
}

@SerializedName("Body")
public MnrlReactivatedDataResponseBodyV2 getBody() {
return body;
}

@SerializedName("Body")
public void setBody(MnrlReactivatedDataResponseBodyV2 body) {
this.body = body;
}

}
