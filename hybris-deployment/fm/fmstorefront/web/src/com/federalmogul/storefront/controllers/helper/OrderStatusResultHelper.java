/**
 * 
 */
package com.federalmogul.storefront.controllers.helper;

import java.util.List;

import org.apache.log4j.Logger;

import com.fmo.wom.business.orderstatus.OrderStatusHelper;
import com.fmo.wom.business.orderstatus.OrderStatusResult;


/**
 * @author SA297584
 * 
 */
public class OrderStatusResultHelper
{



	private static final Logger LOG = Logger.getLogger(OrderStatusResultHelper.class);

	/**
	 * 
	 * @param orderStatusHelper
	 * @return
	 */
	public List<OrderStatusResult> sortByOrderDate(final OrderStatusHelper orderStatusHelper)
	{
		LOG.info("OrderStatusResultHelper's sortByOrderDate Called ");
		return orderStatusHelper.sortByOrderDate();

	}

	/**
	 * 
	 * @param orderStatusHelper
	 * @return
	 */
	public List<OrderStatusResult> sortByPurchaseOrderNumber(final OrderStatusHelper orderStatusHelper)
	{
		LOG.info("OrderStatusResultHelper's sortByPurchaseOrderNumber Called ");
		return orderStatusHelper.sortByPurchaseOrderNumber();

	}

	/**
	 * 
	 * @param orderStatusHelper
	 * @return
	 */
	public List<OrderStatusResult> sortByOrderNumber(final OrderStatusHelper orderStatusHelper)
	{
		LOG.info("OrderStatusResultHelper's sortByOrderNumber Called ");
		return orderStatusHelper.sortByOrderNumber();

	}

	/**
	 * 
	 * @param orderStatusHelper
	 * @return
	 */
	public List<OrderStatusResult> sortByOrderPackingSlipNumber(final OrderStatusHelper orderStatusHelper)
	{
		LOG.info("OrderStatusResultHelper's sortByOrderPackingSlipNumber Called ");
		return orderStatusHelper.sortByPackingSlipNumber();

	}

	/**
	 * 
	 * @param orderStatusHelper
	 * @return
	 */
	public List<OrderStatusResult> sortByOrderShipDate(final OrderStatusHelper orderStatusHelper)
	{
		LOG.info("OrderStatusResultHelper's sortByOrderShipDate Called ");
		return orderStatusHelper.sortByShipDate();

	}

	/**
	 * 
	 * @param orderStatusHelper
	 * @return
	 */
	public List<OrderStatusResult> sortByOrderShippingDC(final OrderStatusHelper orderStatusHelper)
	{
		LOG.info("OrderStatusResultHelper's sortByOrderShippingDC Called ");
		return orderStatusHelper.sortByShippingDC();

	}

	/**
	 * 
	 * @param orderStatusHelper
	 * @return
	 */
	public List<OrderStatusResult> sortByOrderStatus(final OrderStatusHelper orderStatusHelper)
	{
		LOG.info("OrderStatusResultHelper's sortByOrderStatus Called ");
		return orderStatusHelper.sortByStatus();

	}
}
