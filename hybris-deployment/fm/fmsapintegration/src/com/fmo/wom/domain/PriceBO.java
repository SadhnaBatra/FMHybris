/*
 * Created on Jun 6, 2011
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.fmo.wom.domain;

//import javax.persistence.*;

import com.fmo.wom.domain.enums.CustomerBusinessType;

/**
 * //author amarnr85
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
//Embeddable
public class PriceBO {

	//Transient
	private CurrencyTypeBO currency;
	
	//Transient
	private CustomerBusinessType priceType = CustomerBusinessType.NONE;
	
	//Column (name="DISPLAY_PRICE")
	private double displayPrice;
	
	//Column (name="FREIGHT_PRICE")
    private Double freightPrice;
	
	public CurrencyTypeBO getCurrency() {
		return currency;
	}
	public void setCurrency(CurrencyTypeBO currency) {
		this.currency = currency;
	}
	public CustomerBusinessType getPriceType() {
		return priceType;
	}
	public void setPriceType(CustomerBusinessType priceType) {
		this.priceType = priceType;
	}
	public double getDisplayPrice() {
		return displayPrice;
	}
	public void setDisplayPrice(double displayPrice) {
		this.displayPrice = displayPrice;
	}
	public Double getFreightPrice() {
		return freightPrice;
	}
	public void setFreightPrice(Double freightPrice) {
		this.freightPrice = freightPrice;
	}
	
	//Override
	public String toString() {
		return "PriceBO [currency=" + currency + ", priceType=" + priceType
				+ ", displayPrice=" + displayPrice + ", freightPrice="
				+ freightPrice + "]";
	}

	
}
