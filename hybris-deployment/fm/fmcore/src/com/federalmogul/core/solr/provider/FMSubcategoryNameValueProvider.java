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


public class FMSubcategoryNameValueProvider extends CategoryCodeValueProvider
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

		final Set<CategoryModel> rootCategories = rootCategories(indexConfig.getCatalogVersions());
		final Collection<CategoryModel> categories = getCategorySource().getCategoriesForConfigAndProperty(indexConfig,
				indexedProperty, model);
		if ((rootCategories != null) && (!(rootCategories.isEmpty())))
		{
			final Collection<FieldValue> fieldValues = new ArrayList<FieldValue>();
			for (final CategoryModel rootCategory : rootCategories)
			{
				for (final CategoryModel subCategory : rootCategory.getCategories())
				{
					for (final CategoryModel category : categories)
					{
						if (subCategory.getCode() == category.getCode() && rootCategory.getSupercategories().size() == 0
								&& subCategory.getSupercategories().size() == 1 && category.getSupercategories().size() == 1)
						{
							//categories.remove(category);
							final Collection<String> fieldNames = getFieldNameProvider().getFieldNames(indexedProperty, null);
							for (final String fieldName : fieldNames)
							{
								fieldValues.add(new FieldValue(fieldName, getPropertyValue(subCategory, indexedProperty)));
							}
						}
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

	protected Set<CategoryModel> rootCategories(final Collection<CatalogVersionModel> catalogVersions)
	{

		final Set<CategoryModel> result = new HashSet();

		for (final CatalogVersionModel catalogVersion : catalogVersions)
		{
			try
			{
				for (final CategoryModel category : catalogVersion.getRootCategories())
				{
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

	protected Object getPropertyValue(final Object model, final IndexedProperty indexedProperty)
	{
		String qualifier = indexedProperty.getValueProviderParameter();

		if ((qualifier == null) || (qualifier.trim().isEmpty()))
		{
			qualifier = "name";
		}
		return this.modelService.getAttributeValue(model, qualifier);
	}


	protected Set<CategoryModel> subCategories(final Collection<CatalogVersionModel> catalogVersions)
	{

		final Set<CategoryModel> result = new HashSet();

		for (final CatalogVersionModel catalogVersion : catalogVersions)
		{
			try
			{
				//if(catalogVersion.)
				for (final CategoryModel category : catalogVersion.getRootCategories())
				{
					//category.getAllSubcategories();
					//String code = category.getCode();
					LOG.error("category ==>" + category.getCode());
					LOG.error("Subcategories.getCategories() ==>" + category.getCategories().size());
					LOG.error("All Sub category.getAllSubcategories() ==>" + category.getAllSubcategories().size());
					LOG.error("category.getAllSupercategories() ==>" + category.getAllSupercategories().size());

					for (final CategoryModel subCategory : category.getCategories())
					{

						if (subCategory.getSupercategories().contains(category) && category.getSupercategories().size() == 0
								&& subCategory.getSupercategories().size() == 1
								&& category.getCode().equals(subCategory.getSupercategories().get(0).getCode()))
						{
							LOG.error("Sub Category Super == >" + subCategory.getSupercategories().get(0).getCode());
							LOG.error("Category :: " + category.getCode() + "Sub Category :: Code :: " + subCategory.getCode()
									+ ":: Name :: " + subCategory.getName());
							LOG.error("category.getSupercategories().size() ==>" + category.getSupercategories().size());
							LOG.error("subCategory.getSupercategories().size() ==>" + subCategory.getSupercategories().size());

							//this.categorySource.isBlockedCategory(subCategory);
							result.add(subCategory);
						}

					}
					/*
					 * if (category.getSupercategories().isEmpty() && category.getProducts().isEmpty()) {
					 * result.addAll(category.getCategories()); }
					 */

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
