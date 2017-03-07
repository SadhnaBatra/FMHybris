package com.fmo.wom.integration.nabs.action.invoice;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.fmo.wom.common.util.StringUtil;
import com.fmo.wom.domain.enums.InvoiceStatusFilter;
import com.fmo.wom.domain.enums.InvoiceTypeFilter;
import com.fmo.wom.integration.nabs.NabsConstants;
import com.fmo.wom.integration.nabs.action.NabsStoredProcInput;

public class NabsGetInvoicesInput extends NabsStoredProcInput {

    private static Logger logger = Logger.getLogger(NabsGetInvoicesInput.class);
    
    private static final String PROC_NAME = "COSP0002";
    private static final String PROC_SQL  = PROC_NAME + "(?, ?, ?, ?, ?, ?, ?, ?, ?)";


    private String nabsAccountCode;
    private List<String> invoiceNumbers;
    private String customerPurchaseOrderNumber;
    private Date startInvoiceDate;
    private Date endInvoiceDate;
    private InvoiceTypeFilter invoiceType;
    private InvoiceStatusFilter invoiceStatus;
    private int maxRecordLimit;
    
    public NabsGetInvoicesInput(String nabsAccountCode, List<String> invoiceNumbers, 
								String customerPurchaseOrderNumber,
								Date startInvoiceDate, Date endInvoiceDate, 
								InvoiceTypeFilter invoiceType,
								InvoiceStatusFilter invoiceStatus,
								int maxRecordLimit) {
    	
    	this.nabsAccountCode  = nabsAccountCode;
    	this.invoiceNumbers   = invoiceNumbers;
    	this.customerPurchaseOrderNumber = customerPurchaseOrderNumber;
    	this.startInvoiceDate = startInvoiceDate;
    	this.endInvoiceDate   = endInvoiceDate;
    	this.invoiceType      = invoiceType;
    	this.invoiceStatus    = invoiceStatus;
    	this.maxRecordLimit   = maxRecordLimit;
    }

	@Override
	public CallableStatement createCallableStatement(Connection con)
			throws SQLException {
		
        CallableStatement cs = con.prepareCall(formatProcCall(PROC_SQL));
        
        logger.debug("createCallableStatement(...): IN param #1: nabsAccountCode: '" + nabsAccountCode + "'");
        // *************************
        // Prepare input parameters:
        // *************************
        String invoiceNumbersStr    = buildInvoiceNumbersString(invoiceNumbers);
        if (invoiceNumbers == null || invoiceNumbers.size() == 0) {
        	logger.debug("createCallableStatement(...): IN param #2: invoiceNumbersStr: 100 blank spaces");
        } else {
        	logger.debug("createCallableStatement(...): IN param #2: invoiceNumbersStr: '" + invoiceNumbersStr + "'");
        }
        customerPurchaseOrderNumber = StringUtil.stringPad(customerPurchaseOrderNumber, ' ', 30).toUpperCase();
        if ("".equals(customerPurchaseOrderNumber.trim())) {
        	logger.debug("createCallableStatement(...): IN param #3: customerPurchaseOrderNumber: 30 blank spaces");
        } else {
        	logger.debug("createCallableStatement(...): IN param #3: customerPurchaseOrderNumber: '" + customerPurchaseOrderNumber + "'");
        }
        
		endInvoiceDate              = (endInvoiceDate == null ? new Date() : endInvoiceDate);
    	String endInvoiceDateStr    = NabsConstants.futureDateFormatter.format(endInvoiceDate);
    	
    	initializeStartInvoiceDate();
    	String startInvoiceDateStr  = NabsConstants.futureDateFormatter.format(startInvoiceDate);
        logger.debug("createCallableStatement(...): IN param #4: startInvoiceDateStr: " + startInvoiceDateStr);
        logger.debug("createCallableStatement(...): IN param #5: endInvoiceDateStr: " + endInvoiceDateStr);
    	
		String invoiceTypeCode      = (invoiceType   == null ? InvoiceTypeFilter.ALL.getCode()   : invoiceType.getCode());
        logger.debug("createCallableStatement(...): IN param #6: invoiceTypeCode: " + invoiceTypeCode);
		String invoiceStatusCode    = (invoiceStatus == null ? InvoiceStatusFilter.ALL.getCode() : invoiceStatus.getCode());
        logger.debug("createCallableStatement(...): IN param #7: invoiceStatusCode: " + invoiceStatusCode);
			
        // IN parameters
        cs.setString(1, getNabsAccountCode());
        cs.setString(2, invoiceNumbersStr);
        cs.setString(3, customerPurchaseOrderNumber);
        cs.setString(4, startInvoiceDateStr);
        cs.setString(5, endInvoiceDateStr);
        cs.setString(6, invoiceTypeCode);
        cs.setString(7, invoiceStatusCode);
        
        if (maxRecordLimit > 9999) {
        	maxRecordLimit = 9999;
        }
        cs.setString(8, StringUtil.stringPad(maxRecordLimit, 4));
        logger.debug("createCallableStatement(...): IN param #8: maxRecordLimit: '" + StringUtil.stringPad(maxRecordLimit, 4) + "'");
        
        // OUT parameters
        cs.registerOutParameter(9, Types.VARCHAR); 
        
        return cs;
	}

