/**
 * 
 */
package com.federalmogul.core.account.impl;

import com.federalmogul.core.event.*;
import de.hybris.platform.b2bacceleratorservices.customer.impl.DefaultB2BCustomerAccountService;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commerceservices.customer.DuplicateUidException;
import de.hybris.platform.converters.Converters;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.exceptions.AmbiguousIdentifierException;
import de.hybris.platform.servicelayer.exceptions.ModelSavingException;
import de.hybris.platform.servicelayer.interceptor.InterceptorException;
import de.hybris.platform.servicelayer.interceptor.impl.UniqueAttributesInterceptor;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.util.ServicesUtil;
import de.hybris.platform.util.Config;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.federalmogul.core.account.FMCustomerService;
import com.federalmogul.core.enums.Fmusertype;
import com.federalmogul.core.graph.form.CSBProductPermissionForm;
import com.federalmogul.core.model.FMCustomerAccountModel;
import com.federalmogul.core.model.FMCustomerAdminProcessModel;
import com.federalmogul.core.model.FMCustomerModel;
import com.federalmogul.core.model.FMCustomerRegistrationProcessModel;
import com.federalmogul.core.network.dao.FMUserSearchDAO;
import com.federalmogul.facades.address.data.FMB2bAddressData;
import com.federalmogul.facades.user.data.FMCustomerAccountData;
import com.federalmogul.falcon.core.model.CPL1CustomerModel;
import com.federalmogul.falcon.core.model.CSBPercents3612Model;
import com.federalmogul.falcon.core.model.CategorySalesBenchmarkPercentsModel;
import com.federalmogul.core.model.ReportLogProcessModel;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.store.services.BaseStoreService;
import com.federalmogul.core.model.FMCustomerAccountModel;
import com.federalmogul.core.model.FMCustomerModel;

import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.store.services.BaseStoreService;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import javax.annotation.Resource;


/**
 * @author SI279688
 * 
 *         Implementation for FmCustomer service for registration
 */
public class FMCustomerServiceImpl extends DefaultB2BCustomerAccountService implements FMCustomerService
{
	private static final Logger LOG = Logger.getLogger(FMCustomerServiceImpl.class);

	@Autowired
	private ModelService modelService;

	@Autowired
	private EventService eventService;

	@Autowired
	private BusinessProcessService businessProcessService;

	@Autowired
	FMUserSearchDAO fmUserSearchDAO;
	@Autowired
	private CommonI18NService commonI18NService;
	@Resource
	private BaseStoreService baseStoreService;

	@Resource
	private BaseSiteService baseSiteService;

	private Populator<AddressModel, FMB2bAddressData> fmb2bAddressPopulator;

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

	private Populator<FMCustomerAccountModel, FMCustomerAccountData> fmCustomerAccountPopulator;
	private Converter<FMCustomerAccountModel, FMCustomerAccountData> fmCustomerAccountConverter;

