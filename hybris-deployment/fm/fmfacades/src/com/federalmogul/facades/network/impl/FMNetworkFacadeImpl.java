/**
 *
 */
package com.federalmogul.facades.network.impl;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNull;

import de.hybris.platform.b2b.constants.B2BConstants;
import de.hybris.platform.b2b.model.B2BCostCenterModel;
import de.hybris.platform.b2b.model.B2BCustomerModel;
import de.hybris.platform.b2b.model.B2BUnitModel;
import de.hybris.platform.b2b.services.B2BUnitService;
import de.hybris.platform.b2bacceleratorfacades.company.impl.DefaultCompanyB2BCommerceFacade;
import de.hybris.platform.b2bacceleratorfacades.order.data.B2BUnitData;
import de.hybris.platform.b2bacceleratorfacades.order.populators.B2BUnitPopulator;
import de.hybris.platform.b2bacceleratorservices.company.B2BCommerceB2BUserGroupService;
import de.hybris.platform.b2bacceleratorservices.company.CompanyB2BCommerceService;
import de.hybris.platform.b2bacceleratorservices.strategies.B2BUserGroupsLookUpStrategy;
import de.hybris.platform.commercefacades.user.converters.populator.AddressPopulator;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commercefacades.user.data.PrincipalData;
import de.hybris.platform.commerceservices.customer.CustomerAccountService;
import de.hybris.platform.commerceservices.customer.DuplicateUidException;
import de.hybris.platform.commerceservices.event.AbstractCommerceUserEvent;
import de.hybris.platform.commerceservices.search.pagedata.PageableData;
import de.hybris.platform.commerceservices.search.pagedata.SearchPageData;
import de.hybris.platform.commerceservices.strategies.CustomerNameStrategy;
import de.hybris.platform.converters.Converters;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.security.PrincipalGroupModel;
import de.hybris.platform.core.model.security.PrincipalModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.TitleModel;
import de.hybris.platform.core.model.user.UserGroupModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.util.Config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;
import javax.xml.soap.SOAPException;

import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.federalmogul.core.enums.Fmusertype;
import com.federalmogul.core.event.FMAdminAddNewUserProcessEvent;
import com.federalmogul.core.model.FMAdminAddNewUserProcessModel;
import com.federalmogul.core.model.FMCustomerAccountModel;
import com.federalmogul.core.model.FMCustomerModel;
import com.federalmogul.core.network.FMNetworkService;
import com.federalmogul.facades.address.data.FMB2bAddressData;
import com.federalmogul.facades.fmpopulator.FMCustomerPopulator;
import com.federalmogul.facades.network.FMNetworkFacade;
import com.federalmogul.facades.user.data.FMCustomerAccountData;
import com.federalmogul.facades.user.data.FMCustomerData;
import com.federalmogul.facades.util.FMFacadeUtility;
import com.fm.falcon.webservices.dto.UserRegistrationRequestDTO;
import com.fm.falcon.webservices.dto.UserRegistrationResponseDTO;
import com.fm.falcon.webservices.soap.helper.UserRegistrationHelper;


/**
 * @author su244261
 *
 */
public class FMNetworkFacadeImpl extends DefaultCompanyB2BCommerceFacade implements FMNetworkFacade
{
	@Resource(name = "fmNetworkService")
	private FMNetworkService fmNetworkService;

	@Resource(name = "fmCustomerPopulator")
	private FMCustomerPopulator fmCustomerPopulator;

	@Resource
	private CustomerAccountService customerAccountService;


	@Resource
	protected UserService userService;

	@Resource
	protected SessionService sessionService;

	@Resource
	private AddressPopulator addressPopulator;

	@Autowired
	protected CompanyB2BCommerceService companyB2BCommerceService;

	@Autowired
	private Converter<PrincipalModel, PrincipalData> principalConverter;
	@Autowired
	private B2BUnitService<B2BUnitModel, B2BCustomerModel> b2BUnitService;
	@Autowired
	private Converter<AddressModel, AddressData> addressConverter;

	@Autowired
	private EventService eventService;

	@Autowired
	private BaseSiteService baseSiteService;

	@Autowired
	private CommonI18NService commonI18NService;

	final static HashMap CUSTOMERACCOUNTTYPES = new HashMap();


	/**
	 * @param fmCustomerPopulator
	 *           the fmCustomerPopulator to set
	 */
	public void setFmCustomerPopulator(final FMCustomerPopulator fmCustomerPopulator)
	{
		this.fmCustomerPopulator = fmCustomerPopulator;
	}

	@Resource
	private CustomerNameStrategy customerNameStrategy;


	@Resource
	private B2BUnitService<B2BUnitModel, UserModel> b2bUnitService;
	private B2BCommerceB2BUserGroupService b2BCommerceB2BUserGroupService;
	private B2BUserGroupsLookUpStrategy b2BUserGroupsLookUpStrategy;
	private Converter<UserModel, FMCustomerData> fmCurrentCustomerConverter;
	private B2BUnitPopulator b2bUnitPopulator;


	/**
	 * @return the b2bUnitPopulator
	 */
	public B2BUnitPopulator getB2bUnitPopulator()
	{
		return b2bUnitPopulator;
	}

	/**
	 * @param b2bUnitPopulator
	 *           the b2bUnitPopulator to set
	 */
	public void setB2bUnitPopulator(final B2BUnitPopulator b2bUnitPopulator)
	{
		this.b2bUnitPopulator = b2bUnitPopulator;
	}


	/**
	 * @return the fmCurrentCustomerConverter
	 */
	public Converter<UserModel, FMCustomerData> getFmCurrentCustomerConverter()
	{
		return fmCurrentCustomerConverter;
	}

	/**
	 * @param fmCurrentCustomerConverter
	 *           the fmCurrentCustomerConverter to set
	 */
	public void setFmCurrentCustomerConverter(final Converter<UserModel, FMCustomerData> fmCurrentCustomerConverter)
	{
		this.fmCurrentCustomerConverter = fmCurrentCustomerConverter;
	}


	private static final Logger LOG = Logger.getLogger(FMNetworkFacadeImpl.class);

	@Resource
	private Populator<AddressModel, FMB2bAddressData> fmb2bAddressPopulator;


	/**
	 * @return the b2BCommerceB2BUserGroupService
	 */
	@Override
	public B2BCommerceB2BUserGroupService getB2BCommerceB2BUserGroupService()
	{
		return b2BCommerceB2BUserGroupService;
	}

	/**
	 * @param b2bCommerceB2BUserGroupService
	 *           the b2BCommerceB2BUserGroupService to set
	 */
	@Override
	public void setB2BCommerceB2BUserGroupService(final B2BCommerceB2BUserGroupService b2bCommerceB2BUserGroupService)
	{
		b2BCommerceB2BUserGroupService = b2bCommerceB2BUserGroupService;
	}

	/**
	 * @return the b2BUserGroupsLookUpStrategy
	 */
	@Override
	public B2BUserGroupsLookUpStrategy getB2BUserGroupsLookUpStrategy()
	{
		return b2BUserGroupsLookUpStrategy;
	}

	/**
	 * @param b2bUserGroupsLookUpStrategy
	 *           the b2BUserGroupsLookUpStrategy to set
	 */
	@Override
	public void setB2BUserGroupsLookUpStrategy(final B2BUserGroupsLookUpStrategy b2bUserGroupsLookUpStrategy)
	{
		b2BUserGroupsLookUpStrategy = b2bUserGroupsLookUpStrategy;
	}

