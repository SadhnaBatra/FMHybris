/**
 * 
 */
package com.federalmogul.storefront.controllers.pages;

import de.hybris.platform.b2bacceleratorservices.company.CompanyB2BCommerceService;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;
import de.hybris.platform.util.Config;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.federalmogul.storefront.annotations.RequireHardLogIn;

import com.federalmogul.core.model.FMCustomerAccountModel;
import com.federalmogul.core.network.dao.FMUserSearchDAO;
import com.federalmogul.core.order.dao.FMCheckoutDAO;
import com.federalmogul.facades.account.FMCustomerFacade;
import com.federalmogul.falcon.core.model.FMShipperOrderTrackingModel;
import com.federalmogul.storefront.constants.WebConstants;
import com.federalmogul.storefront.controllers.ControllerConstants;
import com.federalmogul.storefront.controllers.helper.OrderStatusResultHelper;
import com.federalmogul.storefront.util.UtilDate;
import com.fmo.wom.business.as.OrderStatusASUSCanImpl;
import com.fmo.wom.business.orderstatus.OrderStatusHelper;
import com.fmo.wom.business.orderstatus.OrderStatusResult;
import com.fmo.wom.domain.AccountBO;
import com.fmo.wom.domain.BillToAcctBO;
import com.fmo.wom.domain.CustomerSalesOrgBO;
import com.fmo.wom.domain.OrderUnitBO;
import com.fmo.wom.domain.SalesOrganizationBO;
import com.fmo.wom.domain.SapAcctBO;
import com.fmo.wom.domain.ShipToAcctBO;
import com.fmo.wom.domain.ShippedOrderBO;
import com.fmo.wom.domain.ShippingUnitBO;
import com.fmo.wom.domain.enums.OrderSearchFilter;
import com.fmo.wom.integration.SAPService;

import java.text.Format;
import java.text.SimpleDateFormat;


/**
 * uploadOrder for B2B
 * 
 */

@Controller
@Scope("tenant")
@RequestMapping(value = "/order-status")
public class OrderStatusController extends AbstractPageController
{
	private static final Logger LOG = Logger.getLogger(OrderStatusController.class);

	final OrderStatusASUSCanImpl orderHeardeService = new OrderStatusASUSCanImpl();

	@Resource
	protected CompanyB2BCommerceService companyB2BCommerceService;

	@Resource
	protected FMCustomerFacade fmCustomerFacade;

	@Autowired
	protected FMUserSearchDAO fmUserSearchDAO;

	@Autowired
	private FMCheckoutDAO fmCheckoutDAO;

	public static final String CMS_PAGE_MODEL = "cmsPage";
	public static final String CMS_PAGE_TITLE = "pageTitle";
	private static final String FMORDER_HISTORY_CMS_PAGE = "OrderHistoryPage";