	/**
	 * method which will save the fmcustomermodel which comes from fmcustomerfacadeimpl (non-Javadoc)
	 * 
	 * @param fmcustomerModel
	 * @param password
	 */
	@Override
	public void createCustomerAccount(final FMCustomerModel fmcustomerModel, final String password)
	{
		try
		{
			registerCustomer(fmcustomerModel, password);

			LOG.info("email initialized ");

			LOG.info("email started");

			/************* email publishing *********/

			if (fmcustomerModel.getChannelCode().equals(Fmusertype.WAREHOUSEDISTRIBUTORCOMMERCIALVEHICLE)
					|| fmcustomerModel.getChannelCode().equals(Fmusertype.WAREHOUSEDISTRIBUTORLIGHTVEHICLE)
					|| fmcustomerModel.getChannelCode().equals(Fmusertype.RETAILER)
					|| fmcustomerModel.getChannelCode().equals(Fmusertype.JOBBERPARTSTORE)
					|| fmcustomerModel.getChannelCode().equals(Fmusertype.SALESREP))
			{
				if (fmcustomerModel.getFmUnit() != null)
				{
					LOG.info("---------B2B registration TO::Admin email generation STARTTTTTT------------");
					final FMCustomerAdminProcessModel fmCustomerAdminProcessModel = new FMCustomerAdminProcessModel();

				fmCustomerAdminProcessModel.setEmailId(Config.getParameter("csrEmailid"));
				fmCustomerAdminProcessModel.setEndMessage("Approve the user");
				fmCustomerAdminProcessModel.setSite(getBaseSiteService().getCurrentBaseSite());
				fmCustomerAdminProcessModel.setStore(getBaseStoreService().getCurrentBaseStore());
				fmCustomerAdminProcessModel.setLanguage(getCommonI18NService().getCurrentLanguage());

				fmCustomerAdminProcessModel.setCustomer(fmcustomerModel);

				final FMCustomerAdminProcessEvent fmCustomerAdminProcessEvent = new FMCustomerAdminProcessEvent(
						fmCustomerAdminProcessModel);
				eventService.publishEvent(initializeEvent(fmCustomerAdminProcessEvent, fmcustomerModel));
				LOG.info("---------B2B registration TO::Admin email generation ENDDDDDD------------");
				}

			}

			//B2C registration
			else if (fmcustomerModel.getChannelCode().equals(Fmusertype.CONSUMERDIFM)
					|| fmcustomerModel.getChannelCode().equals(Fmusertype.CONSUMERDIY)
					|| fmcustomerModel.getChannelCode().equals(Fmusertype.CONSUMER))
			{

				LOG.info("---------B2C registration TO::User email generation STARTTTTTT------------");

				final FMCustomerRegistrationProcessModel fmCustomerRegistrationProcessModel = new FMCustomerRegistrationProcessModel();

				fmCustomerRegistrationProcessModel.setEmailId(fmcustomerModel.getEmail());
				fmCustomerRegistrationProcessModel.setEndMessage("Hi");
				fmCustomerRegistrationProcessModel.setSite(getBaseSiteService().getCurrentBaseSite());
				fmCustomerRegistrationProcessModel.setStore(getBaseStoreService().getCurrentBaseStore());
				fmCustomerRegistrationProcessModel.setLanguage(getCommonI18NService().getCurrentLanguage());

				fmCustomerRegistrationProcessModel.setCustomer(fmcustomerModel);

				final FMCustomerRegistrationProcessEvent fmCustomerRegistrationProcessEvent = new FMCustomerRegistrationProcessEvent(
						fmCustomerRegistrationProcessModel);

				eventService.publishEvent(initializeEvent(fmCustomerRegistrationProcessEvent, fmcustomerModel));

				LOG.info("----------B2C registration email TO::Admin- END---------");

			}

			else if (fmcustomerModel.getChannelCode().equals(Fmusertype.SHOPOWNERTECHNICIAN)
					|| fmcustomerModel.getChannelCode().equals(Fmusertype.REPAIRSHOPOWNER)
					|| fmcustomerModel.getChannelCode().equals(Fmusertype.TECHNICIAN)
					|| fmcustomerModel.getChannelCode().equals(Fmusertype.STUDENT))
			{
				LOG.info("------11111111111---B2SB/B2T registration TO::User email generation STARTTTTTT------------");

				final FMCustomerRegistrationProcessModel fmCustomerRegistrationProcessModel = new FMCustomerRegistrationProcessModel();

				fmCustomerRegistrationProcessModel.setEmailId(fmcustomerModel.getEmail());
				fmCustomerRegistrationProcessModel.setEndMessage("Hi");
				fmCustomerRegistrationProcessModel.setSite(getBaseSiteService().getCurrentBaseSite());
				fmCustomerRegistrationProcessModel.setStore(getBaseStoreService().getCurrentBaseStore());
				fmCustomerRegistrationProcessModel.setLanguage(getCommonI18NService().getCurrentLanguage());

				fmCustomerRegistrationProcessModel.setCustomer(fmcustomerModel);

				final FMCustomerRegistrationProcessEvent fmCustomerRegistrationProcessEvent = new FMCustomerRegistrationProcessEvent(
						fmCustomerRegistrationProcessModel);

				eventService.publishEvent(initializeEvent(fmCustomerRegistrationProcessEvent, fmcustomerModel));

				LOG.info("----------END B2SB/B2T registration email TO::User----------");
			}
			/************* end of email publishing ************/
		}
		catch (final DuplicateUidException e)
		{
			LOG.error(e.getMessage());
		}
	}

