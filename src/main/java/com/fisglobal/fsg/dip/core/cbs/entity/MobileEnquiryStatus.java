
package com.fisglobal.fsg.dip.core.cbs.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fisglobal.fsg.dip.core.utils.Base_VO;


public class MobileEnquiryStatus extends Base_VO{

	@JsonProperty("code")
    private String code;
	@JsonProperty("desc")
    private String desc;
    
	@JsonProperty("code")
    public String getCode() {
        return code;
    }

	@JsonProperty("code")
    public void setCode(String code) {
        this.code = code;
    }

	@JsonProperty("desc")
    public String getDesc() {
        return desc;
    }

	@JsonProperty("desc")
    public void setDesc(String desc) {
        this.desc = desc;
    }

    

}
