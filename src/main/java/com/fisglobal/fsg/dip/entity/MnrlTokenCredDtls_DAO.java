package com.fisglobal.fsg.dip.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Table(name = "MNRL_TOKEN_CRED_DTLS")
@Entity
public class MnrlTokenCredDtls_DAO {

	@Id
	private Long id;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "SECURETERM")
	private String secureTerm;

	@Column(name = "BANKID")
	private String bankId;

	@Column(name = "INDICATOR")
	private String indicator;

	public String getIndicator() {
		return indicator;
	}

	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSecureTerm() {
		return secureTerm;
	}

	public void setSecureTerm(String secureTerm) {
		this.secureTerm = secureTerm;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

}
