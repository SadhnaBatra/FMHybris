package com.fmo.wom.integration.nabs.action.invoice;

import org.apache.log4j.Logger;

import com.fmo.wom.integration.nabs.action.NabsStoredProcOutput;

public class NabsGetInvoicesOutput extends NabsStoredProcOutput {

    private static Logger logger = Logger.getLogger(NabsGetInvoicesOutput.class);
    
	private static String NABS_SUCCESS = "000";
	private static String NABS_NO_DATA_FOUND = "000";
	
	private static String NABS_NO_BILLTO_CODE = "001";
	private static String NABS_INVALID_INVOICE_NUMBER = "002";
	private static String NABS_INVALID_START_DATE = "003";
	private static String NABS_INVALID_END_DATE = "004";
	private static String NABS_INVALID_TYPE = "005";
	private static String NABS_INVALID_STATUS = "006";
	private static String NABS_INVALID_RECORD_LIMIT = "007";

	private static String NABS_CUSTOMER_NOT_FOUND = "100";
	private static String NABS_CUSTOMER_NOT_AUTHORIZED = "101";
	private static String NABS_INVOICE_NOT_FOUND = "200"; 
	private static String NABS_RECORD_LIMIT_EXCEEDED = "201";
	
    public NabsGetInvoicesOutput() {}
    
	/**
	 * Documentation on Return codes from stored procedure:
	 * ----------------------------------------------------
	 * If an error occurs, the return message will contain a unique code followed
	 * by a description of the error. If the code is 999, then a system error has
	 * occurred and the text will indicate the SQLCode and a terse description of
	 * where the error occurred. A more detailed error will be displayed in the
	 * stored procedure address space. The application should not abend if the
	 * error can be handled by the application.
	 * 
	 * Non-System related errors are:
	 * 
	 *       001   "No BillTo Code"
	 *       002   "Invalid Invoice Number Entered"  An invoice number was not numeric.
	 *       003   "Invalid Start Date"
	 *       004   "Invalid End Date"
	 *       005   "Invalid Type"
	 *       006   "Invalid Status"
	 *       007   "Invalid Return Limit"
	 *       100   "Customer Not Found"
	 *       101   "Customer Not Authorized" The Info View Indicator for this customer is 0 or blank.
	 *       200   "Invoice Not Found" 
	 *             This will be followed by a list of 10 indicators, one for each invoice entered.
	 *                      Y  Invoice found
	 *                      N  Invoice not found
	 *                      X  No invoice listed at this position
	 *       201   Too many invoices matched
	 * 
	 * If the retrieval was successful, the return message will be 000. This will
	 * apply even if no results set is returned unless specific invoices have been
	 * entered.
	 */
    public boolean isReturnStatusSuccessful() {
        return NABS_SUCCESS.equals(getReturnCode());
    }

    public boolean isNoResultsFound() {
        return NABS_NO_DATA_FOUND.equals(getReturnCode());
    }
    
    public boolean isNoBillToCodeSpecified() {
    	return getReturnCode().contains(NABS_NO_BILLTO_CODE);
    }

    public boolean isInvalidInvoiceNumber() {
    	return getReturnCode().contains(NABS_INVALID_INVOICE_NUMBER);
    }

    public boolean isInvalidStartDate() {
    	return getReturnCode().contains(NABS_INVALID_START_DATE);
    }

    public boolean isInvalidEndDate() {
    	return getReturnCode().contains(NABS_INVALID_END_DATE);
    }

    public boolean isInvalidType() {
    	return getReturnCode().contains(NABS_INVALID_TYPE);
    }

    public boolean isInvalidStatus() {
    	return getReturnCode().contains(NABS_INVALID_STATUS);
    }

    public boolean isInvalidRecordLimit() {
    	return getReturnCode().contains(NABS_INVALID_RECORD_LIMIT);
    }

    public boolean isCustomerNotFound() {
    	return getReturnCode().contains(NABS_CUSTOMER_NOT_FOUND);
    }

    public boolean isCustomerNotAuthorized() {
    	return getReturnCode().contains(NABS_CUSTOMER_NOT_AUTHORIZED);
    }

    public boolean isInvoiceNotFound() {
    	return getReturnCode().contains(NABS_INVOICE_NOT_FOUND);
    }
    
    public boolean isRecordLimitExceeded() {
    	return getReturnCode().contains(NABS_RECORD_LIMIT_EXCEEDED);
    }
}
