package com.fisglobal.fsg.dip.request.entity;

import java.util.List;

public class MnrlAtrUpdate_Vo {

	private String bank_id;
	private String client_id;
	private List<MnrlAtrData_VO> atr_data;

	public String getBank_id() {
		return bank_id;
	}

	public void setBank_id(String bank_id) {
		this.bank_id = bank_id;
	}

	public List<MnrlAtrData_VO> getAtr_data() {
		return atr_data;
	}

	public void setAtr_data(List<MnrlAtrData_VO> atr_data) {
		this.atr_data = atr_data;
	}

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

}
