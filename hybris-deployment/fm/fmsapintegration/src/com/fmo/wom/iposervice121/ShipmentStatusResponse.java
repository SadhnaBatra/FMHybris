
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
 *         &lt;element name="ShipmentStatusResult" type="{http://www.aftermarket.org/InternetPartsOrder}IPOShipmentStatusResponse" minOccurs="0"/>
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
    "shipmentStatusResult"
})
@XmlRootElement(name = "ShipmentStatusResponse")
public class ShipmentStatusResponse {

    @XmlElement(name = "ShipmentStatusResult")
    protected IPOShipmentStatusResponse shipmentStatusResult;

    /**
     * Gets the value of the shipmentStatusResult property.
     * 
     * @return
     *     possible object is
     *     {@link IPOShipmentStatusResponse }
     *     
     */
    public IPOShipmentStatusResponse getShipmentStatusResult() {
        return shipmentStatusResult;
    }

    /**
     * Sets the value of the shipmentStatusResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link IPOShipmentStatusResponse }
     *     
     */
    public void setShipmentStatusResult(IPOShipmentStatusResponse value) {
        this.shipmentStatusResult = value;
    }

}
