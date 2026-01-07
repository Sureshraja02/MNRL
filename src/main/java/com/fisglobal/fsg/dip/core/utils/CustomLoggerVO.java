package com.fisglobal.fsg.dip.core.utils;

import com.fisglobal.fsg.dip.entity.BaseDTO;

public class CustomLoggerVO extends BaseDTO {
	private static final long serialVersionUID = 1L;
	String instCode;
	String channelId;
	String reqMsgId;
	String serviceName;
	String requestType;
	String requestTime;
	String responseTime;
	String totalResponseTime;
	String requestStatus;
	public String getInstCode(){return instCode;}
	public void setInstCode(String instCode){this.instCode=instCode;}
	public String getChannelId(){return channelId;}
	public void setChannelId(String channelId){this.channelId=channelId;}
	public String getReqMsgId(){return reqMsgId;}
	public void setReqMsgId(String reqMsgId){this.reqMsgId=reqMsgId;}
	public String getServiceName(){return serviceName;}
	public void setServiceName(String serviceName){this.serviceName=serviceName;}
	public String getRequestType(){return requestType;}
	public void setRequestType(String requestType){this.requestType=requestType;}
	public String getRequestTime(){return requestTime;}
	public void setRequestTime(String requestTime){this.requestTime=requestTime;}
	public String getResponseTime(){return responseTime;}
	public void setResponseTime(String responseTime){this.responseTime=responseTime;}
	public String getTotalResponseTime(){return totalResponseTime;}
	public void setTotalResponseTime(String totalResponseTime){this.totalResponseTime=totalResponseTime;}
	public String getRequestStatus(){return requestStatus;}
	public void setRequestStatus(String requestStatus){this.requestStatus=requestStatus;}
}
