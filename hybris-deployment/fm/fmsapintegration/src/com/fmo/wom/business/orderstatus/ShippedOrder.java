package com.fmo.wom.business.orderstatus;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.fmo.wom.domain.OrderUnitBO;
import com.fmo.wom.domain.ShippedOrderBO;
import com.fmo.wom.domain.ShippingUnitBO;

public class ShippedOrder {

	private static Logger logger = Logger.getLogger(ShippedOrder.class);

	private ShippedOrderBO shippedOrderBO;
	private List<OrderShippingUnit> orderShippingUnitList;
	
	
	public ShippedOrder(ShippedOrderBO shippedOrderBO) {
		super();
		logger.debug("shippedOrderBO is "+shippedOrderBO);
		this.shippedOrderBO = shippedOrderBO;
		populateOrderUnitDetails();
	}

	public void populateOrderUnitDetails() {
	    
	    if (this.shippedOrderBO != null) {
            List<OrderUnitBO> orderUnitList = shippedOrderBO.getOrderUnitList();
            if (orderUnitList != null) {
                orderShippingUnitList = new ArrayList<OrderShippingUnit>();
                for (OrderUnitBO orderUnitBO: orderUnitList) {
                    List<ShippingUnitBO> shippingUnitList = orderUnitBO.getShippingUnitList();
                    if (shippingUnitList != null && shippingUnitList.size() > 0) {
                        for (ShippingUnitBO shippingUnitBO : shippingUnitList) {
                            OrderShippingUnit orderShippingUnit = new OrderShippingUnit(orderUnitBO, shippingUnitBO);
                            orderShippingUnitList.add(orderShippingUnit);
                        }
                    } else {
                        // shipping unit list was empty - create a default OrderShippingUnit with the data we have
                        OrderShippingUnit orderShippingUnit = new OrderShippingUnit(orderUnitBO);
                        orderShippingUnitList.add(orderShippingUnit);
                    }
                }
            }
        }
	}
	
	public List<OrderShippingUnit> getOrderShippingUnitList() {
		return orderShippingUnitList == null ? new ArrayList<OrderShippingUnit>(0) : orderShippingUnitList;
	}
	
	public String getCustomerPurchaseOrderNumber() {
		String customerPurchaseOrderNumber = null;
		if (shippedOrderBO != null)	{
			customerPurchaseOrderNumber = shippedOrderBO.getCustomerPurchaseOrderNum();
		}
		logger.debug("CUSTOMER PURCHASE ORDER NUMBER IS "+ customerPurchaseOrderNumber);
		return customerPurchaseOrderNumber == null ? "" : customerPurchaseOrderNumber;
	}

	public String getMasterOrderNumber() {
		String masterOrderNumber = null;
		if (shippedOrderBO != null)	{
			masterOrderNumber = shippedOrderBO.getMasterOrderNumber();
		}
		logger.debug("MASTER ORDER NUMBER IS "+ masterOrderNumber);
		return masterOrderNumber == null ? "" : masterOrderNumber;
	}

	public ShippedOrderBO getShippedOrderBO() {
		return this.shippedOrderBO;
	}

}
