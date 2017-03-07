
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
 *         &lt;element name="GetShipmentBOD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "getShipmentBOD"
})
@XmlRootElement(name = "ShipmentStatus")
public class ShipmentStatus {

    @XmlElement(name = "GetShipmentBOD")
    protected String getShipmentBOD;

    /**
     * Gets the value of the getShipmentBOD property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetShipmentBOD() {
        return getShipmentBOD;
    }

    /**
     * Sets the value of the getShipmentBOD property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetShipmentBOD(String value) {
        this.getShipmentBOD = value;
    }

}
