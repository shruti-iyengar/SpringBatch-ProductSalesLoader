package com.javafans.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import com.javafans.common.LoggerConstants;

@Component
public class CustomJobListener implements JobExecutionListener {
	
	Logger logger=LoggerFactory.getLogger(CustomJobListener.class);

	@Override
	public void beforeJob(JobExecution jobExecution) {
		logger.info(LoggerConstants.BEFORE_PRODUCTSALES_LOADER);
		
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		logger.info(LoggerConstants.AFTER_PRODUCTSALES_LOADER);
	}

}
