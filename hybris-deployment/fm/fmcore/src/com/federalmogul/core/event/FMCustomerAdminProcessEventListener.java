/**
 * 
 */
package com.federalmogul.core.event;

import de.hybris.platform.core.Registry;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.event.impl.AbstractEventListener;
import de.hybris.platform.servicelayer.model.ModelService;

import org.apache.log4j.Logger;

import com.federalmogul.core.model.FMCustomerAdminProcessModel;


/**
 * @author su244261
 * 
 */
public class FMCustomerAdminProcessEventListener extends AbstractEventListener<FMCustomerAdminProcessEvent>
{
	private static final Logger LOG = Logger.getLogger(FMCustomerAdminProcessEventListener.class);




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
	protected void onEvent(final FMCustomerAdminProcessEvent event)
	{
		try
		{


			LOG.info("onEvent started");
			final FMCustomerAdminProcessModel fmCustomerAdminProcessModel = (FMCustomerAdminProcessModel) getBusinessProcessService()
					.createProcess("FMCustomerAdminProcess" + System.currentTimeMillis(), "FMCustomerAdminProcess");


			final String emaiId = event.getProcess().getEmailId();

			fmCustomerAdminProcessModel.setEmailId(emaiId);

			LOG.debug("event.getBaseStore() " + event.getBaseStore());

			fmCustomerAdminProcessModel.setSite(event.getSite());
			fmCustomerAdminProcessModel.setCustomer(event.getCustomer());
			fmCustomerAdminProcessModel.setLanguage(event.getLanguage());
			fmCustomerAdminProcessModel.setCurrency(event.getCurrency());
			fmCustomerAdminProcessModel.setStore(event.getBaseStore());



			getModelServiceViaLookup().save(fmCustomerAdminProcessModel);
			getBusinessProcessService().startProcess(fmCustomerAdminProcessModel);
		}
		catch (final Exception e)
		{
			LOG.error(e.getMessage());
		}

	}

}
