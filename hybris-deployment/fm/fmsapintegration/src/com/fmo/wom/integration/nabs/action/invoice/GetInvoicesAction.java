package com.fmo.wom.integration.nabs.action.invoice;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.common.exception.WOMTransactionException;
import com.fmo.wom.domain.AccountBO;
import com.fmo.wom.domain.CurrencyTypeBO;
import com.fmo.wom.domain.InvoiceBO;
import com.fmo.wom.domain.InvoicedOrderBO;
import com.fmo.wom.domain.PriceBO;
import com.fmo.wom.domain.enums.InvoiceStatus;
import com.fmo.wom.domain.enums.InvoiceStatusFilter;
import com.fmo.wom.domain.enums.InvoiceType;
import com.fmo.wom.domain.enums.InvoiceTypeFilter;
import com.fmo.wom.integration.nabs.NabsConstants;
import com.fmo.wom.integration.nabs.action.NabsStoredProcActionBase;
import com.fmo.wom.integration.nabs.action.NabsStoredProcOutput;

public class GetInvoicesAction extends NabsStoredProcActionBase {

    private static Logger logger = Logger.getLogger(GetInvoicesAction.class);
    
    private static int MAX_RECORD_LIMIT = 800;

    private InvoiceTypeFilter invoiceType;
    
    /**
     * Fetch Invoices for passed-in NABS Account Code and other criteria.
     * @param accountCode	NABS Account Code
     * @param invoiceNumbers	List of zero or upto 10 Invoice Numbers to search for
     * @param customerPurchaseOrderNumber
     * @param startInvoiceDate
     * @param endInvoiceDate
     * @param invoiceType
     * @param invoiceStatus
     * @return <code>List&lt;InvoiceBO&gt;<code>
     * @throws WOMExternalSystemException
     * @throws WOMTransactionException
     */
    public List<InvoiceBO> executeInvoiceSearch(String nabsAccountCode, List<String> invoiceNumbers, 
    											String customerPurchaseOrderNumber,
    											Date startInvoiceDate, Date endInvoiceDate, 
    											InvoiceTypeFilter invoiceType,
    											InvoiceStatusFilter invoiceStatus)
			throws WOMTransactionException, WOMExternalSystemException {
		
		logger.debug("Inside GetInvoicesAction.executeInvoiceSearch()");
		
		// Store Invoice Type to set it back into the fetched Invoices eventually.
		this.invoiceType = invoiceType;
		
		// Create an input object
		NabsGetInvoicesInput input = new NabsGetInvoicesInput(nabsAccountCode, invoiceNumbers, 
																customerPurchaseOrderNumber,
																startInvoiceDate, endInvoiceDate, 
																invoiceType, invoiceStatus,
																MAX_RECORD_LIMIT);
		
		// Make the call to execute the action. 
		NabsGetInvoicesOutput output = (NabsGetInvoicesOutput) executeNabsStoredProcCall(input);
		
		List<InvoiceBO> invoices = (List<InvoiceBO>) output.getResultData();
		if (invoices != null) {
			logger.debug("executeInvoiceSearch(): invoices.size(): " + invoices.size());
		}

		// Check the proc response
		checkReturnCode(output);
		
		return invoices;
	}
	
	@Override
	protected NabsStoredProcOutput createOutput(CallableStatement cs, ResultSet rs) throws SQLException {

		NabsStoredProcOutput output = new NabsGetInvoicesOutput();
		
		output.setResultData(processResultSet(rs));
		output.setReturnCode(getReturnCode(cs));
		
		return output;
	}

