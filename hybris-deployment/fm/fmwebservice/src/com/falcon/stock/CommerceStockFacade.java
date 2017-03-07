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

package com.falcon.stock;


import de.hybris.platform.basecommerce.enums.StockLevelStatus;
import de.hybris.platform.commercefacades.product.data.StockData;
import de.hybris.platform.servicelayer.exceptions.AmbiguousIdentifierException;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.storelocator.model.PointOfServiceModel;

import java.util.Map;


public interface CommerceStockFacade
{
	/**
	 * Indicates if stock system is enabled for given base store
	 * 
	 * @param baseSiteId
	 *           to be checked
	 * @return true if stock system is enabled
	 * @throws UnknownIdentifierException
	 *            the unknown identifier exception when no base site with given id was found
	 */
	boolean isStockSystemEnabled(String baseSiteId) throws UnknownIdentifierException;

	/**
	 * Returns stock data for combination of given product and base site
	 * 
	 * @param productCode
	 * @param baseSiteId
	 * @return {@link StockData} information
	 * @throws UnknownIdentifierException
	 *            the unknown identifier exception when no base site or product with given id was found
	 * @throws IllegalArgumentException
	 *            the illegal argument exception when any one parameter is null
	 * @throws AmbiguousIdentifierException
	 *            the ambiguous identifier exception when there is more than one product with given code
	 */
	StockData getStockDataForProductAndBaseSite(String productCode, String baseSiteId) throws UnknownIdentifierException,
			IllegalArgumentException, AmbiguousIdentifierException;

	/**
	 * Returns stock data for given product and point of service (that also indicates warehouse)
	 * 
	 * @param productCode
	 * @param storeName
	 * @return {@link StockData} information
	 * @throws UnknownIdentifierException
	 *            the unknown identifier exception when no store or product with given id was found
	 * @throws IllegalArgumentException
	 *            the illegal argument exception when any one parameter is null
	 * @throws AmbiguousIdentifierException
	 *            the ambiguous identifier exception when there is more than one product with given code
	 */
	StockData getStockDataForProductAndPointOfService(String productCode, String storeName) throws UnknownIdentifierException,
			IllegalArgumentException, AmbiguousIdentifierException;

	/**
	 * Returns map of POS and StockData for given product and base store
	 * 
	 * @param productCode
	 * @param baseSiteId
	 * @return Map of {@link PointOfServiceModel} and {@link StockData} information
	 * @throws UnknownIdentifierException
	 *            the unknown identifier exception when no base site or product with given id was found
	 * @throws IllegalArgumentException
	 *            the illegal argument exception when any one parameter is null
	 * @throws AmbiguousIdentifierException
	 *            the ambiguous identifier exception when there is more than one product with given code
	 */
	Map<String, StockLevelStatus> getPosAndStockDataForProduct(String productCode, String baseSiteId)
			throws UnknownIdentifierException, IllegalArgumentException, AmbiguousIdentifierException;
}
