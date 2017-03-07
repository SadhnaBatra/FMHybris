/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2013 hybris AG
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of hybris
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with hybris.
 *
 *
 */
package com.federalmogul.storefront.controllers.misc;

import de.hybris.platform.acceleratorservices.config.SiteConfigService;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.servicelayer.services.CMSComponentService;
import de.hybris.platform.commercefacades.i18n.I18NFacade;
import de.hybris.platform.commercefacades.order.CartFacade;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.order.CartService;
import de.hybris.platform.servicelayer.keygenerator.impl.PersistentKeyGenerator;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

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

import com.federalmogul.core.model.FMCustomerAccountModel;
import de.hybris.platform.core.model.order.CartModel;
import com.federalmogul.core.model.TSCLocationModel;
import com.federalmogul.core.product.PartService;
import com.federalmogul.core.util.FMUtils;
import com.federalmogul.facades.order.FMDistrubtionCenterFacade;
import com.federalmogul.facades.order.data.DistrubtionCenterData;
import com.federalmogul.facades.store.finder.FMStoreFinderFacades;
import com.federalmogul.falcon.core.model.FMCorporateModel;
import com.federalmogul.falcon.core.model.FMDCCenterModel;
import com.federalmogul.falcon.core.model.FMDistrubtionCenterModel;
import com.federalmogul.falcon.core.model.FMPartModel;
import com.federalmogul.storefront.constants.WebConstants;
import com.federalmogul.storefront.controllers.ControllerConstants;
import com.federalmogul.storefront.controllers.pages.AbstractPageController;
import com.federalmogul.storefront.controllers.util.GlobalMessages;
import com.fmo.wom.business.as.InventoryASUSCanImpl;
import com.fmo.wom.business.as.ItemASUSCanImpl;
import com.fmo.wom.business.as.WOMServices;
import com.fmo.wom.common.exception.GATPCreditCheckException;
import com.fmo.wom.common.exception.WOMBusinessDataException;
import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.common.exception.WOMTransactionException;
import com.fmo.wom.common.exception.WOMValidationException;
import com.fmo.wom.comparators.InventoryBOConparator;
import com.fmo.wom.domain.AccountBO;
import com.fmo.wom.domain.BillToAcctBO;
import com.fmo.wom.domain.CustomerSalesOrgBO;
import com.fmo.wom.domain.DistributionCenterBO;
import com.fmo.wom.domain.InventoryBO;
import com.fmo.wom.domain.ItemBO;
import com.fmo.wom.domain.PartBO;
import com.fmo.wom.domain.Quantity;
import com.fmo.wom.domain.QuantityBO;
import com.fmo.wom.domain.SalesOrganizationBO;
import com.fmo.wom.domain.SapAcctBO;
import com.fmo.wom.domain.ShipToAcctBO;
import com.fmo.wom.domain.enums.ExternalSystem;
import com.fmo.wom.domain.enums.OrderSource;
import com.fmo.wom.domain.enums.OrderType;
import com.federalmogul.facades.uploadorder.UploadOrderFacade;
import com.federalmogul.facades.account.FMCustomerFacade;
import com.federalmogul.core.network.dao.FMUserSearchDAO;


/**
 * Controller for MiniGATP functionality which is not specific to a page.
 */
@Controller
@Scope("tenant")
@RequestMapping("/inventory")
public class MiniGATPController extends AbstractPageController
{
	private static final Logger LOG = Logger.getLogger(MiniGATPController.class);

	private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyMMdd");
	/**
	 * We use this suffix pattern because of an issue with Spring 3.1 where a Uri value is incorrectly extracted if it
	 * contains on or more '.' characters. Please see https://jira.springsource.org/browse/SPR-6164 for a discussion on
	 * the issue and future resolution.
	 */
	//private static final String TOTAL_DISPLAY_PATH_VARIABLE_PATTERN = "{totalDisplay:.*}";
	//private static final String COMPONENT_UID_PATH_VARIABLE_PATTERN = "{componentUid:.*}";

	@Deprecated
	@Resource(name = "cartFacade")
	private CartFacade cartFacade;

	@Resource(name = "cmsComponentService")
	private CMSComponentService cmsComponentService;

	@Autowired
	private UploadOrderFacade uploadorderFacade;

	@Resource
	protected FMCustomerFacade fmCustomerFacade;

	@Resource(name = "partService")
	private PartService partService;

	@Autowired
	private CartService cartService;

	@Autowired
	private FMDistrubtionCenterFacade fmDistrubtionCenterFacade;

	@Resource(name = "fmstoreFinderFacades")
	FMStoreFinderFacades fmstoreFinderFacades;

	@Resource(name = "i18NFacade")
	private I18NFacade i18NFacade;

	@Resource(name = "siteConfigService")
	private SiteConfigService siteConfigService;

	@Autowired
	private ModelService modelService;

	@Resource
	private PersistentKeyGenerator orderCodeGenerator;
	@Autowired
	FMUserSearchDAO fmUserSearchDAO;


