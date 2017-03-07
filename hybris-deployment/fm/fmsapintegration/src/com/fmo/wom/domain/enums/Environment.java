
package com.fmo.wom.domain.enums;

/**
 * Type safe representation of possible Customer biz types
 */
public enum Environment  {
    
    DEV("Development"), 
    TIE("Testing"),
    PROD("Production");
    
    private String description;

    Environment(final String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
