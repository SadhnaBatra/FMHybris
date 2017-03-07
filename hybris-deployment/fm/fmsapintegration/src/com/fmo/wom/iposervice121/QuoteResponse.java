
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
 *         &lt;element name="QuoteResult" type="{http://www.aftermarket.org/InternetPartsOrder}IPOQuoteResponse" minOccurs="0"/>
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
    "quoteResult"
})
@XmlRootElement(name = "QuoteResponse")
public class QuoteResponse {

    @XmlElement(name = "QuoteResult")
    protected IPOQuoteResponse quoteResult;

    /**
     * Gets the value of the quoteResult property.
     * 
     * @return
     *     possible object is
     *     {@link IPOQuoteResponse }
     *     
     */
    public IPOQuoteResponse getQuoteResult() {
        return quoteResult;
    }

    /**
     * Sets the value of the quoteResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link IPOQuoteResponse }
     *     
     */
    public void setQuoteResult(IPOQuoteResponse value) {
        this.quoteResult = value;
    }

}
