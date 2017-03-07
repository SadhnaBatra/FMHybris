/**
 * 
 */
package com.federalmogul.core.event;

import de.hybris.platform.core.Registry;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.event.impl.AbstractEventListener;
import de.hybris.platform.servicelayer.model.ModelService;

import org.apache.log4j.Logger;

import com.federalmogul.core.model.FMAdminAddNewUserProcessModel;


/**
 * @author di278272
 * 
 */
public class FMAdminAddNewUserProcessEventListener extends AbstractEventListener<FMAdminAddNewUserProcessEvent>
{
	private static final Logger LOG = Logger.getLogger(FMAdminAddNewUserProcessEventListener.class);




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
	protected void onEvent(final FMAdminAddNewUserProcessEvent event)
	{
		try
		{


			LOG.info("onEvent started");
			final FMAdminAddNewUserProcessModel fmAdminAddNewUserProcessModel = (FMAdminAddNewUserProcessModel) getBusinessProcessService()
					.createProcess("FMAdminAddNewUserProcess" + System.currentTimeMillis(), "FMAdminAddNewUserProcess");


			final String emaiId = event.getProcess().getEmailId();

			fmAdminAddNewUserProcessModel.setEmailId(emaiId);

			LOG.debug("event.getBaseStore() " + event.getBaseStore());

			fmAdminAddNewUserProcessModel.setSite(event.getSite());
			fmAdminAddNewUserProcessModel.setCustomer(event.getCustomer());
			fmAdminAddNewUserProcessModel.setLanguage(event.getLanguage());
			fmAdminAddNewUserProcessModel.setCurrency(event.getCurrency());
			fmAdminAddNewUserProcessModel.setStore(event.getBaseStore());



			getModelServiceViaLookup().save(fmAdminAddNewUserProcessModel);
			getBusinessProcessService().startProcess(fmAdminAddNewUserProcessModel);
		}
		catch (final Exception e)
		{
			LOG.error(e.getMessage());
		}

	}

}
