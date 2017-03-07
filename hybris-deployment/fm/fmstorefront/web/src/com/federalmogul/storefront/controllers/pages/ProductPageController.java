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

import de.hybris.platform.acceleratorservices.config.SiteConfigService;
import de.hybris.platform.acceleratorservices.controllers.page.PageType;
import de.hybris.platform.b2bacceleratorfacades.futurestock.B2BFutureStockFacade;
import de.hybris.platform.b2bacceleratorservices.company.CompanyB2BCommerceService;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;
import de.hybris.platform.cms2.model.site.CMSSiteModel;
import de.hybris.platform.cms2.servicelayer.services.CMSSiteService;
import de.hybris.platform.commercefacades.product.ProductFacade;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.BaseOptionData;
import de.hybris.platform.commercefacades.product.data.FutureStockData;
import de.hybris.platform.commercefacades.product.data.ImageData;
import de.hybris.platform.commercefacades.product.data.ImageDataType;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.product.data.ReviewData;
import de.hybris.platform.commerceservices.url.UrlResolver;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.product.UnitModel;
import de.hybris.platform.core.model.security.PrincipalGroupModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.europe1.model.PriceRowModel;
import de.hybris.platform.util.Config;
import de.hybris.platform.ordersplitting.WarehouseService;
import de.hybris.platform.ordersplitting.model.StockLevelModel;
import de.hybris.platform.ordersplitting.model.WarehouseModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.util.Config;
import de.hybris.platform.variants.model.VariantProductModel;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.xml.soap.SOAPException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.federalmogul.core.model.FMCustomerAccountModel;
import com.federalmogul.core.product.PartService;
import com.federalmogul.facades.account.FMReviewFacade;
import com.federalmogul.facades.account.LoyaltySurveyFacade;
import com.federalmogul.facades.search.impl.DefaultFMFitmentSearchFacade;
import com.federalmogul.facades.user.data.FMSurveyData;
import com.federalmogul.falcon.core.model.FMCorporateModel;
import com.federalmogul.falcon.core.model.FMPartAlsoFitsModel;
import com.federalmogul.falcon.core.model.FMPartModel;
import com.federalmogul.storefront.breadcrumb.impl.ProductBreadcrumbBuilder;
import com.federalmogul.storefront.constants.WebConstants;
import com.federalmogul.storefront.controllers.ControllerConstants;
import com.federalmogul.storefront.controllers.util.GlobalMessages;
import com.federalmogul.storefront.forms.FutureStockForm;
import com.federalmogul.storefront.forms.ReviewForm;
import com.federalmogul.storefront.util.MetaSanitizerUtil;
import com.federalmogul.storefront.util.XSSFilterUtil;
import com.federalmogul.storefront.variants.VariantSortStrategy;
import com.fm.falcon.webservices.dto.LoyaltySurveyResponseDTO;
import com.fmo.wom.business.as.ItemASUSCanImpl;
import com.fmo.wom.business.as.WOMServices;
import com.fmo.wom.common.exception.WOMBusinessDataException;
import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.common.exception.WOMTransactionException;
import com.fmo.wom.common.exception.WOMValidationException;
import com.fmo.wom.domain.AccountBO;
import com.fmo.wom.domain.BillToAcctBO;
import com.fmo.wom.domain.CustomerSalesOrgBO;
import com.fmo.wom.domain.ItemBO;
import com.fmo.wom.domain.PartBO;
import com.fmo.wom.domain.PriceBO;
import com.fmo.wom.domain.ProblemBO;
import com.fmo.wom.domain.QuantityBO;
import com.fmo.wom.domain.SalesOrganizationBO;
import com.fmo.wom.domain.SapAcctBO;
import com.fmo.wom.domain.ShipToAcctBO;
import com.fmo.wom.domain.WeightBO;
import com.fmo.wom.domain.enums.ExternalSystem;
import com.fmo.wom.integration.nabs.action.GetMasterOrderNumberAction;
import com.fmo.wom.integration.util.MessageResourceUtil;
import com.google.common.collect.Maps;
import com.federalmogul.facades.uploadorder.UploadOrderFacade;

/**
 * Controller for product details page.
 */
@Controller
@Scope("tenant")
@RequestMapping(value = "/**/p")
public class ProductPageController extends AbstractPageController
{
	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(ProductPageController.class);

	/**
	 * We use this suffix pattern because of an issue with Spring 3.1 where a Uri value is incorrectly extracted if it
	 * contains on or more '.' characters. Please see https://jira.springsource.org/browse/SPR-6164 for a discussion on
	 * the issue and future resolution.
	 */

	protected static final String TITLE_WORD_SEPARATOR = " | ";
	private static final String PRODUCT_CODE_PATH_VARIABLE_PATTERN = "/{productCode:.*}";
	private static final String REVIEWS_PATH_VARIABLE_PATTERN = "{numberOfReviews:.*}";

	private static final String FUTURE_STOCK_ENABLED = "storefront.products.futurestock.enabled";
	private static final String STOCK_SERVICE_UNAVAILABLE = "basket.page.viewFuture.unavailable";
	private static final String NOT_MULTISKU_ITEM_ERROR = "basket.page.viewFuture.not.multisku";

	@Resource(name = "productModelUrlResolver")
	private UrlResolver<ProductModel> productModelUrlResolver;

	@Resource(name = "b2bProductFacade")
	private ProductFacade productFacade;

	@Resource(name = "productService")
	private ProductService productService;

	@Resource(name = "partService")
	private PartService partService;

