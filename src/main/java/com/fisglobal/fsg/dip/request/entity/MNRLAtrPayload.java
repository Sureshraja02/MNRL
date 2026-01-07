package com.fisglobal.fsg.dip.request.entity;

import com.fisglobal.fsg.dip.core.utils.Base_VO;

public class MNRLAtrPayload extends Base_VO{

	private String encryptedKey;
	private String encryptedData;

	public String getEncryptedKey() {
		return encryptedKey;
	}

	public void setEncryptedKey(String encryptedKey) {
		this.encryptedKey = encryptedKey;
	}

	public String getEncryptedData() {
		return encryptedData;
	}

	public void setEncryptedData(String encryptedData) {
		this.encryptedData = encryptedData;
	}

}
