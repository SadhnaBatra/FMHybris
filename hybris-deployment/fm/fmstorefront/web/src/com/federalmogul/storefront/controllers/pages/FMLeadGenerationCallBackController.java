/**
 * 
 */
package com.federalmogul.storefront.controllers.pages;

import de.hybris.platform.b2bacceleratorfacades.company.B2BCommerceUserFacade;
import de.hybris.platform.b2bacceleratorfacades.company.CompanyB2BCommerceFacade;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.i18n.I18NFacade;
import de.hybris.platform.commercefacades.order.CheckoutFacade;
import de.hybris.platform.commercefacades.user.UserFacade;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.data.CountryData;
import de.hybris.platform.commercefacades.user.data.RegionData;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.user.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.federalmogul.facades.account.FMCustomerFacade;
//import com.federalmogul.facades.account.FMCustomerFacade;
import com.federalmogul.facades.account.FMLeadGenerationCallBackFacade;
import com.federalmogul.facades.account.FMUserFacade;
import com.federalmogul.facades.customer.data.FMLeadGenerationCallBackData;
import com.federalmogul.facades.user.data.FMCustomerData;
import com.federalmogul.storefront.breadcrumb.impl.SupportBreadcrumbBuilder;
import com.federalmogul.storefront.controllers.ControllerConstants;
import com.federalmogul.storefront.controllers.util.GlobalMessages;
import com.federalmogul.storefront.forms.LeadGenerationCallBackRequestForm;


/**
 * @author Balaji Controller for home page.
 * 
 * 
 */

@Controller
@Scope("tenant")
@RequestMapping("/leadGeneration")
public class FMLeadGenerationCallBackController extends SearchPageController
{

	private static final String FMLEADGENERATION_CALLBACK_CMS_PAGE = "LeadGenerationCallBackPage";

	protected static final String LANDING_SUPPORT_PAGE = "fmContactUsPage";

	static final String ORDER_INQUIRY = "Order Inquiry";
	static final String PRODUCT_INQUIRY = "Product Inquiry";
	static final String TECHNICAL_INQUIRY = "Technical Inquiry";
	static final String SALES_INQUIRY = "Sales Inquiry";
	static final String PROMOTIONAL_INQUIRY = "Promotional Inquiry";
	static final String GARAGE_REWARDS = "Garage Rewards";
	static final String OTHER = "Other";
	static final String PHONE = "Phone";

	private static final Logger LOG = Logger.getLogger(FMLeadGenerationCallBackController.class);

	@Resource(name = "userFacade")
	protected UserFacade userFacade;

	@Resource(name = "checkoutFacade")
	private CheckoutFacade checkoutFacade;

	@Resource
	protected FMUserFacade fmUserfacade;
	@Resource
	protected B2BCommerceUserFacade b2bCommerceUserFacade;

	@Resource(name = "i18NFacade")
	private I18NFacade i18NFacade;

	@Resource(name = "leadGenerationCallBackFacade")
	protected FMLeadGenerationCallBackFacade fmLeadGenerationCallBackFacade;

	@Resource(name = "b2bCommerceFacade")
	protected CompanyB2BCommerceFacade companyB2BCommerceFacade;

	@Resource(name = "SupportBreadcrumbBuilder")
	protected SupportBreadcrumbBuilder supportBreadcrumbBuilder;

	@Resource(name = "fmCustomerFacade")
	protected FMCustomerFacade fmCustomerFacade;

	@Autowired
	@Resource
	private UserService userService;

