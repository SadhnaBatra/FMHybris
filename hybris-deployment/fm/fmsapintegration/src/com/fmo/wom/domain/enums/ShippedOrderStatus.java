
package com.fmo.wom.domain.enums;

public enum ShippedOrderStatus  {
    
    IN_PROCESS,
    CANCELLED,
    SHIPPED,
    BACKORDER,
    CREDIT_INVOICED;
    
//
//    private String code;
//    private String description;
//
//    ShippedOrderStatus(final String code, final String description) {
//        this.code = code;
//        this.description = description;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public String getCode() {
//        return this.code;
//    }
//
//    public static ShippedOrderStatus getFromCode(final String aCode) {
//
//        if (aCode == null) return null;
//        
//        for (ShippedOrderStatus aStatus : ShippedOrderStatus.values()) {
//
//            if (aStatus.getCode().equals(aCode) == true) {
//                return aStatus;
//            }
//        }
//
//        throw new IllegalStateException("ShippedOrderStatus " + aCode + " does not exist.");
//    }



}
