package com.federalmogul.storefront.controllers.pages;


import com.federalmogul.core.constants.GeneratedFmCoreConstants.Enumerations.FmTaxValidationType;
import com.federalmogul.core.enums.Fmusertype;
import com.federalmogul.core.model.FMCustomerAccountModel;
import com.federalmogul.facades.account.FMCustomerFacade;
import com.federalmogul.facades.address.data.FMB2bAddressData;
import com.federalmogul.facades.user.data.FMCustomerData;
import com.federalmogul.facades.user.data.FMTaxDocumentData;
import com.federalmogul.storefront.constants.WebConstants;
import com.federalmogul.storefront.controllers.ControllerConstants;
import com.federalmogul.storefront.controllers.util.GlobalMessages;
import com.federalmogul.storefront.forms.FMRegistrationForm;

import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2bacceleratorfacades.company.CompanyB2BCommerceFacade;
import de.hybris.platform.b2bacceleratorservices.company.CompanyB2BCommerceService;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.i18n.I18NFacade;
import de.hybris.platform.commercefacades.order.CheckoutFacade;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.data.CountryData;
import de.hybris.platform.commercefacades.user.data.RegionData;
import de.hybris.platform.util.Config;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.xml.soap.SOAPException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;


/**
 * Controller for FederalMogul Registration page.
 */
@Controller
@Scope("tenant")
@RequestMapping(value = "/registration")
public class FMCustomerController extends AbstractPageController
{
	@Autowired
	private FMCustomerFacade fmCustomerFacade;

	@Resource(name = "b2bCommerceFacade")
	protected CompanyB2BCommerceFacade companyB2BCommerceFacade;

	private static final Logger LOG = Logger.getLogger(FMCustomerController.class);

	/*
	 * @Resource(name = "accountBreadcrumbBuilder") private ResourceBreadcrumbBuilder accountBreadcrumbBuilder;
	 */
	@Resource(name = "checkoutFacade")
	private CheckoutFacade checkoutFacade;

	@Resource(name = "i18NFacade")
	private I18NFacade i18NFacade;

	@Resource
	protected CompanyB2BCommerceService companyB2BCommerceService;
	// CMS Pages
	private static final String FM_ACCOUNT_CMS_PAGE = "RegisterUserPage";

