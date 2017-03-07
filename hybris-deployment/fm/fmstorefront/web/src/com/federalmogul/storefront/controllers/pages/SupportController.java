/**
 * 
 */
package com.federalmogul.storefront.controllers.pages;


import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.i18n.I18NFacade;
import de.hybris.platform.commercefacades.order.CheckoutFacade;
import de.hybris.platform.commercefacades.user.data.CountryData;
import de.hybris.platform.core.model.c2l.CountryModel;
import de.hybris.platform.core.model.c2l.RegionModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.user.UserService;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.federalmogul.facades.account.FMCustomerFacade;
import com.federalmogul.facades.locations.FederalMogulLocationsFacades;
import com.federalmogul.facades.user.data.FMCustomerData;
import com.federalmogul.falcon.core.model.FMZonesModel;
import com.federalmogul.storefront.breadcrumb.impl.SupportBreadcrumbBuilder;
import com.federalmogul.storefront.controllers.ControllerConstants;
import com.federalmogul.storefront.forms.LeadGenerationCallBackRequestForm;
import com.federalmogul.storefront.util.XSSFilterUtil;


/**
 * @author SU276498
 * 
 */

@Controller
@Scope("tenant")
@RequestMapping(value = "/**/support")
public class SupportController extends AbstractPageController
{

	protected static final String LANDING_SUPPORT_PAGE = "fmContactUsPage";
	protected static final String SUPPORT_TECHNICAL_LINE = "fmTechnicalLinePage";
	protected static final String SUPPORT_CUSTOMER_SERVICE = "fmCustomerServicePage";

	@Resource(name = "SupportBreadcrumbBuilder")
	protected SupportBreadcrumbBuilder supportBreadcrumbBuilder;

	@Autowired
	@Resource
	private UserService userService;

	@Resource
	private FederalMogulLocationsFacades federalMogulLocationsFacades;




	/**
	 * @return the federalMogulLocationsFacades
	 */
	public FederalMogulLocationsFacades getFederalMogulLocationsFacades()
	{
		return federalMogulLocationsFacades;
	}

	@Resource(name = "fmCustomerFacade")
	protected FMCustomerFacade fmCustomerFacade;

	@Resource(name = "checkoutFacade")
	private CheckoutFacade checkoutFacade;

	private static final Logger LOG = Logger.getLogger(SupportController.class);



	@Resource(name = "i18NFacade")
	private I18NFacade i18NFacade;



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


