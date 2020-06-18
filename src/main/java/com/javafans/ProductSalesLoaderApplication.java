package com.javafans;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;


@EnableBatchProcessing
@EnableAsync
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
@EnableMongoRepositories(basePackages="com.javafans.repository")
@SpringBootApplication
public class ProductSalesLoaderApplication {
	

	public static void main(String[] args) {
		SpringApplication.run(ProductSalesLoaderApplication.class, args);
	}

}
