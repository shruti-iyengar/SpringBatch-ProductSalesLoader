package com.javafans.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.javafans.model.ProductSalesRecord;

@Repository
public interface ProductSalesRecordRepository extends MongoRepository<ProductSalesRecord, String>{

}
