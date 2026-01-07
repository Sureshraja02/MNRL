/*
 * package com.fisglobal.fsg.dip.core.comm.config;
 * 
 * import java.io.Serializable;
 * 
 * import org.springframework.beans.factory.annotation.Value; import
 * org.springframework.context.annotation.Configuration; import
 * org.springframework.context.annotation.PropertySource; import
 * org.springframework.stereotype.Component;
 * 
 * @Component
 * 
 * @Configuration
 * 
 * @PropertySource("${router.propertypath}req-rsp-msg-code-config.properties")
 * public class ReqstRespDetailsProptiesVO implements Serializable{
 * 
 *//**
	* 
	*/
/*
 * private static final long serialVersionUID = 1L;
 * 
 * @Value("${res.common-error-code}") private String commonErrorCode;
 * 
 * @Value("${res.common-error-msg}") private String commonErrorMsg;
 * 
 * @Value("${res.success-code}") private String successCode;
 * 
 * @Value("${res.success-msg}") private String successMsg;
 * 
 * @Value("${res.record-not-found-error-code}") private String
 * recordNotFoundErrCode;
 * 
 * @Value("${res.record-not-found-error-msg}") private String
 * recordNotFoundErrMsg;
 * 
 * @Value("${res.callback-staus-pending}") private String callbackStsPending;
 * 
 * @Value("${res.callback-staus-failure}") private String callbackStsFailure;
 * 
 * @Value("${res.callback-staus-success}") private String callbackStsSucces;
 * 
 * 
 * @Value("${res.callback-staus-pending-msg}") private String
 * callbackStsPendingMsg;
 * 
 * @Value("${res.callback-staus-failure-msg}") private String
 * callbackStsFailureMsg;
 * 
 * @Value("${res.callback-staus-success-msg}") private String
 * callbackStsSuccesMsg;
 * 
 * 
 * @Value("${res.complaints-status-pending-code}") private String
 * complaintsStsPendingCode;
 * 
 * @Value("${res.complaints-status-failure-code}") private String
 * complaintsStsFailureCode;
 * 
 * @Value("${res.complaints-status-success-code}") private String
 * complaintsStsSuccesCode;
 * 
 * @Value("${res.complaints-status-inprogress-code}") private String
 * complaintsStsInprogressCode;
 * 
 * @Value("${res.invalid-json-format-error-code}") private String
 * invalidJsonFormatErrCode;
 * 
 * @Value("${res.invalid-json-format-error-msg}") private String
 * invalidJsonFormatErrMsg;
 * 
 * 
 * @Value("${res.invalid-incidents-count-error-code}") private String
 * invalidIncidentsCountErrCode;
 * 
 * @Value("${res.invalid-incidents-count-error-msg}") private String
 * invalidIncidentsCountErrMsg;
 * 
 * 
 * @Value("${res.requested-string-size-error-code}") private String
 * requestedStrSizeErrCode;
 * 
 * @Value("${res.requested-string-size-error-msg}") private String
 * requestedStrSizeErrMsg;
 * 
 * 
 * @Value("${res.failure-error-code}") private String failureErrCode;
 * 
 * @Value("${res.failure-error-msg}") private String failureErrMsg;
 * 
 * @Value("${res.invalid-input-param-error-code}") private String
 * invalidInputParamErrCode;
 * 
 * @Value("${res.invalid-input-param-error-msg}") private String
 * invalidInputParamErrMsg;
 * 
 * @Value("${res.invalid-incidents-input-param-error-code}") private String
 * invalidIncidentsInputParamErrCode;
 * 
 * @Value("${res.invalid-incidents-input-param-error-msg}") private String
 * invalidIncidentsInputParamErrMsg;
 * 
 * @Value("${res.rrn-duplicate-found-error-code}") private String
 * rrnDuplicateErrCode;
 * 
 * @Value("${res.rrn-duplicate-found-error-msg}") private String
 * rrnDuplicateErrMsg;
 * 
 * @Value("${res.api-failure-error-code}") private String apiFailureErrorCode;
 * 
 * @Value("${res.api-failure-error-msg}") private String apiFailureErrorMsg;
 * 
 * @Value("${res.holdapi-failure-error-msg}") private String
 * holdApiFailureErrorMsg;
 * 
 * @Value("${res.beneficiaryapi-failure-error-msg}") private String
 * beneficiaryApiFailureErrorMsg;
 * 
 * 
 * public String getCommonErrorCode() { return commonErrorCode; }
 * 
 * public void setCommonErrorCode(String commonErrorCode) { this.commonErrorCode
 * = commonErrorCode; }
 * 
 * public String getCommonErrorMsg() { return commonErrorMsg; }
 * 
 * public void setCommonErrorMsg(String commonErrorMsg) { this.commonErrorMsg =
 * commonErrorMsg; }
 * 
 * public String getSuccessCode() { return successCode; }
 * 
 * public void setSuccessCode(String successCode) { this.successCode =
 * successCode; }
 * 
 * public String getSuccessMsg() { return successMsg; }
 * 
 * public void setSuccessMsg(String successMsg) { this.successMsg = successMsg;
 * }
 * 
 * public String getRecordNotFoundErrCode() { return recordNotFoundErrCode; }
 * 
 * public void setRecordNotFoundErrCode(String recordNotFoundErrCode) {
 * this.recordNotFoundErrCode = recordNotFoundErrCode; }
 * 
 * public String getRecordNotFoundErrMsg() { return recordNotFoundErrMsg; }
 * 
 * public void setRecordNotFoundErrMsg(String recordNotFoundErrMsg) {
 * this.recordNotFoundErrMsg = recordNotFoundErrMsg; }
 * 
 * public String getCallbackStsPending() { return callbackStsPending; }
 * 
 * public void setCallbackStsPending(String callbackStsPending) {
 * this.callbackStsPending = callbackStsPending; }
 * 
 * public String getCallbackStsFailure() { return callbackStsFailure; }
 * 
 * public void setCallbackStsFailure(String callbackStsFailure) {
 * this.callbackStsFailure = callbackStsFailure; }
 * 
 * public String getCallbackStsSucces() { return callbackStsSucces; }
 * 
 * public void setCallbackStsSucces(String callbackStsSucces) {
 * this.callbackStsSucces = callbackStsSucces; }
 * 
 * public String getCallbackStsPendingMsg() { return callbackStsPendingMsg; }
 * 
 * public void setCallbackStsPendingMsg(String callbackStsPendingMsg) {
 * this.callbackStsPendingMsg = callbackStsPendingMsg; }
 * 
 * public String getCallbackStsFailureMsg() { return callbackStsFailureMsg; }
 * 
 * public void setCallbackStsFailureMsg(String callbackStsFailureMsg) {
 * this.callbackStsFailureMsg = callbackStsFailureMsg; }
 * 
 * public String getCallbackStsSuccesMsg() { return callbackStsSuccesMsg; }
 * 
 * public void setCallbackStsSuccesMsg(String callbackStsSuccesMsg) {
 * this.callbackStsSuccesMsg = callbackStsSuccesMsg; }
 * 
 * public String getComplaintsStsPendingCode() { return
 * complaintsStsPendingCode; }
 * 
 * public void setComplaintsStsPendingCode(String complaintsStsPendingCode) {
 * this.complaintsStsPendingCode = complaintsStsPendingCode; }
 * 
 * public String getComplaintsStsFailureCode() { return
 * complaintsStsFailureCode; }
 * 
 * public void setComplaintsStsFailureCode(String complaintsStsFailureCode) {
 * this.complaintsStsFailureCode = complaintsStsFailureCode; }
 * 
 * public String getComplaintsStsSuccesCode() { return complaintsStsSuccesCode;
 * }
 * 
 * public void setComplaintsStsSuccesCode(String complaintsStsSuccesCode) {
 * this.complaintsStsSuccesCode = complaintsStsSuccesCode; }
 * 
 * public String getComplaintsStsInprogressCode() { return
 * complaintsStsInprogressCode; }
 * 
 * public void setComplaintsStsInprogressCode(String
 * complaintsStsInprogressCode) { this.complaintsStsInprogressCode =
 * complaintsStsInprogressCode; }
 * 
 * public String getInvalidJsonFormatErrCode() { return
 * invalidJsonFormatErrCode; }
 * 
 * public void setInvalidJsonFormatErrCode(String invalidJsonFormatErrCode) {
 * this.invalidJsonFormatErrCode = invalidJsonFormatErrCode; }
 * 
 * public String getInvalidJsonFormatErrMsg() { return invalidJsonFormatErrMsg;
 * }
 * 
 * public void setInvalidJsonFormatErrMsg(String invalidJsonFormatErrMsg) {
 * this.invalidJsonFormatErrMsg = invalidJsonFormatErrMsg; }
 * 
 * public String getInvalidIncidentsCountErrCode() { return
 * invalidIncidentsCountErrCode; }
 * 
 * public void setInvalidIncidentsCountErrCode(String
 * invalidIncidentsCountErrCode) { this.invalidIncidentsCountErrCode =
 * invalidIncidentsCountErrCode; }
 * 
 * public String getInvalidIncidentsCountErrMsg() { return
 * invalidIncidentsCountErrMsg; }
 * 
 * public void setInvalidIncidentsCountErrMsg(String
 * invalidIncidentsCountErrMsg) { this.invalidIncidentsCountErrMsg =
 * invalidIncidentsCountErrMsg; }
 * 
 * public String getRequestedStrSizeErrCode() { return requestedStrSizeErrCode;
 * }
 * 
 * public void setRequestedStrSizeErrCode(String requestedStrSizeErrCode) {
 * this.requestedStrSizeErrCode = requestedStrSizeErrCode; }
 * 
 * public String getRequestedStrSizeErrMsg() { return requestedStrSizeErrMsg; }
 * 
 * public void setRequestedStrSizeErrMsg(String requestedStrSizeErrMsg) {
 * this.requestedStrSizeErrMsg = requestedStrSizeErrMsg; }
 * 
 * public String getFailureErrCode() { return failureErrCode; }
 * 
 * public void setFailureErrCode(String failureErrCode) { this.failureErrCode =
 * failureErrCode; }
 * 
 * public String getFailureErrMsg() { return failureErrMsg; }
 * 
 * public void setFailureErrMsg(String failureErrMsg) { this.failureErrMsg =
 * failureErrMsg; }
 * 
 * public String getInvalidInputParamErrCode() { return
 * invalidInputParamErrCode; }
 * 
 * public void setInvalidInputParamErrCode(String invalidInputParamErrCode) {
 * this.invalidInputParamErrCode = invalidInputParamErrCode; }
 * 
 * public String getInvalidInputParamErrMsg() { return invalidInputParamErrMsg;
 * }
 * 
 * public void setInvalidInputParamErrMsg(String invalidInputParamErrMsg) {
 * this.invalidInputParamErrMsg = invalidInputParamErrMsg; }
 * 
 * public String getInvalidIncidentsInputParamErrCode() { return
 * invalidIncidentsInputParamErrCode; }
 * 
 * public void setInvalidIncidentsInputParamErrCode(String
 * invalidIncidentsInputParamErrCode) { this.invalidIncidentsInputParamErrCode =
 * invalidIncidentsInputParamErrCode; }
 * 
 * public String getInvalidIncidentsInputParamErrMsg() { return
 * invalidIncidentsInputParamErrMsg; }
 * 
 * public void setInvalidIncidentsInputParamErrMsg(String
 * invalidIncidentsInputParamErrMsg) { this.invalidIncidentsInputParamErrMsg =
 * invalidIncidentsInputParamErrMsg; }
 * 
 * public String getRrnDuplicateErrCode() { return rrnDuplicateErrCode; }
 * 
 * public void setRrnDuplicateErrCode(String rrnDuplicateErrCode) {
 * this.rrnDuplicateErrCode = rrnDuplicateErrCode; }
 * 
 * public String getRrnDuplicateErrMsg() { return rrnDuplicateErrMsg; }
 * 
 * public void setRrnDuplicateErrMsg(String rrnDuplicateErrMsg) {
 * this.rrnDuplicateErrMsg = rrnDuplicateErrMsg; }
 * 
 *//**
	 * @return the apiFailureErrorCode
	 */
