/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2014 hybris AG
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of hybris
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with hybris.
 * 
 *  
 */
package com.federalmogul.facades.search.converters.impl;

import de.hybris.platform.commercefacades.converter.ConfigurablePopulator;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.search.data.SearchQueryData;
import de.hybris.platform.commercefacades.search.data.SearchStateData;
import de.hybris.platform.commerceservices.search.facetdata.ProductSearchPageData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.util.Config;
import com.federalmogul.facades.search.converters.AbstractB2BFlexibleSearchConverter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Required;


public class DefaultB2BFlexibleSearchConverter extends AbstractB2BFlexibleSearchConverter<ProductSearchPageData> implements
		Serializable
{
	private static final String ADVANCED_SEARCH_PRODUCT_IDS_DELIMITER = "storefront.advancedsearch.delimiter";
	private ConfigurablePopulator<ProductModel, ProductData, ProductOption> productConfiguredPopulator;
	private Collection<String> skus;
	private Collection<ProductOption> options;

	@Override
	public ProductSearchPageData convert(final SearchPageData searchPageData, final ProductSearchPageData target)
			throws ConversionException
	{

		target.setResults(getProductDataList(searchPageData));
		target.setPagination(searchPageData.getPagination());
		target.setSorts(searchPageData.getSorts());
		target.setCurrentQuery(getSearchStateData());

		return target;
	}


	@Override
	protected ProductSearchPageData createDataObject()
	{
		return new ProductSearchPageData();
	}

	@Required
	public void setProductConfiguredPopulator(
			final ConfigurablePopulator<ProductModel, ProductData, ProductOption> productConfiguredPopulator)
	{
		this.productConfiguredPopulator = productConfiguredPopulator;
	}

	protected SearchStateData getSearchStateData()
	{
		final SearchQueryData searchQueryData = new SearchQueryData();
		searchQueryData.setValue(StringUtils.join(skus.toArray(), Config.getParameter(ADVANCED_SEARCH_PRODUCT_IDS_DELIMITER)));

		final SearchStateData searchStateData = new SearchStateData();
		searchStateData.setQuery(searchQueryData);

		return searchStateData;
	}

	protected List<ProductData> getProductDataList(final SearchPageData searchPageData)
	{

		final List<ProductModel> productModelList = searchPageData.getResults();
		final List<ProductData> productDataList = new ArrayList<>(productModelList.size());

		for (final ProductModel productModel : productModelList)
		{
			final ProductData productData = new ProductData();

			if (productConfiguredPopulator != null && productModel != null)
			{
				productConfiguredPopulator.populate(productModel, productData, options);
			}

			productDataList.add(productData);
		}

		return productDataList;
	}

	public void setSkus(final Collection<String> skus)
	{
		this.skus = skus;
	}

	public void setOptions(final Collection<ProductOption> options)
	{
		this.options = options;
	}


}
