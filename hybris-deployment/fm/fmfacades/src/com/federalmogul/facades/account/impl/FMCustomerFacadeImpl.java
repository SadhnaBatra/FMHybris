/**
 *
 */
package com.federalmogul.facades.account.impl;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNullStandardMessage;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2bacceleratorfacades.order.data.B2BUnitData;
import de.hybris.platform.b2bacceleratorservices.company.CompanyB2BCommerceService;
import de.hybris.platform.catalog.CatalogService;
import de.hybris.platform.catalog.model.CatalogModel;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.commercefacades.customer.impl.DefaultCustomerFacade;
import de.hybris.platform.commercefacades.user.converters.populator.AddressPopulator;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.data.RegionData;
import de.hybris.platform.commercefacades.user.data.TitleData;
import de.hybris.platform.commercefacades.user.exceptions.PasswordMismatchException;
import de.hybris.platform.commerceservices.customer.DuplicateUidException;
import de.hybris.platform.commerceservices.customer.TokenInvalidatedException;
import de.hybris.platform.commerceservices.event.AbstractCommerceUserEvent;
import de.hybris.platform.commerceservices.i18n.CommerceCommonI18NService;
import de.hybris.platform.converters.Converters;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.c2l.CountryModel;
import de.hybris.platform.core.model.c2l.RegionModel;
import de.hybris.platform.core.model.security.PrincipalGroupModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.media.MediaService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.store.services.BaseStoreService;
import de.hybris.platform.util.Config;

import com.federalmogul.core.enums.BusinessUnitType;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.xml.soap.SOAPException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.federalmogul.core.account.FMCustomerAccountService;
import com.federalmogul.core.account.FMCustomerService;
import com.federalmogul.core.customer.account.dao.FMCustomerAccountDAO;
import com.federalmogul.core.enums.FmTaxValidationType;
import com.federalmogul.core.enums.Fmusertype;
import com.federalmogul.core.event.FMB2SBTaxAdminApprovalProcessEvent;
import com.federalmogul.core.event.FMB2SBTaxApprovalProcessEvent;
import com.federalmogul.core.model.FMB2SBTaxAdminApprovalProcessModel;
import com.federalmogul.core.model.FMB2SBTaxApprovalProcessModel;
import com.federalmogul.core.model.FMBrandCampaignModel;
import com.federalmogul.core.model.FMCustomerAccountModel;
import com.federalmogul.core.model.FMCustomerModel;
import com.federalmogul.core.model.FMCustomerPartnerFunctionModel;
import com.federalmogul.core.model.FMTaxDocumentModel;
import com.federalmogul.core.network.FMNetworkService;
import com.federalmogul.core.network.dao.FMUserSearchDAO;
import com.federalmogul.facades.account.FMCustomerFacade;
import com.federalmogul.facades.address.data.FMB2bAddressData;
import com.federalmogul.facades.user.data.FMBrandCampaignData;
import com.federalmogul.facades.user.data.FMCustomerAccountData;
import com.federalmogul.facades.user.data.FMCustomerData;
import com.federalmogul.facades.user.data.FMTaxDocumentData;
import com.federalmogul.facades.user.data.FMTaxdocsData;
import com.federalmogul.facades.util.FMFacadeUtility;
import com.federalmogul.falcon.core.model.FMCsrAccountListModel;
import com.fm.falcon.webservices.dto.UserRegistrationRequestDTO;
import com.fm.falcon.webservices.dto.UserRegistrationResponseDTO;
import com.fm.falcon.webservices.soap.helper.ShipToCreationHelper;
import com.fm.falcon.webservices.soap.helper.UpdateProfileHelper;
import com.fm.falcon.webservices.soap.helper.UserRegistrationHelper;


/**
 * @author SR279690
 *
 */
public class FMCustomerFacadeImpl extends DefaultCustomerFacade implements FMCustomerFacade {

	private static final Logger LOG = Logger.getLogger(FMCustomerFacadeImpl.class);
	
	private static final String REGISTRATION_SUCCESS_RESPONSE_CODE = "000";
	private static final String REGISTRATION_TBD_RESPONSE_CODE = "TBD";

	private static final String CUSTOMER_TYPE_CODE_B2B = "B2B";
	private static final String CUSTOMER_TYPE_CODE_B2C = "B2C";
	private static final String CUSTOMER_TYPE_CODE_WAREHOUSE_DISTRIBUTOR_LIGHT_VEHICLE = "B2B_LV";
	private static final String CUSTOMER_TYPE_CODE_WAREHOUSE_DISTRIBUTOR_COMMERCIAL_VEHICLE = "B2B_CV";
	private static final String CUSTOMER_TYPE_CODE_RETAILER = "B2B_RT";
	private static final String CUSTOMER_TYPE_CODE_JOBBER = "B2B_JB";
	private static final String CUSTOMER_TYPE_CODE_REPAIR_SHOP_OWNER = "B2B_RS";
	private static final String CUSTOMER_TYPE_CODE_TECHNICIAN = "B2T_TC";
	private static final String CUSTOMER_TYPE_CODE_CONSUMER = "CON_AN";
	private static final String CUSTOMER_TYPE_CODE_STUDENT = "B2T_ST";

	private static final String UID_FMINSTALLER_CUSTOMER_GROUP = "FMInstallerGroup";
	private static final String UID_FMB2C_CUSTOMER_GROUP = "FMB2C";
	private static final String UID_B2B_CUSTOMER_GROUP = "b2bcustomergroup";
	private static final String UID_DUMMY_B2B_CUSTOMER_GROUP = "DummyB2BUnit";

	private static final String FLAG_X = "X";
	private static final String LDAP_USER_DN_NA = "na";
	private static final String UNIT_NAME_AND_LOC_NAME_DASH = "-";


	@Resource
	private FMCustomerService fmcustomerservice;

	@Resource
	protected CompanyB2BCommerceService companyB2BCommerceService;

	@Resource
	protected SessionService sessionService;

	@Resource
	protected UserService userService;

	@Resource
	protected ShipToCreationHelper b2bShipToCreationHelper;

	@Autowired
	private FMUserSearchDAO fmUserSearchDAO;

	@Autowired
	private ModelService modelService;

	@Autowired
	private EventService eventService;

	@Resource
	private CatalogService catalogService;

	@Resource
	private MediaService mediaService;

	@Resource
	private AddressPopulator addressPopulator;

	@Resource
	protected FMCustomerFacade fmCustomerFacade;

	@Resource
	private FMCustomerAccountDAO defaultFMCustomerAccountDAO;

	@Resource
	private FMCustomerAccountService fmCustomerAccountService;

	@Resource
	private FMNetworkService fmNetworkService;

	@Autowired
	private BaseSiteService baseSiteService;

	@Autowired
	private CommonI18NService commonI18NService;

	@Autowired
	private BaseStoreService baseStoreService;

	private Converter<FMCustomerAccountModel, FMCustomerAccountData> fmCustomerAccountConverter;
	private Populator<FMCustomerAccountModel, FMCustomerAccountData> fmCustomerAccountPopulator;

	private CommerceCommonI18NService commerceCommonI18NService;

	private Converter<FMCustomerModel, FMCustomerData> fmCustomerConverter;
	private Converter<UserModel, FMCustomerData> fmCurrentCustomerConverter;

	private Populator<FMB2bAddressData, AddressModel> fmb2baddressReversePopulator;

	private Populator<AddressModel, FMB2bAddressData> fmb2bAddressPopulator;

	private Converter<B2BUnitModel, B2BUnitData> b2BUnitConverter;

	/**
	 * method which will call the service methods to save the model
	 *
	 * @param fmcustomerdata
	 * @throws IOException
	 * @throws SOAPException
	 * @throws ClassNotFoundException
	 * @throws UnsupportedOperationException
	 */
	@Override
	public void createCustomerAccount(final FMCustomerData fmcustomerdata) throws UnsupportedOperationException,
			ClassNotFoundException, SOAPException, IOException {
		validateParameterNotNullStandardMessage("fmcustomerdata", fmcustomerdata);
		Assert.hasText(fmcustomerdata.getFirstName(), "The field [FirstName] cannot be empty");
		Assert.hasText(fmcustomerdata.getLastName(), "The field [LastName] cannot be empty");

		if (fmcustomerdata.getUserTypeDescription().equals(Fmusertype.WAREHOUSEDISTRIBUTORCOMMERCIALVEHICLE)
				|| fmcustomerdata.getUserTypeDescription().equals(Fmusertype.WAREHOUSEDISTRIBUTORLIGHTVEHICLE)
				|| fmcustomerdata.getUserTypeDescription().equals(Fmusertype.RETAILER)
				|| fmcustomerdata.getUserTypeDescription().equals(Fmusertype.JOBBERPARTSTORE)
				|| fmcustomerdata.getUserTypeDescription().equals(Fmusertype.SALESREP)) {

			fmcustomerservice.createCustomerAccount(b2BcustomerCreate(fmcustomerdata), fmcustomerdata.getPassword());
		} else if (fmcustomerdata.getUserTypeDescription().equals(Fmusertype.CONSUMERDIFM)
				|| fmcustomerdata.getUserTypeDescription().equals(Fmusertype.CONSUMERDIY)
				|| fmcustomerdata.getUserTypeDescription().equals(Fmusertype.CONSUMER)) {

			fmcustomerservice.createCustomerAccount(b2CcustomerCreate(fmcustomerdata), fmcustomerdata.getPassword());
		} else if (fmcustomerdata.getUserTypeDescription().equals(Fmusertype.REPAIRSHOPOWNER)
				|| fmcustomerdata.getUserTypeDescription().equals(Fmusertype.TECHNICIAN)
				|| fmcustomerdata.getUserTypeDescription().equals(Fmusertype.STUDENT)) {

			fmcustomerservice.createCustomerAccount(b2bcustomerCreate(fmcustomerdata), fmcustomerdata.getPassword());
		}
	}

	/**
	 * User defined method for saving the B2B customer data in fmcustomer model which returns the FMCustomerModel
	 *
	 * @param fmcustomerdata
	 * @return FMCustomerModel
	 * @throws IOException
	 * @throws SOAPException
	 * @throws ClassNotFoundException
	 * @throws UnsupportedOperationException
	 */
	private FMCustomerModel b2BcustomerCreate(final FMCustomerData fmcustomerdata) throws UnsupportedOperationException,
			ClassNotFoundException, SOAPException, IOException {
		LOG.info("Inside 'b2BcustomerCreate(...)'");
		final FMCustomerModel newCustomer = getModelService().create(FMCustomerModel.class);
		newCustomer.setName(getCustomerNameStrategy().getName(fmcustomerdata.getFirstName(), fmcustomerdata.getLastName()));
		if (StringUtils.isNotBlank(fmcustomerdata.getFirstName()) && StringUtils.isNotBlank(fmcustomerdata.getLastName())) {
			newCustomer.setName(getCustomerNameStrategy().getName(fmcustomerdata.getFirstName(), fmcustomerdata.getLastName()));
		}
		final UserRegistrationHelper userRegHelper = new UserRegistrationHelper();
		UserRegistrationResponseDTO regResponse = null;

		if (fmcustomerdata.getUnit() != null
				&& !fmcustomerdata.getUnit().getUid().equals(Config.getParameter("federalmogulaccountCode_Internalusers"))) {
			final String codes = Config.getParameter("accountcodes");
			if (codes == null) {
				LOG.info("b2BcustomerCreate(...): Configuration parameter 'accountcodes' not found. Please add it to 'config/local.properties' " +
							"or configure it for current session in Hybris HAC");
			}
			final String[] acccodes = codes.split(",");
			int acccount = 0;
			for (int i = 0; i < acccodes.length; i++) {
				if (fmcustomerdata.getUnit().getUid().contains(acccodes[i])) {
					acccount++;
				}
			}

			if (acccount == 0) {
				LOG.info("b2BcustomerCreate(...): Sending SOAP message to SAP CRM");
				regResponse = userRegHelper.sendSOAPMessage(convertDataToDTO(fmcustomerdata));

				if (!REGISTRATION_SUCCESS_RESPONSE_CODE.equals(regResponse.getResponseCode())) {
					LOG.info("b2BcustomerCreate(...): SAP CRM - Throwing IOException");
					throw new IOException(regResponse.getSeverityText());
				} else {
					setUidForRegister(fmcustomerdata, newCustomer);
				}
				newCustomer.setCrmCustomerID(regResponse.getContactID());
				LOG.info("b2BcustomerCreate(...): B2B contact id from CRM: " + regResponse.getContactID());
			}
		} else {
			setUidForRegister(fmcustomerdata, newCustomer);
		}

		newCustomer.setUid(fmcustomerdata.getEmail().toLowerCase());

		boolean fmEmployeeFlag = fmcustomerdata.getUserTypeDescription().equals(Fmusertype.SALESREP) ? true : false;
		final String ldapUserDN = FMFacadeUtility.createNewCustomer(fmcustomerdata, fmEmployeeFlag);
		LOG.info("b2BcustomerCreate(...): LDAP User DN: " + ldapUserDN);

		if (!LDAP_USER_DN_NA.equalsIgnoreCase(ldapUserDN)) {
			newCustomer.setLdapaccount(true);
			newCustomer.setLdaplogin(ldapUserDN);
		}

		newCustomer.setEmail(fmcustomerdata.getEmail());
		newCustomer.setLastName(fmcustomerdata.getLastName());
		newCustomer.setChannelCode(fmcustomerdata.getUserTypeDescription());
		newCustomer.setNewsLetterSubscription(fmcustomerdata.getNewsLetterSubscription());
		if (fmcustomerdata.getUnit() != null) {
			newCustomer.setDefaultB2BUnit(companyB2BCommerceService.getUnitForUid(fmcustomerdata.getUnit().getUid()));
			newCustomer.setFmUnit((FMCustomerAccountModel) companyB2BCommerceService
					.getUnitForUid(fmcustomerdata.getUnit().getUid()));
		} else {
			LOG.info("b2BcustomerCreate(...): Setting FM root organization for B2B Sold to  not present");
			newCustomer.setDefaultB2BUnit(companyB2BCommerceService.getUnitForUid(Config.getParameter("fmOrganizationAccount")));
			newCustomer.setFmUnit((FMCustomerAccountModel) companyB2BCommerceService.getUnitForUid(Config
					.getParameter("fmOrganizationAccount")));
		}

		final Set<PrincipalGroupModel> customerGroups = new HashSet<PrincipalGroupModel>();

		if (fmcustomerdata.getUnit() != null
				&& !fmcustomerdata.getUnit().getUid().equals(Config.getParameter("federalmogulaccountCode_Internalusers"))) {
			final String codes = Config.getParameter("accountcodes");
			final String[] acccodes = codes.split(",");
			int acccount = 0;
			for (int i = 0; i < acccodes.length; i++) {

				if (fmcustomerdata.getUnit().getUid().contains(acccodes[i])) {
					acccount++;
				}
			}

			if (acccount == 0) {

				if (fmcustomerdata.getRoles().contains("FMFullAccessGroup")) {
					customerGroups.add(userService.getUserGroupForUID("b2bcustomergroup"));
					customerGroups.add(userService.getUserGroupForUID("FMFullAccessGroup"));
					customerGroups.add(userService.getUserGroupForUID("FMB2BB"));
				} else if (fmcustomerdata.getRoles().contains("FMNoInvoiceGroup")) {
					customerGroups.add(userService.getUserGroupForUID("b2bcustomergroup"));
					customerGroups.add(userService.getUserGroupForUID("FMNoInvoiceGroup"));
				} else if (fmcustomerdata.getRoles().contains("FMViewOnlyGroup")) {
					customerGroups.add(userService.getUserGroupForUID("b2bcustomergroup"));
					customerGroups.add(userService.getUserGroupForUID("FMViewOnlyGroup"));
				} else if (fmcustomerdata.getRoles().contains("FMBUVOGroup")) {
					customerGroups.add(userService.getUserGroupForUID("b2bcustomergroup"));
					customerGroups.add(userService.getUserGroupForUID("FMBUVOGroup"));
				}

				if (newCustomer.getChannelCode().equals(Fmusertype.JOBBERPARTSTORE)) {
					customerGroups.add(userService.getUserGroupForUID("FMJobberGroup"));
				}
			} else {
				LOG.info("b2BcustomerCreate(...): In BUVor group");
				customerGroups.add(userService.getUserGroupForUID("b2bcustomergroup"));
				customerGroups.add(userService.getUserGroupForUID("FMBUVOR"));
			}
		} else if (null != fmcustomerdata.getUnit()
				&& fmcustomerdata.getUnit().getUid().equals(Config.getParameter("federalmogulaccountCode_Internalusers"))) {
			customerGroups.add(userService.getUserGroupForUID("b2bcustomergroup"));
			customerGroups.add(userService.getUserGroupForUID("FMCSR"));
		}

		newCustomer.setGroups(customerGroups);

		CustomerModel currentCustomer = null;
		if (fmcustomerdata.getFromCronJob() == null) {
			currentCustomer = this.getCurrentSessionCustomer();
		} else {
			currentCustomer = userService.getAnonymousUser();
		}

		LOG.info("b2BcustomerCreate(...): Current Customer UId: " + currentCustomer.getUid());
		final AddressData customeraddressdata = fmcustomerdata.getDefaultShippingAddress();
		if (customeraddressdata != null) {
			final AddressModel customeraddress = getModelService().create(AddressModel.class);
			getAddressReversePopulator().populate(customeraddressdata, customeraddress);
			getCustomerAccountService().saveAddressEntry(currentCustomer, customeraddress);
			final List<AddressModel> custaddress = new ArrayList<AddressModel>();
			custaddress.add(customeraddress);
			newCustomer.setAddresses(custaddress);
	
			newCustomer.setDefaultShipmentAddress(customeraddress);
			LOG.info("b2BcustomerCreate(...): newCustomer town: " + newCustomer.getDefaultShipmentAddress().getTown());
			LOG.info("b2BcustomerCreate(...): newCustomer state: " + newCustomer.getDefaultShipmentAddress().getRegion());
		}
		newCustomer.setLoginDisabled(true);
		return newCustomer;
	}

