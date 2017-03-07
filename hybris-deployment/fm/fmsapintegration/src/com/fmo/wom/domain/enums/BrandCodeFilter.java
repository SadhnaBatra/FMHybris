package com.fmo.wom.domain.enums;

public enum BrandCodeFilter {

	ALL("ALL", "All");
	       
    private String code;
    private String description;

    BrandCodeFilter(final String code, final String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public String getCode() {
        return this.code;
    }

    public static BrandCodeFilter getFromCode(final String aCode) {

        if (aCode == null) return null;
        
        for (BrandCodeFilter aType : BrandCodeFilter.values()) {

            if (aType.getCode().equals(aCode) == true) {
                return aType;
            }
        }

        throw new IllegalStateException("BrandCodeFilter " + aCode + " does not exist.");

    }
}
