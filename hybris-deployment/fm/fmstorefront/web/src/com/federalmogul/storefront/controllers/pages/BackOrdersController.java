/**
 * 
 */
package com.federalmogul.storefront.controllers.pages;

import de.hybris.platform.b2bacceleratorservices.company.CompanyB2BCommerceService;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;
import de.hybris.platform.commercefacades.product.ProductFacade;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.security.PrincipalGroupModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.user.UserService;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.federalmogul.core.model.FMCustomerAccountModel;
import com.federalmogul.core.network.dao.FMUserSearchDAO;
import com.federalmogul.facades.account.FMCustomerFacade;
import com.federalmogul.facades.account.FMUserFacade;
import com.federalmogul.facades.network.FMNetworkFacade;
import com.federalmogul.facades.user.data.FMCustomerAccountData;
import com.federalmogul.storefront.breadcrumb.ResourceBreadcrumbBuilder;
import com.federalmogul.storefront.constants.WebConstants;
import com.federalmogul.storefront.controllers.ControllerConstants;
import com.federalmogul.storefront.controllers.helper.BackOrderHeaderHelper;
import com.federalmogul.storefront.controllers.util.MailUtil;
import com.federalmogul.storefront.forms.EmailForm;
import com.fmo.wom.business.as.BackOrderASUSCanImpl;
import com.fmo.wom.business.as.OrderStatusASUSCanImpl;
import com.fmo.wom.business.util.backorder.BackOrderHelper;
import com.fmo.wom.business.util.backorder.BackOrderSearchValues;
import com.fmo.wom.domain.AccountBO;
import com.fmo.wom.domain.AddressBO;
import com.fmo.wom.domain.BackOrderBO;
import com.fmo.wom.domain.BillToAcctBO;
import com.fmo.wom.domain.CountryBO;
import com.fmo.wom.domain.CustomerSalesOrgBO;
import com.fmo.wom.domain.OrderUnitBO;
import com.fmo.wom.domain.SalesOrganizationBO;
import com.fmo.wom.domain.SapAcctBO;
import com.fmo.wom.domain.ShipToAcctBO;
import com.fmo.wom.domain.ShippedItemBO;
import com.fmo.wom.domain.ShippedOrderBO;
import com.fmo.wom.domain.ShippingUnitBO;
import com.fmo.wom.domain.enums.OrderSearchFilter;
import com.federalmogul.facades.uploadorder.UploadOrderFacade;
import com.federalmogul.facades.account.FMCustomerFacade;
import com.federalmogul.core.network.dao.FMUserSearchDAO;


/**
 * uploadOrder for B2B
 * 
 */

@Controller
@Scope("tenant")
@RequestMapping(value = "/backOrder")
public class BackOrdersController extends AbstractPageController
{
	private static final Logger LOG = Logger.getLogger(BackOrdersController.class);

	final OrderStatusASUSCanImpl orderHeardeService = new OrderStatusASUSCanImpl();

	private static final String FMORDER_HISTORY_CMS_PAGE = "OrderHistoryPage";
	protected static final String REDIRECT_TO_ORDER_HISTORY = REDIRECT_PREFIX + "/my-fmaccount/order-history";

	@Resource
	private UserService userService;

	@Resource(name = "accountBreadcrumbBuilder")
	private ResourceBreadcrumbBuilder accountBreadcrumbBuilder;

	@Resource
	protected CompanyB2BCommerceService companyB2BCommerceService;

	@Resource
	protected FMCustomerFacade fmCustomerFacade;

	
	

	@Resource(name = "productService")
	private ProductService productService;

	@Resource(name = "b2bProductFacade")
	private ProductFacade productFacade;

	@Autowired
	private FMNetworkFacade fmNetworkFacade;
   @Autowired
	FMUserSearchDAO fmUserSearchDAO;
	@Autowired
	private UploadOrderFacade uploadorderFacade;


	@Resource
	protected FMUserFacade fmUserfacade;

	@RequestMapping(value = "/back-order", method = RequestMethod.POST)
	public String backOrder(@RequestParam(value = "ponumber", required = false) final String ponumber,
			@RequestParam(value = "partnumber", required = false) final String partnumber, final Model model)