	private UserRegistrationRequestDTO convertDataToDTO(final FMCustomerData fmcustomerdata) {
		final UserRegistrationRequestDTO reqDTO = new UserRegistrationRequestDTO();
		reqDTO.setServiceName("Request");
		reqDTO.setCustomerEmailID(fmcustomerdata.getEmail());
		LOG.info("convertDataToDTO(...): " + CUSTOMERACCOUNTTYPES.get(fmcustomerdata.getUserTypeDescription()) + " "
				+ fmcustomerdata.getUserTypeDescription());
		reqDTO.setAccountCode("" + CUSTOMERACCOUNTTYPES.get(fmcustomerdata.getUserTypeDescription()));
		reqDTO.setFirstName(fmcustomerdata.getFirstName());
		reqDTO.setLastName(fmcustomerdata.getLastName());
		
		if (fmcustomerdata.getDefaultShippingAddress() != null) {
			reqDTO.setStreetName1(fmcustomerdata.getDefaultShippingAddress().getLine1());
			reqDTO.setStreetName2(fmcustomerdata.getDefaultShippingAddress().getLine2());
			reqDTO.setCity(fmcustomerdata.getDefaultShippingAddress().getTown());

			if (fmcustomerdata.getDefaultShippingAddress().getRegion() != null) {
				final int len = fmcustomerdata.getDefaultShippingAddress().getRegion().getIsocode().length();
				LOG.info("convertDataToDTO(...): State from fmcustomerdata: " + fmcustomerdata.getDefaultShippingAddress().getRegion().getIsocode());
				LOG.info("convertDataToDTO(...): State Code Length: " + len);
				reqDTO.setState((fmcustomerdata.getDefaultShippingAddress().getRegion().getIsocode()).substring(3, 5));
				LOG.info("convertDataToDTO(...): State from reqDTO: " + reqDTO.getState());
			}

			reqDTO.setPostalCode(fmcustomerdata.getDefaultShippingAddress().getPostalCode());
			if (fmcustomerdata.getDefaultShippingAddress().getCountry() != null) {
				reqDTO.setCountry(fmcustomerdata.getDefaultShippingAddress().getCountry().getIsocode());
			}
			reqDTO.setTelephone(fmcustomerdata.getDefaultShippingAddress().getPhone());
		} else {
			reqDTO.setPostalCode("");
		}

		if (fmcustomerdata.getNewsLetterSubscription() !=null && fmcustomerdata.getNewsLetterSubscription()) {
			reqDTO.setNewsletterFlag(FLAG_X);
		}

		if (fmcustomerdata.getUnit() != null) {
			reqDTO.setSold_ShipTo(fmcustomerdata.getUnit().getUid());
		} else {
			reqDTO.setSold_ShipTo("");
		}

		reqDTO.setCustomerType(getCustomerTypeForFMUserType(fmcustomerdata));
		LOG.info("convertDataToDTO(...): customerType: " + reqDTO.getCustomerType());

		if (Fmusertype.JOBBERPARTSTORE.equals(fmcustomerdata.getUserTypeDescription())
				|| Fmusertype.SHOPOWNERTECHNICIAN.equals(fmcustomerdata.getUserTypeDescription())
				|| Fmusertype.REPAIRSHOPOWNER.equals(fmcustomerdata.getUserTypeDescription())
				|| Fmusertype.TECHNICIAN.equals(fmcustomerdata.getUserTypeDescription())
				|| Fmusertype.STUDENT.equals(fmcustomerdata.getUserTypeDescription())) {

			if (fmcustomerdata.getB2baddress() != null) {
				reqDTO.setCompanyName(fmcustomerdata.getB2baddress().getCompanyName());
				reqDTO.setTaxNumber(fmcustomerdata.getTaxID());
				reqDTO.setCompStreetName1(fmcustomerdata.getB2baddress().getLine1());
				reqDTO.setCompStreetName2(fmcustomerdata.getB2baddress().getLine2());
				reqDTO.setCompCity(fmcustomerdata.getB2baddress().getTown());

				if (fmcustomerdata.getB2baddress().getRegion() != null) {
					LOG.info("convertDataToDTO(...): STATE from fmcustomerdata: " + fmcustomerdata.getB2baddress().getRegion().getIsocode());
					reqDTO.setCompState((fmcustomerdata.getB2baddress().getRegion().getIsocode()).substring(3, 5));
				}
				LOG.info("convertDataToDTO(...): STATE COMPANY: " + reqDTO.getCompState());

				reqDTO.setCompPostCode(fmcustomerdata.getB2baddress().getPostalCode());
				if (fmcustomerdata.getB2baddress().getCountry() != null) {
					reqDTO.setCompCountry(fmcustomerdata.getB2baddress().getCountry().getIsocode());
				}
			} else {
				reqDTO.setCompPostCode("");
			}
			reqDTO.setLmsId(fmcustomerdata.getLmsSigninId());
			reqDTO.setPromoCode(fmcustomerdata.getPromoCode());
			if (fmcustomerdata.getUniqueID() != null) {
				if (fmcustomerdata.getUniqueID().size() > 0) {
					reqDTO.setUniqueID(fmcustomerdata.getUniqueID());
				}
			}
		}

		if (Fmusertype.CONSUMERDIFM.equals(fmcustomerdata.getUserTypeDescription())
				|| Fmusertype.CONSUMERDIY.equals(fmcustomerdata.getUserTypeDescription())
				|| Fmusertype.CONSUMER.equals(fmcustomerdata.getUserTypeDescription())) {
			reqDTO.setSold_ShipTo("");
		}

		if (fmcustomerdata.getLoyaltySignup() !=null && fmcustomerdata.getLoyaltySignup()) {
			reqDTO.setLoyaltyEnrollmentFlag(FLAG_X);
		}

		if (fmcustomerdata.getPromotionInfoSubscription() != null) {
			if (fmcustomerdata.getPromotionInfoSubscription()) {
				reqDTO.setNewPromotionFlag(FLAG_X);
			}
		}

		if (fmcustomerdata.getNewProductAlerts() != null) {
			if (fmcustomerdata.getNewProductAlerts()) {
				reqDTO.setNewProductAlert(FLAG_X);
			}
		}

		if (fmcustomerdata.getPromotionInfoSubscription() != null) {
			if (fmcustomerdata.getPromotionInfoSubscription()) {
				reqDTO.setNewPromotionFlag(FLAG_X);
			}
		}

		if (fmcustomerdata.getReferEmailId() != null) {
			reqDTO.setReferralEmailId(fmcustomerdata.getReferEmailId());
		}

		return reqDTO;
	}

	/**
	 * User defined method for saving the B2b customer data in fmcustomer model which returns the FMCustomerModel
	 *
	 * @param fmcustomerdata
	 * @return FMCustomerModel
	 * @throws IOException
	 * @throws SOAPException
	 * @throws ClassNotFoundException
	 * @throws UnsupportedOperationException
	 */
	@SuppressWarnings("deprecation")
	private FMCustomerModel b2bcustomerCreate(final FMCustomerData fmcustomerdata) throws UnsupportedOperationException,
			ClassNotFoundException, SOAPException, IOException {
		final FMCustomerModel newCustomer = getModelService().create(FMCustomerModel.class);
		UserRegistrationResponseDTO regB2bResponse = null;
		final UserRegistrationHelper userRegHelper = new UserRegistrationHelper();

		LOG.info("b2bcustomerCreate(...): Sending SOAP message to SAP CRM");
		regB2bResponse = userRegHelper.sendSOAPMessage(convertDataToDTO(fmcustomerdata));

		if (!REGISTRATION_SUCCESS_RESPONSE_CODE.equals(regB2bResponse.getResponseCode())) {
			throw new IOException(regB2bResponse.getSeverityText());
		} else if (REGISTRATION_TBD_RESPONSE_CODE.equalsIgnoreCase(regB2bResponse.getResponseCode())) {
			throw new IOException("You have an ID XXXX already registered, use the same for Log In.");
		}

		LOG.info("b2bcustomerCreate(...): regB2bResponse.getSeverityText(): " + regB2bResponse.getSeverityText());
		if (regB2bResponse.getSeverityText() == null || regB2bResponse.getSeverityText().isEmpty()) {
			newCustomer.setName(getCustomerNameStrategy().getName(fmcustomerdata.getFirstName(), fmcustomerdata.getLastName()));
			if (StringUtils.isNotBlank(fmcustomerdata.getFirstName()) && StringUtils.isNotBlank(fmcustomerdata.getLastName())) {
				newCustomer.setName(getCustomerNameStrategy().getName(fmcustomerdata.getFirstName(), fmcustomerdata.getLastName()));
			}
			LOG.info("b2bcustomerCreate(...): regB2bResponse.getContactID() :0: " + regB2bResponse.getContactID());

			newCustomer.setCrmCustomerID(regB2bResponse.getContactID());
			newCustomer.setUid(fmcustomerdata.getEmail().toLowerCase());
			newCustomer.setB2cLoyaltyMembershipId(regB2bResponse.getB2cLoyaltyMembershipId());
			final String ldapUserDN = FMFacadeUtility.createNewCustomer(fmcustomerdata);
			LOG.info("b2bcustomerCreate(...): LDAP User DN: " + ldapUserDN);

			if (!LDAP_USER_DN_NA.equalsIgnoreCase(ldapUserDN)) {
				newCustomer.setLdapaccount(true);
				newCustomer.setLdaplogin(ldapUserDN);
			}

			newCustomer.setEmail(fmcustomerdata.getEmail());
			newCustomer.setLastName(fmcustomerdata.getLastName());
			newCustomer.setChannelCode(fmcustomerdata.getUserTypeDescription());
			final CustomerModel currentCustomer = this.getCurrentSessionCustomer();
			LOG.info("b2bcustomerCreate(...): Current Customer UId: " + currentCustomer.getUid());
			final AddressData customeraddressdata = fmcustomerdata.getDefaultShippingAddress();
			final AddressModel customeraddress = getModelService().create(AddressModel.class);
			getAddressReversePopulator().populate(customeraddressdata, customeraddress);
			getCustomerAccountService().saveAddressEntry(currentCustomer, customeraddress);
			final List<AddressModel> custaddress = new ArrayList<AddressModel>();
			custaddress.add(customeraddress);
			newCustomer.setAddresses(custaddress);
			newCustomer.setDefaultShipmentAddress(customeraddress);
			LOG.info("b2bcustomerCreate(...): newCustomer town: " + newCustomer.getDefaultShipmentAddress().getTown());
			LOG.info("b2bcustomerCreate(...): newCustomer state: " + newCustomer.getDefaultShipmentAddress().getRegion());
			LOG.info("b2bcustomerCreate(...): NAME IN ADDRESS: " + newCustomer.getDefaultShipmentAddress().getFirstname()
					+ newCustomer.getDefaultShipmentAddress().getLastname());
			//FOR CREATING ADMIN USER FOR ADDING A NEW UNIT
			sessionService.setAttribute(SessionContext.USER, userService.getAdminUser());

			//Change for -- checking Unique AccountCode from CRM

			final String userUid = regB2bResponse.getProspectID();
			final boolean checkProspectid = fmcustomerservice.checkProspectid(userUid);
			LOG.info("b2bcustomerCreate(...): checkProspectid Result from facade:: " + checkProspectid);
			final FMCustomerAccountModel unitModel = this.getModelService().create(FMCustomerAccountModel.class);
			unitModel.setUid(regB2bResponse.getProspectID());
			LOG.info("b2bcustomerCreate(...): regB2bResponse.getContactID() :1: " + regB2bResponse.getContactID());
			unitModel.setUid(regB2bResponse.getContactID());
			if (fmcustomerdata.getB2baddress() != null && fmcustomerdata.getB2baddress().getCompanyName() != null) {
				unitModel.setName(fmcustomerdata.getB2baddress().getCompanyName());
				unitModel.setLocName(fmcustomerdata.getB2baddress().getCompanyName());
			} else {
				unitModel.setName(UNIT_NAME_AND_LOC_NAME_DASH);
				unitModel.setLocName(UNIT_NAME_AND_LOC_NAME_DASH);
			}
			unitModel.setProspectuid(regB2bResponse.getProspectID());
			final Set<PrincipalGroupModel> parentUnit = new HashSet();
			parentUnit.add(companyB2BCommerceService.getUnitForUid(Config.getParameter("fmOrganizationAccount")));
			unitModel.setGroups(parentUnit);
			unitModel.setTaxID(fmcustomerdata.getTaxID());

			if (fmcustomerdata.getTaxDocumentList() != null && fmcustomerdata.getTaxDocumentList().size() > 0) {
				final List<FMTaxDocumentData> fmTaxDocumentDataList = fmcustomerdata.getTaxDocumentList();
				final Iterator taxdociter = fmTaxDocumentDataList.iterator();
				final FMTaxDocumentData fmtaxdoc = (FMTaxDocumentData) taxdociter.next();
				LOG.info("b2bcustomerCreate(...): in b2b after getting from data " + fmtaxdoc.getDocname());
				final List<FMTaxDocumentModel> taxdocsmodel = new ArrayList<FMTaxDocumentModel>();
				final FMTaxDocumentModel taxdocmodel = getModelService().create(FMTaxDocumentModel.class);
				final CatalogModel cm = catalogService.getCatalogForId("federalmogulContentCatalog");
				final Set catalogModelSet = cm.getCatalogVersions();
				if (catalogModelSet != null) {
					final Iterator itr = catalogModelSet.iterator();
					final CatalogVersionModel catalogVersionModel = (CatalogVersionModel) itr.next();
					LOG.info("b2bcustomerCreate(...): #### getCategorySystemID " + catalogVersionModel.getCategorySystemID());
					LOG.info("b2bcustomerCreate(...): #### getVersion " + catalogVersionModel.getVersion());
					LOG.info("b2bcustomerCreate(...): #### getCatalog " + catalogVersionModel.getCatalog());

					taxdocmodel.setCatalogVersion(catalogVersionModel);
					taxdocmodel.setCode(fmtaxdoc.getCode());
					taxdocmodel.setRealFileName(fmtaxdoc.getRealFileName());
					taxdocmodel.setUrl(fmtaxdoc.getURL());
					taxdocmodel.setUrl2(fmtaxdoc.getURL());
					taxdocmodel.setDocname(fmtaxdoc.getDocname());
					taxdocmodel.setUrl2(fmtaxdoc.getDownloadURL());
					LOG.info("b2bcustomerCreate(...): #### CatalogModelSet size " + catalogModelSet.size());
				}

				final String isocode = fmtaxdoc.getState().getIsocode();
				final RegionModel regionModel = getCommonI18NService().getRegion(
						getCommonI18NService().getCountry(fmcustomerdata.getDefaultShippingAddress().getCountry().getIsocode()),
						isocode);
				taxdocmodel.setState(regionModel);
				LOG.info("b2bcustomerCreate(...): taxdocmodel STATE " + taxdocmodel.getState().getIsocode());
				taxdocsmodel.add(taxdocmodel);
				unitModel.setFmtaxDocument(taxdocsmodel);
			}

			this.getModelService().save(unitModel);
			final FMB2bAddressData unitaddressdata = fmcustomerdata.getB2baddress();
			final AddressModel companyaddress = getModelService().create(AddressModel.class);
			LOG.info("b2bcustomerCreate(...): B4 CALLING POPULATOR");
			getFmb2baddressReversePopulator().populate(unitaddressdata, companyaddress);
			LOG.info("b2bcustomerCreate(...): AFTER CALLING POPULATOR");
			companyaddress.setOwner(unitModel);
			getModelService().save(companyaddress);
			getModelService().refresh(companyaddress);
			final List<AddressModel> unitaddresses = new ArrayList<AddressModel>();
			unitaddresses.add(companyaddress);
			unitModel.setAddresses(unitaddresses);
			this.getModelService().save(unitModel);
			sessionService.setAttribute(SessionContext.USER, userService.getCurrentUser());

			final Set<PrincipalGroupModel> customerGroups = new HashSet();
			customerGroups.add(userService.getUserGroupForUID("b2bcustomergroup"));
			customerGroups.add(userService.getUserGroupForUID("FMFullAccessGroup"));
			customerGroups.add(userService.getUserGroupForUID("FMB2T"));
			customerGroups.add(userService.getUserGroupForUID("FMInstallerGroup"));
			unitModel.setUnitType(BusinessUnitType.BTOSB);
			this.getModelService().save(unitModel);

			newCustomer.setGroups(customerGroups);
			newCustomer.setLoyaltySubscription(fmcustomerdata.getLoyaltySignup());
			newCustomer.setTechAcademySubscription(fmcustomerdata.getTechAcademySubscription());
			newCustomer.setPromSubscription(fmcustomerdata.getPromotionInfoSubscription());
			newCustomer.setNewPrductAlerts(fmcustomerdata.getNewProductAlerts());
			newCustomer.setDefaultB2BUnit(unitModel);
			newCustomer.setFmUnit(unitModel);
			//Loyalty Question change
			newCustomer.setIsGarageRewardMember(fmcustomerdata.getIsGarageRewardMember());
			if (fmcustomerdata.getIsGarageRewardMember()) {
				newCustomer.setShopType(fmcustomerdata.getShopType());
				newCustomer.setAboutShop(fmcustomerdata.getAboutShop());
				newCustomer.setShopBays(fmcustomerdata.getShopBays());
				newCustomer.setMostIntersted(fmcustomerdata.getMostIntersted());
				newCustomer.setBrands(fmcustomerdata.getBrands());
				newCustomer.setShopBanner(fmcustomerdata.getShopBanner());
			}

			newCustomer.setNewsLetterSubscription(fmcustomerdata.getNewsLetterSubscription());
			newCustomer.setLmsSigninID(fmcustomerdata.getLmsSigninId());
		} else {
			throw new IOException(regB2bResponse.getSeverityText());
		}

		return newCustomer;
	}

