package com.fisglobal.fsg.dip.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "FRI_INDICATOR_DETAILS")
@Entity
public class FriIndicatorDetails_DAO {

	@Id
	@Column(name = "FRI_INDICATOR_ID")
	private String friIndicatorId;

	@Column(name = "FRI_INDICATOR_ID_DESCRIPTION")
	private String friIndicatorIdDescription;

	public String getFriIndicatorId() {
		return friIndicatorId;
	}

	public void setFriIndicatorId(String friIndicatorId) {
		this.friIndicatorId = friIndicatorId;
	}

	public String getFriIndicatorIdDescription() {
		return friIndicatorIdDescription;
	}

	public void setFriIndicatorIdDescription(String friIndicatorIdDescription) {
		this.friIndicatorIdDescription = friIndicatorIdDescription;
	}
}
