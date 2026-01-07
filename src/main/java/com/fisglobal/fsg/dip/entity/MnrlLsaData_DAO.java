package com.fisglobal.fsg.dip.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Table(name = "MNRL_LSA_DATA")
@Entity
public class MnrlLsaData_DAO {

	@Id
	@GeneratedValue(generator = "ID", strategy = GenerationType.IDENTITY)
	@SequenceGenerator(name = "IDSEQLS", sequenceName = "IDSEQLS", allocationSize = 1)
	private Long id;
	
	@Column(name = "LSAID")
	private String lsaId;
	
	@Column(name = "LSACODE")
	private String lsaCode;
	
	@Column(name = "LSANAME")
	private String lsaName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLsaId() {
		return lsaId;
	}

	public void setLsaId(String lsaId) {
		this.lsaId = lsaId;
	}

	public String getLsaCode() {
		return lsaCode;
	}

	public void setLsaCode(String lsaCode) {
		this.lsaCode = lsaCode;
	}

	public String getLsaName() {
		return lsaName;
	}

	public void setLsaName(String lsaName) {
		this.lsaName = lsaName;
	}
	
}
