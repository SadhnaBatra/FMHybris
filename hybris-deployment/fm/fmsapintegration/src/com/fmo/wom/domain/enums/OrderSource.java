package com.fmo.wom.domain.enums;

public enum OrderSource {

	// This column is 10 chars. Do not create larger
	IPO("IPO","ECON"), WEB("WEB","ECON"), UPLOAD("UPLOAD","ECUP"),HYBRIS("ECOM","WEB");

	private String orderSource;
	private String poType;
	

	private OrderSource(String orderSource, String poType) {
		this.orderSource = orderSource;
		this.poType = poType;
	}

	public String getOrderSource() {
		return orderSource;
	}
	
	public String getPoType() {
		return poType;
	}
	
	 public static OrderSource getFromCode(final String aCode) {

	        if (aCode == null) return null;
	        
	        for (OrderSource aSource : OrderSource.values()) {

	            if (aSource.getOrderSource().equals(aCode) == true) {
	                return aSource;
	            }
	        }

	        throw new IllegalStateException("OrderSource " + aCode + " does not exist.");

	    }
}
