package com.fmo.wom.integration.sap.action;

import java.util.List;

import org.apache.log4j.Logger;

import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.domain.CustomerSalesOrgBO;
import com.fmo.wom.domain.ItemBO;
import com.fmo.wom.domain.enums.Market;
import com.fmo.wom.integration.sap.SapConnectionManager;
import com.fmo.wom.integration.sap.SapConnectionManager.SAPDestinationType;
import com.fmo.wom.integration.sap.SapConstants;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;

public class CheckInventoryAction extends com.fmo.wom.integration.sap.common.CheckInventoryAction {

	private static Logger logger = Logger.getLogger(CheckInventoryAction.class);
	
	protected CheckInventoryAction() { super(); }

	public CheckInventoryAction(String sapAccountCode, String shipToAccountCode, CustomerSalesOrgBO salesOrg, List<ItemBO> itemList) {
		super(sapAccountCode, shipToAccountCode, salesOrg, itemList);
	}
	
	public CheckInventoryAction(String sapAccountCode, String shipToAccountCode, 
	        CustomerSalesOrgBO salesOrg, List<ItemBO> itemList, SapConnectionManager aCxnMgr) {
        super(sapAccountCode, shipToAccountCode, salesOrg, itemList, aCxnMgr);
    }

	
	public CheckInventoryAction(String sapBillToAcctCode, String sapShipToAcctCode, 
								CustomerSalesOrgBO salesOrg, List<ItemBO> itemList, 
								String orderType, String orderSource /*, 
								String payementMethod*/) {

		super(sapBillToAcctCode,sapShipToAcctCode,salesOrg, itemList,orderType,orderSource/*,payementMethod*/);
	}

	@Override
	protected Logger getLogger() { return logger; }
	
	@Override
	public String getFunctionName() {
		return SapConstants.CHECK_INVENTORY_FUNC_NAME;
	}
    
    @Override
    protected JCoDestination getDestination() throws JCoException, WOMExternalSystemException {
        return sapCxnMgr.getJCoDestination(SAPDestinationType.EVEREST);
    }
    
    @Override
    protected Market getMarket() {
        return Market.US_CANADA;
    }
}
