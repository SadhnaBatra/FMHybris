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

package com.falcon.strategies;

import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.store.BaseStoreModel;


/**
 * Strategy for retrieving base store from the base site.
 */
public interface BaseStoreForSiteSelectorStrategy
{
	BaseStoreModel getBaseStore(BaseSiteModel baseSiteModel);
}
