package com.fisglobal.fsg.dip.core.cbs.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;

public class MobileEnquiryPayload extends Base_VO{

	@JsonProperty("Collection")
	private MobileEnquiryCollection Collection;

	@JsonProperty("Collection")
	public MobileEnquiryCollection getCollection() {
		return Collection;
	}

	@JsonProperty("Collection")
	public void setCollection(MobileEnquiryCollection collection) {
		this.Collection = collection;
	}

	
}
