/**
 * 
 */
package com.federalmogul.storefront.controllers.helper;

import java.util.List;

import org.apache.log4j.Logger;

import com.fmo.wom.business.util.backorder.BackOrderHelper;
import com.fmo.wom.business.util.backorder.BackOrderSearchValues;


/**
 * @author SA297584
 * 
 */
public class BackOrderHeaderHelper
{
	private static final Logger LOG = Logger.getLogger(BackOrderHeaderHelper.class);


	/**
	 * 
	 * @param backOrderHelper
	 * @return
	 */
	public List<BackOrderSearchValues> performOrderDateSort(final BackOrderHelper backOrderHelper)
	{
		LOG.info("### Invoking the performOrderDateSort  ### 1111111");
		backOrderHelper.performOrderDateSort();
		final List<BackOrderSearchValues> bos = backOrderHelper.getBackOrderValuesList();

		for (final BackOrderSearchValues backOrderSearchValues : bos)
		{
			LOG.info("getPartNumber : " + backOrderSearchValues.getPartNumber());
			LOG.info("getAccountCode : " + backOrderSearchValues.getShipToAccount().getAccountCode());
			LOG.info("getOrderNumber : " + backOrderSearchValues.getOrderNumber());
			LOG.info("getCustomerPONumber : " + backOrderSearchValues.getCustomerPONumber());
			LOG.info("getCode : " + backOrderSearchValues.getDistCntr().getCode());
			LOG.info("getOrderDate : " + backOrderSearchValues.getOrderDate());
			LOG.info("getOriginalOrderQty : " + backOrderSearchValues.getOriginalOrderQty());
			LOG.info("getBackOrderQty : " + backOrderSearchValues.getBackOrderQty());

		}
		return backOrderHelper.getBackOrderValuesList();
	}

	/**
	 * 
	 * @param backOrderHelper
	 * @return
	 */
	public List<BackOrderSearchValues> performOrderNumberSort(final BackOrderHelper backOrderHelper)
	{
		LOG.info("### Invoking the performOrderNumberSort  ###");
		backOrderHelper.performOrderNumberSort();
		return backOrderHelper.getBackOrderValuesList();
	}

	/**
	 * 
	 * @param backOrderHelper
	 * @return
	 */
	public List<BackOrderSearchValues> performPartNumberSort(final BackOrderHelper backOrderHelper)
	{
		LOG.info("### Invoking the performPartNumberSort  ###");
		backOrderHelper.performPartNumberSort();
		return backOrderHelper.getBackOrderValuesList();
	}

	/**
	 * 
	 * @param backOrderHelper
	 * @return
	 */
	public List<BackOrderSearchValues> performPurchaseOrderNumberSort(final BackOrderHelper backOrderHelper)
	{
		LOG.info("### Invoking the performPurchaseOrderNumberSort  ###");
		backOrderHelper.performPurchaseOrderNumberSort();
		return backOrderHelper.getBackOrderValuesList();
	}
}
