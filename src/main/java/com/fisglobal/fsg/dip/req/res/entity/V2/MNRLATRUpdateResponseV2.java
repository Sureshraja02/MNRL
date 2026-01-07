package com.fisglobal.fsg.dip.req.res.entity.V2;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;


public class MNRLATRUpdateResponseV2 extends Base_VO{

@SerializedName("metadata")
private MnrlMetadataV2 metadata;

@SerializedName("Body")
private MnrlAtrResponseBodyV2 body;

@SerializedName("metadata")
public MnrlMetadataV2 getMetadata() {
return metadata;
}

@SerializedName("metadata")
public void setMetadata(MnrlMetadataV2 metadata) {
this.metadata = metadata;
}

@SerializedName("Body")
public MnrlAtrResponseBodyV2 getBody() {
return body;
}

@SerializedName("Body")
public void setBody(MnrlAtrResponseBodyV2 body) {
this.body = body;
}

}
