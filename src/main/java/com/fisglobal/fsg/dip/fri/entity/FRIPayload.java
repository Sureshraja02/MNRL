package com.fisglobal.fsg.dip.fri.entity;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;

public class FRIPayload extends Base_VO {

	@SerializedName("date")
	private String date;

	@SerializedName("offset")
	private int offset;

	@SerializedName("count")
	private int count;

	@SerializedName("client_id")
	private String clientId;

	/*@SerializedName("encryptedKey")
	private String encryptedKey;

	@SerializedName("encryptedData")
	private String encryptedData;

	@SerializedName("nonce")
	private String nonce;

	@SerializedName("authTag")
	private String authTag;*/

	@SerializedName("date")
	public String getDate() {
		return date;
	}

	@SerializedName("date")
	public void setDate(String date) {
		this.date = date;
	}

	@SerializedName("offset")
	public int getOffset() {
		return offset;
	}

	@SerializedName("offset")
	public void setOffset(int offset) {
		this.offset = offset;
	}

	@SerializedName("count")
	public int getCount() {
		return count;
	}

	@SerializedName("count")
	public void setCount(int count) {
		this.count = count;
	}

	@SerializedName("client_id")
	public String getClientId() {
		return clientId;
	}

	@SerializedName("client_id")
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	/*@SerializedName("encryptedKey")
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
	}*/

}
