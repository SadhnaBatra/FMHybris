
package com.fmo.wom.domain.enums;

/**
 * Type safe representation of possible Customer biz types
 */
public enum CustomerBusinessType  {
    
    JBR("J", "businessTypeJobber"), 
    WD1("D", "businessTypeDistributor"),
    NONE("N/A", "businessTypeNone");
    
    private String code;
    private String displayKey;

    CustomerBusinessType(final String code, final String displayKey) {
        this.code = code;
        this.displayKey = displayKey;
    }

    public String getDisplayKey() {
        return this.displayKey;
    }

    public String getCode() {
        return this.code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public static CustomerBusinessType getFromCode(final String aCode) {

        if (aCode == null) return null;
        
        for (CustomerBusinessType aCBT : CustomerBusinessType.values()) {

            if (aCBT.getCode().equals(aCode) == true) {
                return aCBT;
            }
        }

        throw new IllegalStateException("CustomerBusinessType " + aCode + " does not exist.");

    }

}