	@RequestMapping(value = "/contact-us", method = RequestMethod.GET)
	public ModelAndView leadGenerationCallBack(final Model model) throws CMSItemNotFoundException
	{
		final UserModel user = userService.getCurrentUser();
		@SuppressWarnings("deprecation")
		final boolean isUserAnonymous = user == null || userService.isAnonymousUser(user);
		final LeadGenerationCallBackRequestForm leadForm = new LeadGenerationCallBackRequestForm();
		final List leadGenerationSubjects = getQueryList();
		LOG.info("leadGeneration-callBack ==>" + leadGenerationSubjects.size());
		model.addAttribute("leadGenerationSubjects", leadGenerationSubjects);
		if (!isUserAnonymous)
		{
			final FMCustomerData currentuserData = fmCustomerFacade.getCurrentFMCustomer();
			LOG.info("leadGeneration-callBack ==>");

			leadForm.setFirstName(currentuserData.getFirstName());
			leadForm.setLastName(currentuserData.getLastName());
			leadForm.setEmail(currentuserData.getEmail());

			if (currentuserData.getDefaultShippingAddress() != null)
			{
				leadForm.setPhoneno(currentuserData.getDefaultShippingAddress().getPhone());
			}
		}
		model.addAttribute("callBackFormData", leadForm);
		final List<CountryData> countries = new ArrayList<CountryData>();
		countries.add(checkoutFacade.getCountryForIsocode("US"));
		countries.add(checkoutFacade.getCountryForIsocode("CA"));
		model.addAttribute("countryData", countries);
		model.addAttribute("regionsdatas", getI18NFacade().getRegionsForAllCountries().values());
		storeCmsPageInModel(model, getContentPageForLabelOrId(FMLEADGENERATION_CALLBACK_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FMLEADGENERATION_CALLBACK_CMS_PAGE));
		model.addAttribute("breadcrumbs", supportBreadcrumbBuilder.getEmailContactUsBreadcrumbs("Contact-Us"));
		model.addAttribute("metaRobots", "no-index,no-follow");
		model.addAttribute("selectedLink", "LeadGenerationCallBack");
		return new ModelAndView(ControllerConstants.Views.Pages.Account.FMLeadGenerationCallBack);
	}

