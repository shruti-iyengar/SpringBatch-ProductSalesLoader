package com.javafans.controller;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javafans.common.BatchConstants;
import com.javafans.common.LoggerConstants;
import com.javafans.listener.CustomJobListener;
import com.javafans.listener.ItemReaderListener;
import com.javafans.listener.ItemWriterListener;
import com.javafans.model.ProductSalesRecord;
import com.javafans.service.CustomWriter;
/*
 * This is the controller class providing API's for processing the ProductSales file
 * 
 */

@RestController
public class ProductSalesLoaderController {

	@Autowired
	JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	JobLauncher jobLauncher;
	
	@Autowired
	CustomJobListener listener;

	@Autowired
	StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	ApplicationContext context;
	
	@Autowired
	ItemReaderListener readerListener;
	
	@Autowired
	ItemWriterListener writerListener;
	
	
	@Autowired
	CustomJobListener jobListener;
	
	@Value("classPath:/ProductSales/ProductSales.csv")
	Resource resource;
	
	Logger logger=LoggerFactory.getLogger(ProductSalesLoaderController.class);
	
	@RequestMapping("/processSalesFile")
	public void processProductSalesFile() {
		Step fileProcessingStep = null;
		/**
		 * FlatFileItemReader reads the file and return a list of ProductSalesRecord
		 * Objects
		 */
		FlatFileItemReader<ProductSalesRecord> reader = (FlatFileItemReader) context.getBean("flatFileItemReader");
		reader.setResource(resource);

		/**
		 * CompositeItemWriter consists of multiple writer , mongowriter and mysql
		 * writers which write to respective tables
		 **/

		CompositeItemWriter<ProductSalesRecord> compositeWriter = (CompositeItemWriter) context
				.getBean("CompositeItemWriter");
		
		fileProcessingStep = stepBuilderFactory.get(BatchConstants.PRODUCT_SALES_LOADER_STEP)
				.<ProductSalesRecord, ProductSalesRecord>chunk(5).reader(reader).writer(compositeWriter)
				.listener(readerListener).listener(writerListener).build();

		Job processProductSalesJob = jobBuilderFactory.get(BatchConstants.PRODUCT_SALES_LOADER_JOB)
				.listener(jobListener).incrementer(new RunIdIncrementer()).flow(fileProcessingStep).end().build();

		try {
			jobLauncher.run(processProductSalesJob, getJobParameters());
		} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
				| JobParametersInvalidException e) {
			logger.error(LoggerConstants.EXCEPTION_DURING_JOB_EXECUTION + e.getMessage());
		}

	}
	
	public JobParameters getJobParameters(){
		JobParametersBuilder builder=new JobParametersBuilder();
		builder.addLong(BatchConstants.RUN_ID,System.currentTimeMillis());
		builder.addString(BatchConstants.JOB_NAME, resource.getFilename());
		return builder.toJobParameters();
	}
}