	@RequestMapping(value = "/return-order-header", method = RequestMethod.POST)
	public String returnOrderHeader(final Model model, @RequestParam(value = "ponumber", required = false) final String ponumber,
			@RequestParam(value = "invoice", required = false) final String invoice,
			@RequestParam(value = "sdate", required = false) final String sdate,
			@RequestParam(value = "edate", required = false) final String edate,
			@RequestParam(value = "status", required = false) final String status) throws CMSItemNotFoundException
	{


		final String soldToAcc = (String) getSessionService().getAttribute(WebConstants.SOLDTO_ACCOUNT_ID);
		FMCustomerAccountModel fmCustomerAccModel = (FMCustomerAccountModel) companyB2BCommerceService.getUnitForUid(soldToAcc);
		final String shipToAcc = (String) getSessionService().getAttribute(WebConstants.SHIPTO_ACCOUNT_ID);
		final FMCustomerAccountModel sfmCustomerAccModel = (FMCustomerAccountModel) companyB2BCommerceService
				.getUnitForUid(shipToAcc);
		//Mahaveer JOBBER change
		final String loggedUserType = (String) getSessionService().getAttribute("logedUserType");
		if (loggedUserType != null && "ShipTo".equalsIgnoreCase(loggedUserType))
		{
			fmCustomerAccModel = sfmCustomerAccModel;
		}
		final BillToAcctBO billToAccount = getBillTOAccount(fmCustomerAccModel);
		//String sapAcctCode = "10013332";
		final SapAcctBO sapAccount = billToAccount.getSapAccount();

		final String customerPurchaseOrderNumber = "";
		final String masterOrderNumber = "";
		final Date startDate = getOrderSearchStartDate();
		final Date endDate = getOrderSearchEndDate();
		final OrderSearchFilter filter = OrderSearchFilter.ALL;
		final String orderRetrunFlag = "RETURNS";
		List<ShippedOrderBO> returnOrderHeaderList = null;
		try
		{
			returnOrderHeaderList = SAPService.getShipmentInfo(sapAccount.getSapAccountCode(), masterOrderNumber,
					customerPurchaseOrderNumber, startDate, endDate, filter, orderRetrunFlag);
			if (returnOrderHeaderList != null && (!returnOrderHeaderList.isEmpty()))
			{
				model.addAttribute("ReturnOrderHeaderStatus", returnOrderHeaderList);
				LOG.info(" Shipped Order after Details ... " + returnOrderHeaderList.size());
			}
		}
		catch (final Exception e)
		{
			LOG.error(" Exception While Fetching Shipped Orders from ECC");
			model.addAttribute("ReturnOrderHeaderStatus", null);
		}
		storeCmsPageInModel(model, getContentPageForLabelOrId(FMORDER_HISTORY_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FMORDER_HISTORY_CMS_PAGE));
		model.addAttribute("metaRobots", "no-index,no-follow");
		return ControllerConstants.Views.Pages.Account.FMReturnStatusHeader;
	}