	{
		LOG.info("backOrder");

		final String soldToAcc = (String) getSessionService().getAttribute(WebConstants.SOLDTO_ACCOUNT_ID);
		FMCustomerAccountModel fmCustomerAccModel = (FMCustomerAccountModel) companyB2BCommerceService.getUnitForUid(soldToAcc);
		final String shipToAcc = (String) getSessionService().getAttribute(WebConstants.SHIPTO_ACCOUNT_ID);
		final FMCustomerAccountModel sfmCustomerAccModel = (FMCustomerAccountModel) companyB2BCommerceService
				.getUnitForUid(shipToAcc);

		final List<AccountBO> billToAccounts = new ArrayList<AccountBO>();

		BackOrderHelper backOrderHelper;
		final BackOrderHeaderHelper backOrderHeaderHelper;

		//Mahaveer JOBBER change
		final String loggedUserType = (String) getSessionService().getAttribute("logedUserType");
		if (loggedUserType != null && "ShipTo".equalsIgnoreCase(loggedUserType))
		{
			fmCustomerAccModel = sfmCustomerAccModel;
			final List<FMCustomerAccountModel> soldToUnitModels = fmUserSearchDAO.getSoldToShipToUnitUid(shipToAcc);
			if (soldToUnitModels != null && !soldToUnitModels.isEmpty())
			{
				for (final FMCustomerAccountModel customerAccountModel : soldToUnitModels)
				{
					billToAccounts.add(getBillTOAccount(customerAccountModel));
				}
			}
		}
		LOG.info("OrderHeader loggedUserType ==>" + loggedUserType);
		final BillToAcctBO billToAccount = getBillTOAccount(fmCustomerAccModel);
		final ShipToAcctBO shipToAccount = getShiptoAccount(sfmCustomerAccModel);
		final BackOrderASUSCanImpl backOrderService = new BackOrderASUSCanImpl();


		final String orderNumber = ponumber;
		final String partNumber = partnumber;

		LOG.info("orderNumber = " + orderNumber + " partNumber = " + partNumber);
		List<BackOrderBO> backOrdersList = null;

		try
		{
			if (loggedUserType != null && "ShipTo".equalsIgnoreCase(loggedUserType))
			{
				backOrdersList = backOrderService.searchBackOrders(billToAccounts, shipToAccount, orderNumber, partNumber);

			}
			else
			{
				backOrdersList = backOrderService.searchBackOrders(billToAccount, orderNumber, partNumber);
			}
		}
		catch (final Exception e)
		{
			LOG.error("Exception while searching BackOrders ...." + e.getMessage());
		}
		if (backOrdersList != null)
		{
			LOG.info("Found BackOrders .. List Size ::  " + backOrdersList.size());

			//Added by sai for Sorting
			backOrderHelper = new BackOrderHelper(backOrdersList);
			backOrderHeaderHelper = new BackOrderHeaderHelper();
			final List<BackOrderSearchValues> backOrderSearchValuesList = backOrderHeaderHelper
					.performOrderDateSort(backOrderHelper);

			getSessionService().setAttribute("sessionBackOrderResult", backOrderSearchValuesList);
			getSessionService().setAttribute("sessionBackOrderHelper", backOrderHelper);

			model.addAttribute("backOrderSearchValues", backOrderSearchValuesList);
		}
		else
		{
			model.addAttribute("BackOrders", backOrdersList);
		}
		model.addAttribute("emailForm", new EmailForm());

		return ControllerConstants.Views.Pages.Account.FMBackOrder;

	}

	private static BillToAcctBO getBillTOAccount(final FMCustomerAccountModel fmCustomerAccModel)
	{
		final BillToAcctBO billToAcct = new BillToAcctBO();

		billToAcct.setAccountName(fmCustomerAccModel.getLocName());
		billToAcct.setAccountCode(fmCustomerAccModel.getNabsAccountCode());
		final SapAcctBO sapAccount = new SapAcctBO();
		sapAccount.setSapAccountCode(fmCustomerAccModel.getUid()); //ORDERS - 10013283 RETURNS - 10013332

		final CustomerSalesOrgBO custSalesOrg = new CustomerSalesOrgBO();
		custSalesOrg.setDistributionChannel(fmCustomerAccModel.getDistributionChannel());
		custSalesOrg.setDivision(fmCustomerAccModel.getDivision());

		final SalesOrganizationBO salesOrg = new SalesOrganizationBO();
		salesOrg.setCode(fmCustomerAccModel.getSalesorg());
		custSalesOrg.setSalesOrganization(salesOrg);

		sapAccount.setCustomerSalesOrganization(custSalesOrg);

		billToAcct.setSapAccount(sapAccount);
		billToAcct.setAddress(setAddress(fmCustomerAccModel));
		return billToAcct;
	}

