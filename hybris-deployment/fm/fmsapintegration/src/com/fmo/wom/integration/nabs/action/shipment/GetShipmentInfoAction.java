package com.fmo.wom.integration.nabs.action.shipment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.common.exception.WOMTransactionException;
import com.fmo.wom.domain.OrderUnitBO;
import com.fmo.wom.domain.ShippedOrderBO;
import com.fmo.wom.domain.enums.OrderSearchFilter;
import com.fmo.wom.integration.nabs.action.NabsActionBase;
import com.fmo.wom.integration.nabs.action.NabsInputBase;
import com.fmo.wom.integration.nabs.ims.NabsTransactionHelper;

public class GetShipmentInfoAction extends NabsActionBase {

    private static Logger logger = Logger.getLogger(GetShipmentInfoAction.class);
   
    //@Autowired
    private ShippingInfoUtil shippingInfoUtil;
    
    
    public GetShipmentInfoAction() {
		super();
		shippingInfoUtil =  new ShippingInfoUtil();
	}

	public Logger getLogger() {
        return logger;
    }
    
    public List<ShippedOrderBO> executeOrderSearch(String accountCode, String masterOrderNumber, String customerPONumber) 
            throws WOMTransactionException, WOMExternalSystemException {
        
        return executeOrderSearch(accountCode, masterOrderNumber, customerPONumber, null, null, null);
    }
    
    public List<ShippedOrderBO> executeOrderSearch(String accountCode, String masterOrderNumber, String customerPONumber, 
                                                   Date startDate, Date endDate, OrderSearchFilter filter)  
        throws WOMTransactionException, WOMExternalSystemException {

        // create an input object
        NabsGetShipmentInfoInput shipmentInfoInput = new NabsGetShipmentInfoInput( accountCode, masterOrderNumber, customerPONumber, startDate, endDate, filter);
        
        // We need to use the specific output for this NABS call
        NabsGetShipmentInfoOutput output = new NabsGetShipmentInfoOutput();
        
        // call nabs
        executeGetShipmentCall(shipmentInfoInput, output);
        
        List<ShippedOrderBO> results = new ArrayList<ShippedOrderBO>();
        if (output.isNoResultsFound() == false) {
            // load the results with the inquiry key returned.
        	logger.info(" Inquiry Key :: "+output.getInquiryKey().trim());
            results = shippingInfoUtil.retrieveShipmentHeaderInfo(output.getInquiryKey());
        }
        
        return results;
    }
    
    public OrderUnitBO executeOrderDetailSearch(String accountCode, String masterOrderNumber, String systemOrderNumber) 
        throws WOMTransactionException, WOMExternalSystemException {
    	getLogger().info("executeOrderDetailSearch () START "); 
        // create an input object
        NabsGetOrderDetailInput orderDetailInput = new NabsGetOrderDetailInput( accountCode, masterOrderNumber, systemOrderNumber);

        // We need to use the specific output for this NABS call
        NabsGetShipmentInfoOutput output = new NabsGetShipmentInfoOutput();
        
        // call nabs
        getLogger().info("begin executeGetShipmentCall () ");
        executeGetShipmentCall(orderDetailInput, output);
        getLogger().info("end executeGetShipmentCall () ");
        
        OrderUnitBO result = null;
        getLogger().info("output.isNoResultsFound()  "+output.isNoResultsFound());
        if (output.isNoResultsFound() == false) {
            // load the results with the inquiry key returned.
            List<ShippedOrderBO> resultList = shippingInfoUtil.retrieveShipmentHeaderInfo(output.getInquiryKey());
        
            // This should only return one
            if (resultList.size() > 1) {
                // hmmmmmm - investigate this one
                getLogger().error("More than one ShippedOrderBO returned for system order number "+systemOrderNumber);
            }
            ShippedOrderBO shippedOrder = resultList.get(0);
            
            
            // get out the one order unit for this order
            List<OrderUnitBO> orderUnits = shippedOrder.getOrderUnitList();
            if (orderUnits == null || orderUnits.size() == 0) {
                // no results?
                getLogger().error("No Order Unit found for system order number "+ systemOrderNumber);
            } else if (orderUnits.size() > 1) {
                // don't think there should be more than one
                getLogger().error("More than one Order Unit returned for system order number "+ systemOrderNumber);
            } else {
                result = orderUnits.get(0);
            }
            
            result.setOrderBy(shippedOrder.getOrderedBy());
        }
        
        getLogger().info("executeOrderDetailSearch () END ");
        return result;
    }

    protected void executeGetShipmentCall(NabsInputBase input, NabsGetShipmentInfoOutput output)
            throws WOMExternalSystemException, WOMTransactionException {

    	getLogger().info("executeGetShipmentCall START");
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
        getLogger().info("executeGetShipmentCall END");
    }

	public void releaseResource() {
		shippingInfoUtil.releaseResource();
	}
    
   
}
