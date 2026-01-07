package com.fisglobal.fsg.dip.core.data.v1;

import java.time.LocalDateTime;

public class UserDetails {
	
	private String userId;
	private String userType;
	private String passwordDate;
	private String groupID;
	private boolean isFirstTimeLogin;
	private boolean isPasswordExpired;

	private String userName;
	private String appLogo;
	private String appPrimaryColor;
	private String appSecondaryColor;
	private String appInfoColor;
	private String appWarningColor;
	private String appSuccessColor;
	private String appDangerColor;

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getPasswordDate() {
		return passwordDate;
	}

	public void setPasswordDate(String passwordDate) {
		this.passwordDate = passwordDate;
	}

	public String getGroupID() {
		return groupID;
	}

	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}

	public boolean isFirstTimeLogin() {
		return isFirstTimeLogin;
	}

	public void setFirstTimeLogin(boolean isFirstTimeLogin) {
		this.isFirstTimeLogin = isFirstTimeLogin;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAppLogo() {
		return appLogo;
	}

	public void setAppLogo(String appLogo) {
		this.appLogo = appLogo;
	}

	public String getAppPrimaryColor() {
		return appPrimaryColor;
	}

	public void setAppPrimaryColor(String appPrimaryColor) {
		this.appPrimaryColor = appPrimaryColor;
	}

	public String getAppSecondaryColor() {
		return appSecondaryColor;
	}

	public void setAppSecondaryColor(String appSecondaryColor) {
		this.appSecondaryColor = appSecondaryColor;
	}

	public String getAppInfoColor() {
		return appInfoColor;
	}

	public void setAppInfoColor(String appInfoColor) {
		this.appInfoColor = appInfoColor;
	}

	public String getAppWarningColor() {
		return appWarningColor;
	}

	public void setAppWarningColor(String appWarningColor) {
		this.appWarningColor = appWarningColor;
	}

	public String getAppSuccessColor() {
		return appSuccessColor;
	}

	public void setAppSuccessColor(String appSuccessColor) {
		this.appSuccessColor = appSuccessColor;
	}

	public String getAppDangerColor() {
		return appDangerColor;
	}

	public void setAppDangerColor(String appDangerColor) {
		this.appDangerColor = appDangerColor;
	}

	public boolean isPasswordExpired() {
		return isPasswordExpired;
	}

	public void setPasswordExpired(boolean isPasswordExpired) {
		this.isPasswordExpired = isPasswordExpired;
	}

}
