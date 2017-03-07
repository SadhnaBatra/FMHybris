package com.fmo.wom.integration.sap.action;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.common.util.DateUtil;
import com.fmo.wom.domain.CustomerSalesOrgBO;
import com.fmo.wom.domain.ItemBO;
import com.fmo.wom.domain.OrderBO;
import com.fmo.wom.domain.TradingPartnerBO;
import com.fmo.wom.domain.enums.OrderSource;
import com.fmo.wom.domain.enums.OrderType;
import com.fmo.wom.integration.sap.SapConnectionManager;
import com.fmo.wom.integration.sap.SapConnectionManager.SAPDestinationType;
import com.fmo.wom.integration.sap.SapConstants;
import com.fmo.wom.integration.util.ItemListUtil;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoParameterList;

public class ProcessOrderAction extends com.fmo.wom.integration.sap.common.ProcessOrderAction  {

    private static Logger logger = Logger.getLogger(ProcessOrderAction.class);
    
    private static String SAP_ACCOUNT_CODE_FOR_NEXPART = "0010013594";
    private static String PO_TYPE_KEY_FOR_NEXPART = "NXPT";
    private static String NAME_OF_ORDERER_FOR_NEXPART = "IPO-NXPT";

    protected ProcessOrderAction() { super(); }

    public ProcessOrderAction(String sapAccountCode, String shipToAccountCode, CustomerSalesOrgBO salesOrg, OrderBO anOrder) {
        super(sapAccountCode, shipToAccountCode, salesOrg, anOrder);
    }
    
    public ProcessOrderAction(String sapAccountCode, String shipToAccountCode, 
                             CustomerSalesOrgBO salesOrg, OrderBO anOrder, SapConnectionManager aCxnMgr) {
        super(sapAccountCode, shipToAccountCode, salesOrg, anOrder, aCxnMgr);
    }

    @Override
    protected Logger getLogger() { return logger; }
    
    @Override
    public String getFunctionName() {
        return SapConstants.PROCESS_ORDER_FUNC_NAME;
    }

    @Override
    protected JCoDestination getDestination() throws JCoException, WOMExternalSystemException {
        return sapCxnMgr.getJCoDestination(SAPDestinationType.EVEREST);
    }
    
    @Override
    protected List<ItemBO> getItemListForSystem(List<ItemBO> inputItemList) {
    	logger.debug("@@ Inside getItemListForSystem : ProcessOrderAction @@ ");
        return ItemListUtil.getSapItemList(inputItemList);
    }
    
    @Override
    public void initializeImportParams(JCoParameterList importParams) {
        super.initializeImportParams(importParams);
        OrderBO order = getOrder();
        OrderSource orderSource = order.getOrderSource();
        TradingPartnerBO tradingPartner = order.getTradingPartner();
        boolean enableShippingFromTsc = false;
        if (tradingPartner != null) {
        	enableShippingFromTsc = tradingPartner.isEnableShippingFromTsc();
        }
        String sapSoldToAccountCode = getSapAccountCode();

        boolean shipFromPartyExistsFlag = false;
        List<ItemBO> orderItems = order.getItemList();
        if (orderItems != null && orderItems.size() > 0 && orderItems.get(0).getRequestedInventory() != null) {
        	shipFromPartyExistsFlag = true;
        	logger.info("initializeImportParams(...): shipFromPartyExistsFlag: " + shipFromPartyExistsFlag);
       }
        if (order != null && shipFromPartyExistsFlag && enableShippingFromTsc && OrderSource.IPO == orderSource && SAP_ACCOUNT_CODE_FOR_NEXPART.equals(sapSoldToAccountCode)) {
        	// For NexPart alone, set the POType to "NXPT".
 //        	logger.info("initializeImportParams(...): PO Type: " + PO_TYPE_KEY_FOR_NEXPART);
 //       	importParams.setValue(SapConstants.PO_TYPE_KEY, PO_TYPE_KEY_FOR_NEXPART);
         	importParams.setValue(SapConstants.ORDERER_NAME_KEY, NAME_OF_ORDERER_FOR_NEXPART);
        }
    	importParams.setValue(SapConstants.PO_TYPE_KEY, getOrder().getOrderSource().getPoType());
    	logger.info("initializeImportParams(...): PO Type: " + getOrder().getOrderSource().getPoType());
    }
   
    @Override
    protected Date getRequestedDeliveryDate() {

    	/* For TSC Pickup Order */
    	if(OrderType.PICKUP == getOrder().getOrderType()){
    		return null;
    	}
    	
    	// if the future date is set, use that
        if(getOrder().getFutureDate() != null){
            return getOrder().getFutureDate();
        } 
        // if this is not emergency and there's no future date, we set today +1 business day
        else if (getOrder().getOrderType() != OrderType.EMERGENCY) {
            Date orderDate = new Date();
            return DateUtil.getNextBusinessDay(orderDate, 1);
        } else {
            return null;
        }
    }
}