	/**
	 * User defined method for saving the B2C customer data in fmcustomer model which returns the FMCustomerModel
	 *
	 * @param fmcustomerdata
	 * @return FMCustomerModel
	 * @throws IOException
	 * @throws SOAPException
	 * @throws ClassNotFoundException
	 * @throws UnsupportedOperationException
	 */
	private FMCustomerModel b2CcustomerCreate(final FMCustomerData fmcustomerdata) throws UnsupportedOperationException,
			ClassNotFoundException, SOAPException, IOException {
		LOG.info("Inside 'b2CcustomerCreate(...)'");
		UserRegistrationResponseDTO regB2bResponse = null;
		final UserRegistrationHelper userRegHelper = new UserRegistrationHelper();
		LOG.info("b2CcustomerCreate(...): Sending SOAP request to SAP CRM");
		regB2bResponse = userRegHelper.sendSOAPMessage(convertDataToDTO(fmcustomerdata));

		LOG.info("b2CcustomerCreate(...): response code" + regB2bResponse.getResponseCode());
		if (!REGISTRATION_SUCCESS_RESPONSE_CODE.equals(regB2bResponse.getResponseCode())) {
			throw new IOException(regB2bResponse.getSeverityText());
		}
		final FMCustomerModel newCustomer = getModelService().create(FMCustomerModel.class);
		newCustomer.setName(getCustomerNameStrategy().getName(fmcustomerdata.getFirstName(), fmcustomerdata.getLastName()));
		if (StringUtils.isNotBlank(fmcustomerdata.getFirstName()) && StringUtils.isNotBlank(fmcustomerdata.getLastName())) {
			newCustomer.setName(getCustomerNameStrategy().getName(fmcustomerdata.getFirstName(), fmcustomerdata.getLastName()));
		}
		newCustomer.setCrmCustomerID(regB2bResponse.getContactID());
		newCustomer.setUid(fmcustomerdata.getEmail().toLowerCase());
		LOG.info("b2CcustomerCreate(...): B2cLoyaltyMembershipId: " + regB2bResponse.getB2cLoyaltyMembershipId());
		newCustomer.setB2cLoyaltyMembershipId(regB2bResponse.getB2cLoyaltyMembershipId());
		final Set<PrincipalGroupModel> customerGroups = new HashSet();
		customerGroups.add(userService.getUserGroupForUID(UID_B2B_CUSTOMER_GROUP));
		customerGroups.add(userService.getUserGroupForUID(UID_FMB2C_CUSTOMER_GROUP));
		customerGroups.add(userService.getUserGroupForUID(UID_DUMMY_B2B_CUSTOMER_GROUP));
		final String ldapUserDN = FMFacadeUtility.createNewCustomer(fmcustomerdata);
		LOG.info("b2CcustomerCreate(...): LDAP User DN: " + ldapUserDN);

		if (!LDAP_USER_DN_NA.equalsIgnoreCase(ldapUserDN)) {
			newCustomer.setLdapaccount(true);
			newCustomer.setLdaplogin(ldapUserDN);
		}
		newCustomer.setGroups(customerGroups);
		newCustomer.setEmail(fmcustomerdata.getEmail());
		newCustomer.setLastName(fmcustomerdata.getLastName());
		newCustomer.setChannelCode(fmcustomerdata.getUserTypeDescription());
		newCustomer.setNewsLetterSubscription(fmcustomerdata.getNewsLetterSubscription());
		LOG.info("b2CcustomerCreate(...): fmcustomerdata.getUnit().getUid(): " + fmcustomerdata.getUnit().getUid());
		final CustomerModel currentCustomer = this.getCurrentSessionCustomer();
		LOG.info("b2CcustomerCreate(...): Current Customer UId: " + currentCustomer.getUid());
		final AddressData addressdata = fmcustomerdata.getDefaultShippingAddress();
		LOG.info("b2CcustomerCreate: address1: " + addressdata.getLine1());
		final AddressModel customeraddress = getModelService().create(AddressModel.class);
		getAddressReversePopulator().populate(addressdata, customeraddress);
		getCustomerAccountService().saveAddressEntry(currentCustomer, customeraddress);
		final List<AddressModel> custaddress = new ArrayList<AddressModel>();
		custaddress.add(customeraddress);
		newCustomer.setAddresses(custaddress);
		newCustomer.setDefaultShipmentAddress(customeraddress);

		return newCustomer;
	}

	/**
	 * Initializes a customer with given registerData
	 */
	protected void setUidForRegister(final FMCustomerData fmcustomerdata, final FMCustomerModel fmcustomer) {
		fmcustomer.setUid(fmcustomerdata.getEmail().toLowerCase());
		fmcustomer.setOriginalUid(fmcustomerdata.getEmail());
	}

	@Override
	public void updateFMProfile(final FMCustomerData fmcustomerData) throws DuplicateUidException, UnsupportedOperationException,
			ClassNotFoundException, SOAPException, IOException {

		if (validateBeforeUpdate(fmcustomerData)) {
			final UpdateProfileHelper updateProfileHelper = new UpdateProfileHelper();

			final String name = getCustomerNameStrategy().getName(fmcustomerData.getFirstName(), fmcustomerData.getLastName());
			final FMCustomerModel customer = (FMCustomerModel) getUserService().getCurrentUser();
			customer.setOriginalUid(fmcustomerData.getDisplayUid());
			customer.setLastName(fmcustomerData.getLastName());
			customer.setNewsLetterSubscription(fmcustomerData.getNewsLetterSubscription());

			final AddressData customeraddressdata = fmcustomerData.getDefaultShippingAddress();

			if (customeraddressdata != null) { // User is not a CSR
				LOG.info("updateFMProfile(...): customeraddressdata.getLine1(): " + customeraddressdata.getLine1());
				LOG.info("updateFMProfile(...): customeraddressdata.getTown(): " + customeraddressdata.getTown());
	
				final AddressModel customeraddress = getModelService().create(AddressModel.class);
				getAddressReversePopulator().populate(customeraddressdata, customeraddress);
	
				customer.setDefaultShipmentAddress(customeraddress);
	
				getCustomerAccountService().saveAddressEntry(customer, customeraddress);
				final List<AddressModel> custaddress = new ArrayList<AddressModel>();
				custaddress.add(customeraddress);
				customer.setAddresses(custaddress);
			}

			/**
			 * Start - Adding Brand Campaign Information to customer
			 */
			if (fmcustomerData.getFmBrandCampaignList() != null) {
				List<FMBrandCampaignModel> fmBrandCampaignModelList = new ArrayList<FMBrandCampaignModel>();
				for (FMBrandCampaignData fmBrandCampaignData : fmcustomerData.getFmBrandCampaignList()) {
					FMBrandCampaignModel fmBrandCampaignModel = getModelService().create(FMBrandCampaignModel.class);
					fmBrandCampaignModel.setBrandCampaignID(fmBrandCampaignData.getFmBrandCampaignID());
					fmBrandCampaignModel.setCampaignInfo(fmBrandCampaignData.getFmBrandCampaignInfo());
					fmBrandCampaignModel.setUserID(fmBrandCampaignData.getUserid());
					LOG.info("updateFMProfile(...): BrandCampaignID: " + fmBrandCampaignModel.getBrandCampaignID() + 
								" BrandCampaignInfo: " + fmBrandCampaignModel.getCampaignInfo());
					fmBrandCampaignModelList.add(fmBrandCampaignModel);
				}
				customer.setBrandCampaign(fmBrandCampaignModelList);
			}
			/**
			 * End - Adding Brand Campaign Information to customer
			 */

			if (Fmusertype.SHOPOWNERTECHNICIAN.equals(fmcustomerData.getUserTypeDescription())
					|| Fmusertype.REPAIRSHOPOWNER.equals(fmcustomerData.getUserTypeDescription())
					|| Fmusertype.TECHNICIAN.equals(fmcustomerData.getUserTypeDescription())
					|| Fmusertype.STUDENT.equals(fmcustomerData.getUserTypeDescription())) {

				customer.setLoyaltySubscription(fmcustomerData.getLoyaltySignup());
				customer.setLmsSigninID(fmcustomerData.getLmsSigninId());
				//Loyalty Question change
				customer.setIsGarageRewardMember(fmcustomerData.getIsGarageRewardMember());

				UserRegistrationResponseDTO regB2bResponse = null;
				final UserRegistrationRequestDTO registrationDTO = convertDataToDTOForUpdate(fmcustomerData);
				if (registrationDTO.getProspectID() == null || registrationDTO.getProspectID().isEmpty()) {
					final FMCustomerAccountModel unitModel = customer.getFmUnit();
					if (unitModel != null) {
						registrationDTO.setProspectID("");
						registrationDTO.setCustomerContactId(unitModel.getUid());
					}
				}

				if (fmcustomerData.getIsGarageRewardMember() != null && fmcustomerData.getIsGarageRewardMember()) {
					customer.setShopType(fmcustomerData.getShopType());
					customer.setAboutShop(fmcustomerData.getAboutShop());
					customer.setShopBays(fmcustomerData.getShopBays());
					customer.setMostIntersted(fmcustomerData.getMostIntersted());
					customer.setBrands(fmcustomerData.getBrands());
				} else {
					customer.setShopType(null);
					customer.setAboutShop(null);
					customer.setShopBays(null);
					customer.setMostIntersted(null);
					customer.setBrands(null);
				}

				LOG.info("updateFMProfile(...): Sending SOAP message to SAP CRM");
				regB2bResponse = updateProfileHelper.sendSOAPMessage(registrationDTO);
				if (regB2bResponse != null) {
					if (REGISTRATION_SUCCESS_RESPONSE_CODE.equals(regB2bResponse.getResponseCode())) {
						LOG.info("updateFMProfile(...): Inside regB2bResponse success 000");
						customer.setB2cLoyaltyMembershipId(regB2bResponse.getB2cLoyaltyMembershipId());
						getModelService().save(customer);
						getFmCustomerAccountService().updateFMProfile(customer, fmcustomerData.getTitleCode(), name,
								fmcustomerData.getUid());
					} else {
						LOG.info("updateFMProfile(...): Inside regB2bResponse ELSE IOException");
						throw new IOException(regB2bResponse.getSeverityText());
					}
				}
			} else {
				LOG.info("updateFMProfile(...): Inside User Type ELSE");
				getFmCustomerAccountService().updateFMProfile(customer, fmcustomerData.getTitleCode(), name, fmcustomerData.getUid());
			}
		}
	}

	@Override
	public FMCustomerData getfmCustomerDetails() {
		final FMCustomerModel currentfmCustomer = (FMCustomerModel) getUserService().getCurrentUser();
		LOG.info("getfmCustomerDetails(): FROM MODEL :: UID " + currentfmCustomer.getUid());
		LOG.info("getfmCustomerDetails(): FROM MODEL :: LASTNAME " + currentfmCustomer.getLastName());
		LOG.info("getfmCustomerDetails(): FROM MODEL :: NAME " + currentfmCustomer.getName());
		final FMCustomerData fmCustomerData = getFmCustomerConverter().convert(currentfmCustomer);
		LOG.info("getfmCustomerDetails(): FROM DATA :: UID " + currentfmCustomer.getUid());
		LOG.info("getfmCustomerDetails(): FROM DATA :: LASTNAME" + currentfmCustomer.getLastName());
		LOG.info("getfmCustomerDetails(): FROM DATA :: NAME" + currentfmCustomer.getName());
		LOG.info("getfmCustomerDetails(): FROM DATA :: EMAIL" + currentfmCustomer.getEmail());

		return fmCustomerData;
	}

	/**
	 * @return the fmCustomerAccountService
	 */
	public FMCustomerAccountService getFmCustomerAccountService() {
		return fmCustomerAccountService;
	}

	/**
	 * @return the fmCustomerConverter
	 */
	public Converter<FMCustomerModel, FMCustomerData> getFmCustomerConverter() {
		return fmCustomerConverter;
	}

	/**
	 * @param fmCustomerConverter
	 *           the fmCustomerConverter to set
	 */
	public void setFmCustomerConverter(final Converter<FMCustomerModel, FMCustomerData> fmCustomerConverter) {
		this.fmCustomerConverter = fmCustomerConverter;
	}

	@Override
	public FMCustomerData getCurrentFMCustomer() {
		final UserModel currentUserModel = getUserService().getCurrentUser();
		final FMCustomerModel fmCustomerModel = fmNetworkService.getFMCustomerForUid(currentUserModel.getUid());

		return getFmCurrentCustomerConverter().convert(fmCustomerModel);
	}

