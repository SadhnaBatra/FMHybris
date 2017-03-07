package com.fmo.wom.business.as;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;

import com.fmo.wom.business.orderstatus.OrderHistoryUtil;
import com.fmo.wom.domain.AccountBO;
import com.fmo.wom.domain.CarrierBO;
import com.fmo.wom.domain.OrderSearchCriteria;
import com.fmo.wom.domain.ShippedOrder;
import com.fmo.wom.domain.ShippedOrderDetail;
import com.fmo.wom.domain.ShippingUnitBO;
import com.fmo.wom.domain.enums.ExternalSystem;
import com.fmo.wom.integration.NabsService;
import com.fmo.wom.integration.SAPService;
import com.fmo.wom.integration.util.CarrierCache;


public class OrderSearchASUSCanImpl  {
	
	private static Logger logger = Logger.getLogger(OrderSearchASUSCanImpl.class);
	
	private NabsService nabsService = null;
	
	public OrderSearchASUSCanImpl() {
		nabsService = new NabsService();
	}
	
	public List<ShippedOrder> searchOrder(OrderSearchCriteria orderSearchCriteria) throws Exception{

		if((orderSearchCriteria.getShipToAccount() == null && orderSearchCriteria.isShipToLogin()) ||
				(orderSearchCriteria.getBillToAccount() == null) && (! orderSearchCriteria.isShipToLogin())){
			throw new Exception("Unable search orders without BillTo/ShipTo Account details ...");
		}
		
		logger.info("START :: Searching Orders For "+orderSearchCriteria);
		
		List<ShippedOrder> nabsOrdersList = nabsService.searchNabsOrders(orderSearchCriteria);
		
		List<ShippedOrder> eccOrdersList = null;
        
        if (!GenericValidator.isBlankOrNull(orderSearchCriteria.getECCAccountCode())){
        	eccOrdersList = SAPService.searchECCOrders(orderSearchCriteria);
        }
        
        
		List<ShippedOrder> ordersList = new ArrayList<ShippedOrder>();
		if(nabsOrdersList != null && (! nabsOrdersList.isEmpty())){
			ordersList.addAll(nabsOrdersList);
		}
		if(eccOrdersList != null && (! eccOrdersList.isEmpty())){
			ordersList.addAll(eccOrdersList);
		}
		logger.info(ordersList.size()+" Orders Found ");
		logger.info("END :: Searching Orders For "+orderSearchCriteria);
		
		//Sort The Orders by Date 
		OrderHistoryUtil orderHistoryUtil = new OrderHistoryUtil();
		return orderHistoryUtil.performOrderDateSort(ordersList);
	}

	public ShippedOrderDetail getOrderDetail(OrderSearchCriteria orderSearchCriteria) throws Exception{
		
		if((orderSearchCriteria.getShipToAccount() == null && orderSearchCriteria.isShipToLogin()) ||
				(orderSearchCriteria.getBillToAccount() == null) && (! orderSearchCriteria.isShipToLogin())){
			throw new Exception("Unable search order details without BillTo/ShipTo Account details ...");
		}
		
		if(orderSearchCriteria.getExternalSystem() == null){
			throw new Exception("Unable search order details without External system detail ...");
		}
		
		logger.info("START :: Order Detail For "+orderSearchCriteria);
		ShippedOrderDetail orderDetail = null;
		
		if(ExternalSystem.NABS == orderSearchCriteria.getExternalSystem()){
			String nabsAccountcode = orderSearchCriteria.getNabsAccountCode();
			String masterOrderNumber = orderSearchCriteria.getConfirmationOrOrderNumber();
			String nabsOrderNumber = orderSearchCriteria.getOrderNumber();
			orderDetail = nabsService.getNABSOrderDetail(nabsAccountcode, masterOrderNumber, nabsOrderNumber);
		} else if(ExternalSystem.EVRST == orderSearchCriteria.getExternalSystem()){
			orderDetail = SAPService.getECCOrderDetail(orderSearchCriteria);
		} else{
			logger.error("Invalid External System ..");
		}
		populateShipperInfo(orderDetail);
		logger.info("END :: Order Detail For "+orderSearchCriteria);
		
		return orderDetail;
	}

	private void populateShipperInfo(ShippedOrderDetail orderDetail) {
		for(ShippingUnitBO shippingUnit: orderDetail.getShippingUnitBOList()){
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
		        	if(null == shippingUnit.getCarrierCode()){
		        		shippingUnit.setCarrierCode("");
		        	}
		        	shippingUnit.setCarrierName("CARRIER NOT FOUND");
		        	shippingUnit.setTrackingURL("");
				}	
	        }
    	}
	}
}
