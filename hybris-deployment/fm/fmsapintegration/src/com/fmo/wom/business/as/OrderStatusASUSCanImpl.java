package com.fmo.wom.business.as;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;

import com.fmo.wom.business.as.config.USCanAS;
import com.fmo.wom.common.exception.WOMBusinessAccessException;
import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.common.exception.WOMResourceException;
import com.fmo.wom.common.exception.WOMTransactionException;
import com.fmo.wom.common.exception.WOMValidationException;
import com.fmo.wom.common.util.StringUtil;
import com.fmo.wom.domain.AccountBO;
import com.fmo.wom.domain.CarrierBO;
import com.fmo.wom.domain.OrderUnitBO;
import com.fmo.wom.domain.SapAcctBO;
import com.fmo.wom.domain.ShippedOrderBO;
import com.fmo.wom.domain.ShipperBO;
import com.fmo.wom.domain.ShippingUnitBO;
import com.fmo.wom.domain.enums.ExternalSystem;
import com.fmo.wom.domain.enums.Market;
import com.fmo.wom.domain.enums.OrderSearchFilter;
import com.fmo.wom.integration.NabsService;
import com.fmo.wom.integration.SAPService;
import com.fmo.wom.integration.util.CarrierCache;

public class OrderStatusASUSCanImpl extends OrderStatusASBase implements OrderStatusASUSCan, USCanAS {

	private static Logger logger = Logger.getLogger(OrderStatusASUSCanImpl.class);
	
	@Override
    public Logger getLogger() {
        return logger;
    }
	
	private NabsService nabsService;
	
	public OrderStatusASUSCanImpl() {
		super();
		nabsService = WOMServices.getNabsService();
	}
	
