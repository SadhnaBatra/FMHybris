package com.fmo.wom.integration.sap.action;

import java.util.List;

import org.apache.log4j.Logger;

import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.domain.enums.Market;
import com.fmo.wom.integration.sap.SapConnectionManager.SAPDestinationType;
import com.fmo.wom.integration.sap.SapConstants;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;

public class GetBackOrdersAction extends com.fmo.wom.integration.sap.common.GetBackOrdersAction {

    private static Logger logger = Logger.getLogger(GetBackOrdersAction.class);
    
    public GetBackOrdersAction(String sapAccountCode) {
    	super(sapAccountCode);
    }

    public GetBackOrdersAction(List<String> soldToAccountList, String shiptoAccountCode) {
        super(soldToAccountList, shiptoAccountCode);
    }
    
	@Override
	protected Market getMarket() {
        return Market.US_CANADA;
	}

	@Override
	public String getFunctionName() {
        return SapConstants.GET_BACKORDERS_FUNC_NAME;
	}

	@Override
	protected JCoDestination getDestination() throws JCoException, WOMExternalSystemException {
        return sapCxnMgr.getJCoDestination(SAPDestinationType.EVEREST);
	}

	@Override
	public String getShipToCustomerKey() {
		return SapConstants.SHIP_TO_CUSTOMER_KEY;
	}

	@Override
	protected Logger getLogger() {
		return logger;
	}

}
