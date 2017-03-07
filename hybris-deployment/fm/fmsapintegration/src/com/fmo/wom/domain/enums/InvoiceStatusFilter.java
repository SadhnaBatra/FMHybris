package com.fmo.wom.domain.enums;

public enum InvoiceStatusFilter {

	ALL("A", "All", "all"),
	OPEN("O", "Open", "open"), 
	CLOSED("C", "Closed", "closed");
	       
    private String code;
    private String description;
    private String messageKey;

    InvoiceStatusFilter(final String code, final String description, final String messageKey) {
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
    
    public static InvoiceStatusFilter getFromCode(final String aCode) {

        if (aCode == null) return null;
        
        for (InvoiceStatusFilter aStatus : InvoiceStatusFilter.values()) {

            if (aStatus.getCode().equals(aCode) == true) {
                return aStatus;
            }
        }

        throw new IllegalStateException("InvoiceStatusFilter " + aCode + " does not exist.");

    }
}
