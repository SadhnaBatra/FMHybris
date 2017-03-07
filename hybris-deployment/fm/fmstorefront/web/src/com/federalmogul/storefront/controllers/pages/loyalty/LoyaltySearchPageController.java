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
package com.federalmogul.storefront.controllers.pages.loyalty;

import de.hybris.platform.acceleratorcms.model.components.SearchBoxComponentModel;
import de.hybris.platform.acceleratorservices.controllers.page.PageType;
import de.hybris.platform.acceleratorservices.customer.CustomerLocationService;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.servicelayer.services.CMSComponentService;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.search.data.AutocompleteResultData;
import de.hybris.platform.commercefacades.search.data.AutocompleteSuggestionData;
import de.hybris.platform.commercefacades.search.data.SearchQueryData;
import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commerceservices.category.CommerceCategoryService;
import de.hybris.platform.commerceservices.search.facetdata.BreadcrumbData;
import de.hybris.platform.commerceservices.search.facetdata.ProductSearchPageData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.PaginationData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.util.Config;
import com.federalmogul.storefront.constants.WebConstants;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.federalmogul.storefront.breadcrumb.impl.LoyaltyHomeBreadcrumbBuilder;
import com.federalmogul.facades.search.B2BProductSearchFacade;
import com.federalmogul.storefront.breadcrumb.impl.SearchBreadcrumbBuilder;
import com.federalmogul.storefront.constants.WebConstants;
import com.federalmogul.storefront.controllers.ControllerConstants;
import com.federalmogul.storefront.controllers.pages.AbstractSearchPageController;
import com.federalmogul.storefront.forms.AdvancedSearchForm;
import com.federalmogul.storefront.util.MetaSanitizerUtil;
import com.federalmogul.storefront.util.XSSFilterUtil;
import com.google.common.collect.Lists;


/**
 * Controller for search page.
 */
@Controller
@Scope("tenant")
@RequestMapping("/lsearch")
public class LoyaltySearchPageController extends AbstractSearchPageController
{


	private static final Logger LOG = Logger.getLogger(LoyaltySearchPageController.class);

	private static final String ADVANCED_SEARCH_PRODUCT_IDS_DELIMITER = "storefront.advancedsearch.delimiter";
	private static final String ADVANCED_SEARCH_PRODUCT_IDS_DELIMITER_DEFAULT = ",";

	private static final String COMPONENT_UID_PATH_VARIABLE_PATTERN = "{componentUid:.*}";

	private static final String ADVANCED_SEARCH_RESULT_TYPE_CATALOG = "catalog";
	private static final String ADVANCED_SEARCH_RESULT_TYPE_ORDER_FORM = "order-form";

	private static final String SEARCH_CMS_PAGE_ID = "loyaltysearch";
	private static final String NO_RESULTS_CMS_PAGE_ID = "loyaltysearch"; //"loyaltysearchEmpty";

	private static final String NO_RESULTS_ADVANCED_PAGE_ID = "searchAdvancedEmpty";

	private static final String FUTURE_STOCK_ENABLED = "storefront.products.futurestock.enabled";

	private static final String INFINITE_SCROLL = "infiniteScroll";

	private static boolean isYMMSearch = false;



	@Resource(name = "b2bSolrProductSearchFacade")
	private B2BProductSearchFacade<ProductData> solrProductSearchFacade;

	@Resource(name = "b2bProductFlexibleSearchFacade")
	private B2BProductSearchFacade<ProductData> flexibleSearchProductSearchFacade;

	@Resource(name = "searchBreadcrumbBuilder")
	private SearchBreadcrumbBuilder searchBreadcrumbBuilder;

	@Resource(name = "customerLocationService")
	private CustomerLocationService customerLocationService;

	@Resource(name = "cmsComponentService")
	private CMSComponentService cmsComponentService;

	@Resource(name = "commerceCategoryService")
	private CommerceCategoryService commerceCategoryService;


