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

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.federalmogul.core.constants.FmCoreConstants;
import com.federalmogul.core.util.FMUtils;
import com.federalmogul.facades.product.data.TSCLocationData;
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

import de.hybris.platform.acceleratorservices.customer.CustomerLocationService;
import de.hybris.platform.acceleratorservices.store.data.UserLocationData;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;

import de.hybris.platform.cms2.model.pages.AbstractPageModel;
import de.hybris.platform.cms2.model.pages.ContentPageModel;
import de.hybris.platform.commercefacades.storefinder.StoreFinderFacade;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commerceservices.customer.DuplicateUidException;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.store.data.GeoPoint;
import de.hybris.platform.commerceservices.storefinder.data.StoreFinderSearchPageData;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.storelocator.exception.GeoLocatorException;
import de.hybris.platform.storelocator.exception.MapServiceException;


/**
 * Controller for store locator search and detail pages.
 */

@Controller
@Scope("tenant")
@RequestMapping(value = "/where-to-buy")
public class StoreLocatorPageController extends AbstractSearchPageController
{
	private static final Logger LOG = Logger.getLogger(StoreLocatorPageController.class);

	private static final String STORE_FINDER_CMS_PAGE_LABEL = "storefinder";
	private static final String GOOGLE_API_KEY_ID = "googleApiKey";
	private static final String GOOGLE_API_VERSION = "googleApiVersion";

	@Resource(name = "configurationService")
	private ConfigurationService configurationService;

	@Resource(name = "storefinderBreadcrumbBuilder")
	private StorefinderBreadcrumbBuilder storefinderBreadcrumbBuilder;

	@Resource(name = "storeFinderFacade")
	private StoreFinderFacade storeFinderFacade;

	@Resource(name = "fmstoreFinderFacades")
	FMStoreFinderFacades fmstoreFinderFacades;

	@Resource(name = "customerLocationService")
	private CustomerLocationService customerLocationService;


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
	@RequestMapping(method = RequestMethod.GET)
	public String getStoreFinderPage(final Model model) throws CMSItemNotFoundException
	{
		setUpPageForms(model);
		model.addAttribute(WebConstants.BREADCRUMBS_KEY, storefinderBreadcrumbBuilder.getBreadcrumbs());
		storeCmsPageInModel(model, getStoreFinderPage());
		setUpMetaDataForContentPage(model, (ContentPageModel) getStoreFinderPage());
		return ControllerConstants.Views.Pages.StoreFinder.StoreFinderSearchPage;
	}

	@RequestMapping(value = "/{brand}", method = RequestMethod.GET)
	public String getStoreFinderBrandPage(final Model model, @PathVariable("brand") final String brand)
			throws CMSItemNotFoundException
	{
		setUpPageForms(model);	
		final StoreFinderForm storeFinderForm = new StoreFinderForm();
		storeFinderForm.setBrand(brand);
		model.addAttribute("storeFinderForm", storeFinderForm);
		model.addAttribute(WebConstants.BREADCRUMBS_KEY, storefinderBreadcrumbBuilder.getBreadcrumbs());
		storeCmsPageInModel(model, getStoreFinderPage());
		setUpMetaDataForContentPage(model, (ContentPageModel) getStoreFinderPage());
		return ControllerConstants.Views.Pages.StoreFinder.StoreFinderSearchPage;
	}

	@RequestMapping(value = "/findStores", method = RequestMethod.POST, params =
		{ "q", "brand", "radius", "showNearest", "shopType" })
		public String findStores(@RequestParam(value = "page", defaultValue = "0") final int page,
				@RequestParam(value = "show", defaultValue = "All") final AbstractSearchPageController.ShowMode showMode,
				@RequestParam(value = "sort", required = false) final String sortCode,
				@RequestParam(value = "q") final String locationQuery, @RequestParam(value = "brand") final String brand,
				@RequestParam(value = "radius") String radius,
				@RequestParam(value = "showNearest") final String showNearest,
				@RequestParam(value = "shopType") final String shopType,final StoreFinderForm storeFinderForm,final Model model) throws GeoLocatorException, MapServiceException, CMSItemNotFoundException, IOException, NumberFormatException, ParseException
		 {  
			if(StringUtils.isBlank(radius)){
				storeFinderForm.setRadius(FmCoreConstants.THRESHOLD_RADIUS);
				radius=FmCoreConstants.THRESHOLD_RADIUS;
			}
			String countryIso = storeFinderForm.getCountry()!=null?storeFinderForm.getCountry():"US";
			storeFinderForm.setCountry(countryIso);
			processWhereToBuyRequest(model,locationQuery,page,sortCode,0,showMode,new Integer(showNearest),radius,storeFinderForm,shopType,brand);
			model.addAttribute("storeFinderForm", storeFinderForm);
			storeCmsPageInModel(model, getStoreFinderPage());
			return ControllerConstants.Views.Pages.StoreFinder.StoreFinderSearchPage;
		 }