	@Resource(name = "productBreadcrumbBuilder")
	private ProductBreadcrumbBuilder productBreadcrumbBuilder;

	@Resource(name = "variantSortStrategy")
	private VariantSortStrategy variantSortStrategy;

	@Resource(name = "b2bFutureStockFacade")
	private B2BFutureStockFacade b2bFutureStockFacade;

	@Resource(name = "loyaltySurveyFacade")
	LoyaltySurveyFacade loyaltySurveyFacade;

	@Autowired
	private UserService userService;

	@Autowired
	protected CompanyB2BCommerceService companyB2BCommerceService;

	@Autowired
	private ModelService modelService;

	@Resource
	private WarehouseService warehouseService;

	/*
	 * @Autowired private EnumerationService enumerationService;
	 */

	@Autowired
	private CommonI18NService commonI18NService;

	@Resource(name = "siteConfigService")
	private SiteConfigService siteConfigService;

	@Autowired
	private DefaultFMFitmentSearchFacade defaultFMFitmentSearchFacade;
	@Autowired
	private FMReviewFacade fmReviewFacade;


	@Autowired
	private CMSSiteService cmsSiteService;

	@Autowired
	private UploadOrderFacade uploadorderFacade;

	/*
	 * @Autowired private PartResolutionFacade partResolutionFacade;
	 */

	private static boolean reviewFlag;

	private static boolean reviewAll;

	@RequestMapping(value = PRODUCT_CODE_PATH_VARIABLE_PATTERN, method = RequestMethod.GET)
	public String productDetail(@PathVariable("productCode") final String productCode, final Model model,
			final HttpServletRequest request, final HttpServletResponse response) throws CMSItemNotFoundException,
			UnsupportedEncodingException
	{
		final FMPartModel productModel = partService.getPartForCode(productCode);

		/* Mahaveer - Part Resolution Message for Product Start */
		final UserModel user = userService.getCurrentUser();
		final boolean isUserAnonymous = user == null || userService.isAnonymousUser(user);
		model.addAttribute("isDummyPart", false);
		final String soldToAcc = (String) getSessionService().getAttribute(WebConstants.SOLDTO_ACCOUNT_ID);
		if (!isUserAnonymous && null != soldToAcc)
		{
			final String partResolutionMessage = getPartResolutions(productModel, getBillTOAccount(), getShiptoAccount(),
					getPONumber(), model);
			model.addAttribute("partResolutionMessage", partResolutionMessage);

		}
		else
		{
			model.addAttribute("isValidPart", false);
		}
		/* Mahaveer - Part Resolution Meeage for Product End */

		final String redirection = checkRequestUrl(request, response, productModelUrlResolver.resolve(productModel));
		if (StringUtils.isNotEmpty(redirection))
		{
			return redirection;
		}
		final String syears = (request.getParameter("years") != null ? request.getParameter("years") : null);
		final String smake = (request.getParameter("make") != null ? request.getParameter("make") : null);
		final String smodels = (request.getParameter("models") != null ? request.getParameter("models") : null);

		final String categoryCode = (request.getParameter("categoryCode") != null ? request.getParameter("categoryCode") : null);

		LOG.info("YMM Searched Value YEAR :: " + syears + " :: MAKE :: " + smake + " :: MODEL :: " + smodels + "CategoryCode: :"
				+ categoryCode);
		//Mahaveer Credit Block
		model.addAttribute(WebConstants.FM_CREDIT_BLOCK, getSessionService().getAttribute(WebConstants.FM_CREDIT_BLOCK));
		model.addAttribute(WebConstants.FM_ORDER_BLOCK, getSessionService().getAttribute(WebConstants.FM_ORDER_BLOCK));
		if (null != syears && null != smake && null != smodels)
		{
			model.addAttribute(WebConstants.YMM_YEARS, XSSFilterUtil.filter(syears));
			model.addAttribute(WebConstants.YMM_MAKE, XSSFilterUtil.filter(smake));
			model.addAttribute(WebConstants.YMM_MODEL, XSSFilterUtil.filter(smodels));
		}
		if (null != categoryCode)
		{
			model.addAttribute(WebConstants.CATEGORY_CODE, XSSFilterUtil.filter(categoryCode));
		}

		updatePageTitle(productModel, model);
		final List<ProductOption> extraOptions = Arrays.asList(ProductOption.VARIANT_MATRIX_BASE, ProductOption.VARIANT_MATRIX_URL,
				ProductOption.VARIANT_MATRIX_MEDIA);
		populateProductDetailForDisplay(productModel, model, request, extraOptions);
		model.addAttribute(new ReviewForm());
		model.addAttribute("pageType", PageType.PRODUCT.name());
		model.addAttribute("futureStockEnabled", Boolean.valueOf(Config.getBoolean(FUTURE_STOCK_ENABLED, false)));

		final String metaKeywords = MetaSanitizerUtil.sanitizeKeywords(productModel.getKeywords());
		final String metaDescription = MetaSanitizerUtil.sanitizeDescription(productModel.getDescription());
		setUpMetaData(model, metaKeywords, metaDescription);

		targetURL(request, model);

		final List<FMPartAlsoFitsModel> alsoFits = defaultFMFitmentSearchFacade.getAlsoFits(productCode);

		model.addAttribute("AlsoFits", alsoFits);

		if (reviewAll)
		{
			LOG.info("review all" + reviewAll);
			model.addAttribute("display", "all");
			model.addAttribute("reviewFlag", "FromReview");
			reviewAll = false;
		}
		else
		{
			model.addAttribute("display", "int");
		}
		return getViewForPage(model);
	}



