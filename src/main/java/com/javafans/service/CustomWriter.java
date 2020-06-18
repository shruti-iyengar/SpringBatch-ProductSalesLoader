package com.javafans.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.javafans.model.ProductSalesRecord;
import com.javafans.repository.ProductSalesRecordRepository;
import com.javafans.repository.ProductSalesService;
/**
 * This class implements a writer which saves in Mongo DB 
 * @author shruti.i
 *
 */
public class CustomWriter implements ItemWriter<ProductSalesRecord>{
	Logger logger=LoggerFactory.getLogger(CustomWriter.class);
	
	@Autowired
	ProductSalesRecordRepository productSalesRecordRepository;
	
	@Override
	public void write(List<? extends ProductSalesRecord> items) throws Exception {
		logger.info("Writing the SalesRecord Details to Mongo DB ");
		productSalesRecordRepository.saveAll(items);
	}

	
}
