/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2013 hybris AG
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of hybris
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with hybris.
 * 
 *  
 */
package com.federalmogul.storefront.controllers.pages;

import de.hybris.platform.b2bacceleratorservices.company.CompanyB2BCommerceService;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;
import de.hybris.platform.commercefacades.i18n.I18NFacade;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.core.model.security.PrincipalGroupModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Date;
import de.hybris.platform.servicelayer.model.ModelService;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.xml.soap.SOAPException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.federalmogul.core.model.FMCustomerAccountModel;
import com.federalmogul.facades.account.FMCustomerFacade;
import com.federalmogul.facades.address.data.FMB2bAddressData;
import com.federalmogul.facades.network.FMNetworkFacade;
import com.federalmogul.facades.user.data.FMCustomerAccountData;
import com.federalmogul.facades.user.data.FMCustomerData;
import com.federalmogul.falcon.core.model.FMCsrAccountListModel;
import com.federalmogul.storefront.constants.WebConstants;
import com.federalmogul.storefront.controllers.ControllerConstants;
import com.federalmogul.storefront.controllers.util.GlobalMessages;
import com.federalmogul.storefront.forms.FMAccountSearchForm;
import com.federalmogul.storefront.forms.FMAddressForm;
import com.federalmogul.storefront.forms.LoginForm;
import com.federalmogul.storefront.forms.OrderUploadForm;
import com.fm.falcon.webservices.dto.LoyaltyHistoryRequestDTO;
import com.fm.falcon.webservices.dto.LoyaltyHistoryResponseDTO;
import com.fm.falcon.webservices.soap.helper.LoyaltyHistoryHelper;
import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.domain.CreditCheckBO;
import com.fmo.wom.domain.SalesOrganizationBO;
import com.fmo.wom.integration.SAPService;


/**
 * Controller for home page.
 */
@Controller
@Scope("tenant")
@RequestMapping("/")
@SessionAttributes(
{ "customerType", "logedUserType" })
public class HomePageController extends AbstractPageController
{

	public final static String SITE_NAME = "loyalty";

	@Autowired
	@Resource
	private UserService userService;

	@Resource
	protected FMCustomerFacade fmCustomerFacade;

	@Resource
	protected CompanyB2BCommerceService companyB2BCommerceService;

	@Resource
	protected SessionService sessionService;

	/**
	 * @return the sessionService
	 */
	@Override
	public SessionService getSessionService()
	{
		return sessionService;
	}

	/**
	 * @param sessionService
	 *           the sessionService to set
	 */
	public void setSessionService(final SessionService sessionService)
	{
		this.sessionService = sessionService;
	}

	@Resource(name = "fmNetworkFacade")
	protected FMNetworkFacade fmNetworkFacade;

	private static final Logger LOG = Logger.getLogger(HomePageController.class);

	private static final int CHECK_RESULT_SIZE = 0;

	@Resource
	private ModelService modelService;


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

	/**
	 * @return the companyB2BCommerceService
	 */
	public CompanyB2BCommerceService getCompanyB2BCommerceService()
	{
		return companyB2BCommerceService;
	}

	/**
	 * @param companyB2BCommerceService
	 *           the companyB2BCommerceService to set
	 */
	public void setCompanyB2BCommerceService(final CompanyB2BCommerceService companyB2BCommerceService)
	{
		this.companyB2BCommerceService = companyB2BCommerceService;
	}



