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
public class SupportBreadcrumbBuilder
{

	private static final String SUPPORT_URL = "/support/contact-us";
	private static final String SUPPORT_KEY = "header.link.support";
	private static final String TECHNICAL_URL = "/support/technical-line";
	private static final String TECHNICAL_KEY = "header.link.technical";
	private static final String CUSTOMER_URL = "/support/customer-service";
	private static final String CUSTOMER_KEY = "header.link.customer";
	private static final String CONTACTUS_KEY = "text.aboutus.contactus";

	//Balaji added for to fix defect FCT 608
	private static final String SUPPORT1_URL = "/leadGeneration/contact-us";


	private MessageSource messageSource;
	private I18NService i18nService;

	public List<Breadcrumb> getBreadcrumbs(final String resourceKey) throws IllegalArgumentException
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();

		breadcrumbs.add(new Breadcrumb(TECHNICAL_URL, getMessageSource().getMessage(SUPPORT_KEY, null,
				getI18nService().getCurrentLocale()), null));
		breadcrumbs.add(new Breadcrumb(TECHNICAL_URL, getMessageSource().getMessage(TECHNICAL_KEY, null,
				getI18nService().getCurrentLocale()), null));

		if (resourceKey != null && StringUtils.isNotBlank(resourceKey))
		{

			breadcrumbs.add(new Breadcrumb("#", getMessageSource()
					.getMessage(resourceKey, null, getI18nService().getCurrentLocale()), null));

		}

		return breadcrumbs;
	}

	public List<Breadcrumb> getTechnicalBreadcrumbs(final String resourceKey) throws IllegalArgumentException
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();

		breadcrumbs.add(new Breadcrumb(TECHNICAL_URL, getMessageSource().getMessage(SUPPORT_KEY, null,
				getI18nService().getCurrentLocale()), null));
		breadcrumbs.add(new Breadcrumb(TECHNICAL_URL, getMessageSource().getMessage(TECHNICAL_KEY, null,
				getI18nService().getCurrentLocale()), null));

		if (resourceKey != null && StringUtils.isNotBlank(resourceKey))
		{

			breadcrumbs.add(new Breadcrumb("#", getMessageSource()
					.getMessage(resourceKey, null, getI18nService().getCurrentLocale()), null));

		}

		return breadcrumbs;
	}

	public List<Breadcrumb> getContactUsBreadcrumbs(final String resourceKey) throws IllegalArgumentException
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb(SUPPORT_URL, getMessageSource().getMessage(SUPPORT_KEY, null,
				getI18nService().getCurrentLocale()), null));
		breadcrumbs.add(new Breadcrumb(SUPPORT_URL, getMessageSource().getMessage(CONTACTUS_KEY, null,
				getI18nService().getCurrentLocale()), null));

		if (resourceKey != null && StringUtils.isNotBlank(resourceKey))
		{

			breadcrumbs.add(new Breadcrumb("#", getMessageSource()
					.getMessage(resourceKey, null, getI18nService().getCurrentLocale()), null));

		}

		return breadcrumbs;
	}


	public List<Breadcrumb> getCustomerBreadcrumbs(final String resourceKey) throws IllegalArgumentException
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb(CUSTOMER_URL, getMessageSource().getMessage(SUPPORT_KEY, null,
				getI18nService().getCurrentLocale()), null));

		breadcrumbs.add(new Breadcrumb(CUSTOMER_URL, getMessageSource().getMessage(CUSTOMER_KEY, null,
				getI18nService().getCurrentLocale()), null));

		if (resourceKey != null && StringUtils.isNotBlank(resourceKey))
		{

			breadcrumbs.add(new Breadcrumb("#", getMessageSource()
					.getMessage(resourceKey, null, getI18nService().getCurrentLocale()), null));

		}

		return breadcrumbs;
	}

	//Start Balaji added for to fix defect FCT 608
	public List<Breadcrumb> getEmailContactUsBreadcrumbs(final String resourceKey) throws IllegalArgumentException
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb(SUPPORT1_URL, getMessageSource().getMessage(SUPPORT_KEY, null,
				getI18nService().getCurrentLocale()), null));
		breadcrumbs.add(new Breadcrumb(SUPPORT1_URL, getMessageSource().getMessage(CONTACTUS_KEY, null,
				getI18nService().getCurrentLocale()), null));

		if (resourceKey != null && StringUtils.isNotBlank(resourceKey))
		{

			breadcrumbs.add(new Breadcrumb("#", getMessageSource()
					.getMessage(resourceKey, null, getI18nService().getCurrentLocale()), null));

		}

		return breadcrumbs;
	}

	//End Balaji added for to fix defect FCT 608
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


	public List<Breadcrumb> createSupportBreadCrumbs(final String componentUID, final String complnkname)
	{
		final List<Breadcrumb> breadcrumbs = this.getContactUsBreadcrumbs(null);
		breadcrumbs.add(new Breadcrumb("/support/contact-us/" + componentUID + "?complnkname=" + complnkname, complnkname, null));
		return breadcrumbs;
	}

	public List<Breadcrumb> createTechnicalBreadCrumbs(final String componentUID, final String complnkname)
	{
		final List<Breadcrumb> breadcrumbs = this.getTechnicalBreadcrumbs(null);
		breadcrumbs
				.add(new Breadcrumb("/support/technical-line/" + componentUID + "?complnkname=" + complnkname, complnkname, null));
		return breadcrumbs;
	}

	public List<Breadcrumb> createCustomerBreadCrumbs(final String componentUID, final String complnkname)
	{
		final List<Breadcrumb> breadcrumbs = this.getCustomerBreadcrumbs(null);
		breadcrumbs.add(new Breadcrumb("/support/customer-service/" + componentUID + "?complnkname=" + complnkname, complnkname,
				null));
		return breadcrumbs;
	}
}