	@Override
	public String getProcedureName() {
		return PROC_NAME;
	}

	private String buildInvoiceNumbersString(List<String> invoiceNumbers) {
		
        StringBuffer invoiceNumbersStr = new StringBuffer();
        
        // Invoice Numbers
        if (invoiceNumbers != null && invoiceNumbers.size() > 0) {
        	for (String invoiceNum : invoiceNumbers) {
        		if (invoiceNum.length() <= 10) {
        			// Pad each Invoice Number with blank spaces until it is 10 characters long.
        			invoiceNumbersStr.append(StringUtil.stringPad(invoiceNum, ' ', 10));
        		}
        	}
        }

        // Check if length of all Invoice Numbers searched on is less than 100 characters.
        if (invoiceNumbersStr.toString().length() < 100) {
        	// Pad the Invoice Numbers string with blank spaces until it is 100 characters long.
        	StringUtil.stringPad(invoiceNumbersStr.toString(), ' ', 100);
        }
        
        return invoiceNumbersStr.toString();
	}
	
	/**
	 * Set the Start Invoice Date to 7 days prior to End Invoice Date
	 * if Start Invoice Date is null or is later than End Invoice Date.
	 */
	private void initializeStartInvoiceDate() {
		
    	if (startInvoiceDate == null || (startInvoiceDate.after(endInvoiceDate))) {
    		Calendar cal = Calendar.getInstance();
    		cal.setTime(endInvoiceDate);
    		cal.add(Calendar.DATE, -7);
    		startInvoiceDate = cal.getTime();
    	}
    	
	}
	
	public String getNabsAccountCode() {
		return nabsAccountCode;
	}

	public void setNabsAccountCode(String nabsAccountCode) {
		this.nabsAccountCode = nabsAccountCode;
	}

	public List<String> getInvoiceNumbers() {
		return invoiceNumbers;
	}

	public void setInvoiceNumbers(List<String> invoiceNumbers) {
		this.invoiceNumbers = invoiceNumbers;
	}

	public String getCustomerPurchaseOrderNumber() {
		return customerPurchaseOrderNumber;
	}

	public void setCustomerPurchaseOrderNumber(String customerPurchaseOrderNumber) {
		this.customerPurchaseOrderNumber = customerPurchaseOrderNumber;
	}

	public Date getStartInvoiceDate() {
		return startInvoiceDate;
	}

	public void setStartInvoiceDate(Date startInvoiceDate) {
		this.startInvoiceDate = startInvoiceDate;
	}

	public Date getEndInvoiceDate() {
		return endInvoiceDate;
	}

	public void setEndInvoiceDate(Date endInvoiceDate) {
		this.endInvoiceDate = endInvoiceDate;
	}

	public InvoiceTypeFilter getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(InvoiceTypeFilter invoiceType) {
		this.invoiceType = invoiceType;
	}

	public InvoiceStatusFilter getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(InvoiceStatusFilter invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public int getMaxRecordLimit() {
		return maxRecordLimit;
	}

	public void setMaxRecordLimit(int maxRecordLimit) {
		this.maxRecordLimit = maxRecordLimit;
	}

	public static Logger getLogger() {
		return logger;
	}
	
	public static void setLogger(Logger logger) {
		NabsGetInvoicesInput.logger = logger;
	}

	@Override
	public String toString() {
		return "NabsGetInvoicesInput [nabsAccountCode=" + nabsAccountCode
				+ ", invoiceNumbers=" + invoiceNumbers
				+ ", customerPurchaseOrderNumber="
				+ customerPurchaseOrderNumber + ", startInvoiceDate="
				+ startInvoiceDate + ", endInvoiceDate=" + endInvoiceDate
				+ ", invoiceType=" + invoiceType.getCode() + ", invoiceStatus="
				+ invoiceStatus.getCode() + ", maxRecordLimit=" 
				+ maxRecordLimit + "]";
	}

}
