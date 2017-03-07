
package com.fmo.wom.iposervice121;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IPOCreatePurchaseOrderAcknowledgePurchaseOrderResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IPOCreatePurchaseOrderAcknowledgePurchaseOrderResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.aftermarket.org/InternetPartsOrder}IPOCreatePurchaseOrderResponse">
 *       &lt;sequence>
 *         &lt;element name="AcknowledgePurchaseOrderBOD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IPOCreatePurchaseOrderAcknowledgePurchaseOrderResponse", propOrder = {
    "acknowledgePurchaseOrderBOD"
})
public class IPOCreatePurchaseOrderAcknowledgePurchaseOrderResponse
    extends IPOCreatePurchaseOrderResponse
{

    @XmlElement(name = "AcknowledgePurchaseOrderBOD")
    protected String acknowledgePurchaseOrderBOD;

    /**
     * Gets the value of the acknowledgePurchaseOrderBOD property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAcknowledgePurchaseOrderBOD() {
        return acknowledgePurchaseOrderBOD;
    }

    /**
     * Sets the value of the acknowledgePurchaseOrderBOD property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAcknowledgePurchaseOrderBOD(String value) {
        this.acknowledgePurchaseOrderBOD = value;
    }

}
