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
package com.federalmogul.storefront.interceptors.beforecontroller;

import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.util.Config;

import java.lang.annotation.Annotation;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.util.CookieGenerator;

import com.federalmogul.storefront.annotations.RequireHardLogIn;
import com.federalmogul.storefront.interceptors.BeforeControllerHandler;
import com.federalmogul.storefront.security.GUIDCookieStrategy;


/**
 */
public class RequireHardLoginBeforeControllerHandler implements BeforeControllerHandler
{
	private static final Logger LOG = Logger.getLogger(RequireHardLoginBeforeControllerHandler.class);

	public static final String SECURE_GUID_SESSION_KEY = "acceleratorSecureGUID";
	public static final String LOGOUT_URL = "/logout";

	private String loginUrl;
	private String loginAndCheckoutUrl;
	private RedirectStrategy redirectStrategy;
	private CookieGenerator cookieGenerator;
	private UserService userService;
	private List<String> restrictedPages;

	@Autowired
	private GUIDCookieStrategy guidCookieStrategy;

	protected String getLoginUrl()
	{
		return loginUrl;
	}

	@Required
	public void setLoginUrl(final String loginUrl)
	{
		this.loginUrl = loginUrl;
	}

	protected RedirectStrategy getRedirectStrategy()
	{
		return redirectStrategy;
	}

	@Required
	public void setRedirectStrategy(final RedirectStrategy redirectStrategy)
	{
		this.redirectStrategy = redirectStrategy;
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

	protected UserService getUserService()
	{
		return userService;
	}

	@Required
	public void setUserService(final UserService userService)
	{
		this.userService = userService;
	}

	public String getLoginAndCheckoutUrl()
	{
		return loginAndCheckoutUrl;
	}

	@Required
	public void setLoginAndCheckoutUrl(final String loginAndCheckoutUrl)
	{
		this.loginAndCheckoutUrl = loginAndCheckoutUrl;
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
	public boolean beforeController(final HttpServletRequest request, final HttpServletResponse response,
			final HandlerMethod handler) throws Exception
	{
		// We only care if the request is secure
		final String guid = (String) request.getSession().getAttribute(SECURE_GUID_SESSION_KEY);
		final boolean anonymousUser = getUserService().isAnonymousUser(getUserService().getCurrentUser());
		final HttpSession session = request.getSession();
		final Date curTime = new Date();
		int diffSeconds = 0;
		LOG.info("session.isNew()  ::" + session.isNew() + "  Diff Seconds " + diffSeconds + "default session timeout"
				+ Config.getParameter("default.session.timeout"));
		LOG.info("guid ::" + guid);
		LOG.info("anonymousUser  ::" + anonymousUser);
		if (null == guid && !anonymousUser)
		{
			LOG.error("Not Found secure cookie with Valid Session User. expected [" + guid + " ] anonymousUser :: [" + anonymousUser
					+ "]");
			if (!request.getServletPath().contains("sign-in"))
			{
				LOG.error("Not Found secure cookie with Valid Session User. So Re-Directing to Logout Page[ "
						+ request.getServletPath() + " ]");
				getCookieGenerator().removeCookie(response);
				request.getSession().invalidate();
				getRedirectStrategy().sendRedirect(request, response, LOGOUT_URL);
				return false;
			}
		}
		if (null == guid && anonymousUser)
		{
			//redirecting to login page controller if they are accessing checkout/myprofile etc...
			for (final String restrictedPage : getRestrictedPages())
			{
				// When logging out from a restricted page, return user to homepage.
				if (request.getServletPath().contains(restrictedPage))
				{
					LOG.error("Non Secure USER accessing the Secure pages. [ " + request.getServletPath() + " ]");
					getCookieGenerator().removeCookie(response);
					request.getSession().invalidate();
					getRedirectStrategy().sendRedirect(request, response, getRedirectUrl(request));
					return false;
				}
			}

		}
		if (!session.isNew())
		{
			final Date lastAccessTime = new Date(session.getLastAccessedTime());
			diffSeconds = (int) ((curTime.getTime() - lastAccessTime.getTime()) / (1000));
			LOG.info("session.isNew() 22 ::" + session.isNew() + "  Diff Seconds " + diffSeconds + "default session timeout"
					+ Config.getParameter("default.session.timeout"));
			if (diffSeconds > Integer.parseInt(Config.getParameter("default.session.timeout")))
			{
				LOG.error("Found secure cookie with invalid Session timeout. expected [" + diffSeconds
						+ " ] IF Actual Diff Seconds :: ["
						+ (diffSeconds > Integer.parseInt(Config.getParameter("default.session.timeout"))) + "]");
				getCookieGenerator().removeCookie(response);
				request.getSession().invalidate();
				final String encodedRedirectUrl = response.encodeRedirectURL(request.getContextPath() + "/");
				response.sendRedirect(encodedRedirectUrl);
				return false;
			}
		}
		if (request.isSecure())
		{
			// Check if the handler has our annotation
			final RequireHardLogIn annotation = findAnnotation(handler, RequireHardLogIn.class);
			if (annotation != null)
			{
				boolean redirect = true;

				if (!anonymousUser && guid != null && request.getCookies() != null)
				{
					final String guidCookieName = getCookieGenerator().getCookieName();
					if (guidCookieName != null)
					{
						for (final Cookie cookie : request.getCookies())
						{
							if (guidCookieName.equals(cookie.getName()))
							{
								if (guid.equals(cookie.getValue()))
								{
									redirect = false;
									break;
								}
								else
								{
									LOG.info("Found secure cookie with invalid value. expected [" + guid + "] actual ["
											+ cookie.getValue() + "]. removing.");
									getCookieGenerator().removeCookie(response);
								}
							}
						}
					}
				}

				if (redirect)
				{
					LOG.warn((guid == null ? "missing secure token in session" : "no matching guid cookie") + ", redirecting");
					getRedirectStrategy().sendRedirect(request, response, getRedirectUrl(request));
					return false;
				}
			}
		}

		return true;
	}

	protected String getRedirectUrl(final HttpServletRequest request)
	{
		if (request != null && request.getServletPath().contains("checkout"))
		{
			return getLoginAndCheckoutUrl();
		}
		else
		{
			return getLoginUrl();
		}
	}

	protected <T extends Annotation> T findAnnotation(final HandlerMethod handlerMethod, final Class<T> annotationType)
	{
		// Search for method level annotation
		final T annotation = handlerMethod.getMethodAnnotation(annotationType);
		if (annotation != null)
		{
			return annotation;
		}

		// Search for class level annotation
		return AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), annotationType);
	}
}
