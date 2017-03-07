package com.fmo.wom.domain.enums;

public enum KitComponentInsufficientInventoryStrategy {

    PARTIAL_KIT_ORDERING(true),
    NO_PARTIAL_KIT_ORDERING(false);

    private boolean partialOrderingAllowed;
    
    private KitComponentInsufficientInventoryStrategy(boolean allowsPartial) {
        this.partialOrderingAllowed = allowsPartial;
    }   

    public boolean isPartialOrderingAllowed() {
        return partialOrderingAllowed;  
    }
}
