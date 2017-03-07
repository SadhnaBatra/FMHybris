package com.fmo.wom.domain.enums;

public enum InvoiceStatus {

	OPEN("O", "Open"), 
	CLOSED("C", "Closed");
	       
    private String code;
    private String description;

    InvoiceStatus(final String code, final String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public String getCode() {
        return this.code;
    }

    public static InvoiceStatus getFromCode(final String aCode) {

        if (aCode == null) return null;
        
        for (InvoiceStatus aStatus : InvoiceStatus.values()) {

            if (aStatus.getCode().equals(aCode) == true) {
                return aStatus;
            }
        }

        throw new IllegalStateException("InvoiceStatus " + aCode + " does not exist.");

    }
}