	@RequestMapping(value = "/home/{partNumber}", method = { RequestMethod.GET, RequestMethod.POST })
	public String getMiniGatp(@PathVariable("partNumber") final String partNumber,
			@RequestParam(value = "TSCPickup", required = false) final String TSCPickup, final Model model)
	{
		final CartData cartData = cartFacade.getSessionCart();
		CartModel cartModel = cartService.getSessionCart();

		LOG.info("Inventory Home TSCPickup :: " + TSCPickup);
		List<String> tscPickupList = new ArrayList<String>();
		boolean isTSCPickup = false;
		final List<ItemBO> sessionItemList = (List<ItemBO>) getSessionService().getAttribute(WebConstants.RESOLUTION_ITEMS);
		LOG.info(sessionItemList);

		if (null == cartData || null == partNumber || null == sessionItemList)
		{
			model.addAttribute("inventory", null);
			return ControllerConstants.Views.Fragments.Gatp.GatpPopup;
		}

		if (null != TSCPickup && TSCPickup.equalsIgnoreCase("true"))
		{
			LOG.info("Inside Home TSCPickup :: ");
			tscPickupList = getTSCPickupList();
			isTSCPickup = true;
			if (null == tscPickupList || tscPickupList.isEmpty())
			{
				model.addAttribute("ErrorMessage", "No pickup location found with in 100 miles");
				model.addAttribute("inventory", null);
				return ControllerConstants.Views.Fragments.Gatp.GatpPopup;
			}
		}

		final List<ItemBO> items = new ArrayList<ItemBO>();
		final String[] partNumberList = partNumber.split("~");
		final HashSet<String> set = new HashSet<>();
		int lineNumber = 1;
		List<ItemBO> copyInventoryList = new ArrayList<ItemBO>();
		int lineCount = 1;
		String newPartNumber = null;

		for (AbstractOrderEntryModel entry : cartModel.getEntries()) {
			if (entry.getProduct() instanceof FMPartModel) {
				final FMPartModel productModel = (FMPartModel) entry.getProduct();
				newPartNumber = productModel.getCode();
				for (final FMCorporateModel corp : productModel.getCorporate()) {
					// Check for NABS part.
					if ("dummy".equalsIgnoreCase(corp.getCorpcode()) || "FELPF".equalsIgnoreCase(corp.getCorpcode())
						|| "SPDPF".equalsIgnoreCase(corp.getCorpcode()) || "SLDPF".equalsIgnoreCase(corp.getCorpcode())) {
						newPartNumber = (productModel.getRawPartNumber() != null ? productModel.getRawPartNumber() : productModel.getCode());
						break;
					}

				}

				final ItemBO anItem = new PartBO();
				anItem.setDisplayPartNumber(newPartNumber.trim().toUpperCase());
				anItem.setPartNumber(newPartNumber.trim().toUpperCase());
				anItem.setLineNumber(lineCount);
				lineCount=lineCount+1;
				if (isTSCPickup) {
					anItem.setPickupStore(" ");
				}
				anItem.setItemQty(getQuantityBO(entry.getQuantity().toString()));
				copyInventoryList.add(anItem);
			}
		}

		List<ItemBO> checkOrderList=womQuickUploadOrder(copyInventoryList);
		LOG.info(checkOrderList);

		for (final String parts : partNumberList) {
			if (null != parts) {
				final String[] itemDetails = parts.trim().split("\\|");
				final String displayPartNumaber = itemDetails[2] != "NA" ? itemDetails[2].trim() + itemDetails[0].trim() : itemDetails[0].trim();

				if (!set.contains(displayPartNumaber)) {
					set.add(displayPartNumaber);
					final String partno = itemDetails[0];
					final String qty = itemDetails[1];
					final String partFlag = itemDetails[2] != "NA" ? itemDetails[2] : null;
					final ItemBO anItem = new PartBO();
					anItem.setDisplayPartNumber(partno.trim().toUpperCase());
					anItem.setPartNumber(partno.trim().toUpperCase());
					anItem.setLineNumber(lineNumber);

					if (isTSCPickup) {
						anItem.setPickupStore(" ");
					}

					anItem.setItemQty(getQuantityBO(qty));
					lineNumber++;

					for (final ItemBO item : sessionItemList) {
						if (item.getPartNumber().equalsIgnoreCase(partno.trim())
								|| item.getDisplayPartNumber().equalsIgnoreCase(partno.trim())) {
							anItem.setExternalSystem(item.getExternalSystem());
							if (null != partFlag && !("NA").equalsIgnoreCase(partFlag) && null != item.getProductFlag()
									&& item.getProductFlag().equalsIgnoreCase(partFlag.trim())) {
								anItem.setProductFlag(item.getProductFlag());
								anItem.setBrandState(item.getBrandState());
							}
						}
					}
					items.add(anItem);
				}
			}
		}

		//fix to show everything in cart for check inventory
		for (ItemBO itemBO : checkOrderList) {
			boolean isExist = false;
			ItemBO refItem = null;
			QuantityBO qty = null;

			for (ItemBO item : items) {
				if (item.getPartNumber().equalsIgnoreCase(itemBO.getPartNumber())) {
					isExist = true;
					refItem = item;
					qty = itemBO.getItemQty();
					break;
				}
			}

			if (!isExist) {
				items.add(itemBO);
			} else {
				refItem.setItemQty(qty);
			}
		}

		try
		{
			final List<ItemBO> inventoryList = getInventoryResult(items, cartData.getCode(), isTSCPickup ? OrderType.PICKUP
					: OrderType.STOCK, isTSCPickup, tscPickupList);
			model.addAttribute("inventory", inventoryList);
		}
		catch (WOMExternalSystemException | WOMTransactionException | WOMBusinessDataException | WOMValidationException |IOException e)
		{
			model.addAttribute("ErrorMessage", "External System Exception :" + e.getMessage());
			if (e instanceof GATPCreditCheckException) {
				LOG.error(e.getMessage());
				model.addAttribute("ErrorMessage",
						getMessageSource().getMessage("gatp_creditcheck", null, getI18nService().getCurrentLocale()));
			}
		}

		getSessionService().setAttribute("itemBO", checkOrderList);
		model.addAttribute("cartData", cartData);
		return ControllerConstants.Views.Fragments.Gatp.GatpPopup;
	}

