package com.federalmogul.storefront.controllers.pages;

import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.federalmogul.storefront.breadcrumb.impl.BrandBreadcrumbBuilder;
import com.federalmogul.storefront.controllers.ControllerConstants;


@Controller
@Scope("tenant")
@RequestMapping(value = "/**/brands")
public class BrandLandingController extends AbstractPageController
{
	private static final String BRAND_LANDING_CMS_PAGE = "BrandLandingPage";
	private static final String ABEX_LANDING_CMS_PAGE = "AbexLandingPage";
	private static final String ANCO_LANDING_CMS_PAGE = "AncoLandingPage";
	private static final String FELPRO_LANDING_CMS_PAGE = "FelproLandingPage";
	private static final String FPD_LANDING_CMS_PAGE = "FPDLandingPage";
	private static final String MOOG_LANDING_CMS_PAGE = "MoogLandingPage";
	private static final String SEALEDPR_LANDING_CMS_PAGE = "SealedPRLandingPage";
	private static final String SPEEDPRO_LANDING_CMS_PAGE = "SpeedproLandingPage";
	private static final String WAGNERBP_LANDING_CMS_PAGE = "WagnerBPLandingPage";
	private static final String NATIONAL_LANDING_CMS_PAGE = "NationalLandingPage";
	private static final String WAGNERLIGHTING_LANDING_CMS_PAGE = "WagnerLightingLandingPage";
	private static final String BECKARNLEY_LANDING_CMS_PAGE = "BeckArnleyLandingPage";

	private static final String FERODO_LANDING_CMS_PAGE = "FerodoLandingPage";
	private static final Logger LOG = Logger.getLogger(BrandLandingController.class);



	@Resource(name = "brandBreadcrumbBuilder")
	protected BrandBreadcrumbBuilder brandBreadcrumbBuilder;

