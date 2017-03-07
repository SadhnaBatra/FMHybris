package com.fmo.wom.business.as;

import java.util.Arrays;
import java.util.List;

import com.fmo.wom.business.as.config.USCanAS;
import com.fmo.wom.domain.enums.KitComponentInsufficientInventoryStrategy;
import com.fmo.wom.domain.enums.Market;
import com.fmo.wom.domain.enums.OrderType;

/**
 * Provide some default implementations for the market questions
 */
public class MarketASUSCanImpl extends MarketASBase implements MarketAS, USCanAS{
	
    // these are in order of precedence.
    private static List<OrderType> availableOrderTypes = 
        Arrays.asList(OrderType.EMERGENCY, OrderType.STOCK, OrderType.REGULAR);

    private static final String MARKET_EMAIL_KEY = "uscan.default.csr.rep.email";
    private static final String MARKET_EMAIL_DEFAULT = "Daryl.Hawken@federalmogul.com";
    private static final String MARKET_ECATALOG_URL_KEY = "uscan.ecatalog.url";
    private static final String MARKET_PUBLICATION_ORDERING_URL_KEY = "uscan.PublicOrdering.url";
    private static final String MARKET_FILE_DOWNLOAD_URL_KEY = "uscan.fileDownload.url";
    private static final String MARKET_EMAIL_ORDER_ADDRESS_KEY = "uscan.emailOrder.address";
    private static final String MARKET_CREDIT_ON_HOLD_CONTACT_KEY = "uscan.creditOnHold.contact.number";


    @Override
    public Market getMarket() {
        return Market.US_CANADA;
    }
    
    @Override
    public List<OrderType> getAvailableOrderTypes() {
        return availableOrderTypes;
    }
    
    @Override
    public boolean isFreightForwardingAllowed() {
        return false;
    }
    
    @Override
    public boolean isWeightSupported() {
        return true;
    }

    /**
     * US/Can does support brand prefix.
     */
    @Override
    public boolean hasQuickOrderBrandPrefixInfo() {
        return true;
    }
    
    @Override
    public boolean hasUploadBrandPrefixInfo() {
        return true;
    }
    
    /**
     * Set true for future order for NA
     * @return
     */
    @Override
    public boolean isFutureOrderAllowed() {
        return true;
    }
    
    @Override
	public boolean hasFileDownloadOption(){
		return true;
	}
	    
    
	@Override
    public boolean hasPublicationOrderingOption(){
    	return true;
    }
    
    @Override
    public boolean hasViewInvoiceOption(){
    	return true;
    }
    
    
    @Override
    public boolean hasPartInterchangeOption(){
    	return true;
    }
    
    @Override
    public String getDefaultCustomerServiceRepEmailAddress() {
        return lookupDefaultCustomerServiceRepEmailAddress(MARKET_EMAIL_KEY, MARKET_EMAIL_DEFAULT);
    }

    @Override
    public String getEcatalogUrl() {
    	return lookupUrl(MARKET_ECATALOG_URL_KEY);
    }
    
    @Override
    public String getPublicationOrderingUrl() {
    	return lookupUrl(MARKET_PUBLICATION_ORDERING_URL_KEY);
    }
    
    @Override
    public String getFileDownloadUrl() {
    	return lookupUrl(MARKET_FILE_DOWNLOAD_URL_KEY);
    }
    
    
    @Override
    public String getEmailOrderAddress() {
    	return lookupEmailAddress(MARKET_EMAIL_ORDER_ADDRESS_KEY);
    }
    
    @Override
    public String getCreditOnHoldContactNbr() {
    	return lookupContactNumber(MARKET_CREDIT_ON_HOLD_CONTACT_KEY);
    }
    
    @Override
    public boolean hasAccountSearch() {
        return true;
    }
    
    @Override
    public boolean hasCarterMessage() {
        return true;
    }
 
    /**
     * US/Can does not allow partial kit ordering
     */
    public KitComponentInsufficientInventoryStrategy getKitComponentInventoryStrategy() {
        return KitComponentInsufficientInventoryStrategy.NO_PARTIAL_KIT_ORDERING;
    }
}
