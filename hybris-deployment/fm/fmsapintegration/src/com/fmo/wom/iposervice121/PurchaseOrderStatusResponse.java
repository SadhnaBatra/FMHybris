
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
 *         &lt;element name="PurchaseOrderStatusResult" type="{http://www.aftermarket.org/InternetPartsOrder}IPOPurchaseOrderStatusResponse" minOccurs="0"/>
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
    "purchaseOrderStatusResult"
})
@XmlRootElement(name = "PurchaseOrderStatusResponse")
public class PurchaseOrderStatusResponse {

    @XmlElement(name = "PurchaseOrderStatusResult")
    protected IPOPurchaseOrderStatusResponse purchaseOrderStatusResult;

    /**
     * Gets the value of the purchaseOrderStatusResult property.
     * 
     * @return
     *     possible object is
     *     {@link IPOPurchaseOrderStatusResponse }
     *     
     */
    public IPOPurchaseOrderStatusResponse getPurchaseOrderStatusResult() {
        return purchaseOrderStatusResult;
    }

    /**
     * Sets the value of the purchaseOrderStatusResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link IPOPurchaseOrderStatusResponse }
     *     
     */
    public void setPurchaseOrderStatusResult(IPOPurchaseOrderStatusResponse value) {
        this.purchaseOrderStatusResult = value;
    }

}
