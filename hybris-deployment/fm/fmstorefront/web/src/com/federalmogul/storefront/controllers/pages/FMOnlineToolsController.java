/**
 * 
 */
package com.federalmogul.storefront.controllers.pages;

/**
 * @author SA297584
 *
 */
import de.hybris.platform.b2bacceleratorservices.company.CompanyB2BCommerceService;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.util.Config;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection; 
import java.util.Date;
import java.util.ArrayList;
import java.util.Iterator;
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
import org.springframework.web.multipart.MultipartFile;

import com.federalmogul.core.account.FMCustomerService;
import com.federalmogul.core.graph.form.CSBProductPermissionForm;
import com.federalmogul.core.model.FMCustomerAccountModel;
import com.federalmogul.core.model.FMFileDownloadModel;
import com.federalmogul.facades.account.FMCustomerFacade;
import com.federalmogul.facades.account.FMUserOnlineToolsFacade;
import com.federalmogul.facades.user.data.FMCustomerData;
import com.federalmogul.falcon.core.model.CPL1CustomerModel;
import com.federalmogul.falcon.core.model.CSBPercents3612Model;
import com.federalmogul.falcon.core.model.CategorySalesBenchmarkPercentsModel;
import com.federalmogul.storefront.breadcrumb.Breadcrumb;
import com.federalmogul.storefront.constants.WebConstants;
import com.federalmogul.storefront.controllers.ControllerConstants;



@Controller
@Scope("tenant")
@RequestMapping("/**/online-tools")
public class FMOnlineToolsController extends SearchPageController
{
	private static final Logger LOG = Logger.getLogger(FMOnlineToolsController.class);

	@Resource
	private FMUserOnlineToolsFacade fmUserOnlineToolsFacade;

	@Resource
	protected FMCustomerFacade fmCustomerFacade;

	@Resource
	protected CompanyB2BCommerceService companyB2BCommerceService;

	@Resource(name = "fmcustomerservice")
	private FMCustomerService fmcustomerservice;

	private static final String FM_ONLINE_TOOLS_PAGE = "FMUserOnlineTools";

	private static final String FM_HELP_CENTER_PAGE = "FMUserHelpCenter";

	private static final String FM_RESEARCH_FILDEOWNLOAD_PAGE = "OnlineToolsLandingPage";

	public static final String FM_ONLINE_TOOL_FILES_LOCATION = "online.tool.files.path";

	public static final String FM_ONLINE_TOOL_FILES_FOLDER = "online.tool.files.folders";

	private static final String PRODUCT_CODE_VARIABLE_PATTERN = "{productCode:.*}";
	private static final String PRODUCT_DESC_VARIABLE_PATTERN = "{productDesc:.*}";
	

	@RequestMapping(value = "/overview", method = RequestMethod.GET)
	public String getOverviewPage(final Model model) throws CMSItemNotFoundException
	{

		LOG.info("############## getOverviewPage ##################### 11");


		model.addAttribute("selectedLinkName", "overview");
		storeCmsPageInModel(model, getContentPageForLabelOrId(FM_ONLINE_TOOLS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FM_ONLINE_TOOLS_PAGE));
		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb("/online-tools/overview", "Online Tools", null));
		breadcrumbs.add(new Breadcrumb("#", "Overview", null));
		getOnlineToolsFolderLinks(model);

		model.addAttribute("breadcrumbs", breadcrumbs);
		model.addAttribute("metaRobots", "no-index,no-follow");
		model.addAttribute("nabsAccCode", userNABSCode());
		model.addAttribute("clickedLink", "Overview");
		LOG.info("############## getOverviewPage ##################### 22");