	@Override
	public FMCustomerData getFMCustomerDataForUid(final String uid)
	{



		Assert.hasText(uid, "The field [uid] cannot be empty");


		final UserModel currentUserModel = userService.getCurrentUser();
		//sessionService.setAttribute(SessionContext.USER, userService.getAdminUser());



		final FMCustomerModel fmCustomerModel = fmNetworkService.getFMCustomerForUid(uid);

		//	LOG.info("USER STATUS:::" + fmCustomerModel.getActive());

		validateParameterNotNull(fmCustomerModel, String.format("Customer for uid %s not found", uid));

		//LOG.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~FROM FMCUSTOMER MODEL~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");



		final FMCustomerData retrievedData = new FMCustomerData();
		fmCustomerPopulator.populate(fmCustomerModel, retrievedData);


		//sessionService.setAttribute(SessionContext.USER, currentUserModel);

		return retrievedData;
	}

	@Override
	public void updateFMUser(final FMCustomerData fmCustomerData, final AddressData addressData,final boolean passwordUpdate)
			throws UnsupportedOperationException, ClassNotFoundException, SOAPException, IOException, DuplicateUidException
	{


		//validateParameterNotNullStandardMessage("fmcustomerData", fmCustomerData);
		//Assert.hasText(fmCustomerData.getTitleCode(), "The field [TitleCode] cannot be empty");
		Assert.hasText(fmCustomerData.getFirstName(), "The field [FirstName] cannot be empty");
		Assert.hasText(fmCustomerData.getLastName(), "The field [LastName] cannot be empty");
		Assert.hasText(fmCustomerData.getEmail(), "The field [Email] cannot be empty");
		//Assert.hasText(fmCustomerData.getWorkContactNo(), "The field [WorkContactNo] cannot be empty");
		/*
		 * Assert.hasText(fmCustomerData.getDefaultShippingAddress().getPhone(),
		 * "The field [PersonalContactNumber] cannot be empty");
		 */
		Assert.hasText(fmCustomerData.getUnit().getUid(), "The field [Unit] cannot be empty");
		Assert.notEmpty(fmCustomerData.getRoles(), "The field [Roles] cannot be empty");


		final UserModel currentUserModel = userService.getCurrentUser();

		//sessionService.setAttribute(SessionContext.USER, userService.getAdminUser());

		//sessionService.setAttribute(SessionContext.USER, userService.getCurrentUser());

		FMCustomerModel fmCustomerModel;

		String userType = "USER_TYPE";

		if (StringUtils.isEmpty(fmCustomerData.getUid()))
		{
			userType = "new";
			fmCustomerModel = this.getModelService().create(FMCustomerModel.class);
			fmCustomerModel.setCustomerID(UUID.randomUUID().toString());
			final Set<PrincipalGroupModel> customerGroups = new HashSet();
			if (fmCustomerData.getUserTypeDescription().equals(Fmusertype.WAREHOUSEDISTRIBUTORCOMMERCIALVEHICLE)
					|| fmCustomerData.getUserTypeDescription().equals(Fmusertype.WAREHOUSEDISTRIBUTORLIGHTVEHICLE)
					|| fmCustomerData.getUserTypeDescription().equals(Fmusertype.RETAILER))
			{

				int acccount = 0;
				final String codes = Config.getParameter("accountcodes");
				final String[] acccodes = codes.split(",");
				for (int i = 0; i < acccodes.length; i++)
				{

					if (fmCustomerData.getUnit().getUid().contains(acccodes[i]))
					{
						acccount++;
					}
				}
				if (acccount == 0
						&& !fmCustomerData.getUnit().getUid().equals(Config.getParameter("federalmogulaccountCode_Internalusers")))
				{
					customerGroups.add(userService.getUserGroupForUID("FMB2BB"));
				customerGroups.add(userService.getUserGroupForUID("FMFullAccessGroup"));
				}
			}

			else if (fmCustomerData.getUserTypeDescription().equals(Fmusertype.CONSUMERDIFM)
					|| fmCustomerData.getUserTypeDescription().equals(Fmusertype.CONSUMERDIY))
			{
				customerGroups.add(userService.getUserGroupForUID("FMB2C"));
				customerGroups.add(userService.getUserGroupForUID("FMFullAccessGroup"));
			}
			else if (fmCustomerData.getUserTypeDescription().equals(Fmusertype.JOBBERPARTSTORE)
					|| fmCustomerData.getUserTypeDescription().equals(Fmusertype.SHOPOWNERTECHNICIAN))
			{
				customerGroups.add(userService.getUserGroupForUID("FMB2SB"));
				customerGroups.add(userService.getUserGroupForUID("b2bcustomergroup"));
				customerGroups.add(userService.getUserGroupForUID("FMFullAccessGroup"));

			}
			fmCustomerModel.setGroups(customerGroups);
		}
		else
		{
			userType = "existing";
			fmCustomerModel = getFMNetworkService().getFMCustomerForUid(fmCustomerData.getUid());
		}

		//sessionService.setAttribute(SessionContext.USER, userService.getCurrentUser());



		if (fmCustomerData.getUserTypeDescription() != null)
		{
			/*if (fmCustomerData.getUserTypeDescription().equals(Fmusertype.JOBBERPARTSTORE)
					|| fmCustomerData.getUserTypeDescription().equals(Fmusertype.WAREHOUSEDISTRIBUTORCOMMERCIALVEHICLE)|| fmCustomerData.getUserTypeDescription().equals(Fmusertype.WAREHOUSEDISTRIBUTORLIGHTVEHICLE))
			{



				UserRegistrationResponseDTO regB2bResponse = null;
				final UserRegistrationHelper userRegHelper = new UserRegistrationHelper();
				regB2bResponse = userRegHelper.sendSOAPMessage(convertDataToDTO(fmCustomerData));

				if (!"000".equalsIgnoreCase(regB2bResponse.getResponseCode()))
				{
					throw new IOException(regB2bResponse.getSeverityText());

				}
				//final FMCustomerModel fmCustomerModel = getModelService().create(FMCustomerModel.class);
				fmCustomerModel.setName(getCustomerNameStrategy()
						.getName(fmCustomerData.getFirstName(), fmCustomerData.getLastName()));
				if (StringUtils.isNotBlank(fmCustomerData.getFirstName()) && StringUtils.isNotBlank(fmCustomerData.getLastName()))
				{
					fmCustomerModel.setName(getCustomerNameStrategy().getName(fmCustomerData.getFirstName(),
							fmCustomerData.getLastName()));
				}

				fmCustomerModel.setOriginalUid(regB2bResponse.getContactID());



				fmCustomerModel.setWorkContactNo(fmCustomerData.getWorkContactNo().trim());
				//	fmCustomerModel.setActive(fmCustomerData.isActive());

				if (StringUtils.isNotBlank(fmCustomerData.getTitleCode()))
				{
					fmCustomerModel.setTitle(getUserService().getTitleForCode(fmCustomerData.getTitleCode()));
				}
				else
				{
					fmCustomerModel.setTitle(null);
				}


				fmCustomerModel.setUid(fmCustomerData.getEmail().toLowerCase());
				final B2BUnitModel defaultUnit = getFMNetworkService().getUnitForUID(fmCustomerData.getUnit().getUid());
				//final B2BUnitModel defaultUnit = getCompanyB2BCommerceService().getUnitForUid(fmCustomerData.getUnit().getUid());
				fmCustomerModel.setEmail(fmCustomerData.getEmail());
				fmCustomerModel.setLastName(fmCustomerData.getLastName());
				fmCustomerModel.setChannelCode(fmCustomerData.getUserTypeDescription());
				fmCustomerModel.setEncodedPassword(fmCustomerData.getPassword());
				fmCustomerModel.setLoyaltySubscription(fmCustomerData.getLoyaltySignup());
				fmCustomerModel.setTechAcademySubscription(fmCustomerData.getTechAcademySubscription());
				fmCustomerModel.setPromSubscription(fmCustomerData.getPromotionInfoSubscription());
				fmCustomerModel.setNewPrductAlerts(fmCustomerData.getNewProductAlerts());
				fmCustomerModel.setNewsLetterSubscription(fmCustomerData.getNewsLetterSubscription());
				final B2BUnitModel oldDefaultUnit = getB2bUnitService().getParent(fmCustomerModel);
				final Set<PrincipalGroupModel> groups = new HashSet<PrincipalGroupModel>(fmCustomerModel.getGroups());

				if (oldDefaultUnit != null && groups.contains(oldDefaultUnit))
				{
					groups.remove(oldDefaultUnit);
				}
				groups.add(defaultUnit);
				groups.add(userService.getUserGroupForUID("b2bcustomergroup"));

				final Set<PrincipalGroupModel> updatedgroups = populategroups(fmCustomerData, groups);

				//remove setactive from server copy
				fmCustomerModel.setLoginDisabled(fmCustomerData.getIsLoginDisabled());

				 * if (fmCustomerData.getIsLoginDisabled() == true) { fmCustomerModel.setActive(false); }



				fmCustomerModel.setFmUnit((FMCustomerAccountModel) getCompanyB2BCommerceService().getUnitForUid(
						fmCustomerData.getUnit().getUid()));
				fmCustomerModel.setDefaultB2BUnit(getCompanyB2BCommerceService().getUnitForUid(fmCustomerData.getUnit().getUid()));

				fmCustomerModel.setGroups(updatedgroups);
				//getB2BCommerceB2BUserGroupService().updateUserGroups(getUserGroups(), fmCustomerData.getRoles(), fmCustomerModel);




				if (fmCustomerModel.getDefaultShipmentAddress() != null)
				{

					if (fmCustomerModel.getDefaultShipmentAddress().getPhone1() != null)
					{


						fmCustomerModel.getDefaultShipmentAddress().setPhone1(fmCustomerData.getDefaultShippingAddress().getPhone());

					}

				}
				else
				{


					 target.getDefaultShipmentAddress().setPhone1(source.getDefaultShippingAddress().getPhone());

					final AddressModel addressModel = new AddressModel();
					addressModel.setPhone1(fmCustomerData.getDefaultShippingAddress().getPhone());
					addressModel.setOwner(fmCustomerModel);
					addressModel.setShippingAddress(true);
					fmCustomerModel.setDefaultShipmentAddress(addressModel);
				}



			}




			else
			{
*/
				populateUser(fmCustomerData, fmCustomerModel);

			/*}*/
		}
		//sessionService.setAttribute(SessionContext.USER, currentUserModel);

		//final String ldapUserDN = FMFacadeUtility.createNewCustomer(fmCustomerData);

		/*
		 * if (!"na".equalsIgnoreCase(ldapUserDN)) {
		 *
		 * fmCustomerModel.setLdapaccount(true); fmCustomerModel.setLdaplogin(ldapUserDN);
		 *
		 * }
		 */

		if (fmCustomerModel.getLdaplogin() != null)
		{
			if ("existing".equalsIgnoreCase(userType) && !fmCustomerModel.getLdaplogin().isEmpty())
			{
				if(passwordUpdate){
				FMFacadeUtility.updateLDAPPassword(fmCustomerModel, fmCustomerData.getPassword());
				}else{
					LOG.info("no need for password update");
				}
			}
			else
			{
				final String ldapUserDN = FMFacadeUtility.createNewCustomer(fmCustomerData);
				if (!"na".equalsIgnoreCase(ldapUserDN))
				{
					fmCustomerModel.setLdapaccount(true);
					fmCustomerModel.setLdaplogin(ldapUserDN);
				}
			}
		}
		else if ("new".equalsIgnoreCase(userType))
		{
			LOG.info("Creating new user LDAP DN");
			final String ldapUserDN = FMFacadeUtility.createNewCustomer(fmCustomerData);
			if (!"na".equalsIgnoreCase(ldapUserDN))
			{
				LOG.info("Created new user LDAP DN as" + ldapUserDN);
				fmCustomerModel.setLdapaccount(true);
				fmCustomerModel.setLdaplogin(ldapUserDN);
				fmCustomerModel.setPasswordEncoding("md5");
				fmCustomerModel.setPwdMeetsLatestStandards(true);

			}
		}


		LOG.info("=====================================================");
		LOG.info("=====================================================");
		LOG.info("=====================================================UID::" + fmCustomerModel.getUid() + "email::"
				+ fmCustomerModel.getEmail() + "email::" + fmCustomerModel.getName());
		LOG.info("=====================================================");
		LOG.info("=====================================================");
		//sessionService.setAttribute(SessionContext.USER, userService.getAdminUser());
		getFMNetworkService().saveModel(fmCustomerModel);
		//sessionService.setAttribute(SessionContext.USER, currentUserModel);



		AddressModel addressModel;
		if (StringUtils.isEmpty(fmCustomerData.getUid()))
		{

			// Create the new address model
			addressModel = getModelService().create(AddressModel.class);
			addressModel.setOwner(fmCustomerModel);
			fmCustomerModel.setDefaultShipmentAddress(addressModel);
			//insert the group code here


		}
		else
		{

			if (null != getFMNetworkService().getFMCustomerForUid(fmCustomerData.getUid())&& null != getFMNetworkService().getFMCustomerForUid(fmCustomerData.getUid()).getDefaultShipmentAddress())
			{
				addressModel = getFMNetworkService().getFMCustomerForUid(fmCustomerData.getUid()).getDefaultShipmentAddress();
			}
			else
			{
				addressModel = getModelService().create(AddressModel.class);
			}

		}
		/* addressModel.setOwner(fmCustomerModel); */

		if (fmCustomerData.getDefaultShippingAddress() != null && fmCustomerData.getDefaultShippingAddress().getPhone() != null)
		{


			addressModel.setPhone1(fmCustomerData.getDefaultShippingAddress().getPhone());

		}




		getAddressReversePopulator().populate(addressData, addressModel);



		if (!StringUtils.isEmpty(fmCustomerData.getUid()))
		{

			final CustomerModel customerModel = getFMNetworkService().getCustomerForUid(fmCustomerData.getUid());
			getCustomerAccountService().saveAddressEntry(customerModel, addressModel);
			addressData.setId(addressModel.getPk().toString());



			if (addressData.isDefaultAddress())
			{

				getCustomerAccountService().setDefaultAddressEntry(fmCustomerModel, addressModel);
			}
		}

		//email generation on user creation
		if ("new".equals(userType))
		{

			int acccount = 0;
			final String codes = Config.getParameter("accountcodes");
			final String[] acccodes = codes.split(",");
			for (int i = 0; i < acccodes.length; i++)
			{

				if (fmCustomerData.getUnit().getUid().contains(acccodes[i]))
				{
					acccount++;
				}
			}


			if (fmCustomerData.getUnit() != null
					&& !fmCustomerData.getUnit().getUid().equals(Config.getParameter("federalmogulaccountCode_Internalusers"))
					&& acccount == 0)
			{



				final FMAdminAddNewUserProcessModel fmAdminAddNewUserProcessModel = new FMAdminAddNewUserProcessModel();

			fmAdminAddNewUserProcessModel.setEmailId(Config.getParameter("csrEmailid"));
			fmAdminAddNewUserProcessModel.setEndMessage("Hi");
			fmAdminAddNewUserProcessModel.setSite(getBaseSiteService().getCurrentBaseSite());
			fmAdminAddNewUserProcessModel.setStore(getBaseStoreService().getCurrentBaseStore());
			fmAdminAddNewUserProcessModel.setLanguage(getCommonI18NService().getCurrentLanguage());


			fmAdminAddNewUserProcessModel.setCustomer(fmCustomerModel);


			final FMAdminAddNewUserProcessEvent fmAdminAddNewUserProcessEvent = new FMAdminAddNewUserProcessEvent(
					fmAdminAddNewUserProcessModel);

			eventService.publishEvent(initializeEvent(fmAdminAddNewUserProcessEvent, fmCustomerModel));

			}
		}

		/* save the addreess */
	}

