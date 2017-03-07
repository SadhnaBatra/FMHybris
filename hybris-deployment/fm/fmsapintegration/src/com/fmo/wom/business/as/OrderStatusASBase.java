package com.fmo.wom.business.as;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.common.exception.WOMResourceException;
import com.fmo.wom.common.exception.WOMTransactionException;
import com.fmo.wom.domain.AccountBO;
import com.fmo.wom.domain.ShippedOrderBO;
import com.fmo.wom.domain.enums.OrderSearchFilter;

public abstract class OrderStatusASBase implements OrderStatusAS {

    public abstract Logger getLogger();
    
	/* (non-Javadoc)
	 * @see com.fmo.wom.business.as.OrderStatusAS#searchOrders(com.fmo.wom.domain.AccountBO, java.lang.String, java.lang.String, java.util.Date, java.util.Date)
	 */
	public List<ShippedOrderBO> searchOrders(AccountBO account, String masterOrderNumber, 
	                                          String custumerPurchaseOrderNumber, Date startDate, Date endDate, String orderReturnFlag)
                throws WOMResourceException {
	     
	     return searchOrders(account, masterOrderNumber, custumerPurchaseOrderNumber, startDate, endDate, OrderSearchFilter.ALL, orderReturnFlag);
	 }

	/*
	 * (non-Javadoc)
	 * @see com.fmo.wom.business.as.OrderStatusAS#searchOrders(java.util.List, com.fmo.wom.domain.AccountBO, java.lang.String, java.lang.String, java.util.Date, java.util.Date, com.fmo.wom.domain.enums.OrderSearchFilter)
     *
     * Default implementation is going to have to be multiple searches for each bill to with ship to filtering maybe?  That can be
     * for later and for now, we'll just throw an exception.  This indicates this operation is not supported for this market.
	 */
	public List<ShippedOrderBO> searchOrders(List<AccountBO> billToAccounts, AccountBO shipToAccount, String masterOrderNumber, 
                                            String customerPurchaseOrderNumber, Date startDate, Date endDate, 
                                            OrderSearchFilter filter ) 
                    throws WOMResourceException {
	    
	    throw new UnsupportedOperationException("This operation not supported for " + getMarket());
	    
	}

	
	/* (non-Javadoc)
	 * @see com.fmo.wom.business.as.OrderStatusAS#getOrderDetail(java.util.List)
	 */
	public void getOrderDetail(List<ShippedOrderBO> shippedOrderList, String orderRetrunFlag) throws WOMExternalSystemException, WOMTransactionException {
        
        if (shippedOrderList == null) {
            return;
        }
        for (ShippedOrderBO aShippedOrder : shippedOrderList) {
            getOrderDetail(aShippedOrder,orderRetrunFlag);
        }
    }

	   
    protected void logOrderSearchCriteria(AccountBO account, String masterOrderNumber, String customerPurchaseOrderNumber, 
                                          Date startDate, Date endDate, OrderSearchFilter filter) {

        // only construct the message if we're going to log something
    	String criteria = "masterOrderNumber=" + masterOrderNumber + ", customerPurchaseOrderNumber=" + customerPurchaseOrderNumber
                + ", startDate=" + startDate + ", endDate=" + endDate + ", filter=" + filter;
        if (getLogger().isDebugEnabled()) {
            getLogger().debug("Searching for orders for account "+ account.getAccountCode()+ ". Criteria:"+ criteria);
        }
        
    }
}
