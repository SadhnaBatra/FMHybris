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
 */
package com.falcon.v2.controller;


import de.hybris.platform.commercefacades.storefinder.StoreFinderFacade;
import de.hybris.platform.commercefacades.storelocator.data.PointOfServiceData;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.store.data.GeoPoint;
import de.hybris.platform.commerceservices.storefinder.data.StoreFinderSearchPageData;
import de.hybris.platform.commercewebservicescommons.cache.CacheControl;
import de.hybris.platform.commercewebservicescommons.cache.CacheControlDirective;
import de.hybris.platform.commercewebservicescommons.dto.store.PointOfServiceWsDTO;
import de.hybris.platform.commercewebservicescommons.dto.store.StoreFinderSearchPageWsDTO;
import de.hybris.platform.commercewebservicescommons.errors.exceptions.RequestParameterException;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 
 * @pathparam storeId Store identifier (currently store name)
 */

@Controller
@CacheControl(directive = CacheControlDirective.PUBLIC, maxAge = 1800)
public class StoresController extends BaseController
{
	private static final String DEFAULT_SEARCH_RADIUS_METRES = "100000.0";
	private static final double EARTH_PERIMETER = 40075000.0;
	private static final String DEFAULT_ACCURACY = "0.0";
	@Resource
	StoreFinderFacade storeFinderFacade;

	/**
	 * Lists all store locations that are near the location specified in a query or by latitude and longitude.
	 * 
	 * @queryparam query Location in natural language i.e. city or country.
	 * @queryparam latitude Coordinate that specifies the north-south position of a point on the Earth's surface.
	 * @queryparam longitude Coordinate that specifies the east-west position of a point on the Earth's surface.
	 * @queryparam currentPage The current result page requested.
	 * @queryparam pageSize The number of results returned per page.
	 * @queryparam sort Sorting method applied to the return results.
	 * @queryparam radius Radius in meters. Max value: 40075000.0 (Earth's perimeter).
	 * @queryparam accuracy Accuracy in meters.
	 * @queryparam fields Response configuration (list of fields, which should be returned in response)
	 * @return Lists of store near given location
	 * @throws RequestParameterException
	 */
	@RequestMapping(value = "/{baseSiteId}/stores", method = RequestMethod.GET)
	@ResponseBody
	public StoreFinderSearchPageWsDTO locationSearch(@RequestParam(required = false) final String query,
			@RequestParam(required = false) final Double latitude, @RequestParam(required = false) final Double longitude,
			@RequestParam(required = false, defaultValue = DEFAULT_CURRENT_PAGE) final int currentPage,
			@RequestParam(required = false, defaultValue = DEFAULT_PAGE_SIZE) final int pageSize,
			@RequestParam(required = false, defaultValue = "asc") final String sort,
			@RequestParam(required = false, defaultValue = DEFAULT_SEARCH_RADIUS_METRES) final double radius,
			@RequestParam(required = false, defaultValue = DEFAULT_ACCURACY) final double accuracy,
			@RequestParam(required = false, defaultValue = DEFAULT_FIELD_SET) final String fields) throws RequestParameterException
	{
		if (radius > EARTH_PERIMETER)
		{
			throw new RequestParameterException("Radius cannot be greater than Earth's perimeter",
					RequestParameterException.INVALID, "radius");
		}

		final double radiusToSearch = getInKilometres(radius, accuracy);
		final PageableData pageableData = createPageable(currentPage, pageSize, sort);
		StoreFinderSearchPageData<PointOfServiceData> result = null;
		if (StringUtils.isNotBlank(query))
		{
			result = storeFinderFacade.locationSearch(query, pageableData, radiusToSearch);
		}
		else if (latitude != null && longitude != null)
		{
			final GeoPoint geoPoint = new GeoPoint();
			geoPoint.setLatitude(latitude.doubleValue());
			geoPoint.setLongitude(longitude.doubleValue());
			result = storeFinderFacade.positionSearch(geoPoint, pageableData, radiusToSearch);
		}
		else
		{
			result = storeFinderFacade.getAllPointOfServices(pageableData);
		}

		return convertStoreFinderSearch(result, fields);
	}

	protected StoreFinderSearchPageWsDTO convertStoreFinderSearch(final StoreFinderSearchPageData<PointOfServiceData> result,
			final String fields)
	{
		return dataMapper.map(result, StoreFinderSearchPageWsDTO.class, fields);
	}

	/**
	 * Returns store location by its unique name.
	 * 
	 * @queryparam fields Response configuration (list of fields, which should be returned in response)
	 * @return Store details
	 */
	@RequestMapping(value = "/{baseSiteId}/stores/{storeId}", method = RequestMethod.GET)
	@ResponseBody
	public PointOfServiceWsDTO locationDetails(@PathVariable final String storeId,
			@RequestParam(required = false, defaultValue = DEFAULT_FIELD_SET) final String fields)
	{
		final PointOfServiceData pointOfServiceData = storeFinderFacade.getPointOfServiceForName(storeId);
		return dataMapper.map(pointOfServiceData, PointOfServiceWsDTO.class, fields);
	}

	protected double getInKilometres(final double radius, final double accuracy)
	{
		return (radius + accuracy) / 1000.0;
	}

	protected PageableData createPageable(final int page, final int pageSize, final String sort)
	{
		final PageableData pageableData = new PageableData();
		pageableData.setCurrentPage(page);
		pageableData.setPageSize(pageSize);
		pageableData.setSort(sort);
		return pageableData;
	}

}
