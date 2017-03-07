package com.fmo.wom.business.as;

import java.util.ArrayList;
import java.util.List;

import com.fmo.wom.common.util.PropertiesUtil;
import com.fmo.wom.domain.UnitOfMeasureBO.WeightUOM;
import com.fmo.wom.domain.enums.BackOrderPolicy;
import com.fmo.wom.domain.enums.KitComponentInsufficientInventoryStrategy;
import com.fmo.wom.domain.enums.OrderType;

/**
 * Provide some default implementations for the market questions
 */
public abstract class MarketASBase {
	
    /**
     * I don't want anyone making an implementation here.  Forcing this to the subclasses to make sure
     * thought it given for each market on the order types allowed.
     * @return
     */
    public abstract List<OrderType> getAvailableOrderTypes();
   
    /** 
     * Default is to return them all.
     * @return
     */
    public List<BackOrderPolicy> getApplicableBackOrderPolicies() {
        return new ArrayList<BackOrderPolicy>(BackOrderPolicy.getDefaultValues());
    }
    
    /**
     * Default is false for Freight Forwarding
     * @return
     */
    public boolean isFreightForwardingAllowed() {
        return false;
    }
    
    /**
     * Default is false for ability to Configuring Multiple Account Codes for "Agent" Users
     * @return
     */
    public boolean isAgentAccountListConfigAllowed() {
        return false;
    }
    
    /**
     * Set the default to false for Weight Supported
     * @return
     */
    public boolean isWeightSupported() {
        return false;
    }
    
    /**
     * In keeping with current country policies, use pounds :)
     */
    public WeightUOM getDefaultWeightUnitOfMeasure() {
        return WeightUOM.POUND;
    }
    
    /** 1
     * Default implementation is to not support this
     * @return
     */
    public boolean hasQuickOrderBrandPrefixInfo() {
        return false;
    }
    
    /** 
     * Default implementation is to not support this
     * @return
     */
    public boolean hasUploadBrandPrefixInfo() {
        return false;
    }
    
    /**
     * Set the default to false for Future Order
     * @return
     */
    public boolean isFutureOrderAllowed() {
        return false;
    }
    
    /**
     * Set the default to false for 'File Download' menu option.
     * @return
     */
	public boolean hasFileDownloadOption(){
		return false;
	}
    
	/**
	 * Set the default to false for 'Publication Ordering' menu option.
	 * @return
	 */
    public boolean hasPublicationOrderingOption(){
    	return false;
    }

    /**
     * Set the default to false for Invoice viewing option.
     * @return
     */
    public boolean hasViewInvoiceOption(){
    	return false;
    }
    
    /**
     * Set the default to false for 'Parts Search' menu option.
     */
    public boolean hasPartsSearchOption() {
    	return false;
    }
    
    /**
     * Set the default to false for 'Competitor Part Interchange' menu option.
     */
    public boolean hasPartInterchangeOption(){
    	return false;
    }
    
    /**
     * Set the default to true for 'Parts Lookup' menu option.
     */
    public boolean hasPartsLookupOption(){
    	return true;
    }
    
    /**
	 * Set the default to false for display Carter message.
	 * @return
	 */
    public boolean hasCarterMessage(){
    	return false;
    }
    
    protected String lookupDefaultCustomerServiceRepEmailAddress(String key, String defaultValue) {
       
        // Get the default CSR Rep for this market from properties?
        String emailAddress = defaultValue;
        try {
            emailAddress = PropertiesUtil.getMarketProperty(key, defaultValue);
        } catch (Exception e) {
            // whoopsie; just go with the default
            ;
        }
        return emailAddress;
    }
    
    protected String lookupUrl(String key) {
    	String url = null;
    	try {
    		url = PropertiesUtil.getMarketProperty(key);
    	} catch (Exception e) {
    		;
    	}
    	return url;
    }
    
    public String getEcatalogUrl() {
    	return null;
    }
    
    protected String lookupEmailAddress(String key) {
    	String emailAddress = null;
        try {
            emailAddress = PropertiesUtil.getMarketProperty(key);
        } catch (Exception e) {
            ;
        }
        return emailAddress;
    }
    
    protected String lookupContactNumber(String key) {
    	String contactNum = null;
    	try {
    		contactNum = PropertiesUtil.getMarketProperty(key);
    	} catch (Exception e) {
    		;
    	}
    	return contactNum;
    }
    
    /**
     * default behaviour here is to allow partial kit ordering
     */
    public KitComponentInsufficientInventoryStrategy getKitComponentInventoryStrategy() {
        return KitComponentInsufficientInventoryStrategy.PARTIAL_KIT_ORDERING;
    }
}
