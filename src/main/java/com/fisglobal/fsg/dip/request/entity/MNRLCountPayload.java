package com.fisglobal.fsg.dip.request.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fisglobal.fsg.dip.core.utils.Base_VO;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MNRLCountPayload extends Base_VO {
	
	//@JsonProperty("date")
	private String date;
	
	//@JsonProperty("date")
	public String getDate() {
	return date;
	}

	//@JsonProperty("date")
	public void setDate(String date) {
	this.date = date;
	}

}