	@RequestMapping(value = PRODUCT_CODE_PATH_VARIABLE_PATTERN + "/orderForm", method = RequestMethod.GET)
	public String productOrderForm(@PathVariable("productCode") final String productCode, final Model model,
			final HttpServletRequest request, final HttpServletResponse response) throws CMSItemNotFoundException
	{
		final ProductModel productModel = productService.getProductForCode(productCode);

		updatePageTitle(productModel, model);
		final List<ProductOption> extraOptions = Arrays.asList(ProductOption.VARIANT_MATRIX_BASE,
				ProductOption.VARIANT_MATRIX_PRICE, ProductOption.VARIANT_MATRIX_MEDIA, ProductOption.VARIANT_MATRIX_STOCK);
		populateProductDetailForDisplay(productModel, model, request, extraOptions);

		if (!model.containsAttribute(WebConstants.MULTI_DIMENSIONAL_PRODUCT))
		{
			return REDIRECT_PREFIX + productModelUrlResolver.resolve(productModel);
		}

		final String metaKeywords = MetaSanitizerUtil.sanitizeKeywords(productModel.getKeywords());
		final String metaDescription = MetaSanitizerUtil.sanitizeDescription(productModel.getDescription());
		setUpMetaData(model, metaKeywords, metaDescription);

		return ControllerConstants.Views.Pages.Product.OrderForm;
	}


	@RequestMapping(value = PRODUCT_CODE_PATH_VARIABLE_PATTERN + "/futureStock", method = RequestMethod.GET)
	public String productFutureStock(@PathVariable("productCode") final String productCode, final Model model,
			final HttpServletRequest request, final HttpServletResponse response) throws CMSItemNotFoundException
	{
		final boolean futureStockEnabled = Config.getBoolean(FUTURE_STOCK_ENABLED, false);
		if (futureStockEnabled)
		{
			final ProductModel productModel = productService.getProductForCode(productCode);
			final List<FutureStockData> futureStockList = b2bFutureStockFacade.getFutureAvailability(productModel);
			if (futureStockList == null)
			{
				GlobalMessages.addErrorMessage(model, STOCK_SERVICE_UNAVAILABLE);
			}
			else if (futureStockList.isEmpty())
			{
				GlobalMessages.addErrorMessage(model, "product.product.details.future.nostock");
			}
			populateProductDetailForDisplay(productModel, model, request, Collections.EMPTY_LIST);
			model.addAttribute("futureStocks", futureStockList);

			final String metaKeywords = MetaSanitizerUtil.sanitizeKeywords(productModel.getKeywords());
			final String metaDescription = MetaSanitizerUtil.sanitizeDescription(productModel.getDescription());
			setUpMetaData(model, metaKeywords, metaDescription);

			return ControllerConstants.Views.Fragments.Product.FutureStockPopup;
		}
		else
		{
			return ControllerConstants.Views.Pages.Error.ErrorNotFoundPage;
		}

	}

	@ResponseBody
	@RequestMapping(value = PRODUCT_CODE_PATH_VARIABLE_PATTERN + "/grid/skusFutureStock", method =
	{ RequestMethod.POST }, produces = MediaType.APPLICATION_JSON_VALUE)
	public final Map<String, Object> productSkusFutureStock(final FutureStockForm form, final Model model)
	{
		final String productCode = form.getProductCode();
		final List<String> skus = form.getSkus();
		final boolean futureStockEnabled = Config.getBoolean(FUTURE_STOCK_ENABLED, false);

		Map<String, Object> result = new HashMap<>();
		if (futureStockEnabled && CollectionUtils.isNotEmpty(skus) && StringUtils.isNotBlank(productCode))
		{
			// retrieve the variants list
			final Set<String> skusSet = new HashSet<String>(skus);
			final ProductModel productModel = productService.getProductForCode(productCode);
			List<ProductModel> variantsList = null;
			if (CollectionUtils.isNotEmpty(productModel.getVariants()))
			{
				variantsList = getSelectedProductModels(skusSet, productModel.getVariants());
			}
			else if (productModel instanceof VariantProductModel)
			{
				variantsList = getSelectedProductModels(skusSet, ((VariantProductModel) productModel).getBaseProduct().getVariants());
			}

			if (CollectionUtils.isNotEmpty(variantsList))
			{
				final Map<String, List<FutureStockData>> futureStockData = b2bFutureStockFacade.getFutureAvailability(variantsList);
				if (futureStockData == null)
				{
					// future availability service is down, we show this to the user
					result = Maps.newHashMap();
					final String errorMessage = getMessageSource().getMessage(STOCK_SERVICE_UNAVAILABLE, null,
							getI18nService().getCurrentLocale());
					result.put(STOCK_SERVICE_UNAVAILABLE, errorMessage);
				}
				else
				{
					for (final Entry<String, List<FutureStockData>> entry : futureStockData.entrySet())
					{
						result.put(entry.getKey(), entry.getValue());
					}
				}
			}
			else
			{
				GlobalMessages.addErrorMessage(model, NOT_MULTISKU_ITEM_ERROR);
			}
		}
		return result;
	}

