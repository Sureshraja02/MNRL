package com.fisglobal.fsg.dip.core.comm.beans;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


public class Router_VO {

	private String name;
	private String hostName;
	private int port;
	private String instId;
	
	private ThreadPoolTaskExecutor pool;
	private ThreadPoolTaskExecutor callbackpool;
	private ThreadPoolTaskExecutor fileProcesspool;
	
	private String type;
	private String timeZone;
	
	private long echo;
	private long logon;
	private long netTimeOut;
	private boolean networkFlag;
	private boolean binStatus;
	private boolean connStatus;
	private long reconTime;
	private int callbackMaxConnCount;
	private int callbackMaxPerRoute;
	private int callbackMaxConnPerHost;
	private int callbackSoTimeOut;
	private int callbackConnTimeOut;
	private int callbackPort;
	private String callbackhost;
	private String endUrl;
	private String callbacksslversion;
	private String callbackjkspath;
	private String callbackjksSecureTerm;
	private long msgTimeOut;
	
	
	private int middlewareMaxConnCount;
	private int middlewareMaxPerRoute;
	private int middlewareMaxConnPerHost;
	private int middlewareSoTimeOut;
	private int middlewareConnTimeOut;
	private int middlewarePort;
	private String middlewarehost;
	private String middlewareEndUrl;
	private String middlewaresslversion;
	private String middlewarejkspath;
	private String middlewarejksSecureTerm;
	private long middlewareMsgTimeOut;
	
	private String inBoundTopic;
	private String outBoundTopic;
	private String syncInBoundTopic;
	private String syncOutBoundTopic;
	private String reqAQIdentifier;
	private String resAQIdentifier;
	private boolean i4cCallbackURLMidlewareHeadAdding;
	
