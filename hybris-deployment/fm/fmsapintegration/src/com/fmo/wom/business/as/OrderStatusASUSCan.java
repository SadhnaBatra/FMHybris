package com.fmo.wom.business.as;

import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.validator.GenericValidator;

import com.fmo.wom.common.exception.WOMBusinessAccessException;
import com.fmo.wom.common.exception.WOMResourceException;
import com.fmo.wom.common.exception.WOMValidationException;
import com.fmo.wom.domain.AccountBO;
import com.fmo.wom.domain.SapAcctBO;
import com.fmo.wom.domain.ShippedOrderBO;
import com.fmo.wom.domain.enums.OrderSearchFilter;
import com.fmo.wom.integration.SAPService;

public interface OrderStatusASUSCan extends OrderStatusAS {
	
	/**
     * Perform a search and order detail retrieval for all orders for the given Bill-To and Master Order
     * Number and Customer PO Number. If the Master Order Number is provided, the Customer PO Number is ignored
     * i.e. the Master Order Number is treated as the real input to avoid mismatches.
     * 
     * @param accountCode The Account Code to use to retrieve the orders
     * @param masterOrderNumber Master Order Number for the order - can be null/empty
     * @param custPoNumber Customer PO Number for order search - can be null/empty
     * @return List of matching order, with all shipment detail populated, matching the input criteria
     * @throws WOMBusinessAccessException
     * @throws WOMValidationException
     * @throws WOMResourceException
     */
    //@Transactional(propagation=Propagation.REQUIRED)
    public List<ShippedOrderBO> getOrderAndShipmentStatus(String accountCode, String masterOrderNumber, 
            String custPoNumber) 
            throws WOMBusinessAccessException, WOMValidationException, WOMResourceException;

    /**
     * Perform a search and order detail retrieval for all orders for the given NABS Bill-To and Master Order
     * Number and Customer PO Number, and filter by DC. If the Master Order Number is provided, the Customer PO Number 
     * is ignored i.e. the master order number is treated as the real input to avoid mismatches.
     * 
     * @param accountCode The Account Code to use to retrieve the orders
     * @param masterOrderNumber Master Order Number for the order - can be null/empty
     * @param custPoNumber Customer PO Number for order search - can be null/empty
     * @return List of matching orders, with all shipment detail populated, matching the input criteria
     * @throws WOMBusinessAccessException
     * @throws WOMValidationException
     * @throws WOMResourceException
     */
    //@Transactional(propagation=Propagation.REQUIRED)
    public List<ShippedOrderBO> getOrderAndShipmentStatusByDC(String accountCode, String masterOrderNumber, 
            String custPoNumber, String distCenterCode) 
            throws WOMBusinessAccessException, WOMValidationException, WOMResourceException;

    /**
     * @param accountCode
     * @param masterOrderNumber
     * @param custPoNumber
     * @return
     * @throws WOMBusinessAccessException
     * @throws WOMValidationException
     * @throws WOMResourceException
     */
    public List<ShippedOrderBO> getOrderStatusInfo(String accountCode, String masterOrderNumber, String custPoNumber) 
            throws WOMBusinessAccessException, WOMValidationException, WOMResourceException;

    
}
