package com.fisglobal.fsg.dip.core.comm.beans;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;




public class RouterGroup_VO {

	private String name;
	private List<Router_VO> routerList;
	private ApplicationContext applicationContext;
	private ThreadPoolTaskExecutor pool;
	private String formatterType;
	private List<Integer> sensitiveDE;

	public List<Integer>getSensitiveDE(){return sensitiveDE;}
	public void setSensitiveDE(List<Integer>sensitiveDE){this.sensitiveDE=sensitiveDE;}
	public String getName(){return name;}
	public void setName(String name){this.name=name;}
	public List<Router_VO>getRouterList(){return routerList;}
	public void setRouterList(List<Router_VO>routerList){this.routerList=routerList;}
	public String getFormatterType(){return formatterType;}
	public void setFormatterType(String formatterType){this.formatterType=formatterType;}
	public ApplicationContext getApplicationContext(){return applicationContext;}
	public void setApplicationContext(ApplicationContext applicationContext){this.applicationContext=applicationContext;}
	public ThreadPoolTaskExecutor getPool(){return pool;}
	public void setPool(ThreadPoolTaskExecutor pool){this.pool=pool;}
}
