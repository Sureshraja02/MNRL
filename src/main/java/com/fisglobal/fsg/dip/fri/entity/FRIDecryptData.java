package com.fisglobal.fsg.dip.fri.entity;

import java.util.List;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;

public class FRIDecryptData extends Base_VO{

	
	@SerializedName("data")
	private List<FRIData> data;

	@SerializedName("data")
	public List<FRIData> getData() {
	return data;
	}
	
	@SerializedName("data")
	public void setData(List<FRIData> data) {
	this.data = data;
	}
}