	/**
	 * controller mapping for saving the values after clicking Submit button
	 * 
	 * @param callBackRequestForm
	 * @return String
	 * @throws CMSItemNotFoundException
	 */
	@RequestMapping(value = "/contact-us-submit", method = RequestMethod.POST)
	public String leadGenerationCallBack(@ModelAttribute("callBackFormData") final LeadGenerationCallBackRequestForm callBackRequestForm, final Model model) throws CMSItemNotFoundException
	{

		LOG.info("leadGeneration-callBack request ==>");

		final List leadGenerationSubjects = getQueryList();
		LOG.info("leadGeneration-callBack ==>" + leadGenerationSubjects.size());


		if (!validateform(callBackRequestForm, model))
		{
			LOG.info("leadGeneration-false request ==>");
			storeCmsPageInModel(model, getContentPageForLabelOrId(LANDING_SUPPORT_PAGE));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId(LANDING_SUPPORT_PAGE));
			model.addAttribute("leadGenerationSubjects", leadGenerationSubjects);

			model.addAttribute("callBackFormData", callBackRequestForm);
			final List<CountryData> countries = new ArrayList<CountryData>();
			countries.add(checkoutFacade.getCountryForIsocode("US"));
			countries.add(checkoutFacade.getCountryForIsocode("CA"));
			LOG.info("countries ==>" + countries.size());
			LOG.info("countries 1 ==>" + countries.get(0));
			model.addAttribute("countryData", countries);
			model.addAttribute("componentUid", "supportgeneralInquiry");
			model.addAttribute("regionsdatas", getI18NFacade().getRegionsForAllCountries().values());
			model.addAttribute("breadcrumbs", supportBreadcrumbBuilder.getEmailContactUsBreadcrumbs("Contact-Us"));
			return ControllerConstants.Views.Pages.Support.ContactUsPage;
		}
		else
		{
			try
			{
				fmLeadGenerationCallBackFacade.createCallBack(populateLeadGenerationCallBackData(callBackRequestForm));
				GlobalMessages.addInfoMessage(model, "Your message has been submitted.");
			}
			catch (final Exception e)
			{
				GlobalMessages.addInfoMessage(model, "Your message has not been submitted... Please try again");
				LOG.error("Exception ...." + e.getMessage());

			}

			storeCmsPageInModel(model, getContentPageForLabelOrId(LANDING_SUPPORT_PAGE));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId(LANDING_SUPPORT_PAGE));
			model.addAttribute("leadGenerationSubjects", leadGenerationSubjects);
			model.addAttribute("callBackData", new LeadGenerationCallBackRequestForm());
			model.addAttribute("countryData", checkoutFacade.getDeliveryCountries());
			model.addAttribute("regionsdatas", getI18NFacade().getRegionsForAllCountries().values());
			model.addAttribute("breadcrumbs", supportBreadcrumbBuilder.getEmailContactUsBreadcrumbs("Contact-Us"));
			model.addAttribute("metaRobots", "no-index,no-follow");
			model.addAttribute("selectedLink", "LeadGenerationCallBackRequest");
			model.addAttribute("componentUid", "supportgeneralInquiry");
			return ControllerConstants.Views.Pages.Support.ContactUsPage;
		}
	}

	@RequestMapping(value = "/contact-us-reset", method = RequestMethod.GET)
	public ModelAndView leadGenerationCallBackReset(final Model model) throws CMSItemNotFoundException
	{

		LOG.info("leadGeneration-callBack ==>");
		final List leadGenerationSubjects = getQueryList();
		LOG.info("leadGeneration-callBack ==>" + leadGenerationSubjects.size());
		model.addAttribute("leadGenerationSubjects", leadGenerationSubjects);


		model.addAttribute("callBackFormData", new LeadGenerationCallBackRequestForm());
		final List<CountryData> countries = new ArrayList<CountryData>();
		countries.add(checkoutFacade.getCountryForIsocode("US"));
		countries.add(checkoutFacade.getCountryForIsocode("CA"));
		model.addAttribute("countryData", countries);
		model.addAttribute("regionsdatas", getI18NFacade().getRegionsForAllCountries().values());
		storeCmsPageInModel(model, getContentPageForLabelOrId(LANDING_SUPPORT_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(LANDING_SUPPORT_PAGE));
		model.addAttribute("breadcrumbs", supportBreadcrumbBuilder.getEmailContactUsBreadcrumbs("Contact-Us"));
		model.addAttribute("metaRobots", "no-index,no-follow");
		model.addAttribute("selectedLink", "LeadGenerationCallBack");
		model.addAttribute("componentUid", "supportgeneralInquiry");
		return new ModelAndView(ControllerConstants.Views.Pages.Support.ContactUsPage);
	}


	private boolean validateform(final LeadGenerationCallBackRequestForm leadGenerationCallBackRequestForm, final Model model)
	{
		LOG.info("Inside vaildate form method of LeadGenerationCallBackRequestForm " + leadGenerationCallBackRequestForm.getSubjects());

		if (null == leadGenerationCallBackRequestForm.getPhoneno() || leadGenerationCallBackRequestForm.getPhoneno().isEmpty())
		{
			GlobalMessages.addErrorMessage(model, "Please enter your Phone Number");
			LOG.info("Inside vaildate form :: Please enter your last Phone Number");

		}
		else
		{
			if (!(leadGenerationCallBackRequestForm.getPhoneno().trim().matches("\\d{3}-\\d{3}-\\d{4}")
					|| leadGenerationCallBackRequestForm.getPhoneno().trim().matches("\\(\\d{3}\\) \\d{3}-\\d{4}")
					|| leadGenerationCallBackRequestForm.getPhoneno().trim().matches("\\d{10}")
					|| leadGenerationCallBackRequestForm.getPhoneno().trim().matches("\\d{3}.\\d{3}.\\d{4}")
					|| leadGenerationCallBackRequestForm.getPhoneno().trim().matches("[(]\\d{3}[)]\\d{3}-\\d{4}") || leadGenerationCallBackRequestForm
					.getPhoneno().trim().matches("\\d{2}-\\d{1}.\\d{1}-\\d{1} \\d{3} \\d{2}")))

			{
				GlobalMessages.addErrorMessage(model, "Please verify that you've entered a 10-digit phone number.");
				LOG.info("Inside vaildate form :: invalid  phone number");
			}
		}

		final UserModel user = userService.getCurrentUser();
		final boolean isUserAnonymous = user == null || userService.isAnonymousUser(user);
		if (isUserAnonymous && "Order Inquiry".equalsIgnoreCase(leadGenerationCallBackRequestForm.getSubjects())) {
			GlobalMessages.addErrorMessage(model, "Please log in to submit your order inquiry.");
		}

		if (GlobalMessages.hasErrorMessage(model))
		{
			LOG.info("Inside vaildate form :: returning failure");
			return false;
		}
		else
		{
			LOG.info("Inside vaildate form :: returning success");
			return true;
		}


	}

	private FMLeadGenerationCallBackData populateLeadGenerationCallBackData(
			final LeadGenerationCallBackRequestForm leadGenerationCallBackRequestForm)
	{

		LOG.info("FORM leadGenerationCallBackRequestForm FORM ::" + leadGenerationCallBackRequestForm.getPhoneno());

		final FMLeadGenerationCallBackData fMLeadGenerationCallBackData = new FMLeadGenerationCallBackData();

		LOG.info("Inside populate Call Back CreationData:");

		String leadSubject = leadGenerationCallBackRequestForm.getSubjects();
		if (PRODUCT_INQUIRY.equalsIgnoreCase(leadSubject)) {
			fMLeadGenerationCallBackData.setPartNumber(leadGenerationCallBackRequestForm.getPartNumber());
		} else if (TECHNICAL_INQUIRY.equalsIgnoreCase(leadSubject)) {
			fMLeadGenerationCallBackData.setPartNumber(leadGenerationCallBackRequestForm.getPartNumber());
			fMLeadGenerationCallBackData.setBrand(leadGenerationCallBackRequestForm.getBrand());
		} else if (ORDER_INQUIRY.equalsIgnoreCase(leadSubject)) {
			fMLeadGenerationCallBackData.setInvoiceNumber(leadGenerationCallBackRequestForm.getInvoiceNumber());
			fMLeadGenerationCallBackData.setOrderNumber(leadGenerationCallBackRequestForm.getOrderNumber());
			if (leadGenerationCallBackRequestForm.getCustomerID() == null) {
				fMLeadGenerationCallBackData.setCustomerID("");
			} else {
				fMLeadGenerationCallBackData.setCustomerID(leadGenerationCallBackRequestForm.getCustomerID());
			}
		}

		fMLeadGenerationCallBackData.setLeadSubjects(leadSubject);
		LOG.info("Subjects::" + leadSubject);
		fMLeadGenerationCallBackData.setCallBackDescription(leadGenerationCallBackRequestForm.getCallBackDescription());
		LOG.info("CallBackDescription::" + leadGenerationCallBackRequestForm.getCallBackDescription());
		fMLeadGenerationCallBackData.setFirstName(leadGenerationCallBackRequestForm.getFirstName());
		LOG.info("FirstName" + leadGenerationCallBackRequestForm.getFirstName());
		fMLeadGenerationCallBackData.setLastName(leadGenerationCallBackRequestForm.getLastName());
		LOG.info("LastName::" + leadGenerationCallBackRequestForm.getLastName());

		fMLeadGenerationCallBackData.setEmail(leadGenerationCallBackRequestForm.getEmail());

		final AddressData customeraddress = new AddressData();
		customeraddress.setPhone(leadGenerationCallBackRequestForm.getPhoneno());
		final CountryData country = new CountryData();
		country.setIsocode(leadGenerationCallBackRequestForm.getCountry());
		customeraddress.setCountry(country);
		customeraddress.setVisibleInAddressBook(true);

		fMLeadGenerationCallBackData.setDefaultShippingAddress(customeraddress);

		String bestWayToContact = leadGenerationCallBackRequestForm.getBestWayToContact();
		fMLeadGenerationCallBackData.setBestWayToContact(bestWayToContact);
		if (PHONE.equalsIgnoreCase(bestWayToContact)) {
			fMLeadGenerationCallBackData.setBestContactDays(Arrays.asList(leadGenerationCallBackRequestForm.getContactDays()));
			fMLeadGenerationCallBackData.setBestContactTimeOfDays(Arrays.asList(leadGenerationCallBackRequestForm.getContactTimeOfDays()));
			fMLeadGenerationCallBackData.setBestContactTimeZone(leadGenerationCallBackRequestForm.getContactTimeZone());
		}

		return fMLeadGenerationCallBackData;
	}

	public List getQueryList()
	{
		return getLeadGenerationSubjects();
	}

	/**
	 * @return the i18NFacade
	 */
	public I18NFacade getI18NFacade()
	{
		return i18NFacade;
	}

	/**
	 * @param i18nFacade
	 *           the i18NFacade to set
	 */
	public void setI18NFacade(final I18NFacade i18nFacade)
	{
		i18NFacade = i18nFacade;
	}

	public static List<String> getLeadGenerationSubjects() {
		final List<String> leadGenerationSubjects = new ArrayList<String>(7);

		leadGenerationSubjects.add(ORDER_INQUIRY);
		leadGenerationSubjects.add(PRODUCT_INQUIRY);
		leadGenerationSubjects.add(TECHNICAL_INQUIRY);
		leadGenerationSubjects.add(SALES_INQUIRY);
		leadGenerationSubjects.add(PROMOTIONAL_INQUIRY);
		leadGenerationSubjects.add(GARAGE_REWARDS);
		leadGenerationSubjects.add(OTHER);

		return leadGenerationSubjects;
	}
}