	private List<InvoiceBO> processResultSet(ResultSet rs) throws SQLException {

		List<InvoiceBO> results = new ArrayList<InvoiceBO>();
        InvoiceBO invoice = null;
    	
		while (rs.next()) {
			
			invoice = new InvoiceBO();
			invoice.setInvoiceNumber(rs.getString(1).trim());
			Date invDate = null;
			try {
				String invDateStr = rs.getString(2);
				invDate = NabsConstants.futureDateFormatter.parse(invDateStr);
				invoice.setInvoiceDate(invDate);
			} catch (ParseException pe) {
				invDate = null;
			}
				
			AccountBO billToAcct = new AccountBO();
			billToAcct.setAccountCode(rs.getString(4));
			AccountBO shipToAcct = new AccountBO();
			shipToAcct.setAccountCode(rs.getString(5));
			
			invoice.setBillToAccount(billToAcct);
			invoice.setShipToAccount(shipToAcct);
			
			
			//List<InvoicedOrderBO> invoicedOrders = new ArrayList<InvoicedOrderBO>();
			InvoicedOrderBO invoicedOrder = new InvoicedOrderBO();
			invoicedOrder.setCustomerPurchaseOrderNumber(rs.getString(6));
			//invoicedOrders.add(invoicedOrder);
			invoice.setInvoiceOrderBO(invoicedOrder);
			
			PriceBO invoiceAmt = new PriceBO();
			Double invoiceDisplayPrice = Double.parseDouble(rs.getString(7));
			invoiceAmt.setDisplayPrice(invoiceDisplayPrice);
			CurrencyTypeBO currencyType = new CurrencyTypeBO();
			currencyType.setCode(CurrencyTypeBO.US_CDN_SYMBOL);
			invoiceAmt.setCurrency(currencyType);
			invoice.setInvoiceAmount(invoiceAmt);
			
			invoice.setInvoiceStatus(InvoiceStatus.getFromCode(rs.getString(8)));
			invoice.setApplicationCode(rs.getString(9));		
			invoice.setInvoiceType(invoiceDisplayPrice >= 0 ? InvoiceType.INVOICE : InvoiceType.CREDITMEMO);
			
			results.add(invoice);
		}
		
		logger.debug("processResultSet(): results.size(): " + results.size());
		return results;
	}


	private String getReturnCode(CallableStatement cs) throws SQLException {
		
		// Get the NABS return code stored in the OUT parameter.
		String returnCode = cs.getString(9).trim();
		logger.debug("getReturnCode(): returnCode = "+ returnCode);
		
		return returnCode;
	}
    
	private void checkReturnCode(NabsGetInvoicesOutput output) throws WOMExternalSystemException {
	
		logger.debug("checkReturnCode(): output return code="+ output.getReturnCode());
		String logMessage = "";

		if (!output.isReturnStatusSuccessful() && !output.isNoResultsFound()) {

			if (output.isNoBillToCodeSpecified()) {
				logMessage = "No Bill-To Account Code specified.";
			} else if (output.isInvalidInvoiceNumber()) {
				logMessage = "Invalid Invoice Number entered. One of the Invoice Numbers is not numeric.";
			} else if (output.isInvalidStartDate()) {
				logMessage = "Invalid Start Date";
			} else if (output.isInvalidEndDate()) {
				logMessage = "Invalid End Date";
			} else if (output.isInvalidType()) { // Invoice/Credit Memo
				logMessage = "Invalid Type";
			} else if (output.isInvalidStatus()) {
				logMessage = "Invalid Status";
			} else if (output.isInvalidRecordLimit()) {
				logMessage = "Invalid record limit";
			} else if (output.isCustomerNotFound()) {
				logMessage = "Customer not found";
			} else if (output.isCustomerNotAuthorized()) {
				logMessage = "Customer not authorized to view Invoices.";
			} else if (output.isInvoiceNotFound()) {
				logMessage = "One or more Invoice(s) specified not found. ";
			} else if (output.isRecordLimitExceeded()) {
				if (output.getResultData().size() == 0) {
					logMessage = "Invoice search result exceeds " + MAX_RECORD_LIMIT + "-record limit. Please narrow your search.";
				}
			} else {
				logMessage = "NABS call failure. Return Code = " + output.getReturnCode() + ".";
			}

			if (!"".equals(logMessage)) {
				getLogger().error(logMessage);
				throw new WOMExternalSystemException(logMessage);
			}
		}
	}
	
	public Logger getLogger() {
		return logger;
	}
	
	public static void setLogger(Logger logger) {
		GetInvoicesAction.logger = logger;
	}

	public InvoiceTypeFilter getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(InvoiceTypeFilter invoiceType) {
		this.invoiceType = invoiceType;
	}

}
