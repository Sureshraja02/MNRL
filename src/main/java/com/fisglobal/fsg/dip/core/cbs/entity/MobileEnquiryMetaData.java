
package com.fisglobal.fsg.dip.core.cbs.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class MobileEnquiryMetaData extends Base_VO{

	@JsonProperty("status")
    private MobileEnquiryStatus status;
    

	@JsonProperty("status")
    public MobileEnquiryStatus getStatus() {
        return status;
    }

	@JsonProperty("status")
    public void setStatus(MobileEnquiryStatus status) {
        this.status = status;
    }

    

}
