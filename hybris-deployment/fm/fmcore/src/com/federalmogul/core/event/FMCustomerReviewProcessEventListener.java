/**
 * 
 */
package com.federalmogul.core.event;

import de.hybris.platform.core.Registry;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.event.impl.AbstractEventListener;
import de.hybris.platform.servicelayer.model.ModelService;

import org.apache.log4j.Logger;

import com.federalmogul.core.model.FMCustomerReviewProcessModel;


/**
 * @author SR279690
 * 
 */
public class FMCustomerReviewProcessEventListener extends AbstractEventListener<FMCustomerReviewProcessEvent>
{
	private static final Logger LOG = Logger.getLogger(FMCustomerReviewProcessEventListener.class);

	protected BusinessProcessService getBusinessProcessService()
	{
		return (BusinessProcessService) Registry.getApplicationContext().getBean("businessProcessService");
	}


	public ModelService getModelServiceViaLookup()
	{
		throw new UnsupportedOperationException(
				"Please define in the spring configuration a <lookup-method> for getModelServiceViaLookup().");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hybris.platform.servicelayer.event.impl.AbstractEventListener#onEvent(de.hybris.platform.servicelayer.event
	 * .events.AbstractEvent)
	 */
	@Override
	protected void onEvent(final FMCustomerReviewProcessEvent event)
	{
		try
		{
			LOG.info("OnEvent method in listener");
			final FMCustomerReviewProcessModel reviewProcessModel = (FMCustomerReviewProcessModel) getBusinessProcessService()
					.createProcess("CustomerReviewProcess" + System.currentTimeMillis(), "CustomerReviewProcess");
			LOG.info("review process model created");
			reviewProcessModel.setSite(event.getSite());
			reviewProcessModel.setStore(event.getBaseStore());
			reviewProcessModel.setCustomerReview(event.getProcess().getCustomerReview());
			LOG.info("review title::" + event.getProcess().getCustomerReview().getHeadline());
			LOG.info("review comment::" + event.getProcess().getCustomerReview().getComment());
			LOG.info("user:::" + event.getCustomer().getCustomerID());
			getModelServiceViaLookup().save(reviewProcessModel);
			getBusinessProcessService().startProcess(reviewProcessModel);
		}
		catch (final Exception e)
		{
			LOG.error("Exception caught ::" + e.getMessage());
		}
	}
}
