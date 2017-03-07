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
import de.hybris.platform.commercefacades.order.CartFacade;
import de.hybris.platform.commerceservices.order.CommerceCartRestorationException;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.user.UserService;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.federalmogul.storefront.constants.WebConstants;
import com.federalmogul.storefront.security.AcceleratorRememberMeServices;
import com.federalmogul.storefront.security.AutoLoginStrategy;
import com.federalmogul.storefront.security.BruteForceAttackCounter;


/**
 * Login Controller. Handles login and register for the account flow.
 */
@Controller
@Scope("tenant")
@RequestMapping(value = "/fmlogin/dologin")
public class AutoLoginPageController extends AbstractPageController
{
	/*
	 * @Resource(name = "httpSessionRequestCache") private HttpSessionRequestCache httpSessionRequestCache;
	 */

	@Resource
	private AcceleratorRememberMeServices rememberMeServices;

	@Resource
	private UserService userService;

	@Resource
	private AutoLoginStrategy autoLoginStrategy;

	@Resource
	private BruteForceAttackCounter bruteForceAttackCounter;
	@Autowired
	private CartFacade cartFacade;

	public static final String ROOT = "/content/loc-na/loc-us/fmmp-corporate/en_US.html";
	private static final String UPDATE_PASSWORD_PAGE_PATH = "/fmstorefront/federalmogul/en/USD/my-fmaccount/update-password"; // For FAL-219 - Sprint 9

	private static final Logger LOG = Logger.getLogger(AutoLoginPageController.class);

