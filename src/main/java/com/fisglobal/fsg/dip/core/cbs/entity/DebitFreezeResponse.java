package com.fisglobal.fsg.dip.core.cbs.entity;

import com.fisglobal.fsg.dip.core.utils.Base_VO;
import com.google.gson.annotations.SerializedName;


public class DebitFreezeResponse extends Base_VO {

	@SerializedName("DepositSetStopFrozenHandedOver_Response")
	private DepositSetStopFrozenHandedOverResponse depositSetStopFrozenHandedOverResponse;
	
	@SerializedName("DepositsSetStopFrozenHandedOver_Response")
	private DepositSetStopFrozenHandedOverResponse depositsSetStopFrozenHandedOverResponse;
	
	@SerializedName("ErrorResponse")
	private ErrorResponse errorResponse;

	@SerializedName("ErrorResponse")
	public ErrorResponse getErrorResponse() {
		return errorResponse;
	}

	@SerializedName("ErrorResponse")
	public void setErrorResponse(ErrorResponse errorResponse) {
		this.errorResponse = errorResponse;
	}

	@SerializedName("DepositsSetStopFrozenHandedOver_Response")
	public DepositSetStopFrozenHandedOverResponse getDepositsSetStopFrozenHandedOverResponse() {
		return depositsSetStopFrozenHandedOverResponse;
	}
	@SerializedName("DepositsSetStopFrozenHandedOver_Response")
	public void setDepositsSetStopFrozenHandedOverResponse(
			DepositSetStopFrozenHandedOverResponse depositsSetStopFrozenHandedOverResponse) {
		this.depositsSetStopFrozenHandedOverResponse = depositsSetStopFrozenHandedOverResponse;
	}

	@SerializedName("DepositSetStopFrozenHandedOver_Response")
	public DepositSetStopFrozenHandedOverResponse getDepositSetStopFrozenHandedOverResponse() {
		return depositSetStopFrozenHandedOverResponse;
	}

	@SerializedName("DepositSetStopFrozenHandedOver_Response")
	public void setDepositSetStopFrozenHandedOverResponse(
			DepositSetStopFrozenHandedOverResponse depositSetStopFrozenHandedOverResponse) {
		this.depositSetStopFrozenHandedOverResponse = depositSetStopFrozenHandedOverResponse;
	}

}
