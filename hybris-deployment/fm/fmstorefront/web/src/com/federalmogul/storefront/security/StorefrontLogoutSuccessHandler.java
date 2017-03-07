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
package com.federalmogul.storefront.security;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class StorefrontLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler
{
	private GUIDCookieStrategy guidCookieStrategy;
	private AEMCookieStrategy aemCookieStrategy;
	private List<String> restrictedPages;
	private RedirectStrategy fmContextlessRedirectStrategy;

	private static final Logger LOG = Logger.getLogger(StorefrontLogoutSuccessHandler.class);

	protected GUIDCookieStrategy getGuidCookieStrategy()
	{
		return guidCookieStrategy;
	}

	@Required
	public void setGuidCookieStrategy(final GUIDCookieStrategy guidCookieStrategy)
	{
		this.guidCookieStrategy = guidCookieStrategy;
	}

	public RedirectStrategy getFmContextlessRedirectStrategy() {
		return fmContextlessRedirectStrategy;
	}

	@Required
	public void setFmContextlessRedirectStrategy(RedirectStrategy fmContextlessRedirectStrategy) {
		this.fmContextlessRedirectStrategy = fmContextlessRedirectStrategy;
	}

	protected List<String> getRestrictedPages()
	{
		return restrictedPages;
	}

	public void setRestrictedPages(final List<String> restrictedPages)
	{
		this.restrictedPages = restrictedPages;
	}

	@Override
	public void onLogoutSuccess(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws IOException, ServletException
	{
		getGuidCookieStrategy().deleteCookie(request, response);
		getAemCookieStrategy().deleteCookie(request, response);

		// Delegate to default redirect behaviour
		this.handle(request, response, authentication);
	}

	@Override
	protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		String targetUrl = this.determineTargetUrl(request, response);
		if(response.isCommitted()) {
			LOG.info("Response has already been committed. Unable to redirect to " + targetUrl);
		} else {
			LOG.info("Attempting redirect to " + targetUrl);
			this.fmContextlessRedirectStrategy.sendRedirect(request, response, targetUrl);
		}
	}

	@Override
	protected String determineTargetUrl(final HttpServletRequest request, final HttpServletResponse response)
	{
		String targetUrl = super.determineTargetUrl(request, response);

		for (final String restrictedPage : getRestrictedPages())
		{
			// When logging out from a restricted page, return user to homepage.
			if (targetUrl.contains(restrictedPage))
			{
				targetUrl = super.getDefaultTargetUrl();
			}
		}

		return targetUrl;
	}

	public AEMCookieStrategy getAemCookieStrategy() {
		return aemCookieStrategy;
	}

	@Required
	public void setAemCookieStrategy(AEMCookieStrategy aemCookieStrategy) {
		this.aemCookieStrategy = aemCookieStrategy;
	}
}
