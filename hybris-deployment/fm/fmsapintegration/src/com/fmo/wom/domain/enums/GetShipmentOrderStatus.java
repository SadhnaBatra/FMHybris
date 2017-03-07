
package com.fmo.wom.domain.enums;

/**
 * Type safe representation of possible order statuses for get shipment
 * input
 */
public enum GetShipmentOrderStatus  {
    
    ALL("A", "All Orders"),
    OPEN("O", "Open Orders"),
    CLOSED("C", "Closed Orders");

    private String code;
    private String description;

    GetShipmentOrderStatus(final String code, final String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getCode() {
        return this.code;
    }

    public static GetShipmentOrderStatus getFromCode(final String aCode) {

        if (aCode == null) return null;
        
        for (GetShipmentOrderStatus aStatus : GetShipmentOrderStatus.values()) {

            if (aStatus.getCode().equals(aCode) == true) {
                return aStatus;
            }
        }

        throw new IllegalStateException("GetShipmentOrderStatus " + aCode + " does not exist.");
    }



}