	/*
     * (non-Javadoc)
     * @see com.fmo.wom.business.as.OrderStatusAS#searchOrders(java.util.List, com.fmo.wom.domain.AccountBO, java.lang.String, java.lang.String, java.util.Date, java.util.Date, com.fmo.wom.domain.enums.OrderSearchFilter)
     *
     * Default implementation is going to have to be multiple searches for each bill to with ship to filtering maybe?  That can be
     * for later and for now, we'll just throw an exception.  This indicates this operation is not supported for this market.
     */
    public List<ShippedOrderBO> searchOrders(List<AccountBO> billToAccounts, AccountBO shipToAccount, String masterOrderNumber, 
                                            String customerPurchaseOrderNumber, Date startDate, Date endDate, 
                                            OrderSearchFilter filter, String orderReturnFlag) 
                    throws WOMResourceException {
        
        logOrderSearchCriteria(shipToAccount, masterOrderNumber, customerPurchaseOrderNumber, startDate, endDate, filter);
        
        // let's create a map of the given bill tos for easier access
        Map<String, AccountBO> billToAccountMapByAccountCode = createBillToAccountMap(billToAccounts);
        
        // Call NABS to get shipment info - NABS supports the ship to account code as the account code and will return all the data
        // for that ship to, so we can send it in.
        List<ShippedOrderBO> nabShippedOrderList = nabsService.getShipmentInfo(shipToAccount.getAccountCode(), masterOrderNumber, customerPurchaseOrderNumber, startDate, endDate, filter);
        
        // go through the nabs orders and make sure we set the given account on them.  
        for (ShippedOrderBO nabsShippedOrder : nabShippedOrderList) {
            
            String billToAccountCodeOnShippedOrder = nabsShippedOrder.getBillToAccount().getAccountCode();
            AccountBO thisBillTo = billToAccountMapByAccountCode.get(billToAccountCodeOnShippedOrder);
            // if this comes back as null somehow, retaining the code is preferable
            if (thisBillTo != null) {
                nabsShippedOrder.setBillToAccount(thisBillTo);
            }
        }
        
        // Call SAP with each bill to that has an associated SAP account.  Going to retain the map of nabs bill to codes to sap bill to codes so we can 
        // link things back up again.
        List<ShippedOrderBO> sapShippedOrderList = null;
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
        
        // also make sure the ship to has a sap account or else this could be a big search    
        // and now call the special service that handles this crazy situation
        String sapShipToAccountCode = null;
        SapAcctBO sapAccount = shipToAccount.getSapAccount();
        if (sapAccount != null) {
            sapShipToAccountCode = sapAccount.getSapAccountCode();
        }

        if (GenericValidator.isBlankOrNull(sapShipToAccountCode) == false) {
            sapShippedOrderList= SAPService.getShipmentInfo(sapBillToAccountCodes, sapShipToAccountCode, masterOrderNumber, customerPurchaseOrderNumber, startDate, endDate, filter, orderReturnFlag);
        
            // go through the sap orders and do the same account matching on them as the returned data will not be complete in
            // this regard.
            for (ShippedOrderBO sapShippedOrder :  sapShippedOrderList) {
                String billToAccountCodeOnShippedOrder = sapShippedOrder.getBillToAccount().getAccountCode();
                AccountBO thisBillTo = billToAccountMapBySapAccountCode.get(billToAccountCodeOnShippedOrder);
                // if this comes back as null somehow, try massaging the account code
                if (thisBillTo == null) {
                    String fullAccountCode = StringUtil.lpad(billToAccountCodeOnShippedOrder, 10, "0");
                    thisBillTo = billToAccountMapBySapAccountCode.get(fullAccountCode);
                    if (thisBillTo == null) {
                        // can't find it - use the returned data.  This is incomplete but better than nothing.
                        thisBillTo = sapShippedOrder.getBillToAccount();
                    }
                } 
                sapShippedOrder.setBillToAccount(thisBillTo);
            }
        }
        
        // merge the orders from the two systems and send it back.
        return mergeRetrievedOrders(nabShippedOrderList, sapShippedOrderList);
        
    }
    
    
    /* (non-Javadoc)
	 * @see com.fmo.wom.business.as.OrderStatusAS#searchOrders(com.fmo.wom.domain.AccountBO, java.lang.String, java.lang.String, java.util.Date, java.util.Date, com.fmo.wom.domain.enums.OrderSearchFilter)
	 */
    @Override
	public List<ShippedOrderBO> searchOrders(AccountBO account, String masterOrderNumber, 
                                             String customerPurchaseOrderNumber, Date startDate, Date endDate, 
                                             OrderSearchFilter filter, String orderReturnFlag) 
                   throws WOMResourceException {

	    logOrderSearchCriteria(account, masterOrderNumber, customerPurchaseOrderNumber, startDate, endDate, filter);
        //AccountBO billToAccount = account;
        AccountBO billToAccount = null;
        try {
            billToAccount = (AccountBO)BeanUtils.cloneBean(account);
        } catch (Exception e) {
            throw new WOMResourceException("Unable to make copy of input account", e);
        }
        
	    // validate account input since we're not calling anything to resolve it?
	    
	    // Call NABS to get shipment info
        List<ShippedOrderBO> nabShippedOrderList = nabsService.getShipmentInfo(billToAccount.getAccountCode(), masterOrderNumber, customerPurchaseOrderNumber, startDate, endDate, filter);
        
        // go through the nabs orders and make sure we set the given account on them.  It seems the return data sometimes c
        for (ShippedOrderBO nabShippedOrder : nabShippedOrderList) {
            if (billToAccount.getAccountCode().equals(nabShippedOrder.getBillToAccount().getAccountCode())) {
                // copy back the bill-to info into the DD112 validated object
                nabShippedOrder.setBillToAccount(billToAccount);
            }
        }
        
        // Call SAP if this account has an associated SAP account
        List<ShippedOrderBO> sapShippedOrderList = null;
        String sapAcctCode = null;
        SapAcctBO sapAccount = billToAccount.getSapAccount();
        if (sapAccount != null) {
        	sapAcctCode = sapAccount.getSapAccountCode();
        }
        if (!GenericValidator.isBlankOrNull(sapAcctCode)) {
            sapShippedOrderList= SAPService.getShipmentInfo(sapAcctCode, masterOrderNumber, customerPurchaseOrderNumber, startDate, endDate, filter, orderReturnFlag);
        
            // go through the sap orders and put the account on them as the returned data will not be complete in
            // this regard.
            for (ShippedOrderBO sapShippedOrder :  sapShippedOrderList) {
                sapShippedOrder.setBillToAccount(billToAccount);
            }
        }
        
        // merge the orders from the two systems and send it back.
        return mergeRetrievedOrders(nabShippedOrderList, sapShippedOrderList);
	}
    
