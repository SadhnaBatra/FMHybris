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


import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldNameProvider;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;
import de.hybris.platform.solrfacetsearch.provider.FieldValueProvider;
import de.hybris.platform.solrfacetsearch.provider.impl.AbstractPropertyFieldValueProvider;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Required;

import com.federalmogul.falcon.core.model.FMFitmentModel;


public class FitmentCodeValueProvider extends AbstractPropertyFieldValueProvider implements FieldValueProvider, Serializable
{
	private FieldNameProvider fieldNameProvider;

	protected FieldNameProvider getFieldNameProvider()
	{
		return this.fieldNameProvider;
	}

	@Required
	public void setFieldNameProvider(final FieldNameProvider fieldNameProvider)
	{
		this.fieldNameProvider = fieldNameProvider;
	}


	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty,
			final Object model) throws FieldValueProviderException
	{
		Collection<FMFitmentModel> fitments = null;
		if (model instanceof ProductModel)
		{
			fitments = modelService.getAttributeValue(model, "fitments");
		}
		if (fitments != null && !fitments.isEmpty())
		{
			final Collection<FieldValue> fieldValues = new ArrayList<FieldValue>();
			for (final FMFitmentModel fitment : fitments)
			{
				final Collection<String> fieldNames = getFieldNameProvider().getFieldNames(indexedProperty, null);
				for (final String fieldName : fieldNames)
				{
					fieldValues.add(new FieldValue(fieldName, getPropertyValue(fitment)));
				}

			}
			return fieldValues;
		}
		else
		{
			return Collections.emptyList();
		}

	}


	protected Object getPropertyValue(final FMFitmentModel model)
	{
		return getPropertyValue(model, "code");
	}

	protected Object getPropertyValue(final Object model, final String propertyName)
	{
		return this.modelService.getAttributeValue(model, propertyName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hybris.platform.solrfacetsearch.provider.FieldValueProvider#getFieldValues(de.hybris.platform.solrfacetsearch
	 * .config.IndexConfig, de.hybris.platform.solrfacetsearch.config.IndexedProperty, java.lang.Object)
	 */


}

/*
 * Location:
 * D:\Mahaveer\Projects\FM\Hybris5.3\hybris\hybris\bin\ext-commerce\commerceservices\bin\commerceservicesserver.jar
 * Qualified Name: de.hybris.platform.commerceservices.search.solrfacetsearch.provider.impl.CategoryCodeValueProvider
 * Java Class Version: 7 (51.0) JD-Core Version: 0.5.3
 */