	@RequestMapping(value = "/order-header", method = RequestMethod.POST)
	public String orderHeader(final Model model, @RequestParam(value = "ponumber", required = false) final String ponumber,
			@RequestParam(value = "invoice", required = false) final String invoice,
			@RequestParam(value = "sdate", required = false) final String sdate,
			@RequestParam(value = "edate", required = false) final String edate,
			@RequestParam(value = "status", required = false) final String status) throws CMSItemNotFoundException
	{

		final String soldToAcc = (String) getSessionService().getAttribute(WebConstants.SOLDTO_ACCOUNT_ID);
		FMCustomerAccountModel fmCustomerAccModel = (FMCustomerAccountModel) companyB2BCommerceService.getUnitForUid(soldToAcc);
		final String shipToAcc = (String) getSessionService().getAttribute(WebConstants.SHIPTO_ACCOUNT_ID);
		final FMCustomerAccountModel sfmCustomerAccModel = (FMCustomerAccountModel) companyB2BCommerceService
				.getUnitForUid(shipToAcc);
		int noOfpages = 0;
		LOG.info("shipToAcc ==>" + shipToAcc);

		final List<AccountBO> billToAccounts = new ArrayList<AccountBO>();
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
		final String customerPurchaseOrderNumber = ponumber;
		final String masterOrderNumber = invoice;
		Date startDate = getOrderSearchStartDate();
		Date endDate = getOrderSearchEndDate();
		OrderSearchFilter filter = OrderSearchFilter.ALL;
		final String orderRetrunFlag = "ORDERS";

		if (sdate != null && sdate != "")
		{
			startDate = UtilDate.getUtilDate(WebConstants.DD_MM_YYYY, sdate);
		}
		if (edate != null && edate != "")
		{
			endDate = UtilDate.getUtilDate(WebConstants.DD_MM_YYYY, edate);
		}

		LOG.info(" startDate :: " + startDate);
		LOG.info("endDate  :: " + endDate);

		if (status != null && status != "" && "IN_PROCESS".equals(status))
		{
			filter = OrderSearchFilter.IN_PROCESS;
		}
		else if (status != null && status != "" && "COMPLETE".equals(status))
		{
			filter = OrderSearchFilter.COMPLETE;
		}


		List<ShippedOrderBO> orderHeaderList = null;
		try
		{
			if (loggedUserType != null && "ShipTo".equalsIgnoreCase(loggedUserType))
			{
				orderHeaderList = orderHeardeService.searchOrders(billToAccounts, shipToAccount, masterOrderNumber,
						customerPurchaseOrderNumber, startDate, endDate, filter, orderRetrunFlag);
			}
			else
			{
				orderHeaderList = orderHeardeService.searchOrders(billToAccount, masterOrderNumber, customerPurchaseOrderNumber,
						startDate, endDate, filter, orderRetrunFlag);
			}

			LOG.info("  Shipped Orders ... " + orderHeaderList);
			if (orderHeaderList != null && (!orderHeaderList.isEmpty()))
			{

				for (final ShippedOrderBO shippedOrderBO : orderHeaderList)
				{
					for (final OrderUnitBO orderUnit : shippedOrderBO.getOrderUnitList())
					{
						for (final ShippingUnitBO shippingUnitBO : orderUnit.getShippingUnitList())
						{
							final FMShipperOrderTrackingModel shipperInfo = fmCheckoutDAO.getShipperOrderDetails(shippingUnitBO
									.getCarrierCode());
							if (null != shipperInfo)
							{
								shippingUnitBO.setCarrierName(shipperInfo.getShipperName());
								if (null != shipperInfo.getShipperName()
										&& ("FED".contains(shipperInfo.getShipperName().toUpperCase()) || "UPS".contains(shipperInfo
												.getShipperName().toUpperCase())))
								{
									shippingUnitBO.setTrackingURL(shipperInfo.getShipperURL() + shippingUnitBO.getTrackingNumber());
								}
								else
								{
									shippingUnitBO.setTrackingURL(shipperInfo.getShipperURL());
								}
							}
						}
					}
				}



				final OrderStatusResultHelper orderStatusResultHelper = new OrderStatusResultHelper();
				final OrderStatusHelper orderStatusHelper = new OrderStatusHelper(orderHeaderList);
				final List<OrderStatusResult> orderStatusResult = orderStatusResultHelper.sortByOrderDate(orderStatusHelper);
				getSessionService().setAttribute("sessionOrderStatusResult", orderStatusResult);
				getSessionService().setAttribute("sessionOrderStatusHelper", orderStatusHelper);
				getSessionService().setAttribute("sessionSortedOrderStatusResult", orderStatusResult);

				LOG.info("OrderHeaderList SIZE :: " + orderHeaderList.size());
				noOfpages = orderStatusResult.size() / 20;
				model.addAttribute("OrderHeaderStatus", orderHeaderList);
				model.addAttribute("orderStatusResult", orderStatusResult);

			}
		}
		catch (final Exception e)
		{
			LOG.error(" Exception While Fetching Shipped Orders  ");
			model.addAttribute("OrderHeaderStatus", null);
		}
		storeCmsPageInModel(model, getContentPageForLabelOrId(FMORDER_HISTORY_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FMORDER_HISTORY_CMS_PAGE));
	
				//Added by sai for FPT-99 issue display order history default 7 days range
		final Format formatter = new SimpleDateFormat("MM/dd/yyyy");
		getSessionService().setAttribute("order_startDate", formatter.format(startDate));
		getSessionService().setAttribute("order_endDate", formatter.format(endDate));
		getSessionService().setAttribute("order_shippedStatus", status);


		model.addAttribute("startDate", getSessionService().getAttribute("order_startDate"));
		model.addAttribute("endDate", getSessionService().getAttribute("order_endDate"));
		model.addAttribute("order_status", getSessionService().getAttribute("order_shippedStatus"));


		model.addAttribute("begin", 0);
		model.addAttribute("end", 20);
		model.addAttribute("itemsCount", 20);
		model.addAttribute("page", 1);
		model.addAttribute("noOfpages", noOfpages);
		model.addAttribute("metaRobots", "no-index,no-follow");
		return ControllerConstants.Views.Pages.Account.FMOrderStatusHeader;

	}

	@RequestMapping(value = "/order-details/{orderHeaderStatusBO}", method = RequestMethod.POST)
	public String orderDeatils(final Model model, @PathVariable("orderHeaderStatusBO") final ShippedOrderBO orderHeaderStatusBO)
			throws CMSItemNotFoundException
	{
		LOG.info("OrderStatusDetail");
		try
		{
			final String orderRetrunFlag = "ORDERS";
			orderHeardeService.getOrderDetail(orderHeaderStatusBO, orderRetrunFlag);
			model.addAttribute("OrderStatusDetail", orderHeaderStatusBO);
			LOG.info("\n invoiceDetails ......." + orderHeaderStatusBO);

		}
		catch (final Exception e)
		{
			LOG.error("Exception While Fetching OrderStatusDetail Details ....." + e.getMessage());
			model.addAttribute("OrderStatusDetail", null);
		}
		storeCmsPageInModel(model, getContentPageForLabelOrId(FMORDER_HISTORY_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FMORDER_HISTORY_CMS_PAGE));
		model.addAttribute("metaRobots", "no-index,no-follow");
		return ControllerConstants.Views.Pages.Account.FMOrderStatusDetails;

	}

