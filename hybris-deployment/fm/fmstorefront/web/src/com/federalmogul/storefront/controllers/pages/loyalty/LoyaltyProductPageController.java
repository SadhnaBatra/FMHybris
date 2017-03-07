/**
 * 
 */
package com.federalmogul.storefront.controllers.pages.loyalty;

import de.hybris.platform.acceleratorservices.controllers.page.PageType;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;
import de.hybris.platform.commercefacades.product.ProductFacade;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.BaseOptionData;
import de.hybris.platform.commercefacades.product.data.ImageData;
import de.hybris.platform.commercefacades.product.data.ImageDataType;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commerceservices.url.UrlResolver;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.util.Config;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;

import com.federalmogul.storefront.breadcrumb.impl.LoyaltyProductBreadcrumbBuilder;
import com.federalmogul.storefront.breadcrumb.impl.ProductBreadcrumbBuilder;
import com.federalmogul.storefront.constants.WebConstants;
import com.federalmogul.storefront.controllers.pages.AbstractPageController;
import com.federalmogul.storefront.forms.ReviewForm;
import com.federalmogul.storefront.util.MetaSanitizerUtil;
import com.federalmogul.storefront.variants.VariantSortStrategy;


/**
 * @author SI279688
 * 
 */
@Controller
@RequestMapping(value = "/**/lp")
public class LoyaltyProductPageController extends AbstractPageController
{
	private static final Logger LOG = Logger.getLogger(LoyaltyProductPageController.class);
	private static final String PRODUCT_CODE_PATH_VARIABLE_PATTERN = "/{productCode:.*}";
	private static final String FUTURE_STOCK_ENABLED = "storefront.products.futurestock.enabled";

	@Resource(name = "productService")
	private ProductService productService;

	@Resource(name = "productModelUrlResolver")
	private UrlResolver<ProductModel> productModelUrlResolver;

	@Resource(name = "b2bProductFacade")
	private ProductFacade productFacade;

	@Resource(name = "productBreadcrumbBuilder")
	private ProductBreadcrumbBuilder productBreadcrumbBuilder;

	@Resource(name = "loyaltyProductBreadcrumbBuilder")
	private LoyaltyProductBreadcrumbBuilder loyaltyProductBreadcrumbBuilder;

	@Resource(name = "variantSortStrategy")
	private VariantSortStrategy variantSortStrategy;

	@RequestMapping(value = PRODUCT_CODE_PATH_VARIABLE_PATTERN, method = RequestMethod.GET)
	public String productDetail(@PathVariable("productCode") final String productCode, final Model model,
			final HttpServletRequest request, final HttpServletResponse response,
			@RequestParam(value = "variantSelected", required = false) final String variantSelected)
			throws CMSItemNotFoundException, UnsupportedEncodingException
	{
		LOG.info("INSIDE LP CONTROLLER");
		ProductModel productModel = productService.getProductForCode(productCode);
		final boolean isVariantSelected = (null != variantSelected && variantSelected.equalsIgnoreCase("true")) ? true : false;

		LOG.info("PRODUCTMNODELLLLLLKHSKJFKDBFKJDBFDBFKJDBFKBDFK orginal" + productModel.getCode());

		if (!productModel.getVariants().isEmpty())
		{
			final String code = productModel.getVariants().iterator().next().getCode();
			productModel = productService.getProductForCode(code);
		}


		model.addAttribute("isVariantSelected", isVariantSelected);

		final String redirection = checkRequestUrlLoyalty(request, response, productModelUrlResolver.resolve(productModel));
		if (StringUtils.isNotEmpty(redirection))
		{
			return redirection;
		}

		LOG.info("PRODUCTMNODELLLLLLKHSKJFKDBFKJDBFDBFKJDBFKBDFK orginal" + productModel.getCode());


		updatePageTitle(productModel, model);
		final List<ProductOption> extraOptions = Arrays.asList(ProductOption.VARIANT_MATRIX_BASE, ProductOption.VARIANT_MATRIX_URL,
				ProductOption.VARIANT_MATRIX_MEDIA);
		populateProductDetailForDisplay(productModel, model, request, extraOptions);
		model.addAttribute(new ReviewForm());
		model.addAttribute("pageType", PageType.PRODUCT.name());
		model.addAttribute("futureStockEnabled", Boolean.valueOf(Config.getBoolean(FUTURE_STOCK_ENABLED, false)));
		model.addAttribute("TotalPoints", getSessionService().getAttribute(WebConstants.BALANCE_LOYALTY_POINTS));
		final String metaKeywords = MetaSanitizerUtil.sanitizeKeywords(productModel.getKeywords());
		final String metaDescription = MetaSanitizerUtil.sanitizeDescription(productModel.getDescription());
		setUpMetaData(model, metaKeywords, metaDescription);
		LOG.info("B44 GET VIEW");
		return getViewForPage(model);
	}

	protected void updatePageTitle(final ProductModel productModel, final Model model)
	{
		storeContentPageTitleInModel(model, getPageTitleResolver().resolveProductPageTitle(productModel));
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
		productData.setLoyaltyPoints(productModel.getLoyaltyPoints());
		LOG.info("PRODUCT CODE)" + productData.getCode());
		LOG.info("PRODUCT NAME" + productData.getName());
		getRequestContextData(request).setProduct(productModel);

		sortVariantOptionData(productData);
		storeCmsPageInModel(model, getPageForProduct(productModel));
		populateProductData(productData, model, request);
		model.addAttribute(WebConstants.BREADCRUMBS_KEY, loyaltyProductBreadcrumbBuilder.getLoyaltyBreadcrumbs(productModel));
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
	protected String checkRequestUrlLoyalty(final HttpServletRequest request, final HttpServletResponse response,
			String resolvedUrlPath) throws UnsupportedEncodingException
	{
		try
		{
			//resolvedUrlPath = "/Hats/ANCO/122/lp/1243";
			resolvedUrlPath = resolvedUrlPath.replace("/p/", "/lp/");
			LOG.info("resolvedUrlPath:::::::::::" + resolvedUrlPath);
			final String resolvedUrl = response.encodeURL(request.getContextPath() + resolvedUrlPath);
			LOG.info("RESOLVED URL" + resolvedUrl);
			LOG.info("RESOLVED PATH" + resolvedUrlPath);
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

}
