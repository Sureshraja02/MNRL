package com.fisglobal.fsg.dip.core.config;

import java.util.concurrent.Executor;

import javax.inject.Inject;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfiguration implements AsyncConfigurer{
	 @Inject
	 private Environment env;
	 
	 	@Value("${async.thread.coresize:20}")
		private int inwardCoreSize;

		@Value("${async.thread.maxsize:30}")
		private int inwardMaxSize;
		
		@Value("${async.thread.name:async-executor-}")
		private String threadName;
		
		//@Value("${async.queue.capacity:50}")
		//private int queueCapacity;

	 @Override
	    public Executor getAsyncExecutor() {
	        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
	        taskExecutor.setMaxPoolSize(inwardMaxSize);
	        taskExecutor.setCorePoolSize(inwardCoreSize);
	        taskExecutor.setThreadNamePrefix(threadName);
	      //  taskExecutor.setQueueCapacity(queueCapacity);
	        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
	        taskExecutor.initialize();
	        return taskExecutor;
	    }
	    
	    @Override
	    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
	        return new SimpleAsyncUncaughtExceptionHandler();
	    }
}

