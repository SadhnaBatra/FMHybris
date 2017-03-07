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
 * @author AI302762
 * 
 */
public class LoyaltyHomeBreadcrumbBuilder
{

	private static final String EARN_URL = "/loyalty/earn";
	private static final String EARN_KEY = "header.link.earn";
	private static final String HISTORY_URL = "/loyalty/history";
	private static final String HISTORY_KEY = "header.link.history";
	private static final String REWARD_URL = "/garageGuru/aboutRewards";
	private static final String GARAGE_REWARD_HOME_URL = "/?site=loyalty";
	private static final String REDEEM_URL = "#";
	private static final String CART_URL = "/loyaltycart";
        private static final String FAQ_URL = "/loyalty/FAQs";
	private static final String SURVEY_URL = "/Survey";
	private MessageSource messageSource;
	private I18NService i18nService;

	public List<Breadcrumb> getLoyaltyBreadcrumbs(final String resourceKey) throws IllegalArgumentException
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb(GARAGE_REWARD_HOME_URL, getMessageSource().getMessage(" Garage Rewards ", null,
				getI18nService().getCurrentLocale()), null));

		if (resourceKey != null)
		{
			if (StringUtils.isNotBlank(resourceKey))
			{
				breadcrumbs.add(new Breadcrumb(EARN_URL, getMessageSource().getMessage("How To Earn Points", null,
						getI18nService().getCurrentLocale()), null));
			}
		}

		return breadcrumbs;
	}

	public List<Breadcrumb> getLoyaltyHistoryBreadcrumbs(final String resourceKey) throws IllegalArgumentException
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();


		if (resourceKey != null)
		{
			if (StringUtils.isNotBlank(resourceKey))
			{
				breadcrumbs.add(new Breadcrumb(HISTORY_URL, getMessageSource().getMessage(resourceKey, null,
						getI18nService().getCurrentLocale()), null));
			}
		}

		return breadcrumbs;
	}
	public List<Breadcrumb> getAboutRewardsBreadcrumbs(final String resourceKey) throws IllegalArgumentException
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb(REWARD_URL, getMessageSource().getMessage(" Garage Gurus ", null,
				getI18nService().getCurrentLocale()), null));
		
		if (resourceKey != null)
		{
			if (StringUtils.isNotBlank(resourceKey))
			{
				breadcrumbs.add(new Breadcrumb(REWARD_URL, getMessageSource().getMessage(resourceKey, null,
						getI18nService().getCurrentLocale()), null));
			}
		}

		return breadcrumbs;
	}
      public List<Breadcrumb> getRedeemBreadcrumbs(final String resourceKey) throws IllegalArgumentException
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb(GARAGE_REWARD_HOME_URL, getMessageSource().getMessage(" Garage Rewards ", null,
				getI18nService().getCurrentLocale()), null));
		
		if (resourceKey != null)
		{
			if (StringUtils.isNotBlank(resourceKey))
			{
				breadcrumbs.add(new Breadcrumb(REDEEM_URL, getMessageSource().getMessage("Redeem Points", null,
						getI18nService().getCurrentLocale()), null));
			}
		}

		return breadcrumbs;
	}
	public List<Breadcrumb> getCheckoutBreadcrumbs(final String resourceKey) throws IllegalArgumentException
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb(GARAGE_REWARD_HOME_URL, getMessageSource().getMessage(" Garage Rewards ", null,
				getI18nService().getCurrentLocale()), null));

		breadcrumbs.add(new Breadcrumb(CART_URL, getMessageSource().getMessage("Shopping Cart", null,
				getI18nService().getCurrentLocale()), null));

		if (resourceKey != null)
		{
			if (StringUtils.isNotBlank(resourceKey))
			{
				breadcrumbs.add(new Breadcrumb("#", getMessageSource().getMessage("Checkout", null,
						getI18nService().getCurrentLocale()), null));
			}
		}

		return breadcrumbs;
	}
      public List<Breadcrumb> getFAQBreadcrumbs(final String resourceKey) throws IllegalArgumentException
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb(GARAGE_REWARD_HOME_URL, getMessageSource().getMessage(" Garage Rewards ", null,
				getI18nService().getCurrentLocale()), null));

		if (resourceKey != null)
		{
			if (StringUtils.isNotBlank(resourceKey))
			{
				breadcrumbs.add(new Breadcrumb(FAQ_URL, getMessageSource().getMessage("FAQs & Rules", null,
						getI18nService().getCurrentLocale()), null));
			}
		}

		return breadcrumbs;
	}

	public List<Breadcrumb> getLoyaltyReferBreadcrumbs(final String resourceKey) throws IllegalArgumentException
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb(GARAGE_REWARD_HOME_URL, getMessageSource().getMessage("Garage Rewards ", null,
				getI18nService().getCurrentLocale()), null));

		if (resourceKey != null)
		{
			if (StringUtils.isNotBlank(resourceKey))
			{
				breadcrumbs.add(new Breadcrumb(EARN_URL, getMessageSource().getMessage("Refer a Tech", null,
						getI18nService().getCurrentLocale()), null));
			}
		}

		return breadcrumbs;
	}
	public List<Breadcrumb> getLoyaltySurveyBreadcrumbs(final String resourceKey) throws IllegalArgumentException
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb(GARAGE_REWARD_HOME_URL, getMessageSource().getMessage(" Garage Rewards ", null,
				getI18nService().getCurrentLocale()), null));
		breadcrumbs.add(new Breadcrumb(EARN_URL, getMessageSource().getMessage("Earn Points", null,
				getI18nService().getCurrentLocale()), null));
		if (resourceKey != null)
		{
			if (StringUtils.isNotBlank(resourceKey))
			{
				breadcrumbs.add(new Breadcrumb(SURVEY_URL, getMessageSource().getMessage("Take Survey", null,
						getI18nService().getCurrentLocale()), null));
				breadcrumbs.add(new Breadcrumb("#", getMessageSource().getMessage(resourceKey, null,
						getI18nService().getCurrentLocale()), null));

			}
		}

		return breadcrumbs;
	}

	public List<Breadcrumb> getLoyaltySurveyCompleteBreadcrumbs(final String resourceKey) throws IllegalArgumentException
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb(GARAGE_REWARD_HOME_URL, getMessageSource().getMessage(" Garage Rewards ", null,
				getI18nService().getCurrentLocale()), null));
		breadcrumbs.add(new Breadcrumb(EARN_URL, getMessageSource().getMessage("Earn Points", null,
				getI18nService().getCurrentLocale()), null));
		if (resourceKey != null)
		{
			if (StringUtils.isNotBlank(resourceKey))
			{
				breadcrumbs.add(new Breadcrumb(SURVEY_URL, getMessageSource().getMessage("Take Survey", null,
						getI18nService().getCurrentLocale()), null));
				breadcrumbs.add(new Breadcrumb("#", getMessageSource().getMessage(resourceKey, null,
						getI18nService().getCurrentLocale()), null));
				breadcrumbs.add(new Breadcrumb("#", getMessageSource().getMessage("Complete", null,
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


}
