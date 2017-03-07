package com.fmo.wom.integration.nabs.action.shipment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityTransaction;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;

import com.fmo.wom.common.exception.WOMTransactionException;
import com.fmo.wom.domain.AccountBO;
import com.fmo.wom.domain.AddressBO;
import com.fmo.wom.domain.CountryBO;
import com.fmo.wom.domain.DistributionCenterBO;
import com.fmo.wom.domain.OrderUnitBO;
import com.fmo.wom.domain.ShippedItemBO;
import com.fmo.wom.domain.ShippedOrderBO;
import com.fmo.wom.domain.ShippingUnitBO;
import com.fmo.wom.domain.enums.ExternalSystem;
import com.fmo.wom.domain.enums.OrderType;
import com.fmo.wom.domain.nabs.inquiry.WebInquiryBO;
import com.fmo.wom.domain.nabs.inquiry.WebInquiryOrderCommentBO;
import com.fmo.wom.domain.nabs.inquiry.WebInquiryOrderDetailBO;
import com.fmo.wom.domain.nabs.inquiry.WebInquiryOrderHeaderBO;
import com.fmo.wom.domain.nabs.inquiry.WebInquiryPK;
import com.fmo.wom.domain.nabs.inquiry.WebInquiryShipmentBO;
import com.fmo.wom.integration.dao.nabs.inquiry.WebInquiryDAO;
import com.fmo.wom.integration.dao.nabs.inquiry.WebInquiryDAOImpl;
import com.fmo.wom.integration.nabs.NabsConstants;
import com.fmo.wom.integration.util.DistributionCenterCache;
import com.fmo.wom.integration.util.MessageResourceUtil;

public class ShippingInfoUtil {
	
	private static Logger logger = Logger.getLogger(ShippingInfoUtil.class);
	
	private WebInquiryDAO webInquiryDAO;
	
	
	public ShippingInfoUtil() {
		super();
		webInquiryDAO = new WebInquiryDAOImpl();
	}