	@RequestMapping(value = PRODUCT_CODE_PATH_VARIABLE_PATTERN + "/zoomImages", method = RequestMethod.GET)
	public String showZoomImages(@PathVariable("productCode") final String productCode,
			@RequestParam(value = "galleryPosition", required = false) final String galleryPosition, final Model model)
	{
		final ProductModel productModel = productService.getProductForCode(productCode);
		final ProductData productData = productFacade.getProductForOptions(productModel,
				Collections.singleton(ProductOption.GALLERY));
		final List<Map<String, ImageData>> images = getGalleryImages(productData);
		model.addAttribute("galleryImages", images);
		model.addAttribute("product", productData);
		if (galleryPosition != null)
		{
			try
			{
				model.addAttribute("zoomImageUrl", images.get(Integer.parseInt(galleryPosition)).get("zoom").getUrl());
			}
			catch (final IndexOutOfBoundsException ignore)
			{
				model.addAttribute("zoomImageUrl", "");
			}
			catch (final NumberFormatException ignore)
			{
				model.addAttribute("zoomImageUrl", "");
			}
		}
		return ControllerConstants.Views.Fragments.Product.ZoomImagesPopup;
	}

	@RequestMapping(value = PRODUCT_CODE_PATH_VARIABLE_PATTERN + "/quickView", method = RequestMethod.GET)
	public String showQuickView(@PathVariable("productCode") final String productCode, final Model model,
			final HttpServletRequest request)
	{
		final ProductModel productModel = productService.getProductForCode(productCode);
		final ProductData productData = productFacade.getProductForOptions(productModel, Arrays.asList(ProductOption.BASIC,
				ProductOption.PRICE, ProductOption.SUMMARY, ProductOption.DESCRIPTION, ProductOption.CATEGORIES,
				ProductOption.PROMOTIONS, ProductOption.STOCK, ProductOption.REVIEW, ProductOption.VOLUME_PRICES));

		populateProductData(productData, model, request);
		getRequestContextData(request).setProduct(productModel);

		return ControllerConstants.Views.Fragments.Product.QuickViewPopup;
	}

	@RequestMapping(value = PRODUCT_CODE_PATH_VARIABLE_PATTERN + "/review", method = RequestMethod.POST)
	public String postReview(@PathVariable final String productCode, final ReviewForm form, final BindingResult result,
			final Model model, final HttpServletRequest request, final RedirectAttributes redirectAttrs)
			throws CMSItemNotFoundException
	{
		LOG.info("Inside review post method");
		LOG.info("headline::" + form.getHeadline());
		final ProductModel productModel = productService.getProductForCode(productCode);
		final Boolean flag;
		if (result.hasErrors()) 
		{
			updatePageTitle(productModel, model);
			//GlobalMessages.addErrorMessage(model, "review.general.error");
			model.addAttribute("showReviewForm", Boolean.TRUE);
			populateProductDetailForDisplay(productModel, model, request, Collections.EMPTY_LIST);
			storeCmsPageInModel(model, getPageForProduct(productModel));
			return getViewForPage(model);
		}

		final ReviewData review = new ReviewData();
		review.setHeadline(XSSFilterUtil.filter(form.getHeadline()));
		review.setComment(XSSFilterUtil.filter(form.getComment()));
		review.setRating(form.getRating());
		review.setAlias(XSSFilterUtil.filter(form.getAlias()));
		final String[] commentWords = Config.getParameter("notInReviewComment").split(",");
		final ArrayList<String> form_review = new ArrayList<String>();

		for (final String reviewComment : form.getComment().split(Config.getParameter("reviewSplitCharacters")))
		{	 
			form_review.add(reviewComment); 
		}
		for (final String reviewHeadline : form.getHeadline().split(Config.getParameter("reviewSplitCharacters")))
		{
			form_review.add(reviewHeadline);
		}
		int count = 0;
		for (final String i : commentWords)
		{
			for (final String j : form_review)
			{
				if ((j.equalsIgnoreCase(i)))
				{
					count++;
					break; 
				}
			}
		}
		flag = count > 0 ? true : false; 
		reviewFlag = true;
		final UserModel user = userService.getCurrentUser();
		final boolean isUserAnonymous = userService.isAnonymousUser(user);
		final List<String> groupUID = new ArrayList<String>();
		if (!isUserAnonymous)
		{
			if (user != null)
			{

				final Set<PrincipalGroupModel> groupss = user.getGroups();
				for (final PrincipalGroupModel groupModel : groupss)
				{
					final String groupId = groupModel.getUid();
					groupUID.add(groupId);
				}
				fmReviewFacade.postReview(productCode, review, flag);
			}
		}




		if (groupUID.contains("FMB2T"))
		{
			try

			{
				final FMSurveyData surveyData = new FMSurveyData();
				final LoyaltySurveyResponseDTO resultFromCrm = loyaltySurveyFacade.populateReviewData(surveyData);
				if (resultFromCrm != null)
				{
					LOG.debug("CRM STATUS" + resultFromCrm.getStatus());
				}
			}
			catch (final UnsupportedOperationException e)
			{
				LOG.error("CRM STATUS" + e);
			}
			catch (final ClassNotFoundException e)
			{
				// YTODO Auto-generated catch block
				LOG.error("CRM STATUS" + e);
			}
			catch (final SOAPException e)
			{
				// YTODO Auto-generated catch block
				LOG.error("CRM STATUS" + e);
			}
			catch (final IOException e)
			{
				// YTODO Auto-generated catch block
				LOG.error("CRM STATUS" + e);
			}



		}
		return "redirect:" + productModelUrlResolver.resolve(productModel) + "#" + Config.getParameter("reviewtab");
	}

