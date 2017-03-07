package com.fmo.wom.domain.enums;

public enum UsageType {

    QUOTE("Q"), 
    PURCHASE("P");
	       
    private String code;
    
    UsageType(final String code) {
        this.code = code;
    }
    
    public String getCode() {
        return this.code;
    }
    
    public static UsageType getFromCode(final String aCode) {
        
        if (aCode == null) return null;
        
        for (UsageType aStatus : UsageType.values()) {

            if (aStatus.getCode().equals(aCode) == true) {
                return aStatus;
            }
        }

        throw new IllegalStateException("UsageType " + aCode + " does not exist.");

    }
}