	@RequestMapping(value = "/technical-line", method = RequestMethod.GET)
	public String support(final Model model) throws CMSItemNotFoundException
	{
		LOG.info("Technical");
		storeCmsPageInModel(model, getContentPageForLabelOrId(SUPPORT_TECHNICAL_LINE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(SUPPORT_TECHNICAL_LINE));
		model.addAttribute("breadcrumbs", supportBreadcrumbBuilder.getBreadcrumbs("Technical Line"));
		model.addAttribute("metaRobots", "no-index,no-follow");

		return ControllerConstants.Views.Pages.Support.TechnicalLinePage;
	}

	@RequestMapping(value = "/technical-line/{componentUid}", method = RequestMethod.GET)
	public String technical(final Model model, @PathVariable final String componentUid, @RequestParam final String complnkname)
			throws CMSItemNotFoundException
	{
		LOG.info("Technical links");

		storeCmsPageInModel(model, getContentPageForLabelOrId(SUPPORT_TECHNICAL_LINE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(SUPPORT_TECHNICAL_LINE));
		model.addAttribute("componentUid", XSSFilterUtil.filter(componentUid));
		model.addAttribute(
				"breadcrumbs",
				supportBreadcrumbBuilder.createTechnicalBreadCrumbs(XSSFilterUtil.filter(componentUid),
						XSSFilterUtil.filter(complnkname)));
		model.addAttribute("metaRobots", "no-index,no-follow");

		return ControllerConstants.Views.Pages.Support.TechnicalLinePage;
	}

	@RequestMapping(value = "/customer-service", method = RequestMethod.GET)
	public String customer(final Model model) throws CMSItemNotFoundException
	{
		LOG.info("Customer");

		storeCmsPageInModel(model, getContentPageForLabelOrId(SUPPORT_CUSTOMER_SERVICE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(SUPPORT_CUSTOMER_SERVICE));
		model.addAttribute("breadcrumbs", supportBreadcrumbBuilder.getCustomerBreadcrumbs("Customer Service Link"));
		model.addAttribute("metaRobots", "no-index,no-follow");

		return ControllerConstants.Views.Pages.Support.CustomerServicePage;
	}

	@RequestMapping(value = "/customer-service/{componentUid}", method = RequestMethod.GET)
	public String customer(final Model model, @PathVariable final String componentUid, @RequestParam final String complnkname)
			throws CMSItemNotFoundException
	{
		LOG.info("Customer");

		storeCmsPageInModel(model, getContentPageForLabelOrId(SUPPORT_CUSTOMER_SERVICE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(SUPPORT_CUSTOMER_SERVICE));
		model.addAttribute("componentUid", XSSFilterUtil.filter(componentUid));
		model.addAttribute(
				"breadcrumbs",
				supportBreadcrumbBuilder.createCustomerBreadCrumbs(XSSFilterUtil.filter(componentUid),
						XSSFilterUtil.filter(complnkname)));
		model.addAttribute("metaRobots", "no-index,no-follow");

		return ControllerConstants.Views.Pages.Support.CustomerServicePage;
	}

	@RequestMapping(value = "/contact-us", method = RequestMethod.GET)
	public String contact(final Model model) throws CMSItemNotFoundException
	{
		LOG.info("ContactUs");

		storeCmsPageInModel(model, getContentPageForLabelOrId(LANDING_SUPPORT_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(LANDING_SUPPORT_PAGE));
		model.addAttribute("regionData", getI18NFacade().getRegionsForAllCountries().values());
		model.addAttribute("breadcrumbs", supportBreadcrumbBuilder.getContactUsBreadcrumbs("Overview"));
		model.addAttribute("metaRobots", "no-index,no-follow");

		return ControllerConstants.Views.Pages.Support.ContactUsPage;
	}

	@RequestMapping(value = "/contact-us/{componentUid}", method = RequestMethod.GET)
	public String contactUs(final Model model, @PathVariable final String componentUid,
			@RequestParam(value = "zone", defaultValue = "NorthAmerica") final String zone,
			@RequestParam(value = "countryISOCode", defaultValue = "US") final String countryISOCode,
			@RequestParam(value = "stateISOCode", defaultValue = "US-MI") final String stateISOCode,
			@RequestParam final String complnkname) throws CMSItemNotFoundException
	{
		LOG.info("ContactUs links");
		if (XSSFilterUtil.filter(componentUid).equals("supportgeneralInquiry"))
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

				//model.addAttribute("leadGenerationSubjects", LeadGenerationSubjects.values());


				leadForm.setFirstName(currentuserData.getFirstName());
				leadForm.setLastName(currentuserData.getLastName());
				leadForm.setEmail(currentuserData.getEmail());
				leadForm.setCustomerID(currentuserData.getUid());

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
			//model.addAttribute("countryData", checkoutFacade.getDeliveryCountries());
			model.addAttribute("regionsdatas", getI18NFacade().getRegionsForAllCountries().values());
			storeCmsPageInModel(model, getContentPageForLabelOrId(LANDING_SUPPORT_PAGE));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId(LANDING_SUPPORT_PAGE));
			model.addAttribute("componentUid", XSSFilterUtil.filter(componentUid));
			model.addAttribute(
					"breadcrumbs",
					supportBreadcrumbBuilder.createSupportBreadCrumbs(XSSFilterUtil.filter(componentUid),
							XSSFilterUtil.filter(complnkname)));
			model.addAttribute("metaRobots", "no-index,no-follow");

		}

		else
		{
			model.addAttribute("callBackFormData", new LeadGenerationCallBackRequestForm());
		}

		final List<AddressModel> fmAddressLocations = federalMogulLocationsFacades.getAllFMLocations(countryISOCode, stateISOCode);
		final List<CountryModel> fmcountries = federalMogulLocationsFacades.getAllFMContries(zone);
		final List<FMZonesModel> fmZones = federalMogulLocationsFacades.getAllFMZones();
		final List<RegionModel> regionList = federalMogulLocationsFacades.getAllFMStates(zone, countryISOCode);

		storeCmsPageInModel(model, getContentPageForLabelOrId(LANDING_SUPPORT_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(LANDING_SUPPORT_PAGE));

		model.addAttribute("fmAddressLocations", fmAddressLocations);
		model.addAttribute("fmcountries", fmcountries);
		model.addAttribute("fmZones", fmZones);
		model.addAttribute("fmStatesData", regionList);
		model.addAttribute("componentUid", componentUid);
		model.addAttribute(
				"breadcrumbs",
				supportBreadcrumbBuilder.createSupportBreadCrumbs(XSSFilterUtil.filter(componentUid),
						XSSFilterUtil.filter(complnkname)));
		model.addAttribute("metaRobots", "no-index,no-follow");

		return ControllerConstants.Views.Pages.Support.ContactUsPage;
	}

	public List getQueryList()
	{
		return FMLeadGenerationCallBackController.getLeadGenerationSubjects();
	}

	@RequestMapping(method =
	{ RequestMethod.GET, RequestMethod.POST }, value = "/getStates/{zone}/{countryISOCode}")
	public String getStates(@PathVariable("countryISOCode") final String countryISOCode, @PathVariable("zone") final String zone,
			final Model model) throws CMSItemNotFoundException
	{
		LOG.info("getStates #########CALLEDDDDDDDD######### countryISOCode :1111: " + countryISOCode);
		final List<RegionModel> regionList = federalMogulLocationsFacades.getAllFMStates(zone, countryISOCode);
		if (regionList == null || regionList.isEmpty())
		{
			final List<AddressModel> fmAddressLocations = federalMogulLocationsFacades.getAllFMLocations(countryISOCode, "");
			model.addAttribute("fmAddressLocations", fmAddressLocations);

			return "pages/fm/ajax/fmaddressLocations";
		}
		else
		{

			model.addAttribute("fmStatesData", regionList);
			return "pages/fm/ajax/fmLocations";
		}

	}

	@RequestMapping(method = RequestMethod.GET, value = "/getCountries/{zone}")
	public String getCountries(@PathVariable("zone") final String zone, final Model model)
	{
		LOG.info("getCountries ########CALLLED ########## Zone :11: " + zone);
		final List<CountryModel> fmcountries = federalMogulLocationsFacades.getAllFMContries(zone);
		model.addAttribute("fmcountries", fmcountries);
		return "pages/fm/ajax/fmLocations";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getLocations/{countryISOCode}/{stateISOCode}")
	public String getLocations(@PathVariable("countryISOCode") final String countryISOCode,
			@PathVariable("stateISOCode") final String stateISOCode, final Model model)
	{
		LOG.info("getLocations ########CALLLED ########## Zone :11: " + countryISOCode + " -- " + stateISOCode);
		final List<AddressModel> fmAddressLocations = federalMogulLocationsFacades.getAllFMLocations(countryISOCode, stateISOCode);
		model.addAttribute("fmAddressLocations", fmAddressLocations);
		return "pages/fm/ajax/fmaddressLocations";
	}

}
