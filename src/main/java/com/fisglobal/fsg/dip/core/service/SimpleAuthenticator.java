package com.fisglobal.fsg.dip.core.service;

/**
 *@author e1013276
 *
 */
import java.net.Authenticator;
import java.net.PasswordAuthentication;

public class SimpleAuthenticator extends Authenticator {
	private String username, password;

	public SimpleAuthenticator(String username, String password) {
		this.username = username;
		this.password = password;
	}

	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(username, password.toCharArray());
	}
}
