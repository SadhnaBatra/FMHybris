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

import de.hybris.platform.acceleratorservices.config.HostConfigService;
import de.hybris.platform.acceleratorservices.config.SiteConfigService;
import de.hybris.platform.acceleratorservices.data.RequestContextData;
import de.hybris.platform.acceleratorservices.storefront.data.MetaElementData;
import de.hybris.platform.acceleratorservices.storefront.util.PageTitleResolver;
import de.hybris.platform.b2bacceleratorservices.company.CompanyB2BCommerceService;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;
import de.hybris.platform.cms2.model.pages.ContentPageModel;
import de.hybris.platform.cms2.model.pages.PageTemplateModel;
import de.hybris.platform.cms2.model.site.CMSSiteModel;
import de.hybris.platform.cms2.servicelayer.services.CMSPageService;
import de.hybris.platform.cms2.servicelayer.services.CMSSiteService;
import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.commercefacades.i18n.I18NFacade;
import de.hybris.platform.commercefacades.storesession.StoreSessionFacade;
import de.hybris.platform.commercefacades.storesession.data.CurrencyData;
import de.hybris.platform.commercefacades.storesession.data.LanguageData;
import de.hybris.platform.commercefacades.user.UserFacade;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.i18n.I18NService;
import de.hybris.platform.servicelayer.session.SessionService;

import com.federalmogul.storefront.constants.WebConstants;
import com.federalmogul.storefront.controllers.AbstractController;
import com.federalmogul.storefront.controllers.ThirdPartyConstants;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.soap.SOAPException;

import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.View;

import com.federalmogul.core.model.FMCustomerAccountModel;
import com.federalmogul.facades.account.FMCustomerFacade;
import com.federalmogul.facades.user.data.FMCustomerAccountData;
import com.federalmogul.facades.user.data.FMCustomerData;
import com.federalmogul.storefront.constants.WebConstants;
import com.federalmogul.storefront.controllers.util.GlobalMessages;
import com.fm.falcon.webservices.dto.LoyaltyHistoryRequestDTO;
import com.fm.falcon.webservices.dto.LoyaltyHistoryResponseDTO;
import com.fm.falcon.webservices.soap.helper.LoyaltyHistoryHelper;

/**
 * Base controller for all page controllers. Provides common functionality for all page controllers.
 */
public abstract class AbstractPageController extends AbstractController
{
	private static final Logger LOG = Logger.getLogger(AbstractPageController.class);
	public static final String PAGE_ROOT = "pages/";
	public static final String ROOT = "/";
	public static final String AEM_ROOT = "/content/loc-na/loc-us/fmmp-corporate/en_US.html";

	public static final String CMS_PAGE_MODEL = "cmsPage";
	public static final String CMS_PAGE_TITLE = "pageTitle";
	public static final String CUSTOMER_PICKUP_RADIUS_LIMIT_KEY = "deliveryMethod.customerPickup.radiusLimit";

	@Resource
	protected CompanyB2BCommerceService companyB2BCommerceService;

	@Resource(name = "cmsSiteService")
	private CMSSiteService cmsSiteService;

	@Resource(name = "cmsPageService")
	private CMSPageService cmsPageService;

	@Resource(name = "storeSessionFacade")
	private StoreSessionFacade storeSessionFacade;

	@Resource(name = "userFacade")
	private UserFacade userFacade;

	@Resource(name = "customerFacade")
	private CustomerFacade customerFacade;

	@Resource(name = "pageTitleResolver")
	private PageTitleResolver pageTitleResolver;

	@Resource(name = "sessionService")
	private SessionService sessionService;

	@Resource(name = "hostConfigService")
	private HostConfigService hostConfigService;

	@Resource(name = "messageSource")
	private MessageSource messageSource;

	@Resource(name = "i18nService")
	private I18NService i18nService;

	@Resource(name = "siteConfigService")
	private SiteConfigService siteConfigService;

	@Resource(name = "i18NFacade")
	private I18NFacade i18NFacade;
	
	@Resource
	private ConfigurationService configurationService;

	@Resource
	protected FMCustomerFacade fmCustomerFacade;

	protected CMSSiteService getCmsSiteService()
	{
		return cmsSiteService;
	}

	protected CMSPageService getCmsPageService()
	{
		return cmsPageService;
	}

	protected StoreSessionFacade getStoreSessionFacade()
	{
		return storeSessionFacade;
	}

