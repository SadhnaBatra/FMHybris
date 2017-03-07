/**
 * 
 */
package com.federalmogul.storefront.controllers.pages;

import de.hybris.platform.b2bacceleratorservices.company.CompanyB2BCommerceService;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;
import de.hybris.platform.util.Config;
import org.springframework.beans.factory.annotation.Autowired;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.util.IOUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.federalmogul.core.model.FMCustomerAccountModel;
import com.federalmogul.storefront.constants.WebConstants;
import com.federalmogul.storefront.controllers.ControllerConstants;
import com.federalmogul.storefront.controllers.helper.InvoiceHeaderHelper;
import com.fmo.wom.business.as.InvoiceASUSCanImpl;
import com.fmo.wom.business.util.invoice.InvoiceHelper;
import com.fmo.wom.business.util.invoice.InvoiceSearchValues;
import com.fmo.wom.domain.BillToAcctBO;
import com.fmo.wom.domain.CustomerSalesOrgBO;
import com.fmo.wom.domain.InvoiceBO;
import com.fmo.wom.domain.InvoiceDetailsBO;
import com.fmo.wom.domain.InvoiceSearchCriteriaBO;
import com.fmo.wom.domain.SalesOrganizationBO;
import com.fmo.wom.domain.SapAcctBO;
import com.fmo.wom.domain.enums.InvoiceTypeFilter;
import com.federalmogul.facades.uploadorder.UploadOrderFacade;
import com.federalmogul.facades.account.FMCustomerFacade;
import com.federalmogul.core.network.dao.FMUserSearchDAO;




/**
 * uploadOrder for B2B
 * 
 */

@Controller
@Scope("tenant")
@RequestMapping(value = "/invoice")
public class InvoicesController extends AbstractPageController
{
	private static final Logger LOG = Logger.getLogger(InvoicesController.class);

	public static final String CMS_PAGE_MODEL = "cmsPage";
	public static final String CMS_PAGE_TITLE = "pageTitle";
	private static final String FMORDER_HISTORY_CMS_PAGE = "OrderHistoryPage";
	private static final String FM_ONLINE_TOOLS_PAGE = "FMUserOnlineTools";

	@Resource
	protected FMCustomerFacade fmCustomerFacade;

	@Autowired
	protected FMUserSearchDAO fmUserSearchDAO;
@Autowired
	private UploadOrderFacade uploadorderFacade;



	@Resource
	protected CompanyB2BCommerceService companyB2BCommerceService;

