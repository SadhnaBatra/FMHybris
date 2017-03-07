package com.federalmogul.facades.process.email.context;

import com.federalmogul.core.model.FMContactUsProcessModel;
import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel;
import de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.util.Config;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by steve on 11/29/16.
 */
public class FMContactUsContext extends AbstractEmailContext<FMContactUsProcessModel> {

	private static final Logger LOG = Logger.getLogger(FMContactUsContext.class);

	private final static Map<String, String> QUERY_TYPE_RECIPIENT_MAP = new HashMap<String, String>();
	private static final String CUSTOMERSERVICE = "Customer Service Team";
	String customerName;
	String customerEmail;;
	String phone;
	String bestWayToContact;
	String queryType;
	String customerID;
	String brand;
	String orderNumber;
	String invoiceNumber;
	String partNumber;
	String phoneDays;
	String phoneTime;
	String phoneTimeZone;
	String description;
	static {
		QUERY_TYPE_RECIPIENT_MAP.put("Sales Inquiry", Config.getParameter("contactus_sales"));
		QUERY_TYPE_RECIPIENT_MAP.put("Order Inquiry", Config.getParameter("contactus_order"));
		QUERY_TYPE_RECIPIENT_MAP.put("Product Inquiry", Config.getParameter("contactus_product"));
		QUERY_TYPE_RECIPIENT_MAP.put("Promotional Inquiry", Config.getParameter("contactus_promotional"));
		QUERY_TYPE_RECIPIENT_MAP.put("Other", Config.getParameter("contactus_other"));
		QUERY_TYPE_RECIPIENT_MAP.put("Garage Rewards", Config.getParameter("contactus_garagerewards"));
		QUERY_TYPE_RECIPIENT_MAP.put("Technical Inquiry", Config.getParameter("contactus_technical"));
	}

	public FMContactUsProcessModel getContactUsProcessModel() {
		return contactUsProcessModel;
	}

	public void setContactUsProcessModel(FMContactUsProcessModel contactUsProcessModel) {
		this.contactUsProcessModel = contactUsProcessModel;
	}

	private FMContactUsProcessModel contactUsProcessModel;

	@Override
	public void init(final FMContactUsProcessModel businessProcessModel, final EmailPageModel emailPageModel) {
		LOG.info("INSIDE CONTEXT INIT");
		super.init(businessProcessModel, emailPageModel);
		contactUsProcessModel = businessProcessModel;
		this.customerName=businessProcessModel.getCustomerName();
		this.queryType=businessProcessModel.getQueryType();
		this.customerEmail=businessProcessModel.getCustomerEmail();
		this.phone=businessProcessModel.getPhone();
		this.bestWayToContact=businessProcessModel.getBestWayToContact();
		this.brand=businessProcessModel.getBrand();
		this.partNumber=businessProcessModel.getPartNumber();
		this.orderNumber=businessProcessModel.getOrderNumber();
		this.invoiceNumber=businessProcessModel.getInvoiceNumber();
		this.phoneDays=businessProcessModel.getPhoneDays();
		this.phoneTime=businessProcessModel.getPhoneTime();
		this.phoneTimeZone=businessProcessModel.getPhoneTimeZone();
		this.customerID=businessProcessModel.getCustomerID();
		this.description=businessProcessModel.getDescription();
		
		if (QUERY_TYPE_RECIPIENT_MAP.containsKey(contactUsProcessModel.getQueryType())) {
			String emailString = QUERY_TYPE_RECIPIENT_MAP.get(contactUsProcessModel.getQueryType());
			LOG.info("emailString:"+emailString);
			put(EMAIL, QUERY_TYPE_RECIPIENT_MAP.get(contactUsProcessModel.getQueryType()));
			put(DISPLAY_NAME,CUSTOMERSERVICE);
			LOG.info("Display name of Customer Service Team added");
		} else {
			put(EMAIL, QUERY_TYPE_RECIPIENT_MAP.get("Other"));
		}
	}

	@Override
	protected BaseSiteModel getSite(FMContactUsProcessModel businessProcessModel) {
		return businessProcessModel.getSite();
	}

	@Override
	protected CustomerModel getCustomer(FMContactUsProcessModel businessProcessModel) {
		return null;
	}

	@Override
	protected LanguageModel getEmailLanguage(FMContactUsProcessModel businessProcessModel) {
		LanguageModel languageModel = new LanguageModel();
		languageModel.setIsocode("en");
		return languageModel;
	}
	
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getBestWayToContact() {
		return bestWayToContact;
	}

	public void setBestWayToContact(String bestWayToContact) {
		this.bestWayToContact = bestWayToContact;
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public String getPhoneDays() {
		return phoneDays;
	}

	public void setPhoneDays(String phoneDays) {
		this.phoneDays = phoneDays;
	}

	public String getPhoneTime() {
		return phoneTime;
	}

	public void setPhoneTime(String phoneTime) {
		this.phoneTime = phoneTime;
	}

	public String getPhoneTimeZone() {
		return phoneTimeZone;
	}

	public void setPhoneTimeZone(String phoneTimeZone) {
		this.phoneTimeZone = phoneTimeZone;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static Map<String, String> getQueryTypeRecipientMap() {
		return QUERY_TYPE_RECIPIENT_MAP;
	}

}