	@RequestMapping(method ={RequestMethod.POST, RequestMethod.GET})
	public void fmLogin(final String referer, final Model model, final HttpServletRequest request,
			final HttpServletResponse response, final HttpSession session) throws CMSItemNotFoundException, IOException
	{
		final String userName = request.getParameter("j_username");
		final String pwd = request.getParameter("j_password");

		//LOG.info("userName : " + userName + " pwd: " + pwd);

		/* For Remember me - Begin */
		final String rememberme = request.getParameter("_spring_security_remember_me");
		LOG.info("remember me value : " + rememberme);

		if (rememberme != null)
		{
			rememberMeServices.setAlwaysRemember(true);
		}
		/* For Remember me - End */

		final UserModel user = userService.getCurrentUser();
		final boolean isUserAnonymous = (user == null || userService.isAnonymousUser(user));
		LOG.info("fmLogin(...): isUserAnonymous: " + isUserAnonymous);
		
		if (!isUserAnonymous)
		{
			// Here we set the response header instead of simply returning the redirect as a string because our ROOT is now outside of Hybris...
			response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
			response.setHeader("Location", ROOT);
		}

		try
		{
			autoLoginStrategy.login(userName.toLowerCase(), pwd, request, response);

			/* For FAL-219 - Sprint 9 - BEGIN: */
			// Fetch the current user again, after auto login.
			UserModel userAfterAutoLogin = userService.getCurrentUser();
			// Re-check if user is still anonymous or not. He shouldn't be anonymous now.
			boolean isUserAnonymousNow = (userAfterAutoLogin == null || userService.isAnonymousUser(userAfterAutoLogin));
			LOG.info("fmLogin(...): userAfterAutoLogin.getPwdMeetsLatestStandards(): " + userAfterAutoLogin.getPwdMeetsLatestStandards());
			LOG.info("fmLogin(...): isUserAnonymousNow: " + isUserAnonymousNow);
			// Check if the user's current password meets the latest password standards.
			if (!isUserAnonymousNow && 
					(userAfterAutoLogin.getPwdMeetsLatestStandards() == null || !userAfterAutoLogin.getPwdMeetsLatestStandards())) { // Does NOT meet latest standards
				LOG.info("fmLogin(...): userAfterAutoLogin.getPwdMeetsLatestStandards() is null or false.");
				LOG.info("fmLogin(...): redirectPath: " + UPDATE_PASSWORD_PAGE_PATH);

				// Here we set the response header instead of simply returning the redirect as a string because our ROOT is now outside of Hybris...
				response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
				response.setHeader("Location", UPDATE_PASSWORD_PAGE_PATH);
				return;
			}
			/* For FAL-219 - Sprint 9 - END */

			request.setAttribute("loginError", false);
			getBruteForceAttackCounter().resetUserCounter(getCustomerFacade().getCurrentCustomer().getUid());
			if ((rememberme != null)
					&& (((rememberme.equalsIgnoreCase("true")) || (rememberme.equalsIgnoreCase("on"))
							|| (rememberme.equalsIgnoreCase("yes")) || (rememberme.equals("1")))))

			{
				LOG.info("fmLogin(...): in custom cookie");
				final String name = "RememberMeCustom";
				final UserModel loggedUser = userService.getUserForUID(request.getParameter("j_username"));
				LOG.info("fmLogin(...): loggedUser : " + loggedUser.getUid().toString());
				final String value = loggedUser.getUid().toString();
				//final Cookie customCookie = new Cookie("fmstorefrontRememberMeCustom", loggedUser.getUid().toString() + ":" + pwd
				//+ ":checked");
				final Cookie customCookie = new Cookie(name, value);

				customCookie.setMaxAge(2592000);
				customCookie.setPath("/");
				response.addCookie(customCookie);
			}
			else
			{
				LOG.info("fmLogin(...): in else block of cookie :");
				final Cookie[] cookies = request.getCookies();

				if (cookies != null)
				{
					for (int i = 0; i < cookies.length; i++)
					{
						final Cookie cookie = cookies[i];
						if (cookie.getName().equals("RememberMeCustom"))
						{
							LOG.info("fmLogin(...): in RememberMeCustom block of cookie :");
							cookie.setValue("");
							cookie.setMaxAge(0);
							cookie.setPath("/");
							response.addCookie(cookie);
						}
					}
				}
			}
			getSessionService().setAttribute("loginError", false);
			if (!cartFacade.hasSessionCart() || cartFacade.getSessionCart().getEntries().isEmpty())
			{
				try
				{
					getSessionService().setAttribute(WebConstants.CART_RESTORATION, cartFacade.restoreSavedCart(null));
				}
				catch (final CommerceCartRestorationException e)
				{
					getSessionService().setAttribute(WebConstants.CART_RESTORATION, "basket.restoration.errorMsg");
				}
			}

		}
		catch (final Exception e)
		{
			LOG.error("fmLogin(...): Error in Autologin " + e.getMessage());
			bruteForceAttackCounter.registerLoginFailure(request.getParameter("j_username"));
			request.setAttribute("loginErrorMessage", ".");
			request.setAttribute("loginError", true);
			request.setAttribute("loginErrorAuto", true);

			getSessionService().setAttribute("loginError", true);
			getSessionService().setAttribute("loginErrorAuto", true);
			getSessionService().setAttribute("loginErrorMessage", "Invalid Email Address or Password");

		}

		// Here we set the response header instead of simply returning the redirect as a string because our ROOT is now outside of Hybris...
		response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
		response.setHeader("Location", ROOT);
	}
	/**
	 * sign-in request from iframe page should invoke this method and user authentication happens. After successful authentication page will be 
	 * redirected to user home page (based on the user type B2T, b2B etc)
	 * @param referer
	 * @param model
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws CMSItemNotFoundException
	 * @throws IOException
	 */
	
	@RequestMapping(value = "/iframe", method ={RequestMethod.POST, RequestMethod.GET})
	public void fmiFrameLogin(final String referer, final Model model, final HttpServletRequest request,
			final HttpServletResponse response, final HttpSession session) throws CMSItemNotFoundException, IOException {
		fmLogin(referer, model, request, response, session);
	}


	protected BruteForceAttackCounter getBruteForceAttackCounter()
	{
		return bruteForceAttackCounter;
	}

	public void setBruteForceAttackCounter(final BruteForceAttackCounter bruteForceAttackCounter)
	{
		this.bruteForceAttackCounter = bruteForceAttackCounter;
	}

}
