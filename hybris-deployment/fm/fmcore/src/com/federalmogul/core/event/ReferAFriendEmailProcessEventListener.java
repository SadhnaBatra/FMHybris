/**
 * 
 */
package com.federalmogul.core.event;

import de.hybris.platform.core.Registry;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.event.impl.AbstractEventListener;
import de.hybris.platform.servicelayer.model.ModelService;

import org.apache.log4j.Logger;

import com.federalmogul.core.model.ReferAFriendEmailProcessModel;


/**
 * @author si279688
 * 
 */
public class ReferAFriendEmailProcessEventListener extends AbstractEventListener<ReferAFriendEmailProcessEvent>
{
	private static final Logger LOG = Logger.getLogger(ReferAFriendEmailProcessEventListener.class);




	public BusinessProcessService getBusinessProcessService()
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
	protected void onEvent(final ReferAFriendEmailProcessEvent event)
	{
		{
			try
			{


				LOG.info("onEvent started");
				final ReferAFriendEmailProcessModel referAFriendProcessModel = (ReferAFriendEmailProcessModel) getBusinessProcessService()
						.createProcess("referAFriendProcess" + System.currentTimeMillis(), "referAFriendProcess");

				referAFriendProcessModel.setReferer(event.getProcess().getReferer());

				LOG.debug("event.getBaseStore() " + event.getBaseStore());

				referAFriendProcessModel.setSite(event.getSite());

				referAFriendProcessModel.setStore(event.getBaseStore());
				referAFriendProcessModel.setRefereeEmail(event.getProcess().getRefereeEmail());
				referAFriendProcessModel.setRefereeFirstName(event.getProcess().getRefereeFirstName());
				referAFriendProcessModel.setRefereeLastName(event.getProcess().getRefereeLastName());
				referAFriendProcessModel.setCurrency(event.getCurrency());
				referAFriendProcessModel.setLanguage(event.getLanguage());
				referAFriendProcessModel.setCustomer(event.getProcess().getCustomer());
				getModelServiceViaLookup().save(referAFriendProcessModel);
				getBusinessProcessService().startProcess(referAFriendProcessModel);
			}
			catch (final Exception e)
			{
				LOG.error(e.getMessage());
			}

		}
	}
}
