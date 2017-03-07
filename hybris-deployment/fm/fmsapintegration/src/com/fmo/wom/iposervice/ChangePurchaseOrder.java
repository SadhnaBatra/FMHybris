
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
 *         &lt;element name="ChangePurchaseOrderBOD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "changePurchaseOrderBOD"
})
@XmlRootElement(name = "ChangePurchaseOrder")
public class ChangePurchaseOrder {

    @XmlElement(name = "ChangePurchaseOrderBOD")
    protected String changePurchaseOrderBOD;

    /**
     * Gets the value of the changePurchaseOrderBOD property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChangePurchaseOrderBOD() {
        return changePurchaseOrderBOD;
    }

    /**
     * Sets the value of the changePurchaseOrderBOD property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChangePurchaseOrderBOD(String value) {
        this.changePurchaseOrderBOD = value;
    }

}
