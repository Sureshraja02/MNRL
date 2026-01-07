/**
 * 
 */
package com.fisglobal.fsg.dip.core.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fisglobal.fsg.dip.core.service.CommonMethodUtils;

/**
 * @author e5745290
 *
 */
@Component
//@Lazy
public class CallbackScheduler {
	private final Logger LOGGER = LoggerFactory.getLogger(CallbackScheduler.class);
	
	@Autowired
	CommonMethodUtils commonMethodUtils;
	@Scheduled(fixedRateString = "${callback.scheduler.time-millis:5000}")
	public void scheduleTask() {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");
		String strDate = dateFormat.format(new Date());

		System.out.println("DIP - MNRL Application Heart Beat at - [" + strDate + "]");
		LOGGER.info("DIP - MNRL Application Heart Beat at - [" + strDate + "]");
		//commonMethodUtils.toGetRequestMQCount();
		//commonMethodUtils.toGetResposeMQCount();
	}
}
