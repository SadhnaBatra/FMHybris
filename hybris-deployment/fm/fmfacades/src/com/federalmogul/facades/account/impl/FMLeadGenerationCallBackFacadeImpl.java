/**
 *
 */
package com.federalmogul.facades.account.impl;

import com.federalmogul.core.account.impl.FMCustomerServiceImpl;
import com.federalmogul.core.event.FMContactUsEmailEvent;
import com.federalmogul.core.model.FMContactUsProcessModel;
import com.federalmogul.core.model.FMLeadGenerationCallBackModel;
import com.federalmogul.facades.account.FMLeadGenerationCallBackFacade;
import com.federalmogul.facades.customer.data.FMLeadGenerationCallBackData;
import com.fm.falcon.webservices.dto.LeadGenerationCallBackRequestDTO;
import com.fm.falcon.webservices.soap.helper.LeadGenerationCallBackHelper;
import de.hybris.platform.commercefacades.customer.impl.DefaultCustomerFacade;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.store.services.BaseStoreService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import javax.xml.soap.SOAPException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Balaji
 */

public class FMLeadGenerationCallBackFacadeImpl extends DefaultCustomerFacade implements FMLeadGenerationCallBackFacade {

	private static final Logger LOG = Logger.getLogger(FMLeadGenerationCallBackFacadeImpl.class);

	final static Map<String, String> QUERYTYPES = new HashMap();
	
	public static final String NOTAPPLICABLE = "Not Applicable for this type of request";
	public static final String EMAIL = "Email";

	@Resource
	private BaseStoreService baseStoreService;

	@Resource
	private BaseSiteService baseSiteService;

	public BaseStoreService getBaseStoreService() {
		return baseStoreService;
	}

	public void setBaseStoreService(BaseStoreService baseStoreService) {
		this.baseStoreService = baseStoreService;
	}

	public BaseSiteService getBaseSiteService() {
		return baseSiteService;
	}

	public void setBaseSiteService(BaseSiteService baseSiteService) {
		this.baseSiteService = baseSiteService;
	}

	static {
		QUERYTYPES.put("Sales Inquiry", "ZCTS");
		QUERYTYPES.put("Order Inquiry", "ZCTS");
		QUERYTYPES.put("Product Inquiry", "ZCTS");
		QUERYTYPES.put("Promotional Inquiry", "ZCTS");
		QUERYTYPES.put("Other", "ZCTS");
		QUERYTYPES.put("Garage Rewards", "ZCTS");
		QUERYTYPES.put("Technical Inquiry", "ZCTS");
	}

	@Resource
	private EventService eventService;

	/**
	 * @return the eventService
	 */
	public EventService getEventService() {
		return eventService;
	}

	/**
	 * @param eventService the eventService to set
	 */
	public void setEventService(final EventService eventService) {
		this.eventService = eventService;
	}

	@Override
	public void createCallBack(final FMLeadGenerationCallBackData fMLeadGenerationCallBackData)
			throws UnsupportedOperationException, ClassNotFoundException, SOAPException, IOException {

		createCrmCallBack(fMLeadGenerationCallBackData);

	}


	/**
	 * User defined method for saving the response Return ID in model which returns the FMReturnItemsModel
	 *
	 * @return FMCustomerModel
	 * @throws IOException
	 * @throws SOAPException
	 * @throws ClassNotFoundException
	 * @throws UnsupportedOperationException
	 */

	@Override
	public FMLeadGenerationCallBackModel createCrmCallBack(final FMLeadGenerationCallBackData fMLeadGenerationCallBackData)
			throws UnsupportedOperationException, ClassNotFoundException, SOAPException, IOException {
		LOG.info("Entered createCrmReturnOrder In FacadeImpl");

		final FMLeadGenerationCallBackModel fMLeadGenerationCallBackModel = getModelService().create(
				FMLeadGenerationCallBackModel.class);
		final LeadGenerationCallBackHelper leadGenerationCallBackHelper = new LeadGenerationCallBackHelper();

		leadGenerationCallBackHelper.sendSOAPMessage(convertDataToDTO(fMLeadGenerationCallBackData));
		LOG.info("Form Posted Successfully to CRM");
		try {
			LOG.info("start email sending");
			sendEmailNotification(fMLeadGenerationCallBackData);
		} catch (Exception e) {
			LOG.error("Could not send contact-us email: ", e);
		}

		return fMLeadGenerationCallBackModel;
	}

