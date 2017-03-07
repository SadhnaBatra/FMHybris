/**
 * 
 */
package com.federalmogul.storefront.controllers.pages;

import de.hybris.platform.b2bacceleratorservices.company.CompanyB2BCommerceService;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;
import de.hybris.platform.commercefacades.i18n.I18NFacade;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.order.CartService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.util.Config;
import de.hybris.platform.core.model.security.PrincipalGroupModel;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.federalmogul.core.model.FMCustomerAccountModel;
import com.federalmogul.core.model.FMCustomerModel;
import com.federalmogul.core.network.FMNetworkService;
import com.federalmogul.facades.account.FMCustomerAccountReturnOrderFacade;
import com.federalmogul.facades.account.FMCustomerFacade;
import com.federalmogul.facades.address.data.FMB2bAddressData;
import com.federalmogul.facades.network.FMNetworkFacade;
import com.federalmogul.facades.search.impl.DefaultFMCompetitorFacade;
import com.federalmogul.facades.user.data.FMCustomerAccountData;
import com.federalmogul.falcon.core.model.FMCsrAccountListModel;
import com.federalmogul.storefront.annotations.RequireHardLogIn;
import com.federalmogul.storefront.breadcrumb.ResourceBreadcrumbBuilder;
import com.federalmogul.storefront.constants.WebConstants;
import com.federalmogul.storefront.controllers.ControllerConstants;
import com.federalmogul.storefront.controllers.util.GlobalMessages;
import com.federalmogul.storefront.forms.FMAccountSearchForm;
import com.federalmogul.storefront.forms.FMAddressForm;
import com.federalmogul.storefront.forms.FMapplicationUsageForm;
import com.federalmogul.storefront.forms.OrderUploadForm;
import com.fmo.wom.business.as.OrderStatusASUSCanImpl;
import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.domain.CreditCheckBO;
import com.fmo.wom.domain.SalesOrganizationBO;
import com.fmo.wom.integration.SAPService;


/**
 * @author SR279690 Controller for home page.
 * 
 * 
 */

@Controller
@Scope("tenant")
@RequestMapping("/csr-emulation")
@SessionAttributes(
{ "csrAccountEmulation", "accountId","accountNm", WebConstants.USER_SHIPTO_FULL_ADDRESS, WebConstants.USER_SOLDTO_FULL_ADDRESS })
public class FMCSRAccountPageController extends SearchPageController
{
	protected SessionService sessionService;

	private static final String CSR_ACCOUNT_UID = "{unitUid:.*}";


	private static final Logger LOG = Logger.getLogger(FMCSRAccountPageController.class);

	final OrderStatusASUSCanImpl orderHeardeService = new OrderStatusASUSCanImpl();

	private static final int CHECK_RESULT_SIZE = 0;

	@Autowired
	DefaultFMCompetitorFacade defaultFMCompetitorFacade;

	@Resource(name = "accountBreadcrumbBuilder")
	private ResourceBreadcrumbBuilder accountBreadcrumbBuilder;


	@Resource
	private UserService userService;

	/*
	 * @Resource(name = "userFacade") protected UserFacade userFacade;
	 */

	/*
	 * @Resource(name = "checkoutFacade") private CheckoutFacade checkoutFacade;
	 */

	/*
	 * @Resource(name = "b2bCustomerFacade") protected CustomerFacade customerFacade;
	 */

	@Resource
	protected FMCustomerFacade fmCustomerFacade;

	/*
	 * @Resource protected FMUserFacade fmUserfacade;
	 */
	/*
	 * @Resource protected B2BCommerceUserFacade b2bCommerceUserFacade;
	 */
	@Resource(name = "i18NFacade")
	private I18NFacade i18NFacade;

	@Autowired
	private FMNetworkFacade fmNetworkFacade;

	/*
	 * @Autowired private FMCustomerFacadeImpl fmCustomerFacadeimpl;
	 */

	/*
	 * @Deprecated
	 * 
	 * @Resource(name = "cartFacade") private CartFacade cartFacade;
	 */
	@Resource(name = "fmNetworkService")
	private FMNetworkService fmNetworkService;

	@Resource
	private ModelService modelService;

	@Resource
	private CartService cartService;

