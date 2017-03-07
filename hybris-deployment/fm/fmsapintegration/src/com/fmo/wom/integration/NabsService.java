package com.fmo.wom.integration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.common.exception.WOMTransactionException;
import com.fmo.wom.domain.BackOrderBO;
import com.fmo.wom.domain.InvoiceBO;
import com.fmo.wom.domain.ItemBO;
import com.fmo.wom.domain.KitBO;
import com.fmo.wom.domain.OrderBO;
import com.fmo.wom.domain.OrderSearchCriteria;
import com.fmo.wom.domain.OrderUnitBO;
import com.fmo.wom.domain.ShippedOrder;
import com.fmo.wom.domain.ShippedOrderBO;
import com.fmo.wom.domain.ShippedOrderDetail;
import com.fmo.wom.domain.enums.InvoiceStatusFilter;
import com.fmo.wom.domain.enums.InvoiceTypeFilter;
import com.fmo.wom.domain.enums.OrderSearchFilter;
import com.fmo.wom.domain.enums.OrderType;
import com.fmo.wom.integration.nabs.NabsConstants;
import com.fmo.wom.integration.nabs.action.CheckInventoryAction;
import com.fmo.wom.integration.nabs.action.CheckOrderableAction;
import com.fmo.wom.integration.nabs.action.GetMasterOrderNumberAction;
import com.fmo.wom.integration.nabs.action.ProcessOrderAction;
import com.fmo.wom.integration.nabs.action.backorder.GetBackOrdersAction;
import com.fmo.wom.integration.nabs.action.invoice.GetInvoicesAction;
import com.fmo.wom.integration.nabs.action.kitcomponents.GetKitComponentsAction;
import com.fmo.wom.integration.nabs.action.shipment.GetShipmentInfoAction;
import com.fmo.wom.integration.nabs.action.shipment.NABSOrderSearchAction;
import com.fmo.wom.integration.nabs.action.upload.ProcessUploadOrderAction;

public class NabsService {
	
	private GetMasterOrderNumberAction getMasterOrderNumberAction;

	private static Logger logger = Logger.getLogger(NabsService.class);
	/**
     * Makes the DD120 call to NABS to check inventory on the given parts list
     * @param masterOrderNumber the master order number established to be used as part of PK on nabs input/output tables
     * @param itemList the list of parts to check inventory for. These items will be edited directly with relevant inventory data.
     * @param billToAcct bill to account for which to check inventory
     * @param shipToAcct the optional ship to account to use in checking inventory
     * @return the list of parts with inventories/distribution centers populated
     * @throws WOMExternalSystemException
     * @throws WOMTransactionException
     */
    public NabsService() {
    	getMasterOrderNumberAction = new GetMasterOrderNumberAction();
    	getMasterOrderNumberAction.setCallingSystem("WEB       ");
    }
	
    /**
     * Encapsulate the call to get a new Master Order number from "nabs".
     * @return the new master order number
     */
    public String getMasterOrderNumber() {
        String masterOrderNumber = getMasterOrderNumberAction.executeAction();
        logger.debug("Successfully called getMasterOrderNumber().  MasterOrderNumber= "+ masterOrderNumber);
        return masterOrderNumber;
    }
    
	public void checkInventory(String masterOrderNumber, List<ItemBO> itemList, String billToAcctCode, String shipToAcctCode, OrderType orderType) 
		  throws WOMExternalSystemException, WOMTransactionException {

	    logger.debug("Executing checkInventory() for MON= "+ masterOrderNumber);
		CheckInventoryAction checkInventoryAction = new CheckInventoryAction();
        checkInventoryAction.executeAction(masterOrderNumber, itemList, transformAccountCode(billToAcctCode), transformShipToCode(billToAcctCode, shipToAcctCode), orderType);
	}
	
	private String transformAccountCode(String nabsAcctCode) {
	    if ("IWOMS".equals(nabsAcctCode)) return "HQ863";
	        
	    if ("ECONN".equals(nabsAcctCode)) return "11423";
	        
	    return nabsAcctCode;
	}
	
	private String transformShipToCode(String billToAcctCode, String shipToAcctCode) {
	    if ("IWOMS".equals(billToAcctCode) || "ECONN".equals(billToAcctCode)) { 
	        return null;
	    }
	    else if (NabsConstants.NABS_MANUAL_SHIP_TO_CODE.equals(shipToAcctCode)) {
	        return billToAcctCode;
	    } else {
	        return shipToAcctCode;
	    }
	}
	