	@RequestMapping(value = "/return-order-details/{returnOrderStatusBO}", method = RequestMethod.POST)
	public String returnOrderDeatils(final Model model,
			@PathVariable("returnOrderStatusBO") final ShippedOrderBO returnOrderStatusBO) throws CMSItemNotFoundException
	{
		LOG.info("OrderStatusDetail");
		try
		{
			final String orderRetrunFlag = "RETURNS";
			SAPService.getShipmentDetail(returnOrderStatusBO, orderRetrunFlag);
			model.addAttribute("ReturnOrderStatusBO", returnOrderStatusBO);
			LOG.info("\n invoiceDetails ......." + returnOrderStatusBO);

		}

		catch (final Exception e)
		{
			LOG.error("Exception While Fetching OrderStatusDetail Details ....." + e.getMessage());
			model.addAttribute("OrderStatusDetail", null);
		}
		storeCmsPageInModel(model, getContentPageForLabelOrId(FMORDER_HISTORY_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FMORDER_HISTORY_CMS_PAGE));
		model.addAttribute("metaRobots", "no-index,no-follow");
		return ControllerConstants.Views.Pages.Account.FMReturnStatusDetails;

	}

	@RequestMapping(method = RequestMethod.GET, value = "/sortOrderHistory")
	public String getSortOrderHistory(@RequestParam(value = "sortBy") final String sortBy, final Model model)
			throws CMSItemNotFoundException
	{
		final List<OrderStatusResult> orderStatusResult = getSessionService().getAttribute("sessionOrderStatusResult");
		final OrderStatusHelper orderStatusHelper = getSessionService().getAttribute("sessionOrderStatusHelper");
		OrderStatusResultHelper orderStatusResultHelper;

		LOG.info("Sort By :: " + sortBy);

		if ((orderStatusResult != null && (!orderStatusResult.isEmpty())) && (orderStatusHelper != null))
		{
			if (sortBy.equals(WebConstants.PURCHASE_ORDER_NUMBER))
			{
				orderStatusResultHelper = new OrderStatusResultHelper();
				final List<OrderStatusResult> sortedOrderStatusResult = orderStatusResultHelper
						.sortByPurchaseOrderNumber(orderStatusHelper);
				LOG.info("sortedOrderStatusResult :: " + sortedOrderStatusResult.size());
				getSessionService().setAttribute("sessionSortedOrderStatusResult", sortedOrderStatusResult);
				model.addAttribute("orderStatusResult", sortedOrderStatusResult);
			}

			if (sortBy.equals(WebConstants.ORDER_NUMBER))
			{
				orderStatusResultHelper = new OrderStatusResultHelper();
				final List<OrderStatusResult> sortedOrderStatusResult = orderStatusResultHelper.sortByOrderNumber(orderStatusHelper);
				LOG.info("sortedOrderStatusResult :: " + sortedOrderStatusResult.size());
				getSessionService().setAttribute("sessionSortedOrderStatusResult", sortedOrderStatusResult);
				model.addAttribute("orderStatusResult", sortedOrderStatusResult);
			}
			if (sortBy.equals(WebConstants.ORDER_PACKING_SLIP))
			{
				orderStatusResultHelper = new OrderStatusResultHelper();
				final List<OrderStatusResult> sortedOrderStatusResult = orderStatusResultHelper
						.sortByOrderPackingSlipNumber(orderStatusHelper);
				LOG.info("sortedOrderStatusResult :: " + sortedOrderStatusResult.size());
				getSessionService().setAttribute("sessionSortedOrderStatusResult", sortedOrderStatusResult);
				model.addAttribute("orderStatusResult", sortedOrderStatusResult);
			}
			if (sortBy.equals(WebConstants.ORDER_SHIPPING_DC))
			{
				orderStatusResultHelper = new OrderStatusResultHelper();
				final List<OrderStatusResult> sortedOrderStatusResult = orderStatusResultHelper
						.sortByOrderShippingDC(orderStatusHelper);
				LOG.info("sortedOrderStatusResult :: " + sortedOrderStatusResult.size());
				getSessionService().setAttribute("sessionSortedOrderStatusResult", sortedOrderStatusResult);
				model.addAttribute("orderStatusResult", sortedOrderStatusResult);
			}
			if (sortBy.equals(WebConstants.ORDER_SHIPPING_DATE))
			{
				orderStatusResultHelper = new OrderStatusResultHelper();
				final List<OrderStatusResult> sortedOrderStatusResult = orderStatusResultHelper
						.sortByOrderShipDate(orderStatusHelper);
				LOG.info("sortedOrderStatusResult :: " + sortedOrderStatusResult.size());
				getSessionService().setAttribute("sessionSortedOrderStatusResult", sortedOrderStatusResult);
				model.addAttribute("orderStatusResult", sortedOrderStatusResult);
			}

			if (sortBy.equals(WebConstants.ORDER_STATUS))
			{
				orderStatusResultHelper = new OrderStatusResultHelper();
				final List<OrderStatusResult> sortedOrderStatusResult = orderStatusResultHelper.sortByOrderStatus(orderStatusHelper);
				LOG.info("sortedOrderStatusResult :: " + sortedOrderStatusResult.size());
				getSessionService().setAttribute("sessionSortedOrderStatusResult", sortedOrderStatusResult);
				model.addAttribute("orderStatusResult", sortedOrderStatusResult);
			}
		}
		storeCmsPageInModel(model, getContentPageForLabelOrId(FMORDER_HISTORY_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FMORDER_HISTORY_CMS_PAGE));

		model.addAttribute("startDate", getSessionService().getAttribute("order_startDate"));
		model.addAttribute("endDate", getSessionService().getAttribute("order_endDate"));
		model.addAttribute("order_status", getSessionService().getAttribute("order_shippedStatus"));

		model.addAttribute("metaRobots", "no-index,no-follow");
		return ControllerConstants.Views.Pages.Account.FMOrderStatusHeader;

	}