	// Precompiled regex's...
	private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*?[0-9])(?=.*[A-Z]).{8,}$");
	private static final Pattern CA_POSTAL_CODE_PATTERN = Pattern.compile("(^[ABCEGHJKLMNPRSTVXYabceghjklmnprstvxy]{1}\\d{1}[A-Za-z]{1}\\s?\\-?\\d{1}[A-Za-z]{1}\\d{1}$)");
	private static final Pattern US_POSTAL_CODE_PATTERN = Pattern.compile("^\\d{5}(-\\d{4})?$");

	private static final String DUMMY_B2B_UNIT_FOR_CONSUMER = "DummyB2BUnit";

	/**
	 * Ajax method for getting the unit details
	 * 
	 * @param accnumber
	 * @return String
	 * @throws CMSItemNotFoundException
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/getFMUnitDetails", method = RequestMethod.POST)
	public String getunitdetails(@RequestParam(value = "accvalue") String accnumber, @RequestParam(value = "readOnly") Boolean readOnly, final Model model)
			throws CMSItemNotFoundException {
		if (accnumber.length() != 10) {

			int acccount = 0;
			final String codes = Config.getParameter(WebConstants.B2T_REGISTRATION_ACCOUNTS);
			if (codes != null) {
				final String[] acccodes = codes.split(",");
				for (int i = 0; i < acccodes.length; i++) {

					if (accnumber.contains(acccodes[i])) {
						acccount++;
					}
				}
				if (acccount == 0) {
					final int accnumberSize = 10 - accnumber.length();
					for (int i = 0; i < accnumberSize; i++) {
						accnumber = "0" + accnumber;
						LOG.info("inside getfmunits  of else part::::::::::::::::::::" + accnumber);
					}

				}
			}
		}


		if (validateacccode(accnumber, model))
		{
			final FMCustomerAccountModel unitB2B = (FMCustomerAccountModel) companyB2BCommerceService.getUnitForUid(accnumber);
			LOG.info("from ajax" + accnumber);
			model.addAttribute("unitdetails", unitB2B);
			LOG.info("unit id from model" + unitB2B.getUid());
			LOG.info("name from model" + " " + unitB2B.getLocName() + " " + unitB2B.getName());
			model.addAttribute("fmusertype", Fmusertype.values());
			model.addAttribute("fmdata", new FMRegistrationForm());
			final List<CountryData> countries = new ArrayList<CountryData>();
			countries.add(checkoutFacade.getCountryForIsocode("US"));
			countries.add(checkoutFacade.getCountryForIsocode("CA"));
			model.addAttribute("countryData", countries);
			model.addAttribute("regionsdatas", getI18NFacade().getRegionsForAllCountries().values());
			storeCmsPageInModel(model, getContentPageForLabelOrId(FM_ACCOUNT_CMS_PAGE));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FM_ACCOUNT_CMS_PAGE));

			if (readOnly) {
				return "fragments/account/b2bCompanyFragmentReadOnly";
			}

			return "fragments/account/b2bCompanyFragment";
		}
		else
		{

			/*
			 * storeCmsPageInModel(model, getContentPageForLabelOrId(FM_ACCOUNT_CMS_PAGE));
			 * setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FM_ACCOUNT_CMS_PAGE)); return REDIRECT_PREFIX
			 * + "/fmcustomer/registrationform";
			 */

			/*
			 * model.addAttribute("fmusertype", Fmusertype.values()); model.addAttribute("countryData",
			 * checkoutFacade.getDeliveryCountries()); model.addAttribute("regionsdatas",
			 * getI18NFacade().getRegionsForAllCountries().values()); model.addAttribute("fmdata", new
			 * FMRegistrationForm());
			 */
			/*
			 * storeCmsPageInModel(model, getContentPageForLabelOrId(FM_ACCOUNT_CMS_PAGE));
			 * setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FM_ACCOUNT_CMS_PAGE)); return
			 * ControllerConstants.Views.Pages.Account.SignUpPage;
			 */
			//	return getFMCustomerRegistrationForm(model);
			/*
			 * model.addAttribute("fmdata", new FMRegistrationForm()); return
			 * ControllerConstants.Views.Pages.Account.SignUpPage;
			 */
			/*
			 * model.addAttribute("fmusertype", Fmusertype.values()); model.addAttribute("fmdata", new
			 * FMRegistrationForm()); model.addAttribute("countryData", checkoutFacade.getDeliveryCountries());
			 * model.addAttribute("regionsdatas", getI18NFacade().getRegionsForAllCountries().values());
			 * storeCmsPageInModel(model, getContentPageForLabelOrId(FM_ACCOUNT_CMS_PAGE));
			 * setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FM_ACCOUNT_CMS_PAGE)); return
			 * ControllerConstants.Views.Pages.Account.SignUpPage;
			 */

			//	storeCmsPageInModel(model, getContentPageForLabelOrId(FM_ACCOUNT_CMS_PAGE));
			//	setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FM_ACCOUNT_CMS_PAGE));
			//	return REDIRECT_PREFIX + "/fmcustomer/registrationform";
			//GlobalMessages.addErrorMessage(model, "please enter the correct account code");

			LOG.info("inside else part of fmunits");
			model.addAttribute("fmusertype", Fmusertype.values());
			model.addAttribute("fmdata", new FMRegistrationForm());
			final List<CountryData> countries = new ArrayList<CountryData>();
			countries.add(checkoutFacade.getCountryForIsocode("US"));
			countries.add(checkoutFacade.getCountryForIsocode("CA"));
			model.addAttribute("countryData", countries);
			model.addAttribute("regionsdatas", getI18NFacade().getRegionsForAllCountries().values());
			storeCmsPageInModel(model, getContentPageForLabelOrId(FM_ACCOUNT_CMS_PAGE));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FM_ACCOUNT_CMS_PAGE));

			if (readOnly) {
				return "fragments/account/b2bCompanyFragmentReadOnly";
			}

			return "fragments/account/b2bErrorAccountDetailsFragment";

		}
	}

	@RequestMapping(value = "/getStates", method = RequestMethod.POST)
	public String getStatesForCountry(@RequestParam(value = "selectedCountry") final String country, final Model model)
			throws CMSItemNotFoundException
	{

		LOG.info("COUNTRY IN CONTROLLER;;" + country);
		model.addAttribute("fmusertype", Fmusertype.values());
		model.addAttribute("fmdata", new FMRegistrationForm());
		final List<CountryData> countries = new ArrayList<CountryData>();
		countries.add(checkoutFacade.getCountryForIsocode("US"));
		countries.add(checkoutFacade.getCountryForIsocode("CA"));
		model.addAttribute("countryData", countries);
		model.addAttribute("regionsdatas", getI18NFacade().getRegionsForCountryIso(country));
		storeCmsPageInModel(model, getContentPageForLabelOrId(FM_ACCOUNT_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FM_ACCOUNT_CMS_PAGE));
		return "fragments/account/statesFragment";
	}


	@RequestMapping(value = "/getCompanyStates", method = RequestMethod.POST)
	public String getCompanyStatesForCountry(@RequestParam(value = "selectedCountry") final String country, final Model model)
			throws CMSItemNotFoundException
	{

		LOG.info("COUNTRY IN CONTROLLER;;" + country);
		model.addAttribute("fmusertype", Fmusertype.values());
		model.addAttribute("fmdata", new FMRegistrationForm());
		final List<CountryData> countries = new ArrayList<CountryData>();
		countries.add(checkoutFacade.getCountryForIsocode("US"));
		countries.add(checkoutFacade.getCountryForIsocode("CA"));
		model.addAttribute("countryData", countries);

		model.addAttribute("regionsdatas", getI18NFacade().getRegionsForCountryIso(country));
		storeCmsPageInModel(model, getContentPageForLabelOrId(FM_ACCOUNT_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FM_ACCOUNT_CMS_PAGE));
		return "fragments/account/companyStatesFragment";
	}



	/**
	 * method to validate the account code entered by the user
	 * 
	 * @param accnumber
	 * @return boolean
	 * @throws IOException
	 */
	private boolean validateacccode(final String accnumber, final Model model)
	{
		if (accnumber == null || accnumber.isEmpty())
		{
			//GlobalMessages.addErrorMessage(model, "Please enter the account code");
			LOG.info("Inside vaildate form :: invalid account code");
			try
			{
				throw new IOException("Please enter the Account Code");
			}
			catch (final IOException e)
			{
				LOG.error("IOException ::" + e.getMessage());
				GlobalMessages.addErrorMessage(model, e.getMessage());
			}

		}
		else
		{

			final B2BUnitModel unitB2B = companyB2BCommerceService.getUnitForUid(accnumber);
			LOG.info("account number inside else part of valacccode:++++++++++++++++++++++++++++++++++" + accnumber);
			if (null == unitB2B)
			{
				//GlobalMessages.addErrorMessage(model, "Please enter the valid account code");
				LOG.info("Inside vaildate form :: invalid account code");
				try
				{
					throw new IOException("Please enter a valid Account Code");
				}
				catch (final IOException e)
				{
					LOG.error("IOException ::" + e.getMessage());
					GlobalMessages.addErrorMessage(model, e.getMessage());
				}


			}

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

	/**
	 * controller mapping for registration form to get signup page
	 * 
	 * @param model
	 * @return ModelAndView
	 * @throws CMSItemNotFoundException
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(method =
	{ RequestMethod.POST, RequestMethod.GET })
	public String getFMCustomerRegistrationForm(final Model model) throws CMSItemNotFoundException
	{
		model.addAttribute("metaRobots", "no-index,no-follow");
		model.addAttribute("fmusertype", Fmusertype.values());
		model.addAttribute("fmdata", new FMRegistrationForm());
		//	model.addAttribute("countryData", checkoutFacade.getDeliveryCountries());
		final List<CountryData> countries = new ArrayList<CountryData>();
		countries.add(checkoutFacade.getCountryForIsocode("US"));
		countries.add(checkoutFacade.getCountryForIsocode("CA"));
		model.addAttribute("countryData", countries);
		final Map<String, String> aboutShopMap = prepareaboutShopMap();
		final Map<String, String> shopTypeMap = prepareshopTypeMap();
		final Map<String, String> shopBayMap = prepareshopbayMap();
		final Map<String, String> mostInterstedMap = prepareMostInterstedMap();
		final Map<String, String> brandsMap = preparebrandMap();


		model.addAttribute("aboutShop", aboutShopMap);

		model.addAttribute("shopType", shopTypeMap);
		model.addAttribute("shopBays", shopBayMap);
		model.addAttribute("mostIntersted", mostInterstedMap);
		model.addAttribute("brands", brandsMap);


		model.addAttribute("regionsdatas", getI18NFacade().getRegionsForAllCountries().values());
		storeCmsPageInModel(model, getContentPageForLabelOrId(FM_ACCOUNT_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FM_ACCOUNT_CMS_PAGE));
		return ControllerConstants.Views.Pages.Account.SignUpPage;
	}

	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/referalFriendRegistration", method =
	{ RequestMethod.POST, RequestMethod.GET })
	public String getFMCustomerReferalRegistrationForm(final Model model) throws CMSItemNotFoundException
	{
		model.addAttribute("metaRobots", "no-index,no-follow");
		model.addAttribute("fmusertype", Fmusertype.values());
		model.addAttribute("fmdata", new FMRegistrationForm());
		//	model.addAttribute("countryData", checkoutFacade.getDeliveryCountries());
		final List<CountryData> countries = new ArrayList<CountryData>();
		countries.add(checkoutFacade.getCountryForIsocode("US"));
		countries.add(checkoutFacade.getCountryForIsocode("CA"));
		model.addAttribute("countryData", countries);
		model.addAttribute("referby", new String("true"));
		final Map<String, String> aboutShopMap = prepareaboutShopMap();
		final Map<String, String> shopTypeMap = prepareshopTypeMap();
		final Map<String, String> shopBayMap = prepareshopbayMap();
		final Map<String, String> mostInterstedMap = prepareMostInterstedMap();
		final Map<String, String> brandsMap = preparebrandMap();


		model.addAttribute("aboutShop", aboutShopMap);

		model.addAttribute("shopType", shopTypeMap);
		model.addAttribute("shopBays", shopBayMap);
		model.addAttribute("mostIntersted", mostInterstedMap);
		model.addAttribute("brands", brandsMap);
		model.addAttribute("regionsdatas", getI18NFacade().getRegionsForAllCountries().values());
		storeCmsPageInModel(model, getContentPageForLabelOrId(FM_ACCOUNT_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FM_ACCOUNT_CMS_PAGE));
		return ControllerConstants.Views.Pages.Account.SignUpPage;
	}

	/**
	 * controller mapping for saving the values after clicking register button
	 * 
	 * @param fmregistrationform
	 * @param result
	 * @return String
	 * @throws CMSItemNotFoundException
	 */
	@RequestMapping(value = "/createcustomer", method = RequestMethod.POST)
	public String createFMCustomer(@Valid @ModelAttribute("fmdata") final FMRegistrationForm fmregistrationform,
			final BindingResult result, final Model model, final HttpServletRequest request) throws CMSItemNotFoundException
	{

		if (!validateform(fmregistrationform, model))
		{
			if (fmregistrationform.getCountry() != null && !fmregistrationform.getCountry().isEmpty()) {
				model.addAttribute("countrydata", getI18NFacade().getCountryForIsocode(fmregistrationform.getCountry()));
				LOG.info("createFMCustomer(...): Country (after validation error): " + getI18NFacade().getCountryForIsocode(fmregistrationform.getCountry()).getName());
			}

			if (fmregistrationform.getState() != null && !fmregistrationform.getState().isEmpty()) {
				model.addAttribute("regionsdatas", getI18NFacade().getRegion(
						(getI18NFacade().getCountryForIsocode(fmregistrationform.getCountry())).getIsocode(), 
						fmregistrationform.getState()));
				LOG.info("createFMCustomer(...): State/Province (after validation error): " + getI18NFacade().getRegion(
						(getI18NFacade().getCountryForIsocode(fmregistrationform.getCountry())).getIsocode(), 
						fmregistrationform.getState()).getName());
			}

			final Map<String, String> shopBayMap = prepareshopbayMap();
			final Map<String, String> mostInterstedMap = prepareMostInterstedMap();
			final Map<String, String> brandsMap = preparebrandMap();

			model.addAttribute("fmusertype", Fmusertype.values());

			final List<CountryData> countries = new ArrayList<CountryData>();
			countries.add(checkoutFacade.getCountryForIsocode("US"));
			countries.add(checkoutFacade.getCountryForIsocode("CA"));
			model.addAttribute("countryData", countries);
			model.addAttribute("regionsdatas", getI18NFacade().getRegionsForAllCountries().values());
			model.addAttribute("fmdata", fmregistrationform);

			storeCmsPageInModel(model, getContentPageForLabelOrId(FM_ACCOUNT_CMS_PAGE));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FM_ACCOUNT_CMS_PAGE));
			return ControllerConstants.Views.Pages.Account.SignUpPage;
		}

		try
		{
			fmCustomerFacade.createCustomerAccount(createCustomerData(fmregistrationform));
			GlobalMessages.addInfoMessage(model, "You have successfully registered for a Federal-Mogul Motorparts account.");
		}
		catch (Throwable t)
		{
			t.printStackTrace();

			if (t.getMessage() != null) {
				LOG.info("Inside Catch block, controller " + t.getMessage());
				GlobalMessages.addErrorMessage(model, t.getMessage());
			}
			else {
				GlobalMessages.addErrorMessage(model, "External service error, please try later.");
			}

		}

		model.addAttribute("fmusertype", Fmusertype.values());
		if (GlobalMessages.hasErrorMessage(model))
		{
			model.addAttribute("fmdata", fmregistrationform);
		}
		else
		{
			model.addAttribute("fmdata", new FMRegistrationForm());
		}
		final List<CountryData> countries = new ArrayList<CountryData>();
		countries.add(checkoutFacade.getCountryForIsocode("US"));
		countries.add(checkoutFacade.getCountryForIsocode("CA"));
		model.addAttribute("countryData", countries);
		final Map<String, String> aboutShopMap = prepareaboutShopMap();
		final Map<String, String> shopTypeMap = prepareshopTypeMap();
		final Map<String, String> shopBayMap = prepareshopbayMap();
		final Map<String, String> mostInterstedMap = prepareMostInterstedMap();
		final Map<String, String> brandsMap = preparebrandMap();

		model.addAttribute("aboutShop", aboutShopMap);

		model.addAttribute("shopType", shopTypeMap);
		model.addAttribute("shopBays", shopBayMap);
		model.addAttribute("mostIntersted", mostInterstedMap);
		model.addAttribute("brands", brandsMap);


		model.addAttribute("regionsdatas", getI18NFacade().getRegionsForAllCountries().values());
		storeCmsPageInModel(model, getContentPageForLabelOrId(FM_ACCOUNT_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FM_ACCOUNT_CMS_PAGE));
		return ControllerConstants.Views.Pages.Account.SignUpPage;

	}

	protected void commonValidation(final FMRegistrationForm fmregistrationform, final Model model) {
		// Validate user type...
		String invalidUserTypeMsg = "Invalid user type.";
		try {
			Fmusertype userType = fmregistrationform.getUsertypedesc();
			if (userType == null) {
				GlobalMessages.addErrorMessage(model, invalidUserTypeMsg);
			}
		} catch (Throwable t) {
			LOG.warn("Unexpected exception encountered while validating Fmusertype, will return an error message.");
			GlobalMessages.addErrorMessage(model, invalidUserTypeMsg);
		}

		// Validate email...
		String email = fmregistrationform.getEmail();
		EmailValidator validator = EmailValidator.getInstance();
		if (!validator.isValid(email)) {
			GlobalMessages.addErrorMessage(model, "Please enter a valid email.");
		} else {
			email = email.trim();
			final boolean checkUid = fmCustomerFacade.checkUidExists(email);
			if (fmCustomerFacade.checkUidExists(email)) {
				GlobalMessages.addErrorMessage(model, "An account with this email already exists, please register with a different email address.");
			}
		}

		// Validate confirm email...
		String confirmEmailMsg = "Please enter a confirmation email that matches your email.";
		String confirmEmail = fmregistrationform.getConfirmEmail();
		if (StringUtils.isBlank(confirmEmail)) {
			GlobalMessages.addErrorMessage(model, confirmEmailMsg);
		}

		if (confirmEmail != null && validator.isValid(email)) {
			if (!confirmEmail.trim().equalsIgnoreCase(email)) {
				GlobalMessages.addErrorMessage(model, confirmEmailMsg);
			}
		}

		// Validate password...
		String password = fmregistrationform.getPassword();
		if (StringUtils.isBlank(password)) {
			GlobalMessages.addErrorMessage(model, "Please enter a password.");
		} else {
			password = password.trim();
			if (!PASSWORD_PATTERN.matcher(password).matches()) {
				GlobalMessages.addErrorMessage(model, "text.Password.Validation.Error");
			}
		}

		// Validate confirm password...
		String confirmPassword = fmregistrationform.getConfpwd();
		if (StringUtils.isBlank(confirmPassword)) {
			GlobalMessages.addErrorMessage(model, "Please enter a confirm password.");
		}

		if (password != null && confirmPassword != null) {
			if (!password.equals(confirmPassword.trim())) {
				GlobalMessages.addErrorMessage(model, "The password and confirm password do not match.");
			}
		}

		// Validate first name...
		String firstName = fmregistrationform.getFirstName();
		if (StringUtils.isBlank(firstName)) {
			GlobalMessages.addErrorMessage(model, "Please enter a first name.");
		}

		// Validate last name...
		String lastName = fmregistrationform.getLastName();
		if (StringUtils.isBlank(lastName)) {
			GlobalMessages.addErrorMessage(model, "Please enter a last name.");
		}
	}

	protected void accountNumberValidation(final FMRegistrationForm fmregistrationform, final Model model) {
		String accCode = fmregistrationform.getAccCode();
		String email = fmregistrationform.getEmail() != null ? fmregistrationform.getEmail().trim().toLowerCase() : "";

		if (StringUtils.isBlank(accCode) && !email.endsWith("@federalmogul.com")) {
			GlobalMessages.addErrorMessage(model, "Please enter an account code.");
		} else {
			accCode = email.endsWith("@federalmogul.com") ? Config.getParameter("federalmogulaccountCode_Internalusers") : accCode;

			if (accCode.length() != 10) {
				int accCount = 0;
				final String codes = Config.getParameter(WebConstants.B2T_REGISTRATION_ACCOUNTS);
				if (codes != null) {
					final String[] accCodes = codes.split(",");
					for (int i = 0; i < accCodes.length; i++) {
						if (accCode.contains(accCodes[i])) {
							accCount++;
						}
					}
				}

				if (accCount == 0) {
					final int accCodeSize = 10 - accCode.length();
					for (int i = 0; i < accCodeSize; i++) {
						accCode = "0" + accCode;
					}
				}
			}

			final FMCustomerAccountModel unitB2B = (FMCustomerAccountModel) companyB2BCommerceService.getUnitForUid(accCode);
			if (unitB2B == null) {
				GlobalMessages.addErrorMessage(model, "Account code was not found, please enter a valid account code.");
			}

			fmregistrationform.setAccCode(accCode);
		}
	}

	protected void referralValidation(final FMRegistrationForm fmregistrationform, final Model model) {
		if (fmregistrationform.getRefered()) {
			String email = fmregistrationform.getReferredBy();
			EmailValidator validator = EmailValidator.getInstance();
			if (!validator.isValid(email)) {
				GlobalMessages.addErrorMessage(model, "Please enter a valid email for the person who referred you.");
			}
		}
	}

	protected void addressLineValidation(final FMRegistrationForm fmregistrationform, final Model model) {
		// Validate Address Line1...
		String addressLine1 = fmregistrationform.getAddressline1();
		if (StringUtils.isBlank(addressLine1)) {
			GlobalMessages.addErrorMessage(model, "Please enter an address.");
		}
	}

	protected void cityValidation(final FMRegistrationForm fmregistrationform, final Model model) {
		// Validate City...
		String city = fmregistrationform.getCity();
		if (StringUtils.isBlank(city)) {
			GlobalMessages.addErrorMessage(model, "Please enter a city.");
		}
	}

	protected void countryValidation(final FMRegistrationForm fmregistrationform, final Model model) {
		String country = fmregistrationform.getCountry();
		if (StringUtils.isBlank(country) || "default".equalsIgnoreCase(country)) {
			GlobalMessages.addErrorMessage(model, "Please select a country.");
		}
	}

	protected void postalCodeValidation(final FMRegistrationForm fmregistrationform, final Model model) {
		String zipCode = fmregistrationform.getZipCode();
		if (StringUtils.isBlank(zipCode)) {
			GlobalMessages.addErrorMessage(model, "Please enter a zip code.");
		} else {
			String country = fmregistrationform.getCountry();
			if (StringUtils.isNotBlank(country)) {
				if (country.toLowerCase().equals("us")) {
					if (!US_POSTAL_CODE_PATTERN.matcher(zipCode).matches()) {
						GlobalMessages.addErrorMessage(model, "The zip code format is incorrect.");
					}
				} else if (country.toLowerCase().equals("ca")) {
					if (!CA_POSTAL_CODE_PATTERN.matcher(zipCode).matches()) {
						GlobalMessages.addErrorMessage(model, "The postal code format is incorrect.");
					}
				}
			}
		}
	}

	protected void employeeIdValidation(final FMRegistrationForm fmregistrationform, final Model model) {
		String employeeId = fmregistrationform.getEmployeeId();

		if (StringUtils.isBlank(employeeId)) {
			GlobalMessages.addErrorMessage(model, "Please enter an employee ID.");
		} else {

			final FMCustomerAccountModel unitB2B = (FMCustomerAccountModel) companyB2BCommerceService.getUnitForUid(employeeId);
			if (unitB2B == null) {
				GlobalMessages.addErrorMessage(model, "Employee ID was not found, please enter a valid employee ID.");
			}
		}
	}

	/**
	 * method for validating the form details
	 * 
	 * @param fmregistrationform
	 * @return boolean
	 */
	private boolean validateform(final FMRegistrationForm fmregistrationform, final Model model)
	{
		commonValidation(fmregistrationform, model);

		Fmusertype userType = fmregistrationform.getUsertypedesc();
		switch (userType) {
			case WAREHOUSEDISTRIBUTORLIGHTVEHICLE:
			case WAREHOUSEDISTRIBUTORCOMMERCIALVEHICLE:
			case RETAILER:
			case JOBBERPARTSTORE:
				accountNumberValidation(fmregistrationform, model);
				break;
			case SHOPOWNERTECHNICIAN:
			case REPAIRSHOPOWNER:
			case TECHNICIAN:
			case STUDENT:
				referralValidation(fmregistrationform, model);
				addressLineValidation(fmregistrationform, model);
				cityValidation(fmregistrationform, model);
				countryValidation(fmregistrationform, model);
				postalCodeValidation(fmregistrationform, model);
				break;
			case CONSUMER:
				countryValidation(fmregistrationform, model);
				postalCodeValidation(fmregistrationform, model);
				break;
			case SALESREP:
				employeeIdValidation(fmregistrationform, model);
				break;
			default:
				break;
		}

		return !GlobalMessages.hasErrorMessage(model);
	}

	protected void setCommonFields(final FMRegistrationForm fmRegistrationForm, final FMCustomerData fmCustomerData) {
		String email = fmRegistrationForm.getEmail().trim();
		fmCustomerData.setUid(email);
		fmCustomerData.setEmail(email);
		fmCustomerData.setPassword(fmRegistrationForm.getPassword().trim());
		fmCustomerData.setFirstName(fmRegistrationForm.getFirstName().trim());
		fmCustomerData.setLastName(fmRegistrationForm.getLastName().trim());
		fmCustomerData.setUserTypeDescription(fmRegistrationForm.getUsertypedesc());
		fmCustomerData.setPromoCode(StringUtils.trim(fmRegistrationForm.getPromoCode()));

		// Defaults to subscriptions...
		fmCustomerData.setNewsLetterSubscription(false);
		fmCustomerData.setNewProductAlerts(false);
		fmCustomerData.setPromotionInfoSubscription(false);
		fmCustomerData.setTechAcademySubscription(false);
		fmCustomerData.setLoyaltySignup(false);
	}

	protected void setAccountNumber(final FMRegistrationForm fmRegistrationForm, final FMCustomerData fmCustomerData) {
		String accCode = fmRegistrationForm.getAccCode().trim();
		fmCustomerData.setUnit(companyB2BCommerceFacade.getUnitForUid(accCode));

		String role = fmRegistrationForm.getFmRole();
		if (StringUtils.isBlank(role)) {
			final String codes = Config.getParameter(WebConstants.B2T_REGISTRATION_ACCOUNTS);
			if (codes != null) {
				final String[] acccodes = codes.split(",");
				for (int i = 0; i < acccodes.length; i++) {
					if (accCode.contains(acccodes[i])) {
						final String myRole = "FMBUVOR";
						final Collection<String> fmRoles = new ArrayList<String>();
						fmRoles.add(myRole);
						fmCustomerData.setRoles(fmRoles);
						break;
					}
				}
			}

		} else {
			final Collection<String> fmRoles = new ArrayList<String>();
			fmRoles.add(role.trim());
			fmCustomerData.setRoles(fmRoles);
		}
	}

	protected void setEmployeeID(final FMRegistrationForm fmRegistrationForm, final FMCustomerData fmCustomerData) {
		String employeeId = fmRegistrationForm.getEmployeeId().trim();
		fmCustomerData.setUnit(companyB2BCommerceFacade.getUnitForUid(employeeId));
		if (fmRegistrationForm.getFmRole() == null) {
			final String myRole = "FMCSR";
			final Collection<String> fmRoles = new ArrayList<String>();
			fmRoles.add(myRole);
			fmCustomerData.setRoles(fmRoles);
		}
	}

	protected void setAddress(final FMRegistrationForm fmRegistrationForm, final FMCustomerData fmCustomerData) {
		final AddressData address = new AddressData();

		address.setLine1(fmRegistrationForm.getAddressline1().trim());
		address.setLine2(fmRegistrationForm.getAddressline2() != null ? fmRegistrationForm.getAddressline2().trim() : null);
		address.setTown(fmRegistrationForm.getCity().trim());
		address.setPostalCode(fmRegistrationForm.getZipCode().trim());

		final CountryData country = new CountryData();
		country.setIsocode(fmRegistrationForm.getCountry().trim());
		address.setCountry(country);

		LOG.info("setAddress(...): fmRegistrationForm.getState(): " + fmRegistrationForm.getState());
		RegionData regionData = new RegionData();
		regionData.setIsocodeShort(fmRegistrationForm.getState());
		regionData.setIsocode(fmRegistrationForm.getState());
		address.setRegion(regionData);

		fmCustomerData.setDefaultShippingAddress(address);
	}

	protected void setInitialLocation(final FMRegistrationForm fmRegistrationForm, final FMCustomerData fmCustomerData) {
		final AddressData address = new AddressData();

		address.setPostalCode(fmRegistrationForm.getZipCode().trim());

		final CountryData country = new CountryData();
		country.setIsocode(fmRegistrationForm.getCountry().trim());
		address.setCountry(country);

		fmCustomerData.setDefaultShippingAddress(address);
	}

	protected void setGarageRewards(final FMRegistrationForm fmRegistrationForm, final FMCustomerData fmCustomerData) {
		// Note, there are no brands, most interested, shop types, bays, banners, etc. specified at registration time so these are not currently sent...
		fmCustomerData.setLoyaltySignup(fmRegistrationForm.getIsGarageRewardMember());
		fmCustomerData.setIsGarageRewardMember(fmRegistrationForm.getIsGarageRewardMember());

		final FMB2bAddressData address = new FMB2bAddressData();
		address.setPostalCode(fmRegistrationForm.getZipCode().trim());

		final CountryData country = new CountryData();
		country.setIsocode(fmRegistrationForm.getCountry().trim());
		address.setCountry(country);

		fmCustomerData.setB2baddress(address);
	}

	protected void setReferral(final FMRegistrationForm fmRegistrationForm, final FMCustomerData fmCustomerData) {
		if (fmRegistrationForm.getRefered()) {
			String referredBy = fmRegistrationForm.getReferredBy();
			if (StringUtils.isNotBlank(referredBy)) {
				fmCustomerData.setReferEmailId(referredBy.trim());
			}
		}
	}

	protected void setEmailOptIn(final FMRegistrationForm fmRegistrationForm, final FMCustomerData fmCustomerData) {
		fmCustomerData.setTechAcademySubscription(fmRegistrationForm.getTechAcademySubscription());
		fmCustomerData.setNewProductAlerts(fmRegistrationForm.getTechAcademySubscription());
		fmCustomerData.setPromotionInfoSubscription(fmRegistrationForm.getTechAcademySubscription());
	}

	protected FMCustomerData createCustomerData(final FMRegistrationForm fmRegistrationForm) {

		final FMCustomerData fmCustomerData = new FMCustomerData();
		setCommonFields(fmRegistrationForm, fmCustomerData);

		Fmusertype userType = fmRegistrationForm.getUsertypedesc();
		switch (userType) {
			case WAREHOUSEDISTRIBUTORLIGHTVEHICLE:
			case WAREHOUSEDISTRIBUTORCOMMERCIALVEHICLE:
			case RETAILER:
			case JOBBERPARTSTORE:
				setAccountNumber(fmRegistrationForm, fmCustomerData);
				setEmailOptIn(fmRegistrationForm, fmCustomerData);
				fmCustomerData.setIsLoginDisabled(true);
				break;
			case SHOPOWNERTECHNICIAN:
			case REPAIRSHOPOWNER:
			case TECHNICIAN:
			case STUDENT:
				setReferral(fmRegistrationForm, fmCustomerData);
				setAddress(fmRegistrationForm, fmCustomerData);
				setEmailOptIn(fmRegistrationForm, fmCustomerData);
				setGarageRewards(fmRegistrationForm, fmCustomerData);
				fmCustomerData.setIsLoginDisabled(false);
				break;
			case CONSUMER:
				setEmailOptIn(fmRegistrationForm, fmCustomerData);
				setInitialLocation(fmRegistrationForm, fmCustomerData);
				fmCustomerData.setIsLoginDisabled(false);
				fmCustomerData.setUnit(companyB2BCommerceFacade.getUnitForUid(DUMMY_B2B_UNIT_FOR_CONSUMER));
				break;
			case SALESREP:
				setEmployeeID(fmRegistrationForm, fmCustomerData);
				fmCustomerData.setIsLoginDisabled(false);
				break;
			default:
				break;
		}

		return fmCustomerData;
	}

	/**
	 * user defined method for populating B2B customer data
	 * 
	 * @param fmregistrationform
	 * @return FMCustomerData
	 * @throws IOException
	 */

	private FMCustomerData populateB2BCustomerData(final FMRegistrationForm fmregistrationform) throws IOException
	{
		//sreedevi  - changes for uid
		final boolean checkUid = fmCustomerFacade.checkUidExists(fmregistrationform.getEmail().trim());

		LOG.info("Result from DAO " + checkUid);
		if (!checkUid)
		{
			LOG.info("FROM FORM " + fmregistrationform.getAddressline1() + " " + fmregistrationform.getAddressline2() + " "
					+ fmregistrationform.getCity() + " " + fmregistrationform.getPhoneno());
			final FMCustomerData fmcustomerdata = new FMCustomerData();
			fmcustomerdata.setUid(fmregistrationform.getEmail().trim());
			fmcustomerdata.setEmail(fmregistrationform.getEmail().trim());
			fmcustomerdata.setUserTypeDescription(fmregistrationform.getUsertypedesc());
			LOG.info("USERTYPE in data" + fmcustomerdata.getUserTypeDescription());
			LOG.info("acc code" + fmregistrationform.getAccCode());
			if (fmregistrationform.getAccCode() != null && !fmregistrationform.getAccCode().isEmpty())
			{

				String accnumber = fmregistrationform.getAccCode().trim();
				if (accnumber.length() != 10)
				{
					int acccount = 0;
					final String codes = Config.getParameter(WebConstants.B2T_REGISTRATION_ACCOUNTS);
					final String[] acccodes = codes.split(",");
					for (int i = 0; i < acccodes.length; i++)
					{

						if (accnumber.contains(acccodes[i]))
						{
							acccount++;
						}
					}
					if (acccount == 0)
					{
						final int accnumberSize = 10 - accnumber.length();
						for (int i = 0; i < accnumberSize; i++)
						{
							accnumber = "0" + accnumber;
							LOG.info("inside getfmunits of b2bcreate::::::::::::::::::::" + accnumber);
						}

					}
				}

				fmcustomerdata.setUnit(companyB2BCommerceFacade.getUnitForUid(accnumber));
			}
			else if (fmregistrationform.getAccCode().isEmpty() && fmregistrationform.getEmail().contains("@federalmogul.com"))
			{
				fmcustomerdata.setUnit(companyB2BCommerceFacade.getUnitForUid(Config
						.getParameter("federalmogulaccountCode_Internalusers")));
				LOG.info("UNIT IN CONTROLLER AFTER SETTING " + fmcustomerdata.getUnit().getUid());
			}
			fmcustomerdata.setPassword(fmregistrationform.getPassword().trim());
			fmcustomerdata.setFirstName(fmregistrationform.getFirstName().trim());
			fmcustomerdata.setLastName(fmregistrationform.getLastName().trim());
			if (null == fmregistrationform.getFmRole())
			{

				/*
				 * if ((fmregistrationform.getEmail() != null && fmregistrationform.getEmail().contains("federalmogul.com")
				 * && fmregistrationform.getAccCode() != null && fmregistrationform.getAccCode().contains("SUS")))
				 */
				if (fmregistrationform.getAccCode() != null)
				{
					final String codes = Config.getParameter(WebConstants.B2T_REGISTRATION_ACCOUNTS);
					final String[] acccodes = codes.split(",");
					for (int i = 0; i < acccodes.length; i++)
					{

						if (fmregistrationform.getAccCode().contains(acccodes[i]))
						{
							final String myRole = "FMBUVOR";
							final Collection<String> fmRoles = new ArrayList<String>();
							fmRoles.add(myRole);
							fmcustomerdata.setRoles(fmRoles);
							break;
						}
					}

				}
				else if (fmregistrationform.getEmail() != null && fmregistrationform.getEmail().contains("@federalmogul.com")
						&& fmregistrationform.getAccCode() == null)
				{
					final String myRole = "FMCSR";
					final Collection<String> fmRoles = new ArrayList<String>();
					fmRoles.add(myRole);
					fmcustomerdata.setRoles(fmRoles);
				}
			}

			else
			{
				final String myRole = (fmregistrationform.getFmRole()).trim();
				final Collection<String> fmRoles = new ArrayList<String>();
				fmRoles.add(myRole);
				fmcustomerdata.setRoles(fmRoles);
			}
			final AddressData customeraddress = new AddressData();
			customeraddress.setLine1(fmregistrationform.getAddressline1().trim());
			if (fmregistrationform.getAddressline2() != null)
			{
				customeraddress.setLine2(fmregistrationform.getAddressline2().trim());
			}
			else
			{
				customeraddress.setLine2(fmregistrationform.getAddressline2());
			}

			customeraddress.setTown(fmregistrationform.getCity().trim());
			LOG.info("FORM FORM CITY" + fmregistrationform.getCity().trim());
			LOG.info("FROM DATA city" + customeraddress.getTown().trim());
			final RegionData state = new RegionData();
			state.setIsocodeShort(fmregistrationform.getState().trim());
			state.setIsocode(fmregistrationform.getState().trim());
			LOG.info("iso" + state.getIsocode());
			customeraddress.setRegion(state);
			customeraddress.setPostalCode(fmregistrationform.getZipCode().trim());
			final CountryData country = new CountryData();
			country.setIsocode(fmregistrationform.getCountry().trim());
			LOG.info("FROM FORM country" + fmregistrationform.getCountry().trim());
			LOG.info("FROM DATA country" + country.getIsocode());
			customeraddress.setCountry(country);
			customeraddress.setPhone(fmregistrationform.getPhoneno().trim().replaceAll("[\\-\\s\\(\\)\\.]", ""));
			customeraddress.setVisibleInAddressBook(true);
			fmcustomerdata.setDefaultShippingAddress(customeraddress);
			fmcustomerdata.setNewsLetterSubscription(fmregistrationform.getNewsLetterSubscription());
			fmcustomerdata.setNewProductAlerts(false);
			fmcustomerdata.setPromotionInfoSubscription(false);
			fmcustomerdata.setTechAcademySubscription(false);
			fmcustomerdata.setLoyaltySignup(false);
			fmcustomerdata.setIsLoginDisabled(true);
			return fmcustomerdata;
		}
		else
		{
			throw new IOException("EmailId Already exists!! Register with a different email Id");
		}
	}

	/**
	 * user defined method for populating B2b customer data
	 * 
	 * @param fmregistrationform
	 * @return FMCustomerData
	 * @throws IOException
	 */
	private FMCustomerData populateB2bCustomerData(final FMRegistrationForm fmregistrationform) throws IOException
	{
		//sreedevi  - changes for uid
		final boolean checkUid = fmCustomerFacade.checkUidExists(fmregistrationform.getEmail().trim());
		final List<String> uniqueId = new ArrayList<String>();

		LOG.info("Result from DAO " + checkUid);
		if (!checkUid)
		{

			final MultipartFile file = fmregistrationform.getTaxDocument();
			final FMCustomerData fmcustomerdata = new FMCustomerData();
			LOG.info(fmregistrationform.getAddressline1());
			fmcustomerdata.setUid(fmregistrationform.getEmail().trim());
			fmcustomerdata.setUserTypeDescription(fmregistrationform.getUsertypedesc());
			final List<FMTaxDocumentData> fmTaxDocumentDataList = new ArrayList<FMTaxDocumentData>();

			final FMTaxDocumentData fmTaxDocumentData = fmCustomerFacade.convertFileToFMTaxDocumentModel(file,
					WebConstants.CUST_UPLOAD_DOCS_FILE_PATH, WebConstants.DD_MM_YYYY);//convertFileToFMTaxDocumentModel(file);
			LOG.info("fmTaxDocumentModel " + fmTaxDocumentData);

			if (fmTaxDocumentData != null)
			{
				LOG.info("FMTaxDocumentData created :: " + fmTaxDocumentData.getRealFileName());
				final RegionData fmTaxDocumentState = new RegionData();
				fmTaxDocumentState.setIsocode(fmregistrationform.getCompanystate().trim());

				fmTaxDocumentData.setValidate(FmTaxValidationType.NOTREVIEWED);
				fmTaxDocumentData.setState(fmTaxDocumentState);
				fmTaxDocumentData.setUploadedBy(fmregistrationform.getFirstName().trim());
				fmTaxDocumentDataList.add(fmTaxDocumentData);
				fmcustomerdata.setTaxDocumentList(fmTaxDocumentDataList);
			}

			fmcustomerdata.setTaxID(fmregistrationform.getTaxID().trim());
			//fmcustomerdata.setLoyaltySignup(fmregistrationform.getLoyaltySignup());
			//we need to update properly once the update profile development is completed
			fmcustomerdata.setLoyaltySignup(fmregistrationform.getIsGarageRewardMember());
			//	if (fmregistrationform.getLoyaltySignup() || fmregistrationform.getIsGarageRewardMember())
			//	{
			fmcustomerdata.setLmsSigninId(fmregistrationform.getLmsSigniId());
			//	}

			//Loyality Update QandA
			if (null != fmregistrationform.getBrands())
			{
				String brands = null;
				for (final String brand : fmregistrationform.getBrands())
				{
					System.err.println("brand ::" + brand);
					if (null != brands)
					{
						brands += brand + ";";
					}
					else
					{
						brands = brand + ";";
					}

					if (!uniqueId.contains(brand))
					{

						uniqueId.add(brand);
					}
				}
				fmcustomerdata.setBrands(brands);
			}
			if (null != fmregistrationform.getMostIntersted())
			{
				String mostIntersted = null;
				for (final String mostInterset : fmregistrationform.getMostIntersted())
				{
					System.err.println("mostInterset  ::" + mostInterset);
					if (null != mostIntersted)
					{
						mostIntersted += mostInterset + ";";
					}
					else
					{
						mostIntersted = mostInterset + ";";
					}

					if (!uniqueId.contains(mostInterset))
					{

						uniqueId.add(mostInterset);
					}
				}
				fmcustomerdata.setMostIntersted(mostIntersted);
			}
			fmcustomerdata.setIsGarageRewardMember(fmregistrationform.getIsGarageRewardMember());
			fmcustomerdata.setShopType(fmregistrationform.getShopType());
			if (null != fmregistrationform.getTechType() && fmregistrationform.getTechType().equalsIgnoreCase("RepairShopOwner"))
			{

				fmcustomerdata.setTechType("RepairShopOwner");
			}
			if (fmregistrationform.getShopType() != null)
			{


				if (!uniqueId.contains(fmregistrationform.getShopType()))
				{
					System.err.println("typeId ::" + fmregistrationform.getShopType());
					uniqueId.add(fmregistrationform.getShopType());
				}
			}
			fmcustomerdata.setAboutShop(fmregistrationform.getAboutShop());
			if (fmregistrationform.getAboutShop() != null)
			{
				if (!uniqueId.contains(fmregistrationform.getAboutShop()))
				{
					uniqueId.add(fmregistrationform.getAboutShop());

				}
			}
			fmcustomerdata.setShopBays(fmregistrationform.getShopBays());
			if (fmregistrationform.getShopBays() != null)
			{
				if (!uniqueId.contains(fmregistrationform.getShopBays()))
				{

					uniqueId.add(fmregistrationform.getShopBays());
				}
			}
			fmcustomerdata.setShopBanner(fmregistrationform.getShopBanner());
			if (fmregistrationform.getShopBanner() != null && !fmregistrationform.getShopBanner().isEmpty())
			{
				uniqueId.add(Config.getParameter("bannerId") + ":" + fmregistrationform.getShopBanner());
			}

			if (uniqueId.size() > 0 && !uniqueId.contains("Select"))
			{
				fmcustomerdata.setUniqueID(uniqueId);
			}


			fmcustomerdata.setTechAcademySubscription(fmregistrationform.getTechAcademySubscription());
			fmcustomerdata.setNewProductAlerts(fmregistrationform.getTechAcademySubscription() ? true : false);
			fmcustomerdata.setPromotionInfoSubscription(fmregistrationform.getTechAcademySubscription() ? true : false);
			fmcustomerdata.setEmail(fmregistrationform.getEmail().trim());
			LOG.info("USERTYPE in data" + fmcustomerdata.getUserTypeDescription());
			fmcustomerdata.setPassword(fmregistrationform.getPassword().trim());
			fmcustomerdata.setFirstName(fmregistrationform.getFirstName().trim());
			fmcustomerdata.setLastName(fmregistrationform.getLastName().trim());

			final AddressData customeraddress = new AddressData();
			customeraddress.setFirstName(fmregistrationform.getFirstName().trim());
			customeraddress.setLastName(fmregistrationform.getLastName().trim());
			customeraddress.setLine1(fmregistrationform.getAddressline1().trim());
			if (fmregistrationform.getAddressline2() != null)
			{
				customeraddress.setLine2(fmregistrationform.getAddressline2().trim());
			}
			else
			{
				customeraddress.setLine2(fmregistrationform.getAddressline2());
			}

			customeraddress.setTown(fmregistrationform.getCity().trim());
			customeraddress.setVisibleInAddressBook(true);
			final RegionData state = new RegionData();
			state.setIsocodeShort(fmregistrationform.getState().trim());
			state.setIsocode(fmregistrationform.getState().trim());
			LOG.info("state" + state);
			LOG.info("iso" + state.getIsocode());
			customeraddress.setRegion(state);
			customeraddress.setPostalCode(fmregistrationform.getZipCode().trim());
			final CountryData country = new CountryData();
			country.setIsocode(fmregistrationform.getCountry().trim());
			LOG.info("COUNTRY ISO" + country.getIsocode());
			customeraddress.setCountry(country);
			customeraddress.setPhone(fmregistrationform.getPhoneno().trim());
			//fmcustomerdata.setDefaultShippingAddress(customeraddress);
			final FMB2bAddressData unitaddress = new FMB2bAddressData();
			unitaddress.setCompanyName(fmregistrationform.getCompanyName().trim());
			unitaddress.setLine1(fmregistrationform.getCompanyaddressline1().trim());
			if (fmregistrationform.getCompanyaddressline2() != null)
			{
				unitaddress.setLine2(fmregistrationform.getCompanyaddressline2().trim());
			}
			else
			{
				unitaddress.setLine2(fmregistrationform.getCompanyaddressline2());
			}

			unitaddress.setTown(fmregistrationform.getCompanycity().trim());
			final RegionData unitstate = new RegionData();
			unitstate.setIsocodeShort(fmregistrationform.getCompanystate().trim());
			unitstate.setIsocode(fmregistrationform.getCompanystate().trim());
			LOG.info("state" + unitstate);
			LOG.info("iso" + unitstate.getIsocode());
			unitaddress.setRegion(unitstate);
			unitaddress.setPostalCode(fmregistrationform.getCompanyzipCode().trim());
			final CountryData unitcountry = new CountryData();
			unitcountry.setIsocode(fmregistrationform.getCompanycountry().trim());
			LOG.info("company country code" + unitcountry.getIsocode());
			unitaddress.setCountry(unitcountry);
			unitaddress.setPhone(fmregistrationform.getCompanyPhoneNumber().trim().replaceAll("[\\-\\s\\(\\)\\.]", ""));
			if (fmregistrationform.getCompanyFax() != null)
			{
				unitaddress.setCompanyFax(fmregistrationform.getCompanyFax().trim());
			}
			else
			{
				unitaddress.setCompanyFax(fmregistrationform.getCompanyFax());
			}

			//unitaddress.setCompanyUrl(fmregistrationform.getCompanyWebsite());
			fmcustomerdata.setB2baddress(unitaddress);
			fmcustomerdata.setNewsLetterSubscription(fmregistrationform.getNewsLetterSubscription());
			fmcustomerdata.setIsLoginDisabled(false);
			//fmcustomerdata.setAssociation(fmregistrationform.getAssociation());

			if (fmregistrationform.getIsGarageRewardMember())
			{
				fmcustomerdata.setDefaultShippingAddress(customeraddress);
			}
			else
			{
				fmcustomerdata.setDefaultShippingAddress(unitaddress);
			}

			if (fmregistrationform.getRefered())
			{
				if (fmregistrationform.getReferredBy() != null)
				{
					fmcustomerdata.setReferEmailId(fmregistrationform.getReferredBy());
				}
			}
			if (!fmregistrationform.getPromoCode().isEmpty())
			{
				fmcustomerdata.setPromoCode(fmregistrationform.getPromoCode().trim());
			}
			return fmcustomerdata;
		}
		else
		{
			throw new IOException("EmailId Already exists!! Register with a different email Id");
		}
	}

	/**
	 * user defined method for populating B2C customer data
	 * 
	 * @param fmregistrationform
	 * @return FMCustomerData
	 * @throws IOException
	 */
	private FMCustomerData populateB2CCustomerData(final FMRegistrationForm fmregistrationform) throws IOException
	{
		//sreedevi  - changes for uid
		final boolean checkUid = fmCustomerFacade.checkUidExists(fmregistrationform.getEmail().trim());

		LOG.info("Result from DAO " + checkUid);
		if (!checkUid)
		{
			final FMCustomerData fmcustomerdata = new FMCustomerData();
			LOG.info("USERTYPE global account " + Config.getParameter("b2CCustGlobalAccount"));
			fmcustomerdata.setUnit(companyB2BCommerceFacade.getUnitForUid(Config.getParameter("b2CCustGlobalAccount")));
			fmcustomerdata.setUserTypeDescription(fmregistrationform.getUsertypedesc());
			LOG.info("USERTYPE in data" + fmcustomerdata.getUserTypeDescription());

			fmcustomerdata.setUid((fmregistrationform.getEmail()).trim());
			fmcustomerdata.setEmail((fmregistrationform.getEmail()).trim());
			fmcustomerdata.setFirstName((fmregistrationform.getFirstName()).trim());
			fmcustomerdata.setLastName((fmregistrationform.getLastName()).trim());


			final AddressData b2ccustomeraddressdata = new AddressData();
			b2ccustomeraddressdata.setFirstName(fmregistrationform.getFirstName().trim());
			b2ccustomeraddressdata.setLastName(fmregistrationform.getLastName().trim());
			b2ccustomeraddressdata.setLine1((fmregistrationform.getAddressline1()).trim());
			if (fmregistrationform.getAddressline2() != null)
			{
				b2ccustomeraddressdata.setLine2((fmregistrationform.getAddressline2()).trim());
			}
			else
			{
				b2ccustomeraddressdata.setLine2(fmregistrationform.getAddressline2());
			}

			LOG.info("address from form" + fmregistrationform.getAddressline1().trim());
			LOG.info("address after setting in address data " + b2ccustomeraddressdata.getLine1()
					+ b2ccustomeraddressdata.getLine2());
			final CountryData country = new CountryData();
			country.setIsocode(fmregistrationform.getCountry().trim());
			LOG.info("COUNTRY IN CONTROLLER" + fmregistrationform.getCountry().trim());
			LOG.info("COUNTRY IN CONTROLLER" + country.getIsocode());
			b2ccustomeraddressdata.setCountry(country);
			b2ccustomeraddressdata.setPhone(fmregistrationform.getPhoneno().trim().replaceAll("[\\-\\s\\(\\)\\.]", ""));
			final RegionData state = new RegionData();
			state.setIsocodeShort(fmregistrationform.getState().trim());
			state.setIsocode(fmregistrationform.getState().trim());
			LOG.info("state" + state);
			LOG.info("iso" + state.getIsocode());
			b2ccustomeraddressdata.setRegion(state);
			b2ccustomeraddressdata.setPostalCode(fmregistrationform.getZipCode().trim());
			//b2ccustomeraddressdata.setRegion(null);
			b2ccustomeraddressdata.setTown(fmregistrationform.getCity().trim());
			b2ccustomeraddressdata.setVisibleInAddressBook(true);
			LOG.info("country b4 setting in controller" + b2ccustomeraddressdata.getCountry().getIsocode());
			fmcustomerdata.setDefaultShippingAddress(b2ccustomeraddressdata);
			LOG.info("line2 after setting in controller" + fmcustomerdata.getDefaultShippingAddress().getLine2());
			LOG.info("country after setting in controller" + fmcustomerdata.getDefaultShippingAddress().getCountry().getIsocode());
			fmcustomerdata.setPassword(fmregistrationform.getPassword().trim());
			//	fmcustomerdata.setSecretQuestion(fmregistrationform.getSecretQuestion());
			//	fmcustomerdata.setSecretAnswer(fmregistrationform.getSecretAnswer());
			fmcustomerdata.setNewsLetterSubscription(fmregistrationform.getNewsLetterSubscription());
			fmcustomerdata.setNewProductAlerts(false);
			fmcustomerdata.setPromotionInfoSubscription(false);
			fmcustomerdata.setTechAcademySubscription(fmregistrationform.getTechAcademySubscription());
			fmcustomerdata.setLoyaltySignup(false);
			fmcustomerdata.setIsLoginDisabled(false);
			return fmcustomerdata;
		}
		else
		{
			throw new IOException("EmailId Already exists!! Register with a different email Id");
		}

	}

	private Map<String, String> prepareaboutShopMap()
	{
		final Map<String, String> aboutShop = new LinkedHashMap<String, String>();
		aboutShop.put(Config.getParameter("aboutShopNational"), Config.getParameter("aboutShopNationalValue"));
		aboutShop.put(Config.getParameter("aboutShopRegional"), Config.getParameter("aboutShopRegionalValue"));
		aboutShop.put(Config.getParameter("aboutShopMemberofabanner"), Config.getParameter("aboutShopMemberbannerValue"));
		aboutShop.put(Config.getParameter("aboutShopIndependent"), Config.getParameter("aboutShopIndependentValue"));

		return aboutShop;
	}

	private Map<String, String> prepareshopTypeMap()
	{
		final Map<String, String> shopType = new LinkedHashMap<String, String>();
		shopType.put(Config.getParameter("shopTypeGeneral"), Config.getParameter("shopTypeGeneralValue"));
		shopType.put(Config.getParameter("shopTypeImport"), Config.getParameter("shopTypeImportValue"));
		shopType.put(Config.getParameter("shopTypeRadiator"), Config.getParameter("shopTypeRadiatorValue"));
		shopType.put(Config.getParameter("shopTypeMuffler/brake"), Config.getParameter("shopTypeMuffler/brakevalue"));
		shopType.put(Config.getParameter("shopTypeTransmission"), Config.getParameter("shopTypeTransmissionValue"));
		shopType.put(Config.getParameter("shopTypeFrontend"), Config.getParameter("shopTypeFrontendValue"));
		shopType.put(Config.getParameter("shopTypeTiredealer"), Config.getParameter("shopTypeTiredealerValue"));
		shopType.put(Config.getParameter("shopTypeCollision"), Config.getParameter("shopTypeCollisionValue"));
		shopType.put(Config.getParameter("shopTypeEngine"), Config.getParameter("shopTypeEngineValue"));
		shopType.put(Config.getParameter("shopTypeQuick"), Config.getParameter("shopTypeQuickValue"));
		shopType.put(Config.getParameter("shopTypeOriginal"), Config.getParameter("shopTypeOriginalValue"));

		return shopType;
	}

	private Map<String, String> prepareMostInterstedMap()
	{
		final Map<String, String> mostIntersted = new LinkedHashMap<String, String>();
		mostIntersted.put(Config.getParameter("mostInterestedTechnical"), Config.getParameter("mostInterestedTechnicalValue"));
		mostIntersted.put(Config.getParameter("mostInterestedApparel"), Config.getParameter("mostInterestedApparelValue"));
		mostIntersted.put(Config.getParameter("mostInterestedTools"), Config.getParameter("mostInterestedToolsValue"));
		mostIntersted.put(Config.getParameter("mostInterestedFreetrial"), Config.getParameter("mostInterestedFreetrialValue"));
		mostIntersted.put(Config.getParameter("mostInterestedPromotional"), Config.getParameter("mostInterestedPromotionalValue"));
		mostIntersted.put(Config.getParameter("mostInterestedExclusive"), Config.getParameter("mostInterestedExclusiveValue"));

		return mostIntersted;
	}

	private Map<String, String> preparebrandMap()
	{
		final Map<String, String> brands = new LinkedHashMap<String, String>();
		brands.put(Config.getParameter("brandAbex"), Config.getParameter("brandAbexValue"));
		brands.put(Config.getParameter("brandANCO"), Config.getParameter("brandANCOValue"));
		brands.put(Config.getParameter("brandChampion"), Config.getParameter("brandChampionValue"));
		brands.put(Config.getParameter("brandFel-Pro"), Config.getParameter("brandFelProValue"));
		brands.put(Config.getParameter("brandFerodo"), Config.getParameter("brandFerodoValue"));
		brands.put(Config.getParameter("brandFPDiesel"), Config.getParameter("brandFPDieselValue"));
		brands.put(Config.getParameter("brandMOOG"), Config.getParameter("brandMOOGValue"));
		brands.put(Config.getParameter("brandNational"), Config.getParameter("brandNationalValue"));
		brands.put(Config.getParameter("brandSealedPower"), Config.getParameter("brandSealedPowerValue"));
		brands.put(Config.getParameter("brandSpeedPro"), Config.getParameter("brandSpeedProValue"));
		brands.put(Config.getParameter("brandWagnerBrak"), Config.getParameter("brandWagnerBrakeValue"));
		brands.put(Config.getParameter("brandWagnerLighting"), Config.getParameter("brandWagnerLightingValue"));

		return brands;
	}

	private Map<String, String> prepareshopbayMap()
	{
		final Map<String, String> shopBay = new LinkedHashMap<String, String>();
		shopBay.put(Config.getParameter("shopBay5-9"), Config.getParameter("shopBay5-9Value"));
		shopBay.put(Config.getParameter("shopBaysLess"), Config.getParameter("shopBaysLessValue"));
		shopBay.put(Config.getParameter("shopBay10-14"), Config.getParameter("shopBay10-14value"));
		shopBay.put(Config.getParameter("greatershopBay"), Config.getParameter("shopBayGreaterValue"));

		return shopBay;
	}

	/**
	 * @return the fmCustomerFacade
	 */
	public FMCustomerFacade getFmCustomerFacade()
	{
		return fmCustomerFacade;
	}

	/**
	 * @param fmCustomerFacade
	 *           the fmCustomerFacade to set
	 */
	public void setFmCustomerFacade(final FMCustomerFacade fmCustomerFacade)
	{
		this.fmCustomerFacade = fmCustomerFacade;
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
}
