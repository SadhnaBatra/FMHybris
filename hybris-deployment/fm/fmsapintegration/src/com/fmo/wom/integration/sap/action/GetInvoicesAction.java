package com.fmo.wom.integration.sap.action;




import org.apache.log4j.Logger;

import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.domain.InvoiceSearchCriteriaBO;
import com.fmo.wom.domain.enums.Market;
import com.fmo.wom.integration.sap.SapConnectionManager.SAPDestinationType;
import com.fmo.wom.integration.sap.SapConstants;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;


public class GetInvoicesAction extends com.fmo.wom.integration.sap.common.GetInvoicesAction {

	private static Logger logger = Logger.getLogger(GetInvoicesAction.class);
	
	protected GetInvoicesAction() { super(); }
	
	public GetInvoicesAction(InvoiceSearchCriteriaBO invoiceSearchCriteria){
		super(invoiceSearchCriteria);
	}
	
	@Override
	protected Logger getLogger() { return logger; }
	
	@Override
	public String getFunctionName() {
		return SapConstants.GET_INVOICES_FUNC_NAME;
	}

	@Override
    protected JCoDestination getDestination() throws JCoException, WOMExternalSystemException {
        return sapCxnMgr.getJCoDestination(SAPDestinationType.EVEREST);
    }
    
    protected Market getMarket() {
        return Market.US_CANADA;
    }
	
}
