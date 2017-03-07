package com.fmo.wom.integration.nabs.action.backorder;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityTransaction;

import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;

import com.fmo.wom.common.exception.WOMTransactionException;
import com.fmo.wom.domain.AccountBO;
import com.fmo.wom.domain.BackOrderBO;
import com.fmo.wom.domain.BackOrderUnitBO;
import com.fmo.wom.domain.BackOrderedItemBO;
import com.fmo.wom.domain.DistributionCenterBO;
import com.fmo.wom.domain.enums.ExternalSystem;
import com.fmo.wom.domain.enums.Market;
import com.fmo.wom.domain.nabs.inquiry.WebInquiryBO;
import com.fmo.wom.domain.nabs.inquiry.WebInquiryOrderDetailBO;
import com.fmo.wom.domain.nabs.inquiry.WebInquiryOrderHeaderBO;
import com.fmo.wom.integration.dao.nabs.inquiry.WebInquiryDAO;
import com.fmo.wom.integration.dao.nabs.inquiry.WebInquiryDAOImpl;
import com.fmo.wom.integration.util.DistributionCenterCache;

public class BackOrderUtil {

    private static Logger logger = Logger.getLogger(BackOrderUtil.class);
    
	private WebInquiryDAO webInquiryDAO;
	
	Map<String, BackOrderBO> boMap;
	
	
	public BackOrderUtil() {
		super();
		webInquiryDAO = new WebInquiryDAOImpl();
	}

	/**
	 * Fetch Back Order Headers and corresponding Back Order Details for the
	 * passed-in Inquiry Key, and return a list of Back Orders.
	 * @param inquiryKey
	 * @return <code>List&lt;BackOrderBO&gt;<code>
	 */
   // @Transactional(propagation=Propagation.REQUIRED)
	public List<BackOrderBO> loadResponseData(String inquiryKey) throws WOMTransactionException{
		
    	logger.debug("Inside GetBackOrdersAction.loadResponseData()");
    	
        inquiryKey = inquiryKey.trim();
        
        List<BackOrderBO> backOrders = new ArrayList<BackOrderBO>();

        if (GenericValidator.isBlankOrNull(inquiryKey)) return backOrders;
        
        WebInquiryDAOImpl daoImpl = (WebInquiryDAOImpl)webInquiryDAO;
        EntityTransaction transaction = null;
        try{
	        transaction = daoImpl.getEntityManager().getTransaction(); 
	        if(transaction != null && (!transaction.isActive())){
	        	transaction.begin();
	            WebInquiryBO boInquiry = webInquiryDAO.findByInquiryKey(inquiryKey);
	            if (boInquiry == null) {
	                logger.error("No Back Order records found for Inquiry Key = "+ inquiryKey+ ". Unable to load Back Order info");
	                return backOrders;
	            }
	            // Fetch the Back Order Header info' using the Inquiry Key.
	            List<WebInquiryOrderHeaderBO> boHeaders = boInquiry.getOrderHeaderList();
	            if (boHeaders == null || boHeaders.size() == 0) {
	            	logger.debug("GetBackOrdersAction.loadResponseData(): boHeaders is null or size = 0");
	            	return backOrders;
	            }
	        	logger.debug("GetBackOrdersAction.loadResponseData(): boHeaders.size(): " + boHeaders.size());
	        	boMap = new HashMap<String, BackOrderBO>();
	            for (WebInquiryOrderHeaderBO boHeader : boHeaders) {
	            	BackOrderBO backOrder = getBackOrder(boHeader);
	            	List<BackOrderUnitBO> boUnits = backOrder.getBackOrderUnits();
	            	boUnits.add(createBackOrderUnit(boHeader));
	            }
	            backOrders = new ArrayList<BackOrderBO>(boMap.values());
	        	transaction.commit();
	        }
        } catch (Exception commitException) {
        	if(transaction !=null && transaction.isActive()){
        		try {
        			transaction.rollback();
        		} catch (Exception rollBackException) {
        			throw new WOMTransactionException(rollBackException);
        		}
        	}
        	throw new WOMTransactionException(commitException);
        }
        return backOrders;
	}

    /**
     * Get a Back Order using the passed-in Back Order Header row.
     * Creates a new Back Order if it does not exist in the Back Order Map.
     * @param boHeader
     * @return <code>BackOrderBO<code>
     */
    private BackOrderBO getBackOrder(WebInquiryOrderHeaderBO boHeader) {
    	
    	String boMapKey = getBackOrderMapKey(boHeader);
    	
    	logger.debug("BackOrderUtil.getBackOrder(): boMapKey = "+boMapKey);
    	
    	BackOrderBO backOrder = boMap.get(boMapKey);
    	if (backOrder == null) {
    		backOrder = createBackOrder(boHeader);
    	}
    	
    	return backOrder;
    }

