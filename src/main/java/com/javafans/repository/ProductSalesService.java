package com.javafans.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javafans.model.ProductSalesRecord;

@Service
public class ProductSalesService {
	
	@Autowired
	ProductSalesRecordRepository productSalesRecordRepository;
	
	
	public void saveAllRecords(List<? extends ProductSalesRecord> items) {
		productSalesRecordRepository.saveAll(items);
	}

}
