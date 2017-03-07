/**
 * 
 */
package com.federalmogul.core.event;

import de.hybris.platform.core.Registry;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.event.impl.AbstractEventListener;
import de.hybris.platform.servicelayer.model.ModelService;

import org.apache.log4j.Logger;

import com.federalmogul.core.model.FMB2BBRegistrationApprovalProcessModel;


/**
 * @author su244261
 * 
 */
public class FMB2BBRegistrationApprovalProcessEventListener extends AbstractEventListener<FMB2BBRegistrationApprovalProcessEvent>
{
	private static final Logger LOG = Logger.getLogger(FMB2BBRegistrationApprovalProcessEventListener.class);




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
	protected void onEvent(final FMB2BBRegistrationApprovalProcessEvent event)
	{
		try
		{


			LOG.info("onEvent started");
			final FMB2BBRegistrationApprovalProcessModel fmB2BBRegistrationApprovalProcessModel = (FMB2BBRegistrationApprovalProcessModel) getBusinessProcessService()
					.createProcess("FMB2BBRegistrationApprovalProcess" + System.currentTimeMillis(),
							"FMB2BBRegistrationApprovalProcess");


			final String emaiId = event.getProcess().getEmailId();

			fmB2BBRegistrationApprovalProcessModel.setEmailId(emaiId);

			LOG.debug("event.getBaseStore() " + event.getBaseStore());

			fmB2BBRegistrationApprovalProcessModel.setSite(event.getSite());
			fmB2BBRegistrationApprovalProcessModel.setCustomer(event.getCustomer());
			fmB2BBRegistrationApprovalProcessModel.setLanguage(event.getLanguage());
			fmB2BBRegistrationApprovalProcessModel.setCurrency(event.getCurrency());
			fmB2BBRegistrationApprovalProcessModel.setStore(event.getBaseStore());



			getModelServiceViaLookup().save(fmB2BBRegistrationApprovalProcessModel);
			getBusinessProcessService().startProcess(fmB2BBRegistrationApprovalProcessModel);
		}
		catch (final Exception e)
		{
			LOG.error(e.getMessage());
		}

	}

}
