/**
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
 * @author DI278272
 * 
 */
public class BrandBreadcrumbBuilder
{

	private static final String BRAND_KEY = "header.link.brand";

	private static final String ABEX_URL = "/brands/abex";
	private static final String ABEX_KEY = "header.link.abex";
	private static final String ANCO_URL = "/brands/anco";
	private static final String ANCO_KEY = "header.link.anco";
	private static final String CHAMPION_URL = "/brands/champion";
	private static final String CHAMPION_KEY = "header.link.champion";
	private static final String FELPRO_URL = "/brands/fel-pro";
	private static final String FELPRO_KEY = "header.link.fel-pro";
	private static final String FPDIESEL_URL = "/brands/fp-diesel";
	private static final String FPDIESEL_KEY = "header.link.fp-diesel";
	private static final String MOOG_URL = "/brands/moog";
	private static final String MOOG_KEY = "header.link.moog";
	private static final String SEALED_POWER_URL = "/brands/sealed-power";
	private static final String SEALED_POWER_KEY = "header.link.sealed-power";
	private static final String SPEEDPRO_URL = "/brands/speed-pro";
	private static final String SPEEDPRO_KEY = "header.link.speed-pro";
	private static final String WAGNER_BRAKE_URL = "/brands/wagner";
	private static final String WAGNER_BRAKE_KEY = "header.link.wagner";
	private static final String WAGNER_LIGHTING_URL = "/brands/wagnerLighting";
	private static final String WAGNER_LIGHTING_KEY = "header.link.wagnerLighting";

	private static final String NATIONAL_URL = "/brands/national";
	private static final String NATIONAL_KEY = "header.link.national";

	private static final String FERODO_URL = "/brands/ferodo";
	private static final String FERODO_KEY = "header.link.ferodo";

	private static final String BECKARNLEY_URL = "/brands/beckarnley";
	private static final String BECKARNLEY_KEY = "header.link.beckarnley";

	private MessageSource messageSource;
	private I18NService i18nService;

