/**
 * 
 */
package com.federalmogul.storefront.controllers.pages;

import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;
import de.hybris.platform.commercefacades.i18n.I18NFacade;
import de.hybris.platform.core.model.security.PrincipalGroupModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.servicelayer.user.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import com.federalmogul.storefront.constants.WebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.federalmogul.facades.account.FMCustomerFacade;
import com.federalmogul.facades.account.FMUserFacade;
import com.federalmogul.facades.account.FMVinLookupFacade;
import com.federalmogul.facades.search.impl.DefaultFMCompetitorFacade;
import com.federalmogul.facades.user.data.FMCustomerData;
import com.federalmogul.falcon.core.model.FMPartModel;
import com.federalmogul.falcon.core.model.PartInterchangeModel;
import com.federalmogul.storefront.breadcrumb.Breadcrumb;
import com.federalmogul.storefront.controllers.ControllerConstants;
import com.federalmogul.storefront.forms.FMAddressForm;
import com.federalmogul.storefront.forms.LicensePlateRequestForm;
import com.federalmogul.storefront.forms.LoginForm;
import com.federalmogul.storefront.forms.OrderUploadForm;
import com.federalmogul.storefront.forms.PartInterchangeForm;
import com.federalmogul.storefront.forms.VinLookupRequestForm;
import com.federalmogul.storefront.util.XSSFilterUtil;
import com.fm.falcon.webservices.dto.LicensePlateResponseDTO;
import com.fm.falcon.webservices.dto.VinLookupResponseDTO;
import com.federalmogul.core.network.dao.FMUserSearchDAO;
import com.federalmogul.facades.uploadorder.UploadOrderFacade;
import de.hybris.platform.servicelayer.user.UserService;
import com.federalmogul.facades.account.FMCustomerFacade;
import org.springframework.beans.factory.annotation.Autowired;
import de.hybris.platform.b2bacceleratorservices.company.CompanyB2BCommerceService;
import com.federalmogul.core.model.FMCustomerAccountModel;


/**
 * @author SU276498
 * 
 */
@Controller
@Scope("tenant")
@RequestMapping("/**/catalog")
public class FMCatalogLookupController extends SearchPageController
{
	private static final Logger LOG = Logger.getLogger(FMCatalogLookupController.class);

	private static final String NO_RESULTS_CMS_PAGE_ID = "searchEmpty";
	private static final String REDIRECT_URL_SEARCH = REDIRECT_PREFIX + "/search";
	private static final String PART_INTERCHANGE_PAGE = "partInterchangePage";
	private static final String LICENSEPLATELOOKUP_PAGE = "LicensePlateLookupLandingPage";
	private static final String LICENSE_VIN_LOOKUP_PAGE = "LicenseVINLookupLandingPage";
	private static final String VEHICLE_LOOKUP_PAGE = "VehicleLookupLandingPage";



	/*
	 * @Resource(name = "accountBreadcrumbBuilder") private ResourceBreadcrumbBuilder accountBreadcrumbBuilder;
	 */

	@Resource
	protected FMUserFacade fmUserfacade;
	@Resource
	protected CompanyB2BCommerceService companyB2BCommerceService;
	


	@Resource(name = "vinLookupFacade")
	protected FMVinLookupFacade fmVinLookupFacade;

	@Autowired
	private UserService userService;

	
	private static final int checkResultSize = 0;
	
	@Autowired
	private UploadOrderFacade uploadorderFacade;
	
	@Autowired
	FMUserSearchDAO fmUserSearchDAO;
	@Resource
	protected FMCustomerFacade fmCustomerFacade;



	/*
	 * @Resource(name = "searchBreadcrumbBuilder") private SearchBreadcrumbBuilder searchBreadcrumbBuilder;
	 */

	@Autowired
	DefaultFMCompetitorFacade defaultFMCompetitorFacade;

	@Resource(name = "i18NFacade")
	private I18NFacade i18NFacade;