        @Resource(name = "loyaltyBreadcrumbBuilder")
	protected LoyaltyHomeBreadcrumbBuilder loyaltyBreadcrumbBuilder;

	/*
	 * @Autowired private DefaultFMFitmentSearchFacade defaultFMFitmentSearchFacade;
	 */

	@RequestMapping(method = RequestMethod.GET, params = "!q")
	public String textSearch(@RequestParam(value = "text", defaultValue = StringUtils.EMPTY) final String searchText,
			final HttpServletRequest request, final Model model) throws CMSItemNotFoundException
	{
		LOG.info("########## textSearch ##################### 00 ");
		if (StringUtils.isNotBlank(searchText))
		{
			LOG.info("SERACH TEXT DATA " + searchText);
			final PageableData pageableData = createPageableData(0, getLoyaltySearchPageSize(), null, ShowMode.Page);
			final SearchStateData searchState = new SearchStateData();
			final SearchQueryData searchQueryData = new SearchQueryData();
			searchQueryData.setValue(XSSFilterUtil.filter(searchText));
			searchState.setQuery(searchQueryData);

			final ProductSearchPageData<SearchStateData, ProductData> searchPageData = solrProductSearchFacade.textSearch(
					searchState, pageableData);

			if (searchPageData == null)
			{
				storeCmsPageInModel(model, getContentPageForLabelOrId(NO_RESULTS_CMS_PAGE_ID));
			}
			else if (searchPageData.getKeywordRedirectUrl() != null)
			{
				// if the search engine returns a redirect, just
				return "redirect:" + searchPageData.getKeywordRedirectUrl();
			}
			else if (searchPageData.getPagination().getTotalNumberOfResults() == 0)
			{
				LOG.info("INSIDE NO RESULTSS::::::::::::::::::::::::::::::::::::::::::::SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
				model.addAttribute("searchPageData", searchPageData);
				storeCmsPageInModel(model, getContentPageForLabelOrId(NO_RESULTS_CMS_PAGE_ID));
				updatePageTitle(searchText, model);
			}
			else
			{
				storeContinueUrl(request);
				populateModel(model, searchPageData, ShowMode.Page);
				storeCmsPageInModel(model, getContentPageForLabelOrId(SEARCH_CMS_PAGE_ID));
				updatePageTitle(searchText, model);
			}
			getRequestContextData(request).setSearch(searchPageData);
			if (searchPageData != null)
			{
				model.addAttribute(
						WebConstants.BREADCRUMBS_KEY,
						loyaltyBreadcrumbBuilder.getRedeemBreadcrumbs("Search"));


                               
			}
		}
		else
		{
			storeCmsPageInModel(model, getContentPageForLabelOrId(NO_RESULTS_CMS_PAGE_ID));
		}

		addMetaData(model, "search.meta.description.results", searchText, "search.meta.description.on", PageType.PRODUCTSEARCH,
				"no-index,follow");
                model.addAttribute("TotalPoints", getSessionService().getAttribute(WebConstants.BALANCE_LOYALTY_POINTS));
		return getViewForPage(model);
	}

	@RequestMapping(method = RequestMethod.GET, params = "q")
	public String refineSearch(@RequestParam("q") final String searchQuery,
			@RequestParam(value = "page", defaultValue = "0") final int page,
			@RequestParam(value = "prodCount", defaultValue = "0") final int productCount,
			@RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode,
			@RequestParam(value = "sort", required = false) final String sortCode,
			@RequestParam(value = "text", required = false) final String searchText, final HttpServletRequest request,
			final Model model) throws CMSItemNotFoundException
	{
		LOG.info("########## refineSearch ##################### 00 ");
		int searchPageSize = getLoyaltySearchPageSize();
		
		if (productCount != 0)
		{
			searchPageSize = productCount;
		}
		final ProductSearchPageData<SearchStateData, ProductData> searchPageData = performSearch(searchQuery, page, showMode,
				sortCode, searchPageSize, false);

		LOG.info("########## refineSearch ##################### 11 ");

		populateModel(model, searchPageData, showMode);
		model.addAttribute("userLocation", customerLocationService.getUserLocation());

		if (searchPageData.getPagination().getTotalNumberOfResults() == 0)
		{
			LOG.info("########## refineSearch ##################### 22 ");
			updatePageTitle(searchPageData.getFreeTextSearch(), model);
			storeCmsPageInModel(model, getContentPageForLabelOrId(NO_RESULTS_CMS_PAGE_ID));
		}
		else
		{
			LOG.info("########## refineSearch ##################### 33 ");
			final List<BreadcrumbData<SearchStateData>> pagedata = searchPageData.getBreadcrumbs();
			final String categoryCode = searchPageData.getCategoryCode() == null ? "Shirts" : searchPageData
					.getCategoryCode();
			final CategoryModel categoryModel = commerceCategoryService.getCategoryForCode(categoryCode);
			final List<CategoryModel> rootCategoriesModelList = categoryModel.getCatalogVersion().getRootCategories();

			final List<CategoryModel> appliedCategories = new ArrayList<CategoryModel>();
			final List<CategoryModel> unAppliedCategories = new ArrayList<CategoryModel>();

			for (final CategoryModel rootCategory : rootCategoriesModelList)
			{
				for (final BreadcrumbData<SearchStateData> facet : pagedata)
				{
					LOG.info(rootCategory.getName() + " -RF- " + facet.getFacetName() + " -CV- " + facet.getFacetValueName());
					LOG.info("applied Facet :: " + facet.getFacetName());
					if (facet.getFacetName() != null && facet.getFacetName().equals("category"))
					{
						if (rootCategory.getName().equalsIgnoreCase(facet.getFacetValueName()))
						{
							LOG.info("Applied :: " + rootCategory.getName());
							appliedCategories.add(rootCategory);
						}

					}
				}
			}

			for (final CategoryModel rootCategory : rootCategoriesModelList)
			{
				if (!appliedCategories.contains(rootCategory))
				{
					LOG.info("Un Applied :: " + rootCategory.getName());
					unAppliedCategories.add(rootCategory);
				}
			}
			model.addAttribute("appliedCategories", appliedCategories);
			model.addAttribute("unAppliedCategories", unAppliedCategories);

			storeContinueUrl(request);
			updatePageTitle(searchPageData.getFreeTextSearch(), model);
			storeCmsPageInModel(model, getContentPageForLabelOrId(SEARCH_CMS_PAGE_ID));
		}
		LOG.info("########## refineSearch ##################### 44 ");

		model.addAttribute(WebConstants.BREADCRUMBS_KEY,loyaltyBreadcrumbBuilder.getRedeemBreadcrumbs("Search"));

		model.addAttribute(WebConstants.NUMBER_OF_PRODUCTS_PER_PAGE, searchPageSize);
		addMetaData(model, "search.meta.description.results", searchText, "search.meta.description.on", PageType.PRODUCTSEARCH,
				"no-index,follow");
                model.addAttribute("TotalPoints", getSessionService().getAttribute(WebConstants.BALANCE_LOYALTY_POINTS));
		return getViewForPage(model);
	}

	protected ProductSearchPageData<SearchStateData, ProductData> performSearch(final String searchQuery, final int page,
			final ShowMode showMode, final String sortCode, final int pageSize, final boolean populateMatrix)
	{
		LOG.info("searchQuery :: " + searchQuery);
		LOG.info("showMode :: " + showMode);
		LOG.info("sortCode :: " + sortCode);
		LOG.info("pageSize :: " + pageSize);
		LOG.info("populateMatrix :: " + populateMatrix);


		final PageableData pageableData = createPageableData(page, pageSize, sortCode, showMode);
		final SearchStateData searchState = new SearchStateData();
		final SearchQueryData searchQueryData = new SearchQueryData();
		searchQueryData.setValue(XSSFilterUtil.filter(searchQuery));
		searchState.setQuery(searchQueryData);

		LOG.info("Filter :: " + XSSFilterUtil.filter(searchQuery));
		
		LOG.info("searchQueryData :: " + searchQueryData);


		final ProductSearchPageData<SearchStateData, ProductData> pageData = solrProductSearchFacade.textSearch(searchState,
				pageableData, populateMatrix);
                   
		return pageData;
	}

	@RequestMapping(value = "/results", method = RequestMethod.GET)
	public String productListerSearchResults(@RequestParam("q") final String searchQuery,
			@RequestParam(value = "page", defaultValue = "0") final int page,
			@RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode,
			@RequestParam(value = "sort", required = false) final String sortCode,
			@RequestParam(value = "searchResultType", required = false) final String searchResultType,
			@RequestParam(value = "skuIndex", required = false, defaultValue = "0") final int skuIndex,
			@RequestParam(value = "isOrderForm", required = false, defaultValue = "false") final boolean isOrderForm,
			@RequestParam(value = "isOnlyProductIds", required = false, defaultValue = "false") final boolean isOnlyProductIds,
			@RequestParam(value = "isCreateOrderForm", required = false, defaultValue = "false") final boolean isCreateOrderForm,
			final Model model) throws CMSItemNotFoundException
	{

		final ProductSearchPageData<SearchStateData, ProductData> searchPageData = performAdvancedSearch(searchQuery,
				isOnlyProductIds, isCreateOrderForm, page, showMode, sortCode, searchResultType, model);

		final SearchResultsData<ProductData> searchResultsData = new SearchResultsData<ProductData>();

		searchResultsData.setResults(searchPageData.getResults());
		searchResultsData.setPagination(searchPageData.getPagination());

		model.addAttribute("searchResultsData", searchResultsData);
		model.addAttribute("skuIndex", Integer.valueOf(skuIndex));
		model.addAttribute("isOrderForm", Boolean.valueOf(isOrderForm));
		model.addAttribute("isCreateOrderForm", Boolean.valueOf(isCreateOrderForm));


		if (isCreateOrderForm)
		{
			model.addAttribute("searchResultType", ADVANCED_SEARCH_RESULT_TYPE_ORDER_FORM);
			final List<String> filterSkus = splitSkusAsList(searchQuery);
			model.addAttribute("filterSkus", filterSkus);
		}
		else
		{
			model.addAttribute("searchResultType", searchResultType);
		}

		return ControllerConstants.Views.Fragments.Product.ProductLister;
	}

	@RequestMapping(value = "/advanced", method = RequestMethod.GET)
	public String advanceSearchResults(
			@RequestParam(value = "keywords", required = false, defaultValue = StringUtils.EMPTY) String keywords,
			@RequestParam(value = "searchResultType", required = false, defaultValue = ADVANCED_SEARCH_RESULT_TYPE_CATALOG) final String searchResultType,
			@RequestParam(value = "inStockOnly", required = false, defaultValue = "false") final boolean inStockOnly,
			@RequestParam(value = "onlyProductIds", required = false, defaultValue = "false") final boolean onlyProductIds,
			@RequestParam(value = "isCreateOrderForm", required = false, defaultValue = "false") final boolean isCreateOrderForm,
			@RequestParam(value = "q", defaultValue = StringUtils.EMPTY) String searchQuery,
			@RequestParam(value = "page", defaultValue = "0") final int page,
			@RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode,
			@RequestParam(value = "sort", required = false) final String sortCode, final Model model)
			throws CMSItemNotFoundException
	{

		if (StringUtils.isNotBlank(keywords))
		{
			searchQuery = keywords;
		}
		else
		{
			if (StringUtils.isNotBlank(searchQuery))
			{
				keywords = StringUtils.split(searchQuery, ":")[0];
			}
		}

		performAdvancedSearch(searchQuery, onlyProductIds, isCreateOrderForm, page, showMode, sortCode, searchResultType, model);

		String metaInfoText = null;
		if (StringUtils.isEmpty(keywords))
		{
			metaInfoText = MetaSanitizerUtil.sanitizeDescription(getMessageSource().getMessage(
					"search.advanced.meta.description.title", null, getCurrentLocale()));
		}
		else
		{
			metaInfoText = MetaSanitizerUtil.sanitizeDescription(keywords);
		}

		model.addAttribute(WebConstants.BREADCRUMBS_KEY, searchBreadcrumbBuilder.getBreadcrumbs(null, metaInfoText, false));

		final AdvancedSearchForm form = new AdvancedSearchForm();
		form.setOnlyProductIds(Boolean.valueOf(onlyProductIds));
		form.setInStockOnly(Boolean.valueOf(inStockOnly));
		form.setKeywords(keywords);
		form.setCreateOrderForm(isCreateOrderForm);


		if (isCreateOrderForm)
		{
			form.setSearchResultType(ADVANCED_SEARCH_RESULT_TYPE_ORDER_FORM);
			final List<String> filterSkus = splitSkusAsList(keywords);
			form.setFilterSkus(filterSkus);
			form.setCreateOrderForm(Boolean.valueOf(false));
			form.setOnlyProductIds(Boolean.valueOf(true));
		}
		else
		{
			form.setSearchResultType(searchResultType);
		}

		model.addAttribute("advancedSearchForm", form);
		model.addAttribute("futureStockEnabled", Boolean.valueOf(Config.getBoolean(FUTURE_STOCK_ENABLED, false)));

		storeCmsPageInModel(model, getContentPageForLabelOrId(NO_RESULTS_ADVANCED_PAGE_ID));

		addMetaData(model, "search.meta.description.results", metaInfoText, "search.meta.description.on", PageType.PRODUCTSEARCH,
				"no-index,follow");

		return getViewForPage(model);
	}

	protected ProductSearchPageData<SearchStateData, ProductData> performAdvancedSearch(final String keywords,
			final boolean onlyProductIds, final boolean isCreateOrderForm, final int page, final ShowMode showMode,
			final String sortCode, final String searchResultType, final Model model)
	{
		ProductSearchPageData<SearchStateData, ProductData> searchPageData = createEmptySearchPageData();

		// check if it is order form (either order form was selected or "Create Order Form"
		final boolean populateMatrix = (searchResultType != null && StringUtils.equals(searchResultType,
				ADVANCED_SEARCH_RESULT_TYPE_ORDER_FORM)) || isCreateOrderForm;

		if (StringUtils.isNotBlank(keywords))
		{
			if (onlyProductIds || isCreateOrderForm)
			{
				// search using flexible search
				final List<String> productIdsList = splitSkusAsList(keywords);
				final PageableData pageableData = createPageableData(page, 10, sortCode, showMode);
				searchPageData = flexibleSearchProductSearchFacade.searchForSkus(productIdsList, pageableData,
						Arrays.asList(ProductOption.URL, ProductOption.IMAGES), true);
			}
			else
			{
				// search using solr.
				searchPageData = performSearch(keywords, page, showMode, sortCode, getLoyaltySearchPageSize(), populateMatrix);
			}

		}
		populateModel(model, searchPageData, showMode);

		return searchPageData;
	}

	private ProductSearchPageData<SearchStateData, ProductData> createEmptySearchPageData()
	{
		final ProductSearchPageData productSearchPageData = new ProductSearchPageData();

		productSearchPageData.setResults(Lists.newArrayList());
		final PaginationData pagination = new PaginationData();
		pagination.setTotalNumberOfResults(0);
		productSearchPageData.setPagination(pagination);
		productSearchPageData.setSorts(Lists.newArrayList());

		return productSearchPageData;
	}

	protected List<String> splitSkusAsList(final String skus)
	{
		return Arrays.asList(StringUtils.split(skus,
				Config.getString(ADVANCED_SEARCH_PRODUCT_IDS_DELIMITER, ADVANCED_SEARCH_PRODUCT_IDS_DELIMITER_DEFAULT)));
	}

	private Locale getCurrentLocale()
	{
		return getI18nService().getCurrentLocale();
	}

	@ResponseBody
	@RequestMapping(value = "/autocomplete", method = RequestMethod.GET)
	public List<String> getAutocompleteSuggestions(@RequestParam("term") final String term)
	{
		final List<String> terms = new ArrayList<String>();
		for (final AutocompleteSuggestionData termData : solrProductSearchFacade.getAutocompleteSuggestions(term))
		{
			terms.add(termData.getTerm());
		}
		return terms;
	}


	@ResponseBody
	@RequestMapping(value = "/autocomplete/" + COMPONENT_UID_PATH_VARIABLE_PATTERN, method = RequestMethod.GET)
	public AutocompleteResultData getAutocompleteSuggestions(@PathVariable final String componentUid,
			@RequestParam("term") final String term) throws CMSItemNotFoundException
	{
		final AutocompleteResultData resultData = new AutocompleteResultData();

		final SearchBoxComponentModel component = (SearchBoxComponentModel) cmsComponentService.getSimpleCMSComponent(componentUid);

		if (component.isDisplaySuggestions())
		{
			resultData.setSuggestions(subList(solrProductSearchFacade.getAutocompleteSuggestions(term),
					component.getMaxSuggestions()));
		}

		if (component.isDisplayProducts())
		{
			final ProductSearchPageData<SearchStateData, ProductData> pageData = solrProductSearchFacade.textSearch(term);
			resultData.setProducts(subList(pageData.getResults(), component.getMaxProducts()));
		}

		return resultData;
	}

	protected <E> List<E> subList(final List<E> list, final int maxElements)
	{
		if (CollectionUtils.isEmpty(list))
		{
			return Collections.emptyList();
		}

		if (list.size() > maxElements)
		{
			return list.subList(0, maxElements);
		}

		return list;
	}

	@ResponseBody
	@RequestMapping(value = "/autocompleteSecure", method = RequestMethod.GET)
	public List<String> getAutocompleteSuggestionsSecure(@RequestParam("term") final String term)
	{
		return getAutocompleteSuggestions(term);
	}



	protected void updatePageTitle(final String searchText, final Model model)
	{
		storeContentPageTitleInModel(
				model,
				getPageTitleResolver().resolveContentPageTitle(
						getMessageSource().getMessage("search.meta.title", null, getCurrentLocale()) + " " + searchText));
	}

	protected void addMetaData(final Model model, final String metaPrefixKey, final String searchText,
			final String metaPostfixKey, final PageType pageType, final String robotsBehaviour)
	{
		final String metaDescription = MetaSanitizerUtil.sanitizeDescription(getMessageSource().getMessage(metaPrefixKey, null,
				getCurrentLocale())
				+ " "
				+ searchText
				+ " "
				+ getMessageSource().getMessage(metaPostfixKey, null, getCurrentLocale())
				+ " "
				+ getSiteName());
		final String metaKeywords = MetaSanitizerUtil.sanitizeKeywords(searchText);
		setUpMetaData(model, metaKeywords, metaDescription);

		model.addAttribute("pageType", pageType.name());
		model.addAttribute("metaRobots", robotsBehaviour);
	}

	@Override
	protected void populateModel(final Model model, final SearchPageData<?> searchPageData, final ShowMode showMode)
	{
		super.populateModel(model, searchPageData, showMode);

		if (StringUtils.equalsIgnoreCase(getSiteConfigService().getString(PAGINATION_TYPE, PAGINATION), INFINITE_SCROLL))
		{
			model.addAttribute(IS_SHOW_ALLOWED, false);
		}
	}

	protected int getLoyaltySearchPageSize()
	{
		return getSiteConfigService().getInt("storefront.loyalty.search.pageSize", 0);
	}

}
