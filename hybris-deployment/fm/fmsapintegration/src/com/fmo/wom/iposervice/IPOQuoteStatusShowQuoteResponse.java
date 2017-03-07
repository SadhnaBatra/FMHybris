
package com.fmo.wom.iposervice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IPOQuoteStatusShowQuoteResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IPOQuoteStatusShowQuoteResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.aaiasoa.org/IPOv2}IPOQuoteStatusResponse">
 *       &lt;sequence>
 *         &lt;element name="ShowQuoteBOD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IPOQuoteStatusShowQuoteResponse", propOrder = {
    "showQuoteBOD"
})
public class IPOQuoteStatusShowQuoteResponse
    extends IPOQuoteStatusResponse
{

    @XmlElement(name = "ShowQuoteBOD")
    protected String showQuoteBOD;

    /**
     * Gets the value of the showQuoteBOD property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShowQuoteBOD() {
        return showQuoteBOD;
    }

    /**
     * Sets the value of the showQuoteBOD property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShowQuoteBOD(String value) {
        this.showQuoteBOD = value;
    }

}
