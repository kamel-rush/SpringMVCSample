package com.GrandCircus.MyControllers;

public class Customer {

	private String cid;
	
	private String compName;

	public String getCid() {
		return cid;
	}

	public Customer(String cid, String compName) {
		super();
		this.cid = cid;
		this.compName = compName;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	} 
	
	
}