	protected AbstractCommerceUserEvent initializeEvent(final AbstractCommerceUserEvent event, final CustomerModel customerModel)
	{
		event.setBaseStore(getBaseStoreService().getCurrentBaseStore());
		event.setSite(getBaseSiteService().getCurrentBaseSite());
		event.setCustomer(customerModel);
		event.setLanguage(getCommonI18NService().getCurrentLanguage());
		event.setCurrency(getCommonI18NService().getCurrentCurrency());
		return event;
	}

	protected BaseSiteService getBaseSiteService()
	{
		return baseSiteService;
	}

	protected CommonI18NService getCommonI18NService()
	{
		return commonI18NService;
	}


	protected <T extends FMNetworkService> T getFMNetworkService()
	{
		return (T) fmNetworkService;
	}

	protected FMCustomerPopulator getFMCustomerPopulator()
	{
		return fmCustomerPopulator;
	}


	public void populateUser(final FMCustomerData source, final FMCustomerModel target) throws ConversionException
	{
		//target.setTitle(source.gett);


		final TitleModel title = this.getModelService().create(TitleModel.class);

		title.setCode(source.getTitleCode());

		target.setTitle(title);

		target.setName(source.getFirstName());

		target.setLastName(source.getLastName());


		target.setEmail(source.getEmail());

if(source.getWorkContactNo()!=null){

		target.setWorkContactNo(source.getWorkContactNo().trim());
}

		//target.setActive(source.isActive());

		target.setLoginDisabled(source.getIsLoginDisabled());

		/*
		 * if (source.getIsLoginDisabled() == true) { target.setActive(false); }
		 */
		if (StringUtils.isEmpty(source.getUid()))
		{

			target.setChannelCode(source.getUserTypeDescription());
		}


		if (target.getDefaultShipmentAddress() != null)
		{

			if (target.getDefaultShipmentAddress().getPhone1() != null)
			{


				target.getDefaultShipmentAddress().setPhone1(source.getDefaultShippingAddress().getPhone().trim());

			}

		}
		else
		{


			/* target.getDefaultShipmentAddress().setPhone1(source.getDefaultShippingAddress().getPhone()); */

			final AddressModel addressModel = new AddressModel();
			addressModel.setPhone1(source.getDefaultShippingAddress().getPhone());
			addressModel.setOwner(target);
			target.setDefaultShipmentAddress(addressModel);
		}


		target.setName(getCustomerNameStrategy().getName(source.getFirstName(), source.getLastName()));



		//final B2BUnitModel defaultUnit = getCompanyB2BCommerceService().getUnitForUid(source.getUnit().getUid());
		final B2BUnitModel defaultUnit = getFMNetworkService().getUnitForUID(source.getUnit().getUid());

		final B2BUnitModel oldDefaultUnit = getB2bUnitService().getParent(target);
		target.setDefaultB2BUnit(defaultUnit);
		target.setFmUnit((FMCustomerAccountModel) defaultUnit);
		final Set<PrincipalGroupModel> groups = new HashSet<PrincipalGroupModel>(target.getGroups());
		if (oldDefaultUnit != null && groups.contains(oldDefaultUnit))
		{
			groups.remove(oldDefaultUnit);
		}
		groups.add(defaultUnit);
		target.setGroups(groups);


		/* getB2BCommerceB2BUserGroupService().updateUserGroups(getUserGroups(), source.getRoles(), target); */


		final Set<PrincipalGroupModel> updatedgroups = populategroups(source, groups);

		target.setGroups(updatedgroups);
		if (StringUtils.isNotBlank(source.getTitleCode()))
		{
			target.setTitle(getUserService().getTitleForCode(source.getTitleCode()));
		}
		else
		{
			target.setTitle(null);
		}
		setUid(source, target);

	}

