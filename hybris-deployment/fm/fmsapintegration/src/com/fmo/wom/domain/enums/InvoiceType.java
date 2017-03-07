package com.fmo.wom.domain.enums;

public enum InvoiceType {

	CREDITMEMO("C", "Credit Memo"), 
	INVOICE("I", "Invoice");
	       
    private String code;
    private String description;

    InvoiceType(final String code, final String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public String getCode() {
        return this.code;
    }

    public static InvoiceType getFromCode(final String aCode) {

        if (aCode == null) return null;
        
        for (InvoiceType aType : InvoiceType.values()) {

            if (aType.getCode().equals(aCode) == true) {
                return aType;
            }
        }

        throw new IllegalStateException("InvoiceType " + aCode + " does not exist.");

    }
}