	@RequestMapping(value = "/invoice-header", method = RequestMethod.POST)
	public String invoiceHeader(final Model model, @RequestParam(value = "ponumber", required = false) final String ponumber,
			@RequestParam(value = "invoice", required = false) final String invoice,
			@RequestParam(value = "sdate", required = false) final String sdate,
			@RequestParam(value = "edate", required = false) final String edate,
			@RequestParam(value = "status", required = false) final String status)
	{
		final InvoiceHelper invoiceHelper;
		final InvoiceHeaderHelper invoiceHeaderHelper;
		try
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


			final Format formatter = new SimpleDateFormat("MM/dd/yyyy");

			if (sdate != null && sdate != "")
			{
				model.addAttribute("sdate", sdate);
				getSessionService().setAttribute("sdate", sdate);

			}
			else
			{

				final String backStartDate = formatter.format(getInvoiceSearchStartDate(sdate));
				model.addAttribute("sdate", backStartDate);
				getSessionService().setAttribute("sdate", sdate);


			}
			if (edate != null && edate != "")
			{

				model.addAttribute("edate", edate);
				getSessionService().setAttribute("edate", edate);


			}
			else
			{
				final String currntEndDate = formatter.format(getInvoiceSearchEndDate(edate));
				model.addAttribute("edate", currntEndDate);
				getSessionService().setAttribute("edate", edate);

			}
			final InvoiceASUSCanImpl invoiceService = new InvoiceASUSCanImpl();
			List<InvoiceBO> invoicesList = new ArrayList<InvoiceBO>();
			//if (status == null || ("ALL").equalsIgnoreCase(status))
			//{
			//	final List<InvoiceBO> creditList = invoiceService.searchInvoices(createInvoicesSearchCriteria(ponumber, invoice,
			//		sdate, edate, "CREDITMEMO", fmCustomerAccModel));
			//	final List<InvoiceBO> invoiceList = invoiceService.searchInvoices(createInvoicesSearchCriteria(ponumber, invoice,
			//		sdate, edate, "INVOICE", fmCustomerAccModel));

			//if (null != creditList && !creditList.isEmpty())
			//{
			//	invoicesList.addAll(creditList);
			//}
			//	if (null != invoiceList && !invoiceList.isEmpty())
			//	{
			//	invoicesList.addAll(invoiceList);
			//}
			//}
			//else
			//{
			invoicesList = invoiceService.searchInvoices(createInvoicesSearchCriteria(ponumber, invoice, sdate, edate, status,
					fmCustomerAccModel));
			//}

			if (invoicesList != null && !(invoicesList.isEmpty()))
			{
				LOG.info("\n Searched Invoices .. " + invoicesList.size());
				model.addAttribute("InvoiceHeader", invoicesList);

				//Added by sai for Sorting
				invoiceHelper = new InvoiceHelper(invoicesList);
				invoiceHeaderHelper = new InvoiceHeaderHelper();
				final List<InvoiceSearchValues> invoiceSearchValuesList = invoiceHeaderHelper.performInvoiceDateSort(invoiceHelper);

				getSessionService().setAttribute("sessionInvoiceHeaderResult", invoiceSearchValuesList);
				getSessionService().setAttribute("sessionInvoiceHelper", invoiceHelper);


				model.addAttribute("invoiceSearchValues", invoiceSearchValuesList);

			}
			else
			{
				LOG.info("\n No Invoices Found .......");
				model.addAttribute("InvoiceHeader", invoicesList);
			}

		}
		catch (final Exception e)
		{
			LOG.error("Exception While Searching Invoices ....." + e.getMessage());
			e.printStackTrace();
			model.addAttribute("InvoiceHeader", null);
		}
		//storeCmsPageInModel(model, getContentPageForLabelOrId(FMORDER_HISTORY_CMS_PAGE));
		//	setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FMORDER_HISTORY_CMS_PAGE));
		model.addAttribute("metaRobots", "no-index,no-follow");
		return ControllerConstants.Views.Pages.Account.FMInvoiceHeader;

	}


	@RequestMapping(value = "/invoice-header-export-excel", method = RequestMethod.GET)
	public ModelAndView invoiceHeaderExcel(final Model model,
			@RequestParam(value = "ponumber", required = false) final String ponumber,
			@RequestParam(value = "invoice", required = false) final String invoice,
			@RequestParam(value = "sdate", required = false) final String sdate,
			@RequestParam(value = "edate", required = false) final String edate,
			@RequestParam(value = "status", required = false) final String status)
	{
		final List<InvoiceSearchValues> invoiceSearchValuesList = getSessionService().getAttribute("sessionInvoiceHeaderResult");


		return new ModelAndView("invoiceExcelView", "invoiceSearchValuesList", invoiceSearchValuesList);
	}

	@RequestMapping(value = "/invoice-header-export-pdf", method = RequestMethod.GET)
	public ModelAndView invoiceHeaderPdf(final Model model,
			@RequestParam(value = "ponumber", required = false) final String ponumber,
			@RequestParam(value = "invoice", required = false) final String invoice,
			@RequestParam(value = "sdate", required = false) final String sdate,
			@RequestParam(value = "edate", required = false) final String edate,
			@RequestParam(value = "status", required = false) final String status)
	{
		final List<InvoiceSearchValues> invoiceSearchValuesList = getSessionService().getAttribute("sessionInvoiceHeaderResult");

		return new ModelAndView("invoicePdfView", "invoiceSearchValuesList", invoiceSearchValuesList);
	}

	@RequestMapping(value = "/invoice-details/{invoiceNumber}", method = RequestMethod.POST)
	public String invoiceDeatils(final Model model, @PathVariable("invoiceNumber") final String invoiceNumber)
			throws CMSItemNotFoundException
	{
		LOG.info("Invoice");
		try
		{
			LOG.info("invoiceNumber" + invoiceNumber);
			final String invoiceNumbers = "1005839489";
			final InvoiceASUSCanImpl invoiceService = new InvoiceASUSCanImpl();
			final InvoiceDetailsBO invoiceDetails = invoiceService.getInvoicedetails(invoiceNumber != null ? invoiceNumber
					: invoiceNumbers);
			model.addAttribute("InvoiceDetails", invoiceDetails);
			LOG.info("\n invoiceDetails ......." + invoiceDetails);

		}
		catch (final Exception e)
		{
			LOG.error("Exception While Fetching Invoice Details .....");
			model.addAttribute("InvoiceDetails", null);
		}
		storeCmsPageInModel(model, getContentPageForLabelOrId(FMORDER_HISTORY_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FMORDER_HISTORY_CMS_PAGE));
		model.addAttribute("metaRobots", "no-index,no-follow");
		return ControllerConstants.Views.Pages.Account.FMInvoiceDetail;

	}


	private static InvoiceSearchCriteriaBO createInvoicesSearchCriteria(final String ponumber, final String invoice,
			final String sdate, final String edate, final String status, final FMCustomerAccountModel fmCustomerAccModel)
	{

		final InvoiceSearchCriteriaBO searchCriteria = new InvoiceSearchCriteriaBO();
		//String documentCategory;
		searchCriteria.setDelimitedInvoiceNumbers(invoice);
		searchCriteria.setBillToAcct(getBillTOAccount(fmCustomerAccModel));

		if (((ponumber != null && ponumber != "") || (invoice != null && invoice != ""))
				&& ((sdate == null || "".equals(sdate)) || (edate == null || "".equals(edate))))
		{
			searchCriteria.setStartInvoiceDate(null);
			searchCriteria.setEndInvoiceDate(null);
		}
		else
		{
			searchCriteria.setStartInvoiceDate(getInvoiceSearchStartDate(sdate));
			searchCriteria.setEndInvoiceDate(getInvoiceSearchEndDate(edate));
		}

		searchCriteria.setCustPONumber(ponumber);
		//searchCriteria.setStartInvoiceDate(getInvoiceSearchStartDate());
		//searchCriteria.setEndInvoiceDate(getInvoiceSearchEndDate());



		//	final InvoiceTypeFilter filter = InvoiceTypeFilter.ALL;
		searchCriteria.setInvoiceType(InvoiceTypeFilter.ALL);
		//documentCategory = filter.getCode();

		if (null != status && status.equals("CREDITMEMO"))
		{
			searchCriteria.setInvoiceType(InvoiceTypeFilter.CREDITMEMO);
		}
		if (null != status && status.equals("INVOICE"))
		{
			searchCriteria.setInvoiceType(InvoiceTypeFilter.INVOICE);
		}
		LOG.info("documentCategory :: " + searchCriteria.getInvoiceType().name());
		LOG.info("searchCriteria :: " + searchCriteria.toString());
		//searchCriteria.setDocumentCategory(documentCategory);
		//searchCriteria.setDocumentCategory("U");
		return searchCriteria;
	}

	private static Date getInvoiceSearchStartDate(final String sdate)
	{
		final Calendar startCalendar = Calendar.getInstance();
		//startCalendar.clear();
		if (sdate != null)
		{
			final Date date = getUtilDate(WebConstants.DD_MM_YYYY, sdate);
			return date;
		}
		else
		{
			try
			{
				final int days = Integer.parseInt(Config.getParameter(WebConstants.MYACCOUNT_INVOICE_START_DATE));
				LOG.info(" Days : " + days);
				startCalendar.add(Calendar.DAY_OF_YEAR, -1 * days);
			}
			catch (final Exception ex)
			{
				startCalendar.add(Calendar.DAY_OF_YEAR, -1 * 7);
			}

		}
		return startCalendar.getTime();
	}

	private static Date getInvoiceSearchEndDate(final String edate)
	{
		final Calendar endCalendar = Calendar.getInstance();
		//endCalendar.clear();
		if (edate != null)
		{
			final Date date = getUtilDate(WebConstants.DD_MM_YYYY, edate);
			return date;
			//endCalendar.set(date.getYear(), date.get, 03);
		}
		else
		{
			return endCalendar.getTime();
		}
		//return new Date();
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

	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/invoicePDF", method =
	{ RequestMethod.GET, RequestMethod.POST })
	public String getInvoicePDF(@RequestParam(value = "invoiceNo", required = true) final String selectedInvoiceNumber,
			@RequestParam(value = "invoiceDate", required = true) final String selectedInvoiceDate, final Model model,
			final HttpServletRequest req, final HttpServletResponse res) throws IOException
	{

		//private static String appExtenderUrl =  "http://sfldmims498.federalmogul.com:8888";
		final String invoiceTomcatUrl = Config.getParameter("fm.invoice.tomcat.url");
		final String invoiceDetailDir = Config.getParameter("fm.invoice.share.dir");

		LOG.info("############## getInvoicePDF ##################### 11");

		LOG.info("############## invoiceNo :: " + selectedInvoiceNumber);

		LOG.info("############## orderInvoiceDate :: " + selectedInvoiceDate);


		/* Mahaveer - App extender - */



		//final String selectedInvoiceDate = UtilDate.getDate(WebConstants.YYYYMMDD, orderInvoiceDate);

		final String selectedAppCode = Config.getParameter(WebConstants.FM_APP_XTENDER_APP_CODE);
		final String appExtenderUrl = Config.getParameter(WebConstants.FM_APP_XTENDER_SERVICE_URL);

		final long randomNumber = System.nanoTime();
		final StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append(invoiceTomcatUrl);
		urlBuilder.append("?invNum=").append(selectedInvoiceNumber);
		urlBuilder.append("&invDate=").append(selectedInvoiceDate);
		urlBuilder.append("&appCode=").append(selectedAppCode);
		urlBuilder.append("&appExtenderUrl=").append(URLEncoder.encode(appExtenderUrl));
		urlBuilder.append("&invoiceDetailDir=").append(URLEncoder.encode(invoiceDetailDir));
		urlBuilder.append("&randomNumber=").append(randomNumber);

		LOG.info("START : Going to get Invoice Detail " + urlBuilder);
		res.reset();
		res.resetBuffer();
		res.setContentType("application/pdf");
		try
		{
			final URL url = new URL(urlBuilder.toString().trim());
			final InputStream in = url.openConnection().getInputStream();

			final BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line = null;
			final StringBuilder responseStr = new StringBuilder();
			while ((line = br.readLine()) != null)
			{
				responseStr.append(line);
			}
			LOG.info("Response Recived From Invoice Details server" + responseStr);

			if ("FileDownloaded".equalsIgnoreCase(responseStr.toString().trim()))
			{

				final String fileName = selectedInvoiceNumber.trim() + String.valueOf(randomNumber) + ".pdf";

				final StringBuilder invoicedirpath = new StringBuilder();
				invoicedirpath.append(invoiceDetailDir).append(fileName);

				final File invoiceFile = new File(invoicedirpath.toString());

				if (invoiceFile.exists())
				{
					LOG.info("Sending Invoice details to browser ...");
					//System.out.println("Tomcat 7 Sending file ... ");

					final FileInputStream fin = new FileInputStream(invoiceFile);
					final OutputStream os = res.getOutputStream();

					IOUtils.copy(fin, os);

					os.flush();
					res.flushBuffer();
					os.close();
					fin.close();
					LOG.info("Invoice details sent to browser ...");
					//System.out.println("Tomcat 7 file sent ... ");

					if (invoiceFile.exists())
					{
						final boolean invoiceFiledeted = invoiceFile.delete();
						LOG.info("invoiceFiledeted " + invoiceFiledeted);
						//System.out.println("invoiceFiledeted "+invoiceFiledeted);
					}
				}

			}
			else
			{
				LOG.error("Failed to fetch the invoice details " + selectedInvoiceNumber + " " + selectedInvoiceDate);
				System.out.println("Failed to fetch the invoice details ....");
			}
			LOG.info("END : Got Invoice Detail " + selectedInvoiceNumber + " " + selectedInvoiceDate);
			br.close();
			in.close();


		}
		catch (final IOException e)
		{
			// YTODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			res.flushBuffer();
		}
		final String soldToAcc = (String) getSessionService().getAttribute(WebConstants.SOLDTO_ACCOUNT_ID);
		FMCustomerAccountModel fmCustomerAccModel = (FMCustomerAccountModel) companyB2BCommerceService.getUnitForUid(soldToAcc);
		uploadorderFacade.createReportLog(fmCustomerAccModel,(fmUserSearchDAO.getUserForUID(fmCustomerFacade.getCurrentFMCustomer().getUid())), "Invoice");


		//generateInvoicePDF(appXtenderUrl.toString(), req, resp);
		//storeCmsPageInModel(model, getContentPageForLabelOrId(FM_ONLINE_TOOLS_PAGE));
		//	setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FM_ONLINE_TOOLS_PAGE));
		//model.addAttribute("breadcrumbs", "Invoice PDF");
		//model.addAttribute("metaRobots", "no-index,no-follow");

		//LOG.info("############## getInvoicePDF ##################### 22");

		//return ControllerConstants.Views.Pages.Account.FMOnlineToolsPage;
		//return new ModelAndView("invoiceDetailsView", "invoiceURL", appXtenderUrl.toString());
		return null;
	}

	public void generateInvoicePDF(final String url, final HttpServletRequest req, final HttpServletResponse resp)
	{

		LOG.info("######### generateInvoicePDF Called ################ 33");
		try
		{
			LOG.info("######### generateInvoicePDF Called ################ 44");
			resp.setContentType("application/pdf");
			final URL newUrl = new URL(url);

			final URLConnection connection1 = newUrl.openConnection();

			connection1.connect();

			// Cast to a HttpURLConnection
			if (connection1 instanceof HttpURLConnection)
			{
				final HttpURLConnection httpConnection = (HttpURLConnection) connection1;

				final int code = httpConnection.getResponseCode();

				LOG.info("code ::::: " + code);
			}
			else
			{
				LOG.info("Its not Http url ");
			}

			LOG.info("######### generateInvoicePDF URL  ################ 55" + newUrl);
			final HttpURLConnection connection = (HttpURLConnection) newUrl.openConnection();
			connection.connect();
			LOG.info("response code :: " + connection.getResponseCode());
			if (connection.getResponseCode() == 200)
			{
				LOG.info("######### generateInvoicePDF response Code 200 ################ 33");
				final int contentLength = connection.getContentLength();
				resp.setContentLength(contentLength);

				final InputStream pdfSource = connection.getInputStream();
				final OutputStream pdfTarget = resp.getOutputStream();

				final int filechunkSize = 1024 * 4;
				final byte[] chunk = new byte[filechunkSize];
				int n = 0;
				while ((n = pdfSource.read(chunk)) != -1)
				{
					LOG.info("N value :: " + n);
					pdfTarget.write(chunk, 0, n);
				}
				LOG.info("############# ok ###########3");

			}
		}
		catch (final IOException e)
		{
			LOG.error("IOException ...." + e.getMessage());
		}

	}



	protected void updatePageTitle(final Model model, final AbstractPageModel cmsPage)
	{
		storeContentPageTitleInModel(model, getPageTitleResolver().resolveHomePageTitle(cmsPage.getTitle()));
	}

	public static Date getUtilDate(final String dateFormate, final String date)
	{
		if (date != null && date != "")
		{

			final SimpleDateFormat sdf = new SimpleDateFormat(dateFormate);
			try
			{
				final Date convertedDate = sdf.parse(date);
				return convertedDate;

			}
			catch (final ParseException e)
			{
				LOG.error("ParseException ....." + e.getMessage());
				return new Date();
			}
		}
		else
		{
			return new Date();
		}

	}

	/**
	 * 
	 * @param sortBy
	 * @param model
	 * @return
	 * @throws CMSItemNotFoundException
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/sortInvoice-Header")
	public String getSortInvoiceHeader(@RequestParam(value = "sortBy") final String sortBy, final Model model)
			throws CMSItemNotFoundException
	{
		LOG.info("**** Invoking sortInvoiceHeader **** SortBy :: " + sortBy);

		final List<InvoiceSearchValues> invoiceSearchValuesList = getSessionService().getAttribute("sessionInvoiceHeaderResult");
		final InvoiceHelper invoiceHelper = getSessionService().getAttribute("sessionInvoiceHelper");
		InvoiceHeaderHelper invoiceHeaderHelper;

		if ((invoiceSearchValuesList != null && (!invoiceSearchValuesList.isEmpty())) && (invoiceHelper != null))
		{
			if (sortBy.equals(WebConstants.PURCHASE_ORDER_NUMBER))
			{
				invoiceHeaderHelper = new InvoiceHeaderHelper();
				final List<InvoiceSearchValues> sortedinvoiceSearchValues = invoiceHeaderHelper
						.performPurchaseOrderNumberSort(invoiceHelper);
				LOG.info("sortedOrderStatusResult :: " + sortedinvoiceSearchValues.size());
				model.addAttribute("invoiceSearchValues", sortedinvoiceSearchValues);
			}
			if (sortBy.equals(WebConstants.INVOICE_NUMBER))
			{
				invoiceHeaderHelper = new InvoiceHeaderHelper();
				final List<InvoiceSearchValues> sortedinvoiceSearchValues = invoiceHeaderHelper
						.performInvoiceNumberSort(invoiceHelper);
				LOG.info("sortedOrderStatusResult :: " + sortedinvoiceSearchValues.size());
				model.addAttribute("invoiceSearchValues", sortedinvoiceSearchValues);
			}
			if (sortBy.equals(WebConstants.INVOICE_DATE))
			{
				invoiceHeaderHelper = new InvoiceHeaderHelper();
				final List<InvoiceSearchValues> sortedinvoiceSearchValues = invoiceHeaderHelper.performInvoiceDateSort(invoiceHelper);
				LOG.info("sortedOrderStatusResult :: " + sortedinvoiceSearchValues.size());
				model.addAttribute("invoiceSearchValues", sortedinvoiceSearchValues);
			}
			if (sortBy.equals(WebConstants.INVOICE_TYPE))
			{
				invoiceHeaderHelper = new InvoiceHeaderHelper();
				final List<InvoiceSearchValues> sortedinvoiceSearchValues = invoiceHeaderHelper.performInvoiceTypeSort(invoiceHelper);
				LOG.info("sortedOrderStatusResult :: " + sortedinvoiceSearchValues.size());
				model.addAttribute("invoiceSearchValues", sortedinvoiceSearchValues);
			}
		}

		storeCmsPageInModel(model, getContentPageForLabelOrId(FMORDER_HISTORY_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FMORDER_HISTORY_CMS_PAGE));

		model.addAttribute("sdate", getSessionService().getAttribute("sdate"));
		model.addAttribute("edate", getSessionService().getAttribute("edate"));

		model.addAttribute("metaRobots", "no-index,no-follow");
		return ControllerConstants.Views.Pages.Account.FMInvoiceHeader;


	}

}