	protected void setUid(final CustomerData source, final B2BCustomerModel target)
	{
		if (source.getDisplayUid() != null && !source.getDisplayUid().isEmpty())
		{
			target.setOriginalUid(source.getDisplayUid());
			target.setUid(source.getDisplayUid().toLowerCase());
		}
		else if (source.getEmail() != null)
		{
			target.setOriginalUid(source.getEmail());
			target.setUid(source.getEmail().toLowerCase());
		}
	}

	@Override
	public CustomerNameStrategy getCustomerNameStrategy()
	{
		return customerNameStrategy;
	}

	public void setCustomerNameStrategy(final CustomerNameStrategy customerNameStrategy)
	{
		this.customerNameStrategy = customerNameStrategy;
	}

	public B2BUnitService<B2BUnitModel, UserModel> getB2bUnitService()
	{
		return b2bUnitService;
	}

	public void setB2bUnitService(final B2BUnitService<B2BUnitModel, UserModel> b2bUnitService)
	{
		this.b2bUnitService = b2bUnitService;
	}


	@Override
	public FMCustomerData getCurrentFMCustomer()
	{
		return getFmCurrentCustomerConverter().convert(getUserService().getCurrentUser());
	}

	protected CustomerAccountService getCustomerAccountService()
	{
		return customerAccountService;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.federalmogul.facades.network.FMNetworkFacade#updateFMUser(com.federalmogul.facades.user.data.FMCustomerData)
	 */
	@Override
	public void updateFMUser(final FMCustomerData fmCustomerData) throws DuplicateUidException, UnsupportedOperationException,
			ClassNotFoundException, SOAPException, IOException
	{
		// YTODO Auto-generated method stub

	}



	private UserRegistrationRequestDTO convertDataToDTO(final FMCustomerData fmcustomerdata)
	{
		final UserRegistrationRequestDTO reqDTO = new UserRegistrationRequestDTO();
		reqDTO.setServiceName("Request");
		reqDTO.setCustomerEmailID(fmcustomerdata.getEmail());

		reqDTO.setAccountCode("" + CUSTOMERACCOUNTTYPES.get(fmcustomerdata.getUserTypeDescription()));

		reqDTO.setFirstName(fmcustomerdata.getFirstName());
		reqDTO.setLastName(fmcustomerdata.getLastName());
		reqDTO.setTelephone(fmcustomerdata.getWorkContactNo());

		if (fmcustomerdata.getUnit() != null)
		{
			reqDTO.setSold_ShipTo(fmcustomerdata.getUnit().getUid());
		}
		else
		{
			reqDTO.setSold_ShipTo("");
		}

		reqDTO.setCustomerType("B2b");







		reqDTO.setStreetName1("xxxxxx");
		reqDTO.setStreetName2("xxxxxx");
		reqDTO.setCity("xxxxx");
		//reqDTO.setState(fmcustomerdata.getDefaultShippingAddress().getRegion().getIsocodeShort());
		reqDTO.setState("MN");
		reqDTO.setPostalCode("12345");
		reqDTO.setCountry("US");




		reqDTO.setCompanyName("yyyyyyy");
		reqDTO.setCompStreetName1("yyyyyy");
		reqDTO.setCompStreetName2("yyyyyyy");
		reqDTO.setCompCity("yyyyyyy");
		//reqDTO.setCompState(fmcustomerdata.getB2baddress().getRegion().getIsocodeShort());
		reqDTO.setCompState("CA");
		reqDTO.setCompPostCode("78901");
		reqDTO.setCompCountry("US");









		/*
		 * final TitleModel title = this.getModelService().create(TitleModel.class);
		 *
		 * title.setCode(source.getTitleCode());
		 *
		 *
		 *
		 * target.setTitle(title);
		 */



		//target.setEncodedPassword(source.getPassword());

		//	target.setWorkContactNo(source.getWorkContactNo());

		/*
		 * final AddressData customeraddress = new AddressData(); customeraddress.setPhone(target.getPhoneno());
		 */


		//target.setName(getCustomerNameStrategy().getName(source.getFirstName(), source.getLastName()));

		//target.setActive(source.isActive());





		/*
		 * final B2BUnitModel defaultUnit = getCompanyB2BCommerceService().getUnitForUid(source.getUnit().getUid());
		 *
		 * final B2BUnitModel oldDefaultUnit = getB2bUnitService().getParent(target);
		 * target.setDefaultB2BUnit(defaultUnit);
		 *
		 * final Set<PrincipalGroupModel> groups = new HashSet<PrincipalGroupModel>(target.getGroups()); if
		 * (oldDefaultUnit != null && groups.contains(oldDefaultUnit)) { groups.remove(oldDefaultUnit); }
		 * groups.add(defaultUnit); target.setGroups(groups);
		 * getB2BCommerceB2BUserGroupService().updateUserGroups(getUserGroups(), source.getRoles(), target); if
		 * (StringUtils.isNotBlank(source.getTitleCode())) {
		 * target.setTitle(getUserService().getTitleForCode(source.getTitleCode())); } else { target.setTitle(null); }
		 * setUid(source, target);
		 */
		return reqDTO;
	}


	@Override
	public SearchPageData<CustomerData> getAllFMUsers(final PageableData pageableData)
	{


		final UserModel currentUserModel = userService.getCurrentUser();

		//sessionService.setAttribute(SessionContext.USER, userService.getAdminUser());

		final SearchPageData<B2BCustomerModel> b2bCustomer = fmNetworkService.getAllFMUsers(pageableData);

		//sessionService.setAttribute(SessionContext.USER, currentUserModel);

		if (b2bCustomer != null)
		{
			return convertPageData(b2bCustomer, getB2BCustomerConverter());
		}
		else
		{

			return null;
		}
	}

	@Override
	public SearchPageData<CustomerData> getAllFMUsersByName(final PageableData pageableData, final String userName)
	{


		final UserModel currentUserModel = userService.getCurrentUser();

		//sessionService.setAttribute(SessionContext.USER, userService.getAdminUser());

		final SearchPageData<B2BCustomerModel> b2bCustomer = fmNetworkService.getAllFMUsersByName(pageableData, userName);

		//sessionService.setAttribute(SessionContext.USER, currentUserModel);

		if (b2bCustomer != null)
		{
			return convertPageData(b2bCustomer, getB2BCustomerConverter());
		}
		else
		{

			return null;
		}
	}



	/*
	 * public SearchPageData<CustomerData> getPagedCustomers(final PageableData pageableData) { final
	 * SearchPageData<B2BCustomerModel> b2bCustomer = getB2BCommerceUserService().getPagedCustomers(pageableData);
	 *
	 * for (final B2BCustomerModel b2bCustomerModel : b2bCustomer.getResults()) {
	 * LOG.info("Users:::In b2bcommercefacade getPagedCustomer" + b2bCustomerModel.getUid()); } return
	 * convertPageData(b2bCustomer, getB2BCustomerConverter()); }
	 */


	@Override
	public SearchPageData<B2BUnitData> getAllFMAccounts(final PageableData pageableData)
	{
		final UserModel currentUserModel = userService.getCurrentUser();

		//sessionService.setAttribute(SessionContext.USER, userService.getAdminUser());
		final SearchPageData<B2BUnitModel> b2bCustomerAccount = fmNetworkService.getAllFMAccounts(pageableData);
		//sessionService.setAttribute(SessionContext.USER, currentUserModel);

		return convertPageData(b2bCustomerAccount, getB2BUnitConverter());
	}




	@Override
	public List<B2BUnitData> getAllFMAccounts()
	{
		final UserModel currentUserModel = userService.getCurrentUser();
		final List<B2BUnitData> b2bUnitData = new ArrayList<B2BUnitData>();
		final B2BUnitData unitData = new B2BUnitData();

		//sessionService.setAttribute(SessionContext.USER, userService.getAdminUser());
		final List<B2BUnitModel> b2bCustomerAccount = fmNetworkService.getAllFMAccounts();

		//sessionService.setAttribute(SessionContext.USER, currentUserModel);
		final Iterator iterator = b2bCustomerAccount.iterator();
		while (iterator.hasNext())
		{
			final B2BUnitModel b2bunitmodel = (B2BUnitModel) iterator.next();//getting NOE in next line 721 populator

			getB2bUnitPopulator().populate(b2bunitmodel, unitData);
			b2bUnitData.add(unitData);

		}

		return b2bUnitData;
	}


	@Override
	public Map<String, String> getAllFMAccountList()
	{
		final Map<String, String> unitlist = new HashMap<String, String>();



		final UserModel currentUserModel = userService.getCurrentUser();

		sessionService.setAttribute(SessionContext.USER, userService.getAdminUser());
		final List<B2BUnitModel> b2bCustomerAccount = fmNetworkService.getAllFMAccounts();

		sessionService.setAttribute(SessionContext.USER, currentUserModel);
		final Iterator iterator = b2bCustomerAccount.iterator();
		while (iterator.hasNext())
		{

			final B2BUnitModel b2bunitmodel = (B2BUnitModel) iterator.next();//getting NOE in next line 721 populator



			final String unitID = b2bunitmodel.getUid();
			final String unitName = b2bunitmodel.getName();

			unitlist.put(unitID, unitName);



		}

		return unitlist;
	}




	@Override
	public List<String> getAllFMAccountsByID(final String unitUid)
	{




		final List<String> units = new ArrayList<String>();

		final List<B2BUnitModel> b2bCustomerAccount = fmNetworkService.getAllFMAccountsByID(unitUid);


		final Iterator iterator = b2bCustomerAccount.iterator();
		while (iterator.hasNext())
		{

			final B2BUnitModel b2bunitmodel = (B2BUnitModel) iterator.next();//getting NOE in next line 721 populator



			final String unitID = b2bunitmodel.getUid();


			units.add(unitID);



		}

		return units;
	}

	static
	{
		CUSTOMERACCOUNTTYPES.put(Fmusertype.WAREHOUSEDISTRIBUTORLIGHTVEHICLE, "002");
		CUSTOMERACCOUNTTYPES.put(Fmusertype.WAREHOUSEDISTRIBUTORCOMMERCIALVEHICLE, "008");
		CUSTOMERACCOUNTTYPES.put(Fmusertype.RETAILER, "007");
		CUSTOMERACCOUNTTYPES.put(Fmusertype.JOBBERPARTSTORE, "037");
		CUSTOMERACCOUNTTYPES.put(Fmusertype.SHOPOWNERTECHNICIAN, "038");
		CUSTOMERACCOUNTTYPES.put(Fmusertype.CONSUMERDIY, "036");
		CUSTOMERACCOUNTTYPES.put(Fmusertype.CONSUMERDIFM, "036");
	}

	@Override
	public B2BUnitData getUnitForUID(final String unitId)
	{
		final UserModel currentUserModel = userService.getCurrentUser();
		//sessionService.setAttribute(SessionContext.USER, userService.getAdminUser());

		final B2BUnitModel b2bUnitModel = fmNetworkService.getUnitForUID(unitId);
		//sessionService.setAttribute(SessionContext.USER, currentUserModel);
		final B2BUnitData b2bUnitData = new B2BUnitData();
		populateB2bUnit(b2bUnitModel, b2bUnitData);

		return b2bUnitData;
	}



	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.federalmogul.facades.network.FMNetworkFacade#getUsersforB2bAdmin(de.hybris.platform.commerceservices.search
	 * .pagedata.PageableData, de.hybris.platform.b2bacceleratorfacades.order.data.B2BUnitData)
	 */
	@Override
	public SearchPageData<CustomerData> getUsersforB2bAdmin(final PageableData pageableData, final String unit)
	{

		final UserModel currentUserModel = userService.getCurrentUser();

		//sessionService.setAttribute(SessionContext.USER, userService.getAdminUser());

		final SearchPageData<B2BCustomerModel> b2bCustomer = fmNetworkService.getUsersforB2bAdmin(pageableData, unit);

		//sessionService.setAttribute(SessionContext.USER, currentUserModel);
		if (b2bCustomer.getResults() != null)
		{
			return convertPageData(b2bCustomer, getB2BCustomerConverter());
		}
		else
		{
			return null;
		}

	}

