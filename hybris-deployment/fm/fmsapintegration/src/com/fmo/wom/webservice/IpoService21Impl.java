package com.fmo.wom.webservice;


import org.apache.log4j.Logger;

import com.fmo.wom.iposervice.IPOCancelPurchaseOrderResponse;
import com.fmo.wom.iposervice.IPOChangePurchaseOrderResponse;
import com.fmo.wom.iposervice.IPOCreatePurchaseOrderAcknowledgePurchaseOrderResponse;
import com.fmo.wom.iposervice.IPOCreatePurchaseOrderResponse;
import com.fmo.wom.iposervice.IPOPurchaseOrderStatusResponse;
import com.fmo.wom.iposervice.IPOQuoteAddQuoteResponse;
import com.fmo.wom.iposervice.IPOQuoteResponse;
import com.fmo.wom.iposervice.IPOQuoteStatusResponse;
import com.fmo.wom.iposervice.IPOShipmentStatusResponse;
import com.fmo.wom.iposervice.IPOShipmentStatusShowShipmentResponse;

@javax.jws.WebService (
		endpointInterface="com.fmo.wom.iposervice.InternetPartsOrderWebServiceSoap"
			, targetNamespace="http://www.aaiasoa.org/IPOv2"
				, serviceName="InternetPartsOrderWebService"
					, portName="InternetPartsOrderWebServiceSoap")
public class IpoService21Impl {
	private static Logger logger = Logger.getLogger(IpoService21Impl.class);

	//@Autowired 
	//private IpoService ipoService;

	/**
	 * Returns the pricing,availability and shipping information for the given parts 
	 * @param addRequestforQuoteBOD String xml data that adhere to aaia AddRequestForQuote.xsd
	 * @return IPOQuoteResponse.AddQuoteBOD xml data that adhere to aaia AddQuote.xsd(  
	 */
	public IPOQuoteResponse quote(String addRequestforQuoteBOD) {
    	IPOQuoteAddQuoteResponse response = new IPOQuoteAddQuoteResponse();
    	IpoService ipoService = new IpoService();
		response.setAddQuoteBOD(ipoService.quote(addRequestforQuoteBOD));			
    	return response;
    }

    public IPOQuoteStatusResponse quoteStatus(String getQuoteBOD) {
        return null;
    }

    public IPOCreatePurchaseOrderResponse createPurchaseOrder(
    		String processPurchaseOrderBOD) {
    	IPOCreatePurchaseOrderAcknowledgePurchaseOrderResponse response = 
    		new IPOCreatePurchaseOrderAcknowledgePurchaseOrderResponse();
    	IpoService ipoService = new IpoService();
    	response.setAcknowledgePurchaseOrderBOD(
    			ipoService.createPurchaseOrder(processPurchaseOrderBOD));
        return response;

    }

    public IPOChangePurchaseOrderResponse changePurchaseOrder(String changePurchaseOrderBOD) {
        return null;
    }

    public IPOPurchaseOrderStatusResponse purchaseOrderStatus(String getPurchaseOrderBOD) {
        return null;
    }

    public IPOShipmentStatusResponse shipmentStatus(String getShipmentBOD) {
    	IPOShipmentStatusShowShipmentResponse response = 
    		new IPOShipmentStatusShowShipmentResponse();
    	IpoService ipoService = new IpoService();
    	response.setShowShipmentBOD(ipoService.shipmentStatus(getShipmentBOD));
        return response;

    }

    public IPOCancelPurchaseOrderResponse cancelPurchaseOrder(String cancelPurchaseOrderBOD) {
        return null;
    }
}

