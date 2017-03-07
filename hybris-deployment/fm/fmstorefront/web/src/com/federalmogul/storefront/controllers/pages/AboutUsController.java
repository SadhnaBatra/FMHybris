package com.federalmogul.storefront.controllers.pages;

import de.hybris.platform.acceleratorservices.config.SiteConfigService;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.federalmogul.storefront.breadcrumb.Breadcrumb;
import com.federalmogul.storefront.breadcrumb.impl.AboutUsBreadcrumbBuilder;
import com.federalmogul.storefront.controllers.ControllerConstants;
import com.federalmogul.storefront.util.XSSFilterUtil;




@Controller
@Scope("tenant")
@RequestMapping(value = "/**/about-us")
public class AboutUsController extends AbstractPageController
{

	protected static final String COMPANY_CMS_PAGE = "fmCompanyLandingPage";
	protected static final String NEWS_CMS_PAGE = "fmNewsLandingPage";
	protected static final String CAREERS_CMS_PAGE = "fmCareersLandingPage";
	protected static final String INVESTORS_CMS_PAGE = "fmInvestorsLandingPage";
	protected static final String SUPPLIERS_CMS_PAGE = "fmSuppliersLandingPage";
	protected static final String PRIVACY_CMS_PAGE = "fmPrivacyLegalLandingPage";
	protected static final String EXECUTIVE_CMS_PAGE = "fmExecutiveLandingPage";
	protected static final String MEDIA_CMS_PAGE = "FMMediaLandingPage";


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

	@Resource(name = "aboutUsBreadcrumbBuilder")
	protected AboutUsBreadcrumbBuilder aboutUsBreadcrumbBuilder;


	@Resource(name = "siteConfigService")
	private SiteConfigService siteConfigService;

	private static final Logger LOG = Logger.getLogger(AboutUsController.class);

	@RequestMapping(value = "/company", method = RequestMethod.GET)
	public String company(final Model model) throws CMSItemNotFoundException
	{


		setUpPageInfo(COMPANY_CMS_PAGE, siteConfigService.getString("fm.aboutus.company", "Company Overview"), null, model,
				COMPANY_URL, siteConfigService.getProperty(COMPANY_KEY));

		return ControllerConstants.Views.Pages.AboutUs.CompanyLanding;
	}

