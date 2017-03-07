/**
 * 
 */
package com.federalmogul.core.event;

import de.hybris.platform.core.Registry;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.event.impl.AbstractEventListener;
import de.hybris.platform.servicelayer.model.ModelService;

import org.apache.log4j.Logger;

import com.federalmogul.core.model.UploadOrderProcessModel;
import com.federalmogul.falcon.core.model.UploadOrderModel;


/**
 * @author mamud
 * 
 */
public class UploadOrderEventListner extends AbstractEventListener<UploadOrderEvent>
{

	private static final Logger LOG = Logger.getLogger(UploadOrderEventListner.class);


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
	protected void onEvent(final UploadOrderEvent event)
	{
		// YTODO Auto-generated method stub
		LOG.info("onEvent started");
		final UploadOrderProcessModel uploadOrderProcessProcessModel = (UploadOrderProcessModel) getBusinessProcessService()
				.createProcess("FMUploadOrderProcess" + System.currentTimeMillis(), "FMUploadOrderProcess");

		final UploadOrderModel orderModel = event.getProcess().getOrder();
		LOG.info("orderModel Event Listener ===>" + orderModel.getCode());
		uploadOrderProcessProcessModel.setOrder(orderModel);
		uploadOrderProcessProcessModel.setSite(event.getSite());
		uploadOrderProcessProcessModel.setStore(event.getBaseStore());
		LOG.info("event.getBaseStore() " + event.getBaseStore());
		getModelServiceViaLookup().save(uploadOrderProcessProcessModel);
		getBusinessProcessService().startProcess(uploadOrderProcessProcessModel);

	}
}
