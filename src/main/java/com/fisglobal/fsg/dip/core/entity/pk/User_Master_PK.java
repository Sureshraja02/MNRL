package com.fisglobal.fsg.dip.core.entity.pk;

import java.io.Serializable;

import javax.persistence.Basic;

public class User_Master_PK implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Basic
	private String userID;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

}
