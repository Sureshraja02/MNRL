package com.fisglobal.fsg.dip.req.res.entity.V2;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;

public class MnrlStatusV2 extends Base_VO{

	@SerializedName("Code")
	private String code;
	
	@SerializedName("Desc")
	private String desc;

	@SerializedName("Code")
	public String getCode() {
	return code;
	}

	@SerializedName("Code")
	public void setCode(String code) {
	this.code = code;
	}

	@SerializedName("Desc")
	public String getDesc() {
	return desc;
	}
	@SerializedName("Desc")
	public void setDesc(String desc) {
	this.desc = desc;
	}
}
