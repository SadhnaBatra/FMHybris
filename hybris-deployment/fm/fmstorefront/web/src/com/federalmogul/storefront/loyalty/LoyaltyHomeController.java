/**
 * 
 */
package com.federalmogul.storefront.loyalty;

import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.store.services.BaseStoreService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.xml.soap.SOAPException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.federalmogul.core.event.ReferAFriendEmailProcessEvent;
import com.federalmogul.core.model.FMCustomerModel;
import com.federalmogul.core.model.ReferAFriendEmailProcessModel;
import com.federalmogul.core.network.dao.FMUserSearchDAO;
import com.federalmogul.facades.account.FMCustomerFacade;
import com.federalmogul.facades.loyalty.data.ReferAFriendBean;
import com.federalmogul.facades.loyalty.data.ReferAFriendBeanList;
import com.federalmogul.facades.user.data.FMCustomerData;
import com.federalmogul.storefront.breadcrumb.impl.LoyaltyHomeBreadcrumbBuilder;
import com.federalmogul.storefront.constants.WebConstants;
import com.federalmogul.storefront.controllers.ControllerConstants;
import com.federalmogul.storefront.controllers.pages.AbstractPageController;
import com.federalmogul.storefront.controllers.util.GlobalMessages;
import com.federalmogul.storefront.forms.B2BCustomerForm;
import com.federalmogul.storefront.forms.ReferAfriendForm;
import com.fm.falcon.webservices.dto.LoyaltyHistoryBean;
import com.fm.falcon.webservices.dto.LoyaltyHistoryRequestDTO;
import com.fm.falcon.webservices.dto.LoyaltyHistoryResponseDTO;
import com.fm.falcon.webservices.dto.LoyaltyItemDetailsList;
import com.fm.falcon.webservices.soap.helper.LoyaltyHistoryHelper;
import com.federalmogul.facades.account.FMCustomerFacade;
import com.fm.falcon.webservices.soap.helper.LoyaltyActivityHistoryHelper;
import com.federalmogul.storefront.breadcrumb.ResourceBreadcrumbBuilder;
import com.fm.falcon.webservices.dto.PromoCodeRequestDTO;
import com.fm.falcon.webservices.dto.PromoCodeResponseDTO;
import com.fm.falcon.webservices.dto.PromoCodeResponseFault;
import com.fm.falcon.webservices.soap.helper.PromoCodeHelper;
/**
 * @author SI279688
 * 
 */
@Controller
@RequestMapping(value = "/**/loyalty")
public class LoyaltyHomeController extends AbstractPageController
{
	private static final String FM_ACCOUNT_CMS_PAGE = "RegisterUserPage";
	private static final Logger LOG = Logger.getLogger(LoyaltyHomeController.class);
	private static final String FM_LOYALTY_HISTORY_PAGE = "LoyaltyHomePage";
	private static final String FMADDRESS_BOOK_CMS_PAGE = "AddressBookPage";

	@Resource
	protected FMCustomerFacade fmCustomerFacade;

	private static final String FM_PRODUCT_LIST_PAGE = "LoyaltySearchResultPage";

	private static final String FM_LOYALTY_EARN_POINTS_PAGE = "loyaltyWayToEarnPoints";

    private static final String FM_LOYALTY_REFER_A_FRIEND_PAGE = "LoyaltyReferPage";
	private static final String FM_LOYALTY_SUPPORT_TOPIC_FAQs_PAGE = "LoyaltyFAQsPage";
	
	private static final String FM_LOYALTY_SUPPORT_TOPIC_RULEs_PAGE = "LoyaltyRulesPage";
	
	private static final String FM_LOYALTY_SURVEY_PAGE = "loyaltySurvey";

	@Resource
	protected UserService userService;

	@Autowired
	FMUserSearchDAO fmUserSearchDAO;

	@Autowired
	private EventService eventService;

	@Resource(name = "loyaltyBreadcrumbBuilder")
	protected LoyaltyHomeBreadcrumbBuilder loyaltyBreadcrumbBuilder;

	@Resource
	private BaseStoreService baseStoreService;

	@Resource
	private BaseSiteService baseSiteService;

	@Resource
	private CommonI18NService commonI18NService;

	@Resource(name = "accountBreadcrumbBuilder")
	private ResourceBreadcrumbBuilder accountBreadcrumbBuilder;