	/**
	 * @return the fmCurrentCustomerConverter
	 */
	public Converter<UserModel, FMCustomerData> getFmCurrentCustomerConverter() {
		return fmCurrentCustomerConverter;
	}

	/**
	 * @param fmCurrentCustomerConverter
	 *           the fmCurrentCustomerConverter to set
	 */
	public void setFmCurrentCustomerConverter(final Converter<UserModel, FMCustomerData> fmCurrentCustomerConverter) {
		this.fmCurrentCustomerConverter = fmCurrentCustomerConverter;
	}

	@Override
	public List<TitleData> getTitles() {
		return Converters.convertAll(getFmCustomerAccountService().getTitles(), getTitleConverter());
	}

	/**
	 * @return the fmb2baddressReversePopulator
	 */
	public Populator<FMB2bAddressData, AddressModel> getFmb2baddressReversePopulator() {
		return fmb2baddressReversePopulator;
	}

	/**
	 * @param fmb2baddressReversePopulator
	 *           the fmb2baddressReversePopulator to set
	 */
	public void setFmb2baddressReversePopulator(final Populator<FMB2bAddressData, AddressModel> fmb2baddressReversePopulator) {
		this.fmb2baddressReversePopulator = fmb2baddressReversePopulator;
	}

	/*
	 * user defined method for adding the tax documents
	 *
	 * @Param fmcustomerdata
	 *
	 * @see
	 * com.federalmogul.facades.account.FMCustomerFacade#taxexemption(com.federalmogul.facades.user.data.FMCustomerData)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void taxexemption(final FMCustomerData fmcustomerdata) {
		final FMCustomerModel fmmodel = (FMCustomerModel) this.getCurrentSessionCustomer();
		final FMCustomerAccountModel currentunitModel = fmmodel.getFmUnit();
		LOG.info("taxexemption(...): CURRENT TAXID" + currentunitModel.getTaxID());
		final List<FMTaxDocumentData> taxdocsdata = fmcustomerdata.getFmunit().getTaxDocumentList();
		final Iterator taxdociter = taxdocsdata.iterator();
		final FMTaxDocumentData fmtaxdoc = (FMTaxDocumentData) taxdociter.next();
		LOG.info("taxexemption(...): in b2b after getting from data " + fmtaxdoc.getDocname());
		final List<FMTaxDocumentModel> newtaxdocsmodel = new ArrayList<FMTaxDocumentModel>(fmmodel.getFmUnit().getFmtaxDocument());
		final String isocode = fmtaxdoc.getState().getIsocode();
		//CHANGE HERE
		final RegionModel regionModel = getCommonI18NService().getRegion(getCommonI18NService().getCountry("US"), isocode);
		final FMTaxDocumentModel newFMtaxDocumentModel = saveFileASMediaModel(fmtaxdoc);
		newFMtaxDocumentModel.setRealFileName(fmtaxdoc.getRealFileName());
		newFMtaxDocumentModel.setDocname(fmtaxdoc.getDocname());
		newFMtaxDocumentModel.setUploadedBy(fmmodel);
		newFMtaxDocumentModel.setState(regionModel);
		if (newFMtaxDocumentModel.getValidate() == null) {
			newFMtaxDocumentModel.setValidate(FmTaxValidationType.NOTREVIEWED);
		}
		modelService.save(newFMtaxDocumentModel);
		newtaxdocsmodel.add(newFMtaxDocumentModel);
		currentunitModel.setFmtaxDocument(newtaxdocsmodel);
		this.getModelService().save(currentunitModel);
		fmmodel.setFmUnit(currentunitModel);
		this.getModelService().save(fmmodel);
		LOG.info("taxexemption(...): UPDATE OF TAX DOC SUCCESS");
		// ashwini
		/************* email publishing *********/

		LOG.info("taxexemption(...): B2SB Tax admin Approval starts:::--");
		final FMCustomerModel fmCustomerModel = (FMCustomerModel) this.getCurrentSessionCustomer();

		LOG.info("taxexemption(...): ***************fmCustomerModel::" + fmCustomerModel);
		final FMB2SBTaxApprovalProcessModel fmB2SBTaxApprovalProcessModel = new FMB2SBTaxApprovalProcessModel();

		LOG.info("taxexemption(...): ----------bbbbbbbbbbbbbbb----------fmB2SBTaxApprovalProcessModel::" + fmB2SBTaxApprovalProcessModel);
		fmB2SBTaxApprovalProcessModel.setEmailId("sujit.balan@wipro.com");
		fmB2SBTaxApprovalProcessModel.setEndMessage("Approve the user");
		fmB2SBTaxApprovalProcessModel.setSite(getBaseSiteService().getCurrentBaseSite());
		fmB2SBTaxApprovalProcessModel.setStore(getBaseStoreService().getCurrentBaseStore());
		fmB2SBTaxApprovalProcessModel.setLanguage(getCommonI18NService().getCurrentLanguage());
		fmB2SBTaxApprovalProcessModel.setCustomer(fmCustomerModel);
		final FMB2SBTaxApprovalProcessEvent fmB2SBTaxApprovalProcessEvent = new FMB2SBTaxApprovalProcessEvent(
				fmB2SBTaxApprovalProcessModel);
		eventService.publishEvent(initializeEvent(fmB2SBTaxApprovalProcessEvent, fmCustomerModel));


