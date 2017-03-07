/**
 * 
 */
package com.federalmogul.storefront.util;

import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;

import com.fmo.wom.domain.OrderUnitBO;
import com.fmo.wom.domain.ShippedOrderBO;


/**
 * @author sa297584
 * 
 */
public class CustomComparator implements Comparator<ShippedOrderBO>
{
	private static final Logger LOG = Logger.getLogger(CustomComparator.class);

	/*
	 * @Override public int compare(final OrderUnitBO o1, final OrderUnitBO o2) { return
	 * o1.getOriginalOrderDate().compareTo(o2.getOriginalOrderDate()); }
	 */

	@Override
	public int compare(final ShippedOrderBO o1, final ShippedOrderBO o2)
	{
		final ShippedOrderBO firstOrderUnitBOo1 = o1;
		final ShippedOrderBO firstOrderUnitBOo2 = o2;

		LOG.info("O1 Date " + firstOrderUnitBOo1.getOrderUnitList().get(0).getOriginalOrderDate());
		LOG.info("O2 Date " + firstOrderUnitBOo2.getOrderUnitList().get(0).getOriginalOrderDate());

		LOG.info(" return val :: "
				+ firstOrderUnitBOo1.getOrderUnitList().get(0).getOriginalOrderDate()
						.compareTo(firstOrderUnitBOo2.getOrderUnitList().get(0).getOriginalOrderDate()));

		/*
		 * return firstOrderUnitBO_o1.getOrderUnitList().get(0).getOriginalOrderDate()
		 * .compareTo(firstOrderUnitBO_o2.getOrderUnitList().get(0).getOriginalOrderDate());
		 */
		return ORDERDATE_COMPARATOR.compare(firstOrderUnitBOo1.getOrderUnitList(), firstOrderUnitBOo2.getOrderUnitList());
	}



	public static final Comparator<List<OrderUnitBO>> ORDERDATE_COMPARATOR = new Comparator<List<OrderUnitBO>>()
	{



		@Override
		public int compare(final List<OrderUnitBO> o1, final List<OrderUnitBO> o2)
		{
			final OrderUnitBO firstOrderUnitBOo1 = o1.get(0);
			final OrderUnitBO firstOrderUnitBOo2 = o2.get(0);

			return firstOrderUnitBOo1.getOriginalOrderDate().compareTo(firstOrderUnitBOo2.getOriginalOrderDate());
		}

	};



}