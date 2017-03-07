package com.fmo.wom.domain;

import java.io.Serializable;
import java.util.List;

import com.fmo.wom.domain.enums.OrderType;

public class PossibleOrderTypeBO implements Serializable {

    private OrderType orderType;
    private boolean allowed;
    private String reasonKey;
    
    public OrderType getOrderType() {
        return orderType;
    }
    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }
    public boolean isAllowed() {
        return allowed;
    }
    public void setAllowed(boolean allowed) {
        this.allowed = allowed;
    }
    public String getReasonKey() {
        return reasonKey;
    }
    public void setReasonKey(String reasonKey) {
        this.reasonKey = reasonKey;
    }
    
    /**
     * Set any order types that are not stock to disallowed if they are not already disallowed.  This
     * means already disallowed types will retain their reason key
     * @param possibleOrderTypes
     * @param reasonKey
     */
    public static void disallowNonStock(List<PossibleOrderTypeBO> possibleOrderTypes, String reasonKey) {
        
        if (possibleOrderTypes == null) return;
        
        for (PossibleOrderTypeBO aPossible : possibleOrderTypes) {
            if (aPossible.getOrderType().isStock() == false && aPossible.isAllowed() == true) {
                aPossible.setAllowed(false);
                aPossible.setReasonKey(reasonKey);
            }
        }
    }
    
}
