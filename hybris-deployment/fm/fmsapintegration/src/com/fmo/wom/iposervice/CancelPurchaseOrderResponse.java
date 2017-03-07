
package com.fmo.wom.iposervice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CancelPurchaseOrderResult" type="{http://www.aaiasoa.org/IPOv2}IPOCancelPurchaseOrderResponse" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "cancelPurchaseOrderResult"
})
@XmlRootElement(name = "CancelPurchaseOrderResponse")
public class CancelPurchaseOrderResponse {

    @XmlElement(name = "CancelPurchaseOrderResult")
    protected IPOCancelPurchaseOrderResponse cancelPurchaseOrderResult;

    /**
     * Gets the value of the cancelPurchaseOrderResult property.
     * 
     * @return
     *     possible object is
     *     {@link IPOCancelPurchaseOrderResponse }
     *     
     */
    public IPOCancelPurchaseOrderResponse getCancelPurchaseOrderResult() {
        return cancelPurchaseOrderResult;
    }

    /**
     * Sets the value of the cancelPurchaseOrderResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link IPOCancelPurchaseOrderResponse }
     *     
     */
    public void setCancelPurchaseOrderResult(IPOCancelPurchaseOrderResponse value) {
        this.cancelPurchaseOrderResult = value;
    }

}
