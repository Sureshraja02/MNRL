package com.fisglobal.fsg.dip.core.comm.beans;

import com.fisglobal.fsg.dip.core.utils.Base_VO;

public class HeaderProperty extends Base_VO{
	
	private String headerAcceptEncoding;
	private String headerBranchNumber;
	private String headerChannel;
	private String headerContentType;
	private String headerHealthCheck;
	private String headerHealthType;
	private String headerOverrideFlag;
	private String headerRecoveryFlag;
	private String headerAcctStatementTellerNumber;
	private String headerTerminalNumber;
	private String headerXclientCertificate;
	private String headerXIbClientId;
	private String headerXIbClientIdSecret;

	public String getHeaderAcceptEncoding() {
		return headerAcceptEncoding;
	}

	public void setHeaderAcceptEncoding(String headerAcceptEncoding) {
		this.headerAcceptEncoding = headerAcceptEncoding;
	}

	public String getHeaderBranchNumber() {
		return headerBranchNumber;
	}

	public void setHeaderBranchNumber(String headerBranchNumber) {
		this.headerBranchNumber = headerBranchNumber;
	}

	public String getHeaderChannel() {
		return headerChannel;
	}

	public void setHeaderChannel(String headerChannel) {
		this.headerChannel = headerChannel;
	}

	public String getHeaderContentType() {
		return headerContentType;
	}

	public void setHeaderContentType(String headerContentType) {
		this.headerContentType = headerContentType;
	}

	public String getHeaderHealthCheck() {
		return headerHealthCheck;
	}

	public void setHeaderHealthCheck(String headerHealthCheck) {
		this.headerHealthCheck = headerHealthCheck;
	}

	public String getHeaderHealthType() {
		return headerHealthType;
	}

	public void setHeaderHealthType(String headerHealthType) {
		this.headerHealthType = headerHealthType;
	}

	public String getHeaderOverrideFlag() {
		return headerOverrideFlag;
	}

	public void setHeaderOverrideFlag(String headerOverrideFlag) {
		this.headerOverrideFlag = headerOverrideFlag;
	}

	public String getHeaderRecoveryFlag() {
		return headerRecoveryFlag;
	}

	public void setHeaderRecoveryFlag(String headerRecoveryFlag) {
		this.headerRecoveryFlag = headerRecoveryFlag;
	}

	public String getHeaderAcctStatementTellerNumber() {
		return headerAcctStatementTellerNumber;
	}

	public void setHeaderAcctStatementTellerNumber(String headerAcctStatementTellerNumber) {
		this.headerAcctStatementTellerNumber = headerAcctStatementTellerNumber;
	}

	public String getHeaderTerminalNumber() {
		return headerTerminalNumber;
	}

	public void setHeaderTerminalNumber(String headerTerminalNumber) {
		this.headerTerminalNumber = headerTerminalNumber;
	}

	public String getHeaderXclientCertificate() {
		return headerXclientCertificate;
	}

	public void setHeaderXclientCertificate(String headerXclientCertificate) {
		this.headerXclientCertificate = headerXclientCertificate;
	}

	public String getHeaderXIbClientId() {
		return headerXIbClientId;
	}

	public void setHeaderXIbClientId(String headerXIbClientId) {
		this.headerXIbClientId = headerXIbClientId;
	}

	public String getHeaderXIbClientIdSecret() {
		return headerXIbClientIdSecret;
	}

	public void setHeaderXIbClientIdSecret(String headerXIbClientIdSecret) {
		this.headerXIbClientIdSecret = headerXIbClientIdSecret;
	}

}
