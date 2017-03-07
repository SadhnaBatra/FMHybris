package com.fmo.wom.domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.validator.GenericValidator;

import com.fmo.wom.domain.enums.InvoiceStatusFilter;
import com.fmo.wom.domain.enums.InvoiceTypeFilter;

public class InvoiceSearchCriteriaBO {

	//INVOICES
	private String delimitedInvoiceNumbers;
	
	//CUST_NO, SALES_ORG, DISTR_CHANNEL, DIVISION
	private AccountBO billToAcct;
	
	//PO_NUM
	private String custPONumber;
	
	//FROM_DATE
	private Date startInvoiceDate;
	
	//TO_DATE
	private Date endInvoiceDate;
	
	//SALES_ORDER
	private String salesOrder;
	
	//DOC_CAT
	private String documentCategory;
	
	private InvoiceTypeFilter invoiceType;
	
	private InvoiceStatusFilter invoiceStatus;
	
	
	public InvoiceSearchCriteriaBO() {
		startInvoiceDate = getDefaultStartInvoiceDate();
		endInvoiceDate   = getDefaultEndInvoiceDate();
		invoiceType = InvoiceTypeFilter.ALL;
		invoiceStatus = InvoiceStatusFilter.ALL;
	}
	
	/**
	 * Get the default Start Invoice Date (US-Can market) or
	 * Invoice Date (eConnect market). The former defaults to 7 days prior
	 * to current system date, while the latter defaults to null.
	 * @return Start Invoice Date / Invoice Date as appropriate
	 */
	private Date getDefaultStartInvoiceDate() {
		Date defaultStartInvoiceDate = null;
		// Set Start Invoice Date to 7 days prior to End Invoice Date.
		Calendar cal = Calendar.getInstance();
		cal.setTime(getDefaultEndInvoiceDate());
		cal.add(Calendar.DATE, -7);
		defaultStartInvoiceDate = cal.getTime();
		return defaultStartInvoiceDate;
	}
	
	/**
	 * Get the default End Invoice Date. Defaults to current system date
	 * for US-Can market. Does NOT apply to eConnect market, hence defaults to null
	 * for that market.
	 * @return End Invoice Date
	 */
	private Date getDefaultEndInvoiceDate() {
		
		Date defaultEndInvoiceDate = new Date();
		return defaultEndInvoiceDate;
	}
	
	public String getDelimitedInvoiceNumbers() {
		return delimitedInvoiceNumbers;
	}


	public void setDelimitedInvoiceNumbers(String delimitedInvoiceNumbers) {
		this.delimitedInvoiceNumbers = delimitedInvoiceNumbers;
	}


	public AccountBO getBillToAcct() {
		return billToAcct;
	}


	public void setBillToAcct(AccountBO billToAcct) {
		this.billToAcct = billToAcct;
	}


	public String getCustPONumber() {
		return custPONumber;
	}


	public void setCustPONumber(String custPONumber) {
		this.custPONumber = custPONumber;
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


	public String getSalesOrder() {
		return salesOrder;
	}


	public void setSalesOrder(String salesOrder) {
		this.salesOrder = salesOrder;
	}


	public String getDocumentCategory() {
		return documentCategory;
	}


	public void setDocumentCategory(String documentCategory) {
		this.documentCategory = documentCategory;
	}

	/**
	 * Build Invoice Numbers list from delimited Invoice Numbers string.
	 * @return <code>List&lt;String&gt;<code>
	 */
	public List<String> getInvoiceNumbers() {
		
		List<String> invoiceNumbers = new ArrayList<String>();
		
		if (!GenericValidator.isBlankOrNull(delimitedInvoiceNumbers)) {

			StringTokenizer st = new StringTokenizer(delimitedInvoiceNumbers, ",");
			 
			while (st.hasMoreElements()) {
				invoiceNumbers.add( ((String) st.nextElement()).trim());
			}
		}

		return invoiceNumbers;
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

	@Override
	public String toString() {
		return "InvoiceSearchCriteriaBO [delimitedInvoiceNumbers="
				+ delimitedInvoiceNumbers + ", billToAcct=" + billToAcct
				+ ", custPONumber=" + custPONumber + ", startInvoiceDate="
				+ startInvoiceDate + ", endInvoiceDate=" + endInvoiceDate
				+ ", salesOrder=" + salesOrder + ", documentCategory="
				+ documentCategory + ", invoiceType=" + invoiceType
				+ ", invoiceStatus=" + invoiceStatus + "]";
	}

}
