
package com.fmo.wom.iposervice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IPOShipmentStatusShowShipmentResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IPOShipmentStatusShowShipmentResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.aaiasoa.org/IPOv2}IPOShipmentStatusResponse">
 *       &lt;sequence>
 *         &lt;element name="ShowShipmentBOD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IPOShipmentStatusShowShipmentResponse", propOrder = {
    "showShipmentBOD"
})
public class IPOShipmentStatusShowShipmentResponse
    extends IPOShipmentStatusResponse
{

    @XmlElement(name = "ShowShipmentBOD")
    protected String showShipmentBOD;

    /**
     * Gets the value of the showShipmentBOD property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShowShipmentBOD() {
        return showShipmentBOD;
    }

    /**
     * Sets the value of the showShipmentBOD property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShowShipmentBOD(String value) {
        this.showShipmentBOD = value;
    }

}
