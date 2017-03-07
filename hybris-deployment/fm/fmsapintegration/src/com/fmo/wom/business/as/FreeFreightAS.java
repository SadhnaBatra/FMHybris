package com.fmo.wom.business.as;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;

import com.fmo.wom.domain.AccountBO;
import com.fmo.wom.domain.AddressBO;
import com.fmo.wom.domain.ItemBO;
import com.fmo.wom.domain.OrderBO;

public class FreeFreightAS {
	
	private static Logger logger = Logger.getLogger(FreeFreightAS.class);
	
	public static final String CHAMPION_PRODUCT_FLAG = "FCC";
	
	public boolean isQualifyForFreeFreight(OrderBO order) {
		
		//the order amount must reach minimum free freight amount set for this customer
        if (isReachedFreeFreightAmt(order)) {
            boolean freeShipping = false;
            // Only qualifies if at least one of the shipping methods in entire order is allow free freight   
            for (ItemBO lineItem : order.getItemList()) { 
                
                // if we can process this item, consider it in the free freight calculation
                if (lineItem.canProcessItem()) {
                    
                    freeShipping = freeShipping || lineItem.getSelectedShippingMethod().isQualifiesForFreeFreight();
                }
            } 
            return freeShipping;
        }
        // made it all the way through, so nothing qualified.
        return false;
	}
	
	/**
	 * Determines if a prepared web order is qualified for free freight.  
	 * This differs from <code>isQualifiedForFreeFreight(OrderBO)</code> in that 
	 * it used to determine if the free freight shipping method selection(s)
	 * should be presented to the user.
	 * @param order the prepared order
	 * @return true if order qualifies for free freight, false otherwise
	 */
	public boolean isQualifiedForFreeFreightSelection(OrderBO order) {

		//Order placed by ShipTo customer will not qualify for free freight
		if(!order.getBillToAcct().isFreeFreightAllowed()) {
			return false;
		}
		//the order amount must reach minimum free freight amount set for this customer
        if (isReachedFreeFreightAmt(order)) {
            return true;
        }
        // made it all the way through, so nothing qualified.
        return false;
	}
	
    /**
     * Returns true if order has enough money to qualify for free freight.
     * Returns false otherwise.
     * Shipping methods are not taken into consideration here.
     * 
     * @return <code>boolean</code>
     */
    public boolean isReachedFreeFreightAmt(OrderBO order)
    {
    	AccountBO billtoAcct = order.getBillToAcct();
    	
    	// not sure what to do here - does no min imply no free freight?  Setting
    	// to huge in the meantime
    	int minfreeFreightAmt = Integer.MAX_VALUE;
    	BigDecimal minFreeFreightAmt = null;
    	
    	if(!isContainAllCarter(order.getItemList())){
    		minFreeFreightAmt = billtoAcct.getMinFreeFreightAmt();
    	}else{
    		minFreeFreightAmt = new BigDecimal("1000.00");
    	}
    	
    	if (minFreeFreightAmt != null) {
    	    minfreeFreightAmt= minFreeFreightAmt.intValue();
    	}
    	
        double orderFreightValue = getFreightValue(order.getItemList());
        logger.info("The minimum free freight amount for this customer is " + minfreeFreightAmt);
        logger.info("The current amount for the cart is " + orderFreightValue);        
       
        if(isContainAllChampion(order.getItemList())) {       	
            //implement Champion free freight policy, if order contains all champion products, and the bill to
            // is in US or CA it's free shipping
           	String billToCountry = billtoAcct.getAddress().getCountry().getIsoCountryCode();
        	return (AddressBO.US_CODE.equalsIgnoreCase(billToCountry) || AddressBO.CANADA_CODE.equalsIgnoreCase(billToCountry)) && orderFreightValue >= 1;
        }
        else if (orderFreightValue >= minfreeFreightAmt) {
            //order freight value exceed minfreeFreightAmt
        	return true;
        }
      
        // nothing applied
        return false;
    }

        
    /**
     * Returns boolean value for if order contain all Champion products
     *
     * @param itemList
     * @return true if all items in the list have champion product flag, false otherwise
     */
    public boolean  isContainAllChampion(List<ItemBO> itemList) {          
        
        for (ItemBO lineItem : itemList) { 
            if (isChampion(lineItem) == false) {
                // found one that is not champion - can return immeidately.
                return false;
            }
        }
        return true;
    }
    
    
    
    /**
     * Returns boolean value for if order contain all Carter products
     *
     * @param itemList
     * @return true if all items in the list are Carter parts, false otherwise
     */
    public boolean  isContainAllCarter(List<ItemBO> itemList) {          
        
        for (ItemBO lineItem : itemList) { 
            if (!lineItem.isCarterPart()) {
                return false;
            }
        }
        return true;
    }
        
    /**
     * Returns freight value for the entire order
     *
     * @return <code>double</code> representing entire cart
     */
    public double getFreightValue(List<ItemBO> itemList) {          
        double freightValueEntireOrder = 0.0d;
            
        for (ItemBO lineItem : itemList) {  
        	if(null != lineItem.getItemPrice()) {
        		int quantity = 1;
        		if ( lineItem.getItemQty() != null ) {
        			quantity = lineItem.getItemQty().getRequestedQuantity();
        		}
        		double price = lineItem.getItemPrice().getFreightPrice();
        		freightValueEntireOrder +=  price * quantity;
        	}
        }
            
        /* Had some issues with numbers like .##999999 */
        return (Math.round(freightValueEntireOrder*100)/100.0);
    }
        
    /**
     * Utility to check if this item is a champion product flag.  This could
     * be moved to Item if need arises for further access.  Also, should the 
     * product flag we're checking be configurable?
     * @param anItem
     * @return
     */
    private boolean isChampion(ItemBO anItem) {
        if (anItem == null ) {
            return false;
        }
            
        return CHAMPION_PRODUCT_FLAG.equalsIgnoreCase(anItem.getProductFlag());
    }
    
    
}
