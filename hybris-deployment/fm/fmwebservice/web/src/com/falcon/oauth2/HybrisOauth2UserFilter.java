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
package com.falcon.oauth2;

import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.servicelayer.user.UserService;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.*;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import org.apache.log4j.Logger;

/**
 * Filter sets current user by userService depending on current principal. <br>
 * This should happen only when there is a customer context. Anonymous credentials are also applicable, because special
 * 'anonymous' user is available for that purpose. Customer context is not available during client credential flow.
 * 
 * @author hansa
 * 
 */
public class HybrisOauth2UserFilter implements Filter
{
	private static final String ROLE_ANONYMOUS = "ROLE_ANONYMOUS";
	private static final String ROLE_CUSTOMERGROUP = "ROLE_CUSTOMERGROUP";
	private static final String ROLE_CUSTOMERMANAGERGROUP = "ROLE_CUSTOMERMANAGERGROUP";

	@Resource(name = "userService")
	private UserService userService;

	private static final Logger LOG = Logger.getLogger(HybrisOauth2UserFilter.class);


	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException
	{
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (containsRole(auth, ROLE_ANONYMOUS) || containsRole(auth, ROLE_CUSTOMERGROUP)
				|| containsRole(auth, ROLE_CUSTOMERMANAGERGROUP))
		{
			refreshSession(request, auth);
			final UserModel userModel = userService.getUserForUID((String) auth.getPrincipal());
			userService.setCurrentUser(userModel);
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy()
	{
		// YTODO Auto-generated method stub
	}

	@Override
	public void init(final FilterConfig arg0) throws ServletException
	{
		// YTODO Auto-generated method stub
	}

	protected void refreshSession(ServletRequest request, Authentication auth) {
		// This check is to validate if there was a context switch in users (i.e., if a session logged in as one user, then logged out, then logged back in as a different user). In this case,
		//	the web service API requires a logout separate from what occurs in fmstorefront. Instead of changing all possible logout URL's to also logout of the web service (which could span
		// 	across both AEM and Hybris), we make a check here to invalidate the session if the user switched. Not closing the JaloSession causes the principal to not be found because it is
		//	done in the context of the user previously in session.
		if (request != null && auth != null && request instanceof HttpServletRequest) {
			String currentUserUid = userService.getCurrentUser().getUid();
			String principal = auth.getPrincipal().toString();
			if (!currentUserUid.equalsIgnoreCase(principal)) {
				LOG.info("Refreshing session flushing " + currentUserUid + " in favor of " + principal);
				JaloSession.getCurrentSession().close();
				((HttpServletRequest) request).getSession().invalidate();
			}
		}
	}

	private static boolean containsRole(final Authentication auth, final String role)
	{
		for (final GrantedAuthority ga : auth.getAuthorities())
		{
			if (ga.getAuthority().equals(role))
			{
				return true;
			}
		}
		return false;
	}
}