		return ControllerConstants.Views.Pages.Account.FMOnlineToolsPage;
	}


	/*
	 * @RequestMapping(value = "/motorparts-monitor", method = RequestMethod.GET) public String
	 * getMotorpartsMonitorPage(final Model model) throws CMSItemNotFoundException {
	 * 
	 * LOG.info("############## getMotorpartsMonitorPage ##################### 11");
	 * 
	 * 
	 * model.addAttribute("selectedLinkName", "motorpartsMonitor"); storeCmsPageInModel(model,
	 * getContentPageForLabelOrId(FM_ONLINE_TOOLS_PAGE)); setUpMetaDataForContentPage(model,
	 * getContentPageForLabelOrId(FM_ONLINE_TOOLS_PAGE));
	 * 
	 * final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>(); breadcrumbs.add(new
	 * Breadcrumb("/online-tools/overview", "Online Tools", null)); breadcrumbs.add(new Breadcrumb("#",
	 * "Motorparts Monitor", null)); getOnlineToolsFolderLinks(model);
	 * 
	 * model.addAttribute("breadcrumbs", breadcrumbs); model.addAttribute("metaRobots", "no-index,no-follow");
	 * model.addAttribute("nabsAccCode", userNABSCode());
	 * 
	 * LOG.info("############## getMotorpartsMonitorPage ##################### 22");
	 * 
	 * return ControllerConstants.Views.Pages.Account.FMOnlineToolsPage; }
	 */

	@RequestMapping(value = "/FileDownloads", method = RequestMethod.GET)
	public String getOnlineToolsPage(final Model model,
			@RequestParam(value = "dirname", defaultValue = "UPC") final String directoryName) throws CMSItemNotFoundException
	{

		LOG.info("############## getOnlineToolsPage ##################### 11");




		final List<FMFileDownloadModel> fmFileDownloadsList = fmUserOnlineToolsFacade.getAllFilesForDownload(directoryName);

		model.addAttribute("selectedLinkName", "Online Tools");
		storeCmsPageInModel(model, getContentPageForLabelOrId(FM_RESEARCH_FILDEOWNLOAD_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FM_RESEARCH_FILDEOWNLOAD_PAGE));
		model.addAttribute("fmFileDownloadsList", fmFileDownloadsList);
		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();

		breadcrumbs.add(new Breadcrumb("/online-tools/overview", "Online Tools", null));
		breadcrumbs.add(new Breadcrumb("/online-tools/FileDownloads", "File Downloads", null));
		breadcrumbs.add(new Breadcrumb("#", directoryName, null));

		getOnlineToolsFolderLinks(model);

		model.addAttribute("breadcrumbs", breadcrumbs);
		model.addAttribute("metaRobots", "no-index,no-follow");
		model.addAttribute("nabsAccCode", userNABSCode());
		model.addAttribute("dirname", directoryName);
		model.addAttribute("clickedLink", "FileDownloads");
		LOG.info("############## getOnlineToolsPage ##################### 22");

		return ControllerConstants.Views.Pages.Account.FMOnlineToolsPage;
	}

	@RequestMapping(value = "/research", method = RequestMethod.GET)
	public String getResearchPage(final Model model,
			@RequestParam(value = "dirname", defaultValue = "Research") final String directoryName) throws CMSItemNotFoundException
	{

		LOG.info("############## getResearchPage ##################### 11");


		final List<FMFileDownloadModel> fmFileDownloadsList = fmUserOnlineToolsFacade.getAllFilesForDownload(directoryName);

		//LOG.info("fmFileDownloadsList size :: " + fmFileDownloadsList.size());

		model.addAttribute("fmFileDownloadsList", fmFileDownloadsList);

		model.addAttribute("selectedLinkName", "Research");
		storeCmsPageInModel(model, getContentPageForLabelOrId(FM_RESEARCH_FILDEOWNLOAD_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FM_RESEARCH_FILDEOWNLOAD_PAGE));

		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb("/online-tools/overview", "Online Tools", null));
		breadcrumbs.add(new Breadcrumb("#", "Research", null));
		getOnlineToolsFolderLinks(model);

		model.addAttribute("breadcrumbs", breadcrumbs);
		model.addAttribute("metaRobots", "no-index,no-follow");
		model.addAttribute("clickedLink", "Research");
		LOG.info("############## getResearchPage ##################### 22");

		return ControllerConstants.Views.Pages.Account.FMOnlineToolsPage;
	}


	@RequestMapping(value = "/market-your-shop", method =
	{ RequestMethod.GET, RequestMethod.POST })
	public String getMarketPage(final Model model,
			@RequestParam(value = "dirname", defaultValue = "Marketyourshop") final String directoryName)
			throws CMSItemNotFoundException
	{

		LOG.info("############## getResearchPage ##################### 11");


		final List<FMFileDownloadModel> fmFileDownloadsList = fmUserOnlineToolsFacade.getAllFilesForDownload(directoryName);

		//LOG.info("fmFileDownloadsList size :: " + fmFileDownloadsList.size());

		model.addAttribute("fmFileDownloadsList", fmFileDownloadsList);

		model.addAttribute("selectedLinkName", "Market Your Shop");
		storeCmsPageInModel(model, getContentPageForLabelOrId(FM_RESEARCH_FILDEOWNLOAD_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FM_RESEARCH_FILDEOWNLOAD_PAGE));

		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb("/online-tools/overview", "Online Tools", null));
		breadcrumbs.add(new Breadcrumb("#", "Market Your Shop", null));
		getOnlineToolsFolderLinks(model);

		model.addAttribute("nabsAccCode", userNABSCode());
		model.addAttribute("breadcrumbs", breadcrumbs);
		model.addAttribute("metaRobots", "no-index,no-follow");
		model.addAttribute("clickedLink", "MarketYourShop");
		LOG.info("############## getResearchPage ##################### 22");

		return ControllerConstants.Views.Pages.Account.FMOnlineToolsPage;
	}


	@RequestMapping(value = "/research-uploadFile", headers = "content-type=multipart/*", method = RequestMethod.POST)
	public String uploadResearchFile(final Model model,
			@RequestParam(value = "dirname", defaultValue = "Research") final String directoryName,
			@RequestParam(value = "researchFile", required = false) final MultipartFile researchFile)
			throws CMSItemNotFoundException
	{

		LOG.info("############## getResearchPage ##################### 11");
		boolean uploadStatus = false;

		if (!researchFile.isEmpty())
		{
			uploadStatus = fmUserOnlineToolsFacade.uploadResearchFileToFMFileDownloadModel(researchFile);
			LOG.info("researchFile " + researchFile.getOriginalFilename());
			LOG.info("uploadStatus :: " + uploadStatus);
		}
		final List<FMFileDownloadModel> fmFileDownloadsList = fmUserOnlineToolsFacade.getAllFilesForDownload(directoryName);

		LOG.info("fmFileDownloadsList size :: " + fmFileDownloadsList.size());

		model.addAttribute("fmFileDownloadsList", fmFileDownloadsList);

		model.addAttribute("selectedLinkName", "research");
		storeCmsPageInModel(model, getContentPageForLabelOrId(FM_ONLINE_TOOLS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FM_ONLINE_TOOLS_PAGE));

		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb("/online-tools/overview", "Online Tools", null));
		breadcrumbs.add(new Breadcrumb("#", "Research", null));
		getOnlineToolsFolderLinks(model);

		model.addAttribute("breadcrumbs", breadcrumbs);
		model.addAttribute("metaRobots", "no-index,no-follow");
		model.addAttribute("nabsAccCode", userNABSCode());
		LOG.info("############## getResearchPage ##################### 22");

		return ControllerConstants.Views.Pages.Account.FMOnlineToolsPage;
	}



	@RequestMapping(value = "/fmHelpCenter", method = RequestMethod.GET)
	public String getfmHelpCenterPage(final Model model,
			@RequestParam(value = "dirname", defaultValue = "HelpCenter") final String directoryName)
			throws CMSItemNotFoundException
	{

		LOG.info("############## getfmHelpCenterPage ##################### 11");




		final List<FMFileDownloadModel> fmFileDownloadsList = fmUserOnlineToolsFacade.getAllFilesForDownload(directoryName);

		model.addAttribute("selectedLinkName", "HelpCenter");
		storeCmsPageInModel(model, getContentPageForLabelOrId(FM_HELP_CENTER_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FM_HELP_CENTER_PAGE));
		model.addAttribute("fmFileDownloadsList", fmFileDownloadsList);
		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb("fmHelpCenter", "Help Center", null));
		breadcrumbs.add(new Breadcrumb("#", "Help Center", null));
		getOnlineToolsFolderLinks(model);

		model.addAttribute("breadcrumbs", breadcrumbs);
		model.addAttribute("metaRobots", "no-index,no-follow");
		model.addAttribute("dirname", directoryName);
		LOG.info("############## getfmHelpCenterPage ##################### 22");

		return ControllerConstants.Views.Pages.Account.FMHelpCenterPage;
	}


	public void getOnlineToolsFolderLinks(final Model model)
	{

		final String folderList = Config.getParameter(FM_ONLINE_TOOL_FILES_FOLDER);
		//final File directory = new File(Config.getParameter(FM_ONLINE_TOOL_FILES_LOCATION));
		final List<String> foldersNameList = new ArrayList<String>();
		for (final String folder : folderList.split("~"))
		{
			LOG.info("File Name :: " + folder);
			foldersNameList.add(folder.trim());

		}
		LOG.info("foldersNameList Size :: " + foldersNameList.size());
		model.addAttribute("onlineToolFoldersList", foldersNameList);

	}

	/**
	 * Method to return NABS account code
	 * 
	 * @return NABSAccount code as string
	 */
	private String userNABSCode()
	{
		String nabsCode = "";


		if (getSessionService().getAttribute(WebConstants.SOLDTO_ACCOUNT_ID) != null)
		{
			nabsCode = ((FMCustomerAccountModel) companyB2BCommerceService.getUnitForUid((String) getSessionService().getAttribute(
					WebConstants.SOLDTO_ACCOUNT_ID))).getNabsAccountCode();
			LOG.info("Nabs account code set from Session Sold To account " + nabsCode);
		}
		else if (fmCustomerFacade.getCurrentFMCustomer().getFmunit().getNabsAccountCode() != null)
		{
			nabsCode = fmCustomerFacade.getCurrentFMCustomer().getFmunit().getNabsAccountCode();
			LOG.info("Nabs account code set from Current User " + nabsCode);
		}
		return nabsCode;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/Motorparts-Monitor")
	public String motorPartsLanding(final Model model) throws CMSItemNotFoundException
	{
		LOG.info("inside motorparts ");
	

		List<CSBProductPermissionForm> queryresult = null;

		

		final String soldToAcc = userNABSCode();
		

		queryresult = fmcustomerservice.getPermissions(soldToAcc);
		

		final List<String> productCode = new ArrayList<String>();
		final List<String> prodDesc = new ArrayList<String>();
		final Iterator a = queryresult.iterator();
		while (a.hasNext())
		{
			final CSBProductPermissionForm a1 = (CSBProductPermissionForm) a.next();
			productCode.add(a1.getCpl1CustomerCode());
			prodDesc.add(fmcustomerservice.getDesc(a1.getCpl1CustomerCode()));
		}

		final List<CPL1CustomerModel> cpl1_list = fmcustomerservice.getAllProducts();
		final List<String> productCode_na = new ArrayList<String>();
		final List<String> prodDesc_na = new ArrayList<String>();
		for (final CPL1CustomerModel cpl1 : cpl1_list)
		{
			final String pid = cpl1.getCpl1ProductCode();
			final String pdesc = cpl1.getCpl1ProductDescription();

			if (!productCode.contains(pid))
			{
				productCode_na.add(pid);
				LOG.info("%%pid added" + pid);
			}
			if (!prodDesc.contains(pdesc))
			{
				prodDesc_na.add(pdesc);
				LOG.info("%%pdesc added" + pdesc);
			}

		} 
		model.addAttribute("landingPage", new String("isLanding"));
		model.addAttribute("productCodeList", productCode);
		model.addAttribute("productDescList", prodDesc);

		model.addAttribute("clickedLink", "MotorpartsMonitor");
		model.addAttribute("productCodeList_na", productCode_na);
		model.addAttribute("productDescList_na", prodDesc_na);

		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb("/online-tools/overview", "Online Tools", null));
		breadcrumbs.add(new Breadcrumb("#", "Motorparts Monitor", null));
		getOnlineToolsFolderLinks(model);

		model.addAttribute("breadcrumbs", breadcrumbs);
		model.addAttribute("metaRobots", "no-index,no-follow");
		//model.addAttribute("nabsAccCode", userNABSCode());
		storeCmsPageInModel(model, getContentPageForLabelOrId(FM_ONLINE_TOOLS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FM_ONLINE_TOOLS_PAGE));
		return ControllerConstants.Views.Pages.Account.FMOnlineToolsPage;
		//return ControllerConstants.Views.Pages.Account.FMCSBDashboardPage;
	}


	@RequestMapping(method = RequestMethod.GET, value = "/getGraph/" + PRODUCT_CODE_VARIABLE_PATTERN + "/"
			+ PRODUCT_DESC_VARIABLE_PATTERN)
	public String motorParts_Graph(final Model model, @PathVariable("productCode") final String inputProductCode,
			@PathVariable("productDesc") String inputProductDesc) throws CMSItemNotFoundException
	{
	LOG.info("inside motorParts_Graph method");
		LOG.info(" productCode" + inputProductCode);
		LOG.info(" productDesc" + inputProductDesc);


		List<CSBProductPermissionForm> queryresult = null;
		final String soldToAcc = userNABSCode();
		queryresult = fmcustomerservice.getPermissions(soldToAcc);

		//final List<CSBProductPermissionForm> queryresult = fmcustomerservice.getPermissions(fmCustomerData.getFmunit().getNabsAccountCode());
		final List<String> productCode = new ArrayList<String>();
		final List<String> prodDesc = new ArrayList<String>();
		String posDollars = null;
		String posUnits = null;
		final Iterator a = queryresult.iterator();
		while (a.hasNext())
		{
			final CSBProductPermissionForm a1 = (CSBProductPermissionForm) a.next();
			posDollars = a1.getPosDollars();
			posUnits = a1.getPosUnits();
			productCode.add(a1.getCpl1CustomerCode());
			prodDesc.add(fmcustomerservice.getDesc(a1.getCpl1CustomerCode()));
		}

		final List<CPL1CustomerModel> cpl1_list = fmcustomerservice.getAllProducts();
		final List<String> productCode_na = new ArrayList<String>();
		final List<String> prodDesc_na = new ArrayList<String>();
		for (final CPL1CustomerModel cpl1 : cpl1_list)
		{
			final String pid = cpl1.getCpl1ProductCode();
			final String pdesc = cpl1.getCpl1ProductDescription();

			if (!productCode.contains(pid))
			{
				productCode_na.add(pid);
				//LOG.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%pid added" + pid);
			}
			if (!prodDesc.contains(pdesc))
			{
				prodDesc_na.add(pdesc);
				//LOG.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%pdesc added" + pdesc);
			}

		}
		model.addAttribute("landingPage", new String("isNotLanding"));
		model.addAttribute("prodDesc", inputProductDesc);
		model.addAttribute("productCodeList", productCode);
		model.addAttribute("productDescList", prodDesc);
		model.addAttribute("clickedlink", "ClickedproductDesc");
		model.addAttribute("productCodeList_na", productCode_na);
		model.addAttribute("productDescList_na", prodDesc_na);

		if (inputProductDesc.equals("Steering Suspension"))
		{
			inputProductDesc = "Steering/Suspension";
		}

		model.addAttribute("desc", inputProductDesc);

		final List<CategorySalesBenchmarkPercentsModel> saleChange_graphValues = fmcustomerservice
				.getUnitorSaleChangePercentsModel(inputProductCode);
		final List<String> xaxis = new ArrayList<String>();
		final List<String> yaxis = new ArrayList<String>();
		final List<String> yaxis_salechange = new ArrayList<String>();
		final List<String> yaxis_unitchange = new ArrayList<String>();

		LOG.info("posDollars" + posDollars);
		LOG.info("posUnits" + posUnits);
		
		int temp = 0;
		int count = 0;
		final DateFormat dateFormat = new SimpleDateFormat("yyyyMM");
		final Date date = new Date();
		LOG.info("date converted " + dateFormat.format(date));

		//final String currentDate = dateFormat.format(date);
		final String currentDate = "201507";
		//final String convertedCurrentDate = getDate(currentDate.substring(0, 4) + " " + currentDate.substring(4));

		if ("1".equals(posDollars) && "1".equals(posUnits))
		{
			LOG.info("Inside 1st if");
			LOG.info("size of percents received" + saleChange_graphValues.size());
			final Iterator i = saleChange_graphValues.iterator();
			while (i.hasNext())
			{
				final CategorySalesBenchmarkPercentsModel csb = (CategorySalesBenchmarkPercentsModel) i.next();
				final String splitString = csb.getCsbCalenderYearMonth();
				LOG.info("split value" + splitString.substring(0, 4) + " " + splitString.substring(4));
				xaxis.add(getDate(splitString.substring(0, 4) + " " + splitString.substring(4)));
				yaxis_salechange.add(csb.getCsbSaleChangePercents());
				yaxis_unitchange.add(csb.getCsbUnitChangePercents());
				count++;
				if (currentDate.equals(splitString))
				{
					temp = count;
				}
			}
			LOG.info("zoomIndex2 :::::::::::" + temp);
			model.addAttribute("zoomIndex2", temp);
			model.addAttribute("xaxis", xaxis);
			model.addAttribute("yaxis1", yaxis_salechange);
			model.addAttribute("yaxis2", yaxis_unitchange);
			model.addAttribute("graphCount", new String("isTwo"));

		}


		if ("1".equals(posUnits) && "0".equals(posDollars))
		{
			LOG.info("Inside 2nd if");
			final Iterator i = saleChange_graphValues.iterator();
			while (i.hasNext())
			{
				final CategorySalesBenchmarkPercentsModel csb = (CategorySalesBenchmarkPercentsModel) i.next();
				final String splitString = csb.getCsbCalenderYearMonth();
				LOG.info("split value" + splitString.substring(0, 4) + " " + splitString.substring(4));
				xaxis.add(getDate(splitString.substring(0, 4) + " " + splitString.substring(4)));
				yaxis.add(csb.getCsbUnitChangePercents());
				count++;
				if (currentDate.equals(splitString))
				{
					temp = count;
				}

			}
			LOG.info("zoomIndex2 :::::::::::" + temp);
			model.addAttribute("zoomIndex2", temp);
			model.addAttribute("xaxis", xaxis);
			model.addAttribute("yaxis2", yaxis);
			model.addAttribute("flag", new String("units"));
			model.addAttribute("graphCount", new String("isOne"));
		}

		if ("1".equals(posDollars) && "0".equals(posUnits))
		{
			LOG.info("Inside 3rd if");
			final Iterator i = saleChange_graphValues.iterator();
			while (i.hasNext())
			{
				final CategorySalesBenchmarkPercentsModel csb = (CategorySalesBenchmarkPercentsModel) i.next();
				final String splitString = csb.getCsbCalenderYearMonth();
				LOG.info("split value" + splitString.substring(0, 4) + " " + splitString.substring(4));
				xaxis.add(getDate(splitString.substring(0, 4) + " " + splitString.substring(4)));
				yaxis.add(csb.getCsbSaleChangePercents());
				count++;
				if (currentDate.equals(splitString))
				{
					temp = count;
				}

			}
			LOG.info("zoomIndex2 :::::::::::" + temp);
			model.addAttribute("zoomIndex2", temp);
			model.addAttribute("xaxis", xaxis);
			model.addAttribute("yaxis1", yaxis);
			model.addAttribute("flag", new String("dollars"));
			model.addAttribute("graphCount", new String("isOne"));
		}
		final CSBPercents3612Model yoyValues = fmcustomerservice.getYearOverYearComparison(inputProductCode);
		model.addAttribute("yoyValues", yoyValues);

		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb("/online-tools/overview", "Online Tools", null));
		breadcrumbs.add(new Breadcrumb("#", "Motorparts Monitor", null));
		getOnlineToolsFolderLinks(model);

		model.addAttribute("breadcrumbs", breadcrumbs);
		model.addAttribute("metaRobots", "no-index,no-follow");
		model.addAttribute("nabsAccCode", userNABSCode());
		model.addAttribute("clickedLink", "MotorpartsMonitor");

		storeCmsPageInModel(model, getContentPageForLabelOrId(FM_ONLINE_TOOLS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FM_ONLINE_TOOLS_PAGE));
		return ControllerConstants.Views.Pages.Account.FMOnlineToolsPage;
	}

	/**
	 * @param xaxis
	 */
	private String getDate(final String xaxis)
	{
		final String[] xaxisSplit = xaxis.split(" "); 
		final String[] months =
		{ "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
		final String combineDatestr = months[Integer.parseInt(xaxisSplit[1]) - 1] + " " + xaxisSplit[0];
		LOG.info("final date" + combineDatestr);
		return combineDatestr;
	}

}