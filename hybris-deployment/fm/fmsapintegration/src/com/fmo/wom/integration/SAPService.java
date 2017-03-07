package com.fmo.wom.integration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.common.exception.WOMTransactionException;
import com.fmo.wom.common.exception.WOMValidationException;
//import com.fmo.wom.common.util.WomWebProfiling;
import com.fmo.wom.domain.AccountBO;
import com.fmo.wom.domain.AddressBO;
import com.fmo.wom.domain.BackOrderBO;
import com.fmo.wom.domain.CreditCheckBO;
import com.fmo.wom.domain.CustomerSalesOrgBO;
import com.fmo.wom.domain.ECCShippedOrder;
import com.fmo.wom.domain.InvoiceBO;
import com.fmo.wom.domain.InvoiceDetailsBO;
import com.fmo.wom.domain.InvoiceSearchCriteriaBO;
import com.fmo.wom.domain.ItemBO;
import com.fmo.wom.domain.ManualShipToAddressBO;
import com.fmo.wom.domain.OrderBO;
import com.fmo.wom.domain.OrderSearchCriteria;
import com.fmo.wom.domain.OrderUnitBO;
import com.fmo.wom.domain.SalesOrganizationBO;
import com.fmo.wom.domain.ShippedOrder;
import com.fmo.wom.domain.ShippedOrderBO;
import com.fmo.wom.domain.ShippedOrderDetail;
import com.fmo.wom.domain.enums.OrderSearchFilter;
import com.fmo.wom.domain.enums.OrderType;
import com.fmo.wom.integration.sap.action.CheckInventoryAction;
import com.fmo.wom.integration.sap.action.CreditCheckAction;
import com.fmo.wom.integration.sap.action.CustomerPartEditAction;
import com.fmo.wom.integration.sap.action.GetBackOrdersAction;
import com.fmo.wom.integration.sap.action.GetInvoiceDetailsAction;
import com.fmo.wom.integration.sap.action.GetInvoicesAction;
import com.fmo.wom.integration.sap.action.GetShipmentDetailAction;
import com.fmo.wom.integration.sap.action.GetShipmentInfoAction;
import com.fmo.wom.integration.sap.action.ProcessOrderAction;
import com.fmo.wom.integration.sap.action.TSCCheckInventory;
import com.fmo.wom.integration.sap.common.ECCOrderDetailAction;
import com.fmo.wom.integration.sap.common.ECCOrderSearchAction;

//WomWebProfiling
public class SAPService  {
	
	private static Logger logger = Logger.getLogger(SAPService.class);
	
	/**
	 * Perform the call to SAP to resolve the given parts list.  The sales org and given account code
	 * are used to retrieve the data
	 * @param itemList parts list to resolve
	 * @param sapAcctCode SAP account code to get the parts for
	 * @param salesOrg sales organization for which to check the parts
	 * @return the list of resolved parts
	 * @throws WOMExternalSystemException
	 */
	public static List<ItemBO> checkOrderable(List<ItemBO> itemList,String sapAcctCode, CustomerSalesOrgBO salesOrg) 
		throws WOMExternalSystemException, WOMValidationException {

		logger.debug("@@@ Inside checkOrderable @@@");
		
		if (itemList == null || itemList.size() == 0) {
			// no input parts
			return itemList;
		}
		
		if (validateSalesOrgInfo(salesOrg) == false) {
			logger.error("Insufficient sales org info supplied to make SAP call: "+ salesOrg);
			throw new WOMValidationException("Insufficient Sales Org info supplied to SAP call:");
		}
		
		// instantiate action class to execute the call
		logger.debug("Inside SAPService:checkOrderable	# sapAcctCode= "+sapAcctCode+ " salesOrg= "+ salesOrg);
		
		CustomerPartEditAction action = new CustomerPartEditAction(sapAcctCode, salesOrg, itemList);
	    return action.execute();
	}
	
