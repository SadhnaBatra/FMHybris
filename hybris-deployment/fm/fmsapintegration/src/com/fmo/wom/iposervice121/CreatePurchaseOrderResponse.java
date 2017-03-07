
package com.fmo.wom.iposervice121;

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
 *         &lt;element name="CreatePurchaseOrderResult" type="{http://www.aftermarket.org/InternetPartsOrder}IPOCreatePurchaseOrderResponse" minOccurs="0"/>
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
    "createPurchaseOrderResult"
})
@XmlRootElement(name = "CreatePurchaseOrderResponse")
public class CreatePurchaseOrderResponse {

    @XmlElement(name = "CreatePurchaseOrderResult")
    protected IPOCreatePurchaseOrderResponse createPurchaseOrderResult;

    /**
     * Gets the value of the createPurchaseOrderResult property.
     * 
     * @return
     *     possible object is
     *     {@link IPOCreatePurchaseOrderResponse }
     *     
     */
    public IPOCreatePurchaseOrderResponse getCreatePurchaseOrderResult() {
        return createPurchaseOrderResult;
    }

    /**
     * Sets the value of the createPurchaseOrderResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link IPOCreatePurchaseOrderResponse }
     *     
     */
    public void setCreatePurchaseOrderResult(IPOCreatePurchaseOrderResponse value) {
        this.createPurchaseOrderResult = value;
    }

}
