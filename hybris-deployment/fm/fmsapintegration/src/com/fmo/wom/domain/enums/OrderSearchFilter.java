
package com.fmo.wom.domain.enums;

/**
 * Type safe representation of possible order statuses for get shipment
 * input
 */
public enum OrderSearchFilter  {
    
    ALL(GetShipmentOrderStatus.ALL),
    IN_PROCESS(GetShipmentOrderStatus.OPEN),
    COMPLETE(GetShipmentOrderStatus.CLOSED);
    
    private GetShipmentOrderStatus shipmentSearchOrderStatus;
    
    private OrderSearchFilter(GetShipmentOrderStatus shipmentSearchOrderStatus) {
        this.shipmentSearchOrderStatus = shipmentSearchOrderStatus;
    }

    public GetShipmentOrderStatus getShipmentSearchOrderStatus() {
        return shipmentSearchOrderStatus;
    }


}
