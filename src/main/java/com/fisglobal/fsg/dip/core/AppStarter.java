package com.fisglobal.fsg.dip.core;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.fisglobal.fsg.dip.core.comm.config.LogbackConfiguration;

@Component
public class AppStarter implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	LogbackConfiguration lc;
	
	org.slf4j.Logger logger = LoggerFactory.getLogger(AppStarter.class);
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// TODO Auto-generated method stub
		lc.configureLogback();
		System.out.println("======================");
		System.out.println("App Started to Serve");
		System.out.println("======================");
		logger.info("App Started to Serve");
		try {
			// userImpl.createSystemUser("sysadmin", "admin@123");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