	public void populateB2bUnit(final B2BUnitModel source, final B2BUnitData target)
	{
		Assert.notNull(source, "Parameter source cannot be null.");
		Assert.notNull(target, "Parameter target cannot be null.");

		populateUnit(source, target);
		populateUnitRelations(source, target);


	}

	protected B2BUnitData populateUnit(final B2BUnitModel source, final B2BUnitData target)
	{
		target.setName(source.getLocName());
		target.setUid(source.getUid());
		target.setActive(Boolean.TRUE.equals(source.getActive()));
		target.setApprovalProcessCode(source.getApprovalProcessCode());
		final Map<String, String> businessProcesses = companyB2BCommerceService.getBusinessProcesses();
		target.setApprovalProcessName((businessProcesses != null) ? businessProcesses.get(source.getApprovalProcessCode()) : "");
		return target;
	}

	protected B2BUnitData populateUnitRelations(final B2BUnitModel source, final B2BUnitData target)
	{
		// unit's budgets
		if (CollectionUtils.isNotEmpty(source.getBudgets()))
		{
			target.setBudgets(Converters.convertAll(source.getBudgets(), getB2BBudgetConverter()));
		}

		// unit's cost centers
		if (CollectionUtils.isNotEmpty(source.getCostCenters()))
		{
			target.setCostCenters(Converters.convertAll(CollectionUtils.select(source.getCostCenters(),
					new BeanPropertyValueEqualsPredicate(B2BCostCenterModel.ACTIVE, Boolean.TRUE)), getB2BCostCenterConverter()));
		}

		// unit approvers
		if (CollectionUtils.isNotEmpty(source.getApprovers()))
		{
			final UserGroupModel approverGroup = userService.getUserGroupForUID(B2BConstants.B2BAPPROVERGROUP);
			target.setApprovers(Converters.convertAll(CollectionUtils.select(source.getApprovers(), new Predicate()
			{
				@Override
				public boolean evaluate(final Object object)
				{
					final B2BCustomerModel b2bCustomerModel = (B2BCustomerModel) object;
					return userService.isMemberOfGroup(b2bCustomerModel, approverGroup);
				}
			}), getB2BCustomerConverter()));
		}

		// unit addresses
		if (CollectionUtils.isNotEmpty(source.getAddresses()))
		{
			target.setAddresses(Converters.convertAll(source.getAddresses(), getAddressConverter()));
		}

		// unit's  customers
		final Collection<B2BCustomerModel> b2BCustomers = getB2BUnitService().getUsersOfUserGroup(source,
				B2BConstants.B2BCUSTOMERGROUP, false);
		if (CollectionUtils.isNotEmpty(b2BCustomers))
		{
			target.setCustomers(Converters.convertAll(b2BCustomers, getB2BCustomerConverter()));
		}

		// unit's  managers
		final Collection<B2BCustomerModel> managers = getB2BUnitService().getUsersOfUserGroup(source, B2BConstants.B2BMANAGERGROUP,
				false);
		if (CollectionUtils.isNotEmpty(managers))
		{
			target.setManagers(Converters.convertAll(managers, getB2BCustomerConverter()));
		}

		// unit's administrators
		final Collection<B2BCustomerModel> administrators = getB2BUnitService().getUsersOfUserGroup(source,
				B2BConstants.B2BADMINGROUP, false);
		if (CollectionUtils.isNotEmpty(administrators))
		{
			target.setAdministrators(Converters.convertAll(administrators, getB2BCustomerConverter()));
		}

		final Collection<PrincipalModel> accountManagers = new HashSet<PrincipalModel>();
		if (source.getAccountManager() != null)
		{
			accountManagers.add(source.getAccountManager());
		}
		if (CollectionUtils.isNotEmpty(source.getAccountManagerGroups()))
		{
			for (final UserGroupModel userGroupModel : source.getAccountManagerGroups())
			{
				accountManagers.addAll(userGroupModel.getMembers());
			}
		}
		if (CollectionUtils.isNotEmpty(accountManagers))
		{
			target.setAccountManagers(Converters.convertAll(accountManagers, getPrincipalConverter()));
		}

		return target;
	}