	@RequestMapping(value = PRODUCT_CODE_PATH_VARIABLE_PATTERN + "/reviewhtml/" + REVIEWS_PATH_VARIABLE_PATTERN, method =
	{ RequestMethod.POST, RequestMethod.GET })
	public String reviewHtml(@PathVariable("productCode") final String productCode,
			@PathVariable("numberOfReviews") final String numberOfReviews, final Model model, final HttpServletRequest request)
	{
		LOG.info("inside review html all method");
		LOG.info("numberOfReviews" + numberOfReviews);

		final ProductModel productModel = productService.getProductForCode(productCode);
		final List<ReviewData> reviews;
		final String reviewCheck = (request.getParameter("review") != null ? request.getParameter("review") : null);
		if ("true".equalsIgnoreCase(reviewCheck))
		{
			model.addAttribute("reviewFlag", "FromReview");

		}

		if ("all".equalsIgnoreCase(numberOfReviews))
		{
			LOG.info("inside review html all method" + numberOfReviews);
			reviews = productFacade.getReviews(productCode);
			//model.addAttribute("display", "all");
			reviewAll = true;
		}
		else
		{
			reviews = productFacade.getReviews(productCode, Integer.valueOf(numberOfReviews));
		}

		getRequestContextData(request).setProduct(productModel);
		model.addAttribute("reviews", reviews);
		model.addAttribute("reviewsTotal", productModel.getNumberOfReviews());
		model.addAttribute(new ReviewForm());


		return "redirect:" + productModelUrlResolver.resolve(productModel) + "#" + Config.getParameter("reviewtab");

	}

	protected void updatePageTitle(final ProductModel productModel, final Model model)
	{
		//Mahaveer - Bug fix - Product Title - Socail media - start
		boolean isCatageoryPathEnabled = false;
		isCatageoryPathEnabled = siteConfigService.getBoolean("fm.productpage.category.title", false);
		if (isCatageoryPathEnabled)
		{
			storeContentPageTitleInModel(model, getPageTitleResolver().resolveProductPageTitle(productModel));
		}
		else
		{
			final FMPartModel product = partService.getPartForCode(productModel.getCode());
			final CMSSiteModel currentSite = cmsSiteService.getCurrentSite();
			if (productModel.getName() != null)
			{
				final StringBuilder builder = new StringBuilder(productModel.getName());

				builder.append(TITLE_WORD_SEPARATOR).append(
						(product.getPartNumber() != null && !product.getPartNumber().isEmpty()) ? product.getPartNumber() : product
								.getCode());
				builder.append(TITLE_WORD_SEPARATOR).append(currentSite.getName());
				storeContentPageTitleInModel(model, StringEscapeUtils.escapeHtml(builder.toString()));
			}


		}
		//Mahaveer - Bug fix - Product Title - Socail media - End
	}

	@ExceptionHandler(UnknownIdentifierException.class)
	public String handleUnknownIdentifierException(final UnknownIdentifierException exception, final HttpServletRequest request)
	{
		request.setAttribute("message", exception.getMessage());
		return FORWARD_PREFIX + "/404";
	}

	protected void populateProductDetailForDisplay(final ProductModel productModel, final Model model,
			final HttpServletRequest request, final List<ProductOption> extraOptions) throws CMSItemNotFoundException
	{


		final List<ProductOption> options = new ArrayList<>(Arrays.asList(ProductOption.BASIC, ProductOption.PRICE,
				ProductOption.SUMMARY, ProductOption.DESCRIPTION, ProductOption.GALLERY, ProductOption.CATEGORIES,
				ProductOption.REVIEW, ProductOption.PROMOTIONS, ProductOption.CLASSIFICATION, ProductOption.VARIANT_FULL,
				ProductOption.STOCK, ProductOption.VOLUME_PRICES, ProductOption.PRICE_RANGE));

		options.addAll(extraOptions);
		final ProductData productData = productFacade.getProductForOptions(productModel, options);
	
		//FAL - 90 - Social Media changes
		final List<Map<String, ImageData>> images = getGalleryImages(productData);
		model.addAttribute("galleryImagesForSocialMedia", images);
		
		getRequestContextData(request).setProduct(productModel);

		sortVariantOptionData(productData);
		storeCmsPageInModel(model, getPageForProduct(productModel));
		populateProductData(productData, model, request);
		model.addAttribute(WebConstants.BREADCRUMBS_KEY, productBreadcrumbBuilder.getBreadcrumbs(productModel));
		if (CollectionUtils.isNotEmpty(productData.getVariantMatrix()))
		{
			model.addAttribute(WebConstants.MULTI_DIMENSIONAL_PRODUCT,
					Boolean.valueOf(CollectionUtils.isNotEmpty(productData.getVariantMatrix())));
		}
	}

	protected void populateProductData(final ProductData productData, final Model model, final HttpServletRequest request)
	{
		model.addAttribute("galleryImages", getGalleryImages(productData));
		model.addAttribute("product", productData);
	}

	protected void sortVariantOptionData(final ProductData productData)
	{
		if (CollectionUtils.isNotEmpty(productData.getBaseOptions()))
		{
			for (final BaseOptionData baseOptionData : productData.getBaseOptions())
			{
				if (CollectionUtils.isNotEmpty(baseOptionData.getOptions()))
				{
					Collections.sort(baseOptionData.getOptions(), variantSortStrategy);
				}
			}
		}

		if (CollectionUtils.isNotEmpty(productData.getVariantOptions()))
		{
			Collections.sort(productData.getVariantOptions(), variantSortStrategy);
		}
	}

	protected AbstractPageModel getPageForProduct(final ProductModel product) throws CMSItemNotFoundException
	{
		return getCmsPageService().getPageForProduct(product);
	}

