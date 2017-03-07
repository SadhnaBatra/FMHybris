/**
 * 
 */
package com.federalmogul.storefront.controllers.helper;

import java.util.List;

import org.apache.log4j.Logger;

import com.fmo.wom.business.util.invoice.InvoiceHelper;
import com.fmo.wom.business.util.invoice.InvoiceSearchValues;


/**
 * @author SA297584
 * 
 */
public class InvoiceHeaderHelper
{
	private static final Logger LOG = Logger.getLogger(InvoiceHeaderHelper.class);


	/**
	 * 
	 * @param invoiceHelper
	 * @return
	 */
	public List<InvoiceSearchValues> performInvoiceDateSort(final InvoiceHelper invoiceHelper)
	{
		LOG.info("### Invoking the performInvoiceDateSort  ###");
		invoiceHelper.performInvoiceDateSort();
		final List<InvoiceSearchValues> isv = invoiceHelper.getInvoiceSearchValuesList();

		for (final InvoiceSearchValues invoiceSearchValues : isv)
		{
			LOG.info(" getInvoiceNumber :: " + invoiceSearchValues.getInvoiceNumber());
			LOG.info("getAccountCode TO " + invoiceSearchValues.getBillToAccount().getAccountCode());
			LOG.info("getAccountCode Deliver TO :: " + invoiceSearchValues.getShipToAccount().getAccountCode());
			LOG.info("getInvoiceDate " + invoiceSearchValues.getInvoiceDate());
			LOG.info("getInvoiceAmount " + invoiceSearchValues.getInvoiceAmount());
			LOG.info(" getCustPONumber" + invoiceSearchValues.getCustomerPurchaseOrderNumber());
			LOG.info(" getInvoiceType" + invoiceSearchValues.getInvoiceType());
		}
		return invoiceHelper.getInvoiceSearchValuesList();
	}

	/**
	 * 
	 * @param invoiceHelper
	 * @return
	 */
	public List<InvoiceSearchValues> performInvoiceNumberSort(final InvoiceHelper invoiceHelper)
	{
		LOG.info("### Invoking the performInvoiceDateSort  ###");
		invoiceHelper.performInvoiceNumberSort();
		return invoiceHelper.getInvoiceSearchValuesList();
	}

	/**
	 * 
	 * @param invoiceHelper
	 * @return
	 */
	public List<InvoiceSearchValues> performInvoiceTypeSort(final InvoiceHelper invoiceHelper)
	{
		LOG.info("### Invoking the performInvoiceDateSort  ###");
		invoiceHelper.performInvoiceTypeSort();
		return invoiceHelper.getInvoiceSearchValuesList();
	}

	/**
	 * 
	 * @param invoiceHelper
	 * @return
	 */
	public List<InvoiceSearchValues> performPurchaseOrderNumberSort(final InvoiceHelper invoiceHelper)
	{
		LOG.info("### Invoking the performInvoiceDateSort  ###");
		invoiceHelper.performPurchaseOrderNumberSort();
		return invoiceHelper.getInvoiceSearchValuesList();
	}

}