	@RequestMapping(method = RequestMethod.POST, value = "/orderHistory-Pagination")
	public String getOrderHistoryPaginationData(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@RequestParam(value = "itemsCount", required = false, defaultValue = "0") final int itemsCount,
			@RequestParam(value = "begin", required = false, defaultValue = "-1") int begin,
			@RequestParam(value = "end", required = false, defaultValue = "0") int end,
			@RequestParam(value = "noOfpages", required = false, defaultValue = "0") int noOfpages,
			@RequestParam(value = "moveFlag", required = false, defaultValue = "0") final int moveFlag, final Model model)
			throws CMSItemNotFoundException

	{

		final List<OrderStatusResult> orderStatusResult = getSessionService().getAttribute("sessionSortedOrderStatusResult");
		LOG.info("##### 00 " + "noOfpages :: " + noOfpages + " Begin:: " + begin + " End :: " + end + " itemsCount:: " + itemsCount
				+ " page:: " + page);
		LOG.info("moveFlag ::: " + moveFlag);





		if (begin != -1 && end != 0 && itemsCount != 0 && moveFlag == 1)
		{

			begin = end;
			end = end + itemsCount;
			page = page + 1;
			noOfpages = orderStatusResult.size() / itemsCount;

			model.addAttribute("begin", begin);
			model.addAttribute("end", end);
			model.addAttribute("page", page);
			model.addAttribute("noOfpages", noOfpages);
			model.addAttribute("itemsCount", itemsCount);
			LOG.info("##### 11 " + "noOfpages :: " + noOfpages + " Begin:: " + begin + " End :: " + end + " itemsCount :: "
					+ itemsCount + " Page :: " + page);


		}
		else if (begin != -1 && end != 0 && itemsCount != 0 && moveFlag == -1)
		{
			end = begin;
			begin = begin - itemsCount;
			page = page - 1;
			noOfpages = orderStatusResult.size() / itemsCount;
			model.addAttribute("begin", begin);
			model.addAttribute("end", end);
			model.addAttribute("page", page);
			model.addAttribute("noOfpages", noOfpages);
			model.addAttribute("itemsCount", itemsCount);
			LOG.info("##### 22 " + "noOfpages :: " + noOfpages + " Begin:: " + begin + " End :: " + end + " itemsCount :: "
					+ itemsCount + " Page :: " + page);


		}
		else if (itemsCount != 0)
		{
			LOG.info("itemsCount :: " + itemsCount);
			noOfpages = orderStatusResult.size() / itemsCount;
			model.addAttribute("begin", 0);
			model.addAttribute("end", itemsCount);
			model.addAttribute("noOfpages", noOfpages);
			model.addAttribute("itemsCount", itemsCount);
			model.addAttribute("page", 1);

			LOG.info("##### 33 " + "noOfpages :: " + noOfpages + " Begin:: " + begin + " End :: " + end + " itemsCount :: "
					+ itemsCount + " Page :: " + page);


		}


		LOG.info("noOfpages :: " + noOfpages + " Begin:: " + begin + " End :: " + end);

		model.addAttribute("startDate", getSessionService().getAttribute("order_startDate"));
		model.addAttribute("endDate", getSessionService().getAttribute("order_endDate"));
		model.addAttribute("order_status", getSessionService().getAttribute("order_shippedStatus"));


		storeCmsPageInModel(model, getContentPageForLabelOrId(FMORDER_HISTORY_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FMORDER_HISTORY_CMS_PAGE));

		model.addAttribute("orderStatusResult", orderStatusResult);
		return ControllerConstants.Views.Pages.Account.FMOrderStatusHeader;


	}