	public List<Breadcrumb> getAbexBreadcrumbs(final String resourceKey) throws IllegalArgumentException
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb(ABEX_URL, getMessageSource()
				.getMessage(BRAND_KEY, null, getI18nService().getCurrentLocale()), null));
		breadcrumbs.add(new Breadcrumb(ABEX_URL,
				getMessageSource().getMessage(ABEX_KEY, null, getI18nService().getCurrentLocale()), null));

		if (resourceKey != null && StringUtils.isNotBlank(resourceKey))
		{

			breadcrumbs.add(new Breadcrumb("#", getMessageSource()
					.getMessage(resourceKey, null, getI18nService().getCurrentLocale()), null));

		}

		return breadcrumbs;
	}

	public List<Breadcrumb> getAncoBreadcrumbs(final String resourceKey) throws IllegalArgumentException
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb(ANCO_URL, getMessageSource()
				.getMessage(BRAND_KEY, null, getI18nService().getCurrentLocale()), null));
		breadcrumbs.add(new Breadcrumb(ANCO_URL,
				getMessageSource().getMessage(ANCO_KEY, null, getI18nService().getCurrentLocale()), null));

		if (resourceKey != null && StringUtils.isNotBlank(resourceKey))
		{

			breadcrumbs.add(new Breadcrumb("#", getMessageSource()
					.getMessage(resourceKey, null, getI18nService().getCurrentLocale()), null));

		}

		return breadcrumbs;
	}

	public List<Breadcrumb> getChampionBreadcrumbs(final String resourceKey) throws IllegalArgumentException
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb(CHAMPION_URL, getMessageSource().getMessage(BRAND_KEY, null,
				getI18nService().getCurrentLocale()), null));
		breadcrumbs.add(new Breadcrumb(CHAMPION_URL, getMessageSource().getMessage(CHAMPION_KEY, null,
				getI18nService().getCurrentLocale()), null));

		if (resourceKey != null && StringUtils.isNotBlank(resourceKey))
		{

			breadcrumbs.add(new Breadcrumb("#", getMessageSource()
					.getMessage(resourceKey, null, getI18nService().getCurrentLocale()), null));

		}

		return breadcrumbs;
	}

	public List<Breadcrumb> getFelproBreadcrumbs(final String resourceKey) throws IllegalArgumentException
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb(FELPRO_URL, getMessageSource().getMessage(BRAND_KEY, null,
				getI18nService().getCurrentLocale()), null));
		breadcrumbs.add(new Breadcrumb(FELPRO_URL, getMessageSource().getMessage(FELPRO_KEY, null,
				getI18nService().getCurrentLocale()), null));

		if (resourceKey != null && StringUtils.isNotBlank(resourceKey))
		{

			breadcrumbs.add(new Breadcrumb("#", getMessageSource()
					.getMessage(resourceKey, null, getI18nService().getCurrentLocale()), null));

		}

		return breadcrumbs;
	}

	public List<Breadcrumb> getFPDieselBreadcrumbs(final String resourceKey) throws IllegalArgumentException
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb(FPDIESEL_URL, getMessageSource().getMessage(BRAND_KEY, null,
				getI18nService().getCurrentLocale()), null));
		breadcrumbs.add(new Breadcrumb(FPDIESEL_URL, getMessageSource().getMessage(FPDIESEL_KEY, null,
				getI18nService().getCurrentLocale()), null));

		if (resourceKey != null && StringUtils.isNotBlank(resourceKey))
		{

			breadcrumbs.add(new Breadcrumb("#", getMessageSource()
					.getMessage(resourceKey, null, getI18nService().getCurrentLocale()), null));

		}

		return breadcrumbs;
	}

	public List<Breadcrumb> getMoogBreadcrumbs(final String resourceKey) throws IllegalArgumentException
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb(MOOG_URL, getMessageSource()
				.getMessage(BRAND_KEY, null, getI18nService().getCurrentLocale()), null));
		breadcrumbs.add(new Breadcrumb(MOOG_URL,
				getMessageSource().getMessage(MOOG_KEY, null, getI18nService().getCurrentLocale()), null));

		if (resourceKey != null && StringUtils.isNotBlank(resourceKey))
		{

			breadcrumbs.add(new Breadcrumb("#", getMessageSource()
					.getMessage(resourceKey, null, getI18nService().getCurrentLocale()), null));

		}

		return breadcrumbs;
	}

	public List<Breadcrumb> getSealedPowerBreadcrumbs(final String resourceKey) throws IllegalArgumentException
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb(SEALED_POWER_URL, getMessageSource().getMessage(BRAND_KEY, null,
				getI18nService().getCurrentLocale()), null));
		breadcrumbs.add(new Breadcrumb(SEALED_POWER_URL, getMessageSource().getMessage(SEALED_POWER_KEY, null,
				getI18nService().getCurrentLocale()), null));

		if (resourceKey != null && StringUtils.isNotBlank(resourceKey))
		{

			breadcrumbs.add(new Breadcrumb("#", getMessageSource()
					.getMessage(resourceKey, null, getI18nService().getCurrentLocale()), null));

		}

		return breadcrumbs;
	}

	public List<Breadcrumb> getSpeedproBreadcrumbs(final String resourceKey) throws IllegalArgumentException
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb(SPEEDPRO_URL, getMessageSource().getMessage(BRAND_KEY, null,
				getI18nService().getCurrentLocale()), null));
		breadcrumbs.add(new Breadcrumb(SPEEDPRO_URL, getMessageSource().getMessage(SPEEDPRO_KEY, null,
				getI18nService().getCurrentLocale()), null));

		if (resourceKey != null && StringUtils.isNotBlank(resourceKey))
		{

			breadcrumbs.add(new Breadcrumb("#", getMessageSource()
					.getMessage(resourceKey, null, getI18nService().getCurrentLocale()), null));

		}

		return breadcrumbs;
	}

	public List<Breadcrumb> getWagnerBrakeBreadcrumbs(final String resourceKey) throws IllegalArgumentException
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb(WAGNER_BRAKE_URL, getMessageSource().getMessage(BRAND_KEY, null,
				getI18nService().getCurrentLocale()), null));
		breadcrumbs.add(new Breadcrumb(WAGNER_BRAKE_URL, getMessageSource().getMessage(WAGNER_BRAKE_KEY, null,
				getI18nService().getCurrentLocale()), null));

		if (resourceKey != null && StringUtils.isNotBlank(resourceKey))
		{

			breadcrumbs.add(new Breadcrumb("#", getMessageSource()
					.getMessage(resourceKey, null, getI18nService().getCurrentLocale()), null));

		}

		return breadcrumbs;
	}

	public List<Breadcrumb> getNationalBreadcrumbs(final String resourceKey) throws IllegalArgumentException
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb(NATIONAL_URL, getMessageSource().getMessage(BRAND_KEY, null,
				getI18nService().getCurrentLocale()), null));
		breadcrumbs.add(new Breadcrumb(NATIONAL_URL, getMessageSource().getMessage(NATIONAL_KEY, null,
				getI18nService().getCurrentLocale()), null));

		if (resourceKey != null && StringUtils.isNotBlank(resourceKey))
		{

			breadcrumbs.add(new Breadcrumb("#", getMessageSource()
					.getMessage(resourceKey, null, getI18nService().getCurrentLocale()), null));

		}

		return breadcrumbs;
	}

	public List<Breadcrumb> getFerodoBreadcrumbs(final String resourceKey) throws IllegalArgumentException
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb(FERODO_URL, getMessageSource().getMessage(BRAND_KEY, null,
				getI18nService().getCurrentLocale()), null));
		breadcrumbs.add(new Breadcrumb(FERODO_URL, getMessageSource().getMessage(FERODO_KEY, null,
				getI18nService().getCurrentLocale()), null));

		if (resourceKey != null && StringUtils.isNotBlank(resourceKey))
		{

			breadcrumbs.add(new Breadcrumb("#", getMessageSource()
					.getMessage(resourceKey, null, getI18nService().getCurrentLocale()), null));

		}

		return breadcrumbs;
	}

	public List<Breadcrumb> getWagnerLightingBreadcrumbs(final String resourceKey) throws IllegalArgumentException
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb(WAGNER_LIGHTING_URL, getMessageSource().getMessage(BRAND_KEY, null,
				getI18nService().getCurrentLocale()), null));
		breadcrumbs.add(new Breadcrumb(WAGNER_LIGHTING_URL, getMessageSource().getMessage(WAGNER_LIGHTING_KEY, null,
				getI18nService().getCurrentLocale()), null));

		if (resourceKey != null && StringUtils.isNotBlank(resourceKey))
		{

			breadcrumbs.add(new Breadcrumb("#", getMessageSource()
					.getMessage(resourceKey, null, getI18nService().getCurrentLocale()), null));

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

	public List<Breadcrumb> createAbexBreadCrumbs(final String componentUID, final String complnkname)
	{
		final List<Breadcrumb> breadcrumbs = this.getAbexBreadcrumbs(null);
		breadcrumbs.add(new Breadcrumb("/brand/abex/" + componentUID + "?complnkname=" + complnkname, complnkname, null));
		return breadcrumbs;
	}

	public List<Breadcrumb> createAncoBreadCrumbs(final String componentUID, final String complnkname)
	{
		final List<Breadcrumb> breadcrumbs = this.getAncoBreadcrumbs(null);
		breadcrumbs.add(new Breadcrumb("/brand/anco/" + componentUID + "?complnkname=" + complnkname, complnkname, null));
		return breadcrumbs;
	}

	public List<Breadcrumb> getBeckArnleyBreadcrumbs(final String resourceKey) throws IllegalArgumentException
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb(BECKARNLEY_URL, getMessageSource().getMessage(BRAND_KEY, null,
				getI18nService().getCurrentLocale()), null));
		breadcrumbs.add(new Breadcrumb(BECKARNLEY_URL, getMessageSource().getMessage(BECKARNLEY_KEY, null,
				getI18nService().getCurrentLocale()), null));

		if (resourceKey != null && StringUtils.isNotBlank(resourceKey))
		{

			breadcrumbs.add(new Breadcrumb("#", getMessageSource()
					.getMessage(resourceKey, null, getI18nService().getCurrentLocale()), null));

		}

		return breadcrumbs;
	}
}