	/**
     * Makes the DD114 call to NABS to resolve the given parts list
     * @param masterOrderNumber the master order number established to be used as part of PK on nabs input/output tables
     * @param itemList list of parts to resolve
     * @param account the Bill To Account for which to resolve the parts
     * @param shipToAcct the optional ship to account to use in resolving the parts
     * @return List of fully resolved parts via NABS
     * @throws WOMExternalSystemException
     * @throws WOMTransactionException
     */
    public List<ItemBO> checkOrderable(String masterOrderNumber, List<ItemBO> itemList, String billToAcctCode, String shipToAcctCode)
        throws WOMExternalSystemException, WOMTransactionException {  

        // make the call and return the results
        logger.debug("Executing checkOrderable() for MON= "+ masterOrderNumber);
    	CheckOrderableAction checkOrderableAction = new CheckOrderableAction();
        return checkOrderableAction.executeAction(masterOrderNumber, itemList, transformAccountCode(billToAcctCode), transformShipToCode(billToAcctCode, shipToAcctCode) );
	}
    
    /** Call DD125 and execute process changes on the given order object
     * @param order order to execute
     * @return recreated order object based on the response from DD125
     * @throws WOMExternalSystemException
     * @throws WOMTransactionException
     */
    public OrderBO executeProcessChanges(OrderBO order) throws WOMExternalSystemException, WOMTransactionException {
    	ProcessOrderAction processOrderAction   = new ProcessOrderAction(); 
    	OrderBO orderBO = processOrderAction.executeProcessChanges(order);
    	processOrderAction.releaseResource();
	    return orderBO;
	}
    
    /** Call DD125 and execute initial assignment on the given order object
	 * @param order order to execute
	 * @return recreated order object based on the response from DD125
	 * @throws WOMExternalSystemException
	 * @throws WOMTransactionException
	 */
	public OrderBO executeInitialAssignment(OrderBO order) throws WOMExternalSystemException, WOMTransactionException {
		ProcessOrderAction processOrderAction   = new ProcessOrderAction();
		OrderBO orderBO = processOrderAction.executeInitialAssignment(order);
		processOrderAction.releaseResource();
	    return orderBO;
	    
	}
	    
    /** Call DD125 and execute book on the given order object
     * @param order order to execute
     * @return recreated order object based on the response from DD125
     * @throws WOMExternalSystemException
     * @throws WOMTransactionException
     */
    public OrderBO executeBook(OrderBO order) throws WOMExternalSystemException, WOMTransactionException {
    	ProcessOrderAction processOrderAction   = new ProcessOrderAction();  
    	OrderBO orderBO = processOrderAction.executeBook(order);
    	processOrderAction.releaseResource();
	    return orderBO;
	}
    
    /** Call DD125 and cancel the given order object
     * @param order to cancel
     * @return recreated order object based on the response from DD125 cancel
     * @throws WOMExternalSystemException
     * @throws WOMTransactionException
     */
    public OrderBO executeCancel(OrderBO order) throws WOMExternalSystemException, WOMTransactionException {
    	ProcessOrderAction processOrderAction   = new ProcessOrderAction();  
    	OrderBO orderBO = processOrderAction.executeCancel(order);
    	processOrderAction.releaseResource();
	    return orderBO;     
	}
    
    /**
     * Fetch Back Orders for passed-in NABS Account Code.
     * @param accountCode
     * @return <code>List&lt;BackOrderBO&gt;<code>
     * @throws WOMExternalSystemException
     * @throws WOMTransactionException
     */
	public List<BackOrderBO> getBackOrders(String accountCode) 
            throws WOMExternalSystemException, WOMTransactionException  {
		GetBackOrdersAction getBackOrdersAction = new GetBackOrdersAction();
		return getBackOrdersAction.executeAction(accountCode);
	}
	
