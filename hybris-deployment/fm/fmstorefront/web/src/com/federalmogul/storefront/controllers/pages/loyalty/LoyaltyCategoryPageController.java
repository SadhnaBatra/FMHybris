/**
 * 
 */
package com.federalmogul.storefront.controllers.pages.loyalty;

import de.hybris.platform.acceleratorservices.controllers.page.PageType;
import de.hybris.platform.acceleratorservices.customer.CustomerLocationService;
import de.hybris.platform.acceleratorservices.data.RequestContextData;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.CategoryPageModel;
import de.hybris.platform.commercefacades.product.data.CategoryData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.search.ProductSearchFacade;
import de.hybris.platform.commercefacades.search.data.SearchQueryData;
import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commerceservices.category.CommerceCategoryService;
import de.hybris.platform.commerceservices.search.facetdata.BreadcrumbData;
import de.hybris.platform.commerceservices.search.facetdata.ProductCategorySearchPageData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.commerceservices.url.UrlResolver;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.federalmogul.storefront.breadcrumb.impl.LoyaltySearchBreadCrumbBuilder;
import com.federalmogul.storefront.breadcrumb.impl.SearchBreadcrumbBuilder;
import com.federalmogul.storefront.constants.WebConstants;
import com.federalmogul.storefront.controllers.ControllerConstants;
import com.federalmogul.storefront.controllers.pages.AbstractSearchPageController;
import com.federalmogul.storefront.util.MetaSanitizerUtil;
import com.federalmogul.storefront.util.XSSFilterUtil;


/**
 * Controller for a category page.
 */
@Controller
@Scope("tenant")
@RequestMapping(value = "/**/lc")
public class LoyaltyCategoryPageController extends AbstractSearchPageController
{
	private static final Logger LOG = Logger.getLogger(LoyaltyCategoryPageController.class);



	protected static final String PRODUCT_GRID_PAGE = "category/loyaltyProductGridPage";
	/**
	 * We use this suffix pattern because of an issue with Spring 3.1 where a Uri value is incorrectly extracted if it
	 * contains on or more '.' characters. Please see https://jira.springsource.org/browse/SPR-6164 for a discussion on
	 * the issue and future resolution.
	 */
	protected static final String CATEGORY_CODE_PATH_VARIABLE_PATTERN = "/{categoryCode:.*}";

	protected static final String INFINITE_SCROLL = "infiniteScroll";

	private static boolean isYMMSearch = false;


	@Resource(name = "productSearchFacade")
	private ProductSearchFacade<ProductData> productSearchFacade;

	@Resource(name = "commerceCategoryService")
	private CommerceCategoryService commerceCategoryService;

	@Resource(name = "searchBreadcrumbBuilder")
	private SearchBreadcrumbBuilder searchBreadcrumbBuilder;

	@Resource(name = "categoryModelUrlResolver")
	private UrlResolver<CategoryModel> categoryModelUrlResolver;

	@Resource(name = "customerLocationService")
	private CustomerLocationService customerLocationService;

	@Resource(name = "loyaltySearchBreadcrumbBuilder")
	private LoyaltySearchBreadCrumbBuilder loyaltySearchBreadcrumbBuilder;

