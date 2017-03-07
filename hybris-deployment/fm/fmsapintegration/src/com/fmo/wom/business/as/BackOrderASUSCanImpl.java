package com.fmo.wom.business.as;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;

import com.fmo.wom.business.as.config.USCanAS;
import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.common.exception.WOMTransactionException;
import com.fmo.wom.common.exception.WOMValidationException;
import com.fmo.wom.common.util.StringUtil;
import com.fmo.wom.domain.AccountBO;
import com.fmo.wom.domain.BackOrderBO;
import com.fmo.wom.domain.BackOrderUnitBO;
import com.fmo.wom.domain.SapAcctBO;
import com.fmo.wom.domain.enums.Market;
import com.fmo.wom.integration.NabsService;
import com.fmo.wom.integration.SAPService;

public class BackOrderASUSCanImpl extends BackOrderASBase implements BackOrderAS, USCanAS {

    private static Logger logger = Logger.getLogger(BackOrderASUSCanImpl.class);

	private NabsService nabsService;
	
	@Override
	public Market getMarket() {
        return Market.US_CANADA;
	}

	@Override
	public Logger getLogger() {
        return logger;
	}

	
	public BackOrderASUSCanImpl() {
		super();
		nabsService   = WOMServices.getNabsService();
	}

	@Override
	public List<BackOrderBO> searchBackOrders(AccountBO account, String orderNumber, String partNumber)
			throws WOMExternalSystemException, WOMTransactionException {

	    // Call NABS to get Back Orders.
        List<BackOrderBO> nabsBackOrders = nabsService.getBackOrders(account.getAccountCode());
        
        // Go through the NABS Back Orders and set the passed-in account on them since the passed-in
        // Account object has more information than the returned data.
        for (BackOrderBO nabsBackOrder : nabsBackOrders) {
        	if (account.getAccountCode().equals(nabsBackOrder.getBillToAccount().getAccountCode())) {
        		nabsBackOrder.setBillToAccount(account);
        	}
        }

        String sapAcctCode = null;
        SapAcctBO sapAccount = account.getSapAccount();
        if (sapAccount != null) {
        	sapAcctCode = sapAccount.getSapAccountCode();
        }
        
        List<BackOrderBO> sapBackOrders = new ArrayList<BackOrderBO>();
        if (!GenericValidator.isBlankOrNull(sapAcctCode)) {
        	// Call SAP to get Back Orders.
            sapBackOrders = SAPService.getBackOrders(sapAcctCode);
        
            // Go through the SAP Back Orders and set the passed-in account on them since the passed-in
            // Account object has more information than the returned data.
            for (BackOrderBO sapBackOrder : sapBackOrders) {
                sapBackOrder.setBillToAccount(account);
            }
        }
        
        // Merge the Back Orders from NABS and SAP systems and send them back.
        List<BackOrderBO> mergedBackOrders = mergeRetrievedBackOrders(nabsBackOrders, sapBackOrders);

        // Filter the list for passed-in Order Number.
        List<BackOrderBO> filteredBackOrders = filterByOrderNumber(mergedBackOrders, orderNumber);
        
        // Filter the list for passed-in Part Number.
        filteredBackOrders = filterByPartNumber(filteredBackOrders, partNumber);
        
        return filteredBackOrders;
	}