	/**
	 * method which will call the generateCustomerId method and will set the encoded password and will call the
	 * internalSaveCustomer method
	 * 
	 * @param fmcustomerModel
	 * @param password
	 * @throws DuplicateUidException
	 */
	protected void registerCustomer(final FMCustomerModel fmcustomerModel, final String password) throws DuplicateUidException
	{
		ServicesUtil.validateParameterNotNullStandardMessage("fmcustomerModel", fmcustomerModel);

		generateCustomerId(fmcustomerModel);
		if (password != null)
		{
			setPassword(fmcustomerModel, password, getPasswordEncoding());
			fmcustomerModel.setPwdMeetsLatestStandards(true); // FAL - 219 - Sprint 9
		}
		internalSaveCustomer(fmcustomerModel);
	}

	/**
	 * method which will call the model service and save the customer details in the backend
	 * 
	 * @param fmcustomerModel
	 * @throws DuplicateUidException
	 */
	protected void internalSaveCustomer(final FMCustomerModel fmcustomerModel) throws DuplicateUidException
	{
		try
		{
			getModelService().save(fmcustomerModel);
		}
		catch (final ModelSavingException e)
		{
			if ((e.getCause() instanceof InterceptorException)
					&& (((InterceptorException) e.getCause()).getInterceptor().getClass().equals(UniqueAttributesInterceptor.class)))
			{
				throw new DuplicateUidException(fmcustomerModel.getUid(), e);
			}

			throw e;
		}
		catch (final AmbiguousIdentifierException e)
		{
			throw new DuplicateUidException(fmcustomerModel.getUid(), e);
		}
	}

	/**
	 * method which will generate the customerid
	 * 
	 * @param customerModel
	 */
	protected void generateCustomerId(final FMCustomerModel customerModel)
	{
		customerModel.setCustomerID(UUID.randomUUID().toString());
	}

	/**
	 * method which will set the encoded password
	 * 
	 * @param fmcustomerModel
	 * @param plainPassword
	 * @param encoding
	 */
	public void setPassword(final FMCustomerModel fmcustomerModel, final String plainPassword, final String encoding)
	{
		fmcustomerModel.setEncodedPassword(this.getPasswordEncoderService().encode(fmcustomerModel, plainPassword, encoding));
		fmcustomerModel.setPasswordEncoding(encoding);
	}


	public FMCustomerAdminProcessEvent initializeEvent(final FMCustomerAdminProcessEvent event, final CustomerModel customerModel)
	{
		try
		{
			LOG.info("--------------------EEEEEEEEEEE---initializing event::STARTTTTTTTTTTTTT----getBaseStoreService().getCurrentBaseStore():"
					+ getBaseStoreService().getCurrentBaseStore()
					+ "-------getBaseSiteService().getCurrentBaseSite()::"
					+ getBaseSiteService().getCurrentBaseSite()
					+ "------customerModel:"
					+ customerModel
					+ "-------getCommonI18NService().getCurrentLanguage()::"
					+ getCommonI18NService().getCurrentLanguage()
					+ "----getCommonI18NService().getCurrentCurrency()::" + getCommonI18NService().getCurrentCurrency());
			event.setBaseStore(getBaseStoreService().getCurrentBaseStore());
			event.setSite(getBaseSiteService().getCurrentBaseSite());
			event.setCustomer(customerModel);
			event.setLanguage(getCommonI18NService().getCurrentLanguage());
			event.setCurrency(getCommonI18NService().getCurrentCurrency());
			LOG.info("--------------------EEEEEEEEEEE---initializing event::ENDDDDDDDDDDDDDDDDDDDD");
			return event;
		}
		catch (final Exception e)
		{
			LOG.error(e.getMessage());
		}
		return null;
	}

	public FMCustomerRegistrationProcessEvent initializeEvent(final FMCustomerRegistrationProcessEvent event,
			final CustomerModel customerModel)
	{
		try
		{
			event.setBaseStore(getBaseStoreService().getCurrentBaseStore());
			event.setSite(getBaseSiteService().getCurrentBaseSite());
			event.setCustomer(customerModel);
			event.setLanguage(getCommonI18NService().getCurrentLanguage());
			event.setCurrency(getCommonI18NService().getCurrentCurrency());
			return event;
		}
		catch (final Exception e)
		{
			LOG.error(e.getMessage());
		}
		return null;
	}

