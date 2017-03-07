/**
 * 
 */
package com.federalmogul.facades.order.converters.populator;

import de.hybris.platform.commercefacades.order.converters.populator.OrderPopulator;
import de.hybris.platform.commercefacades.order.data.AbstractOrderData;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.order.OrderModel;


/**
 * @author mamud

 * 
 */
/**
 * Converter implementation for {@link de.hybris.platform.core.model.order.OrderModel} as source and
 * {@link de.hybris.platform.commercefacades.order.data.OrderData} as target type.
 */
public class FMOrderPopulator extends OrderPopulator
{
	@Override
	public void populate(final OrderModel source, final OrderData target)
	{
		super.populate(source, target);
		populateSAPCustDetails(source, target);

	}

	void populateSAPCustDetails(final AbstractOrderModel source, final AbstractOrderData target)
	{
		target.setFmordertype(source.getFmordertype());
		target.setCustponumber(source.getCustponumber());
		target.setSapordernumber(source.getSapordernumber());
		target.setOrdercomments(source.getOrdercomments());
		target.setPocustid(source.getPocustid());
	}

}
