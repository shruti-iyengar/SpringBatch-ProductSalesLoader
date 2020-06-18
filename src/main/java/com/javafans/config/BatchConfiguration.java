package com.javafans.config;


import java.util.Arrays;

import javax.sql.DataSource;

import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.javafans.common.BatchConstants;
import com.javafans.model.ProductSalesRecord;
import com.javafans.service.CustomWriter;

@Configuration
public class BatchConfiguration {

	@Value("${spring.datasource.url}")
	String jdbcURL;

	@Value("${spring.datasource.username}")
	String userName;

	@Value("${spring.datasource.password}")
	String password;

	@Value("${spring.datasource.driver-class-name}")
	String driverClassName;

	@Bean(name = "mysql")
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().url(jdbcURL).driverClassName(driverClassName).username(userName)
				.password(password).build();
	}

	@Bean
	public JobRepository jobRepository() throws Exception {
		JobRepositoryFactoryBean jobRepositoryFactoryBean = new JobRepositoryFactoryBean();
		jobRepositoryFactoryBean.setDataSource(dataSource());
		jobRepositoryFactoryBean.setTransactionManager(new DataSourceTransactionManager());
		jobRepositoryFactoryBean.setDatabaseType("mysql");
		jobRepositoryFactoryBean.afterPropertiesSet();
		return jobRepositoryFactoryBean.getObject();
	}

	@Bean
	public JobLauncher jobLauncher() throws Exception {
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.setJobRepository(jobRepository());
		jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
		return jobLauncher();
	}
	
	@Bean(name="CustomWriter")
	@Scope("prototype")
	public CustomWriter customWriter() {
		return new CustomWriter();
	}
	
	@Bean(name="mysqlWriter")
	@Scope("prototype")
	public JdbcBatchItemWriter<ProductSalesRecord> mysqlWriter() {
		JdbcBatchItemWriter<ProductSalesRecord> batchItemWriter = new JdbcBatchItemWriter<ProductSalesRecord>();
		batchItemWriter.setItemSqlParameterSourceProvider(
				new BeanPropertyItemSqlParameterSourceProvider<ProductSalesRecord>());
		batchItemWriter.setSql(BatchConstants.PRODUCT_SALES_BILLING_SQL);
		batchItemWriter.setDataSource(dataSource());
		return batchItemWriter;
	}

	@Bean(name = "CompositeItemWriter")
	@Scope("prototype")
	public CompositeItemWriter<ProductSalesRecord> compositeItemWriter() {
		CompositeItemWriter<ProductSalesRecord> writer = new CompositeItemWriter<ProductSalesRecord>();
		writer.setDelegates(Arrays.asList(customWriter(),mysqlWriter()));
		return writer;
	}

}
