package com.fisglobal.fsg.dip.core.comm.beans;

import java.util.List;

import com.fisglobal.fsg.dip.core.utils.Base_VO;

public class AppProperty extends Base_VO {

	private String keystoreAlias;
	private String keystoreSecureTerm;
	private String KeystoreType;
	private String mnrlCountDate;
	private String mnrlCountDateFlag;
	private String mastersecureterm;
	private String mnrlXMLPath;
	private String mnrlCsvPath;
	private String mnrlAuthAPIHeader;
	private String mnrlCountAPIHeader;
	private String mnrlDataAPIHeader;
	private String mnrlAuthURL;
	private String mnrlCountURL;
	private String mnrlDataURL;
	private String keystoreFilePath;
	private boolean proxyFlag;
	private String proxyHost;
	private int proxyPort;
	private int pool;
	private int restTimeOut;
	private int restReadTimeOut;
	private String userName;
	private String userSecureterm;
	private boolean sslVerify;
	private int countPerOffset;
	private int reqCount;
	private long reqIntervalTime;
	private String mnrlAtrInCsvPath;
	private String mnrlAtrOutCsvPath;
	private String mnrlPubKeyPath;
	private String mnrlAtrUrl;
	private String mnrlAtrApiHeader;
	private String cbsAccEnqHeader;
	private String cbsAccEnqUrl;
	private String cbsMobileEnqHeader;
	private String cbsMobileEnqUrl;
	private String cbsCifEnqHeader;
	private String cbsCifEnqUrl;
	private String cbsCifBlockHeader;
	private String cbsCifBlockUrl;
	private String cbsMobileRemovalHeader;
	private String cbsMobileRemovalUrl;
	private String friPublicKeyPath;
	private String friauthHeader;
	private String friauthURL;
	private String friCountDate;
	private String friCountDateFlag;
	private String friCountAPIHeader;
	private String frikeystoreAlias;
	private String frikeystoreSecureTerm;
	private String friKeystoreType;
	private String frikeystoreFilePath;
	private long retryIntervalTime;
	private String mnrlReactivatedCountDate;
	private String mnrlReactivatedCountDateFlag;
	private String mnrlReactivatedCountAPIHeader;
	private String mnrlReactivatedCountURL;
	private String mnrlReactivatedDataAPIHeader;
	private String mnrlReactivatedDataURL;
	private int friReqCount;
	private long friReqIntervalTime;
	private int reactivatedReqCount;
	private long reactivatedReqIntervalTime;
	private int friCountPerOffset;
	private int reactivatedCountPerOffset;
	private long friRetryIntervalTime;
	private long reactivatedRetryIntervalTime;
	private String friAtrUrl;
	private String friAtrApiHeader;
	private int friMobileEnquiryPagingSize;
	private int friMobileEnquiryThreadPoolSize;
	private int friActionPagingSize;
	private int friActionThreadPoolSize;
	private String headerEnvironments;
	private String stopReason;
	private String accountCondition;
	private String debitFreezeHeader;
	private String debitFreezeUrl;
	private int mnrlMobileEnquiryPagingSize;
	private int mnrlMobileEnquiryThreadPoolSize;
	private int mnrlActionPagingSize;
	private int mnrlActionThreadPoolSize;
	private int mnrlEnquiryPagingSize;
	private int mnrlEnquiryPoolSize;
	private String countryCode;
	private String mobileEnqNoDataError;
	private int mnrlATRPagingSize;
	private int mnrlATRPoolSize;
	
	private int friATRPagingSize;
	private int friATRPoolSize;

	
	public int getMnrlATRPagingSize() {
		return mnrlATRPagingSize;
	}

	public void setMnrlATRPagingSize(int mnrlATRPagingSize) {
		this.mnrlATRPagingSize = mnrlATRPagingSize;
	}

	public int getMnrlATRPoolSize() {
		return mnrlATRPoolSize;
	}

	public void setMnrlATRPoolSize(int mnrlATRPoolSize) {
		this.mnrlATRPoolSize = mnrlATRPoolSize;
	}

