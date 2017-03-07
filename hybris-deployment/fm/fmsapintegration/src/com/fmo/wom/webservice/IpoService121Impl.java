package com.fmo.wom.webservice;


//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.apache.log4j.Logger;

import com.fmo.wom.iposervice121.IPOCancelPurchaseOrderResponse;
import com.fmo.wom.iposervice121.IPOChangePurchaseOrderResponse;
import com.fmo.wom.iposervice121.IPOCreatePurchaseOrderAcknowledgePurchaseOrderResponse;
import com.fmo.wom.iposervice121.IPOCreatePurchaseOrderResponse;
import com.fmo.wom.iposervice121.IPOPurchaseOrderStatusResponse;
import com.fmo.wom.iposervice121.IPOQuoteAddQuoteResponse;
import com.fmo.wom.iposervice121.IPOQuoteResponse;
import com.fmo.wom.iposervice121.IPOQuoteStatusResponse;
import com.fmo.wom.iposervice121.IPOShipmentStatusResponse;
import com.fmo.wom.iposervice121.IPOShipmentStatusShowShipmentResponse;

@javax.jws.WebService (
		endpointInterface="com.fmo.wom.iposervice121.InternetPartsOrderWebServiceSoap"
			, targetNamespace="http://www.aftermarket.org/InternetPartsOrder"
				, serviceName="InternetPartsOrderWebService"
					, portName="InternetPartsOrderWebServiceSoap")
public class IpoService121Impl {
	private static Logger logger = Logger.getLogger(IpoService121Impl.class);
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

	/*public IpoService getIpoService() {
		return ipoService;
	}

	public void setIpoService(IpoService ipoService) {
		this.ipoService = ipoService;
	}*/

}