	/**
	 * Make the SAP call to check inventory for the given item list and sap bill to and sap ship to
	 * account codes.
	 * @param itemList list of items to check inventory for
	 * @param sapBillToAcctCode bill to account in sap to use
	 * @param sapShipToAcctCode ship to account in sap to use
	 * @param salesOrg the sales organization info to use
	 * @return The input list of items is augmented in place so nothing is returned.
	 * @throws WOMExternalSystemException
	 * @throws WOMTransactionException
	 */
	public static void checkInventory(List<ItemBO> itemList, String sapBillToAcctCode, 
                                       String sapShipToAcctCode, CustomerSalesOrgBO salesOrg)
         throws WOMExternalSystemException, WOMTransactionException, WOMValidationException {

        if (itemList == null || itemList.size() == 0) {
            // no input parts
            return;
        }
        
        if (validateSalesOrgInfo(salesOrg) == false) {
            logger.error("Insufficient sales org info supplied to make SAP call: "+ salesOrg);
            throw new WOMValidationException("Insufficient Sales Org info supplied to SAP call:");
        }
     
        // instantiate action class to execute the call
        CheckInventoryAction action = new CheckInventoryAction(sapBillToAcctCode,sapShipToAcctCode, salesOrg, itemList);
        logger.debug("Executing checkInventory() for sap account= "+sapBillToAcctCode+ ", sales org= "+salesOrg);
        action.execute();
    }
	
	public static void checkInventory(List<ItemBO> itemList, String sapBillToAcctCode, 
			                          String sapShipToAcctCode, CustomerSalesOrgBO salesOrg, 
								      String orderType, String orderSource/*,
								      String payementMethod*/) throws WOMExternalSystemException, WOMTransactionException, WOMValidationException{
		
		if (itemList == null || itemList.size() == 0) {
            // no input parts
            return;
        }
        
        if (validateSalesOrgInfo(salesOrg) == false) {
            logger.error("Insufficient sales org info supplied to make SAP call: "+ salesOrg);
            throw new WOMValidationException("Insufficient Sales Org info supplied to SAP call:");
        }
     
        // instantiate action class to execute the call
        CheckInventoryAction action = new CheckInventoryAction(sapBillToAcctCode, sapShipToAcctCode, 
        													   salesOrg, itemList, orderType, 
        													   orderSource/*,payementMethod*/);
        logger.debug("Executing checkInventory() for sap account= "+sapBillToAcctCode+ ", sales org= "+ salesOrg);
        action.execute();		
	}
    
	
	public static void tscPickUpcheckInventory(List<ItemBO> itemList, String sapBillToAcctCode, 
            								   String sapShipToAcctCode, CustomerSalesOrgBO salesOrg, 
            								   String orderType, String orderSource) throws WOMExternalSystemException, WOMTransactionException, WOMValidationException{

		if (itemList == null || itemList.size() == 0) {
			// no input parts
			return;
		}

		if (validateSalesOrgInfo(salesOrg) == false) {
			logger.error("Insufficient sales org info supplied to make SAP call: "+ salesOrg);
			throw new WOMValidationException("Insufficient Sales Org info supplied to SAP call:");
		}

		// instantiate action class to execute the call
		TSCCheckInventory action = new TSCCheckInventory(sapBillToAcctCode, sapShipToAcctCode, salesOrg, itemList, orderType, orderSource);
		logger.debug("Executing tscPickUpcheckInventory() for sap account= "+ sapBillToAcctCode + ", sales org= " + salesOrg);
		action.execute();		
	}
	
	
	/**
	 * 
	 * @param order
	 * @param sapBillToAcctCode
	 * @param sapShipToAcctCode
	 * @param salesOrg
	 * @return
	 * @throws WOMExternalSystemException
	 */
	public static String processOrder(OrderBO order, String sapBillToAcctCode, String sapShipToAcctCode, CustomerSalesOrgBO salesOrg)
        throws WOMExternalSystemException, WOMValidationException {
	    if (validateSalesOrgInfo(salesOrg) == false) {
            logger.error("Insufficient sales org info supplied to make SAP call: "+salesOrg);
            throw new WOMValidationException("Insufficient Sales Org info supplied to SAP call:");
        }
	    
	    validateOrderInfo(order);
	    
	    // instantiate action class to execute the call
        ProcessOrderAction action = new ProcessOrderAction(sapBillToAcctCode,sapShipToAcctCode, salesOrg, order);
        logger.info("Executing processOrder() for sap account= "+ sapBillToAcctCode+ ", sales org="+ salesOrg);//Added by Abhijit
        String sapOrderNumber = action.execute();
        logger.info("Complete processOrder() for sap account= "+ sapBillToAcctCode+ ". SAPConfirmation number = "+sapOrderNumber);//Added by Abhijit
        return sapOrderNumber;
	}
	
