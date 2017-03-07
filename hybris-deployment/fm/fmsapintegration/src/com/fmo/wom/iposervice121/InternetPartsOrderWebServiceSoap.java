
package com.fmo.wom.iposervice121;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.10
 * Generated source version: 2.2
 * 
 */
@WebService(name = "InternetPartsOrderWebServiceSoap", targetNamespace = "http://www.aftermarket.org/InternetPartsOrder")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface InternetPartsOrderWebServiceSoap {


    /**
     * Request for parts, price and availability  information.  This operation accepts an Add Request For Quote BOD and returns either an AddQuote BOD or a Confirm BOD
     * 
     * @param addRequestforQuoteBOD
     * @return
     *     returns com.fmo.wom.iposervice121.IPOQuoteResponse
     */
    @WebMethod(operationName = "Quote", action = "http://www.aftermarket.org/InternetPartsOrder/Quote")
    @WebResult(name = "QuoteResult", targetNamespace = "http://www.aftermarket.org/InternetPartsOrder")
    @RequestWrapper(localName = "Quote", targetNamespace = "http://www.aftermarket.org/InternetPartsOrder", className = "com.fmo.wom.iposervice121.Quote")
    @ResponseWrapper(localName = "QuoteResponse", targetNamespace = "http://www.aftermarket.org/InternetPartsOrder", className = "com.fmo.wom.iposervice121.QuoteResponse")
    public IPOQuoteResponse quote(
        @WebParam(name = "AddRequestforQuoteBOD", targetNamespace = "http://www.aftermarket.org/InternetPartsOrder")
        String addRequestforQuoteBOD);

    /**
     * Request the status of a pending quote.   This opperation accepts a GetQuote  BOD and returns either an ShowQuote BOD or a Confirm BOD
     * 
     * @param getQuoteBOD
     * @return
     *     returns com.fmo.wom.iposervice121.IPOQuoteStatusResponse
     */
    @WebMethod(operationName = "QuoteStatus", action = "http://www.aftermarket.org/InternetPartsOrder/QuoteStatus")
    @WebResult(name = "QuoteStatusResult", targetNamespace = "http://www.aftermarket.org/InternetPartsOrder")
    @RequestWrapper(localName = "QuoteStatus", targetNamespace = "http://www.aftermarket.org/InternetPartsOrder", className = "com.fmo.wom.iposervice121.QuoteStatus")
    @ResponseWrapper(localName = "QuoteStatusResponse", targetNamespace = "http://www.aftermarket.org/InternetPartsOrder", className = "com.fmo.wom.iposervice121.QuoteStatusResponse")
    public IPOQuoteStatusResponse quoteStatus(
        @WebParam(name = "GetQuoteBOD", targetNamespace = "http://www.aftermarket.org/InternetPartsOrder")
        String getQuoteBOD);

    /**
     * Process Purchase Order for parts.  This operation accepts a Process Purchase Order BOD and returns an Acknowledge Purchase Order BOD or a Confirm BOD
     * 
     * @param processPurchaseOrderBOD
     * @return
     *     returns com.fmo.wom.iposervice121.IPOCreatePurchaseOrderResponse
     */
    @WebMethod(operationName = "CreatePurchaseOrder", action = "http://www.aftermarket.org/InternetPartsOrder/CreatePurchaseOrder")
    @WebResult(name = "CreatePurchaseOrderResult", targetNamespace = "http://www.aftermarket.org/InternetPartsOrder")
    @RequestWrapper(localName = "CreatePurchaseOrder", targetNamespace = "http://www.aftermarket.org/InternetPartsOrder", className = "com.fmo.wom.iposervice121.CreatePurchaseOrder")
    @ResponseWrapper(localName = "CreatePurchaseOrderResponse", targetNamespace = "http://www.aftermarket.org/InternetPartsOrder", className = "com.fmo.wom.iposervice121.CreatePurchaseOrderResponse")
    public IPOCreatePurchaseOrderResponse createPurchaseOrder(
        @WebParam(name = "ProcessPurchaseOrderBOD", targetNamespace = "http://www.aftermarket.org/InternetPartsOrder")
        String processPurchaseOrderBOD);

    /**
     * Change an existing Purchase Order for parts.  This operation accepts a Change Purchase Order BOD and returns an Acknowledge Purchase Order BOD or a Confirm BOD
     * 
     * @param changePurchaseOrderBOD
     * @return
     *     returns com.fmo.wom.iposervice121.IPOChangePurchaseOrderResponse
     */
    @WebMethod(operationName = "ChangePurchaseOrder", action = "http://www.aftermarket.org/InternetPartsOrder/ChangePurchaseOrder")
    @WebResult(name = "ChangePurchaseOrderResult", targetNamespace = "http://www.aftermarket.org/InternetPartsOrder")
    @RequestWrapper(localName = "ChangePurchaseOrder", targetNamespace = "http://www.aftermarket.org/InternetPartsOrder", className = "com.fmo.wom.iposervice121.ChangePurchaseOrder")
    @ResponseWrapper(localName = "ChangePurchaseOrderResponse", targetNamespace = "http://www.aftermarket.org/InternetPartsOrder", className = "com.fmo.wom.iposervice121.ChangePurchaseOrderResponse")
    public IPOChangePurchaseOrderResponse changePurchaseOrder(
        @WebParam(name = "ChangePurchaseOrderBOD", targetNamespace = "http://www.aftermarket.org/InternetPartsOrder")
        String changePurchaseOrderBOD);

    /**
     * Inquiry on status of  existing Purchase Order.   This operation accepts a Get Purchase Order BOD and returns  an Acknowledge Purchase Order BOD, or a Confim BOD.  In the demonstration web service pass "A" or   "B"  to see the different responses.
     * 
     * @param getPurchaseOrderBOD
     * @return
     *     returns com.fmo.wom.iposervice121.IPOPurchaseOrderStatusResponse
     */
    @WebMethod(operationName = "PurchaseOrderStatus", action = "http://www.aftermarket.org/InternetPartsOrder/PurchaseOrderStatus")
    @WebResult(name = "PurchaseOrderStatusResult", targetNamespace = "http://www.aftermarket.org/InternetPartsOrder")
    @RequestWrapper(localName = "PurchaseOrderStatus", targetNamespace = "http://www.aftermarket.org/InternetPartsOrder", className = "com.fmo.wom.iposervice121.PurchaseOrderStatus")
    @ResponseWrapper(localName = "PurchaseOrderStatusResponse", targetNamespace = "http://www.aftermarket.org/InternetPartsOrder", className = "com.fmo.wom.iposervice121.PurchaseOrderStatusResponse")
    public IPOPurchaseOrderStatusResponse purchaseOrderStatus(
        @WebParam(name = "GetPurchaseOrderBOD", targetNamespace = "http://www.aftermarket.org/InternetPartsOrder")
        String getPurchaseOrderBOD);

    /**
     * Inquiry on status of  existing Shipment.   This operation accepts a Get Shipment BOD and returns   a Show Shipment BOD or a Confim BOD.  In the demonstration web service pass "A" or   "B"    to see the different responses.
     * 
     * @param getShipmentBOD
     * @return
     *     returns com.fmo.wom.iposervice121.IPOShipmentStatusResponse
     */
    @WebMethod(operationName = "ShipmentStatus", action = "http://www.aftermarket.org/InternetPartsOrder/ShipmentStatus")
    @WebResult(name = "ShipmentStatusResult", targetNamespace = "http://www.aftermarket.org/InternetPartsOrder")
    @RequestWrapper(localName = "ShipmentStatus", targetNamespace = "http://www.aftermarket.org/InternetPartsOrder", className = "com.fmo.wom.iposervice121.ShipmentStatus")
    @ResponseWrapper(localName = "ShipmentStatusResponse", targetNamespace = "http://www.aftermarket.org/InternetPartsOrder", className = "com.fmo.wom.iposervice121.ShipmentStatusResponse")
    public IPOShipmentStatusResponse shipmentStatus(
        @WebParam(name = "GetShipmentBOD", targetNamespace = "http://www.aftermarket.org/InternetPartsOrder")
        String getShipmentBOD);

    /**
     * Used by the buyer to cancel an accepted purchase order.  May be requested at any time, however, the supplier may have constraints after which time the cancel will not be accepted.  This operation accepts a Cancel Purchase Order BOD and returns a Confirm BOD
     * 
     * @param cancelPurchaseOrderBOD
     * @return
     *     returns com.fmo.wom.iposervice121.IPOCancelPurchaseOrderResponse
     */
    @WebMethod(operationName = "CancelPurchaseOrder", action = "http://www.aftermarket.org/InternetPartsOrder/CancelPurchaseOrder")
    @WebResult(name = "CancelPurchaseOrderResult", targetNamespace = "http://www.aftermarket.org/InternetPartsOrder")
    @RequestWrapper(localName = "CancelPurchaseOrder", targetNamespace = "http://www.aftermarket.org/InternetPartsOrder", className = "com.fmo.wom.iposervice121.CancelPurchaseOrder")
    @ResponseWrapper(localName = "CancelPurchaseOrderResponse", targetNamespace = "http://www.aftermarket.org/InternetPartsOrder", className = "com.fmo.wom.iposervice121.CancelPurchaseOrderResponse")
    public IPOCancelPurchaseOrderResponse cancelPurchaseOrder(
        @WebParam(name = "CancelPurchaseOrderBOD", targetNamespace = "http://www.aftermarket.org/InternetPartsOrder")
        String cancelPurchaseOrderBOD);

}