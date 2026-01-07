package com.fisglobal.fsg.dip.fri.entity;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;

public class FRIAtrPayload extends Base_VO {

	@SerializedName("encryptedKey")
	private String encryptedKey;

	@SerializedName("encryptedData")
	private String encryptedData;

	@SerializedName("nonce")
	private String nonce;

	@SerializedName("authTag")
	private String authTag;

	@SerializedName("encryptedKey")
	public String getEncryptedKey() {
		return encryptedKey;
	}

	@SerializedName("encryptedKey")
	public void setEncryptedKey(String encryptedKey) {
		this.encryptedKey = encryptedKey;
	}

	@SerializedName("encryptedData")
	public String getEncryptedData() {
		return encryptedData;
	}

	@SerializedName("encryptedData")
	public void setEncryptedData(String encryptedData) {
		this.encryptedData = encryptedData;
	}

	@SerializedName("nonce")
	public String getNonce() {
		return nonce;
	}

	@SerializedName("nonce")
	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	@SerializedName("authTag")
	public String getAuthTag() {
		return authTag;
	}

	@SerializedName("authTag")
	public void setAuthTag(String authTag) {
		this.authTag = authTag;
	}

}