	@RequestMapping(value = "/champion", method = RequestMethod.GET)
	public String brandlanding(final Model model) throws CMSItemNotFoundException
	{

		LOG.info("Brand Landing");
		model.addAttribute("breadcrumbs", brandBreadcrumbBuilder.getChampionBreadcrumbs(null));
		model.addAttribute("metaRobots", "no-index,no-follow");
		storeCmsPageInModel(model, getContentPageForLabelOrId(BRAND_LANDING_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(BRAND_LANDING_CMS_PAGE));

		return ControllerConstants.Views.Pages.Brand.BrandLanding;
	}


	@RequestMapping(value = "/abex", method = RequestMethod.GET)
	public String abexlanding(final Model model) throws CMSItemNotFoundException
	{

		LOG.info("Abex Landing");
		model.addAttribute("breadcrumbs", brandBreadcrumbBuilder.getAbexBreadcrumbs(""));
		model.addAttribute("metaRobots", "no-index,no-follow");
		storeCmsPageInModel(model, getContentPageForLabelOrId(ABEX_LANDING_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ABEX_LANDING_CMS_PAGE));

		return ControllerConstants.Views.Pages.Brand.AbexLanding;
	}

	@RequestMapping(value = "/anco", method = RequestMethod.GET)
	public String ancolanding(final Model model) throws CMSItemNotFoundException
	{

		LOG.info("Anco Landing");
		model.addAttribute("breadcrumbs", brandBreadcrumbBuilder.getAncoBreadcrumbs(""));
		model.addAttribute("metaRobots", "no-index,no-follow");
		storeCmsPageInModel(model, getContentPageForLabelOrId(ANCO_LANDING_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(ANCO_LANDING_CMS_PAGE));

		return ControllerConstants.Views.Pages.Brand.AncoLanding;
	}

	@RequestMapping(value = "/fel-pro", method = RequestMethod.GET)
	public String felprolanding(final Model model) throws CMSItemNotFoundException
	{


		LOG.info("Felpro Landing");
		model.addAttribute("breadcrumbs", brandBreadcrumbBuilder.getFelproBreadcrumbs(null));
		model.addAttribute("metaRobots", "no-index,no-follow");
		storeCmsPageInModel(model, getContentPageForLabelOrId(FELPRO_LANDING_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FELPRO_LANDING_CMS_PAGE));

		return ControllerConstants.Views.Pages.Brand.FelproLanding;
	}

	@RequestMapping(value = "/fp-diesel", method = RequestMethod.GET)
	public String fpdLanding(final Model model) throws CMSItemNotFoundException
	{


		LOG.info("FPD Landing");
		model.addAttribute("breadcrumbs", brandBreadcrumbBuilder.getFPDieselBreadcrumbs(null));
		model.addAttribute("metaRobots", "no-index,no-follow");
		storeCmsPageInModel(model, getContentPageForLabelOrId(FPD_LANDING_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FPD_LANDING_CMS_PAGE));

		return ControllerConstants.Views.Pages.Brand.FPDLanding;
	}

	@RequestMapping(value = "/moog", method = RequestMethod.GET)
	public String mooglanding(final Model model) throws CMSItemNotFoundException
	{


		LOG.info("Moog Landing");
		model.addAttribute("breadcrumbs", brandBreadcrumbBuilder.getMoogBreadcrumbs(null));
		model.addAttribute("metaRobots", "no-index,no-follow");
		storeCmsPageInModel(model, getContentPageForLabelOrId(MOOG_LANDING_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(MOOG_LANDING_CMS_PAGE));

		return ControllerConstants.Views.Pages.Brand.MoogLanding;
	}

	@RequestMapping(value = "/sealed-power", method = RequestMethod.GET)
	public String sealedPRlanding(final Model model) throws CMSItemNotFoundException
	{


		LOG.info("SealedPR Landing");
		model.addAttribute("breadcrumbs", brandBreadcrumbBuilder.getSealedPowerBreadcrumbs(null));
		model.addAttribute("metaRobots", "no-index,no-follow");
		storeCmsPageInModel(model, getContentPageForLabelOrId(SEALEDPR_LANDING_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(SEALEDPR_LANDING_CMS_PAGE));

		return ControllerConstants.Views.Pages.Brand.SealedLanding;
	}

	@RequestMapping(value = "/speed-pro", method = RequestMethod.GET)
	public String speedprolanding(final Model model) throws CMSItemNotFoundException
	{


		LOG.info("Speedpro Landing");
		model.addAttribute("breadcrumbs", brandBreadcrumbBuilder.getSpeedproBreadcrumbs(null));
		model.addAttribute("metaRobots", "no-index,no-follow");
		storeCmsPageInModel(model, getContentPageForLabelOrId(SPEEDPRO_LANDING_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(SPEEDPRO_LANDING_CMS_PAGE));

		return ControllerConstants.Views.Pages.Brand.SpeedproLanding;
	}

	@RequestMapping(value = "/wagner", method = RequestMethod.GET)
	public String wagnerBPlanding(final Model model) throws CMSItemNotFoundException
	{


		LOG.info("wagnerbrake Landing");
		model.addAttribute("breadcrumbs", brandBreadcrumbBuilder.getWagnerBrakeBreadcrumbs(null));
		model.addAttribute("metaRobots", "no-index,no-follow");
		storeCmsPageInModel(model, getContentPageForLabelOrId(WAGNERBP_LANDING_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(WAGNERBP_LANDING_CMS_PAGE));

		return ControllerConstants.Views.Pages.Brand.WagnerBPLanding;
	}

	@RequestMapping(value = "/national", method = RequestMethod.GET)
	public String nationalBrandlanding(final Model model) throws CMSItemNotFoundException
	{


		LOG.info("wagnerbrake Landing");
		model.addAttribute("breadcrumbs", brandBreadcrumbBuilder.getNationalBreadcrumbs(null));
		model.addAttribute("metaRobots", "no-index,no-follow");
		storeCmsPageInModel(model, getContentPageForLabelOrId(NATIONAL_LANDING_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(NATIONAL_LANDING_CMS_PAGE));

		return ControllerConstants.Views.Pages.Brand.NationalLanding;
	}

	@RequestMapping(value = "/ferodo", method = RequestMethod.GET)
	public String ferodoBrandlanding(final Model model) throws CMSItemNotFoundException
	{


		LOG.info("Ferodo Landing");
		model.addAttribute("breadcrumbs", brandBreadcrumbBuilder.getFerodoBreadcrumbs(null));
		model.addAttribute("metaRobots", "no-index,no-follow");
		storeCmsPageInModel(model, getContentPageForLabelOrId(FERODO_LANDING_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FERODO_LANDING_CMS_PAGE));

		return ControllerConstants.Views.Pages.Brand.FerodoLanding;
	}


	@RequestMapping(value = "/wagnerLighting", method = RequestMethod.GET)
	public String wagnerLightinglanding(final Model model) throws CMSItemNotFoundException
	{


		LOG.info("wagnerlighting Landing");
		model.addAttribute("breadcrumbs", brandBreadcrumbBuilder.getWagnerLightingBreadcrumbs(null));
		model.addAttribute("metaRobots", "no-index,no-follow");
		storeCmsPageInModel(model, getContentPageForLabelOrId(WAGNERLIGHTING_LANDING_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(WAGNERLIGHTING_LANDING_CMS_PAGE));

		return ControllerConstants.Views.Pages.Brand.WagnerLightingLanding;
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String searchpage(final Model model) throws CMSItemNotFoundException

	{
		model.addAttribute("metaRobots", "no-index,no-follow");
		return "kj";
	}

	@RequestMapping(value = "/beckarnley", method = RequestMethod.GET)
	public String beckarnleylanding(final Model model) throws CMSItemNotFoundException
	{

		LOG.info("beckarnley Landing");
		model.addAttribute("breadcrumbs", brandBreadcrumbBuilder.getBeckArnleyBreadcrumbs(""));
		storeCmsPageInModel(model, getContentPageForLabelOrId(BECKARNLEY_LANDING_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(BECKARNLEY_LANDING_CMS_PAGE));

		return ControllerConstants.Views.Pages.Brand.BeckArnleyLanding;
	}

}