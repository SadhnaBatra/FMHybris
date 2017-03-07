package com.fmo.wom.integration.sap.action;




import org.apache.log4j.Logger;

import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.domain.enums.Market;
import com.fmo.wom.integration.sap.SapConnectionManager.SAPDestinationType;
import com.fmo.wom.integration.sap.SapConstants;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;


public class GetInvoiceDetailsAction extends com.fmo.wom.integration.sap.common.GetInvoiceDetailsAction {

	private static Logger logger = Logger.getLogger(GetInvoiceDetailsAction.class);
	
	public GetInvoiceDetailsAction() {
		super();
	}

	public GetInvoiceDetailsAction(String invoiceNumber) {
		super(invoiceNumber);
	}
	
	@Override
	public String getFunctionName() {
		return SapConstants.GET_INVOICE_DETAILS_FUNC_NAME;
	}

	@Override
	protected JCoDestination getDestination() throws JCoException,
			WOMExternalSystemException {
		return sapCxnMgr.getJCoDestination(SAPDestinationType.EVEREST);
	}

	@Override
	protected Logger getLogger() { return logger; }
	
	
	protected Market getMarket() {
        return Market.US_CANADA;
    }

}
