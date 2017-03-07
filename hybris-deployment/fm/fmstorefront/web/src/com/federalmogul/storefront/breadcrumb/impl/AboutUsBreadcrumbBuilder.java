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
package com.federalmogul.storefront.breadcrumb.impl;

import de.hybris.platform.servicelayer.i18n.I18NService;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.MessageSource;

import com.federalmogul.storefront.breadcrumb.Breadcrumb;


/**
 * MyCompanyBreadcrumbBuilder implementation for account related pages
 */
public class AboutUsBreadcrumbBuilder
{
	private static final String ABOUT_US_URL = "/about-us/company";
	private static final String ABOUT_US_KEY = "header.link.aboutus";
	private static final String COMPANY_URL = "/about-us/company";
	private static final String COMPANY_KEY = "header.link.aboutuscompany";
	private static final String NEWS_URL = "/about-us/news";
	private static final String NEWS_KEY = "header.link.news";
	private static final String CAREERS_URL = "/about-us/careers";
	private static final String CAREERS_KEY = "header.link.careers";
	private static final String EXECUTIVE_URL = "/about-us/executive";
	private static final String EXECUTIVE_KEY = "header.link.executive";
	private static final String INVESTORS_URL = "/about-us/investors";
	private static final String INVESTORS_KEY = "header.link.investors";
	private static final String SUPPLIERS_URL = "/about-us/suppliers";
	private static final String SUPPLIERS_KEY = "header.link.suppliers";
	private static final String PRIVACY_URL = "/about-us/privacy-legal";
	private static final String PRIVACY_KEY = "header.link.privacy";
	private static final String MEDIA_URL = "/about-us/media";
	private static final String MEDIA_KEY = "header.link.media";
	private MessageSource messageSource;
	private I18NService i18nService;

