/*
 * Created on Jun 6, 2011
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.fmo.wom.domain;


/**
 * @author amarnr85
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CurrencyTypeBO extends WOMCodeDescBO {

    public static final String USD = "USD";
    public static final String CDN = "CAD";
    public static final String US_CDN_SYMBOL = "$";

    public static String getSymbolForUsCan(String inputCurrency) {
        if (USD.equals(inputCurrency) || CDN.equals(inputCurrency)) {
            return US_CDN_SYMBOL;
        }
        return inputCurrency;
    }
	@Override
	public String toString() {
		return "CurrencyTypeBO [code=" + code + ", desc=" + desc
//				+ ", createUserId=" + createUserId + ", createTimestamp="
//				+ createTimestamp + ", updateUserId=" + updateUserId
//				+ ", updateTimestamp=" + updateTimestamp 
				+ "]";
	}

}
