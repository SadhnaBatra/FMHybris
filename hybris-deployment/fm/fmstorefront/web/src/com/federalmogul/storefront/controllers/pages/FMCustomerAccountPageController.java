/**
 * 
 */
package com.federalmogul.storefront.controllers.pages;

import com.federalmogul.core.enums.FmTaxValidationType;
import com.federalmogul.core.enums.Fmusertype;
import com.federalmogul.core.model.FMCustomerAccountModel;
import com.federalmogul.core.model.FMCustomerModel;
import com.federalmogul.core.model.FMTaxDocumentModel;
import com.federalmogul.core.network.FMNetworkService;
import com.federalmogul.core.network.dao.FMUserSearchDAO;
import com.federalmogul.core.order.dao.FMCheckoutDAO;
import com.federalmogul.facades.account.FMCustomerAccountReturnOrderFacade;
import com.federalmogul.facades.account.FMCustomerFacade;
import com.federalmogul.facades.account.FMUserFacade;
import com.federalmogul.facades.account.impl.FMCustomerFacadeImpl;
import com.federalmogul.facades.address.data.FMB2bAddressData;
import com.federalmogul.facades.network.FMNetworkFacade;
import com.federalmogul.facades.order.data.FMReturnItemsData;
import com.federalmogul.facades.order.data.FMReturnOrderData;
import com.federalmogul.facades.uploadorder.UploadOrderFacade;
import com.federalmogul.facades.user.data.FMCustomerAccountData;
import com.federalmogul.facades.user.data.FMCustomerData;
import com.federalmogul.facades.user.data.FMTaxDocumentData;
import com.federalmogul.falcon.core.model.FMCsrAccountListModel;
import com.federalmogul.falcon.core.model.FMShipperOrderTrackingModel;
import com.federalmogul.storefront.annotations.RequireHardLogIn;
import com.federalmogul.storefront.breadcrumb.Breadcrumb;
import com.federalmogul.storefront.breadcrumb.ResourceBreadcrumbBuilder;
import com.federalmogul.storefront.constants.WebConstants;
import com.federalmogul.storefront.controllers.ControllerConstants;
import com.federalmogul.storefront.controllers.helper.OrderStatusResultHelper;
import com.federalmogul.storefront.controllers.util.GlobalMessages;
import com.federalmogul.storefront.controllers.util.MailUtil;
import com.federalmogul.storefront.forms.FMAccountSearchForm;
import com.federalmogul.storefront.forms.FMAddressForm;
import com.federalmogul.storefront.forms.FMUpdateProfileForm;
import com.federalmogul.storefront.forms.FmB2bAddressSort;
import com.federalmogul.storefront.forms.OrderSearchForm;
import com.federalmogul.storefront.forms.OrderUploadForm;
import com.federalmogul.storefront.forms.QuickOrderUploadForm;
import com.federalmogul.storefront.forms.ReturnRequestForm;
import com.federalmogul.storefront.forms.TaxExemptionForm;
import com.federalmogul.storefront.forms.UpdatePasswordForm;
import com.federalmogul.storefront.util.CustomComparator;
import com.federalmogul.storefront.util.UtilDate;
import com.fmo.wom.business.as.OrderStatusASUSCanImpl;
import com.fmo.wom.business.orderstatus.OrderStatusHelper;
import com.fmo.wom.business.orderstatus.OrderStatusResult;
import com.fmo.wom.domain.AccountBO;
import com.fmo.wom.domain.AddressBO;
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
import com.fmo.wom.integration.SAPService;
import de.hybris.platform.acceleratorservices.email.EmailService;
import de.hybris.platform.acceleratorservices.model.email.EmailAddressModel;
import de.hybris.platform.acceleratorservices.model.email.EmailAttachmentModel;
import de.hybris.platform.acceleratorservices.model.email.EmailMessageModel;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2bacceleratorfacades.company.B2BCommerceUserFacade;
import de.hybris.platform.b2bacceleratorfacades.company.CompanyB2BCommerceFacade;
import de.hybris.platform.b2bacceleratorfacades.order.data.B2BUnitData;
import de.hybris.platform.b2bacceleratorservices.company.CompanyB2BCommerceService;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.AbstractPageModel;
import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.commercefacades.i18n.I18NFacade;
import de.hybris.platform.commercefacades.order.CheckoutFacade;
import de.hybris.platform.commercefacades.product.ProductFacade;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.user.UserFacade;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.data.CountryData;
import de.hybris.platform.commercefacades.user.data.RegionData;
import de.hybris.platform.commercefacades.user.data.TitleData;
import de.hybris.platform.commercefacades.user.exceptions.PasswordMismatchException;
import de.hybris.platform.commerceservices.customer.DuplicateUidException;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.core.model.c2l.CountryModel;
import de.hybris.platform.core.model.c2l.RegionModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.security.PrincipalGroupModel;
import de.hybris.platform.core.model.security.PrincipalModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.order.CartService;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.util.Config;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.xml.soap.SOAPException;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;



/**
 * @author SR279690 Controller for home page.
 * 
 * 
 */

@Controller
@Scope("tenant")
@RequestMapping("/my-fmaccount")
@SessionAttributes(
{ WebConstants.USER_SHIPTO_FULL_ADDRESS, WebConstants.USER_SOLDTO_FULL_ADDRESS })
public class FMCustomerAccountPageController extends SearchPageController
{
	protected SessionService sessionService;
	private static final String ADDRESS_CODE_PATH_VARIABLE_PATTERN = "{addressCode:.*}";
	private static final String DOWNLOAD_TAX_DOC_NAME = "{taxdocname:.*}";
	private static final String DOWNLOAD_TAX_DOC_PK = "{pk:.*}";
	private static final String CSR_ACCOUNT_UID = "{unitUid:.*}";
	// CMS Pages
	private static final String FMADMIN_ADDRESS_BOOK_CMS_PAGE = "AdminAddressBookPage";

	private static final String FMPROFILE_CMS_PAGE = "UpdateProfilePage";
	private static final String FMADDRESS_BOOK_CMS_PAGE = "AddressBookPage";
	private static final String REDIRECT_TO_PROFILE_PAGE = REDIRECT_PREFIX + "/my-fmaccount/profile";
	private static final String REDIRECT_TO_ADDRESS_BOOK_PAGE = REDIRECT_PREFIX + "/my-fmaccount/address-book";
	private static final String REDIRECT_TO_ADMIN_ADDRESS_BOOK_PAGE = REDIRECT_PREFIX + "/my-fmaccount/adminAddress-book";
	protected static final String REDIRECT_TO_ORDER_HISTORY = REDIRECT_PREFIX + "/my-fmaccount/order-history";
	private static final String FMADD_EDIT_ADDRESS_CMS_PAGE = "AddEditAddressPage";
	private static final String FM_TAXEXEMPTION_CMS_PAGE = "TaxExemptionPage";
	private static final String FM_TAXEXEMPTION_APPROVAL_CMS_PAGE = "TaxExemptionApprovalpage";

	private static final String FMORDER_HISTORY_CMS_PAGE = "OrderHistoryPage";
	private static final String FMORDER_DETAILS_CMS_PAGE = "OrderDetailsPage";

	//<!-- Balaji---Start Order Return --> 
	private static final String FMRETURN_HISTORY_CMS_PAGE = "ReturnHistoryPage";
	private static final String FMRETURN_DETAILS_CMS_PAGE = "ReturnDetailsPage";
	private static final String FMRETURN_ORDER_REQUEST_CMS_PAGE = "ReturnRequestPage";
	private static final String FMRETURN_ORDER_REQUEST_CONFIRMATION_CMS_PAGE = "ReturnRequestConfirmationPage";
	//<!-- Balaji---End Order Return -->

	private static final Logger LOG = Logger.getLogger(FMCustomerAccountPageController.class);


	final OrderStatusASUSCanImpl orderHeardeService = new OrderStatusASUSCanImpl();

	private static final int CHECKRESULTSIZE = 0;

	private static final Pattern CA_POSTAL_CODE_PATTERN = Pattern.compile("(^[ABCEGHJKLMNPRSTVXYabceghjklmnprstvxy]{1}\\d{1}[A-Za-z]{1}\\s?\\-?\\d{1}[A-Za-z]{1}\\d{1}$)");
	private static final Pattern US_POSTAL_CODE_PATTERN = Pattern.compile("^\\d{5}(-?\\d{4})?$");
	
	private static final String FM_CSR_ROLE = "FMCSR";

	@Resource(name = "accountBreadcrumbBuilder")
	private ResourceBreadcrumbBuilder accountBreadcrumbBuilder;

	@Resource
	private UserService userService;

	@Resource
	private CartService cartService;

	@Resource(name = "userFacade")
	protected UserFacade userFacade;
	
	@Autowired
	private UploadOrderFacade uploadorderFacade;

	@Resource(name = "checkoutFacade")
	private CheckoutFacade checkoutFacade;

	@Resource(name = "b2bCustomerFacade")
	protected CustomerFacade customerFacade;

	@Autowired
	private EmailService emailService;

	@Resource
	protected FMCustomerFacade fmCustomerFacade;

	@Resource(name = "productService")
	private ProductService productService;

	@Resource(name = "b2bProductFacade")
	private ProductFacade productFacade;

	@Resource
	protected FMUserFacade fmUserfacade;
	@Resource
	protected B2BCommerceUserFacade b2bCommerceUserFacade;

	@Resource(name = "i18NFacade")
	private I18NFacade i18NFacade;

	@Autowired
	private FMNetworkFacade fmNetworkFacade;

	@Autowired
	private FMCheckoutDAO fmCheckoutDAO;

	@Autowired
	private FMCustomerFacadeImpl fmCustomerFacadeimpl;

	@Resource(name = "fmNetworkService")
	private FMNetworkService fmNetworkService;

	@Resource
	private ModelService modelService;

	@Resource(name = "customerAccountReturnOrderFacade")
	protected FMCustomerAccountReturnOrderFacade fmCustomerAccountReturnOrderFacade;

	@Autowired
	protected FMUserSearchDAO fmUserSearchDAO;

	@Resource
	protected CompanyB2BCommerceService companyB2BCommerceService;

	@Resource(name = "b2bCommerceFacade")
	protected CompanyB2BCommerceFacade companyB2BCommerceFacade;

	@Autowired
	private CommonI18NService commonI18NService;


	/**
	 * Ajax method for getting the unit details
	 * 
	 * @param accnumber
	 * @return String
	 * @throws CMSItemNotFoundException
	 */
	@RequestMapping(value = "/getFMUnitDetailsForUpdateProfile", method = RequestMethod.POST)
	public String getUnitDetailsForUpdateProfile(@RequestParam(value = "acctNbr", required = false) String accnumber, final Model model)
			throws CMSItemNotFoundException
	{
		
		LOG.info("getUnitDetailsForUpdateProfile(...): From Ajax: Account Code: " + accnumber);
		if (accnumber == null) {
			FMCustomerModel customerModel = (FMCustomerModel) userService.getCurrentUser();
			accnumber = customerModel.getDefaultB2BUnit().getUid();
			LOG.info("getUnitDetailsForUpdateProfile(...): From CustomerModel: Account Code: " + accnumber);
		}
		if (accnumber.length() != 10) {

			int acccount = 0;
			final String codes = Config.getParameter(WebConstants.B2T_REGISTRATION_ACCOUNTS);
			final String[] acccodes = codes.split(",");
			for (int i = 0; i < acccodes.length; i++) {

				if (accnumber.contains(acccodes[i])) {
					acccount++;
				}
			}
			
			if (acccount == 0) {
				final int accnumberSize = 10 - accnumber.length();
				for (int i = 0; i < accnumberSize; i++) {
					accnumber = "0" + accnumber;
					LOG.info("getunitdetails(...): else part: " + accnumber);
				}
			}
		}

		final FMCustomerAccountModel unitB2B = (FMCustomerAccountModel) companyB2BCommerceService.getUnitForUid(accnumber);
		model.addAttribute("unitdetails", unitB2B);
		LOG.info("getUnitDetailsForUpdateProfile(...): unit id from model: " + unitB2B.getUid());
		LOG.info("getUnitDetailsForUpdateProfile(...): name from model: " + unitB2B.getLocName() + " " + unitB2B.getName());
		model.addAttribute("accountCode", unitB2B.getUid());
		model.addAttribute("companyName", unitB2B.getLocName());
		List<AddressModel> addressModels = (List<AddressModel>) unitB2B.getAddresses();
		if (addressModels != null && addressModels.size() > 0) {
			AddressModel addressModel = addressModels.get(0);
			if (addressModel != null) {
				model.addAttribute("companyAddressLine1", addressModel.getLine1());
				model.addAttribute("companyAddressLine2", addressModel.getLine2());
				model.addAttribute("companyCity", addressModel.getTown());
				model.addAttribute("companyStateOrProvIsoCode", addressModel.getRegion().getIsocode());
				model.addAttribute("companyCountryIsoCode", addressModel.getCountry().getIsocode());
				model.addAttribute("companyZipOrPostalCode", addressModel.getPostalcode());
			}
		}

		return "pages/fm/ajax/unitDetails";
	}

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	@RequireHardLogIn
	public String profile(final Model model) throws CMSItemNotFoundException
	{
		final List<TitleData> titles = userFacade.getTitles();

		final FMCustomerData fmCustomerData = fmCustomerFacade.getCurrentFMCustomer();

		if (fmCustomerData.getTitleCode() != null) {
			model.addAttribute("title", CollectionUtils.find(titles, new Predicate() {
				@Override
				public boolean evaluate(final Object object) {
					if (object instanceof TitleData) {
						return fmCustomerData.getTitleCode().equals(((TitleData) object).getCode());
					}
					return false;
				}
			}));
		}

		model.addAttribute("orderSearchData", new OrderSearchForm());
		model.addAttribute("fmCustomerData", fmCustomerData);

		LOG.info("profile(...): sold to: " + getSessionService().getAttribute(WebConstants.SOLDTO_ACCOUNT_ID));
		LOG.info("profile(...): ship to: " + getSessionService().getAttribute(WebConstants.SHIPTO_ACCOUNT_ID));

		storeCmsPageInModel(model, getContentPageForLabelOrId(FMPROFILE_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FMPROFILE_CMS_PAGE));
		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getFMBreadcrumbs("text.account.profile"));
		model.addAttribute("metaRobots", "no-index,no-follow");
		model.addAttribute("clickedlink", "clickedProfile");

