package com.fmo.wom.business.as;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;

import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.common.exception.WOMTransactionException;
import com.fmo.wom.common.exception.WOMValidationException;
import com.fmo.wom.domain.AccountBO;
import com.fmo.wom.domain.BackOrderBO;
import com.fmo.wom.domain.BackOrderUnitBO;
import com.fmo.wom.domain.BackOrderedItemBO;
import com.fmo.wom.domain.enums.Market;

public abstract class BackOrderASBase {

    public abstract Logger getLogger();
    
    public abstract Market getMarket();
    
    public List<BackOrderBO> searchBackOrders(List<AccountBO> billToAccounts, AccountBO shipToAccount, String orderNumber, String partNumber)
        throws WOMExternalSystemException, WOMValidationException, WOMTransactionException {
        
        throw new UnsupportedOperationException("This operation not supported for " + getMarket());
    }
    
    /**
     * Filter the passed-in Back Order list for specified Order Number.
     * @param backOrders Original Back Order list
     * @param orderNumber Order Number to filter the list on
     * @return <code>List&lt;BackOrderBO&gt;<code> - Filtered Back Order list
     */
    protected List<BackOrderBO> filterByOrderNumber(List<BackOrderBO> backOrders, String orderNumber) {
    	
    	if (GenericValidator.isBlankOrNull(orderNumber)) return backOrders;
    	
    	orderNumber = orderNumber.toUpperCase();
    	
    	List<BackOrderBO> filteredBackOrders = new ArrayList<BackOrderBO>();
    	
    	for (BackOrderBO backOrder : backOrders) {
    		
    		List<BackOrderUnitBO> boUnits = backOrder.getBackOrderUnits();
    		
    		List<BackOrderUnitBO> filteredBoUnits = new ArrayList<BackOrderUnitBO>();
    		
    		for (BackOrderUnitBO boUnit : boUnits) {
    			
    			if (orderNumber.equals(boUnit.getOrderNumber())) {
    				
    				filteredBoUnits.add(boUnit);
    			}
    		}
    		
    		if (filteredBoUnits.size() > 0) {
    			backOrder.setBackOrderUnits(filteredBoUnits);
    			filteredBackOrders.add(backOrder);
    		}
    	}
    	
    	return filteredBackOrders;
    }

    /**
     * Filter the passed-in Back Order list for specified Part Number.
     * @param backOrders Original Back Order list
     * @param partNumber Part Number to filter the list on
     * @return <code>List&lt;BackOrderBO&gt;<code> - Filtered Back Order list
     */
    protected List<BackOrderBO> filterByPartNumber(List<BackOrderBO> backOrders, String partNumber) {
    	
    	if (GenericValidator.isBlankOrNull(partNumber)) return backOrders;
    	
    	partNumber = partNumber.toUpperCase();
    	
    	List<BackOrderBO> filteredBackOrders = new ArrayList<BackOrderBO>();
    	
    	for (BackOrderBO backOrder : backOrders) {
    		
    		List<BackOrderUnitBO> boUnits = backOrder.getBackOrderUnits();
    		
    		List<BackOrderUnitBO> filteredBoUnits = new ArrayList<BackOrderUnitBO>();
    		
    		for (BackOrderUnitBO boUnit : boUnits) {
    			
    			List<BackOrderedItemBO> boItems = boUnit.getBackOrderedItems();
    			
            	List<BackOrderedItemBO> filteredBoItems = new ArrayList<BackOrderedItemBO>();
            	
    			for (BackOrderedItemBO boItem : boItems) {
    				
    				if (partNumber.equals(boItem.getPartNumber())) {
    					filteredBoItems.add(boItem);
    				}
    			}
    			
    			if (filteredBoItems.size() > 0) {
    				boUnit.setBackOrderedItems(filteredBoItems);
    				filteredBoUnits.add(boUnit);
    			}
    			
    		}
    		
    		if (filteredBoUnits.size() > 0) {
    			backOrder.setBackOrderUnits(filteredBoUnits);
    			filteredBackOrders.add(backOrder);
    		}
    	}
    	
    	return filteredBackOrders;
    }
    
}