	/**
     * Perform order search on the given criteria.  Account code is required.
     * @param accountCode
     * @param customerPONumber
     * @param masterOrderNumber
     * @return list of shipped orders matching the criteria, with no details populated
     * @throws WOMExternalSystemException
     * @throws WOMTransactionException
     */
    public List<ShippedOrderBO> getShipmentInfo(String accountCode, String customerPONumber, 
                                                String masterOrderNumber, Date startDate, Date endDate, OrderSearchFilter filter) 
            throws WOMExternalSystemException, WOMTransactionException  {
    	GetShipmentInfoAction getShipmentInfoAction = new GetShipmentInfoAction();
    	List<ShippedOrderBO> shippedOrderBOList = getShipmentInfoAction.executeOrderSearch(accountCode, customerPONumber, masterOrderNumber, startDate, endDate, filter); 
        getShipmentInfoAction.releaseResource();
    	return shippedOrderBOList;
        
    }
    
    
    public List<ShippedOrder> searchNabsOrders(OrderSearchCriteria anOrderSearchCriteria) throws WOMExternalSystemException, WOMTransactionException {
		logger.info("Searhing NABS orders "+anOrderSearchCriteria);
		NABSOrderSearchAction nabsOrderSearchAction = new NABSOrderSearchAction();
		List<ShippedOrder> nabsOrdersList = nabsOrderSearchAction.searchOrders(anOrderSearchCriteria);
		int noOfNABSOrders = nabsOrdersList != null ?nabsOrdersList.size():0;
		logger.info(noOfNABSOrders+" NABS Orders Found for "+anOrderSearchCriteria);
		nabsOrderSearchAction.releaseResource();
    	return nabsOrdersList;
	}
    
    public ShippedOrderDetail getNABSOrderDetail(String anAccountCode, String anOrderConfirmationNumber, String anOrderNumber) throws WOMExternalSystemException, WOMTransactionException {
    	logger.info("START :: NABS Order Detail Account Code= "+anAccountCode+" Order Confirmation Number= "+anOrderConfirmationNumber+" Order  Number= "+anOrderNumber);
    	NABSOrderSearchAction nabsOrderSearchAction = new NABSOrderSearchAction();
    	ShippedOrderDetail nabsOrderDetail = nabsOrderSearchAction.getOrderDetail(anAccountCode,anOrderConfirmationNumber,anOrderNumber);
    	nabsOrderSearchAction.releaseResource();
    	logger.info("END :: NABS Order Detail Account Code= "+anAccountCode+" Order Confirmation Number= "+anOrderConfirmationNumber+" Order  Number= "+anOrderNumber);
    	return nabsOrderDetail;
    }
    
    /**
	 * Perform order detail query on the given system order number.
	 * @param accountCode
	 * @param masterOrderNumber
	 * @param systemOrderNumber
	 * @return
	 * @throws WOMExternalSystemException
	 * @throws WOMTransactionException
	 */
	/*public OrderUnitBO getShipmentDetail(String accountCode, String masterOrderNumber, String systemOrderNumber) 
	        throws WOMExternalSystemException, WOMTransactionException  {
   
	    return getShipmentInfoAction.executeOrderDetailSearch(accountCode, masterOrderNumber, systemOrderNumber);
	}*/

	/**
	 * Perform order detail search on all the orders in the given Shipped Order.  Appends order detail information
	 * to the given object.
	 * @param shippedOrder
	 * @throws WOMExternalSystemException
	 * @throws WOMTransactionException
	 */
	public void getShipmentDetail(ShippedOrderBO shippedOrder) 
	    throws WOMExternalSystemException, WOMTransactionException  {
	    
		logger.info(" getShipmentDetail() .. "+shippedOrder);
	    if (shippedOrder == null || shippedOrder.getOrderUnitList() == null) {
            return;
        }
	    
	    GetShipmentInfoAction getShipmentInfoAction = new GetShipmentInfoAction(); 
	    String accountCode = shippedOrder.getBillToAccount().getAccountCode();
	    List<OrderUnitBO> resolvedOrderUnitsList = new ArrayList<OrderUnitBO>();
	    
	    // go through and get the details for each order unit.
	    for (OrderUnitBO anOrderUnit : shippedOrder.getOrderUnitList()) {
	    	logger.info(" NABS Order Details Start ...... "+anOrderUnit);
	        OrderUnitBO orderUnitDetail = getShipmentInfoAction.executeOrderDetailSearch(accountCode, 
	                                                                             shippedOrder.getMasterOrderNumber(), 
	                                                                             anOrderUnit.getOrderNumber());
	        logger.info(" NABS Order Details End ......");
	        logger.info(" orderUnitDetail "+orderUnitDetail);
	        // if nothing was received, the order was not found.  Put the original object on the list
	        // Otherwise, put the returned resolved object on the list.
	        resolvedOrderUnitsList.add( orderUnitDetail != null ? orderUnitDetail : anOrderUnit);
	    }
	    //set order by
	    shippedOrder.setOrderedBy(resolvedOrderUnitsList.get(0).getOrderBy());
	    
	    // set the list
	    shippedOrder.setOrderUnitList(resolvedOrderUnitsList);
	    getShipmentInfoAction.releaseResource();
	}
	
