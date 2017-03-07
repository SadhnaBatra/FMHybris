package com.federalmogul.core.event;



import de.hybris.platform.core.Registry;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.event.impl.AbstractEventListener;
import de.hybris.platform.servicelayer.model.ModelService;

import org.apache.log4j.Logger;

import com.federalmogul.core.model.FMCustomerRegistrationProcessModel;


public class FMCustomerRegistrationProcessEventListener extends AbstractEventListener<FMCustomerRegistrationProcessEvent>
{

	private static final Logger LOG = Logger.getLogger(FMCustomerRegistrationProcessEventListener.class);




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
	protected void onEvent(final FMCustomerRegistrationProcessEvent event)
	{
		try
		{


			LOG.info("onEvent started");
			final FMCustomerRegistrationProcessModel fmCustomerRegistrationProcessModel = (FMCustomerRegistrationProcessModel) getBusinessProcessService()
					.createProcess("FMCustomerRegistrationProcess" + System.currentTimeMillis(), "FMCustomerRegistrationProcess");


			final String emaiId = event.getProcess().getEmailId();

			fmCustomerRegistrationProcessModel.setEmailId(emaiId);

			LOG.debug("event.getBaseStore() " + event.getBaseStore());

			fmCustomerRegistrationProcessModel.setSite(event.getSite());
			fmCustomerRegistrationProcessModel.setCustomer(event.getCustomer());
			fmCustomerRegistrationProcessModel.setLanguage(event.getLanguage());
			fmCustomerRegistrationProcessModel.setCurrency(event.getCurrency());
			fmCustomerRegistrationProcessModel.setStore(event.getBaseStore());



			getModelServiceViaLookup().save(fmCustomerRegistrationProcessModel);
			getBusinessProcessService().startProcess(fmCustomerRegistrationProcessModel);
		}
		catch (final Exception e)
		{
			LOG.error(e.getMessage());
		}

	}

}
