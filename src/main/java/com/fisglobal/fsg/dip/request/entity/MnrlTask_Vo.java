package com.fisglobal.fsg.dip.request.entity;

import java.util.List;

import com.fisglobal.fsg.dip.entity.FriCountDetails_DAO;
import com.fisglobal.fsg.dip.entity.FriData_DAO;
import com.fisglobal.fsg.dip.entity.MnrlCountDetails_DAO;
import com.fisglobal.fsg.dip.entity.MnrlData_DAO;
import com.fisglobal.fsg.dip.entity.ReactivatedCountDetails_DAO;
import com.fisglobal.fsg.dip.entity.ReactivatedData_DAO;

public class MnrlTask_Vo {

	private int fromCount;
	private int totalSize;
	private int offsetNumber;
	private int offLimit;
	private int rateLimit;
	private String reqdate;
	private int partialLimit;
	private String token;
	private boolean retryDataDownload;
	 
	private List<MnrlData_DAO> mnrlDataListtotal;
	private List<FriData_DAO>  friDataListtotal;
	private List<ReactivatedData_DAO>  reactivatedDataListtotal;
	private MnrlCountDetails_DAO mnrlCountDetails;
	private FriCountDetails_DAO friCountDetails;
	private ReactivatedCountDetails_DAO reactivatedCountDetails;
	
	
	public FriCountDetails_DAO getFriCountDetails() {
		return friCountDetails;
	}

	public void setFriCountDetails(FriCountDetails_DAO friCountDetails) {
		this.friCountDetails = friCountDetails;
	}

	public ReactivatedCountDetails_DAO getReactivatedCountDetails() {
		return reactivatedCountDetails;
	}

	public void setReactivatedCountDetails(ReactivatedCountDetails_DAO reactivatedCountDetails) {
		this.reactivatedCountDetails = reactivatedCountDetails;
	}

	public MnrlCountDetails_DAO getMnrlCountDetails() {
		return mnrlCountDetails;
	}

	public void setMnrlCountDetails(MnrlCountDetails_DAO mnrlCountDetails) {
		this.mnrlCountDetails = mnrlCountDetails;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean isRetryDataDownload() {
		return retryDataDownload;
	}

	public void setRetryDataDownload(boolean retryDataDownload) {
		this.retryDataDownload = retryDataDownload;
	}

	public int getFromCount() {
		return fromCount;
	}

	public void setFromCount(int fromCount) {
		this.fromCount = fromCount;
	}

	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	public int getOffsetNumber() {
		return offsetNumber;
	}

	public void setOffsetNumber(int offsetNumber) {
		this.offsetNumber = offsetNumber;
	}

	public int getOffLimit() {
		return offLimit;
	}

	public void setOffLimit(int offLimit) {
		this.offLimit = offLimit;
	}

	public int getRateLimit() {
		return rateLimit;
	}

	public void setRateLimit(int rateLimit) {
		this.rateLimit = rateLimit;
	}

	public List<MnrlData_DAO> getMnrlDataListtotal() {
		return mnrlDataListtotal;
	}

	public void setMnrlDataListtotal(List<MnrlData_DAO> mnrlDataListtotal) {
		this.mnrlDataListtotal = mnrlDataListtotal;
	}

	public int getPartialLimit() {
		return partialLimit;
	}

	public void setPartialLimit(int partialLimit) {
		this.partialLimit = partialLimit;
	}

	public String getReqdate() {
		return reqdate;
	}

	public void setReqdate(String reqdate) {
		this.reqdate = reqdate;
	}

	public List<FriData_DAO> getFriDataListtotal() {
		return friDataListtotal;
	}

	public void setFriDataListtotal(List<FriData_DAO> friDataListtotal) {
		this.friDataListtotal = friDataListtotal;
	}

	public List<ReactivatedData_DAO> getReactivatedDataListtotal() {
		return reactivatedDataListtotal;
	}

	public void setReactivatedDataListtotal(List<ReactivatedData_DAO> reactivatedDataListtotal) {
		this.reactivatedDataListtotal = reactivatedDataListtotal;
	}

}