	@RequestMapping(value = CATEGORY_CODE_PATH_VARIABLE_PATTERN, method = RequestMethod.GET)
	public String category(@PathVariable("categoryCode") final String categoryCode,
			@RequestParam(value = "q", required = false) final String searchQuery,
			@RequestParam(value = "page", defaultValue = "0") final int page,
			@RequestParam(value = "prodCount", defaultValue = "0") final int productCount,
			@RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode,
			@RequestParam(value = "sort", required = false) final String sortCode, final Model model,
			final HttpServletRequest request, final HttpServletResponse response) throws UnsupportedEncodingException
	{
		LOG.info("********************* Loyalty Page Controller ************** 00 " + categoryCode);

		final CategoryModel category = commerceCategoryService.getCategoryForCode(categoryCode);

		LOG.info("********************* Loyalty Page Controller ************** 11 ");

		LOG.info("categoryModel NAME ::  " + category.getCatalogVersion().getCatalog().getName());

		//final String redirection = checkRequestUrl(request, response, categoryModelUrlResolver.resolve(category));
		final String urlResolver = "/" + categoryCode + "/lc/" + categoryCode;
		final String redirection = checkRequestUrl(request, response, urlResolver);
		if (StringUtils.isNotEmpty(redirection))
		{
			return redirection;
		}

		int searchPageSize = getLoyaltySearchPageSize();
		
		if (productCount != 0)
		{

			searchPageSize = productCount;
		}
		LOG.info("searchPageSize :: " + searchPageSize);
		LOG.info("productCount :: " + productCount);

		CategoryPageModel categoryPage = getCategoryPage(category);

		final ProductCategorySearchPageData<SearchStateData, ProductData, CategoryData> searchPageData;

		final SearchQueryData searchQueryData = new SearchQueryData();

		LOG.info("********************* Loyalty Page Controller ************** 33 ");

		searchQueryData.setValue(XSSFilterUtil.filter(searchQuery));

		LOG.info(" ##Loyalty Page Controller ##  44 searchQuery " + searchQuery);

		if (searchQueryData.getValue() == null)
		{
			// Direct category link without filtering
			LOG.info(" ##Loyalty Page Controller ##  55 categoryCode " + categoryCode);

			searchPageData = productSearchFacade.categorySearch(categoryCode);

			LOG.info(" ##Loyalty Page Controller ##  66 categoryCode " + categoryCode);

			if (searchPageData.getSubCategories() != null)
			{

				LOG.info("Size of SUb Cat " + searchPageData.getSubCategories().size());
				for (final CategoryData catData : searchPageData.getSubCategories())
				{

					LOG.info("Sub Cat Code :: " + catData.getCode());
					LOG.info("Sub Cat Name :: " + catData.getName());
				}

			}
		}
		else
		{
			// We have some search filtering
			LOG.info(" ##Loyalty Page Controller ##  77 ");
			if (categoryPage == null || Boolean.FALSE.equals(categoryPage.getDefaultPage()))
			{
				// Load the default category page
				LOG.info(" ##Loyalty Page Controller ##  88 ");
				categoryPage = getDefaultCategoryPage();
			}

			LOG.info(" ##Loyalty Page Controller ##  99 ");

			final SearchStateData searchState = new SearchStateData();
			searchState.setQuery(searchQueryData);

			LOG.info(" ##Loyalty Page Controller ##  111 ");

			final PageableData pageableData = createPageableData(page, searchPageSize, sortCode, showMode);
			searchPageData = productSearchFacade.categorySearch(categoryCode, searchState, pageableData);

			LOG.info(" ##Loyalty Page Controller ##  222 ");
		}

		storeCmsPageInModel(model, categoryPage);
		storeContinueUrl(request);
		final boolean showCategoriesOnly = searchQueryData.getValue() == null && categoryPage != null
				&& Boolean.FALSE.equals(categoryPage.getDefaultPage()) && searchPageData.getSubCategories() != null
				&& !searchPageData.getSubCategories().isEmpty();


		populateModel(model, searchPageData, showMode);
		model.addAttribute(WebConstants.BREADCRUMBS_KEY,
				loyaltySearchBreadcrumbBuilder.getBreadcrumbs(categoryCode, searchPageData));
		model.addAttribute("showCategoriesOnly", Boolean.valueOf(showCategoriesOnly));
		model.addAttribute("pageType", PageType.CATEGORY.name());
		model.addAttribute("userLocation", customerLocationService.getUserLocation());
		model.addAttribute(WebConstants.NUMBER_OF_PRODUCTS_PER_PAGE, searchPageSize);

		updatePageTitle(category, searchPageData.getBreadcrumbs(), model);
		final RequestContextData requestContextData = getRequestContextData(request);
		requestContextData.setCategory(category);
		requestContextData.setSearch(searchPageData);

		final List<CategoryModel> rootCategoriesModelList = category.getCatalogVersion().getRootCategories();
		model.addAttribute("rootCategoriesList", rootCategoriesModelList);

		if (searchQueryData.getValue() != null)
		{
			model.addAttribute("metaRobots", "no-index,follow");
		}

	
	        model.addAttribute("TotalPoints", getSessionService().getAttribute(WebConstants.BALANCE_LOYALTY_POINTS));
		final String metaKeywords = MetaSanitizerUtil.sanitizeKeywords(category.getKeywords());
		final String metaDescription = MetaSanitizerUtil.sanitizeDescription(category.getDescription());
		setUpMetaData(model, metaKeywords, metaDescription);
		return getViewPage(categoryPage);
	}

