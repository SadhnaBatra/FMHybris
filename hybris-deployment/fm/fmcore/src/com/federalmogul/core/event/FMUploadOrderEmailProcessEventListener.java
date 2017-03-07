/**
 * 
 */
package com.federalmogul.core.event;

import de.hybris.platform.core.Registry;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.event.impl.AbstractEventListener;
import de.hybris.platform.servicelayer.model.ModelService;

import org.apache.log4j.Logger;

import com.federalmogul.core.model.UploadOrderProcessEmailNotificationModel;
import com.federalmogul.falcon.core.model.UploadOrderModel;


/**
 * @author su244261
 * 
 */
public class FMUploadOrderEmailProcessEventListener extends AbstractEventListener<FMUploadOrderEmailProcessEvent>
{
	private static final Logger LOG = Logger.getLogger(FMUploadOrderEmailProcessEventListener.class);




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
	protected void onEvent(final FMUploadOrderEmailProcessEvent event)
	{
		try
		{
			LOG.info("onEvent started");
			final UploadOrderProcessEmailNotificationModel uploadOrderEmailProcessModel = (UploadOrderProcessEmailNotificationModel) getBusinessProcessService()
					.createProcess("FMUploadOrderEmailProcess" + System.currentTimeMillis(), "FMUploadOrderEmailProcess");

			LOG.info("uploadOrderProcessModel ==>" + uploadOrderEmailProcessModel);
			final String emaiId = event.getProcess().getEmailId();
			final UploadOrderModel orderModel = event.getProcess().getOrder();
			uploadOrderEmailProcessModel.setOrder(orderModel);
			uploadOrderEmailProcessModel.setEmailId(emaiId);
			LOG.debug("event.getBaseStore() " + event.getBaseStore());
			uploadOrderEmailProcessModel.setSite(event.getSite());
			uploadOrderEmailProcessModel.setStore(event.getBaseStore());
			getModelServiceViaLookup().save(uploadOrderEmailProcessModel);
			getBusinessProcessService().startProcess(uploadOrderEmailProcessModel);

		}
		catch (final Exception e)
		{
			LOG.error(e.getMessage());
		}

	}
}