	private static ShipToAcctBO getShiptoAccount(final FMCustomerAccountModel fmCustomerAccModel)
	{
		final ShipToAcctBO shipToAcct = new ShipToAcctBO();
		shipToAcct.setAccountCode(fmCustomerAccModel.getNabsAccountCode());
		final SapAcctBO sapAccount = new SapAcctBO();
		sapAccount.setSapAccountCode(fmCustomerAccModel.getUid());
		shipToAcct.setSapAccount(sapAccount);
		shipToAcct.setAddress(setAddress(fmCustomerAccModel));
		return shipToAcct;
	}

	protected void updatePageTitle(final Model model, final AbstractPageModel cmsPage)
	{
		storeContentPageTitleInModel(model, getPageTitleResolver().resolveHomePageTitle(cmsPage.getTitle()));
	}

	/**
	 * 
	 * @param sortBy
	 * @param model
	 * @return
	 * @throws CMSItemNotFoundException
	 */
	@RequestMapping(value = "/sort-Back-Order", method = RequestMethod.POST)
	public String getSortBackOrders(@RequestParam(value = "sortBy") final String sortBy, final Model model)

	{
		LOG.info("**** Invoking sortInvoiceHeader **** SortBy :: " + sortBy);

		final List<BackOrderSearchValues> backOrderSearchValuesList = getSessionService().getAttribute("sessionBackOrderResult");
		final BackOrderHelper backOrderHelper = getSessionService().getAttribute("sessionBackOrderHelper");
		BackOrderHeaderHelper backOrderHeaderHelper;

		if ((backOrderSearchValuesList != null && (!backOrderSearchValuesList.isEmpty())) && (backOrderHelper != null))
		{
			if (sortBy.equals(WebConstants.PURCHASE_ORDER_NUMBER))
			{
				backOrderHeaderHelper = new BackOrderHeaderHelper();
				final List<BackOrderSearchValues> sortedBackOrderSearchValuesList = backOrderHeaderHelper
						.performPurchaseOrderNumberSort(backOrderHelper);
				LOG.info("sortedBackOrderSearchValuesList :: " + sortedBackOrderSearchValuesList.size());
				model.addAttribute("backOrderSearchValues", sortedBackOrderSearchValuesList);
			}
			if (sortBy.equals(WebConstants.ORDER_NUMBER))
			{
				backOrderHeaderHelper = new BackOrderHeaderHelper();
				final List<BackOrderSearchValues> sortedBackOrderSearchValuesList = backOrderHeaderHelper
						.performOrderNumberSort(backOrderHelper);
				LOG.info("sortedBackOrderSearchValuesList :: " + sortedBackOrderSearchValuesList.size());
				model.addAttribute("backOrderSearchValues", sortedBackOrderSearchValuesList);
			}
			if (sortBy.equals(WebConstants.PART_NUMBER))
			{
				backOrderHeaderHelper = new BackOrderHeaderHelper();
				final List<BackOrderSearchValues> sortedBackOrderSearchValuesList = backOrderHeaderHelper
						.performPartNumberSort(backOrderHelper);
				LOG.info("sortedBackOrderSearchValuesList :: " + sortedBackOrderSearchValuesList.size());
				model.addAttribute("backOrderSearchValues", sortedBackOrderSearchValuesList);
			}
			if (sortBy.equals(WebConstants.ORDER_DATE))
			{
				backOrderHeaderHelper = new BackOrderHeaderHelper();
				final List<BackOrderSearchValues> sortedBackOrderSearchValuesList = backOrderHeaderHelper
						.performOrderDateSort(backOrderHelper);
				LOG.info("sortedBackOrderSearchValuesList :: " + sortedBackOrderSearchValuesList.size());
				model.addAttribute("backOrderSearchValues", sortedBackOrderSearchValuesList);
			}
		}
		model.addAttribute("emailForm", new EmailForm());

		return ControllerConstants.Views.Pages.Account.FMBackOrder;


	}

