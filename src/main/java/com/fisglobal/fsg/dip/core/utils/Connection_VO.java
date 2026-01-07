package com.fisglobal.fsg.dip.core.utils;

import java.io.Serializable;

import org.springframework.stereotype.Component;
@Component
public class Connection_VO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int maxConnCount;
	private int maxPerRoute;
	private int maxConnPerHost;
	private int soTimeOut;
	private int connTimeOut;
	private int port;
	private String endUrl;
	private String sslVersion;
	private String jkspath;
	private String jksfileSecureTerm;
	
	public String getEndUrl(){return endUrl;}
	public void setEndUrl(String endUrl){this.endUrl=endUrl;}
	private String host;public int getMaxConnCount(){return maxConnCount;}
	public void setMaxConnCount(int maxConnCount){this.maxConnCount=maxConnCount;}
	public int getMaxPerRoute(){return maxPerRoute;}
	public void setMaxPerRoute(int maxPerRoute){this.maxPerRoute=maxPerRoute;}
	public int getMaxConnPerHost(){return maxConnPerHost;}
	public void setMaxConnPerHost(int maxConnPerHost){this.maxConnPerHost=maxConnPerHost;}
	public int getSoTimeOut(){return soTimeOut;}
	public void setSoTimeOut(int soTimeOut){this.soTimeOut=soTimeOut;}
	public int getConnTimeOut(){return connTimeOut;}
	public void setConnTimeOut(int connTimeOut){this.connTimeOut=connTimeOut;}
	public int getPort(){return port;}
	public void setPort(int port){this.port=port;}
	public String getHost(){return host;}
	public void setHost(String host){this.host=host;}
	public String getSslVersion(){return sslVersion;}
	public void setSslVersion(String sslVersion){this.sslVersion=sslVersion;}
	public String getJkspath(){return jkspath;}
	public void setJkspath(String jkspath){this.jkspath=jkspath;}
	public String getJksfileSecureTerm() {return jksfileSecureTerm;}
	public void setJksfileSecureTerm(String jksfileSecureTerm) {this.jksfileSecureTerm = jksfileSecureTerm;}
	
}
