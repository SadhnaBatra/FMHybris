package com.federalmogul.core.event;

import com.federalmogul.core.event.FMContactUsEmailEvent;
import com.federalmogul.core.model.FMContactUsProcessModel;

import de.hybris.platform.commerceservices.event.AbstractSiteEventListener;
import de.hybris.platform.core.Registry;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.event.impl.AbstractEventListener;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.store.services.BaseStoreService;

import org.apache.log4j.Logger;

import javax.annotation.Resource;

/**
 * Created by steve on 11/29/16.
 */
public class FMContactUsProcessEventListener extends AbstractEventListener<FMContactUsEmailEvent> {

	private static final Logger LOG = Logger.getLogger(FMContactUsProcessEventListener.class);



	public BusinessProcessService getBusinessProcessService() {
		return (BusinessProcessService) Registry.getApplicationContext().getBean("businessProcessService");
	}

	public ModelService getModelServiceViaLookup() {
		throw new UnsupportedOperationException(
				"Please define in the spring configuration a <lookup-method> for getModelServiceViaLookup().");
	}

	@Override
	protected void onEvent(final FMContactUsEmailEvent event) {
		try {
			LOG.info("contactUsEmail onEvent started");
			final FMContactUsProcessModel fmContactUsProcessModel = (FMContactUsProcessModel) getBusinessProcessService()
					.createProcess("FMContactUsProcess" + System.currentTimeMillis(), "FMContactUsProcess");
			LOG.info("created process");

			fmContactUsProcessModel.setBestWayToContact(event.getProcess().getBestWayToContact());
			LOG.info("Set best way to contact:"+fmContactUsProcessModel.getBestWayToContact());
			fmContactUsProcessModel.setBrand(event.getProcess().getBrand());
			LOG.info("Set brand:"+fmContactUsProcessModel.getBrand());
			fmContactUsProcessModel.setCustomerID(event.getProcess().getCustomerID());
			LOG.info("Set customer id:"+fmContactUsProcessModel.getCustomerID());
			fmContactUsProcessModel.setInvoiceNumber(event.getProcess().getInvoiceNumber());
			LOG.info("Set invoice number:"+fmContactUsProcessModel.getInvoiceNumber());
			fmContactUsProcessModel.setOrderNumber(event.getProcess().getOrderNumber());
			LOG.info("Set order number:"+fmContactUsProcessModel.getOrderNumber());
			fmContactUsProcessModel.setPartNumber(event.getProcess().getPartNumber());
			LOG.info("Set part number:"+fmContactUsProcessModel.getPartNumber());
			fmContactUsProcessModel.setPhoneDays(event.getProcess().getPhoneDays());
			LOG.info("Set phone days:"+fmContactUsProcessModel.getPhoneDays());
			fmContactUsProcessModel.setPhoneTime(event.getProcess().getPhoneTime());
			LOG.info("Set phone time:"+fmContactUsProcessModel.getPhoneTime());
			fmContactUsProcessModel.setPhoneTimeZone(event.getProcess().getPhoneTimeZone());
			LOG.info("Set phone time zone:"+fmContactUsProcessModel.getPhoneTimeZone());
			fmContactUsProcessModel.setQueryType(event.getProcess().getQueryType());
			LOG.info("Set query type:"+fmContactUsProcessModel.getQueryType());

			fmContactUsProcessModel.setCustomerName(event.getProcess().getCustomerName());
			LOG.info("Set customer name:"+fmContactUsProcessModel.getCustomerName());
			fmContactUsProcessModel.setCustomerEmail(event.getProcess().getCustomerEmail());
			LOG.info("Set customer name:"+fmContactUsProcessModel.getCustomerEmail());
			fmContactUsProcessModel.setPhone(event.getProcess().getPhone());
			LOG.info("Set customer phone:"+fmContactUsProcessModel.getPhone());
			fmContactUsProcessModel.setDescription(event.getProcess().getDescription());
			LOG.info("Set Description:"+fmContactUsProcessModel.getDescription());
			
			fmContactUsProcessModel.setSite(event.getSite());
			LOG.info("Set Site:"+fmContactUsProcessModel.getSite());
			fmContactUsProcessModel.setStore(event.getBaseStore());
			LOG.info("Set Site:"+fmContactUsProcessModel.getStore());

			getModelServiceViaLookup().save(fmContactUsProcessModel);
			LOG.info("Get model service via lookup completed");
			getBusinessProcessService().startProcess(fmContactUsProcessModel);
			LOG.info("Started contact us process model");
		} catch (final Exception e) {
			LOG.error(e.getMessage(), e);
		}

	}
}
