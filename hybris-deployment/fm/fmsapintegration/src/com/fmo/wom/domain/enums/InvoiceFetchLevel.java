package com.fmo.wom.domain.enums;

public enum InvoiceFetchLevel {

	INVOICE("N", "Invoice Level", "invoiceLevel"),
	ORDER("Y", "Order Level", "orderLevel");
	
    private String code;
    private String description;
    private String messageKey;

    InvoiceFetchLevel(final String code, final String description, final String messageKey) {
        this.code = code;
        this.description = description;
        this.messageKey = messageKey;
    }

    public String getDescription() {
        return this.description;
    }

    public String getCode() {
        return this.code;
    }
    
    public String getMessageKey() {
    	return this.messageKey;
    }
    
    public static InvoiceFetchLevel getFromCode(final String aCode) {

        if (aCode == null) return null;
        
        for (InvoiceFetchLevel aStatus : InvoiceFetchLevel.values()) {

            if (aStatus.getCode().equals(aCode) == true) {
                return aStatus;
            }
        }

        throw new IllegalStateException("InvoiceFetchLevel " + aCode + " does not exist.");

    }
}
