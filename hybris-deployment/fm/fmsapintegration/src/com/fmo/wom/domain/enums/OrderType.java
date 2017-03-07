package com.fmo.wom.domain.enums;

public enum OrderType  {

    // This column is 15 chars currently - do not create
    // larger that that.
	EMERGENCY(false, "E", "YEM"),
	STOCK(true, "S", "YOR"),
	REGULAR(true, "R", "YOR"),
	PICKUP(true,"P","YTC");
    
	private boolean stock;
	private String bpcsCode;
	private String econCode;
	
	private OrderType(boolean stock, String bpcsCode, String econCode) {
	    this.stock = stock;
	    this.bpcsCode = bpcsCode;
	    this.econCode = econCode;
	}
	
	public String getBpcsCode() {
	    return bpcsCode;
	}
	
	public String getEconCode() { 
	    return econCode;
	}

    public boolean isStock() {
        return stock;
    }
	
    public static OrderType getFromEconCode(final String aCode) {

        if (aCode == null) return null;
        
        for (OrderType aStatus : OrderType.values()) {

            if (aStatus.getEconCode().equals(aCode) == true) {
                return aStatus;
            }
        }

        throw new IllegalStateException("OrderType " + aCode + " does not exist.");

    }
}