	@RequestMapping(method = RequestMethod.GET)
	public String getLoyaltyHome(final Model model) throws CMSItemNotFoundException
	{
		storeCmsPageInModel(model, getContentPageForLabelOrId(FM_ACCOUNT_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FM_ACCOUNT_CMS_PAGE));
		return ControllerConstants.Views.Pages.Account.LoyaltyHomePage;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/checkout")
	public String getLoyaltyCheckout(final Model model) throws CMSItemNotFoundException
	{
		storeCmsPageInModel(model, getContentPageForLabelOrId(FM_ACCOUNT_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FM_ACCOUNT_CMS_PAGE));
		return ControllerConstants.Views.Pages.Account.LoyaltyCheckoutPage;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/history")
	public String getLoyaltyHistory(final Model model) throws CMSItemNotFoundException, UnsupportedOperationException,
			ClassNotFoundException, SOAPException, IOException

	{

		LOG.info("inside history method");

		final LoyaltyActivityHistoryHelper loyaltyHelper = new LoyaltyActivityHistoryHelper();
		final LoyaltyHistoryRequestDTO requestDto = new LoyaltyHistoryRequestDTO();
		//	requestDto.setSapId("10013191");
		requestDto.setMembership_Id(fmCustomerFacade.getCurrentFMCustomer().getCrmContactId());
		requestDto.setFlag("H");
		final LoyaltyHistoryResponseDTO responseDto = (LoyaltyHistoryResponseDTO) loyaltyHelper.sendSOAPMessage(requestDto);

		if (responseDto != null)
		{
			model.addAttribute("ActivityResult", responseDto.getResultActivity());

			model.addAttribute("RedemptionResult", responseDto.getResultRedemption());

		//LOG.info("TOTAL NUMBER OF ACTIVITIES" + responseDto.getResultActivity().size());

			//LOG.info("TOTAL NUMBER OF redemption" + responseDto.getResultRedemption().size());
			int activityCount = 0;
			int redemptionCount = 0;
			final List<LoyaltyHistoryBean> result = responseDto.getResultRedemption();
			if (result != null)
			{
				for (final LoyaltyHistoryBean bean1 : result)
				{
					LOG.info("ACTIVITYID" + bean1.getActivityId());
					LOG.info("POSTING DATE" + bean1.getPostingDate());
					final List<LoyaltyItemDetailsList> items = bean1.getItemsDetail();
					for (final LoyaltyItemDetailsList item : items)
					{
						LOG.info("PRODUCTID" + item.getProductId());
						LOG.info("ProductActivity" + item.getProductActivity());
						LOG.info("Points" + item.getPoints());

						redemptionCount += Double.parseDouble(item.getPoints());
					}
				}
			}

			final List<LoyaltyHistoryBean> result2 = responseDto.getResultActivity();
			if (result2 != null)
			{
				for (final LoyaltyHistoryBean bean1 : result2)
				{
					LOG.info("ACTIVITYID" + bean1.getActivityId());
					LOG.info("POSTING DATE" + bean1.getPostingDate());
					final List<LoyaltyItemDetailsList> items = bean1.getItemsDetail();
					for (final LoyaltyItemDetailsList item : items)
					{
						LOG.info("PRODUCTID" + item.getProductId());
						LOG.info("ProductActivity" + item.getProductActivity());
						LOG.info("Points" + item.getPoints());

						activityCount += Double.parseDouble(item.getPoints());
					}
				}

			}
			model.addAttribute("totalredemptionPoints", redemptionCount);
			model.addAttribute("totalActivityPoints", activityCount);
		}
		else
		{
			GlobalMessages.addErrorMessage(model, "Unable to get the response from CRM");
		}
		/*
		 * storeCmsPageInModel(model, getContentPageForLabelOrId(FM_LOYALTY_HISTORY_PAGE));
		 * setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FM_LOYALTY_HISTORY_PAGE));
		 * model.addAttribute("breadcrumbs", loyaltyBreadcrumbBuilder.getLoyaltyHistoryBreadcrumbs("History"));
		 * model.addAttribute("metaRobots", "no-index,no-follow");
		 */

		storeCmsPageInModel(model, getContentPageForLabelOrId(FMADDRESS_BOOK_CMS_PAGE));
		model.addAttribute("clickedlink", "clickedLoyaltyHistory");
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FMADDRESS_BOOK_CMS_PAGE));
		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getFMBreadcrumbs("Loyalty History"));
		model.addAttribute("metaRobots", "no-index,no-follow");
		return ControllerConstants.Views.Pages.Account.LoyaltyHistoryPage;
	}





	@RequestMapping(method = RequestMethod.GET, value = "/searchResult")
	public String getRewardsSearchResult(final Model model) throws CMSItemNotFoundException
	{

		storeCmsPageInModel(model, getContentPageForLabelOrId(FM_PRODUCT_LIST_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FM_PRODUCT_LIST_PAGE));


		return ControllerConstants.Views.Pages.Account.LoyaltySearchResultPage;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/earn")
	public String getLoyaltyEarnPoints(final Model model) throws CMSItemNotFoundException
	{

		storeCmsPageInModel(model, getContentPageForLabelOrId(FM_LOYALTY_EARN_POINTS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FM_LOYALTY_EARN_POINTS_PAGE));
                model.addAttribute("TotalPoints", getSessionService().getAttribute(WebConstants.BALANCE_LOYALTY_POINTS));
		model.addAttribute("breadcrumbs", loyaltyBreadcrumbBuilder.getLoyaltyBreadcrumbs("Earn"));
		model.addAttribute("metaRobots", "no-index,no-follow");
		return ControllerConstants.Views.Pages.Account.loyaltyWayToEarnPoints;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/FAQs")
	public String getLoyaltyFAQs(final Model model) throws CMSItemNotFoundException
	{

		LOG.info("###### getLoyaltyFAQs ######## ");

		storeCmsPageInModel(model, getContentPageForLabelOrId(FM_LOYALTY_SUPPORT_TOPIC_FAQs_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FM_LOYALTY_SUPPORT_TOPIC_FAQs_PAGE));
		model.addAttribute("TotalPoints", getSessionService().getAttribute(WebConstants.BALANCE_LOYALTY_POINTS));
		model.addAttribute("breadcrumbs", loyaltyBreadcrumbBuilder.getLoyaltyBreadcrumbs("Earn"));
		model.addAttribute("metaRobots", "no-index,no-follow");
		return ControllerConstants.Views.Pages.Account.loyaltySupportTopicFAQs;
	}
	@RequestMapping(method = RequestMethod.GET, value = "/Rules")
	public String getLoyaltyRules(final Model model) throws CMSItemNotFoundException
	{

		LOG.info("###### getLoyaltyRules ######## ");

		storeCmsPageInModel(model, getContentPageForLabelOrId(FM_LOYALTY_SUPPORT_TOPIC_RULEs_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FM_LOYALTY_SUPPORT_TOPIC_RULEs_PAGE));
		model.addAttribute("TotalPoints", getSessionService().getAttribute(WebConstants.BALANCE_LOYALTY_POINTS));
		model.addAttribute("breadcrumbs", loyaltyBreadcrumbBuilder.getLoyaltyBreadcrumbs("Earn"));
		model.addAttribute("metaRobots", "no-index,no-follow");
		return ControllerConstants.Views.Pages.Account.loyaltySupportTopicFAQs;
	}


	@RequestMapping(method = RequestMethod.GET, value = "/refer")
	public String getLoyaltyReferPoints(final Model model) throws CMSItemNotFoundException
	{

		storeCmsPageInModel(model, getContentPageForLabelOrId(FM_LOYALTY_REFER_A_FRIEND_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FM_LOYALTY_REFER_A_FRIEND_PAGE));
		model.addAttribute("TotalPoints", getSessionService().getAttribute(WebConstants.BALANCE_LOYALTY_POINTS));
		model.addAttribute("breadcrumbs", loyaltyBreadcrumbBuilder.getLoyaltyReferBreadcrumbs("Earn"));
		model.addAttribute("metaRobots", "no-index,no-follow");
		final List<ReferAFriendBean> newList = new ArrayList<ReferAFriendBean>();
		for (int i = 0; i < 5; i++)
		{
			newList.add(new ReferAFriendBean());
		}
		final ReferAFriendBeanList referList = new ReferAFriendBeanList();
		referList.setReferals(newList);
		model.addAttribute("ReferalForm", referList);
		return ControllerConstants.Views.Pages.Account.loyaltyReferaFriendpage;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/referEmail")
	public String sendLoyaltyReferalEmail(@Valid @ModelAttribute("ReferalForm") final ReferAFriendBeanList referalForm,
			final Model model) throws CMSItemNotFoundException
	{

		storeCmsPageInModel(model, getContentPageForLabelOrId(FM_LOYALTY_REFER_A_FRIEND_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FM_LOYALTY_REFER_A_FRIEND_PAGE));
		model.addAttribute("TotalPoints", getSessionService().getAttribute(WebConstants.BALANCE_LOYALTY_POINTS));
		model.addAttribute("breadcrumbs", loyaltyBreadcrumbBuilder.getLoyaltyReferBreadcrumbs("Earn"));
		model.addAttribute("metaRobots", "no-index,no-follow");


		//model.addAttribute("ReferalForm", new B2BCustomerForm());

		final List<ReferAFriendBean> referalListBean = referalForm.getReferals();


		for (final ReferAFriendBean referForm : referalListBean)
		{
			if (referForm.getMailId() != null && referForm.getMailId() != "")
			{
				LOG.info("INSIDE IF CONDITON");
				final ReferAFriendEmailProcessModel refer = new ReferAFriendEmailProcessModel();
				refer.setReferer((FMCustomerModel) userService.getCurrentUser());
				refer.setRefereeEmail(referForm.getMailId());
				refer.setRefereeFirstName(referForm.getFName());
				refer.setRefereeLastName(referForm.getLName());
				refer.setSite(baseSiteService.getCurrentBaseSite());
				refer.setStore(baseStoreService.getCurrentBaseStore());
				refer.setCustomer(fmUserSearchDAO.getUserForUID(fmCustomerFacade.getCurrentFMCustomer().getUid()));
				final ReferAFriendEmailProcessEvent referevent = new ReferAFriendEmailProcessEvent(refer);
				eventService.publishEvent(initializeEvent(referevent,
						fmUserSearchDAO.getUserForUID(fmCustomerFacade.getCurrentFMCustomer().getUid())));
			}
		}
		final List<ReferAFriendBean> newList = new ArrayList<ReferAFriendBean>();
		for (int i = 0; i < 5; i++)
		{
			newList.add(new ReferAFriendBean());
		}
		final ReferAFriendBeanList referList = new ReferAFriendBeanList();
		referList.setReferals(newList);
		model.addAttribute("ReferalForm", referList);

		GlobalMessages.addErrorMessage(model, "Thank you for referring a technician");

		return ControllerConstants.Views.Pages.Account.loyaltyReferaFriendpage;
	}

	public ReferAFriendEmailProcessEvent initializeEvent(final ReferAFriendEmailProcessEvent event,
			final FMCustomerModel customerModel)
	{
		LOG.info("Inside initialise event");
		try
		{
			LOG.info("inside try");
			event.setBaseStore(baseStoreService.getCurrentBaseStore());
			LOG.info("BASE STORE" + baseStoreService.getCurrentBaseStore().getName());
			event.setSite(baseSiteService.getCurrentBaseSite());
			LOG.info("BASE SITE" + baseSiteService.getCurrentBaseSite().getName());
			event.setCustomer(customerModel);
			LOG.info("FROM EVEN CUSTOMER ID" + event.getCustomer().getUid());
			event.setLanguage(commonI18NService.getCurrentLanguage());
			event.setCurrency(commonI18NService.getCurrentCurrency());
			LOG.info("b4 returning event");
			return event;
		}
		catch (final Exception e)
		{
			LOG.error(e.getMessage());
		}
		return null;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/promocode")
	public String getPromoCode(@RequestParam(value = "selectedPromo") final String promo, @RequestParam(value = "promotype") final String promotype,final Model model)
			throws CMSItemNotFoundException
	{	
		final FMCustomerData currentCustomer = fmCustomerFacade.getCurrentFMCustomer();
		final PromoCodeRequestDTO request = new PromoCodeRequestDTO();
		request.setPromoCode(promo);
		request.setMembershipID(currentCustomer.getCrmContactId());
		request.setContactPersonId(currentCustomer.getB2cLoyaltyMembershipId());
		request.setProspectID(currentCustomer.getFmunit().getProspectId());
		request.setServiceName("Request");
		String errormessage = "";
		String severity = "";
		final PromoCodeHelper promoRequest = new PromoCodeHelper();
		try
		{
			final PromoCodeResponseDTO promoResponse = (PromoCodeResponseDTO) promoRequest.sendSOAPMessage(request);
		
			if (promoResponse != null)
			{
				errormessage = promoResponse.getSeverityText();
				severity = promoResponse.getSeverity();
			}
		}
		catch (final UnsupportedOperationException e)
		{
			// YTODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (final ClassNotFoundException e)
		{
			// YTODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (final SOAPException e)
		{
			// YTODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (final IOException e)
		{
			// YTODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("errortype", errormessage);
		model.addAttribute("promotype", promotype);


		if (severity.equalsIgnoreCase("S"))
		{
			return "fragments/loyalty/promoSuccess";
		}

		
		return "fragments/loyalty/promoFailure";


	}
}
