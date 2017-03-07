/**
 * 
 */
package com.federalmogul.storefront.controllers.pages;

import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.federalmogul.storefront.breadcrumb.impl.LearningCenterBreadcrumbBuilder;
import com.federalmogul.storefront.breadcrumb.impl.LoyaltyHomeBreadcrumbBuilder;
import com.federalmogul.storefront.breadcrumb.impl.MyCompanyBreadcrumbBuilder;
import com.federalmogul.storefront.controllers.ControllerConstants;


/**
 * @author SU276498
 * 
 */
@Controller
@Scope("tenant")
@RequestMapping(value = "/**/lc")
public class LearningCenterPageController extends AbstractPageController
{
	@Resource(name = "myCompanyBreadcrumbBuilder")
	protected MyCompanyBreadcrumbBuilder myCompanyBreadcrumbBuilder;

	private static final String LEARNING_CMS_PAGE = "LearningCenterPage";
	private static final String TRAINING_OPTIONS_CMS_PAGE = "TrainingOptionsLandingPage";
	private static final String COURSES_CMS_PAGE = "CoursesLandingPage";
	private static final String TECH_TIPS_CMS_PAGE = "TechTipsLandingPage";
	private static final String VIDEO_GALLERY_CMS_PAGE = "VideoGalleryLandingPage";
	private static final String DIAGNOSTIC_CENTER_CMS_PAGE = "DiagnosticCenterLandingPage";
	private static final String BULLETINS_CMS_PAGE = "BulletinsLandingPage";
	private static final String CATALOGS_CMS_PAGE = "CatalogsLandingPage";
	private static final String SPEC_LOOKUP_CMS_PAGE = "SpecLookupLandingPage";
	private static final String CALCULATORS_CMS_PAGE = "CalculatorsLandingPage";

	protected static final String FM_ANONYMOUS_GARAGE_REWARDS_PAGE = "FMAnonymousRewardsAboutPage";

	@Resource(name = "loyaltyBreadcrumbBuilder")
	protected LoyaltyHomeBreadcrumbBuilder loyaltyBreadcrumbBuilder;

	private static final Logger LOG = Logger.getLogger(LearningCenterPageController.class);

	@Resource(name = "learningCenterBreadcrumbBuilder")
	protected LearningCenterBreadcrumbBuilder learningCenterBreadcrumbBuilder;


