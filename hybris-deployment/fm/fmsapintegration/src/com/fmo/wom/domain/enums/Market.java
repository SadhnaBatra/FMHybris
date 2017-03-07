package com.fmo.wom.domain.enums;

import com.fmo.wom.domain.enums.ExternalSystem;

public enum Market {
   
    US_CANADA("USCAN", ExternalSystem.NABS, "uscan", "na"),
    BPCS("BPCS", ExternalSystem.BPCS, "bpcs", "ex"),
    ECONNECT("ECON", ExternalSystem.EVRST, "econnect", "eu"),
    MEXICO("MEX", ExternalSystem.RP1, "mexico", "mx");
    
    private String code;
    private ExternalSystem externalSystem;
    /* database Property table property name prefix */
    private String propertyTablePrefix;
    /* short form of code for use in URL */
    private String shortCode;
    
    private Market(String code, ExternalSystem externalSystem, String propertyTablePrefix, String shortCode) {
        this.code = code;
        this.externalSystem = externalSystem;
        this.propertyTablePrefix = propertyTablePrefix;
        this.shortCode = shortCode;
    }
    
    public String getCode() {
        return code;
    }
    
    public ExternalSystem getExternalSystem() {
    	return externalSystem;
    }
 
    public String getPropertyTablePrefix() {
		return propertyTablePrefix;
	}

    public String getShortCode() {
    	return shortCode;
    }

	public static Market getFromCode(final String aCode) {

        if (aCode == null) return null;
        
        for (Market aMarket : Market.values()) {

            if (aMarket.getCode().equals(aCode) == true) {
                return aMarket;
            }
        }

        throw new IllegalStateException("Market " + aCode + " does not exist.");

    }

}