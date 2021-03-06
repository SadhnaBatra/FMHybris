
package com.fmo.wom.iposervice121;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IPOCreatePurchaseOrderConfirmResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IPOCreatePurchaseOrderConfirmResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.aftermarket.org/InternetPartsOrder}IPOCreatePurchaseOrderResponse">
 *       &lt;sequence>
 *         &lt;element name="ConfirmBOD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IPOCreatePurchaseOrderConfirmResponse", propOrder = {
    "confirmBOD"
})
public class IPOCreatePurchaseOrderConfirmResponse
    extends IPOCreatePurchaseOrderResponse
{

    @XmlElement(name = "ConfirmBOD")
    protected String confirmBOD;

    /**
     * Gets the value of the confirmBOD property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConfirmBOD() {
        return confirmBOD;
    }

    /**
     * Sets the value of the confirmBOD property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConfirmBOD(String value) {
        this.confirmBOD = value;
    }

}
