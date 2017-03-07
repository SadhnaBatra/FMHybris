/**
 * 
 */
package com.federalmogul.core.solr.provider;

import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.customerreview.CustomerReviewService;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldNameProvider;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;
import de.hybris.platform.solrfacetsearch.provider.FieldValueProvider;
import de.hybris.platform.solrfacetsearch.provider.impl.AbstractPropertyFieldValueProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Required;


/**
 * @author SR279690
 * 
 */
public class ReviewCountValueProvider extends AbstractPropertyFieldValueProvider implements FieldValueProvider
{
	private FieldNameProvider fieldNameProvider;
	private CustomerReviewService customerReviewService;

	protected CustomerReviewService getCustomerReviewService()
	{
		return this.customerReviewService;
	}

	@Required
	public void setCustomerReviewService(final CustomerReviewService customerReviewService)
	{
		this.customerReviewService = customerReviewService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hybris.platform.solrfacetsearch.provider.FieldValueProvider#getFieldValues(de.hybris.platform.solrfacetsearch
	 * .config.IndexConfig, de.hybris.platform.solrfacetsearch.config.IndexedProperty, java.lang.Object)
	 */
	@Override
	public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty,
			final Object model) throws FieldValueProviderException
	{

		if (model instanceof ProductModel)
		{
			final ProductModel product = (ProductModel) model;

			final Collection fieldValues = new ArrayList();

			if (indexedProperty.isLocalized())
			{
				final Collection<LanguageModel> languages = indexConfig.getLanguages();
				for (final LanguageModel language : languages)
				{
					fieldValues.addAll(createFieldValue(product, language, indexedProperty));
				}
			}
			else
			{
				fieldValues.addAll(createFieldValue(product, null, indexedProperty));
			}
			return fieldValues;

		}

		throw new FieldValueProviderException("Cannot evaluate rating of non-product item");
	}

	protected List<FieldValue> createFieldValue(final ProductModel product, final LanguageModel language,
			final IndexedProperty indexedProperty)
	{
		final List fieldValues = new ArrayList();

		final int productRating = getProductRating(product, language);
		if (productRating > 0)
		{
			addFieldValues(fieldValues, indexedProperty, language, productRating);
		}

		return fieldValues;
	}


	protected void addFieldValues(final List<FieldValue> fieldValues, final IndexedProperty indexedProperty,
			final LanguageModel language, final Object value)
	{
		final Collection<String> fieldNames = getFieldNameProvider().getFieldNames(indexedProperty,
				(language == null) ? null : language.getIsocode());
		for (final String fieldName : fieldNames)
		{
			fieldValues.add(new FieldValue(fieldName, value));
		}
	}


	protected int getProductRating(final ProductModel product, final LanguageModel language)
	{
		final int reviewCount = getCustomerReviewService().getNumberOfReviews(product);
		//final CustomerReviewModel rev = getCustomerReviewService().getReviewsForProduct(product);
		/*
		 * final int i = getCustomerReviewService().getNumberOfReviews(product); for (final CustomerReviewModel review :
		 * getCustomerReviewService().getReviewsForProduct(product)) { if (((language != null) &&
		 * (!(language.equals(review.getLanguage())))) || (review.getRating() == null) || (review.getApprovalStatus() !=
		 * CustomerReviewApprovalType.APPROVED)) { continue; } ++count; sum += review.getRating().doubleValue();
		 * 
		 * }
		 * 
		 * if (count > 0) { return Double.valueOf(sum / count); }
		 */
		return reviewCount;
	}

	/**
	 * @return the fieldNameProvider
	 */
	public FieldNameProvider getFieldNameProvider()
	{
		return fieldNameProvider;
	}


	/**
	 * @param fieldNameProvider
	 *           the fieldNameProvider to set
	 */
	public void setFieldNameProvider(final FieldNameProvider fieldNameProvider)
	{
		this.fieldNameProvider = fieldNameProvider;
	}

}
