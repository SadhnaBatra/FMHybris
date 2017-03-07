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

import de.hybris.platform.b2b.services.B2BOrderService;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;
import de.hybris.platform.cms2.model.pages.ContentPageModel;
import de.hybris.platform.commercefacades.i18n.I18NFacade;
import de.hybris.platform.commercefacades.order.CartFacade;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.storefinder.StoreFinderFacade;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commerceservices.customer.DuplicateUidException;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.store.data.GeoPoint;
import de.hybris.platform.commerceservices.storefinder.data.StoreFinderSearchPageData;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.storelocator.exception.GeoLocatorException;
import de.hybris.platform.storelocator.exception.MapServiceException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.federalmogul.core.model.TSCLocationModel;
import com.federalmogul.facades.store.finder.FMStoreFinderFacades;
import com.federalmogul.storefront.breadcrumb.Breadcrumb;
import com.federalmogul.storefront.breadcrumb.impl.StorefinderBreadcrumbBuilder;
import com.federalmogul.storefront.constants.WebConstants;
import com.federalmogul.storefront.controllers.ControllerConstants;
import com.federalmogul.storefront.controllers.util.GlobalMessages;
import com.federalmogul.storefront.forms.StoreFinderForm;
import com.federalmogul.storefront.forms.StorePositionForm;
import com.federalmogul.storefront.util.MetaSanitizerUtil;
import com.federalmogul.storefront.util.XSSFilterUtil;


/**
 * Controller for store locator search and detail pages.
 */

@Controller
@Scope("tenant")
@RequestMapping(value = "/cart-store-finder")
public class FMStoreLocatorPageController extends AbstractSearchPageController
{

	private static final Logger LOG = Logger.getLogger(FMStoreLocatorPageController.class);

	private static final String STORE_FINDER_CMS_PAGE_LABEL = "cartPage";
	private static final String GOOGLE_API_KEY_ID = "googleApiKey";
	private static final String GOOGLE_API_VERSION = "googleApiVersion";
	private static final String FMCART_STORE_FINDER_CMS_PAGE = "cartPage";
	private static final String CART_CMS_PAGE = "cartPage";


	@Resource(name = "configurationService")
	private ConfigurationService configurationService;

	@Resource(name = "storefinderBreadcrumbBuilder")
	private StorefinderBreadcrumbBuilder storefinderBreadcrumbBuilder;

	@Resource(name = "storeFinderFacade")
	private StoreFinderFacade storeFinderFacade;

	/*
	 * @Autowired private UserService userservice;
	 */

	@Resource(name = "fmstoreFinderFacades")
	FMStoreFinderFacades fmstoreFinderFacades;

	@Resource
	private B2BOrderService b2bOrderService;


	@Autowired
	private ModelService modelService;

	@Deprecated
	@Resource(name = "cartFacade")
	private CartFacade cartFacade;

	@Resource(name = "i18NFacade")
	private I18NFacade i18NFacade;


	@Autowired
	private SessionService service;