	public int getCallbackMaxConnCount(){return callbackMaxConnCount;}
	public void setCallbackMaxConnCount(int callbackMaxConnCount){this.callbackMaxConnCount=callbackMaxConnCount;}
	public int getCallbackMaxPerRoute(){return callbackMaxPerRoute;}
	public void setCallbackMaxPerRoute(int callbackMaxPerRoute){this.callbackMaxPerRoute=callbackMaxPerRoute;}
	public int getCallbackMaxConnPerHost(){return callbackMaxConnPerHost;}
	public void setCallbackMaxConnPerHost(int callbackMaxConnPerHost){this.callbackMaxConnPerHost=callbackMaxConnPerHost;}
	public int getCallbackSoTimeOut(){return callbackSoTimeOut;}
	public void setCallbackSoTimeOut(int callbackSoTimeOut){this.callbackSoTimeOut=callbackSoTimeOut;}
	public int getCallbackConnTimeOut(){return callbackConnTimeOut;}
	public void setCallbackConnTimeOut(int callbackConnTimeOut){this.callbackConnTimeOut=callbackConnTimeOut;}
	public int getCallbackPort(){return callbackPort;}
	public void setCallbackPort(int callbackPort){this.callbackPort=callbackPort;}
	public String getCallbackhost(){return callbackhost;}
	public void setCallbackhost(String callbackhost){this.callbackhost=callbackhost;}
	public String getEndUrl(){return endUrl;}
	public void setEndUrl(String endUrl){this.endUrl=endUrl;}
	private BufferedOutputStream outputStream;private BufferedInputStream inputStream;private Map<String,Byte>map=new ConcurrentHashMap<String,Byte>();private String formatterType;private ExecutorService executor=Executors.newSingleThreadExecutor();
	public String getFormatterType(){return formatterType;}
	public void setFormatterType(String formatterType){this.formatterType=formatterType;}
	public boolean isNetworkFlag(){return networkFlag;}
	public void setNetworkFlag(boolean networkFlag){this.networkFlag=networkFlag;}
	public String getName(){return name;}
	public void setName(String name){this.name=name;}
	public String getHostName(){return hostName;}
	public void setHostName(String hostName){this.hostName=hostName;}
	public int getPort(){return port;}
	public void setPort(int port){this.port=port;}
	public String getType(){return type;}
	public void setType(String type){this.type=type;}
	public String getTimeZone(){return timeZone;}
	public void setTimeZone(String timeZone){this.timeZone=timeZone;}
	public long getEcho(){return echo;}
	public void setEcho(long echo){this.echo=echo;}
	public long getLogon(){return logon;}
	public void setLogon(long logon){this.logon=logon;}
	public long getNetTimeOut(){return netTimeOut;}
	public void setNetTimeOut(long netTimeOut){this.netTimeOut=netTimeOut;}
	public boolean isBinStatus(){return binStatus;}
	public void setBinStatus(boolean binStatus){this.binStatus=binStatus;}
	public boolean isConnStatus(){return connStatus;}
	public void setConnStatus(boolean connStatus){this.connStatus=connStatus;}
	public long getReconTime(){return reconTime;}
	public void setReconTime(long reconTime){this.reconTime=reconTime;}
	public BufferedOutputStream getOutputStream(){return outputStream;}
	public void setOutputStream(BufferedOutputStream outputStream){this.outputStream=outputStream;}
	public BufferedInputStream getInputStream(){return inputStream;}
	public void setInputStream(BufferedInputStream inputStream){this.inputStream=inputStream;}
	public Map<String,Byte>getMap(){return map;}
	public void setMap(Map<String,Byte>map){this.map=map;}
	public ThreadPoolTaskExecutor getPool(){return pool;}
	public void setPool(ThreadPoolTaskExecutor pool){this.pool=pool;}
	public ExecutorService getExecutor(){return executor;}
	public void setExecutor(ExecutorService executor){this.executor=executor;}
	public long getMsgTimeOut(){return msgTimeOut;}
	public void setMsgTimeOut(long msgTimeOut){this.msgTimeOut=msgTimeOut;}
	public String getInstId(){return instId;}
	public void setInstId(String instId){this.instId=instId;}
	public String getCallbacksslversion(){return callbacksslversion;}
	public void setCallbacksslversion(String callbacksslversion){this.callbacksslversion=callbacksslversion;}
	public String getCallbackjkspath(){return callbackjkspath;}
	public void setCallbackjkspath(String callbackjkspath){this.callbackjkspath=callbackjkspath;}
	
