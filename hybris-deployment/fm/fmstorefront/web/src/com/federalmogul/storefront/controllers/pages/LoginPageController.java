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
package com.federalmogul.storefront.controllers.pages;

import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.user.UserService;
import com.federalmogul.storefront.controllers.ControllerConstants;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * Login Controller. Handles login and register for the account flow.
 */
@Controller
@Scope("tenant")
@RequestMapping(value = "/**/sign-in")
public class LoginPageController extends AbstractLoginPageController
{
	private static final Logger LOG = Logger.getLogger(LoginPageController.class);
	private static final String UPDATE_PASSWORD_PAGE_PATH = "/my-fmaccount/update-password"; // For FAL-219 - Sprint 9

	@Resource(name = "httpSessionRequestCache")
	private HttpSessionRequestCache httpSessionRequestCache;

	@Resource
	private UserService userService;

	public void setHttpSessionRequestCache(final HttpSessionRequestCache accHttpSessionRequestCache)
	{
		this.httpSessionRequestCache = accHttpSessionRequestCache;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String doLogin(@RequestHeader(value = "referer", required = false) final String referer,
			@RequestParam(value = "error", defaultValue = "false") final boolean loginError, final Model model,
			final HttpServletRequest request, final HttpServletResponse response, final HttpSession session)
			throws CMSItemNotFoundException, IOException
	{
		LOG.info("doLogin(...): URL :: " + request.getRequestURL());
		LOG.info("doLogin(...): URI :: " + request.getRequestURI());

		final UserModel user = userService.getCurrentUser();
		final boolean isUserAnonymous = user == null || userService.isAnonymousUser(user);

		if (!isUserAnonymous)
		{
			/* For FAL-219 - Sprint 9 - BEGIN: */
			LOG.info("doLogin(...): isUserAnonymous: " + isUserAnonymous);
			LOG.info("doLogin(...): user.getPwdMeetsLatestStandards(): " + user.getPwdMeetsLatestStandards());
			if (!isUserAnonymous && (user.getPwdMeetsLatestStandards() == null || !user.getPwdMeetsLatestStandards()))
			{ // Password does NOT meet latest standards)
				String redirectPath = UPDATE_PASSWORD_PAGE_PATH;
				LOG.info("doLogin(...): user.getPwdMeetsLatestStandards() is null or false.");
				LOG.info("doLogin(...): Redirecting to: redirectPath: " + redirectPath);
				return REDIRECT_PREFIX + UPDATE_PASSWORD_PAGE_PATH;
			}
			/* For FAL-219 - Sprint 9 - END */

			return REDIRECT_PREFIX + ROOT;
		}

		return getDefaultLoginPage(loginError, session, request, response, model);	
	}

	@RequestMapping(value = "/loyality", method = RequestMethod.GET)
	public String doLoyalityLogin(@RequestHeader(value = "referer", required = false) final String referer,
			@RequestParam(value = "error", defaultValue = "false") final boolean loginError, final Model model,
			final HttpServletRequest request, final HttpServletResponse response, final HttpSession session)
			throws CMSItemNotFoundException, IOException
	{

		final UserModel user = userService.getCurrentUser();
			final boolean isUserAnonymous = user == null || userService.isAnonymousUser(user);

		if (!isUserAnonymous)
		{
			getSessionService().setAttribute("loyality", "loyality");
			return REDIRECT_PREFIX + ROOT;
		}

		return getDefaultLoginPage(loginError, session, model);
	}

	@Override
	protected String getLoginView()
	{
		return ControllerConstants.Views.Pages.Account.AccountLoginPage;
	}

	@Override
	protected String getSuccessRedirect(final HttpServletRequest request, final HttpServletResponse response)
	{
		if (httpSessionRequestCache.getRequest(request, response) != null)
		{
			return httpSessionRequestCache.getRequest(request, response).getRedirectUrl();
		}

		return "/my-account";
	}

	@Override
	protected AbstractPageModel getLoginCmsPage() throws CMSItemNotFoundException
	{
		return getContentPageForLabelOrId("login");
	}

	protected void storeReferer(final String referer, final HttpServletRequest request, final HttpServletResponse response)
	{
		if (StringUtils.isNotBlank(referer))
		{
			httpSessionRequestCache.saveRequest(request, response);
		}
	}
}
