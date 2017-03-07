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
package com.federalmogul.storefront.breadcrumb.impl;

import de.hybris.platform.catalog.model.classification.ClassificationClassModel;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commerceservices.url.UrlResolver;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.variants.model.VariantProductModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import de.hybris.platform.servicelayer.i18n.I18NService;
import org.springframework.context.MessageSource;
import com.federalmogul.storefront.breadcrumb.Breadcrumb;
import com.federalmogul.storefront.history.BrowseHistory;


/**
 * ProductBreadcrumbBuilder implementation for {@link ProductData}
 */
public class LoyaltyProductBreadcrumbBuilder
{
	private static final String LAST_LINK_CLASS = "active";
	private static final Logger LOG = Logger.getLogger(LoyaltyProductBreadcrumbBuilder.class);
	private UrlResolver<ProductModel> productModelUrlResolver;
	private UrlResolver<CategoryModel> categoryModelUrlResolver;
	private BrowseHistory browseHistory;
	private MessageSource messageSource;
	private I18NService i18nService;

	private static final String REDEEM_URL = "/lsearch?q=:name-asc:&amp;text=#";
	private static final String GARAGE_REWARD_HOME_URL = "/?site=loyalty";

	public List<Breadcrumb> getLoyaltyBreadcrumbs(final ProductModel productModel) throws IllegalArgumentException
	{

		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		final List<Breadcrumb> addbreadcrumbs = getInternalLoyaltyBreadcrumbs(productModel);

		breadcrumbs.add(new Breadcrumb(GARAGE_REWARD_HOME_URL, getMessageSource().getMessage("Garage Rewards", null,
				getI18nService().getCurrentLocale()), null));
		breadcrumbs.add(new Breadcrumb(REDEEM_URL, getMessageSource().getMessage("Redeem Points", null,
				getI18nService().getCurrentLocale()), null));
        return breadcrumbs;
	}     
   
	public List<Breadcrumb> getInternalLoyaltyBreadcrumbs(final ProductModel productModel) throws IllegalArgumentException
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
               
		final Collection<CategoryModel> categoryModels = new ArrayList<CategoryModel>();
		final Breadcrumb last;

		final ProductModel baseProductModel = getBaseProduct(productModel);
		last = getLoyaltyProductBreadcrumb(baseProductModel);
		categoryModels.addAll(baseProductModel.getSupercategories());
		last.setLinkClass(LAST_LINK_CLASS);
                
		breadcrumbs.add(last);

		while (!categoryModels.isEmpty())
		{
			CategoryModel toDisplay = null;
			for (final CategoryModel categoryModel : categoryModels)
			{
				if (!(categoryModel instanceof ClassificationClassModel))
				{
					if (toDisplay == null)
					{
						toDisplay = categoryModel;
					}
					if (getBrowseHistory().findUrlInHistory(categoryModel.getCode()) != null)
					{
						break;
					}
				}
			}
			categoryModels.clear();
			if (toDisplay != null)
			{
                             
				breadcrumbs.add(getLoyaltyCategoryBreadcrumb(toDisplay));
				categoryModels.addAll(toDisplay.getSupercategories());
			}
		}
		Collections.reverse(breadcrumbs);
		return breadcrumbs;
	}

	protected ProductModel getBaseProduct(final ProductModel product)
	{
		if (product instanceof VariantProductModel)
		{
			return getBaseProduct(((VariantProductModel) product).getBaseProduct());
		}
		return product;
	}

	protected Breadcrumb getLoyaltyProductBreadcrumb(final ProductModel product)
	{
		String productUrl = getProductModelUrlResolver().resolve(product);
		productUrl = productUrl.replace("/p/", "/lp/");
		LOG.info("product url::::::::::::::::::::::::::::::::" + productUrl);
		return new Breadcrumb(productUrl, product.getName(), null);
	}

	protected Breadcrumb getLoyaltyCategoryBreadcrumb(final CategoryModel category)
	{
		String categoryUrl = getCategoryModelUrlResolver().resolve(category);
		LOG.info(" before CATEGORY URL:::::::::::::::::::::" + categoryUrl);
		categoryUrl = categoryUrl.replace("/c/", "/lc/");
		LOG.info(" After CATEGORY URL:::::::::::::::::::::" + categoryUrl);
		LOG.info(" cat name:::::::::::::::::::::" + category.getName());
		return new Breadcrumb(categoryUrl, category.getName(), null);
	}

	protected UrlResolver<ProductModel> getProductModelUrlResolver()
	{
		return productModelUrlResolver;
	}

	@Required
	public void setProductModelUrlResolver(final UrlResolver<ProductModel> productModelUrlResolver)
	{
		this.productModelUrlResolver = productModelUrlResolver;
	}

	protected UrlResolver<CategoryModel> getCategoryModelUrlResolver()
	{
		return categoryModelUrlResolver;
	}

	@Required
	public void setCategoryModelUrlResolver(final UrlResolver<CategoryModel> categoryModelUrlResolver)
	{
		this.categoryModelUrlResolver = categoryModelUrlResolver;
	}


	protected BrowseHistory getBrowseHistory()
	{
		return browseHistory;
	}


	@Required
	public void setBrowseHistory(final BrowseHistory browseHistory)
	{
		this.browseHistory = browseHistory;
	}

	protected I18NService getI18nService()
	{
		return i18nService;
	}

	@Required
	public void setI18nService(final I18NService i18nService)
	{
		this.i18nService = i18nService;
	}

	protected MessageSource getMessageSource()
	{
		return messageSource;
	}

	@Required
	public void setMessageSource(final MessageSource messageSource)
	{
		this.messageSource = messageSource;
	}


}
