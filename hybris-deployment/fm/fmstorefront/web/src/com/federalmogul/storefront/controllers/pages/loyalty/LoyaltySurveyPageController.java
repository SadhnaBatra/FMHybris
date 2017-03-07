/**
 * 
 */
package com.federalmogul.storefront.controllers.pages.loyalty;

import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.core.model.security.PrincipalGroupModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.xml.soap.SOAPException;
import de.hybris.platform.util.Config;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.federalmogul.core.model.FMCustomerModel;
import com.federalmogul.facades.account.FMCustomerFacade;
import com.federalmogul.facades.account.LoyaltySurveyFacade;
import com.federalmogul.facades.network.FMNetworkFacade;
import com.federalmogul.facades.user.data.FMSurveyData;
import com.federalmogul.storefront.breadcrumb.impl.LoyaltyHomeBreadcrumbBuilder;
import com.federalmogul.storefront.constants.WebConstants;
import com.federalmogul.storefront.controllers.ControllerConstants;
import com.federalmogul.storefront.controllers.pages.AbstractPageController;
import com.federalmogul.storefront.controllers.util.GlobalMessages;
import com.federalmogul.storefront.forms.BrandQuestionsForm;
import com.federalmogul.storefront.forms.IntersetAndHobbiesForm;
import com.federalmogul.storefront.forms.PartsBuyingQuestionsForm;
import com.fm.falcon.webservices.dto.LoyaltySurveyResponseDTO;


/**
 * @author sa297584
 * 
 */
@Controller
@RequestMapping(value = "/Survey")
public class LoyaltySurveyPageController extends AbstractPageController
{
	private static final Logger LOG = Logger.getLogger(LoyaltySurveyPageController.class);

	private static final String FM_LOYALTY_AVAILABLE_SURVEYS_PAGE = "loyaltySurvey";
	private static final String FM_LOYALTY_SURVEY_PAGE = "loyaltyBrandSurvey";

	@Autowired
	private FMCustomerFacade fmCustomerFacade;

	@Autowired
	private FMNetworkFacade fmNetworkFacade;

	@Resource(name = "loyaltySurveyFacade")
	LoyaltySurveyFacade loyaltySurveyFacade;

	@Resource(name = "loyaltyBreadcrumbBuilder")
	protected LoyaltyHomeBreadcrumbBuilder loyaltyBreadcrumbBuilder;

	@Resource
	private ModelService modelService;

