package com.fmo.wom.domain;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import com.fmo.wom.domain.enums.OrderType;

@XmlRootElement
public class ShippedOrderBO {

	private String customerPurchaseOrderNum;
	private String masterOrderNumber;
	private String orderedBy;
	private OrderType orderType;
    
	private AccountBO billToAccount;
	private AccountBO shipToAccount;
	private List<OrderUnitBO> orderUnitList;

	private String returnOrderReason;
	
	public String getCustomerPurchaseOrderNum() {
		return customerPurchaseOrderNum;
	}

	public void setCustomerPurchaseOrderNum(String customerPurchaseOrderNum) {
		this.customerPurchaseOrderNum = customerPurchaseOrderNum;
	}

	public String getMasterOrderNumber() {
		return masterOrderNumber;
	}

	public void setMasterOrderNumber(String masterOrderNumber) {
		this.masterOrderNumber = masterOrderNumber;
	}

	public AccountBO getBillToAccount() {
		return billToAccount;
	}

	public void setBillToAccount(AccountBO billToAccount) {
		this.billToAccount = billToAccount;
	}

	public AccountBO getShipToAccount() {
		return shipToAccount;
	}

	public void setShipToAccount(AccountBO shipToAccount) {
		this.shipToAccount = shipToAccount;
	}

	public List<OrderUnitBO> getOrderUnitList() {
		return orderUnitList;
	}

	public void setOrderUnitList(List<OrderUnitBO> orderUnitList) {
		this.orderUnitList = orderUnitList;
	}

	public String getOrderedBy() {
        return orderedBy;
    }
    public void setOrderedBy(String orderedBy) {
        this.orderedBy = orderedBy;
    }
    public OrderType getOrderType() {
        return orderType;
    }
    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }
    
    

    /** 
     * Utility method to get the order status from the list of shipping units in the 
     * list of order units.  This will become enum based for ease of use, but for the 
     * moment is still String based.
     * The algorithm will roughly be defined with these rules (in order of precedence):
     *  <ul>
     *   <dd> if there is any order unit IN_PROCESS, this shipped order is IN PROGRESS.
     *   <dd> if there are none in IN_PROCESS, but some in BACKORDER, this order is back ordered 
     *   <dd> if there are none in IN_PROCESS, and any order units SHIPPED, this shipped order is SHIPPED
     *   <dd> if ALL order units are CANCELLED, this shipped order is CANCELLED
     *  </ul>
     * //return
     */
    public String getOrderStatus() {
        
        List<OrderUnitBO> orderUnitList = getOrderUnitList();
        if (orderUnitList == null) {
            return null;
        }
        
        boolean foundShipped = false;
        boolean foundCancelled = false;
        boolean foundBackOrder = false;
        
        for (OrderUnitBO anOrderUnit : orderUnitList) {
            
            List<ShippingUnitBO> shippingUnits = anOrderUnit.getShippingUnitList();
            if (shippingUnits == null) {
                continue;
            }
            
            for (ShippingUnitBO aShippingUnit : shippingUnits) {
                if ("IN_PROCESS".equals(aShippingUnit.getPackingStatus())) {
                    // found on in progress - return it
                    return aShippingUnit.getPackingStatus();
                } else if ("BACKORDER".equals(aShippingUnit.getPackingStatus())) {
                    foundBackOrder = true;
                } else if ("CANCELLED".equals(aShippingUnit.getPackingStatus())) {
                    foundCancelled = true;
                }  else if ("SHIPPED".equals(aShippingUnit.getPackingStatus())) {
                    foundShipped = true;
                }
            }
        }
        
        // if we're here, means we didn't find an in process one.
        if (foundBackOrder) { return "BACKORDER"; }
        if (foundShipped) { return "SHIPPED"; }
        if (foundCancelled) { return "CANCELLED"; }
        
        // didn't find anything - could mean this:
        return "IN_PROCESS";
    }
    
    
    /**
     * Utility method to get the "order date" of this shipped order. Because the order
     * date is associated to the underlying order units, this method will return
     * the earliest original order date of all underlying order units.
     * //return
     */
    public Date getOriginalOrderDate() {
        
        List<OrderUnitBO> orderUnitList = getOrderUnitList();
        if (orderUnitList == null) {
            return null;
        }
        
        Date earliestDate = null;
        for (OrderUnitBO anOrderUnit : orderUnitList) {
            if (earliestDate == null) {
                // set it
                earliestDate = anOrderUnit.getOriginalOrderDate();
            } else {
                if (earliestDate.after(anOrderUnit.getOriginalOrderDate())) {
                    earliestDate = anOrderUnit.getOriginalOrderDate();
                }
            }
        }
        
        return earliestDate;
    }
    
    
    public String getReturnOrderReason() {
		return returnOrderReason;
	}

	public void setReturnOrderReason(String returnOrderReason) {
		this.returnOrderReason = returnOrderReason;
	}

    @Override
	public String toString() {
		return "ShippedOrderBO [customerPurchaseOrderNum="
				+ customerPurchaseOrderNum + ", masterOrderNumber="
				+ masterOrderNumber + ", billToAccount=" + billToAccount
				+ ", shipToAccount=" + shipToAccount + ", orderUnitList="
				+ orderUnitList 
				+ "returnOrderReason "+returnOrderReason
				+"]";
	}


}