     @Override
	 public void getOrderDetail(ShippedOrderBO shippedOrder, String orderRetrunFlag) 
        throws WOMExternalSystemException, WOMTransactionException {
   
	     if (shippedOrder == null) {
	         return;
	     }
	     logger.info("OrderStatusASCanImpl: getOrderDetail() Order details for masterOrderNumber= "+shippedOrder.getMasterOrderNumber()+" "+shippedOrder);
	     getShipmentDetail(shippedOrder,ExternalSystem.NABS, orderRetrunFlag);
	     logger.info("OrderStatusASCanImpl: getOrderDetail() NABS Order details for masterOrderNumber= "+shippedOrder.getMasterOrderNumber()+" "+shippedOrder);
	     getShipmentDetail(shippedOrder,ExternalSystem.EVRST,orderRetrunFlag);
	     logger.info("OrderStatusASCanImpl: getOrderDetail() EVRST Order details for masterOrderNumber= "+shippedOrder.getMasterOrderNumber()+" "+shippedOrder);
	 }
	
    /**
     * Call getShipmentDetail for the given shipped order to the given system.
     * 
     * @param shippedOrder
     * @param externalSystem
     * @throws WOMTransactionException
     * @throws WOMExternalSystemException
     */
    protected void getShipmentDetail(ShippedOrderBO shippedOrder, ExternalSystem externalSystem, String orderRetrunFlag)
            throws WOMTransactionException, WOMExternalSystemException {
        
        if (externalSystem == ExternalSystem.NABS) {
            nabsService.getShipmentDetail(shippedOrder);
        } else if (externalSystem == ExternalSystem.EVRST) {
            SAPService.getShipmentDetail(shippedOrder,orderRetrunFlag);
        }
        
        getShipperInfo(shippedOrder,externalSystem);
        
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
    
    /**
     * Create a  way to uniquely identify an order.  We used to use master order number,
     * but not all orders get a master order number for some reason.  Now using a combo of
     * the customer po number and master order number.  Because NABS sends all customer po
     * numbers as one case despite input case differences, these are all going to appear
     * as the same customer po number. Who know what the case will be in master order
     * numbers so upper-ing those too.
     * @param sapShippedOrder
     * @return
     */
    private String getOrderKey(ShippedOrderBO aShippedOrder) {
        String customerPONumber = aShippedOrder.getCustomerPurchaseOrderNum() != null ? aShippedOrder.getCustomerPurchaseOrderNum().trim().toUpperCase() : "";
        String masterOrderNumber = aShippedOrder.getMasterOrderNumber() != null ? aShippedOrder.getMasterOrderNumber().trim().toUpperCase() : ""; 
        return customerPONumber + masterOrderNumber;
    }
    
    
    private List<ShippedOrderBO> mergeRetrievedOrders(List<ShippedOrderBO> nabShippedOrderList, List<ShippedOrderBO> sapShippedOrderList) {
        
        // ShippedOrderBO is identical with ShippingUnit in BOD, one ShippedOrder/ShippingUnit only have one master order number
        // Make a map of all the orders and their master order numbers.
        Map<String, ShippedOrderBO> shippedOrderMap = new HashMap<String, ShippedOrderBO>();

        // add all the NABS orders to the map with its master order number + po number as the key
        for (ShippedOrderBO nabShippedOrder : nabShippedOrderList) {      	        	
            //Retrieve shipper info from Oracle Shipper table
        	getShipperInfo(nabShippedOrder,ExternalSystem.NABS);
        	       	
            shippedOrderMap.put(getOrderKey(nabShippedOrder), nabShippedOrder);
        }
        
        // now go through the SAP list.  If there is a match in the NABS list, merge it. If not, just set the account
        // info and put it in the map
        if (sapShippedOrderList != null) {
        	for (ShippedOrderBO sapShippedOrder : sapShippedOrderList) {
        		//Retrieve shipper info from Oracle Shipper table
            	getShipperInfo(sapShippedOrder,ExternalSystem.EVRST);
        		            
        		ShippedOrderBO nabsShippedOrder = shippedOrderMap.get(getOrderKey(sapShippedOrder));
            
        		if (nabsShippedOrder == null) {
        			// no NABS shipped order, so just need to put in the map
        			shippedOrderMap.put(getOrderKey(sapShippedOrder), sapShippedOrder);
        		} else {
        			// there is a NABS order side for this master order number.  Merge the order lists from both orders  
        			List<OrderUnitBO> orderUnitList =  new ArrayList<OrderUnitBO>();
        			orderUnitList.addAll(nabsShippedOrder.getOrderUnitList());
        			orderUnitList.addAll(sapShippedOrder.getOrderUnitList());                   
        			nabsShippedOrder.setOrderUnitList(orderUnitList);
        			
        			// Nabs returning the customer po number with all upper case 
        	        // We should use the one returning from SAP which contains the original customer po number
        			nabsShippedOrder.setCustomerPurchaseOrderNum(sapShippedOrder.getCustomerPurchaseOrderNum());
        		}
        	}       
        }
        // all our orders are in the values collection of the map
        List<ShippedOrderBO> shippedOrders = new ArrayList<ShippedOrderBO>(shippedOrderMap.values());
        return shippedOrders;
    }
    
    private void getShipperInfo(ShippedOrderBO shippedOrder,ExternalSystem externalSystem){
    	
        //Retrieve shipper info from Oracle Shipper table
        for(OrderUnitBO orderUnit:shippedOrder.getOrderUnitList()){
        	for(ShippingUnitBO shippingUnit:orderUnit.getShippingUnitList()){
        		if(externalSystem == shippingUnit.getExternalSystmem()){
        			String carrierName = shippingUnit.getCarrierName();
        	        if(carrierName == null || carrierName.trim().isEmpty()){
        	        	String carrierCode = shippingUnit.getCarrierCode();
        	        	if(carrierCode != null && (!carrierCode.trim().isEmpty())){
        	        		CarrierBO aCrrierFrmcache = CarrierCache.getCarrierCache().getcarrier(carrierCode.trim());
            	        	if(aCrrierFrmcache != null){
            	        		carrierName = aCrrierFrmcache.getCarrierName();
            	        	}else {
            	        		carrierName = carrierCode.trim();
							} 
        	        	} else {
        	        		ShipperBO shipper = getShipper(shippingUnit.getCarrierCode(),externalSystem);
        		        	if(null == shippingUnit.getCarrierCode()){
        		        		shippingUnit.setCarrierCode("");
        		        	}
        		        	shippingUnit.setCarrierName(shipper.getShipperName());
        		        	shippingUnit.setTrackingURL(shipper.getShipperUrl());
        				}	
        	        }
        		}
        	}
        }
    }
    
    private ShipperBO getShipper(String systemShipperCode,ExternalSystem externalSystem) {
		ShipperBO shipper = null;
	    if (GenericValidator.isBlankOrNull(systemShipperCode)) {
	        return createShipperNotFound();
	    }
	    
	    //ShipperDetailBO shipperDtl = null;
	    shipper = createShipperNotFound();
	   /* try {
	        //shipperDtl = shipperDtlDAO.findByExtSysCodeAndShipperCode(externalSystem, systemShipperCode);
	        //shipper = shipperDtl.getShipper();
	    	
	    } catch (WOMPersistanceException pe) {
	        shipper = createShipperNotFound();
	    }*/
	    
	    return shipper;
	}
	
	private ShipperBO createShipperNotFound() {
		ShipperBO shipper = new ShipperBO();
        shipper.setShipperName("CARRIER NOT FOUND");
        shipper.setShipperUrl("");
        return shipper;
	}
    

	@Override
	public Market getMarket() {
        return Market.US_CANADA;
	}


	@Override
	public List<ShippedOrderBO> getOrderAndShipmentStatus(String accountCode,
			String masterOrderNumber, String custPoNumber)
			throws WOMBusinessAccessException, WOMValidationException,
			WOMResourceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ShippedOrderBO> getOrderAndShipmentStatusByDC(
			String accountCode, String masterOrderNumber, String custPoNumber,
			String distCenterCode) throws WOMBusinessAccessException,
			WOMValidationException, WOMResourceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ShippedOrderBO> getOrderStatusInfo(String accountCode,
			String masterOrderNumber, String custPoNumber)
			throws WOMBusinessAccessException, WOMValidationException,
			WOMResourceException {
		// TODO Auto-generated method stub
		return null;
	}

}
