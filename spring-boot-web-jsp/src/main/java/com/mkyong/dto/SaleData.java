package com.mkyong.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class SaleData {

	private String productCategory;
	
	private String year;
	
	private double saleValue;

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public double getSaleValue() {
		return saleValue;
	}

	public void setSaleValue(double saleValue) {
		this.saleValue = saleValue;
	}

	public void addSaleValue(double saleValue2) {
		this.saleValue += saleValue2;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

}