	@Resource(name = "customerAccountReturnOrderFacade")
	protected FMCustomerAccountReturnOrderFacade fmCustomerAccountReturnOrderFacade;


	@Resource
	protected CompanyB2BCommerceService companyB2BCommerceService;

	@RequestMapping(value = "/start-emulate/" + CSR_ACCOUNT_UID, method = RequestMethod.GET)
	@RequireHardLogIn
	public String startEmulate(@PathVariable("unitUid") final String accountId, final Model model) throws CMSItemNotFoundException
	{


		LOG.info("Inside return-order-history Method ==>");
		LOG.info("account from linkkkkkkkkkkk" + accountId);
		model.addAttribute("accountId", accountId);
		
		getSessionService().setAttribute("accountIdCSR", accountId);
		final FMCustomerAccountData accountData = fmNetworkFacade.getAccountID(accountId);


		final UserModel currentUserModel = userService.getCurrentUser();
		final boolean isUserAnonymous = currentUserModel == null || userService.isAnonymousUser(currentUserModel);
		final List<String> groupUID = new ArrayList<String>();
		if(!isUserAnonymous){
			final Set<PrincipalGroupModel> groupss = currentUserModel.getGroups();
			for (final PrincipalGroupModel groupModel : groupss)
			{
				final String groupId = groupModel.getUid();
				groupUID.add(groupId);
			}
		}

		if (isUserAnonymous || groupUID.contains("FMB2T"))
		{
			return REDIRECT_PREFIX + ROOT;
		}
		final FMCustomerModel fmCustomerModel = fmNetworkService.getFMCustomerForUid(fmCustomerFacade.getCurrentFMCustomer()
				.getUid());

		/*
		 * FMCsrAccountListModel csrModel = new FMCsrAccountListModel();
		 * 
		 * csrModel.setAccountnum(accountId); csrModel.setDate(new Date());
		 * 
		 * modelService.save(csrModel);
		 */

		LOG.info("FMCUSTOMER NAME" + fmCustomerModel.getUid());
		LOG.info("LISTSIZE" + fmCustomerModel.getCsrAccountEntry().size());
		final List<FMCsrAccountListModel> csrAccountList = fmCustomerModel.getCsrAccountEntry();
		getSessionService().setAttribute(SessionContext.USER, userService.getAdminUser());

		final FMCsrAccountListModel csrm = new FMCsrAccountListModel();
		csrm.setAccountnum(accountId);
		csrm.setDate(new Date());
		csrm.setCsrUserID(currentUserModel.getUid());
		csrm.setNabsAccountCode(accountData.getNabsAccountCode());
		getSessionService().setAttribute("csremulatedNABSId", accountData.getNabsAccountCode());
		final List<FMCsrAccountListModel> newCSRList = new ArrayList<FMCsrAccountListModel>();
		if (csrAccountList.size() != 0)
		{
			LOG.info("Inside csraccount if loop " + csrAccountList.size());
			newCSRList.addAll(csrAccountList);
			final List<String> fmAccountList = new ArrayList<String>();
			for (final FMCsrAccountListModel fmCsrAccountListModel : csrAccountList)
			{

				fmAccountList.add(fmCsrAccountListModel.getAccountnum());

				if (fmCsrAccountListModel.getAccountnum().equals(accountId))
				{
					LOG.info("--------------accountID is same as");
					fmCsrAccountListModel.setDate(new Date());
					fmCsrAccountListModel.setCsrUserID(currentUserModel.getUid());
					fmCsrAccountListModel.setNabsAccountCode(accountData.getNabsAccountCode());
					modelService.save(fmCsrAccountListModel);
				}


			}

			if (!fmAccountList.contains(accountId))
			{
				newCSRList.add(csrm);
			}


		}
		else
		{
			newCSRList.add(csrm);
		}

		fmCustomerModel.setCsrAccountEntry(newCSRList);

		getSessionService().setAttribute(SessionContext.USER, userService.getAdminUser());
		modelService.save(fmCustomerModel);


		model.addAttribute("csrAccountList", csrAccountList);

		getSessionService().setAttribute(SessionContext.USER, currentUserModel);

		//final List<FMB2bAddressData> unitAddress = accountData.getUnitAddress();
		setB2BAccountDetails(accountData, model);

		model.addAttribute("csrAccountEmulation", true);
		getSessionService().setAttribute("emulationCSR", true);
		storeCmsPageInModel(model, getContentPageForLabelOrId("FMB2BHomePage"));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId("FMB2BHomePage"));
		updatePageTitle(model, getContentPageForLabelOrId("FMB2BHomePage"));
		model.addAttribute("customerType", "b2BCustomer");
		final String uploadOrderStatus = getSessionService().getAttribute("uploadStatus");
		if (uploadOrderStatus != null && !uploadOrderStatus.isEmpty())
		{
			model.addAttribute("uploadStatus", uploadOrderStatus);
			getSessionService().setAttribute("uploadStatus", "");
		}
		model.addAttribute("orderUpload", new OrderUploadForm());
		model.addAttribute("addressForm", new FMAddressForm());

