package com.fisglobal.fsg.dip.response.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fisglobal.fsg.dip.entity.BaseDTO;

public class MnrlDataResponse extends BaseDTO{
	
	@JsonProperty("encryptedKey")
	private String encryptedKey;
	
	@JsonProperty("encryptedData")
	private String encryptedData;

	@JsonProperty("encryptedKey")
	public String getEncryptedKey() {
		return encryptedKey;
	}

	@JsonProperty("encryptedKey")
	public void setEncryptedKey(String encryptedKey) {
		this.encryptedKey = encryptedKey;
	}

	@JsonProperty("encryptedData")
	public String getEncryptedData() {
		return encryptedData;
	}

	@JsonProperty("encryptedData")
	public void setEncryptedData(String encryptedData) {
		this.encryptedData = encryptedData;
	}
	
	

}
