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
package com.federalmogul.core.solr.provider;


import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.commerceservices.search.solrfacetsearch.provider.CategorySource;
import de.hybris.platform.commerceservices.search.solrfacetsearch.provider.impl.CategoryCodeValueProvider;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldNameProvider;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Required;


public class FMCategoryNameValueProvider extends CategoryCodeValueProvider
{
	private FieldNameProvider fieldNameProvider;
	private CategorySource categorySource;
	private String categoriesQualifier;

	/**
	 * @return the categoriesQualifier
	 */
	public String getCategoriesQualifier()
	{
		return categoriesQualifier;
	}

	/**
	 * @param categoriesQualifier
	 *           the categoriesQualifier to set
	 */
	public void setCategoriesQualifier(final String categoriesQualifier)
	{
		this.categoriesQualifier = categoriesQualifier;
	}

	/**
	 * @return the categorySource
	 */
	@Override
	public CategorySource getCategorySource()
	{
		return categorySource;
	}

	/**
	 * @param categorySource
	 *           the categorySource to set
	 */
	@Override
	public void setCategorySource(final CategorySource categorySource)
	{
		this.categorySource = categorySource;
	}

	/**
	 * @return the commonI18NService
	 */
	@Override
	public CommonI18NService getCommonI18NService()
	{
		return commonI18NService;
	}

	/**
	 * @param commonI18NService
	 *           the commonI18NService to set
	 */
	@Override
	public void setCommonI18NService(final CommonI18NService commonI18NService)
	{
		this.commonI18NService = commonI18NService;
	}


	private CommonI18NService commonI18NService;
	private boolean includeClassificationClasses;


	@Override
	protected FieldNameProvider getFieldNameProvider()
	{
		return this.fieldNameProvider;
	}

	@Override
	@Required
	public void setFieldNameProvider(final FieldNameProvider fieldNameProvider)
	{
		this.fieldNameProvider = fieldNameProvider;
	}

	public boolean isIncludeClassificationClasses()
	{
		return this.includeClassificationClasses;
	}


	public void setIncludeClassificationClasses(final boolean includeClassificationClasses)
	{
		this.includeClassificationClasses = includeClassificationClasses;
	}


	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty,
			final Object model) throws FieldValueProviderException
	{
		//System.out.println("*************** Invoking getFieldValues Method **************** ");

		final Set<CategoryModel> rootCategories = RootCategories(indexConfig.getCatalogVersions());
		final Collection<CategoryModel> categories = getCategorySource().getCategoriesForConfigAndProperty(indexConfig,
				indexedProperty, model);
		if ((categories != null) && (!(categories.isEmpty())))
		{
			final Collection<FieldValue> fieldValues = new ArrayList<FieldValue>();
			for (final CategoryModel category : categories)
			{
				if (rootCategories != null && !rootCategories.isEmpty() && rootCategories.contains(category))
				{
					final Collection<String> fieldNames = getFieldNameProvider().getFieldNames(indexedProperty, null);
					for (final String fieldName : fieldNames)
					{
						fieldValues.add(new FieldValue(fieldName, getPropertyValue(category, indexedProperty)));
					}
				}


			}

			return fieldValues;
		}
		else
		{
			return Collections.emptyList();
		}

	}



	protected Object getPropertyValue(final Object model, final IndexedProperty indexedProperty)
	{
		String qualifier = indexedProperty.getValueProviderParameter();

		if ((qualifier == null) || (qualifier.trim().isEmpty()))
		{
			qualifier = "name";
		}
		return this.modelService.getAttributeValue(model, qualifier);
	}


	protected Set<CategoryModel> RootCategories(final Collection<CatalogVersionModel> catalogVersions)
	{
		//System.out.println("*************** Invoking RootCategories Method **************** ");


		final Set<CategoryModel> result = new HashSet();

		for (final CatalogVersionModel catalogVersion : catalogVersions)
		{
			//System.out.println(": Catalog ID : " + catalogVersion.getCatalog().getId());
			//System.out.println(": Catalog Version : " + catalogVersion.getCatalog().getVersion());
			try
			{
				for (final CategoryModel category : catalogVersion.getRootCategories())
				{
					//System.out.println("category Name:  " + category.getName());
					LOG.info("category Name:  " + category.getName());
					result.add(category);
				}
				return result;

			}
			catch (final UnknownIdentifierException localUnknownIdentifierException)
			{
				LOG.warn("Failed to load category from catalog version [" + catalogVersion + "]");
			}
		}

		if (result.isEmpty())
		{
			LOG.error("Failed to load category from catalog version [" + catalogVersions + "]");
		}
		else
		{
			return result;
		}


		return null;
	}



}