	protected UserFacade getUserFacade()
	{
		return userFacade;
	}

	protected SessionService getSessionService()
	{
		return sessionService;
	}

	protected MessageSource getMessageSource()
	{
		return messageSource;
	}

	protected I18NService getI18nService()
	{
		return i18nService;
	}

	protected HostConfigService getHostConfigService()
	{
		return hostConfigService;
	}

	protected CustomerFacade getCustomerFacade()
	{
		return customerFacade;
	}

	protected SiteConfigService getSiteConfigService()
	{
		return siteConfigService;
	}

	@ModelAttribute("languages")
	public Collection<LanguageData> getLanguages()
	{
		return storeSessionFacade.getAllLanguages();
	}

	@ModelAttribute("currencies")
	public Collection<CurrencyData> getCurrencies()
	{
		return storeSessionFacade.getAllCurrencies();
	}

	@ModelAttribute("currentLanguage")
	public LanguageData getCurrentLanguage()
	{
		return storeSessionFacade.getCurrentLanguage();
	}

	@ModelAttribute("currentCurrency")
	public CurrencyData getCurrentCurrency()
	{
		return storeSessionFacade.getCurrentCurrency();
	}

	@ModelAttribute("currentsiteUID")
	public String getSiteUID()
	{
		final CMSSiteModel site = cmsSiteService.getCurrentSite();
		return site != null ? site.getUid() : "";
	}

	@ModelAttribute("siteName")
	public String getSiteName()
	{
		final CMSSiteModel site = cmsSiteService.getCurrentSite();
		return site != null ? site.getName() : "";
	}

	@ModelAttribute("shippingOption")
	public String getShippingOption()
	{
		final String shippingOption = getSessionService().getAttribute("shipingOption");
		return shippingOption != null ? shippingOption : "pickup";
	}

	@ModelAttribute("user")
	public CustomerData getUser()
	{
		return customerFacade.getCurrentCustomer();
	}

	@ModelAttribute("googleAnalyticsTrackingId")
	public String getGoogleAnalyticsTrackingId(final HttpServletRequest request)
	{
		return getHostConfigService().getProperty(ThirdPartyConstants.Google.ANALYTICS_TRACKING_ID, request.getServerName());
	}


	@ModelAttribute("jirafeApiUrl")
	public String getJirafeApiUrl(final HttpServletRequest request)
	{
		return getHostConfigService().getProperty(ThirdPartyConstants.Jirafe.API_URL, request.getServerName());
	}

	@ModelAttribute("jirafeApiToken")
	public String getJirafeApitoken(final HttpServletRequest request)
	{
		return getHostConfigService().getProperty(ThirdPartyConstants.Jirafe.API_TOKEN, request.getServerName());
	}

	@ModelAttribute("jirafeApplicationId")
	public String getJirafeApplicationId(final HttpServletRequest request)
	{
		return getHostConfigService().getProperty(ThirdPartyConstants.Jirafe.APPLICATION_ID, request.getServerName());
	}

	@ModelAttribute("jirafeSiteId")
	public String getJirafeSiteId(final HttpServletRequest request)
	{
		String propertyValue = getHostConfigService().getProperty(
				ThirdPartyConstants.Jirafe.SITE_ID + "." + storeSessionFacade.getCurrentCurrency().getIsocode().toLowerCase(),
				request.getServerName());
		if ("".equals(propertyValue))
		{
			propertyValue = getHostConfigService().getProperty(ThirdPartyConstants.Jirafe.SITE_ID, request.getServerName());
		}
		return propertyValue;
	}

	@ModelAttribute("jirafeVersion")
	public String getJirafeVersion(final HttpServletRequest request)
	{
		return getHostConfigService().getProperty(ThirdPartyConstants.Jirafe.VERSION, request.getServerName());
	}

	@ModelAttribute("jirafeDataUrl")
	public String getJirafeDataUrl(final HttpServletRequest request)
	{
		return getHostConfigService().getProperty(ThirdPartyConstants.Jirafe.DATA_URL, request.getServerName());
	}

	@ModelAttribute("radiusLimitForPickup")
	public String getRadiusLimitForPickup(final HttpServletRequest request)
	{
		return configurationService.getConfiguration().getString(CUSTOMER_PICKUP_RADIUS_LIMIT_KEY);
	}