	protected List<Map<String, ImageData>> getGalleryImages(final ProductData productData)
	{
		final List<Map<String, ImageData>> galleryImages = new ArrayList<Map<String, ImageData>>();
		if (CollectionUtils.isNotEmpty(productData.getImages()))
		{
			final List<ImageData> images = new ArrayList<ImageData>();
			for (final ImageData image : productData.getImages())
			{
				if (ImageDataType.GALLERY.equals(image.getImageType()))
				{
					images.add(image);
				}
			}
			Collections.sort(images, new Comparator<ImageData>()
			{
				@Override
				public int compare(final ImageData image1, final ImageData image2)
				{
					return image1.getGalleryIndex().compareTo(image2.getGalleryIndex());
				}
			});

			if (CollectionUtils.isNotEmpty(images))
			{
				int currentIndex = images.get(0).getGalleryIndex().intValue();
				Map<String, ImageData> formats = new HashMap<String, ImageData>();
				for (final ImageData image : images)
				{
					if (currentIndex != image.getGalleryIndex().intValue())
					{
						galleryImages.add(formats);
						formats = new HashMap<String, ImageData>();
						currentIndex = image.getGalleryIndex().intValue();
					}
					formats.put(image.getFormat(), image);
				}
				if (!formats.isEmpty())
				{
					galleryImages.add(formats);
				}
			}
		}
		return galleryImages;
	}


	protected List<ProductModel> getSelectedProductModels(final Set<String> skus,
			final Collection<VariantProductModel> productModels)
	{
		final List<ProductModel> selectedProductModels = new ArrayList<ProductModel>();
		for (final ProductModel productModel : productModels)
		{
			if (skus.contains(productModel.getCode()))
			{
				selectedProductModels.add(productModel);
			}

		}
		return selectedProductModels;
	}

	private String getPONumber()
	{
		final GetMasterOrderNumberAction masterOrderNumAction = new GetMasterOrderNumberAction();
		masterOrderNumAction.setCallingSystem(null);
		final String poNumber = masterOrderNumAction.executeAction();
		return poNumber;
	}

	private AccountBO getBillTOAccount()
	{
		final AccountBO billToAcct = new BillToAcctBO();
		LOG.info("Session Code ::" + getSessionService().getAttribute(WebConstants.SOLDTO_ACCOUNT_ID));


		final String soldToAcc = (String) getSessionService().getAttribute(WebConstants.SOLDTO_ACCOUNT_ID);
		final FMCustomerAccountModel fmCustomerAccModel = (FMCustomerAccountModel) companyB2BCommerceService
				.getUnitForUid(soldToAcc);

		//final String shipToAcc = (String) getSessionService().getAttribute(WebConstants.SHIPTO_ACCOUNT_ID);
		//final FMCustomerAccountModel sfmCustomerAccModel = (FMCustomerAccountModel) companyB2BCommerceService
		//	.getUnitForUid(shipToAcc);

		//Mahaveer JOBBER Price change
		//final String loggedUserType = (String) getSessionService().getAttribute("logedUserType");
		//if (loggedUserType != null && "ShipTo".equalsIgnoreCase(loggedUserType))
		//{
		//	fmCustomerAccModel = sfmCustomerAccModel;
		//}



		billToAcct.setAccountCode(fmCustomerAccModel.getNabsAccountCode());//soldToUnit.getNabsAccountCode());
		final SapAcctBO sapAccount = new SapAcctBO();
		sapAccount.setSapAccountCode(fmCustomerAccModel.getUid());

		final CustomerSalesOrgBO custSalesOrg = new CustomerSalesOrgBO();
		custSalesOrg.setDistributionChannel(fmCustomerAccModel.getDistributionChannel());
		custSalesOrg.setDivision(fmCustomerAccModel.getDivision());

		final SalesOrganizationBO salesOrg = new SalesOrganizationBO();
		salesOrg.setCode(fmCustomerAccModel.getSalesorg());

		custSalesOrg.setSalesOrganization(salesOrg);
		sapAccount.setCustomerSalesOrganization(custSalesOrg);
		billToAcct.setSapAccount(sapAccount);
		return billToAcct;
	}

	private AccountBO getShiptoAccount()
	{
		final String shipToAcc = (String) getSessionService().getAttribute(WebConstants.SHIPTO_ACCOUNT_ID);
		final FMCustomerAccountModel fmCustomerAccModel = (FMCustomerAccountModel) companyB2BCommerceService
				.getUnitForUid(shipToAcc);
		final AccountBO shipToAcct = new ShipToAcctBO();
		shipToAcct.setAccountCode(fmCustomerAccModel.getNabsAccountCode());//shipToUnit.getUid());
		return shipToAcct;
	}

