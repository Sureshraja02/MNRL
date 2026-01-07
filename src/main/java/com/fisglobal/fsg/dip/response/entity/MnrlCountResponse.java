package com.fisglobal.fsg.dip.response.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fisglobal.fsg.dip.entity.BaseDTO;

public class MnrlCountResponse extends BaseDTO{
	
	@JsonProperty("date")
	private String date;
	
	@JsonProperty("count")
	private int count;

	@JsonProperty("date")
	public String getDate() {
		return date;
	}

	@JsonProperty("date")
	public void setDate(String date) {
		this.date = date;
	}

	@JsonProperty("count")
	public int getCount() {
		return count;
	}

	@JsonProperty("count")
	public void setCount(int count) {
		this.count = count;
	}

}
