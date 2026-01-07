package com.fisglobal.fsg.dip.core.comm.beans;

import java.util.Map;

public class RouterList {
	private Map<String, Router_VO> routerMap;
	public Map<String,Router_VO>getRouterMap(){return routerMap;}
	public void setRouterMap(Map<String,Router_VO>routerMap){this.routerMap=routerMap;}
	public Router_VO getRouter(String key){return routerMap.get(key);}
}