	public UploadOrderEvent initializeEvent(final UploadOrderEvent event, final CustomerModel customerModel)
	{
		try
		{
			event.setBaseStore(getBaseStoreService().getCurrentBaseStore());
			event.setSite(getBaseSiteService().getCurrentBaseSite());
			event.setCustomer(customerModel);
			event.setLanguage(getCommonI18NService().getCurrentLanguage());
			event.setCurrency(getCommonI18NService().getCurrentCurrency());
			return event;
		}
		catch (final Exception e)
		{
			LOG.error(e.getMessage());
		}
		return null;
	}

	public FMUploadOrderEmailProcessEvent initializeEvent(final FMUploadOrderEmailProcessEvent event,
			final CustomerModel customerModel)
	{
		try
		{
			event.setBaseStore(getBaseStoreService().getCurrentBaseStore());
			event.setSite(getBaseSiteService().getCurrentBaseSite());
			event.setCustomer(customerModel);
			event.setLanguage(getCommonI18NService().getCurrentLanguage());
			event.setCurrency(getCommonI18NService().getCurrentCurrency());
			return event;
		}
		catch (final Exception e)
		{
			LOG.error(e.getMessage());
		}
		return null;
	}

	/**
	 * @return the modelService
	 */
	@Override
	public ModelService getModelService()
	{
		return modelService;
	}