	protected void storeCmsPageInModel(final Model model, final AbstractPageModel cmsPage)
	{
		if (model != null && cmsPage != null)
		{
			model.addAttribute(CMS_PAGE_MODEL, cmsPage);
			if (cmsPage instanceof ContentPageModel)
			{
				storeContentPageTitleInModel(model, getPageTitleResolver().resolveContentPageTitle(cmsPage.getTitle()));
			}
		}
	}

	protected void storeContentPageTitleInModel(final Model model, final String title)
	{
		model.addAttribute(CMS_PAGE_TITLE, title);
	}

	protected String getViewForPage(final Model model)
	{
		if (model.containsAttribute(CMS_PAGE_MODEL))
		{
			final AbstractPageModel page = (AbstractPageModel) model.asMap().get(CMS_PAGE_MODEL);
			if (page != null)
			{
				return getViewForPage(page);
			}
		}
		return null;
	}

	protected String getViewForPage(final AbstractPageModel page)
	{
		if (page != null)
		{
			final PageTemplateModel masterTemplate = page.getMasterTemplate();
			if (masterTemplate != null)
			{
				final String targetPage = cmsPageService.getFrontendTemplateName(masterTemplate);
				if (targetPage != null && !targetPage.isEmpty())
				{
					LOG.info("PAGE_ROOT + targetPage :: " + PAGE_ROOT + targetPage);
					return PAGE_ROOT + targetPage;
				}
			}
		}
		return null;
	}

