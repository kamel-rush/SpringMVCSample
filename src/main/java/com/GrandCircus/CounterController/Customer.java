package com.GrandCircus.CounterController;

import java.io.Serializable;

public class Customer implements Serializable{

	public String getCustomerid() {
		return customerid;
	}
	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}
	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	private String customerid;
	private String companyname; 
	
	
}