	@RequestMapping(value = "/quick/{partNumber}/{qty}/{partFlag}", method = { RequestMethod.GET, RequestMethod.POST })
	public String rolloverMiniCartPopup(@PathVariable("partNumber") final String partNumber,
			@PathVariable("qty") final String qty, @PathVariable("partFlag") final String partFlag, final Model model)
			throws CMSItemNotFoundException
	{
		final CartData cartData = cartFacade.getMiniCart();
		final List<ItemBO> sessionItemList = (List<ItemBO>) getSessionService().getAttribute(WebConstants.RESOLUTION_ITEMS);
		if (null == cartData || null == sessionItemList)
		{
			model.addAttribute("inventory", null);
			return ControllerConstants.Views.Fragments.Gatp.GatpPopup;
		}

		final List<ItemBO> items = new ArrayList<ItemBO>();
		final ItemBO anItem = new PartBO();
		anItem.setDisplayPartNumber(partNumber.trim().toUpperCase());
		anItem.setPartNumber(partNumber.trim().toUpperCase());
		anItem.setLineNumber(1);
		anItem.setItemQty(getQuantityBO(qty));


		for (final ItemBO item : sessionItemList)
		{
			if (item.getPartNumber().equalsIgnoreCase(partNumber.trim())
					|| item.getDisplayPartNumber().equalsIgnoreCase(partNumber.trim()))
			{
				anItem.setExternalSystem(item.getExternalSystem());
				if (null != partFlag && !("NA").equalsIgnoreCase(partFlag) && null != item.getProductFlag()
						&& item.getProductFlag().equalsIgnoreCase(partFlag.trim()))
				{
					anItem.setProductFlag(item.getProductFlag());
					anItem.setBrandState(item.getBrandState());
				}
			}
		}

		items.add(anItem);

		try
		{
			final List<ItemBO> ineventoryList = getInventoryResult(items, cartData.getCode(), OrderType.STOCK, false, null);
			model.addAttribute("inventory", ineventoryList);
		}
		catch (WOMExternalSystemException | WOMTransactionException | WOMBusinessDataException | WOMValidationException|IOException e)
		{
			model.addAttribute("ErrorMessage", "External System Exception :" + e.getMessage());
			if (e instanceof GATPCreditCheckException)
			{
				LOG.error(e.getMessage());
				model.addAttribute("ErrorMessage",
						getMessageSource().getMessage("gatp_creditcheck", null, getI18nService().getCurrentLocale()));
			}
		}

		return ControllerConstants.Views.Fragments.Gatp.GatpPopup;
	}


