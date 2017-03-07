/**
 * 
 */
package com.federalmogul.core.event;

import de.hybris.platform.core.Registry;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.event.impl.AbstractEventListener;
import de.hybris.platform.servicelayer.model.ModelService;

import org.apache.log4j.Logger;

import com.federalmogul.core.model.FMB2SBTaxApprovalProcessModel;


/**
 * @author su244261
 * 
 */
public class FMB2SBTaxApprovalProcessEventListener extends AbstractEventListener<FMB2SBTaxApprovalProcessEvent>
{
	private static final Logger LOG = Logger.getLogger(FMB2SBTaxApprovalProcessEventListener.class);




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
	protected void onEvent(final FMB2SBTaxApprovalProcessEvent event)
	{
		try
		{


			LOG.info("onEvent started");
			final FMB2SBTaxApprovalProcessModel fmB2SBTaxApprovalProcessModel = (FMB2SBTaxApprovalProcessModel) getBusinessProcessService()
					.createProcess("FMB2SBTaxApprovalProcess" + System.currentTimeMillis(), "FMB2SBTaxApprovalProcess");


			final String emaiId = event.getProcess().getEmailId();

			fmB2SBTaxApprovalProcessModel.setEmailId(emaiId);

			LOG.debug("event.getBaseStore() " + event.getBaseStore());

			fmB2SBTaxApprovalProcessModel.setSite(event.getSite());
			fmB2SBTaxApprovalProcessModel.setCustomer(event.getCustomer());
			fmB2SBTaxApprovalProcessModel.setLanguage(event.getLanguage());
			fmB2SBTaxApprovalProcessModel.setCurrency(event.getCurrency());
			fmB2SBTaxApprovalProcessModel.setStore(event.getBaseStore());



			getModelServiceViaLookup().save(fmB2SBTaxApprovalProcessModel);
			getBusinessProcessService().startProcess(fmB2SBTaxApprovalProcessModel);
		}
		catch (final Exception e)
		{
			LOG.error(e.getMessage());
		}

	}

}