		/************* end of email publishing ************/
	}

	protected AbstractCommerceUserEvent initializeEvent(final AbstractCommerceUserEvent event, final CustomerModel customerModel) {
		event.setBaseStore(getBaseStoreService().getCurrentBaseStore());
		event.setSite(getBaseSiteService().getCurrentBaseSite());
		event.setCustomer(customerModel);
		event.setLanguage(getCommonI18NService().getCurrentLanguage());
		event.setCurrency(getCommonI18NService().getCurrentCurrency());
		return event;
	}

	protected BaseSiteService getBaseSiteService() {
		return baseSiteService;
	}

	@Override
	protected CommonI18NService getCommonI18NService() {
		return commonI18NService;
	}

	public BaseStoreService getBaseStoreService() {
		return baseStoreService;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.federalmogul.facades.account.FMCustomerFacade#downloadtaxdoc(java.lang.String)
	 */
	@Override
	public boolean downloadtaxdoc(final String taxdocname) {
		LOG.info("::::::::::::::taxdocname:::::::::::::::::::" + taxdocname);
		final String downloadDirectory = "C:/Test/Download/";
		final FMCustomerData fmcustomerdata = getCurrentFMCustomer();
		final List<FMTaxDocumentData> taxdocss = fmcustomerdata.getFmunit().getTaxDocumentList();
		final Iterator taxdociter = taxdocss.iterator();
		Boolean result = true;
		while (taxdociter.hasNext()) {
			final FMTaxdocsData taxdoc = (FMTaxdocsData) taxdociter.next();
			LOG.info(":::::::::::::::::::::::::::::::::::" + taxdoc.getTaxdocname() + ":::::::::::::::::::::::::::::::");
			if (taxdocname != null && taxdoc.getTaxdocname() != null) {
				if (taxdocname.equalsIgnoreCase(taxdoc.getTaxdocname())) {
					LOG.info(":::::::::::::::::::::::::::::::::::" + taxdoc.getTaxdocname() + ":::::::::::::::::::::::::::::::");
					final MultipartFile minputfile = (MultipartFile) taxdoc.getTaxdoc();
					try
					{
						String line = "";
						final InputStream inputStream = minputfile.getInputStream();
						final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
						final BufferedWriter outwriter = new BufferedWriter(new FileWriter(new File(downloadDirectory
								+ taxdoc.getTaxdocname())));

						while ((line = bufferedReader.readLine()) != null) {
							LOG.info("");
							LOG.info(line);
							outwriter.write(line);
						}
						LOG.info("after while block" + line);

						outwriter.close();
						bufferedReader.close();
						inputStream.close();
						LOG.info(":::::::::::::::::::::::::::::::::FILE DOWNLOAD SUCCESS::::::::::::::::::::::::::::::");
						result = true;

					}
					catch (final IOException e) {
						LOG.info(e);
						result = false;
					}
				}
			}
		}

		return result;
	}

	@Override
	public boolean deletetaxdoc(final String taxdocname) {

		final FMCustomerModel fmmodel = (FMCustomerModel) this.getCurrentSessionCustomer();
		LOG.info("name in taxemeoptiondoc" + fmmodel.getName());

		final FMCustomerAccountModel currentunitModel = fmmodel.getFmUnit();
		LOG.info("CURRENT TAXID" + currentunitModel.getTaxID());

		boolean result = true;
		final List<FMTaxDocumentModel> taxdocsmodel = new ArrayList<FMTaxDocumentModel>(currentunitModel.getFmtaxDocument());
		final Iterator taxdociterator = taxdocsmodel.iterator();

		while (taxdociterator.hasNext()) {
			final FMTaxDocumentModel newtaxdocmodel = (FMTaxDocumentModel) taxdociterator.next();
			//LOG.info(":::::::::::::::::::::::::::::::::::" + newtaxdocmodel.getDoc() + ":::::::::::::::::::::::::::::::");
			if (taxdocname != null && newtaxdocmodel.getDocname() != null) {
				if (taxdocname.equalsIgnoreCase(newtaxdocmodel.getDocname())) {
					taxdocsmodel.remove(newtaxdocmodel);
					currentunitModel.setFmtaxDocument(taxdocsmodel);
					fmmodel.setDefaultB2BUnit(currentunitModel);
					fmmodel.setFmUnit(currentunitModel);
					getModelService().save(currentunitModel);
					//fmmodel.getFmUnit().setFmtaxDocument(taxdocsmodel);
					getModelService().save(fmmodel);
					LOG.info(":::::::::::::::::::::::::::DELETED AND SAVED" + ":::::::::::::::::::::::");
					return true;
				}
				result = false;
			}
			result = false;
		}

		return result;
	}


	final static HashMap CUSTOMERACCOUNTTYPES = new HashMap();

	static {
		CUSTOMERACCOUNTTYPES.put(Fmusertype.WAREHOUSEDISTRIBUTORLIGHTVEHICLE, "002");
		CUSTOMERACCOUNTTYPES.put(Fmusertype.WAREHOUSEDISTRIBUTORCOMMERCIALVEHICLE, "008");
		CUSTOMERACCOUNTTYPES.put(Fmusertype.RETAILER, "007");
		CUSTOMERACCOUNTTYPES.put(Fmusertype.JOBBERPARTSTORE, "037");
		CUSTOMERACCOUNTTYPES.put(Fmusertype.SHOPOWNERTECHNICIAN, "038");
		CUSTOMERACCOUNTTYPES.put(Fmusertype.REPAIRSHOPOWNER, "038");
		CUSTOMERACCOUNTTYPES.put(Fmusertype.TECHNICIAN, "038");
		CUSTOMERACCOUNTTYPES.put(Fmusertype.CONSUMERDIY, "036");
		CUSTOMERACCOUNTTYPES.put(Fmusertype.CONSUMERDIFM, "036");
		CUSTOMERACCOUNTTYPES.put(Fmusertype.CONSUMER, "036");
		CUSTOMERACCOUNTTYPES.put(Fmusertype.STUDENT, "038");
	}

	@Override
	public List<AddressData> getFMAddressBook() {
		LOG.info("In facade get address book method:::");
		// Get the current customer's addresses

		final Collection<AddressModel> addresses = getFmCustomerAccountService().getFMAddressBookEntries(
				(FMCustomerModel) getUserService().getCurrentUser());

		if (addresses != null && !addresses.isEmpty()) {
			LOG.info("Addr not empty hence inside IF Loop");
			final Collection<CountryModel> deliveryCountries = getCommerceCommonI18NService().getAllCountries();

			final List<AddressData> result = new ArrayList<AddressData>();
			final AddressData defaultAddress = getFMDefaultAddress();

			// Filter for delivery addresses
			for (final AddressModel address : addresses) {
				if (address.getCountry() != null) {
					final boolean validForSite = deliveryCountries != null && deliveryCountries.contains(address.getCountry());
					// Filter out invalid addresses for the site
					if (validForSite) {
						final AddressData addressData = getAddressConverter().convert(address);
						if (defaultAddress != null && defaultAddress.getId() != null
								&& defaultAddress.getId().equals(addressData.getId())) {
							addressData.setDefaultAddress(true);
							result.add(0, addressData);
						} else {
							result.add(addressData);
						}
					}
				}
			}

			return result;
		}
		return Collections.emptyList();
	}

	@Override
	public AddressData getFMDefaultAddress() {
		LOG.info("In getfmdefault address - in my facade");

		final FMCustomerModel currentCustomer = (FMCustomerModel) getUserService().getCurrentUser();

		LOG.info("Addresssessss" + currentCustomer.getAddresses());

		AddressData defaultAddressData = null;

		final AddressModel defaultAddress = getFmCustomerAccountService().getFMDefaultAddress(currentCustomer);
		if (defaultAddress != null) {
			defaultAddressData = getAddressConverter().convert(defaultAddress);
		} else {
			final List<AddressModel> addresses = getFmCustomerAccountService().getFMAddressBookEntries(currentCustomer);
			if (CollectionUtils.isNotEmpty(addresses)) {
				defaultAddressData = getAddressConverter().convert(addresses.get(0));
			}
		}
		return defaultAddressData;
	}

	/**
	 * @return the commerceCommonI18NService
	 */
	public CommerceCommonI18NService getCommerceCommonI18NService()
	{
		return commerceCommonI18NService;
	}

	/**
	 * @param commerceCommonI18NService
	 *           the commerceCommonI18NService to set
	 */
	public void setCommerceCommonI18NService(final CommerceCommonI18NService commerceCommonI18NService)
	{
		this.commerceCommonI18NService = commerceCommonI18NService;
	}

	/**
	 * @return the defaultFMCustomerAccountDAO
	 */
	public FMCustomerAccountDAO getDefaultFMCustomerAccountDAO()
	{
		return defaultFMCustomerAccountDAO;
	}

	/**
	 * @param defaultFMCustomerAccountDAO
	 *           the defaultFMCustomerAccountDAO to set
	 */
	public void setDefaultFMCustomerAccountDAO(final FMCustomerAccountDAO defaultFMCustomerAccountDAO)
	{
		this.defaultFMCustomerAccountDAO = defaultFMCustomerAccountDAO;
	}

	@Override
	public List<FMTaxDocumentModel> getAllTaxDocuments(final String searchTaxDocName, final String sortOption)
	{
		LOG.info("getAllTaxDocuments under FMCustomerFacadeImpl ");
		// YTODO Auto-generated method stub
		return getDefaultFMCustomerAccountDAO().getAllFMTaxDocuments(searchTaxDocName, sortOption);
	}


	public FMCustomerAccountModel getFMB2BUnit(final String uid)
	{
		return getDefaultFMCustomerAccountDAO().getFMB2BUnit(uid);
	}

	@Override
	public boolean updateTaxExemptionApprovalForPK(final String taxDocPK, final FmTaxValidationType adminTaxDocApproval)
	{
		LOG.info("updateTaxExemptionApprovalForPK under FMCustomerFacadeImpl ");

		if (taxDocPK != null && taxDocPK != "")
		{
			final FMTaxDocumentModel fmTaxDocumentsModel = getDefaultFMCustomerAccountDAO().getFMTaxDocumentsForPK(taxDocPK);
			if (fmTaxDocumentsModel != null)
			{
				final FMCustomerData fmCustomerData = fmCustomerFacade.getCurrentFMCustomer();
				LOG.info("fmCustomerData NAME :: " + fmCustomerData.getName());
				LOG.info("fmCustomerData LAST NAME :: " + fmCustomerData.getLastName());
				LOG.info("fmCustomerData First NAME :: " + fmCustomerData.getFirstName());
				fmTaxDocumentsModel.setApprovedBy(fmCustomerData.getName());
				fmTaxDocumentsModel.setValidate(adminTaxDocApproval);
				LOG.info("adminTaxDocApproval ::at FACED IMPL  " + adminTaxDocApproval);
				this.getModelService().save(fmTaxDocumentsModel);
				LOG.info("SUCCESS");
				if (adminTaxDocApproval.equals(FmTaxValidationType.APPROVED))
				{
					LOG.info("Document submission email trigger STARTttttttttttttt");
					final FMCustomerModel fmCustomerModel = (FMCustomerModel) this.getCurrentSessionCustomer();
					final FMB2SBTaxAdminApprovalProcessModel fmB2SBTaxAdminApprovalProcessModel = new FMB2SBTaxAdminApprovalProcessModel(); //
					LOG.info("----------bbbbbbbbbbbbbbb----------fmB2SBTaxApprovalProcessModel::" + fmB2SBTaxAdminApprovalProcessModel);
					fmB2SBTaxAdminApprovalProcessModel.setEmailId(fmTaxDocumentsModel.getUploadedBy().getUid());
					fmB2SBTaxAdminApprovalProcessModel.setEndMessage("Approve the user");
					fmB2SBTaxAdminApprovalProcessModel.setSite(getBaseSiteService().getCurrentBaseSite());
					fmB2SBTaxAdminApprovalProcessModel.setStore(getBaseStoreService().getCurrentBaseStore());
					fmB2SBTaxAdminApprovalProcessModel.setLanguage(getCommonI18NService().getCurrentLanguage());
					fmB2SBTaxAdminApprovalProcessModel.setCustomer(fmCustomerModel);
					final FMB2SBTaxAdminApprovalProcessEvent fmB2SBTaxAdminApprovalProcessEvent = new FMB2SBTaxAdminApprovalProcessEvent(
							fmB2SBTaxAdminApprovalProcessModel);

					LOG.info("-------DDDDDDDDDDDDDDDD--before publishing the event ------FMB2SBTaxApprovalProcessEvent::"
							+ fmB2SBTaxAdminApprovalProcessEvent + "----fmcustomerModel::");

					eventService.publishEvent(initializeEvent(fmB2SBTaxAdminApprovalProcessEvent, fmCustomerModel));

					LOG.info("Document submission email trigger ENDdddddddddddd");
				}
				return true;

			}
			else
			{
				LOG.info("UPDATE FAILED#1");

				return false;
			}
		}
		else
		{
			LOG.info("UPDATE FAILED#2");
			return false;
		}
	}

	/**
	 * Method to create the users in LDAP
	 *
	 * @param customerData
	 * @return
	 */
	public String createNewCustomer(final FMCustomerData customerData)
	{

		final String newCustomer = "cn=" + customerData.getEmail() + "," + Config.getParameter("ldap.customer.root.dn");
		final Attributes newAttributes = new BasicAttributes(true);
		final Attribute oc = new BasicAttribute("objectclass");
		oc.add("top");
		oc.add("person");
		oc.add("organizationalperson");
		oc.add("user");
		newAttributes.put(oc);
		newAttributes.put(new BasicAttribute("mail", customerData.getEmail()));
		newAttributes.put(new BasicAttribute("uid", customerData.getEmail()));
		newAttributes.put(new BasicAttribute("cn", customerData.getEmail()));
		newAttributes.put(new BasicAttribute("sn", customerData.getLastName()));
		newAttributes.put(new BasicAttribute("givenName", customerData.getFirstName()));
		newAttributes.put(new BasicAttribute("displayName", customerData.getFirstName() + " " + customerData.getLastName()));
		newAttributes.put(new BasicAttribute("fmoSmPasswordData", customerData.getPassword()));
		newAttributes.put(new BasicAttribute("userPassword", customerData.getPassword()));

		try
		{
			getLDAPDirContext().createSubcontext(newCustomer, newAttributes);
			return newCustomer;
		}
		catch (final Exception e)
		{
			LOG.error("create error: " + e.getMessage());

			return "NA";
		}
	}


	/**
	 * Method creates the LDAP context
	 *
	 * @return DirContext LDAP context
	 * @throws NamingException
	 */
	private DirContext getLDAPDirContext() throws NamingException
	{
		final String userDn = Config.getParameter("ldap.jndi.principals");//"cn=hybris-svc,o=fmoweb";
		final String ldapPassword = Config.getParameter("ldap.jndi.credentials");//"Passw0rd1";
		final String ldapAdServer = "ldap://" + Config.getParameter("ldap.server.url");//"ldap://sfldmims107:389";

		final Hashtable<String, Object> env = new Hashtable<String, Object>();
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, userDn);
		env.put(Context.SECURITY_CREDENTIALS, ldapPassword);

		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, ldapAdServer);

		// ensures that objectSID attribute values
		// will be returned as a byte[] instead of a String
		env.put("java.naming.ldap.attributes.binary", "objectSID");

		return new InitialDirContext(env);
	}

	@Override
	public void changeFMPassword(final String oldPassword, final String newPassword) throws PasswordMismatchException
	{
		final FMCustomerModel currentUser = (FMCustomerModel) getUserService().getCurrentUser();
		try
		{

			if (currentUser.getLdapaccount() != null)
			{
				getFmCustomerAccountService().changeFMPassword(currentUser, oldPassword, newPassword);
				LOG.info("Updating password in LDAP for " + currentUser.getLdaplogin());
				FMFacadeUtility.updateLDAPPassword(currentUser, newPassword);
			}

			LOG.info("Updated the customer password successfully");
		}
		catch (final de.hybris.platform.commerceservices.customer.PasswordMismatchException e)
		{
			throw new PasswordMismatchException(e);
		}

	}


	@Override
	public FMTaxDocumentData convertFileToFMTaxDocumentModel(final MultipartFile file, final String custUploadFilePath,
			final String dateFormate)
	{
		/* Newly Implemented code for upload */

		FMTaxDocumentData fmTaxDocumentData = null;
		try
		{
			if (file != null && !file.isEmpty() && file.getSize() > 0)
			{
				LOG.info("****** fileOriginalName: " + file.getOriginalFilename());
				fmTaxDocumentData = new FMTaxDocumentData();
				fmTaxDocumentData.setDocname(file.getOriginalFilename());
				fmTaxDocumentData.setFile(file);
				fmTaxDocumentData.setRealFileName(file.getOriginalFilename());
				fmTaxDocumentData.setCode(file.getOriginalFilename());
				LOG.info("##############fmTaxDocumentData Object Created########");
				return fmTaxDocumentData;
			}
		}
		catch (final Exception ex)
		{
			LOG.error(ex.getMessage());
			return fmTaxDocumentData;
		}
		return fmTaxDocumentData;
	}

	@Override
	public FMTaxDocumentModel getFMTaxDocumentsForPK(final String taxDocPK)
	{
		final FMTaxDocumentModel fmTaxDocumentModel = getDefaultFMCustomerAccountDAO().getFMTaxDocumentsForPK(taxDocPK);
		return fmTaxDocumentModel;
	}


	@Override
	public boolean fmTaxDocumentFileDownload(final String taxDocumentPK)
	{

		boolean fileDownloadStatus = false;

		final String downloadDirectory = "C:\\FMTaxDownload";

		final File dir = new File(downloadDirectory);

		if (!dir.exists())
		{
			dir.mkdirs();
		}

		final FMTaxDocumentModel fmTaxDocumentModel = getFMTaxDocumentsForPK(taxDocumentPK);

		LOG.info("fmTaxDocumentFileDownload in Facade :: " + fmTaxDocumentModel.getURL());

		@SuppressWarnings("deprecation")
		URL url = null;
		try
		{
			url = new URL("file://" + fmTaxDocumentModel.getURL());
			LOG.info("######11111######### :: PATH " + url.getPath());
		}
		catch (final MalformedURLException e1)
		{
			LOG.info("######2222#########");
			LOG.error(e1.getMessage());
			fileDownloadStatus = false;
		}

		File file;
		try
		{
			LOG.info("url.toURI() :####################### " + url.toURI());
			file = new File(url.toURI());


		}
		catch (final URISyntaxException e)
		{
			LOG.info("######33333######### :::  " + url.getPath());

			file = new File(url.getPath());
			fileDownloadStatus = false;
		}

		final DiskFileItem fileItem = new DiskFileItem("file", fmTaxDocumentModel.getMime(), false, file.getName(),
				(int) file.length(), file.getParentFile());
		try
		{
			fileItem.getOutputStream();
		}
		catch (final IOException e1)
		{
			LOG.info("######444444#########");

			file = new File(url.getPath());
			fileDownloadStatus = false;
			LOG.error(e1.getMessage());
		}
		final MultipartFile minputfile = new CommonsMultipartFile(fileItem);
		try
		{
			String line = "";
			final InputStream inputStream = minputfile.getInputStream();
			final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			final BufferedWriter outwriter = new BufferedWriter(new FileWriter(new File(downloadDirectory,
					fmTaxDocumentModel.getDocname())));
			LOG.info("after while block" + line.getBytes().length);
			while (bufferedReader.readLine() != null)
			{
				line = bufferedReader.readLine();
				LOG.info("after while block ###### Size ::: " + line.getBytes().length);
				LOG.info("");
				LOG.info(line);
				outwriter.write(line);
			}
			LOG.info("after while block" + line);
			outwriter.close();
			bufferedReader.close();
			inputStream.close();
			LOG.info(":::::::::::::::::::::::::::::::::FILE DOWNLOAD SUCCESS::::::::::::::::::::::::::::::");
			fileDownloadStatus = true;

		}
		catch (final IOException e)
		{
			LOG.info(e);
			fileDownloadStatus = false;
		}
		return fileDownloadStatus;

	}

	@Override
	public List<RegionData> getTaxApprovedRegions(final FMCustomerAccountData fmunit)
	{

		final List<RegionData> taxapprovedregions = new ArrayList<RegionData>();
		final List<FMTaxDocumentData> newtaxdocsdata = fmunit.getTaxDocumentList();
		final Iterator taxdocsmodeliterator = newtaxdocsdata.iterator();
		while (taxdocsmodeliterator.hasNext())
		{
			final FMTaxDocumentData newtaxdocdata = (FMTaxDocumentData) taxdocsmodeliterator.next();
			if (newtaxdocdata.getValidate() != null)
			{
				if ("APPROVED".equals(newtaxdocdata.getValidate()))
				{
					taxapprovedregions.add(newtaxdocdata.getState());
				}

			}
		}
		return taxapprovedregions;

	}

	@Override
	public boolean checkUidExists(final String email)
	{
		final UserModel currentUserModel = userService.getCurrentUser();
		sessionService.setAttribute(SessionContext.USER, userService.getAdminUser());


		final boolean checkid = fmcustomerservice.checkUidExists(email);
		LOG.info("Result from DAO - facade" + checkid);
		sessionService.setAttribute(SessionContext.USER, currentUserModel);

		return checkid;
	}

	/**
	 *
	 * @param fmTaxDocument
	 * @return
	 *
	 *         Implemented this functionality for save the File as Media Object
	 */

	public FMTaxDocumentModel saveFileASMediaModel(final FMTaxDocumentData fmTaxDocument)
	{

		FMTaxDocumentModel fmTaxDocumentModel = null;
		try
		{

			final MultipartFile file = fmTaxDocument.getFile();

			if (file != null && !file.isEmpty() && file.getSize() > 0)
			{
				final byte[] bytes = file.getBytes();
				LOG.info("$$$$$$$$$$$$$$$$$ 11111 $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ SIZE :: " + bytes.length);
				fmTaxDocumentModel = new FMTaxDocumentModel();
				fmTaxDocumentModel.setCode(file.getOriginalFilename());
				//media.setDocname(file.getOriginalFilename());
				LOG.info("$$$$$$$$$$$$$$$$$ 3333 4 $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
				final CatalogModel cm = catalogService.getCatalogForId("federalmogulContentCatalog");
				LOG.info("$$$$$$$$$$$$$$$$$ 3333 5 $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
				final Set catalogModelSet = cm.getCatalogVersions();
				if (catalogModelSet != null)
				{
					final Iterator itr = catalogModelSet.iterator();
					final CatalogVersionModel catalogVersionModel = (CatalogVersionModel) itr.next();
					LOG.info("#### getCategorySystemID " + catalogVersionModel.getCategorySystemID());
					LOG.info("#### getVersion " + catalogVersionModel.getVersion());
					LOG.info("#### getVersion " + catalogVersionModel.getCatalog());
					LOG.info("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$00 catalogVersionModel :: " + catalogVersionModel);
					fmTaxDocumentModel.setCatalogVersion(catalogVersionModel);
				}
				LOG.info("$$$$$$$$$$$$$$$$$ 4444 $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");

				fmTaxDocumentModel.setDescription(file.getOriginalFilename());
				modelService.save(fmTaxDocumentModel);
				LOG.info("$$$$$$$$$$$$$$$$$ 5555 $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");

				try
				{
					final InputStream inputStream = new ByteArrayInputStream(bytes);
					mediaService.setStreamForMedia(fmTaxDocumentModel, inputStream, file.getOriginalFilename(), file.getContentType());
				}
				catch (final Exception e)
				{
					LOG.error(e.getMessage());
				}

				LOG.info("$$$$$$$$$$$$$$$$$ 6666 $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");

				return fmTaxDocumentModel;

			}
		}
		catch (final Exception ex)
		{
			LOG.error(ex.getMessage());
			return fmTaxDocumentModel;
		}

		return fmTaxDocumentModel;
	}


	@Override
	public void fmUpdatePassword(final String token, final String newPassword) throws TokenInvalidatedException
	{
		LOG.info("$$$$$$$$$$$$$$$$$$$$Before updating password::zzzzzzzzzzzzzzzzzzz---token:-" + token
				+ "$$$$$$$$$$$$newpassword---" + newPassword);
		fmCustomerAccountService.fmUpdatePassword(token, newPassword);


	}

	//forgot password code
	@Override
	public boolean checkTaxIdExists(final String taxId)
	{
		final boolean checkTaxid = fmcustomerservice.checkTaxIdExists(taxId);
		LOG.info("#############Result from DAO - facade -- For Check Tax ID" + checkTaxid);
		return checkTaxid;
	}

	@Override
	public B2BUnitData getSoldToUnit(final String uid)
	{
		// YTODO Auto-generated method stub
		final B2BUnitData soldToUnit = getB2BUnitConverter().convert(
				companyB2BCommerceService.getParentUnit(companyB2BCommerceService.getUnitForUid(uid)));
		return soldToUnit;
	}

	@Override
	public AddressData getAddressByID(final String addressID)
	{
		final AddressData addressData = new AddressData();
		final AddressModel addressModel = fmCustomerAccountService.getAddressByID(addressID);
		addressModel.getOwner();
		//delete the model
		getAddressPopulator().populate(addressModel, addressData);
		return addressData;
	}

	@Override
	public void removeAdminAddress(final String addressID)
	{
		final AddressModel addressModel = fmCustomerAccountService.getAddressByID(addressID);
		//delete the model
		fmCustomerAccountService.removeAdminAddress(addressModel);
	}

	@Override
	public void editAdminAddress(final AddressData addressData)
	{
		final AddressModel addressModel = fmCustomerAccountService.getAddressByID(addressData.getId());
		getAddressReversePopulator().populate(addressData, addressModel);
		LOG.info("************company::" + addressModel.getCompany());
		fmCustomerAccountService.saveAdminAddressEntry(addressModel);
	}

	@Override
	public List<FMCustomerAccountData> getPartnerAddress()
	{
		final List<FMCustomerAccountData> result = new ArrayList<FMCustomerAccountData>();

		final FMCustomerModel customer = (FMCustomerModel) userService.getCurrentUser();
		final FMCustomerAccountModel fmAccount = (FMCustomerAccountModel) customer.getDefaultB2BUnit();
		LOG.info("default B2B UNIT " + fmAccount);
		final List<FMCustomerPartnerFunctionModel> partnermodel = fmAccount.getFmCustTargetAccountCode();
		final Iterator i1 = partnermodel.iterator();
		while (i1.hasNext())
		{
			final FMCustomerPartnerFunctionModel targetcode = (FMCustomerPartnerFunctionModel) i1.next();
			if (targetcode.getPartnerFMAccountType() != null)
			{
				if ("ShipTo".equals(targetcode.getPartnerFMAccountType()))
				{

					final FMCustomerAccountModel membersModel = targetcode.getFMTargetCustomerAccount();

					final List<FMB2bAddressData> shiptoAddress = new ArrayList<FMB2bAddressData>();

					if (membersModel != null)
					{
						final FMCustomerAccountData membersData = new FMCustomerAccountData();

						final List<AddressModel> listmembersFullAddress = (List<AddressModel>) membersModel.getAddresses();
						final Iterator i3 = listmembersFullAddress.iterator();
						while (i3.hasNext())
						{
							final AddressModel shiptomodel = (AddressModel) i3.next();
							final FMB2bAddressData shiptoaddressData = new FMB2bAddressData();
							getFmb2bAddressPopulator().populate(shiptomodel, shiptoaddressData);
							shiptoaddressData.setCompanyName(membersModel.getLocName());
							shiptoAddress.add(shiptoaddressData);
						}

						membersData.setUid(membersModel.getUid());
						membersData.setUnitAddress(shiptoAddress);
						membersData.setNabsAccountCode(membersModel.getNabsAccountCode());
						membersData.setDistributionChannel(membersModel.getDistributionChannel());
						membersData.setSalesorg(membersModel.getSalesorg());
						membersData.setDivision(membersModel.getDivision());
						result.add(membersData);
					}

				}
			}

		}
		return result;
	}

	@Override
	public List<FMCustomerAccountData> getPartnerAddressCSR(final FMCustomerAccountData accData)
	{
		final List<FMCustomerAccountData> result = new ArrayList<FMCustomerAccountData>();

		final FMCustomerAccountModel fmAccount = fmNetworkService.getAccountID(accData.getUid());


		final List<FMCustomerPartnerFunctionModel> partnermodel = fmAccount.getFmCustTargetAccountCode();
		final Iterator i1 = partnermodel.iterator();
		while (i1.hasNext())
		{
			final FMCustomerPartnerFunctionModel targetcode = (FMCustomerPartnerFunctionModel) i1.next();

			if (targetcode.getPartnerFMAccountType() != null)
			{
				if ("ShipTo".equals(targetcode.getPartnerFMAccountType()))
				{

					final FMCustomerAccountModel membersModel = targetcode.getFMTargetCustomerAccount();
					final List<FMB2bAddressData> shiptoaddress = new ArrayList<FMB2bAddressData>();

					if (membersModel != null)
					{
						final FMCustomerAccountData membersData = new FMCustomerAccountData();
						final List<AddressModel> listmembersFullAddress = (List<AddressModel>) membersModel.getAddresses();
						final Iterator i3 = listmembersFullAddress.iterator();
						while (i3.hasNext())
						{
							final AddressModel shiptomodel = (AddressModel) i3.next();
							final FMB2bAddressData shiptoaddressData = new FMB2bAddressData();
							getFmb2bAddressPopulator().populate(shiptomodel, shiptoaddressData);
							shiptoaddressData.setCompanyName(membersModel.getLocName());
							shiptoaddress.add(shiptoaddressData);
						}
						membersData.setUid(membersModel.getUid());
						membersData.setNabsAccountCode(membersModel.getNabsAccountCode());
						membersData.setDistributionChannel(membersModel.getDistributionChannel());
						membersData.setDivision(membersModel.getDivision());
						membersData.setSalesorg(membersModel.getSalesorg());
						membersData.setUnitAddress(shiptoaddress);
						result.add(membersData);

					}
				}
			}
		}
		return result;
	}

	@Override
	public List<FMCustomerAccountData> getMultipleSoldTos(final String unitID)
	{
		final List<FMCustomerAccountData> result = new ArrayList<FMCustomerAccountData>();
		final FMCustomerAccountModel fmAccount = (FMCustomerAccountModel) companyB2BCommerceService.getUnitForUid(unitID);
		LOG.info("default B2B UNIT " + fmAccount.getUid());
		final List<FMCustomerPartnerFunctionModel> partnermodel = fmAccount.getFmCustTargetAccountCode();
		for (final FMCustomerPartnerFunctionModel sourceCode : partnermodel)
		{
			if (sourceCode.getPartnerFMAccountType() != null)
			{
				if ("sold-to".equals(sourceCode.getPartnerFMAccountType()))
				{
					LOG.info("INSIDE       >>>>>>>>>>>>>>>>>>>>>>>> MULTIPLE SOLD TO");
					final FMCustomerAccountModel soldToModel = sourceCode.getFMTargetCustomerAccount();
					final FMCustomerAccountData soldtoData = new FMCustomerAccountData();
					getFmCustomerAccountPopulator().populate(soldToModel, soldtoData);
					result.add(soldtoData);
				}
			}
		}
		return result;
	}

	@Override
	public List<FMCustomerAccountData> getMultipleSoldTosCSR(final FMCustomerAccountData accData)
	{
		final List<FMCustomerAccountData> result = new ArrayList<FMCustomerAccountData>();
		final FMCustomerAccountModel fmAccount = fmNetworkService.getAccountID(accData.getUid());
		final List<FMCustomerPartnerFunctionModel> partnermodel = fmAccount.getFmCustTargetAccountCode();
		final Iterator i1 = partnermodel.iterator();
		while (i1.hasNext())
		{
			final FMCustomerPartnerFunctionModel sourceCode = (FMCustomerPartnerFunctionModel) i1.next();
			if (sourceCode.getPartnerFMAccountType() != null)
			{
				if ("sold-to".equals(sourceCode.getPartnerFMAccountType()))
				{
					LOG.info("INSIDE       >>>>>>>>>>>>>>>>>>>>>>>> MULTIPLE SOLD TO");
					final FMCustomerAccountModel soldToModel = sourceCode.getFMTargetCustomerAccount();
					final List<FMB2bAddressData> soldtoaddress = new ArrayList<FMB2bAddressData>();
					final FMCustomerAccountData soldtoData = new FMCustomerAccountData();
					final List<AddressModel> listSoldToFullAddress = (List<AddressModel>) soldToModel.getAddresses();
					final Iterator i3 = listSoldToFullAddress.iterator();
					while (i3.hasNext())
					{
						final AddressModel soldToAddresstomodel = (AddressModel) i3.next();
						final FMB2bAddressData soldToAddressData = new FMB2bAddressData();
						getFmb2bAddressPopulator().populate(soldToAddresstomodel, soldToAddressData);
						soldToAddressData.setCompanyName(soldToModel.getLocName());
						soldtoaddress.add(soldToAddressData);
					}

					soldtoData.setUid(soldToModel.getUid());
					soldtoData.setUnitAddress(soldtoaddress);
					result.add(soldtoData);
				}
			}
		}
		return result;
	}

	/**
	 * @return the addressPopulator
	 */
	public Populator<AddressModel, AddressData> getAddressPopulator()
	{
		return addressPopulator;
	}

	/**
	 * @param addressPopulator
	 *           the addressPopulator to set
	 */
	public void setAddressPopulator(final Populator<AddressModel, AddressData> addressPopulator)
	{
		this.addressPopulator = (AddressPopulator) addressPopulator;
	}

	@Override
	public List<AddressData> getSoldToUnitAddress(final String uid)
	{
		final UserModel currentUserModel = userService.getCurrentUser();

		sessionService.setAttribute(SessionContext.USER, userService.getAdminUser());

		final List<AddressData> soldToAddressData = fmcustomerservice.getSoldToUnitAddress(uid);
		sessionService.setAttribute(SessionContext.USER, currentUserModel);
		return soldToAddressData;
	}

	@Override
	public List<FMCustomerAccountData> getSoldToShipToUnitUid(final String uid)
	{
		final List<FMCustomerAccountData> getFMUnitUid = fmcustomerservice.getSoldToShipToUnitUid(uid);
		return getFMUnitUid;
	}

	@Override
	public boolean validateStateWiseTaxDocument(final FMCustomerData fmcustomerdata, final String state)
	{

		LOG.info("#####  validateStateWiseTaxDocument Method ###################");
		final FMCustomerModel fmmodel = (FMCustomerModel) this.getCurrentSessionCustomer();
		final RegionModel regionModel = getCommonI18NService().getRegion(getCommonI18NService().getCountry("US"), state);
		return getDefaultFMCustomerAccountDAO().validateStateWiseTaxDocument(fmmodel, regionModel);
	}

	@Override
	public boolean createAdminAddress(final AddressData newAddress)
	{
		final FMCustomerAccountModel unitModel = this.getModelService().create(FMCustomerAccountModel.class);

		//
		unitModel.setName(newAddress.getFirstName());
		unitModel.setLocName(newAddress.getFirstName());

		final FMCustomerModel customer = (FMCustomerModel) getUserService().getCurrentUser();
		try
		{

			final UserRegistrationResponseDTO responseShipTo = b2bShipToCreationHelper.sendSOAPMessage(convertShipToDataToDTO(
					newAddress, customer.getDefaultB2BUnit().getUid()));
			if ("s".equalsIgnoreCase(responseShipTo.getSeverity()))
			{
				unitModel.setUid(responseShipTo.getResponseCode());
			}
			else
			{
				return false;
			}
		}
		catch (final SOAPException soapExce)
		{
			return false;
		}
		catch (final IOException soapExce)
		{
			return false;
		}
		catch (final ClassNotFoundException classnotfouond)
		{
			return false;
		}

		final AddressModel companyaddress = getModelService().create(AddressModel.class);
		LOG.info("B2b add address");
		companyaddress.setOwner(unitModel);
		companyaddress.setLine1(newAddress.getLine1());
		companyaddress.setLine1(newAddress.getLine2());

		UserModel currentUserModel = userService.getCurrentUser();
		sessionService.setAttribute(SessionContext.USER, userService.getAdminUser());
		getModelService().save(companyaddress);

		sessionService.setAttribute(SessionContext.USER, currentUserModel);

		final List<AddressModel> unitaddresses = new ArrayList<AddressModel>();
		unitaddresses.add(companyaddress);
		unitModel.setAddresses(unitaddresses);
		currentUserModel = userService.getCurrentUser();
		sessionService.setAttribute(SessionContext.USER, userService.getAdminUser());
		this.getModelService().save(unitModel);
		sessionService.setAttribute(SessionContext.USER, currentUserModel);

		final FMCustomerPartnerFunctionModel partnerFunctionModel = this.getModelService().create(
				FMCustomerPartnerFunctionModel.class);
		partnerFunctionModel.setCode(unitModel.getUid() + customer.getDefaultB2BUnit().getUid());
		partnerFunctionModel.setFMSourceCustomerAccount(unitModel);
		partnerFunctionModel.setFMTargetCustomerAccount((FMCustomerAccountModel) customer.getDefaultB2BUnit());
		final List<FMCustomerAccountModel> fmCus = new ArrayList<FMCustomerAccountModel>();
		fmCus.add((FMCustomerAccountModel) customer.getDefaultB2BUnit());
		partnerFunctionModel.setFmCustSourceAccountCode(fmCus);

		currentUserModel = userService.getCurrentUser();
		sessionService.setAttribute(SessionContext.USER, userService.getAdminUser());
		this.getModelService().save(partnerFunctionModel);
		sessionService.setAttribute(SessionContext.USER, currentUserModel);

		return true;
	}

	/**
	 * Method to convert the address to request DTO
	 *
	 * @param shipToAddress
	 * @param soldToID
	 * @return UserRegistrationRequestDTO object
	 */
	private UserRegistrationRequestDTO convertShipToDataToDTO(final AddressData shipToAddress, final String soldToID)
	{

		final UserRegistrationRequestDTO reqDTO = new UserRegistrationRequestDTO();
		reqDTO.setCompanyName(shipToAddress.getFirstName());
		reqDTO.setCity(shipToAddress.getTown());
		reqDTO.setState("MI");
		reqDTO.setCountry(shipToAddress.getCountry().getIsocode());
		reqDTO.setPostalCode(shipToAddress.getPostalCode());
		reqDTO.setCompStreetName1(shipToAddress.getLine1());
		reqDTO.setCompStreetName2(shipToAddress.getLine2());
		reqDTO.setTelephone(shipToAddress.getPhone());
		reqDTO.setSold_ShipTo(soldToID);
		reqDTO.setServiceName("MT_Hybris_ShipTo_Request");
		return reqDTO;

	}

	@Override
	public List<FMCsrAccountListModel> getFMCSRAccountList()
	{
		final UserModel currentUserModel = userService.getCurrentUser();

		sessionService.setAttribute(SessionContext.USER, userService.getAdminUser());
		final List<FMCsrAccountListModel> fmCsrAccountListModel = fmUserSearchDAO.getFMCSRAccountList(currentUserModel.getUid());
		sessionService.setAttribute(SessionContext.USER, currentUserModel);

		return fmCsrAccountListModel;
	}

	//for ajax
	@Override
	public List<FMCustomerAccountData> getB2BShipToAddressForSoldTo(final String inputString, final String accountID)
	{
		final List<FMCustomerAccountData> result = new ArrayList<FMCustomerAccountData>();
		final List<FMCustomerAccountData> result1 = new ArrayList<FMCustomerAccountData>();
		final FMCustomerAccountModel fmAccount = fmUserSearchDAO.getAccountID(accountID);
		final List<FMCustomerPartnerFunctionModel> partnermodel = fmAccount.getFmCustTargetAccountCode();
		final Iterator i1 = partnermodel.iterator();
		if (inputString != null && "getAll*".equals(inputString))
		{
			while (i1.hasNext())
			{
				LOG.info("Inside while loop");
				final FMCustomerPartnerFunctionModel targetcode = (FMCustomerPartnerFunctionModel) i1.next();
				final FMCustomerAccountModel membersModel = targetcode.getFMTargetCustomerAccount();
				if (membersModel != null)
				{
					final List<FMB2bAddressData> shiptoaddress = new ArrayList<FMB2bAddressData>();
					final FMCustomerAccountData membersData = new FMCustomerAccountData();
					final List<AddressModel> listmembersFullAddress = (List<AddressModel>) membersModel.getAddresses();
					final Iterator i3 = listmembersFullAddress.iterator();
					while (i3.hasNext())
					{
						final AddressModel shiptomodel = (AddressModel) i3.next();
						final FMB2bAddressData shiptoaddressData = new FMB2bAddressData();
						getFmb2bAddressPopulator().populate(shiptomodel, shiptoaddressData);
						shiptoaddressData.setCompanyName(membersModel.getLocName());

						shiptoaddress.add(shiptoaddressData);
					}

					membersData.setUid(membersModel.getUid());
					membersData.setNabsAccountCode(membersModel.getNabsAccountCode());
					membersData.setUnitAddress(shiptoaddress);
					result.add(membersData);
					result1.add(membersData);
				}
			}

			final Iterator checkIterator = result.iterator();
			while (checkIterator.hasNext())
			{
				LOG.info("INSIDE Iterator!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				final FMCustomerAccountData checkExistingAccount = (FMCustomerAccountData) checkIterator.next();
				LOG.info("UID" + checkExistingAccount.getUid());
				LOG.info("fmaccount UID" + fmAccount.getUid());
				if (fmAccount.getUid() != checkExistingAccount.getUid())
				{
					LOG.info("Inside IF !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! ");
					final List<FMB2bAddressData> soldtoaddress = new ArrayList<FMB2bAddressData>();
					final FMCustomerAccountData soldtoData = new FMCustomerAccountData();
					final List<AddressModel> listsoldtoFullAddress = (List<AddressModel>) fmAccount.getAddresses();
					LOG.info("fmaccount UID" + checkExistingAccount.getUid());
					final Iterator i4 = listsoldtoFullAddress.iterator();
					while (i4.hasNext())
					{
						final AddressModel soldtoaddressmodel = (AddressModel) i4.next();
						final FMB2bAddressData soldtoaddressData = new FMB2bAddressData();
						getFmb2bAddressPopulator().populate(soldtoaddressmodel, soldtoaddressData);
						soldtoaddressData.setCompanyName(checkExistingAccount.getName());
						soldtoaddress.add(soldtoaddressData);
					}
					soldtoData.setUid(checkExistingAccount.getUid());
					soldtoData.setNabsAccountCode(checkExistingAccount.getNabsAccountCode());
					soldtoData.setUnitAddress(soldtoaddress);
					result1.add(soldtoData);
				}
				else if (fmAccount.getUid() == checkExistingAccount.getUid())
				{
					break;
				}
			}
		}
		else
		{
			while (i1.hasNext())
			{
				LOG.info("Inside while loop");
				final FMCustomerPartnerFunctionModel targetcode = (FMCustomerPartnerFunctionModel) i1.next();
				final FMCustomerAccountModel membersModel = targetcode.getFMTargetCustomerAccount();
				int citySearchCounter = 0;
				if (membersModel != null)
				{
					final List<String> cities = new ArrayList<String>();
					final List<AddressModel> listmembersFullAddressCity = (List<AddressModel>) membersModel.getAddresses();

					final Iterator listmembersFullAddressIteratorCity = listmembersFullAddressCity.iterator();
					while (listmembersFullAddressIteratorCity.hasNext())
					{
						final AddressModel shiptomodel = (AddressModel) listmembersFullAddressIteratorCity.next();
						cities.add(shiptomodel.getTown());
					}
					if (cities.size() > 0)
					{
						final Iterator cityIterator = cities.iterator();
						while (cityIterator.hasNext())
						{
							final String eachCity = (String) cityIterator.next();
							if (eachCity != null && !eachCity.isEmpty() && inputString != null && !inputString.isEmpty())
							{
								if (eachCity.toUpperCase().indexOf(inputString.toUpperCase()) != -1)
								{
									citySearchCounter++;
								}
							}
						}
					}
					if (membersModel.getNabsAccountCode() != null)
					{
						if ((membersModel.getNabsAccountCode().toUpperCase().indexOf(inputString.toUpperCase()) != -1)
								|| (membersModel.getUid().toUpperCase().indexOf(inputString.toUpperCase()) != -1)
								|| (membersModel.getLocName().toUpperCase().indexOf(inputString.toUpperCase()) != -1)
								|| (citySearchCounter > 0))
						{
							final List<FMB2bAddressData> shiptoaddress = new ArrayList<FMB2bAddressData>();
							final FMCustomerAccountData membersData = new FMCustomerAccountData();
							final List<AddressModel> listmembersFullAddress = (List<AddressModel>) membersModel.getAddresses();
							final Iterator i3 = listmembersFullAddress.iterator();
							while (i3.hasNext())
							{
								final AddressModel shiptomodel = (AddressModel) i3.next();
								final FMB2bAddressData shiptoaddressData = new FMB2bAddressData();
								getFmb2bAddressPopulator().populate(shiptomodel, shiptoaddressData);
								shiptoaddressData.setCompanyName(membersModel.getLocName());
								shiptoaddress.add(shiptoaddressData);
							}
							membersData.setUid(membersModel.getUid());
							membersData.setNabsAccountCode(membersModel.getNabsAccountCode());
							membersData.setUnitAddress(shiptoaddress);
							result.add(membersData);
							result1.add(membersData);
						}
					}
				}
			}
			final Iterator checkIterator = result.iterator();
			while (checkIterator.hasNext())
			{
				LOG.info("INSIDE Iterator!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				final FMCustomerAccountData checkExistingAccount = (FMCustomerAccountData) checkIterator.next();
				LOG.info("UID" + checkExistingAccount.getUid());
				LOG.info("fmaccount UID" + fmAccount.getUid());
				if (fmAccount.getUid() != checkExistingAccount.getUid())
				{
					LOG.info("INSIDE NEW METHOD");
					int citySearchCounterFmaccount = 0;

					final List<String> citiesFmaccount = new ArrayList<String>();
					final List<AddressModel> listmembersFullAddressCityfmaccount = (List<AddressModel>) fmAccount.getAddresses();

					final Iterator listmembersFullAddressIteratorCityfmaccount = listmembersFullAddressCityfmaccount.iterator();
					while (listmembersFullAddressIteratorCityfmaccount.hasNext())
					{
						final AddressModel shiptomodel = (AddressModel) listmembersFullAddressIteratorCityfmaccount.next();
						citiesFmaccount.add(shiptomodel.getTown());
					}
					final Iterator cityIteratorFmaccount = citiesFmaccount.iterator();
					while (cityIteratorFmaccount.hasNext())
					{
						final String eachCity = (String) cityIteratorFmaccount.next();
						if (eachCity != null && !eachCity.isEmpty() && inputString != null && !inputString.isEmpty())
						{
							if (eachCity.toUpperCase().indexOf(inputString.toUpperCase()) != -1)
							{
								citySearchCounterFmaccount++;
							}
						}
					}

					if ((fmAccount.getNabsAccountCode().toUpperCase().indexOf(inputString.toUpperCase()) != -1)
							|| (fmAccount.getUid().toUpperCase().indexOf(inputString.toUpperCase()) != -1)
							|| (fmAccount.getLocName().toUpperCase().indexOf(inputString.toUpperCase()) != -1)
							|| (citySearchCounterFmaccount > 0))
					{
						final List<FMB2bAddressData> soldtoaddress = new ArrayList<FMB2bAddressData>();
						final FMCustomerAccountData soldtoData = new FMCustomerAccountData();
						final List<AddressModel> listsoldtoFullAddress = (List<AddressModel>) fmAccount.getAddresses();
						final Iterator i4 = listsoldtoFullAddress.iterator();
						while (i4.hasNext())
						{
							final AddressModel soldtoaddressmodel = (AddressModel) i4.next();
							final FMB2bAddressData soldtoaddressData = new FMB2bAddressData();
							getFmb2bAddressPopulator().populate(soldtoaddressmodel, soldtoaddressData);
							soldtoaddressData.setCompanyName(fmAccount.getLocName());
							soldtoaddress.add(soldtoaddressData);
						}
						soldtoData.setUid(fmAccount.getUid());
						soldtoData.setNabsAccountCode(fmAccount.getNabsAccountCode());
						soldtoData.setUnitAddress(soldtoaddress);
						result1.add(soldtoData);
					}
				}
				else if (fmAccount.getUid() == checkExistingAccount.getUid())
				{
					break;
				}
			}
		}

		return result1;
	}

	@Override
	public List<FMCustomerAccountData> getB2BSoldToAddressForSoldTo(final String inputString, final String accountID)
	{
		final List<FMCustomerAccountData> result = new ArrayList<FMCustomerAccountData>();
		final FMCustomerAccountModel fmAccount = fmUserSearchDAO.getAccountID(accountID);
		final List<FMCustomerPartnerFunctionModel> partnermodel = fmAccount.getFmCustTargetAccountCode();
		final Iterator i1 = partnermodel.iterator();
		if (inputString != null && "getAll*".equals(inputString))
		{
			while (i1.hasNext())
			{
				final FMCustomerPartnerFunctionModel sourceCode = (FMCustomerPartnerFunctionModel) i1.next();
				if (sourceCode.getPartnerFMAccountType() != null)
				{
					if ("sold-to".equals(sourceCode.getPartnerFMAccountType()))
					{
						final FMCustomerAccountModel soldToModel = sourceCode.getFMTargetCustomerAccount();
						if (soldToModel != null)
						{
							final List<FMB2bAddressData> soldtoaddress = new ArrayList<FMB2bAddressData>();
							final FMCustomerAccountData soldtoData = new FMCustomerAccountData();
							final List<AddressModel> listSoldToFullAddress = (List<AddressModel>) soldToModel.getAddresses();
							final Iterator i3 = listSoldToFullAddress.iterator();
							while (i3.hasNext())
							{
								final AddressModel soldToAddresstomodel = (AddressModel) i3.next();
								final FMB2bAddressData soldToAddressData = new FMB2bAddressData();
								getFmb2bAddressPopulator().populate(soldToAddresstomodel, soldToAddressData);
								soldToAddressData.setCompanyName(soldToModel.getLocName());
								soldtoaddress.add(soldToAddressData);
							}
							soldtoData.setUid(soldToModel.getUid());
							soldtoData.setNabsAccountCode(soldToModel.getNabsAccountCode());
							soldtoData.setUnitAddress(soldtoaddress);
							result.add(soldtoData);
						}
					}
				}
			}
		}
		else
		{
			while (i1.hasNext())
			{
				final FMCustomerPartnerFunctionModel sourceCode = (FMCustomerPartnerFunctionModel) i1.next();
				if (sourceCode.getPartnerFMAccountType() != null)
				{
					if ("sold-to".equals(sourceCode.getPartnerFMAccountType()))
					{
						final FMCustomerAccountModel soldToModel = sourceCode.getFMTargetCustomerAccount();
						if (soldToModel != null)
						{
							int citySearchCounterFmaccount = 0;

							final List<String> citiesFmaccount = new ArrayList<String>();
							final List<AddressModel> listmembersFullAddressCityfmaccount = (List<AddressModel>) soldToModel
									.getAddresses();

							final Iterator listmembersFullAddressIteratorCityfmaccount = listmembersFullAddressCityfmaccount.iterator();
							while (listmembersFullAddressIteratorCityfmaccount.hasNext())
							{
								final AddressModel shiptomodel = (AddressModel) listmembersFullAddressIteratorCityfmaccount.next();
								citiesFmaccount.add(shiptomodel.getTown());
							}
							if (citiesFmaccount.size() > 0)
							{
								final Iterator cityIteratorFmaccount = citiesFmaccount.iterator();
								while (cityIteratorFmaccount.hasNext())
								{
									final String eachCity = (String) cityIteratorFmaccount.next();

									if (eachCity != null && !eachCity.isEmpty() && inputString != null && !inputString.isEmpty())
									{
										if (eachCity.toUpperCase().indexOf(inputString.toUpperCase()) != -1)
										{
											citySearchCounterFmaccount++;
										}
									}
								}
							}
							if ((soldToModel.getNabsAccountCode().toUpperCase().indexOf(inputString.toUpperCase()) != -1)
									|| (soldToModel.getUid().toUpperCase().indexOf(inputString.toUpperCase()) != -1)
									|| (soldToModel.getLocName().toUpperCase().indexOf(inputString.toUpperCase()) != -1)
									|| (citySearchCounterFmaccount > 0))
							{
								final List<FMB2bAddressData> soldtoaddress = new ArrayList<FMB2bAddressData>();
								final FMCustomerAccountData soldtoData = new FMCustomerAccountData();
								final List<AddressModel> listSoldToFullAddress = (List<AddressModel>) soldToModel.getAddresses();
								final Iterator i3 = listSoldToFullAddress.iterator();
								while (i3.hasNext())
								{
									final AddressModel soldToAddresstomodel = (AddressModel) i3.next();
									final FMB2bAddressData soldToAddressData = new FMB2bAddressData();
									getFmb2bAddressPopulator().populate(soldToAddresstomodel, soldToAddressData);
									soldToAddressData.setCompanyName(soldToModel.getLocName());
									soldtoaddress.add(soldToAddressData);
								}
								soldtoData.setUid(soldToModel.getUid());
								soldtoData.setNabsAccountCode(soldToModel.getNabsAccountCode());
								soldtoData.setUnitAddress(soldtoaddress);
								result.add(soldtoData);
							}
						}
					}
				}
			}
		}
		return result;
	}

	@Override
	public List<FMCustomerAccountData> getB2BSoldToAddressForShipTo(final String inputString, final String accountID)
	{
		final List<FMCustomerAccountData> result = new ArrayList<FMCustomerAccountData>();

		final FMCustomerAccountModel fmAccount = fmUserSearchDAO.getAccountID(accountID);
		final List<FMCustomerAccountData> getFMUnitUid = fmcustomerservice.getSoldToShipToUnitUid(fmAccount.getUid());
		final Iterator i1 = getFMUnitUid.iterator();
		if (inputString != null && "getAll*".equals(inputString))
		{
			while (i1.hasNext())
			{
				final FMCustomerAccountData soldToAccdata = (FMCustomerAccountData) i1.next();
				if (soldToAccdata != null)
				{
					final FMCustomerAccountData soldtoData = new FMCustomerAccountData();
					final List<FMB2bAddressData> listSoldToFullAddress = soldToAccdata.getUnitAddress();
					soldtoData.setUid(soldToAccdata.getUid());
					soldtoData.setNabsAccountCode(soldToAccdata.getNabsAccountCode());
					soldtoData.setUnitAddress(listSoldToFullAddress);
					result.add(soldtoData);
				}
			}
		}
		else
		{
			while (i1.hasNext())
			{
				final FMCustomerAccountData soldToAccdata = (FMCustomerAccountData) i1.next();
				if (soldToAccdata != null)
				{
					int citySearchCounterFmaccountData = 0;

					final List<String> citiesFmaccount = new ArrayList<String>();
					final List<FMB2bAddressData> listmembersFullAddressCityfmaccount = soldToAccdata.getUnitAddress();

					final Iterator listmembersFullAddressIteratorCityfmaccount = listmembersFullAddressCityfmaccount.iterator();
					while (listmembersFullAddressIteratorCityfmaccount.hasNext())
					{
						final FMB2bAddressData shiptoData = (FMB2bAddressData) listmembersFullAddressIteratorCityfmaccount.next();
						citiesFmaccount.add(shiptoData.getTown());
					}
					final Iterator cityIteratorFmaccount = citiesFmaccount.iterator();
					while (cityIteratorFmaccount.hasNext())
					{
						final String eachCity = (String) cityIteratorFmaccount.next();
						if (eachCity != null && !eachCity.isEmpty() && inputString != null && !inputString.isEmpty())
						{
							if (eachCity.toUpperCase().indexOf(inputString.toUpperCase()) != -1)
							{
								citySearchCounterFmaccountData++;
							}
						}
					}

					if ((soldToAccdata.getNabsAccountCode().toUpperCase().indexOf(inputString.toUpperCase()) != -1)
							|| (soldToAccdata.getUid().toUpperCase().indexOf(inputString.toUpperCase()) != -1)
							|| (soldToAccdata.getName().toUpperCase().indexOf(inputString.toUpperCase()) != -1)
							|| (citySearchCounterFmaccountData > 0))
					{
						final FMCustomerAccountData soldtoData = new FMCustomerAccountData();
						final List<FMB2bAddressData> listSoldToFullAddress = soldToAccdata.getUnitAddress();
						soldtoData.setUid(soldToAccdata.getUid());
						soldtoData.setNabsAccountCode(soldToAccdata.getNabsAccountCode());
						soldtoData.setUnitAddress(listSoldToFullAddress);
						result.add(soldtoData);
					}
				}
			}
		}

		return result;
	}

	@Override
	public List<FMCustomerAccountData> getB2BShipToAddressForShipTo(final String inputString, final String accountID)
	{
		final List<FMCustomerAccountData> result = new ArrayList<FMCustomerAccountData>();
		final FMCustomerAccountModel fmAccount = fmUserSearchDAO.getAccountID(accountID);
		final List<FMCustomerAccountData> getFMUnitUid = fmcustomerservice.getSoldToShipToUnitUid(fmAccount.getUid());
		//to add shipto address to shipto search
		final FMCustomerAccountData addfmAccountData = new FMCustomerAccountData();
		final List<FMB2bAddressData> shiptoaddressData = new ArrayList<FMB2bAddressData>();
		final List<AddressModel> shiptoaddressModel = (List<AddressModel>) fmAccount.getAddresses();
		final Iterator i2 = shiptoaddressModel.iterator();
		while (i2.hasNext())
		{
			final AddressModel shipToAddresstomodel = (AddressModel) i2.next();
			final FMB2bAddressData shipToAddressData = new FMB2bAddressData();

			getFmb2bAddressPopulator().populate(shipToAddresstomodel, shipToAddressData);
			shipToAddressData.setCompanyName(fmAccount.getLocName());
			shiptoaddressData.add(shipToAddressData);
		}
		//because of Data, adding company name to 'Name'
		addfmAccountData.setName(fmAccount.getLocName());
		addfmAccountData.setUid(fmAccount.getUid());
		addfmAccountData.setNabsAccountCode(fmAccount.getNabsAccountCode());
		addfmAccountData.setUnitAddress(shiptoaddressData);
		getFMUnitUid.add(addfmAccountData);

		//start here
		final Iterator i1 = getFMUnitUid.iterator();
		if (inputString != null && "getAll*".equals(inputString))
		{
			while (i1.hasNext())
			{
				final FMCustomerAccountData soldToAccdata = (FMCustomerAccountData) i1.next();
				if (soldToAccdata != null)
				{
					final FMCustomerAccountData soldtoData = new FMCustomerAccountData();
					final List<FMB2bAddressData> listSoldToFullAddress = soldToAccdata.getUnitAddress();
					soldtoData.setUid(soldToAccdata.getUid());
					soldtoData.setNabsAccountCode(soldToAccdata.getNabsAccountCode());
					soldtoData.setUnitAddress(listSoldToFullAddress);
					result.add(soldtoData);
				}
			}
		}
		else
		{
			while (i1.hasNext())
			{
				final FMCustomerAccountData soldToAccdata = (FMCustomerAccountData) i1.next();
				if (soldToAccdata != null)
				{
					int citySearchCounterFmaccountData = 0;

					final List<String> citiesFmaccount = new ArrayList<String>();
					final List<FMB2bAddressData> listmembersFullAddressCityFmaccount = soldToAccdata.getUnitAddress();

					final Iterator listmembersFullAddressIteratorCityFmaccount = listmembersFullAddressCityFmaccount.iterator();
					while (listmembersFullAddressIteratorCityFmaccount.hasNext())
					{
						final FMB2bAddressData shiptoData = (FMB2bAddressData) listmembersFullAddressIteratorCityFmaccount.next();
						citiesFmaccount.add(shiptoData.getTown());
					}
					final Iterator cityIteratorFmaccount = citiesFmaccount.iterator();
					while (cityIteratorFmaccount.hasNext())
					{
						final String eachCity = (String) cityIteratorFmaccount.next();
						if (eachCity != null && !eachCity.isEmpty() && inputString != null && !inputString.isEmpty())
						{
							if (eachCity.toUpperCase().indexOf(inputString.toUpperCase()) != -1)
							{
								citySearchCounterFmaccountData++;
							}
						}
					}

					if ((soldToAccdata.getNabsAccountCode() != null) && (soldToAccdata.getName() != null))
					{
						if ((soldToAccdata.getNabsAccountCode().toUpperCase().indexOf(inputString.toUpperCase()) != -1)
								|| (soldToAccdata.getUid().toUpperCase().indexOf(inputString.toUpperCase()) != -1)
								|| (soldToAccdata.getName().toUpperCase().indexOf(inputString.toUpperCase()) != -1)
								|| (citySearchCounterFmaccountData > 0))
						{
							final FMCustomerAccountData soldtoData = new FMCustomerAccountData();
							final List<FMB2bAddressData> listSoldToFullAddress = soldToAccdata.getUnitAddress();
							soldtoData.setUid(soldToAccdata.getUid());
							soldtoData.setNabsAccountCode(soldToAccdata.getNabsAccountCode());
							soldtoData.setUnitAddress(listSoldToFullAddress);
							result.add(soldtoData);
						}
					}
				}
			}
		}
		return result;
	}
	@Override
	public List<FMCustomerAccountData> getB2BShipToAddressForSoldTo(final String inputString, final String accountID,
			final String searchType, final String loggedUserType)
	{
		// YTODO Auto-generated method stub
		final List<FMCustomerAccountData> shipToList = new ArrayList<FMCustomerAccountData>();
		final List<FMCustomerAccountModel> shipToAcc = fmUserSearchDAO.getB2BShipToAddressForSoldTo(inputString, accountID,
				searchType, loggedUserType);
		if (null == shipToAcc)
		{
			return null;
		}
		for (final FMCustomerAccountModel shipTo : shipToAcc)
		{
			final List<FMB2bAddressData> shiptoaddress = new ArrayList<FMB2bAddressData>();
			final FMCustomerAccountData shipToData = new FMCustomerAccountData();
			for (final AddressModel address : shipTo.getAddresses())
			{
				final FMB2bAddressData shiptoaddressData = new FMB2bAddressData();
				getFmb2bAddressPopulator().populate(address, shiptoaddressData);
				shiptoaddressData.setCompanyName(shipTo.getLocName());
				shipToData.setFMB2baddress(shiptoaddressData);
				shiptoaddress.add(shiptoaddressData);
			}
			shipToData.setUid(shipTo.getUid());
			shipToData.setNabsAccountCode(shipTo.getNabsAccountCode());
			shipToData.setUnitAddress(shiptoaddress);
			shipToList.add(shipToData);
		}
		return shipToList;
	}

	private boolean validateBeforeUpdate(final FMCustomerData customerData) {

		if (customerData != null && (customerData.getFirstName() != null && (!customerData.getFirstName().isEmpty())) 
				&& (customerData.getLastName() != null && (!customerData.getLastName().isEmpty())) 
				&& (customerData.getUid() != null && (!customerData.getUid().isEmpty()))) {
			return true;
		}

		return false;
	}


	private UserRegistrationRequestDTO convertDataToDTOForUpdate(final FMCustomerData fmcustomerdata) {

		final UserRegistrationRequestDTO reqDTO = new UserRegistrationRequestDTO();

		reqDTO.setServiceName("Request");
		reqDTO.setCustomerEmailID(fmcustomerdata.getEmail());
		LOG.info("convertDataToDTOForUpdate(...): " + CUSTOMERACCOUNTTYPES.get(fmcustomerdata.getUserTypeDescription()) + " "
				+ fmcustomerdata.getUserTypeDescription());
		reqDTO.setAccountCode("" + CUSTOMERACCOUNTTYPES.get(fmcustomerdata.getUserTypeDescription()));
		reqDTO.setFirstName(fmcustomerdata.getFirstName());
		reqDTO.setLastName(fmcustomerdata.getLastName());
		
		if (fmcustomerdata.getDefaultShippingAddress() != null) {
			reqDTO.setStreetName1(fmcustomerdata.getDefaultShippingAddress().getLine1());
			reqDTO.setStreetName2(fmcustomerdata.getDefaultShippingAddress().getLine2());
			reqDTO.setCity(fmcustomerdata.getDefaultShippingAddress().getTown());
			if (fmcustomerdata.getDefaultShippingAddress().getRegion() != null) {
				final int len = fmcustomerdata.getDefaultShippingAddress().getRegion().getIsocode().length();
				LOG.info("convertDataToDTOForUpdate(...): state: " + fmcustomerdata.getDefaultShippingAddress().getRegion().getIsocode());
				LOG.info("convertDataToDTOForUpdate(...): LENGTH: " + len);
				reqDTO.setState((fmcustomerdata.getDefaultShippingAddress().getRegion().getIsocode()).substring(3, 5));
				LOG.info("convertDataToDTOForUpdate(...): reqDTO.state: " + reqDTO.getState());
			}
			reqDTO.setPostalCode(fmcustomerdata.getDefaultShippingAddress().getPostalCode());
			if (fmcustomerdata.getDefaultShippingAddress().getCountry() != null) {
				reqDTO.setCountry(fmcustomerdata.getDefaultShippingAddress().getCountry().getIsocode());
			}
			reqDTO.setTelephone(fmcustomerdata.getDefaultShippingAddress().getPhone());
		} else {
			reqDTO.setPostalCode("");
		}

		if (fmcustomerdata.getNewsLetterSubscription() != null && fmcustomerdata.getNewsLetterSubscription()) {
			reqDTO.setNewsletterFlag(FLAG_X);
		} else {
			reqDTO.setNewsletterFlag("");
		}

		if (fmcustomerdata.getUnit() != null) {
			reqDTO.setSold_ShipTo(fmcustomerdata.getUnit().getUid());
		} else {
			reqDTO.setSold_ShipTo("");
		}
		
		LOG.info("convertDataToDTOForUpdate(...): Customer Type for Fmusertype '" + fmcustomerdata.getUserTypeDescription() +
					"': " + getCustomerTypeForFMUserType(fmcustomerdata));
		reqDTO.setCustomerType(getCustomerTypeForFMUserType(fmcustomerdata));
		if (Fmusertype.REPAIRSHOPOWNER.equals(fmcustomerdata.getUserTypeDescription())
				|| Fmusertype.TECHNICIAN.equals(fmcustomerdata.getUserTypeDescription())
				|| Fmusertype.STUDENT.equals(fmcustomerdata.getUserTypeDescription())
				|| Fmusertype.SHOPOWNERTECHNICIAN.equals(fmcustomerdata.getUserTypeDescription())) {

			if (fmcustomerdata.getB2baddress() != null) {
				reqDTO.setCompanyName(fmcustomerdata.getB2baddress().getCompanyName());
				reqDTO.setTaxNumber(fmcustomerdata.getTaxID());
				reqDTO.setCompStreetName1(fmcustomerdata.getB2baddress().getLine1());
				reqDTO.setCompStreetName2(fmcustomerdata.getB2baddress().getLine2());
				reqDTO.setCompCity(fmcustomerdata.getB2baddress().getTown());

				if (fmcustomerdata.getB2baddress().getRegion() != null) {
					LOG.info("convertDataToDTOForUpdate(...): STATE: " + fmcustomerdata.getB2baddress().getRegion().getIsocode());
					reqDTO.setCompState((fmcustomerdata.getB2baddress().getRegion().getIsocode()).substring(3, 5));
					LOG.info("convertDataToDTOForUpdate(...): STATE COMPANY: " + reqDTO.getCompState());
				}

				reqDTO.setCompPostCode(fmcustomerdata.getB2baddress().getPostalCode());
				if (fmcustomerdata.getB2baddress().getCountry() != null) {
					reqDTO.setCompCountry(fmcustomerdata.getB2baddress().getCountry().getIsocode());
				}
			} else {
				reqDTO.setCompPostCode("");
			}

			if (fmcustomerdata.getLoyaltySignup()) {
				reqDTO.setLoyaltyEnrollmentFlag(FLAG_X);
			}

			reqDTO.setLmsId(fmcustomerdata.getLmsSigninId());
			reqDTO.setPromoCode(fmcustomerdata.getPromoCode());
			if (fmcustomerdata.getUniqueID() != null) {
				if (fmcustomerdata.getUniqueID().size() > 0) {
					reqDTO.setUniqueID(fmcustomerdata.getUniqueID());
				}
			}

			if (fmcustomerdata.getFmunit() != null) {
				reqDTO.setProspectID("");
				LOG.info("convertDataToDTOForUpdate(...): ProspectId: " + fmcustomerdata.getFmunit().getProspectId());
				reqDTO.setCustomerContactId(fmcustomerdata.getFmunit().getUid());
			}
		}

		if (Fmusertype.CONSUMERDIFM.equals(fmcustomerdata.getUserTypeDescription())
				|| Fmusertype.CONSUMERDIY.equals(fmcustomerdata.getUserTypeDescription())
				|| Fmusertype.CONSUMER.equals(fmcustomerdata.getUserTypeDescription())) {
			reqDTO.setSold_ShipTo("");
		}

		if (fmcustomerdata.getLoyaltySignup() != null && fmcustomerdata.getLoyaltySignup()) {
			reqDTO.setLoyaltyEnrollmentFlag(FLAG_X);
		}

		if (fmcustomerdata.getTechAcademySubscription() != null && fmcustomerdata.getTechAcademySubscription()) {
			reqDTO.setTrainingParticiptaionFlag(FLAG_X);
		}

		if (fmcustomerdata.getNewProductAlerts() != null && fmcustomerdata.getNewProductAlerts()) {
			reqDTO.setNewProductAlert(FLAG_X);
		}

		if (fmcustomerdata.getPromotionInfoSubscription() != null && fmcustomerdata.getPromotionInfoSubscription()) {
			reqDTO.setNewPromotionFlag(FLAG_X);
		}

		if (fmcustomerdata.getReferEmailId() != null) {
			reqDTO.setReferralEmailId(fmcustomerdata.getReferEmailId());
		}

		// Indicate to SAP CRM that the user's profile is being updated so the user gets Garage Reward points for that activity!!
		reqDTO.setProfileUpdateFlag(FLAG_X);

		return reqDTO;
	}

	private String getCustomerTypeForFMUserType(FMCustomerData fmcustomerdata) {
		String customerType = null;

		Fmusertype userType = fmcustomerdata.getUserTypeDescription();
		
		switch (userType) {
			case WAREHOUSEDISTRIBUTORCOMMERCIALVEHICLE:
				customerType = CUSTOMER_TYPE_CODE_WAREHOUSE_DISTRIBUTOR_COMMERCIAL_VEHICLE;
				break;
			case WAREHOUSEDISTRIBUTORLIGHTVEHICLE:
				customerType = CUSTOMER_TYPE_CODE_WAREHOUSE_DISTRIBUTOR_LIGHT_VEHICLE;
				break;
			case RETAILER:
				customerType = CUSTOMER_TYPE_CODE_RETAILER;
				break;
			case JOBBERPARTSTORE:
				customerType = CUSTOMER_TYPE_CODE_JOBBER;
				break;
			case SHOPOWNERTECHNICIAN:
			case REPAIRSHOPOWNER:
				customerType = CUSTOMER_TYPE_CODE_REPAIR_SHOP_OWNER;
				break;
			case TECHNICIAN:
				customerType = CUSTOMER_TYPE_CODE_TECHNICIAN;
				break;
			case STUDENT:
				customerType = CUSTOMER_TYPE_CODE_STUDENT;
				break;
			case CONSUMER:
				customerType = CUSTOMER_TYPE_CODE_CONSUMER;
				break;
			case CONSUMERDIFM:
			case CONSUMERDIY:
				customerType = CUSTOMER_TYPE_CODE_B2C;
				break;
			default:
				customerType = CUSTOMER_TYPE_CODE_B2B;
		}

		return customerType;
	}

	@Override
	public String checkSoldToShipto(final String uid)
	{
		final String soldtoshiptostring = fmcustomerservice.checkSoldToShipto(uid);
		return soldtoshiptostring;
	}

	/**
	 * @return the fmCustomerAccountConverter
	 */
	public Converter<FMCustomerAccountModel, FMCustomerAccountData> getFmCustomerAccountConverter()
	{
		return fmCustomerAccountConverter;
	}

	/**
	 * @param fmCustomerAccountConverter
	 *           the fmCustomerAccountConverter to set
	 */
	public void setFmCustomerAccountConverter(
			final Converter<FMCustomerAccountModel, FMCustomerAccountData> fmCustomerAccountConverter)
	{
		this.fmCustomerAccountConverter = fmCustomerAccountConverter;
	}

	/**
	 * @return the fmCustomerAccountPopulator
	 */
	public Populator<FMCustomerAccountModel, FMCustomerAccountData> getFmCustomerAccountPopulator()
	{
		return fmCustomerAccountPopulator;
	}

	/**
	 * @param fmCustomerAccountPopulator
	 *           the fmCustomerAccountPopulator to set
	 */
	public void setFmCustomerAccountPopulator(
			final Populator<FMCustomerAccountModel, FMCustomerAccountData> fmCustomerAccountPopulator)
	{
		this.fmCustomerAccountPopulator = fmCustomerAccountPopulator;
	}

	/**
	 * @param addressPopulator
	 *           the addressPopulator to set
	 */
	public void setAddressPopulator(final AddressPopulator addressPopulator)
	{
		this.addressPopulator = addressPopulator;
	}

	/**
	 * @return the b2BUnitConverter
	 */
	public Converter<B2BUnitModel, B2BUnitData> getB2BUnitConverter()
	{
		return b2BUnitConverter;
	}

	/**
	 * @param b2bUnitConverter
	 *           the b2BUnitConverter to set
	 */
	public void setB2BUnitConverter(final Converter<B2BUnitModel, B2BUnitData> b2bUnitConverter)
	{
		b2BUnitConverter = b2bUnitConverter;
	}

	/**
	 * @return the fmb2bAddressPopulator
	 */
	public Populator<AddressModel, FMB2bAddressData> getFmb2bAddressPopulator()
	{
		return fmb2bAddressPopulator;
	}

	/**
	 * @param fmb2bAddressPopulator
	 *           the fmb2bAddressPopulator to set
	 */
	public void setFmb2bAddressPopulator(final Populator<AddressModel, FMB2bAddressData> fmb2bAddressPopulator)
	{
		this.fmb2bAddressPopulator = fmb2bAddressPopulator;
	}

	/**
	 * @return the fmNetworkService
	 */
	public FMNetworkService getFmNetworkService()
	{
		return fmNetworkService;
	}

	/**
	 * @param fmNetworkService
	 *           the fmNetworkService to set
	 */
	public void setFmNetworkService(final FMNetworkService fmNetworkService)
	{
		this.fmNetworkService = fmNetworkService;
	}

}
