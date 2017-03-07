/**
 * 
 */
package com.federalmogul.core.event;

import de.hybris.platform.core.Registry;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.event.impl.AbstractEventListener;
import de.hybris.platform.servicelayer.model.ModelService;

import org.apache.log4j.Logger;

import com.federalmogul.core.model.LoyaltyOrderConfirmationProcessModel;


/**
 * @author di278272
 * 
 */
public class LoyaltyOrderConfirmationProcessEventListener extends AbstractEventListener<LoyaltyOrderConfirmationProcessEvent>
{
	private static final Logger LOG = Logger.getLogger(LoyaltyOrderConfirmationProcessEventListener.class);




	public BusinessProcessService getBusinessProcessService()
	{
		return (BusinessProcessService) Registry.getApplicationContext().getBean("businessProcessService");
	}

	public ModelService getModelServiceViaLookup()
	{
		throw new UnsupportedOperationException(
				"Please define in the spring configuration a <lookup-method> for getModelServiceViaLookup().");
	}


	@Override
	protected void onEvent(final LoyaltyOrderConfirmationProcessEvent event)
	{
		try
		{


			LOG.info("onEvent started");
			final LoyaltyOrderConfirmationProcessModel loyaltyOrderConfirmProcessModel = (LoyaltyOrderConfirmationProcessModel) getBusinessProcessService()
					.createProcess("loyaltyOrderConfirmationProcess" + System.currentTimeMillis(), "loyaltyOrderConfirmationProcess");

			loyaltyOrderConfirmProcessModel.setOrder(event.getProcess().getOrder());

			LOG.debug("event.getBaseStore() " + event.getBaseStore());

			loyaltyOrderConfirmProcessModel.setSite(event.getSite());

			loyaltyOrderConfirmProcessModel.setStore(event.getBaseStore());

			loyaltyOrderConfirmProcessModel.setCustomer(event.getProcess().getCustomer());
			loyaltyOrderConfirmProcessModel.setOrderNumber(event.getProcess().getOrder().getPurchaseOrderNumber());
			LOG.info("FROM EVENT  CUSTOMER ID" + event.getProcess().getCustomer().getUid());
			LOG.info("FROM EVENT  CUSTOMER ID" + event.getCustomer().getUid());
			getModelServiceViaLookup().save(loyaltyOrderConfirmProcessModel);
			getBusinessProcessService().startProcess(loyaltyOrderConfirmProcessModel);
		}
		catch (final Exception e)
		{
			LOG.error(e.getMessage());
		}

	}

}
