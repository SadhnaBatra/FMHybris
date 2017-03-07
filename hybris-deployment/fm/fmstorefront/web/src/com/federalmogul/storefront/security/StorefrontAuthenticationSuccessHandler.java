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

import de.hybris.platform.acceleratorservices.uiexperience.UiExperienceService;
import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.commercefacades.order.CartFacade;
import de.hybris.platform.commerceservices.enums.UiExperienceLevel;
import de.hybris.platform.commerceservices.order.CommerceCartRestorationException;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.session.SessionService;
import com.federalmogul.storefront.constants.WebConstants;
import de.hybris.platform.servicelayer.user.UserService;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;


/**
 * Success handler initializing user settings and ensuring the cart is handled correctly
 */
public class StorefrontAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler
{
	private static final Logger LOG = Logger.getLogger(StorefrontAuthenticationSuccessHandler.class);
	private CustomerFacade customerFacade;
	private UiExperienceService uiExperienceService;
	private CartFacade cartFacade;
	private SessionService sessionService;
	private BruteForceAttackCounter bruteForceAttackCounter;

	private Map<UiExperienceLevel, Boolean> forceDefaultTargetForUiExperienceLevel;
	@Resource
	private UserService userService;

	public UiExperienceService getUiExperienceService()
	{
		return uiExperienceService;
	}

	@Required
	public void setUiExperienceService(final UiExperienceService uiExperienceService)
	{
		this.uiExperienceService = uiExperienceService;
	}

	@Override
	public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response,
			final Authentication authentication) throws IOException, ServletException
	{

		getSessionService().setAttribute("loginError", false);
		getCustomerFacade().loginSuccess();

		if (!getCartFacade().hasSessionCart() || getCartFacade().getSessionCart().getEntries().isEmpty())
		{
			try
			{
				getSessionService().setAttribute(WebConstants.CART_RESTORATION, getCartFacade().restoreSavedCart(null));
			}
			catch (final CommerceCartRestorationException e)
			{
				getSessionService().setAttribute(WebConstants.CART_RESTORATION, "basket.restoration.errorMsg");
			}
		}

		getBruteForceAttackCounter().resetUserCounter(getCustomerFacade().getCurrentCustomer().getUid());
		final String rememberme = request.getParameter("_spring_security_remember_me");

		LOG.info("---------rememberme value in storefront authenticationsuccess::" + rememberme);
		if ((rememberme != null)
				&& (((rememberme.equalsIgnoreCase("true")) || (rememberme.equalsIgnoreCase("on"))
						|| (rememberme.equalsIgnoreCase("yes")) || (rememberme.equals("1")))))

		{
			LOG.info("in custom cookie");
			final String name = "RememberMeCustom";
			final UserModel loggedUser = userService.getUserForUID(request.getParameter("j_username"));
			LOG.info("loggedUser : " + loggedUser.getUid().toString());
			final String value = loggedUser.getUid().toString();
			//final Cookie customCookie = new Cookie("fmstorefrontRememberMeCustom", loggedUser.getUid().toString() + ":" + pwd
			//+ ":checked");
			final Cookie customCookie = new Cookie(name, value);

                        customCookie.setMaxAge(2592000);		
			customCookie.setPath("/");
			customCookie.setHttpOnly(false);
			response.addCookie(customCookie);

		}
		else
		{
			LOG.info("in else block of cookie :");
			final Cookie[] cookies = request.getCookies();

			if (cookies != null)
			{
				for (int i = 0; i < cookies.length; i++)
				{
					final Cookie cookie = cookies[i];
					if (cookie.getName().equals("RememberMeCustom"))
					{
						LOG.info("in RememberMeCustom block of cookie :");
						cookie.setValue("");
						cookie.setMaxAge(0);
						cookie.setPath("/");
						response.addCookie(cookie);


					}
				}
			}
		}
		super.onAuthenticationSuccess(request, response, authentication);
	}

	protected CartFacade getCartFacade()
	{
		return cartFacade;
	}

	@Required
	public void setCartFacade(final CartFacade cartFacade)
	{
		this.cartFacade = cartFacade;
	}

	protected SessionService getSessionService()
	{
		return sessionService;
	}

	@Required
	public void setSessionService(final SessionService sessionService)
	{
		this.sessionService = sessionService;
	}

	protected CustomerFacade getCustomerFacade()
	{
		return customerFacade;
	}

	@Required
	public void setCustomerFacade(final CustomerFacade customerFacade)
	{
		this.customerFacade = customerFacade;
	}

	@Override
	protected String determineTargetUrl(final HttpServletRequest request, final HttpServletResponse response) {
		return getDefaultTargetUrl();
	}

	/*
	 * @see org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler#
	 * isAlwaysUseDefaultTargetUrl()
	 */
	@Override
	protected boolean isAlwaysUseDefaultTargetUrl()
	{
		final UiExperienceLevel uiExperienceLevel = getUiExperienceService().getUiExperienceLevel();
		if (getForceDefaultTargetForUiExperienceLevel().containsKey(uiExperienceLevel))
		{
			return Boolean.TRUE.equals(getForceDefaultTargetForUiExperienceLevel().get(uiExperienceLevel));
		}
		else
		{
			return false;
		}
	}

	protected Map<UiExperienceLevel, Boolean> getForceDefaultTargetForUiExperienceLevel()
	{
		return forceDefaultTargetForUiExperienceLevel;
	}

	@Required
	public void setForceDefaultTargetForUiExperienceLevel(
			final Map<UiExperienceLevel, Boolean> forceDefaultTargetForUiExperienceLevel)
	{
		this.forceDefaultTargetForUiExperienceLevel = forceDefaultTargetForUiExperienceLevel;
	}


	protected BruteForceAttackCounter getBruteForceAttackCounter()
	{
		return bruteForceAttackCounter;
	}

	@Required
	public void setBruteForceAttackCounter(final BruteForceAttackCounter bruteForceAttackCounter)
	{
		this.bruteForceAttackCounter = bruteForceAttackCounter;
	}
}