	@Resource
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET)
	public String getAvailableSurveyPage(final Model model) throws CMSItemNotFoundException
	{
		//model.addAttribute("breadcrumbs", loyaltyBreadcrumbBuilder.getLoyaltySurveyBreadcrumbs(""));
		storeCmsPageInModel(model, getContentPageForLabelOrId(FM_LOYALTY_AVAILABLE_SURVEYS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FM_LOYALTY_AVAILABLE_SURVEYS_PAGE));
		model.addAttribute("TotalPoints", getSessionService().getAttribute(WebConstants.BALANCE_LOYALTY_POINTS));
		model.addAttribute("metaRobots", "no-index,no-follow");
		final FMCustomerModel currentFmuser = (FMCustomerModel) userService.getCurrentUser();
		final String status = currentFmuser.getSurveyStatus();
		LOG.info("STATUS" + status);
		model.addAttribute("surevyStats", status);
		return ControllerConstants.Views.Pages.Account.LoyaltyAvailableSurveysPage;
	}


	@RequestMapping(value = "/BrandSurvey", method = RequestMethod.GET)
	public String getBrandSurveyPage(final Model model) throws CMSItemNotFoundException
	{
		LOG.info("### getBrandSurveyPage #### ");
		model.addAttribute("breadcrumbs", loyaltyBreadcrumbBuilder.getLoyaltySurveyBreadcrumbs("Brand Preference Survey"));
		storeCmsPageInModel(model, getContentPageForLabelOrId(FM_LOYALTY_SURVEY_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FM_LOYALTY_SURVEY_PAGE));
		model.addAttribute("surveyType", "Brand");
		model.addAttribute("metaRobots", "no-index,no-follow");
		model.addAttribute("brandQuestionform", new BrandQuestionsForm());
		model.addAttribute("partsBuyingQuestionsForm", new PartsBuyingQuestionsForm());
		return ControllerConstants.Views.Pages.Account.LoyaltySurveyPage;
	}

	@RequestMapping(value = "/PartsBuyingSurvey", method = RequestMethod.GET)
	public String getBPartsBuyingSurvey(final Model model) throws CMSItemNotFoundException
	{
		LOG.info("### getBPartsBuyingSurvey #### ");
		model.addAttribute("breadcrumbs", loyaltyBreadcrumbBuilder.getLoyaltySurveyBreadcrumbs("Parts Buying Survey"));
		storeCmsPageInModel(model, getContentPageForLabelOrId(FM_LOYALTY_SURVEY_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FM_LOYALTY_SURVEY_PAGE));
		model.addAttribute("metaRobots", "no-index,no-follow");
		model.addAttribute("surveyType", "PartsBuyingSurvey");
		model.addAttribute("brandQuestionform", new BrandQuestionsForm());
		model.addAttribute("partsBuyingQuestionsForm", new PartsBuyingQuestionsForm());
		return ControllerConstants.Views.Pages.Account.LoyaltySurveyPage;
	}

	@RequestMapping(value = "/InterestsandHobbies", method = RequestMethod.GET)
	public String getInterestsandHobbiesSurvey(final Model model) throws CMSItemNotFoundException
	{
		LOG.info("### getBPartsBuyingSurvey #### ");
		model.addAttribute("breadcrumbs", loyaltyBreadcrumbBuilder.getLoyaltySurveyBreadcrumbs("Interests and Hobbies Survey"));
		storeCmsPageInModel(model, getContentPageForLabelOrId(FM_LOYALTY_SURVEY_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FM_LOYALTY_SURVEY_PAGE));
		model.addAttribute("interestAndHobbiesQuestionsForm", new IntersetAndHobbiesForm());
		model.addAttribute("metaRobots", "no-index,no-follow");
		model.addAttribute("surveyType", "InterestsandHobbies");
		return ControllerConstants.Views.Pages.Account.LoyaltySurveyPage;
	}

	@RequestMapping(value = "/updateBrandSurvey", method = RequestMethod.POST)
	public String updateBrandSurvey(final Model model,
			@ModelAttribute("brandQuestionform") final BrandQuestionsForm brandQuestionform) throws CMSItemNotFoundException
	{
		LOG.info("### updateBrandSurvey #### " + brandQuestionform.getFirstQuestionPrimary());
		LOG.info("### updateBrandSurvey2 #### " + brandQuestionform.getFirstQuestionSecondary());
		LOG.info("### updateBrandSurvey2 #### " + brandQuestionform.getFirstQuestionOtherPrimary());
		LOG.info("### updateBrandSurvey2 #### " + brandQuestionform.getFirstQuestionOtherSecondary());
		LOG.info("### updateBrandSurvey2 #### " + brandQuestionform.getThirdQuestionOtherPrimary());
		model.addAttribute("breadcrumbs", loyaltyBreadcrumbBuilder.getLoyaltySurveyCompleteBreadcrumbs("Brand Preference Survey"));
		storeCmsPageInModel(model, getContentPageForLabelOrId(FM_LOYALTY_SURVEY_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FM_LOYALTY_SURVEY_PAGE));
		model.addAttribute("surveyName", "Brand Preference Survey");
		final String result = populatebrandQuestionformData(brandQuestionform);
		if (result != null && result.equalsIgnoreCase("error"))
		{
			model.addAttribute("isSurveyError", new String("true"));
			GlobalMessages.addErrorMessage(model, Config.getParameter("surveyError"));
		}
		if (result != null && result.equalsIgnoreCase("Error in survey"))
		{
			GlobalMessages.addErrorMessage(model, Config.getParameter("surveyError"));
			model.addAttribute("isSurveyError", new String("true"));
		}
		model.addAttribute("metaRobots", "no-index,no-follow");
		//populatebrandQuestionformData(brandQuestionform);
		if (result.equalsIgnoreCase("success"))
		{
			String completedSurveys = fmCustomerFacade.getCurrentFMCustomer().getSurveyStatus();
			LOG.info("SUREVY STATUYS" + completedSurveys);
			completedSurveys = completedSurveys + "/" + "Brand";
			final FMCustomerModel currentFmuser = (FMCustomerModel) userService.getCurrentUser();
			//LOG.info("current users " + currentUser.getFmUnit().getUid());
			currentFmuser.setSurveyStatus(completedSurveys);
			modelService.save(currentFmuser);
		}
		return ControllerConstants.Views.Pages.Account.LoyaltySurveyCompletedPage;
	}

	@RequestMapping(value = "/updateIntersetAndHobbiesSurvey", method = RequestMethod.POST)
	public String updateIntersetAndHobbiesSurvey(final Model model,
			@ModelAttribute("interestAndHobbiesQuestionsForm") final IntersetAndHobbiesForm interestAndHobbiesQuestionsForm)
			throws CMSItemNotFoundException
	{
		model.addAttribute("breadcrumbs",
				loyaltyBreadcrumbBuilder.getLoyaltySurveyCompleteBreadcrumbs("Interests and Hobbies Survey"));
		storeCmsPageInModel(model, getContentPageForLabelOrId(FM_LOYALTY_SURVEY_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FM_LOYALTY_SURVEY_PAGE));
		model.addAttribute("surveyName", "Interests and Hobbies Survey");
		model.addAttribute("metaRobots", "no-index,no-follow");
		final String hobbiesSurveyResult = populateinterestAndHobbiesQuestionformData(interestAndHobbiesQuestionsForm);
		if (hobbiesSurveyResult != null && hobbiesSurveyResult.equalsIgnoreCase("interestSurveyError"))
		{
			model.addAttribute("isSurveyError", new String("true"));
			GlobalMessages.addErrorMessage(model, Config.getParameter("surveyError"));
		}
		if (hobbiesSurveyResult != null && hobbiesSurveyResult.equalsIgnoreCase("Error in survey"))
		{
			model.addAttribute("isSurveyError", new String("true"));
			GlobalMessages.addErrorMessage(model, Config.getParameter("surveyError"));
		}
		if (hobbiesSurveyResult.equalsIgnoreCase("success"))
		{
			String completedSurveys = fmCustomerFacade.getCurrentFMCustomer().getSurveyStatus();
			LOG.info("SUREVY STATUYS" + completedSurveys);
			completedSurveys = completedSurveys + "/" + "IntersetAndHobbies";
			final FMCustomerModel currentFmuser = (FMCustomerModel) userService.getCurrentUser();
			//LOG.info("current users " + currentUser.getFmUnit().getUid());
			currentFmuser.setSurveyStatus(completedSurveys);
			modelService.save(currentFmuser);
		}

		return ControllerConstants.Views.Pages.Account.LoyaltySurveyCompletedPage;
	}

	@SuppressWarnings("null")
	@RequestMapping(value = "/socialMedia", method = RequestMethod.POST)
	public String socialMedia(final Model model) throws CMSItemNotFoundException, UnsupportedOperationException,
			ClassNotFoundException, SOAPException, IOException
	{
		final String pages = "pages/fm/ajax/summaryDetails";
		LoyaltySurveyResponseDTO loyaltySurveyResponseDTO = null;
		final FMSurveyData surveyData = new FMSurveyData();
		final UserModel user = userService.getCurrentUser();
		final List<String> groupUID = new ArrayList<String>();

		final boolean isUserAnonymous = user == null || userService.isAnonymousUser(user);
		if (!isUserAnonymous)
		{
			final Set<PrincipalGroupModel> groupss = user.getGroups();
			for (final PrincipalGroupModel groupModel : groupss)
			{
				final String groupId = groupModel.getUid();
				groupUID.add(groupId);
			}
			if (groupUID.contains("FMB2T"))
			{

				loyaltySurveyResponseDTO = loyaltySurveyFacade.populateSocialMediaData(surveyData);
				if (loyaltySurveyResponseDTO != null && "0".equals(loyaltySurveyResponseDTO.getResponseCode()))
				{
					LOG.info("Social Media Sharing went fine successfully");
					return "success";

				}
				else
				{
					LOG.info("Social Media Sharing failed");
				}
			}
		}
		return pages;

	}

	@RequestMapping(value = "/updatePartsBuySurvey", method = RequestMethod.POST)
	public String updatePartsBuySurvey(final Model model,
			@ModelAttribute("brandQuestionform") final PartsBuyingQuestionsForm partsBuyingQuestionsForm)
			throws CMSItemNotFoundException
	{
		LOG.info("### updateBrandSurvey #### " + partsBuyingQuestionsForm.getQ1Batteries());
		LOG.info("### updateBrandSurvey2 #### " + partsBuyingQuestionsForm.getQ1Brakes());

		model.addAttribute("breadcrumbs", loyaltyBreadcrumbBuilder.getLoyaltySurveyCompleteBreadcrumbs("Parts Buying Survey"));
		storeCmsPageInModel(model, getContentPageForLabelOrId(FM_LOYALTY_SURVEY_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FM_LOYALTY_SURVEY_PAGE));
		model.addAttribute("surveyName", "Parts Buying Survey");
		model.addAttribute("metaRobots", "no-index,no-follow");
		final String buyingResult = populatepartsBuyingformData(partsBuyingQuestionsForm);
		if (buyingResult != null && buyingResult.equalsIgnoreCase("buyingSurveyError"))
		{
			model.addAttribute("isSurveyError", new String("true"));
			GlobalMessages.addErrorMessage(model, Config.getParameter("surveyError"));
		}
		if (buyingResult != null && buyingResult.equalsIgnoreCase("Error in survey"))
		{
			model.addAttribute("isSurveyError", new String("true"));
			GlobalMessages.addErrorMessage(model, Config.getParameter("surveyError"));
		}
		if (buyingResult.equalsIgnoreCase("success"))
		{
			String completedSurveys = fmCustomerFacade.getCurrentFMCustomer().getSurveyStatus();
			LOG.info("SUREVY STATUYS" + completedSurveys);
			completedSurveys = completedSurveys + "/" + "Parts";
			final FMCustomerModel currentFmuser = (FMCustomerModel) userService.getCurrentUser();
			//LOG.info("current users " + currentUser.getFmUnit().getUid());
			currentFmuser.setSurveyStatus(completedSurveys);
			modelService.save(currentFmuser);
		}

		return ControllerConstants.Views.Pages.Account.LoyaltySurveyCompletedPage;
	}


	public String populateinterestAndHobbiesQuestionformData(final IntersetAndHobbiesForm interestAndHobbiesForm)
	{


		final List<String> uniqueId = new ArrayList<String>();
		if (interestAndHobbiesForm.getFirstQAge() != null)
		{
			if (!(uniqueId.contains(interestAndHobbiesForm.getFirstQAge())))
			{
				uniqueId.add(interestAndHobbiesForm.getFirstQAge());
			}
		}
		if (interestAndHobbiesForm.getSecondQBirthMonth() != null)
		{
			if (!(uniqueId.contains(interestAndHobbiesForm.getSecondQBirthMonth())))
			{
				uniqueId.add(interestAndHobbiesForm.getSecondQBirthMonth());
			}
		}
		if (interestAndHobbiesForm.getThirdQGender() != null)
		{

			if (!(uniqueId.contains(interestAndHobbiesForm.getThirdQGender())))
			{
				uniqueId.add(interestAndHobbiesForm.getThirdQGender());
			}
		}
		if (interestAndHobbiesForm.getFourthQEthnicity() != null)
		{

			if (!(uniqueId.contains(interestAndHobbiesForm.getFourthQEthnicity())))
			{
				uniqueId.add(interestAndHobbiesForm.getFourthQEthnicity());
			}
		}
		if (interestAndHobbiesForm.getFifthQTechExperience() != null)
		{

			if (!(uniqueId.contains(interestAndHobbiesForm.getFifthQTechExperience())))
			{
				uniqueId.add(interestAndHobbiesForm.getFifthQTechExperience());
			}
		}
		if (interestAndHobbiesForm.getSixthQRegularActivities() != null
				&& interestAndHobbiesForm.getSixthQRegularActivities().size() > 0)
		{
			for (final String activities : interestAndHobbiesForm.getSixthQRegularActivities())
			{

				if (null != activities)
				{
					if (!(uniqueId.contains(activities)))
					{
						uniqueId.add(activities);
					}
				}
			}

		}
		if (interestAndHobbiesForm.getSixthQRegularActivitiesOther() != null)
		{

			if (!(uniqueId.contains("1057")))
			{
				uniqueId.add("1057");
			}
		}
		if (interestAndHobbiesForm.getSeventhQMotorSportsTimeNascar() != null)
		{
			LOG.info("selected function" + interestAndHobbiesForm.getSeventhQMotorSportsTimeNascar());
			if (selected(interestAndHobbiesForm.getSeventhQMotorSportsTimeNascar()))
			{
				LOG.info("inside true case");
				if (!(uniqueId.contains(interestAndHobbiesForm.getSeventhQMotorSportsTimeNascar())))
				{
					uniqueId.add(interestAndHobbiesForm.getSeventhQMotorSportsTimeNascar());
				}

			}
		}
		if (interestAndHobbiesForm.getSeventhQMotorSportsTimeFormula1() != null)
		{
			if (selected(interestAndHobbiesForm.getSeventhQMotorSportsTimeFormula1()))
			{
				if (!(uniqueId.contains(interestAndHobbiesForm.getSeventhQMotorSportsTimeFormula1())))
				{
					uniqueId.add(interestAndHobbiesForm.getSeventhQMotorSportsTimeFormula1());
				}
			}

		}
		if (interestAndHobbiesForm.getSeventhQMotorSportsTimeDragRacing() != null)
		{
			if (selected(interestAndHobbiesForm.getSeventhQMotorSportsTimeDragRacing()))
			{
				if (!(uniqueId.contains(interestAndHobbiesForm.getSeventhQMotorSportsTimeDragRacing())))
				{
					uniqueId.add(interestAndHobbiesForm.getSeventhQMotorSportsTimeDragRacing());
				}

			}
		}
		if (interestAndHobbiesForm.getSeventhQMotorSportsTimeMotocross() != null)
		{
			if (selected(interestAndHobbiesForm.getSeventhQMotorSportsTimeMotocross()))
			{
				if (!(uniqueId.contains(interestAndHobbiesForm.getSeventhQMotorSportsTimeMotocross())))
				{
					uniqueId.add(interestAndHobbiesForm.getSeventhQMotorSportsTimeMotocross());
				}
			}

		}
		if (interestAndHobbiesForm.getSeventhQMotorSportsTimeSupercross() != null)
		{
			if (selected(interestAndHobbiesForm.getSeventhQMotorSportsTimeSupercross()))
			{
				if (!(uniqueId.contains(interestAndHobbiesForm.getSeventhQMotorSportsTimeSupercross())))
				{
					uniqueId.add(interestAndHobbiesForm.getSeventhQMotorSportsTimeSupercross());
				}
			}
		}


		if (interestAndHobbiesForm.getSeventhQMotorSportsTimeDrifting() != null)
		{
			if (selected(interestAndHobbiesForm.getSeventhQMotorSportsTimeDrifting()))
			{
				if (!(uniqueId.contains(interestAndHobbiesForm.getSeventhQMotorSportsTimeDrifting())))
				{
					uniqueId.add(interestAndHobbiesForm.getSeventhQMotorSportsTimeDrifting());
				}
			}
		}
		if (interestAndHobbiesForm.getSeventhQMotorSportsTimeRally() != null)
		{
			if (selected(interestAndHobbiesForm.getSeventhQMotorSportsTimeRally()))
			{
				if (!(uniqueId.contains(interestAndHobbiesForm.getSeventhQMotorSportsTimeRally())))
				{
					uniqueId.add(interestAndHobbiesForm.getSeventhQMotorSportsTimeRally());
				}
			}
		}
		if (interestAndHobbiesForm.getEightQProfessionalSportsTimeFootball() != null)
		{
			if (selected(interestAndHobbiesForm.getEightQProfessionalSportsTimeFootball()))
			{
				if (!(uniqueId.contains(interestAndHobbiesForm.getEightQProfessionalSportsTimeFootball())))
				{
					uniqueId.add(interestAndHobbiesForm.getEightQProfessionalSportsTimeFootball());
				}
			}

		}
		if (interestAndHobbiesForm.getEightQProfessionalSportsTimeBasketball() != null)
		{
			if (selected(interestAndHobbiesForm.getEightQProfessionalSportsTimeBasketball()))
			{
				if (!(uniqueId.contains(interestAndHobbiesForm.getEightQProfessionalSportsTimeBasketball())))
				{
					uniqueId.add(interestAndHobbiesForm.getEightQProfessionalSportsTimeBasketball());
				}
			}

		}
		if (interestAndHobbiesForm.getEightQProfessionalSportsTimeBaseball() != null)
		{
			if (selected(interestAndHobbiesForm.getEightQProfessionalSportsTimeBaseball()))
			{
				if (!(uniqueId.contains(interestAndHobbiesForm.getEightQProfessionalSportsTimeBaseball())))
				{
					uniqueId.add(interestAndHobbiesForm.getEightQProfessionalSportsTimeBaseball());
				}
			}

		}
		if (interestAndHobbiesForm.getEightQProfessionalSportsTimeHockey() != null)
		{
			if (selected(interestAndHobbiesForm.getEightQProfessionalSportsTimeHockey()))
			{
				if (!(uniqueId.contains(interestAndHobbiesForm.getEightQProfessionalSportsTimeHockey())))
				{
					uniqueId.add(interestAndHobbiesForm.getEightQProfessionalSportsTimeHockey());
				}
			}

		}
		if (interestAndHobbiesForm.getEightQProfessionalSportsTimeMMA() != null)
		{
			if (selected(interestAndHobbiesForm.getEightQProfessionalSportsTimeMMA()))
			{
				if (!(uniqueId.contains(interestAndHobbiesForm.getEightQProfessionalSportsTimeMMA())))
				{
					uniqueId.add(interestAndHobbiesForm.getEightQProfessionalSportsTimeMMA());
				}
			}

		}
		if (interestAndHobbiesForm.getEightQProfessionalSportsTimeSoccer() != null)
		{
			if (selected(interestAndHobbiesForm.getEightQProfessionalSportsTimeSoccer()))
			{
				if (!(uniqueId.contains(interestAndHobbiesForm.getEightQProfessionalSportsTimeSoccer())))
				{
					uniqueId.add(interestAndHobbiesForm.getEightQProfessionalSportsTimeSoccer());
				}
			}

		}
		if (interestAndHobbiesForm.getEightQProfessionalSportsTimeTennis() != null)
		{

			if (selected(interestAndHobbiesForm.getEightQProfessionalSportsTimeTennis()))

			{
				if (!(uniqueId.contains(interestAndHobbiesForm.getEightQProfessionalSportsTimeTennis())))
				{
					uniqueId.add(interestAndHobbiesForm.getEightQProfessionalSportsTimeTennis());
				}
			}

		}
		if (interestAndHobbiesForm.getEightQProfessionalSportsTimeWWE() != null)
		{
			if (selected(interestAndHobbiesForm.getEightQProfessionalSportsTimeWWE()))
			{
				if (!(uniqueId.contains(interestAndHobbiesForm.getEightQProfessionalSportsTimeWWE())))
				{
					uniqueId.add(interestAndHobbiesForm.getEightQProfessionalSportsTimeWWE());
				}
			}
		}
		if (interestAndHobbiesForm.getEightQProfessionalSportsTimeFantasySports() != null)
		{

			if (selected(interestAndHobbiesForm.getEightQProfessionalSportsTimeFantasySports()))
			{
				if (!(uniqueId.contains(interestAndHobbiesForm.getEightQProfessionalSportsTimeFantasySports())))
				{
					uniqueId.add(interestAndHobbiesForm.getEightQProfessionalSportsTimeFantasySports());
				}
			}

		}
		if (interestAndHobbiesForm.getEightQProfessionalSportsTimeExtremeSports() != null)
		{
			if (selected(interestAndHobbiesForm.getEightQProfessionalSportsTimeExtremeSports()))
			{
				if (!(uniqueId.contains(interestAndHobbiesForm.getEightQProfessionalSportsTimeExtremeSports())))
				{
					uniqueId.add(interestAndHobbiesForm.getEightQProfessionalSportsTimeExtremeSports());
				}
			}

		}
		if (interestAndHobbiesForm.getNinthQSocialMedia() != null && interestAndHobbiesForm.getNinthQSocialMedia().size() > 0)
		{
			for (final String media : interestAndHobbiesForm.getNinthQSocialMedia())
			{

				if (null != media)
				{
					if (!(uniqueId.contains(media)))
					{
						uniqueId.add(media);
					}
				}
			}

		}
		if (interestAndHobbiesForm.getTenthQTypicalRadio() != null && interestAndHobbiesForm.getTenthQTypicalRadio().size() > 0)
		{
			for (final String typicalRadio : interestAndHobbiesForm.getTenthQTypicalRadio())
			{

				if (null != typicalRadio)
				{
					if (!(uniqueId.contains(typicalRadio)))
					{
						uniqueId.add(typicalRadio);
					}
				}
			}

		}
		if (interestAndHobbiesForm.getEleventhQInternetUsage() != null
				&& interestAndHobbiesForm.getEleventhQInternetUsage().size() > 0)
		{
			for (final String internetUsage : interestAndHobbiesForm.getEleventhQInternetUsage())
			{

				if (null != internetUsage)
				{
					if (!(uniqueId.contains(internetUsage)))
					{
						uniqueId.add(internetUsage);
					}
				}
			}

		}

		if (interestAndHobbiesForm.getTwelthQPreferredLearningMethod() != null
				&& interestAndHobbiesForm.getTwelthQPreferredLearningMethod().size() > 0)
		{
			for (final String learningmethod : interestAndHobbiesForm.getTwelthQPreferredLearningMethod())
			{

				if (null != learningmethod)
				{
					if (!(uniqueId.contains(learningmethod)))
					{
						uniqueId.add(learningmethod);
					}
				}
			}

		}
		if (interestAndHobbiesForm.getThirteenthQNewTechSpeedUp() != null
				&& interestAndHobbiesForm.getThirteenthQNewTechSpeedUp().size() > 0)
		{
			for (final String newTechSpeed : interestAndHobbiesForm.getThirteenthQNewTechSpeedUp())
			{

				if (null != newTechSpeed)
				{
					if (!(uniqueId.contains(newTechSpeed)))
					{
						uniqueId.add(newTechSpeed);
					}
				}
			}

		}
		if (interestAndHobbiesForm.getFourtheenthQEmailCheck() != null)
		{
			if (!(uniqueId.contains(interestAndHobbiesForm.getFourtheenthQEmailCheck())))
			{
				uniqueId.add(interestAndHobbiesForm.getFourtheenthQEmailCheck());
			}

		}
		if (interestAndHobbiesForm.getFifteenthQBestAboutJob() != null
				&& interestAndHobbiesForm.getFifteenthQBestAboutJob().size() > 0)
		{
			for (final String bestAbtJob : interestAndHobbiesForm.getFifteenthQBestAboutJob())
			{

				if (null != bestAbtJob)
				{
					if (!(uniqueId.contains(bestAbtJob)))
					{
						uniqueId.add(bestAbtJob);
					}
				}
			}

		}
		if (interestAndHobbiesForm.getSixteenthQLeastAboutJob() != null
				&& interestAndHobbiesForm.getSixteenthQLeastAboutJob().size() > 0)
		{
			for (final String leastAbtJob : interestAndHobbiesForm.getSixteenthQLeastAboutJob())
			{

				if (null != leastAbtJob)
				{
					if (!(uniqueId.contains(leastAbtJob)))
					{
						uniqueId.add(leastAbtJob);
					}
				}
			}

		}
		if (interestAndHobbiesForm.getSeventeethQProfTechCharRankContinuingeducation() != null)
		{

			if (selected(interestAndHobbiesForm.getSeventeethQProfTechCharRankContinuingeducation()))
			{
				if (!(uniqueId.contains(interestAndHobbiesForm.getSeventeethQProfTechCharRankContinuingeducation())))
				{
					uniqueId.add(interestAndHobbiesForm.getSeventeethQProfTechCharRankContinuingeducation());
				}
			}

		}
		if (interestAndHobbiesForm.getSeventeethQProfTechCharRankPassionforcarsandtrucks() != null)
		{

			if (selected(interestAndHobbiesForm.getSeventeethQProfTechCharRankPassionforcarsandtrucks()))
			{

				if (!(uniqueId.contains(interestAndHobbiesForm.getSeventeethQProfTechCharRankPassionforcarsandtrucks())))
				{
					uniqueId.add(interestAndHobbiesForm.getSeventeethQProfTechCharRankPassionforcarsandtrucks());
				}
			}

		}
		if (interestAndHobbiesForm.getSeventeethQProfTechCharRankConnectingwithtechniciansonline() != null)
		{
			if (selected(interestAndHobbiesForm.getSeventeethQProfTechCharRankConnectingwithtechniciansonline()))
			{
				if (!(uniqueId.contains(interestAndHobbiesForm.getSeventeethQProfTechCharRankConnectingwithtechniciansonline())))
				{
					uniqueId.add(interestAndHobbiesForm.getSeventeethQProfTechCharRankConnectingwithtechniciansonline());
				}
			}

		}

		if (interestAndHobbiesForm.getSeventeethQProfTechCharRankNetworkingwithtechnicians() != null)
		{
			if (selected(interestAndHobbiesForm.getSeventeethQProfTechCharRankNetworkingwithtechnicians()))
			{
				if (!(uniqueId.contains(interestAndHobbiesForm.getSeventeethQProfTechCharRankNetworkingwithtechnicians())))
				{
					uniqueId.add(interestAndHobbiesForm.getSeventeethQProfTechCharRankNetworkingwithtechnicians());
				}
			}

		}
		if (interestAndHobbiesForm.getSeventeethQProfTechCharRankBusinesssavvysensetorunashop() != null)
		{
			if (selected(interestAndHobbiesForm.getSeventeethQProfTechCharRankBusinesssavvysensetorunashop()))
			{
				if (!(uniqueId.contains(interestAndHobbiesForm.getSeventeethQProfTechCharRankBusinesssavvysensetorunashop())))
				{
					uniqueId.add(interestAndHobbiesForm.getSeventeethQProfTechCharRankBusinesssavvysensetorunashop());
				}
			}

		}
		if (interestAndHobbiesForm.getSeventeethQProfTechCharRankStayingknowledgeableinnewtechnologies() != null)
		{

			if (selected(interestAndHobbiesForm.getSeventeethQProfTechCharRankStayingknowledgeableinnewtechnologies()))
			{

				if (!(uniqueId.contains(interestAndHobbiesForm.getSeventeethQProfTechCharRankStayingknowledgeableinnewtechnologies())))
				{
					uniqueId.add(interestAndHobbiesForm.getSeventeethQProfTechCharRankStayingknowledgeableinnewtechnologies());
				}

			}

		}
		if (interestAndHobbiesForm.getSeventeethQProfTechCharRankNaturalmechanicalability() != null)
		{

			if (selected(interestAndHobbiesForm.getSeventeethQProfTechCharRankNaturalmechanicalability()))
			{

				if (!(uniqueId.contains(interestAndHobbiesForm.getSeventeethQProfTechCharRankNaturalmechanicalability())))
				{
					uniqueId.add(interestAndHobbiesForm.getSeventeethQProfTechCharRankNaturalmechanicalability());
				}
			}
		}
		if (interestAndHobbiesForm.getSeventeethQProfTechCharRankStrongworkethic() != null)
		{
			if (selected(interestAndHobbiesForm.getSeventeethQProfTechCharRankStrongworkethic()))
			{
				if (!(uniqueId.contains(interestAndHobbiesForm.getSeventeethQProfTechCharRankStrongworkethic())))
				{
					uniqueId.add(interestAndHobbiesForm.getSeventeethQProfTechCharRankStrongworkethic());
				}

			}

		}
		if (interestAndHobbiesForm.getEighteenthQDevicesOwn() != null
				&& interestAndHobbiesForm.getEighteenthQDevicesOwn().size() > 0)
		{
			for (final String deviceOwn : interestAndHobbiesForm.getEighteenthQDevicesOwn())
			{

				if (null != deviceOwn)
				{
					if (!(uniqueId.contains(deviceOwn)))
					{
						uniqueId.add(deviceOwn);
					}
				}
			}

		}
		if (interestAndHobbiesForm.getNineteethQProfCertificates() != null
				&& interestAndHobbiesForm.getNineteethQProfCertificates().size() > 0)
		{
			for (final String profCertificates : interestAndHobbiesForm.getNineteethQProfCertificates())
			{

				if (null != profCertificates)
				{
					if (!(uniqueId.contains(profCertificates)))
					{
						uniqueId.add(profCertificates);
					}
				}
			}

		}
		final FMSurveyData surveyData = new FMSurveyData();
		surveyData.setUniqueID(uniqueId);
		try
		{
			final LoyaltySurveyResponseDTO resultFromCrm = loyaltySurveyFacade.populateHobbiesSurveyData(surveyData);
			if (resultFromCrm != null)
			{
				LOG.info("STATUS" + resultFromCrm.getStatus());
				if (resultFromCrm.getStatus().contains("Error:"))
				{
					//throw new IOException("Error in survey" + resultFromCrm.getStatus());
					return "Error in survey";
				}
			}
		}
		catch (final UnsupportedOperationException e)
		{
			return "interestSurveyError";
		}
		catch (final ClassNotFoundException e)
		{
			// YTODO Auto-generated catch block
			return "interestSurveyError";
		}
		catch (final SOAPException e)
		{
			// YTODO Auto-generated catch block
			return "interestSurveyError";
		}
		catch (final IOException e)
		{
			// YTODO Auto-generated catch block
			return "interestSurveyError";
		}
		return "success";

	}

	public String populatebrandQuestionformData(final BrandQuestionsForm brandQuestionform)
	{


		final List<String> uniqueId = new ArrayList<String>();
		if (brandQuestionform.getFirstQuestionPrimary() != null)
		{
			if (!(uniqueId.contains(brandQuestionform.getFirstQuestionPrimary())))
			{
				uniqueId.add(brandQuestionform.getFirstQuestionPrimary());
			}
		}
		if (brandQuestionform.getFirstQuestionSecondary() != null)
		{
			if (!(uniqueId.contains(brandQuestionform.getFirstQuestionSecondary())))
			{
				uniqueId.add(brandQuestionform.getFirstQuestionSecondary());
			}
		}
		if (brandQuestionform.getFirstQuestionPrimary() == null && brandQuestionform.getFirstQuestionOtherPrimary() != null)
		{
			brandQuestionform.setFirstQuestionOtherPrimary("0027");
			if (!(uniqueId.contains(brandQuestionform.getFirstQuestionOtherPrimary())))
			{
				uniqueId.add(brandQuestionform.getFirstQuestionOtherPrimary());
			}
		}

		if (brandQuestionform.getFirstQuestionSecondary() == null && brandQuestionform.getFirstQuestionOtherSecondary() != null)
		{
			brandQuestionform.setFirstQuestionOtherSecondary("0028");
			if (!(uniqueId.contains(brandQuestionform.getFirstQuestionOtherSecondary())))
			{
				uniqueId.add(brandQuestionform.getFirstQuestionOtherSecondary());
			}
		}
		if (brandQuestionform.getSecondQuestionPrimary() != null)
		{
			if (!(uniqueId.contains(brandQuestionform.getSecondQuestionPrimary())))
			{
				uniqueId.add(brandQuestionform.getSecondQuestionPrimary());
			}
		}
		if (brandQuestionform.getSecondQuestionSecondary() != null)
		{
			if (!(uniqueId.contains(brandQuestionform.getSecondQuestionSecondary())))
			{
				uniqueId.add(brandQuestionform.getSecondQuestionSecondary());
			}
		}
		if (brandQuestionform.getSecondQuestionPrimary() == null && brandQuestionform.getSecondQuestionOtherPrimary() != null)
		{
			brandQuestionform.setSecondQuestionOtherPrimary("0055");
			if (!(uniqueId.contains(brandQuestionform.getSecondQuestionOtherPrimary())))
			{
				uniqueId.add(brandQuestionform.getSecondQuestionOtherPrimary());
			}
		}
		if (brandQuestionform.getSecondQuestionOtherSecondary() != null && brandQuestionform.getSecondQuestionSecondary() == null)
		{
			brandQuestionform.setSecondQuestionOtherSecondary("0056");
			if (!(uniqueId.contains(brandQuestionform.getSecondQuestionOtherSecondary())))
			{
				uniqueId.add(brandQuestionform.getSecondQuestionOtherSecondary());
			}
		}
		if (brandQuestionform.getThirdQuestionPrimary() != null)
		{
			if (!(uniqueId.contains(brandQuestionform.getThirdQuestionPrimary())))
			{
				uniqueId.add(brandQuestionform.getThirdQuestionPrimary());
			}
		}
		if (brandQuestionform.getThirdQuestionSecondary() != null)
		{
			if (!(uniqueId.contains(brandQuestionform.getThirdQuestionSecondary())))
			{
				uniqueId.add(brandQuestionform.getThirdQuestionSecondary());
			}
		}
		if (brandQuestionform.getThirdQuestionPrimary() == null && brandQuestionform.getThirdQuestionOtherPrimary() != null)
		{
			brandQuestionform.setThirdQuestionOtherPrimary("0083");
			if (!(uniqueId.contains(brandQuestionform.getThirdQuestionOtherPrimary())))
			{
				uniqueId.add(brandQuestionform.getThirdQuestionOtherPrimary());
			}
		}
		if (brandQuestionform.getThirdQuestionOtherSecondary() != null && brandQuestionform.getThirdQuestionSecondary() == null)
		{
			brandQuestionform.setThirdQuestionOtherSecondary("0084");
			if (!(uniqueId.contains(brandQuestionform.getThirdQuestionOtherSecondary())))
			{
				uniqueId.add(brandQuestionform.getThirdQuestionOtherSecondary());
			}
		}
		if (brandQuestionform.getFourthQuestionPrimary() != null)
		{
			if (!(uniqueId.contains(brandQuestionform.getFourthQuestionPrimary())))
			{
				uniqueId.add(brandQuestionform.getFourthQuestionPrimary());
			}
		}
		if (brandQuestionform.getFourthQuestionSecondary() != null)
		{
			if (!(uniqueId.contains(brandQuestionform.getFourthQuestionSecondary())))
			{
				uniqueId.add(brandQuestionform.getFourthQuestionSecondary());
			}
		}
		if (brandQuestionform.getFourthQuestionPrimary() == null && brandQuestionform.getFourthQuestionOtherPrimary() != null)
		{
			brandQuestionform.setFourthQuestionOtherPrimary("0111");
			if (!(uniqueId.contains(brandQuestionform.getFourthQuestionOtherPrimary())))
			{
				uniqueId.add(brandQuestionform.getFourthQuestionOtherPrimary());
			}
		}
		if (brandQuestionform.getFourthQuestionOtherSecondary() != null && brandQuestionform.getFourthQuestionSecondary() == null)
		{
			brandQuestionform.setFourthQuestionOtherSecondary("0112");
			if (!(uniqueId.contains(brandQuestionform.getFourthQuestionOtherSecondary())))
			{
				uniqueId.add(brandQuestionform.getFourthQuestionOtherSecondary());
			}
		}

		if (brandQuestionform.getFifthQuestionPrimary() != null)
		{
			if (!(uniqueId.contains(brandQuestionform.getFifthQuestionPrimary())))
			{
				uniqueId.add(brandQuestionform.getFifthQuestionPrimary());
			}
		}
		if (brandQuestionform.getFifthQuestionSecondary() != null)
		{
			if (!(uniqueId.contains(brandQuestionform.getFifthQuestionSecondary())))
			{
				uniqueId.add(brandQuestionform.getFifthQuestionSecondary());
			}
		}
		if (brandQuestionform.getFifthQuestionPrimary() == null && brandQuestionform.getFifthQuestionOtherPrimary() != null)
		{
			brandQuestionform.setFifthQuestionOtherPrimary("0129");
			if (!(uniqueId.contains(brandQuestionform.getFifthQuestionOtherPrimary())))
			{
				uniqueId.add(brandQuestionform.getFifthQuestionOtherPrimary());
			}
		}
		if (brandQuestionform.getFifthQuestionOtherSecondary() != null && brandQuestionform.getFifthQuestionSecondary() == null)
		{
			brandQuestionform.setFifthQuestionOtherSecondary("0130");
			if (!(uniqueId.contains(brandQuestionform.getFifthQuestionOtherSecondary())))
			{
				uniqueId.add(brandQuestionform.getFifthQuestionOtherSecondary());
			}
		}
		if (brandQuestionform.getSixthQuestionPrimary() != null)
		{
			if (!(uniqueId.contains(brandQuestionform.getSixthQuestionPrimary())))
			{
				uniqueId.add(brandQuestionform.getSixthQuestionPrimary());
			}
		}
		if (brandQuestionform.getSixthQuestionSecondary() != null)
		{
			if (!(uniqueId.contains(brandQuestionform.getSixthQuestionSecondary())))
			{
				uniqueId.add(brandQuestionform.getSixthQuestionSecondary());
			}
		}
		if (brandQuestionform.getSixthQuestionPrimary() == null && brandQuestionform.getSixthQuestionOtherPrimary() != null)
		{
			brandQuestionform.setSixthQuestionOtherPrimary("0147");
			if (!(uniqueId.contains(brandQuestionform.getSixthQuestionOtherPrimary())))
			{
				uniqueId.add(brandQuestionform.getSixthQuestionOtherPrimary());
			}
		}
		if (brandQuestionform.getSixthQuestionOtherSecondary() != null && brandQuestionform.getSixthQuestionSecondary() == null)
		{
			brandQuestionform.setSixthQuestionOtherSecondary("0148");
			if (!(uniqueId.contains(brandQuestionform.getSixthQuestionOtherSecondary())))
			{
				uniqueId.add(brandQuestionform.getSixthQuestionOtherSecondary());
			}
		}
		if (brandQuestionform.getSeventhQuestionPrimary() != null)
		{
			if (!(uniqueId.contains(brandQuestionform.getSeventhQuestionPrimary())))
			{
				uniqueId.add(brandQuestionform.getSeventhQuestionPrimary());
			}
		}
		if (brandQuestionform.getSeventhQuestionSecondary() != null)
		{
			if (!(uniqueId.contains(brandQuestionform.getSeventhQuestionSecondary())))
			{
				uniqueId.add(brandQuestionform.getSeventhQuestionSecondary());
			}
		}
		if (brandQuestionform.getSeventhQuestionPrimary() == null && brandQuestionform.getSeventhQuestionOtherPrimary() != null)
		{
			brandQuestionform.setSeventhQuestionOtherPrimary("0165");
			if (!(uniqueId.contains(brandQuestionform.getSeventhQuestionOtherPrimary())))
			{
				uniqueId.add(brandQuestionform.getSeventhQuestionOtherPrimary());
			}
		}
		if (brandQuestionform.getSeventhQuestionOtherSecondary() != null && brandQuestionform.getSeventhQuestionSecondary() == null)
		{
			brandQuestionform.setSeventhQuestionSecondary("0166");
			if (!(uniqueId.contains(brandQuestionform.getSeventhQuestionOtherSecondary())))
			{
				uniqueId.add(brandQuestionform.getSeventhQuestionOtherSecondary());
			}
		}
		if (brandQuestionform.getEightQuestionPrimary() != null)
		{
			if (!(uniqueId.contains(brandQuestionform.getEightQuestionPrimary())))
			{
				uniqueId.add(brandQuestionform.getEightQuestionPrimary());
			}
		}
		if (brandQuestionform.getEightQuestionSecondary() != null)
		{
			if (!(uniqueId.contains(brandQuestionform.getEightQuestionSecondary())))
			{
				uniqueId.add(brandQuestionform.getEightQuestionSecondary());
			}
		}
		if (brandQuestionform.getEightQuestionPrimary() == null && brandQuestionform.getEightQuestionOtherPrimary() != null)
		{
			brandQuestionform.setEightQuestionOtherPrimary("0185");
			if (!(uniqueId.contains(brandQuestionform.getEightQuestionOtherPrimary())))
			{
				uniqueId.add(brandQuestionform.getEightQuestionOtherPrimary());
			}
		}
		if (brandQuestionform.getEightQuestionOtherSecondary() != null && brandQuestionform.getEightQuestionSecondary() == null)
		{
			brandQuestionform.setEightQuestionOtherSecondary("0186");
			if (!(uniqueId.contains(brandQuestionform.getEightQuestionOtherSecondary())))
			{
				uniqueId.add(brandQuestionform.getEightQuestionOtherSecondary());
			}
		}

		if (brandQuestionform.getNinthQuestionPrimary() != null)
		{
			if (!(uniqueId.contains(brandQuestionform.getNinthQuestionPrimary())))
			{
				uniqueId.add(brandQuestionform.getNinthQuestionPrimary());
			}
		}
		if (brandQuestionform.getNinthQuestionSecondary() != null)
		{
			if (!(uniqueId.contains(brandQuestionform.getNinthQuestionSecondary())))
			{
				uniqueId.add(brandQuestionform.getNinthQuestionSecondary());
			}
		}
		if (brandQuestionform.getNinthQuestionPrimary() == null && brandQuestionform.getNinthQuestionOtherPrimary() != null)
		{
			brandQuestionform.setNinthQuestionOtherPrimary("0211");
			if (!(uniqueId.contains(brandQuestionform.getNinthQuestionOtherPrimary())))
			{
				uniqueId.add(brandQuestionform.getNinthQuestionOtherPrimary());
			}
		}
		if (brandQuestionform.getNinthQuestionOtherSecondary() != null && brandQuestionform.getNinthQuestionSecondary() == null)
		{
			brandQuestionform.setNinthQuestionOtherSecondary("0212");
			if (!(uniqueId.contains(brandQuestionform.getNinthQuestionOtherSecondary())))
			{
				uniqueId.add(brandQuestionform.getNinthQuestionOtherSecondary());
			}
		}
		if (brandQuestionform.getTenthQuestionPrimary() != null)
		{
			if (!(uniqueId.contains(brandQuestionform.getTenthQuestionPrimary())))
			{
				uniqueId.add(brandQuestionform.getTenthQuestionPrimary());
			}
		}
		if (brandQuestionform.getTenthQuestionSecondary() != null)
		{
			if (!(uniqueId.contains(brandQuestionform.getTenthQuestionSecondary())))
			{
				uniqueId.add(brandQuestionform.getTenthQuestionSecondary());
			}
		}
		if (brandQuestionform.getTenthQuestionPrimary() == null && brandQuestionform.getTenthQuestionOtherPrimary() != null)
		{
			brandQuestionform.setTenthQuestionOtherPrimary("0237");
			if (!(uniqueId.contains(brandQuestionform.getTenthQuestionOtherPrimary())))
			{
				uniqueId.add(brandQuestionform.getTenthQuestionOtherPrimary());
			}
		}
		if (brandQuestionform.getTenthQuestionOtherSecondary() != null && brandQuestionform.getTenthQuestionSecondary() == null)
		{
			brandQuestionform.setTenthQuestionOtherSecondary("0238");
			if (!(uniqueId.contains(brandQuestionform.getTenthQuestionOtherSecondary())))
			{
				uniqueId.add(brandQuestionform.getTenthQuestionOtherSecondary());
			}
		}
		if (brandQuestionform.getElevanthQuestionPrimary() != null)
		{
			if (!(uniqueId.contains(brandQuestionform.getElevanthQuestionPrimary())))
			{
				uniqueId.add(brandQuestionform.getElevanthQuestionPrimary());
			}
		}
		if (brandQuestionform.getElevanthQuestionSecondary() != null)
		{
			if (!(uniqueId.contains(brandQuestionform.getElevanthQuestionSecondary())))
			{
				uniqueId.add(brandQuestionform.getElevanthQuestionSecondary());
			}
		}
		if (brandQuestionform.getElevanthQuestionPrimary() == null && brandQuestionform.getElevanthQuestionOtherPrimary() != null)
		{
			brandQuestionform.setElevanthQuestionOtherPrimary("0255");
			if (!(uniqueId.contains(brandQuestionform.getElevanthQuestionOtherPrimary())))
			{
				uniqueId.add(brandQuestionform.getElevanthQuestionOtherPrimary());
			}
		}
		if (brandQuestionform.getElevanthQuestionOtherSecondary() != null
				&& brandQuestionform.getElevanthQuestionSecondary() == null)
		{
			brandQuestionform.setElevanthQuestionOtherSecondary("0256");
			if (!(uniqueId.contains(brandQuestionform.getElevanthQuestionOtherSecondary())))
			{
				uniqueId.add(brandQuestionform.getElevanthQuestionOtherSecondary());
			}
		}
		if (brandQuestionform.getTwelthQuestionPrimary() != null)
		{
			if (!(uniqueId.contains(brandQuestionform.getTwelthQuestionPrimary())))
			{
				uniqueId.add(brandQuestionform.getTwelthQuestionPrimary());
			}
		}
		if (brandQuestionform.getTewlthQuestionSecondary() != null)
		{
			if (!(uniqueId.contains(brandQuestionform.getTewlthQuestionSecondary())))
			{
				uniqueId.add(brandQuestionform.getTewlthQuestionSecondary());
			}
		}
		if (brandQuestionform.getTwelthQuestionPrimary() == null && brandQuestionform.getTwelthQuestionOtherPrimary() != null)
		{
			brandQuestionform.setTwelthQuestionOtherPrimary("0281");
			if (!(uniqueId.contains(brandQuestionform.getTwelthQuestionOtherPrimary())))
			{
				uniqueId.add(brandQuestionform.getTwelthQuestionOtherPrimary());
			}
		}
		if (brandQuestionform.getTwelthQuestionOtherSecondary() != null && brandQuestionform.getTewlthQuestionSecondary() == null)
		{
			brandQuestionform.setTwelthQuestionOtherSecondary("0282");
			if (!(uniqueId.contains(brandQuestionform.getTwelthQuestionOtherSecondary())))
			{
				uniqueId.add(brandQuestionform.getTwelthQuestionOtherSecondary());
			}
		}
		if (brandQuestionform.getThirteenthQuestionPrimary() != null)
		{
			if (!(uniqueId.contains(brandQuestionform.getThirteenthQuestionPrimary())))
			{
				uniqueId.add(brandQuestionform.getThirteenthQuestionPrimary());
			}
		}
		if (brandQuestionform.getThirteenthQuestionSecondary() != null)
		{
			if (!(uniqueId.contains(brandQuestionform.getThirteenthQuestionSecondary())))
			{
				uniqueId.add(brandQuestionform.getThirteenthQuestionSecondary());
			}
		}
		if (brandQuestionform.getThirteenthQuestionPrimary() == null
				&& brandQuestionform.getThirteenthQuestionOtherPrimary() != null)
		{
			brandQuestionform.setThirteenthQuestionOtherPrimary("0307");
			if (!(uniqueId.contains(brandQuestionform.getThirteenthQuestionOtherPrimary())))
			{
				uniqueId.add(brandQuestionform.getThirteenthQuestionOtherPrimary());
			}
		}
		if (brandQuestionform.getThirteenthQuestionOtherSecondary() != null
				&& brandQuestionform.getThirteenthQuestionSecondary() == null)
		{
			brandQuestionform.setThirteenthQuestionOtherSecondary("0308");
			if (!(uniqueId.contains(brandQuestionform.getThirteenthQuestionOtherSecondary())))
			{
				uniqueId.add(brandQuestionform.getThirteenthQuestionOtherSecondary());
			}
		}
		if (brandQuestionform.getFourteenthQuestionPrimary() != null)
		{
			if (!(uniqueId.contains(brandQuestionform.getFourteenthQuestionPrimary())))
			{
				uniqueId.add(brandQuestionform.getFourteenthQuestionPrimary());
			}
		}
		if (brandQuestionform.getFourteenthQuestionSecondary() != null)
		{
			if (!(uniqueId.contains(brandQuestionform.getFourteenthQuestionSecondary())))
			{
				uniqueId.add(brandQuestionform.getFourteenthQuestionSecondary());
			}
		}
		if (brandQuestionform.getFourteenthQuestionPrimary() == null
				&& brandQuestionform.getFourteenthQuestionOtherPrimary() != null)
		{
			brandQuestionform.setFourteenthQuestionOtherPrimary("0333");
			if (!(uniqueId.contains(brandQuestionform.getFourteenthQuestionOtherPrimary())))
			{
				uniqueId.add(brandQuestionform.getFourteenthQuestionOtherPrimary());
			}
		}
		if (brandQuestionform.getFourteenthQuestionOtherSecondary() != null
				&& brandQuestionform.getFourteenthQuestionSecondary() == null)
		{
			brandQuestionform.setFourteenthQuestionOtherSecondary("0334");
			if (!(uniqueId.contains(brandQuestionform.getFourteenthQuestionOtherSecondary())))
			{
				uniqueId.add(brandQuestionform.getFourteenthQuestionOtherSecondary());
			}
		}
		if (brandQuestionform.getFifteenthQuestionPrimary() != null)
		{
			if (!(uniqueId.contains(brandQuestionform.getFifteenthQuestionPrimary())))
			{
				uniqueId.add(brandQuestionform.getFifteenthQuestionPrimary());
			}
		}
		if (brandQuestionform.getFifteenthQuestionSecondary() != null)
		{
			if (!(uniqueId.contains(brandQuestionform.getFifteenthQuestionSecondary())))
			{
				uniqueId.add(brandQuestionform.getFifteenthQuestionSecondary());
			}
		}
		if (brandQuestionform.getFifteenthQuestionPrimary() == null && brandQuestionform.getFifteenthQuestionOtherPrimary() != null)
		{
			brandQuestionform.setFifteenthQuestionOtherPrimary("0359");
			if (!(uniqueId.contains(brandQuestionform.getFifteenthQuestionOtherPrimary())))
			{
				uniqueId.add(brandQuestionform.getFifteenthQuestionOtherPrimary());
			}
		}
		if (brandQuestionform.getFifteenthQuestionOtherSecondary() != null
				&& brandQuestionform.getFifteenthQuestionSecondary() == null)
		{
			brandQuestionform.setFifteenthQuestionOtherSecondary("0360");
			if (!(uniqueId.contains(brandQuestionform.getFifteenthQuestionOtherSecondary())))
			{
				uniqueId.add(brandQuestionform.getFifteenthQuestionOtherSecondary());
			}
		}
		if (brandQuestionform.getSixteenthQuestionPrimary() != null)
		{
			if (!(uniqueId.contains(brandQuestionform.getSixteenthQuestionPrimary())))
			{
				uniqueId.add(brandQuestionform.getSixteenthQuestionPrimary());
			}
		}
		if (brandQuestionform.getSixteenthQuestionSecondary() != null)
		{
			if (!(uniqueId.contains(brandQuestionform.getSixteenthQuestionSecondary())))
			{
				uniqueId.add(brandQuestionform.getSixteenthQuestionSecondary());
			}
		}
		if (brandQuestionform.getSixteenthQuestionPrimary() == null && brandQuestionform.getSixteenthQuestionOtherPrimary() != null)
		{
			brandQuestionform.setSixteenthQuestionOtherPrimary("0381");
			if (!(uniqueId.contains(brandQuestionform.getSixteenthQuestionOtherPrimary())))
			{
				uniqueId.add(brandQuestionform.getSixteenthQuestionOtherPrimary());
			}
		}
		if (brandQuestionform.getSixteenthQuestionOtherSecondary() != null
				&& brandQuestionform.getSixteenthQuestionSecondary() == null)
		{
			brandQuestionform.setSixteenthQuestionOtherSecondary("0382");
			if (!(uniqueId.contains(brandQuestionform.getSixteenthQuestionOtherSecondary())))
			{
				uniqueId.add(brandQuestionform.getSixteenthQuestionOtherSecondary());
			}
		}
		if (brandQuestionform.getSeventeenthQuestionPrimary() != null)
		{
			if (!(uniqueId.contains(brandQuestionform.getSeventeenthQuestionPrimary())))
			{
				uniqueId.add(brandQuestionform.getSeventeenthQuestionPrimary());
			}
		}
		if (brandQuestionform.getSeventeenthQuestionSecondary() != null)
		{
			if (!(uniqueId.contains(brandQuestionform.getSeventeenthQuestionSecondary())))
			{
				uniqueId.add(brandQuestionform.getSeventeenthQuestionSecondary());
			}
		}
		if (brandQuestionform.getSeventeenthQuestionPrimary() == null
				&& brandQuestionform.getSeventeenthQuestionOtherPrimary() != null)
		{
			brandQuestionform.setSeventeenthQuestionOtherPrimary("0405");
			if (!(uniqueId.contains(brandQuestionform.getSeventeenthQuestionOtherPrimary())))
			{
				uniqueId.add(brandQuestionform.getSeventeenthQuestionOtherPrimary());
			}
		}
		if (brandQuestionform.getSeventeenthQuestionOtherSecondary() != null
				&& brandQuestionform.getSeventeenthQuestionSecondary() == null)
		{
			brandQuestionform.setSeventeenthQuestionOtherSecondary("0406");
			if (!(uniqueId.contains(brandQuestionform.getSeventeenthQuestionOtherSecondary())))
			{
				uniqueId.add(brandQuestionform.getSeventeenthQuestionOtherSecondary());
			}
		}
		if (brandQuestionform.getEighteenthQuestionPrimary() != null)
		{
			if (!(uniqueId.contains(brandQuestionform.getEighteenthQuestionPrimary())))
			{
				uniqueId.add(brandQuestionform.getEighteenthQuestionPrimary());
			}
		}
		if (brandQuestionform.getEighteenthQuestionSecondary() != null)
		{
			if (!(uniqueId.contains(brandQuestionform.getEighteenthQuestionSecondary())))
			{
				uniqueId.add(brandQuestionform.getEighteenthQuestionSecondary());
			}
		}
		if (brandQuestionform.getEighteenthQuestionPrimary() == null
				&& brandQuestionform.getEighteenthQuestionOtherPrimary() != null)
		{
			brandQuestionform.setEighteenthQuestionOtherPrimary("0421");
			if (!(uniqueId.contains(brandQuestionform.getEighteenthQuestionOtherPrimary())))
			{
				uniqueId.add(brandQuestionform.getEighteenthQuestionOtherPrimary());
			}
		}
		if (brandQuestionform.getEighteenthQuestionOtherSecondary() != null
				&& brandQuestionform.getEighteenthQuestionSecondary() == null)
		{
			brandQuestionform.setEighteenthQuestionOtherSecondary("0422");
			if (!(uniqueId.contains(brandQuestionform.getEighteenthQuestionOtherSecondary())))
			{
				uniqueId.add(brandQuestionform.getEighteenthQuestionOtherSecondary());
			}
		}
		if (brandQuestionform.getQuestion19AlignmentParts() != null)
		{
			if (selected(brandQuestionform.getQuestion19AlignmentParts()))
			{
				if (!(uniqueId.contains(brandQuestionform.getQuestion19AlignmentParts())))
				{
					uniqueId.add(brandQuestionform.getQuestion19AlignmentParts());
				}
			}
		}
		if (brandQuestionform.getQuestion19ballJoints() != null)
		{
			if (selected(brandQuestionform.getQuestion19ballJoints()))
			{
				if (!(uniqueId.contains(brandQuestionform.getQuestion19ballJoints())))
				{
					uniqueId.add(brandQuestionform.getQuestion19ballJoints());
				}
			}
		}
		if (brandQuestionform.getQuestion19Batteries() != null)
		{
			if (selected(brandQuestionform.getQuestion19Batteries()))
			{
				if (!(uniqueId.contains(brandQuestionform.getQuestion19Batteries())))
				{
					uniqueId.add(brandQuestionform.getQuestion19Batteries());
				}
			}
		}
		if (brandQuestionform.getQuestion19Bearingseals() != null)
		{

			if (selected(brandQuestionform.getQuestion19Bearingseals()))
			{
				if (!(uniqueId.contains(brandQuestionform.getQuestion19Bearingseals())))
				{
					uniqueId.add(brandQuestionform.getQuestion19Bearingseals());
				}
			}
		}
		if (brandQuestionform.getQuestion19Brakepads() != null)
		{
			if (selected(brandQuestionform.getQuestion19Brakepads()))
			{

				if (!(uniqueId.contains(brandQuestionform.getQuestion19Brakepads())))
				{
					uniqueId.add(brandQuestionform.getQuestion19Brakepads());
				}
			}
		}
		if (brandQuestionform.getQuestion19Bushings() != null)
		{

			if (selected(brandQuestionform.getQuestion19Bushings()))
			{
				if (!(uniqueId.contains(brandQuestionform.getQuestion19Bushings())))
				{
					uniqueId.add(brandQuestionform.getQuestion19Bushings());
				}
			}
		}
		if (brandQuestionform.getQuestion19coilSprings() != null)
		{
			if (selected(brandQuestionform.getQuestion19coilSprings()))
			{
				if (!(uniqueId.contains(brandQuestionform.getQuestion19coilSprings())))
				{
					uniqueId.add(brandQuestionform.getQuestion19coilSprings());
				}
			}
		}
		if (brandQuestionform.getQuestion19controlArms() != null)
		{
			if (selected(brandQuestionform.getQuestion19controlArms()))
			{
				if (!(uniqueId.contains(brandQuestionform.getQuestion19controlArms())))
				{
					uniqueId.add(brandQuestionform.getQuestion19controlArms());
				}
			}
		}
		if (brandQuestionform.getQuestion19engineComponents() != null)
		{
			if (selected(brandQuestionform.getQuestion19engineComponents()))
			{

				if (!(uniqueId.contains(brandQuestionform.getQuestion19engineComponents())))
				{
					uniqueId.add(brandQuestionform.getQuestion19engineComponents());
				}
			}
		}
		if (brandQuestionform.getQuestion19filters() != null)
		{
			if (selected(brandQuestionform.getQuestion19filters()))
			{

				if (!(uniqueId.contains(brandQuestionform.getQuestion19filters())))
				{
					uniqueId.add(brandQuestionform.getQuestion19filters());
				}
			}
		}
		if (brandQuestionform.getQuestion19gaskets() != null)
		{

			if (selected(brandQuestionform.getQuestion19gaskets()))
			{
				if (!(uniqueId.contains(brandQuestionform.getQuestion19gaskets())))
				{
					uniqueId.add(brandQuestionform.getQuestion19gaskets());
				}
			}
		}
		if (brandQuestionform.getQuestion19hubAssemblie() != null)
		{
			if (selected(brandQuestionform.getQuestion19hubAssemblie()))
			{
				if (!(uniqueId.contains(brandQuestionform.getQuestion19hubAssemblie())))
				{
					uniqueId.add(brandQuestionform.getQuestion19hubAssemblie());
				}
			}
		}
		if (brandQuestionform.getQuestion19idlerarms() != null)
		{
			if (selected(brandQuestionform.getQuestion19idlerarms()))
			{

				if (!(uniqueId.contains(brandQuestionform.getQuestion19idlerarms())))
				{
					uniqueId.add(brandQuestionform.getQuestion19idlerarms());
				}
			}
		}
		if (brandQuestionform.getQuestion19lighting() != null)
		{
			if (selected(brandQuestionform.getQuestion19lighting()))
			{

				if (!(uniqueId.contains(brandQuestionform.getQuestion19lighting())))
				{
					uniqueId.add(brandQuestionform.getQuestion19lighting());
				}
			}
		}
		if (brandQuestionform.getQuestion19Rotors() != null)
		{
			if (selected(brandQuestionform.getQuestion19Rotors()))
			{
				if (!(uniqueId.contains(brandQuestionform.getQuestion19Rotors())))
				{
					uniqueId.add(brandQuestionform.getQuestion19Rotors());
				}
			}
		}
		if (brandQuestionform.getQuestion19streeringStablizier() != null)
		{
			if (selected(brandQuestionform.getQuestion19streeringStablizier()))
			{

				if (!(uniqueId.contains(brandQuestionform.getQuestion19streeringStablizier())))
				{
					uniqueId.add(brandQuestionform.getQuestion19streeringStablizier());
				}
			}
		}
		if (brandQuestionform.getQuestion19structAssemblie() != null)
		{
			if (selected(brandQuestionform.getQuestion19structAssemblie()))
			{

				if (!(uniqueId.contains(brandQuestionform.getQuestion19structAssemblie())))
				{
					uniqueId.add(brandQuestionform.getQuestion19structAssemblie());
				}
			}
		}
		if (brandQuestionform.getQuestion19structMountr() != null)
		{
			if (selected(brandQuestionform.getQuestion19structMountr()))
			{

				if (!(uniqueId.contains(brandQuestionform.getQuestion19structMountr())))
				{
					uniqueId.add(brandQuestionform.getQuestion19structMountr());
				}
			}
		}
		if (brandQuestionform.getQuestion19swaybarlinkr() != null)
		{
			if (selected(brandQuestionform.getQuestion19swaybarlinkr()))
			{

				if (!(uniqueId.contains(brandQuestionform.getQuestion19swaybarlinkr())))
				{
					uniqueId.add(brandQuestionform.getQuestion19swaybarlinkr());
				}
			}
		}
		if (brandQuestionform.getQuestion19TieRods() != null)
		{
			if (selected(brandQuestionform.getQuestion19TieRods()))
			{

				if (!(uniqueId.contains(brandQuestionform.getQuestion19TieRods())))
				{
					uniqueId.add(brandQuestionform.getQuestion19TieRods());
				}
			}
		}
		if (brandQuestionform.getQuestion19UJoints() != null)
		{
			if (selected(brandQuestionform.getQuestion19UJoints()))
			{

				if (!(uniqueId.contains(brandQuestionform.getQuestion19UJoints())))
				{
					uniqueId.add(brandQuestionform.getQuestion19UJoints());
				}
			}
		}
		if (brandQuestionform.getQuestion19Wipers() != null)
		{
			if (selected(brandQuestionform.getQuestion19Wipers()))
			{

				if (!(uniqueId.contains(brandQuestionform.getQuestion19Wipers())))
				{
					uniqueId.add(brandQuestionform.getQuestion19Wipers());
				}
			}
		}
		if (brandQuestionform.getQuestion20AlignmentParts() != null)
		{
			if (selected(brandQuestionform.getQuestion20AlignmentParts()))
			{

				if (!(uniqueId.contains(brandQuestionform.getQuestion20AlignmentParts())))
				{
					uniqueId.add(brandQuestionform.getQuestion20AlignmentParts());
				}
			}
		}
		if (brandQuestionform.getQuestion20ballJoints() != null)
		{
			if (selected(brandQuestionform.getQuestion20ballJoints()))
			{
				if (!(uniqueId.contains(brandQuestionform.getQuestion20ballJoints())))
				{
					uniqueId.add(brandQuestionform.getQuestion20ballJoints());
				}
			}
		}
		if (brandQuestionform.getQuestion20Batteries() != null)
		{
			if (selected(brandQuestionform.getQuestion20Batteries()))
			{
				if (!(uniqueId.contains(brandQuestionform.getQuestion20Batteries())))
				{
					uniqueId.add(brandQuestionform.getQuestion20Batteries());
				}
			}
		}
		if (brandQuestionform.getQuestion20Bearingseals() != null)
		{
			if (selected(brandQuestionform.getQuestion20Bearingseals()))
			{
				if (!(uniqueId.contains(brandQuestionform.getQuestion20Bearingseals())))
				{
					uniqueId.add(brandQuestionform.getQuestion20Bearingseals());
				}
			}
		}
		if (brandQuestionform.getQuestion20Brakepads() != null)
		{
			if (selected(brandQuestionform.getQuestion20Brakepads()))
			{

				if (!(uniqueId.contains(brandQuestionform.getQuestion20Brakepads())))
				{
					uniqueId.add(brandQuestionform.getQuestion20Brakepads());
				}
			}
		}
		if (brandQuestionform.getQuestion20Bushings() != null)
		{
			if (selected(brandQuestionform.getQuestion20Bushings()))
			{
				if (!(uniqueId.contains(brandQuestionform.getQuestion20Bushings())))
				{
					uniqueId.add(brandQuestionform.getQuestion20Bushings());
				}
			}
		}
		if (brandQuestionform.getQuestion20coilSprings() != null)
		{
			if (selected(brandQuestionform.getQuestion20coilSprings()))
			{

				if (!(uniqueId.contains(brandQuestionform.getQuestion20coilSprings())))
				{
					uniqueId.add(brandQuestionform.getQuestion20coilSprings());
				}
			}
		}
		if (brandQuestionform.getQuestion20controlArms() != null)
		{
			if (selected(brandQuestionform.getQuestion20controlArms()))
			{
				if (!(uniqueId.contains(brandQuestionform.getQuestion20controlArms())))
				{
					uniqueId.add(brandQuestionform.getQuestion20controlArms());
				}
			}
		}
		if (brandQuestionform.getQuestion20engineComponents() != null)
		{
			if (selected(brandQuestionform.getQuestion20engineComponents()))
			{
				if (!(uniqueId.contains(brandQuestionform.getQuestion20engineComponents())))
				{
					uniqueId.add(brandQuestionform.getQuestion20engineComponents());
				}
			}
		}
		if (brandQuestionform.getQuestion20filters() != null)
		{
			if (selected(brandQuestionform.getQuestion20filters()))
			{
				if (!(uniqueId.contains(brandQuestionform.getQuestion20filters())))
				{
					uniqueId.add(brandQuestionform.getQuestion20filters());
				}
			}
		}
		if (brandQuestionform.getQuestion20gaskets() != null)
		{
			if (selected(brandQuestionform.getQuestion20gaskets()))
			{

				if (!(uniqueId.contains(brandQuestionform.getQuestion20gaskets())))
				{
					uniqueId.add(brandQuestionform.getQuestion20gaskets());
				}
			}
		}
		if (brandQuestionform.getQuestion20hubAssemblie() != null)
		{

			if (selected(brandQuestionform.getQuestion20hubAssemblie()))
			{
				if (!(uniqueId.contains(brandQuestionform.getQuestion20hubAssemblie())))
				{
					uniqueId.add(brandQuestionform.getQuestion20hubAssemblie());
				}
			}
		}
		if (brandQuestionform.getQuestion20idlerarms() != null)
		{
			if (selected(brandQuestionform.getQuestion20idlerarms()))
			{
				if (!(uniqueId.contains(brandQuestionform.getQuestion20idlerarms())))
				{
					uniqueId.add(brandQuestionform.getQuestion20idlerarms());
				}
			}
		}
		if (brandQuestionform.getQuestion20lighting() != null)
		{
			if (selected(brandQuestionform.getQuestion20lighting()))
			{
				if (!(uniqueId.contains(brandQuestionform.getQuestion20lighting())))
				{
					uniqueId.add(brandQuestionform.getQuestion20lighting());
				}

			}
		}
		if (brandQuestionform.getQuestion20Rotors() != null)
		{
			if (selected(brandQuestionform.getQuestion20Rotors()))
			{
				if (!(uniqueId.contains(brandQuestionform.getQuestion20Rotors())))
				{
					uniqueId.add(brandQuestionform.getQuestion20Rotors());
				}
			}
		}
		if (brandQuestionform.getQuestion20streeringStablizier() != null)
		{
			if (selected(brandQuestionform.getQuestion20streeringStablizier()))
			{
				if (!(uniqueId.contains(brandQuestionform.getQuestion20streeringStablizier())))
				{
					uniqueId.add(brandQuestionform.getQuestion20streeringStablizier());
				}
			}
		}
		if (brandQuestionform.getQuestion20structAssemblie() != null)
		{
			if (selected(brandQuestionform.getQuestion20structAssemblie()))
			{
				if (!(uniqueId.contains(brandQuestionform.getQuestion20structAssemblie())))
				{
					uniqueId.add(brandQuestionform.getQuestion20structAssemblie());
				}
			}
		}
		if (brandQuestionform.getQuestion20structMountr() != null)
		{
			if (selected(brandQuestionform.getQuestion20structMountr()))
			{
				if (!(uniqueId.contains(brandQuestionform.getQuestion20structMountr())))
				{
					uniqueId.add(brandQuestionform.getQuestion20structMountr());
				}
			}
		}
		if (brandQuestionform.getQuestion20swaybarlinkr() != null)
		{
			if (selected(brandQuestionform.getQuestion20swaybarlinkr()))
			{

				if (!(uniqueId.contains(brandQuestionform.getQuestion20swaybarlinkr())))
				{
					uniqueId.add(brandQuestionform.getQuestion20swaybarlinkr());
				}
			}
		}
		if (brandQuestionform.getQuestion20TieRods() != null)
		{
			if (selected(brandQuestionform.getQuestion20TieRods()))
			{
				if (!(uniqueId.contains(brandQuestionform.getQuestion20TieRods())))
				{
					uniqueId.add(brandQuestionform.getQuestion20TieRods());
				}
			}
		}
		if (brandQuestionform.getQuestion20UJoints() != null)
		{
			if (selected(brandQuestionform.getQuestion20UJoints()))
			{
				if (!(uniqueId.contains(brandQuestionform.getQuestion20UJoints())))
				{
					uniqueId.add(brandQuestionform.getQuestion20UJoints());
				}
			}
		}
		if (brandQuestionform.getQuestion20Wipers() != null)
		{
			if (selected(brandQuestionform.getQuestion20Wipers()))
			{
				if (!(uniqueId.contains(brandQuestionform.getQuestion20Wipers())))
				{
					uniqueId.add(brandQuestionform.getQuestion20Wipers());
				}
			}
		}


		final FMSurveyData surveyData = new FMSurveyData();
		surveyData.setUniqueID(uniqueId);
		try
		{
			final LoyaltySurveyResponseDTO resultFromCrm = loyaltySurveyFacade.populatebrandSurveyData(surveyData);
			if (resultFromCrm != null)
			{
				LOG.info("STATUS" + resultFromCrm.getStatus());
				if (resultFromCrm.getStatus().contains("Error:"))
				{
					//throw new IOException("Error in survey" + resultFromCrm.getStatus());
					return "Error in survey";
				}
			}

		}
		catch (final UnsupportedOperationException e)
		{
			// YTODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		}
		catch (final ClassNotFoundException e)
		{
			// YTODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		}
		catch (final SOAPException e)
		{
			// YTODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		}
		catch (final IOException e)
		{
			// YTODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		}
		return "success";
	}

	public String populatepartsBuyingformData(final PartsBuyingQuestionsForm partsquestionform)
	{


		final List<String> uniqueId = new ArrayList<String>();
		if (partsquestionform.getQ1Brakes() != null)
		{
			if (!(uniqueId.contains(partsquestionform.getQ1Brakes())))
			{
				uniqueId.add(partsquestionform.getQ1Brakes());
			}
		}
		if (partsquestionform.getQ1DriveLine() != null)
		{
			if (!(uniqueId.contains(partsquestionform.getQ1DriveLine())))
			{
				uniqueId.add(partsquestionform.getQ1DriveLine());
			}
		}
		if (partsquestionform.getQ1Engine() != null)
		{
			if (!(uniqueId.contains(partsquestionform.getQ1Engine())))
			{
				uniqueId.add(partsquestionform.getQ1Engine());
			}
		}
		if (partsquestionform.getQ1Filters() != null)
		{
			if (!(uniqueId.contains(partsquestionform.getQ1Filters())))
			{
				uniqueId.add(partsquestionform.getQ1Filters());
			}
		}
		if (partsquestionform.getQ1Gaskets() != null)
		{
			if (!(uniqueId.contains(partsquestionform.getQ1Gaskets())))
			{
				uniqueId.add(partsquestionform.getQ1Gaskets());
			}
		}
		if (partsquestionform.getQ1Lighting() != null)
		{
			if (!(uniqueId.contains(partsquestionform.getQ1Lighting())))
			{
				uniqueId.add(partsquestionform.getQ1Lighting());
			}
		}
		if (partsquestionform.getQ1Steering() != null)
		{
			if (!(uniqueId.contains(partsquestionform.getQ1Steering())))
			{
				uniqueId.add(partsquestionform.getQ1Steering());
			}
		}
		if (partsquestionform.getQ1Suspension() != null)
		{
			if (!(uniqueId.contains(partsquestionform.getQ1Suspension())))
			{
				uniqueId.add(partsquestionform.getQ1Suspension());
			}
		}
		if (partsquestionform.getQ1Wipers() != null)
		{
			if (!(uniqueId.contains(partsquestionform.getQ1Wipers())))
			{
				uniqueId.add(partsquestionform.getQ1Wipers());
			}
		}
		if (partsquestionform.getQ1Batteries() != null)
		{
			if (!(uniqueId.contains(partsquestionform.getQ1Batteries())))
			{
				uniqueId.add(partsquestionform.getQ1Batteries());
			}
		}
		if (partsquestionform.getQ2BetterFit() != null && !partsquestionform.getQ2BetterFit().isEmpty())
		{
			if (!(uniqueId.contains(partsquestionform.getQ2BetterFit())))
			{
				uniqueId.add(partsquestionform.getQ2BetterFit());
			}
		}
		if (partsquestionform.getQ2BetterPerformance() != null && !partsquestionform.getQ2BetterPerformance().isEmpty())
		{
			if (!(uniqueId.contains(partsquestionform.getQ2BetterPerformance())))
			{
				uniqueId.add(partsquestionform.getQ2BetterPerformance());
			}
		}
		if (partsquestionform.getQ2BetterPrice() != null && !partsquestionform.getQ2BetterPrice().isEmpty())
		{
			if (!(uniqueId.contains(partsquestionform.getQ2BetterPrice())))
			{
				uniqueId.add(partsquestionform.getQ2BetterPrice());
			}
		}
		if (partsquestionform.getQ2CustomerReq() != null && !partsquestionform.getQ2CustomerReq().isEmpty())
		{
			if (!(uniqueId.contains(partsquestionform.getQ2CustomerReq())))
			{
				uniqueId.add(partsquestionform.getQ2CustomerReq());
			}
		}
		if (partsquestionform.getQ2FavoriteBrand() != null && !partsquestionform.getQ2FavoriteBrand().isEmpty())
		{
			if (!(uniqueId.contains(partsquestionform.getQ2FavoriteBrand())))
			{
				uniqueId.add(partsquestionform.getQ2FavoriteBrand());
			}
		}
		if (partsquestionform.getQ2PartsForVehicle() != null && !partsquestionform.getQ2PartsForVehicle().isEmpty())
		{
			if (!(uniqueId.contains(partsquestionform.getQ2PartsForVehicle())))
			{
				uniqueId.add(partsquestionform.getQ2PartsForVehicle());
			}
		}
		if (partsquestionform.getQ2Other() != null && !partsquestionform.getQ2Other().isEmpty())
		{
			if (!(uniqueId.contains(partsquestionform.getQ2Other())))
			{
				uniqueId.add(partsquestionform.getQ2Other());
			}
		}

		if (partsquestionform.getQ3Primary() != null)
		{
			if (!(uniqueId.contains(partsquestionform.getQ3Primary())))
			{
				uniqueId.add(partsquestionform.getQ3Primary());
			}
		}
		if (partsquestionform.getQ3Secondary() != null)
		{
			if (!(uniqueId.contains(partsquestionform.getQ3Secondary())))
			{
				uniqueId.add(partsquestionform.getQ3Secondary());
			}
		}
		if (partsquestionform.getQ3FirstOther() != null)
		{
			if (!(uniqueId.contains(partsquestionform.getQ3FirstOther())))
			{
				uniqueId.add(partsquestionform.getQ3FirstOther());
			}
		}
		if (partsquestionform.getQ3SecondOther() != null)
		{
			if (!(uniqueId.contains(partsquestionform.getQ3SecondOther())))
			{
				uniqueId.add(partsquestionform.getQ3SecondOther());
			}
		}

		if (partsquestionform.getQ4Primary() != null)
		{
			if (!(uniqueId.contains(partsquestionform.getQ4Primary())))
			{
				uniqueId.add(partsquestionform.getQ4Primary());
			}
		}
		if (partsquestionform.getQ4Secondary() != null)
		{
			if (!(uniqueId.contains(partsquestionform.getQ4Secondary())))
			{
				uniqueId.add(partsquestionform.getQ4Secondary());
			}
		}
		if (partsquestionform.getQ4FirstOther() != null)
		{
			if (!(uniqueId.contains(partsquestionform.getQ4FirstOther())))
			{
				uniqueId.add(partsquestionform.getQ4FirstOther());
			}
		}
		if (partsquestionform.getQ4SecondOther() != null)
		{
			if (!(uniqueId.contains(partsquestionform.getQ4SecondOther())))
			{
				uniqueId.add(partsquestionform.getQ4SecondOther());
			}
		}
		if (partsquestionform.getQ5Primary() != null)
		{
			if (!(uniqueId.contains(partsquestionform.getQ5Primary())))
			{
				uniqueId.add(partsquestionform.getQ5Primary());
			}
		}
		if (partsquestionform.getQ5Secondary() != null)
		{
			if (!(uniqueId.contains(partsquestionform.getQ5Secondary())))
			{
				uniqueId.add(partsquestionform.getQ5Secondary());
			}
		}
		if (partsquestionform.getQ5FirstOther() != null)
		{
			if (!(uniqueId.contains(partsquestionform.getQ5FirstOther())))
			{
				uniqueId.add(partsquestionform.getQ5FirstOther());
			}
		}
		if (partsquestionform.getQ5SecondOther() != null)
		{
			if (!(uniqueId.contains(partsquestionform.getQ5SecondOther())))
			{
				uniqueId.add(partsquestionform.getQ5SecondOther());
			}
		}
		if (partsquestionform.getQ6Primary() != null)
		{
			if (!(uniqueId.contains(partsquestionform.getQ6Primary())))
			{
				uniqueId.add(partsquestionform.getQ6Primary());
			}
		}
		if (partsquestionform.getQ6Secondary() != null)
		{
			if (!(uniqueId.contains(partsquestionform.getQ6Secondary())))
			{
				uniqueId.add(partsquestionform.getQ6Secondary());
			}
		}
		if (partsquestionform.getQ6FirstOther() != null)
		{
			if (!(uniqueId.contains(partsquestionform.getQ6FirstOther())))
			{
				uniqueId.add(partsquestionform.getQ6FirstOther());
			}
		}
		if (partsquestionform.getQ6SecondOther() != null)
		{
			if (!(uniqueId.contains(partsquestionform.getQ6SecondOther())))
			{
				uniqueId.add(partsquestionform.getQ6SecondOther());
			}
		}

		if (partsquestionform.getQ7Primary() != null)
		{
			if (!(uniqueId.contains(partsquestionform.getQ7Primary())))
			{
				uniqueId.add(partsquestionform.getQ7Primary());
			}
		}
		if (partsquestionform.getQ7Secondary() != null)
		{
			if (!(uniqueId.contains(partsquestionform.getQ7Secondary())))
			{
				uniqueId.add(partsquestionform.getQ7Secondary());
			}
		}
		if (partsquestionform.getQ7FirstOther() != null)
		{
			if (!(uniqueId.contains(partsquestionform.getQ7FirstOther())))
			{
				uniqueId.add(partsquestionform.getQ7FirstOther());
			}
		}
		if (partsquestionform.getQ7SecondOther() != null)
		{
			if (!(uniqueId.contains(partsquestionform.getQ7SecondOther())))
			{
				uniqueId.add(partsquestionform.getQ7SecondOther());
			}
		}
		if (partsquestionform.getQ8Primary() != null)
		{
			if (!(uniqueId.contains(partsquestionform.getQ8Primary())))
			{
				uniqueId.add(partsquestionform.getQ8Primary());
			}
		}
		if (partsquestionform.getQ8Secondary() != null)
		{
			if (!(uniqueId.contains(partsquestionform.getQ8Secondary())))
			{
				uniqueId.add(partsquestionform.getQ8Secondary());
			}
		}
		if (partsquestionform.getQ8FirstOther() != null)
		{
			if (!(uniqueId.contains(partsquestionform.getQ8FirstOther())))
			{
				uniqueId.add(partsquestionform.getQ8FirstOther());
			}
		}
		if (partsquestionform.getQ8SecondOther() != null)
		{
			if (!(uniqueId.contains(partsquestionform.getQ8SecondOther())))
			{
				uniqueId.add(partsquestionform.getQ8SecondOther());
			}
		}
		if (partsquestionform.getQ9Primary() != null)
		{
			if (!(uniqueId.contains(partsquestionform.getQ9Primary())))
			{
				uniqueId.add(partsquestionform.getQ9Primary());
			}
		}
		if (partsquestionform.getQ9Secondary() != null)
		{
			if (!(uniqueId.contains(partsquestionform.getQ9Secondary())))
			{
				uniqueId.add(partsquestionform.getQ9Secondary());
			}
		}
		if (partsquestionform.getQ9FirstOther() != null)
		{
			if (!(uniqueId.contains(partsquestionform.getQ9FirstOther())))
			{
				uniqueId.add(partsquestionform.getQ9FirstOther());
			}
		}
		if (partsquestionform.getQ9SecondOther() != null)
		{
			if (!(uniqueId.contains(partsquestionform.getQ9SecondOther())))
			{
				uniqueId.add(partsquestionform.getQ9SecondOther());
			}
		}
		if (partsquestionform.getQ10Primary() != null)
		{
			if (!(uniqueId.contains(partsquestionform.getQ10Primary())))
			{
				uniqueId.add(partsquestionform.getQ10Primary());
			}
		}
		if (partsquestionform.getQ10Secondary() != null)
		{
			if (!(uniqueId.contains(partsquestionform.getQ10Secondary())))
			{
				uniqueId.add(partsquestionform.getQ10Secondary());
			}
		}
		if (partsquestionform.getQ10FirstOther() != null)
		{
			if (!(uniqueId.contains(partsquestionform.getQ10FirstOther())))
			{
				uniqueId.add(partsquestionform.getQ10FirstOther());
			}
		}
		if (partsquestionform.getQ10SecondOther() != null)
		{
			if (!(uniqueId.contains(partsquestionform.getQ10SecondOther())))
			{
				uniqueId.add(partsquestionform.getQ10SecondOther());
			}
		}
		if (partsquestionform.getQ11Primary() != null)
		{
			if (!(uniqueId.contains(partsquestionform.getQ11Primary())))
			{
				uniqueId.add(partsquestionform.getQ11Primary());
			}
		}
		if (partsquestionform.getQ11Secondary() != null)
		{
			if (!(uniqueId.contains(partsquestionform.getQ11Secondary())))
			{
				uniqueId.add(partsquestionform.getQ11Secondary());
			}
		}
		if (partsquestionform.getQ11FirstOther() != null)
		{
			if (!(uniqueId.contains(partsquestionform.getQ11FirstOther())))
			{
				uniqueId.add(partsquestionform.getQ11FirstOther());
			}
		}
		if (partsquestionform.getQ11SecondOther() != null)
		{
			if (!(uniqueId.contains(partsquestionform.getQ11SecondOther())))
			{
				uniqueId.add(partsquestionform.getQ11SecondOther());
			}
		}
		if (partsquestionform.getQ12Primary() != null)
		{
			if (!(uniqueId.contains(partsquestionform.getQ12Primary())))
			{
				uniqueId.add(partsquestionform.getQ12Primary());
			}
		}
		if (partsquestionform.getQ12Secondary() != null)
		{
			if (!(uniqueId.contains(partsquestionform.getQ12Secondary())))
			{
				uniqueId.add(partsquestionform.getQ12Secondary());
			}
		}
		if (partsquestionform.getQ12FirstOther() != null)
		{
			if (!(uniqueId.contains(partsquestionform.getQ12FirstOther())))
			{
				uniqueId.add(partsquestionform.getQ12FirstOther());
			}
		}
		if (partsquestionform.getQ12SecondOther() != null)
		{
			if (!(uniqueId.contains(partsquestionform.getQ12SecondOther())))
			{
				uniqueId.add(partsquestionform.getQ12SecondOther());
			}
		}

		if (partsquestionform.getQ13Primary() != null)
		{
			if (!(uniqueId.contains(partsquestionform.getQ13Primary())))
			{
				uniqueId.add(partsquestionform.getQ13Primary());
			}
		}
		if (partsquestionform.getQ13Secondary() != null)
		{
			if (!(uniqueId.contains(partsquestionform.getQ13Secondary())))
			{
				uniqueId.add(partsquestionform.getQ13Secondary());
			}
		}
		if (partsquestionform.getQ13FirstOther() != null)
		{
			if (!(uniqueId.contains(partsquestionform.getQ13FirstOther())))
			{
				uniqueId.add(partsquestionform.getQ13FirstOther());
			}
		}
		if (partsquestionform.getQ13SecondOther() != null)
		{
			if (!(uniqueId.contains(partsquestionform.getQ13SecondOther())))
			{
				uniqueId.add(partsquestionform.getQ13SecondOther());
			}
		}
		if (partsquestionform.getQ14Primary() != null)
		{
			if (!(uniqueId.contains(partsquestionform.getQ14Primary())))
			{
				uniqueId.add(partsquestionform.getQ14Primary());
			}
		}
		if (partsquestionform.getQ14Secondary() != null)
		{
			if (!(uniqueId.contains(partsquestionform.getQ14Secondary())))
			{
				uniqueId.add(partsquestionform.getQ14Secondary());
			}
		}
		if (partsquestionform.getQ14FirstOther() != null)
		{
			if (!(uniqueId.contains(partsquestionform.getQ14FirstOther())))
			{
				uniqueId.add(partsquestionform.getQ14FirstOther());
			}
		}
		if (partsquestionform.getQ14SecondOther() != null)
		{
			if (!(uniqueId.contains(partsquestionform.getQ14SecondOther())))
			{
				uniqueId.add(partsquestionform.getQ14SecondOther());
			}
		}
		if (partsquestionform.getQ15BrandsOffered() != null && !partsquestionform.getQ15BrandsOffered().isEmpty())
		{
			if (!(uniqueId.contains(partsquestionform.getQ15BrandsOffered())))
			{
				uniqueId.add(partsquestionform.getQ15BrandsOffered());
			}
		}
		if (partsquestionform.getQ15PartAvailability() != null && !partsquestionform.getQ15PartAvailability().isEmpty())
		{
			if (!(uniqueId.contains(partsquestionform.getQ15PartAvailability())))
			{
				uniqueId.add(partsquestionform.getQ15PartAvailability());
			}
		}
		if (partsquestionform.getQ15Price() != null && !partsquestionform.getQ15Price().isEmpty())
		{
			if (!(uniqueId.contains(partsquestionform.getQ15Price())))
			{
				uniqueId.add(partsquestionform.getQ15Price());
			}
		}
		if (partsquestionform.getQ15Proximity() != null && !partsquestionform.getQ15Proximity().isEmpty())
		{
			if (!(uniqueId.contains(partsquestionform.getQ15Proximity())))
			{
				uniqueId.add(partsquestionform.getQ15Proximity());
			}
		}
		if (partsquestionform.getQ15Service() != null && !partsquestionform.getQ15Service().isEmpty())
		{
			if (!(uniqueId.contains(partsquestionform.getQ15Service())))
			{
				uniqueId.add(partsquestionform.getQ15Service());
			}
		}
		if (partsquestionform.getQ15Warranty() != null && !partsquestionform.getQ15Warranty().isEmpty())
		{
			if (!(uniqueId.contains(partsquestionform.getQ15Warranty())))
			{
				uniqueId.add(partsquestionform.getQ15Warranty());
			}
		}
		final FMSurveyData surveyData = new FMSurveyData();
		surveyData.setUniqueID(uniqueId);
		try
		{
			final LoyaltySurveyResponseDTO resultFromCrm = loyaltySurveyFacade.populatePartsBuyingSurveyData(surveyData);
			if (resultFromCrm != null)
			{
				LOG.info("STATUS" + resultFromCrm.getStatus());
				if (resultFromCrm.getStatus().contains("Error:"))
				{
					//throw new IOException("Error in survey" + resultFromCrm.getStatus());
					return "Error in survey";
				}
			}
		}
		catch (final UnsupportedOperationException e)
		{
			// YTODO Auto-generated catch block
			return "buyingSurveyError";
		}
		catch (final ClassNotFoundException e)
		{
			// YTODO Auto-generated catch block
			return "buyingSurveyError";
		}
		catch (final SOAPException e)
		{
			// YTODO Auto-generated catch block
			return "buyingSurveyError";
		}
		catch (final IOException e)
		{
			// YTODO Auto-generated catch block
			return "buyingSurveyError";
		}
		return "success";

	}

	private boolean selected(final String selectedAnswer)
	{
		if (selectedAnswer != null && selectedAnswer.contains(":"))
		{
			LOG.info("selected value" + selectedAnswer);
			final String[] answerArray = selectedAnswer.split(":");
			final String answerPart2 = answerArray[1];
			if (Integer.parseInt(answerPart2) != 0)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		return false;
	}



}