	public String getMobileEnqNoDataError() {
		return mobileEnqNoDataError;
	}

	public void setMobileEnqNoDataError(String mobileEnqNoDataError) {
		this.mobileEnqNoDataError = mobileEnqNoDataError;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public int getMnrlMobileEnquiryPagingSize() {
		return mnrlMobileEnquiryPagingSize;
	}

	public void setMnrlMobileEnquiryPagingSize(int mnrlMobileEnquiryPagingSize) {
		this.mnrlMobileEnquiryPagingSize = mnrlMobileEnquiryPagingSize;
	}

	public int getMnrlMobileEnquiryThreadPoolSize() {
		return mnrlMobileEnquiryThreadPoolSize;
	}

	public void setMnrlMobileEnquiryThreadPoolSize(int mnrlMobileEnquiryThreadPoolSize) {
		this.mnrlMobileEnquiryThreadPoolSize = mnrlMobileEnquiryThreadPoolSize;
	}

	public int getMnrlActionPagingSize() {
		return mnrlActionPagingSize;
	}

	public void setMnrlActionPagingSize(int mnrlActionPagingSize) {
		this.mnrlActionPagingSize = mnrlActionPagingSize;
	}

	public int getMnrlActionThreadPoolSize() {
		return mnrlActionThreadPoolSize;
	}

	public void setMnrlActionThreadPoolSize(int mnrlActionThreadPoolSize) {
		this.mnrlActionThreadPoolSize = mnrlActionThreadPoolSize;
	}

	public int getMnrlEnquiryPagingSize() {
		return mnrlEnquiryPagingSize;
	}

	public void setMnrlEnquiryPagingSize(int mnrlEnquiryPagingSize) {
		this.mnrlEnquiryPagingSize = mnrlEnquiryPagingSize;
	}

	public int getMnrlEnquiryPoolSize() {
		return mnrlEnquiryPoolSize;
	}

	public void setMnrlEnquiryPoolSize(int mnrlEnquiryPoolSize) {
		this.mnrlEnquiryPoolSize = mnrlEnquiryPoolSize;
	}

	public String getStopReason() {
		return stopReason;
	}

	public void setStopReason(String stopReason) {
		this.stopReason = stopReason;
	}

	public String getAccountCondition() {
		return accountCondition;
	}

	public void setAccountCondition(String accountCondition) {
		this.accountCondition = accountCondition;
	}

	public String getDebitFreezeHeader() {
		return debitFreezeHeader;
	}

	public void setDebitFreezeHeader(String debitFreezeHeader) {
		this.debitFreezeHeader = debitFreezeHeader;
	}

	public String getDebitFreezeUrl() {
		return debitFreezeUrl;
	}

	public void setDebitFreezeUrl(String debitFreezeUrl) {
		this.debitFreezeUrl = debitFreezeUrl;
	}

	public int getFriMobileEnquiryPagingSize() {
		return friMobileEnquiryPagingSize;
	}

	public void setFriMobileEnquiryPagingSize(int friMobileEnquiryPagingSize) {
		this.friMobileEnquiryPagingSize = friMobileEnquiryPagingSize;
	}

	public int getFriMobileEnquiryThreadPoolSize() {
		return friMobileEnquiryThreadPoolSize;
	}

	public void setFriMobileEnquiryThreadPoolSize(int friMobileEnquiryThreadPoolSize) {
		this.friMobileEnquiryThreadPoolSize = friMobileEnquiryThreadPoolSize;
	}

	public int getFriActionPagingSize() {
		return friActionPagingSize;
	}

	public void setFriActionPagingSize(int friActionPagingSize) {
		this.friActionPagingSize = friActionPagingSize;
	}

	public int getFriActionThreadPoolSize() {
		return friActionThreadPoolSize;
	}

	public void setFriActionThreadPoolSize(int friActionThreadPoolSize) {
		this.friActionThreadPoolSize = friActionThreadPoolSize;
	}

	public String getFriAtrUrl() {
		return friAtrUrl;
	}

	public void setFriAtrUrl(String friAtrUrl) {
		this.friAtrUrl = friAtrUrl;
	}

	public String getFriAtrApiHeader() {
		return friAtrApiHeader;
	}

	public void setFriAtrApiHeader(String friAtrApiHeader) {
		this.friAtrApiHeader = friAtrApiHeader;
	}

	public int getFriCountPerOffset() {
		return friCountPerOffset;
	}

	public void setFriCountPerOffset(int friCountPerOffset) {
		this.friCountPerOffset = friCountPerOffset;
	}

	public int getReactivatedCountPerOffset() {
		return reactivatedCountPerOffset;
	}

	public void setReactivatedCountPerOffset(int reactivatedCountPerOffset) {
		this.reactivatedCountPerOffset = reactivatedCountPerOffset;
	}

	public int getFriReqCount() {
		return friReqCount;
	}

	public void setFriReqCount(int friReqCount) {
		this.friReqCount = friReqCount;
	}

	public long getFriReqIntervalTime() {
		return friReqIntervalTime;
	}

	public void setFriReqIntervalTime(long friReqIntervalTime) {
		this.friReqIntervalTime = friReqIntervalTime;
	}

	public int getReactivatedReqCount() {
		return reactivatedReqCount;
	}

	public void setReactivatedReqCount(int reactivatedReqCount) {
		this.reactivatedReqCount = reactivatedReqCount;
	}

	public long getReactivatedReqIntervalTime() {
		return reactivatedReqIntervalTime;
	}

	public void setReactivatedReqIntervalTime(long reactivatedReqIntervalTime) {
		this.reactivatedReqIntervalTime = reactivatedReqIntervalTime;
	}

	public long getFriRetryIntervalTime() {
		return friRetryIntervalTime;
	}

	public void setFriRetryIntervalTime(long friRetryIntervalTime) {
		this.friRetryIntervalTime = friRetryIntervalTime;
	}

	public String getMnrlReactivatedDataAPIHeader() {
		return mnrlReactivatedDataAPIHeader;
	}

	public void setMnrlReactivatedDataAPIHeader(String mnrlReactivatedDataAPIHeader) {
		this.mnrlReactivatedDataAPIHeader = mnrlReactivatedDataAPIHeader;
	}

	public String getMnrlReactivatedDataURL() {
		return mnrlReactivatedDataURL;
	}

	public void setMnrlReactivatedDataURL(String mnrlReactivatedDataURL) {
		this.mnrlReactivatedDataURL = mnrlReactivatedDataURL;
	}

	public String getMnrlReactivatedCountAPIHeader() {
		return mnrlReactivatedCountAPIHeader;
	}

	public void setMnrlReactivatedCountAPIHeader(String mnrlReactivatedCountAPIHeader) {
		this.mnrlReactivatedCountAPIHeader = mnrlReactivatedCountAPIHeader;
	}

	public String getMnrlReactivatedCountURL() {
		return mnrlReactivatedCountURL;
	}

	public void setMnrlReactivatedCountURL(String mnrlReactivatedCountURL) {
		this.mnrlReactivatedCountURL = mnrlReactivatedCountURL;
	}

	public String getMnrlReactivatedCountDate() {
		return mnrlReactivatedCountDate;
	}

	public void setMnrlReactivatedCountDate(String mnrlReactivatedCountDate) {
		this.mnrlReactivatedCountDate = mnrlReactivatedCountDate;
	}

	public String getMnrlReactivatedCountDateFlag() {
		return mnrlReactivatedCountDateFlag;
	}

	public void setMnrlReactivatedCountDateFlag(String mnrlReactivatedCountDateFlag) {
		this.mnrlReactivatedCountDateFlag = mnrlReactivatedCountDateFlag;
	}

	public long getRetryIntervalTime() {
		return retryIntervalTime;
	}

	public void setRetryIntervalTime(long retryIntervalTime) {
		this.retryIntervalTime = retryIntervalTime;
	}

	public String getFrikeystoreAlias() {
		return frikeystoreAlias;
	}

	public void setFrikeystoreAlias(String frikeystoreAlias) {
		this.frikeystoreAlias = frikeystoreAlias;
	}

	public String getFrikeystoreSecureTerm() {
		return frikeystoreSecureTerm;
	}

	public void setFrikeystoreSecureTerm(String frikeystoreSecureTerm) {
		this.frikeystoreSecureTerm = frikeystoreSecureTerm;
	}

	public String getFriKeystoreType() {
		return friKeystoreType;
	}

	public void setFriKeystoreType(String friKeystoreType) {
		this.friKeystoreType = friKeystoreType;
	}

	public String getFrikeystoreFilePath() {
		return frikeystoreFilePath;
	}

	public void setFrikeystoreFilePath(String frikeystoreFilePath) {
		this.frikeystoreFilePath = frikeystoreFilePath;
	}

	public String getFriCountAPIHeader() {
		return friCountAPIHeader;
	}

	public void setFriCountAPIHeader(String friCountAPIHeader) {
		this.friCountAPIHeader = friCountAPIHeader;
	}

	public String getFriDataAPIHeader() {
		return friDataAPIHeader;
	}

	public void setFriDataAPIHeader(String friDataAPIHeader) {
		this.friDataAPIHeader = friDataAPIHeader;
	}

	public String getFriCountURL() {
		return friCountURL;
	}

	public void setFriCountURL(String friCountURL) {
		this.friCountURL = friCountURL;
	}

	public String getFriDataURL() {
		return friDataURL;
	}

	public void setFriDataURL(String friDataURL) {
		this.friDataURL = friDataURL;
	}

	private String friDataAPIHeader;
	private String friCountURL;
	private String friDataURL;

	public String getCbsMobileRemovalHeader() {
		return cbsMobileRemovalHeader;
	}

	public void setCbsMobileRemovalHeader(String cbsMobileRemovalHeader) {
		this.cbsMobileRemovalHeader = cbsMobileRemovalHeader;
	}

	public String getCbsMobileRemovalUrl() {
		return cbsMobileRemovalUrl;
	}

	public void setCbsMobileRemovalUrl(String cbsMobileRemovalUrl) {
		this.cbsMobileRemovalUrl = cbsMobileRemovalUrl;
	}

	public int getReqCount() {
		return reqCount;
	}

	public void setReqCount(int reqCount) {
		this.reqCount = reqCount;
	}

	public long getReqIntervalTime() {
		return reqIntervalTime;
	}

	public void setReqIntervalTime(long reqIntervalTime) {
		this.reqIntervalTime = reqIntervalTime;
	}

	public String getMnrlAtrInCsvPath() {
		return mnrlAtrInCsvPath;
	}

	public void setMnrlAtrInCsvPath(String mnrlAtrInCsvPath) {
		this.mnrlAtrInCsvPath = mnrlAtrInCsvPath;
	}

	public String getMnrlAtrOutCsvPath() {
		return mnrlAtrOutCsvPath;
	}

	public void setMnrlAtrOutCsvPath(String mnrlAtrOutCsvPath) {
		this.mnrlAtrOutCsvPath = mnrlAtrOutCsvPath;
	}

	public String getMnrlPubKeyPath() {
		return mnrlPubKeyPath;
	}

	public void setMnrlPubKeyPath(String mnrlPubKeyPath) {
		this.mnrlPubKeyPath = mnrlPubKeyPath;
	}

	public String getMnrlAtrUrl() {
		return mnrlAtrUrl;
	}

	public void setMnrlAtrUrl(String mnrlAtrUrl) {
		this.mnrlAtrUrl = mnrlAtrUrl;
	}

	public String getProxyHost() {
		return proxyHost;
	}

	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}

