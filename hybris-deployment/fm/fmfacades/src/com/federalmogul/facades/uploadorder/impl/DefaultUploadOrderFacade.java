/**
 * 
 */
package com.federalmogul.facades.uploadorder.impl;

import de.hybris.platform.b2bacceleratorservices.company.CompanyB2BCommerceService;
import de.hybris.platform.catalog.CatalogService;
import de.hybris.platform.catalog.model.CatalogModel;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.core.model.c2l.CountryModel;
import de.hybris.platform.core.model.c2l.RegionModel;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.core.model.product.UnitModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.europe1.model.PriceRowModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.keygenerator.impl.PersistentKeyGenerator;
import de.hybris.platform.servicelayer.media.MediaService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;
import com.fmo.wom.domain.WeightBO;
import com.federalmogul.core.model.FMCustomerAccountModel;
import com.federalmogul.core.model.FMCustomerModel;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.store.services.BaseStoreService;



import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.federalmogul.core.account.impl.FMCustomerServiceImpl;
import com.federalmogul.core.enums.UploadOrderStatus;
import com.federalmogul.core.event.FMUploadOrderEmailProcessEvent;
import com.federalmogul.core.event.UploadOrderEvent;
import com.federalmogul.core.model.FMCustomerAccountModel;
import com.federalmogul.core.model.FMCustomerModel;
import com.federalmogul.core.model.UploadOrderProcessEmailNotificationModel;
import com.federalmogul.core.model.UploadOrderProcessModel;
import com.federalmogul.core.uploadorder.dao.impl.DefaultUploadOrderDAO;
import com.federalmogul.facades.account.FMCustomerFacade;
import com.federalmogul.facades.upload.order.data.UploadOrderHistoryData;
import com.federalmogul.facades.upload.order.data.uploadOrderData;
import com.federalmogul.facades.upload.order.data.uploadOrderEntryData;
import com.federalmogul.facades.uploadorder.UploadOrderFacade;
import com.federalmogul.facades.user.data.FMCustomerAccountData;
import com.federalmogul.falcon.core.model.FMPartModel;
import com.federalmogul.falcon.core.model.UploadOrderEntryModel;
import com.federalmogul.falcon.core.model.UploadOrderHistoryModel;
import com.federalmogul.falcon.core.model.UploadOrderModel;
import com.federalmogul.core.event.ReportLogEvent;
import com.federalmogul.core.model.ReportLogProcessModel;
import com.federalmogul.core.account.FMCustomerService;





/**
 * @author mamud
 * 
 */
public class DefaultUploadOrderFacade implements UploadOrderFacade
{
	private static final Logger LOG = Logger.getLogger(DefaultUploadOrderFacade.class);

	@Autowired
	private ModelService modelService;

	@Resource
	private CatalogService catalogService;

	@Resource
	protected FMCustomerFacade fmCustomerFacade;

	@Resource
	private PersistentKeyGenerator orderCodeGenerator;
	@Resource(name = "fmcustomerservice")
	private FMCustomerService fmcustomerservice;

	@Resource
	private DefaultUploadOrderDAO fmUploadOrderDAO;

	@Autowired
	private UserService userService;

	@Autowired
	private SessionService sessionService;


	@Resource
	private MediaService mediaService;
	@Resource
	private ProductService productService;
	@Autowired
	private EventService eventService;
	@Resource
	private BaseStoreService baseStoreService;
	@Resource
	private BaseSiteService baseSiteService;
	@Autowired
	private FMCustomerServiceImpl fmCustomerServiceImpl;

	@Resource
	protected CompanyB2BCommerceService companyB2BCommerceService;