	@RequestMapping(value = "/learningCenter", method = RequestMethod.GET)
	public String learningCenter(final Model model) throws CMSItemNotFoundException
	{

		LOG.info("Learning Center Landing");
		model.addAttribute("breadcrumbs", learningCenterBreadcrumbBuilder.getBreadcrumbs("Learning Center Overview"));
		model.addAttribute("metaRobots", "no-index,no-follow");
		storeCmsPageInModel(model, getContentPageForLabelOrId(LEARNING_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(LEARNING_CMS_PAGE));

		return ControllerConstants.Views.Pages.Learning.LearningCenter;
	}

	@RequestMapping(value = "/training-options", method = RequestMethod.GET)
	public String trainingOptions(final Model model) throws CMSItemNotFoundException
	{

		model.addAttribute("breadcrumbs", learningCenterBreadcrumbBuilder.getTrainingOptionsBreadcrumbs("Training Options"));
		model.addAttribute("metaRobots", "no-index,no-follow");
		storeCmsPageInModel(model, getContentPageForLabelOrId(TRAINING_OPTIONS_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(TRAINING_OPTIONS_CMS_PAGE));

		return ControllerConstants.Views.Pages.Learning.TrainingOptions;
	}

	@RequestMapping(value = "/training-options/{componentUid}", method = RequestMethod.GET)
	public String learningCenter(final Model model, @PathVariable final String componentUid, @RequestParam final String complnkname)
			throws CMSItemNotFoundException
	{

		storeCmsPageInModel(model, getContentPageForLabelOrId(TRAINING_OPTIONS_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(TRAINING_OPTIONS_CMS_PAGE));
		model.addAttribute("componentUid", componentUid);
		model.addAttribute("breadcrumbs",
				learningCenterBreadcrumbBuilder.createTrainingOptionsBreadCrumbs(componentUid, complnkname));
		model.addAttribute("metaRobots", "no-index,no-follow");


		return ControllerConstants.Views.Pages.Learning.TrainingOptions;
	}

	@RequestMapping(value = "/courses", method = RequestMethod.GET)
	public String courses(final Model model) throws CMSItemNotFoundException
	{

		model.addAttribute("breadcrumbs", learningCenterBreadcrumbBuilder.getCoursesBreadcrumbs("Courses"));
		model.addAttribute("metaRobots", "no-index,no-follow");
		storeCmsPageInModel(model, getContentPageForLabelOrId(COURSES_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(COURSES_CMS_PAGE));

		return ControllerConstants.Views.Pages.Learning.Courses;
	}

	@RequestMapping(value = "/courses/{componentUid}", method = RequestMethod.GET)
	public String courses(final Model model, @PathVariable final String componentUid, @RequestParam final String complnkname)
			throws CMSItemNotFoundException
	{

		LOG.info("news");
		LOG.info("in controller");
		storeCmsPageInModel(model, getContentPageForLabelOrId(COURSES_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(COURSES_CMS_PAGE));
		model.addAttribute("componentUid", componentUid);
		model.addAttribute("breadcrumbs", learningCenterBreadcrumbBuilder.createCoursesBreadCrumbs(componentUid, complnkname));
		model.addAttribute("metaRobots", "no-index,no-follow");

		return ControllerConstants.Views.Pages.Learning.Courses;
	}


	@RequestMapping(value = "/tech-tips", method = RequestMethod.GET)
	public String techTips(final Model model) throws CMSItemNotFoundException
	{

		model.addAttribute("breadcrumbs", learningCenterBreadcrumbBuilder.getTechTipsBreadcrumbs("Tech Tips"));
		model.addAttribute("metaRobots", "no-index,no-follow");
		storeCmsPageInModel(model, getContentPageForLabelOrId(TECH_TIPS_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(TECH_TIPS_CMS_PAGE));

		return ControllerConstants.Views.Pages.Learning.TechTips;
	}

	@RequestMapping(value = "/tech-tips/{componentUid}", method = RequestMethod.GET)
	public String techTips(final Model model, @PathVariable final String componentUid, @RequestParam final String complnkname)
			throws CMSItemNotFoundException
	{

		model.addAttribute("breadcrumbs", myCompanyBreadcrumbBuilder.getBreadcrumbs(null));
		model.addAttribute("metaRobots", "no-index,no-follow");
		model.addAttribute("componentUid", componentUid);
		model.addAttribute("breadcrumbs", learningCenterBreadcrumbBuilder.createTechTipsBreadCrumbs(componentUid, complnkname));
		storeCmsPageInModel(model, getContentPageForLabelOrId(TECH_TIPS_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(TECH_TIPS_CMS_PAGE));

		return ControllerConstants.Views.Pages.Learning.TechTips;
	}

	@RequestMapping(value = "/video-gallery", method = RequestMethod.GET)
	public String videoGallery(final Model model) throws CMSItemNotFoundException
	{

		model.addAttribute("breadcrumbs", learningCenterBreadcrumbBuilder.getVideoGalleryBreadcrumbs("Video Gallery"));
		model.addAttribute("metaRobots", "no-index,no-follow");
		storeCmsPageInModel(model, getContentPageForLabelOrId(VIDEO_GALLERY_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(VIDEO_GALLERY_CMS_PAGE));

		return ControllerConstants.Views.Pages.Learning.VideoGallery;
	}

	@RequestMapping(value = "/video-gallery/{componentUid}", method = RequestMethod.GET)
	public String videoGallery(final Model model, @PathVariable final String componentUid, @RequestParam final String complnkname)
			throws CMSItemNotFoundException
	{

		storeCmsPageInModel(model, getContentPageForLabelOrId(VIDEO_GALLERY_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(VIDEO_GALLERY_CMS_PAGE));
		model.addAttribute("componentUid", componentUid);
		model.addAttribute("breadcrumbs", learningCenterBreadcrumbBuilder.createVideoGalleryBreadCrumbs(componentUid, complnkname));
		model.addAttribute("metaRobots", "no-index,no-follow");

		return ControllerConstants.Views.Pages.Learning.VideoGallery;
	}


	@RequestMapping(value = "/diagnostic-center", method = RequestMethod.GET)
	public String diagnosticCenter(final Model model) throws CMSItemNotFoundException
	{

		model.addAttribute("breadcrumbs", learningCenterBreadcrumbBuilder.getDiagnosticCenterBreadcrumbs("Diagnostic Center"));
		model.addAttribute("metaRobots", "no-index,no-follow");
		storeCmsPageInModel(model, getContentPageForLabelOrId(DIAGNOSTIC_CENTER_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(DIAGNOSTIC_CENTER_CMS_PAGE));

		return ControllerConstants.Views.Pages.Learning.DiagnosticCenter;
	}

	@RequestMapping(value = "/diagnostic-center/{componentUid}", method = RequestMethod.GET)
	public String diagnosticCenter(final Model model, @PathVariable final String componentUid,
			@RequestParam final String complnkname) throws CMSItemNotFoundException
	{

		storeCmsPageInModel(model, getContentPageForLabelOrId(DIAGNOSTIC_CENTER_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(DIAGNOSTIC_CENTER_CMS_PAGE));
		model.addAttribute("componentUid", componentUid);
		model.addAttribute("breadcrumbs",
				learningCenterBreadcrumbBuilder.createDiagnosticCenterBreadCrumbs(componentUid, complnkname));
		model.addAttribute("metaRobots", "no-index,no-follow");

		return ControllerConstants.Views.Pages.Learning.DiagnosticCenter;
	}

	@RequestMapping(value = "/bulletins", method = RequestMethod.GET)
	public String bulletins(final Model model) throws CMSItemNotFoundException
	{

		model.addAttribute("breadcrumbs", learningCenterBreadcrumbBuilder.getBulletinsBreadcrumbs("Bulletins"));
		model.addAttribute("metaRobots", "no-index,no-follow");
		storeCmsPageInModel(model, getContentPageForLabelOrId(BULLETINS_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(BULLETINS_CMS_PAGE));

		return ControllerConstants.Views.Pages.Learning.Bulletins;
	}

	@RequestMapping(value = "/bulletins/{componentUid}", method = RequestMethod.GET)
	public String bulletins(final Model model, @PathVariable final String componentUid, @RequestParam final String complnkname)
			throws CMSItemNotFoundException
	{

		storeCmsPageInModel(model, getContentPageForLabelOrId(BULLETINS_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(BULLETINS_CMS_PAGE));
		model.addAttribute("componentUid", componentUid);
		model.addAttribute("breadcrumbs", learningCenterBreadcrumbBuilder.createBulletinsBreadCrumbs(componentUid, complnkname));
		model.addAttribute("metaRobots", "no-index,no-follow");

		return ControllerConstants.Views.Pages.Learning.Bulletins;
	}

	@RequestMapping(value = "/catalogs", method = RequestMethod.GET)
	public String catalogs(final Model model) throws CMSItemNotFoundException
	{

		model.addAttribute("breadcrumbs", learningCenterBreadcrumbBuilder.getCatalogsBreadcrumbs("Catalogs"));
		model.addAttribute("metaRobots", "no-index,no-follow");
		storeCmsPageInModel(model, getContentPageForLabelOrId(CATALOGS_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(CATALOGS_CMS_PAGE));

		return ControllerConstants.Views.Pages.Learning.Catalogs;
	}

	@RequestMapping(value = "/catalogs/{componentUid}", method = RequestMethod.GET)
	public String catalogs(final Model model, @PathVariable final String componentUid, @RequestParam final String complnkname)
			throws CMSItemNotFoundException
	{

		storeCmsPageInModel(model, getContentPageForLabelOrId(CATALOGS_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(CATALOGS_CMS_PAGE));
		model.addAttribute("componentUid", componentUid);
		model.addAttribute("breadcrumbs", learningCenterBreadcrumbBuilder.createCatalogsBreadCrumbs(componentUid, complnkname));
		model.addAttribute("metaRobots", "no-index,no-follow");

		return ControllerConstants.Views.Pages.Learning.Catalogs;
	}

	@RequestMapping(value = "/speclookup", method = RequestMethod.GET)
	public String specLookup(final Model model) throws CMSItemNotFoundException
	{

		model.addAttribute("breadcrumbs", learningCenterBreadcrumbBuilder.getSpeclookUpBreadcrumbs("Specification LookUp"));
		model.addAttribute("metaRobots", "no-index,no-follow");
		storeCmsPageInModel(model, getContentPageForLabelOrId(SPEC_LOOKUP_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(SPEC_LOOKUP_CMS_PAGE));

		return ControllerConstants.Views.Pages.Learning.SpecLookup;
	}

	@RequestMapping(value = "/speclookup/{componentUid}", method = RequestMethod.GET)
	public String specLookup(final Model model, @PathVariable final String componentUid, @RequestParam final String complnkname)
			throws CMSItemNotFoundException
	{

		storeCmsPageInModel(model, getContentPageForLabelOrId(SPEC_LOOKUP_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(SPEC_LOOKUP_CMS_PAGE));
		model.addAttribute("componentUid", componentUid);
		model.addAttribute("breadcrumbs", learningCenterBreadcrumbBuilder.createSpecLookUpBreadCrumbs(componentUid, complnkname));
		model.addAttribute("metaRobots", "no-index,no-follow");

		return ControllerConstants.Views.Pages.Learning.SpecLookup;
	}

	@RequestMapping(value = "/calculators", method = RequestMethod.GET)
	public String calculators(final Model model) throws CMSItemNotFoundException
	{

		model.addAttribute("breadcrumbs", learningCenterBreadcrumbBuilder.getCalculatorsBreadcrumbs("Calculators"));
		model.addAttribute("metaRobots", "no-index,no-follow");
		storeCmsPageInModel(model, getContentPageForLabelOrId(CALCULATORS_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(CALCULATORS_CMS_PAGE));

		return ControllerConstants.Views.Pages.Learning.Calculators;
	}

	@RequestMapping(value = "/calculators/{componentUid}", method = RequestMethod.GET)
	public String calculators(final Model model, @PathVariable final String componentUid, @RequestParam final String complnkname)
			throws CMSItemNotFoundException
	{

		storeCmsPageInModel(model, getContentPageForLabelOrId(CALCULATORS_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(CALCULATORS_CMS_PAGE));
		model.addAttribute("componentUid", componentUid);
		model.addAttribute("breadcrumbs", learningCenterBreadcrumbBuilder.createCalculatorsBreadCrumbs(componentUid, complnkname));
		model.addAttribute("metaRobots", "no-index,no-follow");

		return ControllerConstants.Views.Pages.Learning.Calculators;
	}

	/**
	 * Added by Sai For Anonymous User About GarageRewards Page Under Learning Center
	 * 
	 * @param model
	 * @return
	 * @throws CMSItemNotFoundException
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/aboutRewards")
	public String getAboutGarageRewards(final Model model) throws CMSItemNotFoundException
	{
		LOG.info("INSIDE getAboutGarageRewards");
		storeCmsPageInModel(model, getContentPageForLabelOrId(FM_ANONYMOUS_GARAGE_REWARDS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FM_ANONYMOUS_GARAGE_REWARDS_PAGE));
		model.addAttribute("breadcrumbs", learningCenterBreadcrumbBuilder.getAboutRewardsBreadcrumbs("Garage Rewards"));
		model.addAttribute("metaRobots", "no-index,no-follow");
		return ControllerConstants.Views.Pages.Account.Rewardsaboutpage;
	}

}
