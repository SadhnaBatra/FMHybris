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
package com.federalmogul.storefront.filters;

import de.hybris.platform.cms2.misc.CMSFilter;
import de.hybris.platform.commercefacades.storesession.StoreSessionFacade;
import de.hybris.platform.util.Config;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.CookieGenerator;

import com.federalmogul.storefront.history.BrowseHistory;
import com.federalmogul.storefront.history.BrowseHistoryEntry;


/**
 * Filter that initializes the session for the fmstorefront
 */
public class StorefrontFilter extends GenericFilterBean
{

	private static final Logger LOG = Logger.getLogger(StorefrontFilter.class);

	private StoreSessionFacade storeSessionFacade;
	private BrowseHistory browseHistory;
	private CookieGenerator cookieGenerator;

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException
	{
		LOG.info("###### StorefrontFilter ################### 111");
		final HttpServletRequest httpRequest = (HttpServletRequest) request;
		final HttpSession session = httpRequest.getSession();
		final String queryString = httpRequest.getQueryString();

		if (isSessionNotInitialized(session, queryString))
		{
			LOG.info("###### StorefrontFilter ################### 222");
			initDefaults(httpRequest);

			markSessionInitialized(session);
		}

		final Date curTime = new Date();
		int diffSeconds = 0;

		if (!session.isNew())
		{
			final Date lastAccessTime = new Date(session.getLastAccessedTime());
			diffSeconds = (int) ((curTime.getTime() - lastAccessTime.getTime()) / (1000));
		}
		LOG.info("session.isNew() ::" + session.isNew() + "  Diff Seconds " + diffSeconds);
		if (session.isNew() || diffSeconds > Integer.parseInt(Config.getParameter("default.session.timeout")))
		{
			LOG.info("###### StorefrontFilter ################### 9090 "
					+ Integer.parseInt(Config.getParameter("default.session.timeout")));
			getCookieGenerator().removeCookie((HttpServletResponse) response);
			session.setAttribute("sessionExpire", "yes");
			/*
			 * final Cookie[] listOfCookie = ((HttpServletRequest) request).getCookies(); if (null != listOfCookie) {
			 * //LOG.info("Cookie lenth is ::"+listOfCookie.length); for (int i = 0; i < listOfCookie.length; i++) { final
			 * Cookie cookie = listOfCookie[i]; if ("userPerspectiveFromJS".equals(cookie.getName())) {
			 * 
			 * LOG.info("###### StorefrontFilter ################### 777"); session.setAttribute("sessionExpire", "yes"); }
			 * } }
			 */
		}

		// For secure requests ensure that the JSESSIONID cookie is visible to insecure requests
		if (isRequestSecure(httpRequest))
		{
			LOG.info("###### StorefrontFilter ################### 333");
			fixSecureHttpJSessionIdCookie(httpRequest, (HttpServletResponse) response);
		}

		if (isGetMethod(httpRequest))
		{
			LOG.info("###### StorefrontFilter ################### 444");
			getBrowseHistory().addBrowseHistoryEntry(new BrowseHistoryEntry(httpRequest.getRequestURI(), null));
		}

		chain.doFilter(request, response);
	}



	protected boolean isGetMethod(final HttpServletRequest httpRequest)
	{
		return "GET".equalsIgnoreCase(httpRequest.getMethod());
	}

	protected boolean isRequestSecure(final HttpServletRequest httpRequest)
	{
		return httpRequest.isSecure();
	}

	protected boolean isSessionNotInitialized(final HttpSession session, final String queryString)
	{
		return session.isNew() || StringUtils.contains(queryString, CMSFilter.CLEAR_CMSSITE_PARAM)
				|| !isSessionInitialized(session);
	}

	@Required
	public void setStoreSessionFacade(final StoreSessionFacade storeSessionFacade)
	{
		this.storeSessionFacade = storeSessionFacade;
	}

	@Required
	public void setBrowseHistory(final BrowseHistory browseHistory)
	{
		this.browseHistory = browseHistory;
	}

	protected void initDefaults(final HttpServletRequest request)
	{
		final StoreSessionFacade storeSessionFcde = getStoreSessionFacade();

		storeSessionFcde.initializeSession(Collections.list(request.getLocales()));
	}

	protected StoreSessionFacade getStoreSessionFacade()
	{
		return storeSessionFacade;
	}

	protected BrowseHistory getBrowseHistory()
	{
		return browseHistory;
	}

	protected boolean isSessionInitialized(final HttpSession session)
	{
		return session.getAttribute(this.getClass().getName()) != null;
	}

	protected void markSessionInitialized(final HttpSession session)
	{
		session.setAttribute(this.getClass().getName(), "initialized");
	}

	protected void fixSecureHttpJSessionIdCookie(final HttpServletRequest httpServletRequest,
			final HttpServletResponse httpServletResponse)
	{
		final HttpSession session = httpServletRequest.getSession(false);
		if (session != null)
		{
			LOG.info("###### StorefrontFilter ################### 555");
			getCookieGenerator().addCookie(httpServletResponse, session.getId());
		}

	}

	protected CookieGenerator getCookieGenerator()
	{
		return cookieGenerator;
	}

	@Required
	public void setCookieGenerator(final CookieGenerator cookieGenerator)
	{
		this.cookieGenerator = cookieGenerator;
	}
}
