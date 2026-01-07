package com.fisglobal.fsg.dip.core.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.fisglobal.fsg.dip.core.cbs.service.MnrlAsyncService;

@Configuration
public class MnrlConfiguration {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MnrlConfiguration.class);
	
	@Value("${mnrl.mobileenq.thread.coresize:8}")
	private int inwardCoreSize;

	@Value("${mnrl.mobileenq.thread.maxsize:16}")
	private int inwardMaxSize;
	
//	@Value("${mnrl.mobileenq.queue.capacity:50}")
	//private int inwardQueueCapacity;
	
	@Value("${mnrl.mobileenq.thread.name:MnrlmobileenquiryThreads-}")
	private String threadName;
	
	@Value("${fri.mobileenq.thread.coresize:8}")
	private int friCoreSize;

	@Value("${fri.mobileenq.thread.maxsize:16}")
	private int friMaxSize;
	
//	@Value("${mnrl.mobileenq.queue.capacity:50}")
	//private int inwardQueueCapacity;
	
	@Value("${fri.mobileenq.thread.name:FrimobileenquiryThreads-}")
	private String friThreadName;

	@Bean("mnrl-mobile-enquiry")
	public TaskExecutor getInwardExecutor() {
		//LOGGER.info("LOADING");
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(inwardCoreSize);
		executor.setMaxPoolSize(inwardMaxSize);
		//executor.setQueueCapacity(inwardQueueCapacity);
		executor.setWaitForTasksToCompleteOnShutdown(true);
		executor.setThreadNamePrefix(threadName);
		return executor;
	}
	
	@Bean("fri-mobile-enquiry")
	public TaskExecutor getFriExecutor() {
		//LOGGER.info("LOADING");
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(friCoreSize);
		executor.setMaxPoolSize(friMaxSize);
		//executor.setQueueCapacity(inwardQueueCapacity);
		executor.setWaitForTasksToCompleteOnShutdown(true);
		executor.setThreadNamePrefix(friThreadName);
		return executor;
	}
}
