/**
 * 
 */
package com.federalmogul.core.search.solrfacetsearch.populators;

import de.hybris.platform.commerceservices.search.facetdata.FacetData;
import de.hybris.platform.commerceservices.search.facetdata.FacetSearchPageData;
import de.hybris.platform.commerceservices.search.facetdata.FacetValueData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchQueryData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchQueryTermData;
import de.hybris.platform.commerceservices.search.solrfacetsearch.data.SolrSearchResponse;
import de.hybris.platform.commerceservices.search.solrfacetsearch.provider.TopValuesProvider;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.IndexedType;
import de.hybris.platform.solrfacetsearch.search.Facet;
import de.hybris.platform.solrfacetsearch.search.FacetValue;
import de.hybris.platform.solrfacetsearch.search.SearchQuery;
import de.hybris.platform.solrfacetsearch.search.SearchResult;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.util.CollectionUtils;


/**
 * @author mamud
 * 
 */
public class FMSearchResponseFacetsPopulator<FACET_SEARCH_CONFIG_TYPE, INDEXED_TYPE_TYPE, INDEXED_PROPERTY_TYPE, INDEXED_TYPE_SORT_TYPE, ITEM>
		implements
		Populator<SolrSearchResponse<FACET_SEARCH_CONFIG_TYPE, INDEXED_TYPE_TYPE, INDEXED_PROPERTY_TYPE, SearchQuery, INDEXED_TYPE_SORT_TYPE, SearchResult>, FacetSearchPageData<SolrSearchQueryData, ITEM>>,
		BeanFactoryAware
{
	private BeanFactory beanFactory;
	String ymmSelected = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.hybris.platform.converters.Populator#populate(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void populate(
			final SolrSearchResponse<FACET_SEARCH_CONFIG_TYPE, INDEXED_TYPE_TYPE, INDEXED_PROPERTY_TYPE, SearchQuery, INDEXED_TYPE_SORT_TYPE, SearchResult> source,
			final FacetSearchPageData<SolrSearchQueryData, ITEM> target)
	{
		// YTODO Auto-generated method stub
		final SearchResult solrSearchResult = source.getSearchResult();
		final SolrSearchQueryData solrSearchQueryData = target.getCurrentQuery();
		final IndexedType indexedType = source.getRequest().getSearchQuery().getIndexedType();
		ymmFacetSelected(solrSearchQueryData);
		target.setFacets(buildFacets(solrSearchResult, solrSearchQueryData, indexedType));

	}

	protected List<FacetData<SolrSearchQueryData>> buildFacets(final SearchResult solrSearchResult,
			final SolrSearchQueryData searchQueryData, final IndexedType indexedType)
	{
		final List solrSearchResultFacets = solrSearchResult.getFacets();
		final List result = new ArrayList(solrSearchResultFacets.size());

		for (final Facet facet : solrSearchResult.getFacets())
		{
			final IndexedProperty indexedProperty = indexedType.getIndexedProperties().get(facet.getName());
			final FacetData facetData = createFacetData();
			facetData.setCode(facet.getName());
			facetData.setCategory(indexedProperty.isCategoryField());
			final String displayName = indexedProperty.getDisplayName();
			facetData.setName((displayName == null) ? facet.getName() : displayName);
			facetData.setMultiSelect(indexedProperty.isMultiSelect());
			facetData.setPriority(indexedProperty.getPriority());
			facetData.setVisible(indexedProperty.isVisible());

			buildFacetValues(facetData, facet, indexedProperty, solrSearchResult, searchQueryData);


			if ((facetData.getValues() == null) || (facetData.getValues().isEmpty()))
			{
				continue;
			}
			result.add(facetData);

		}

		return result;
	}

	protected void buildFacetValues(final FacetData<SolrSearchQueryData> facetData, final Facet facet,
			final IndexedProperty indexedProperty, final SearchResult solrSearchResult, final SolrSearchQueryData searchQueryData)
	{
		final List facetValues = facet.getFacetValues();
		if ((facetValues == null) || (facetValues.isEmpty()))
		{
			return;
		}
		final List allFacetValues = new ArrayList(facetValues.size());

		for (final FacetValue facetValue : facet.getFacetValues())
		{
			if (isYMMFacetSelected(facetData, facetValue))
			{
				final FacetValueData facetValueData = buildFacetValue(facetData, facet, facetValue, solrSearchResult, searchQueryData);
				if (facetValueData == null)
				{
					continue;
				}
				allFacetValues.add(facetValueData);
			}
		}

		facetData.setValues(allFacetValues);

		final TopValuesProvider topValuesProvider = getTopValuesProvider(indexedProperty);
		if ((isRanged(indexedProperty)) || (topValuesProvider == null))
		{
			return;
		}
		final List topFacetValues = topValuesProvider.getTopValues(indexedProperty, facetValues);
		if (topFacetValues == null)
		{
			return;
		}
		final List topFacetValuesData = new ArrayList();
		for (final FacetValue facetValue : topValuesProvider.getTopValues(indexedProperty, facetValues))
		{
			final FacetValueData topFacetValueData = buildFacetValue(facetData, facet, facetValue, solrSearchResult, searchQueryData);
			if (topFacetValueData == null)
			{
				continue;
			}
			topFacetValuesData.add(topFacetValueData);
		}

		facetData.setTopValues(topFacetValuesData);
	}

	protected FacetValueData<SolrSearchQueryData> buildFacetValue(final FacetData<SolrSearchQueryData> facetData,
			final Facet facet, final FacetValue facetValue, final SearchResult solrSearchResult,
			final SolrSearchQueryData searchQueryData)
	{
		if (facetData.isMultiSelect())
		{
			final FacetValueData facetValueData = createFacetValueData();
			final String facetValueName = ymmSelected != null ? facetValue.getName().startsWith(ymmSelected) ? facetValue.getName()
					.substring(ymmSelected.length()) : facetValue.getName() : facetValue.getName();
			final String facetValueDisplayName = ymmSelected != null ? facetValue.getDisplayName().startsWith(ymmSelected) ? facetValue
					.getDisplayName().substring(ymmSelected.length()) : facetValue.getDisplayName()
					: facetValue.getDisplayName();
			facetValueData.setCode(facetValueName);
			facetValueData.setName(facetValueDisplayName);
			facetValueData.setCount(facetValue.getCount());


			facetValueData.setSelected(isFacetSelected(searchQueryData, facet.getName(), facetValue.getName()));

			if (facetValueData.isSelected())

			{
				facetValueData.setQuery(refineQueryRemoveFacet(searchQueryData, facet.getName(), facetValue.getName()));

			}
			else
			{
				facetValueData.setQuery(refineQueryAddFacet(searchQueryData, facet.getName(), facetValue.getName()));
			}

			return facetValueData;


		}

		if (facetValue.getCount() < solrSearchResult.getTotalNumberOfResults())
		{
			final FacetValueData facetValueData = createFacetValueData();
			facetValueData.setCode(facetValue.getName());
			facetValueData.setName(facetValue.getDisplayName());
			facetValueData.setCount(facetValue.getCount());

			if (facetValueData.isSelected())

			{
				facetValueData.setQuery(refineQueryRemoveFacet(searchQueryData, facet.getName(), facetValue.getName()));

			}
			else
			{
				facetValueData.setQuery(refineQueryAddFacet(searchQueryData, facet.getName(), facetValue.getName()));
			}

			return facetValueData;
		}
		return null;
	}

	protected boolean isFacetSelected(final SolrSearchQueryData searchQueryData, final String facetName, final String facetValue)
	{
		for (final SolrSearchQueryTermData termData : searchQueryData.getFilterTerms())
		{
			if ((termData.getKey().equals(facetName)) && (termData.getValue().equals(facetValue)))
			{
				return true;
			}
		}
		return false;
	}

	protected void ymmFacetSelected(final SolrSearchQueryData searchQueryData)
	{
		String years = null;
		//String make = null;
		//String model = null;

		for (final SolrSearchQueryTermData termData : searchQueryData.getFilterTerms())
		{
			if ("year".equals(termData.getKey()))
			{
				final int len = termData.getValue().indexOf("|");
				years = termData.getValue().substring(0, len + 1);

				//years = termData.getValue().split("|");
				break;
			}
			/*
			 * if (termData.getKey().equals("make")) { make = termData.getValue(); } if (termData.getKey().equals("model"))
			 * { model = termData.getValue(); }
			 */
		}

		ymmSelected = years;
	}

	protected boolean isYMMFacetSelected(final FacetData<SolrSearchQueryData> facetData, final FacetValue facetValue)
	{

		boolean facetDataRet;
		if (facetData.getCode().startsWith("Fit-"))
		{
			if (facetValue != null && ymmSelected != null && facetValue.getName().startsWith(ymmSelected)
					&& !facetValue.getName().endsWith("ALL"))
			{
				facetDataRet = true;
				//return true;
			}
			else
			{
				facetDataRet = false;
				//return false;
			}
		}
		else
		{
			facetDataRet = true;
			//return true;
		}

		return facetDataRet;

	}


	protected SolrSearchQueryData refineQueryAddFacet(final SolrSearchQueryData searchQueryData, final String facet,
			final String facetValue)
	{
		final SolrSearchQueryTermData newTerm = createSearchQueryTermData();
		newTerm.setKey(facet);
		newTerm.setValue(facetValue);

		final List newTerms = new ArrayList(searchQueryData.getFilterTerms());
		newTerms.add(newTerm);


		final SolrSearchQueryData result = cloneSearchQueryData(searchQueryData);
		result.setFilterTerms(newTerms);
		return result;
	}


	protected SolrSearchQueryData refineQueryRemoveFacet(final SolrSearchQueryData searchQueryData, final String facet,
			final String facetValue)
	{
		final List newTerms = new ArrayList(searchQueryData.getFilterTerms());


		final Iterator iterator = newTerms.iterator();
		while (iterator.hasNext())
		{
			final SolrSearchQueryTermData term = (SolrSearchQueryTermData) iterator.next();
			if ((!(facet.equals(term.getKey()))) || (!(facetValue.equals(term.getValue()))))
			{
				continue;
			}
			iterator.remove();


		}

		final SolrSearchQueryData result = cloneSearchQueryData(searchQueryData);
		result.setFilterTerms(newTerms);
		return result;
	}

	protected SolrSearchQueryData cloneSearchQueryData(final SolrSearchQueryData source)
	{
		final SolrSearchQueryData target = createSearchQueryData();
		target.setFreeTextSearch(source.getFreeTextSearch());
		target.setCategoryCode(source.getCategoryCode());
		target.setSort(source.getSort());
		target.setFilterTerms(source.getFilterTerms());
		return target;
	}

	protected FacetData<SolrSearchQueryData> createFacetData()
	{
		return new FacetData();
	}

	protected FacetValueData<SolrSearchQueryData> createFacetValueData()
	{
		return new FacetValueData();
	}

	protected SolrSearchQueryTermData createSearchQueryTermData()
	{
		return new SolrSearchQueryTermData();
	}

	protected SolrSearchQueryData createSearchQueryData()
	{
		return new SolrSearchQueryData();
	}

	protected TopValuesProvider getTopValuesProvider(final IndexedProperty property)
	{
		final String name = property.getTopValuesProvider();
		return ((name == null) ? null : (TopValuesProvider) this.beanFactory.getBean(name, TopValuesProvider.class));
	}

	protected boolean isRanged(final IndexedProperty property)
	{
		return (!(CollectionUtils.isEmpty(property.getValueRangeSets())));
	}

	@Override
	public void setBeanFactory(final BeanFactory beanFactory) throws BeansException
	{
		this.beanFactory = beanFactory;
	}
}
