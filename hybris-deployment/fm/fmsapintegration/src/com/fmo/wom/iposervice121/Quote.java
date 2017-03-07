
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
 *         &lt;element name="AddRequestforQuoteBOD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "addRequestforQuoteBOD"
})
@XmlRootElement(name = "Quote")
public class Quote {

    @XmlElement(name = "AddRequestforQuoteBOD")
    protected String addRequestforQuoteBOD;

    /**
     * Gets the value of the addRequestforQuoteBOD property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddRequestforQuoteBOD() {
        return addRequestforQuoteBOD;
    }

    /**
     * Sets the value of the addRequestforQuoteBOD property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddRequestforQuoteBOD(String value) {
        this.addRequestforQuoteBOD = value;
    }

}
