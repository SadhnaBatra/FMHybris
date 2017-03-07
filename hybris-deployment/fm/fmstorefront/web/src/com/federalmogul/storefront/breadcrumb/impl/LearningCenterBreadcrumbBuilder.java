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
 * @author SR279690
 * 
 */
public class LearningCenterBreadcrumbBuilder
{
	private static final String LEARNING_CENTER_URL = "/lc/learningCenter";
	private static final String LEARNING_CENTER_KEY = "header.link.learningcenter";
	private static final String OVERVIEW_URL = "/lc/learningCenter";
	private static final String OVERVIEW_KEY = "header.link.learningcenteroverview";
	private static final String TRAINING_OPTIONS_URL = "/lc/training-options";
	private static final String TRAINING_OPTIONS_KEY = "header.link.trainingoptions";
	private static final String COURSES_URL = "/lc/courses";
	private static final String COURSES_KEY = "header.link.courses";
	private static final String TECH_TIPS_URL = "/lc/tech-tips";
	private static final String TECH_TIPS_KEY = "header.link.techtips";
	private static final String DIAGNOSTIC_CENTER_URL = "/lc/diagnostic-center";
	private static final String DIAGNOSTIC_CENTER_KEY = "header.link.diagnosticcenter";
	private static final String BULLETINS_URL = "/lc/bulletins";
	private static final String BULLETINS_KEY = "header.link.bulletins";
	private static final String CATALOGS_URL = "/lc/catalogs";
	private static final String CATALOGS_KEY = "header.link.catalogs";
	private static final String VIDEO_GALLERY_URL = "/lc/video-gallery";
	private static final String VIDEO_GALLERY_KEY = "header.link.videogallery";
	private static final String SPEC_LOOKUP_URL = "/lc/speclookup";
	private static final String SPEC_LOOKUP_KEY = "header.link.speclookup";
	private static final String CALCULATORS_URL = "/lc/calculators";
	private static final String CALCULATORS_KEY = "header.link.calculators";

	private static final String GARAGEGURU_URL = "/lc/aboutRewards";
	private static final String GARAGEREWARD_ABOUT_URL = "/lc/aboutRewards";

	private MessageSource messageSource;
	private I18NService i18nService;