	// Method to get the empty search form --- Not Required--should be cleaned up while refactor
	@RequestMapping(value = "/inputZipCodeSearchGet")
	public String getStorePickupPage(final Model model, @ModelAttribute("tscLocationData") final TSCLocationData tscLocationData)
			throws DuplicateUidException, CMSItemNotFoundException
	{
		LOG.info("Inside get Method in controller");
		final ModelAndView modelAndView = new ModelAndView();
		model.addAttribute("tscLocationData", tscLocationData);
    	model.addAttribute(WebConstants.BREADCRUMBS_KEY, storefinderBreadcrumbBuilder.getBreadcrumbs());
		storeCmsPageInModel(model, getStoreFinderPage());
		setUpMetaDataForContentPage(model, (ContentPageModel) getStoreFinderPage());
		modelAndView.setViewName("fmcartPageStoreFinder");
		modelAndView.addObject("tscLocationData", tscLocationData);
		setUpPageForms(model);
		model.addAttribute(WebConstants.BREADCRUMBS_KEY, storefinderBreadcrumbBuilder.getBreadcrumbs());
		storeCmsPageInModel(model, getStoreFinderPage());
		setUpMetaDataForContentPage(model, (ContentPageModel) getStoreFinderPage());
		return ControllerConstants.Views.Pages.Cart.StoreFinderSearchPageForCart;
	}

	//Not Required--should be cleaned up while refactor
	@RequestMapping(value = "/inputZipCodeSearchPost/{q}", method = RequestMethod.POST)
	public String findStoress(
			@RequestParam(value = "page", defaultValue = "0") final int page,
			@PathVariable("q") final String locationQuery,
			@RequestParam(value = "show", defaultValue = "Page") final AbstractSearchPageController.ShowMode showMode,
			@RequestParam(value = "sort", required = false) final String sortCode, final StoreFinderForm storeFinderForm,
			final Model model) throws GeoLocatorException, MapServiceException, CMSItemNotFoundException, IOException, NumberFormatException, ParseException
	{
		processWhereToBuyRequest(model,locationQuery,page,sortCode,10,showMode,null,null,storeFinderForm,null,null);
		storeCmsPageInModel(model, getStoreFinderPage());
		return "pages/fm/ajax/summaryDetails";
	}

	@ModelAttribute("googleApiVersion")
	public String getGoogleApiVersion()
	{
		return configurationService.getConfiguration().getString(GOOGLE_API_VERSION);
	}