	private LeadGenerationCallBackRequestDTO convertDataToDTO(final FMLeadGenerationCallBackData fMLeadGenerationCallBackData) {
		final LeadGenerationCallBackRequestDTO reqDTO = new LeadGenerationCallBackRequestDTO();

		reqDTO.setServiceName("MT_ECOMMERCECALLBACK");

		LOG.info("subject::" + fMLeadGenerationCallBackData.getLeadSubjects());

		reqDTO.setQueryType(QUERYTYPES.get(fMLeadGenerationCallBackData.getLeadSubjects()) != null ? QUERYTYPES.get(fMLeadGenerationCallBackData.getLeadSubjects()) : "ZCTS");
		LOG.info("QueryType::" + reqDTO.getQueryType());
		reqDTO.setFreeText(fMLeadGenerationCallBackData.getCallBackDescription());
		LOG.info("FreeText::" + reqDTO.getFreeText());
		reqDTO.setFirstName(fMLeadGenerationCallBackData.getFirstName());
		LOG.info("FirstName::" + reqDTO.getFirstName());
		reqDTO.setQueryDescription(fMLeadGenerationCallBackData.getLeadSubjects());
		reqDTO.setLastName(fMLeadGenerationCallBackData.getLastName());
		LOG.info("LastName::" + reqDTO.getLastName());

		reqDTO.setEmail(fMLeadGenerationCallBackData.getEmail());
		LOG.info("Email::" + reqDTO.getEmail());

		reqDTO.setTelephone(fMLeadGenerationCallBackData.getDefaultShippingAddress().getPhone());
		LOG.info("Telephone::" + reqDTO.getTelephone());

		reqDTO.setCountry(fMLeadGenerationCallBackData.getDefaultShippingAddress().getCountry().getIsocode());

		reqDTO.setBestWayToContact(fMLeadGenerationCallBackData.getBestWayToContact());
		reqDTO.setPhoneDays(StringUtils.join(fMLeadGenerationCallBackData.getBestContactDays(), ','));
		reqDTO.setPhoneTime(StringUtils.join(fMLeadGenerationCallBackData.getBestContactTimeOfDays(), ','));
		reqDTO.setPhoneTimeZone(fMLeadGenerationCallBackData.getBestContactTimeZone());

		reqDTO.setPartNumber(fMLeadGenerationCallBackData.getPartNumber());
		reqDTO.setBrand(fMLeadGenerationCallBackData.getBrand());
		reqDTO.setInvoiceNumber(fMLeadGenerationCallBackData.getInvoiceNumber());
		reqDTO.setOrderNumber(fMLeadGenerationCallBackData.getOrderNumber());
		reqDTO.setCustomerID(fMLeadGenerationCallBackData.getCustomerID());

		return reqDTO;
	}

	private void sendEmailNotification(final FMLeadGenerationCallBackData fMLeadGenerationCallBackData) throws Exception {
		LOG.info("sendEmailNotification method invoked ");
		FMContactUsProcessModel model = new FMContactUsProcessModel();
		model.setQueryType(fMLeadGenerationCallBackData.getLeadSubjects());
		
		model.setPartNumber(fMLeadGenerationCallBackData.getPartNumber()!=null?fMLeadGenerationCallBackData.getPartNumber():NOTAPPLICABLE);
		model.setOrderNumber(fMLeadGenerationCallBackData.getOrderNumber()!=null?fMLeadGenerationCallBackData.getOrderNumber():NOTAPPLICABLE);
		model.setBestWayToContact(fMLeadGenerationCallBackData.getBestWayToContact());
		model.setDescription(fMLeadGenerationCallBackData.getCallBackDescription()!=null?fMLeadGenerationCallBackData.getCallBackDescription():NOTAPPLICABLE);
		model.setBrand(fMLeadGenerationCallBackData.getBrand()!=null?fMLeadGenerationCallBackData.getBrand():NOTAPPLICABLE);
		model.setInvoiceNumber(fMLeadGenerationCallBackData.getInvoiceNumber()!=null?fMLeadGenerationCallBackData.getInvoiceNumber():NOTAPPLICABLE);
		model.setCustomerID(fMLeadGenerationCallBackData.getCustomerID()!=null?fMLeadGenerationCallBackData.getCustomerID():NOTAPPLICABLE);
		model.setCustomerEmail(fMLeadGenerationCallBackData.getEmail());
		model.setCustomerName(fMLeadGenerationCallBackData.getFirstName()+" "+fMLeadGenerationCallBackData.getLastName());
		model.setPhone(fMLeadGenerationCallBackData.getDefaultShippingAddress().getPhone());
		if(fMLeadGenerationCallBackData.getBestWayToContact()!=null && fMLeadGenerationCallBackData.getBestWayToContact().toString().equalsIgnoreCase(EMAIL)){
			model.setPhoneDays(NOTAPPLICABLE);
			model.setPhoneTime(NOTAPPLICABLE);
			model.setPhoneTimeZone(NOTAPPLICABLE);
		}
		else{
			model.setPhoneDays(fMLeadGenerationCallBackData.getBestContactDays()!=null?fMLeadGenerationCallBackData.getBestContactDays().toString():NOTAPPLICABLE);
			model.setPhoneTime(fMLeadGenerationCallBackData.getBestContactTimeOfDays()!=null?fMLeadGenerationCallBackData.getBestContactTimeOfDays().toString():NOTAPPLICABLE);
			model.setPhoneTimeZone(fMLeadGenerationCallBackData.getBestContactTimeZone()!=null?fMLeadGenerationCallBackData.getBestContactTimeZone().toString():NOTAPPLICABLE);
		}
		LOG.info("FMContactUsProcessModel Object Values:"+model.getQueryType()+":"+model.getBestWayToContact()+":"+model.getCustomerEmail()+":"+model.getDescription()+":"+model.getPhoneDays()+":"+model.getCustomerName()+":"+model.getPhone()
		           +":"+model.getPartNumber()+":"+model.getBrand());
		final FMContactUsEmailEvent fmContactUsEmailEvent = new FMContactUsEmailEvent(model);

		fmContactUsEmailEvent.setBaseStore(getBaseStoreService().getCurrentBaseStore());
		fmContactUsEmailEvent.setSite(getBaseSiteService().getCurrentBaseSite());
		fmContactUsEmailEvent.setLanguage(getCommonI18NService().getCurrentLanguage());
		fmContactUsEmailEvent.setCurrency(getCommonI18NService().getCurrentCurrency());

		getEventService().publishEvent(fmContactUsEmailEvent);
	}


}
