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
package com.federalmogul.storefront.filters.btg;

import de.hybris.platform.cms2.misc.CMSFilter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.filter.OncePerRequestFilter;

import com.federalmogul.storefront.filters.btg.support.BTGSegmentStrategy;


/**
 * Filter that evaluates the BTG context for the current request
 */
public class BTGSegmentFilter extends OncePerRequestFilter implements CMSFilter
{
	/*
	 * @SuppressWarnings("unused") private static final Logger LOG = Logger.getLogger(BTGSegmentFilter.class);
	 */

	private BTGSegmentStrategy btgSegmentStrategy;

	@Override
	protected void doFilterInternal(final HttpServletRequest httpRequest, final HttpServletResponse httpResponse,
			final FilterChain filterChain) throws ServletException, IOException
	{
		filterChain.doFilter(httpRequest, httpResponse);
		getBtgSegmentStrategy().evaluateSegment(httpRequest);
	}

	protected BTGSegmentStrategy getBtgSegmentStrategy()
	{
		return btgSegmentStrategy;
	}

	@Required
	public void setBtgSegmentStrategy(final BTGSegmentStrategy btgSegmentStrategy)
	{
		this.btgSegmentStrategy = btgSegmentStrategy;
	}
}