    /**
     * Create a Back Order using the passed-in Back Order Header row.
     * @param boHeader
     * @return <code>BackOrderBO<code>
     */
    private BackOrderBO createBackOrder(WebInquiryOrderHeaderBO boHeader) {
    	
    	String billToAcctCode    = boHeader.getBillToCustomerCode().trim();
    	String masterOrderNumber = boHeader.getId().getMasterOrderNumber().trim();
    	String customerPONumber  = boHeader.getCustomerPONumber().trim();
    	String shipToAcctCode    = boHeader.getShipToCustomerCode().trim();
    	
    	logger.debug("BackOrderUtil.createBackOrder(): " +
    					"billToAcctCode=" + billToAcctCode + ", masterOrderNumber=" +
    					masterOrderNumber + ", customerPONumber=" + customerPONumber);
    	
    	BackOrderBO newBackOrder = new BackOrderBO();
    	
    	String boMapKey = getBackOrderMapKey(boHeader);
		boMap.put(boMapKey, newBackOrder);

		AccountBO billToAcct = new AccountBO();
    	billToAcct.setAccountCode(billToAcctCode);
    	newBackOrder.setBillToAccount(billToAcct);
    	
    	AccountBO shipToAcct = new AccountBO();
    	shipToAcct.setAccountCode(shipToAcctCode);
    	newBackOrder.setShipToAccount(shipToAcct);
    	
    	newBackOrder.setMasterOrderNumber(masterOrderNumber);
    	newBackOrder.setCustomerPurchaseOrderNumber(customerPONumber);
    	
    	List<BackOrderUnitBO> boUnits = new ArrayList<BackOrderUnitBO>();

    	newBackOrder.setBackOrderUnits(boUnits);
    	
    	return newBackOrder;
    }

    /**
     * Create a Back Order Unit using the passed-in Back Order Header row.
     * @param boHeader
     * @return <code>BackOrderUnitBO<code>
     */
    private BackOrderUnitBO createBackOrderUnit(WebInquiryOrderHeaderBO boHeader) {
    	
    	String orderNumber = boHeader.getId().getOrderNumber().trim();
    	String dcCode      = boHeader.getShipFromLocationID().trim();
    	Date   orderDate   = boHeader.getOriginalOrderDate();
    	
    	logger.debug("BackOrderUtil.createBackOrderUnit(): orderNumber=" + orderNumber +
    					"DcCode=" + dcCode);
    	
    	BackOrderUnitBO boUnit = new BackOrderUnitBO();
		
		boUnit.setOrderNumber(orderNumber);
		boUnit.setExternalSystem(ExternalSystem.NABS);
		boUnit.setOrderDate(orderDate);
		boUnit.setDistributionCenter(createDistributionCenter(getMarket(), dcCode, true));
	        		
		List<BackOrderedItemBO> boItems = getBackOrderedItems(boHeader);

		boUnit.setBackOrderedItems(boItems);
		
		return boUnit;
    }

    /**
     * Fetch Back Order Details for the passed-in Back Order Header row, and  
     * create a list of Back Ordered Items.
     * @param boHeader
     * @return <code>List&lt;BackOrderedItemBO&gt;<code>
     */
    private List<BackOrderedItemBO> getBackOrderedItems(WebInquiryOrderHeaderBO boHeader) {
    	
		List<WebInquiryOrderDetailBO> boDetails = boHeader.getOrderDetailList();
		List<BackOrderedItemBO> boItems = new ArrayList<BackOrderedItemBO>();
		
		if (boDetails == null || boDetails.size() == 0) {
			return boItems; 
		}
		
		logger.debug("GetBackOrdersAction.getBackOrderedItems(): boDetails.size(): " + boDetails.size());
			
		for (WebInquiryOrderDetailBO boDetail : boDetails) {
			
			BackOrderedItemBO boItem = createBackOrderedItem(boDetail);
			
			boItems.add(boItem);
		}
			
		return boItems;
    }

    /**
     * Create a Back Ordered Item using the passed-in Back Order Detail row.
     * @param boDetail
     * @return <code>BackOrderedItemBO<code>
     */
    private BackOrderedItemBO createBackOrderedItem(WebInquiryOrderDetailBO boDetail) {
    	
    	String partNumber       = boDetail.getPartNumber().trim();
    	int    originalOrderQty = boDetail.getOriginalOrderQuantity();
    	int    backOrderQty     = boDetail.getBackOrderedQuantity();
    	
    	logger.debug("GetBackOrdersAction.createBackOrderedItem(): partNumber=" + partNumber +
    						", originalOrderQty=" + originalOrderQty + ", backOrderQty=" + backOrderQty);
    	
    	BackOrderedItemBO boItem = new BackOrderedItemBO();
    	
		boItem.setPartNumber(partNumber);
		boItem.setOriginalOrderQty(originalOrderQty);
		boItem.setBackOrderQty(backOrderQty);
		
		return boItem;
    }

    /**
     * Build Back Order map key.
     * @param boHeader
     * @return String
     */
    private String getBackOrderMapKey(WebInquiryOrderHeaderBO boHeader) {
    	
    	return boHeader.getBillToCustomerCode().trim() + "|" + boHeader.getId().getMasterOrderNumber().trim() +
    				"|" + boHeader.getCustomerPONumber().trim();
    }

	private Market getMarket() {
        return Market.US_CANADA;
	}

	private DistributionCenterBO createDistributionCenter(Market market, String dcCode, boolean allowInactive) {
	    
	    DistributionCenterBO distCenter = null; 
	   /* if (allowInactive) {
	        distCenter =  DistributionCenterCache.getDistributionCenterWithDefault(market, dcCode);
	    } else {
	        distCenter =  DistributionCenterCache.getActiveDistributionCenterWithDefault(market, dcCode);
	    }*/
	    distCenter = new DistributionCenterBO();
	    distCenter.setCode(dcCode);

	    DistributionCenterBO aDCFrmCache = DistributionCenterCache.getDistributionCenterCache().getDistributionCenter(dcCode);
	    if(aDCFrmCache != null){
	    	distCenter.setName(aDCFrmCache.getName());
	    }
	    
	    return distCenter;
	}
	
	public void releaseResources(){
		if(webInquiryDAO != null){
			webInquiryDAO.releaseEntityManager();
		}
	}

}