	@RequestMapping(value = "/backorder-details", method = RequestMethod.GET)
	public String getBackOrderDetails(@RequestParam(value = "backOrderNumber") final String backOrderNumber, final Model model)
			throws CMSItemNotFoundException
	{
		LOG.info("Inside backorder-details Method ==>");
		String userGroup = "";
		final List<String> groupUID = new ArrayList<String>();
		final UserModel user = userService.getCurrentUser();
		final Set<PrincipalGroupModel> groupss = user.getGroups();
		final Map<String, ProductData> pictureMap = new HashMap<String, ProductData>();
		for (final PrincipalGroupModel groupModel : groupss)
		{
			final String groupId = groupModel.getUid();
			groupUID.add(groupId);
		}
		if (groupUID.contains("FMB2BB"))
		{
			userGroup = "FMB2BB";
		}
		else
		{
			userGroup = "FMB2sbC";
		}
		LOG.info("userGroup ==>" + userGroup);

		final String soldToAcc = (String) getSessionService().getAttribute(WebConstants.SOLDTO_ACCOUNT_ID);
		final FMCustomerAccountModel fmCustomerAccModel = (FMCustomerAccountModel) companyB2BCommerceService
				.getUnitForUid(soldToAcc);
		final String shipToAcc = (String) getSessionService().getAttribute(WebConstants.SHIPTO_ACCOUNT_ID);
		final FMCustomerAccountModel sfmCustomerAccModel = (FMCustomerAccountModel) companyB2BCommerceService
				.getUnitForUid(shipToAcc);
		final List<AccountBO> billToAccounts = new ArrayList<AccountBO>();
		//Mahaveer JOBBER change
		final String loggedUserType = (String) getSessionService().getAttribute("logedUserType");
		if (loggedUserType != null && "ShipTo".equalsIgnoreCase(loggedUserType))
		{
			//fmCustomerAccModel = sfmCustomerAccModel;
			final List<FMCustomerAccountModel> soldToUnitModels = fmUserSearchDAO.getSoldToShipToUnitUid(shipToAcc);
			if (soldToUnitModels != null && !soldToUnitModels.isEmpty())
			{
				for (final FMCustomerAccountModel customerAccountModel : soldToUnitModels)
				{
					billToAccounts.add(getBillTOAccount(customerAccountModel));
				}
			}
		}
		final BillToAcctBO billToAccount = getBillTOAccount(fmCustomerAccModel);
		final ShipToAcctBO shipToAccount = getShiptoAccount(sfmCustomerAccModel);
		final String customerPurchaseOrderNumber = "";
		final String masterOrderNumber = backOrderNumber;
		model.addAttribute("orderNumber", backOrderNumber);
		//final Date startDate = getOrderSearchStartDate();
		//final Date endDate = getOrderSearchEndDate();
		final OrderSearchFilter filter = OrderSearchFilter.ALL;
		final String orderRetrunFlag = "ORDERS";
		List<ShippedOrderBO> orderHeaderList = null;
		ProductModel productModel = null;
		ProductData productData = null;
		try
		{
			if (loggedUserType != null && "ShipTo".equalsIgnoreCase(loggedUserType))
			{
				orderHeaderList = orderHeardeService.searchOrders(billToAccounts, shipToAccount, masterOrderNumber,
						customerPurchaseOrderNumber, null, null, filter, orderRetrunFlag);
			}
			else
			{
				orderHeaderList = orderHeardeService.searchOrders(billToAccount, masterOrderNumber, customerPurchaseOrderNumber,
						null, null, filter, orderRetrunFlag);
			}
			LOG.info("  Shipped Orders ... " + orderHeaderList);

			if (orderHeaderList != null && (!orderHeaderList.isEmpty()))
			{
				final ShippedOrderBO shippedOrderBO = orderHeaderList.get(0);
				orderHeardeService.getOrderDetail(shippedOrderBO, orderRetrunFlag);
				LOG.info("shippedOrderBO ==>" + shippedOrderBO);
				LOG.info("billToAccount :: " + billToAccount);
				shippedOrderBO.setBillToAccount(billToAccount);
				for (final OrderUnitBO orderUnit : shippedOrderBO.getOrderUnitList())
				{
					for (final ShippingUnitBO shippingUnitBO : orderUnit.getShippingUnitList())
					{

						for (final ShippedItemBO shippedItemBOList : shippingUnitBO.getShippedItemsList())
						{
							try
							{
								productModel = productService
										.getProductForCode(shippedItemBOList.getDisplayPartNumber() != null ? shippedItemBOList
												.getDisplayPartNumber() : shippedItemBOList.getPartNumber());
								//LOG.info(" productModel++++++++++++++  " + productModel);
								productData = productFacade.getProductForOptions(productModel, Arrays.asList(ProductOption.SUMMARY,
										ProductOption.DESCRIPTION, ProductOption.GALLERY, ProductOption.IMAGES, ProductOption.BASIC,
										ProductOption.CATEGORIES, ProductOption.VARIANT_MATRIX_BASE, ProductOption.VARIANT_MATRIX_PRICE,
										ProductOption.VARIANT_MATRIX_MEDIA, ProductOption.VARIANT_MATRIX_STOCK));
								//LOG.info(" productData--------------  " + productData);
								pictureMap.put(
										shippedItemBOList.getDisplayPartNumber() != null ? shippedItemBOList.getDisplayPartNumber()
												: shippedItemBOList.getPartNumber(), productData);
							}
							catch (final Exception e)
							{
								pictureMap.put(
										shippedItemBOList.getDisplayPartNumber() != null ? shippedItemBOList.getDisplayPartNumber()
												: shippedItemBOList.getPartNumber(), null);
								LOG.error(" Exception While Fetching Shipped Orders  " + e.getMessage());
							}
						}
					}
				}
				final AccountBO shipToAcct = shippedOrderBO.getShipToAccount();
				if (null != shipToAcct)
				{
					String shipToNabsAccountCode = shipToAcct.getAccountCode();
					String shipToECCAccountCode = null;
					if (null != shipToNabsAccountCode)
					{
						FMCustomerAccountData accountData = null;

						try
						{
							accountData = fmNetworkFacade.getAccountByNabs(shipToNabsAccountCode);
						}
						catch (final Exception e)
						{
							e.printStackTrace();
						}
						if (accountData != null)
						{
							shipToECCAccountCode = accountData.getUid();
						}
						else
						{

							final FMCustomerAccountModel ShipToAccount = (FMCustomerAccountModel) companyB2BCommerceService
									.getUnitForUid(shipToNabsAccountCode);
							if (null != ShipToAccount)
							{

								shipToECCAccountCode = shipToNabsAccountCode;
								shipToNabsAccountCode = ShipToAccount.getNabsAccountCode();
							}

						}
					}
					else
					{
						final FMCustomerAccountModel ShipToAccount = (FMCustomerAccountModel) companyB2BCommerceService
								.getUnitForUid(shipToNabsAccountCode);
						if (null != ShipToAccount)
						{
							shipToECCAccountCode = shipToNabsAccountCode;
							shipToNabsAccountCode = ShipToAccount.getNabsAccountCode();
						}
					}
					if (null != shipToNabsAccountCode && null != shipToECCAccountCode)
					{
						//shipToAcct.setAccountCode(shipToNabsAccountCode + "/" + shipToECCAccountCode);
						LOG.info("shipToAccountCode :: " + shipToNabsAccountCode + "/" + shipToECCAccountCode);
						model.addAttribute("shipToAccountCode", shipToNabsAccountCode + "/" + shipToECCAccountCode);

					}
				}
				LOG.info("Test inside the Backorder ");
				model.addAttribute("pictureMap", pictureMap);
				model.addAttribute("OrderStatusDetail", shippedOrderBO);
			}
		}
		catch (final Exception e)
		{
			LOG.error(" Exception While Fetching Shipped Orders  " + e.getMessage());
			model.addAttribute("OrderStatusDetail", null);
		}

		storeCmsPageInModel(model, getContentPageForLabelOrId(FMORDER_HISTORY_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FMORDER_HISTORY_CMS_PAGE));
		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getFMOrderDetailsBreadcrumbs("Back Order Details"));
		//model.addAttribute("orderDataList", orderDataList);
		model.addAttribute("metaRobots", "no-index,no-follow");
		model.addAttribute("userGroup", userGroup);
		//	model.addAttribute("selectedLink", "ReturnHistory");
	uploadorderFacade.createReportLog(fmCustomerAccModel,(fmUserSearchDAO.getUserForUID(fmCustomerFacade.getCurrentFMCustomer().getUid())), "backOrder");
		return ControllerConstants.Views.Pages.Account.fmOrderDetailsPage;
	}

	@RequestMapping(value = "/backOrder-header-export-pdf", method = RequestMethod.GET)
	public ModelAndView getBackOrderDetailsPDF()
	{
		final List<BackOrderSearchValues> backOrderSearchValuesList = getSessionService().getAttribute("sessionBackOrderResult");
		return new ModelAndView("backOrderPDFView", "backOrderSearchValuesList", backOrderSearchValuesList);

	}

	@RequestMapping(value = "/backOrder-header-export-xls", method = RequestMethod.GET)
	public ModelAndView getBackOrderDetailsExcel()
	{
		final List<BackOrderSearchValues> backOrderSearchValuesList = getSessionService().getAttribute("sessionBackOrderResult");
		return new ModelAndView("backOrderExcelView", "backOrderSearchValuesList", backOrderSearchValuesList);

	}

	@RequestMapping(value = "/backOrder-header-mail", method = RequestMethod.POST)
	public String getBackOrderSendmail(@ModelAttribute("emailForm") final EmailForm emailForm)
	{
		LOG.info("Hitting getBackOrderSendmail()");

		final List<BackOrderSearchValues> backOrderSearchValuesList = getSessionService().getAttribute("sessionBackOrderResult");
		if (backOrderSearchValuesList != null)
		{
			final MailUtil mu = new MailUtil();
			final HSSFWorkbook backOrderWorkBook = mu.getBackOrderWorkbookForEmailAttachment(backOrderSearchValuesList);

			try
			{
				final byte[] byteArray = backOrderWorkBook.getBytes();
				final InputStream inputStream = new ByteArrayInputStream(byteArray);

				LOG.info("emailform object" + emailForm);


				final JavaMailSenderImpl sender = new JavaMailSenderImpl();
				sender.setHost("134.238.225.222");

				if (emailForm != null)
				{
					if (emailForm.getPrimaryEmail() != null)
					{


						final MimeMessage message = sender.createMimeMessage();

						// use the true flag to indicate you need a multipart message
						MimeMessageHelper helper;
						helper = new MimeMessageHelper(message, true);
						helper.setSubject("Back Order");
						helper.setTo(emailForm.getPrimaryEmail());

						helper.setText("Back Order attachment");
						helper.setFrom("fmparts@federalmogul.com", "Federal Mogul Motor Parts");

						// let's attach the infamous windows Sample file (this time copied to c:/)

						//helper.addAttachment("OrderHistory.csv", new ByteArrayResource(IOUtils.toByteArray(inputStream)));
						helper.addAttachment("BackOrder.csv", new ByteArrayResource(IOUtils.toByteArray(inputStream)),
								"application/vnd.csv");

						sender.send(message);
						LOG.info("Message sent to primary email.");
					}
					if (emailForm.getSecondaryEmail() != null)
					{

						final MimeMessage message = sender.createMimeMessage();

						// use the true flag to indicate you need a multipart message
						MimeMessageHelper helper;
						helper = new MimeMessageHelper(message, true);
						helper.setSubject("Back Order");
						helper.setTo(emailForm.getSecondaryEmail());

						helper.setText("Back Order attachment");
						helper.setFrom("fmparts@federalmogul.com", "Federal Mogul Motor Parts");

						// let's attach the infamous windows Sample file (this time copied to c:/)

						//helper.addAttachment("OrderHistory.csv", new ByteArrayResource(IOUtils.toByteArray(inputStream)));
						helper.addAttachment("BackOrder.csv", new ByteArrayResource(IOUtils.toByteArray(inputStream)),
								"application/vnd.csv");

						sender.send(message);
						LOG.info("Message sent to secondary email.");

					}
				}
				else
				{
					LOG.info("EmailForm object comes as null cannot send email.");
				}
			}
			catch (final FileNotFoundException e)
			{
				LOG.error("ERROR" + e.getMessage());
			}
			catch (final IOException e)
			{
				LOG.error("ERROR" + e.getMessage());
			}
			catch (final MessagingException e)
			{
				LOG.error("ERROR" + e.getMessage());
			}
		}

		return REDIRECT_TO_ORDER_HISTORY;
	}

	private static AddressBO setAddress(final FMCustomerAccountModel fmCustomerAccModel)
	{

		AddressModel addres = fmCustomerAccModel.getBillingAddress();
		for (final AddressModel address : fmCustomerAccModel.getAddresses())
		{
			addres = address;
		}
		final AddressBO anAddress = new AddressBO();
		anAddress.setCity(addres.getTown());
		anAddress.setAddrLine1(addres.getLine1());
		anAddress.setAddrLine2(addres.getLine2());
		anAddress.setZipOrPostalCode(addres.getPostalcode());
		anAddress.setStateOrProv(addres.getRegion().getIsocodeShort());
		final CountryBO country = new CountryBO();
		country.setIsoCountryCode(addres.getCountry().getIsocode());
		anAddress.setCountry(country);
		return anAddress;
	}


}
