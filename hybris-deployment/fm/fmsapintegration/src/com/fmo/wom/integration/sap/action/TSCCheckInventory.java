package com.fmo.wom.integration.sap.action;

import java.util.List;

import org.apache.log4j.Logger;

import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.domain.CustomerSalesOrgBO;
import com.fmo.wom.domain.ItemBO;
import com.fmo.wom.domain.enums.Market;
import com.fmo.wom.integration.sap.SapConnectionManager;
import com.fmo.wom.integration.sap.SapConstants;
import com.fmo.wom.integration.sap.SapConnectionManager.SAPDestinationType;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;

public class TSCCheckInventory extends com.fmo.wom.integration.sap.common.TSCCheckInventory{
	
	private static Logger logger = Logger.getLogger(TSCCheckInventory.class);
	
	protected TSCCheckInventory() { super(); }

	public TSCCheckInventory(String sapAccountCode, String shipToAccountCode, CustomerSalesOrgBO salesOrg, List<ItemBO> itemList) {
		super(sapAccountCode, shipToAccountCode, salesOrg, itemList);
	}
	
	public TSCCheckInventory(String sapAccountCode, String shipToAccountCode, 
	        CustomerSalesOrgBO salesOrg, List<ItemBO> itemList, SapConnectionManager aCxnMgr) {
        super(sapAccountCode, shipToAccountCode, salesOrg, itemList, aCxnMgr);
    }

	
	public TSCCheckInventory(String sapBillToAcctCode, String sapShipToAcctCode, 
								CustomerSalesOrgBO salesOrg, List<ItemBO> itemList, 
								String orderType, String orderSource) {

		super(sapBillToAcctCode,sapShipToAcctCode,salesOrg, itemList,orderType,orderSource);
	}
	
	@Override
	public String getFunctionName() {
		return SapConstants.TSC_PICKUP_CHECK_INVENTORY_FUNC_NAME;
	}
    
    @Override
    protected JCoDestination getDestination() throws JCoException, WOMExternalSystemException {
        return sapCxnMgr.getJCoDestination(SAPDestinationType.EVEREST);
    }
    
    @Override
    protected Market getMarket() {
        return Market.US_CANADA;
    }

    @Override
	protected Logger getLogger() { return logger; }
    
}
