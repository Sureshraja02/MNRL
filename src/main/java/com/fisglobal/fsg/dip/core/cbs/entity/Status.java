
package com.fisglobal.fsg.dip.core.cbs.entity;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Status extends Base_VO{

	@SerializedName("code")
    private String code;
	@SerializedName("desc")
    private String desc;
    
	@SerializedName("code")
    public String getCode() {
        return code;
    }

	@SerializedName("code")
    public void setCode(String code) {
        this.code = code;
    }

	@SerializedName("desc")
    public String getDesc() {
        return desc;
    }

	@SerializedName("desc")
    public void setDesc(String desc) {
        this.desc = desc;
    }

    

}