	// Method to get the empty search form
	@RequestMapping(value = "/inputZipCodeSearchGet/{shiptoAddress}")
	public String getStorePickupPage(@PathVariable("shiptoAddress") final String shiptoAddress, final Model model)
			throws DuplicateUidException, CMSItemNotFoundException
	{

		LOG.info("Inside get Method in controller");
		final CartData cartData = cartFacade.getSessionCart();
		final ModelAndView modelAndView = new ModelAndView();
		final StoreFinderForm storeFinderForm = new StoreFinderForm();
		clearDCInfo(cartData.getCode());
		try
		{
			if (null != shiptoAddress)
			{
				final Map<TSCLocationModel, Integer> storeList = fmstoreFinderFacades.getTSCLocationByZipCode(shiptoAddress);
				model.addAttribute("storeList", storeList);
				storeFinderForm.setQ(shiptoAddress);
				LOG.info("ListValues object's value in Controller ==>" + storeList);
			}

		}
		catch (final Exception e)
		{
			LOG.info("fmStoreFinderFacades.getTSCLocationByZipCode(postCode) catch bloack");
		}
		model.addAttribute(WebConstants.BREADCRUMBS_KEY, storefinderBreadcrumbBuilder.getBreadcrumbs());
		storeCmsPageInModel(model, getContentPageForLabelOrId(CART_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(CART_CMS_PAGE));
		modelAndView.setViewName("fmcartPageStoreFinder");
		model.addAttribute("storeFinderForm", storeFinderForm);
		return ControllerConstants.Views.Pages.Cart.StoreFinderSearchPageForCart;
	}

	@RequestMapping(value = "/setStore/{storeId}", method =
	{ RequestMethod.GET, RequestMethod.POST })
	public String addItemToCart(@PathVariable("storeId") final String storeId, final Model model) throws ClassNotFoundException,
			SQLException, DuplicateUidException, CMSItemNotFoundException, IOException
	{

		LOG.info("Inside setStore method inside Controller");
		final TSCLocationModel store = fmstoreFinderFacades.getStore(storeId);
		final AddressData storeAddress = new AddressData();
		storeAddress.setFirstName(store.getTSCLocId());
		storeAddress.setLine1(store.getAddressLine1());
		if (store.getAddressLine2() != null)
		{
			storeAddress.setLine2(store.getAddressLine2());
		}
		storeAddress.setTown(store.getTown());
		storeAddress.setRegion(i18NFacade.getRegion(store.getCountry(), (store.getCountry() + "-" + store.getState())));
		storeAddress.setCountry(i18NFacade.getCountryForIsocode(store.getCountry()));
		storeAddress.setPostalCode(store.getZipcode());
		service.setAttribute("pickUpStore", "true");
		service.setAttribute("tscaddress", storeAddress);
		service.setAttribute("storeDetail", store);
		model.addAttribute("storePickupAddress", storeAddress);
		return "pages/fm/ajax/summaryDetails";
	}



	@RequestMapping(value = "/inputZipCodeSearchPost", method =
	{ RequestMethod.POST, RequestMethod.GET })
	public String getStoreByZipCode(@ModelAttribute("storeFinderForm") final StoreFinderForm storeFinderForm, final Model model)
			throws DuplicateUidException, CMSItemNotFoundException, IOException
	{
		LOG.info("inside POST Method in controller");
		final ModelAndView modelAndView = new ModelAndView();
	
		try
		{
			final Map<TSCLocationModel, Integer> storeList = fmstoreFinderFacades.getTSCLocationByZipCode(storeFinderForm.getQ());

			model.addAttribute("storeList", storeList);

			LOG.info("ListValues object's value in Controller ==>" + storeList);

		}
		catch (final Exception e)
		{
			LOG.info("fmStoreFinderFacades.getTSCLocationByZipCode(postCode) catch bloack");
		}

		modelAndView.setViewName("fmcartPageStoreFinder");

		model.addAttribute("storeFinderForm", storeFinderForm);

		model.addAttribute(WebConstants.BREADCRUMBS_KEY, storefinderBreadcrumbBuilder.getBreadcrumbs());
		storeCmsPageInModel(model, getContentPageForLabelOrId(CART_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(CART_CMS_PAGE));

		return ControllerConstants.Views.Pages.Cart.StoreFinderSearchPageForCart;
		//return "pages/fm/ajax/summaryDetails";
	}



	private void clearDCInfo(final String orderCode)
	{
		final AbstractOrderModel abstractOrderModel = b2bOrderService.getAbstractOrderForCode(orderCode);
		for (final AbstractOrderEntryModel abstractOrderEntry : abstractOrderModel.getEntries())
		{
			abstractOrderEntry.setDistrubtionCenter(null);
			modelService.save(abstractOrderEntry);
			modelService.refresh(abstractOrderEntry);
		}
	}




	@ModelAttribute("googleApiVersion")
	public String getGoogleApiVersion()
	{
		return configurationService.getConfiguration().getString(GOOGLE_API_VERSION);
	}

	@ModelAttribute("googleApiKey")
	public String getGoogleApiKey(final HttpServletRequest request)
	{
		final String googleApiKey = getHostConfigService().getProperty(GOOGLE_API_KEY_ID, request.getServerName());
		if (StringUtils.isEmpty(googleApiKey))
		{
			LOG.warn("No Google API key found for server: " + request.getServerName());
		}
		return googleApiKey;
	}

	// Method to get the empty search form
	@RequestMapping(value = "/fetchPage", method = RequestMethod.GET)
	public String getStoreFinderPage(final Model model) throws CMSItemNotFoundException, IOException
	{
		LOG.info("Inside getStoreFinderPage Method ==>");




		setUpPageForms(model);
		storeCmsPageInModel(model, getContentPageForLabelOrId(FMCART_STORE_FINDER_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FMCART_STORE_FINDER_CMS_PAGE));
		model.addAttribute(WebConstants.BREADCRUMBS_KEY, storefinderBreadcrumbBuilder.getBreadcrumbs());
		storeCmsPageInModel(model, getStoreFinderPage());
		setUpMetaDataForContentPage(model, (ContentPageModel) getStoreFinderPage());
		return ControllerConstants.Views.Pages.Cart.StoreFinderSearchPageForCart;
	}







	@RequestMapping(method = RequestMethod.GET, params = "q")
	public String findStores(@RequestParam(value = "page", defaultValue = "0") final int page,
			@RequestParam(value = "show", defaultValue = "Page") final AbstractSearchPageController.ShowMode showMode,
			@RequestParam(value = "sort", required = false) final String sortCode,
			@RequestParam(value = "q") final String locationQuery,
			@RequestParam(value = "latitude", required = false) final Double latitude,
			@RequestParam(value = "longitude", required = false) final Double longitude, final StoreFinderForm storeFinderForm,
			final Model model, final BindingResult bindingResult) throws GeoLocatorException, MapServiceException,
			CMSItemNotFoundException
	{
		final String sanitizedSearchQuery = XSSFilterUtil.filter(locationQuery);

		if (latitude != null && longitude != null)
		{
			final GeoPoint geoPoint = new GeoPoint();
			geoPoint.setLatitude(latitude.doubleValue());
			geoPoint.setLongitude(longitude.doubleValue());

			setUpSearchResultsForPosition(geoPoint, createPageableData(page, getStoreLocatorPageSize(), sortCode, showMode), model);

		}
		else if (StringUtils.isNotBlank(sanitizedSearchQuery))
		{

			setUpSearchResultsForLocationQuery(sanitizedSearchQuery,
					createPageableData(page, getStoreLocatorPageSize(), sortCode, showMode), model);
			setUpMetaData(sanitizedSearchQuery, model);
			setUpPageForms(model);
			setUpPageTitle(sanitizedSearchQuery, model);

		}
		else
		{

			GlobalMessages.addErrorMessage(model, "storelocator.error.no.results.subtitle");
			model.addAttribute(WebConstants.BREADCRUMBS_KEY,
					storefinderBreadcrumbBuilder.getBreadcrumbsForLocationSearch(sanitizedSearchQuery));

		}

		storeCmsPageInModel(model, getStoreFinderPage());

		return ControllerConstants.Views.Pages.StoreFinder.StoreFinderSearchPage;
	}




	@RequestMapping(value = "/position", method =
	{ RequestMethod.GET, RequestMethod.POST })
	public String searchByCurrentPosition(@RequestParam(value = "page", defaultValue = "0") final int page,
			@RequestParam(value = "show", defaultValue = "Page") final AbstractSearchPageController.ShowMode showMode,
			@RequestParam(value = "sort", required = false) final String sortCode, final StorePositionForm storePositionForm,
			final Model model) throws GeoLocatorException, MapServiceException, CMSItemNotFoundException
	{
		final GeoPoint geoPoint = new GeoPoint();
		geoPoint.setLatitude(storePositionForm.getLatitude());
		geoPoint.setLongitude(storePositionForm.getLongitude());

		setUpSearchResultsForPosition(geoPoint, createPageableData(page, getStoreLocatorPageSize(), sortCode, showMode), model);
		setUpPageForms(model);
		storeCmsPageInModel(model, getStoreFinderPage());

		return ControllerConstants.Views.Pages.StoreFinder.StoreFinderSearchPage;
	}

	// setup methods to populate the model
	protected void setUpMetaData(final String locationQuery, final Model model)
	{
		model.addAttribute("metaRobots", "no-index,follow");
		final String metaKeywords = MetaSanitizerUtil.sanitizeKeywords(locationQuery);
		final String metaDescription = MetaSanitizerUtil.sanitizeDescription(getSiteName() + " "
				+ getMessageSource().getMessage("storeFinder.meta.description.results", null, getI18nService().getCurrentLocale())
				+ " " + locationQuery);
		super.setUpMetaData(model, metaKeywords, metaDescription);
	}

	protected void setUpNoResultsErrorMessage(final Model model, final StoreFinderSearchPageData<PointOfServiceData> searchResult)
	{
		if (searchResult.getResults().isEmpty())
		{
			GlobalMessages.addErrorMessage(model, "storelocator.error.no.results.subtitle");
		}
	}

	protected void setUpPageData(final Model model, final StoreFinderSearchPageData<PointOfServiceData> searchResult,
			final List<Breadcrumb> breadCrumbsList)
	{
		populateModel(model, searchResult, ShowMode.Page);
		model.addAttribute("locationQuery", StringEscapeUtils.escapeHtml(searchResult.getLocationText()));
		model.addAttribute(WebConstants.BREADCRUMBS_KEY, breadCrumbsList);
	}

	protected void setUpSearchResultsForPosition(final GeoPoint geoPoint, final PageableData pageableData, final Model model)
	{
		// Run the location search & populate the model
		final StoreFinderSearchPageData<PointOfServiceData> searchResult = storeFinderFacade.positionSearch(geoPoint, pageableData);

		setUpPageData(model, searchResult, storefinderBreadcrumbBuilder.getBreadcrumbsForCurrentPositionSearch());
		setUpPosition(model, geoPoint);
		setUpNoResultsErrorMessage(model, searchResult);
	}

	protected void setUpPosition(final Model model, final GeoPoint geoPoint)
	{
		model.addAttribute("geoPoint", geoPoint);
	}

	protected void setUpSearchResultsForLocationQuery(final String locationQuery, final PageableData pageableData,
			final Model model)
	{
		// Run the location search & populate the model
		final StoreFinderSearchPageData<PointOfServiceData> searchResult = storeFinderFacade.locationSearch(locationQuery,
				pageableData);

		setUpPageData(model, searchResult, storefinderBreadcrumbBuilder.getBreadcrumbsForLocationSearch(locationQuery));
		setUpNoResultsErrorMessage(model, searchResult);
	}

	protected void setUpPageForms(final Model model)
	{
		final StoreFinderForm storeFinderForm = new StoreFinderForm();
		final StorePositionForm storePositionForm = new StorePositionForm();
		model.addAttribute("storeFinderForm", storeFinderForm);
		model.addAttribute("storePositionForm", storePositionForm);
	}

	protected void setUpPageTitle(final String searchText, final Model model)
	{
		storeContentPageTitleInModel(
				model,
				getPageTitleResolver().resolveContentPageTitle(
						getMessageSource().getMessage("storeFinder.meta.title", null, getI18nService().getCurrentLocale()) + " "
								+ searchText));
	}

	protected AbstractPageModel getStoreFinderPage() throws CMSItemNotFoundException
	{
		return getContentPageForLabelOrId(STORE_FINDER_CMS_PAGE_LABEL);
	}

	/**
	 * Get the default search page size.
	 * 
	 * @return the number of results per page, <tt>0</tt> (zero) indicated 'default' size should be used
	 */
	protected int getStoreLocatorPageSize()
	{
		return getSiteConfigService().getInt("storefront.storelocator.pageSize", 0);
	}
}
