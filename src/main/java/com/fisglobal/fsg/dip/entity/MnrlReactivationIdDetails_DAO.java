package com.fisglobal.fsg.dip.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "MNRL_REACTIVATION_ID_DETAILS")
@Entity
public class MnrlReactivationIdDetails_DAO {
	

	@Id
	@Column(name = "REACTIVATION_ID")
	private int reactivationId;

	@Column(name = "REACTIVATION_ID_DESCRIPTION")
	private String reactivationIdDescription;
	
	public int getReactivationId() {
		return reactivationId;
	}

	public void setReactivationId(int reactivationId) {
		this.reactivationId = reactivationId;
	}

	public String getReactivationIdDescription() {
		return reactivationIdDescription;
	}

	public void setReactivationIdDescription(String reactivationIdDescription) {
		this.reactivationIdDescription = reactivationIdDescription;
	}


}
