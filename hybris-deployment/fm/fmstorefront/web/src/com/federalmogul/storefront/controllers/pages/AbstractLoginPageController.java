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
import de.hybris.platform.cms2.model.pages.ContentPageModel;
import de.hybris.platform.commercefacades.user.data.TitleData;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.user.UserService;

import java.util.Collection;
import java.util.Collections;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.federalmogul.storefront.breadcrumb.Breadcrumb;
import com.federalmogul.storefront.controllers.util.GlobalMessages;
import com.federalmogul.storefront.forms.LoginForm;


/**
 * Abstract base class for login page controllers
 */
public abstract class AbstractLoginPageController extends AbstractPageController
{
	protected static final String SPRING_SECURITY_LAST_USERNAME = "SPRING_SECURITY_LAST_USERNAME";
	private static final Logger LOG = Logger.getLogger(AbstractLoginPageController.class);

	@Resource
	private UserService userService;

	@ModelAttribute("titles")
	public Collection<TitleData> getTitles()
	{
		return getUserFacade().getTitles();
	}

	protected abstract String getLoginView();

	protected abstract AbstractPageModel getLoginCmsPage() throws CMSItemNotFoundException;

	protected abstract String getSuccessRedirect(final HttpServletRequest request, final HttpServletResponse response);


	protected String getDefaultLoginPage(final boolean loginError, final HttpSession session, final Model model)
			throws CMSItemNotFoundException
	{
		final LoginForm loginForm = new LoginForm();
		model.addAttribute(loginForm);
		final String username = (String) session.getAttribute(SPRING_SECURITY_LAST_USERNAME);
		if (username != null)
		{
			session.removeAttribute(SPRING_SECURITY_LAST_USERNAME);
		}

		LOG.info("Inside 'getDefaultLoginPage(loginError, request, response)'");
		loginForm.setJ_username(username);
		storeCmsPageInModel(model, getLoginCmsPage());
		setUpMetaDataForContentPage(model, (ContentPageModel) getLoginCmsPage());
		model.addAttribute("metaRobots", "index,no-follow");

		final Breadcrumb loginBreadcrumbEntry = new Breadcrumb("#", getMessageSource().getMessage("header.link.login", null,
																getI18nService().getCurrentLocale()), null);
		model.addAttribute("breadcrumbs", Collections.singletonList(loginBreadcrumbEntry));

		if (username != null)
		{
			if (loginError)
			{
				//request.setAttribute("loginError", true);
				session.setAttribute("loginError", true);
				LOG.info("inside 'if (loginError)'");
				try
				{

					final UserModel loggedUser = userService.getUserForUID(username);
					if (loggedUser == null)
					{
						GlobalMessages.addErrorMessage(model, "Invalid Email Address or Password");
					}
					else if (loggedUser.isLoginDisabled())
					{
						GlobalMessages.addErrorMessage(model, "Your account is locked. Please contact the Administrator");
					}
					else
					{
						GlobalMessages.addErrorMessage(model, "Invalid Email Address or Password");
					}
				}
				catch (final UnknownIdentifierException unknownIdentifierException)
				{
					GlobalMessages.addErrorMessage(model, "Invalid Email Address or Password");
				}

			}
		}
		else
		{
			GlobalMessages.addErrorMessage(model, "Invalid Email Address or Password");
		}

		return getLoginView();
	}

	protected String getDefaultLoginPage(final boolean loginError, final HttpSession session, final HttpServletRequest request,
			final HttpServletResponse response, final Model model) throws CMSItemNotFoundException
	{
		final LoginForm loginForm = new LoginForm();
		model.addAttribute(loginForm);
		final String username = (String) session.getAttribute(SPRING_SECURITY_LAST_USERNAME);
		if (username != null)
		{
			session.removeAttribute(SPRING_SECURITY_LAST_USERNAME);
		}
		LOG.info("Inside 'getDefaultLoginPage(loginError, session, request, response, model)'");
		loginForm.setJ_username(username);

		//	final LoginForm loginForm = new LoginForm();
		final Cookie[] cookies = request.getCookies();
		if (cookies != null)
		{
			for (int i = 0; i < cookies.length; i++)
			{
				final Cookie cookie = cookies[i];
				if (cookie.getName().equals("RememberMeCustom"))
				{
					LOG.info("in RememberMeCustom block of cookie :");
					LOG.info("COOKIE VAL IS :" + cookie.getValue());

					loginForm.setJ_username(cookie.getValue());
					model.addAttribute("checkboxx", "true");
					break;
				}
			}
		}
		model.addAttribute("loginForm", loginForm);

		storeCmsPageInModel(model, getLoginCmsPage());
		setUpMetaDataForContentPage(model, (ContentPageModel) getLoginCmsPage());
		model.addAttribute("metaRobots", "index,no-follow");

		final Breadcrumb loginBreadcrumbEntry = new Breadcrumb("#", getMessageSource().getMessage("header.link.login", null,
				getI18nService().getCurrentLocale()), null);
		model.addAttribute("breadcrumbs", Collections.singletonList(loginBreadcrumbEntry));

		if (username != null)
		{
			if (loginError)
			{
				//request.setAttribute("loginError", true);
				session.setAttribute("loginError", true);
				LOG.info("inside 'if (loginError)'");
				try
				{

					final UserModel loggedUser = userService.getUserForUID(username);
					if (loggedUser == null)
					{
						GlobalMessages.addErrorMessage(model, "Invalid Email Address or Password");
					}
					else if (loggedUser.isLoginDisabled())
					{
						GlobalMessages.addErrorMessage(model, "Your account is locked. Please contact the Administrator");
					}
					else
					{
						GlobalMessages.addErrorMessage(model, "Invalid Email Address or Password");
					}
				}
				catch (final UnknownIdentifierException unknownIdentifierException)
				{
					GlobalMessages.addErrorMessage(model, "Invalid Email Address or Password");
				}

			}
		}
		else
		{
			GlobalMessages.addErrorMessage(model, "Invalid Email Address or Password");
		}

		return getLoginView();
	}
}
