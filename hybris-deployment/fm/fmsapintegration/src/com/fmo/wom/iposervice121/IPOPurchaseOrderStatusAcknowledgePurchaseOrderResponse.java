
package com.fmo.wom.iposervice121;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IPOPurchaseOrderStatusAcknowledgePurchaseOrderResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IPOPurchaseOrderStatusAcknowledgePurchaseOrderResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.aftermarket.org/InternetPartsOrder}IPOPurchaseOrderStatusResponse">
 *       &lt;sequence>
 *         &lt;element name="AcknowlegePurchaseOrderBOD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IPOPurchaseOrderStatusAcknowledgePurchaseOrderResponse", propOrder = {
    "acknowlegePurchaseOrderBOD"
})
public class IPOPurchaseOrderStatusAcknowledgePurchaseOrderResponse
    extends IPOPurchaseOrderStatusResponse
{

    @XmlElement(name = "AcknowlegePurchaseOrderBOD")
    protected String acknowlegePurchaseOrderBOD;

    /**
     * Gets the value of the acknowlegePurchaseOrderBOD property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAcknowlegePurchaseOrderBOD() {
        return acknowlegePurchaseOrderBOD;
    }

    /**
     * Sets the value of the acknowlegePurchaseOrderBOD property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAcknowlegePurchaseOrderBOD(String value) {
        this.acknowlegePurchaseOrderBOD = value;
    }

}