	private String getPartResolutions(final FMPartModel productModel, final AccountBO billTo, final AccountBO shipTo,
			final String poNumber, final Model model)
	{
		final UserModel user = userService.getCurrentUser();
		final boolean isUserAnonymous = user == null || userService.isAnonymousUser(user);
		boolean isValidPart = true;
		if (!isUserAnonymous)
		{
			//INvoke Part resolution for product.
			try
			{
				boolean isNabsProduct = false;
				boolean isDummyPart = false;
				for (final FMCorporateModel corp : productModel.getCorporate())
				{
					if ("FELPF".equalsIgnoreCase(corp.getCorpcode()) || "SPDPF".equalsIgnoreCase(corp.getCorpcode())
							|| "SLDPF".equalsIgnoreCase(corp.getCorpcode()))
					{
						isNabsProduct = true;
					}
					if ("dummy".equalsIgnoreCase(corp.getCorpcode()))
					{
						isDummyPart = true;
					}
				}
				String displayPartNumber = productModel.getPartNumber();
				if (isDummyPart && productModel.getProductFlag() != null && null != displayPartNumber)
				{
					displayPartNumber = displayPartNumber.substring(1);
					System.err.println("displayPartNumber :: " + displayPartNumber);
					isNabsProduct = true;
				}
				model.addAttribute("isDummyPart", isDummyPart);
				final List<ItemBO> itemBOList = new ArrayList<ItemBO>();
				final ItemBO anItem = new PartBO();
				if (isNabsProduct)
				{
					anItem.setPartNumber(productModel.getRawPartNumber());
					anItem.setDisplayPartNumber(displayPartNumber);
					anItem.setProductFlag(isDummyPart ? productModel.getProductFlag().toUpperCase() : productModel.getFlagcode()
							.toUpperCase());
					anItem.setExternalSystem(ExternalSystem.NABS);
				}
				else
				{
					anItem.setPartNumber(productModel.getCode());
					anItem.setDisplayPartNumber(productModel.getCode());
					anItem.setExternalSystem(ExternalSystem.EVRST);
					//anItem.setProductFlag(productModel.getProductFlag());
				}
				anItem.setAaiaBrand(siteConfigService.getString("wom.item.aaiabrand", "DZH"));
				anItem.setScacCode(siteConfigService.getString("wom.item.scaccode", "UPS-REG"));
				anItem.setItemQty(getDefaultQuanity());
				anItem.setLineNumber(1);
				itemBOList.add(anItem);
				LOG.info("Before itemBOList :: " + itemBOList);

				final List<ItemBO> items = womQuickUploadOrder(itemBOList, billTo, shipTo, poNumber);

				LOG.info("After items :: " + items);
				for (final ItemBO item : items)
				{
					if (productModel.getStockLevels().isEmpty())
					{
						createStockLevel(productModel);
					}
					final WeightBO weight = item.getItemWeight();
					if (item.getItemPrice() != null)
					{
						final PriceBO priceBO = item.getItemPrice();
						//createPriceRowForUser(priceBO.getDisplayPrice(), productModel, priceBO.getCurrency().getCode(), weight);
						uploadorderFacade.createPriceRowForUser(priceBO.getDisplayPrice(), productModel, priceBO.getCurrency()
								.getCode(), weight);
					}
					final List<ProblemBO> problemBOList = item.getProblemItemList();
					if (item.hasProblemItem() || item.isKit())
					{
						String partProblem = null;
						if (item.isKit())
						{
							partProblem = getMessageSource().getMessage(MessageResourceUtil.SOLD_IN_SETS_ONLY, null,
									getI18nService().getCurrentLocale());
							LOG.info("Inside isKit Con Problem::" + partProblem);
							isValidPart = false;
						}
						if (problemBOList != null && !problemBOList.isEmpty())
						{
							for (final ProblemBO problem : problemBOList)
							{
								LOG.info("resolvedItem : " + item.getPartNumber() + "message :" + problem.getMessageKey());
								LOG.info("resolvedItem : " + item.getPartNumber() + "Status :" + problem.getStatusCategory().name());
								String issues = null;
								if (MessageResourceUtil.SOLD_IN_MULTIPLES.equals(problem.getMessageKey()))
								{
									final int qty = roundUpToMultiplesQuantity(item.getItemQty().getRequestedQuantity(), item.getItemQty()
											.getSoldInMultipleQuantity());
									final String[] parms =
									{ item.getPartNumber(), String.valueOf(item.getItemQty().getSoldInMultipleQuantity()).toString() };
									issues = getMessageSource().getMessage(problem.getMessageKey(), parms,
											getI18nService().getCurrentLocale())
											+ "@" + qty;
									model.addAttribute("qty", qty);
								}
								else if (MessageResourceUtil.MULITPLE_PARTS_FOUND.equals(problem.getMessageKey()))
								{
									//issues = getMessageSource().getMessage(problem.getMessageKey(), null,
									//getI18nService().getCurrentLocale());
									isValidPart = true;
								}
								else if (MessageResourceUtil.PRICE_UNAVAILABLE.equals(problem.getMessageKey()))
								{
									//issues = getMessageSource().getMessage(problem.getMessageKey(), null,
									//getI18nService().getCurrentLocale());
									isValidPart = true;
								}
								else if (MessageResourceUtil.PART_SUPERCEDED.equals(problem.getMessageKey()))
								{
									final String[] parms =
									{ item.getDisplayPartNumber(), item.getPartNumber() };
									issues = getMessageSource().getMessage(problem.getMessageKey(), parms,
											getI18nService().getCurrentLocale());
								}
								else if (MessageResourceUtil.REASONABLE_QUANTITY_THRESHOLD.equals(problem.getMessageKey()))
								{
									issues = getMessageSource().getMessage(problem.getMessageKey(), null,
											getI18nService().getCurrentLocale());
								}
								else if (problem.getMessageKey() != null && !problem.getMessageKey().isEmpty())
								{
									issues = getMessageSource().getMessage(problem.getMessageKey(), null,
											getI18nService().getCurrentLocale());
									isValidPart = false;
									if (MessageResourceUtil.VINTAGE_PART.equals(problem.getMessageKey()))
									{
										isValidPart = true;
									}
								}

								if (null != problem.getStatusCategory()
										&& !"PART_FOUND".equalsIgnoreCase(problem.getStatusCategory().name()))
								{
									issues = getMessageSource().getMessage(problem.getMessageKey(), null,
											getI18nService().getCurrentLocale());
									isValidPart = false;
								}
								if (issues != null)
								{
									if (partProblem == null)
									{
										partProblem = issues;
									}
									else
									{
										partProblem = partProblem + "~" + issues;
									}
								}
							}
						}
						if (partProblem != null)
						{
							model.addAttribute("isValidPart", isValidPart);
							return partProblem;
						}
					}

				}
			}
			catch (final Exception e)
			{
				e.printStackTrace();
				isValidPart = false;
				model.addAttribute("isValidPart", isValidPart);
				return getMessageSource().getMessage(MessageResourceUtil.NOT_ALLOWED_TO_ORDER, null,
						getI18nService().getCurrentLocale());
			}

		}
		model.addAttribute("isValidPart", isValidPart);
		return "success";
	}