	protected Converter<PrincipalModel, PrincipalData> getPrincipalConverter()
	{
		return principalConverter;
	}

	protected B2BUnitService<B2BUnitModel, B2BCustomerModel> getB2BUnitService()
	{
		return b2BUnitService;
	}

	protected Converter<AddressModel, AddressData> getAddressConverter()
	{
		return addressConverter;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.federalmogul.facades.network.FMNetworkFacade#getAllAccountsforCSR()
	 */
	@Override
	public List<FMCustomerAccountData> getAllAccountsforCSR()
	{

		final List<FMCustomerAccountModel> listFMCustomerAccountModel = fmNetworkService.getAllAccountsforCSR();
		final List<FMCustomerAccountData> listfmCustomerAccountData = new ArrayList<FMCustomerAccountData>();
		if (listFMCustomerAccountModel != null && listFMCustomerAccountModel.size() > 0)
		{

			final Iterator fmCustomerAccountModelIterator = listFMCustomerAccountModel.iterator();
			while (fmCustomerAccountModelIterator.hasNext())
			{


				final FMCustomerAccountModel accountModel = (FMCustomerAccountModel) fmCustomerAccountModelIterator.next();

				final FMCustomerAccountData accountData = new FMCustomerAccountData();
				accountData.setUid(accountModel.getUid());
				accountData.setCreationTime(accountModel.getCreationtime());
				accountData.setNabsAccountCode(accountModel.getNabsAccountCode());
				listfmCustomerAccountData.add(accountData);
			}
		}
		return listfmCustomerAccountData;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.federalmogul.facades.network.FMNetworkFacade#getAccountID(java.lang.String)
	 */
	@Override
	public FMCustomerAccountData getAccountID(final String accountId)
	{
		final FMCustomerAccountModel fmCustomerAccountModel = fmNetworkService.getAccountID(accountId);
		final FMCustomerAccountData accountData = new FMCustomerAccountData();
		if (fmCustomerAccountModel != null)
		{
			accountData.setUid(fmCustomerAccountModel.getUid());
			accountData.setCreationTime(fmCustomerAccountModel.getCreationtime());
			accountData.setNabsAccountCode(fmCustomerAccountModel.getNabsAccountCode());

			accountData.setAccComments(fmCustomerAccountModel.getAccComments());
			accountData.setAccComments2(fmCustomerAccountModel.getAccComments2());

			final List<FMB2bAddressData> listAdddressdata = new ArrayList<FMB2bAddressData>();
			final List<AddressModel> listAddressModel = (List<AddressModel>) fmCustomerAccountModel.getAddresses();

			if (listAddressModel != null && listAddressModel.size() > 0)
			{
				LOG.info("addressmodel size" + listAddressModel.size());
				final Iterator addressModelIterator = listAddressModel.iterator();
				while (addressModelIterator.hasNext())
				{
					LOG.info("inside WHILEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
					final FMB2bAddressData addressData = new FMB2bAddressData();
					final AddressModel addressModel = (AddressModel) addressModelIterator.next();
					//addressPopulator.populate(addressModel, addressData);
					fmb2bAddressPopulator.populate(addressModel, addressData);
					addressData.setCompanyName(fmCustomerAccountModel.getLocName());
					LOG.info("addressdata linne1 1111111111111111" + addressData.getLine1());
					listAdddressdata.add(addressData);
				}
			}
			LOG.info("addressmodel size after while lopp" + listAddressModel.size());
			accountData.setUnitAddress(listAdddressdata);
			return accountData;
		}
		else
		{
			return null;
		}

		/*
		 * final List<AddressModel> list_addressmodel = (List<AddressModel>) fmCustomerAccountModel.getAddresses(); final
		 * List<AddressData> list_adddressdata = new ArrayList<AddressData>(); final Iterator i =
		 * list_addressmodel.iterator(); while (i.hasNext()) { final AddressData addressData = new AddressData(); final
		 * AddressModel addressModel = (AddressModel) i.next(); addressPopulator.populate(addressModel, addressData);
		 * addressData.setCompanyName(fmCustomerAccountModel.getLocName());
		 *
		 * list_adddressdata.add(addressData); } accountData.setAddresses(list_adddressdata);
		 */


	}


	/*
	 * (non-Javadoc)
	 *
	 * @see com.federalmogul.facades.network.FMNetworkFacade#getAccountByNabs(java.lang.String)
	 */
	@Override
	public FMCustomerAccountData getAccountByNabs(final String accountId)
	{
		final FMCustomerAccountModel fmCustomerAccountModel = fmNetworkService.getAccountByNabs(accountId);
		final FMCustomerAccountData accountData = new FMCustomerAccountData();
		if (fmCustomerAccountModel != null)
		{
			accountData.setUid(fmCustomerAccountModel.getUid());
			accountData.setCreationTime(fmCustomerAccountModel.getCreationtime());
			accountData.setNabsAccountCode(fmCustomerAccountModel.getNabsAccountCode());

			accountData.setAccComments(fmCustomerAccountModel.getAccComments());
			accountData.setAccComments2(fmCustomerAccountModel.getAccComments2());

			final List<FMB2bAddressData> listAdddressdata = new ArrayList<FMB2bAddressData>();
			final List<AddressModel> listaAdressModel = (List<AddressModel>) fmCustomerAccountModel.getAddresses();

			if (listaAdressModel != null && listaAdressModel.size() > 0)
			{
				LOG.info("addressmodel size" + listaAdressModel.size());
				final Iterator addressModelIterator = listaAdressModel.iterator();
				while (addressModelIterator.hasNext())
				{
					LOG.info("inside WHILEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
					final FMB2bAddressData addressData = new FMB2bAddressData();
					final AddressModel addressModel = (AddressModel) addressModelIterator.next();
					//addressPopulator.populate(addressModel, addressData);
					fmb2bAddressPopulator.populate(addressModel, addressData);
					addressData.setCompanyName(fmCustomerAccountModel.getLocName());
					LOG.info("addressdata linne1 1111111111111111" + addressData.getLine1());
					listAdddressdata.add(addressData);
				}
			}
			LOG.info("addressmodel size after while lopp" + listaAdressModel.size());
			accountData.setUnitAddress(listAdddressdata);

			return accountData;
		}
		else
		{
			return null;
		}

	}



	/*
	 * (non-Javadoc)
	 *
	 * @see com.federalmogul.facades.network.FMNetworkFacade#fetchAddress(java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<FMCustomerAccountData> fetchAddress(final String companyName, final String line1, final String townCity,
			final String postcode, final String region)
	{
		final List<FMCustomerAccountData> listAccData = new ArrayList<FMCustomerAccountData>();

		final List<FMCustomerAccountModel> listAccModel = fmNetworkService.fetchAddress(companyName, line1, townCity, postcode,
				region);

		if (listAccModel != null && listAccModel.size() > 0)
		{

			final Iterator listAccModelIterator = listAccModel.iterator();


			while (listAccModelIterator.hasNext())
			{
				final FMCustomerAccountData accData = new FMCustomerAccountData();
				final FMCustomerAccountModel accModel = (FMCustomerAccountModel) listAccModelIterator.next();
				//final FMCustomerAccountData accData = new FMCustomerAccountData();
				final List<AddressModel> listAddressModel = (List<AddressModel>) accModel.getAddresses();
				final List<AddressData> listAdddressdata = new ArrayList<AddressData>();

				if (listAddressModel != null && listAddressModel.size() > 0)
				{

					final Iterator addressModelIterator = listAddressModel.iterator();
					while (addressModelIterator.hasNext())
					{
						final AddressData addressData = new AddressData();
						final AddressModel addressModel = (AddressModel) addressModelIterator.next();
						addressPopulator.populate(addressModel, addressData);
						addressData.setCompanyName(accModel.getLocName());
						listAdddressdata.add(addressData);
					}
				}
				accData.setAddresses(listAdddressdata);
				accData.setUid(accModel.getUid());
				listAccData.add(accData);

			}


		}
		return listAccData;

	}


private Set<PrincipalGroupModel> populategroups(final FMCustomerData custdata, final Set<PrincipalGroupModel> existinggroups)
	{

		if (custdata.getRoles().contains("FMAdminGroup"))
		{


			if (existinggroups.contains(userService.getUserGroupForUID("FMFullAccessGroup")))
			{
				existinggroups.remove(userService.getUserGroupForUID("FMFullAccessGroup"));
			}
			if (existinggroups.contains(userService.getUserGroupForUID("FMNoInvoiceGroup")))
			{
				existinggroups.remove(userService.getUserGroupForUID("FMNoInvoiceGroup"));
			}
			if (existinggroups.contains(userService.getUserGroupForUID("FMViewOnlyGroup")))
			{
				existinggroups.remove(userService.getUserGroupForUID("FMViewOnlyGroup"));
			}
			/*
			 * if (groups.contains(userService.getUserGroupForUID("FMBUVOGroup"))) {
			 * groups.remove(userService.getUserGroupForUID("FMBUVOGroup")); }
			 */


			if (!existinggroups.contains(userService.getUserGroupForUID("b2bcustomergroup")))
			{
				existinggroups.add(userService.getUserGroupForUID("b2bcustomergroup"));
			}
			if (!existinggroups.contains(userService.getUserGroupForUID("b2badmingroup")))
			{
				existinggroups.add(userService.getUserGroupForUID("b2badmingroup"));
			}
			if (!existinggroups.contains(userService.getUserGroupForUID("FMAdminGroup")))
			{
				existinggroups.add(userService.getUserGroupForUID("FMAdminGroup"));
			}


		}
		else if (custdata.getRoles().contains("FMFullAccessGroup"))
		{

			if (existinggroups.contains(userService.getUserGroupForUID("FMAdminGroup")))
			{
				existinggroups.remove(userService.getUserGroupForUID("FMAdminGroup"));
			}
			if (existinggroups.contains(userService.getUserGroupForUID("FMNoInvoiceGroup")))
			{
				existinggroups.remove(userService.getUserGroupForUID("FMNoInvoiceGroup"));
			}
			if (existinggroups.contains(userService.getUserGroupForUID("FMViewOnlyGroup")))
			{
				existinggroups.remove(userService.getUserGroupForUID("FMViewOnlyGroup"));
			}
			/*
			 * if (groups.contains(userService.getUserGroupForUID("FMBUVOGroup"))) {
			 * groups.remove(userService.getUserGroupForUID("FMBUVOGroup")); }
			 */

			if (!existinggroups.contains(userService.getUserGroupForUID("b2bcustomergroup")))
			{
				existinggroups.add(userService.getUserGroupForUID("b2bcustomergroup"));
			}
			if (!existinggroups.contains(userService.getUserGroupForUID("FMFullAccessGroup")))
			{
				existinggroups.add(userService.getUserGroupForUID("FMFullAccessGroup"));
			}
			if (!existinggroups.contains(userService.getUserGroupForUID("FMB2BB")))
			{
				existinggroups.add(userService.getUserGroupForUID("FMB2BB"));
			}


		}
		else if (custdata.getRoles().contains("FMNoInvoiceGroup"))
		{

			if (existinggroups.contains(userService.getUserGroupForUID("FMAdminGroup")))
			{
				existinggroups.remove(userService.getUserGroupForUID("FMAdminGroup"));
			}
			if (existinggroups.contains(userService.getUserGroupForUID("FMFullAccessGroup")))
			{
				existinggroups.remove(userService.getUserGroupForUID("FMFullAccessGroup"));
			}
			if (existinggroups.contains(userService.getUserGroupForUID("FMViewOnlyGroup")))
			{
				existinggroups.remove(userService.getUserGroupForUID("FMViewOnlyGroup"));
			}
			/*
			 * if (groups.contains(userService.getUserGroupForUID("FMBUVOGroup"))) {
			 * groups.remove(userService.getUserGroupForUID("FMBUVOGroup")); }
			 */

			if (!existinggroups.contains(userService.getUserGroupForUID("b2bcustomergroup")))
			{
				existinggroups.add(userService.getUserGroupForUID("b2bcustomergroup"));
			}
			if (!existinggroups.contains(userService.getUserGroupForUID("FMNoInvoiceGroup")))
			{
				existinggroups.add(userService.getUserGroupForUID("FMNoInvoiceGroup"));
			}
			if (existinggroups.contains(userService.getUserGroupForUID("FMB2BB")))
			{
				existinggroups.remove(userService.getUserGroupForUID("FMB2BB"));
			}

		}
		else if (custdata.getRoles().contains("FMViewOnlyGroup"))
		{

			if (existinggroups.contains(userService.getUserGroupForUID("FMAdminGroup")))
			{
				existinggroups.remove(userService.getUserGroupForUID("FMAdminGroup"));
			}
			if (existinggroups.contains(userService.getUserGroupForUID("FMFullAccessGroup")))
			{
				existinggroups.remove(userService.getUserGroupForUID("FMFullAccessGroup"));
			}
			if (existinggroups.contains(userService.getUserGroupForUID("FMNoInvoiceGroup")))
			{
				existinggroups.remove(userService.getUserGroupForUID("FMNoInvoiceGroup"));
			}
			/*
			 * if (groups.contains(userService.getUserGroupForUID("FMBUVOGroup"))) {
			 * groups.remove(userService.getUserGroupForUID("FMBUVOGroup")); }
			 */


			if (!existinggroups.contains(userService.getUserGroupForUID("b2bcustomergroup")))
			{
				existinggroups.add(userService.getUserGroupForUID("b2bcustomergroup"));
			}
			if (!existinggroups.contains(userService.getUserGroupForUID("FMViewOnlyGroup")))
			{
				existinggroups.add(userService.getUserGroupForUID("FMViewOnlyGroup"));
			}
			if (existinggroups.contains(userService.getUserGroupForUID("FMB2BB")))
			{
				existinggroups.remove(userService.getUserGroupForUID("FMB2BB"));
			}

		}



		else if (custdata.getRoles().contains("FMBUVOR"))
		{
			LOG.info("%%%%%%%%%%%%% ---Inside FMBUVOGroup START" + existinggroups);

			if (existinggroups.contains(userService.getUserGroupForUID("FMAdminGroup")))
			{
				existinggroups.remove(userService.getUserGroupForUID("FMAdminGroup"));
			}
			if (existinggroups.contains(userService.getUserGroupForUID("FMFullAccessGroup")))
			{
				existinggroups.remove(userService.getUserGroupForUID("FMFullAccessGroup"));
			}
			if (existinggroups.contains(userService.getUserGroupForUID("FMNoInvoiceGroup")))
			{
				existinggroups.remove(userService.getUserGroupForUID("FMNoInvoiceGroup"));
			}
			if (existinggroups.contains(userService.getUserGroupForUID("FMViewOnlyGroup")))
			{
				existinggroups.remove(userService.getUserGroupForUID("FMViewOnlyGroup"));
			}


			if (!existinggroups.contains(userService.getUserGroupForUID("b2bcustomergroup")))
			{
				existinggroups.add(userService.getUserGroupForUID("b2bcustomergroup"));
			}
			if (!existinggroups.contains(userService.getUserGroupForUID("FMBUVOR")))
			{
				existinggroups.add(userService.getUserGroupForUID("FMBUVOR"));
			}
			LOG.info("%%%%%%%%%%%%% ---Inside FMViewOnlyGroup END");

		}
		else if(custdata.getRoles().contains("FMCSR"))
		{
			if (existinggroups.contains(userService.getUserGroupForUID("FMAdminGroup")))
			{
				existinggroups.remove(userService.getUserGroupForUID("FMAdminGroup"));
			}
			if (existinggroups.contains(userService.getUserGroupForUID("FMFullAccessGroup")))
			{
				existinggroups.remove(userService.getUserGroupForUID("FMFullAccessGroup"));
			}
			if (existinggroups.contains(userService.getUserGroupForUID("FMNoInvoiceGroup")))
			{
				existinggroups.remove(userService.getUserGroupForUID("FMNoInvoiceGroup"));
			}
			if (existinggroups.contains(userService.getUserGroupForUID("FMViewOnlyGroup")))
			{
				existinggroups.remove(userService.getUserGroupForUID("FMViewOnlyGroup"));
			}
			if (existinggroups.contains(userService.getUserGroupForUID("FMBUVOR")))
			{
				existinggroups.remove(userService.getUserGroupForUID("FMBUVOR"));
			}


			if (!existinggroups.contains(userService.getUserGroupForUID("b2bcustomergroup")))
			{
				existinggroups.add(userService.getUserGroupForUID("b2bcustomergroup"));
			}
			if (!existinggroups.contains(userService.getUserGroupForUID("FMCSR")))
			{
				existinggroups.add(userService.getUserGroupForUID("FMCSR"));
			}

		}
		return existinggroups;

	}


	/*
	 * (non-Javadoc)
	 *
	 * @see com.federalmogul.facades.network.FMNetworkFacade#enableCustomer(java.lang.String)
	 */
	@Override
	public void enableCustomer(final String uid) throws DuplicateUidException
	{
		validateParameterNotNullStandardMessage("uid", uid);
		fmNetworkService.enableCustomer(uid);
	}

	public static void validateParameterNotNullStandardMessage(final String parameter, final Object parameterValue)
	{
		validateParameterNotNull(parameterValue, "Parameter " + parameter + " can not be null");

	}

	/**
	 * Method to retrieve the Ship Tos for a given Sold To
	 *
	 * @param uid
	 *           Sold To
	 * @return List of Ship Tos
	 */

	@Override
	public List<FMCustomerAccountData> getAdminUserUnits(final String uid)
	{
		return fmNetworkService.getAdminUserUnits(uid);
	}
}
