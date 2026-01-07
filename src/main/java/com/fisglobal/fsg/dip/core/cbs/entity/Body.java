
package com.fisglobal.fsg.dip.core.cbs.entity;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Body extends Base_VO{

	@SerializedName("Payload")
    private Payload payload;
    

	@SerializedName("Payload")
    public Payload getPayload() {
        return payload;
    }

	@SerializedName("Payload")
    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    

}
