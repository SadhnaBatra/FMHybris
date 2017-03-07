/**
 * 
 */
package com.federalmogul.storefront.controllers.pages;

import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;
import de.hybris.platform.core.model.security.PrincipalGroupModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.user.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.federalmogul.core.model.FMCustomerModel;
import com.federalmogul.facades.upload.order.data.UploadOrderHistoryData;
import com.federalmogul.facades.upload.order.data.uploadOrderData;
import com.federalmogul.facades.upload.order.data.uploadOrderEntryData;
import com.federalmogul.facades.uploadorder.UploadOrderFacade;
import com.federalmogul.storefront.breadcrumb.ResourceBreadcrumbBuilder;
import com.federalmogul.storefront.constants.WebConstants;
import com.federalmogul.storefront.controllers.ControllerConstants;
import com.federalmogul.storefront.forms.OrderUploadForm;
import com.fmo.wom.business.as.OrderASUSCanImpl;


/**
 * uploadOrder for B2B
 * 
 */

@Controller
@Scope("tenant")
@RequestMapping(value = "/uploadOrder")
@SessionAttributes(
{ "uploadStatus" })
public class UploadOrderController extends AbstractPageController
{
	private static final Logger LOG = Logger.getLogger(UploadOrderController.class);

	@Autowired
	private UploadOrderFacade uploadOrderFacade;

	@Autowired
	private UserService userService;

	@Resource(name = "accountBreadcrumbBuilder")
	private ResourceBreadcrumbBuilder accountBreadcrumbBuilder;

	@RequestMapping(value = "/upload-order", method = RequestMethod.POST)
	public String uploadOrder(final Model model, @ModelAttribute("orderUpload") final OrderUploadForm orderUploadForm)
			throws CMSItemNotFoundException
	{
		LOG.info("uploadOrder");


		if (validateUploadOrderform(orderUploadForm, model))
		{
			uploadOrderFile(orderUploadForm);
			getSessionService().setAttribute("uploadStatus", "File uploaded successfully");
			model.addAttribute("uploadFlag", new String("success"));
		}

		storeCmsPageInModel(model, getContentPageForLabelOrId("FMB2BHomePage"));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId("FMB2BHomePage"));
		updatePageTitle(model, getContentPageForLabelOrId("FMB2BHomePage"));


