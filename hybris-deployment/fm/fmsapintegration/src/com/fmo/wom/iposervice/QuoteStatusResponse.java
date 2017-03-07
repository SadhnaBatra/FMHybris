
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
 *         &lt;element name="QuoteStatusResult" type="{http://www.aaiasoa.org/IPOv2}IPOQuoteStatusResponse" minOccurs="0"/>
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
    "quoteStatusResult"
})
@XmlRootElement(name = "QuoteStatusResponse")
public class QuoteStatusResponse {

    @XmlElement(name = "QuoteStatusResult")
    protected IPOQuoteStatusResponse quoteStatusResult;

    /**
     * Gets the value of the quoteStatusResult property.
     * 
     * @return
     *     possible object is
     *     {@link IPOQuoteStatusResponse }
     *     
     */
    public IPOQuoteStatusResponse getQuoteStatusResult() {
        return quoteStatusResult;
    }

    /**
     * Sets the value of the quoteStatusResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link IPOQuoteStatusResponse }
     *     
     */
    public void setQuoteStatusResult(IPOQuoteStatusResponse value) {
        this.quoteStatusResult = value;
    }

}
