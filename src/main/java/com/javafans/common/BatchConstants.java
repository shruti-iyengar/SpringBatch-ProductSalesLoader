package com.javafans.common;

public class BatchConstants {
	
	public static final String PRODUCT_SALES_LOADER_STEP="ReadWriteProcessProductSales";
	public static final String PRODUCT_SALES_LOADER_JOB="ProductSalesLoaderJob";
	public static final String RUN_ID="run.id";
	public static String JOB_NAME="job.name";
	public static String COMMA=",";
	public static String PRODUCT_SALES_BILLING_SQL="INSERT INTO billing (quantity,unit_price) VALUES(:quantity,:unitPrice)";

}