	//this method is never used as in there are no flows from ui to controller with this url path. Check with the team and remove it
	//during clean up
	@RequestMapping(value = "/position", method =
	{ RequestMethod.GET, RequestMethod.POST })
	public String searchByCurrentPosition(@RequestParam(value = "page", defaultValue = "0") final int page,
			@RequestParam(value = "show", defaultValue = "Page") final AbstractSearchPageController.ShowMode showMode,
			@RequestParam(value = "sort", required = false) final String sortCode, final StorePositionForm storePositionForm,
			final StoreFinderForm storeFinderForm, final Model model) throws GeoLocatorException, MapServiceException,
			CMSItemNotFoundException
	{
		final GeoPoint geoPoint = new GeoPoint();
		geoPoint.setLatitude(storePositionForm.getLatitude());
		geoPoint.setLongitude(storePositionForm.getLongitude());
		setUpSearchResultsForPosition(geoPoint, createPageableData(page, getStoreLocatorPageSize(), sortCode, showMode), model,
				storeFinderForm);
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


	protected void setUpSearchResultsForPositionPickup(final GeoPoint geoPoint, final PageableData pageableData, final Model model)
	{
		// Run the location search & populate the model
		final StoreFinderSearchPageData<PointOfServiceData> searchResult = storeFinderFacade.positionSearch(geoPoint, pageableData,
				100);
		final List<PointOfServiceData> pos = new ArrayList<PointOfServiceData>();
		for (int i = 0; i < searchResult.getResults().size(); i++)
		{
			if (searchResult.getResults().get(i).getShopType() != null
					&& "WAREHOUSE".equalsIgnoreCase(searchResult.getResults().get(i).getShopType()))
			{
				pos.add(searchResult.getResults().get(i));
				LOG.info("pos Shop Type ==>" + searchResult.getResults().get(i).getShopType());
			}
		}
		if (pos != null && !pos.isEmpty())
		{
			searchResult.setResults(pos);
		}
		model.addAttribute("searchResult", searchResult);
		setUpPageData(model, searchResult, storefinderBreadcrumbBuilder.getBreadcrumbsForCurrentPositionSearch());
		setUpPosition(model, geoPoint);
		setUpNoResultsErrorMessage(model, searchResult);
	}

	protected void setUpSearchResultsForLocation(final GeoPoint geoPoint, final PageableData pageableData, final Model model,Integer showNearest,String radius,
			final StoreFinderForm storeFinderForm,String shopType, String brand, String partType, String subBrand) throws NumberFormatException, ParseException
	{
		// Run the location search & populate the model
		Double maxRadius = null;
		Double actualRadius = null;
		if (null==radius||radius.isEmpty() || radius.equals("?"))
		{
			maxRadius = null;
			actualRadius=null;
		}else{
			maxRadius = Double.parseDouble(radius) * FmCoreConstants.MILESTOKMCONVERSIONRATIO; //miles to km converted to
			actualRadius=Double.parseDouble(radius);
		}			
		final StoreFinderSearchPageData<PointOfServiceData> searchResult = fmstoreFinderFacades.positionSearch(geoPoint, pageableData,
				maxRadius,shopType,brand,partType,subBrand,storeFinderForm.getCountry(),FmCoreConstants.MILES);

		final GeoPoint newGeoPoint = new GeoPoint();
		final List<PointOfServiceData> pointOfServiceList = new ArrayList<PointOfServiceData>();
		for (int i = 0; i < searchResult.getResults().size(); i++)
		{
			final String[] mapRadius = searchResult.getResults().get(i).getFormattedDistance().split(" "+FmCoreConstants.MILES);
			if((null==actualRadius||actualRadius >= Double.parseDouble(mapRadius[0].toString()))
					&& (showNearest > pointOfServiceList.size()))
			{
				pageableData.setPageSize(searchResult.getResults().size());
				pointOfServiceList.add(searchResult.getResults().get(i));
				newGeoPoint.setLatitude(searchResult.getSourceLatitude());
				newGeoPoint.setLongitude(searchResult.getSourceLongitude());
			}
		}
		searchResult.setResults(pointOfServiceList);
		model.addAttribute("searchResult", searchResult);
		updateLocalUserPreferences(newGeoPoint, searchResult.getLocationText());
		setUpPageData(model, searchResult, storefinderBreadcrumbBuilder.getBreadcrumbsForCurrentPositionSearch());
		setUpPosition(model, newGeoPoint);
		setUpNoResultsErrorMessage(model, searchResult);
	}
	
	//This method is also not required...Need to check with team and remove it during refactor
	protected void setUpSearchResultsForPosition(final GeoPoint geoPoint, final PageableData pageableData, final Model model,
			final StoreFinderForm storeFinderForm)
	{
		final StoreFinderSearchPageData<PointOfServiceData> searchResult = storeFinderFacade.positionSearch(geoPoint, pageableData,
				(Double.parseDouble(storeFinderForm.getRadius()) * 1.5));
		final GeoPoint newGeoPoint = new GeoPoint();

		final List<PointOfServiceData> pointOfServiceList = new ArrayList<PointOfServiceData>();
		for (int i = 0; i < searchResult.getResults().size(); i++)
		{
			final String[] mapRadius = searchResult.getResults().get(i).getFormattedDistance().split(" Miles");
			if (storeFinderForm.getBrand().equalsIgnoreCase(searchResult.getResults().get(i).getBrand())
					&& (("".equalsIgnoreCase(storeFinderForm.getShopType()) || storeFinderForm.getShopType() == null || "null"
							.equalsIgnoreCase(storeFinderForm.getShopType())) ? true : storeFinderForm.getShopType().equalsIgnoreCase(
							searchResult.getResults().get(i).getShopType()))
					&& (Double.valueOf(storeFinderForm.getRadius()) >= Double.valueOf(mapRadius[0].toString()))
					&& (Integer.parseInt(storeFinderForm.getShowNearest()) > pointOfServiceList.size()))
			{
				pageableData.setPageSize(searchResult.getResults().size());
				pointOfServiceList.add(searchResult.getResults().get(i));
				newGeoPoint.setLatitude(searchResult.getSourceLatitude());
				newGeoPoint.setLongitude(searchResult.getSourceLongitude());
			}
		}
		searchResult.getResults().size();
		searchResult.setResults(pointOfServiceList);
		model.addAttribute("searchResult", searchResult);
		updateLocalUserPreferences(newGeoPoint, searchResult.getLocationText());
		setUpPageData(model, searchResult, storefinderBreadcrumbBuilder.getBreadcrumbsForCurrentPositionSearch());
		setUpPosition(model, newGeoPoint);
		setUpNoResultsErrorMessage(model, searchResult);
	}

	protected void updateLocalUserPreferences(final GeoPoint geoPoint, final String location)
	{
		final UserLocationData userLocationData = new UserLocationData();
		userLocationData.setSearchTerm(location);
		userLocationData.setPoint(geoPoint);
		customerLocationService.setUserLocation(userLocationData);
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
				pageableData, 100);
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

	@Override
	public PageableData createPageableData(final int pageNumber, final int pageSize, final String sortCode, final ShowMode showMode)
	{
		final PageableData pageableData = new PageableData();
		pageableData.setCurrentPage(pageNumber);
		pageableData.setSort(sortCode);

		if (ShowMode.All == showMode)
		{
			pageableData.setPageSize(260000);
		}
		else
		{
			pageableData.setPageSize(pageSize);
		}
		return pageableData;
	}
	
	private void processWhereToBuyRequest(Model model,String locationQuery,int page,String sortCode,int pageSize,ShowMode showMode,Integer showNearest,String radius,StoreFinderForm storeFinderForm,String shopType,String brand) throws NumberFormatException, ParseException, IOException
	{	
		model.addAttribute("shopType", shopType);
		String[] brandPartTypeArray=processInputBrand(brand);
		brand=brandPartTypeArray[0];
		String partType=brandPartTypeArray[1];
		String subBrand=null;
		final String sanitizedSearchQuery = XSSFilterUtil.filter(locationQuery);
		String latlng = FMUtils.getLocationByLocationQuery(locationQuery);		
		Double latitude = Double.parseDouble(FMUtils.extractMiddle(latlng, "\"lat\" :", ","));
		Double longitude = Double.parseDouble(FMUtils.extractMiddle(latlng, "\"lng\" :"));
		
		if (latitude != null && longitude != null)
		{
			final GeoPoint geoPoint = new GeoPoint();
			geoPoint.setLatitude(latitude.doubleValue());
			geoPoint.setLongitude(longitude.doubleValue());
			if(null!=brand && null!=shopType)
			{	
				setUpSearchResultsForLocation(geoPoint, createPageableData(page, pageSize, sortCode, showMode), model,
					showNearest,radius,storeFinderForm,shopType,brand,partType,subBrand);
			}else{
				setUpSearchResultsForPositionPickup(geoPoint, createPageableData(page, pageSize, sortCode, showMode), model);
			}
		}
		else if (StringUtils.isNotBlank(sanitizedSearchQuery))
		{
			setUpSearchResultsForLocationQuery(sanitizedSearchQuery,
					createPageableData(page, pageSize, sortCode, showMode), model);
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
	}
	
	private String[] processInputBrand(String brand){
		String[] brandPartTypeArray=new String[2];
		if(StringUtils.isNotBlank(brand)) {
		  if(brand.trim().contains(FmCoreConstants.WAGNER))
		  {
			  brandPartTypeArray = brand.trim().split(" ");
		  }
		  else if(brand.trim().contains(FmCoreConstants.MOOG))
		  {
			  brandPartTypeArray = brand.trim().split(" ");
			  brandPartTypeArray[1]=null;
		  }else{
			  brandPartTypeArray[0]=brand;
			  brandPartTypeArray[1]=null;	
		  }
	    }
	    return brandPartTypeArray;
	}

}