	/* Mahaveer - TSC Chages */
	@RequestMapping(value = "/pickup/{storeId}/{isPopup}/{shipOption}", method = { RequestMethod.GET, RequestMethod.POST })
	public String pickupInventory(@PathVariable("storeId") final String storeId, @PathVariable("isPopup") final String isPopup,
			@PathVariable("shipOption") final String shipOption, final Model model) throws CMSItemNotFoundException
	{
		final boolean isShowPopup = (null != isPopup && isPopup.equalsIgnoreCase("true")) ? true : false;
		LOG.info("Pick up Store ::" + storeId);
		final List<ItemBO> itemBOSession = getSessionService().getAttribute("itemBO");
		final CartData cartData = cartFacade.getSessionCart();
		String returnPage = ControllerConstants.Views.Fragments.Gatp.GatpPopup;
		if (null == cartData || null == itemBOSession || null == storeId)
		{
			model.addAttribute("inventory", null);
			return returnPage;
		}

		if (!isShowPopup)
		{
			final TSCLocationModel store = fmstoreFinderFacades.getStore(storeId);
			final AddressData storeAddress = new AddressData();
			storeAddress.setFirstName(store.getTSCLocId());
			storeAddress.setLine1(store.getAddressLine1());
			if (store.getAddressLine2() != null)
			{
				storeAddress.setLine2(store.getAddressLine2());
			}
			storeAddress.setTown(store.getTown());
			storeAddress.setRegion(i18NFacade.getRegion(store.getCountry(), (store.getCountry() + "-" + store.getState())));
			storeAddress.setCountry(i18NFacade.getCountryForIsocode(store.getCountry()));
			storeAddress.setPostalCode(store.getZipcode());
			getSessionService().setAttribute("pickUpStore", "true");
			getSessionService().setAttribute("tscaddress", storeAddress);
			getSessionService().setAttribute("storeDetail", store);
			getSessionService().setAttribute("shipingOption", null != shipOption ? shipOption : "pickup");
			model.addAttribute("storePickupAddress", storeAddress);
			returnPage = REDIRECT_PREFIX + "/cart/checkout";
		}

		try
		{
			if (cartData.getEntries() != null && !cartData.getEntries().isEmpty())
			{
				final List<ItemBO> items = new ArrayList<ItemBO>();
				{
					for (final OrderEntryData entry : cartData.getEntries())
					{
						for (final ItemBO solvItem : itemBOSession)
						{
							String displayPartNumber = solvItem.getDisplayPartNumber();
							if ((solvItem.getExternalSystem() != null && solvItem.getExternalSystem().equals(ExternalSystem.NABS))
									|| (null != solvItem.getProductFlag()))
							{
								displayPartNumber = solvItem.getProductFlag() + solvItem.getDisplayPartNumber();
							}
							if (entry.getProduct().getCode().equalsIgnoreCase(solvItem.getPartNumber())
									|| displayPartNumber.equalsIgnoreCase(entry.getProduct().getCode()))
							{
								final ItemBO anItem = new PartBO();
								anItem.setItemPrice(solvItem.getItemPrice());
								anItem.setItemWeight(solvItem.getItemWeight());
								anItem.setDescription(solvItem.getDescription());
								if (solvItem.getExternalSystem().equals(ExternalSystem.NABS))
								{
									anItem.setDisplayPartNumber(solvItem.getDisplayPartNumber());
									anItem.setPartNumber(solvItem.getPartNumber());
									anItem.setLineNumber(entry.getEntryNumber() + 1);
									anItem.setExternalSystem(solvItem.getExternalSystem());
									anItem.setProductFlag(solvItem.getProductFlag());
									anItem.setBrandState(solvItem.getBrandState());
									anItem.setItemQty(getDefaultQuanity(entry.getQuantity()));
									anItem.setPickupStore(storeId);
								}
								else
								{
									anItem.setDisplayPartNumber(solvItem.getDisplayPartNumber());
									anItem.setPartNumber(solvItem.getPartNumber());
									anItem.setAaiaBrand(solvItem.getAaiaBrand());
									anItem.setLineNumber(entry.getEntryNumber() + 1);
									anItem.setScacCode(solvItem.getScacCode());
									anItem.setItemQty(getDefaultQuanity(entry.getQuantity()));
									anItem.setExternalSystem(solvItem.getExternalSystem());
									anItem.setPickupStore(storeId);
								}
								items.add(anItem);
							}
						}
					}
				}
				final List<ItemBO> ineventoryList = getInventoryResult(items, cartData.getCode(), OrderType.PICKUP, false, null);
				model.addAttribute("inventory", ineventoryList);
				getSessionService().setAttribute("tscInventoryListItemBO", ineventoryList);
			}
		}
		catch (WOMExternalSystemException | WOMTransactionException | WOMBusinessDataException | WOMValidationException e)
		{
			if (e instanceof GATPCreditCheckException)
			{
				LOG.error(e.getMessage());
				//cart.gatp.creditcheck
				GlobalMessages.addErrorMessage(model,
						getMessageSource().getMessage("gatp_creditcheck", null, getI18nService().getCurrentLocale()));
				return FORWARD_PREFIX + "/cart";
			}
			GlobalMessages.addErrorMessage(model, e.getMessage());
			return FORWARD_PREFIX + "/cart";
		}
		catch (final Exception e)
		{
			// YTODO: handle exception
			LOG.error(e.getMessage());
			GlobalMessages.addErrorMessage(model, e.getMessage());
			return FORWARD_PREFIX + "/cart";
		}

		return returnPage;
	}

	private QuantityBO getQuantityBO(final String qty)
	{
		final QuantityBO quantity = new QuantityBO();
		quantity.setRequestedQuantity(Integer.parseInt(qty));
		return quantity;
	}