	/**
     * With the given inquiry key, construct as much of the shipping information as possible.
     * This code is reused for dd126 and dd123, with only variants in the amount of data populated
     * being different.
     * @param inquiryKey key to the search data in DD09 schema
     * @return list of shipped order bo object retrieved for the inquiry key
     */
   // @Transactional(propagation=Propagation.REQUIRES_NEW)
    public List<ShippedOrderBO> retrieveShipmentHeaderInfo(String inquiryKey) throws WOMTransactionException{
        
        if (inquiryKey == null) return null;
        
        inquiryKey = inquiryKey.trim();
        
        List<ShippedOrderBO> results = new ArrayList<ShippedOrderBO>();
        WebInquiryDAOImpl daoImpl = (WebInquiryDAOImpl)webInquiryDAO;
        EntityTransaction transaction = null;
        try{
	        transaction = daoImpl.getEntityManager().getTransaction(); 
	        if(transaction != null && (!transaction.isActive())){
	        	transaction.begin();
	        	WebInquiryPK inquiryPK = new WebInquiryPK();
	            inquiryPK.setInquiryKey(inquiryKey);
	            
	            WebInquiryBO inquiry = webInquiryDAO.findById(inquiryPK);
	            if (inquiry == null) {
	                logger.error("No Web Inquiry record found for "+ inquiryKey+ ".  Unable to load order header info");
					if(transaction !=null && transaction.isActive()){
						transaction.commit();
					}
	                return results;
	            }
	            
	            // Have some order headers.  Get the list and step through, creating ShippedOrderBOs where appropriate.
	            // Its possible the search results span multiple master order numbers, so we'll group these together into
	            // the same ShippedOrderBO where we can.
	            
	            // use this map to keep track of our created shipped order BOs.  Key is
	            // master order number
	            Map<String, ShippedOrderBO> masterOrderNumberShippedOrderMap = new HashMap<String, ShippedOrderBO>();
	            
	            List<WebInquiryOrderHeaderBO> orderHeaders = inquiry.getOrderHeaderList();
	            for (WebInquiryOrderHeaderBO anOrderHeader : orderHeaders) {
	                
	                // first get the ShippedOrderBO for this master order number
	                String masterOrderNumber = anOrderHeader.getId().getMasterOrderNumber().trim();
	                ShippedOrderBO aShippedOrder = masterOrderNumberShippedOrderMap.get(masterOrderNumber);
	               
	                if (aShippedOrder == null) {
	                    // if its null, create one and add to the map and our results list
	                    aShippedOrder = createShippedOrder(anOrderHeader);
	                    masterOrderNumberShippedOrderMap.put(masterOrderNumber, aShippedOrder);
	                    results.add(aShippedOrder);
	                }
	                
	                // now we have an old or new ShippedOrderBO.  Create and add the 
	                // OrderUnitBO represented by this WebInquiryOrderHeaderBO entry
	                List<OrderUnitBO> orderUnits = aShippedOrder.getOrderUnitList();
	                OrderUnitBO anOrderUnit = createOrderUnit(anOrderHeader);
	                orderUnits.add(anOrderUnit);
	            }
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
        return results;
    }
    
    /**
     * Encapsulate creation of ShippedOrderBO corresponding to the row in the DD09 Web Inquiry
     * order header table indicated by the given WebInquiryOrderHeaderBO
     * @param anOrderHeader search response data in dd09 schema
     * @return
     */
    private ShippedOrderBO createShippedOrder(WebInquiryOrderHeaderBO anOrderHeader) {

        ShippedOrderBO aShippedOrder = new ShippedOrderBO();
        aShippedOrder.setMasterOrderNumber(anOrderHeader.getId().getMasterOrderNumber().trim());

        String custPONbr = trim(anOrderHeader.getCustomerPONumber());
        logger.debug("createShippedOrder(...): Customer PO # (before removing special characters: "+ custPONbr); 
        // Replace all special characters with empty strings. Else, it will create UI issues!!
        // Note: This problem seems to be specific to NABS!
        if(custPONbr != null){
        	custPONbr= custPONbr.replaceAll("[^a-zA-Z0-9_-]", "");
        }
        logger.debug("createShippedOrder(...): Customer PO # (after removing special characters: "+ custPONbr); 
        aShippedOrder.setCustomerPurchaseOrderNum(custPONbr);
        
        aShippedOrder.setOrderType(OrderType.STOCK);
        if (anOrderHeader.isEmergency()) {
            aShippedOrder.setOrderType(OrderType.EMERGENCY);
        }
        
        aShippedOrder.setOrderedBy(trim(anOrderHeader.getOrderedBy()));
        
        createBillToAccount(aShippedOrder, anOrderHeader);
        createShipToAccount(aShippedOrder, anOrderHeader);
        
        // create an empty order unit list and add it to this object so other
        // code doesn't have to check
        List<OrderUnitBO> orderUnits = new ArrayList<OrderUnitBO>();
        aShippedOrder.setOrderUnitList(orderUnits);
        return aShippedOrder;
    }
    
    /**
     * Create the bill to account and populate with as much info as we can from the
     * returned NABS data
     * @param aShippedOrder
     * @param anOrderHeader
     */
    private void createBillToAccount(ShippedOrderBO aShippedOrder, WebInquiryOrderHeaderBO anOrderHeader) {
        AccountBO billToAccount = new AccountBO();
        billToAccount.setAccountCode(anOrderHeader.getBillToCustomerCode());
     
        aShippedOrder.setBillToAccount(billToAccount);
    }
    
    /**
     * Using the returned data, populate as much ship to account info as we can.  If the code
     * is blank or MAN, this indicates a manual ship to, so construct the address from the 
     * data in the nabs table and populate.
     * @param aShippedOrder
     * @param anOrderHeader
     */
    private void createShipToAccount(ShippedOrderBO aShippedOrder, WebInquiryOrderHeaderBO anOrderHeader) {
        String shipToAccountCode = anOrderHeader.getShipToCustomerCode();
        AccountBO shipToAccount = new AccountBO();
        shipToAccount.setAccountCode(shipToAccountCode);
        
        if (NabsConstants.NABS_MANUAL_SHIP_TO_CODE.equals(shipToAccountCode) ||
            GenericValidator.isBlankOrNull(shipToAccountCode)) {
            
            shipToAccount.setAccountCode("");
        } else {
            shipToAccount.setAccountCode(shipToAccountCode);
        }
        
        shipToAccount.setAccountName(trim(anOrderHeader.getShipToName()));
        // manual ship to, so this table has the address
        AddressBO shipToAddress = new AddressBO();
        shipToAddress.setAddrLine1(trim(anOrderHeader.getShipToAddress1()));
        shipToAddress.setAddrLine2(trim(anOrderHeader.getShipToAddress2()));
        shipToAddress.setCity(trim(anOrderHeader.getShipToCity()));
        CountryBO country = new CountryBO();
        country.setIsoCountryCode(trim(anOrderHeader.getShipToCountryCode()));
        country.setCountryAbbreviation(trim(anOrderHeader.getShipToCountryCode()));
        country.setCountryName(trim(anOrderHeader.getShipToCountryName()));
        shipToAddress.setCountry(country);
        shipToAddress.setStateOrProv(trim(anOrderHeader.getShipToState()));
        shipToAddress.setZipOrPostalCode(trim(anOrderHeader.getShipToPostalCode()));
        shipToAccount.setAddress(shipToAddress);

        aShippedOrder.setShipToAccount(shipToAccount);
    }
    
    public OrderUnitBO createOrderUnit(WebInquiryOrderHeaderBO anOrderHeader) {
        
        OrderUnitBO anOrderUnit = new OrderUnitBO();
        
        anOrderUnit.setExternalSystem(ExternalSystem.NABS);
        anOrderUnit.setOrderNumber(anOrderHeader.getId().getOrderNumber().trim());
        
        // order source is done via lookup in status on the entry method
        String orderSource;
        String entryMethod = "";
        try {
            entryMethod = trim(anOrderHeader.getEntryMethod());
            orderSource = MessageResourceUtil.getNabsStatusKeyViaStatusCode(entryMethod);
        } catch (IllegalArgumentException e) {
            // if we didn't find that on the look up, I'm going to just use the returned key.
            // This should not be a fatal error
            orderSource = entryMethod;
        }
        anOrderUnit.setOrderSourceKey(orderSource);
        anOrderUnit.setOrderSourceDescription(orderSource); //MessageResourceUtil.getIPOMessage(orderSource, null)
        
        anOrderUnit.setOriginalOrderDate(anOrderHeader.getOriginalOrderDate());
        anOrderUnit.setInvoiceDate(anOrderHeader.getInvoiceDate());
        anOrderUnit.setCancelledDate(anOrderHeader.getCancelledDate());
        anOrderUnit.setRequestedDeliveryDate(anOrderHeader.getWantDate());

        // set the comments
        anOrderUnit.setCommentsList(new ArrayList<String>());
        for (WebInquiryOrderCommentBO aComment : anOrderHeader.getOrderCommentList()) {
            anOrderUnit.addComment(trim(aComment.getCommentText()));
        }
        
        List<ShippingUnitBO> shippingUnits = new ArrayList<ShippingUnitBO>();
        
        // go create a shipping unit for this guy
        ShippingUnitBO aShippingUnit = new ShippingUnitBO();                
            
        // in NABS packing slip = order number
        aShippingUnit.setPackingSlip(trim(anOrderUnit.getOrderNumber()));
            
        // package status is done via lookup in status on the order status returned from NABS
        String orderStatus = trim(anOrderHeader.getOrderStatus());
        String packingStatus = "";
        try {
            packingStatus = MessageResourceUtil.getNabsPackageStatusKeyViaCode(orderStatus);
        } catch (IllegalArgumentException e) {
            // if we didn't find that on the look up, I'm going to just use the returned key.
            // This should not be a fatal error
            packingStatus = orderStatus;
        }
        aShippingUnit.setPackingStatus(packingStatus);
        aShippingUnit.setPackingStatusDescription(packingStatus); //MessageResourceUtil.getIPOMessage(packingStatus, null)
            
        aShippingUnit.setShipDate(anOrderHeader.getShipDate());
        aShippingUnit.setShipFrom(createDistributionCenter(anOrderHeader.getShipFromLocationID()));
            
        aShippingUnit.setCarrierCode(trim(anOrderHeader.getCarrierID()));
        aShippingUnit.setCarrierName(trim(anOrderHeader.getCarrierName()));
        aShippingUnit.setShippingMethodCode(trim(anOrderHeader.getShipViaDescription()));
            
        aShippingUnit.setExternalSystmem(ExternalSystem.NABS);
           
        // get the shipment info for this order number.  In theory, there is only one
        for (WebInquiryShipmentBO aShipment : anOrderHeader.getShipmentList()) {
            aShippingUnit.setTrackingNumber(trim(aShipment.getId().getTrackingNumber()));
        } 
                
        // get the detail records for this order number
        List<ShippedItemBO> shippedItemsList = new ArrayList<ShippedItemBO>();
            
        List<WebInquiryOrderDetailBO> orderDetailsForShipment = anOrderHeader.getOrderDetailList();
        for (WebInquiryOrderDetailBO anOrderDetail : orderDetailsForShipment) {
            ShippedItemBO thisShippedItem = createShippedItem(anOrderDetail);
            shippedItemsList.add(thisShippedItem);
            /*if (thisShippedItem.isKit()) {
                // this is a kit part - get the components
                List<ShippedItemBO> shippedKitCompsList = new ArrayList<ShippedItemBO>();
                thisShippedItem.setKitComponents(shippedKitCompsList);
                for (WebInquiryKitComponentBO aKitComponent : anOrderDetail.getKitComponentList()) {
                    shippedKitCompsList.add(createShippedItem(aKitComponent));
                }
            }*/
        }
            
        aShippingUnit.setShippedItemsList(shippedItemsList);
        shippingUnits.add(aShippingUnit);
        
        anOrderUnit.setShippingUnitList(shippingUnits);
        return anOrderUnit;
    }
    
    private ShippedItemBO createShippedItem(WebInquiryOrderDetailBO anOrderDetail) {
        ShippedItemBO shippedItem = new ShippedItemBO();
        shippedItem.setExternalSystem(ExternalSystem.NABS);
        shippedItem.setLineNumber(anOrderDetail.getId().getItemSequenceNumber());
        shippedItem.setPartNumber(trim(anOrderDetail.getPartNumber()));
        shippedItem.setDescription(trim(anOrderDetail.getPartDescription()));
        shippedItem.setProductFlag(anOrderDetail.getProductFlag());
        shippedItem.setBrandState(anOrderDetail.getBrandState());
        shippedItem.setShippedQuantity(anOrderDetail.getShippedQuantity());
        shippedItem.setOrderedQuantity(anOrderDetail.getThisOrderQuantity());
        shippedItem.setBackorderedQuantity(anOrderDetail.getBackOrderedQuantity());
        shippedItem.setAssignedQuantity(anOrderDetail.getAssignedQuantity());
        shippedItem.setKit(NabsConstants.NABS_YES.equals(trim(anOrderDetail.getKitFlag())));
        return shippedItem;
    }
    
    private DistributionCenterBO createDistributionCenter(String code) {
    	
    	DistributionCenterBO distCenter = new DistributionCenterBO();
    	distCenter.setCode(code); 
    	DistributionCenterBO aDcFrmCache= DistributionCenterCache.getDistributionCenterCache().getDistributionCenter(code);
    	if(aDcFrmCache !=null){
    		distCenter.setName(aDcFrmCache.getName());
    	}
    	return distCenter;
        //return DistributionCenterCache.getDistributionCenterWithDefault(Market.US_CANADA, code.trim());
    }
    
    
    /**
     * I hate typing StringUtils each time
     * @param in
     * @return
     */
    private String trim(String in) {
        return StringUtils.trim(in);
    }

	public void releaseResource() {
		if(webInquiryDAO !=null){
			webInquiryDAO.releaseEntityManager();
		}
	}

}