	/**
	 * Get Shipment info for the given customer and master order numbers on the 
	 * given account
	 * @param accountCode
	 * @param customerPONumber
	 * @param masterOrderNumber
	 * @return
	 * @throws WOMExternalSystemException
	 */
	public static List<ShippedOrderBO> getShipmentInfo(String accountCode, String customerPONumber, String masterOrderNumber, String orderReturnFlag) 
	       throws WOMExternalSystemException  {
   
	    return getShipmentInfo(accountCode, customerPONumber, masterOrderNumber, null, null, OrderSearchFilter.ALL, orderReturnFlag);
	}
	
	/**
     * Get Shipment info for the given customer and master order numbers on the 
     * given account
     * @param accountCode
     * @param customerPONumber
     * @param masterOrderNumber
     * @return
     * @throws WOMExternalSystemException
     */
    public static List<ShippedOrderBO> getShipmentInfo(String accountCode, String customerPONumber, String masterOrderNumber,
                                                       Date startDate, Date endDate, OrderSearchFilter filter, String orderReturnFlag) 
           throws WOMExternalSystemException  {
   
        // instantiate action class to execute the call
        GetShipmentInfoAction action = new GetShipmentInfoAction(accountCode, customerPONumber, masterOrderNumber, startDate, endDate, filter);
        action.setOrderRetrunFlag(orderReturnFlag);
        return action.execute();
    }
    
    public static List<ShippedOrder> searchECCOrders(OrderSearchCriteria anOrderSearchCriteria) throws WOMExternalSystemException {
    	logger.info("Searhing ECC orders "+anOrderSearchCriteria);
    	ECCOrderSearchAction eccOrderSearchAction = new ECCOrderSearchAction(anOrderSearchCriteria);
    	List<ShippedOrder> eccShippedOrderList = new ArrayList<ShippedOrder>();
    	List<ECCShippedOrder> rawECCOrderList = eccOrderSearchAction.execute();
    	if(rawECCOrderList !=null && (!rawECCOrderList.isEmpty())){
        	for(ECCShippedOrder rawOrder : rawECCOrderList){
        		ShippedOrder eccShippedOrder = new ShippedOrder();
        		eccShippedOrder.setExternalSystem(rawOrder.getExternalSystem());
        		eccShippedOrder.setPoNumber(rawOrder.getPoNumber());
        		eccShippedOrder.setMasterOrderNumber(rawOrder.getMasterOrderNumber());
        		eccShippedOrder.setReturnOrderReason(rawOrder.getReturnOrderReason());
        		eccShippedOrder.setOrderNumber(rawOrder.getOrderNumber());
        		eccShippedOrder.setOrderDate(rawOrder.getOrderDate());
        		eccShippedOrder.setOrderSource(rawOrder.getOrderSource());
        		eccShippedOrder.setPackingSlipNumber(rawOrder.getDeliveryNumber());
        		eccShippedOrder.setShippingDC(rawOrder.getShippingDC());
        		eccShippedOrder.setShipDate(rawOrder.getShipDate());
        		eccShippedOrder.setOrderStatus(rawOrder.getTrackingStatus());
        		eccShippedOrder.setTrackingNumber(rawOrder.getTrackingNumber());
        		eccShippedOrder.setCarrierCode(rawOrder.getCarrierCode());
        		eccShippedOrder.setCarrierName(rawOrder.getCarrierName());
        		eccShippedOrderList.add(eccShippedOrder);
        	}
        }
    	logger.info(eccShippedOrderList.size()+" ECC Orders Found for "+anOrderSearchCriteria);
    	return eccShippedOrderList;
    }
    
    /**
     * Get Shipment info for the list of given sold tos and the given ship to for the search criteria
     * @param accountCode
     * @param customerPONumber
     * @param masterOrderNumber
     * @return
     * @throws WOMExternalSystemException
     */
    public static List<ShippedOrderBO> getShipmentInfo(List<String> soldToAccountList, String shipToAccountCode, String customerPONumber, String masterOrderNumber,
                                                       Date startDate, Date endDate, OrderSearchFilter filter,String orderReturnFlag) 
           throws WOMExternalSystemException  {
   
        // instantiate action class to execute the call
        GetShipmentInfoAction action = new GetShipmentInfoAction(soldToAccountList, shipToAccountCode, customerPONumber, masterOrderNumber, startDate, endDate, filter);
        action.setOrderRetrunFlag(orderReturnFlag);
        return action.execute();
    }
    