	/**
	 * Checks request URL against properly resolved URL and returns null if url is proper or redirection string if not.
	 * 
	 * @param request
	 *           - request that contains current URL
	 * @param response
	 *           - response to write "301 Moved Permanently" status to if redirected
	 * @param resolvedUrlPath
	 *           - properly resolved URL
	 * @return null if url is properly resolved or redirection string if not
	 * @throws UnsupportedEncodingException
	 */
	protected String checkRequestUrl(final HttpServletRequest request, final HttpServletResponse response,
			final String resolvedUrlPath) throws UnsupportedEncodingException
	{
		try
		{
			final String resolvedUrl = response.encodeURL(request.getContextPath() + resolvedUrlPath);
			final String requestURI = URIUtil.decode(request.getRequestURI(), "utf-8");
			final String decoded = URIUtil.decode(resolvedUrl, "utf-8");
			if (StringUtils.isNotEmpty(requestURI) && requestURI.endsWith(decoded))
			{
				return null;
			}
			else
			{
				request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.MOVED_PERMANENTLY);
				final String queryString = request.getQueryString();
				if (queryString != null && !queryString.isEmpty())
				{
					return "redirect:" + resolvedUrlPath + "?" + queryString;
				}
				return "redirect:" + resolvedUrlPath;
			}
		}
		catch (final URIException e)
		{
			throw new UnsupportedEncodingException();
		}
	}

	protected ContentPageModel getContentPageForLabelOrId(final String labelOrId) throws CMSItemNotFoundException
	{
		String key = labelOrId;
		if (StringUtils.isEmpty(labelOrId))
		{
			// Fallback to site home page
			final ContentPageModel homePage = cmsPageService.getHomepage();
			if (homePage != null)
			{
				key = cmsPageService.getLabelOrId(homePage);
			}
			else
			{
				// Fallback to site start page label
				final CMSSiteModel site = cmsSiteService.getCurrentSite();
				if (site != null)
				{
					key = cmsSiteService.getStartPageLabelOrId(site);
				}
			}
		}

		// Actually resolve the label or id - running cms restrictions
		return cmsPageService.getPageForLabelOrId(key);
	}

	protected PageTitleResolver getPageTitleResolver()
	{
		return pageTitleResolver;
	}

	protected void storeContinueUrl(final HttpServletRequest request)
	{
		final StringBuilder url = new StringBuilder();
		url.append(request.getServletPath());
		final String queryString = request.getQueryString();
		if (queryString != null && !queryString.isEmpty())
		{
			url.append('?').append(queryString);
		}
		getSessionService().setAttribute(WebConstants.CONTINUE_URL, url.toString());
	}

	protected void setUpMetaData(final Model model, final String metaKeywords, final String metaDescription)
	{
		final List<MetaElementData> metadata = new LinkedList<MetaElementData>();
		metadata.add(createMetaElement("keywords", metaKeywords));
		metadata.add(createMetaElement("description", metaDescription));
		model.addAttribute("metatags", metadata);
	}
	//FAL - 90 - Social Media changes
	protected void targetURL(final HttpServletRequest request, final Model model)
	{
		getSessionService().setAttribute("targetURL", request.getRequestURL().toString());
		model.addAttribute("targetURL", request.getRequestURL().toString());
	}

	protected MetaElementData createMetaElement(final String name, final String content)
	{
		final MetaElementData element = new MetaElementData();
		element.setName(name);
		element.setContent(content);
		return element;
	}

	protected void setUpMetaDataForContentPage(final Model model, final ContentPageModel contentPage)
	{
		setUpMetaData(model, contentPage.getKeywords(), contentPage.getDescription());
	}

	protected RequestContextData getRequestContextData(final HttpServletRequest request)
	{
		return getBean(request, "requestContextData", RequestContextData.class);
	}

	/**
	 * Method to return NABS account code
	 * 
	 * @return NABSAccount code as string
	 */

	@ModelAttribute("nabsAccCode")
	protected String getNabsAccCode()
	{
		String nabsCode = "";

		if (getSessionService().getAttribute(WebConstants.SOLDTO_ACCOUNT_ID) != null)
		{
			nabsCode = ((FMCustomerAccountModel) companyB2BCommerceService.getUnitForUid((String) getSessionService().getAttribute(
					WebConstants.SOLDTO_ACCOUNT_ID))).getNabsAccountCode();
		}

		return nabsCode;
	}
	
	@SuppressWarnings("null")
	protected void setB2BAccountDetails(final FMCustomerAccountData fmCustomerAccountData, final Model model)
	{
		//To identify whether the unit is SoldTo or ShipTo
		final String soldtoshiptostring = fmCustomerFacade.checkSoldToShipto(fmCustomerAccountData.getUid());
		//If unit is SoldTo
		if (soldtoshiptostring != null && soldtoshiptostring.equals("SoldTo"))
		{
			// if logged in as sold to	
			LOG.info("LOggin As Soldto");
			//For Checking multiple SoldTo
			final List<FMCustomerAccountData> multiplesoldTos = fmCustomerFacade.getMultipleSoldTos(fmCustomerAccountData.getUid());
			LOG.info("multiplesoldTos " + multiplesoldTos.size());
			model.addAttribute("insideMulitpleSoldTo", multiplesoldTos.size() > 0 ? "multiple" : "single");
			//adding to session
			if (getSessionService().getAttribute(WebConstants.SOLDTO_ACCOUNT_ID) == null
					|| getSessionService().getAttribute(WebConstants.SHIPTO_ACCOUNT_ID) == null)
			{
				getSessionService().setAttribute(WebConstants.SOLDTO_ACCOUNT_ID, fmCustomerAccountData.getUid());
				getSessionService().setAttribute(WebConstants.SHIPTO_ACCOUNT_ID, fmCustomerAccountData.getUid());
			}
			if(fmCustomerAccountData.getUnitAddress()!=null && fmCustomerAccountData.getUnitAddress().get(0).getCompanyName()==null) {
				LOG.info("Customer UnitAddress has company name null so setting it from account name");
				fmCustomerAccountData.getUnitAddress().get(0).setCompanyName(fmCustomerAccountData.getName());
			}
			
			model.addAttribute("SoldToAccount", fmCustomerAccountData);
			model.addAttribute("nabsAccCode", fmCustomerAccountData.getNabsAccountCode());
			model.addAttribute("ifType", new String("SoldTo"));
			model.addAttribute("logedUserType", "SoldTo");
			getSessionService().setAttribute("logedUserType", "SoldTo");
		}
		else if (soldtoshiptostring != null && soldtoshiptostring.equals("ShipTo"))
		{ //if logged in as ship to 
			final List<FMCustomerAccountData> parentUnitShiptTo = fmCustomerFacade.getSoldToShipToUnitUid(fmCustomerAccountData
					.getUid());
			LOG.info("LOggin As SHipto");
			model.addAttribute("insideMulitpleSoldTo", parentUnitShiptTo != null && parentUnitShiptTo.size() > 1 ? "multiple" : "single");
			//for getting all the unit's - soldTo

			FMCustomerAccountData soldToData = null;
			for (final FMCustomerAccountData unitData : parentUnitShiptTo)
			{
				soldToData = unitData;
				LOG.info("Insdide FOR id ::: " + soldToData.getUid());
				break;
			}
			//adding to session
			if (getSessionService().getAttribute(WebConstants.SOLDTO_ACCOUNT_ID) == null
					|| getSessionService().getAttribute(WebConstants.SHIPTO_ACCOUNT_ID) == null)
			{
				getSessionService().setAttribute(WebConstants.SOLDTO_ACCOUNT_ID, soldToData.getUid());
				getSessionService().setAttribute(WebConstants.SHIPTO_ACCOUNT_ID, fmCustomerAccountData.getUid());
			}
			model.addAttribute("SoldToAccount", soldToData);
			model.addAttribute("nabsAccCode", soldToData.getNabsAccountCode());
			model.addAttribute("ifType", new String("ShipTo"));
			model.addAttribute("logedUserType", "ShipTo");
			getSessionService().setAttribute("logedUserType", "ShipTo");
		}
		else if (soldtoshiptostring != null && soldtoshiptostring.equals("invalid"))
		{
			LOG.info("002|002 error");
			GlobalMessages.addErrorMessage(model, "b2b.accountdetails.error");
			model.addAttribute("nabsAccCode", fmCustomerAccountData.getNabsAccountCode());
			model.addAttribute("SoldToAccount", fmCustomerAccountData);
			if (getSessionService().getAttribute(WebConstants.SOLDTO_ACCOUNT_ID) == null
					|| getSessionService().getAttribute(WebConstants.SHIPTO_ACCOUNT_ID) == null)
			{
				getSessionService().setAttribute(WebConstants.SOLDTO_ACCOUNT_ID, fmCustomerAccountData.getUid());
				getSessionService().setAttribute(WebConstants.SHIPTO_ACCOUNT_ID, fmCustomerAccountData.getUid());
			}
		}
		LOG.info("size of address"+fmCustomerAccountData.getUnitAddress().size());
		if (!fmCustomerAccountData.getUnitAddress().isEmpty() && fmCustomerAccountData.getUnitAddress().size() > 0)
		{
			model.addAttribute("country",
					i18NFacade.getCountryForIsocode(fmCustomerAccountData.getUnitAddress().get(0).getCountry().getIsocode()));
			model.addAttribute("regionData",
					i18NFacade.getRegionsForCountryIso(fmCustomerAccountData.getUnitAddress().get(0).getCountry().getIsocode()));
		}

		model.addAttribute("ShipToAccount", fmCustomerAccountData);
	}

	protected void getCurrentUserLoyaltyPoints(final FMCustomerData currentUser, final Model model)
	{
		if (currentUser.getLoyaltySignup() !=null && currentUser.getLoyaltySignup() == true)
		{
			final LoyaltyHistoryHelper loyaltyHelper = new LoyaltyHistoryHelper();
			final LoyaltyHistoryRequestDTO requestDto = new LoyaltyHistoryRequestDTO();
			requestDto.setMembership_Id(currentUser.getCrmContactId());
			requestDto.setFlag("P");
			LoyaltyHistoryResponseDTO responseDto = null;
			try
			{
				responseDto = (LoyaltyHistoryResponseDTO) loyaltyHelper.sendSOAPMessage(requestDto);
			}
			catch (final UnsupportedOperationException e)
			{
				LOG.info("exception " + e.getMessage());

			}
			catch (final ClassNotFoundException e)
			{
				// YTODO Auto-generated catch block
				LOG.info("exception " + e.getMessage());
			}
			catch (final SOAPException e)
			{
				// YTODO Auto-generated catch block
				LOG.info("exception " + e.getMessage());
			}
			catch (final IOException e)
			{
				// YTODO Auto-generated catch block
				LOG.info("exception " + e.getMessage());
			}
			catch (final Exception e)
			{
				// YTODO: handle exception
				LOG.info("exception " + e.getMessage());
			}
			if (responseDto != null)
			{
				getSessionService().setAttribute(WebConstants.BALANCE_LOYALTY_POINTS, responseDto.getBalancePoints());
			}
			else
			{
				GlobalMessages.addErrorMessage(model, "no response from crm");
				getSessionService().setAttribute(WebConstants.BALANCE_LOYALTY_POINTS, 0);
			}
			model.addAttribute("TotalPoints", getSessionService().getAttribute(WebConstants.BALANCE_LOYALTY_POINTS));
		}
		else
		{
			model.addAttribute("TotalPoints", "0");
		}

		getSessionService().setAttribute(WebConstants.LOYALTY_SOLDTO_ACCOUNT_ID, currentUser.getFmunit().getUid());
	}

}
