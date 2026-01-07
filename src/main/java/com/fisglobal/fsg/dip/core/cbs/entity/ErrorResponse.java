package com.fisglobal.fsg.dip.core.cbs.entity;

import javax.annotation.Generated;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;

public class ErrorResponse extends Base_VO{

	@SerializedName("metadata")
	private MetaData metadata;
	@SerializedName("additionalinfo")
	private Additionalinfo additionalinfo;
	@SerializedName("metadata")
	public MetaData getMetadata() {
		return metadata;
	}
	@SerializedName("metadata")
	public void setMetadata(MetaData metadata) {
		this.metadata = metadata;
	}
	@SerializedName("additionalinfo")
	public Additionalinfo getAdditionalinfo() {
		return additionalinfo;
	}
	@SerializedName("additionalinfo")
	public void setAdditionalinfo(Additionalinfo additionalinfo) {
		this.additionalinfo = additionalinfo;
	}

}
