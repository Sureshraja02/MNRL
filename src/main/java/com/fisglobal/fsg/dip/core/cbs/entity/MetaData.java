
package com.fisglobal.fsg.dip.core.cbs.entity;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class MetaData extends Base_VO{

	@SerializedName("status")
    private Status status;
    

	@SerializedName("status")
    public Status getStatus() {
        return status;
    }

	@SerializedName("status")
    public void setStatus(Status status) {
        this.status = status;
    }

    

}
