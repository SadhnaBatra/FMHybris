package com.fmo.wom.business.as;

import java.util.List;

import com.fmo.wom.business.as.config.ConfigurableAS;
import com.fmo.wom.common.exception.WOMProcessException;
import com.fmo.wom.common.exception.WOMResourceException;
import com.fmo.wom.common.exception.WOMValidationException;
import com.fmo.wom.domain.OrderBO;
import com.fmo.wom.domain.UnitOfMeasureBO.WeightUOM;
import com.fmo.wom.domain.enums.BackOrderPolicy;
import com.fmo.wom.domain.enums.KitComponentInsufficientInventoryStrategy;
import com.fmo.wom.domain.enums.OrderType;

public interface MarketAS extends ConfigurableAS {
	
    /**
     * Get the available order types for the market
     * @return
     */
    public List<OrderType> getAvailableOrderTypes();
    
    /**
     * Get the supported back order policies for this market
     * @return
     */
    public List<BackOrderPolicy> getApplicableBackOrderPolicies();
    
    /**
     * Determine if this market supports Freight Forwarding
     * @return
     */
    public boolean isFreightForwardingAllowed();
    
    /**
     * Determine if this market supports Configuring Multiple Account Codes for "Agent" Users
     * @return
     */
    public boolean isAgentAccountListConfigAllowed();
    
    /**
     * Not all markets get part weights from their respective back ends. This
     * will return if they do or not for subsequent calculations
     * @return
     */
    public boolean isWeightSupported();
    
    /**
     * ERPs do not always (or ever) return the units the material weights are in. In
     * these cases, the unit of measure is assumed.  This will return that assumed unit
     * of measure for the market.
     * @return
     */
    public WeightUOM getDefaultWeightUnitOfMeasure();
    
    /**
     * Retrieve the default customer service representative email address for
     * the market.
     * @return
     */
    public String getDefaultCustomerServiceRepEmailAddress();
    
    /**
     * Gets the e-Catalog URL for the market
     * @return url string or null if e-catalog is not supported for market
     */
    public String getEcatalogUrl();
    
    /**
     * Generic way to define if a region supports any brand prefix info on the quick order page
     * @return
     */
    public boolean hasQuickOrderBrandPrefixInfo();
    
    /**
     * Generic way to define if a region supports any brand prefix info on the upload page
     * @return
     */
    public boolean hasUploadBrandPrefixInfo();
    
    
    /**
     * Determine if this market supports future order
     * @return
     */
    public boolean isFutureOrderAllowed();
    
    
    /**
     * Determine if this market supports File download
     * @return
     */
    public boolean hasFileDownloadOption();
    
    
    /**
     * Determine if this market supports publication ordering
     * @return
     */
    public boolean hasPublicationOrderingOption();
    
    /**
     * Determine if this market have view invoice function
     * @return
     */
    public boolean hasViewInvoiceOption();
    
    /**
     * Determine if this market supports Parts Search function
     * @return
     */
    public boolean hasPartsSearchOption();
    
    /**
     * Determine if this market supports Competitor Part Interchange function
     * @return
     */
    public boolean hasPartInterchangeOption();
    
    /**
     * Determine if this market supports Parts Lookup function
     * @return
     */
    public boolean hasPartsLookupOption();
    
    /**
     * Determine if this market display Carter brand specific message
     * @return
     */
    public boolean hasCarterMessage();
    /**
     * Gets the Publication Ordering URL for the market
     * @return 
     */
    public String getPublicationOrderingUrl();
    
    /**
     * Gets the File Download URL for the market
     * @return 
     */
    public String getFileDownloadUrl();
    
    /**
     * Gets the email address of email order for the market
     * @return 
     */
    public String getEmailOrderAddress();
    
    /**
     * Gets the credit on hold contact phone number for the market
     * @return 
     */
    public String getCreditOnHoldContactNbr();
    
    /**
     * Determine if the market supports account search. Currently only those markets with accounts
     * in the DB support it, so is disabled for others.  Once a BAPI or similar is exposed for
     * the others, we'll turn them back on.
     */
    public boolean hasAccountSearch();
    
    /**
     * Markets would like to define how to treat insufficient quantities for kit components
     * differently.  This will return which strategy each market will use.  Obviously, this
     * means its a market wide setting.
     * @return
     */
    public KitComponentInsufficientInventoryStrategy getKitComponentInventoryStrategy();
}
