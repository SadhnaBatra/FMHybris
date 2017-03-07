package com.fmo.wom.webservice;


import java.sql.BatchUpdateException;
import java.util.Date;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.soap.SOAPFaultException;

import org.apache.log4j.Logger;

import com.fmo.wom.common.exception.WOMAuthenticationException;
import com.fmo.wom.common.exception.WOMValidationException;
import com.fmo.wom.transformation.service.ConfirmBODTransformer;
import com.fmo.wom.transformation.util.JAXBTransformer;
import com.fmo.wom.webservice.handler.FMOBaseServiceHandler;
import com.fmo.wom.webservice.handler.GSServiceHandler;
import com.fmo.wom.webservice.handler.POServiceHandler;
import com.fmo.wom.webservice.handler.RFQServiceHandler;


public class IpoService {	
	private static Logger logger = Logger.getLogger(IpoService.class);
	public static final String CLIENT = "Client";
	public static final String CLIENT_AUTH = "Client.Authentication";
	public static final String SERVER = "Server";
	
	/*@Autowired 
	RFQServiceHandler rfqRequestHandler;
	@Autowired 
	POServiceHandler poRequestHandler;
	@Autowired 
	GSServiceHandler gsServiceHandler;*/


	/**
	 * Returns the pricing,availability and shipping information for the given parts 
	 * @param addRequestforQuoteBOD String xml data that adhere to aaia AddRequestForQuote.xsd
	 * @return IPOQuoteResponse.AddQuoteBOD xml data that adhere to aaia AddQuote.xsd(  
	 */
	public String quote(String addRequestforQuoteBOD) {
		Date startTime = new Date();
		logger.debug("Request BOD::"+addRequestforQuoteBOD);
    	String response = null;    	
    	try {
    		response = getRfqRequestHandler().execute(addRequestforQuoteBOD);			
		} catch (Exception e) {
			logger.error("Error executing quote", e);
			createSoapFault(addRequestforQuoteBOD,e);
		}
		logger.debug("Time takes to complete Quote request"+(new Date().getTime()-startTime.getTime()));		
    	return response;
    }

    public String quoteStatus(String getQuoteBOD) {
        return null;
    }

    public String createPurchaseOrder(
    		String processPurchaseOrderBOD) {
    	String poResponse = null;
    	// for use in the while below in case something happens with an infinite nesting.
    	int failSafeLevel = 10;
    	try {
    		poResponse = 
    			getPoRequestHandler().execute(processPurchaseOrderBOD);			
		} catch (Exception e) {
		    int nestingLevel = 1;
		    Throwable rootCause = e;
		    while ( rootCause.getCause() != null && (rootCause instanceof BatchUpdateException) == false && nestingLevel < failSafeLevel) { 
		        rootCause = rootCause.getCause();
		        nestingLevel++;
		    }
		    logger.error("Error creating purchase order", e);
			createSoapFault(processPurchaseOrderBOD, rootCause);
		}    	
        return poResponse;
    }

    public String changePurchaseOrder(String changePurchaseOrderBOD) {
        return null;
    }

    public String purchaseOrderStatus(String getPurchaseOrderBOD) {
        return null;
    }

    public String shipmentStatus(String getShipmentBOD) {
    	String shipmentStatus = null;
    	try {
    		shipmentStatus = getGsRequestHandler().execute(getShipmentBOD);			
		} catch (Exception e) {
			logger.error("Error getting shipment status", e);
			createSoapFault(getShipmentBOD,e);
		}    	
        return shipmentStatus;
    }

    public String cancelPurchaseOrder(String cancelPurchaseOrderBOD) {
        return null;
    }

	public RFQServiceHandler getRfqRequestHandler() {
		RFQServiceHandler rfqRequestHandler = new RFQServiceHandler();
		return rfqRequestHandler;
	}
	public POServiceHandler getPoRequestHandler() {
		POServiceHandler poRequestHandler = new POServiceHandler();
		return poRequestHandler;
	}
	public GSServiceHandler getGsRequestHandler() {
		GSServiceHandler gsServiceHandler = new GSServiceHandler(); 
		return gsServiceHandler;
	}

//	public void setOrderRequestHandler(RFQServiceHandler orderRequestHandler) {
//		this.orderRequestHandler = orderRequestHandler;
//	}
	private void createSoapFault(String addRequestforQuoteBOD,Throwable e){
		logger.error("Error processing request.Exception Message is ", e);
		logger.error("Error processing request.Exception is ", e);
		String confirmBod = "";
		String errorMessage = e.getMessage()!=null?e.getMessage():e.toString();				
		try {
			// remove illegal XML chars 
			errorMessage = JAXBTransformer.cleanStringForXml(errorMessage, ' ');
			confirmBod = new ConfirmBODTransformer().transformResponse(
					addRequestforQuoteBOD, "<message> Unexpected Error occurred during processing. Please contact customer service at 800-334-3210, Press Option 1. Error detail ["+
					errorMessage+"]</message>",
					FMOBaseServiceHandler.getIpoVersion(addRequestforQuoteBOD),null);				
		} catch (Exception e1) {
			// returning the exception to the client
			confirmBod = "Unable to process ur request. Please contact Federal Mogul support team 800-334-3210, Press Option 1.";
			logger.error("Unable to create ConfirmBOD for "+errorMessage+".Reason:"+e1.toString());
		}
		SOAPFault fault = null;
		try {
			MessageFactory msgFactory = MessageFactory.newInstance();
			SOAPMessage message = msgFactory.createMessage();
			SOAPBody body = message.getSOAPBody();
			fault = body.addFault();
			fault.setFaultCode(getFaultName(e));
		    fault.setFaultString(confirmBod);
		} catch (SOAPException e1) {
			logger.error("Errorr creating the SoapFault:",e1);
			throw new RuntimeException("Unable to process ur request. Please contact Federal Mogul support team");
		}
		throw new SOAPFaultException(fault);
	}
    private Name getFaultName(Throwable e) throws SOAPException{
    	SOAPFactory soapFactory = SOAPFactory.newInstance();
    	Name qname = null;
		if(e instanceof WOMValidationException){
			qname = soapFactory.createName(CLIENT);
		}if (e instanceof WOMAuthenticationException){
			qname = soapFactory.createName(CLIENT_AUTH);
		}else{
			qname = soapFactory.createName(SERVER);
		}
		
		return qname;
    }

}