		return REDIRECT_PREFIX + ROOT;

	}

	@RequestMapping(value = "/orderstatus", method =
	{ RequestMethod.GET, RequestMethod.POST })
	public String orderstatus(final Model model) throws CMSItemNotFoundException
	{
		final boolean isAnoymousUser = isAnonymousUser(userService);
		if (!isAnoymousUser)
		{
			final String shipToAcc = (String) getSessionService().getAttribute(WebConstants.SHIPTO_ACCOUNT_ID);
			final FMCustomerModel customer = (FMCustomerModel) userService.getCurrentUser();
			boolean isCSRLogin = false;
			final Set<PrincipalGroupModel> groupss = customer.getGroups();
			for (final PrincipalGroupModel groupModel : groupss)
			{
				final String groupId = groupModel.getUid();
				if (groupId.contains("FMCSR") || groupId.contains("FMBUVOR"))
				{
					isCSRLogin = true;
					break;
				}
			}

			final List<uploadOrderData> uploadOrderData = isCSRLogin ? uploadOrderFacade.viewCSRUploadorder(shipToAcc)
					: uploadOrderFacade.viewUploadorder();
			model.addAttribute("uploadOrderData", uploadOrderData);
			return ControllerConstants.Views.Pages.Account.FMUploadOrder;
		}
		else
		{
			return REDIRECT_PREFIX + ROOT;
		}
	}

	@RequestMapping(value = "/csrorderstatus", method =
	{ RequestMethod.GET, RequestMethod.POST })
	public String csrOrderStatus(final Model model) throws CMSItemNotFoundException
	{
		LOG.info("CSR Uppload Order Status Controller");
		final boolean isAnoymousUser = isAnonymousUser(userService);
		if (!isAnoymousUser)
		{
			final List<uploadOrderData> uploadOrderData = uploadOrderFacade.viewCSRUploadorder();
			model.addAttribute("uploadOrderData", uploadOrderData);
			return ControllerConstants.Views.Pages.Account.FMUploadOrder;
		}
		else
		{
			return REDIRECT_PREFIX + ROOT;
		}
	}

	@RequestMapping(value = "/uploadOrderEntry/{code}", method =
	{ RequestMethod.GET, RequestMethod.POST })
	public String uploadOrderEntry(final Model model, @PathVariable("code") final String code) throws CMSItemNotFoundException
	{
		final boolean isAnoymousUser = isAnonymousUser(userService);
		if (!isAnoymousUser)
		{
			final List<uploadOrderEntryData> uploadOrderEntryData = uploadOrderFacade.viewUploadorderEntry(code);
			model.addAttribute("uploadOrderEntryData", uploadOrderEntryData);
			model.addAttribute("uploadOrderCode", code);
			return ControllerConstants.Views.Pages.Account.FMUploadOrderEntry;
		}
		else
		{
			return REDIRECT_PREFIX + ROOT;
		}
	}

	@RequestMapping(value = "/uploadOrderEntryDelete/{code}/{entryNumber}", method =
	{ RequestMethod.GET, RequestMethod.POST })
	public String uploadOrderEntryDelete(final Model model, @PathVariable("code") final String code,
			@PathVariable("entryNumber") final String entryNumber) throws CMSItemNotFoundException
	{
		final boolean isAnoymousUser = isAnonymousUser(userService);
		if (!isAnoymousUser)
		{
			final boolean deleteOrderEntry = uploadOrderFacade.deleteUploadorderEntry(code, entryNumber);
			List<uploadOrderEntryData> uploadOrderEntryData = new ArrayList<uploadOrderEntryData>();
			if (deleteOrderEntry)
			{
				uploadOrderEntryData = uploadOrderFacade.viewUploadorderEntry(code);
			}
			model.addAttribute("DeleteStatus", deleteOrderEntry);
			model.addAttribute("uploadOrderEntryData", uploadOrderEntryData);
			model.addAttribute("uploadOrderCode", code);
			return ControllerConstants.Views.Pages.Account.FMUploadOrderEntry;
		}
		else
		{
			return REDIRECT_PREFIX + ROOT;
		}
	}

	@RequestMapping(value = "/uploadOrderEntryEdit/{code}/{entryNumber}/{partnumber}/{qty}/{productFlag}", method =
	{ RequestMethod.GET, RequestMethod.POST })
	public String uploadOrderEntryEdit(final Model model, @PathVariable("code") final String code,
			@PathVariable("entryNumber") final String entryNumber, @PathVariable("partnumber") final String partnumber,
			@PathVariable("qty") final String qty, @PathVariable("productFlag") final String productFlag)
			throws CMSItemNotFoundException
	{
		final boolean isAnoymousUser = isAnonymousUser(userService);
		if (!isAnoymousUser)
		{
			final boolean editOrderEntry = uploadOrderFacade.editUploadorderEntry(code, partnumber, qty, entryNumber, productFlag);
			List<uploadOrderEntryData> uploadOrderEntryData = new ArrayList<uploadOrderEntryData>();
			if (editOrderEntry)
			{
				uploadOrderEntryData = uploadOrderFacade.viewUploadorderEntry(code);
			}
			model.addAttribute("UpdateStatus", editOrderEntry);
			model.addAttribute("uploadOrderEntryData", uploadOrderEntryData);
			model.addAttribute("uploadOrderCode", code);
			return ControllerConstants.Views.Pages.Account.FMUploadOrderEntry;
		}
		else
		{
			return REDIRECT_PREFIX + ROOT;
		}
	}

	@RequestMapping(value = "/uploadOrderDelete/{code}", method =
	{ RequestMethod.GET, RequestMethod.POST })
	public String uploadOrderDelete(final Model model, @PathVariable("code") final String code) throws CMSItemNotFoundException
	{
		final boolean isAnoymousUser = isAnonymousUser(userService);
		if (!isAnoymousUser)
		{
			final boolean deleteOrder = uploadOrderFacade.deleteUploadorder(code);
			List<uploadOrderData> uploadOrderData = new ArrayList<uploadOrderData>();
			if (deleteOrder)
			{
				final String shipToAcc = (String) getSessionService().getAttribute(WebConstants.SHIPTO_ACCOUNT_ID);
				final FMCustomerModel customer = (FMCustomerModel) userService.getCurrentUser();
				boolean isCSRLogin = false;
				boolean isGlobalCSRLogin = false;
				final Set<PrincipalGroupModel> groupss = customer.getGroups();
				for (final PrincipalGroupModel groupModel : groupss)
				{
					final String groupId = groupModel.getUid();
					if ((groupId.contains("FMCSR") || groupId.contains("FMBUVOR")) && shipToAcc != null)
					{
						isCSRLogin = true;
						break;
					}
					else if ((groupId.contains("FMCSR") || groupId.contains("FMBUVOR")) && shipToAcc == null)
					{
						isGlobalCSRLogin = true;
						break;
					}
				}
				uploadOrderData = isGlobalCSRLogin ? uploadOrderFacade.viewCSRUploadorder() : isCSRLogin ? uploadOrderFacade
						.viewCSRUploadorder(shipToAcc) : uploadOrderFacade.viewUploadorder();

			}
			model.addAttribute("DeleteStatus", deleteOrder);
			model.addAttribute("uploadOrderData", uploadOrderData);
			return ControllerConstants.Views.Pages.Account.FMUploadOrder;
		}
		else
		{
			return REDIRECT_PREFIX + ROOT;
		}
	}

	@RequestMapping(value = "/uploadOrderSave/{code}", method =
	{ RequestMethod.GET, RequestMethod.POST })
	public String uploadOrderSave(final Model model, @PathVariable("code") final String code) throws CMSItemNotFoundException
	{
		final boolean isAnoymousUser = isAnonymousUser(userService);
		if (!isAnoymousUser)
		{
			final boolean saveOrder = uploadOrderFacade.saveUploadorder(code);
			List<uploadOrderData> uploadOrderData = new ArrayList<uploadOrderData>();
			if (saveOrder)
			{
				final String shipToAcc = (String) getSessionService().getAttribute(WebConstants.SHIPTO_ACCOUNT_ID);
				final FMCustomerModel customer = (FMCustomerModel) userService.getCurrentUser();
				boolean isCSRLogin = false;
				boolean isGlobalCSRLogin = false;
				final Set<PrincipalGroupModel> groupss = customer.getGroups();
				for (final PrincipalGroupModel groupModel : groupss)
				{
					final String groupId = groupModel.getUid();
					if ((groupId.contains("FMCSR") || groupId.contains("FMBUVOR")) && shipToAcc != null)
					{
						isCSRLogin = true;
						break;
					}
					else if ((groupId.contains("FMCSR") || groupId.contains("FMBUVOR")) && shipToAcc == null)
					{
						isGlobalCSRLogin = true;
						break;
					}
				}
				uploadOrderData = isGlobalCSRLogin ? uploadOrderFacade.viewCSRUploadorder() : isCSRLogin ? uploadOrderFacade
						.viewCSRUploadorder(shipToAcc) : uploadOrderFacade.viewUploadorder();
			}
			model.addAttribute("SaveOrder", saveOrder);
			model.addAttribute("uploadOrderData", uploadOrderData);
			return ControllerConstants.Views.Pages.Account.FMUploadOrder;
		}
		else
		{
			return REDIRECT_PREFIX + ROOT;
		}
	}

	@RequestMapping(value = "/uploadOrderHistory/{code}", method =
	{ RequestMethod.GET, RequestMethod.POST })
	public String uploadOrderHistory(final Model model, @PathVariable("code") final String code) throws CMSItemNotFoundException
	{
		final boolean isAnoymousUser = isAnonymousUser(userService);
		if (!isAnoymousUser)
		{
			final List<UploadOrderHistoryData> uploadOrderHistoryData = uploadOrderFacade.viewUploadorderHistory(code);
			model.addAttribute("UploadOrderHistoryData", uploadOrderHistoryData);
			return ControllerConstants.Views.Pages.Account.FMUploadOrderHistory;
		}
		else
		{
			return REDIRECT_PREFIX + ROOT;
		}
	}

	@RequestMapping(value = "/search", method =
	{ RequestMethod.GET, RequestMethod.POST })
	public String searchOrderStatus(final Model model, @RequestParam(value = "ponumber", required = false) final String ponumber,
			@RequestParam(value = "sapordernumber", required = false) final String sapordernumber,
			@RequestParam(value = "sdate", required = false) final String sdate,
			@RequestParam(value = "edate", required = false) final String edate,
			@RequestParam(value = "status", required = false) final String status) throws CMSItemNotFoundException
	{
		final boolean isAnoymousUser = isAnonymousUser(userService);
		if (!isAnoymousUser)
		{
			LOG.info("PONUMBER" + ponumber);
			LOG.info("sapordernumber" + sapordernumber);
			LOG.info("sdate" + sdate);
			LOG.info("edate" + edate);
			LOG.info("status" + status);
			final String shipToAcc = (String) getSessionService().getAttribute(WebConstants.SHIPTO_ACCOUNT_ID);
			final FMCustomerModel customer = (FMCustomerModel) userService.getCurrentUser();
			boolean isCSRLogin = false;
			boolean isGlobalCSRLogin = false;
			List<uploadOrderData> uploadOrderData = null;
			final Set<PrincipalGroupModel> groupss = customer.getGroups();
			for (final PrincipalGroupModel groupModel : groupss)
			{
				final String groupId = groupModel.getUid();
				if ((groupId.contains("FMCSR") || groupId.contains("FMBUVOR")) && shipToAcc != null)
				{
					isCSRLogin = true;
					break;
				}
				else if ((groupId.contains("FMCSR") || groupId.contains("FMBUVOR")) && shipToAcc == null)
				{
					isGlobalCSRLogin = true;
					break;
				}
			}
			LOG.info("isCSRLogin :: " + isCSRLogin + " isGlobalCSRLogin ::" + isGlobalCSRLogin);
			LOG.info("shipToAcc :: " + shipToAcc);
			LOG.info("searchOrderStatus{} isCSRLogin ::" + isCSRLogin);
			uploadOrderData = isCSRLogin ? uploadOrderFacade.searchCSRUploadorder(shipToAcc, ponumber, sapordernumber, sdate, edate,
					status) : isGlobalCSRLogin ? uploadOrderFacade.getGlobalCSRSearchUploadOrderData(ponumber, sapordernumber, sdate,
					edate, status) : uploadOrderFacade.searchUploadorder(ponumber, sapordernumber, sdate, edate, status);



			model.addAttribute("ponumber", ponumber);
			model.addAttribute("sapordernumber", sapordernumber);
			model.addAttribute("sdate", sdate);
			model.addAttribute("edate", edate);
			model.addAttribute("status", status);

			model.addAttribute("uploadOrderData", uploadOrderData);
			return ControllerConstants.Views.Pages.Account.FMUploadOrder;
		}
		else
		{
			return REDIRECT_PREFIX + ROOT;
		}
	}

	public void uploadOrderFile(final OrderUploadForm orderUploadForm)
	{
		LOG.info("Inside the file processor");
		final MultipartFile uploadFile = orderUploadForm.getUploadFile();
		final String purchaseOrderNumber = orderUploadForm.getPONumber().length() > 20 ? orderUploadForm.getPONumber().substring(0,
				20) : orderUploadForm.getPONumber();
		final String orderComments = (null != orderUploadForm.getDescription() && orderUploadForm.getDescription().length() > 60) ? orderUploadForm
				.getDescription().substring(0, 60) : orderUploadForm.getDescription();
		LOG.info("File Name :: " + uploadFile.getName() + "PO NUmber :: " + purchaseOrderNumber + "orderComments :: "
				+ orderComments);
		final uploadOrderData orderdata = new uploadOrderData();
		orderdata.setCode(getmasterOrderNumber().trim());
		orderdata.setUploadfile(uploadFile);
		orderdata.setPONumber(purchaseOrderNumber);
		orderdata.setOrdercomments(orderComments);
		uploadOrderFacade.createUploadorder(orderdata);
	}

	private String getmasterOrderNumber()
	{
		final OrderASUSCanImpl processOrderService = new OrderASUSCanImpl();
		return processOrderService.getMasterOrderNumber();
	}

	protected void updatePageTitle(final Model model, final AbstractPageModel cmsPage)
	{
		storeContentPageTitleInModel(model, getPageTitleResolver().resolveHomePageTitle(cmsPage.getTitle()));
	}

	/**
	 * 
	 * @param orderUploadForm
	 * @param model
	 * @return
	 */
	private boolean validateUploadOrderform(final OrderUploadForm orderUploadForm, final Model model)
	{
		LOG.info("orderUploadForm.getPONumber() :: " + orderUploadForm.getPONumber());
		LOG.info("orderUploadForm.getDescription() :: " + orderUploadForm.getDescription());
		LOG.info("orderUploadForm.getUploadFile() :: " + orderUploadForm.getUploadFile());


		if ((orderUploadForm.getPONumber() != null && !orderUploadForm.getPONumber().isEmpty())
		//&& (orderUploadForm.getDescription() != null && !orderUploadForm.getDescription().isEmpty())
				&& (orderUploadForm.getUploadFile() != null && !orderUploadForm.getUploadFile().isEmpty()))
		{

			LOG.info("###########File Uploaded Successfully ################# ");
			model.addAttribute("uploadStatus", "File uploaded successfully");
			getSessionService().setAttribute("uploadStatus", "File uploaded successfully");

			model.addAttribute("uploadFlag", new String("success"));
			return true;
		}
		else
		{
			LOG.info("###########3 File Upload Failed ################# ");
			model.addAttribute("uploadStatus", "File upload failed");
			model.addAttribute("uploadFlag", new String("failed"));
			getSessionService().setAttribute("uploadStatus", "File upload failed");

			return false;
		}
	}

	@RequestMapping(value = "/uploadorderstatus", method =
	{ RequestMethod.GET, RequestMethod.POST })
	public String uploadorderstatus(final Model model) throws CMSItemNotFoundException
	{
		final boolean isAnoymousUser = isAnonymousUser(userService);
		if (!isAnoymousUser)
		{
			final String shipToAcc = (String) getSessionService().getAttribute(WebConstants.SHIPTO_ACCOUNT_ID);
			final FMCustomerModel customer = (FMCustomerModel) userService.getCurrentUser();
			boolean isCSRLogin = false;
			final Set<PrincipalGroupModel> groupss = customer.getGroups();
			for (final PrincipalGroupModel groupModel : groupss)
			{
				final String groupId = groupModel.getUid();
				if (groupId.contains("FMCSR") || groupId.contains("FMBUVOR"))
				{
					isCSRLogin = true;
					break;
				}
			}

			final List<uploadOrderData> uploadOrderData = isCSRLogin ? uploadOrderFacade.viewCSRUploadorder(shipToAcc)
					: uploadOrderFacade.viewUploadorder();

			storeCmsPageInModel(model, getContentPageForLabelOrId("FMB2BHomePage"));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId("FMB2BHomePage"));
			updatePageTitle(model, getContentPageForLabelOrId("FMB2BHomePage"));
			model.addAttribute("clickedlink", "ClickedUploadOrder");
			model.addAttribute("uploadOrderData", uploadOrderData);
			model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getFMBreadcrumbs("Upload Order Status"));
			return ControllerConstants.Views.Pages.Account.UploadOrderStatus;
		}
		else
		{
			return REDIRECT_PREFIX + ROOT;
		}
	}

	public boolean isAnonymousUser(final UserService userService)
	{
		final UserModel user = userService.getCurrentUser();
		final boolean isUserAnonymous = user == null || userService.isAnonymousUser(user);
		return isUserAnonymous;
	}
}
