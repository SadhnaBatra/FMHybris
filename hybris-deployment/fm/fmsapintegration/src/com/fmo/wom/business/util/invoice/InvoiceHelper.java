package com.fmo.wom.business.util.invoice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;

import com.fmo.wom.business.util.backorder.BackOrderHelper;
import com.fmo.wom.domain.InvoiceBO;
import com.fmo.wom.domain.InvoicedOrderBO;

public class InvoiceHelper {

	private static Logger logger = Logger.getLogger(BackOrderHelper.class); 
	
	private List<InvoiceSearchValues> invoiceSearchValuesList = null;
	
	private List<InvoiceBO> invoicesList = null;
	
	
	public InvoiceHelper(List<InvoiceBO> invoicesList) {
		super();
		this.invoicesList = invoicesList;
		this.invoiceSearchValuesList = buildInvoiceSearchValuesList(this.invoicesList);
	}
	
	public List<InvoiceSearchValues> getInvoiceSearchValuesList() {
		return invoiceSearchValuesList;
	}

	public void performInvoiceTypeSort() {
		logger.debug("Inside 'performInvoiceTypeSort()'");
		Collections.sort(this.invoiceSearchValuesList, Collections.reverseOrder(new Comparator<InvoiceSearchValues>() {
			
			public int compare(InvoiceSearchValues obj1, InvoiceSearchValues obj2) {
				int result = obj2.getInvoiceType().getCode().compareToIgnoreCase(obj1.getInvoiceType().getCode());
				if (result == 0) { // Invoice Types are equal
					// Compare Invoice Dates.
					result = obj2.getInvoiceDate().compareTo(obj1.getInvoiceDate());
					if (result == 0) { // Invoice Dates are equal
						result = obj2.getInvoiceNumber().compareToIgnoreCase(obj1.getInvoiceNumber());
					}
				}
				return result;
			}
		}));
	}

	
	public void performInvoiceDateSort() {
		logger.debug("Inside 'performInvoiceDateSort()'");
		Collections.sort(this.invoiceSearchValuesList, Collections.reverseOrder(new Comparator<InvoiceSearchValues>() {
			
			public int compare(InvoiceSearchValues obj1, InvoiceSearchValues obj2) {
				int result = obj2.getInvoiceDate().compareTo(obj1.getInvoiceDate());
				if (result == 0) { // Invoice Dates are equal
					// Compare Invoice Types.
					result = obj2.getInvoiceType().getCode().compareToIgnoreCase(obj1.getInvoiceType().getCode());
					if (result == 0) { // Invoice Types are equal
						result = obj2.getInvoiceNumber().compareToIgnoreCase(obj1.getInvoiceNumber());
					}
				}
				return result;
			}
		}));
	}
	

	public void performInvoiceNumberSort() {
		logger.debug("Inside 'performInvoiceNumberSort()'");
		Collections.sort(this.invoiceSearchValuesList, Collections.reverseOrder(new Comparator<InvoiceSearchValues>() {
			
			public int compare(InvoiceSearchValues obj1, InvoiceSearchValues obj2) {
				int result = obj2.getInvoiceNumber().compareToIgnoreCase(obj1.getInvoiceNumber());
				return result;
			}
		}));
	}
	
	
	public void performPurchaseOrderNumberSort() {
		logger.debug("Inside 'performPurchaseOrderNumberSort()'");
		Collections.sort(this.invoiceSearchValuesList, Collections.reverseOrder(new Comparator<InvoiceSearchValues>() {
			
			public int compare(InvoiceSearchValues obj1, InvoiceSearchValues obj2) {
				int result = obj2.getCustomerPurchaseOrderNumber().compareTo(obj1.getCustomerPurchaseOrderNumber());
				if (result == 0) { // P.O. #s are equal
					// Compare Invoice Dates.
					result = obj2.getInvoiceDate().compareTo(obj1.getInvoiceDate());
					if (result == 0) { // Invoice Dates are equal
						result = obj2.getInvoiceNumber().compareToIgnoreCase(obj1.getInvoiceNumber());
					}
				}
				return result;
			}
		}));
	}	
	
	/**
	 * Build the Invoice list for display on the screen.
	 * @param invoices
	 * @return <code>List&lt;InvoiceSearchValues&gt;<code>
	 */
	private List<InvoiceSearchValues> buildInvoiceSearchValuesList(List<InvoiceBO> invoices) {

		logger.debug("Inside 'buildInvoiceSearchValuesList()'");
		
		List<InvoiceSearchValues> invoiceSearchValuesList = new ArrayList<InvoiceSearchValues>();
		
		if (invoices == null || invoices.size() == 0) {
			if (invoices == null) {
				logger.debug("buildInvoiceSearchValuesList(): invoices is null");
			} else {
				logger.debug("buildInvoiceSearchValuesList(): invoices.size() = 0");
			}
			return invoiceSearchValuesList;
		}
		
		logger.debug("buildInvoiceSearchValuesList(): invoices.size(): "+invoices.size());

		for (InvoiceBO invoice : invoices) {
			
			InvoiceSearchValues invoiceSearchValues = new InvoiceSearchValues();
			
			invoiceSearchValues.setBillToAccount(invoice.getBillToAccount());
			invoiceSearchValues.setShipToAccount(invoice.getShipToAccount());
			invoiceSearchValues.setInvoiceNumber(invoice.getInvoiceNumber());
			invoiceSearchValues.setInvoiceDate(invoice.getInvoiceDate());
			invoiceSearchValues.setInvoiceAmount(invoice.getInvoiceAmount());
			
			InvoicedOrderBO invoicedOrderBO = invoice.getInvoiceOrderBO();
			 
			invoiceSearchValues.setOrderNumber(invoicedOrderBO.getOrderNumber());
			invoiceSearchValues.setCustomerPurchaseOrderNumber(invoicedOrderBO.getCustomerPurchaseOrderNumber());
			
			invoiceSearchValues.setApplicationCode(invoice.getApplicationCode());
			invoiceSearchValues.setInvoiceStatus(invoice.getInvoiceStatus());
			invoiceSearchValues.setInvoiceType(invoice.getInvoiceType());
			
			invoiceSearchValuesList.add(invoiceSearchValues);
			
		}
		
		return invoiceSearchValuesList;
	}
	
}
