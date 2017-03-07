package com.fmo.wom.domain.enums;

public enum OrderStatus {

	  CHECK_INVENTORY("04", "CHECKINVENTORY"), 
	  FULFILLED("05", "FULFILLED"),
	  CANCELLED("06", "CANCELLED"),
	  UNPROCESSED("00", "UNPROCESSED"),
	  CART("99", "CART"),
	  DELETED("88","DELETED");
	       
	    private String code;
	    private String displayKey;

	    OrderStatus(final String code, final String displayKey) {
	        this.code = code;
	        this.displayKey = displayKey;
	    }

	    public String getDisplayKey() {
	        return this.displayKey;
	    }

	    public String getCode() {
	        return this.code;
	    }

	    public static OrderStatus getFromCode(final String aCode) {

	        if (aCode == null) return null;
	        
	        for (OrderStatus aStatus : OrderStatus.values()) {

	            if (aStatus.getCode().equals(aCode) == true) {
	                return aStatus;
	            }
	        }

	        throw new IllegalStateException("OrderStatus " + aCode + " does not exist.");

	    }
	}