	/**
	 * controller mapping to get the YMM from VIN Service, if success returns to Search listing page
	 * 
	 * @param callBackRequestForm
	 * @param result
	 * @return String
	 * @throws CMSItemNotFoundException
	 */
	@SuppressWarnings("null")
	@RequestMapping(value = "/vin", method = RequestMethod.POST)
	public String vinLookup(@ModelAttribute("vinLookupFormData") final VinLookupRequestForm vinLookupRequestForm,
			final Model model, final HttpServletRequest request) throws CMSItemNotFoundException
	{
		LOG.info("vinSearch request ==>");
		VinLookupResponseDTO vinLookupResponseDTO = null;


		try
		{
			vinLookupResponseDTO = fmVinLookupFacade.vinLookup(XSSFilterUtil.filter(vinLookupRequestForm.getVin().toUpperCase()));
		}
		catch (final Exception e)
		{
			//GlobalMessages.addInfoMessage(model, "Vin Lookup Service not available.. Please try again");
			LOG.error("Exception ...." + e.getMessage());

		}
		if (vinLookupResponseDTO != null && "0".equals(vinLookupResponseDTO.getResponseCode()))
		{
			final String year = vinLookupResponseDTO.getYear();
			final String make = vinLookupResponseDTO.getMake();
			final String model1 = vinLookupResponseDTO.getModel();

			final String ymmString = year + make + model1 + "|";
			LOG.info("VIN ymmString:::" + ymmString);
			final StringBuffer q = new StringBuffer();
			q.append("?q=:name-asc:vehiclesegment:" + ymmString);
			q.append("Passenger+Car+Light+Truck:year:" + ymmString);
			q.append(year + ":make:" + ymmString + make);
			q.append(":model:" + ymmString + model1 + "&text=#");

			LOG.info("VIN queryParam:::" + q);

			return REDIRECT_URL_SEARCH + q;
		}
		final UserModel user = userService.getCurrentUser();
		final boolean isUserAnonymous = user == null || userService.isAnonymousUser(user);


		final Set<PrincipalGroupModel> groupss = user.getGroups();
		final List<String> groupUID = new ArrayList<String>();
		for (final PrincipalGroupModel groupModel : groupss)
		{
			final String groupId = groupModel.getUid();
			groupUID.add(groupId);
		}
		LOG.info("VIN Checking b2b:::");
		if (!isUserAnonymous)
		{
			LOG.info("VIN Checking b2b:::insideif");
			final FMCustomerData fmCustomerData = fmCustomerFacade.getCurrentFMCustomer();
			//			LOG.info("My parent unit is" + fmCustomerData.getFmunit().getUid());

			if (groupUID.contains("FMB2BB"))
			{
				LOG.info("VIN Checking b2b:::insideif b2b");
				setB2BAccountDetails(fmCustomerData.getFmunit(), model);
				model.addAttribute("customerType", "b2BCustomer");
				final String uploadOrderStatus = getSessionService().getAttribute("uploadStatus");
				if (uploadOrderStatus != null && !uploadOrderStatus.isEmpty())
				{
					model.addAttribute("uploadStatus", uploadOrderStatus);
					getSessionService().setAttribute("uploadStatus", "");
				}
				model.addAttribute("VPNError", "There is no data found for the search");

				model.addAttribute("orderUpload", new OrderUploadForm());
				model.addAttribute("addressForm", new FMAddressForm());
				model.addAttribute("notFoundVPN", vinLookupRequestForm.getVin());

				if (vinLookupRequestForm.getSourceRequestURL() != null
						&& vinLookupRequestForm.getSourceRequestURL().equalsIgnoreCase("fromHome"))
				{
					LOG.info("############# FROM HOME ##########");

					storeCmsPageInModel(model, getContentPageForLabelOrId("FMB2BHomePage"));
					setUpMetaDataForContentPage(model, getContentPageForLabelOrId("FMB2BHomePage"));
					updatePageTitleNew(model, getContentPageForLabelOrId("FMB2BHomePage"));
					return ControllerConstants.Views.Pages.Account.AccountBToBigB;
				}
				else
				{
					LOG.info("############# FROM FOOTER ##########");

					storeCmsPageInModel(model, getContentPageForLabelOrId(LICENSE_VIN_LOOKUP_PAGE));
					setUpMetaDataForContentPage(model, getContentPageForLabelOrId(LICENSE_VIN_LOOKUP_PAGE));
					updatePageTitleNew(model, getContentPageForLabelOrId(LICENSE_VIN_LOOKUP_PAGE));
					return ControllerConstants.Views.Pages.Account.PartsFinderLicenseVINLookup;
				}

			}
		}
		model.addAttribute("regionData", i18NFacade.getRegionsForCountryIso("US"));
		model.addAttribute("notFoundVPN", vinLookupRequestForm.getVin());
		model.addAttribute("isUserAnonymous", isUserAnonymous);
		LOG.info("VIN not b2b:::");
		model.addAttribute("VPNError", "There is no data found for the search");

		if (vinLookupRequestForm.getSourceRequestURL() != null
				&& vinLookupRequestForm.getSourceRequestURL().equalsIgnoreCase("fromHome"))
		{
			LOG.info("############# FROM HOME ##########");
			final LoginForm loginForm = new LoginForm();
			model.addAttribute("loginForm", loginForm);
			updatePageTitleNew(model, getContentPageForLabelOrId("fmAnonymousHomePage"));
			storeCmsPageInModel(model, getContentPageForLabelOrId("fmAnonymousHomePage"));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId("fmAnonymousHomePage"));
			return getViewForPage(model);
		}
		else
		{
			final LoginForm loginForm = new LoginForm();
			model.addAttribute("loginForm", loginForm);
			LOG.info("############# FROM FOOTER ##########");

			updatePageTitleNew(model, getContentPageForLabelOrId(LICENSE_VIN_LOOKUP_PAGE));
			storeCmsPageInModel(model, getContentPageForLabelOrId(LICENSE_VIN_LOOKUP_PAGE));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId(LICENSE_VIN_LOOKUP_PAGE));
			return ControllerConstants.Views.Pages.Account.PartsFinderLicenseVINLookup;
		}

	}

	/**
	 * controller mapping to get the FM Parts for the requested External part, if success returns to Search listing page
	 * 
	 * @return String
	 * @throws CMSItemNotFoundException
	 */
	@RequestMapping(value = "/competitor-interchange", method =
	{ RequestMethod.POST, RequestMethod.GET })
	public String getFMParts(@ModelAttribute("partInterchangeForm") final PartInterchangeForm partInterchangeForm,
			final Model model) throws CMSItemNotFoundException
	{
		LOG.info("Competitor request ==>");
		List<PartInterchangeModel> partInterchangeFromModel = null;
		final List<PartInterchangeModel> partInterchangeFromModelNew = new ArrayList<PartInterchangeModel>();
		String externalPart = XSSFilterUtil.filter(partInterchangeForm.getExternalPart());
		model.addAttribute("externalPart", externalPart);
		List<FMPartModel> fmPartModel = null;

		if (externalPart == null)
		{

			externalPart = "000";

		}
		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb("#", "Competitor Interchange", null));
		model.addAttribute("breadcrumbs", breadcrumbs);
		partInterchangeFromModel = defaultFMCompetitorFacade.getExternalPartInfo(externalPart);
		model.addAttribute("partsFinderLink", "PartInterchange");
		if (partInterchangeFromModel != null)
		{
			for (int i = 0; i < partInterchangeFromModel.size(); i++)
			{
				fmPartModel = defaultFMCompetitorFacade.getPartNumberInfo(partInterchangeFromModel.get(i).getFmRawPartNumber());
				for (int j = 0; j < fmPartModel.size(); j++)
				{
					if (fmPartModel.get(j).getRawPartNumber().equalsIgnoreCase(partInterchangeFromModel.get(i).getFmRawPartNumber()))
					{
						partInterchangeFromModelNew.add(partInterchangeFromModel.get(i));
						break;
					}
				}
				final UserModel user = userService.getCurrentUser();
				final boolean isUserAnonymous = userService.isAnonymousUser(user);
				if (!isUserAnonymous){
					final String soldToAcc = (String) getSessionService().getAttribute(WebConstants.SOLDTO_ACCOUNT_ID);
					final FMCustomerAccountModel fmCustomerAccModel = (FMCustomerAccountModel) companyB2BCommerceService
							.getUnitForUid(soldToAcc);
				uploadorderFacade.createReportLog(fmCustomerAccModel,(fmUserSearchDAO.getUserForUID(fmCustomerFacade.getCurrentFMCustomer().getUid())), "PartInterChange");
				}

			}
			model.addAttribute("partInterchangeFromModel", partInterchangeFromModelNew);
			model.addAttribute("metaRobots", "no-index,no-follow");
			storeCmsPageInModel(model, getContentPageForLabelOrId(PART_INTERCHANGE_PAGE));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId(PART_INTERCHANGE_PAGE));

			return ControllerConstants.Views.Pages.Account.PartInterchange;

		}

		storeCmsPageInModel(model, getContentPageForLabelOrId(NO_RESULTS_CMS_PAGE_ID));
		return getViewForPage(model);
	}

	@RequestMapping(value = "/part-Number-search", method =
	{ RequestMethod.POST, RequestMethod.GET })
	public String getFMPartSearch(@ModelAttribute("partInterchangeForm") final PartInterchangeForm partInterchangeForm,
			final Model model) throws CMSItemNotFoundException
	{
		LOG.info("part-Number-search ==>");
		List<FMPartModel> fmPartModel = null;
		String partNumber = XSSFilterUtil.filter(partInterchangeForm.getPartNumber());
		LOG.info("partNumber  ==>" + partNumber);
		model.addAttribute("partNumber", partNumber);
		if (partNumber == null)
		{
			partNumber = "000";



		}
		fmPartModel = defaultFMCompetitorFacade.getPartNumberInfo(partNumber);
		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb("#", "Part Search", null));
		model.addAttribute("breadcrumbs", breadcrumbs);

		if (fmPartModel != null)
		{

			model.addAttribute("fmPartModel", fmPartModel);
			model.addAttribute("metaRobots", "no-index,no-follow");
			model.addAttribute("partsFinderLink", "PartNumberSearch");
			LOG.info("model attribute set with parts finder link after part model check");
			storeCmsPageInModel(model, getContentPageForLabelOrId(PART_INTERCHANGE_PAGE));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId(PART_INTERCHANGE_PAGE));

			return ControllerConstants.Views.Pages.Account.PartNumber;

		}
		LOG.info("model attribute set with parts finder link");
		model.addAttribute("partsFinderLink", "PartNumberSearch");
		storeCmsPageInModel(model, getContentPageForLabelOrId(NO_RESULTS_CMS_PAGE_ID));
		return getViewForPage(model);
	}

	/**
	 * controller mapping to get the YMM from License Plate Service, if success returns to Search listing page
	 * 
	 * @param callBackRequestForm
	 * @param result
	 * @return String
	 * @throws CMSItemNotFoundException
	 */
	@SuppressWarnings("null")
	@RequestMapping(value = "/license-plate", method = RequestMethod.POST)
	public String licensePlateSearch(
			@ModelAttribute("LicensePlateRequestForm") final LicensePlateRequestForm licensePlateRequestForm, final Model model,
			final HttpServletRequest request) throws CMSItemNotFoundException
	{
		LOG.info("licensePlateSearch request ==>");
		LicensePlateResponseDTO licensePlateResponseDTO = null;
		final String[] state = licensePlateRequestForm.getState().split(",");
		final String plate = XSSFilterUtil.filter(licensePlateRequestForm.getLicensePlate());

		model.addAttribute("selectedStateIsoCode", state[1]);

		try
		{
			licensePlateResponseDTO = fmVinLookupFacade.licensePlate(plate, state[1]);
			LOG.info("licensePlateResponseDTO::::::::::::::::::: ==>" + licensePlateResponseDTO);
			LOG.info("licensePlateResponseDTO::::::::::::::::::: ==>" + licensePlateResponseDTO.getResponseCode());
		}
		catch (final Exception e)
		{
			//GlobalMessages.addInfoMessage(model, "License Plate Service not available.. Please try again");
			LOG.error("Exception ...." + e.getMessage());


		}
		if (licensePlateResponseDTO != null && licensePlateResponseDTO.getResponseCode().isEmpty()
				&& licensePlateResponseDTO.getYear() != null && licensePlateResponseDTO.getMake() != null
				&& licensePlateResponseDTO.getModel() != null)
		{
			final String year = licensePlateResponseDTO.getYear();
			final String make = licensePlateResponseDTO.getMake();
			final String model1 = licensePlateResponseDTO.getModel();

			final String ymmString = year + make + model1 + "|";
			LOG.info("License Plate ymmString:::" + ymmString);
			final StringBuffer q = new StringBuffer();
			q.append("?q=:name-asc:vehiclesegment:" + ymmString);
			q.append("Passenger+Car+Light+Truck:year:" + ymmString);
			q.append(year + ":make:" + ymmString + make);
			q.append(":model:" + ymmString + model1 + "&text=#");

			LOG.info("License Plate queryParam:::" + q);

			return REDIRECT_URL_SEARCH + q;
		}
		final UserModel user = userService.getCurrentUser();
		final boolean isUserAnonymous = user == null || userService.isAnonymousUser(user);


		final Set<PrincipalGroupModel> groupss = user.getGroups();
		final List<String> groupUID = new ArrayList<String>();
		for (final PrincipalGroupModel groupModel : groupss)
		{
			final String groupId = groupModel.getUid();
			groupUID.add(groupId);
		}
		if (!isUserAnonymous)
		{
			final FMCustomerData fmCustomerData = fmCustomerFacade.getCurrentFMCustomer();
			//			LOG.info("My parent unit is" + fmCustomerData.getFmunit().getUid());

			if (groupUID.contains("FMB2BB"))
			{
				setB2BAccountDetails(fmCustomerData.getFmunit(), model);
				model.addAttribute("customerType", "b2BCustomer");
				final String uploadOrderStatus = getSessionService().getAttribute("uploadStatus");
				if (uploadOrderStatus != null && !uploadOrderStatus.isEmpty())
				{
					model.addAttribute("uploadStatus", uploadOrderStatus);
					getSessionService().setAttribute("uploadStatus", "");
				}
				model.addAttribute("LicenseError", "There is no data found for the search");

				model.addAttribute("orderUpload", new OrderUploadForm());
				model.addAttribute("addressForm", new FMAddressForm());
				model.addAttribute("noLicensePlate", plate);

				if (licensePlateRequestForm.getSourceRequestURL() != null
						&& licensePlateRequestForm.getSourceRequestURL().equalsIgnoreCase("fromHome"))
				{
					LOG.info("##### FROM HOME #######");
					storeCmsPageInModel(model, getContentPageForLabelOrId("FMB2BHomePage"));
					setUpMetaDataForContentPage(model, getContentPageForLabelOrId("FMB2BHomePage"));
					updatePageTitleNew(model, getContentPageForLabelOrId("FMB2BHomePage"));
					return ControllerConstants.Views.Pages.Account.AccountBToBigB;
				}
				else
				{
					LOG.info("##### FROM FOOTER #######");
					storeCmsPageInModel(model, getContentPageForLabelOrId(LICENSEPLATELOOKUP_PAGE));
					setUpMetaDataForContentPage(model, getContentPageForLabelOrId(LICENSEPLATELOOKUP_PAGE));
					return ControllerConstants.Views.Pages.Account.PartsFinderLicensePlateLookup;
				}

			}
		}
		model.addAttribute("regionData", i18NFacade.getRegionsForCountryIso("US"));
		model.addAttribute("noLicensePlate", plate);
		model.addAttribute("isUserAnonymous", isUserAnonymous);
		LOG.info("LicenseError not b2b:::");
		model.addAttribute("LicenseError", "There is no data found for the search");


		if (licensePlateRequestForm.getSourceRequestURL() != null
				&& licensePlateRequestForm.getSourceRequestURL().equalsIgnoreCase("fromHome"))
		{
			LOG.info("##### FROM HOME #######");
			final LoginForm loginForm = new LoginForm();
			model.addAttribute("loginForm", loginForm);

			updatePageTitleNew(model, getContentPageForLabelOrId("fmAnonymousHomePage"));
			storeCmsPageInModel(model, getContentPageForLabelOrId("fmAnonymousHomePage"));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId("fmAnonymousHomePage"));
			return getViewForPage(model);
		}
		else
		{
			LOG.info("##### FROM FOOTER #######");
			final LoginForm loginForm = new LoginForm();
			model.addAttribute("loginForm", loginForm);
			storeCmsPageInModel(model, getContentPageForLabelOrId(LICENSEPLATELOOKUP_PAGE));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId(LICENSEPLATELOOKUP_PAGE));
			return ControllerConstants.Views.Pages.Account.PartsFinderLicensePlateLookup;
		}

	}

	@RequestMapping(value = "/partsFinderLicensePlateLookup", method =
	{ RequestMethod.POST, RequestMethod.GET })
	public String getpartsFinderLicensePlateLookup(final Model model) throws CMSItemNotFoundException
	{
		final LicensePlateRequestForm licenseForm = new LicensePlateRequestForm();
		model.addAttribute("LicensePlateRequestForm", licenseForm);
		model.addAttribute("partsFinderLink", "LicensePlateLookup");
		model.addAttribute("regionData", i18NFacade.getRegionsForCountryIso("US"));
		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb("#", "License Plate Lookup", null));
		model.addAttribute("breadcrumbs", breadcrumbs);
		storeCmsPageInModel(model, getContentPageForLabelOrId(LICENSEPLATELOOKUP_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(LICENSEPLATELOOKUP_PAGE));
		return ControllerConstants.Views.Pages.Account.PartsFinderLicensePlateLookup;
	}

	@RequestMapping(value = "/partsFinderLicenseVINLookup", method =
	{ RequestMethod.POST, RequestMethod.GET })
	public String getpartsFinderLicenseVINLookup(final Model model) throws CMSItemNotFoundException
	{
		final VinLookupRequestForm vinForm = new VinLookupRequestForm();
		model.addAttribute("vinLookupFormData", vinForm);
		model.addAttribute("partsFinderLink", "LicenseVINLookup");
		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb("#", "VIN Lookup", null));
		model.addAttribute("breadcrumbs", breadcrumbs);
		storeCmsPageInModel(model, getContentPageForLabelOrId(LICENSE_VIN_LOOKUP_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(LICENSE_VIN_LOOKUP_PAGE));
		return ControllerConstants.Views.Pages.Account.PartsFinderLicenseVINLookup;
	}

	@RequestMapping(value = "/partsFinderVehicleLookup", method =
	{ RequestMethod.POST, RequestMethod.GET })
	public String getpartsFinderVehicleLookup(final Model model) throws CMSItemNotFoundException
	{
		final List<Breadcrumb> breadcrumbs = new ArrayList<Breadcrumb>();
		breadcrumbs.add(new Breadcrumb("#", "Vehicle Lookup", null));
		model.addAttribute("breadcrumbs", breadcrumbs);
		model.addAttribute("partsFinderLink", "VehicleLookUp");
		storeCmsPageInModel(model, getContentPageForLabelOrId(VEHICLE_LOOKUP_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(VEHICLE_LOOKUP_PAGE));
		return ControllerConstants.Views.Pages.Account.PartsFinderVehicleLookup;
	}

	protected void updatePageTitleNew(final Model model, final AbstractPageModel cmsPage)
	{
		storeContentPageTitleInModel(model, getPageTitleResolver().resolveHomePageTitle(cmsPage.getTitle()));
	}
}
