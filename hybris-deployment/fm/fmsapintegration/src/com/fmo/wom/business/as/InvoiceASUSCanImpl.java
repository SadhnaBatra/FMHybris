package com.fmo.wom.business.as;

import java.util.List;

import org.apache.log4j.Logger;

import com.fmo.wom.business.as.config.USCanAS;
import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.common.exception.WOMTransactionException;
import com.fmo.wom.common.exception.WOMValidationException;
import com.fmo.wom.domain.InvoiceBO;
import com.fmo.wom.domain.InvoiceDetailsBO;
import com.fmo.wom.domain.InvoiceSearchCriteriaBO;
import com.fmo.wom.domain.enums.Market;
import com.fmo.wom.integration.NabsService;
import com.fmo.wom.integration.SAPService;

public class InvoiceASUSCanImpl extends InvoiceASBase implements InvoiceAS, USCanAS {

	private static Logger logger = Logger.getLogger(InvoiceASUSCanImpl.class);
	
	private NabsService nabsService;
	
	
	public InvoiceASUSCanImpl() {
		super();
		nabsService   = WOMServices.getNabsService();
	}

	@Override
	public Market getMarket() {
		return Market.US_CANADA;
	}
	
	@Override
	public List<InvoiceBO> searchInvoices(InvoiceSearchCriteriaBO searchCriteria)
			throws WOMExternalSystemException, WOMTransactionException {

	    // Call NABS to get Invoices and/or Credit Memos.
        List<InvoiceBO> nabsInvoices = nabsService.getInvoices(searchCriteria.getBillToAcct().getAccountCode(), 
        														searchCriteria.getInvoiceNumbers(),
        														searchCriteria.getCustPONumber(),
        														searchCriteria.getStartInvoiceDate(), 
        														searchCriteria.getEndInvoiceDate(),
        														searchCriteria.getInvoiceType(), 
        														searchCriteria.getInvoiceStatus());
        
        // Go through the NABS Invoices and set the passed-in account on them since the passed-in
        // Account object has more information than the returned data.
        for (InvoiceBO nabsInvoice : nabsInvoices) {
        	if (searchCriteria.getBillToAcct().getAccountCode().equals(nabsInvoice.getBillToAccount().getAccountCode())) {
        		nabsInvoice.setBillToAccount(searchCriteria.getBillToAcct());
        	}
        }

		return nabsInvoices;
	}

	/*@Override
	public List<InvoiceBO> searchInvoices(InvoiceSearchCriteriaBO invoiceSearchCriteria) 
												throws WOMExternalSystemException, WOMValidationException, WOMTransactionException {

		return SAPService.searchInvoices(invoiceSearchCriteria);
	}*/
	
	public InvoiceDetailsBO getInvoicedetails(String invoiceNumber) throws WOMExternalSystemException, WOMValidationException, WOMTransactionException { 
		return SAPService.getInvoiceDetails(invoiceNumber);
	}

	@Override
	public Logger getLogger() {
		return logger;
	}

}