	private QuantityBO getDefaultQuanity(final long quantity)
	{
		final int iquantity = Long.valueOf(quantity).intValue();
		final QuantityBO qty = new QuantityBO();
		qty.setRequestedQuantity(iquantity);
		return qty;
	}

	private List<ItemBO> getInventoryResult(final List<ItemBO> items, final String code, final OrderType orderType,
			final boolean isTSCPickup, final List<String> tscPickupList) throws WOMExternalSystemException, WOMTransactionException,
			WOMBusinessDataException, WOMValidationException,IOException
	{
		List<ItemBO> ineventoryList = null;
		final String soldToAcc = (String) getSessionService().getAttribute(WebConstants.SOLDTO_ACCOUNT_ID);
		final FMCustomerAccountModel fmCustomerAccModel = (FMCustomerAccountModel) companyB2BCommerceService
				.getUnitForUid(soldToAcc);

		final CustomerSalesOrgBO custSalesOrg = new CustomerSalesOrgBO();
		custSalesOrg.setDistributionChannel(fmCustomerAccModel.getDistributionChannel());
		custSalesOrg.setDivision(fmCustomerAccModel.getDivision());

		final SalesOrganizationBO salesOrg = new SalesOrganizationBO();
		salesOrg.setCode(fmCustomerAccModel.getSalesorg());
		custSalesOrg.setSalesOrganization(salesOrg);


		final String shipToAcc = (String) getSessionService().getAttribute(WebConstants.SHIPTO_ACCOUNT_ID);
		final FMCustomerAccountModel sfmCustomerAccModel = (FMCustomerAccountModel) companyB2BCommerceService
				.getUnitForUid(shipToAcc);


		final AccountBO billToAcct = new BillToAcctBO();
		billToAcct.setAccountCode(fmCustomerAccModel.getNabsAccountCode());

		//if nabs not

		final SapAcctBO sapAccount = new SapAcctBO();
		sapAccount.setSapAccountCode(fmCustomerAccModel.getUid());
		billToAcct.setSapAccount(sapAccount);

		final AccountBO shipToAcct = new ShipToAcctBO();
		shipToAcct.setAccountCode(sfmCustomerAccModel.getUid());

		final InventoryASUSCanImpl inventoryService = WOMServices.getCheckInventoryService();

		if (!orderType.equals(OrderType.PICKUP))
		{
			ineventoryList = inventoryService.checkInventory(code, items, billToAcct, shipToAcct, custSalesOrg, orderType,
					OrderSource.HYBRIS,true);
		}
		else
		{
			ineventoryList = inventoryService.tscPickUpCheckInventory(items, billToAcct, shipToAcct, custSalesOrg, orderType,
					OrderSource.HYBRIS);
		}

		String zipCode=null;
		final AddressData manualShipToAddress = (AddressData) getSessionService().getAttribute("shiptToAddress");
		if (manualShipToAddress != null)
		{
			zipCode = manualShipToAddress.getPostalCode();
		}
		else if (null != sfmCustomerAccModel.getAddresses())
		{

			for (final AddressModel shipToAddresss : sfmCustomerAccModel.getAddresses())
			{
				zipCode = shipToAddresss.getPostalcode();
			}
		}
		//TempCode
		CartData cartData= cartFacade.getSessionCart();
		//String orderCode = cartData.getCode();
		final String carrierAccCode = fmCustomerAccModel.getCarrierAccountCode();
		int entryNumber=0;
		int qtyInCart=0;
		boolean nabsPart=true;
		String nabsCode=null;
		String rootString="DS-" + cartData.getCode() + "-" + entryNumber;
		if (ineventoryList != null)
		{
			final List<ItemBO> ineventorys = new ArrayList<ItemBO>();
			for (final ItemBO resolvedItem : ineventoryList)
			{

				for(OrderEntryData data:cartData.getEntries()){

					final FMPartModel productModel = partService.getPartForCode(data
							.getProduct().getCode());
					for (final FMCorporateModel corp : productModel.getCorporate()) {
						if ("dummy".equalsIgnoreCase(corp.getCorpcode())
								|| "FELPF".equalsIgnoreCase(corp.getCorpcode())
								|| "SPDPF".equalsIgnoreCase(corp.getCorpcode())
								|| "SLDPF".equalsIgnoreCase(corp.getCorpcode())) {
							nabsPart = false;
						}
					}
					if (!nabsPart && null != productModel.getFlagcode()) {
						nabsCode = productModel.getFlagcode().toUpperCase()
								+ productModel.getRawPartNumber();
					}
					else{
						nabsCode=productModel.getCode();
					}
					String displayPartNumber = resolvedItem.getDisplayPartNumber();
					if ((resolvedItem.getExternalSystem() != null && resolvedItem.getExternalSystem().equals(ExternalSystem.NABS))
							|| (null != resolvedItem.getProductFlag())) {
						displayPartNumber = resolvedItem.getProductFlag() + resolvedItem.getDisplayPartNumber();
					}
					LOG.info("nabs code " + nabsCode+ " displayPartNumber "+displayPartNumber);

					if(resolvedItem.getPartNumber().equalsIgnoreCase(productModel.getCode())||nabsCode.equalsIgnoreCase(displayPartNumber))
					{
						entryNumber=data.getEntryNumber();
						qtyInCart=data.getQuantity().intValue();
					}


				}
				if (resolvedItem.getInventory() != null && resolvedItem.getInventory().size() > 0)
				{
					final List<InventoryBO> inventoryList = new ArrayList<InventoryBO>();
					for (final InventoryBO inv : resolvedItem.getInventory())
					{
						final InventoryBO inventory = inv;
						final DistributionCenterBO distributionCenter = inv.getDistributionCenter();
						if (isTSCPickup && !tscPickupList.isEmpty())
						{
							for (final String locCode : tscPickupList)
							{
								if (locCode.equalsIgnoreCase(distributionCenter.getCode()))
								{
									LOG.info("Inside The TSC Pickup ::" + locCode);
									final FMDCCenterModel dcdetails = fmDistrubtionCenterFacade.getCutOffTimeForDC(distributionCenter
											.getCode());
									distributionCenter.setName(dcdetails.getDcName() + "," + dcdetails.getState());
									inventory.setDistributionCenter(distributionCenter);
									inventoryList.add(inventory);
								}
							}
						}
						else
						{
							final FMDCCenterModel dcdetails = fmDistrubtionCenterFacade.getCutOffTimeForDC(distributionCenter.getCode());
							distributionCenter.setCode(dcdetails.getDcCode());
							distributionCenter.setName(dcdetails.getDcName() + "," + dcdetails.getState());
							distributionCenter.setLatitude(dcdetails.getLatitude());
							distributionCenter.setLongitude(dcdetails.getLongitude());
							distributionCenter.setTscFlag((dcdetails.getTscFlag() != null && dcdetails.getTscFlag() == true ? 1 : 0));
							inventory.setDistributionCenter(distributionCenter);
							inventoryList.add(inventory);
						}
					}
					List<InventoryBO> sortedInventoryList = sortDistributionCenterData(zipCode, inventoryList);
					Collections.sort(sortedInventoryList, new InventoryBOConparator());
					LOG.info("getInventoryResult(...): ==========================================================");
					LOG.info("getInventoryResult(...): Part #: " + resolvedItem.getPartNumber());
					for (int i = 0; i < sortedInventoryList.size(); i++) {
						DistributionCenterBO dbo = sortedInventoryList.get(i).getDistributionCenter();
						String dcCode = dbo.getCode();
						dbo.setCode("DS-" + cartData.getCode() + "-" + entryNumber + "-" + i);

						createDistrubtionCenter(dbo, resolvedItem.getPartNumber(), sortedInventoryList.get(i), resolvedItem.getItemQty(), dcCode, qtyInCart, carrierAccCode);
						LOG.info("getInventoryResult(...): ----------------------------------------------------------");
					}
					LOG.info("getInventoryResult(...): ==========================================================");
					resolvedItem.setInventory(sortedInventoryList);
				}
				ineventorys.add(resolvedItem);
				entryNumber = entryNumber+1;
			}
			uploadorderFacade.createReportLog(fmCustomerAccModel,(fmUserSearchDAO.getUserForUID(fmCustomerFacade.getCurrentFMCustomer().getUid())), "INVENTORY");
			return ineventorys;
		}
		return ineventoryList;
	}

