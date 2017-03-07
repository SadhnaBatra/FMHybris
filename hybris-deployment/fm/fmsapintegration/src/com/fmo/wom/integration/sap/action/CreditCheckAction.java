package com.fmo.wom.integration.sap.action;




import org.apache.log4j.Logger;

import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.domain.SalesOrganizationBO;
import com.fmo.wom.domain.enums.Market;
import com.fmo.wom.integration.sap.SapConnectionManager.SAPDestinationType;
import com.fmo.wom.integration.sap.SapConstants;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;

public class CreditCheckAction extends com.fmo.wom.integration.sap.common.CreditCheckAction {
	
	private static Logger logger = Logger.getLogger(CreditCheckAction.class);
	
	protected CreditCheckAction() { super(); }
	
	public CreditCheckAction(String customerNumber, SalesOrganizationBO salesOrganization){
		super(customerNumber, salesOrganization);
	}
	
	@Override
	protected Logger getLogger() { return logger; }
	
	@Override
	public String getFunctionName() {
		return SapConstants.CREDIT_CHECK_FUNC_NAME;
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
