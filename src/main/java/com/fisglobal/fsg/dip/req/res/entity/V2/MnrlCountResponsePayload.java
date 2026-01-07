package com.fisglobal.fsg.dip.req.res.entity.V2;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;

public class MnrlCountResponsePayload extends Base_VO{
	
	@SerializedName("count")
	private int count;
	
	@SerializedName("date")
	private String date;

	@SerializedName("count")
	public int getCount() {
	return count;
	}

	@SerializedName("count")
	public void setCount(int count) {
	this.count = count;
	}

	@SerializedName("date")
	public String getDate() {
	return date;
	}

	@SerializedName("date")
	public void setDate(String date) {
	this.date = date;
	}


}