	private static Date getOrderSearchStartDate()
	{
		final Calendar startCal = Calendar.getInstance();
		try
		{
			final int days = Integer.parseInt(Config.getParameter(WebConstants.MYACCOUNT_ORDERHISTORY_START_DATE));
			LOG.info(" Days : " + days);
			startCal.add(Calendar.DAY_OF_YEAR, -1 * days);
		}
		catch (final Exception ex)
		{
			startCal.add(Calendar.DAY_OF_YEAR, -1 * 7);
		}
		LOG.info(" Date : " + startCal.getTime());
		return startCal.getTime();
	}

	private static Date getOrderSearchEndDate()
	{

		final Calendar endCal = Calendar.getInstance();
		return endCal.getTime();
	}

	private static BillToAcctBO getBillTOAccount(final FMCustomerAccountModel fmCustomerAccModel)
	{
		final BillToAcctBO billToAcct = new BillToAcctBO();


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

		return billToAcct;
	}

	private static ShipToAcctBO getShiptoAccount(final FMCustomerAccountModel fmCustomerAccModel)
	{
		final ShipToAcctBO shipToAcct = new ShipToAcctBO();
		shipToAcct.setAccountCode(fmCustomerAccModel.getNabsAccountCode());
		final SapAcctBO sapAccount = new SapAcctBO();
		sapAccount.setSapAccountCode(fmCustomerAccModel.getUid());
		shipToAcct.setSapAccount(sapAccount);

		return shipToAcct;
	}

	protected void updatePageTitle(final Model model, final AbstractPageModel cmsPage)
	{
		storeContentPageTitleInModel(model, getPageTitleResolver().resolveHomePageTitle(cmsPage.getTitle()));
	}

	/*
	 * 297584 - Added new method for exporting the list of All order details in PDF , Excel and Mail format
	 */

	@RequireHardLogIn
	@RequestMapping(value = "/exportToExcel", method = RequestMethod.GET)
	public ModelAndView downloadExcel(final Model model) throws CMSItemNotFoundException
	{
		LOG.info("Order Status downloadExcel");
		final List<OrderStatusResult> orderStatusResult = getSessionService().getAttribute("sessionSortedOrderStatusResult");
		return new ModelAndView("ordersExcelView", "orderStatusResult", orderStatusResult);
	}

	@RequireHardLogIn
	@RequestMapping(value = "/exportToPdf", method = RequestMethod.GET)
	public ModelAndView downloadPdf(final Model model) throws CMSItemNotFoundException
	{
		LOG.info("Order Status exportToPdf");
		final List<OrderStatusResult> orderStatusResult = getSessionService().getAttribute("sessionSortedOrderStatusResult");
		return new ModelAndView("ordersPDFView", "orderStatusResult", orderStatusResult);
	}


}