	private String getCutoffTime(final FMDCCenterModel dcBO)
	{
		String cot = "Distant";
		final Date calculationDate = new Date();
		if (dcBO == null)
		{
			cot = "Distant";
		}
		else
		{
			if (fmDistrubtionCenterFacade.isCutOffTimeApproaching(calculationDate, dcBO))
			{
				cot = "Approaching";
			}
			else if (fmDistrubtionCenterFacade.isCutOffTimeExpired(calculationDate, dcBO))
			{
				cot = "Passed";
			}
			else
			{
				cot = "Distant";
			}
		}
		return cot;
	}

	public String[] removeDuplicates(final String[] partNumberList)
	{
		final String[] result = new String[5];
		final HashSet<String> set = new HashSet<>();
		int count = 0;
		for (final String item : partNumberList)
		{
			final String items = item;
			final String[] part = items.split("|");
			if (!set.contains(part[1]))
			{
				result[count++] = item;
				set.add(part[1]);
			}
		}
		return result;
	}

	private List<String> getTSCPickupList()
	{
		String postalCode = null;
		final List<String> tscPickupList = new ArrayList<String>();
		final AddressData manualShipToAddress = (AddressData) getSessionService().getAttribute("shiptToAddress");
		if (manualShipToAddress != null)
		{
			postalCode = manualShipToAddress.getPostalCode();
		}
		else
		{
			final String shipToAcc = (String) getSessionService().getAttribute(WebConstants.SHIPTO_ACCOUNT_ID);
			final FMCustomerAccountModel sfmCustomerAccModel = (FMCustomerAccountModel) companyB2BCommerceService
					.getUnitForUid(shipToAcc);
			for (final AddressModel address : sfmCustomerAccModel.getAddresses())
			{
				postalCode = address.getPostalcode();
			}
		}
		if (null != postalCode)
		{
			try
			{
				final Map<TSCLocationModel, Integer> storeList = fmstoreFinderFacades.getTSCLocationByZipCode(postalCode);

				if (!storeList.isEmpty())
				{
					for (final Map.Entry<TSCLocationModel, Integer> stores : storeList.entrySet())
					{
						final TSCLocationModel store = stores.getKey();
						tscPickupList.add(store.getCode());
					}
				}
				else
				{
					LOG.info("Store not able to find with in 100 miles radiius.");
					return tscPickupList;
				}
			}
			catch (final IOException e)
			{
				// YTODO Auto-generated catch block
				LOG.error("Store not able to find Exception");
				return tscPickupList;
			}
		}
		return tscPickupList;
	}