/*
 * public String getApiFailureErrorCode() { return apiFailureErrorCode; }
 * 
 *//**
	 * @param apiFailureErrorCode the apiFailureErrorCode to set
	 */
/*
 * public void setApiFailureErrorCode(String apiFailureErrorCode) {
 * this.apiFailureErrorCode = apiFailureErrorCode; }
 * 
 * 
 * 
 *//**
	 * @return the apiFailureErrorMsg
	 */
/*
 * public String getApiFailureErrorMsg() { return apiFailureErrorMsg; }
 * 
 *//**
	 * @param apiFailureErrorMsg the apiFailureErrorMsg to set
	 */
/*
 * public void setApiFailureErrorMsg(String apiFailureErrorMsg) {
 * this.apiFailureErrorMsg = apiFailureErrorMsg; }
 * 
 *//**
	 * @return the holdApiFailureErrorMsg
	 */
/*
 * public String getHoldApiFailureErrorMsg() { return holdApiFailureErrorMsg; }
 * 
 *//**
	 * @param holdApiFailureErrorMsg the holdApiFailureErrorMsg to set
	 */
/*
 * public void setHoldApiFailureErrorMsg(String holdApiFailureErrorMsg) {
 * this.holdApiFailureErrorMsg = holdApiFailureErrorMsg; }
 * 
 *//**
	 * @return the beneficiaryApiFailureErrorMsg
	 */
/*
 * public String getBeneficiaryApiFailureErrorMsg() { return
 * beneficiaryApiFailureErrorMsg; }
 * 
 *//**
	 * @param beneficiaryApiFailureErrorMsg the beneficiaryApiFailureErrorMsg to set
	 *//*
		 * public void setBeneficiaryApiFailureErrorMsg(String
		 * beneficiaryApiFailureErrorMsg) { this.beneficiaryApiFailureErrorMsg =
		 * beneficiaryApiFailureErrorMsg; }
		 * 
		 * 
		 * 
		 * }
		 */
