package com.fisglobal.fsg.dip.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "MNRL_ACTION_DETAIL")
@Entity
public class MnrlActionDetail_DAO {
	@Id
	@Column(name = "ACTION_CODE")
	private String actionCode;

	@Column(name = "ACTION_REASON")
	private String actionReason;

	public String getActionCode() {
		return actionCode;
	}

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	public String getActionReason() {
		return actionReason;
	}

	public void setActionReason(String actionReason) {
		this.actionReason = actionReason;
	}

	
	
	

}