		return ControllerConstants.Views.Pages.Account.FMAccountHomePage;
	}

	@RequestMapping(value = "/update-profile", method = RequestMethod.GET)
	@RequireHardLogIn
	@SuppressWarnings("deprecation")
	public String editProfile(final Model model) throws CMSItemNotFoundException
	{
		model.addAttribute("titleData", userFacade.getTitles());
		final FMCustomerData fmCustomerData = fmCustomerFacade.getCurrentFMCustomer();
		FMCustomerModel customerModel = (FMCustomerModel) userService.getCurrentUser();
		
		final FMUpdateProfileForm fmUpdateProfileForm = new FMUpdateProfileForm();

		final List<CountryData> countries = new ArrayList<CountryData>();
		countries.add(checkoutFacade.getCountryForIsocode("US"));
		countries.add(checkoutFacade.getCountryForIsocode("CA"));
		model.addAttribute("countryData", countries);

		model.addAttribute("regionsdatas", getI18NFacade().getRegionsForAllCountries().values());

		final Map<String, String> aboutShopMap = prepareaboutShopMap();
		final Map<String, String> shopTypeMap = prepareshopTypeMap();
		final Map<String, String> shopBayMap = prepareshopbayMap();
		final Map<String, String> mostInterstedMap = prepareMostInterstedMap();
		final Map<String, String> brandsMap = preparebrandMap();

		model.addAttribute("aboutShop", aboutShopMap);

		model.addAttribute("shopType", shopTypeMap);
		model.addAttribute("shopBays", shopBayMap);
		model.addAttribute("mostIntersted", mostInterstedMap);
		model.addAttribute("brands", brandsMap);

		LOG.info("First name retrieved is fmCustomerData.getName():: " + fmCustomerData.getName());
		LOG.info("First name retrieved is fmCustomerData.getFirstName():: " + fmCustomerData.getFirstName());

		if (fmCustomerData.getTitleCode() != null) {
			fmUpdateProfileForm.setTitleCode(fmCustomerData.getTitleCode());
		}
		if (fmCustomerData.getFirstName() != null) {
			fmUpdateProfileForm.setFirstName(fmCustomerData.getFirstName());
		}
		if (fmCustomerData.getLastName() != null) {
			fmUpdateProfileForm.setLastName(fmCustomerData.getLastName());
		}
		if (fmCustomerData.getNewsLetterSubscription() != null) {
			fmUpdateProfileForm.setNewsLetterSubscription(fmCustomerData.getNewsLetterSubscription());
		}

		AddressModel defaultShipmentAddressModel = customerModel.getDefaultShipmentAddress();
		if (defaultShipmentAddressModel != null) {
			LOG.info("editProfile(...): defaultShipmentAddressModel: Address Line 1: " + defaultShipmentAddressModel.getLine1() + 
						", Address Line2: " + defaultShipmentAddressModel.getLine2() +
						", City: " + defaultShipmentAddressModel.getTown());
		} else {
			LOG.info("editProfile(...): defaultShipmentAddressModel is null");
		}

		List<AddressModel> addressModels = (List<AddressModel>) customerModel.getAddresses();
		if (addressModels != null && addressModels.size() > 0) {
			AddressModel addressModel = addressModels.get(0);
			LOG.info("editProfile(...): addressModel: Address Line 1: " + addressModel.getLine1() + 
						", Address Line2: " + addressModel.getLine2() +
						", City: " + addressModel.getTown());
			fmUpdateProfileForm.setAddressline1(addressModel.getLine1());
			fmUpdateProfileForm.setAddressline2(addressModel.getLine2());
			fmUpdateProfileForm.setCity(addressModel.getTown());
			if (addressModel.getCountry() != null) {
				LOG.info("editProfile(...): addressModel: Country.isocode: " + addressModel.getCountry().getIsocode());
				fmUpdateProfileForm.setCountry(addressModel.getCountry().getIsocode());
			}
			
			if (addressModel.getRegion() != null) {
				LOG.info("editProfile(...): addressModel: Region.isocodeShort: " + addressModel.getRegion().getIsocodeShort());
				fmUpdateProfileForm.setState(addressModel.getRegion().getIsocodeShort());
			}
			fmUpdateProfileForm.setZipCode(addressModel.getPostalcode());
			fmUpdateProfileForm.setPhoneno(addressModel.getPhone1());
		}
		
		fmUpdateProfileForm.setLoyaltySignup(fmCustomerData.getLoyaltySignup());
		fmUpdateProfileForm.setLmsSigniId(fmCustomerData.getLmsSigninId());

		fmUpdateProfileForm.setIsGarageRewardMember(fmCustomerData.getIsGarageRewardMember());
		fmUpdateProfileForm.setPromoCode(fmCustomerData.getPromoCode());
		fmUpdateProfileForm.setAboutShop(fmCustomerData.getAboutShop());
		fmUpdateProfileForm.setShopType(fmCustomerData.getShopType());
		fmUpdateProfileForm.setShopBanner(fmCustomerData.getShopBanner());
		fmUpdateProfileForm.setShopBays(fmCustomerData.getShopBays());
		
		LOG.info("editProfile(...): fmCustomerData.getMostIntersted(): " + fmCustomerData.getMostIntersted());
		if (fmCustomerData.getMostIntersted() != null) {
			List<String> mostInterested = new ArrayList<String>();
			// Check if the string contains semi-colon character.
			if (fmCustomerData.getMostIntersted().indexOf(";") >= 0) { // Contains multiple items delimited by semi-colon
				mostInterested = Arrays.asList(fmCustomerData.getMostIntersted().split("\\s*;\\s*"));
			} else { // Contains just one item
				mostInterested.add(fmCustomerData.getMostIntersted());
			}
			fmUpdateProfileForm.setMostIntersted(mostInterested);
		}

		LOG.info("editProfile(...): fmCustomerData.getBrands(): " + fmCustomerData.getBrands());
		if (fmCustomerData.getBrands() != null) {
			List<String> brands = new ArrayList<String>();
			// Check if the string contains semi-colon character.
			if (fmCustomerData.getBrands().indexOf(";") >= 0) { // Contains multiple items delimited by semi-colon
				brands = Arrays.asList(fmCustomerData.getBrands().split("\\s*;\\s*"));
			} else { // Contains just one item
				brands.add(fmCustomerData.getBrands());
			}
			fmUpdateProfileForm.setBrands(brands);
		}
		
		fmUpdateProfileForm.setIsLoyaltyRequestedMember(fmCustomerData.getIsLoyaltyRequestedMember());

		model.addAttribute("customerdata", fmCustomerData);
		model.addAttribute("email", fmCustomerData.getEmail());
		model.addAttribute("updateProfileForm", fmUpdateProfileForm);

		storeCmsPageInModel(model, getContentPageForLabelOrId(FMPROFILE_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FMPROFILE_CMS_PAGE));

		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getFMBreadcrumbs("text.account.profile.updateProfile"));
		model.addAttribute("metaRobots", "no-index,no-follow");
		return ControllerConstants.Views.Pages.Account.FMUpdateProfilePage;
	}

	@RequestMapping(value = "/update-profile", method = RequestMethod.POST)
	@RequireHardLogIn
	@SuppressWarnings("deprecation")
	public String updateProfile(@Valid final FMUpdateProfileForm fmUpdateProfileForm, final BindingResult bindingResult,
			final Model model, final RedirectAttributes redirectAttributes) throws CMSItemNotFoundException,
			UnsupportedOperationException, ClassNotFoundException, SOAPException, IOException
	{
		final List<CountryData> countries = new ArrayList<CountryData>();
		countries.add(checkoutFacade.getCountryForIsocode("US"));
		countries.add(checkoutFacade.getCountryForIsocode("CA"));
		model.addAttribute("countryData", countries);

		model.addAttribute("regionsdatas", getI18NFacade().getRegionsForAllCountries().values());

		final List<String> uniqueId = new ArrayList<String>();
		final FMCustomerData fmCurrentCustomerData = fmCustomerFacade.getCurrentFMCustomer();
		List<String> roles = (List<String>) fmCurrentCustomerData.getRoles();
		boolean csrUserFlag = (roles != null && roles.size() > 0 && roles.contains(FM_CSR_ROLE) ? true : false);
		if (!validateProfileForm(fmUpdateProfileForm, model, csrUserFlag)) {

			storeCmsPageInModel(model, getContentPageForLabelOrId(FMPROFILE_CMS_PAGE));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FMPROFILE_CMS_PAGE));
			setUpdateProfile(model, fmUpdateProfileForm);
			model.addAttribute("customerdata", fmCurrentCustomerData);
			return ControllerConstants.Views.Pages.Account.FMUpdateProfilePage;
		}

		String returnAction = ControllerConstants.Views.Pages.Account.FMUpdateProfilePage;
		
		final FMCustomerData fmCustomerData = new FMCustomerData();

		LOG.info("updateProfile(...): fmUpdateProfileForm.getFirstName(): " + fmUpdateProfileForm.getFirstName());

		fmCustomerData.setTitleCode(fmUpdateProfileForm.getTitleCode());
		fmCustomerData.setFirstName(fmUpdateProfileForm.getFirstName());
		fmCustomerData.setLastName(fmUpdateProfileForm.getLastName());
		fmCustomerData.setNewsLetterSubscription(fmUpdateProfileForm.getNewsLetterSubscription());
		fmCustomerData.setTechAcademySubscription(fmCurrentCustomerData.getTechAcademySubscription());
		fmCustomerData.setUid(fmCurrentCustomerData.getUid());
		fmCustomerData.setEmail(fmCurrentCustomerData.getEmail());
		fmCustomerData.setDisplayUid(fmCurrentCustomerData.getDisplayUid());

		if (!csrUserFlag) {
			AddressData defaultShippingAddress = new AddressData();
			defaultShippingAddress.setLine1(fmUpdateProfileForm.getAddressline1().trim());
			defaultShippingAddress.setLine2(fmUpdateProfileForm.getAddressline2().trim());
			defaultShippingAddress.setTown(fmUpdateProfileForm.getCity().trim());
			
			LOG.info("updateProfile(...): fmUpdateProfileForm.getCountry(): " + fmUpdateProfileForm.getCountry());
			CountryData countryData = new CountryData();
			countryData.setIsocode(fmUpdateProfileForm.getCountry());
			defaultShippingAddress.setCountry(countryData);
			
			LOG.info("updateProfile(...): fmUpdateProfileForm.getState(): " + fmUpdateProfileForm.getState());
			RegionData regionData = new RegionData();
			regionData.setIsocodeShort(fmUpdateProfileForm.getState());
			regionData.setIsocode(fmUpdateProfileForm.getState());
			defaultShippingAddress.setRegion(regionData);
	
			defaultShippingAddress.setPostalCode(fmUpdateProfileForm.getZipCode().trim());
			defaultShippingAddress.setPhone(fmUpdateProfileForm.getPhoneno().trim().replaceAll("[\\-\\s\\(\\)\\.]", ""));
			fmCustomerData.setDefaultShippingAddress(defaultShippingAddress);
		}
		
		LOG.info("updateProfile(...): fmUpdateProfileForm.getPromoCode(): " + fmUpdateProfileForm.getPromoCode());
		if (fmUpdateProfileForm.getPromoCode() != null && !fmUpdateProfileForm.getPromoCode().isEmpty()) {
			fmCustomerData.setPromoCode(fmUpdateProfileForm.getPromoCode());
		}

		fmCustomerData.setLoyaltySignup(fmCurrentCustomerData.getLoyaltySignup());
		fmCustomerData.setIsGarageRewardMember(fmCurrentCustomerData.getIsGarageRewardMember());
		fmCustomerData.setAboutShop(fmUpdateProfileForm.getAboutShop());
		if ((null != fmUpdateProfileForm.getAboutShop() && !fmUpdateProfileForm.getAboutShop().isEmpty())
				&& (!uniqueId.contains(fmUpdateProfileForm.getAboutShop()))) {
			uniqueId.add(fmUpdateProfileForm.getAboutShop());
		}

		fmCustomerData.setShopType(fmUpdateProfileForm.getShopType());
		if ((null != fmUpdateProfileForm.getShopType() && !fmUpdateProfileForm.getShopType().isEmpty())
				&& (!uniqueId.contains(fmUpdateProfileForm.getShopType()))) {
			uniqueId.add(fmUpdateProfileForm.getShopType());
		}

		fmCustomerData.setShopBays(fmUpdateProfileForm.getShopBays());
		if ((null != fmUpdateProfileForm.getShopBays() && !fmUpdateProfileForm.getShopBays().isEmpty())
				&& (!uniqueId.contains(fmUpdateProfileForm.getShopBays()))) {
			uniqueId.add(fmUpdateProfileForm.getShopBays());
		}

		if (null != fmUpdateProfileForm.getMostIntersted()) {
			String mostIntersted = null;
			for (final String mostInterset : fmUpdateProfileForm.getMostIntersted()) {
				if (null != mostIntersted) {
					mostIntersted += mostInterset + ";";
				} else {
					mostIntersted = mostInterset + ";";
				}
				
				if (!uniqueId.contains(mostInterset)) {
					uniqueId.add(mostInterset);
				}
			}
			fmCustomerData.setMostIntersted(mostIntersted);
		}

		if (null != fmUpdateProfileForm.getBrands()) {
			String brands = null;
			for (final String brand : fmUpdateProfileForm.getBrands()) {
				if (null != brands) {
					brands += brand + ";";
				} else {
					brands = brand + ";";
				}
				
				if (!uniqueId.contains(brand)) {
					uniqueId.add(brand);
				}
			}
			fmCustomerData.setBrands(brands);
		}
		
		fmCustomerData.setShopBanner(fmUpdateProfileForm.getShopBanner());
		if (fmUpdateProfileForm.getShopBanner() != null && !fmUpdateProfileForm.getShopBanner().isEmpty()) {
			uniqueId.add(Config.getParameter("bannerId") + ":" + fmUpdateProfileForm.getShopBanner());
		}

		if (uniqueId.size() > 0 && !uniqueId.contains("Select")) {
			fmCustomerData.setUniqueID(uniqueId);
		}

		fmCustomerData.setLmsSigninId(fmUpdateProfileForm.getLmsSigniId());

		fmCustomerData.setIsLoyaltyRequestedMember(fmUpdateProfileForm.getIsLoyaltyRequestedMember());
		fmCustomerData.setUserTypeDescription(fmCurrentCustomerData.getUserTypeDescription());
		LOG.info("updateProfile(...): fmCurrentCustomerData.getUserTypeDescription(): " + fmCurrentCustomerData.getUserTypeDescription());
		if (fmCurrentCustomerData.getFmunit() != null) {
			fmCustomerData.setFmunit(fmCurrentCustomerData.getFmunit());
			LOG.info("updateProfile(...): Prospect ID for F-M Unit: " + fmCurrentCustomerData.getFmunit().getProspectId());
		}

		if (fmCurrentCustomerData.getFmunit() != null && fmCurrentCustomerData.getFmunit().getUnitAddress() != null 
				&& fmCurrentCustomerData.getFmunit().getUnitAddress().size() > 0) {
			fmCustomerData.setB2baddress(fmCurrentCustomerData.getFmunit().getUnitAddress().iterator().next());
			LOG.info("updateProfile(...): F-M Unit Address: " + fmCurrentCustomerData.getFmunit().getUnitAddress().iterator().next().getCompanyName()
					+ fmCurrentCustomerData.getFmunit().getUnitAddress().iterator().next().getLine1());
		}

		model.addAttribute("titleData", userFacade.getTitles());

		try
		{
			LOG.info("updateProfile(...): Calling 'fmCustomerFacade.updateFMProfile(fmCustomerData)'");
			fmCustomerFacade.updateFMProfile(fmCustomerData);
			GlobalMessages.addFlashMessage(redirectAttributes, GlobalMessages.CONF_MESSAGES_HOLDER,
					"text.account.profile.confirmationUpdated");
			returnAction = REDIRECT_TO_PROFILE_PAGE;
			LOG.info("updateProfile(...): returnAction: " + returnAction);
		}
		catch (final DuplicateUidException e) {
			bindingResult.rejectValue("email", "registration.error.account.exists.title");
			GlobalMessages.addErrorMessage(model, "form.global.error");
		}
		catch (final Exception e) {
			LOG.info("updateProfile(...): An Exception occurred while calling 'fmCustomerFacade.updateFMProfile(fmCustomerData)'");
			LOG.info("updateProfile(...): Exception: " + e);
			LOG.info("updateProfile(...): Exception Message: " + e.getMessage());
			LOG.info(e.getCause());
			LOG.info("updateProfile(...): EXCEPTION " + e.getStackTrace().toString());
			setUpdateProfile(model, fmUpdateProfileForm);
			model.addAttribute("customerdata", fmCurrentCustomerData);
			GlobalMessages.addErrorMessage(model, e.getMessage());
		}

		storeCmsPageInModel(model, getContentPageForLabelOrId(FMPROFILE_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FMPROFILE_CMS_PAGE));
		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getFMBreadcrumbs("text.account.profile"));
		return returnAction;
	}

	/**
	 * @param fmUpdateProfileForm
	 * @param model
	 * @param csrUserFlag Indicates whether the logged-in user is a CSR
	 * @return
	 */
	@SuppressWarnings("javadoc")
	private boolean validateProfileForm(final FMUpdateProfileForm fmUpdateProfileForm, final Model model, final boolean csrUserFlag)
	{
		LOG.info("Inside validateProfileForm method ");

		if (null == fmUpdateProfileForm.getFirstName() || fmUpdateProfileForm.getFirstName().isEmpty()) {
			GlobalMessages.addErrorMessage(model, "First Name should not be blank");
			LOG.info("validateProfileForm(...) :: missing First Name");
		}

		if (null == fmUpdateProfileForm.getLastName() || fmUpdateProfileForm.getLastName().isEmpty()) {
			GlobalMessages.addErrorMessage(model, "Last Name should not be blank");
			LOG.info("validateProfileForm(...) :: missing Last Name");
		}
		
		// Check if logged-in user is a CSR. If yes, then bypass remaining validations!
		if (!csrUserFlag) { // User is NOT a CSR
			LOG.info("validateProfileForm(...) :: User is not a CSR. So, validating remaining fields");
			if (fmUpdateProfileForm.getPromoCode() != null && !fmUpdateProfileForm.getPromoCode().isEmpty()) {
				LOG.info("validateProfileForm(...): Promo Code from form: " + fmUpdateProfileForm.getPromoCode());
				int count = 0;
				final String codes = Config.getParameter(WebConstants.B2T_REGISTRATION_PROMOCODES);
				final String[] promocodes = codes.split(",");
				for (int i = 0; i < promocodes.length; i++) {
					LOG.info("validateProfileForm(...): PROMOCODE[" + i + "]: " + promocodes[i]);
					if (promocodes[i].equalsIgnoreCase(fmUpdateProfileForm.getPromoCode())) {
						count++;
					}
				}

				if (count == 0) {
					GlobalMessages.addErrorMessage(model, "Enter a valid Promo Code");
				}
			}
			
			addressValidation(fmUpdateProfileForm, model);
			cityValidation(fmUpdateProfileForm, model);
			countryValidation(fmUpdateProfileForm, model);
			stateValidation(fmUpdateProfileForm, model);
			postalCodeValidation(fmUpdateProfileForm, model);
		} else {
			LOG.info("validateProfileForm(...) :: User is a CSR. So, remaining validations bypassed!");
		}

		if (GlobalMessages.hasErrorMessage(model)) {
			LOG.info("validateProfileForm(...) :: returning failure");
			return false;
		} else {
			LOG.info("validateProfileForm(...) :: returning success");
			return true;
		}
	}


	@RequestMapping(value = "/update-password", method = RequestMethod.GET)
	@RequireHardLogIn
	public String updatePassword(final Model model) throws CMSItemNotFoundException
	{
		final UpdatePasswordForm updatePasswordForm = new UpdatePasswordForm();

		model.addAttribute("updatePasswordForm", updatePasswordForm);

		storeCmsPageInModel(model, getContentPageForLabelOrId(FMPROFILE_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FMPROFILE_CMS_PAGE));

		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getFMBreadcrumbs("text.account.profile.updatePasswordForm"));
		model.addAttribute("metaRobots", "no-index,no-follow");

		final FMCustomerModel currentUser = (FMCustomerModel) userService.getCurrentUser();
		model.addAttribute("pwdMeetsLatestStandards", (currentUser.getPwdMeetsLatestStandards() != null && currentUser.getPwdMeetsLatestStandards()) ? true : false);
		if (currentUser.getPwdMeetsLatestStandards() != null && !currentUser.getPwdMeetsLatestStandards())
		{
			GlobalMessages.addErrorMessage(model, "text.Password.standard.info");
		}
		return ControllerConstants.Views.Pages.Account.FMUpdatePasswordPage;
	}

	@RequestMapping(value = "/update-password", method = RequestMethod.POST)
	@RequireHardLogIn
	public String updatePassword(@Valid final UpdatePasswordForm updatePasswordForm, final BindingResult bindingResult,
			final Model model, final RedirectAttributes redirectAttributes) throws CMSItemNotFoundException
	{
		final FMCustomerModel currentUser = (FMCustomerModel) userService.getCurrentUser();
		model.addAttribute("pwdMeetsLatestStandards", (currentUser.getPwdMeetsLatestStandards() != null && currentUser.getPwdMeetsLatestStandards()) ? true : false);

		if (!validatePasswordForm(updatePasswordForm, model))
		{
			storeCmsPageInModel(model, getContentPageForLabelOrId(FMPROFILE_CMS_PAGE));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FMPROFILE_CMS_PAGE));
			model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getFMBreadcrumbs("text.account.profile.updatePasswordForm"));
			return ControllerConstants.Views.Pages.Account.FMUpdatePasswordPage;
		}
		if (updatePasswordForm.getNewPassword().equals(updatePasswordForm.getCheckNewPassword()))
		{
			try
			{
			 int pwdForceChangeCount = 0;
				if (currentUser.getPwdMeetsLatestStandards() != null && !currentUser.getPwdMeetsLatestStandards())
				{
					pwdForceChangeCount++;
				}
				fmCustomerFacade.changeFMPassword(updatePasswordForm.getCurrentPassword(), updatePasswordForm.getNewPassword());
				LOG.info("Inside try block, update password works ");

				if (pwdForceChangeCount > 0)
				{
					LOG.info("INSIDE IF OF getPwdMeetsLatestStandards NOT TRUE");

					return REDIRECT_PREFIX + ROOT;
				}
				else
				{
					LOG.info("INSIDE IF OF getPwdMeetsLatestStandards TRUE");


					GlobalMessages.addFlashMessage(redirectAttributes, GlobalMessages.CONF_MESSAGES_HOLDER,
							"text.account.confirmation.password.updated");
					return REDIRECT_TO_PROFILE_PAGE;
				}

			}
			catch (final PasswordMismatchException localException)
			{
				LOG.info("Inside catch block, current pwd is wrong");
				bindingResult.rejectValue("currentPassword", "profile.currentPassword.invalid", new Object[] {},
						"profile.currentPassword.invalid");
				GlobalMessages.addErrorMessage(model, "Check current password");
			}

		}
		else
		{
			LOG.info("Inside else, password mismatch");
			bindingResult.rejectValue("checkNewPassword", "validation.checkPwd.equals", new Object[] {},
					"validation.checkPwd.equals");
		}
		storeCmsPageInModel(model, getContentPageForLabelOrId(FMPROFILE_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FMPROFILE_CMS_PAGE));

		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getFMBreadcrumbs("text.account.profile.updatePasswordForm"));
		return "redirect:";
	}

	/**
	 * @param updatePasswordForm
	 * @param model
	 * @return
	 */
	@SuppressWarnings("javadoc")
	private boolean validatePasswordForm(final UpdatePasswordForm updatePasswordForm, final Model model)
	{
		LOG.info("Inside validatePasswordForm method ");
		if (updatePasswordForm.getCurrentPassword() == null || updatePasswordForm.getCurrentPassword().isEmpty())
		{
			GlobalMessages.addErrorMessage(model, "Please enter your current password");
			LOG.info("Inside vaildate form :: invalid password");
		}
		if (updatePasswordForm.getNewPassword() == null || updatePasswordForm.getNewPassword().isEmpty())
		{
			GlobalMessages.addErrorMessage(model, "Please enter the new password");
			LOG.info("Inside vaildate form :: invalid password");
		}
		else
		{
			final String pwd = "^(?=.*?[0-9])(?=.*[A-Z]).{8,}$";

			if (!updatePasswordForm.getNewPassword().matches(pwd))
			{
				GlobalMessages.addErrorMessage(model, "text.Password.Validation.Error");
				LOG.info("Inside vaildate form :: invalid password");
			}
		}
		if (updatePasswordForm.getCheckNewPassword() == null || updatePasswordForm.getCheckNewPassword().isEmpty())
		{
			GlobalMessages.addErrorMessage(model, "Please confirm the password");
			LOG.info("Inside vaildate form :: invalid confirm password");
		}
		if (updatePasswordForm.getCheckNewPassword() != null)
		{
			if (updatePasswordForm.getNewPassword().equals(updatePasswordForm.getCheckNewPassword()))
			{
				LOG.info("Inside vaildate form :: valid password");
			}
			else
			{
				GlobalMessages.addErrorMessage(model, "Passwords do not match!! Re-Check the password");
				LOG.info("Inside vaildate form :: invalid password");
			}
		}
		if (GlobalMessages.hasErrorMessage(model))
		{
			LOG.info("Inside password validate form :: returning failure");
			return false;
		}
		else
		{
			LOG.info("Inside password validate form :: returning success");
			return true;
		}
	}

	@RequestMapping(value = "/address-book", method = RequestMethod.GET)
	@RequireHardLogIn
	public String getAddressBook(final Model model) throws CMSItemNotFoundException
	{

		LOG.info("Inside 'getAddressBook(...)'");
		final FMCustomerData fmCustomerData = fmCustomerFacade.getCurrentFMCustomer();
		model.addAttribute("currentdata", fmCustomerFacade.getCurrentFMCustomer());
		if (Fmusertype.WAREHOUSEDISTRIBUTORCOMMERCIALVEHICLE.equals(fmCustomerFacade.getCurrentFMCustomer()
				.getUserTypeDescription())
				|| Fmusertype.WAREHOUSEDISTRIBUTORLIGHTVEHICLE.equals(fmCustomerFacade.getCurrentFMCustomer()
						.getUserTypeDescription())
				|| Fmusertype.RETAILER.equals(fmCustomerFacade.getCurrentFMCustomer().getUserTypeDescription()))
		{
			//changeD here
			int unitCheckCounter = 0;
			final List<FMCustomerAccountData> checkUnitUid = fmCustomerFacade.getSoldToShipToUnitUid(fmCustomerData.getFmunit()
					.getUid());
			if (checkUnitUid.size() > CHECKRESULTSIZE)
			{
				final Iterator<FMCustomerAccountData> uidIterator = checkUnitUid.iterator();
				while (uidIterator.hasNext())
				{
					final FMCustomerAccountData unitUidData = (FMCustomerAccountData) uidIterator.next();

					if (unitUidData.getUid().equals(fmCustomerData.getFmunit().getUid()))
					{
						final List<FMB2bAddressData> unitAddress = fmCustomerData.getFmunit().getUnitAddress();
						final List<FMB2bAddressData> allMembersAddress = new ArrayList<FMB2bAddressData>();
						final List<FMCustomerAccountData> members = fmCustomerFacade.getPartnerAddress();
						final Iterator<FMCustomerAccountData> membersIterator = members.iterator();
						List<FmB2bAddressSort> sortAddress = new ArrayList<FmB2bAddressSort>();

						while (membersIterator.hasNext())
						{
							final FMCustomerAccountData member = (FMCustomerAccountData) membersIterator.next();
							final List<FMB2bAddressData> membersAddress = member.getUnitAddress();

							final Iterator<FMB2bAddressData> unitAddressIterator = membersAddress.iterator();
							while (unitAddressIterator.hasNext())
							{
								allMembersAddress.add((FMB2bAddressData) unitAddressIterator.next());
							}
						}
						sortAddress = setAddressFromAddressData(allMembersAddress, sortAddress);

						LOG.info(" size of allMembersAddress: " + allMembersAddress.size());
						model.addAttribute("unitDetails", fmCustomerData.getFmunit());
						model.addAttribute("SoldTo", unitAddress);
						LOG.info("size of file to be sorted address: " + sortAddress.size());
						java.util.Collections.sort(sortAddress);
						model.addAttribute("addressData", sortAddress);
					}
					else
					{
						unitCheckCounter++;
					}
				}

				if (unitCheckCounter > 0)
				{
					model.addAttribute("addressData", fmCustomerData.getFmunit().getUnitAddress());
					LOG.info("size: " + fmCustomerData.getFmunit().getUnitAddress().size());
				}
			}
			else
			{
				model.addAttribute("addressData", fmCustomerData.getFmunit().getUnitAddress());
			}
		}
		else if (Fmusertype.JOBBERPARTSTORE.equals(fmCustomerFacade.getCurrentFMCustomer().getUserTypeDescription())
				|| Fmusertype.SHOPOWNERTECHNICIAN.equals(fmCustomerFacade.getCurrentFMCustomer().getUserTypeDescription()))
		{
			model.addAttribute("addressData", fmCustomerFacade.getFMAddressBook());
		}
		else
		{
			model.addAttribute("addressData", fmCustomerFacade.getFMAddressBook());
		}
		storeCmsPageInModel(model, getContentPageForLabelOrId(FMADDRESS_BOOK_CMS_PAGE));
		model.addAttribute("clickedlink", "ClickedAddressbook");
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FMADDRESS_BOOK_CMS_PAGE));
		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getFMBreadcrumbs("text.account.addressBook"));
		model.addAttribute("metaRobots", "no-index,no-follow");
		return ControllerConstants.Views.Pages.Account.FMAccountAddressBookPage;
	}

	private List<FmB2bAddressSort> setAddressFromAddressData(final List<FMB2bAddressData> allMembersAddress,
			final List<FmB2bAddressSort> sortedAddres)
	{
		LOG.info("SIZE IN METHOD: " + allMembersAddress.size());
		final Iterator<FMB2bAddressData> allMembersIterator = allMembersAddress.iterator();
		while (allMembersIterator.hasNext())
		{
			final FMB2bAddressData fmAddressData = (FMB2bAddressData) allMembersIterator.next();
			final FmB2bAddressSort resultData = new FmB2bAddressSort();
			resultData.setCompanyFax(fmAddressData.getCompanyFax());
			resultData.setCompanyName(fmAddressData.getCompanyName());
			resultData.setCompanyUrl(fmAddressData.getCompanyUrl());
			resultData.setCountry(fmAddressData.getCountry());
			resultData.setDefaultAddress(fmAddressData.isDefaultAddress());
			resultData.setEmail(fmAddressData.getEmail());
			resultData.setFirstName(fmAddressData.getFirstName());
			resultData.setLastName(fmAddressData.getLastName());
			resultData.setLine1(fmAddressData.getLine1());
			resultData.setLine2(fmAddressData.getLine2());
			resultData.setPhone(fmAddressData.getPhone());
			resultData.setPostalCode(fmAddressData.getPostalCode());
			resultData.setRegion(fmAddressData.getRegion());
			resultData.setShippingAddress(fmAddressData.isShippingAddress());
			resultData.setTitle(fmAddressData.getTitle());
			resultData.setTown(fmAddressData.getTown());
			resultData.setVisibleInAddressBook(fmAddressData.isVisibleInAddressBook());
			sortedAddres.add(resultData);
		}

		return sortedAddres;
	}

	@RequestMapping(value = "/add-address", method = RequestMethod.GET)
	@RequireHardLogIn
	public String addAddress(final Model model) throws CMSItemNotFoundException
	{
		model.addAttribute("countryData", checkoutFacade.getDeliveryCountries());
		model.addAttribute("titleData", userFacade.getTitles());

		final FMCustomerData fmCustomerData = fmCustomerFacade.getCurrentFMCustomer();

		if (Fmusertype.JOBBERPARTSTORE.equals(fmCustomerData.getUserTypeDescription())
				|| Fmusertype.SHOPOWNERTECHNICIAN.equals(fmCustomerData.getUserTypeDescription()))
		{
			model.addAttribute("currentdata", fmCustomerData);
			model.addAttribute("regionData", fmCustomerFacade.getTaxApprovedRegions(fmCustomerData.getFmunit()));
			LOG.info("SIZE OF REGIONS: " + fmCustomerFacade.getTaxApprovedRegions(fmCustomerData.getFmunit()).size());
		}
		else
		{
			model.addAttribute("regionData", getI18NFacade().getRegionsForAllCountries().values());
		}
		model.addAttribute("addressForm", new FMAddressForm());
		model.addAttribute("addressBookEmpty", Boolean.valueOf(userFacade.isAddressBookEmpty()));

		storeCmsPageInModel(model, getContentPageForLabelOrId(FMADD_EDIT_ADDRESS_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FMADD_EDIT_ADDRESS_CMS_PAGE));

		final List<Breadcrumb> breadcrumbs = accountBreadcrumbBuilder.getFMBreadcrumbs(null);
		breadcrumbs.add(new Breadcrumb("/my-fmaccount/address-book", getMessageSource().getMessage("text.account.addressBook",
				null, getI18nService().getCurrentLocale()), null));
		breadcrumbs.add(new Breadcrumb("#", getMessageSource().getMessage("text.account.addressBook.addEditAddress", null,
				getI18nService().getCurrentLocale()), null));
		model.addAttribute("breadcrumbs", breadcrumbs);
		model.addAttribute("metaRobots", "no-index,no-follow");
		return ControllerConstants.Views.Pages.Account.FMAccountEditAddressPage;
	}


	@RequestMapping(value = "/getStatesForAddEdit", method = RequestMethod.POST)
	public String getStatesForAddEdit(@RequestParam(value = "selectedCountry") final String country, final Model model)
			throws CMSItemNotFoundException
	{

		LOG.info("COUNTRY IN CONTROLLER: " + country);

		model.addAttribute("addressForm", new FMAddressForm());
		model.addAttribute("countryData", checkoutFacade.getDeliveryCountries());

		model.addAttribute("regionsdatas", getI18NFacade().getRegionsForCountryIso(country));
		storeCmsPageInModel(model, getContentPageForLabelOrId(FMADD_EDIT_ADDRESS_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FMADD_EDIT_ADDRESS_CMS_PAGE));

		return "fragments/account/addEditStatesFragment";
	}

	@RequestMapping(value = "/add-address", method = RequestMethod.POST)
	@RequireHardLogIn
	public String addAddress(@Valid final FMAddressForm fmaddressForm, final Model model, final RedirectAttributes redirectModel)
			throws CMSItemNotFoundException
	{
		if (!validateAddressForm(fmaddressForm, model))
		{

			storeCmsPageInModel(model, getContentPageForLabelOrId(FMADD_EDIT_ADDRESS_CMS_PAGE));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FMADD_EDIT_ADDRESS_CMS_PAGE));
			model.addAttribute("countryData", checkoutFacade.getDeliveryCountries());
			model.addAttribute("titleData", userFacade.getTitles());
			final FMCustomerData fmCustomerData = fmCustomerFacade.getCurrentFMCustomer();

			if (Fmusertype.JOBBERPARTSTORE.equals(fmCustomerData.getUserTypeDescription())
					|| Fmusertype.SHOPOWNERTECHNICIAN.equals(fmCustomerData.getUserTypeDescription()))
			{
				model.addAttribute("currentdata", fmCustomerData);
				model.addAttribute("regionData", fmCustomerFacade.getTaxApprovedRegions(fmCustomerData.getFmunit()));
				LOG.info("SIZE OF REGIONS: " + fmCustomerFacade.getTaxApprovedRegions(fmCustomerData.getFmunit()).size());
			}
			else
			{
				model.addAttribute("regionData", getI18NFacade().getRegionsForAllCountries().values());
			}
			model.addAttribute("addressForm", fmaddressForm);
			model.addAttribute("addressBookEmpty", Boolean.valueOf(userFacade.isAddressBookEmpty()));
			return ControllerConstants.Views.Pages.Account.FMAccountEditAddressPage;
		}

		final AddressData newAddress = new AddressData();
		newAddress.setTitleCode(fmaddressForm.getTitleCode());
		newAddress.setFirstName(fmaddressForm.getFirstName());
		newAddress.setLastName(fmaddressForm.getLastName());
		newAddress.setLine1(fmaddressForm.getLine1());
		newAddress.setLine2(fmaddressForm.getLine2());
		newAddress.setTown(fmaddressForm.getTownCity());
		newAddress.setPostalCode(fmaddressForm.getPostcode());
		newAddress.setBillingAddress(false);
		newAddress.setShippingAddress(true);
		newAddress.setVisibleInAddressBook(true);

		final CountryData countryData = new CountryData();

		countryData.setIsocode(fmaddressForm.getCountryIso());
		newAddress.setCountry(countryData);

		final RegionData regionData = new RegionData();

		regionData.setIsocode(fmaddressForm.getRegion());
		newAddress.setRegion(regionData);

		if (userFacade.isAddressBookEmpty())
		{
			newAddress.setDefaultAddress(true);
		}
		else
		{
			newAddress.setDefaultAddress(true);
		}

		userFacade.addAddress(newAddress);
		GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, "account.confirmation.address.added");

		return REDIRECT_TO_ADDRESS_BOOK_PAGE;
	}

	/**
	 * @param fmaddressForm
	 * @param model
	 * @return
	 */
	@SuppressWarnings("javadoc")
	private boolean validateAddressForm(final FMAddressForm fmaddressForm, final Model model)
	{
		LOG.info("Inside validateAddressForm method ");
		if (null == fmaddressForm.getTitleCode() || fmaddressForm.getTitleCode().isEmpty())
		{
			GlobalMessages.addErrorMessage(model, "Please select the title");
			LOG.info("Inside validate form :: missing title");
		}
		if (null == fmaddressForm.getFirstName() || fmaddressForm.getFirstName().isEmpty())
		{
			GlobalMessages.addErrorMessage(model, "First Name should not be blank");
			LOG.info("Inside validate form :: missing first name");
		}
		if (null == fmaddressForm.getLastName() || fmaddressForm.getLastName().isEmpty())
		{
			GlobalMessages.addErrorMessage(model, "Last Name should not be blank");
			LOG.info("Inside validate form :: missing last name");
		}

		if (null == fmaddressForm.getLine1() || fmaddressForm.getLine1().isEmpty())
		{
			GlobalMessages.addErrorMessage(model, "Enter the address ");
			LOG.info("Inside validate form :: missing address");
		}
		if (null == fmaddressForm.getTownCity() || fmaddressForm.getTownCity().isEmpty())
		{
			GlobalMessages.addErrorMessage(model, "Please enter the company city");
			LOG.info("Inside validate form :: missing city");
		}
		if (null == fmaddressForm.getRegion() || fmaddressForm.getRegion().isEmpty())
		{
			GlobalMessages.addErrorMessage(model, "Select the region");
			LOG.info("Inside validate form :: missing region");
		}
		String zipcode = new String();
		if ("US".equals(fmaddressForm.getCountryIso()))
		{
			zipcode = "^\\d{5}(-\\d{4})?$";
		}
		else if ("CA".equals(fmaddressForm.getCountryIso()))
		{
			zipcode = "(^[ABCEGHJKLMNPRSTVXYabceghjklmnprstvxy]{1}\\d{1}[A-Za-z]{1} \\d{1}[A-Za-z]{1}\\d{1}$)";
		}

		if (null == fmaddressForm.getPostcode() || fmaddressForm.getPostcode().isEmpty())
		{
			GlobalMessages.addErrorMessage(model, "Enter the ZIP Code");
			LOG.info("Inside validate form :: missing postal code");
		}
		else if (!fmaddressForm.getPostcode().matches(zipcode))
		{
			if ("US".equals(fmaddressForm.getCountryIso()))
			{
				GlobalMessages.addErrorMessage(model, " zip code should have valid format like 11111 or 11111-1111");
			}
			else if ("CA".equals(fmaddressForm.getCountryIso()))
			{
				GlobalMessages.addErrorMessage(model, "zip code should have valid format like A1A1A1 or a1a 1a1");
			}
			LOG.info("Inside vaildate form :: invalid zip code");
		}

		if ("Default".equals(fmaddressForm.getRegion()))
		{
			GlobalMessages.addErrorMessage(model, "Please select the state");
			LOG.info("Inside validate form :: invalid state");
		}

		if ("Default".equals(fmaddressForm.getCountryIso()))
		{
			GlobalMessages.addErrorMessage(model, "Please select the country");
			LOG.info("Inside vaildate form :: invalid country");
		}
		if (!"Default".equals(fmaddressForm.getRegion()) && !"Default".equals(fmaddressForm.getCountryIso()))
		{
			LOG.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			if (fmaddressForm.getRegion().contains("CA-") && fmaddressForm.getCountryIso().contains("US"))
			{
				GlobalMessages.addErrorMessage(model, "check the state and country you added");
				LOG.info("Inside vaildate form :: invalid country");
			}
			else
			{
				if (fmaddressForm.getRegion().contains("US-") && fmaddressForm.getCountryIso().contains("CA"))
				{
					GlobalMessages.addErrorMessage(model, "check the state and country you added");
					LOG.info("Inside vaildate form :: invalid country");
				}
			}
		}
		if (GlobalMessages.hasErrorMessage(model))
		{
			LOG.info("Inside address validate form :: returning failure");
			return false;
		}
		else
		{
			LOG.info("Inside address validate form :: returning success");
			return true;
		}
	}

	@RequestMapping(value = "/edit-address/" + ADDRESS_CODE_PATH_VARIABLE_PATTERN, method = RequestMethod.GET)
	@RequireHardLogIn
	public String editAddress(@PathVariable("addressCode") final String addressCode, final Model model)
			throws CMSItemNotFoundException
	{
		final FMAddressForm addressForm = new FMAddressForm();
		model.addAttribute("countryData", checkoutFacade.getDeliveryCountries());
		model.addAttribute("titleData", userFacade.getTitles());

		final FMCustomerData fmCustomerData = fmCustomerFacade.getCurrentFMCustomer();
		if (fmCustomerData.getUserTypeDescription().equals(Fmusertype.JOBBERPARTSTORE)
				|| fmCustomerData.getUserTypeDescription().equals(Fmusertype.SHOPOWNERTECHNICIAN))
		{
			model.addAttribute("regionData", fmCustomerFacade.getTaxApprovedRegions(fmCustomerData.getFmunit()));
			LOG.info("SIZE OF REGIONS: " + fmCustomerFacade.getTaxApprovedRegions(fmCustomerData.getFmunit()).size());
		}
		else
		{
			model.addAttribute("regionData", getI18NFacade().getRegionsForAllCountries().values());
		}

		model.addAttribute("addressForm", addressForm);
		model.addAttribute("addressBookEmpty", Boolean.valueOf(userFacade.isAddressBookEmpty()));

		//for (final AddressData addressData : userFacade.getAddressBook())
		for (final AddressData addressData : fmCustomerFacade.getFMAddressBook())
		{
			if (addressData.getId() != null && addressData.getId().equals(addressCode))
			{
				model.addAttribute("addressData", addressData);
				addressForm.setAddressId(addressData.getId());
				addressForm.setTitleCode(addressData.getTitleCode());
				addressForm.setFirstName(addressData.getFirstName());
				addressForm.setLastName(addressData.getLastName());
				addressForm.setLine1(addressData.getLine1());
				addressForm.setLine2(addressData.getLine2());
				addressForm.setTownCity(addressData.getTown());
				if (addressData.getRegion() != null)
				{
					addressForm.setRegion(addressData.getRegion().getIsocode());
				}
				addressForm.setPostcode(addressData.getPostalCode());
				addressForm.setCountryIso(addressData.getCountry().getIsocode());
				if (userFacade.getDefaultAddress() != null && userFacade.getDefaultAddress().getId() != null
						&& userFacade.getDefaultAddress().getId().equals(addressData.getId()))
				{
					addressForm.setDefaultAddress(Boolean.TRUE);
				}
				break;
			}
		}

		storeCmsPageInModel(model, getContentPageForLabelOrId(FMADD_EDIT_ADDRESS_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FMADD_EDIT_ADDRESS_CMS_PAGE));

		final List<Breadcrumb> breadcrumbs = accountBreadcrumbBuilder.getFMBreadcrumbs(null);
		breadcrumbs.add(new Breadcrumb("/my-fmaccount/address-book", getMessageSource().getMessage("text.account.addressBook",
				null, getI18nService().getCurrentLocale()), null));
		breadcrumbs.add(new Breadcrumb("#", getMessageSource().getMessage("text.account.addressBook.addEditAddress", null,
				getI18nService().getCurrentLocale()), null));
		model.addAttribute("breadcrumbs", breadcrumbs);
		model.addAttribute("metaRobots", "no-index,no-follow");
		return ControllerConstants.Views.Pages.Account.FMAccountEditAddressPage;
	}

	@RequestMapping(value = "/edit-address/" + ADDRESS_CODE_PATH_VARIABLE_PATTERN, method = RequestMethod.POST)
	@RequireHardLogIn
	public String editAddress(@Valid final FMAddressForm fmaddressForm, final Model model, final RedirectAttributes redirectModel)
			throws CMSItemNotFoundException
	{
		if (!validateAddressForm(fmaddressForm, model))
		{
			GlobalMessages.addErrorMessage(model, "form.global.error");
			storeCmsPageInModel(model, getContentPageForLabelOrId(FMADD_EDIT_ADDRESS_CMS_PAGE));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FMADD_EDIT_ADDRESS_CMS_PAGE));
			model.addAttribute("countryData", checkoutFacade.getDeliveryCountries());
			model.addAttribute("titleData", userFacade.getTitles());
			final FMCustomerData fmCustomerData = fmCustomerFacade.getCurrentFMCustomer();
			if (fmCustomerData.getUserTypeDescription().equals(Fmusertype.JOBBERPARTSTORE)
					|| fmCustomerData.getUserTypeDescription().equals(Fmusertype.SHOPOWNERTECHNICIAN))
			{
				model.addAttribute("regionData", fmCustomerFacade.getTaxApprovedRegions(fmCustomerData.getFmunit()));
				LOG.info("SIZE OF REGIONS: " + fmCustomerFacade.getTaxApprovedRegions(fmCustomerData.getFmunit()).size());
			}
			else
			{
				model.addAttribute("regionData", getI18NFacade().getRegionsForAllCountries().values());
			}
			model.addAttribute("addressForm", fmaddressForm);
			model.addAttribute("addressBookEmpty", Boolean.valueOf(userFacade.isAddressBookEmpty()));
			return ControllerConstants.Views.Pages.Account.FMAccountEditAddressPage;
		}

		model.addAttribute("metaRobots", "no-index,no-follow");

		LOG.info("INSIDE EDIT ADDRESS add id: " + fmaddressForm.getAddressId());
		LOG.info("INSIDE EDIT ADDRESS name: " + fmaddressForm.getFirstName());
		LOG.info("INSIDE EDIT ADDRESS title: " + fmaddressForm.getTitleCode());
		final AddressData newAddress = new AddressData();

		newAddress.setId(fmaddressForm.getAddressId());
		newAddress.setTitleCode(fmaddressForm.getTitleCode());
		newAddress.setFirstName(fmaddressForm.getFirstName());
		newAddress.setLastName(fmaddressForm.getLastName());
		newAddress.setLine1(fmaddressForm.getLine1());
		newAddress.setLine2(fmaddressForm.getLine2());
		newAddress.setTown(fmaddressForm.getTownCity());
		newAddress.setPostalCode(fmaddressForm.getPostcode());
		newAddress.setBillingAddress(false);
		newAddress.setShippingAddress(true);
		newAddress.setVisibleInAddressBook(true);

		final CountryData countryData = new CountryData();

		countryData.setIsocode(fmaddressForm.getCountryIso());
		newAddress.setCountry(countryData);

		final RegionData regionData = new RegionData();

		regionData.setIsocode(fmaddressForm.getRegion());
		newAddress.setRegion(regionData);

		if (Boolean.TRUE.equals(fmaddressForm.getDefaultAddress()) || userFacade.getAddressBook().size() <= 1)
		{
			newAddress.setDefaultAddress(true);
		}
		else
		{
			newAddress.setDefaultAddress(fmaddressForm.getDefaultAddress() != null
					&& fmaddressForm.getDefaultAddress().booleanValue());
		}
		userFacade.editAddress(newAddress);

		GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER,
				"text.account.addressBook.confirmationUpdated");
		return REDIRECT_TO_ADDRESS_BOOK_PAGE;
	}

	@RequestMapping(value = "/setadd", method = RequestMethod.POST)
	@RequireHardLogIn
	public String getunitdetails(@RequestParam(value = "t") final String addid)
	{
		LOG.info("from ajax: " + addid);

		return addid;
	}

	@RequestMapping(value = "/set-default-address", method = RequestMethod.POST)
	@RequireHardLogIn
	public String setDefaultAddress(@RequestParam(value = "addressid") final String addressCode,
			final RedirectAttributes redirectModel)
	{
		LOG.info("addressCode in controller  - set default address method: " + addressCode);

		final AddressData addressData = new AddressData();
		addressData.setDefaultAddress(true);
		addressData.setVisibleInAddressBook(true);
		addressData.setId(addressCode);

		userFacade.setDefaultAddress(addressData);
		GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER,
				"account.confirmation.default.address.changed");

		LOG.info("addressCode in controller  post default address method: " + addressCode);

		return REDIRECT_TO_ADDRESS_BOOK_PAGE;
	}

	@RequestMapping(value = "/set-admin-default-address", method = RequestMethod.POST)
	@RequireHardLogIn
	public String setAdminDefaultAddress(@RequestParam(value = "addressid") final String addressCode,
			final RedirectAttributes redirectModel)
	{
		LOG.info("addressCode in controller  - set default address method: " + addressCode);

		final AddressData addressData = new AddressData();
		addressData.setDefaultAddress(true);
		addressData.setVisibleInAddressBook(true);
		addressData.setId(addressCode);

		userFacade.setDefaultAddress(addressData);
		GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER,
				"account.confirmation.default.address.changed");

		LOG.info("addressCode in controller  post default address method: " + addressCode);

		return REDIRECT_TO_ADMIN_ADDRESS_BOOK_PAGE;
	}

	@RequestMapping(value = "/remove-address/" + ADDRESS_CODE_PATH_VARIABLE_PATTERN, method = RequestMethod.GET)
	@RequireHardLogIn
	public String removeAddress(@PathVariable("addressCode") final String addressCode, final RedirectAttributes redirectModel)
	{
		final AddressData addressData = new AddressData();
		addressData.setId(addressCode);
		userFacade.removeAddress(addressData);
		GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, "account.confirmation.address.removed");
		return REDIRECT_TO_ADDRESS_BOOK_PAGE;
	}

	@RequestMapping(value = "/removeAdmin-address/" + ADDRESS_CODE_PATH_VARIABLE_PATTERN, method = RequestMethod.GET)
	@RequireHardLogIn
	public String removeAdminAddress(@PathVariable("addressCode") final String addressCode, final RedirectAttributes redirectModel)
	{

		fmCustomerFacadeimpl.removeAdminAddress(addressCode);
		LOG.info("removie admin adresss before redirecting");

		GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, "account.confirmation.address.removed");
		return REDIRECT_TO_ADMIN_ADDRESS_BOOK_PAGE;
	}

	@RequestMapping(value = "/adminAddress-book", method = RequestMethod.GET)
	@RequireHardLogIn
	public String getAdminAddressBook(@RequestParam(value = "page", defaultValue = "0") final int page,
			@RequestParam(value = "show", defaultValue = "Page") final ShowMode showMode,
			@RequestParam(value = "noOfRecords", defaultValue = "9") final int noOfRecords,
			@RequestParam(value = "sort", defaultValue = B2BCustomerModel.NAME) final String sortCode, final Model model)
			throws CMSItemNotFoundException
	{
		if (fmCustomerFacade.getCurrentFMCustomer().getUserTypeDescription() != null)
		{
			if (fmCustomerFacade.getCurrentFMCustomer().getUserTypeDescription().equals(Fmusertype.JOBBERPARTSTORE)
					|| fmCustomerFacade.getCurrentFMCustomer().getUserTypeDescription().equals(Fmusertype.SHOPOWNERTECHNICIAN))
			{
				final List<FMB2bAddressData> companyAddress = fmCustomerFacade.getCurrentFMCustomer().getFmunit().getUnitAddress();
				model.addAttribute("unitDetails", fmCustomerFacade.getCurrentFMCustomer().getFmunit());
				model.addAttribute("unitAddress", companyAddress);
			}
			else
			{
				LOG.info("HI from get adminaddress book method ");
				final PageableData pageableData = createPageableData(page, noOfRecords, sortCode, showMode);
				final SearchPageData<B2BUnitData> searchPageData = fmNetworkFacade.getAllFMAccounts(pageableData);
				populateModel(model, searchPageData, showMode);

				final Map<B2BUnitData, List<AddressData>> unitAddressMap = new HashMap<B2BUnitData, List<AddressData>>();
				final Iterator<B2BUnitData> i = searchPageData.getResults().iterator();
				while (i.hasNext())
				{
					final B2BUnitData b2bUnitData = (B2BUnitData) i.next();
					LOG.info("customer UId:: " + b2bUnitData.getUid());
					final List<AddressData> addressDataForUnit = b2bUnitData.getAddresses();
					unitAddressMap.put(b2bUnitData, addressDataForUnit);
					model.addAttribute("unitAddressMap", unitAddressMap);
					model.addAttribute("currentAdminAddressData", userFacade.getAddressBook());
					model.addAttribute("currentdata", fmCustomerFacade.getCurrentFMCustomer());
				}
			}
		}
		else
		{
			LOG.info("HI from get adminaddress book method ");
			final PageableData pageableData = createPageableData(page, noOfRecords, sortCode, showMode);
			final SearchPageData<B2BUnitData> searchPageData = fmNetworkFacade.getAllFMAccounts(pageableData);

			populateModel(model, searchPageData, showMode);

			final Map<B2BUnitData, List<AddressData>> unitAddressMap = new HashMap<B2BUnitData, List<AddressData>>();
			final Iterator<B2BUnitData> i = searchPageData.getResults().iterator();
			while (i.hasNext())
			{
				final B2BUnitData b2bUnitData = (B2BUnitData) i.next();
				LOG.info("customer UId:: " + b2bUnitData.getUid());

				//for each customer
				final List<AddressData> addressDataForUnit = b2bUnitData.getAddresses();
				unitAddressMap.put(b2bUnitData, addressDataForUnit);
				model.addAttribute("unitAddressMap", unitAddressMap);
				model.addAttribute("currentAdminAddressData", userFacade.getAddressBook());
				model.addAttribute("currentdata", fmCustomerFacade.getCurrentFMCustomer());
			}
		}
		storeCmsPageInModel(model, getContentPageForLabelOrId(FMADMIN_ADDRESS_BOOK_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FMADMIN_ADDRESS_BOOK_CMS_PAGE));
		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getFMBreadcrumbs("text.account.adminAddressBook"));
		model.addAttribute("ProductCountPerPage", noOfRecords);
		model.addAttribute("metaRobots", "no-index,no-follow");
		return ControllerConstants.Views.Pages.Account.FMAccountAdminAddressBookPage;

	}

	@RequestMapping(value = "/admin-add-address", method = RequestMethod.GET)
	@RequireHardLogIn
	public String adminAddAddress(final Model model) throws CMSItemNotFoundException
	{
		model.addAttribute("countryData", checkoutFacade.getDeliveryCountries());
		model.addAttribute("titleData", userFacade.getTitles());
		//model.addAttribute("regionData", getI18NFacade().getRegionsForAllCountries().values());
		final FMCustomerData fmCustomerData = fmCustomerFacade.getCurrentFMCustomer();
		if (fmCustomerData.getUserTypeDescription() != null)
		{
			if (fmCustomerData.getUserTypeDescription().equals(Fmusertype.JOBBERPARTSTORE)
					|| fmCustomerData.getUserTypeDescription().equals(Fmusertype.SHOPOWNERTECHNICIAN))
			{
				model.addAttribute("currentdata", fmCustomerData);
				model.addAttribute("regionData", fmCustomerFacade.getTaxApprovedRegions(fmCustomerData.getFmunit()));
				LOG.info("SIZE OF REGIONS: " + fmCustomerFacade.getTaxApprovedRegions(fmCustomerData.getFmunit()).size());
			}
			else
			{
				model.addAttribute("regionData", getI18NFacade().getRegionsForAllCountries().values());
			}
		}
		else
		{
			model.addAttribute("regionData", getI18NFacade().getRegionsForAllCountries().values());
		}

		model.addAttribute("addressForm", new FMAddressForm());
		model.addAttribute("addressBookEmpty", Boolean.valueOf(userFacade.isAddressBookEmpty()));

		storeCmsPageInModel(model, getContentPageForLabelOrId(FMADD_EDIT_ADDRESS_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FMADD_EDIT_ADDRESS_CMS_PAGE));

		final List<Breadcrumb> breadcrumbs = accountBreadcrumbBuilder.getFMBreadcrumbs(null);
		breadcrumbs.add(new Breadcrumb("/my-fmaccount/address-book", getMessageSource().getMessage("text.account.addressBook",
				null, getI18nService().getCurrentLocale()), null));
		breadcrumbs.add(new Breadcrumb("#", getMessageSource().getMessage("text.account.addressBook.addEditAddress", null,
				getI18nService().getCurrentLocale()), null));
		model.addAttribute("breadcrumbs", breadcrumbs);
		model.addAttribute("metaRobots", "no-index,no-follow");
		return ControllerConstants.Views.Pages.Account.FMAccountAdminAddEditAddressPage;
	}

	@RequestMapping(value = "/admin-add-address", method = RequestMethod.POST)
	@RequireHardLogIn
	public String adminAddAddress(@Valid final FMAddressForm fmaddressForm, final Model model,
			final RedirectAttributes redirectModel) throws CMSItemNotFoundException
	{
		LOG.info("title and name in add address post method: " + fmaddressForm.getTitleCode() + " " + fmaddressForm.getFirstName());

		if (!validateAddressForm(fmaddressForm, model))
		{
			GlobalMessages.addErrorMessage(model, "form.global.error");
			storeCmsPageInModel(model, getContentPageForLabelOrId(FMADD_EDIT_ADDRESS_CMS_PAGE));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FMADD_EDIT_ADDRESS_CMS_PAGE));
			model.addAttribute("countryData", checkoutFacade.getDeliveryCountries());
			model.addAttribute("titleData", userFacade.getTitles());
			final FMCustomerData fmCustomerData = fmCustomerFacade.getCurrentFMCustomer();
			if (fmCustomerData.getUserTypeDescription() != null)
			{
				if (fmCustomerData.getUserTypeDescription().equals(Fmusertype.JOBBERPARTSTORE)
						|| fmCustomerData.getUserTypeDescription().equals(Fmusertype.SHOPOWNERTECHNICIAN))
				{
					model.addAttribute("currentdata", fmCustomerData);
					model.addAttribute("regionData", fmCustomerFacade.getTaxApprovedRegions(fmCustomerData.getFmunit()));
					LOG.info("SIZE OF REGIONS: " + fmCustomerFacade.getTaxApprovedRegions(fmCustomerData.getFmunit()).size());
				}
				else
				{
					model.addAttribute("regionData", getI18NFacade().getRegionsForAllCountries().values());
				}
			}
			model.addAttribute("addressForm", new FMAddressForm());
			model.addAttribute("addressBookEmpty", Boolean.valueOf(userFacade.isAddressBookEmpty()));
			storeCmsPageInModel(model, getContentPageForLabelOrId(FMADD_EDIT_ADDRESS_CMS_PAGE));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FMADD_EDIT_ADDRESS_CMS_PAGE));
			return ControllerConstants.Views.Pages.Account.FMAccountEditAddressPage;
		}

		final AddressData newAddress = new AddressData();
		newAddress.setTitleCode(fmaddressForm.getTitleCode());
		newAddress.setFirstName(fmaddressForm.getFirstName());
		newAddress.setLastName(fmaddressForm.getLastName());
		newAddress.setLine1(fmaddressForm.getLine1());
		newAddress.setLine2(fmaddressForm.getLine2());
		newAddress.setTown(fmaddressForm.getTownCity());
		newAddress.setPostalCode(fmaddressForm.getPostcode());
		newAddress.setBillingAddress(false);
		newAddress.setShippingAddress(true);
		newAddress.setVisibleInAddressBook(true);

		final CountryData countryData = new CountryData();

		countryData.setIsocode(fmaddressForm.getCountryIso());
		newAddress.setCountry(countryData);

		final RegionData regionData = new RegionData();

		regionData.setIsocode(fmaddressForm.getRegion());
		newAddress.setRegion(regionData);

		if (userFacade.isAddressBookEmpty())
		{
			newAddress.setDefaultAddress(true);
		}
		else
		{
			newAddress.setDefaultAddress(true);
		}

		fmCustomerFacade.createAdminAddress(newAddress);
		GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, "account.confirmation.address.added");

		return REDIRECT_TO_ADMIN_ADDRESS_BOOK_PAGE;
	}

	@RequestMapping(value = "/admin-edit-address/" + ADDRESS_CODE_PATH_VARIABLE_PATTERN, method = RequestMethod.GET)
	@RequireHardLogIn
	public String adminEditAddress(@PathVariable("addressCode") final String addressCode, final Model model)
			throws CMSItemNotFoundException
	{
		final FMAddressForm addressForm = new FMAddressForm();
		model.addAttribute("countryData", checkoutFacade.getDeliveryCountries());
		model.addAttribute("titleData", userFacade.getTitles());
		model.addAttribute("regionData", getI18NFacade().getRegionsForAllCountries().values());
		model.addAttribute("addressBookEmpty", Boolean.valueOf(userFacade.isAddressBookEmpty()));

		final AddressData addressData = fmCustomerFacade.getAddressByID(addressCode);
		LOG.info("*************company name: " + addressData.getCompanyName());

		if (addressData.getId() != null && addressData.getId().equals(addressCode))
		{
			LOG.info("*******adddresss data id not null*****1*id:" + addressData.getId() + "*****2:" + addressData.getLine1()
					+ "**3" + addressData.getLine2() + "**4" + addressData.getTown() + "*****5" + addressData.getRegion().getIsocode()
					+ "**6" + addressData.getPostalCode() + "**7::" + addressData.getCountry().getIsocode());
			model.addAttribute("addressData", addressData);
			addressForm.setAddressId(addressData.getId());
			addressForm.setLine1(addressData.getLine1());
			if (addressData.getLine2() != null)
			{
				addressForm.setLine2(addressData.getLine2());
			}
			addressForm.setTownCity(addressData.getTown());
			addressForm.setRegion(addressData.getRegion().getIsocode());
			addressForm.setPostcode(addressData.getPostalCode());
			addressForm.setCountryIso(addressData.getCountry().getIsocode());
		}
		model.addAttribute("addressForm", addressForm);

		storeCmsPageInModel(model, getContentPageForLabelOrId(FMADD_EDIT_ADDRESS_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FMADD_EDIT_ADDRESS_CMS_PAGE));

		final List<Breadcrumb> breadcrumbs = accountBreadcrumbBuilder.getFMBreadcrumbs(null);
		breadcrumbs.add(new Breadcrumb("/my-fmaccount/address-book", getMessageSource().getMessage("text.account.addressBook",
				null, getI18nService().getCurrentLocale()), null));
		breadcrumbs.add(new Breadcrumb("#", getMessageSource().getMessage("text.account.addressBook.addEditAddress", null,
				getI18nService().getCurrentLocale()), null));
		model.addAttribute("breadcrumbs", breadcrumbs);
		model.addAttribute("metaRobots", "no-index,no-follow");
		return ControllerConstants.Views.Pages.Account.FMAccountAdminAddEditAddressPage;
	}

	@RequestMapping(value = "/admin-edit-address/" + ADDRESS_CODE_PATH_VARIABLE_PATTERN, method = RequestMethod.POST)
	@RequireHardLogIn
	public String adminEditAddress(@Valid final FMAddressForm fmaddressForm, final Model model,
			final RedirectAttributes redirectModel) throws CMSItemNotFoundException
	{

		model.addAttribute("metaRobots", "no-index,no-follow");
		LOG.info("INSIDE EDIT ADDRESS add id" + fmaddressForm.getAddressId());

		final AddressData newAddress = fmCustomerFacade.getAddressByID(fmaddressForm.getAddressId());

		LOG.info("***********AddressID::" + fmaddressForm.getAddressId() + "*****************line1:" + fmaddressForm.getLine1()
				+ "******Line2::" + fmaddressForm.getLine2() + "********" + fmaddressForm.getTownCity() + "*******contryISO"
				+ fmaddressForm.getCountryIso() + "***");
		newAddress.setLine1(fmaddressForm.getLine1());
		if (fmaddressForm.getLine2() != null)
		{
			newAddress.setLine2(fmaddressForm.getLine2());
		}
		newAddress.setTown(fmaddressForm.getTownCity());
		newAddress.setPostalCode(fmaddressForm.getPostcode());
		newAddress.setBillingAddress(false);
		newAddress.setShippingAddress(true);
		newAddress.setVisibleInAddressBook(true);

		final CountryData countryData = new CountryData();

		countryData.setIsocode(fmaddressForm.getCountryIso());
		newAddress.setCountry(countryData);

		final RegionData regionData = new RegionData();
		regionData.setIsocode(fmaddressForm.getRegion());

		newAddress.setRegion(regionData);

		if (Boolean.TRUE.equals(fmaddressForm.getDefaultAddress()) || userFacade.getAddressBook().size() <= 1)
		{
			newAddress.setDefaultAddress(true);
		}
		else
		{
			newAddress.setDefaultAddress(fmaddressForm.getDefaultAddress() != null
					&& fmaddressForm.getDefaultAddress().booleanValue());
		}
		fmCustomerFacade.editAdminAddress(newAddress);

		GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER,
				"text.account.addressBook.confirmationUpdated");
		return REDIRECT_TO_ADMIN_ADDRESS_BOOK_PAGE;
	}

	@RequestMapping(value = "/taxexemptionpage", method = RequestMethod.GET)
	public ModelAndView gettaxexemptionpage(final Model model) throws CMSItemNotFoundException
	{
		model.addAttribute("metaRobots", "no-index,no-follow");
		model.addAttribute("currentdata", fmCustomerFacade.getCurrentFMCustomer());
		model.addAttribute("taxdata", new TaxExemptionForm());
		model.addAttribute("regionsdatas", getI18NFacade().getRegionsForAllCountries().values());
		model.addAttribute("existingtaxdocs", fmCustomerFacade.getCurrentFMCustomer().getFmunit().getTaxDocumentList());
		storeCmsPageInModel(model, getContentPageForLabelOrId(FM_TAXEXEMPTION_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FM_TAXEXEMPTION_CMS_PAGE));
		return new ModelAndView(ControllerConstants.Views.Pages.Account.FmTaxExemptionPage);
	}

	@RequestMapping(value = "/taxexemption", method = RequestMethod.POST)
	public String dotaxexemption(@Valid @ModelAttribute("taxdata") final TaxExemptionForm taxexemptionform, final Model model)
			throws CMSItemNotFoundException
	{
		LOG.info("inside controller method for taxemeption");
		final FMCustomerData fmcustomerdata = fmCustomerFacade.getCurrentFMCustomer();
		if (taxexemptionform.getTaxDocument().isEmpty())
		{

			LOG.info("INSIDE if PART OF TAXEXEMTION");
			LOG.info("TAXDOC" + taxexemptionform.getTaxDocument().getOriginalFilename());
			GlobalMessages.addErrorMessage(model, "enter the document to uplaod");
			model.addAttribute("metaRobots", "no-index,no-follow");
			model.addAttribute("currentdata", fmCustomerFacade.getCurrentFMCustomer());
			model.addAttribute("taxdata", new TaxExemptionForm());
			model.addAttribute("regionsdatas", getI18NFacade().getRegionsForAllCountries().values());
			model.addAttribute("existingtaxdocs", fmCustomerFacade.getCurrentFMCustomer().getFmunit().getTaxDocumentList());
			storeCmsPageInModel(model, getContentPageForLabelOrId(FM_TAXEXEMPTION_CMS_PAGE));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FM_TAXEXEMPTION_CMS_PAGE));
			return ControllerConstants.Views.Pages.Account.FmTaxExemptionPage;
		}
		else
		{
			if (fmCustomerFacade.validateStateWiseTaxDocument(fmcustomerdata, taxexemptionform.getState()))
			{
				GlobalMessages.addErrorMessage(model, "Already Tax Document for this State exists");
				model.addAttribute("metaRobots", "no-index,no-follow");
				model.addAttribute("currentdata", fmCustomerFacade.getCurrentFMCustomer());
				model.addAttribute("taxdata", new TaxExemptionForm());
				model.addAttribute("regionsdatas", getI18NFacade().getRegionsForAllCountries().values());
				model.addAttribute("existingtaxdocs", fmCustomerFacade.getCurrentFMCustomer().getFmunit().getTaxDocumentList());
				storeCmsPageInModel(model, getContentPageForLabelOrId(FM_TAXEXEMPTION_CMS_PAGE));
				setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FM_TAXEXEMPTION_CMS_PAGE));
				return ControllerConstants.Views.Pages.Account.FmTaxExemptionPage;
			}
			else
			{
				LOG.info("INSIDE else PART OF TAXEXEMTION");
				LOG.info("TAXDOC" + taxexemptionform.getTaxDocument().getOriginalFilename());

				final MultipartFile file = taxexemptionform.getTaxDocument();
				final List<FMTaxDocumentData> taxdocss = new ArrayList<FMTaxDocumentData>();
				final FMTaxDocumentData fmtaxdoc = fmCustomerFacade.convertFileToFMTaxDocumentModel(file,
						WebConstants.CUST_UPLOAD_DOCS_FILE_PATH, WebConstants.DD_MM_YYYY);//convertFileToFMTaxDocumentModel(file);

				fmtaxdoc.setDocname(taxexemptionform.getState() + file.getOriginalFilename());
				final RegionData state = new RegionData();
				state.setIsocode(taxexemptionform.getState());
				fmtaxdoc.setState(state);
				LOG.info("STATE in controller after setting" + fmtaxdoc.getState().getIsocode());

				taxdocss.add(fmtaxdoc);
				fmcustomerdata.getFmunit().setTaxDocumentList(taxdocss);
				fmcustomerdata.getFmunit().setTaxID(taxexemptionform.getTaxID());

				fmCustomerFacade.taxexemption(fmcustomerdata);
				GlobalMessages.addInfoMessage(model, "document uploaded successfully");
				model.addAttribute("metaRobots", "no-index,no-follow");
				model.addAttribute("currentdata", fmCustomerFacade.getCurrentFMCustomer());
				model.addAttribute("taxdata", new TaxExemptionForm());
				model.addAttribute("regionsdatas", getI18NFacade().getRegionsForAllCountries().values());
				model.addAttribute("existingtaxdocs", fmCustomerFacade.getCurrentFMCustomer().getFmunit().getTaxDocumentList());
				storeCmsPageInModel(model, getContentPageForLabelOrId(FM_TAXEXEMPTION_CMS_PAGE));
				setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FM_TAXEXEMPTION_CMS_PAGE));
				return ControllerConstants.Views.Pages.Account.FmTaxExemptionPage;
			}
		}
	}

	@RequestMapping(value = "/taxexemption-approval", method = RequestMethod.GET)
	public String doTaxexemptionApproval(@RequestParam(value = "taxDocName", defaultValue = "") final String taxDocName,
			@RequestParam(value = "sortOption", defaultValue = "") final String sortOption, final Model model)
			throws CMSItemNotFoundException
	{
		LOG.info("searchTaxDocName :: " + taxDocName);
		LOG.info("inside controller method for doTaxexemptionApproval");
		LOG.info("INSIDE if PART OF TAXEXEMTION APPROVAL");

		final List<FMTaxDocumentModel> fmTaxDocumentsList = fmCustomerFacade.getAllTaxDocuments(taxDocName, sortOption);

		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getFMBreadcrumbs("text.account.taxexemptionapproval"));
		model.addAttribute("metaRobots", "no-index,no-follow");
		model.addAttribute("selectedLink", "TaxDocument");
		model.addAttribute("validateEnum", FmTaxValidationType.values());
		model.addAttribute("allUnitsTaxDocuments", fmTaxDocumentsList);
		model.addAttribute("searchTaxDocument", taxDocName);

		storeCmsPageInModel(model, getContentPageForLabelOrId(FM_TAXEXEMPTION_APPROVAL_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FM_TAXEXEMPTION_APPROVAL_CMS_PAGE));
		return ControllerConstants.Views.Pages.Account.FmTaxExemptionApprovalPage;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/update-taxexemption-approval/{taxDocApproval}/{taxDocPK}")
	public String doUpdateTaxexemptionApproval(@PathVariable("taxDocApproval") final FmTaxValidationType adminTaxDocApproval,
			@PathVariable("taxDocPK") final String taxDocPK, final Model model) throws CMSItemNotFoundException
	{
		LOG.info("inside controller method for doUpdateTaxexemptionApproval");
		LOG.info("INSIDE if PART OF UPDATE TAXEXEMTION APPROVAL");
		LOG.info("taxDocApproval selected value  :: " + adminTaxDocApproval.toString());
		LOG.info("taxDocPK value  :: " + taxDocPK);

		final boolean taxexemptionUpdateStatus = fmCustomerFacade.updateTaxExemptionApprovalForPK(taxDocPK, adminTaxDocApproval);

		final String updatedtaxDocPK = taxDocPK;

		LOG.info("taxexemptionUpdateStatus ::: " + taxexemptionUpdateStatus);

		final List<FMTaxDocumentModel> fmTaxDocumentsList = fmCustomerFacade.getAllTaxDocuments("", "ASC");

		LOG.info("Number of TaxDocuments :: " + fmTaxDocumentsList.size());

		final Iterator<FMTaxDocumentModel> taxdocsmodeliterator = fmTaxDocumentsList.iterator();
		while (taxdocsmodeliterator.hasNext())
		{
			final FMTaxDocumentModel fmTaxDocumentsModel = (FMTaxDocumentModel) taxdocsmodeliterator.next();


			LOG.info("Doc Name  :: " + fmTaxDocumentsModel.getDocname());
			LOG.info("State :: " + fmTaxDocumentsModel.getState().getIsocode());
			LOG.info("Validate :: " + fmTaxDocumentsModel.getValidate());
		}
		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getFMBreadcrumbs("text.account.taxexemptionapproval"));
		model.addAttribute("metaRobots", "no-index,no-follow");
		model.addAttribute("selectedLink", "TaxDocument");
		model.addAttribute("updatedtaxDocPK", updatedtaxDocPK);
		model.addAttribute("updateStatus", taxexemptionUpdateStatus);
		model.addAttribute("allUnitsTaxDocuments", fmTaxDocumentsList);
		storeCmsPageInModel(model, getContentPageForLabelOrId(FM_TAXEXEMPTION_APPROVAL_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FM_TAXEXEMPTION_APPROVAL_CMS_PAGE));
		return ControllerConstants.Views.Pages.Account.FmTaxExemptionApprovalPage;

	}

	@RequestMapping(value = "/filedownload/" + DOWNLOAD_TAX_DOC_NAME, method = RequestMethod.GET)
	public String dodownload(@PathVariable("taxdocname") final String taxdocname, final Model model)
			throws CMSItemNotFoundException
	{
		LOG.info("INSIDE FILEUPLOAD OF CONTROLLER");
		LOG.info("DOC NAME FROM JSP IN CONTROLLER" + taxdocname);

		if (fmCustomerFacade.downloadtaxdoc(taxdocname))
		{
			GlobalMessages.addInfoMessage(model, "document downloaded successfully to the path ");
			storeCmsPageInModel(model, getContentPageForLabelOrId(FM_TAXEXEMPTION_CMS_PAGE));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FM_TAXEXEMPTION_CMS_PAGE));

			return FORWARD_PREFIX + "/my-fmaccount/taxexemptionpage";
		}
		else
		{
			GlobalMessages.addErrorMessage(model, "some problem in downloading the document");

			model.addAttribute("metaRobots", "no-index,no-follow");
			model.addAttribute("currentdata", fmCustomerFacade.getCurrentFMCustomer());
			model.addAttribute("taxdata", new TaxExemptionForm());
			model.addAttribute("regionsdatas", getI18NFacade().getRegionsForAllCountries().values());
			storeCmsPageInModel(model, getContentPageForLabelOrId(FM_TAXEXEMPTION_CMS_PAGE));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FM_TAXEXEMPTION_CMS_PAGE));

			return FORWARD_PREFIX + "/my-fmaccount/taxexemptionpage";
		}
	}

	@RequestMapping(value = "/filedelete/" + DOWNLOAD_TAX_DOC_NAME, method = RequestMethod.GET)
	public String dodeletetaxdoc(@PathVariable("taxdocname") final String taxdocname, final Model model)
			throws CMSItemNotFoundException
	{
		LOG.info("INSIDE file delete OF CONTROLLER");
		LOG.info("DOC NAME FROM JSP IN CONTROLLER" + taxdocname);

		if (fmCustomerFacade.deletetaxdoc(taxdocname))
		{
			GlobalMessages.addInfoMessage(model, "document removed successfully ");
			storeCmsPageInModel(model, getContentPageForLabelOrId(FM_TAXEXEMPTION_CMS_PAGE));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FM_TAXEXEMPTION_CMS_PAGE));

			return FORWARD_PREFIX + "/my-fmaccount/taxexemptionpage";
		}
		else
		{
			GlobalMessages.addErrorMessage(model, "some problem in deleteing the document");

			model.addAttribute("metaRobots", "no-index,no-follow");
			model.addAttribute("currentdata", fmCustomerFacade.getCurrentFMCustomer());
			model.addAttribute("taxdata", new TaxExemptionForm());
			model.addAttribute("regionsdatas", getI18NFacade().getRegionsForAllCountries().values());
			storeCmsPageInModel(model, getContentPageForLabelOrId(FM_TAXEXEMPTION_CMS_PAGE));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FM_TAXEXEMPTION_CMS_PAGE));

			return FORWARD_PREFIX + "/my-fmaccount/taxexemptionpage";
		}
	}

	@RequestMapping(value = "/taxfiledownload/" + DOWNLOAD_TAX_DOC_PK, method = RequestMethod.GET)
	public String taxDocumentDownload(@PathVariable("pk") final String taxDocPK, final Model model)
			throws CMSItemNotFoundException, IOException
	{
		LOG.info("INSIDE taxDocumentDownload OF CONTROLLER");
		LOG.info("DOC NAME FROM JSP IN CONTROLLER" + taxDocPK);

		if (fmCustomerFacade.fmTaxDocumentFileDownload(taxDocPK))
		{
			GlobalMessages.addInfoMessage(model, "document downloaded successfully to the path ");
			storeCmsPageInModel(model, getContentPageForLabelOrId(FM_TAXEXEMPTION_APPROVAL_CMS_PAGE));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FM_TAXEXEMPTION_APPROVAL_CMS_PAGE));

			return FORWARD_PREFIX + "/my-fmaccount/taxexemption-approval";
		}
		else
		{
			GlobalMessages.addErrorMessage(model, "some problem in downloading the document");

			model.addAttribute("metaRobots", "no-index,no-follow");
			storeCmsPageInModel(model, getContentPageForLabelOrId(FM_TAXEXEMPTION_APPROVAL_CMS_PAGE));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FM_TAXEXEMPTION_APPROVAL_CMS_PAGE));

			return FORWARD_PREFIX + "/my-fmaccount/taxexemption-approval";
		}
	}

	//<!-- Balaji---Start Order Return --> 

	@RequestMapping(value = "/return-request/{orderNumber}", method = RequestMethod.GET)
	@RequireHardLogIn
	public String returnRequest(final Model model, @PathVariable("orderNumber") final String orderNumber)
			throws CMSItemNotFoundException
	{

		LOG.info("Inside returnRequest Method ==>");

		final FMCustomerData fmCustomerData = fmCustomerFacade.getCurrentFMCustomer();
		LOG.info(fmCustomerData);

		final String soldToAcc = (String) getSessionService().getAttribute(WebConstants.SOLDTO_ACCOUNT_ID);
		FMCustomerAccountModel fmCustomerAccModel = (FMCustomerAccountModel) companyB2BCommerceService.getUnitForUid(soldToAcc);
		final String shipToAcc = (String) getSessionService().getAttribute(WebConstants.SHIPTO_ACCOUNT_ID);
		final FMCustomerAccountModel sFmCustomerAccModel = (FMCustomerAccountModel) companyB2BCommerceService
				.getUnitForUid(shipToAcc);
		final List<AccountBO> billToAccounts = new ArrayList<AccountBO>();
		//Mahaveer JOBBER change
		final String loggedUserType = (String) getSessionService().getAttribute("logedUserType");
		if (loggedUserType != null && "ShipTo".equalsIgnoreCase(loggedUserType))
		{
			fmCustomerAccModel = sFmCustomerAccModel;
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
		final ShipToAcctBO shipToAccount = getShiptoAccount(sFmCustomerAccModel);
		final String customerPurchaseOrderNumber = "";
		final String masterOrderNumber = "";
		final Date startDate = getOrderSearchStartDate();
		final Date endDate = getOrderSearchEndDate();
		final OrderSearchFilter filter = OrderSearchFilter.ALL;
		final String orderRetrunFlag = "ORDERS";
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
			if (orderHeaderList != null && (!orderHeaderList.isEmpty()))
			{
				LOG.info("orderNumber ==>" + orderNumber);
				boolean found = false;
				for (final ShippedOrderBO shippedOrderBO : orderHeaderList)
				{
					for (final OrderUnitBO orderUnit : shippedOrderBO.getOrderUnitList())
					{
						if (orderUnit.getOrderNumber().equalsIgnoreCase(orderNumber))
						{
							orderHeardeService.getOrderDetail(shippedOrderBO, orderRetrunFlag);

							model.addAttribute("ReturnOrder", shippedOrderBO);
							LOG.info("\n invoiceDetails ......." + shippedOrderBO);
							found = true;
							break;
						}
					}
					if (found)
					{
						break;
					}
				}
			}
		}
		catch (final Exception e)
		{
			LOG.error(" Exception While Fetching Shipped Orders  " + e.getMessage());
			model.addAttribute("ReturnOrder", null);
		}

		model.addAttribute("fMReasonForReturn", getReturnReasonList());
		model.addAttribute("returnRequestFormData", new ReturnRequestForm());
		storeCmsPageInModel(model, getContentPageForLabelOrId(FMRETURN_ORDER_REQUEST_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FMRETURN_ORDER_REQUEST_CMS_PAGE));
		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getFMBreadcrumbs("text.account.returnrequest"));
		model.addAttribute("metaRobots", "no-index,no-follow");
		model.addAttribute("selectedLink", "ReturnRequest");
		return ControllerConstants.Views.Pages.Account.FMAccountReturnRequestPage;
	}

	public List<String> getReturnReasonList()
	{
		final List<String> fMReasonForReturn = new ArrayList<String>();

		fMReasonForReturn.add("Warranty Return");
		fMReasonForReturn.add("Warranty wLabour Claim");
		fMReasonForReturn.add("Shipping Discrepancy");
		fMReasonForReturn.add("Damaged");
		fMReasonForReturn.add("Obsolete");

		fMReasonForReturn.add("Return due to Late Delivery");
		fMReasonForReturn.add("Warranty Return Destroy in Field");
		fMReasonForReturn.add("Product Recall");
		fMReasonForReturn.add("Return Customer Error");
		fMReasonForReturn.add("Customer Service Error");

		return fMReasonForReturn;
	}

	@RequestMapping(value = "/return-request-confirmation", method = RequestMethod.POST)
	@RequireHardLogIn
	public String returnRequestConfirmation(
			@Valid @ModelAttribute("returnRequestFormData") final ReturnRequestForm returnRequestForm, final Model model)
			throws CMSItemNotFoundException
	{

		try
		{
			final FMReturnOrderData returnOrder = fmCustomerAccountReturnOrderFacade
					.createReturn(populateReturnCreationData(returnRequestForm));


			//final FMReturnOrderData returnOrder = fmCustomerAccountReturnOrderFacade.createReturn(populateReturnCreationTestData(returnRequestForm));

			if (!returnOrder.getReturnCode().isEmpty() && returnOrder.getReturnCode() == "000")
			{
				LOG.info(" ReturnId ==>" + returnOrder.getReturnId());
				LOG.info("ReturnCode ==>" + returnOrder.getReturnCode());
			}
			else if (!returnOrder.getReturnCode().isEmpty() && returnOrder.getReturnCode() == "001")
			{
				LOG.info("ReturnCode ==>" + returnOrder.getReturnCode());
			}


			model.addAttribute("returnOrder", returnOrder);

		}
		catch (final Exception e)
		{
			LOG.error("fmCustomerAccountReturnOrderFacade.populated() catch bloack");

		}

		storeCmsPageInModel(model, getContentPageForLabelOrId(FMRETURN_ORDER_REQUEST_CONFIRMATION_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FMRETURN_ORDER_REQUEST_CONFIRMATION_CMS_PAGE));
		model.addAttribute("returnRequestFormData", new ReturnRequestForm());
		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getFMBreadcrumbs("Return"));
		model.addAttribute("metaRobots", "no-index,no-follow");
		model.addAttribute("selectedLink", "ReturnRequest");
		return ControllerConstants.Views.Pages.Account.FMAccountReturnRequestConfirmationPage;
	}

	private FMReturnOrderData populateReturnCreationData(final ReturnRequestForm returnRequestForm)
	{
		final FMReturnOrderData fmReturnOrderData = new FMReturnOrderData();

		LOG.info("Inside populateReturnCreationData:");

		final ShippedOrderBO shippedOrderBO = new ShippedOrderBO();
		LOG.info("Inside populateReturnCreationData:" + shippedOrderBO);

		fmReturnOrderData.setConfirmationNumber(shippedOrderBO.getCustomerPurchaseOrderNum());
		LOG.info("Customer purchase order number:" + shippedOrderBO.getCustomerPurchaseOrderNum());
		fmReturnOrderData.setInvoiceNumber("34262");
		fmReturnOrderData.setSalesOrderNumber(shippedOrderBO.getCustomerPurchaseOrderNum());
		LOG.info("setSalesOrderNumber:" + shippedOrderBO.getCustomerPurchaseOrderNum());
		fmReturnOrderData.setReasonReturn(returnRequestForm.getReturnReason());
		LOG.info("setReasonReturn:" + returnRequestForm.getReturnReason());
		fmReturnOrderData.setReturnDescription(returnRequestForm.getReturnDescriptionform());
		LOG.info("getReturnDescriptionform:" + returnRequestForm.getReturnDescriptionform());
		fmReturnOrderData.setBillToAccount("10014254");
		fmReturnOrderData.setShiftToAccount("10014254");

		final List<FMReturnItemsData> fmReturnItems = new ArrayList<FMReturnItemsData>();
		LOG.info("fmReturnItems:" + fmReturnItems);

		final List<OrderUnitBO> orderUnitBO = shippedOrderBO.getOrderUnitList();
		LOG.info("orderUnitBO:" + orderUnitBO);

		if (orderUnitBO != null)
		{
			LOG.info("if (orderUnitBO != null):" + orderUnitBO);
			final Iterator<OrderUnitBO> i = orderUnitBO.iterator();
			while (i.hasNext())
			{
				final OrderUnitBO o = (OrderUnitBO) i.next();
				final List<ShippingUnitBO> list = o.getShippingUnitList();
				LOG.info("List<ShippingUnitBO> list:" + list);
				final Iterator<ShippingUnitBO> itr = list.iterator();
				while (itr.hasNext())
				{
					final ShippingUnitBO shipUnitBO = (ShippingUnitBO) itr.next();
					final List<ShippedItemBO> shippedItem = shipUnitBO.getShippedItemsList();
					LOG.info("List<ShippedItemBO> shippedItem:" + list);
					final Iterator<ShippedItemBO> itr1 = shippedItem.iterator();
					while (itr1.hasNext())
					{
						final ShippedItemBO bo = (ShippedItemBO) itr1.next();

						final FMReturnItemsData fmReturnItemsData1 = new FMReturnItemsData();
						LOG.info("fmReturnItemsData1:" + fmReturnItemsData1);

						fmReturnItemsData1.setItemDescription(bo.getDescription());
						LOG.info("bo.getDescription():" + bo.getDescription());
						fmReturnItemsData1.setPartNumber(bo.getDisplayPartNumber());
						LOG.info("bo.getDisplayPartNumber():" + bo.getDisplayPartNumber());
						fmReturnItemsData1.setReturnqty(returnRequestForm.getReturnQuantityform());
						LOG.info("returnRequestForm.getReturnQuantityform():" + returnRequestForm.getReturnQuantityform());

						fmReturnItems.add(fmReturnItemsData1);

					}
					LOG.info("before setting fmReturnItems to fmReturnOrderData");
					fmReturnOrderData.setReturnItems(fmReturnItems);
				}

			}
		}
		else
		{
			final FMReturnItemsData fmReturnItemsData1 = new FMReturnItemsData();

			fmReturnItemsData1.setItemDescription("High Performance Spark Plug - Boxed");
			fmReturnItemsData1.setPartNumber("CCH1010");
			fmReturnItemsData1.setReturnqty("1");

			fmReturnItems.add(fmReturnItemsData1);

			fmReturnOrderData.setReturnItems(fmReturnItems);
		}
		LOG.info("before return fmReturnOrderData");
		return fmReturnOrderData;
	}

	@RequestMapping(value = "/return-history", method = RequestMethod.GET)
	@RequireHardLogIn
	public String returnHistory(final Model model) throws CMSItemNotFoundException
	{

		LOG.info("Inside returnHistory Method ==>");

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
			model.addAttribute("logedUserType", "ShipTo");
		}

		final BillToAcctBO billToAccount = getBillTOAccount(fmCustomerAccModel);
		final String customerPurchaseOrderNumber = "";
		final String masterOrderNumber = "";
		final Date startDate = getOrderSearchStartDate();
		final Date endDate = getOrderSearchEndDate();
		final OrderSearchFilter filter = OrderSearchFilter.ALL;
		final String orderRetrunFlag = "RETURNS";
		List<ShippedOrderBO> returnOrderHeaderList = null;
		try
		{
			returnOrderHeaderList = SAPService.getShipmentInfo(billToAccount.getSapAccount().getSapAccountCode(), masterOrderNumber,
					customerPurchaseOrderNumber, startDate, endDate, filter, orderRetrunFlag);
			if (returnOrderHeaderList != null && (!returnOrderHeaderList.isEmpty()))
			{
				model.addAttribute("ReturnOrderHeaderStatus", returnOrderHeaderList);
				LOG.info(" Shipped Order after Details ... " + returnOrderHeaderList.size());
			}
		}
		catch (final Exception e)
		{
			LOG.error(" Exception While Fetching Shipped Orders from ECC" + e.getMessage());
			model.addAttribute("ReturnOrderHeaderStatus", null);
		}

		storeCmsPageInModel(model, getContentPageForLabelOrId(FMRETURN_HISTORY_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FMRETURN_HISTORY_CMS_PAGE));
		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getFMBreadcrumbs("Return History"));
		model.addAttribute("metaRobots", "no-index,no-follow");
		model.addAttribute("selectedLink", "ReturnHistory");
		return ControllerConstants.Views.Pages.Account.FMAccountReturnHistoryPage;
	}

	@RequestMapping(value = "/return-details", method = RequestMethod.GET)
	@RequireHardLogIn
	public String returnDetails(final Model model) throws CMSItemNotFoundException
	{

		LOG.info("Inside returnDetails Method ==>");


		storeCmsPageInModel(model, getContentPageForLabelOrId(FMRETURN_DETAILS_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FMRETURN_DETAILS_CMS_PAGE));
		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getFMBreadcrumbs("Return Details"));
		model.addAttribute("metaRobots", "no-index,no-follow");
		model.addAttribute("selectedLink", "ReturnDetails");
		return ControllerConstants.Views.Pages.Account.FMAccountReturnDetailsPage;
	}

	//<!-- Balaji---End Order Return -->


	@RequestMapping(value = "/order-history", method = {RequestMethod.POST, RequestMethod.GET})
	@RequireHardLogIn
	public String orderHistory(@ModelAttribute("orderSearchData") final OrderSearchForm orderSearchForm, final Model model)
			throws CMSItemNotFoundException
	{

		LOG.info("purchaseorderNUMBER" + orderSearchForm.getPurchaseOrderNumber());
		LOG.info("orderConfirmnationNUMBER" + orderSearchForm.getConfirmationOrderNumber());
		LOG.info("startdata" + orderSearchForm.getStartDate());
		LOG.info("enddata" + orderSearchForm.getEndDate());
		LOG.info("status" + orderSearchForm.getStatus());
		LOG.info("Inside order-history Method ==>");
		String userGroup = "";
		int noOfpages = 0;
		final List<String> groupUID = new ArrayList<String>();
		final UserModel user = userService.getCurrentUser();
		final Set<PrincipalGroupModel> groupss = user.getGroups();
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
		final String soldToAcc = (String) getSessionService().getAttribute(WebConstants.SOLDTO_ACCOUNT_ID);
		FMCustomerAccountModel fmCustomerAccModel = (FMCustomerAccountModel) companyB2BCommerceService.getUnitForUid(soldToAcc);
		final String shipToAcc = (String) getSessionService().getAttribute(WebConstants.SHIPTO_ACCOUNT_ID);
		final FMCustomerAccountModel sfmCustomerAccModel = (FMCustomerAccountModel) companyB2BCommerceService
				.getUnitForUid(shipToAcc);

		final List<AccountBO> billToAccounts = new ArrayList<AccountBO>();
		//Mahaveer JOBBER change
		final String loggedUserType = (String) getSessionService().getAttribute("logedUserType");
		LOG.info("loggedUserType ::" + loggedUserType);
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
			model.addAttribute("logedUserType", "ShipTo");
		}
		final BillToAcctBO billToAccount = getBillTOAccount(fmCustomerAccModel);
		final ShipToAcctBO shipToAccount = getShiptoAccount(sfmCustomerAccModel);
		String customerPurchaseOrderNumber = orderSearchForm.getPurchaseOrderNumber();
		if (customerPurchaseOrderNumber != null && !customerPurchaseOrderNumber.isEmpty())
		{
			customerPurchaseOrderNumber = customerPurchaseOrderNumber.trim();
		}
		String masterOrderNumber = orderSearchForm.getConfirmationOrderNumber();
		if (masterOrderNumber != null && !masterOrderNumber.isEmpty())
		{
			masterOrderNumber = masterOrderNumber.trim();
		}

		Date startDate = getOrderSearchStartDate();
		Date endDate = getOrderSearchEndDate();

		if (orderSearchForm.getStartDate() != null && orderSearchForm.getStartDate() != "")
		{
			startDate = UtilDate.getUtilDate(WebConstants.DD_MM_YYYY, orderSearchForm.getStartDate());
		}
		if (orderSearchForm.getEndDate() != null && orderSearchForm.getEndDate() != "")
		{
			endDate = UtilDate.getUtilDate(WebConstants.DD_MM_YYYY, orderSearchForm.getEndDate());
		}

		LOG.info(" startDate :: " + startDate);
		LOG.info("endDate  :: " + endDate);
		OrderSearchFilter filter = OrderSearchFilter.ALL;
		LOG.info("orderSearchForm.getStatus() :: " + orderSearchForm.getStatus());
		if (orderSearchForm.getStatus() != null && orderSearchForm.getStatus() != ""
				&& "IN_PROCESS".equals(orderSearchForm.getStatus()))
		{
			filter = OrderSearchFilter.IN_PROCESS;
		}
		else if (orderSearchForm.getStatus() != null && orderSearchForm.getStatus() != ""
				&& "COMPLETE".equals(orderSearchForm.getStatus()))
		{
			filter = OrderSearchFilter.COMPLETE;
		}

		final String orderRetrunFlag = "ORDERS";
		List<ShippedOrderBO> orderHeaderList = null;
		try
		{
			if (loggedUserType != null && "ShipTo".equalsIgnoreCase(loggedUserType))
			{
				LOG.info("loggedUserType is TRUE ::" + loggedUserType);
				orderHeaderList = orderHeardeService.searchOrders(billToAccounts, shipToAccount,
						masterOrderNumber != null ? masterOrderNumber.trim() : masterOrderNumber,
						customerPurchaseOrderNumber != null ? customerPurchaseOrderNumber.trim() : customerPurchaseOrderNumber,
						startDate, endDate, filter, orderRetrunFlag);
			}
			else
			{
				LOG.info("loggedUserType is FALSE ::" + loggedUserType);
				orderHeaderList = orderHeardeService.searchOrders(billToAccount, masterOrderNumber != null ? masterOrderNumber.trim()
						: masterOrderNumber, customerPurchaseOrderNumber != null ? customerPurchaseOrderNumber.trim()
						: customerPurchaseOrderNumber, startDate, endDate, filter, orderRetrunFlag);
			}
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
				noOfpages = orderStatusResult.size() / 20;
				getSessionService().setAttribute("sessionOrderStatusResult", orderStatusResult);
				getSessionService().setAttribute("sessionOrderStatusHelper", orderStatusHelper);
				getSessionService().setAttribute("sessionSortedOrderStatusResult", orderStatusResult);
				LOG.info("OrderHeaderList SIZE :: " + orderHeaderList.size());
				model.addAttribute("OrderHeaderStatus", orderHeaderList);
				model.addAttribute("orderStatusResult", orderStatusResult);

			}
		}
		catch (final Exception e)
		{
			LOG.error(" Exception While Fetching Shipped Orders  " + e.getMessage());
			model.addAttribute("OrderHeaderStatus", null);
		}

		storeCmsPageInModel(model, getContentPageForLabelOrId(FMORDER_DETAILS_CMS_PAGE));
		model.addAttribute("clickedlink", "clickedOrderHistory");
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FMORDER_DETAILS_CMS_PAGE));
		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getFMBreadcrumbs("Order History"));


		//Added by sai for FPT-99 issue display order history default 7 days range
		final Format formatter = new SimpleDateFormat("MM/dd/yyyy");
		getSessionService().setAttribute("order_startDate", formatter.format(startDate));
		getSessionService().setAttribute("order_endDate", formatter.format(endDate));
		getSessionService().setAttribute("order_shippedStatus", orderSearchForm.getStatus());

		model.addAttribute("startDate", getSessionService().getAttribute("order_startDate"));
		model.addAttribute("endDate", getSessionService().getAttribute("order_endDate"));
		model.addAttribute("order_status", getSessionService().getAttribute("order_shippedStatus"));
		model.addAttribute("userGroup", userGroup);
		model.addAttribute("begin", 0);
		model.addAttribute("end", 20);
		model.addAttribute("itemsCount", 20);
		model.addAttribute("page", 1);
		model.addAttribute("noOfpages", noOfpages);
		model.addAttribute("metaRobots", "no-index,no-follow");

		return ControllerConstants.Views.Pages.Account.FmOrderHistoryPage;
	}

	@RequestMapping(value = "/order-tracking/{orderNumber}/{packingSlipNumber}", method = RequestMethod.GET)
	@RequireHardLogIn
	public String orderTrackingDetails(final Model model, @PathVariable("orderNumber") final String orderNumber,
			@PathVariable("packingSlipNumber") final String packingSlipNumber) throws CMSItemNotFoundException
	{
		orderHistory(model, orderNumber, packingSlipNumber);

		storeCmsPageInModel(model, getContentPageForLabelOrId(FMORDER_HISTORY_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FMORDER_HISTORY_CMS_PAGE));
		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getFMBreadcrumbs("Order Details"));
		model.addAttribute("metaRobots", "no-index,no-follow");
		return ControllerConstants.Views.Pages.Account.fmOrderDetailsPage;
	}

	private void orderHistory(final Model model, final String orderNumber, final String packingNumber)
	{
		String userGroup = "";
		final List<String> groupUID = new ArrayList<String>();
		final UserModel user = userService.getCurrentUser();
		final Set<PrincipalGroupModel> groupss = user.getGroups();
		model.addAttribute("orderNumber", orderNumber);
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
		final String soldToAcc = (String) getSessionService().getAttribute(WebConstants.SOLDTO_ACCOUNT_ID);
		FMCustomerAccountModel fmCustomerAccModel = (FMCustomerAccountModel) companyB2BCommerceService.getUnitForUid(soldToAcc);
		final String shipToAcc = (String) getSessionService().getAttribute(WebConstants.SHIPTO_ACCOUNT_ID);
		final FMCustomerAccountModel sfmCustomerAccModel = (FMCustomerAccountModel) companyB2BCommerceService
				.getUnitForUid(shipToAcc);
		final List<AccountBO> billToAccounts = new ArrayList<AccountBO>();
		//Mahaveer JOBBER change
		final String loggedUserType = (String) getSessionService().getAttribute("logedUserType");
		if (loggedUserType != null && "ShipTo".equalsIgnoreCase(loggedUserType))
		{
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
		final String orderRetrunFlag = "ORDERS";
		final Map<String, ProductData> pictureMap = new HashMap<String, ProductData>();
		ProductModel productModel = null;
		ProductData productData = null;
		try
		{
			final List<OrderStatusResult> orderStatusResult = (List<OrderStatusResult>) getSessionService().getAttribute(
					"sessionOrderStatusResult");
			if (orderStatusResult != null && (!orderStatusResult.isEmpty()))
			{
				boolean found = false;

				for (final OrderStatusResult orderStatus : orderStatusResult)
				{
					final ShippedOrderBO shippedOrderBO = orderStatus.getShippedOrder().getShippedOrderBO();
					for (final OrderUnitBO orderUnit : shippedOrderBO.getOrderUnitList())
					{
						if (orderUnit.getOrderNumber().equalsIgnoreCase(orderNumber))
						{
							orderHeardeService.getOrderDetail(shippedOrderBO, orderRetrunFlag);
							shippedOrderBO.setBillToAccount(billToAccount);

							final List<ShippingUnitBO> packingShippingUnitBO = new ArrayList<ShippingUnitBO>();
							for (final ShippingUnitBO shippingUnitBO : orderUnit.getShippingUnitList())
							{
								if (null != packingNumber && packingNumber.equalsIgnoreCase(shippingUnitBO.getPackingSlip()))
								{
									packingShippingUnitBO.add(shippingUnitBO);
								}
							
							}
							if (null != packingNumber && !packingShippingUnitBO.isEmpty())
							{
								orderUnit.setShippingUnitList(packingShippingUnitBO);
							}
							model.addAttribute("OrderStatusDetail", shippedOrderBO);

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
									LOG.info("shipToAccountCode :: " + shipToNabsAccountCode + "/" + shipToECCAccountCode);
									model.addAttribute("shipToAccountCode", shipToNabsAccountCode + "/" + shipToECCAccountCode);
								}
							}

							model.addAttribute("pictureMap", pictureMap);
							found = true;
							uploadorderFacade.createReportLog(sfmCustomerAccModel,(fmUserSearchDAO.getUserForUID(fmCustomerFacade.getCurrentFMCustomer().getUid())), "ORDERSTATUS");break;
						}
					}
					
					if (found)
					{
						break;
					}
				}
			}
		}
		catch (final Exception e)
		{
			LOG.error(" Exception While Fetching Shipped Orders  " + e.getMessage());
			model.addAttribute("OrderHeaderStatus", null);
		}
		model.addAttribute("userGroup", userGroup);
	}

	@RequestMapping(value = "/order-details/{orderNumber}", method = RequestMethod.GET)
	@RequireHardLogIn
	public String orderDetails(final Model model, @PathVariable("orderNumber") final String orderNumber)
			throws CMSItemNotFoundException
	{
		orderHistory(model, orderNumber, null);

		storeCmsPageInModel(model, getContentPageForLabelOrId(FMORDER_HISTORY_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FMORDER_HISTORY_CMS_PAGE));
		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getFMOrderDetailsBreadcrumbs("Order Details"));
		model.addAttribute("metaRobots", "no-index,no-follow");
		return ControllerConstants.Views.Pages.Account.fmOrderDetailsPage;
	}



	@RequestMapping(value = "/reset-order", method = RequestMethod.GET)
	@RequireHardLogIn
	public String resetQuickOrder(final Model model) throws CMSItemNotFoundException
	{
		LOG.info("Inside reset Quick Order Method ==>");
		cartService.removeSessionCart();
		storeCmsPageInModel(model, getContentPageForLabelOrId(FMORDER_HISTORY_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FMORDER_HISTORY_CMS_PAGE));
		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getFMBreadcrumbs("Quick Order"));
		model.addAttribute("metaRobots", "no-index,no-follow");
		model.addAttribute("quickOrderUpload", new QuickOrderUploadForm());
		model.addAttribute("cartData", null);
		return ControllerConstants.Views.Pages.Account.FMQuickOrderPage;
	}

	@RequestMapping(value = "/return-order-history/" + CSR_ACCOUNT_UID, method = RequestMethod.GET)
	@RequireHardLogIn
	public String returnOrderHistory(@PathVariable("unitUid") final String accountId, final Model model)
			throws CMSItemNotFoundException
	{
		LOG.info("Inside return-order-history Method ==>");
		LOG.info("account from linkkkkkkkkkkk" + accountId);
		final FMCustomerAccountData accountData = fmNetworkFacade.getAccountID(accountId);

		final UserModel currentUserModel = userService.getCurrentUser();

		final FMCustomerModel fmCustomerModel = fmNetworkService.getFMCustomerForUid(fmCustomerFacade.getCurrentFMCustomer()
				.getUid());

		LOG.info("FMCUSTOMER NAME" + fmCustomerModel.getUid());
		LOG.info("LISTSIZE" + fmCustomerModel.getCsrAccountEntry().size());
		final List<FMCsrAccountListModel> csrAccountList = fmCustomerModel.getCsrAccountEntry();
		getSessionService().setAttribute(SessionContext.USER, userService.getAdminUser());

		final FMCsrAccountListModel csrm = new FMCsrAccountListModel();
		csrm.setAccountnum(accountId);
		csrm.setDate(new Date());
		csrm.setCsrUserID(currentUserModel.getUid());

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

		//sreedevi added for B2B routing
		LOG.info("DHFDJHFDFD");
		final Set<PrincipalModel> groups = companyB2BCommerceService.getUnitForUid(accountData.getUid()).getMembers();
		LOG.info("SIZE" + groups.size());
		final Iterator<PrincipalModel> i = groups.iterator();
		while (i.hasNext())
		{
			final PrincipalModel memberUnit = (PrincipalModel) i.next();
			LOG.info("b4444444444444444444444444444444444444444 if");
			final Set<PrincipalGroupModel> groupss = memberUnit.getGroups();
			for (final PrincipalGroupModel groupModel : groupss)
			{
				final String groupId = groupModel.getUid();
				LOG.info("Group DEtails :: " + groupId);
				if ("FMB2BB".equals(groupId))
				{
					LOG.info("hello inside if loop");
					LOG.info("CSR Emulated As  Account :: " + accountData.getUid());
					final List<FMCustomerAccountData> checkUnitUid = fmCustomerFacade.getSoldToShipToUnitUid(accountData.getUid());
					if (checkUnitUid.size() > CHECKRESULTSIZE)
					{
						final Iterator<FMCustomerAccountData> uidIterator = checkUnitUid.iterator();
						while (uidIterator.hasNext())
						{
							final FMCustomerAccountData unitUidData = (FMCustomerAccountData) uidIterator.next();

							if (unitUidData.getUid().equals(accountData.getUid()))
							{
								// if logged in as sold to
								LOG.info("CSR Emulated As SOLD TO Account :: " + accountData.getUid());
								final Map<FMCustomerAccountData, List<FMB2bAddressData>> unitAddressMap = new HashMap<FMCustomerAccountData, List<FMB2bAddressData>>();

								final List<FMB2bAddressData> unitAddress = accountData.getUnitAddress();
								unitAddressMap.put(accountData, unitAddress);



								final List<Map<FMCustomerAccountData, List<FMB2bAddressData>>> unitAddressMapMultipleSoldTo = new ArrayList<Map<FMCustomerAccountData, List<FMB2bAddressData>>>();

								final List<FMCustomerAccountData> soldTos = fmCustomerFacade.getMultipleSoldTosCSR(accountData);

								if (soldTos.size() > 0)
								{

									LOG.info("SIZE OF  >>>>>>>>>>>>>>>>>" + soldTos.size());
									final Iterator<FMCustomerAccountData> soldtoIterator = soldTos.iterator();
									while (soldtoIterator.hasNext())
									{
										final FMCustomerAccountData soldtoData = (FMCustomerAccountData) soldtoIterator.next();
										final Map<FMCustomerAccountData, List<FMB2bAddressData>> soldtoAddressMap = new HashMap<FMCustomerAccountData, List<FMB2bAddressData>>();
										soldtoAddressMap.put(soldtoData, soldtoData.getUnitAddress());
										unitAddressMapMultipleSoldTo.add(soldtoAddressMap);

										final List<FMB2bAddressData> fdfdf = soldtoData.getUnitAddress();
										final Iterator<FMB2bAddressData> fhggfh = fdfdf.iterator();
										while (fhggfh.hasNext())
										{
											final FMB2bAddressData hfd = (FMB2bAddressData) fhggfh.next();
											LOG.info("LINE111>>>>....>" + hfd.getLine1());
											LOG.info("LINE2222>>>>..>..." + hfd.getLine2());
											LOG.info("LINE111>>....>" + hfd.getCountry().getIsocode());
										}

									}

									model.addAttribute("insideMulitpleSoldTo", new String("multiple"));
								}

								final List<FMCustomerAccountData> members = fmCustomerFacade.getPartnerAddressCSR(accountData);
								final List<Map<FMCustomerAccountData, List<FMB2bAddressData>>> listmemberAddressMap = new ArrayList<Map<FMCustomerAccountData, List<FMB2bAddressData>>>();
								final Iterator<FMCustomerAccountData> memberIterator = members.iterator();
								while (memberIterator.hasNext())
								{
									final FMCustomerAccountData memberData = (FMCustomerAccountData) memberIterator.next();
									final Map<FMCustomerAccountData, List<FMB2bAddressData>> memberAddressMap = new HashMap<FMCustomerAccountData, List<FMB2bAddressData>>();
									memberAddressMap.put(memberData, memberData.getUnitAddress());
									listmemberAddressMap.add(memberAddressMap);
								}
								//siva for adding soldto's to ship to's
								final Map<FMCustomerAccountData, List<FMB2bAddressData>> soldtoMemberforShipto = new HashMap<FMCustomerAccountData, List<FMB2bAddressData>>();
								soldtoMemberforShipto.put(accountData, unitAddress);
								listmemberAddressMap.add(soldtoMemberforShipto);
								if (soldTos.size() > 0)
								{
									listmemberAddressMap.addAll(unitAddressMapMultipleSoldTo);
								}
								//siva for adding soldto's to ship to's ends here

								//For getting Sold to's country code
								String getCountryIso = null;
								final Iterator<FMB2bAddressData> unitAddressIterator = unitAddress.iterator();
								while (unitAddressIterator.hasNext())
								{
									FMB2bAddressData countryIsocode = new FMB2bAddressData();
									countryIsocode = (FMB2bAddressData) unitAddressIterator.next();
									getCountryIso = countryIsocode.getCountry().getIsocode();
									LOG.info("Country Isocode :: " + getCountryIso);
								}


								LOG.info("Default soldto id" + unitAddressMap.entrySet().iterator().next().getKey().getUid());
								LOG.info("Default sold to address" + unitAddressMap.entrySet().iterator().next().getValue());

								FMCustomerAccountData shiptokey = new FMCustomerAccountData();
								List<FMB2bAddressData> shiptovalue = new ArrayList<FMB2bAddressData>();
								for (final Map<FMCustomerAccountData, List<FMB2bAddressData>> map : listmemberAddressMap)
								{
									for (final Map.Entry<FMCustomerAccountData, List<FMB2bAddressData>> entry : map.entrySet())
									{
										shiptokey = entry.getKey();
										shiptovalue = entry.getValue();
										break;
									}
									break;
								}

								LOG.info("First entry in shipto" + shiptokey + " " + shiptokey.getUid());
								LOG.info("First entry address in shipto" + shiptovalue + " " + shiptovalue.iterator().next().getLine1());

								//adding to session
								LOG.info("SOLD TO before:: getSessionService().getAttribute(WebConstants.SOLDTO_ACCOUNT_ID){}"
										+ getSessionService().getAttribute(WebConstants.SOLDTO_ACCOUNT_ID));
								LOG.info("SOLD TO before:: getSessionService().getAttribute(WebConstants.SHIPTO_ACCOUNT_ID) {}"
										+ getSessionService().getAttribute(WebConstants.SHIPTO_ACCOUNT_ID));
								if (getSessionService().getAttribute(WebConstants.SOLDTO_ACCOUNT_ID) == null
										|| getSessionService().getAttribute(WebConstants.SHIPTO_ACCOUNT_ID) == null)
								{
									getSessionService().setAttribute(WebConstants.SOLDTO_ACCOUNT_ID, accountData.getUid());
									getSessionService().setAttribute(WebConstants.SHIPTO_ACCOUNT_ID, shiptokey.getUid());
								}
								LOG.info("SOLD TO after:: getSessionService().getAttribute(WebConstants.SOLDTO_ACCOUNT_ID){}"
										+ getSessionService().getAttribute(WebConstants.SOLDTO_ACCOUNT_ID));
								LOG.info("SOLD TO after:: getSessionService().getAttribute(WebConstants.SHIPTO_ACCOUNT_ID) {}"
										+ getSessionService().getAttribute(WebConstants.SHIPTO_ACCOUNT_ID));
								model.addAttribute("country", getI18NFacade().getCountryForIsocode(getCountryIso));
								model.addAttribute("regionData", getI18NFacade().getRegionsForCountryIso(getCountryIso));
								model.addAttribute("addressForm", new FMAddressForm());

								model.addAttribute("SoldToAccount", unitAddressMap);
								model.addAttribute("ShipToAccount", listmemberAddressMap);
								model.addAttribute("MultipleSoldToAccount", unitAddressMapMultipleSoldTo);
								model.addAttribute("Shipto_defaultaccount", shiptokey);
								model.addAttribute("Shipto_defaultaddress", shiptovalue);
								LOG.info("NABSACC" + accountData.getNabsAccountCode());
								model.addAttribute("nabsAccCode", accountData.getNabsAccountCode());
								model.addAttribute("ifType", new String("SoldTo"));
								getSessionService().setAttribute("logedUserType", "SoldTo");

								break;
							}
							else
							{ //if logged in as ship to
								LOG.info("CSR Emulated As SHIP TO Account :: " + accountData.getUid());
								final List<FMCustomerAccountData> parentunit = fmCustomerFacade.getSoldToShipToUnitUid(accountData
										.getUid());
								final List<Map<FMCustomerAccountData, List<FMB2bAddressData>>> parentAddressMapList = new ArrayList<Map<FMCustomerAccountData, List<FMB2bAddressData>>>();
								String getCountryIso = null;
								final Iterator<FMCustomerAccountData> i1 = parentunit.iterator();
								while (i1.hasNext())
								{
									final FMCustomerAccountData unitData = (FMCustomerAccountData) i1.next();

									final List<FMB2bAddressData> unitAddress = unitData.getUnitAddress();

									final Iterator<FMB2bAddressData> additerator = unitAddress.iterator();
									while (additerator.hasNext())
									{
										AddressData countryIsocode = new AddressData();
										countryIsocode = (AddressData) additerator.next();
										getCountryIso = countryIsocode.getRegion().getCountryIso();
										LOG.info("Country Isocode :: " + getCountryIso);
									}
									final Map<FMCustomerAccountData, List<FMB2bAddressData>> parentAddressMap = new HashMap<FMCustomerAccountData, List<FMB2bAddressData>>();
									parentAddressMap.put(unitData, unitAddress);
									parentAddressMapList.add(parentAddressMap);
								}
								final Map<FMCustomerAccountData, List<FMB2bAddressData>> unitAddressMap = new HashMap<FMCustomerAccountData, List<FMB2bAddressData>>();
								unitAddressMap.put(accountData, accountData.getUnitAddress());

								//for adding soldto's to shipto's
								final Iterator<Map<FMCustomerAccountData, List<FMB2bAddressData>>> soldtosIterator = parentAddressMapList.iterator();
								while (soldtosIterator.hasNext())
								{
									final Map<FMCustomerAccountData, List<FMB2bAddressData>> soldto = (Map<FMCustomerAccountData, List<FMB2bAddressData>>) soldtosIterator
											.next();
									unitAddressMap.putAll(soldto);
								}

								FMCustomerAccountData soldtoKey = new FMCustomerAccountData();

								List<FMB2bAddressData> soldtoValue = new ArrayList<FMB2bAddressData>();
								for (final Map<FMCustomerAccountData, List<FMB2bAddressData>> map : parentAddressMapList)
								{
									for (final Map.Entry<FMCustomerAccountData, List<FMB2bAddressData>> entry : map.entrySet())
									{
										soldtoKey = entry.getKey();
										soldtoValue = entry.getValue();
										break;
									}
									break;
								}

								LOG.info("First entry in soldto" + soldtoKey + " " + soldtoKey.getUid());
								LOG.info("First entry address in soldto" + soldtoValue + " " + soldtoValue.iterator().next().getLine1());

								//adding to session
								LOG.info("Ship TO before:: getSessionService().getAttribute(WebConstants.SOLDTO_ACCOUNT_ID){}"
										+ getSessionService().getAttribute(WebConstants.SOLDTO_ACCOUNT_ID));
								LOG.info("Ship TO before:: getSessionService().getAttribute(WebConstants.SHIPTO_ACCOUNT_ID) {}"
										+ getSessionService().getAttribute(WebConstants.SHIPTO_ACCOUNT_ID));
								if (getSessionService().getAttribute(WebConstants.SOLDTO_ACCOUNT_ID) == null
										|| getSessionService().getAttribute(WebConstants.SHIPTO_ACCOUNT_ID) == null)
								{
									getSessionService().setAttribute(WebConstants.SOLDTO_ACCOUNT_ID, soldtoKey.getUid());
									getSessionService().setAttribute(WebConstants.SHIPTO_ACCOUNT_ID, accountData.getUid());
								}

								LOG.info("Ship TO after:: getSessionService().getAttribute(WebConstants.SOLDTO_ACCOUNT_ID){}"
										+ getSessionService().getAttribute(WebConstants.SOLDTO_ACCOUNT_ID));
								LOG.info("Ship TO after:: getSessionService().getAttribute(WebConstants.SHIPTO_ACCOUNT_ID) {}"
										+ getSessionService().getAttribute(WebConstants.SHIPTO_ACCOUNT_ID));
								model.addAttribute("Soldto_defaultaccount", soldtoKey);
								model.addAttribute("Soldto_defaultaddress", soldtoValue);
								model.addAttribute("SoldToAccount", parentAddressMapList);
								model.addAttribute("ShipToAccount", unitAddressMap);
								model.addAttribute("Shipto_defaultaccount", accountData);
								model.addAttribute("Shipto_defaultaddress", accountData.getUnitAddress());
								model.addAttribute("country", getI18NFacade().getCountryForIsocode(getCountryIso));
								model.addAttribute(
										"regionData",
										getI18NFacade().getRegionsForCountryIso(
												accountData.getUnitAddress().iterator().next().getCountry().getIsocode()));
								model.addAttribute("addressForm", new FMAddressForm());
								model.addAttribute("nabsAccCode", soldtoKey.getNabsAccountCode());
								model.addAttribute("ifType", new String("ShipTo"));
								getSessionService().setAttribute("logedUserType", "ShipTo");
							}
						}
					}
					else
					{

						final Map<FMCustomerAccountData, List<FMB2bAddressData>> unitAddressMap = new HashMap<FMCustomerAccountData, List<FMB2bAddressData>>();

						final List<FMB2bAddressData> unitAddress = accountData.getUnitAddress();
						unitAddressMap.put(accountData, unitAddress);

						model.addAttribute("SoldToAccount", unitAddressMap);
						model.addAttribute("ShipToAccount", unitAddressMap);
						model.addAttribute(
								"regionData",
								getI18NFacade().getRegionsForCountryIso(
										accountData.getUnitAddress().iterator().next().getCountry().getIsocode()));
						model.addAttribute("ifType", new String("SoldTo&ShipTo"));
						getSessionService().setAttribute("logedUserType", "SoldTo");
						getSessionService().setAttribute(WebConstants.SOLDTO_ACCOUNT_ID, accountData.getUid());
						getSessionService().setAttribute(WebConstants.SHIPTO_ACCOUNT_ID, accountData.getUid());

						model.addAttribute("addressForm", new FMAddressForm());
						model.addAttribute("nabsAccCode", accountData.getNabsAccountCode());

						LOG.info("Inside Else loop");
					}

					storeCmsPageInModel(model, getContentPageForLabelOrId("FMB2BHomePage"));
					setUpMetaDataForContentPage(model, getContentPageForLabelOrId("FMB2BHomePage"));
					updatePageTitle(model, getContentPageForLabelOrId("FMB2BHomePage"));
					model.addAttribute("customerType", "b2BCustomer");
					model.addAttribute("orderUpload", new OrderUploadForm());
					model.addAttribute("addressForm", new FMAddressForm());
					return ControllerConstants.Views.Pages.Account.AccountBToBigB;
				}
			}
		}

		if (groups.size() == 0)
		{
			LOG.info("Inside soldto without shipto");
			final Map<FMCustomerAccountData, List<FMB2bAddressData>> unitAddressMap = new HashMap<FMCustomerAccountData, List<FMB2bAddressData>>();

			final List<FMB2bAddressData> unitAddress = accountData.getUnitAddress();
			unitAddressMap.put(accountData, unitAddress);

			model.addAttribute("SoldToAccount", unitAddressMap);
			model.addAttribute("ShipToAccount", unitAddressMap);
			model.addAttribute("regionData",
					getI18NFacade().getRegionsForCountryIso(accountData.getUnitAddress().iterator().next().getCountry().getIsocode()));
			model.addAttribute("ifType", new String("SoldTo&ShipTo"));
			getSessionService().setAttribute(WebConstants.SOLDTO_ACCOUNT_ID, accountData.getUid());
			getSessionService().setAttribute(WebConstants.SHIPTO_ACCOUNT_ID, accountData.getUid());

			model.addAttribute("addressForm", new FMAddressForm());
			model.addAttribute("nabsAccCode", accountData.getNabsAccountCode());

			storeCmsPageInModel(model, getContentPageForLabelOrId("FMB2BHomePage"));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId("FMB2BHomePage"));
			updatePageTitle(model, getContentPageForLabelOrId("FMB2BHomePage"));
			model.addAttribute("customerType", "b2BCustomer");
			model.addAttribute("orderUpload", new OrderUploadForm());
			model.addAttribute("addressForm", new FMAddressForm());
			getSessionService().setAttribute("logedUserType", "SoldTo");
			return ControllerConstants.Views.Pages.Account.AccountBToBigB;
		}

		//Sreedevi end

		final String soldToAcc = (String) getSessionService().getAttribute(WebConstants.SOLDTO_ACCOUNT_ID);
		FMCustomerAccountModel fmCustomerAccModel = (FMCustomerAccountModel) companyB2BCommerceService.getUnitForUid(soldToAcc);
		final String shipToAcc = (String) getSessionService().getAttribute(WebConstants.SHIPTO_ACCOUNT_ID);
		final FMCustomerAccountModel sfmCustomerAccModel = (FMCustomerAccountModel) companyB2BCommerceService
				.getUnitForUid(shipToAcc);
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
		final BillToAcctBO billToAccount = getBillTOAccount(fmCustomerAccModel);
		final ShipToAcctBO shipToAccount = getShiptoAccount(sfmCustomerAccModel);
		final String customerPurchaseOrderNumber = "";
		final String masterOrderNumber = "";
		final Date startDate = getOrderSearchStartDate();
		final Date endDate = getOrderSearchEndDate();
		final OrderSearchFilter filter = OrderSearchFilter.ALL;
		final String orderRetrunFlag = "ORDERS";
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
				model.addAttribute("OrderHeaderStatus", orderHeaderList);
				LOG.info(" Shipped Order after Details ... " + orderHeaderList.size());
			}
		}
		catch (final Exception e)
		{
			LOG.error(" Exception While Fetching Shipped Orders  " + e.getMessage());
			model.addAttribute("OrderHeaderStatus", null);
		}

		storeCmsPageInModel(model, getContentPageForLabelOrId(FMORDER_HISTORY_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FMORDER_HISTORY_CMS_PAGE));
		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getFMBreadcrumbs("Order History"));
		model.addAttribute("metaRobots", "no-index,no-follow");
		model.addAttribute("orderSearchData", new OrderSearchForm());

		model.addAttribute("comment", accountData.getAccComments());
		model.addAttribute("comment1", accountData.getAccComments2());
		return ControllerConstants.Views.Pages.Account.FmOrderHistoryPage;
	}

	protected void updatePageTitle(final Model model, final AbstractPageModel cmsPage)
	{
		storeContentPageTitleInModel(model, getPageTitleResolver().resolveHomePageTitle(cmsPage.getTitle()));
	}

	@RequestMapping(value = "/get-accounts", method = RequestMethod.POST)
	@RequireHardLogIn
	public String getAccounts(@Valid final FMAccountSearchForm fmAccountSearchForm, final Model model)
			throws CMSItemNotFoundException
	{
		final String accountId = fmAccountSearchForm.getAccountNumber();
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

			final Iterator<FMCustomerAccountData> i = listaccData.iterator();
			while (i.hasNext())
			{
				final FMCustomerAccountData accData = (FMCustomerAccountData) i.next();

				final List<AddressData> address = accData.getAddresses();
				final Iterator<AddressData> a = address.iterator();
				while (a.hasNext())
				{
					final AddressData add = (AddressData) a.next();
					LOG.info("add" + add);
				}
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
		return ControllerConstants.Views.Pages.Account.AccountCSR;

	}

	//sreedevi - for new address 
	@RequestMapping(value = "/add-address-tosession", method = RequestMethod.POST)
	@RequireHardLogIn
	public String addAddresstoSession(final FMAddressForm fmaddressForm, final Model model)
	{
		final AddressData newAddress = new AddressData();
		newAddress.setFirstName(fmaddressForm.getFirstName() + " ");
		newAddress.setLine1(fmaddressForm.getLine1());
		newAddress.setLine2(fmaddressForm.getLine2());
		newAddress.setTown(fmaddressForm.getTownCity());
		newAddress.setPostalCode(fmaddressForm.getPostcode());
		newAddress.setPhone(fmaddressForm.getContactNo());

		final CountryModel country = commonI18NService.getCountry(fmaddressForm.getRegion().split("-")[0]);
		final RegionModel region = commonI18NService.getRegion(country, fmaddressForm.getRegion());


		final CountryData countryData = new CountryData();
		countryData.setIsocode(country.getIsocode());
		newAddress.setCountry(countryData);

		final RegionData regionData = new RegionData();

		regionData.setIsocode(region.getIsocode());
		regionData.setIsocodeShort(region.getIsocodeShort());
		newAddress.setRegion(regionData);

		getSessionService().setAttribute("shiptToAddress", newAddress);
		model.addAttribute("orderUpload", new OrderUploadForm());
		model.addAttribute("NewShiptToAddress", newAddress);

		final String unitAddress = newAddress.getFirstName() + "<br>" + newAddress.getLine1()
				+ (null != newAddress.getLine2() ? "<br>" + newAddress.getLine2() : "") + "<br>" + newAddress.getTown() + ",&nbsp;"
				+ newAddress.getRegion().getIsocodeShort() + "&nbsp;" + newAddress.getPostalCode() + "&nbsp;"
				+ newAddress.getCountry().getIsocode() + "<br> Phone :" + newAddress.getPhone();//+ "<br>"

		getSessionService().removeAttribute(WebConstants.USER_SHIPTO_FULL_ADDRESS);
		getSessionService().setAttribute(WebConstants.USER_SHIPTO_FULL_ADDRESS, unitAddress);
		model.addAttribute(WebConstants.USER_SHIPTO_FULL_ADDRESS, unitAddress);

		return "pages/fm/ajax/userNewSessionAddress";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/change-shipto-soldto-session/{accountId}/{shipToSoldToFlag}/{nabsAccountCode}")
	public String changeShipToSoldToSession(@PathVariable("accountId") final String accountId,
			@PathVariable("shipToSoldToFlag") final String shipToSoldToFlag,
			@PathVariable("nabsAccountCode") String nabsAccountCode, final Model model)
	{
		LOG.info("changeShipToSoldToSession ################## ");
		LOG.info("shipto acc id :: " + accountId);
		LOG.info("shipToSoldToFlag :: " + shipToSoldToFlag);
		//fix -Mahaveer A
		if (null != nabsAccountCode && nabsAccountCode.equalsIgnoreCase("dummy"))
		{
			nabsAccountCode = "";
		}
		B2BUnitData b2bUnitData = null;
		if ("shipTo".equals(shipToSoldToFlag))
		{
			getSessionService().setAttribute("shiptToAddress", null);
			LOG.info("OLD SESSION VALUE SOLDTO" + getSessionService().getAttribute(WebConstants.SOLDTO_ACCOUNT_ID));
			LOG.info("OLD SESSION VALUE SHIPTO" + getSessionService().getAttribute(WebConstants.SHIPTO_ACCOUNT_ID));
			LOG.info("############ ShipTO Clickede ##################");
			getSessionService().removeAttribute(WebConstants.SHIPTO_ACCOUNT_ID);
			getSessionService().setAttribute(WebConstants.SHIPTO_ACCOUNT_ID, accountId);
			getSessionService().setAttribute(WebConstants.SOLDTO_ACCOUNT_ID,
					getSessionService().getAttribute(WebConstants.SOLDTO_ACCOUNT_ID));
			b2bUnitData = companyB2BCommerceFacade.getUnitForUid(accountId);
			String unitAddress = null;
			for (final AddressData shipToAddress : b2bUnitData.getAddresses())
			{
				unitAddress = b2bUnitData.getName() + "<br>" + shipToAddress.getLine1() + "<br>" + shipToAddress.getTown()
						+ ",&nbsp;" + shipToAddress.getRegion().getIsocodeShort() + "&nbsp;" + shipToAddress.getPostalCode() + "&nbsp;"
						+ shipToAddress.getCountry().getIsocode() + "<br>" + b2bUnitData.getUid() + "/" + nabsAccountCode;
			}
			getSessionService().removeAttribute(WebConstants.USER_SHIPTO_FULL_ADDRESS);
			getSessionService().setAttribute(WebConstants.USER_SHIPTO_FULL_ADDRESS, unitAddress);
			model.addAttribute(WebConstants.USER_SHIPTO_FULL_ADDRESS, unitAddress);
			LOG.info("NEW SESSION VALUE SOLDTO" + getSessionService().getAttribute(WebConstants.SOLDTO_ACCOUNT_ID));
			LOG.info("NEW SESSION VALUE SHIPTO" + getSessionService().getAttribute(WebConstants.SHIPTO_ACCOUNT_ID));

		}
		else if ("soldTo".equals(shipToSoldToFlag))
		{
			b2bUnitData = companyB2BCommerceFacade.getUnitForUid(accountId);
			String unitAddress = null;
			for (final AddressData shipToAddress : b2bUnitData.getAddresses())
			{
				unitAddress = b2bUnitData.getName() + "<br>" + shipToAddress.getLine1() + "<br>" + shipToAddress.getTown()
						+ ",&nbsp;" + shipToAddress.getRegion().getIsocodeShort() + "&nbsp;" + shipToAddress.getPostalCode() + "&nbsp;"
						+ shipToAddress.getCountry().getIsocode() + "<br>" + b2bUnitData.getUid() + "/" + nabsAccountCode;
			}
			LOG.info("OLD SESSION VALUE SOLDTO" + getSessionService().getAttribute(WebConstants.SOLDTO_ACCOUNT_ID));
			LOG.info("OLD SESSION VALUE SHIPTO" + getSessionService().getAttribute(WebConstants.SHIPTO_ACCOUNT_ID));
			LOG.info("############ Sold To Clickede ##################");
			getSessionService().removeAttribute(WebConstants.SOLDTO_ACCOUNT_ID);
			getSessionService().setAttribute(WebConstants.SOLDTO_ACCOUNT_ID, accountId);
			getSessionService().setAttribute(WebConstants.SHIPTO_ACCOUNT_ID,
					getSessionService().getAttribute(WebConstants.SHIPTO_ACCOUNT_ID));

			final AddressData manualShipToAddress = (AddressData) getSessionService().getAttribute("shiptToAddress");
			if (null != manualShipToAddress)
			{
				getSessionService().removeAttribute(WebConstants.SHIPTO_ACCOUNT_ID);
				getSessionService().setAttribute(WebConstants.SHIPTO_ACCOUNT_ID, accountId);
			}

			getSessionService().removeAttribute(WebConstants.USER_SOLDTO_FULL_ADDRESS);
			getSessionService().setAttribute(WebConstants.USER_SOLDTO_FULL_ADDRESS, unitAddress);
			model.addAttribute(WebConstants.USER_SOLDTO_FULL_ADDRESS, unitAddress);

			LOG.info("NEW SESSION VALUE SOLDTO" + getSessionService().getAttribute(WebConstants.SOLDTO_ACCOUNT_ID));
			LOG.info("NEW SESSION VALUE SHIPTO" + getSessionService().getAttribute(WebConstants.SHIPTO_ACCOUNT_ID));
		}
		else if ("defaultShipTo".equals(shipToSoldToFlag))
		{
			getSessionService().setAttribute("shiptToAddress", null);
			LOG.info("OLD SESSION VALUE SOLDTO" + getSessionService().getAttribute(WebConstants.SOLDTO_ACCOUNT_ID));
			LOG.info("OLD SESSION VALUE SHIPTO" + getSessionService().getAttribute(WebConstants.SHIPTO_ACCOUNT_ID));
			LOG.info("############ default Clicked ##################");
			getSessionService().removeAttribute(WebConstants.USER_SHIPTO_FULL_ADDRESS);

			getSessionService().setAttribute(WebConstants.USER_SHIPTO_FULL_ADDRESS, "");
			model.addAttribute(WebConstants.USER_SHIPTO_FULL_ADDRESS, "");


			getSessionService().removeAttribute(WebConstants.SHIPTO_ACCOUNT_ID);
			getSessionService().setAttribute(WebConstants.SHIPTO_ACCOUNT_ID, accountId);
			getSessionService().setAttribute(WebConstants.SOLDTO_ACCOUNT_ID,
					getSessionService().getAttribute(WebConstants.SOLDTO_ACCOUNT_ID));
			LOG.info("NEW SESSION VALUE SOLDTO" + getSessionService().getAttribute(WebConstants.SOLDTO_ACCOUNT_ID));
			LOG.info("NEW SESSION VALUE SHIPTO" + getSessionService().getAttribute(WebConstants.SHIPTO_ACCOUNT_ID));
		}
		else if ("defaultSoldTo".equals(shipToSoldToFlag))
		{
			LOG.info("OLD SESSION VALUE SOLDTO" + getSessionService().getAttribute(WebConstants.SOLDTO_ACCOUNT_ID));
			LOG.info("OLD SESSION VALUE SHIPTO" + getSessionService().getAttribute(WebConstants.SHIPTO_ACCOUNT_ID));
			LOG.info("############ default Clicked ##################");
			getSessionService().removeAttribute(WebConstants.SOLDTO_ACCOUNT_ID);
			getSessionService().setAttribute(WebConstants.SOLDTO_ACCOUNT_ID, accountId);
			getSessionService().removeAttribute(WebConstants.USER_SOLDTO_FULL_ADDRESS);
			getSessionService().setAttribute(WebConstants.USER_SOLDTO_FULL_ADDRESS, "");
			model.addAttribute(WebConstants.USER_SOLDTO_FULL_ADDRESS, "");

			final AddressData manualShipToAddress = (AddressData) getSessionService().getAttribute("shiptToAddress");
			if (null != manualShipToAddress)
			{
				getSessionService().removeAttribute(WebConstants.SHIPTO_ACCOUNT_ID);
				getSessionService().setAttribute(WebConstants.SHIPTO_ACCOUNT_ID, accountId);
			}

			LOG.info("NEW SESSION VALUE SOLDTO" + getSessionService().getAttribute(WebConstants.SOLDTO_ACCOUNT_ID));
			LOG.info("NEW SESSION VALUE SHIPTO" + getSessionService().getAttribute(WebConstants.SHIPTO_ACCOUNT_ID));
		}
		model.addAttribute("SessionAddresschanged", "Success");
		return "pages/fm/ajax/userNewSessionAddress";
	}

	/**
	 * 
	 * @param userNewAddress
	 * @param shipToSoldToFlag
	 * @param model
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/user-new-Address/{userNewAddress}/{shipToSoldToFlag}")
	public void updateUserNewAddressToSession(@PathVariable("userNewAddress") final String userNewAddress,
			@PathVariable("shipToSoldToFlag") final String shipToSoldToFlag, final Model model)
	{
		LOG.info("updateUserNewAddressToSession ################## ");
		LOG.info("my new Address :: " + userNewAddress);
		LOG.info("shipToSoldToFlag :: " + shipToSoldToFlag);

		if ("shipTo".equals(shipToSoldToFlag))
		{
			LOG.info("############ ShipTO Clickede ##################");
			getSessionService().removeAttribute(WebConstants.USER_SHIPTO_FULL_ADDRESS);
			getSessionService().setAttribute(WebConstants.USER_SHIPTO_FULL_ADDRESS, userNewAddress);
			model.addAttribute(WebConstants.USER_SHIPTO_FULL_ADDRESS, userNewAddress);

			LOG.info("NEW SESSION FULL VALUE SHIPTO" + getSessionService().getAttribute(WebConstants.USER_SHIPTO_FULL_ADDRESS));
		}
		else if ("soldTo".equals(shipToSoldToFlag))
		{
			LOG.info("############ ShipTO Clickede ##################");
			getSessionService().removeAttribute(WebConstants.USER_SOLDTO_FULL_ADDRESS);
			getSessionService().setAttribute(WebConstants.USER_SOLDTO_FULL_ADDRESS, userNewAddress);
			model.addAttribute(WebConstants.USER_SOLDTO_FULL_ADDRESS, userNewAddress);

			LOG.info("NEW SESSION FULL VALUE SLODTO" + getSessionService().getAttribute(WebConstants.USER_SOLDTO_FULL_ADDRESS));
		}
	}

	/**
	 * @return the i18NFacade
	 */
	public I18NFacade getI18NFacade()
	{
		return i18NFacade;
	}

	/**
	 * @param i18nFacade
	 *           the i18NFacade to set
	 */
	public void setI18NFacade(final I18NFacade i18nFacade)
	{
		i18NFacade = i18nFacade;
	}

	/* Mahaveer WOM CODE ORDER Header Start */

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

	private static ShipToAcctBO getShiptoAccount(final FMCustomerAccountModel fmCustomerAccModel)
	{
		final ShipToAcctBO shipToAcct = new ShipToAcctBO();
		shipToAcct.setAccountCode(fmCustomerAccModel.getNabsAccountCode());
		shipToAcct.setAccountName(fmCustomerAccModel.getLocName());
		final SapAcctBO sapAccount = new SapAcctBO();
		sapAccount.setSapAccountCode(fmCustomerAccModel.getUid());
		shipToAcct.setSapAccount(sapAccount);
		shipToAcct.setAddress(setAddress(fmCustomerAccModel));
		return shipToAcct;
	}

	/* Mahaveer end */

	public List<ShippedOrderBO> sortByOrderDate(final List<ShippedOrderBO> shippedOrderBOList)
	{

		int i = 0;
		for (final ShippedOrderBO shippedOrderBO : shippedOrderBOList)
		{
			for (final OrderUnitBO orderUnitBO : shippedOrderBO.getOrderUnitList())
			{
				LOG.info((i++) + "Befre Sort orderUnitBO Date :: " + orderUnitBO.getOriginalOrderDate());
			}
		}

		Collections.sort(shippedOrderBOList, new CustomComparator());

		i = 0;
		for (final ShippedOrderBO shippedOrderBO : shippedOrderBOList)
		{
			for (final OrderUnitBO orderUnitBO : shippedOrderBO.getOrderUnitList())
			{
				LOG.info((i++) + "After Sort orderUnitBO Date :: " + orderUnitBO.getOriginalOrderDate());
			}
		}

		return shippedOrderBOList;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/userAutoAddressSearch/{searchAddressText}/{shipToSoldToFlag}")
	public String getUserAutoAddressSearch(@PathVariable("searchAddressText") final String searchAddressText,
			@PathVariable("shipToSoldToFlag") final String shipToSoldToFlag,
			@RequestParam(value = "loggedUserType", required = false) final String loggedUserType,
			@RequestParam(value = "loggedAccountID", required = false) String loggedAccountId, final Model model)

	{
		LOG.info("getUserAutoAddressSearch ################## ");
		LOG.info("searchAddressText :: " + searchAddressText);
		LOG.info("loggedUserType ---> " + loggedUserType);
		LOG.info("logged accountId" + loggedAccountId);
		//if final user is normal final take from current 
		String csrAccountID = null;
		if (loggedAccountId == null || loggedAccountId.isEmpty())
		{
			final FMCustomerModel customer = (FMCustomerModel) userService.getCurrentUser();
			final FMCustomerAccountModel fmAccount = (FMCustomerAccountModel) customer.getDefaultB2BUnit();
			loggedAccountId = fmAccount.getUid();
		}
		else
		{
			csrAccountID = (String) getSessionService().getAttribute("accountIdCSR");
			LOG.info("csrAccountID ::" + csrAccountID);
		}
		if ("SoldTo".equals(loggedUserType))
		{
			if ("shipTo".equals(shipToSoldToFlag))
			{
				LOG.info("Inside SHIP TO");//works when sold to logged in and searching for ship to
				final List<FMCustomerAccountData> shipToDataList = fmCustomerFacade.getB2BShipToAddressForSoldTo(searchAddressText,
						loggedAccountId, shipToSoldToFlag, loggedUserType);
				model.addAttribute("SearchList", shipToDataList);
				model.addAttribute("Type", new String("searchShipTo"));
				model.addAttribute("newSearch", "true");
			}
			else if ("soldTo".equals(shipToSoldToFlag))
			{
				LOG.info("Inside SOLDTO");//when sold to logged in and searching for sold to
				final List<FMCustomerAccountData> soldToAddress = fmCustomerFacade.getB2BShipToAddressForSoldTo(searchAddressText,
						loggedAccountId, "sold-to", loggedUserType);
				model.addAttribute("SearchList", soldToAddress);
				model.addAttribute("Type", new String("searchSoldTo"));
				model.addAttribute("newSearch", "true");
			}
		}

		if ("ShipTo".equals(loggedUserType))
		{
			if ("shipTo".equals(shipToSoldToFlag))
			{
				LOG.info("Inside SHIP TO");//when sold to logged in and searching for ship to
				final List<FMCustomerAccountData> shipToDataList = fmCustomerFacade.getB2BShipToAddressForSoldTo(searchAddressText,
						loggedAccountId, shipToSoldToFlag, (null != csrAccountID && csrAccountID.startsWith("001")) ? "SoldTo"
								: loggedUserType);
				model.addAttribute("SearchList", shipToDataList);
				model.addAttribute("Type", new String("searchShipTo"));
				model.addAttribute("newSearch", "true");
			}
			else if ("soldTo".equals(shipToSoldToFlag))
			{

				LOG.info("Inside SOLD TO");//works when sold to logged in and searching for ship to
				final List<FMCustomerAccountData> soldToAddress = fmCustomerFacade.getB2BShipToAddressForSoldTo(searchAddressText,
						loggedAccountId, loggedAccountId.startsWith("002") ? loggedUserType : "sold-to", loggedUserType);
				model.addAttribute("SearchList", soldToAddress);
				model.addAttribute("Type", new String("searchSoldTo"));
				model.addAttribute("newSearch", "true");
			}

		}
		return ControllerConstants.Views.Pages.Account.FMAutoAddressSearch;
	}

	@RequestMapping(value = "/end-emulate")
	@RequireHardLogIn
	public void endEmulate(final Model model, final HttpServletResponse response) throws CMSItemNotFoundException
	{
		getSessionService().removeAttribute(WebConstants.SOLDTO_ACCOUNT_ID);
		getSessionService().removeAttribute(WebConstants.SHIPTO_ACCOUNT_ID);
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


	@RequestMapping(method = RequestMethod.GET, value = "/sortOrderHistory")
	public String getSortOrderHistory(@RequestParam(value = "sortBy") final String sortBy, final Model model)
			throws CMSItemNotFoundException
	{
		final List<OrderStatusResult> orderStatusResult = getSessionService().getAttribute("sessionOrderStatusResult");
		final OrderStatusHelper orderStatusHelper = getSessionService().getAttribute("sessionOrderStatusHelper");
		OrderStatusResultHelper orderStatusResultHelper;

		LOG.info("Sort By :: " + sortBy);
		String userGroup = "";
		int noOfpages = 0;
		final List<String> groupUID = new ArrayList<String>();
		final UserModel user = userService.getCurrentUser();
		final Set<PrincipalGroupModel> groupss = user.getGroups();
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


		if ((orderStatusResult != null && (!orderStatusResult.isEmpty())) && (orderStatusHelper != null))
		{
			noOfpages = orderStatusResult.size() / 20;

			if (sortBy.equals(WebConstants.PURCHASE_ORDER_NUMBER))
			{
				orderStatusResultHelper = new OrderStatusResultHelper();
				final List<OrderStatusResult> sortedOrderStatusResult = orderStatusResultHelper
						.sortByPurchaseOrderNumber(orderStatusHelper);
				LOG.info("sortedOrderStatusResult :: " + sortedOrderStatusResult.size());
				model.addAttribute("orderStatusResult", sortedOrderStatusResult);
			}

			if (sortBy.equals(WebConstants.ORDER_NUMBER))
			{
				orderStatusResultHelper = new OrderStatusResultHelper();
				final List<OrderStatusResult> sortedOrderStatusResult = orderStatusResultHelper.sortByOrderNumber(orderStatusHelper);
				getSessionService().setAttribute("sessionSortedOrderStatusResult", sortedOrderStatusResult);
				LOG.info("sortedOrderStatusResult :: " + sortedOrderStatusResult.size());
				model.addAttribute("orderStatusResult", sortedOrderStatusResult);
			}
			if (sortBy.equals(WebConstants.ORDER_PACKING_SLIP))
			{
				orderStatusResultHelper = new OrderStatusResultHelper();
				final List<OrderStatusResult> sortedOrderStatusResult = orderStatusResultHelper
						.sortByOrderPackingSlipNumber(orderStatusHelper);
				getSessionService().setAttribute("sessionSortedOrderStatusResult", sortedOrderStatusResult);
				LOG.info("sortedOrderStatusResult :: " + sortedOrderStatusResult.size());
				model.addAttribute("orderStatusResult", sortedOrderStatusResult);
			}
			if (sortBy.equals(WebConstants.ORDER_SHIPPING_DC))
			{
				orderStatusResultHelper = new OrderStatusResultHelper();
				final List<OrderStatusResult> sortedOrderStatusResult = orderStatusResultHelper
						.sortByOrderShippingDC(orderStatusHelper);
				getSessionService().setAttribute("sessionSortedOrderStatusResult", sortedOrderStatusResult);
				LOG.info("sortedOrderStatusResult :: " + sortedOrderStatusResult.size());
				model.addAttribute("orderStatusResult", sortedOrderStatusResult);
			}
			if (sortBy.equals(WebConstants.ORDER_SHIPPING_DATE))
			{
				orderStatusResultHelper = new OrderStatusResultHelper();
				final List<OrderStatusResult> sortedOrderStatusResult = orderStatusResultHelper
						.sortByOrderShipDate(orderStatusHelper);
				getSessionService().setAttribute("sessionSortedOrderStatusResult", sortedOrderStatusResult);
				LOG.info("sortedOrderStatusResult :: " + sortedOrderStatusResult.size());
				model.addAttribute("orderStatusResult", sortedOrderStatusResult);
			}

			if (sortBy.equals(WebConstants.ORDER_STATUS))
			{
				orderStatusResultHelper = new OrderStatusResultHelper();
				final List<OrderStatusResult> sortedOrderStatusResult = orderStatusResultHelper.sortByOrderStatus(orderStatusHelper);
				getSessionService().setAttribute("sessionSortedOrderStatusResult", sortedOrderStatusResult);
				LOG.info("sortedOrderStatusResult :: " + sortedOrderStatusResult.size());
				model.addAttribute("orderStatusResult", sortedOrderStatusResult);
			}
			if (sortBy.equals(WebConstants.ORDER_DATE))
			{
				orderStatusResultHelper = new OrderStatusResultHelper();
				final List<OrderStatusResult> sortedOrderStatusResult = orderStatusResultHelper.sortByOrderDate(orderStatusHelper);
				getSessionService().setAttribute("sessionSortedOrderStatusResult", sortedOrderStatusResult);
				LOG.info("sortedOrderStatusResult :: " + sortedOrderStatusResult.size());
				model.addAttribute("orderStatusResult", sortedOrderStatusResult);
			}
		}
		storeCmsPageInModel(model, getContentPageForLabelOrId(FMORDER_DETAILS_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FMORDER_DETAILS_CMS_PAGE));
		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getFMBreadcrumbs("Order History"));
		model.addAttribute("orderSearchData", new OrderSearchForm());
		model.addAttribute("userGroup", userGroup);
		model.addAttribute("begin", 0);
		model.addAttribute("end", 20);
		model.addAttribute("itemsCount", 20);
		model.addAttribute("page", 1);
		model.addAttribute("noOfpages", noOfpages);
		model.addAttribute("metaRobots", "no-index,no-follow");

		return ControllerConstants.Views.Pages.Account.FmOrderHistoryPage;
	}

	@RequireHardLogIn
	@RequestMapping(value = "/exportToExcel/{orderNumber}/{packingNumber}", method = RequestMethod.GET)
	public ModelAndView downloadExcel(final Model model, @PathVariable("orderNumber") final String orderNumber,
			@PathVariable("packingNumber") final String packingNumber) throws CMSItemNotFoundException
	{
		final ShippedOrderBO shippedOrderDetailsBO = getExportOrderDetails(orderNumber, packingNumber);
		return new ModelAndView("orderExcelView", "shippedOrderDetailsBO", shippedOrderDetailsBO);
	}

	@RequireHardLogIn
	@RequestMapping(value = "/exportToPdf/{orderNumber}/{packingNumber}", method = RequestMethod.GET)
	public ModelAndView downloadPdf(final Model model, @PathVariable("orderNumber") final String orderNumber,
			@PathVariable("packingNumber") final String packingNumber) throws CMSItemNotFoundException
	{
		final ShippedOrderBO shippedOrderDetailsBO = getExportOrderDetails(orderNumber, packingNumber);
		return new ModelAndView("orderPDFView", "shippedOrderDetailsBO", shippedOrderDetailsBO);
	}

	@RequireHardLogIn
	@RequestMapping(value = "/sendOrderHistoryEmail/{orderNumber}", method = RequestMethod.GET)
	public String sendOrderHistoryEmail(final Model model, @PathVariable("orderNumber") final String orderNumber)
			throws CMSItemNotFoundException
	{
		final String orderRetrunFlag = "ORDERS";
		ShippedOrderBO shippedOrderDetailsBO = null;
		try
		{
			final List<OrderStatusResult> orderStatusResult = (List<OrderStatusResult>) getSessionService().getAttribute(
					"sessionOrderStatusResult");
			if (orderStatusResult != null && (!orderStatusResult.isEmpty()))
			{
				LOG.info("orderNumber ==>" + orderNumber);
				boolean found = false;
				for (final OrderStatusResult orderStatus : orderStatusResult)
				{
					final ShippedOrderBO shippedOrderBO = orderStatus.getShippedOrder().getShippedOrderBO();

					for (final OrderUnitBO orderUnit : shippedOrderBO.getOrderUnitList())
					{
						if (orderUnit.getOrderNumber().equalsIgnoreCase(orderNumber))
						{
							orderHeardeService.getOrderDetail(shippedOrderBO, orderRetrunFlag);
							shippedOrderDetailsBO = shippedOrderBO;
							LOG.info("\n invoiceDetails ......." + shippedOrderBO);
							found = true;
							break;
						}
					}
					if (found)
					{
						break;
					}
				}
			}
		}
		catch (final Exception e)
		{
			LOG.error("ERROR" + e.getMessage());
			model.addAttribute("OrderHeaderStatus", null);
		}

		final MailUtil mailUtil = new MailUtil();
		final HSSFWorkbook orderHistoryWorkbook = mailUtil.getOrderHistoryCSVforEmail(shippedOrderDetailsBO);
		try
		{
			//Logic to write excel file
			final String excelFileName = "OrderHistory.xls";
			final FileOutputStream out = new FileOutputStream(new File(excelFileName));
			orderHistoryWorkbook.write(out);
			out.flush();
			out.close();


			final byte[] byteArray = orderHistoryWorkbook.getBytes();
			final InputStream inputStream = new ByteArrayInputStream(byteArray);


			final JavaMailSenderImpl sender = new JavaMailSenderImpl();
			sender.setHost(Config.getParameter("orderHistoryEmailHost"));

			final MimeMessage message = sender.createMimeMessage();

			// use the true flag to indicate you need a multipart message
			MimeMessageHelper helper;
			final UserModel user = userService.getCurrentUser();
			helper = new MimeMessageHelper(message, true);
			helper.setSubject("Order History");

			helper.setTo(user.getUid());

			helper.setText("Order history attachment");
			helper.setFrom("fmparts@federalmogul.com", "Federal Mogul Motor Parts");

			sendEmail(user.getUid(), "Order history attachment", "fmparts@federalmogul.com", "OrderHistory.xls", inputStream,excelFileName);
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

		//Added by sai for fixing Email attachment

		String userGroup = "";
		final int noOfpages = 0;
		final List<String> groupUID = new ArrayList<String>();
		final UserModel user = userService.getCurrentUser();
		final Set<PrincipalGroupModel> groupss = user.getGroups();
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
		storeCmsPageInModel(model, getContentPageForLabelOrId(FMORDER_DETAILS_CMS_PAGE));
		model.addAttribute("clickedlink", "clickedOrderHistory");
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FMORDER_DETAILS_CMS_PAGE));
		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getFMBreadcrumbs("Order History"));
		model.addAttribute("orderStatusResult", getSessionService().getAttribute("sessionOrderStatusResult"));
		model.addAttribute("userGroup", userGroup);
		model.addAttribute("begin", 0);
		model.addAttribute("end", 20);
		model.addAttribute("itemsCount", 20);
		model.addAttribute("page", 1);
		model.addAttribute("noOfpages", noOfpages);
		model.addAttribute("metaRobots", "no-index,no-follow");
		model.addAttribute("orderSearchData", new OrderSearchForm());
		return ControllerConstants.Views.Pages.Account.FmOrderHistoryPage;
	}

	/*
	 * Mahaveer - Added new method for exporting the order details in PDF , Excel and Mail format
	 */
	private ShippedOrderBO getExportOrderDetails(final String orderNumber, final String packingNumber)
	{
		final String orderRetrunFlag = "ORDERS";
		ShippedOrderBO shippedOrderDetailsBO = null;
		try
		{
			final String soldToAcc = (String) getSessionService().getAttribute(WebConstants.SOLDTO_ACCOUNT_ID);
			FMCustomerAccountModel fmCustomerAccModel = (FMCustomerAccountModel) companyB2BCommerceService.getUnitForUid(soldToAcc);
			final String shipToAcc = (String) getSessionService().getAttribute(WebConstants.SHIPTO_ACCOUNT_ID);
			final FMCustomerAccountModel sfmCustomerAccModel = (FMCustomerAccountModel) companyB2BCommerceService
					.getUnitForUid(shipToAcc);

			final List<AccountBO> billToAccounts = new ArrayList<AccountBO>();
			final String loggedUserType = (String) getSessionService().getAttribute("logedUserType");
			LOG.info("loggedUserType ::" + loggedUserType);
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
			final BillToAcctBO billToAccount = getBillTOAccount(fmCustomerAccModel);
			final ShipToAcctBO shipToAccount = getShiptoAccount(sfmCustomerAccModel);
			final String customerPurchaseOrderNumber = null;

			final String masterOrderNumber = orderNumber.trim();

			final Date startDate = getOrderSearchStartDate();
			final Date endDate = getOrderSearchEndDate();
			final OrderSearchFilter filter = OrderSearchFilter.ALL;
			List<ShippedOrderBO> orderHeaderList = null;

			if (loggedUserType != null && "ShipTo".equalsIgnoreCase(loggedUserType))
			{
				LOG.info("loggedUserType is TRUE ::" + loggedUserType);
				orderHeaderList = orderHeardeService.searchOrders(billToAccounts, shipToAccount,
						masterOrderNumber != null ? masterOrderNumber.trim() : masterOrderNumber, customerPurchaseOrderNumber,
						startDate, endDate, filter, orderRetrunFlag);
			}
			else
			{
				LOG.info("loggedUserType is FALSE ::" + loggedUserType);
				orderHeaderList = orderHeardeService.searchOrders(billToAccount, masterOrderNumber != null ? masterOrderNumber.trim()
						: masterOrderNumber, customerPurchaseOrderNumber, startDate, endDate, filter, orderRetrunFlag);
			}
			if (orderHeaderList != null && (!orderHeaderList.isEmpty()))
			{
				boolean found = false;
				for (final ShippedOrderBO shippedOrderBO : orderHeaderList)
				{
					final List<ShippingUnitBO> packingShippingUnitBO = new ArrayList<ShippingUnitBO>();
					final List<OrderUnitBO> orderUnitListBO = new ArrayList<OrderUnitBO>();
					OrderUnitBO packingOrderUnit = null;
					for (final OrderUnitBO orderUnit : shippedOrderBO.getOrderUnitList())
					{
						if (orderUnit.getOrderNumber().equalsIgnoreCase(orderNumber))
						{
							orderHeardeService.getOrderDetail(shippedOrderBO, orderRetrunFlag);
							if (null != packingNumber && !packingNumber.equalsIgnoreCase(orderNumber))
							{
								for (final ShippingUnitBO shippingUnitBO : orderUnit.getShippingUnitList())
								{
									if (packingNumber.equalsIgnoreCase(shippingUnitBO.getPackingSlip()))
									{
										packingShippingUnitBO.add(shippingUnitBO);
										break;
									}
								}
							}
							if (null != packingNumber && !packingShippingUnitBO.isEmpty()
									&& !packingNumber.equalsIgnoreCase(orderNumber))
							{
								packingOrderUnit = orderUnit;
								packingOrderUnit.setShippingUnitList(packingShippingUnitBO);
								orderUnitListBO.add(packingOrderUnit);
								shippedOrderDetailsBO = shippedOrderBO;
								shippedOrderDetailsBO.setOrderUnitList(orderUnitListBO);
								found = true;
							}
							else
							{
								shippedOrderDetailsBO = shippedOrderBO;
								found = true;
							}
							if (found)
							{
								break;
							}
						}
					}
					if (found)
					{
						break;
					}
				}
			}
		}
		catch (final Exception e)

		{
			e.printStackTrace();
			LOG.error("ERROR" + e.getMessage());
			return null;
		}
		return shippedOrderDetailsBO;
	}

	/**
	 * Method to send email notification
	 * 
	 * 
	 */
	private void sendEmail(final String userid, final String text, final String from, final String name, final InputStream file,
			final String excel)
	{
		LOG.info("in sendEmail ");
		try
		{
			final String fromAddress = from;
			final String toAddress = userid;

			final String body = text;
			final EmailAddressModel fromAddressModel = emailService.getOrCreateEmailAddressForEmail(fromAddress, from);
			final String subject = text;
			final FileInputStream filenew = new FileInputStream(new File(excel));
			final DataInputStream in = new DataInputStream(filenew);
			final EmailAttachmentModel model = emailService.createEmailAttachment(in, excel, "application/xls");
			final List<EmailAddressModel> toAddresses = getEmailAddresses(toAddress);
			final List<EmailAttachmentModel> modelList = new ArrayList<EmailAttachmentModel>();
			modelList.add(model);

			final EmailMessageModel emailMessageModel = emailService.createEmailMessage(toAddresses, null, null, fromAddressModel,
					fromAddress, subject, body, modelList);
			if (emailService.send(emailMessageModel))
			{
				LOG.info("Email has been send");
			}
			else
			{
				LOG.info("Email not sent send");
			}
			LOG.info("in sendEmail after sent ");
			modelService.remove(emailMessageModel);
			modelService.remove(model);
			LOG.info("### Model Deleted ###### ");

		}
		catch (final IllegalArgumentException exception)
		{
			LOG.info("Email not sent send" + exception);
		}
		catch (final Exception e)
		{
			LOG.info("Email not sent send" + e);
		}
	}

	private List<EmailAddressModel> getEmailAddresses(final String toAddress)
	{
		final String emailId = toAddress;
		final List<EmailAddressModel> emailAddresses = new ArrayList<EmailAddressModel>();

		emailAddresses.add(emailService.getOrCreateEmailAddressForEmail(emailId, emailId));

		return emailAddresses;
	}

	private Map<String, String> prepareaboutShopMap()
	{
		final Map<String, String> aboutShop = new LinkedHashMap<String, String>();
		aboutShop.put(Config.getParameter("aboutShopNational"), Config.getParameter("aboutShopNationalValue"));
		aboutShop.put(Config.getParameter("aboutShopRegional"), Config.getParameter("aboutShopRegionalValue"));
		aboutShop.put(Config.getParameter("aboutShopMemberofabanner"), Config.getParameter("aboutShopMemberbannerValue"));
		aboutShop.put(Config.getParameter("aboutShopIndependent"), Config.getParameter("aboutShopIndependentValue"));

		return aboutShop;
	}

	private Map<String, String> prepareshopTypeMap()
	{
		final Map<String, String> shopType = new LinkedHashMap<String, String>();
		shopType.put(Config.getParameter("shopTypeGeneral"), Config.getParameter("shopTypeGeneralValue"));
		shopType.put(Config.getParameter("shopTypeImport"), Config.getParameter("shopTypeImportValue"));
		shopType.put(Config.getParameter("shopTypeRadiator"), Config.getParameter("shopTypeRadiatorValue"));
		shopType.put(Config.getParameter("shopTypeMuffler/brake"), Config.getParameter("shopTypeMuffler/brakevalue"));
		shopType.put(Config.getParameter("shopTypeTransmission"), Config.getParameter("shopTypeTransmissionValue"));
		shopType.put(Config.getParameter("shopTypeFrontend"), Config.getParameter("shopTypeFrontendValue"));
		shopType.put(Config.getParameter("shopTypeTiredealer"), Config.getParameter("shopTypeTiredealerValue"));
		shopType.put(Config.getParameter("shopTypeCollision"), Config.getParameter("shopTypeCollisionValue"));
		shopType.put(Config.getParameter("shopTypeEngine"), Config.getParameter("shopTypeEngineValue"));
		shopType.put(Config.getParameter("shopTypeQuick"), Config.getParameter("shopTypeQuickValue"));
		shopType.put(Config.getParameter("shopTypeOriginal"), Config.getParameter("shopTypeOriginalValue"));

		return shopType;
	}

	private Map<String, String> prepareMostInterstedMap()
	{
		final Map<String, String> mostIntersted = new LinkedHashMap<String, String>();
		mostIntersted.put(Config.getParameter("mostInterestedTechnical"), Config.getParameter("mostInterestedTechnicalValue"));
		mostIntersted.put(Config.getParameter("mostInterestedApparel"), Config.getParameter("mostInterestedApparelValue"));
		mostIntersted.put(Config.getParameter("mostInterestedTools"), Config.getParameter("mostInterestedToolsValue"));
		mostIntersted.put(Config.getParameter("mostInterestedFreetrial"), Config.getParameter("mostInterestedFreetrialValue"));
		mostIntersted.put(Config.getParameter("mostInterestedPromotional"), Config.getParameter("mostInterestedPromotionalValue"));
		mostIntersted.put(Config.getParameter("mostInterestedExclusive"), Config.getParameter("mostInterestedExclusiveValue"));

		return mostIntersted;
	}

	private Map<String, String> preparebrandMap()
	{
		final Map<String, String> brands = new LinkedHashMap<String, String>();
		brands.put(Config.getParameter("brandAbex"), Config.getParameter("brandAbexValue"));
		brands.put(Config.getParameter("brandANCO"), Config.getParameter("brandANCOValue"));
		brands.put(Config.getParameter("brandChampion"), Config.getParameter("brandChampionValue"));
		brands.put(Config.getParameter("brandFel-Pro"), Config.getParameter("brandFelProValue"));
		brands.put(Config.getParameter("brandFerodo"), Config.getParameter("brandFerodoValue"));
		brands.put(Config.getParameter("brandFPDiesel"), Config.getParameter("brandFPDieselValue"));
		brands.put(Config.getParameter("brandMOOG"), Config.getParameter("brandMOOGValue"));
		brands.put(Config.getParameter("brandNational"), Config.getParameter("brandNationalValue"));
		brands.put(Config.getParameter("brandSealedPower"), Config.getParameter("brandSealedPowerValue"));
		brands.put(Config.getParameter("brandSpeedPro"), Config.getParameter("brandSpeedProValue"));
		brands.put(Config.getParameter("brandWagnerBrak"), Config.getParameter("brandWagnerBrakeValue"));
		brands.put(Config.getParameter("brandWagnerLighting"), Config.getParameter("brandWagnerLightingValue"));

		return brands;
	}

	private Map<String, String> prepareshopbayMap()
	{
		final Map<String, String> shopBay = new LinkedHashMap<String, String>();
		shopBay.put(Config.getParameter("shopBay5-9"), Config.getParameter("shopBay5-9Value"));
		shopBay.put(Config.getParameter("shopBaysLess"), Config.getParameter("shopBaysLessValue"));
		shopBay.put(Config.getParameter("shopBay10-14"), Config.getParameter("shopBay10-14value"));
		shopBay.put(Config.getParameter("greatershopBay"), Config.getParameter("shopBayGreaterValue"));

		return shopBay;
	}

	public void setUpdateProfile(final Model model, final FMUpdateProfileForm fmUpdateProfileForm)
	{
		final FMCustomerData fmCurrentCustomerData = fmCustomerFacade.getCurrentFMCustomer();
		model.addAttribute("email", fmCurrentCustomerData.getEmail());
		final Map<String, String> aboutShopMap = prepareaboutShopMap();
		final Map<String, String> shopTypeMap = prepareshopTypeMap();
		final Map<String, String> shopBayMap = prepareshopbayMap();
		final Map<String, String> mostInterstedMap = prepareMostInterstedMap();
		final Map<String, String> brandsMap = preparebrandMap();


		model.addAttribute("aboutShop", aboutShopMap);

		model.addAttribute("shopType", shopTypeMap);
		model.addAttribute("shopBays", shopBayMap);
		model.addAttribute("mostIntersted", mostInterstedMap);
		model.addAttribute("brands", brandsMap);
		model.addAttribute("breadcrumbs", accountBreadcrumbBuilder.getFMBreadcrumbs("text.account.profile"));
		model.addAttribute("titleData", userFacade.getTitles());
			
		fmUpdateProfileForm.setLoyaltySignup(fmCurrentCustomerData.getLoyaltySignup());
		model.addAttribute("updateProfileForm", fmUpdateProfileForm);
	}

	protected void addressValidation(final FMUpdateProfileForm fmUpdateProfileForm, final Model model) {
		String addressLine1 = fmUpdateProfileForm.getAddressline1();
		if (StringUtils.isBlank(addressLine1) || StringUtils.isEmpty(addressLine1)) {
			GlobalMessages.addErrorMessage(model, "Please enter the Address.");
		}
	}

	protected void cityValidation(final FMUpdateProfileForm fmUpdateProfileForm, final Model model) {
		String city = fmUpdateProfileForm.getCity();
		if (StringUtils.isBlank(city) || StringUtils.isEmpty(city)) {
			GlobalMessages.addErrorMessage(model, "Please enter the City.");
		}
	}

	protected void countryValidation(final FMUpdateProfileForm fmUpdateProfileForm, final Model model) {
		String country = fmUpdateProfileForm.getCountry();
		if (StringUtils.isBlank(country) || "default".equalsIgnoreCase(country)) {
			GlobalMessages.addErrorMessage(model, "Please select a Country.");
		}
	}

	protected void stateValidation(final FMUpdateProfileForm fmUpdateProfileForm, final Model model) {
		String state = fmUpdateProfileForm.getState();
		if (StringUtils.isBlank(state) || "default".equalsIgnoreCase(state)) {
			GlobalMessages.addErrorMessage(model, "Please select a State/Province.");
		}
	}

	protected void postalCodeValidation(final FMUpdateProfileForm fmUpdateProfileForm, final Model model) {
		String zipCode = fmUpdateProfileForm.getZipCode();
		if (StringUtils.isBlank(zipCode)) {
			GlobalMessages.addErrorMessage(model, "Please enter a Zip/Postal Code.");
		} else {
			String country = fmUpdateProfileForm.getCountry();
			if (StringUtils.isNotBlank(country)) {
				if (country.toLowerCase().equals("us")) {
					if (!US_POSTAL_CODE_PATTERN.matcher(zipCode).matches()) {
						GlobalMessages.addErrorMessage(model, "The zip code format is incorrect.");
					}
				} else if (country.toLowerCase().equals("ca")) {
					if (!CA_POSTAL_CODE_PATTERN.matcher(zipCode).matches()) {
						GlobalMessages.addErrorMessage(model, "The postal code format is incorrect.");
					}
				}
			}
		}
	}

	protected void phoneNumberValidation(final FMUpdateProfileForm fmUpdateProfileForm, final Model model) {
		String phoneNbr = fmUpdateProfileForm.getPhoneno();
		if (StringUtils.isBlank(phoneNbr) || StringUtils.isEmpty(phoneNbr)) {
			GlobalMessages.addErrorMessage(model, "Please enter the Phone Number.");
		}
	}

}