	/**
	 * @param modelService
	 *           the modelService to set
	 */
	@Override
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}

	/**
	 * @return the eventService
	 */
	@Override
	public EventService getEventService()
	{
		return eventService;
	}

	/**
	 * @param eventService
	 *           the eventService to set
	 */
	@Override
	public void setEventService(final EventService eventService)
	{
		this.eventService = eventService;
	}

	/**
	 * @return the businessProcessService
	 */
	public BusinessProcessService getBusinessProcessService()
	{
		return businessProcessService;
	}

	/**
	 * @param businessProcessService
	 *           the businessProcessService to set
	 */
	public void setBusinessProcessService(final BusinessProcessService businessProcessService)
	{
		this.businessProcessService = businessProcessService;
	}

	@Override
	public boolean checkUidExists(final String email)
	{
		LOG.info("Result from DAO - service" + fmUserSearchDAO.checkUidForID(email));
		return fmUserSearchDAO.checkUidForID(email);
	}

	@Override
	public boolean checkTaxIdExists(final String taxId)
	{
		LOG.info("Result from DAO - service" + fmUserSearchDAO.checkTaxForID(taxId));
		return fmUserSearchDAO.checkTaxForID(taxId);
	}

	@Override
	public boolean checkProspectid(final String userUid)
	{
		LOG.info("Result from DAO - service" + fmUserSearchDAO.checkProspectid(userUid));
		return fmUserSearchDAO.checkProspectid(userUid);
	}

	@Override
	public List<AddressData> getSoldToUnitAddress(final String uid)
	{

		final List<AddressData> soldToAddressData = fmUserSearchDAO.getSoldToUnitAddress(uid);
		LOG.info("Result from DAO - service" + fmUserSearchDAO.getSoldToUnitAddress(uid));
		return soldToAddressData;
	}

	@Override
	public List<FMCustomerAccountData> getSoldToShipToUnitUid(final String uid)
	{
		final List<FMCustomerAccountModel> listGetFMUnitUidModel = fmUserSearchDAO.getSoldToShipToUnitUid(uid);
		final List<FMCustomerAccountData> listGetFMUnitUidData = new ArrayList<FMCustomerAccountData>();

		if (listGetFMUnitUidModel != null)
		{
			final Iterator i = listGetFMUnitUidModel.iterator();
			while (i.hasNext())
			{
				final FMCustomerAccountData fmUnitUidData = new FMCustomerAccountData();
				final FMCustomerAccountModel fMUnitUidModel = (FMCustomerAccountModel) i.next();
				final List<FMB2bAddressData> listAddressdata = new ArrayList<FMB2bAddressData>();
				final List<AddressModel> listAddressmodel = (List<AddressModel>) fMUnitUidModel.getAddresses();
				final Iterator i1 = listAddressmodel.iterator();
				while (i1.hasNext())
				{
					final AddressModel addressModel = (AddressModel) i1.next();
					final FMB2bAddressData addressData = new FMB2bAddressData();
					getFmb2bAddressPopulator().populate(addressModel, addressData);
					addressData.setCompanyName(fMUnitUidModel.getLocName());
					listAddressdata.add(addressData);
					LOG.info("getSoldToShipToUnitUid(...): list_addressdata :: " + listAddressdata);
				}
				fmUnitUidData.setName(fMUnitUidModel.getLocName());
				fmUnitUidData.setUid(fMUnitUidModel.getUid());
				fmUnitUidData.setUnitAddress(listAddressdata);
				fmUnitUidData.setNabsAccountCode(fMUnitUidModel.getNabsAccountCode());
				fmUnitUidData.setDistributionChannel(fMUnitUidModel.getDistributionChannel());
				fmUnitUidData.setSalesorg(fMUnitUidModel.getSalesorg());
				fmUnitUidData.setDivision(fMUnitUidModel.getDivision());
				listGetFMUnitUidData.add(fmUnitUidData);
			}

		}
		return listGetFMUnitUidData;
	}

	@Override
	public List<FMCustomerAccountModel> getB2BAddressSearch(final String searchString)
	{
		return fmUserSearchDAO.getB2BAddressSearch(searchString);
	}

	@Override
	public List<CSBProductPermissionForm> getPermissions(final String soldToAccount)
	{
		// YTODO Auto-generated method stub
		return fmUserSearchDAO.getPermissions(soldToAccount);
	}

	@Override
	public List<CategorySalesBenchmarkPercentsModel> getUnitorSaleChangePercentsModel(final String cpl1Code)
	{ // YTODO Auto-generated method stub return
		return fmUserSearchDAO.getUnitorSaleChangePercentsModel(cpl1Code);
	}
 
	@Override
	public String getDesc(final String productCode)
	{
		// YTODO Auto-generated method stub
		return fmUserSearchDAO.getDesc(productCode);
	}

	@Override
	public CSBPercents3612Model getYearOverYearComparison(final String inputProductCode)
	{
		// YTODO Auto-generated method stub
		return fmUserSearchDAO.getYearOverYearComparison(inputProductCode);
	}

	@Override
	public List<CPL1CustomerModel> getAllProducts()
	{
		// YTODO Auto-generated method stub 
		return fmUserSearchDAO.getAllProducts();
	}

	@Override
	public String checkSoldToShipto(final String uid)
	{
		final String soldtoshiptostring = fmUserSearchDAO.checkSoldToShipto(uid);
		return soldtoshiptostring;
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
	 * @return the fmCustomerAccountConverter
	 */
	public Converter<FMCustomerAccountModel, FMCustomerAccountData> getFmCustomerAccountConverter()
	{
		return fmCustomerAccountConverter;
	}

	/**
	 * @param fmCustomerAccountConverter the fmCustomerAccountConverter to set
	 */
	public void setFmCustomerAccountConverter(Converter<FMCustomerAccountModel, FMCustomerAccountData> fmCustomerAccountConverter)
	{
		this.fmCustomerAccountConverter = fmCustomerAccountConverter;
	}

	@Override
	public void createReportLog(FMCustomerAccountModel accModel,FMCustomerModel custModel,String eventType){
		final ReportLogProcessModel reportProcess = new ReportLogProcessModel();
		reportProcess.setCustomerAccount(accModel);
		reportProcess.setEventType(eventType);
		reportProcess.setUser(custModel);
		reportProcess.setSite(baseSiteService.getCurrentBaseSite());
		reportProcess.setStore(baseStoreService.getCurrentBaseStore());
		final ReportLogEvent logEvent = new ReportLogEvent(reportProcess);
		eventService.publishEvent(initializeEvent(logEvent,custModel));
	}
	
	private ReportLogEvent initializeEvent(final ReportLogEvent event,
			final FMCustomerModel customerModel)
	{
		LOG.info("Inside initialize event");
		try
		{
			event.setBaseStore(baseStoreService.getCurrentBaseStore());
			LOG.info("BASE STORE" + baseStoreService.getCurrentBaseStore().getName());
			event.setSite(baseSiteService.getCurrentBaseSite());
			LOG.info("BASE SITE" + baseSiteService.getCurrentBaseSite().getName());
			event.setCustomer(customerModel);
			LOG.info("FROM EVEN CUSTOMER ID" + event.getCustomer().getUid());
			event.setLanguage(commonI18NService.getCurrentLanguage());
			event.setCurrency(commonI18NService.getCurrentCurrency());
			LOG.info("b4 returning ReportLogEvent");
			return event;
		}
		catch (final Exception e)
		{
			LOG.error(e.getMessage());
		}
		return null;
	}

}