	@SuppressWarnings("null")
	@RequestMapping(method = RequestMethod.GET)
	public String home(@RequestParam(value = "logout", defaultValue = "false") final boolean logout, final Model model,
			final RedirectAttributes redirectModel, final HttpServletRequest request) throws CMSItemNotFoundException
	{

		final String queryString = request.getQueryString();

		LOG.info("queryString :: " + queryString);

		LOG.info("URL :: " + request.getRequestURL());
		LOG.info("URI  :: " + request.getRequestURI());

		if (logout)
		{
			LOG.info("INSIDE LOGOUT");
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.INFO_MESSAGES_HOLDER, "account.confirmation.signout.title");
			return REDIRECT_PREFIX + ROOT;
		}
		final UserModel user = userService.getCurrentUser();
		LOG.info("user" + user.getUid());
		LOG.info("loginError session : " + getSessionService().getAttribute("loginError"));
		final String rememberme = request.getParameter("_spring_security_remember_me");
		LOG.info("---------rememberme::" + rememberme);
		LOG.info("loginErrorMessage session: " + getSessionService().getAttribute("loginErrorMessage"));
		model.addAttribute("regionData", getI18NFacade().getRegionsForCountryIso("US"));
		@SuppressWarnings("deprecation")
		boolean isUserAnonymous = user == null || userService.isAnonymousUser(user);
		storeCmsPageInModel(model, getContentPageForLabelOrId(null));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(null));
		updatePageTitle(model, getContentPageForLabelOrId(null));

		model.addAttribute("loginError", getSessionService().getAttribute("loginError"));
		model.addAttribute("displayIFrame", false);
		getSessionService().setAttribute("iframe", false);
		LOG.debug("IFrame::" + getSessionService().getAttribute("iframe"));
		if (null != getSessionService().getAttribute("loginError") && (boolean) getSessionService().getAttribute("loginError"))
		{
			if (getSessionService().getAttribute("loginError").equals(true))
			{

				GlobalMessages.addErrorMessage(model, getSessionService().getAttribute("loginErrorMessage").toString());

			}
			/* else
			{
				GlobalMessages.addErrorMessage(model, " ");
			} */

			//	getSessionService().removeAttribute("loginError");
			getSessionService().setAttribute("loginError", false);

		}
		else
		{
			getSessionService().setAttribute("loginError", false);

			//GlobalMessages.addErrorMessage(model, " ");

		}


		final List<String> groupUID = new ArrayList<String>();
		final Set<PrincipalGroupModel> groupss = user.getGroups();
		for (final PrincipalGroupModel groupModel : groupss)
		{
			final String groupId = groupModel.getUid();
			groupUID.add(groupId);
		}
		model.addAttribute("isUserAnonymous", isUserAnonymous);
		if (!isUserAnonymous)
		{
			if (user.getLogindate() == null)
			{
				LOG.info("in date  setting");
				user.setLogindate(new Date());
				modelService.save(user);
			}
		}


		if ((queryString != null && queryString.contains(SITE_NAME))
				|| (getSessionService().getAttribute("loyality") != null && getSessionService().getAttribute("loyality").equals(
						"loyality")))
		{
			LOG.info("Loyalty Site Invoking ");
			if (!isUserAnonymous)
			{

				final FMCustomerData currentUser = fmCustomerFacade.getCurrentFMCustomer();
				getCurrentUserLoyaltyPoints(currentUser, model);
				isUserAnonymous = true;
				storeCmsPageInModel(model, getContentPageForLabelOrId("LoyaltyHomePage"));
				setUpMetaDataForContentPage(model, getContentPageForLabelOrId("LoyaltyHomePage"));
				updatePageTitle(model, getContentPageForLabelOrId("LoyaltyHomePage"));
			}
			else{
				return REDIRECT_PREFIX + ROOT+"?site=federalmogul&clear=true";

			}
		}
		else
		{
			LOG.info("Anonymous Site Invoking ");
			final LoginForm loginForm = new LoginForm();
			LOG.info("in else block of cookie :");
			final Cookie[] cookies = request.getCookies();

			if (cookies != null)
			{
				for (int i = 0; i < cookies.length; i++)
				{
					final Cookie cookie = cookies[i];
					if (cookie.getName().equals("RememberMeCustom"))
					{
						LOG.info("in RememberMeCustom block of cookie :");
						LOG.info("COOKIE VAL IS :" + cookie.getValue());


						loginForm.setJ_username(cookie.getValue());
						model.addAttribute("checkboxx", "true");
						break;
					}
					model.addAttribute("checkboxx", "false");
				}
			}
			model.addAttribute("loginForm", loginForm);			
			storeCmsPageInModel(model, getContentPageForLabelOrId("fmAnonymousHomePage"));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId("fmAnonymousHomePage"));
			updatePageTitle(model, getContentPageForLabelOrId("fmAnonymousHomePage"));
		}

		if (!isUserAnonymous)
		{
			final FMCustomerData fmCustomerData = fmCustomerFacade.getCurrentFMCustomer();
			if (groupUID.contains("FMB2BB")|| groupUID.contains("FMViewOnlyGroup") || groupUID.contains("FMNoInvoiceGroup"))
			{
				setB2BAccountDetails(fmCustomerData.getFmunit(), model);
				final String soldToAcc = (String) getSessionService().getAttribute(WebConstants.SOLDTO_ACCOUNT_ID);
				final FMCustomerAccountModel fmCustomerAccModel = (FMCustomerAccountModel) companyB2BCommerceService
						.getUnitForUid(soldToAcc);
				final SalesOrganizationBO salesOrg = new SalesOrganizationBO();
				salesOrg.setCode(fmCustomerAccModel.getSalesorg());
				model.addAttribute("manualShipToFlagEnable", (null != fmCustomerAccModel.getShipToLimitFlag() && fmCustomerAccModel
						.getShipToLimitFlag().equalsIgnoreCase("C")) ? "No" : "Yes");
				try
				{
					final CreditCheckBO creditCheckBO = SAPService.doCreditCheck(soldToAcc, salesOrg);
					LOG.info(" creditCheckBO .. getCreditBlock :: " + creditCheckBO.getCreditBlock());
					LOG.info("IS Customer Order Blocked ... :: " + creditCheckBO.getOrderBlock());

					getSessionService().setAttribute(WebConstants.FM_CREDIT_BLOCK, creditCheckBO.getCreditBlock());
					getSessionService().setAttribute(WebConstants.FM_ORDER_BLOCK, creditCheckBO.getOrderBlock());
					model.addAttribute(WebConstants.FM_CREDIT_BLOCK, creditCheckBO.getCreditBlock());
					model.addAttribute(WebConstants.FM_ORDER_BLOCK, creditCheckBO.getOrderBlock());
				}
				catch (final WOMExternalSystemException e)
				{
					getSessionService().setAttribute(WebConstants.FM_CREDIT_BLOCK, "Y");
					getSessionService().setAttribute(WebConstants.FM_ORDER_BLOCK, "Y");
				}
				catch (final Exception e)
				{
					getSessionService().setAttribute(WebConstants.FM_CREDIT_BLOCK, "Y");
					getSessionService().setAttribute(WebConstants.FM_ORDER_BLOCK, "Y");
				}

				storeCmsPageInModel(model, getContentPageForLabelOrId("FMB2BHomePage"));
				setUpMetaDataForContentPage(model, getContentPageForLabelOrId("FMB2BHomePage"));
				updatePageTitle(model, getContentPageForLabelOrId("FMB2BHomePage"));
				model.addAttribute("customerType", "b2BCustomer");
				final String uploadOrderStatus = getSessionService().getAttribute("uploadStatus");
				if (uploadOrderStatus != null && !uploadOrderStatus.isEmpty())
				{
					model.addAttribute("uploadStatus", uploadOrderStatus);
					getSessionService().setAttribute("uploadStatus", "");
				}

				model.addAttribute("orderUpload", new OrderUploadForm());
				model.addAttribute("addressForm", new FMAddressForm());

				return ControllerConstants.Views.Pages.Account.AccountBToBigB;
			}
			else if (groupUID.contains("FMB2SB"))
			{

				final List<FMB2bAddressData> companyAddress = fmCustomerData.getFmunit().getUnitAddress();
				model.addAttribute("customerType", "b2bCustomer");
				model.addAttribute("address", companyAddress);
				model.addAttribute("unitDetails", fmCustomerData.getFmunit());
				model.addAttribute("orderUpload", new OrderUploadForm());
				model.addAttribute("addressForm", new FMAddressForm());
				storeCmsPageInModel(model, getContentPageForLabelOrId("FMHomePageB2b"));
				setUpMetaDataForContentPage(model, getContentPageForLabelOrId("FMHomePageB2b"));
				updatePageTitle(model, getContentPageForLabelOrId("FMHomePageB2b"));
				model.addAttribute("customerType", "b2bCustomer");
				return ControllerConstants.Views.Pages.Account.AccountBToSmallB;
			}
			else if (groupUID.contains("FMB2T"))
			{
				model.addAttribute("customerType", "b2bCustomer");
				//final FMCustomerData currentUser = fmCustomerFacade.getCurrentFMCustomer();
				model.addAttribute("currentUser", fmCustomerData);
				model.addAttribute("fromb2t", "fromb2t");
				LOG.info("CURRENTUSER loyalty" + fmCustomerData.getLoyaltySignup());
				getCurrentUserLoyaltyPoints(fmCustomerData, model);

				LOG.info("################## LOGGED as FMB2T ################ ");
				storeCmsPageInModel(model, getContentPageForLabelOrId("FMHomePageB2T"));
				setUpMetaDataForContentPage(model, getContentPageForLabelOrId("FMHomePageB2T"));
				updatePageTitle(model, getContentPageForLabelOrId("FMHomePageB2T"));
				return ControllerConstants.Views.Pages.Account.AccountBToT;
			}
			else if (groupUID.contains("FMB2C") || groupUID.contains("FMTaxAdminGroup"))
			{
				storeCmsPageInModel(model, getContentPageForLabelOrId("FMB2CHomePage"));
				setUpMetaDataForContentPage(model, getContentPageForLabelOrId("FMB2CHomePage"));
				updatePageTitle(model, getContentPageForLabelOrId("FMB2CHomePage"));
				model.addAttribute("customerType", "b2cCustomer");
				return ControllerConstants.Views.Pages.Account.AccountBToC;
			}
			else if (groupUID.contains("FMAdminGroup"))
			{

				final boolean isFMAdminHomePage = true;
				LOG.info("################## LOGGED as FMAdminGroup ################ ");
				storeCmsPageInModel(model, getContentPageForLabelOrId("FMB2CHomePage"));
				setUpMetaDataForContentPage(model, getContentPageForLabelOrId("FMB2CHomePage"));
				updatePageTitle(model, getContentPageForLabelOrId("FMB2CHomePage"));
				model.addAttribute("customerType", "FMAdmin");
				model.addAttribute("isFMAdminHomePage", isFMAdminHomePage);
				//return ControllerConstants.Views.Pages.Account.AccountBToC;
				return FORWARD_PREFIX + "/my-fmaccount/profile";

			}
			else if (groupUID.contains("FMCSR") || groupUID.contains("FMBUVOR"))
			{

				if (getSessionService().getAttribute("emulationCSR") != null)
				{
					LOG.info("!!!!!!!!!!!!!!!!!!emulationCSR:::not null");
					final boolean isAccountEmulated = getSessionService().getAttribute("emulationCSR");
					if (isAccountEmulated)
					{
						LOG.info("!!!!!!!!!!!!!!!!!!isAccountEmulated::true");
						final String emulatedAcocuntID = getSessionService().getAttribute("accountIdCSR");
						return FORWARD_PREFIX + "/csr-emulation/start-emulate/" + emulatedAcocuntID;
					}
					else
					{
						LOG.info("!!!!!!!!!!!!!!!!!!isAccountEmulated::falseeeeeeeeeeeeeeeeeeee");
						model.addAttribute("fmaccountSearchForm", new FMAccountSearchForm());
						model.addAttribute("accountNumberFlag", true);
						storeCmsPageInModel(model, getContentPageForLabelOrId("CSRHomePage"));
						setUpMetaDataForContentPage(model, getContentPageForLabelOrId("CSRHomePage"));
						updatePageTitle(model, getContentPageForLabelOrId("CSRHomePage"));
						model.addAttribute("customerType", "CSRCustomer");
						final List<FMCsrAccountListModel> csrAccountList = fmCustomerFacade.getFMCSRAccountList();
						model.addAttribute("csrAccountList", csrAccountList);
						return ControllerConstants.Views.Pages.Account.AccountCSR;
					}

				}
				else
				{
					LOG.info("!!!!!!!!!!!!!!!!!!emulationCSR:::is nullllllllllllllllllllllllllllllll");
					model.addAttribute("fmaccountSearchForm", new FMAccountSearchForm());
					model.addAttribute("accountNumberFlag", true);
					storeCmsPageInModel(model, getContentPageForLabelOrId("CSRHomePage"));
					setUpMetaDataForContentPage(model, getContentPageForLabelOrId("CSRHomePage"));
					updatePageTitle(model, getContentPageForLabelOrId("CSRHomePage"));
					model.addAttribute("customerType", "CSRCustomer");
					final List<FMCsrAccountListModel> csrAccountList = fmCustomerFacade.getFMCSRAccountList();
					model.addAttribute("csrAccountList", csrAccountList);
					return ControllerConstants.Views.Pages.Account.AccountCSR;
				}


			}

		}

		return getViewForPage(model);
	}
	
	
	/**
	 * FM20-213 changes
	 * @param model
	 * @param cmsPage
	 */

	@SuppressWarnings("null")
	@RequestMapping(value = "/iframe", method = { RequestMethod.POST, RequestMethod.GET })
	public String homeFrame(@RequestParam(value = "logout", defaultValue = "false") final boolean logout, final Model model,
			final RedirectAttributes redirectModel, final HttpServletRequest request) throws CMSItemNotFoundException
	{
		final String queryString = request.getQueryString();

		LOG.debug("queryString iframe:: " + queryString);
		
		LOG.debug("URL homepage:: " + request.getRequestURL());
		LOG.debug("URI  homepage:: " + request.getRequestURI());

		if (logout) {
			LOG.debug("iframe logout");
			GlobalMessages.addFlashMessage(redirectModel,
					GlobalMessages.INFO_MESSAGES_HOLDER,
					"account.confirmation.signout.title");
			return REDIRECT_PREFIX + ROOT;
		}
		final UserModel user = userService.getCurrentUser();
		LOG.debug("user" + user.getUid());
		LOG.debug("loginError session : "
				+ getSessionService().getAttribute("loginError"));
		final String rememberme = request
				.getParameter("_spring_security_remember_me");
		LOG.debug("---------rememberme::" + rememberme);
		LOG.debug("loginErrorMessage session: "
				+ getSessionService().getAttribute("loginErrorMessage"));
		model.addAttribute("regionData", getI18NFacade()
				.getRegionsForCountryIso("US"));
		@SuppressWarnings("deprecation")
		boolean isUserAnonymous = user == null
				|| userService.isAnonymousUser(user);
		storeCmsPageInModel(model, getContentPageForLabelOrId(null));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(null));
		updatePageTitle(model, getContentPageForLabelOrId(null));

		model.addAttribute("loginError",
				getSessionService().getAttribute("loginError"));
		model.addAttribute("displayIFrame", true);
		getSessionService().setAttribute("iframe", true);
		LOG.debug("IFrame::" + getSessionService().getAttribute("iframe"));
		LOG.debug("displayIFrame::" + model.containsAttribute("displayIFrame"));
		if (null != getSessionService().getAttribute("loginError")
				&& (boolean) getSessionService().getAttribute("loginError")) {
			if (getSessionService().getAttribute("loginError").equals(true)) {

				GlobalMessages.addErrorMessage(model, getSessionService()
						.getAttribute("loginErrorMessage").toString());

			}
			getSessionService().setAttribute("loginError", false);

		} else {
			getSessionService().setAttribute("loginError", false);
		}

		final List<String> groupUID = new ArrayList<String>();
		final Set<PrincipalGroupModel> groupss = user.getGroups();
		for (final PrincipalGroupModel groupModel : groupss) {
			final String groupId = groupModel.getUid();
			groupUID.add(groupId);
		}
		model.addAttribute("isUserAnonymous", isUserAnonymous);
		if (!isUserAnonymous) {
			if (user.getLogindate() == null) {
				LOG.debug("in date  setting");
				user.setLogindate(new Date());
				modelService.save(user);
			}
		}

		if ((queryString != null && queryString.contains(SITE_NAME))
				|| (getSessionService().getAttribute("loyality") != null && getSessionService()
						.getAttribute("loyality").equals("loyality"))) {
			LOG.debug("Loyalty Site Invoking ");
			if (!isUserAnonymous) {

				final FMCustomerData currentUser = fmCustomerFacade
						.getCurrentFMCustomer();
				getCurrentUserLoyaltyPoints(currentUser, model);
				isUserAnonymous = true;
				storeCmsPageInModel(model,
						getContentPageForLabelOrId("LoyaltyHomePage"));
				setUpMetaDataForContentPage(model,
						getContentPageForLabelOrId("LoyaltyHomePage"));
				updatePageTitle(model,
						getContentPageForLabelOrId("LoyaltyHomePage"));
			} else {
				return REDIRECT_PREFIX + ROOT + "?site=federalmogul&clear=true";

			}
		} else {
			LOG.debug("Anonymous Site Invoking ");
			final LoginForm loginForm = new LoginForm();
			LOG.debug("in else block of cookie :");
			final Cookie[] cookies = request.getCookies();

			if (cookies != null) {
				for (int i = 0; i < cookies.length; i++) {
					final Cookie cookie = cookies[i];
					if (cookie.getName().equals("RememberMeCustom")) {
						LOG.debug("in RememberMeCustom block of cookie :");
						LOG.debug("COOKIE VAL IS :" + cookie.getValue());

						loginForm.setJ_username(cookie.getValue());
						model.addAttribute("checkboxx", "true");
						break;
					}
					model.addAttribute("checkboxx", "false");
				}
			}
			model.addAttribute("loginForm", loginForm);
			storeCmsPageInModel(model,
					getContentPageForLabelOrId("fmAnonymousHomePage"));
			setUpMetaDataForContentPage(model,
					getContentPageForLabelOrId("fmAnonymousHomePage"));
			updatePageTitle(model,
					getContentPageForLabelOrId("fmAnonymousHomePage"));
		}

		if (!isUserAnonymous) {
			final FMCustomerData fmCustomerData = fmCustomerFacade
					.getCurrentFMCustomer();
			if (groupUID.contains("FMB2BB")
					|| groupUID.contains("FMViewOnlyGroup")
					|| groupUID.contains("FMNoInvoiceGroup")) {
				setB2BAccountDetails(fmCustomerData.getFmunit(), model);
				final String soldToAcc = (String) getSessionService()
						.getAttribute(WebConstants.SOLDTO_ACCOUNT_ID);
				final FMCustomerAccountModel fmCustomerAccModel = (FMCustomerAccountModel) companyB2BCommerceService
						.getUnitForUid(soldToAcc);
				final SalesOrganizationBO salesOrg = new SalesOrganizationBO();
				salesOrg.setCode(fmCustomerAccModel.getSalesorg());
				model.addAttribute(
						"manualShipToFlagEnable",
						(null != fmCustomerAccModel.getShipToLimitFlag() && fmCustomerAccModel
								.getShipToLimitFlag().equalsIgnoreCase("C")) ? "No"
								: "Yes");
				try {
					final CreditCheckBO creditCheckBO = SAPService
							.doCreditCheck(soldToAcc, salesOrg);
					LOG.debug(" creditCheckBO .. getCreditBlock :: "
							+ creditCheckBO.getCreditBlock());
					LOG.debug("IS Customer Order Blocked ... :: "
							+ creditCheckBO.getOrderBlock());

					getSessionService().setAttribute(
							WebConstants.FM_CREDIT_BLOCK,
							creditCheckBO.getCreditBlock());
					getSessionService().setAttribute(
							WebConstants.FM_ORDER_BLOCK,
							creditCheckBO.getOrderBlock());
					model.addAttribute(WebConstants.FM_CREDIT_BLOCK,
							creditCheckBO.getCreditBlock());
					model.addAttribute(WebConstants.FM_ORDER_BLOCK,
							creditCheckBO.getOrderBlock());
				} catch (final WOMExternalSystemException e) {
					getSessionService().setAttribute(
							WebConstants.FM_CREDIT_BLOCK, "Y");
					getSessionService().setAttribute(
							WebConstants.FM_ORDER_BLOCK, "Y");
				} catch (final Exception e) {
					getSessionService().setAttribute(
							WebConstants.FM_CREDIT_BLOCK, "Y");
					getSessionService().setAttribute(
							WebConstants.FM_ORDER_BLOCK, "Y");
				}

				storeCmsPageInModel(model,
						getContentPageForLabelOrId("FMB2BHomePage"));
				setUpMetaDataForContentPage(model,
						getContentPageForLabelOrId("FMB2BHomePage"));
				updatePageTitle(model,
						getContentPageForLabelOrId("FMB2BHomePage"));
				model.addAttribute("customerType", "b2BCustomer");
				final String uploadOrderStatus = getSessionService()
						.getAttribute("uploadStatus");
				if (uploadOrderStatus != null && !uploadOrderStatus.isEmpty()) {
					model.addAttribute("uploadStatus", uploadOrderStatus);
					getSessionService().setAttribute("uploadStatus", "");
				}

				model.addAttribute("orderUpload", new OrderUploadForm());
				model.addAttribute("addressForm", new FMAddressForm());

				return ControllerConstants.Views.Pages.Account.AccountBToBigB;
			}
			else if (groupUID.contains("FMB2T")) {
				model.addAttribute("customerType", "b2bCustomer");
				model.addAttribute("currentUser", fmCustomerData);
				model.addAttribute("fromb2t", "fromb2t");
				LOG.debug("CURRENTUSER loyalty"
						+ fmCustomerData.getLoyaltySignup());
				getCurrentUserLoyaltyPoints(fmCustomerData, model);

				LOG.debug("################## LOGGED as FMB2T ################ ");
				storeCmsPageInModel(model,
						getContentPageForLabelOrId("FMHomePageB2T"));
				setUpMetaDataForContentPage(model,
						getContentPageForLabelOrId("FMHomePageB2T"));
				updatePageTitle(model,
						getContentPageForLabelOrId("FMHomePageB2T"));
				return ControllerConstants.Views.Pages.Account.AccountBToT;
			}
			else if (groupUID.contains("FMB2C") || groupUID.contains("FMTaxAdminGroup"))
			{
				LOG.info("customerType:b2cCustomer");
				storeCmsPageInModel(model, getContentPageForLabelOrId("FMB2CHomePage"));
				setUpMetaDataForContentPage(model, getContentPageForLabelOrId("FMB2CHomePage"));
				updatePageTitle(model, getContentPageForLabelOrId("FMB2CHomePage"));
				model.addAttribute("customerType", "b2cCustomer");
				return ControllerConstants.Views.Pages.Account.AccountBToC;
			}

			else if (groupUID.contains("FMAdminGroup")) {

				final boolean isFMAdminHomePage = true;
				LOG.debug("################## LOGGED as FMAdminGroup ################ ");
				storeCmsPageInModel(model,
						getContentPageForLabelOrId("FMB2CHomePage"));
				setUpMetaDataForContentPage(model,
						getContentPageForLabelOrId("FMB2CHomePage"));
				updatePageTitle(model,
						getContentPageForLabelOrId("FMB2CHomePage"));
				model.addAttribute("customerType", "FMAdmin");
				model.addAttribute("isFMAdminHomePage", isFMAdminHomePage);
				// return ControllerConstants.Views.Pages.Account.AccountBToC;
				return FORWARD_PREFIX + "/my-fmaccount/profile";

			} else if (groupUID.contains("FMCSR")
					|| groupUID.contains("FMBUVOR")) {

				if (getSessionService().getAttribute("emulationCSR") != null) {
					LOG.debug("!!!!!!!!!!!!!!!!!!emulationCSR:::not null");
					final boolean isAccountEmulated = getSessionService()
							.getAttribute("emulationCSR");
					if (isAccountEmulated) {
						LOG.debug("!!!!!!!!!!!!!!!!!!isAccountEmulated::true");
						final String emulatedAcocuntID = getSessionService()
								.getAttribute("accountIdCSR");
						return FORWARD_PREFIX + "/csr-emulation/start-emulate/"
								+ emulatedAcocuntID;
					} else {
						LOG.debug("!!!!!!!!!!!!!!!!!!isAccountEmulated::falseeeeeeeeeeeeeeeeeeee");
						model.addAttribute("fmaccountSearchForm",
								new FMAccountSearchForm());
						model.addAttribute("accountNumberFlag", true);
						storeCmsPageInModel(model,
								getContentPageForLabelOrId("CSRHomePage"));
						setUpMetaDataForContentPage(model,
								getContentPageForLabelOrId("CSRHomePage"));
						updatePageTitle(model,
								getContentPageForLabelOrId("CSRHomePage"));
						model.addAttribute("customerType", "CSRCustomer");
						final List<FMCsrAccountListModel> csrAccountList = fmCustomerFacade
								.getFMCSRAccountList();
						model.addAttribute("csrAccountList", csrAccountList);
						return ControllerConstants.Views.Pages.Account.AccountCSR;
					}

				} else {
					LOG.debug("!!!!!!!!!!!!!!!!!!emulationCSR:::is nullllllllllllllllllllllllllllllll");
					model.addAttribute("fmaccountSearchForm",
							new FMAccountSearchForm());
					model.addAttribute("accountNumberFlag", true);
					storeCmsPageInModel(model,
							getContentPageForLabelOrId("CSRHomePage"));
					setUpMetaDataForContentPage(model,
							getContentPageForLabelOrId("CSRHomePage"));
					updatePageTitle(model,
							getContentPageForLabelOrId("CSRHomePage"));
					model.addAttribute("customerType", "CSRCustomer");
					final List<FMCsrAccountListModel> csrAccountList = fmCustomerFacade
							.getFMCSRAccountList();
					model.addAttribute("csrAccountList", csrAccountList);
					return ControllerConstants.Views.Pages.Account.AccountCSR;
				}

			}

		}

		return getViewForPage(model);
	}

	
	protected void updatePageTitle(final Model model, final AbstractPageModel cmsPage)
	{
		storeContentPageTitleInModel(model, getPageTitleResolver().resolveHomePageTitle(cmsPage.getTitle()));
	}
}