	private List<InventoryBO> sortDistributionCenterData(String zipCode,List<InventoryBO> dcCenterData) throws IOException{
		String latlng = FMUtils.getLocationByLocationQuery(zipCode);
		final double latitude = Double.parseDouble(FMUtils.extractMiddle(latlng, "\"lat\" :", ","));
		final double longitude = Double.parseDouble(FMUtils.extractMiddle(latlng, "\"lng\" :"));
		List<InventoryBO> dcCenterDataList=new ArrayList<InventoryBO>();
		for (final InventoryBO dcCenter:dcCenterData)
		{
			final double distance = FMUtils.distanceTo(dcCenter.getDistributionCenter().getLatitude(),dcCenter.getDistributionCenter().getLongitude(),latitude,longitude);
			LOG.info("distance :: " + distance + "location :: " + dcCenter.getDistributionCenter().getDistance());
			LOG.info("The closet TSC Location is at: " + dcCenter.getDistributionCenter().getName());
			LOG.info("The distance is " + distance + " Miles");
			dcCenter.getDistributionCenter().setDistance((int)distance);
			dcCenterDataList.add(dcCenter);
		}
		return dcCenterDataList;
	}

	private void createDistrubtionCenter(final DistributionCenterBO dc, String partNumber, InventoryBO inventory, QuantityBO qty, String dcCode, int qtyInCart, String carrierAccCode)
	{
		final FMDistrubtionCenterModel availableDC = fmDistrubtionCenterFacade.getDistrubtionCenterData(dc.getCode());
		if (availableDC != null)
		{
			modelService.remove(availableDC.getPk());
		}
		String country = "US";
		final String shipToAcc = (String) getSessionService().getAttribute(WebConstants.SHIPTO_ACCOUNT_ID);
		final FMCustomerAccountModel sfmCustomerAccModel = (FMCustomerAccountModel) companyB2BCommerceService
				.getUnitForUid(shipToAcc);
		if (null != sfmCustomerAccModel.getAddresses())
		{

			for (final AddressModel shipToAddresss : sfmCustomerAccModel.getAddresses())
			{
				country = shipToAddresss.getCountry().getIsocode();
			}
		}
		final FMDistrubtionCenterModel dcModel = modelService.create(FMDistrubtionCenterModel.class);
		dcModel.setCode(dc.getCode());
		dcModel.setDistrubtionCenterDataCode(dcCode);
		dcModel.setDistrubtionCenterDataName(dc.getName());
		dcModel.setRawpartNumber(partNumber);

		LOG.info("createDistrubtionCenter(...): Requested Qty: " + String.valueOf(qtyInCart));
		LOG.info("createDistrubtionCenter(...): Available Qty: " + inventory.getAvailableQty() + " for " + dc.getCode() + " - " + dc.getName());
		if (inventory.getAvailableQty() >= qtyInCart) {
			LOG.info("createDistrubtionCenter(...): Setting 'dcModel.availableQTY' to Requested Qty");
			dcModel.setAvailableQTY(String.valueOf(qtyInCart));
		} else {
			LOG.info("createDistrubtionCenter(...): Setting 'dcModel.availableQTY' to Available Qty");
			dcModel.setAvailableQTY(String.valueOf(inventory.getAvailableQty()));
		}
		dcModel.setBackorderQTY("0");
		dcModel.setBackorderQTYAll("0");
		dcModel.setBackorderFlag("available");
		dcModel.setCarrierAccountCode(null != carrierAccCode ? carrierAccCode : "NA");
		CartModel cartModel = cartService.getSessionCart();
		if (null != cartModel.getFmordertype() && cartModel.getFmordertype().equalsIgnoreCase(OrderType.STOCK.name()))
		{
			dcModel.setCarrierName(siteConfigService.getString("fm.stock.carrier.code", "FM"));
			dcModel.setCarrierDispName(siteConfigService.getString("fm.stock.carrier.name", "FM Specified Carrier"));
			dcModel.setShippingMethod(siteConfigService.getString("fm.stock.shipmthd.code", "STDS"));
			dcModel.setShippingMethodName(siteConfigService.getString("fm.stock.shipmthd.name", "Standard Shipping"));
		}
		else
		{
			if (null != country && country.equalsIgnoreCase("ca"))
			{
				dcModel.setCarrierName(siteConfigService.getString("fm.emergency.ca.carrier.code", "UPS"));
				dcModel.setCarrierDispName(siteConfigService.getString("fm.emergency.ca.carrier.name", "UPS Collect"));
				dcModel.setShippingMethod(siteConfigService.getString("fm.emergency.ca.shipmthd.code", "GRDC"));
				dcModel.setShippingMethodName(siteConfigService.getString("fm.emergency.ca.shipmthd.name", "Ground Collect"));
			}
			else
			{
				dcModel.setCarrierName(siteConfigService.getString("fm.emergency.carrier.code", "FED"));
				dcModel.setCarrierDispName(siteConfigService.getString("fm.emergency.carrier.name", "FedEx"));
				dcModel.setShippingMethod(siteConfigService.getString("fm.emergency.shipmthd.code", "GRD"));
				dcModel.setShippingMethodName(siteConfigService.getString("fm.emergency.shipmthd.name", "Ground"));
			}
		}
		modelService.save(dcModel);
	}