	public List<Breadcrumb> getBreadcrumbs(final String resourceKey) throws IllegalArgumentException
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb(LEARNING_CENTER_URL, getMessageSource().getMessage(LEARNING_CENTER_KEY, null,
				getI18nService().getCurrentLocale()), null));
		breadcrumbs.add(new Breadcrumb(OVERVIEW_URL, getMessageSource().getMessage(OVERVIEW_KEY, null,
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

	public List<Breadcrumb> getTrainingOptionsBreadcrumbs(final String resourceKey) throws IllegalArgumentException
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb(LEARNING_CENTER_URL, getMessageSource().getMessage(LEARNING_CENTER_KEY, null,
				getI18nService().getCurrentLocale()), null));
		breadcrumbs.add(new Breadcrumb(TRAINING_OPTIONS_URL, getMessageSource().getMessage(TRAINING_OPTIONS_KEY, null,
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

	public List<Breadcrumb> getCoursesBreadcrumbs(final String resourceKey) throws IllegalArgumentException
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb(LEARNING_CENTER_URL, getMessageSource().getMessage(LEARNING_CENTER_KEY, null,
				getI18nService().getCurrentLocale()), null));
		breadcrumbs.add(new Breadcrumb(COURSES_URL, getMessageSource().getMessage(COURSES_KEY, null,
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

	public List<Breadcrumb> getTechTipsBreadcrumbs(final String resourceKey) throws IllegalArgumentException
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb(LEARNING_CENTER_URL, getMessageSource().getMessage(LEARNING_CENTER_KEY, null,
				getI18nService().getCurrentLocale()), null));
		breadcrumbs.add(new Breadcrumb(TECH_TIPS_URL, getMessageSource().getMessage(TECH_TIPS_KEY, null,
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

	public List<Breadcrumb> getDiagnosticCenterBreadcrumbs(final String resourceKey) throws IllegalArgumentException
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb(LEARNING_CENTER_URL, getMessageSource().getMessage(LEARNING_CENTER_KEY, null,
				getI18nService().getCurrentLocale()), null));
		breadcrumbs.add(new Breadcrumb(DIAGNOSTIC_CENTER_URL, getMessageSource().getMessage(DIAGNOSTIC_CENTER_KEY, null,
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

	public List<Breadcrumb> getBulletinsBreadcrumbs(final String resourceKey) throws IllegalArgumentException
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb(LEARNING_CENTER_URL, getMessageSource().getMessage(LEARNING_CENTER_KEY, null,
				getI18nService().getCurrentLocale()), null));
		breadcrumbs.add(new Breadcrumb(BULLETINS_URL, getMessageSource().getMessage(BULLETINS_KEY, null,
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

	public List<Breadcrumb> getCatalogsBreadcrumbs(final String resourceKey) throws IllegalArgumentException
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb(LEARNING_CENTER_URL, getMessageSource().getMessage(LEARNING_CENTER_KEY, null,
				getI18nService().getCurrentLocale()), null));
		breadcrumbs.add(new Breadcrumb(CATALOGS_URL, getMessageSource().getMessage(CATALOGS_KEY, null,
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

	public List<Breadcrumb> getVideoGalleryBreadcrumbs(final String resourceKey) throws IllegalArgumentException
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb(LEARNING_CENTER_URL, getMessageSource().getMessage(LEARNING_CENTER_KEY, null,
				getI18nService().getCurrentLocale()), null));
		breadcrumbs.add(new Breadcrumb(VIDEO_GALLERY_URL, getMessageSource().getMessage(VIDEO_GALLERY_KEY, null,
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

	public List<Breadcrumb> getSpeclookUpBreadcrumbs(final String resourceKey) throws IllegalArgumentException
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb(LEARNING_CENTER_URL, getMessageSource().getMessage(LEARNING_CENTER_KEY, null,
				getI18nService().getCurrentLocale()), null));
		breadcrumbs.add(new Breadcrumb(SPEC_LOOKUP_URL, getMessageSource().getMessage(SPEC_LOOKUP_KEY, null,
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

	public List<Breadcrumb> getCalculatorsBreadcrumbs(final String resourceKey) throws IllegalArgumentException
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb(LEARNING_CENTER_URL, getMessageSource().getMessage(LEARNING_CENTER_KEY, null,
				getI18nService().getCurrentLocale()), null));
		breadcrumbs.add(new Breadcrumb(CALCULATORS_URL, getMessageSource().getMessage(CALCULATORS_KEY, null,
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

	public List<Breadcrumb> createTrainingOptionsBreadCrumbs(final String componentUID, final String complnkname)
	{
		final List<Breadcrumb> breadcrumbs = this.getTrainingOptionsBreadcrumbs(null);
		breadcrumbs.add(new Breadcrumb("/lc/training-options/" + componentUID + "?complnkname=" + complnkname, complnkname, null));
		return breadcrumbs;
	}

	public List<Breadcrumb> createCoursesBreadCrumbs(final String componentUID, final String complnkname)
	{
		final List<Breadcrumb> breadcrumbs = this.getCoursesBreadcrumbs(null);
		breadcrumbs.add(new Breadcrumb("/lc/courses/" + componentUID + "?complnkname=" + complnkname, complnkname, null));
		return breadcrumbs;
	}

	public List<Breadcrumb> createTechTipsBreadCrumbs(final String componentUID, final String complnkname)
	{
		final List<Breadcrumb> breadcrumbs = this.getTechTipsBreadcrumbs(null);
		breadcrumbs.add(new Breadcrumb("/lc/tech-tips/" + componentUID + "?complnkname=" + complnkname, complnkname, null));
		return breadcrumbs;
	}

	public List<Breadcrumb> createDiagnosticCenterBreadCrumbs(final String componentUID, final String complnkname)
	{
		final List<Breadcrumb> breadcrumbs = this.getDiagnosticCenterBreadcrumbs(null);
		breadcrumbs.add(new Breadcrumb("/lc/diagnostic-center/" + componentUID + "?complnkname=" + complnkname, complnkname, null));
		return breadcrumbs;
	}

	public List<Breadcrumb> createBulletinsBreadCrumbs(final String componentUID, final String complnkname)
	{
		final List<Breadcrumb> breadcrumbs = this.getBulletinsBreadcrumbs(null);
		breadcrumbs.add(new Breadcrumb("/lc/bulletins/" + componentUID + "?complnkname=" + complnkname, complnkname, null));
		return breadcrumbs;
	}

	public List<Breadcrumb> createCatalogsBreadCrumbs(final String componentUID, final String complnkname)
	{
		final List<Breadcrumb> breadcrumbs = this.getCatalogsBreadcrumbs(null);
		breadcrumbs.add(new Breadcrumb("/lc/catalogs/" + componentUID + "?complnkname=" + complnkname, complnkname, null));
		return breadcrumbs;
	}

	public List<Breadcrumb> createVideoGalleryBreadCrumbs(final String componentUID, final String complnkname)
	{
		final List<Breadcrumb> breadcrumbs = this.getVideoGalleryBreadcrumbs(null);
		breadcrumbs.add(new Breadcrumb("/lc/video-gallery/" + componentUID + "?complnkname=" + complnkname, complnkname, null));
		return breadcrumbs;
	}

	public List<Breadcrumb> createSpecLookUpBreadCrumbs(final String componentUID, final String complnkname)
	{
		final List<Breadcrumb> breadcrumbs = this.getSpeclookUpBreadcrumbs(null);
		breadcrumbs.add(new Breadcrumb("/lc/speclookup/" + componentUID + "?complnkname=" + complnkname, complnkname, null));
		return breadcrumbs;
	}

	public List<Breadcrumb> createCalculatorsBreadCrumbs(final String componentUID, final String complnkname)
	{
		final List<Breadcrumb> breadcrumbs = this.getCalculatorsBreadcrumbs(null);
		breadcrumbs.add(new Breadcrumb("/lc/calculators/" + componentUID + "?complnkname=" + complnkname, complnkname, null));
		return breadcrumbs;
	}

	public List<Breadcrumb> getAboutRewardsBreadcrumbs(final String resourceKey) throws IllegalArgumentException
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb(GARAGEGURU_URL, getMessageSource().getMessage("Learning Center", null,
				getI18nService().getCurrentLocale()), null));

		if (resourceKey != null)
		{
			if (StringUtils.isNotBlank(resourceKey))
			{
				breadcrumbs.add(new Breadcrumb(GARAGEREWARD_ABOUT_URL, getMessageSource().getMessage(resourceKey, null,
						getI18nService().getCurrentLocale()), null));
			}
		}

		return breadcrumbs;
	}


}
