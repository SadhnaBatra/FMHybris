package com.fmo.wom.domain.enums;

public enum SearchCriteria {

	STARTS_WITH("1", "Starts with"),
	ENDS_WITH("2", "Ends with"),
	CONTAINS("3", "Contains");
	       
    private String code;
    private String description;

    SearchCriteria(final String code, final String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public String getCode() {
        return this.code;
    }

    public static SearchCriteria getFromCode(final String aCode) {

        if (aCode == null) return null;
        
        for (SearchCriteria aStatus : SearchCriteria.values()) {

            if (aStatus.getCode().equals(aCode) == true) {
                return aStatus;
            }
        }

        throw new IllegalStateException("SearchCriteria " + aCode + " does not exist.");

    }
}