		final String soldToAcc = (String) getSessionService().getAttribute(WebConstants.SOLDTO_ACCOUNT_ID);
		final FMCustomerAccountModel fmCustomerAccModel = (FMCustomerAccountModel) companyB2BCommerceService
				.getUnitForUid(soldToAcc);
		final SalesOrganizationBO salesOrg = new SalesOrganizationBO();
		salesOrg.setCode(fmCustomerAccModel.getSalesorg());
		model.addAttribute("accountNm","-"+fmCustomerAccModel.getLocName());
		getSessionService().setAttribute("accountNm", "-"+fmCustomerAccModel.getLocName());
		model.addAttribute("manualShipToFlagEnable", (null != fmCustomerAccModel.getShipToLimitFlag() && fmCustomerAccModel
				.getShipToLimitFlag().equalsIgnoreCase("C")) ? "No" : "Yes");
		try
		{
			final CreditCheckBO creditCheckBO = SAPService.doCreditCheck(soldToAcc, salesOrg);
			LOG.info(" creditCheckBO .. getCreditBlock :: " + creditCheckBO.getCreditBlock());
			LOG.info("IS Customer Order Blocked ... :: " + creditCheckBO.getOrderBlock());

			getSessionService().setAttribute(WebConstants.FM_CREDIT_BLOCK, creditCheckBO.getCreditBlock());
			getSessionService().setAttribute(WebConstants.FM_ORDER_BLOCK, creditCheckBO.getOrderBlock());
			model.addAttribute(WebConstants.FM_CREDIT_BLOCK, creditCheckBO.getCreditBlock());
			model.addAttribute(WebConstants.FM_ORDER_BLOCK, creditCheckBO.getOrderBlock());
		}
		catch (final WOMExternalSystemException e)
		{
			getSessionService().setAttribute(WebConstants.FM_CREDIT_BLOCK, "Y");
			getSessionService().setAttribute(WebConstants.FM_ORDER_BLOCK, "Y");
		}
		catch (final Exception e)
		{
			getSessionService().setAttribute(WebConstants.FM_CREDIT_BLOCK, "Y");
			getSessionService().setAttribute(WebConstants.FM_ORDER_BLOCK, "Y");
		}
		boolean displayIframe=getSessionService().getAttribute("iframe");
		LOG.info("startEmulate() -> displayIframe:: " + displayIframe);
		model.addAttribute("displayIFrame", displayIframe);
		return ControllerConstants.Views.Pages.Account.AccountBToBigB;
	}

	protected void updatePageTitle(final Model model, final AbstractPageModel cmsPage)
	{
		storeContentPageTitleInModel(model, getPageTitleResolver().resolveHomePageTitle(cmsPage.getTitle()));
	}

	@RequestMapping(value = "/get-accounts", method = RequestMethod.POST)
	@RequireHardLogIn
	public String getAccounts(@Valid final FMAccountSearchForm fmAccountSearchForm, final BindingResult bindingResult,
			final Model model, final HttpServletRequest request, final RedirectAttributes redirectModel)
			throws CMSItemNotFoundException
	{
		String accountId = fmAccountSearchForm.getAccountNumber();
		final boolean accountNumberFlag = true;


		LOG.info("accountId :: " + accountId);
		if (accountId.isEmpty())
		{
			LOG.info("Inside If Loop");
			final List<FMCustomerAccountData> listaccountData = fmNetworkFacade.getAllAccountsforCSR();
			model.addAttribute("accounts", listaccountData);
			model.addAttribute("test_condition", new String("allAccounts"));

		}
		else
		{
			LOG.info("Inside else Loop");
			final FMCustomerAccountData accountData = fmNetworkFacade.getAccountID(accountId);
			if (accountData != null)
			{
				model.addAttribute("accounts", accountData);
				model.addAttribute("test_condition", new String("UniqueAccount"));
			}
			else
			{
				GlobalMessages.addErrorMessage(model, "Invalid Account");
			}

		}
		model.addAttribute("accountNumberFlag", accountNumberFlag);

		model.addAttribute("fmaccountSearchForm", fmAccountSearchForm);
		storeCmsPageInModel(model, getContentPageForLabelOrId("CSRHomePage"));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId("CSRHomePage"));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId("CSRHomePage"));
		boolean displayIframe=getSessionService().getAttribute("iframe");
		LOG.info(" getAccounts()>> iframe:: " + displayIframe);
		model.addAttribute("displayIFrame", displayIframe);

		return ControllerConstants.Views.Pages.Account.AccountCSR;
	}



	@RequestMapping(value = "/get-accountsByNabsCode", method = RequestMethod.POST)
	@RequireHardLogIn
	public String getAccountsByNabs(@Valid @ModelAttribute("fmaccountSearchForm") final FMAccountSearchForm fmAccountSearchForm,
			final BindingResult bindingResult, final Model model, final HttpServletRequest request,
			final RedirectAttributes redirectModel) throws CMSItemNotFoundException
	{
		final String accountId = fmAccountSearchForm.getNabsAccount();
		final boolean accountNumberFlagnabs = true;


		LOG.info("accountId :: " + accountId);
		if (accountId.isEmpty())
		{
			LOG.info("Inside If Loop");
			final List<FMCustomerAccountData> listaccountData = fmNetworkFacade.getAllAccountsforCSR();
			model.addAttribute("accountsNabs", listaccountData);
			model.addAttribute("test_condition_Nabs", new String("allAccounts"));

		}
		else
		{
			LOG.info("Inside else Loop");
			final FMCustomerAccountData accountData = fmNetworkFacade.getAccountByNabs(accountId);
			if (accountData != null)
			{
				model.addAttribute("accountsNabs", accountData);
				model.addAttribute("test_condition_Nabs", new String("UniqueAccount"));
			}
			else
			{
				GlobalMessages.addErrorMessage(model, "Invalid Account");
			}

		}
		model.addAttribute("accountNumberFlagnabs", accountNumberFlagnabs);

		model.addAttribute("fmaccountSearchForm", fmAccountSearchForm);
		storeCmsPageInModel(model, getContentPageForLabelOrId("CSRHomePage"));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId("CSRHomePage"));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId("CSRHomePage"));
		boolean displayIframe=getSessionService().getAttribute("iframe");
		LOG.info(" getAccountsByNabs -> search by nabs account>> iframe:: " + displayIframe);
		model.addAttribute("displayIFrame", displayIframe);

		return ControllerConstants.Views.Pages.Account.AccountCSR;
	}

	@RequestMapping(value = "/get-account-address", method = RequestMethod.POST)
	@RequireHardLogIn
	public String getAccountAddress(@Valid final FMAccountSearchForm fmAccountSearchForm, final BindingResult bindingResult,
			final Model model, final HttpServletRequest request, final RedirectAttributes redirectModel)
			throws CMSItemNotFoundException
	{

		final boolean addressFlag = true;

		final List<FMCustomerAccountData> listaccData = fmNetworkFacade.fetchAddress(fmAccountSearchForm.getCompanyName(),
				fmAccountSearchForm.getLine1(), fmAccountSearchForm.getTownCity(), fmAccountSearchForm.getPostcode(),
				fmAccountSearchForm.getRegion());
		LOG.info("controller list size" + listaccData.size());
		if (listaccData.size() > 0)
		{

			final Iterator i = listaccData.iterator();
			while (i.hasNext())
			{
				final FMCustomerAccountData accData = (FMCustomerAccountData) i.next();

				final List<AddressData> address = accData.getAddresses();
				final Iterator a = address.iterator();
				while (a.hasNext())
				{
					final AddressData add = (AddressData) a.next();
					LOG.info("AddressData::" + add);
					/*
					 * LOG.info("UId" + accData.getUid()); LOG.info("Company" + add.getCompanyName()); LOG.info("Line1" +
					 * add.getLine1()); LOG.info("town" + add.getTown()); LOG.info("Postalcode" + add.getPostalCode());
					 * LOG.info("region" + add.getRegion().getIsocode());
					 */
				}
				/*
				 * LOG.info("UId" + accData.getUid()); LOG.info("Company" +
				 * accData.getAddresses().iterator().next().getCompanyName()); LOG.info("Line1" +
				 * accData.getAddresses().iterator().next().getLine1()); LOG.info("town" +
				 * accData.getAddresses().iterator().next().getTown()); LOG.info("Postalcode" +
				 * accData.getAddresses().iterator().next().getPostalCode()); LOG.info("region" +
				 * accData.getAddresses().iterator().next().getRegion().getIsocode());
				 */
			}


			model.addAttribute("unitAddressData", listaccData);

		}
		else
		{
			GlobalMessages.addErrorMessage(model, "Invalid Address");
		}
		model.addAttribute("check", new String("bye"));
		model.addAttribute("addressFlag", addressFlag);
		model.addAttribute("fmaccountSearchForm", fmAccountSearchForm);
		storeCmsPageInModel(model, getContentPageForLabelOrId("CSRHomePage"));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId("CSRHomePage"));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId("CSRHomePage"));
		boolean displayIframe=getSessionService().getAttribute("iframe");
		LOG.info("getAccountAddress >> iframe:: " + displayIframe);
		model.addAttribute("displayIFrame", displayIframe);

		return ControllerConstants.Views.Pages.Account.AccountCSR;

	}

	public I18NFacade getI18NFacade()
	{
		return i18NFacade;
	}

	@RequestMapping(value = "/end-emulate")
	@RequireHardLogIn
	public void endEmulate(final Model model, final HttpServletResponse response) throws CMSItemNotFoundException
	{
		getSessionService().removeAttribute(WebConstants.SOLDTO_ACCOUNT_ID);
		getSessionService().removeAttribute(WebConstants.SHIPTO_ACCOUNT_ID);
		LOG.info("Before removing session End-EM" + getSessionService().getAttribute(WebConstants.USER_SOLDTO_FULL_ADDRESS));
		LOG.info("Before removing session End-EM" + getSessionService().getAttribute(WebConstants.USER_SHIPTO_FULL_ADDRESS));


		getSessionService().removeAttribute(WebConstants.USER_SOLDTO_FULL_ADDRESS);
		getSessionService().removeAttribute(WebConstants.USER_SHIPTO_FULL_ADDRESS);
		getSessionService().setAttribute("shiptToAddress", null);
		getSessionService().removeAttribute("shiptToAddress");
		LOG.info("After removing session " + getSessionService().getAttribute(WebConstants.SOLDTO_ACCOUNT_ID));
		LOG.info("After removing session " + getSessionService().getAttribute(WebConstants.SHIPTO_ACCOUNT_ID));
		LOG.info("After removing session " + getSessionService().getAttribute(WebConstants.USER_SOLDTO_FULL_ADDRESS));
		LOG.info("After removing session " + getSessionService().getAttribute(WebConstants.USER_SHIPTO_FULL_ADDRESS));

		model.addAttribute(WebConstants.USER_SOLDTO_FULL_ADDRESS, "");
		model.addAttribute(WebConstants.USER_SHIPTO_FULL_ADDRESS, "");


		cartService.removeSessionCart();

		model.addAttribute("csrAccountEmulation", false);
		getSessionService().setAttribute("emulationCSR", false);
		model.addAttribute("accountId", "");
		model.addAttribute("accountNm", "");
		getSessionService().setAttribute("accountNm", "");
		getSessionService().setAttribute("accountIdCSR", "");
		getSessionService().setAttribute("csremulatedNABSId", "");
		model.addAttribute("fmaccountSearchForm", new FMAccountSearchForm());
		model.addAttribute("accountNumberFlag", true);
		storeCmsPageInModel(model, getContentPageForLabelOrId("CSRHomePage"));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId("CSRHomePage"));
		updatePageTitle(model, getContentPageForLabelOrId("CSRHomePage"));
		model.addAttribute("customerType", "CSRCustomer");
		final List<FMCsrAccountListModel> csrAccountList = fmCustomerFacade.getFMCSRAccountList();


		model.addAttribute("csrAccountList", csrAccountList);

		// Here we set the response header instead of simply returning the redirect as a string because our ROOT is now outside of Hybris...
		response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
		response.setHeader("Location", AEM_ROOT);
	}




	@RequestMapping(method = RequestMethod.GET, value = "/application-usage")
	public String Applicationusage(final Model model) throws CMSItemNotFoundException
	{
		LOG.info("inside app usage report method");
		boolean showReport=false;
		final UserModel currentUserModel = userService.getCurrentUser();
		final boolean isUserAnonymous = currentUserModel == null || userService.isAnonymousUser(currentUserModel);
		final List<String> groupUID = new ArrayList<String>();
		if(!isUserAnonymous && null!=currentUserModel){
			final Set<PrincipalGroupModel> groupss = currentUserModel.getGroups();
			for (final PrincipalGroupModel groupModel : groupss)
			{
				final String groupId = groupModel.getUid();
				groupUID.add(groupId);
			}
		}
		if(groupUID.contains("FMCSR")){
			showReport=true;
		}
		if(!showReport){
			return REDIRECT_PREFIX + ROOT;
		}
		final FMapplicationUsageForm fmApplicationUsageForm = new FMapplicationUsageForm();
		getApplicationUsage(fmApplicationUsageForm, model);
		return ControllerConstants.Views.Pages.Account.ApplicationUsageReport;
	}

	@SuppressWarnings("null")
	@RequestMapping(value = "/csrReport", method = RequestMethod.POST)
	public String ApplicationUsageSearch(final FMapplicationUsageForm fmApplicationUsageForm, final Model model,
			final HttpServletRequest request) throws CMSItemNotFoundException
	{
		getApplicationUsage(fmApplicationUsageForm, model);
		return ControllerConstants.Views.Pages.Account.ApplicationUsageReport;

	}

	private String getOrderSearchDate(final String dType)
	{
		final Format formatter = new SimpleDateFormat("MM/dd/yyyy");
		final Calendar startCal = Calendar.getInstance();
		if (dType != null && dType.equalsIgnoreCase("startDate"))
		{
			final int days = Integer.parseInt(Config.getParameter(WebConstants.MYACCOUNT_ORDERHISTORY_START_DATE));
			startCal.add(Calendar.DAY_OF_YEAR, -1 * days);
			return formatter.format(startCal.getTime());
		}
		else
		{
			return formatter.format(startCal.getTime());
		}

	}



	// to get the by defalut application usage for all the SAP accounts to placed order in last 1 week or custom defined search
	// param 1 - form
	//param 2 - model
	private boolean getApplicationUsage(final FMapplicationUsageForm fmApplicationUsageForm, final Model model)
	{
		try
		{
			model.addAttribute("fmApplicationUsageForm", fmApplicationUsageForm);
			model.addAttribute("metaRobots", "no-index,no-follow");
			model.addAttribute("clickedlink", "ClickedApplicationUsageReport");
			storeCmsPageInModel(model, getContentPageForLabelOrId("ApplicationUsageReportPage"));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId("ApplicationUsageReportPage"));
			model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getFMBreadcrumbs("Application Usage Report"));

			final List<String> userUsageDetails = defaultFMCompetitorFacade.getAppilcationUsageReports(fmApplicationUsageForm
					.getAccountNumber(), fmApplicationUsageForm.getUserid(),
					fmApplicationUsageForm.getStartDate() != null ? fmApplicationUsageForm.getStartDate()
							: getOrderSearchDate("startDate"),
					fmApplicationUsageForm.getEndDate() != null ? fmApplicationUsageForm.getEndDate() : getOrderSearchDate("endDate"));
			if (userUsageDetails != null)
			{
				model.addAttribute("userUsageDetails", userUsageDetails);
			}
			else
			{
				model.addAttribute("userUsageDetails", userUsageDetails);
				return false;
			}
		}
		catch (final CMSItemNotFoundException e)
		{
			// YTODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		catch (final Exception e)
		{
			return false;
		}
		return true;

	}

}