	/**
     * Makes the DD116 call to get kit component info for the given kit
     * @param billToAcctCode account code to check with
     * @param inputKit kit to check
     * @return the kit component info
     * @throws WOMExternalSystemException
     * @throws WOMTransactionException
     */
    public void getKitComponents(String billToAcctCode, KitBO inputKit) 
        throws WOMExternalSystemException, WOMTransactionException {
    	GetKitComponentsAction getKitComponentsAction = new GetKitComponentsAction(); 
        getKitComponentsAction.executeAction(null, inputKit, billToAcctCode);
        getKitComponentsAction.releaseResorce();
    }
    
	/**
	 * Makes the DD116 call to get kit component info for the given kit
	 * @param masterOrderNumber master order number associated with this call
	 * @param billToAcctCode account code to check with
	 * @param inputKit kit to check
	 * @return the kit component info
	 * @throws WOMExternalSystemException
	 * @throws WOMTransactionException
	 */
	public void getKitComponents(String masterOrderNumber, String billToAcctCode, KitBO inputKit) 
        throws WOMExternalSystemException, WOMTransactionException {

	    logger.debug("Getting kit info for kit no="+ inputKit.getDisplayPartNumber());
		GetKitComponentsAction getKitComponentsAction = new GetKitComponentsAction();
	    getKitComponentsAction.executeAction(masterOrderNumber, inputKit, billToAcctCode);
	    getKitComponentsAction.releaseResorce();
	}
	
	
	/**
     * Fetch Invoices for passed-in NABS Account Code and other criteria.
     * @param accountCode	NABS Account Code
     * @param invoiceNumbers	List of zero or upto 10 Invoice Numbers to search for
     * @param customerPurchaseOrderNumber
     * @param startInvoiceDate
     * @param endInvoiceDate
     * @param invoiceType
     * @param invoiceStatus
     * @return <code>List&lt;InvoiceBO&gt;<code>
     * @throws WOMExternalSystemException
     * @throws WOMTransactionException
     */
    public List<InvoiceBO> getInvoices(String accountCode, List<String> invoiceNumbers, 
    								   String customerPurchaseOrderNumber,
    								   Date startInvoiceDate, Date endInvoiceDate,
    								   InvoiceTypeFilter invoiceType, InvoiceStatusFilter invoiceStatus)
    		throws WOMExternalSystemException, WOMTransactionException {
    	GetInvoicesAction getInvoicesAction = new GetInvoicesAction();
    	return getInvoicesAction.executeInvoiceSearch(accountCode, invoiceNumbers, customerPurchaseOrderNumber, 
    											startInvoiceDate, endInvoiceDate, invoiceType, invoiceStatus);
    }

    public int executeUploadOrder(OrderBO order) throws WOMExternalSystemException, WOMTransactionException {
        logger.info("Executing upload order in NABs for cust po no= "+ order.getCustPoNbr());
        ProcessUploadOrderAction processUploadOrderAction = new ProcessUploadOrderAction();
        return processUploadOrderAction.executeAction(order);
    }
    
    public void updateUploadOrderToProcessable(int transactionSequenceId) throws WOMExternalSystemException, WOMTransactionException {
        updateUploadOrder(transactionSequenceId, "NE");
    }
     
    public void cancelUploadOrder(int transactionSequenceId) throws WOMExternalSystemException, WOMTransactionException{
        updateUploadOrder(transactionSequenceId, "CA");
    }
    
    private void updateUploadOrder(int transactionSequenceId, String statusCode) throws WOMExternalSystemException, WOMTransactionException{
        if (transactionSequenceId < 0) {
            // invalid value
            return;
        }
        logger.info("Executing update of NABs uploaded order for transaction sequence id="+ transactionSequenceId+ " to status= "+ statusCode);
        ProcessUploadOrderAction processUploadOrderAction = new ProcessUploadOrderAction();
        processUploadOrderAction.executeUpdate(transactionSequenceId, statusCode);
    }

	
    
}
