package com.fisglobal.fsg.dip.entity;

import java.io.Serializable;

import com.fisglobal.fsg.dip.core.utils.Base_VO;

public class MnrlPropertyPK extends Base_VO implements Serializable {
	
	private String propertyName;
	
	private String environment;

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

}