	public int getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(int proxyPort) {
		this.proxyPort = proxyPort;
	}

	public int getPool() {
		return pool;
	}

	public void setPool(int pool) {
		this.pool = pool;
	}

	public int getRestTimeOut() {
		return restTimeOut;
	}

	public void setRestTimeOut(int restTimeOut) {
		this.restTimeOut = restTimeOut;
	}

	public int getRestReadTimeOut() {
		return restReadTimeOut;
	}

	public void setRestReadTimeOut(int restReadTimeOut) {
		this.restReadTimeOut = restReadTimeOut;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserSecureterm() {
		return userSecureterm;
	}

	public void setUserSecureterm(String userSecureterm) {
		this.userSecureterm = userSecureterm;
	}

	public boolean isSslVerify() {
		return sslVerify;
	}

	public void setSslVerify(boolean sslVerify) {
		this.sslVerify = sslVerify;
	}

	public String getMnrlCountDate() {
		return mnrlCountDate;
	}

	public void setMnrlCountDate(String mnrlCountDate) {
		this.mnrlCountDate = mnrlCountDate;
	}

	public String getMnrlCountDateFlag() {
		return mnrlCountDateFlag;
	}

	public void setMnrlCountDateFlag(String mnrlCountDateFlag) {
		this.mnrlCountDateFlag = mnrlCountDateFlag;
	}

	public String getMastersecureterm() {
		return mastersecureterm;
	}

	public void setMastersecureterm(String mastersecureterm) {
		this.mastersecureterm = mastersecureterm;
	}

	public String getMnrlXMLPath() {
		return mnrlXMLPath;
	}

	public void setMnrlXMLPath(String mnrlXMLPath) {
		this.mnrlXMLPath = mnrlXMLPath;
	}

	public String getMnrlCsvPath() {
		return mnrlCsvPath;
	}

	public void setMnrlCsvPath(String mnrlCsvPath) {
		this.mnrlCsvPath = mnrlCsvPath;
	}

	public String getMnrlAuthAPIHeader() {
		return mnrlAuthAPIHeader;
	}

	public void setMnrlAuthAPIHeader(String mnrlAuthAPIHeader) {
		this.mnrlAuthAPIHeader = mnrlAuthAPIHeader;
	}

	public String getMnrlCountAPIHeader() {
		return mnrlCountAPIHeader;
	}

	public void setMnrlCountAPIHeader(String mnrlCountAPIHeader) {
		this.mnrlCountAPIHeader = mnrlCountAPIHeader;
	}

	public String getMnrlDataAPIHeader() {
		return mnrlDataAPIHeader;
	}

	public void setMnrlDataAPIHeader(String mnrlDataAPIHeader) {
		this.mnrlDataAPIHeader = mnrlDataAPIHeader;
	}

	public String getMnrlAuthURL() {
		return mnrlAuthURL;
	}

	public void setMnrlAuthURL(String mnrlAuthURL) {
		this.mnrlAuthURL = mnrlAuthURL;
	}

	public String getMnrlCountURL() {
		return mnrlCountURL;
	}

	public void setMnrlCountURL(String mnrlCountURL) {
		this.mnrlCountURL = mnrlCountURL;
	}

	public String getMnrlDataURL() {
		return mnrlDataURL;
	}

	public void setMnrlDataURL(String mnrlDataURL) {
		this.mnrlDataURL = mnrlDataURL;
	}

	public String getKeystoreAlias() {
		return keystoreAlias;
	}

	public void setKeystoreAlias(String keystoreAlias) {
		this.keystoreAlias = keystoreAlias;
	}

	public String getKeystoreSecureTerm() {
		return keystoreSecureTerm;
	}

	public void setKeystoreSecureTerm(String keystoreSecureTerm) {
		this.keystoreSecureTerm = keystoreSecureTerm;
	}

	public String getKeystoreType() {
		return KeystoreType;
	}

	public void setKeystoreType(String keystoreType) {
		KeystoreType = keystoreType;
	}

	public String getKeystoreFilePath() {
		return keystoreFilePath;
	}

	public void setKeystoreFilePath(String keystoreFilePath) {
		this.keystoreFilePath = keystoreFilePath;
	}

	public boolean isProxyFlag() {
		return proxyFlag;
	}

	public void setProxyFlag(boolean proxyFlag) {
		this.proxyFlag = proxyFlag;
	}

	public int getCountPerOffset() {
		return countPerOffset;
	}

	public void setCountPerOffset(int countPerOffset) {
		this.countPerOffset = countPerOffset;
	}

	public String getCbsAccEnqHeader() {
		return cbsAccEnqHeader;
	}

	public void setCbsAccEnqHeader(String cbsAccEnqHeader) {
		this.cbsAccEnqHeader = cbsAccEnqHeader;
	}

	public String getCbsAccEnqUrl() {
		return cbsAccEnqUrl;
	}

	public void setCbsAccEnqUrl(String cbsAccEnqUrl) {
		this.cbsAccEnqUrl = cbsAccEnqUrl;
	}

	public String getCbsMobileEnqHeader() {
		return cbsMobileEnqHeader;
	}

	public void setCbsMobileEnqHeader(String cbsMobileEnqHeader) {
		this.cbsMobileEnqHeader = cbsMobileEnqHeader;
	}

	public String getCbsMobileEnqUrl() {
		return cbsMobileEnqUrl;
	}

	public void setCbsMobileEnqUrl(String cbsMobileEnqUrl) {
		this.cbsMobileEnqUrl = cbsMobileEnqUrl;
	}

	public String getCbsCifEnqHeader() {
		return cbsCifEnqHeader;
	}

	public void setCbsCifEnqHeader(String cbsCifEnqHeader) {
		this.cbsCifEnqHeader = cbsCifEnqHeader;
	}

	public String getCbsCifEnqUrl() {
		return cbsCifEnqUrl;
	}

	public void setCbsCifEnqUrl(String cbsCifEnqUrl) {
		this.cbsCifEnqUrl = cbsCifEnqUrl;
	}

	public String getCbsCifBlockHeader() {
		return cbsCifBlockHeader;
	}

	public void setCbsCifBlockHeader(String cbsCifBlockHeader) {
		this.cbsCifBlockHeader = cbsCifBlockHeader;
	}

	public String getCbsCifBlockUrl() {
		return cbsCifBlockUrl;
	}

	public void setCbsCifBlockUrl(String cbsCifBlockUrl) {
		this.cbsCifBlockUrl = cbsCifBlockUrl;
	}

	public String getFriPublicKeyPath() {
		return friPublicKeyPath;
	}

	public void setFriPublicKeyPath(String friPublicKeyPath) {
		this.friPublicKeyPath = friPublicKeyPath;
	}

	public String getFriauthHeader() {
		return friauthHeader;
	}

	public void setFriauthHeader(String friauthHeader) {
		this.friauthHeader = friauthHeader;
	}

	public String getFriauthURL() {
		return friauthURL;
	}

	public void setFriauthURL(String friauthURL) {
		this.friauthURL = friauthURL;
	}

	public String getMnrlAtrApiHeader() {
		return mnrlAtrApiHeader;
	}

	public void setMnrlAtrApiHeader(String mnrlAtrApiHeader) {
		this.mnrlAtrApiHeader = mnrlAtrApiHeader;
	}

	public String getFriCountDate() {
		return friCountDate;
	}

	public void setFriCountDate(String friCountDate) {
		this.friCountDate = friCountDate;
	}

	public String getFriCountDateFlag() {
		return friCountDateFlag;
	}

	public void setFriCountDateFlag(String friCountDateFlag) {
		this.friCountDateFlag = friCountDateFlag;
	}

	public long getReactivatedRetryIntervalTime() {
		return reactivatedRetryIntervalTime;
	}

	public void setReactivatedRetryIntervalTime(long reactivatedRetryIntervalTime) {
		this.reactivatedRetryIntervalTime = reactivatedRetryIntervalTime;
	}

	public String getHeaderEnvironments() {
		return headerEnvironments;
	}

	public void setHeaderEnvironments(String headerEnvironments) {
		this.headerEnvironments = headerEnvironments;
	}

	public int getFriATRPagingSize() {
		return friATRPagingSize;
	}

	public void setFriATRPagingSize(int friATRPagingSize) {
		this.friATRPagingSize = friATRPagingSize;
	}

	public int getFriATRPoolSize() {
		return friATRPoolSize;
	}

	public void setFriATRPoolSize(int friATRPoolSize) {
		this.friATRPoolSize = friATRPoolSize;
	}
	
	

}
