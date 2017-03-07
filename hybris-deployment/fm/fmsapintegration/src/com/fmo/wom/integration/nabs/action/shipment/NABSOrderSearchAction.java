package com.fmo.wom.integration.nabs.action.shipment;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.common.exception.WOMTransactionException;
import com.fmo.wom.domain.OrderSearchCriteria;
import com.fmo.wom.domain.ShippedOrder;
import com.fmo.wom.domain.ShippedOrderDetail;
import com.fmo.wom.integration.nabs.action.NabsActionBase;
import com.fmo.wom.integration.nabs.action.NabsInputBase;
import com.fmo.wom.integration.nabs.ims.NabsTransactionHelper;

public class NABSOrderSearchAction extends NabsActionBase {

	private static Logger logger = Logger.getLogger(NABSOrderSearchAction.class);

	private NABSOrderSearchUtil nabsOrderSearchUtil;
	
	public NABSOrderSearchAction() {
		super();
		nabsOrderSearchUtil =  new NABSOrderSearchUtil();
	}
	
	
	@Override
	public Logger getLogger() {
		return logger;
	}
	
	public List<ShippedOrder> searchOrders(OrderSearchCriteria anOrderSearchCriteriaBO) throws WOMTransactionException, WOMExternalSystemException{
		
		logger.info("START :: searchOrders()");
		NabsGetShipmentInfoInput shipmentInfoInput = new NabsGetShipmentInfoInput(anOrderSearchCriteriaBO.getNabsAccountCode(),
																				  anOrderSearchCriteriaBO.getConfirmationOrOrderNumber(),
																				  anOrderSearchCriteriaBO.getPoNumber(),
																				  anOrderSearchCriteriaBO.getSearchFrom(),
																				  anOrderSearchCriteriaBO.getSearchTo(),
																				  anOrderSearchCriteriaBO.getOrderStatus());
		 NabsGetShipmentInfoOutput output = new NabsGetShipmentInfoOutput();
		 logger.info("Send Order Header Request to NABS "+shipmentInfoInput);
		 executeGetShipmentCall(shipmentInfoInput, output);
		 logger.info("Order Header Response Recieved from NABS ");
		 List<ShippedOrder> nabsOrdersList = new ArrayList<ShippedOrder>();
	     if (output.isNoResultsFound() == false) {
	    	 logger.info("Order Header Database Inquiry Key :: "+output.getInquiryKey().trim());
	         nabsOrdersList = nabsOrderSearchUtil.retrieveOrders(output.getInquiryKey());
	     }
	     logger.info("END :: searchOrders()");     
	   
	     return nabsOrdersList;
	}
	
	protected void executeGetShipmentCall(NabsInputBase input, NabsGetShipmentInfoOutput output)
            throws WOMExternalSystemException, WOMTransactionException {

    	getLogger().info("START :: executeGetShipmentCall()");
        String inputString = input.getInputString();
        NabsTransactionHelper nabsTransactionHelper = new NabsTransactionHelper();
        String outputString = nabsTransactionHelper.callNabsTransaction(input.getTransactionCode(), inputString);
        getLogger().info("NABS Call executed. INPUT=["+ inputString+ "] OUTPUT=["+ outputString+"].");
        output.parseOutputString(outputString);
        getLogger().info("output.isReturnStatusSuccessful() "+output.isReturnStatusSuccessful());
        getLogger().info("output.isNoResultsFound()  "+output.isNoResultsFound());
        if (output.isReturnStatusSuccessful() == false && output.isNoResultsFound() == false) {
            String logMessage = "NABS " + input.getTransactionCode() + " call failure. ID = " + input.getNabsCallID() + 
                                ". Status = " + output.getStatusCode() + ". Error message = "+ output.getStatusMessage();
            getLogger().error(logMessage);
            getLogger().error("NABS Tran ID " + input.getTransactionCode() + ":  INPUT: "+input.getInputString() +" OUTPUT: "+ outputString);
            throw new WOMExternalSystemException(logMessage);
        }
        
        // some quick logging to note this fact if it applies
        if (output.isNoResultsFound()) {
            String logMessage = "NABS " + input.getTransactionCode() + " call returned no reults. ID = " + input.getNabsCallID() + 
            ". Status = " + output.getStatusCode() + ". Error message = "+ output.getStatusMessage();
            getLogger().info(logMessage);
            getLogger().info("NABS Tran ID " + input.getTransactionCode() + ":  INPUT: "+input.getInputString() +" OUTPUT: "+ outputString);
        }
        getLogger().info("END :: executeGetShipmentCall()");
    }

	public void releaseResource() {
		nabsOrderSearchUtil.releaseResource();
	}


	public ShippedOrderDetail getOrderDetail(String anAccountCode, String anMasterOrderNumber, String anOrderNumber) 
																		throws WOMTransactionException, WOMExternalSystemException {
		
		getLogger().info("START :: getOrderDetail()");
		
		// create an input object
        NabsGetOrderDetailInput orderDetailInput = new NabsGetOrderDetailInput(anAccountCode, anMasterOrderNumber, anOrderNumber);

        // We need to use the specific output for this NABS call
        NabsGetShipmentInfoOutput output = new NabsGetShipmentInfoOutput();
        
        // call nabs
		logger.info("Send Order Detail Request to NABS "+orderDetailInput);
        executeGetShipmentCall(orderDetailInput, output);
        logger.info("Order Header Response Recieved from NABS ");

        ShippedOrderDetail orderDetail = null;
        if (output.isNoResultsFound() == false){
        	 logger.info("Order Details Database Inquiry Key :: "+output.getInquiryKey().trim());
        	orderDetail = nabsOrderSearchUtil.retrieveOrderDetail(output.getInquiryKey()); 
        }
		
		getLogger().info("END :: getOrderDetail()");
		return orderDetail;
	}	
}
