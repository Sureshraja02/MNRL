package com.fisglobal.fsg.dip.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "MNRL_DISCONNECTION_REASON_MAPPING")
@Entity
public class MrnlDisconnectionReasonMapping_DAO {

	@Id
	@Column(name = "ACTION_ID")
	private String actionId;

	@Column(name = "DISCONNECTION_REASON")
	private String disconnectionReason;

	public String getActionId() {
		return actionId;
	}

	public void setActionId(String actionId) {
		this.actionId = actionId;
	}

	public String getDisconnectionReason() {
		return disconnectionReason;
	}

	public void setDisconnectionReason(String disconnectionReason) {
		this.disconnectionReason = disconnectionReason;
	}
	
	
}
