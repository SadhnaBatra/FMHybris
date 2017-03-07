package com.fmo.wom.integration.nabs.action.shipment;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;

import com.fmo.wom.domain.AccountBO;
import com.fmo.wom.domain.AddressBO;
import com.fmo.wom.domain.CountryBO;
import com.fmo.wom.domain.DistributionCenterBO;
import com.fmo.wom.domain.ShippedItemBO;
import com.fmo.wom.domain.ShippedOrder;
import com.fmo.wom.domain.ShippedOrderDetail;
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

public class NABSOrderSearchUtil {

	private static Logger logger = Logger.getLogger(NABSOrderSearchUtil.class);
	
	private WebInquiryDAO webInquiryDAO;
	
	
	public NABSOrderSearchUtil() {
		super();
		webInquiryDAO = new WebInquiryDAOImpl();
	}


	public List<ShippedOrder> retrieveOrders(String inquiryKey) {
		
		List<ShippedOrder> nabsOrdersList = null;
		
		if(inquiryKey != null && (!inquiryKey.isEmpty())){
			
			nabsOrdersList = new ArrayList<ShippedOrder>();
			WebInquiryPK inquiryPK = new WebInquiryPK();
	        inquiryPK.setInquiryKey(inquiryKey.trim());
	        
	        WebInquiryBO inquiry = webInquiryDAO.findById(inquiryPK);
	        
	        if (inquiry == null) {
	            logger.error("No Web Inquiry record found for "+ inquiryKey+ ".  Unable to load NABS Orders info");
	            return nabsOrdersList;
	        }
	        List<WebInquiryOrderHeaderBO> orderHeadersList = inquiry.getOrderHeaderList();
	        
	        for(WebInquiryOrderHeaderBO anOrderHeader : orderHeadersList){
	        	ShippedOrder aShippedOrder = new ShippedOrder();
	        	poplulateOrderHeader(anOrderHeader, aShippedOrder);
	        	nabsOrdersList.add(aShippedOrder);
	        }
		}
		return nabsOrdersList;
	}
	
	private void poplulateOrderHeader(WebInquiryOrderHeaderBO anOrderHeader, ShippedOrder aShippedOrder){

		aShippedOrder.setExternalSystem(ExternalSystem.NABS);
    	
    	aShippedOrder.setOrderType(OrderType.STOCK);
        if (anOrderHeader.isEmergency()) {
        	aShippedOrder.setOrderType(OrderType.EMERGENCY);
        }
        aShippedOrder.setPoNumber(trim(anOrderHeader.getCustomerPONumber()));
        aShippedOrder.setMasterOrderNumber(anOrderHeader.getId().getMasterOrderNumber().trim());
        aShippedOrder.setOrderNumber(anOrderHeader.getId().getOrderNumber().trim());
        
        // in NABS packing slip = order number
        aShippedOrder.setPackingSlipNumber(anOrderHeader.getId().getOrderNumber().trim());
        aShippedOrder.setShippingDC(createDistributionCenter(anOrderHeader.getShipFromLocationID()));
        aShippedOrder.setShipDate(anOrderHeader.getShipDate());
        
        // get the shipment info for this order number.  In theory, there is only one
        for (WebInquiryShipmentBO aShipment : anOrderHeader.getShipmentList()) {
        	aShippedOrder.setTrackingNumber(trim(aShipment.getId().getTrackingNumber()));
        }
        
        aShippedOrder.setOrderDate(anOrderHeader.getOriginalOrderDate());
        
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
        aShippedOrder.setOrderStatus(packingStatus);
        
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
        aShippedOrder.setOrderSource(orderSource);
        aShippedOrder.setCarrierCode(trim(anOrderHeader.getCarrierID()));
        aShippedOrder.setCarrierName(trim(anOrderHeader.getCarrierName()));
	}
	
	private DistributionCenterBO createDistributionCenter(String code) {
    	
    	DistributionCenterBO distCenter = new DistributionCenterBO();
    	distCenter.setCode(code); 
    	DistributionCenterBO aDcFrmCache= DistributionCenterCache.getDistributionCenterCache().getDistributionCenter(code);
    	if(aDcFrmCache !=null){
    		distCenter.setName(aDcFrmCache.getName());
    	}
    	return distCenter;
    }
	