	public String getCallbackjksSecureTerm() {return callbackjksSecureTerm;}
	public void setCallbackjksSecureTerm(String callbackjksSecureTerm) {this.callbackjksSecureTerm = callbackjksSecureTerm;}
	public int getMiddlewareMaxConnCount() {
		return middlewareMaxConnCount;
	}
	public void setMiddlewareMaxConnCount(int middlewareMaxConnCount) {
		this.middlewareMaxConnCount = middlewareMaxConnCount;
	}
	public int getMiddlewareMaxPerRoute() {
		return middlewareMaxPerRoute;
	}
	public void setMiddlewareMaxPerRoute(int middlewareMaxPerRoute) {
		this.middlewareMaxPerRoute = middlewareMaxPerRoute;
	}
	public int getMiddlewareMaxConnPerHost() {
		return middlewareMaxConnPerHost;
	}
	public void setMiddlewareMaxConnPerHost(int middlewareMaxConnPerHost) {
		this.middlewareMaxConnPerHost = middlewareMaxConnPerHost;
	}
	public int getMiddlewareSoTimeOut() {
		return middlewareSoTimeOut;
	}
	public void setMiddlewareSoTimeOut(int middlewareSoTimeOut) {
		this.middlewareSoTimeOut = middlewareSoTimeOut;
	}
	public int getMiddlewareConnTimeOut() {
		return middlewareConnTimeOut;
	}
	public void setMiddlewareConnTimeOut(int middlewareConnTimeOut) {
		this.middlewareConnTimeOut = middlewareConnTimeOut;
	}
	public int getMiddlewarePort() {
		return middlewarePort;
	}
	public void setMiddlewarePort(int middlewarePort) {
		this.middlewarePort = middlewarePort;
	}
	public String getMiddlewarehost() {
		return middlewarehost;
	}
	public void setMiddlewarehost(String middlewarehost) {
		this.middlewarehost = middlewarehost;
	}
	public String getMiddlewareEndUrl() {
		return middlewareEndUrl;
	}
	public void setMiddlewareEndUrl(String middlewareEndUrl) {
		this.middlewareEndUrl = middlewareEndUrl;
	}
	public String getMiddlewaresslversion() {
		return middlewaresslversion;
	}
	public void setMiddlewaresslversion(String middlewaresslversion) {
		this.middlewaresslversion = middlewaresslversion;
	}
	public String getMiddlewarejkspath() {
		return middlewarejkspath;
	}
	public void setMiddlewarejkspath(String middlewarejkspath) {
		this.middlewarejkspath = middlewarejkspath;
	}
	public String getMiddlewarejksSecureTerm() {
		return middlewarejksSecureTerm;
	}
	public void setMiddlewarejksSecureTerm(String middlewarejksSecureTerm) {
		this.middlewarejksSecureTerm = middlewarejksSecureTerm;
	}
	public long getMiddlewareMsgTimeOut() {
		return middlewareMsgTimeOut;
	}
	public void setMiddlewareMsgTimeOut(long middlewareMsgTimeOut) {
		this.middlewareMsgTimeOut = middlewareMsgTimeOut;
	}
	public String getInBoundTopic() {
		return inBoundTopic;
	}
	public void setInBoundTopic(String inBoundTopic) {
		this.inBoundTopic = inBoundTopic;
	}
	public String getOutBoundTopic() {
		return outBoundTopic;
	}
	public void setOutBoundTopic(String outBoundTopic) {
		this.outBoundTopic = outBoundTopic;
	}
	public String getSyncInBoundTopic() {
		return syncInBoundTopic;
	}
	public void setSyncInBoundTopic(String syncInBoundTopic) {
		this.syncInBoundTopic = syncInBoundTopic;
	}
	public String getSyncOutBoundTopic() {
		return syncOutBoundTopic;
	}
	public void setSyncOutBoundTopic(String syncOutBoundTopic) {
		this.syncOutBoundTopic = syncOutBoundTopic;
	}
	public String getReqAQIdentifier() {
		return reqAQIdentifier;
	}
	public void setReqAQIdentifier(String reqAQIdentifier) {
		this.reqAQIdentifier = reqAQIdentifier;
	}
	public String getResAQIdentifier() {
		return resAQIdentifier;
	}
	public void setResAQIdentifier(String resAQIdentifier) {
		this.resAQIdentifier = resAQIdentifier;
	}
	/**
	 * @return the callbackpool
	 */
	public ThreadPoolTaskExecutor getCallbackpool() {
		return callbackpool;
	}
	/**
	 * @param callbackpool the callbackpool to set
	 */
	public void setCallbackpool(ThreadPoolTaskExecutor callbackpool) {
		this.callbackpool = callbackpool;
	}
	/**
	 * @return the fileProcesspool
	 */
	public ThreadPoolTaskExecutor getFileProcesspool() {
		return fileProcesspool;
	}
	/**
	 * @param fileProcesspool the fileProcesspool to set
	 */
	public void setFileProcesspool(ThreadPoolTaskExecutor fileProcesspool) {
		this.fileProcesspool = fileProcesspool;
	}
	public boolean isI4cCallbackURLMidlewareHeadAdding() {
		return i4cCallbackURLMidlewareHeadAdding;
	}
	public void setI4cCallbackURLMidlewareHeadAdding(boolean i4cCallbackURLMidlewareHeadAdding) {
		this.i4cCallbackURLMidlewareHeadAdding = i4cCallbackURLMidlewareHeadAdding;
	}
	
}