	@Autowired
	private CommonI18NService commonI18NService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.federalmogul.facades.uploadorder.UploadOrderFacade#createUploadorder(com.federalmogul.facades.upload.order
	 * .data.uploadOrderData)
	 */
	@Override
	public void createUploadorder(final uploadOrderData orderData)
	{
		// YTODO Auto-generated method stub

		final String soldToAcc = (String) sessionService.getAttribute("SoldTo_Account_Id");
		final FMCustomerAccountModel fmCustomerAccModel = (FMCustomerAccountModel) companyB2BCommerceService
				.getUnitForUid(soldToAcc);
		final String shipToAcc = (String) sessionService.getAttribute("ShipTo_Account_Id");
		final FMCustomerAccountModel fmCustomerAccountModel = (FMCustomerAccountModel) companyB2BCommerceService
				.getUnitForUid(shipToAcc);


		final FMCustomerModel customer = (FMCustomerModel) userService.getCurrentUser();
		AddressModel soldToAddress = null;
		AddressModel shipToAddress = null;
		final List<AddressModel> addressModel = (List<AddressModel>) fmCustomerAccModel.getAddresses();
		for (final AddressModel address : addressModel)
		{
			soldToAddress = address;
			soldToAddress.setCompany(fmCustomerAccModel.getLocName());
		}
		final List<AddressModel> addressModelList = (List<AddressModel>) fmCustomerAccountModel.getAddresses();
		for (final AddressModel address : addressModelList)
		{
			shipToAddress = address;
			shipToAddress.setCompany(fmCustomerAccountModel.getLocName());
			shipToAddress.setShippingAddress(false);

		}
		boolean isManualShipTo = false;
		final AddressData manualShipToAddress = (AddressData) sessionService.getAttribute("shiptToAddress");
		final AddressModel manualShipTo = new AddressModel();
		if (manualShipToAddress != null)
		{
			manualShipTo.setOwner(customer);
			manualShipTo.setFirstname(manualShipToAddress.getFirstName());
			manualShipTo.setLastname(manualShipToAddress.getLastName());
			manualShipTo.setStreetname(manualShipToAddress.getLine1());
			manualShipTo.setStreetnumber(manualShipToAddress.getLine2());
			manualShipTo.setPostalcode(manualShipToAddress.getPostalCode());
			manualShipTo.setTown(manualShipToAddress.getTown());
			manualShipTo.setRemarks("manualShipToAddress");
			final CountryModel country = commonI18NService.getCountry(manualShipToAddress.getRegion().getIsocode().split("-")[0]);
			final RegionModel region = commonI18NService.getRegion(country, manualShipToAddress.getRegion().getIsocode());
			manualShipTo.setRegion(region);
			manualShipTo.setCountry(country);
			manualShipTo.setShippingAddress(true);
			isManualShipTo = true;
			//shipToAddress = manualShipTo;
			modelService.save(manualShipTo);
		}

		final UploadOrderModel uploadOrderModel = modelService.create(UploadOrderModel.class);
		final String uploadOrderNumber = "UO" + (String) orderCodeGenerator.generate();
		uploadOrderModel.setCode(null != orderData.getCode() ? orderData.getCode() : uploadOrderNumber);
		uploadOrderModel.setPONumber(orderData.getPONumber());
		uploadOrderModel.setUserFirstName(customer.getName());
		uploadOrderModel.setUserLastName(customer.getLastName());
		uploadOrderModel.setOrdercomments(orderData.getOrdercomments());
		final MediaModel uploadFile = convertFileMedia(orderData, uploadOrderNumber);
		uploadOrderModel.setUploadOrderFile(uploadFile);
		uploadOrderModel.setUser(customer);
		uploadOrderModel.setShipToAddress(isManualShipTo ? manualShipTo : shipToAddress);
		uploadOrderModel.setBillToAddress(soldToAddress);
		uploadOrderModel.setSoldToAccount(fmCustomerAccModel);
		uploadOrderModel.setShipToAccount(fmCustomerAccountModel);
		uploadOrderModel.setCreationtime(new Date());
		uploadOrderModel.setUpdatedTime(new Date());
		setHistory(uploadOrderModel, "uploadOrderModel", "NEW UPLOAD Order", "File Saved", "Uploaded");
		uploadOrderModel.setUploadOrderStatus(UploadOrderStatus.NEW);
		modelService.save(uploadOrderModel);
		final UploadOrderProcessModel uploadOrderProcessModel = new UploadOrderProcessModel();
		uploadOrderProcessModel.setOrder(uploadOrderModel);
		final UploadOrderEvent uploadOrderEvent = new UploadOrderEvent(uploadOrderProcessModel);
		LOG.info("Upload Order Event Starts");
		eventService.publishEvent(fmCustomerServiceImpl.initializeEvent(uploadOrderEvent, customer));

		//Email Publishing

		/*
		 * final UploadOrderProcessEmailNotificationModel uploadOrderEmail = new
		 * UploadOrderProcessEmailNotificationModel(); uploadOrderEmail.setOrder(uploadOrderModel);
		 * uploadOrderEmail.setEmailId(customer.getUid()); final FMUploadOrderEmailProcessEvent uploadOrderEmailEvent =
		 * new FMUploadOrderEmailProcessEvent(uploadOrderEmail); LOG.info("Upload Order Email Event Starts");
		 * eventService.publishEvent(fmCustomerServiceImpl.initializeEvent(uploadOrderEmailEvent, customer));
		 */

	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.federalmogul.facades.uploadorder.UploadOrderFacade#deleteUploadorder(com.federalmogul.facades.upload.order
	 * .data.uploadOrderData)
	 */
	@Override
	public boolean deleteUploadorder(final String code)
	{
		// YTODO Auto-generated method stub
		final UploadOrderModel order = fmUploadOrderDAO.viewUploadOrderEntry(code);
		if (order != null)
		{
			order.setUploadOrderStatus(UploadOrderStatus.CANCELLED);
			setHistory(order, "uploadOrderModel", "Status", "CANCELLED", "Upload Order Cancelled");
			modelService.save(order);

			final UploadOrderProcessEmailNotificationModel uploadOrderEmail = new UploadOrderProcessEmailNotificationModel();
			uploadOrderEmail.setOrder(order);
			uploadOrderEmail.setEmailId(order.getUser().getUid());
			final FMUploadOrderEmailProcessEvent uploadOrderEmailEvent = new FMUploadOrderEmailProcessEvent(uploadOrderEmail);
			LOG.info("Upload Order Email Event Starts for Cancel order");
			eventService.publishEvent(fmCustomerServiceImpl.initializeEvent(uploadOrderEmailEvent, order.getUser()));

			return true;
		}
		return false;

	}

	public MediaModel convertFileMedia(final uploadOrderData orderData, final String uploadOrderNumber)
	{
		MediaModel uploadFileMedia = null;
		try
		{

			final MultipartFile file = orderData.getUploadfile();

			if (file != null && !file.isEmpty() && file.getSize() > 0)
			{
				final byte[] bytes = file.getBytes();
				uploadFileMedia = new MediaModel();
				uploadFileMedia.setCode(uploadOrderNumber + "_" + file.getOriginalFilename());
				final CatalogModel cm = catalogService.getCatalogForId("federalmogulContentCatalog");
				final Set catalogModelSet = cm.getCatalogVersions();
				if (catalogModelSet != null)
				{
					final Iterator itr = catalogModelSet.iterator();
					final CatalogVersionModel catalogVersionModel = (CatalogVersionModel) itr.next();
					uploadFileMedia.setCatalogVersion(catalogVersionModel);
				}
				uploadFileMedia.setDescription(file.getOriginalFilename());
				modelService.save(uploadFileMedia);
				try
				{
					final InputStream inputStream = new ByteArrayInputStream(bytes);
					mediaService.setStreamForMedia(uploadFileMedia, inputStream, file.getOriginalFilename(), file.getContentType());
				}
				catch (final NullPointerException e)
				{
					LOG.error(e.getMessage());
				}
				return uploadFileMedia;

			}
		}
		catch (final NullPointerException | IOException ex)
		{
			LOG.error(ex.getMessage());
			return uploadFileMedia;
		}
		return uploadFileMedia;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.federalmogul.facades.uploadorder.UploadOrderFacade#viewUploadorder(com.federalmogul.facades.upload.order.data
	 * .uploadOrderData)
	 */
	@Override
	public List<uploadOrderData> viewUploadorder()
	{
		// YTODO Auto-generated method stub
		final FMCustomerModel customer = (FMCustomerModel) userService.getCurrentUser();
		final List<uploadOrderData> orderDatas = new ArrayList<uploadOrderData>();
		final List<UploadOrderModel> orderModel = fmUploadOrderDAO.viewUploadorder(customer.getUid());
		if (orderModel != null && !orderModel.isEmpty())
		{
			for (final UploadOrderModel order : orderModel)
			{
				final uploadOrderData orderData = new uploadOrderData();
				orderData.setCode(order.getCode());
				orderData.setPONumber(order.getPONumber());
				if (null != order.getSoldToAccount())
				{
					final FMCustomerAccountModel soldToUnit = order.getSoldToAccount();
					final FMCustomerAccountData soldTounitData = new FMCustomerAccountData();
					soldTounitData.setUid(soldToUnit.getUid());
					orderData.setSoldToAccount(soldTounitData);
					orderData.setSapordernumber(order.getSapordernumber());
					orderData.setUserFirstName(order.getUserFirstName());
					orderData.setUpdatedTime(order.getUpdatedTime());
					orderData.setStatus(order.getUploadOrderStatus().getCode().toString());
					orderData.setUploadfilemedia(order.getUploadOrderFile());
					orderDatas.add(orderData);
				}
			}
		}
		return orderDatas;
	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.facades.uploadorder.UploadOrderFacade#viewUploadorderEntry()
	 */
	@Override
	public List<uploadOrderEntryData> viewUploadorderEntry(final String code)
	{

		// YTODO Auto-generated method stub
		final List<uploadOrderEntryData> orderEntryDatas = new ArrayList<uploadOrderEntryData>();
		final UploadOrderModel order = fmUploadOrderDAO.viewUploadOrderEntry(code);
		if (order != null)
		{
			for (final UploadOrderEntryModel orderEntry : order.getEntries())
			{
				final uploadOrderEntryData orderEntryData = new uploadOrderEntryData();
				orderEntryData.setPartNumber(orderEntry.getPartNumber());
				orderEntryData.setPartResolutionMsg(orderEntry.getPartResolutionMsg());
				final FMCustomerAccountModel acc = orderEntry.getAccountcode();
				final FMCustomerAccountData account = new FMCustomerAccountData();
				account.setUid(acc.getUid());
				orderEntryData.setQuantity(orderEntry.getQuantity());
				orderEntryData.setRawPartNumber(orderEntry.getRawPartNumber());
				orderEntryData.setBasePrice(orderEntry.getBasePrice());
				orderEntryData.setEntryNumber(orderEntry.getOrderentryNumber());
				orderEntryData.setAccountcode(account);
				orderEntryData.setUpdatedTime(orderEntry.getUpdatedTime());
				orderEntryData.setStatus(order.getUploadOrderStatus().getCode().toString());
				orderEntryData.setProductDescription(orderEntry.getPartDescription());
				orderEntryData.setProductFlag(orderEntry.getPartFlag());
				orderEntryDatas.add(orderEntryData);

			}
		}
		return orderEntryDatas;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.facades.uploadorder.UploadOrderFacade#editUploadorderEntry()
	 */
	@Override
	public boolean editUploadorderEntry(final String code, final String partnumber, final String quantity, final String entryCode,
			final String productFlag)
	{
		// YTODO Auto-generated method stub
		final UploadOrderModel order = fmUploadOrderDAO.viewUploadOrderEntry(code);
		if (order != null)
		{
			for (final UploadOrderEntryModel orderEntry : order.getEntries())
			{
				if (orderEntry.getOrderentryNumber().equalsIgnoreCase(entryCode))
				{
					orderEntry.setPartNumber(partnumber);
					orderEntry.setQuantity(Integer.valueOf(quantity).intValue());
					orderEntry.setPartFlag(productFlag);
					setHistory(orderEntry, "uploadOrderEntryModel", "Update", entryCode, partnumber + "-" + quantity + "-"
							+ productFlag);
					modelService.save(orderEntry);
					return true;
				}

			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.facades.uploadorder.UploadOrderFacade#searchUploadorder(java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<uploadOrderData> searchUploadorder(final String ponumber, final String sapordernumber, final String sdate,
			final String edate, final String status)
	{
		// YTODO Auto-generated method stub

		final FMCustomerModel customer = (FMCustomerModel) userService.getCurrentUser();
		final List<uploadOrderData> orderDatas = new ArrayList<uploadOrderData>();
		final List<UploadOrderModel> orderModel = fmUploadOrderDAO.searchUploadorder(customer.getUid(), ponumber, sapordernumber,
				sdate, edate, status);
		if (orderModel != null && !orderModel.isEmpty())
		{
			for (final UploadOrderModel order : orderModel)
			{
				final uploadOrderData orderData = new uploadOrderData();
				orderData.setCode(order.getCode());
				orderData.setPONumber(order.getPONumber());
				if (null != order.getSoldToAccount())
				{
					final FMCustomerAccountModel soldToUnit = order.getSoldToAccount();
					final FMCustomerAccountData soldTounitData = new FMCustomerAccountData();
					soldTounitData.setUid(soldToUnit.getUid());
					orderData.setSoldToAccount(soldTounitData);
					orderData.setSapordernumber(order.getSapordernumber());
					orderData.setUserFirstName(order.getUserFirstName());
					orderData.setUpdatedTime(order.getUpdatedTime());
					orderData.setStatus(order.getUploadOrderStatus().getCode().toString());
					orderData.setUploadfilemedia(order.getUploadOrderFile());
					orderDatas.add(orderData);
				}
			}
		}
		return orderDatas;
	}

	protected void setHistory(final UploadOrderModel orderModel, final String attribute, final String from, final String to,
			final String status)
	{
		final UploadOrderHistoryModel history = modelService.create(UploadOrderHistoryModel.class);
		history.setModifiedattribute(attribute);
		history.setUpdatedTime(new Date());
		history.setModifiedValueFrom(from);
		history.setModifiedValueTo(to);
		history.setOrder(orderModel);
		history.setStatus(status);
		modelService.save(history);
	}

	protected void setHistory(final UploadOrderEntryModel orderModel, final String attribute, final String from, final String to,
			final String status)
	{

		final UploadOrderHistoryModel history = modelService.create(UploadOrderHistoryModel.class);
		history.setModifiedattribute(attribute);
		history.setUpdatedTime(new Date());
		history.setModifiedValueFrom(from);
		history.setModifiedValueTo(to);
		history.setEntry(orderModel);
		history.setStatus(status);
		modelService.save(history);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.facades.uploadorder.UploadOrderFacade#viewUploadorderHistory(java.lang.String)
	 */
	@Override
	public List<UploadOrderHistoryData> viewUploadorderHistory(final String code)
	{
		// YTODO Auto-generated method stub
		final List<UploadOrderHistoryData> orderHistorys = new ArrayList<UploadOrderHistoryData>();
		final UploadOrderModel order = fmUploadOrderDAO.viewUploadOrderEntry(code);
		if (order != null)
		{
			for (final UploadOrderHistoryModel orderHistory : order.getHistory())
			{
				final UploadOrderHistoryData orderHistoryData = new UploadOrderHistoryData();
				orderHistoryData.setStatus(orderHistory.getStatus());
				orderHistoryData.setModifiedValueFrom(orderHistory.getModifiedValueFrom());
				orderHistoryData.setModifiedValueTo(orderHistory.getModifiedValueTo());
				orderHistoryData.setUser(order.getUserFirstName());
				orderHistoryData.setOrderNumber(order.getCode());
				orderHistoryData.setUpdatedTime(orderHistory.getUpdatedTime());
				orderHistorys.add(orderHistoryData);
			}
		}
		return orderHistorys;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.facades.uploadorder.UploadOrderFacade#deleteUploadorderEntry()
	 */
	@Override
	public boolean deleteUploadorderEntry(final String code, final String entryCode)
	{
		// YTODO Auto-generated method stub
		final UploadOrderModel order = fmUploadOrderDAO.viewUploadOrderEntry(code);
		if (order != null)
		{
			for (final UploadOrderEntryModel orderEntry : order.getEntries())
			{
				if (orderEntry.getOrderentryNumber().equalsIgnoreCase(entryCode))
				{
					setHistory(order, "uploadOrderEntryModel", "Remove", entryCode, "Part Removed");
					modelService.remove(orderEntry.getPk());
					modelService.refresh(order);
					modelService.save(order);
					return true;
				}

			}
		}
		return false;
	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.facades.uploadorder.UploadOrderFacade#saveUploadorder(java.lang.String)
	 */
	@Override
	public boolean saveUploadorder(final String code)
	{
		// YTODO Auto-generated method stub
		final FMCustomerModel customer = (FMCustomerModel) userService.getCurrentUser();
		final UploadOrderModel uploadOrderModel = fmUploadOrderDAO.viewUploadOrderEntry(code);
		setHistory(uploadOrderModel, "uploadOrderModel", "Re-Trigger", "File ReProcess", "Uploaded");
		uploadOrderModel.setUploadOrderStatus(UploadOrderStatus.FILEPARSED);
		uploadOrderModel.setUpdatedTime(new Date());
		modelService.save(uploadOrderModel);
		final UploadOrderProcessModel uploadOrderProcessModel = new UploadOrderProcessModel();
		uploadOrderProcessModel.setOrder(uploadOrderModel);
		final UploadOrderEvent uploadOrderEvent = new UploadOrderEvent(uploadOrderProcessModel);
		LOG.info("Upload Order Event Starts");
		eventService.publishEvent(fmCustomerServiceImpl.initializeEvent(uploadOrderEvent, customer));
		return true;
	}





	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.facades.uploadorder.UploadOrderFacade#viewCSRUploadorder()
	 */
	@Override
	public List<uploadOrderData> viewCSRUploadorder(final String accountCode)
	{
		// YTODO Auto-generated method stub
		LOG.info("viewCSRUploadorder{} Inside Account Code" + accountCode);
		final List<uploadOrderData> orderDatas = new ArrayList<uploadOrderData>();
		final List<UploadOrderModel> orderModel = fmUploadOrderDAO.viewCSRUploadorder(accountCode);
		if (orderModel != null && !orderModel.isEmpty())
		{
			for (final UploadOrderModel order : orderModel)
			{
				final uploadOrderData orderData = new uploadOrderData();
				orderData.setCode(order.getCode());
				orderData.setPONumber(order.getPONumber());
				if (null != order.getSoldToAccount())
				{
					final FMCustomerAccountModel soldToUnit = order.getSoldToAccount();
					final FMCustomerAccountData soldTounitData = new FMCustomerAccountData();
					soldTounitData.setUid(soldToUnit.getUid());
					orderData.setSoldToAccount(soldTounitData);
					orderData.setSapordernumber(order.getSapordernumber());
					orderData.setUserFirstName(order.getUserFirstName());
					orderData.setUpdatedTime(order.getUpdatedTime());
					orderData.setStatus(order.getUploadOrderStatus().getCode().toString());
					orderData.setUploadfilemedia(order.getUploadOrderFile());
					orderDatas.add(orderData);
				}
			}
		}
		return orderDatas;
	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.facades.uploadorder.UploadOrderFacade#searchCSRUploadorder(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<uploadOrderData> searchCSRUploadorder(final String code, final String ponumber, final String sapordernumber,
			final String sdate, final String edate, final String status)
	{
		// YTODO Auto-generated method stub
		LOG.info("searchCSRUploadorder{} Inside Account Code" + code);
		final List<uploadOrderData> orderDatas = new ArrayList<uploadOrderData>();
		final List<UploadOrderModel> orderModel = fmUploadOrderDAO.searchCSRUploadorder(code, ponumber, sapordernumber, sdate,
				edate, status);
		if (orderModel != null && !orderModel.isEmpty())
		{
			for (final UploadOrderModel order : orderModel)
			{
				final uploadOrderData orderData = new uploadOrderData();
				orderData.setCode(order.getCode());
				orderData.setPONumber(order.getPONumber());
				if (null != order.getSoldToAccount())
				{
					final FMCustomerAccountModel soldToUnit = order.getSoldToAccount();
					final FMCustomerAccountData soldTounitData = new FMCustomerAccountData();
					soldTounitData.setUid(soldToUnit.getUid());
					orderData.setSoldToAccount(soldTounitData);
					orderData.setSapordernumber(order.getSapordernumber());
					orderData.setUserFirstName(order.getUserFirstName());
					orderData.setUpdatedTime(order.getUpdatedTime());
					orderData.setStatus(order.getUploadOrderStatus().getCode().toString());
					orderData.setUploadfilemedia(order.getUploadOrderFile());
					orderDatas.add(orderData);
				}
			}
		}
		return orderDatas;
	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.facades.uploadorder.UploadOrderFacade#viewCSRUploadorder()
	 */
	@Override
	public List<uploadOrderData> viewCSRUploadorder()
	{
		// YTODO Auto-generated method stub
		LOG.info("viewCSRUploadorder{} Inside Account Code");
		final List<uploadOrderData> orderDatas = new ArrayList<uploadOrderData>();
		final List<UploadOrderModel> orderModel = fmUploadOrderDAO.viewCSRUploadorder();
		if (orderModel != null && !orderModel.isEmpty())
		{
			for (final UploadOrderModel order : orderModel)
			{
				final uploadOrderData orderData = new uploadOrderData();
				orderData.setCode(order.getCode());
				orderData.setPONumber(order.getPONumber());
				if (null != order.getSoldToAccount())
				{
					final FMCustomerAccountModel soldToUnit = order.getSoldToAccount();
					final FMCustomerAccountData soldTounitData = new FMCustomerAccountData();
					soldTounitData.setUid(soldToUnit.getUid());
					orderData.setSoldToAccount(soldTounitData);
					orderData.setSapordernumber(order.getSapordernumber());
					orderData.setUserFirstName(order.getUserFirstName());
					orderData.setUpdatedTime(order.getUpdatedTime());
					orderData.setStatus(order.getUploadOrderStatus().getCode().toString());
					orderData.setUploadfilemedia(order.getUploadOrderFile());
					orderDatas.add(orderData);
				}
			}
		}
		return orderDatas;
	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see com.federalmogul.facades.uploadorder.UploadOrderFacade#getGlobalCSRSearchUploadOrderData(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<uploadOrderData> getGlobalCSRSearchUploadOrderData(final String ponumber, final String sapordernumber,
			final String sdate, final String edate, final String status)
	{
		// YTODO Auto-generated method stub
		LOG.info("viewCSRUploadorder{} Inside Account Code");
		final List<uploadOrderData> orderDatas = new ArrayList<uploadOrderData>();
		final List<UploadOrderModel> orderModel = fmUploadOrderDAO.viewGlobalCSRUploadorder(ponumber, sapordernumber, sdate, edate,
				status);
		if (orderModel != null && !orderModel.isEmpty())
		{
			for (final UploadOrderModel order : orderModel)
			{
				final uploadOrderData orderData = new uploadOrderData();
				orderData.setCode(order.getCode());
				orderData.setPONumber(order.getPONumber());
				if (null != order.getSoldToAccount())
				{
					final FMCustomerAccountModel soldToUnit = order.getSoldToAccount();
					final FMCustomerAccountData soldTounitData = new FMCustomerAccountData();
					soldTounitData.setUid(soldToUnit.getUid());
					orderData.setSoldToAccount(soldTounitData);
					orderData.setSapordernumber(order.getSapordernumber());
					orderData.setUserFirstName(order.getUserFirstName());
					orderData.setUpdatedTime(order.getUpdatedTime());
					orderData.setStatus(order.getUploadOrderStatus().getCode().toString());
					orderData.setUploadfilemedia(order.getUploadOrderFile());
					orderDatas.add(orderData);
				}
			}
		}
		return orderDatas;
	}


	@Override
	public void createPriceRowForUser(final Double price, final FMPartModel part, final String currency, final WeightBO weight)
	{

		final UserModel user = userService.getCurrentUser();
		final Collection<PriceRowModel> partPrices = new LinkedList<PriceRowModel>();
		final Collection<PriceRowModel> pricerowModels = part.getEurope1Prices();
		for (final PriceRowModel pricerowModel : pricerowModels)
		{
			if (pricerowModel.getUser() != null && pricerowModel.getUser().getUid().equalsIgnoreCase(user.getUid()))
			{
				modelService.remove(pricerowModel.getPk());
			}
			partPrices.add(pricerowModel);
		}
		modelService.refresh(part);
		final PriceRowModel priceRowModel = modelService.create(PriceRowModel.class);
		priceRowModel.setProduct(part);
		priceRowModel.setCatalogVersion(part.getCatalogVersion());
		//if (price != 0.0)
		if (sessionService.getAttribute("logedUserType").equals("ShipTo"))
		{
			priceRowModel.setPrice(0.0);
		}
		else
		{
			if (Double.doubleToLongBits(price) != 0)
			{
				priceRowModel.setPrice(price);
			}
			else
			{
				priceRowModel.setPrice(0.0);
			}
		} /*
		   * if (currency != null && ("CAD").equalsIgnoreCase(currency)) {
		   * priceRowModel.setCurrency(commonI18NService.getCurrency(currency)); } else {
		   * priceRowModel.setCurrency(commonI18NService.getCurrency("USD")); }
		   */
		priceRowModel.setCurrency(commonI18NService.getCurrency("USD"));
		UnitModel unit = productService.getUnit("lbs");
		if (unit == null)
		{
			unit = createUnit("lbs", "pound", "FM-pound", 1.0);
		}
		priceRowModel.setUnit(unit);
		priceRowModel.setUnitFactor(1);
		priceRowModel.setUser(user);
		partPrices.add(priceRowModel);
		part.setEurope1Prices(partPrices);
		part.setUnit(unit);
		if (null != weight)
		{
			part.setWeightUOM(String.valueOf(weight.getWeight()).toString());
		}
		modelService.save(part);
	}





	private UnitModel createUnit(final String code, final String name, final String type, final Double conversion)
	{
		final UnitModel unit = modelService.create(UnitModel.class);
		unit.setCode(code);
		unit.setName(name);
		unit.setUnitType(type);
		unit.setConversion(conversion);
		modelService.save(unit);
		return unit;
	}

@Override
	public void createReportLog(FMCustomerAccountModel accModel,FMCustomerModel custModel,String eventType){
		fmcustomerservice.createReportLog(accModel,custModel,eventType);	
		
	}
}