	public ShippedOrderDetail retrieveOrderDetail(String inquiryKey ){
		
		ShippedOrderDetail orderDetail = null;
		if(inquiryKey != null && (!inquiryKey.isEmpty())){
			
			WebInquiryPK inquiryPK = new WebInquiryPK();
	        inquiryPK.setInquiryKey(inquiryKey.trim());
	        
	        WebInquiryBO inquiry = webInquiryDAO.findById(inquiryPK);
	        
	        if (inquiry == null) {
	            logger.error("No Web Inquiry record found for "+ inquiryKey+ ". Unable to load NABS Orders info");
	            return null;
	        }

	        //NABS should return only one Header Object
	        List<WebInquiryOrderHeaderBO> orderHeadersList = inquiry.getOrderHeaderList();
	        WebInquiryOrderHeaderBO orderHeader = orderHeadersList.get(0);
	        orderDetail = new ShippedOrderDetail();
	        poplulateOrderHeader(orderHeader, orderDetail);
	        
	        orderDetail.setOrderedBy(trim(orderHeader.getOrderedBy()));
	        createBillToAccount(orderDetail, orderHeader);
	        createShipToAccount(orderDetail, orderHeader);
	        
	        orderDetail.setOrderDate(orderHeader.getOriginalOrderDate());
	        orderDetail.setInvoiceDate(orderHeader.getInvoiceDate());
	        orderDetail.setCancelledDate(orderHeader.getCancelledDate());
	        orderDetail.setRequestedDeliveryDate(orderHeader.getWantDate());
	        
	        // set the comments
	        orderDetail.setCommentsList(new ArrayList<String>());
	        for (WebInquiryOrderCommentBO aComment : orderHeader.getOrderCommentList()) {
	            orderDetail.addComment(trim(aComment.getCommentText()));
	        }
	        
	        List<ShippingUnitBO> shippingUnits = new ArrayList<ShippingUnitBO>();
	        
	        ShippingUnitBO aShippingUnit = new ShippingUnitBO();                
            
	        // in NABS packing slip = order number
	        aShippingUnit.setPackingSlip(orderHeader.getId().getOrderNumber().trim());
	            
	        // package status is done via lookup in status on the order status returned from NABS
	        String orderStatus = trim(orderHeader.getOrderStatus());
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
	            
	        aShippingUnit.setShipDate(orderHeader.getShipDate());
	        aShippingUnit.setShipFrom(createDistributionCenter(orderHeader.getShipFromLocationID()));
	            
	        aShippingUnit.setCarrierCode(trim(orderHeader.getCarrierID()));
	        aShippingUnit.setCarrierName(trim(orderHeader.getCarrierName()));
	        aShippingUnit.setShippingMethodCode(trim(orderHeader.getShipViaDescription()));
	            
	        aShippingUnit.setExternalSystmem(ExternalSystem.NABS);
	           
	        // get the shipment info for this order number.  In theory, there is only one
	        for (WebInquiryShipmentBO aShipment : orderHeader.getShipmentList()) {
	            aShippingUnit.setTrackingNumber(trim(aShipment.getId().getTrackingNumber()));
	        } 
	                
	        // get the detail records for this order number
	        List<ShippedItemBO> shippedItemsList = new ArrayList<ShippedItemBO>();
	            
	        List<WebInquiryOrderDetailBO> orderDetailsForShipment = orderHeader.getOrderDetailList();
	        for (WebInquiryOrderDetailBO anOrderDetail : orderDetailsForShipment) {
	            ShippedItemBO thisShippedItem = createShippedItem(anOrderDetail);
	            shippedItemsList.add(thisShippedItem);
	        }
	            
	        aShippingUnit.setShippedItemsList(shippedItemsList);
	        shippingUnits.add(aShippingUnit);
	        
	        orderDetail.setShippingUnitBOList(shippingUnits);
	        
		}
		return orderDetail;
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
	
	
    private void createBillToAccount(ShippedOrderDetail aShippedOrder, WebInquiryOrderHeaderBO anOrderHeader) {
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
    private void createShipToAccount(ShippedOrderDetail aShippedOrder, WebInquiryOrderHeaderBO anOrderHeader) {

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

    private String trim(String in) {
        return StringUtils.trim(in);
    }
    
	public void releaseResource() {
		if(webInquiryDAO !=null){
			webInquiryDAO.releaseEntityManager();
		}
	}
}
