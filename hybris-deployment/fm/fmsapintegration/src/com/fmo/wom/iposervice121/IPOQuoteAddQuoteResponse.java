
package com.fmo.wom.iposervice121;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IPOQuoteAddQuoteResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IPOQuoteAddQuoteResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.aftermarket.org/InternetPartsOrder}IPOQuoteResponse">
 *       &lt;sequence>
 *         &lt;element name="AddQuoteBOD" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IPOQuoteAddQuoteResponse", propOrder = {
    "addQuoteBOD"
})
public class IPOQuoteAddQuoteResponse
    extends IPOQuoteResponse
{

    @XmlElement(name = "AddQuoteBOD")
    protected String addQuoteBOD;

    /**
     * Gets the value of the addQuoteBOD property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddQuoteBOD() {
        return addQuoteBOD;
    }

    /**
     * Sets the value of the addQuoteBOD property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddQuoteBOD(String value) {
        this.addQuoteBOD = value;
    }

}