	@RequestMapping(value = "/{categoryCode}/results", method = RequestMethod.GET)
	public String searchResults(@PathVariable("categoryCode") final String categoryCode,
			@RequestParam("q") final String searchQuery, @RequestParam(value = "page", defaultValue = "0") final int page,
			@RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode,
			@RequestParam(value = "sort", required = false) final String sortCode, final Model model)
			throws CMSItemNotFoundException
	{
		LOG.info("############### searchResults ################# ");
		final ProductCategorySearchPageData<SearchStateData, ProductData, CategoryData> searchPageData = performSearch(
				categoryCode, searchQuery, page, showMode, sortCode, getLoyaltySearchPageSize());
		final SearchResultsData<ProductData> searchResultsData = new SearchResultsData<ProductData>();
		searchResultsData.setResults(searchPageData.getResults());
		searchResultsData.setPagination(searchPageData.getPagination());

		model.addAttribute("searchResultsData", searchResultsData);

		return ControllerConstants.Views.Fragments.Product.ProductLister;
	}

	protected ProductCategorySearchPageData<SearchStateData, ProductData, CategoryData> performSearch(final String categoryCode,
			final String searchQuery, final int page, final ShowMode showMode, final String sortCode, final int pageSize)
	{
		final PageableData pageableData = createPageableData(page, pageSize, sortCode, showMode);
		final SearchStateData searchState = new SearchStateData();
		final SearchQueryData searchQueryData = new SearchQueryData();
		searchQueryData.setValue(searchQuery);
		searchState.setQuery(searchQueryData);

		return productSearchFacade.categorySearch(categoryCode, searchState, pageableData);
	}

	protected CategoryPageModel getCategoryPage(final CategoryModel category)
	{
		try
		{
			return getCmsPageService().getPageForCategory(category);
		}
		catch (final CMSItemNotFoundException ignore)
		{
			// Ignore
		}
		return null;
	}

	protected CategoryPageModel getDefaultCategoryPage()
	{
		try
		{
			return getCmsPageService().getPageForCategory(null);
		}
		catch (final CMSItemNotFoundException ignore)
		{
			// Ignore
		}
		return null;
	}

	protected <QUERY> void updatePageTitle(final CategoryModel category, final List<BreadcrumbData<QUERY>> appliedFacets,
			final Model model)
	{
		storeContentPageTitleInModel(model, getPageTitleResolver().resolveCategoryPageTitle(category, appliedFacets));
	}

	protected String getViewPage(final CategoryPageModel categoryPage)
	{
		if (categoryPage != null)
		{
			final String targetPage = getViewForPage(categoryPage);
			if (targetPage != null && !targetPage.isEmpty())
			{
				return targetPage;
			}
		}
		return PAGE_ROOT + PRODUCT_GRID_PAGE;
	}

	@ExceptionHandler(UnknownIdentifierException.class)
	public String handleUnknownIdentifierException(final UnknownIdentifierException exception, final HttpServletRequest request)
	{
		request.setAttribute("message", exception.getMessage());
		return FORWARD_PREFIX + "/404";
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