	protected final List<ItemBO> womQuickUploadOrder(final List<ItemBO> anItemBOList, final AccountBO billTo,
			final AccountBO shipTo, final String poNumber)
	{
		final ItemASUSCanImpl partResolutionservice = WOMServices.getPartResolutionService();
		List<ItemBO> itemsOrderable = new ArrayList<ItemBO>();
		try
		{
			itemsOrderable = partResolutionservice.checkOrderable(poNumber, anItemBOList, billTo, shipTo);
		}
		catch (WOMExternalSystemException | WOMValidationException e)
		{
			LOG.error("WOMExternalSystemException::" + e.getMessage());
		}
		catch (final WOMTransactionException e)
		{
			LOG.error("WOMTransactionException::" + e.getMessage());
		}
		catch (final WOMBusinessDataException e)
		{
			LOG.error("WOMBusinessDataException::" + e.getMessage());
		}
		return itemsOrderable;
	}

	private QuantityBO getDefaultQuanity()
	{

		final QuantityBO qty = new QuantityBO();
		qty.setRequestedQuantity(1);
		return qty;
	}

	private int roundUpToMultiplesQuantity(final int inputQuantity, final int soldInMultiplesQuantity)
	{

		final int upMultiple = (int) Math.ceil((double) inputQuantity / (double) soldInMultiplesQuantity);
		return upMultiple * soldInMultiplesQuantity;
	}

	public void createPriceRowForUser(final Double price, final FMPartModel part, final String currency, final WeightBO weight)
	{
		final UserModel user = userService.getCurrentUser();
		final Collection<PriceRowModel> partPrices = new LinkedList<PriceRowModel>();
		final Collection<PriceRowModel> pricerowModels = part.getEurope1Prices();
		for (final PriceRowModel pricerowModel : pricerowModels)
		{
			if (pricerowModel.getUser() != null && pricerowModel.getUser().getUid().equalsIgnoreCase(user.getUid()))
			{
				modelService.remove(pricerowModel.getPk());
			}
			partPrices.add(pricerowModel);
		}
		modelService.refresh(part);
		final PriceRowModel priceRowModel = modelService.create(PriceRowModel.class);
		priceRowModel.setProduct(part);
		priceRowModel.setCatalogVersion(part.getCatalogVersion());
		//if (price != 0.0)
		if (getSessionService().getAttribute("logedUserType").equals("ShipTo")){
			priceRowModel.setPrice(0.0);
		}
		else{
		if (Double.doubleToLongBits(price) != 0)
		{
			priceRowModel.setPrice(price);
		}
		else
		{
			priceRowModel.setPrice(0.0);
		}
		}		/*
		 * if (currency != null && ("CAD").equalsIgnoreCase(currency)) {
		 * priceRowModel.setCurrency(commonI18NService.getCurrency(currency)); } else {
		 * priceRowModel.setCurrency(commonI18NService.getCurrency("USD")); }
		 */
		priceRowModel.setCurrency(commonI18NService.getCurrency("USD"));
		UnitModel unit = productService.getUnit("lbs");
		if (unit == null)
		{
			unit = createUnit("lbs", "pound", "FM-pound", 1.0);
		}
		priceRowModel.setUnit(unit);
		priceRowModel.setUnitFactor(1);
		priceRowModel.setUser(user);
		partPrices.add(priceRowModel);
		part.setEurope1Prices(partPrices);
		part.setUnit(unit);
		part.setWeightUOM(String.valueOf(weight.getWeight()).toString());
		modelService.save(part);
	}

	private void createStockLevel(final FMPartModel part)
	{
		final WarehouseModel wareHouseModel = warehouseService.getWarehouseForCode("federalmogul");
		LOG.info("Ware House ==>" + siteConfigService.getString("fm.part.warehouse", "default"));
		final StockLevelModel stockLevel = modelService.create(StockLevelModel.class);
		stockLevel.setProductCode(part.getCode());
		stockLevel.setProduct(part);
		stockLevel.setWarehouse(wareHouseModel);
		stockLevel.setAvailable(100000);
		stockLevel.setOverSelling(0);
		stockLevel.setReserved(0);
		//stockLevel.setInStockStatus(status);
		modelService.save(stockLevel);
	}

	private UnitModel createUnit(final String code, final String name, final String type, final Double conversion)
	{
		final UnitModel unit = modelService.create(UnitModel.class);
		unit.setCode(code);
		unit.setName(name);
		unit.setUnitType(type);
		unit.setConversion(conversion);
		modelService.save(unit);
		return unit;
	}
}
