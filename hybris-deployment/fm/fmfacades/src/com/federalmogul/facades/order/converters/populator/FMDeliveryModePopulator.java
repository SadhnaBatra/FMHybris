/**
 * 
 */
package com.federalmogul.facades.order.converters.populator;

import de.hybris.platform.commercefacades.order.converters.populator.DeliveryModePopulator;
import de.hybris.platform.commercefacades.order.data.DeliveryModeData;
import de.hybris.platform.core.model.order.delivery.DeliveryModeModel;

import org.apache.log4j.Logger;


/**
 * @author mamud
 * 
 */
public class FMDeliveryModePopulator extends DeliveryModePopulator
{
	private static final Logger LOG = Logger.getLogger(FMDeliveryModePopulator.class);

	@Override
	public void populate(final DeliveryModeModel source, final DeliveryModeData target)
	{
		super.populate(source, target);
		LOG.error("source ==>" + source.getCarrier());
		target.setCarrier(source.getCarrier());
		LOG.error("target ==>" + target.getCarrier());
	}
}