	public List<BackOrderBO> searchBackOrders(List<AccountBO> billToAccounts, AccountBO shipToAccount, String orderNumber, String partNumber)
	    throws WOMExternalSystemException, WOMValidationException, WOMTransactionException {

	    // let's create a map of the given bill tos for easier access
        Map<String, AccountBO> billToAccountMapByAccountCode = createBillToAccountMap(billToAccounts);
       
	    // Call NABS to get Back Orders.  NABS supports the ship to account directly, so 
	    // send that one.
        List<BackOrderBO> nabsBackOrders = nabsService.getBackOrders(shipToAccount.getAccountCode());
        
        // Go through the NABS Back Orders and set the passed-in account on them since the passed-in
        // Account object has more information than the returned data.
        for (BackOrderBO nabsBackOrder : nabsBackOrders) {
            String billToAccountCodeOnBackOrder = nabsBackOrder.getBillToAccount().getAccountCode();
            AccountBO thisBillTo = billToAccountMapByAccountCode.get(billToAccountCodeOnBackOrder);
            // if this comes back as null somehow, retaining the code is preferable
            if (thisBillTo != null) {
                nabsBackOrder.setBillToAccount(thisBillTo);
            }
        }

        // Call SAP with each bill to that has an associated SAP account.  Going to retain the map of nabs bill to codes to sap bill to codes so we can 
        // link things back up again.
        List<BackOrderBO> sapBackOrders = null;
        List<String> sapBillToAccountCodes = new ArrayList<String>();
        Map<String, AccountBO> billToAccountMapBySapAccountCode = new HashMap<String, AccountBO>();
        
        for (AccountBO aBillToAccount : billToAccountMapByAccountCode.values()) {
            String sapAcctCode = null;
            SapAcctBO sapAccount = aBillToAccount.getSapAccount();
            if (sapAccount != null) {
                sapAcctCode = sapAccount.getSapAccountCode();
            }
            if (GenericValidator.isBlankOrNull(sapAcctCode) == false) {
                billToAccountMapBySapAccountCode.put(sapAcctCode, aBillToAccount);
                sapBillToAccountCodes.add(sapAcctCode);
            }
        }
     
        // also make sure the ship to has a sap account or else this could be an incorrect search    
        // and now call the special service that handles this crazy situation
        String sapShipToAccountCode = null;
        SapAcctBO sapAccount = shipToAccount.getSapAccount();
        if (sapAccount != null) {
            sapShipToAccountCode = sapAccount.getSapAccountCode();
        }

        if (GenericValidator.isBlankOrNull(sapShipToAccountCode) == false) {
            
            sapBackOrders = SAPService.getBackOrders(sapBillToAccountCodes, sapShipToAccountCode);
        
            // Go through the SAP Back Orders and set the passed-in account on them since the passed-in
            // Account object has more information than the returned data.
            for (BackOrderBO sapBackOrder : sapBackOrders) {
                
                String billToAccountCodeOnShippedOrder = sapBackOrder.getBillToAccount().getAccountCode();
                AccountBO thisBillTo = billToAccountMapBySapAccountCode.get(billToAccountCodeOnShippedOrder);
                // if this comes back as null somehow, try massaging the account code
                if (thisBillTo == null) {
                    String fullAccountCode = StringUtil.lpad(billToAccountCodeOnShippedOrder, 10, "0");
                    thisBillTo = billToAccountMapBySapAccountCode.get(fullAccountCode);
                    if (thisBillTo == null) {
                        // can't find it - use the returned data.  This is incomplete but better than nothing.
                        thisBillTo = sapBackOrder.getBillToAccount();
                    }
                } 
                sapBackOrder.setBillToAccount(thisBillTo);
            }
        }
        
        // Merge the Back Orders from NABS and SAP systems and send them back.
        List<BackOrderBO> mergedBackOrders = mergeRetrievedBackOrders(nabsBackOrders, sapBackOrders);

        // Filter the list for passed-in Order Number.
        List<BackOrderBO> filteredBackOrders = filterByOrderNumber(mergedBackOrders, orderNumber);
        
        // Filter the list for passed-in Part Number.
        filteredBackOrders = filterByPartNumber(filteredBackOrders, partNumber);
        
        return filteredBackOrders;
	    
	}
	
	
	/**
	 * Merged Back Orders returned by NABS and SAP systems.
	 * @param nabsBackOrders
	 * @param sapBackOrders
	 * @return <code>List&lt;BackOrderBO&gt; List of merged Back Orders
	 */
    private List<BackOrderBO> mergeRetrievedBackOrders(List<BackOrderBO> nabsBackOrders, List<BackOrderBO> sapBackOrders) {
        
        // The combination of Bill-To Account Code, Master Order Number and Customer Purchase Order Number 
    	// is unique for each Back Order. Make a map of all the Back Orders and this combination.
        Map<String, BackOrderBO> backOrderMap = new HashMap<String, BackOrderBO>();

        String boMapKey = null;
        // add all the NABS orders to the map with its master order number as the key
        for (BackOrderBO nabsBackOrder : nabsBackOrders) {
        	boMapKey = getBackOrderMapKey(nabsBackOrder);
        	backOrderMap.put(boMapKey, nabsBackOrder);
        }
        
        // now go through the SAP list.  If there is a match in the NABS list, merge it. If not, just set the account
        // info and put it in the map
        if (sapBackOrders != null) {
        	boMapKey = null;
        	for (BackOrderBO sapBackOrder : sapBackOrders) {
        		
        		boMapKey = getBackOrderMapKey(sapBackOrder);
        		
        		BackOrderBO nabsBackOrder = backOrderMap.get(boMapKey);            
        		if (nabsBackOrder == null) {
        			// No NABS Back Order found, so just need to put in the map.
        			backOrderMap.put(boMapKey, sapBackOrder);
        		} else {
        			// There is a NABS Back Order for this map key.  Merge the Back Order lists from both systems.  
        			List<BackOrderUnitBO> boUnits =  new ArrayList<BackOrderUnitBO>();
        			
        			boUnits.addAll(nabsBackOrder.getBackOrderUnits());
        			boUnits.addAll(sapBackOrder.getBackOrderUnits());                   
        			nabsBackOrder.setBackOrderUnits(boUnits);
        		}
        	}       
        }

        // All the Back Orders are in the values collection of the map.
        List<BackOrderBO> backOrders = new ArrayList<BackOrderBO>(backOrderMap.values());
        
        return backOrders;
    }
      
    /**
     * Build map key for the passed-in Back Order.
     * @param backOrder
     * @return String
     */
    private String getBackOrderMapKey(BackOrderBO backOrder) {
    	
    	if (backOrder == null) return null;
    	
    	return backOrder.getBillToAccount().getAccountCode() + "|" + backOrder.getMasterOrderNumber() + 
    				"|" + backOrder.getCustomerPurchaseOrderNumber();
    }
    
    private Map<String, AccountBO> createBillToAccountMap(List<AccountBO> billToAccounts) {
        
        Map<String, AccountBO> billToAccountMapByAccountCode = new HashMap<String, AccountBO>();
        if (billToAccounts != null) {
            for (AccountBO billToAccount : billToAccounts) {
                billToAccountMapByAccountCode.put(billToAccount.getAccountCode(), billToAccount);
            }
        }
        
        return billToAccountMapByAccountCode;
    }
}
