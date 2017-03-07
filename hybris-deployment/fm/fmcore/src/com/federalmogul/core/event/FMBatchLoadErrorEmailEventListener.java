package com.federalmogul.core.event;



import de.hybris.platform.core.Registry;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.event.impl.AbstractEventListener;
import de.hybris.platform.servicelayer.model.ModelService;

import org.apache.log4j.Logger;

import com.federalmogul.core.model.FMBatchLoadErrorEmailModel;


public class FMBatchLoadErrorEmailEventListener extends AbstractEventListener<FMBatchLoadErrorEmailEvent>
{

	private static final Logger LOG = Logger.getLogger(FMBatchLoadErrorEmailEventListener.class);




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
	protected void onEvent(final FMBatchLoadErrorEmailEvent event)
	{
		try
		{


			LOG.info("onEvent started");
			final FMBatchLoadErrorEmailModel fmBatchLoadErrorEmailModel = (FMBatchLoadErrorEmailModel) getBusinessProcessService()
					.createProcess("batchLoadErrorEmailProcess" + System.currentTimeMillis(), "batchLoadErrorEmailProcess");


			final String emaiId = event.getProcess().getEmailId();
			final String Error = event.getProcess().getErrors();
			fmBatchLoadErrorEmailModel.setEmailId(emaiId);
			fmBatchLoadErrorEmailModel.setFileName(event.getProcess().getFileName());
			fmBatchLoadErrorEmailModel.setErrors(Error);
			LOG.info("event.getSite():::::" + event.getProcess().getSite());
			fmBatchLoadErrorEmailModel.setSite(event.getProcess().getSite());
			fmBatchLoadErrorEmailModel.setCustomer(event.getCustomer());
			fmBatchLoadErrorEmailModel.setLanguage(event.getLanguage());
			fmBatchLoadErrorEmailModel.setStore(event.getProcess().getStore());
			LOG.info("event.getStore():::::" + event.getProcess().getStore());
			//fmBatchLoadErrorEmailModel.setCustomer(event.getCustomer());
			getModelServiceViaLookup().save(fmBatchLoadErrorEmailModel);
			getBusinessProcessService().startProcess(fmBatchLoadErrorEmailModel);
		}
		catch (final Exception e)
		{
			LOG.error(e.getMessage());
		}

	}

}
