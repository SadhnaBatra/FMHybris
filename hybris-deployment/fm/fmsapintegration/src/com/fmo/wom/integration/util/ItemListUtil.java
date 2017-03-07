package com.fmo.wom.integration.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.fmo.wom.domain.ItemBO;
import com.fmo.wom.domain.enums.ExternalSystem;

public class ItemListUtil {

    private static Logger logger = Logger.getLogger(ItemListUtil.class);
    
    /**
     * Filter the given item list for NABS parts 
     * @param itemList
     * @return list of parts for NABS
     */
    public static List<ItemBO> getNabsItemList(List<ItemBO> itemList) {
        return getItemListForSystem(itemList, ExternalSystem.NABS);
    }
       
    /**
     * Filter the given item list for SAP parts 
     * @param itemList
     * @return list of parts for SAP
     */
    public static List<ItemBO> getSapItemList(List<ItemBO> itemList) {
        return getItemListForSystem(itemList, ExternalSystem.EVRST);
    }
    
    /**
     * Filter the given item list for RP parts 
     * @param itemList
     * @return list of parts for RP
     */
    public static List<ItemBO> getRPItemList(List<ItemBO> itemList) {
        return getItemListForSystem(itemList, ExternalSystem.RP1);
    }
    
    /**
     * For the given input list of items, filter for parts for the given external system only
     * @param itemList list of parts to filter
     * @param externalSystem system to return parts for
     * @return list of parts with the given external system
     */
    public static List<ItemBO> getItemListForSystem(List<ItemBO> itemList, ExternalSystem externalSystem) {
    	
    	logger.debug("@@ Inside getItemListForSystem : ItemListUtil @@");//Added by Abhijit
        List<ItemBO> itemsBySystem = new ArrayList<ItemBO>();
        if (itemList == null) {
            return itemsBySystem;
        }
        
        // now go through input list and add to colleciton if they match our input system
        for (ItemBO anItemBO : itemList) {
        	logger.debug("Get External System for the Item "+anItemBO.getExternalSystem());//Added by Abhijit
            if (externalSystem == anItemBO.getExternalSystem()) {
                itemsBySystem.add(anItemBO);
            }
        }
        return itemsBySystem;
    }

    public static List<ItemBO> mergeResults(List<ItemBO> completeItemList, List<ItemBO> resolvedItemList) {
        // create a map of the line number -> resolved items to be able to retrieve the objects via line sequence number
       Map<Integer, ItemBO> lineNumberResolvedItemMap = createLineNumberItemMap(resolvedItemList);
       
       int index = 0;
       for (ItemBO anInputItem : completeItemList) {
           
           Integer inputLineItemNumber = anInputItem.getLineNumber();
           
           // If this input item line number is in the resolved map, replace the object
           if (lineNumberResolvedItemMap.containsKey(inputLineItemNumber) == true) {
               completeItemList.set(index, lineNumberResolvedItemMap.get(inputLineItemNumber));
           } 
           
           index++;
       }
       
       return completeItemList;
    }
    
    public static Map<Integer, ItemBO> createLineNumberItemMap(List<ItemBO> itemList) {
        Map<Integer, ItemBO> lineNumberItemMap = new HashMap<Integer, ItemBO>();
        
        for (ItemBO anInputItem : itemList) {
            lineNumberItemMap.put(anInputItem.getLineNumber(), anInputItem);
        }
        
        return lineNumberItemMap;
    }
    
}