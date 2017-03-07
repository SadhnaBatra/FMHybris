package com.fmo.wom.domain.enums;

public enum ExternalSystem {

    NABS(true),
    EVRST(false),
    RP1(false),
    BPCS(true),
    NONE(true);

    private final boolean productFlagAccepted;
    
    private ExternalSystem(boolean acceptsProductFlag) {
        this.productFlagAccepted = acceptsProductFlag;
    }
    
    /** Currently the min db column size housing this attribute is varchar2(5), so we can use the enum name.  However, there
     * is no guarantee this is going to remain the case, nor do I want the name of the enum to be dictated by this restriction.
     * Thus I'm adding the getCode() and getFromCode() methods here for use by everyone in case we ever need to switch to it.
     * These are currently implemented with name() but a code attribute of the proper size can be added at any time and
     * no code mapping this enum to the database need be changed.
     */
    
    
    public String getCode() {
        return name();
    }
    
    public boolean isProductFlagAccepted() {
        return productFlagAccepted;
    }

    public static ExternalSystem getFromCode(final String aCode) {

        if (aCode == null)
            return null;

        for (ExternalSystem aStatus : ExternalSystem.values()) {

            if (aStatus.getCode().equals(aCode) == true) {
                return aStatus;
            }
        }

        throw new IllegalStateException("ExternalSystem " + aCode + " does not exist.");

    }
}
