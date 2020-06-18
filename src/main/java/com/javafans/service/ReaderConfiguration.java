package com.javafans.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;

import com.javafans.common.BatchConstants;
import com.javafans.model.ProductSalesRecord;

@Configuration
public class  ReaderConfiguration {
	Logger logger=LoggerFactory.getLogger(ReaderConfiguration.class);
	
	Resource resource ;
	
	public void setResource(Resource resource) {
		this.resource = resource;
	}

	@Bean(name="flatFileItemReader")
	@Scope("prototype")
	public FlatFileItemReader<ProductSalesRecord> flatFileItemReader()
	{
		logger.info("Reading File details");
		FlatFileItemReader< ProductSalesRecord> fileItemReader=new FlatFileItemReader<ProductSalesRecord>();
		fileItemReader.setLineMapper(lineMapper());
		fileItemReader.setLinesToSkip(1);
		fileItemReader.setResource(resource);
		return fileItemReader;
	}

	private LineMapper<ProductSalesRecord> lineMapper() {
		DefaultLineMapper<ProductSalesRecord> lineMapper=new DefaultLineMapper<ProductSalesRecord>();
		BeanWrapperFieldSetMapper beanWrapperFieldSetMapper=new BeanWrapperFieldSetMapper<ProductSalesRecord>();
		beanWrapperFieldSetMapper.setTargetType(ProductSalesRecord.class);
		lineMapper.setLineTokenizer(getProductSalesTokenizer());
		lineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);
		return lineMapper;
	}

	private LineTokenizer getProductSalesTokenizer() {
		DelimitedLineTokenizer delimitedLineTokenizer=new DelimitedLineTokenizer();
		delimitedLineTokenizer.setDelimiter(BatchConstants.COMMA);
		delimitedLineTokenizer.setNames(new String[]{"invoiceNo", "stockCode", "description","quantity","invoiceDate","unitPrice","customerID","country"});
		return delimitedLineTokenizer;
	}

}