	public static ShippedOrderDetail getECCOrderDetail(OrderSearchCriteria eccOrderSearchCriteria) throws WOMExternalSystemException, WOMTransactionException, Exception {
		logger.info("START :: ECC Order detail for "+eccOrderSearchCriteria);
		ECCOrderSearchAction eccOrderSearchAction = new ECCOrderSearchAction(eccOrderSearchCriteria);
		List<ECCShippedOrder> rawECCOrderList = eccOrderSearchAction.execute();
		logger.info("Fetched the Order Header "+rawECCOrderList.size()+" for "+eccOrderSearchCriteria);
		ECCOrderDetailAction eccOrderDetailaction = new ECCOrderDetailAction(rawECCOrderList);
		ShippedOrderDetail eccShippedOrderDetail = eccOrderDetailaction.getOrderDetail(eccOrderSearchCriteria.getOrderNumber());	
		logger.info("END :: ECC Order detail for "+eccOrderSearchCriteria);
		return eccShippedOrderDetail;
	}    
    
	/**
	 * Retrieve the order detail for the given shipped order. This will append order detail and order meta
	 * data to the given shipped order, while preserving other fields already populated.
	 * @param shippedOrder
	 * @return
	 * @throws WOMExternalSystemException
	 * @throws WOMTransactionException
	 */
	public static void getShipmentDetail(ShippedOrderBO shippedOrder, String orderReturnFlag) 
            throws WOMExternalSystemException, WOMTransactionException  {
	    
	    if (shippedOrder == null || shippedOrder.getOrderUnitList() == null) {
	        return;
	    }
	    
	    // go through and get the details for each order unit.  The order details call will populate some further
	    // info onto a returned ShippedOrderBO, so we'll retain that and copy it onto the Shipped Order passed in,
	    // as well as populate the proper order unit info.
	    ShippedOrderBO returnedShippedOrderDetail;
	    boolean first = true;
	    for (OrderUnitBO anOrderUnit : shippedOrder.getOrderUnitList()) {
	        GetShipmentDetailAction getShipmentDetailAction = new GetShipmentDetailAction(anOrderUnit);
	        getShipmentDetailAction.setOrderRetrunFlag(orderReturnFlag);
	        returnedShippedOrderDetail = getShipmentDetailAction.execute();
	        if (returnedShippedOrderDetail == null) {
	            // order not found
	            continue;
	        }
	        
	        if (first == true) {
	            // this is the first order unit.  Move the appropriate meta data from the returned object
	            first = false;
	            
	            if(returnedShippedOrderDetail.getBillToAccount() !=null){
	            	shippedOrder.setBillToAccount(returnedShippedOrderDetail.getBillToAccount());
	            }

	            if(returnedShippedOrderDetail.getShipToAccount() != null){
	            	shippedOrder.setShipToAccount(returnedShippedOrderDetail.getShipToAccount());
	            }
	            
                if(returnedShippedOrderDetail.getOrderedBy() != null){
                	shippedOrder.setOrderedBy(returnedShippedOrderDetail.getOrderedBy());
                }
                if(returnedShippedOrderDetail.getOrderType() != null){
                	shippedOrder.setOrderType(returnedShippedOrderDetail.getOrderType());
                }
                shippedOrder.setOrderType(returnedShippedOrderDetail.getOrderType());
	        }
	    }
	}


	/**
	 * Makes sure the necessary info is supplied on the given salesOrg
	 * @param salesOrg CustomerSalesOrgBO to verify
	 * @return true if necessary info is present, false otherwise
	 */
	public static boolean validateSalesOrgInfo(CustomerSalesOrgBO salesOrg) {
		
		logger.debug("@@@ Inside validateSalesOrgInfo: SAPService @@@"); //Added by Abhijit
		
		if (salesOrg == null)  {
			// that's bad
			logger.debug("@@@ SalesOrg is null @@@"); //Added by Abhijit
			return false;
		}
		
		logger.debug("@@@ SalesOrg is not null @@@"); //Added by Abhijit
		
		// values we're interested in
		String code = salesOrg.getSalesOrganization() != null ? salesOrg.getSalesOrganization().getCode() : null; 
		logger.debug("code "+code);
		String distChannel = salesOrg.getDistributionChannel();
		logger.debug(" distChannel "+ distChannel);
		String division = salesOrg.getDivision(); 
		logger.debug(" division "+division);
		
		// we want this to be false for this info to be valid
		return ( ( code == null || "".equals(code.trim()) ) ||
				 ( distChannel == null || "".equals(distChannel.trim()) ) ||
				 ( division == null || "".equals(division.trim())) == false );
	}
	