	public List<Breadcrumb> getBreadcrumbs(final String resourceKey) throws IllegalArgumentException
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb(ABOUT_US_URL, getMessageSource().getMessage(ABOUT_US_KEY, null,
				getI18nService().getCurrentLocale()), null));
		breadcrumbs.add(new Breadcrumb(COMPANY_URL, getMessageSource().getMessage(COMPANY_KEY, null,
				getI18nService().getCurrentLocale()), null));

		if (resourceKey != null)
		{
			if (StringUtils.isNotBlank(resourceKey))
			{
				breadcrumbs.add(new Breadcrumb("#", getMessageSource().getMessage(resourceKey, null,
						getI18nService().getCurrentLocale()), null));
			}
		}

		return breadcrumbs;
	}

	public List<Breadcrumb> getBreadcrumbs(String resourceKey, final String url, final String Msg, final String compUid,
			final String compLinkName) throws IllegalArgumentException
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb(url, getMessageSource().getMessage(ABOUT_US_KEY, null, getI18nService().getCurrentLocale()),
				null));
		breadcrumbs.add(new Breadcrumb(url, getMessageSource().getMessage(Msg, null, getI18nService().getCurrentLocale()), null));

		if (null != compUid && null != compLinkName)
		{
			breadcrumbs.add(new Breadcrumb(url + "/" + compUid + "?complnkname=" + compLinkName, compLinkName, null));
			resourceKey = null;
		}

		if (resourceKey != null)
		{
			if (StringUtils.isNotBlank(resourceKey))
			{
				breadcrumbs.add(new Breadcrumb("#", getMessageSource().getMessage(resourceKey, null,
						getI18nService().getCurrentLocale()), null));
			}
		}

		return breadcrumbs;
	}

	public List<Breadcrumb> getNewsBreadcrumbs(final String resourceKey) throws IllegalArgumentException
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb(ABOUT_US_URL, getMessageSource().getMessage(ABOUT_US_KEY, null,
				getI18nService().getCurrentLocale()), null));
		breadcrumbs.add(new Breadcrumb(NEWS_URL,
				getMessageSource().getMessage(NEWS_KEY, null, getI18nService().getCurrentLocale()), null));

		if (resourceKey != null)
		{
			if (StringUtils.isNotBlank(resourceKey))
			{
				breadcrumbs.add(new Breadcrumb("#", getMessageSource().getMessage(resourceKey, null,
						getI18nService().getCurrentLocale()), null));
			}
		}

		return breadcrumbs;
	}

	public List<Breadcrumb> getCareersBreadcrumbs(final String resourceKey) throws IllegalArgumentException
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb(ABOUT_US_URL, getMessageSource().getMessage(ABOUT_US_KEY, null,
				getI18nService().getCurrentLocale()), null));
		breadcrumbs.add(new Breadcrumb(CAREERS_URL, getMessageSource().getMessage(CAREERS_KEY, null,
				getI18nService().getCurrentLocale()), null));

		if (resourceKey != null)
		{
			if (StringUtils.isNotBlank(resourceKey))
			{
				breadcrumbs.add(new Breadcrumb("#", getMessageSource().getMessage(resourceKey, null,
						getI18nService().getCurrentLocale()), null));
			}
		}

		return breadcrumbs;
	}

	public List<Breadcrumb> getInvestorsBreadcrumbs(final String resourceKey) throws IllegalArgumentException
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb(ABOUT_US_URL, getMessageSource().getMessage(ABOUT_US_KEY, null,
				getI18nService().getCurrentLocale()), null));
		breadcrumbs.add(new Breadcrumb(INVESTORS_URL, getMessageSource().getMessage(INVESTORS_KEY, null,
				getI18nService().getCurrentLocale()), null));

		if (resourceKey != null && StringUtils.isNotBlank(resourceKey))
		{

			breadcrumbs.add(new Breadcrumb("#", getMessageSource()
					.getMessage(resourceKey, null, getI18nService().getCurrentLocale()), null));

		}

		return breadcrumbs;
	}

	public List<Breadcrumb> getSuppliersBreadcrumbs(final String resourceKey) throws IllegalArgumentException
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb(ABOUT_US_URL, getMessageSource().getMessage(ABOUT_US_KEY, null,
				getI18nService().getCurrentLocale()), null));
		breadcrumbs.add(new Breadcrumb(SUPPLIERS_URL, getMessageSource().getMessage(SUPPLIERS_KEY, null,
				getI18nService().getCurrentLocale()), null));

		if (resourceKey != null && StringUtils.isNotBlank(resourceKey))
		{

			breadcrumbs.add(new Breadcrumb("#", getMessageSource()
					.getMessage(resourceKey, null, getI18nService().getCurrentLocale()), null));

		}

		return breadcrumbs;
	}

	public List<Breadcrumb> getPrivacyBreadcrumbs(final String resourceKey) throws IllegalArgumentException
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb(ABOUT_US_URL, getMessageSource().getMessage(ABOUT_US_KEY, null,
				getI18nService().getCurrentLocale()), null));
		breadcrumbs.add(new Breadcrumb(PRIVACY_URL, getMessageSource().getMessage(PRIVACY_KEY, null,
				getI18nService().getCurrentLocale()), null));

		if (resourceKey != null)
		{
			if (StringUtils.isNotBlank(resourceKey))
			{
				breadcrumbs.add(new Breadcrumb("#", getMessageSource().getMessage(resourceKey, null,
						getI18nService().getCurrentLocale()), null));
			}
		}

		return breadcrumbs;
	}

	public List<Breadcrumb> getExecutiveBreadcrumbs(final String resourceKey) throws IllegalArgumentException
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb(ABOUT_US_URL, getMessageSource().getMessage(ABOUT_US_KEY, null,
				getI18nService().getCurrentLocale()), null));
		breadcrumbs.add(new Breadcrumb(EXECUTIVE_URL, getMessageSource().getMessage(EXECUTIVE_KEY, null,
				getI18nService().getCurrentLocale()), null));

		if (resourceKey != null && StringUtils.isNotBlank(resourceKey))
		{
			breadcrumbs.add(new Breadcrumb("#", getMessageSource()
					.getMessage(resourceKey, null, getI18nService().getCurrentLocale()), null));

		}

		return breadcrumbs;
	}
	
	public List<Breadcrumb> getMediaBreadcrumbs(final String resourceKey) throws IllegalArgumentException
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb(ABOUT_US_URL, getMessageSource().getMessage(ABOUT_US_KEY, null,
				getI18nService().getCurrentLocale()), null));
		breadcrumbs.add(new Breadcrumb(MEDIA_URL, getMessageSource().getMessage(MEDIA_KEY, null,
				getI18nService().getCurrentLocale()), null));

		if (resourceKey != null)
		{
			if (StringUtils.isNotBlank(resourceKey))
			{
				breadcrumbs.add(new Breadcrumb("#", getMessageSource().getMessage(resourceKey, null,
						getI18nService().getCurrentLocale()), null));
			}
		}

		return breadcrumbs;
	}

	protected I18NService getI18nService()
	{
		return i18nService;
	}

	@Required
	public void setI18nService(final I18NService i18nService)
	{
		this.i18nService = i18nService;
	}

	protected MessageSource getMessageSource()
	{
		return messageSource;
	}

	@Required
	public void setMessageSource(final MessageSource messageSource)
	{
		this.messageSource = messageSource;
	}

	public List<Breadcrumb> createParentBreadCrumbs(final String componentUID, final String complnkname,
			final String parentCompname, final String parentComplink)
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb(componentUID + "?complnkname=" + complnkname + "&parentCompname=" + parentCompname
				+ "&parentComplink=" + parentComplink, complnkname, null));
		return breadcrumbs;
	}



	public List<Breadcrumb> createAboutUsBreadCrumbs(final String componentUID, final String complnkname)
	{
		final List<Breadcrumb> breadcrumbs = this.getBreadcrumbs(null);
		breadcrumbs.add(new Breadcrumb("/about-us/company/" + componentUID + "?complnkname=" + complnkname, complnkname, null));
		return breadcrumbs;
	}

	public List<Breadcrumb> createNewsBreadCrumbs(final String componentUID, final String complnkname)
	{
		final List<Breadcrumb> breadcrumbs = this.getNewsBreadcrumbs(null);
		breadcrumbs.add(new Breadcrumb("/about-us/news/" + componentUID + "?complnkname=" + complnkname, complnkname, null));
		return breadcrumbs;
	}

	public List<Breadcrumb> createCareersBreadCrumbs(final String componentUID, final String complnkname)
	{
		final List<Breadcrumb> breadcrumbs = this.getCareersBreadcrumbs(null);
		breadcrumbs.add(new Breadcrumb("/about-us/careers/" + componentUID + "?complnkname=" + complnkname, complnkname, null));
		return breadcrumbs;
	}

	public List<Breadcrumb> createInvestorsBreadCrumbs(final String componentUID, final String complnkname)
	{
		final List<Breadcrumb> breadcrumbs = this.getInvestorsBreadcrumbs(null);
		breadcrumbs.add(new Breadcrumb("/about-us/investors/" + componentUID + "?complnkname=" + complnkname, complnkname, null));
		return breadcrumbs;
	}

	public List<Breadcrumb> createSuppliersBreadCrumbs(final String componentUID, final String complnkname)
	{
		final List<Breadcrumb> breadcrumbs = this.getSuppliersBreadcrumbs(null);
		breadcrumbs.add(new Breadcrumb("/about-us/suppliers/" + componentUID + "?complnkname=" + complnkname, complnkname, null));
		return breadcrumbs;
	}

	public List<Breadcrumb> createPrivacyBreadCrumbs(final String componentUID, final String complnkname)
	{
		final List<Breadcrumb> breadcrumbs = this.getPrivacyBreadcrumbs(null);
		breadcrumbs
				.add(new Breadcrumb("/about-us/privacy-legal/" + componentUID + "?complnkname=" + complnkname, complnkname, null));
		return breadcrumbs;
	}

	public List<Breadcrumb> createExecutiveBreadCrumbs(final String componentUID, final String complnkname)
	{
		final List<Breadcrumb> breadcrumbs = this.getExecutiveBreadcrumbs(null);
		breadcrumbs.add(new Breadcrumb("/about-us/executive/" + componentUID + "?complnkname=" + complnkname, complnkname, null));
		return breadcrumbs;
	}

	public List<Breadcrumb> createMediaBreadCrumbs(final String componentUID, final String complnkname)
	{
		final List<Breadcrumb> breadcrumbs = this.getMediaBreadcrumbs(null);
		breadcrumbs.add(new Breadcrumb("/about-us/media/" + componentUID + "?complnkname=" + complnkname, complnkname, null));
		return breadcrumbs;
	}
}
