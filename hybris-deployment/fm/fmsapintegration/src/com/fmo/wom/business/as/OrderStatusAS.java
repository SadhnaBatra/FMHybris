package com.fmo.wom.business.as;

import java.util.Date;
import java.util.List;

import com.fmo.wom.business.as.config.ConfigurableAS;
import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.common.exception.WOMNoResultException;
import com.fmo.wom.common.exception.WOMNonUniqueResultException;
import com.fmo.wom.common.exception.WOMResourceException;
import com.fmo.wom.common.exception.WOMTransactionException;
import com.fmo.wom.domain.AccountBO;
import com.fmo.wom.domain.ShippedOrderBO;
import com.fmo.wom.domain.enums.OrderSearchFilter;

public interface OrderStatusAS extends ConfigurableAS {

    /**
     * Search for all orders for the given account number and search criteria.  All params are optional.  Population
     * of master order number causes date range to be ignored.
     * @param account  account to search for orders. required
     * @param masterOrderNumber master order number to search for 
     * @param custumerPurchaseOrderNumber customer order number to search for.  if master order number supplied, this
     * is ignored but results may be filtered based on it.
     * @param startDate date range to start search
     * @param endDate end of the date range for search
     * @return list of shipped orders matching the search criteria.  
     * @throws WOMResourceException
     */
    public List<ShippedOrderBO> searchOrders(AccountBO account, String masterOrderNumber, 
                                             String custumerPurchaseOrderNumber, Date startDate, Date endDate, String orderReturnFlag)
            throws WOMResourceException;
    
    /**
     * Search for all orders for the given account number and search criteria.  All params are optional.  Population
     * of master order number causes date range to be ignored.
     * @param account  account to search for orders. required
     * @param masterOrderNumber master order number to search for 
     * @param custumerPurchaseOrderNumber customer order number to search for.  if master order number supplied, this
     * is ignored but results may be filtered based on it.
     * @param startDate date range to start search
     * @param endDate end of the date range for search
     * @param filter search filter for order status - ALL order, in progress orders, or completed orders.
     * @return list of shipped orders matching the search criteria.  
     * @throws WOMResourceException
     */
    public List<ShippedOrderBO> searchOrders(AccountBO account, String masterOrderNumber, 
                                             String customerPurchaseOrderNumber, Date startDate, Date endDate, 
                                             OrderSearchFilter filter, String orderReturnFlag) 
            throws WOMResourceException;

   
    /**
     * Special API for situations where a user would like to get all orders for the given ship to that were
     * ordered by the list of given bill tos.  This should be used quite rarely - currently not all markets
     * support.
     * @param billToAccounts
     * @param shipToAccount
     * @param masterOrderNumber
     * @param custumerPurchaseOrderNumber
     * @param startDate
     * @param endDate
     * @param filter
     * @return
     * @throws WOMResourceException
     */
    public List<ShippedOrderBO> searchOrders(List<AccountBO> billToAccounts, AccountBO shipToAccount, String masterOrderNumber, 
                                             String custumerPurchaseOrderNumber, Date startDate, Date endDate, 
                                             OrderSearchFilter filter ) 
            throws WOMResourceException;

    
    /**
     * Find order detail for the list of shipped orders.
     * 
     * @param shippedOrderList
     * @return 
     * @throws WOMTransactionException 
     * @throws WOMExternalSystemException 
     * @throws WOMNoResultException 
     * @throws WOMNonUniqueResultException 
     */
    public void getOrderDetail(List<ShippedOrderBO> shippedOrderList, String orderRetrunFlag) 
            throws WOMExternalSystemException, WOMTransactionException;
    
    /**
     * Find order detail for the the given shipped order.
     * 
     * @param shippedOrder
     * @return 
     * @throws WOMTransactionException 
     * @throws WOMExternalSystemException 
     * @throws WOMNoResultException 
     * @throws WOMNonUniqueResultException 
     */
    public void getOrderDetail(ShippedOrderBO shippedOrder, String orderRetrunFlag) 
            throws WOMExternalSystemException, WOMTransactionException;
    
}