	protected List<ItemBO> womQuickUploadOrder(final List<ItemBO> anItemBOList)
	{
		final ItemASUSCanImpl partResolutionservice = WOMServices.getPartResolutionService();
		//unitDetails();
		List<ItemBO> itemsOrderable = new ArrayList<ItemBO>();
		try
		{
			itemsOrderable = partResolutionservice.checkOrderable(getPONumber(), anItemBOList, getBillTOAccount(),
					getShiptoAccount());
			LOG.info("womQuickUploadOrder :: itemsOrderable ==>" + itemsOrderable);

		}
		catch (WOMExternalSystemException | WOMValidationException e)
		{
			LOG.error("WOMExternalSystemException::" + e.getMessage());
		}
		catch (final WOMTransactionException e)
		{
			LOG.error("WOMTransactionException::" + e.getMessage());
		}
		catch (final WOMBusinessDataException e)
		{
			LOG.error("WOMBusinessDataException::" + e.getMessage());
		}
		return itemsOrderable;
	}

	private String getPONumber()
	{

		final StringBuffer masterOrderNumber = new StringBuffer("W");
		masterOrderNumber.append(dateFormatter.format(new Date()));
		final CartData cartData = cartFacade.getSessionCart();
		if (null != cartData && null != cartData.getCode())
		{
			masterOrderNumber.append(cartData.getCode().substring(2));
		}
		else
		{
			masterOrderNumber.append(((String) orderCodeGenerator.generate()).substring(2));
		}
		return masterOrderNumber.toString();
	}

	private AccountBO getBillTOAccount()
	{
		final AccountBO billToAcct = new BillToAcctBO();

		LOG.info("Session Code ::" + getSessionService().getAttribute(WebConstants.SOLDTO_ACCOUNT_ID));


		final String soldToAcc = (String) getSessionService().getAttribute(WebConstants.SOLDTO_ACCOUNT_ID);
		final FMCustomerAccountModel fmCustomerAccModel = (FMCustomerAccountModel) companyB2BCommerceService
				.getUnitForUid(soldToAcc);

		//final String shipToAcc = (String) getSessionService().getAttribute(WebConstants.SHIPTO_ACCOUNT_ID);
		//final FMCustomerAccountModel sfmCustomerAccModel = (FMCustomerAccountModel) companyB2BCommerceService
		//.getUnitForUid(shipToAcc);

		//Mahaveer JOBBER Price change
		//final String loggedUserType = (String) getSessionService().getAttribute("logedUserType");
		//if (loggedUserType != null && "ShipTo".equalsIgnoreCase(loggedUserType))
		//{
		//	fmCustomerAccModel = sfmCustomerAccModel;
		//}

		billToAcct.setAccountCode(fmCustomerAccModel.getNabsAccountCode());//soldToUnit.getNabsAccountCode());
		final SapAcctBO sapAccount = new SapAcctBO();
		sapAccount.setSapAccountCode(fmCustomerAccModel.getUid());

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

	private AccountBO getShiptoAccount()
	{
		final String shipToAcc = (String) getSessionService().getAttribute(WebConstants.SHIPTO_ACCOUNT_ID);
		final FMCustomerAccountModel fmCustomerAccModel = (FMCustomerAccountModel) companyB2BCommerceService
				.getUnitForUid(shipToAcc);
		final AccountBO shipToAcct = new ShipToAcctBO();
		shipToAcct.setAccountCode(fmCustomerAccModel.getNabsAccountCode());//shipToUnit.getUid());
		return shipToAcct;
	}

}