	@RequestMapping(value = "/company/{componentUid}", method = RequestMethod.GET)
	public String company(final Model model, @PathVariable final String componentUid, @RequestParam final String complnkname,
			@RequestParam(value = "parentCompname", required = false) final String parentCompname,
			@RequestParam(value = "parentComplink", required = false) final String parentComplink) throws CMSItemNotFoundException
	{

		LOG.info("company");
		LOG.info("in controller");
		storeCmsPageInModel(model, getContentPageForLabelOrId(COMPANY_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(COMPANY_CMS_PAGE));
		model.addAttribute("componentUid", XSSFilterUtil.filter(componentUid));
		final List<Breadcrumb> breadCrumb = new ArrayList<Breadcrumb>();
		if (parentComplink != null && parentCompname != null)
		{

			breadCrumb.addAll(aboutUsBreadcrumbBuilder.createAboutUsBreadCrumbs(XSSFilterUtil.filter(parentCompname),
					XSSFilterUtil.filter(parentComplink)));
			breadCrumb.addAll(aboutUsBreadcrumbBuilder.createParentBreadCrumbs(XSSFilterUtil.filter(componentUid),
					XSSFilterUtil.filter(complnkname), XSSFilterUtil.filter(parentCompname), XSSFilterUtil.filter(parentComplink)));
			model.addAttribute("breadcrumbs", breadCrumb);
		}
		else
		{
			model.addAttribute(
					"breadcrumbs",
					aboutUsBreadcrumbBuilder.createAboutUsBreadCrumbs(XSSFilterUtil.filter(componentUid),
							XSSFilterUtil.filter(complnkname)));
		}


		model.addAttribute("metaRobots", siteConfigService.getString("fm.http.metaRobots.index", "no-index,no-follow"));

		return ControllerConstants.Views.Pages.AboutUs.CompanyLanding;
	}

	@RequestMapping(value = "/news", method = RequestMethod.GET)
	public String news(final Model model) throws CMSItemNotFoundException
	{


		setUpPageInfo(NEWS_CMS_PAGE, siteConfigService.getString("fm.aboutus.news", "News"), null, model, NEWS_URL,
				siteConfigService.getProperty(NEWS_KEY));

		return ControllerConstants.Views.Pages.AboutUs.NewsLanding;
	}

	@RequestMapping(value = "/news/{componentUid}", method = RequestMethod.GET)
	public String news(final Model model, @PathVariable final String componentUid, @RequestParam final String complnkname)
			throws CMSItemNotFoundException
	{

		model.addAttribute("componentUid", XSSFilterUtil.filter(componentUid));

		setUpPageInfo(NEWS_CMS_PAGE, componentUid, complnkname, model, NEWS_URL, siteConfigService.getProperty(NEWS_KEY));

		return ControllerConstants.Views.Pages.AboutUs.NewsLanding;
	}

	@RequestMapping(value = "/careers", method = RequestMethod.GET)
	public String careers(final Model model) throws CMSItemNotFoundException
	{



		setUpPageInfo(CAREERS_CMS_PAGE, siteConfigService.getString("fm.aboutus.careers", "Careers Home"), null, model,
				CAREERS_URL, siteConfigService.getProperty(CAREERS_KEY));

		return ControllerConstants.Views.Pages.AboutUs.CareersLanding;
	}

	@RequestMapping(value = "/careers/{componentUid}", method = RequestMethod.GET)
	public String careers(final Model model, @PathVariable final String componentUid, @RequestParam final String complnkname)
			throws CMSItemNotFoundException
	{


		model.addAttribute("componentUid", XSSFilterUtil.filter(componentUid));

		setUpPageInfo(CAREERS_CMS_PAGE, XSSFilterUtil.filter(componentUid), XSSFilterUtil.filter(complnkname), model, CAREERS_URL,
				siteConfigService.getProperty(CAREERS_KEY));

		return ControllerConstants.Views.Pages.AboutUs.CareersLanding;
	}

	@RequestMapping(value = "/investors", method = RequestMethod.GET)
	public String investors(final Model model) throws CMSItemNotFoundException
	{



		setUpPageInfo(INVESTORS_CMS_PAGE, siteConfigService.getString("fm.aboutus.investors", "Investors Overview"), null, model,
				INVESTORS_URL, siteConfigService.getProperty(INVESTORS_KEY));

		return ControllerConstants.Views.Pages.AboutUs.InvestorsLanding;
	}

	@RequestMapping(value = "/investors/{componentUid}", method = RequestMethod.GET)
	public String investors(final Model model, @PathVariable final String componentUid, @RequestParam final String complnkname)
			throws CMSItemNotFoundException
	{

		model.addAttribute("componentUid", XSSFilterUtil.filter(componentUid));

		setUpPageInfo(INVESTORS_CMS_PAGE, XSSFilterUtil.filter(componentUid), XSSFilterUtil.filter(complnkname), model,
				INVESTORS_URL, siteConfigService.getProperty(INVESTORS_KEY));

		return ControllerConstants.Views.Pages.AboutUs.InvestorsLanding;
	}

	@RequestMapping(value = "/suppliers", method = RequestMethod.GET)
	public String suppliers(final Model model) throws CMSItemNotFoundException
	{


		setUpPageInfo(SUPPLIERS_CMS_PAGE, siteConfigService.getString("fm.aboutus.suppliers", "Suppliers Overview"), null, model,
				SUPPLIERS_URL, siteConfigService.getProperty(SUPPLIERS_KEY));
		return ControllerConstants.Views.Pages.AboutUs.SuppliersLanding;
	}

	@RequestMapping(value = "/suppliers/{componentUid}", method = RequestMethod.GET)
	public String suppliers(final Model model, @PathVariable final String componentUid, @RequestParam final String complnkname,
			@RequestParam(value = "parentCompname", required = false) final String parentCompname,
			@RequestParam(value = "parentComplink", required = false) final String parentComplink) throws CMSItemNotFoundException
	{

		LOG.info("suppliers");
		LOG.info("in controller");
		storeCmsPageInModel(model, getContentPageForLabelOrId(SUPPLIERS_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(SUPPLIERS_CMS_PAGE));
		model.addAttribute("componentUid", XSSFilterUtil.filter(componentUid));
		final List<Breadcrumb> breadCrumb = new ArrayList<Breadcrumb>();
		if (parentComplink != null && parentCompname != null)
		{
			breadCrumb.addAll(aboutUsBreadcrumbBuilder.createSuppliersBreadCrumbs(XSSFilterUtil.filter(parentCompname),
					XSSFilterUtil.filter(parentComplink)));
			breadCrumb.addAll(aboutUsBreadcrumbBuilder.createParentBreadCrumbs(XSSFilterUtil.filter(componentUid),
					XSSFilterUtil.filter(complnkname), XSSFilterUtil.filter(parentCompname), XSSFilterUtil.filter(parentComplink)));
			model.addAttribute("breadcrumbs", breadCrumb);
		}
		else
		{
			model.addAttribute(
					"breadcrumbs",
					aboutUsBreadcrumbBuilder.createSuppliersBreadCrumbs(XSSFilterUtil.filter(componentUid),
							XSSFilterUtil.filter(complnkname)));
		}


		model.addAttribute("metaRobots", siteConfigService.getString("fm.http.metaRobots.index", "no-index,no-follow"));

		return ControllerConstants.Views.Pages.AboutUs.SuppliersLanding;
	}

	@RequestMapping(value = "/privacy-legal", method = RequestMethod.GET)
	public String privacy(final Model model) throws CMSItemNotFoundException
	{


		setUpPageInfo(PRIVACY_CMS_PAGE, siteConfigService.getString("fm.aboutus.privacy", "Privacy and Terms"), null, model,
				PRIVACY_URL, siteConfigService.getProperty(PRIVACY_KEY));
		return ControllerConstants.Views.Pages.AboutUs.PrivacyLegalLanding;
	}

	@RequestMapping(value = "/privacy-legal/{componentUid}", method = RequestMethod.GET)
	public String privacy(final Model model, @PathVariable final String componentUid, @RequestParam final String complnkname)
			throws CMSItemNotFoundException
	{


		model.addAttribute("componentUid", XSSFilterUtil.filter(componentUid));
		setUpPageInfo(PRIVACY_CMS_PAGE, XSSFilterUtil.filter(componentUid), XSSFilterUtil.filter(complnkname), model, PRIVACY_URL,
				siteConfigService.getProperty(PRIVACY_KEY));

		return ControllerConstants.Views.Pages.AboutUs.PrivacyLegalLanding;
	}

	@RequestMapping(value = "/executive", method = RequestMethod.GET)
	public String executive(final Model model) throws CMSItemNotFoundException
	{

		setUpPageInfo(EXECUTIVE_CMS_PAGE, siteConfigService.getString("fm.aboutus.executive", "Search Jobs"), null, model,
				EXECUTIVE_URL, siteConfigService.getProperty(EXECUTIVE_KEY));
		return ControllerConstants.Views.Pages.AboutUs.ExecutiveLanding;
	}

	@RequestMapping(value = "/executive/{componentUid}", method = RequestMethod.GET)
	public String executive(final Model model, @PathVariable final String componentUid, @RequestParam final String complnkname)
			throws CMSItemNotFoundException
	{


		model.addAttribute("componentUid", XSSFilterUtil.filter(componentUid));
		setUpPageInfo(EXECUTIVE_CMS_PAGE, XSSFilterUtil.filter(componentUid), XSSFilterUtil.filter(complnkname), model,
				EXECUTIVE_URL, siteConfigService.getProperty(EXECUTIVE_KEY));

		return ControllerConstants.Views.Pages.AboutUs.ExecutiveLanding;
	}

	@RequestMapping(value = "/media", method = RequestMethod.GET)
	public String media(final Model model) throws CMSItemNotFoundException
	{


		setUpPageInfo(MEDIA_CMS_PAGE, siteConfigService.getString("fm.aboutus.media", "Overview"), null, model, MEDIA_URL,
				siteConfigService.getProperty(MEDIA_KEY));

		return ControllerConstants.Views.Pages.AboutUs.MediaLanding;
	}

	@RequestMapping(value = "/media/{componentUid}", method = RequestMethod.GET)
	public String media(final Model model, @PathVariable final String componentUid, @RequestParam final String complnkname)
			throws CMSItemNotFoundException
	{


		model.addAttribute("componentUid", XSSFilterUtil.filter(componentUid));
		setUpPageInfo(MEDIA_CMS_PAGE, XSSFilterUtil.filter(componentUid), XSSFilterUtil.filter(complnkname), model, MEDIA_URL,
				siteConfigService.getProperty(MEDIA_KEY));

		return ControllerConstants.Views.Pages.AboutUs.MediaLanding;
	}

	private void setUpPageInfo(final String pageId, final String compUid, final String compLnkName, final Model model,
			final String url, final String msg) throws CMSItemNotFoundException
	{

		storeCmsPageInModel(model, getContentPageForLabelOrId(pageId));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(pageId));

		model.addAttribute("breadcrumbs", aboutUsBreadcrumbBuilder.getBreadcrumbs(XSSFilterUtil.filter(compUid), url, msg,
				XSSFilterUtil.filter(compUid), XSSFilterUtil.filter(compLnkName)));


		model.addAttribute("metaRobots", siteConfigService.getString("fm.http.metaRobots.index", "no-index,no-follow"));

	}
}
