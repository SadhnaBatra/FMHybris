
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
 *         &lt;element name="ChangePurchaseOrderResult" type="{http://www.aaiasoa.org/IPOv2}IPOChangePurchaseOrderResponse" minOccurs="0"/>
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
    "changePurchaseOrderResult"
})
@XmlRootElement(name = "ChangePurchaseOrderResponse")
public class ChangePurchaseOrderResponse {

    @XmlElement(name = "ChangePurchaseOrderResult")
    protected IPOChangePurchaseOrderResponse changePurchaseOrderResult;

    /**
     * Gets the value of the changePurchaseOrderResult property.
     * 
     * @return
     *     possible object is
     *     {@link IPOChangePurchaseOrderResponse }
     *     
     */
    public IPOChangePurchaseOrderResponse getChangePurchaseOrderResult() {
        return changePurchaseOrderResult;
    }

    /**
     * Sets the value of the changePurchaseOrderResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link IPOChangePurchaseOrderResponse }
     *     
     */
    public void setChangePurchaseOrderResult(IPOChangePurchaseOrderResponse value) {
        this.changePurchaseOrderResult = value;
    }

}
