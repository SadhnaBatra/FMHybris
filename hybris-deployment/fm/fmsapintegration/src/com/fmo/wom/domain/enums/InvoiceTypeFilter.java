package com.fmo.wom.domain.enums;

public enum InvoiceTypeFilter {

	ALL("A", "All", "all"),
	CREDITMEMO("C", "Credit Memo", "creditMemo"), 
	INVOICE("I", "Invoice", "invoice");
	       
    private String code;
    private String description;
    private String messageKey;

    InvoiceTypeFilter(final String code, final String description, final String messageKey) {
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

    public static InvoiceTypeFilter getFromCode(final String aCode) {

        if (aCode == null) return null;
        
        for (InvoiceTypeFilter aType : InvoiceTypeFilter.values()) {

            if (aType.getCode().equals(aCode) == true) {
                return aType;
            }
        }

        throw new IllegalStateException("InvoiceTypeFilter " + aCode + " does not exist.");

    }
}