	/**
     * Makes sure the necessary info is supplied on the given order
     * throws validation exception if necessary info is not present
     */
    private static void validateOrderInfo(OrderBO order) throws  WOMValidationException {
    	logger.debug("@@ Inside validateOrderInfo : SAPService @@");
        boolean validationSuccessful = true;
        String errorMessage = "";
        if (order == null)  {
            // that's bad
            logger.error("No order submitted");
            errorMessage = "No order submitted";
            validationSuccessful = false;
        }
        logger.debug("@@ Order is not null @@");
        // values we're interested in
        AccountBO shipToAcct = order.getShipToAcct();
        ManualShipToAddressBO manualShipTo = order.getManualShipToAddress();
        // both of these can't be null
        if(order.getOrderType() != OrderType.PICKUP){
        	if (shipToAcct == null && manualShipTo == null) {
                logger.error("No ship to account or manual ship to submitted");
                errorMessage = "No ship to account submitted";
                validationSuccessful = false;
            } else {
               // one of them is populated. make sure the address is there.
                AddressBO shipToAddress = shipToAcct != null ? shipToAcct.getAddress() : manualShipTo.getAddress() ; 
                if (shipToAddress == null) {
                    logger.error("No ship to address submitted");
                    errorMessage ="No ship to address submitted";
                    validationSuccessful = false;
                }
            }
        }
        
        //String zipCode = shipToAddress.getZipOrPostalCode();
        // RA- no zip code validation for now. SUNR TP sends international zipcodes
//        if (zipCode == null || StringUtil.isAValidUSZipCode(zipCode) == false) {
//            logger.error("Invalid zipcode submitted.  Zipcode={}", zipCode);
//            errorMessage = "Invalid ship to zipcode submitted.  Zipcode="+zipCode;
//            validationSuccessful = false;
//        }
        
        if (validationSuccessful == false) {
            logger.error("Order Data Validation Failure: "+ errorMessage);
            throw new WOMValidationException("Order Data Validation Failure: "+errorMessage);
        }
    }

    /**
     * Fetch Back Orders for passed-in SAP Account Code.
     * @param accountCode
     * @return <code>List&lt;BackOrderBO&gt;<code>
     * @throws WOMExternalSystemException
     * @throws WOMTransactionException
     */
    public static List<BackOrderBO> getBackOrders(String accountCode) 
    		throws WOMExternalSystemException, WOMTransactionException  {

        GetBackOrdersAction action = new GetBackOrdersAction(accountCode);
        
    	return action.execute();
    }

    /**
     * Fetch Back Orders for passed-in list of SAP sold to codes and the supplied ship to code
     * @return <code>List&lt;BackOrderBO&gt;<code>
     * @throws WOMExternalSystemException
     * @throws WOMTransactionException
     */
    public static List<BackOrderBO> getBackOrders(List<String> soldToAccountList, String shipToAccountCode) 
            throws WOMExternalSystemException, WOMTransactionException  {

        GetBackOrdersAction action = new GetBackOrdersAction(soldToAccountList, shipToAccountCode);
        return action.execute();
    }

    public static CreditCheckBO doCreditCheck(String customerNumber, SalesOrganizationBO salesOrganization) throws WOMExternalSystemException {
    	CreditCheckAction creditCheckAction = new CreditCheckAction(customerNumber, salesOrganization);
    	return creditCheckAction.execute();
    }

	public static List<InvoiceBO> searchInvoices(InvoiceSearchCriteriaBO invoiceSearchCriteria) throws WOMExternalSystemException {
		GetInvoicesAction getInvoicesAction = new GetInvoicesAction(invoiceSearchCriteria);
		return getInvoicesAction.execute();
	}

	public static InvoiceDetailsBO getInvoiceDetails(String invoiceNumber) throws WOMExternalSystemException {
		GetInvoiceDetailsAction invoiceDetailsAction = new GetInvoiceDetailsAction(invoiceNumber);
		return invoiceDetailsAction.execute();
	}



}
