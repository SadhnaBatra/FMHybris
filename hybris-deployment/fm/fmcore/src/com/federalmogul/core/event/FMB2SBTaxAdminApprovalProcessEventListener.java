/**
 * 
 */
package com.federalmogul.core.event;

import de.hybris.platform.core.Registry;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.event.impl.AbstractEventListener;
import de.hybris.platform.servicelayer.model.ModelService;

import org.apache.log4j.Logger;

import com.federalmogul.core.model.FMB2SBTaxAdminApprovalProcessModel;


/**
 * @author su244261
 * 
 */
public class FMB2SBTaxAdminApprovalProcessEventListener extends AbstractEventListener<FMB2SBTaxAdminApprovalProcessEvent>
{
	private static final Logger LOG = Logger.getLogger(FMB2SBTaxAdminApprovalProcessEventListener.class);




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
	protected void onEvent(final FMB2SBTaxAdminApprovalProcessEvent event)
	{
		try
		{


			LOG.info("onEvent started");
			final FMB2SBTaxAdminApprovalProcessModel fmB2SBTaxAdminApprovalProcessModel = (FMB2SBTaxAdminApprovalProcessModel) getBusinessProcessService()
					.createProcess("FMB2SBTaxAdminApprovalProcess" + System.currentTimeMillis(), "FMB2SBTaxAdminApprovalProcess");


			final String emaiId = event.getProcess().getEmailId();

			fmB2SBTaxAdminApprovalProcessModel.setEmailId(emaiId);

			LOG.debug("event.getBaseStore() " + event.getBaseStore());

			fmB2SBTaxAdminApprovalProcessModel.setSite(event.getSite());
			fmB2SBTaxAdminApprovalProcessModel.setCustomer(event.getCustomer());
			fmB2SBTaxAdminApprovalProcessModel.setLanguage(event.getLanguage());
			fmB2SBTaxAdminApprovalProcessModel.setCurrency(event.getCurrency());
			fmB2SBTaxAdminApprovalProcessModel.setStore(event.getBaseStore());



			getModelServiceViaLookup().save(fmB2SBTaxAdminApprovalProcessModel);
			getBusinessProcessService().startProcess(fmB2SBTaxAdminApprovalProcessModel);
		}
		catch (final Exception e)
		{
			LOG.error(e.getMessage());
		}

	}

}
