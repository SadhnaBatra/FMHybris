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

import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.UserService;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


/**
 * Default implementation of {@link AuthenticationSuccessHandler}.
 */
public class GUIDAuthenticationSuccessHandler implements AuthenticationSuccessHandler
{
	private GUIDCookieStrategy guidCookieStrategy;
	private AEMCookieStrategy aemCookieStrategy;
	private AuthenticationSuccessHandler authenticationSuccessHandler;
	@Resource
	private ModelService modelService;
	private static final Logger LOG = Logger.getLogger(GUIDAuthenticationSuccessHandler.class);

	@Resource
	private UserService userService;

	@Override
	public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response,
			final Authentication authentication) throws IOException, ServletException
	{
		LOG.info("-------------AAAAAAAA on authenticationSuccess START:: ");

		getGuidCookieStrategy().setCookie(request, response);
		LOG.info("-----------ccccccccccc");

		String username = request.getParameter("j_username");
		String password = request.getParameter("j_password");
		LOG.info("Trying to log in to the auth token service with user '" + username + "'");
		if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
			aemCookieStrategy.setCookie(username, password, request, response);
		} else {
			LOG.warn("A blank username or password was sent in the request, AEM integration may be affected by this.");
		}

		getAuthenticationSuccessHandler().onAuthenticationSuccess(request, response, authentication);

		final UserModel user = userService.getCurrentUser();
		final boolean isUserAnonymous = user == null || userService.isAnonymousUser(user);
		LOG.info("----------eeeeeeeeeeee-----isUserAnonymous::" + isUserAnonymous);



		if (!isUserAnonymous)
		{
			if (user.getLastLogin() != null)
			{
				LOG.info("---LAST LOGIN------Time:::::" + user.getLastLogin());

				final Date date = Calendar.getInstance().getTime();

				LOG.info("---------Date object::" + date);

				user.setLastLogin(date);

				modelService.save(user);

				LOG.info("-------After changing last login-------" + user.getLastLogin());
			}
			else
			{
				LOG.info("-----------Last login is null---");
				final Date date = Calendar.getInstance().getTime();

				LOG.info("---------Date object::" + date);

				user.setLastLogin(date);

				modelService.save(user);

				LOG.info("-------After setting last login-------" + user.getLastLogin());

			}
		}


		LOG.info("#####222222222 on authenticationSuccess END:: ");
	}

	protected GUIDCookieStrategy getGuidCookieStrategy()
	{
		LOG.info("---------------BBBBBBBB");
		return guidCookieStrategy;
	}

	/**
	 * @param guidCookieStrategy
	 *           the guidCookieStrategy to set
	 */
	@Required
	public void setGuidCookieStrategy(final GUIDCookieStrategy guidCookieStrategy)
	{
		this.guidCookieStrategy = guidCookieStrategy;
	}

	protected AuthenticationSuccessHandler getAuthenticationSuccessHandler()
	{
		LOG.info("------in getAuthenticationSuccesshandleer method  ddddddddddddddddddd-------");
		return authenticationSuccessHandler;
	}

	/**
	 * @param authenticationSuccessHandler
	 *           the authenticationSuccessHandler to set
	 */
	@Required
	public void setAuthenticationSuccessHandler(final AuthenticationSuccessHandler authenticationSuccessHandler)
	{
		this.authenticationSuccessHandler = authenticationSuccessHandler;
	}

	public AEMCookieStrategy getAemCookieStrategy() {
		return aemCookieStrategy;
	}

	@Required
	public void setAemCookieStrategy(AEMCookieStrategy aemCookieStrategy) {
		this.aemCookieStrategy = aemCookieStrategy;
	}
}
