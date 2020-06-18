package com.javafans.model;

import lombok.Data;

@Data
public class ProductSalesRecord {
	
	String invoiceNo;
	
	String stockCode;
	
	String description;
	
	String quantity;
	
	String invoiceDate;
	
	String unitPrice;
	
	String customerID;
	
	String country;

	@Override
	public String toString(){
		return "invoiceNo: "+invoiceNo+" stockCode: "+stockCode+" description: "+description+" quantity: "+quantity;
	}
	
}
