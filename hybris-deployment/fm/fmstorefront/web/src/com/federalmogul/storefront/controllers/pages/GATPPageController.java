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
package com.federalmogul.storefront.controllers.pages;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
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
import org.springframework.web.bind.annotation.SessionAttributes;

import com.federalmogul.core.comparators.DistributionCenterDataComparator;
import com.federalmogul.core.model.FMCustomerAccountModel;
import com.federalmogul.core.product.PartService;
import com.federalmogul.core.util.FMUtils;
import com.federalmogul.facades.account.FMCustomerFacade;
import com.federalmogul.facades.order.FMDistrubtionCenterFacade;
import com.federalmogul.facades.order.data.DistrubtionCenterData;
import com.federalmogul.falcon.core.model.FMCorporateModel;
import com.federalmogul.falcon.core.model.FMDCCenterModel;
import com.federalmogul.falcon.core.model.FMDistrubtionCenterModel;
import com.federalmogul.falcon.core.model.FMPartModel;
import com.federalmogul.storefront.annotations.RequireHardLogIn;
import com.federalmogul.storefront.breadcrumb.ResourceBreadcrumbBuilder;
import com.federalmogul.storefront.constants.WebConstants;
import com.federalmogul.storefront.controllers.util.GlobalMessages;
import com.federalmogul.storefront.forms.UpdateQuantityForm;
import com.fmo.wom.business.as.InventoryASUSCanImpl;
import com.fmo.wom.business.as.WOMServices;
import com.fmo.wom.common.exception.GATPCreditCheckException;
import com.fmo.wom.common.exception.WOMBusinessDataException;
import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.common.exception.WOMTransactionException;
import com.fmo.wom.common.exception.WOMValidationException;
import com.fmo.wom.common.util.MessageResourceConstants;
import com.fmo.wom.domain.AccountBO;
import com.fmo.wom.domain.BillToAcctBO;
import com.fmo.wom.domain.CustomerSalesOrgBO;
import com.fmo.wom.domain.InventoryBO;
import com.fmo.wom.domain.ItemBO;
import com.fmo.wom.domain.PartBO;
import com.fmo.wom.domain.ProblemBO;
import com.fmo.wom.domain.QuantityBO;
import com.fmo.wom.domain.SalesOrganizationBO;
import com.fmo.wom.domain.SapAcctBO;
import com.fmo.wom.domain.ShipToAcctBO;
import com.fmo.wom.domain.enums.ExternalSystem;
import com.fmo.wom.domain.enums.OrderSource;
import com.fmo.wom.domain.enums.OrderType;
import com.fmo.wom.integration.sap.SapConstants;

import de.hybris.platform.acceleratorservices.config.SiteConfigService;
import de.hybris.platform.acceleratorservices.controllers.page.PageType;
import de.hybris.platform.b2b.services.B2BOrderService;
import de.hybris.platform.b2bacceleratorfacades.company.CompanyB2BCommerceFacade;
import de.hybris.platform.b2bacceleratorfacades.order.data.B2BUnitData;
import de.hybris.platform.b2bacceleratorservices.company.CompanyB2BCommerceService;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.order.CartFacade;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.order.data.CartRestorationData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.europe1.model.PriceRowModel;
import de.hybris.platform.order.CartService;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.internal.dao.GenericDao;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;
import com.federalmogul.facades.uploadorder.UploadOrderFacade;
import com.federalmogul.facades.account.FMCustomerFacade;
import com.federalmogul.core.network.dao.FMUserSearchDAO;


/**
 * Controller for cart page
 */
@Controller
@Scope("tenant")
@RequestMapping(value = "/gatp")
@SessionAttributes("itemBO")
public class GATPPageController extends AbstractPageController
{
	private static final Logger LOG = Logger.getLogger(GATPPageController.class);

	private static final String CART_CMS_PAGE = "cartPage";

	private static final String CONTINUE_URL = "continueUrl";
	public static final String SUCCESSFUL_MODIFICATION_CODE = "success";
	private static final String REDIRECT_URL_CART = REDIRECT_PREFIX + "/cart";

	@Deprecated
	@Resource(name = "cartFacade")
	private CartFacade cartFacade;

	/*
	 * @Resource(name = "cartFacade") private de.hybris.platform.b2bacceleratorfacades.api.cart.CartFacade b2bCartFacade;
	 */

	@Autowired
	private ModelService modelService;

	/*
	 * @Autowired private EnumerationService enumerationService;
	 */

	@Autowired
	private CommonI18NService commonI18NService;

	@Resource(name = "sessionService")
	private SessionService sessionService;

	@Resource(name = "simpleBreadcrumbBuilder")
	private ResourceBreadcrumbBuilder resourceBreadcrumbBuilder;

	@Resource(name = "partService")
	private PartService partService;

	@Autowired
	private UploadOrderFacade uploadorderFacade;

	@Autowired
	FMUserSearchDAO fmUserSearchDAO;

	@Resource(name = "siteConfigService")
	private SiteConfigService siteConfigService;

	@Resource
	private UserService userService;

	@Autowired
	private CartService cartService;

	@Resource
	private B2BOrderService b2bOrderService;

	@Resource
	protected FMCustomerFacade fmCustomerFacade;

	@Resource(name = "b2bCommerceFacade")
	protected CompanyB2BCommerceFacade companyB2BCommerceFacade;

	@Resource
	protected CompanyB2BCommerceService companyB2BCommerceService;

	@Autowired
	private FMDistrubtionCenterFacade fmDistrubtionCenterFacade;

	private GenericDao<AbstractOrderModel> abstractOrderGenericDao;

	protected GenericDao<AbstractOrderModel> getAbstractOrderGenericDao()
	{
		return abstractOrderGenericDao;
	}

	@RequestMapping(method = RequestMethod.GET)
	@RequireHardLogIn
	public String showCart(final Model model)
	{
		LOG.info("showCart(...): Inside the GATP First call");
		try
		{
			prepareDataForPage(model);
			//if (gatpAvailFlag)
			//{
			LOG.info("showCart(...): Inside the GATP End call");
			//}
			//return REDIRECT_URL_CART;
		}
		catch (final WOMExternalSystemException e)
		{
			e.printStackTrace();
			LOG.error("showCart(...): Exception ...." + e.getMessage());
			if (e instanceof GATPCreditCheckException)
			{
				LOG.error("showCart(...): Exception ...." + e.getMessage());
				//cart.gatp.creditcheck
				GlobalMessages.addErrorMessage(model,
						getMessageSource().getMessage("gatp_creditcheck", null, getI18nService().getCurrentLocale()));
				return FORWARD_PREFIX + "/cart";
			}
			GlobalMessages.addErrorMessage(model, e.getMessage());
			return FORWARD_PREFIX + "/cart";
		}
		catch(IOException ioe)
		{
			
		}
		catch (final Exception e)
		{
			// YTODO: handle exception
			LOG.error("showCart(...): Exception ...." + e.getMessage());
			e.printStackTrace();
			GlobalMessages.addErrorMessage(model, e.getMessage());
			return FORWARD_PREFIX + "/cart";
		}

		/* mahaveer - Link to set the Zipcode */
		AddressData shipToAddress = new AddressData();
		final B2BUnitData shipToUnit = companyB2BCommerceFacade.getUnitForUid((String) getSessionService().getAttribute(
				WebConstants.SHIPTO_ACCOUNT_ID));
		for (final AddressData shipToAddresss : shipToUnit.getAddresses())
		{
			shipToAddress = shipToAddresss;
		}

		final AddressData manualShipToAddress = (AddressData) getSessionService().getAttribute("shiptToAddress");
		if (manualShipToAddress != null)
		{
			shipToAddress = manualShipToAddress;
		}
		model.addAttribute("shipToAddress", shipToAddress);
		/* Mahaveer - End Here */

		return "pages/cart/fmGATPPage";
	}

	protected void createProductList(final Model model) throws WOMExternalSystemException, WOMTransactionException,
			WOMBusinessDataException, WOMValidationException, CMSItemNotFoundException,IOException
	{
		CartData cartData = cartFacade.getSessionCart();

		//reverseCartProductsOrder(cartData.getEntries());
		if (cartData.getEntries() != null && !cartData.getEntries().isEmpty())
		{
			for (final OrderEntryData entry : cartData.getEntries())
			{
				final UpdateQuantityForm uqf = new UpdateQuantityForm();
				uqf.setQuantity(entry.getQuantity());
				model.addAttribute("updateQuantityForm" + entry.getEntryNumber(), uqf);
			}
		}

		LOG.info("createProductList(...): Before calling checkInventory(...)");

		cartData = checkInventory(model, cartData);
		LOG.info("createProductList(...): After calling checkInventory(...)");
		
		boolean gatpAvailFlag = false;
		if (cartData.getEntries() != null && !cartData.getEntries().isEmpty())
		{
			for (final OrderEntryData entry : cartData.getEntries())
			{
				if (entry.getDistrubtionCenter() != null && entry.getDistrubtionCenter().size() > 0)
				{
					gatpAvailFlag = true;
				}
			}
		}

		model.addAttribute("gatpAvailFlag", gatpAvailFlag);

		storeCmsPageInModel(model, getContentPageForLabelOrId(CART_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(CART_CMS_PAGE));
	}

	protected void reverseCartProductsOrder(final List<OrderEntryData> entries)
	{
		if (entries != null)
		{
			Collections.reverse(entries);
		}
	}

	protected void prepareDataForPage(final Model model) throws WOMExternalSystemException, WOMTransactionException,
			WOMBusinessDataException, WOMValidationException,IOException, CMSItemNotFoundException
	{
		final String continueUrl = (String) getSessionService().getAttribute(WebConstants.CONTINUE_URL);
		model.addAttribute(CONTINUE_URL, (continueUrl != null && !continueUrl.isEmpty()) ? continueUrl : ROOT);

		final CartRestorationData restorationData = (CartRestorationData) sessionService
				.getAttribute(WebConstants.CART_RESTORATION);
		model.addAttribute("restorationData", restorationData);

		createProductList(model);
		model.addAttribute(WebConstants.BREADCRUMBS_KEY, resourceBreadcrumbBuilder.getBreadcrumbs("breadcrumb.cart"));
		model.addAttribute("pageType", PageType.CART.name());
	}

	public CartData checkInventory(final Model model, final CartData cartData) throws WOMExternalSystemException,
			WOMTransactionException, WOMBusinessDataException, WOMValidationException,IOException
	{
		final CartModel cartModel = cartService.getSessionCart();
		final List<ItemBO> itemBOSession = getSessionService().getAttribute("itemBO");
		model.addAttribute("itemBO", itemBOSession);

		LOG.info("checkInventory(...): Before processing cartData entries");
		if (cartData.getEntries() != null && !cartData.getEntries().isEmpty())
		{
			final List<ItemBO> items = new ArrayList<ItemBO>();
			List<ItemBO> itemsCopy = new ArrayList<ItemBO>();
			{
				for (final OrderEntryData entry : cartData.getEntries())
				{
					final FMPartModel productModel = partService.getPartForCode(entry.getProduct().getCode());
					String code = productModel.getPartNumber();
					boolean catalogProduct = true;

					for (final FMCorporateModel corp : productModel.getCorporate())
					{
						if ("dummy".equalsIgnoreCase(corp.getCorpcode()) || "FELPF".equalsIgnoreCase(corp.getCorpcode())
								|| "SPDPF".equalsIgnoreCase(corp.getCorpcode()) || "SLDPF".equalsIgnoreCase(corp.getCorpcode()))
						{
							catalogProduct = false;
						}
					}
					if (!catalogProduct && null != productModel.getFlagcode())
					{
						code = productModel.getFlagcode().toUpperCase() + productModel.getPartNumber();
					}

					for (final ItemBO solvItem : itemBOSession)
					{
						String displayPartNumber = solvItem.getDisplayPartNumber();
						if ((solvItem.getExternalSystem() != null && solvItem.getExternalSystem().equals(ExternalSystem.NABS))
								|| (null != solvItem.getProductFlag()))
						{
							displayPartNumber = solvItem.getProductFlag() + solvItem.getDisplayPartNumber();
						}
						if (entry.getProduct().getCode().equalsIgnoreCase(solvItem.getPartNumber())
								|| displayPartNumber.equalsIgnoreCase(entry.getProduct().getCode())
								|| displayPartNumber.equalsIgnoreCase(code))
						{
							final ItemBO anItem = new PartBO();
							if (solvItem.getExternalSystem().equals(ExternalSystem.NABS))
							{
								anItem.setDisplayPartNumber(solvItem.getDisplayPartNumber());
								anItem.setPartNumber(solvItem.getPartNumber());
								anItem.setLineNumber(entry.getEntryNumber() + 1);
								anItem.setExternalSystem(solvItem.getExternalSystem());
								anItem.setProductFlag(solvItem.getProductFlag());
								anItem.setBrandState(solvItem.getBrandState());
								anItem.setItemQty(getDefaultQuanity(entry.getQuantity()));
							}
							else
							{
								anItem.setDisplayPartNumber(solvItem.getDisplayPartNumber());
								anItem.setPartNumber(solvItem.getPartNumber());
								anItem.setAaiaBrand(solvItem.getAaiaBrand());
								anItem.setLineNumber(entry.getEntryNumber() + 1);
								anItem.setScacCode(solvItem.getScacCode());
								//anItem.setComment("Mahaveer Comment");
								anItem.setItemQty(getDefaultQuanity(entry.getQuantity()));
								anItem.setExternalSystem(solvItem.getExternalSystem());
								anItem.setDeliveryMethod(SapConstants.SHIP_TYPE_SHIPPING);
								//anItem.setDeliveryMethod(SapConstants.SHIP_TYPE_STOREPICKUP);
								//anItem.setPickupStore("4 digit TSC location code/Name");
							}
							items.add(anItem);
							// Create a duplicate item for use with the ** second ** Check Inventory call for EMERGENCY Orders ONLY!!
							ItemBO duplicateItem = createDuplicateItem(solvItem, entry);
							itemsCopy.add(duplicateItem);
						}
					}
				}
			}
			LOG.info("checkInventory(...): After processing cartData entries - items :: " + items);
			
			final String soldToAcc = (String) getSessionService().getAttribute(WebConstants.SOLDTO_ACCOUNT_ID);
			final FMCustomerAccountModel fmCustomerAccModel = (FMCustomerAccountModel) companyB2BCommerceService
					.getUnitForUid(soldToAcc);
			final String carrierAccCode = fmCustomerAccModel.getCarrierAccountCode();
			final CustomerSalesOrgBO custSalesOrg = new CustomerSalesOrgBO();
			custSalesOrg.setDistributionChannel(fmCustomerAccModel.getDistributionChannel());//siteConfigService.getString("wom.custSalesOrg.distributionchannel", "02"));
			custSalesOrg.setDivision(fmCustomerAccModel.getDivision());//siteConfigService.getString("wom.custSalesOrg.Division", "00"));

			final SalesOrganizationBO salesOrg = new SalesOrganizationBO();
			salesOrg.setCode(fmCustomerAccModel.getSalesorg());
			custSalesOrg.setSalesOrganization(salesOrg);

			final String shipToAcc = (String) getSessionService().getAttribute(WebConstants.SHIPTO_ACCOUNT_ID);
			final FMCustomerAccountModel sfmCustomerAccModel = (FMCustomerAccountModel) companyB2BCommerceService
					.getUnitForUid(shipToAcc);

			final AccountBO billToAcct = new BillToAcctBO();
			billToAcct.setAccountCode(fmCustomerAccModel.getNabsAccountCode());//"28073"); //Nabs Account code

			//if nabs not

			final SapAcctBO sapAccount = new SapAcctBO();
			sapAccount.setSapAccountCode(fmCustomerAccModel.getUid());
			billToAcct.setSapAccount(sapAccount);

			final AccountBO shipToAcct = new ShipToAcctBO();
			shipToAcct.setAccountCode(sfmCustomerAccModel.getUid());

			//final ShipToAcctBO shipToAcct = new ShipToAcctBO();
			//shipToAcct.setAccountCode("20023890");

			final InventoryASUSCanImpl inventoryService = WOMServices.getCheckInventoryService();
			List<ItemBO> itemList = null;
			List<ItemBO> nonGatpItemList = null;
			
			if (cartModel.getFmordertype() == null)
			{
				cartModel.setFmordertype("STOCK");
			}

			if (cartModel.getFmordertype().equalsIgnoreCase(OrderType.EMERGENCY.name()))
			{
				LOG.info("checkInventory(...): ");
				LOG.info("checkInventory(...): OrderType EMERGENCY - ** Start **");
				LOG.info("checkInventory(...): OrderType EMERGENCY - inventoryService.checkInventory - Start call (for EMERGENCY Order Type)");

				// Get the Item list with GATP Proposed Inventory Locations.
				itemList = inventoryService.checkInventory(cartModel.getCode(), items, billToAcct, shipToAcct, custSalesOrg,
																	OrderType.EMERGENCY, OrderSource.HYBRIS);
				LOG.info("checkInventory(...): GATP itemList.get(0).getInventory().size(): " + itemList.get(0).getInventory().size());
				LOG.info("checkInventory(...): OrderType EMERGENCY - inventoryService.checkInventory - End call (for EMERGENCY Order Type)");
				LOG.info("checkInventory(...): ");
				LOG.info("checkInventory(...): -------------------------------------------------------------------------------------------");
				LOG.info("checkInventory(...): ");
				LOG.info("checkInventory(...): OrderType EMERGENCY - inventoryService.checkInventory - Start call (for STOCK Order Type)");

				// Get the Item list with non-GATP Inventory Locations.
				nonGatpItemList = inventoryService.checkInventory(cartModel.getCode(), itemsCopy, billToAcct, shipToAcct, custSalesOrg,
																	OrderType.STOCK, OrderSource.HYBRIS, true);

				LOG.info("checkInventory(...): nonGatpItemList.get(0).getInventory().size(): " + nonGatpItemList.get(0).getInventory().size());
				LOG.info("checkInventory(...): OrderType EMERGENCY - inventoryService.checkInventory - End call (for STOCK Order Type)");
				LOG.info("checkInventory(...): OrderType EMERGENCY - ** End **");
				LOG.info("checkInventory(...): ");
			}
			else if (cartModel.getFmordertype().equalsIgnoreCase(OrderType.REGULAR.name()))
			{
				LOG.info("checkInventory(...): OrderType REGULAR - ** Start **");
				itemList = inventoryService.checkInventory(cartModel.getCode(), items, billToAcct, shipToAcct, custSalesOrg,
																	OrderType.REGULAR, OrderSource.HYBRIS);
				LOG.info("checkInventory(...): OrderType REGULAR - ** End **");
			}
			else if (cartModel.getFmordertype().equalsIgnoreCase(OrderType.STOCK.name()))
			{
				LOG.info("checkInventory(...): OrderType STOCK - ** Start **");
				itemList = inventoryService.checkInventory(cartModel.getCode(), items, billToAcct, shipToAcct, custSalesOrg,
																	OrderType.STOCK, OrderSource.HYBRIS);
				LOG.info("checkInventory(...): OrderType STOCK - ** End **");
			}
			else if (cartModel.getFmordertype().equalsIgnoreCase(OrderType.PICKUP.name()))
			{
				LOG.info("checkInventory(...): OrderType PICKUP - ** Start **");
				itemList = inventoryService.checkInventory(cartModel.getCode(), items, billToAcct, shipToAcct, custSalesOrg,
																	OrderType.PICKUP, OrderSource.HYBRIS);
				LOG.info("checkInventory(...): OrderType PICKUP - ** End **");
			}
			
			// Create a map to store GATP Proposed DC Codes.
			Map<String, String> gatpDcCodesMap = new HashMap<String, String>();

			if (itemList != null)
			{
				LOG.info("checkInventory(...): Processing 'itemList' - ** Start **");
				LOG.info("checkInventory(...): itemList.size(): " + itemList.size());

				getSessionService().setAttribute("inventoryBO", itemList); // NOTE: Misleading attribute name!!
				clearDCInfo(cartData.getCode());
				final List<OrderEntryData> entryies = new ArrayList<OrderEntryData>();
				for (final OrderEntryData entry : cartData.getEntries())
				{
					String code = entry.getProduct().getCode();
					boolean catalogProduct = true;
					final FMPartModel productModel = partService.getPartForCode(entry.getProduct().getCode());
					String dispPart = productModel.getPartNumber();
					for (final FMCorporateModel corp : productModel.getCorporate())
					{
						if ("dummy".equalsIgnoreCase(corp.getCorpcode()) || "FELPF".equalsIgnoreCase(corp.getCorpcode())
								|| "SPDPF".equalsIgnoreCase(corp.getCorpcode()) || "SLDPF".equalsIgnoreCase(corp.getCorpcode()))
						{
							catalogProduct = false;
						}
					}

					if (!catalogProduct && null != productModel.getFlagcode())
					{
						code = productModel.getFlagcode().toUpperCase() + productModel.getRawPartNumber();
						dispPart = productModel.getFlagcode().toUpperCase() + productModel.getPartNumber();
					}

					for (final ItemBO resolvedItem : itemList)
					{
						String displayPartNumber = resolvedItem.getDisplayPartNumber();
						if ((resolvedItem.getExternalSystem() != null && resolvedItem.getExternalSystem().equals(ExternalSystem.NABS))
								|| (null != resolvedItem.getProductFlag()))
						{
							displayPartNumber = resolvedItem.getProductFlag() + resolvedItem.getDisplayPartNumber();
						}
						final List<ProblemBO> problemBOList = resolvedItem.getProblemItemList();
						if (resolvedItem.getPartNumber().equalsIgnoreCase(entry.getProduct().getCode())
								|| displayPartNumber.equalsIgnoreCase(entry.getProduct().getCode())
								|| (displayPartNumber.equalsIgnoreCase(code)) || (displayPartNumber.equalsIgnoreCase(dispPart)))
						{
							entry.getProduct().setPartNumber(!catalogProduct ? productModel.getRawPartNumber() : productModel.getCode());
							entry.getProduct().setRawPartNumber(resolvedItem.getPartNumber());
							final List<DistrubtionCenterData> distrubtionCenters = new ArrayList<DistrubtionCenterData>();
							boolean insufficientInventoryFound = false;
							if (resolvedItem.hasProblemItem())
							{
								String partProblem = null;
								if (problemBOList != null && !problemBOList.isEmpty())
								{
									for (final ProblemBO problem : problemBOList)
									{
										LOG.info("checkInventory(...): resolvedItem: " + resolvedItem.getPartNumber() + ", message:  " 
													+ problem.getMessageKey());
										LOG.info("checkInventory(...): resolvedItem: " + resolvedItem.getPartNumber() + ", Status: "
													+ problem.getStatusCategory().name());

										String issues = null;
										if (null != problem.getStatusCategory()
												&& !"PART_FOUND".equalsIgnoreCase(problem.getStatusCategory().name()))
										{
											final String[] parms = { resolvedItem.getPartNumber() };
											issues = getMessageSource().getMessage("NO_INVENTORY_MSG", parms,
														getI18nService().getCurrentLocale());
										}

										if (MessageResourceConstants.NO_INVENTORY.equals(problem.getMessageKey())
												|| MessageResourceConstants.DISCONTINUED_AND_NO_INVENTORY.equals(problem.getMessageKey()))
										{
											final String[] parms = { resolvedItem.getPartNumber() };
											issues = getMessageSource().getMessage("NO_INVENTORY_MSG", parms,
														getI18nService().getCurrentLocale());
										}

										if (MessageResourceConstants.INSUFFICIENT_INVENTORY.equals(problem.getMessageKey())
												|| MessageResourceConstants.DISCONTINUED_AND_INSUFFICIENT_INVENTORY.equals(problem
														.getMessageKey()))
										{
											final String[] parms = { resolvedItem.getPartNumber() };
											issues = getMessageSource().getMessage("NO_INVENTORY_MSG", parms,
														getI18nService().getCurrentLocale());
										}

										if (issues != null)
										{
											if (partProblem == null)
											{
												partProblem = issues;
											}
											else
											{
												partProblem = partProblem + "~" + issues;
											}
										}
									}
								}

								if (partProblem != null)
								{
									entry.setErrorMessage(partProblem);
								}
							}

							model.addAttribute("invFlag", insufficientInventoryFound);
							LOG.info("checkInventory(...): Inventory for resolvedItem :: " + resolvedItem.getInventory());
							String rootString=null;
							if (resolvedItem.getInventory() != null && resolvedItem.getInventory().size() > 0)
							{
								LOG.info("checkInventory(...): resolvedItem.getInventory().size(): " + resolvedItem.getInventory().size());
								final List<InventoryBO> inventorys = resolvedItem.getInventory();
								int count = 0;
								for (final InventoryBO inventory : inventorys)
								{
									LOG.info("checkInventory(...): insufficientInventoryFound Flag :: " + insufficientInventoryFound);
									final FMDCCenterModel dcdetails = fmDistrubtionCenterFacade.getCutOffTimeForDC(inventory
																		.getDistributionCenter().getCode());

									final String cutoffTime = getCutoffTime(dcdetails);

									LOG.info("checkInventory(...): DC Code and Name: " + inventory.getDistributionCenter().getCode() + " - " +
												inventory.getDistributionCenter().getName());
									LOG.info("checkInventory(...): Cut-off Time (for DC Code " + 
												inventory.getDistributionCenter().getCode() + "): " + cutoffTime);
									final int availableQty = inventory.getAvailableQty();
									final int requestedQty = Long.valueOf(entry.getQuantity()).intValue();
									int backQTY = 0;
									int backQTYAll = 0;
									int dcQty = 0;
									String backorderFlag = "available";
									if (availableQty >= requestedQty)
									{
										dcQty = requestedQty;
										insufficientInventoryFound = false;
										LOG.info("checkInventory(...): Available QTY is Greater Than the Requested QTY");
									}
									else
									{
										dcQty = availableQty;
										backQTY = requestedQty - availableQty;
										backQTYAll = requestedQty;
										if (availableQty <= 0)
										{
											backorderFlag = "nothing";
											insufficientInventoryFound = true;
										}
										else
										{
											backorderFlag = "partial";
											insufficientInventoryFound = false;
										}
										LOG.info("checkInventory(...): Available QTY is Lesser Than the Requested QTY. So Backorder logic starts");
									}
									LOG.info("checkInventory(...): Quantity Info :: dcQty :: " + dcQty + ", backQTY ::" + backQTY + ", backQTYAll ::" + backQTYAll
											+ ", backorderFlag ::" + backorderFlag);

									final DistrubtionCenterData distrubtionCenter = new DistrubtionCenterData();
									rootString="DS-" + cartData.getCode() + "-" + entry.getEntryNumber();
									distrubtionCenter.setCode("DS-" + cartData.getCode() + "-" + entry.getEntryNumber() + "-" + count);
									distrubtionCenter.setDistrubtionCenterDataCode(inventory.getDistributionCenter().getCode());
									distrubtionCenter.setDistrubtionCenterDataName(dcdetails.getDcName() + ", " + dcdetails.getState());
									distrubtionCenter.setAvailableQTY(String.valueOf(dcQty).toString());
									distrubtionCenter.setRawpartNumber(entry.getProduct().getCode());
									distrubtionCenter.setBackorderQTY(String.valueOf(backQTY).toString());
									distrubtionCenter.setBackorderQTYAll(String.valueOf(backQTYAll).toString());
									distrubtionCenter.setBackorderFlag(backorderFlag);
									distrubtionCenter.setCutOffTime(cutoffTime);
									distrubtionCenter.setAvailableDate(inventory.getDistributionCenter().getAvailabilityDate());
									distrubtionCenter.setLatitude(dcdetails.getLatitude());
									distrubtionCenter.setLongitude(dcdetails.getLongitude());

									if (cartModel.getFmordertype().equalsIgnoreCase(OrderType.EMERGENCY.name())) {
										if (!insufficientInventoryFound) {
											distrubtionCenter.setGatpProposed(true);
											LOG.info("checkInventory(...): Putting into gatpDcCodesMap: Key: " + resolvedItem.getDisplayPartNumber() + "-" + distrubtionCenter.getDistrubtionCenterDataCode() + 
														",\nValue: " + distrubtionCenter.getDistrubtionCenterDataCode());
											// Add the DC Code to the GATP Proposed DC Codes map with the Display Part # - DC Code combination as the key.
											gatpDcCodesMap.put(resolvedItem.getDisplayPartNumber() + "-" + distrubtionCenter.getDistrubtionCenterDataCode(), distrubtionCenter.getDistrubtionCenterDataCode());
											distrubtionCenters.add(distrubtionCenter);
											count++;
										}
									} else { // Order Type = Stock
										distrubtionCenter.setGatpProposed(false);
										LOG.info("checkInventory(...): dcdetails.getTscFlag(): " + dcdetails.getTscFlag());
										// Make sure that TSCs are EXCLUDED for Non-Emergency order types.
										if (!dcdetails.getTscFlag()) {
											distrubtionCenters.add(distrubtionCenter);
											count++;
										}
									}
								}
							}
							else
							{
								insufficientInventoryFound = true;
							}

							if (insufficientInventoryFound && cartModel.getFmordertype().equalsIgnoreCase(OrderType.EMERGENCY.name()))
							{
								final String[] parms = { resolvedItem.getPartNumber() };
								entry.setErrorMessage(getMessageSource().getMessage("NO_INVENTORY_MSG", parms, getI18nService().getCurrentLocale()));
								entry.setDistrubtionCenter(null);
							}
							else
							{
								//sort and set here begin --- 
								String zipCode=null;
								if (null != sfmCustomerAccModel.getAddresses())
								{
									for (final AddressModel shipToAddresss : sfmCustomerAccModel.getAddresses())
									{
										zipCode = shipToAddresss.getPostalcode();
									}
								}
								List<DistrubtionCenterData> dcCenterDataList=sortDistributionCenterData(zipCode,distrubtionCenters);
								Collections.sort(dcCenterDataList,new DistributionCenterDataComparator());
								//sort and set here end ---set the sorted list to entries 
								for(int i=0;i<dcCenterDataList.size();i++){
									dcCenterDataList.get(i).setCode(rootString+"-"+i);
									createDistrubtionCenter(dcCenterDataList.get(i), cartModel, carrierAccCode);
								}
								entry.setDistrubtionCenter(dcCenterDataList);

								//wom design
								if (!resolvedItem.isFoundInMultipleLocations() && distrubtionCenters.size() == 1
										&& !cartModel.getFmordertype().equalsIgnoreCase(OrderType.EMERGENCY.name()))
								{
									final DistrubtionCenterData distrubtionCenter = distrubtionCenters.get(0);
									//if (distrubtionCenter.getBackorderFlag().equalsIgnoreCase("available"))
									//{
									setDistrubtionCenter(distrubtionCenter.getCode(), cartData.getCode(), entry.getEntryNumber(),
											distrubtionCenter.getBackorderFlag());
									//}
								}
								entry.setErrorMessage(null);
							}
							entryies.add(entry);
						}
					}
				}
				cartData.setEntries(entryies);
				uploadorderFacade.createReportLog(sfmCustomerAccModel,(fmUserSearchDAO.getUserForUID(fmCustomerFacade.getCurrentFMCustomer().getUid())), "INVENTORY");

				LOG.info("checkInventory(...): Processing 'itemList' - ** End **");
			}
			else 
			{
				LOG.debug("checkInventory(...): itemList is NULL");
			} // if (itemList != null)

			if (gatpDcCodesMap != null) {
				LOG.info("checkInventory(...): gatpDcCodesMap.size(): " + gatpDcCodesMap.size());
			}
			
			if (nonGatpItemList != null && nonGatpItemList.size() > 0)
			{
				LOG.info("checkInventory(...): Processing 'nonGatpItemList' - ** Start **");
				LOG.info("checkInventory(...): nonGatpItemList.size(): " + nonGatpItemList.size());

				String mapKey = "";
				final List<OrderEntryData> entryies = new ArrayList<OrderEntryData>();
				for (final OrderEntryData entry : cartData.getEntries())
				{
					String code = entry.getProduct().getCode();
					boolean catalogProduct = true;
					final FMPartModel productModel = partService.getPartForCode(entry.getProduct().getCode());
					String dispPart = productModel.getPartNumber();
					for (final FMCorporateModel corp : productModel.getCorporate())
					{
						if ("dummy".equalsIgnoreCase(corp.getCorpcode()) || "FELPF".equalsIgnoreCase(corp.getCorpcode())
								|| "SPDPF".equalsIgnoreCase(corp.getCorpcode()) || "SLDPF".equalsIgnoreCase(corp.getCorpcode()))
						{
							catalogProduct = false;
						}
					}

					if (!catalogProduct && null != productModel.getFlagcode())
					{
						code = productModel.getFlagcode().toUpperCase() + productModel.getRawPartNumber();
						dispPart = productModel.getFlagcode().toUpperCase() + productModel.getPartNumber();
					}

					for (final ItemBO resolvedItem : nonGatpItemList)
					{
						String displayPartNumber = resolvedItem.getDisplayPartNumber();
						if ((resolvedItem.getExternalSystem() != null && resolvedItem.getExternalSystem().equals(ExternalSystem.NABS))
								|| (null != resolvedItem.getProductFlag()))
						{
							displayPartNumber = resolvedItem.getProductFlag() + resolvedItem.getDisplayPartNumber();
						}
						final List<ProblemBO> problemBOList = resolvedItem.getProblemItemList();
						if (problemBOList != null) {
							LOG.debug("checkInventory(...): problemBOList.size(): " + problemBOList.size());
						} else {
							LOG.debug("checkInventory(...): problemBOList is NULL");
						}
						if (resolvedItem.getPartNumber().equalsIgnoreCase(entry.getProduct().getCode())
								|| displayPartNumber.equalsIgnoreCase(entry.getProduct().getCode())
								|| (code.equalsIgnoreCase(displayPartNumber)) || (dispPart.equalsIgnoreCase(displayPartNumber)))
						{
							entry.getProduct().setPartNumber(!catalogProduct ? productModel.getRawPartNumber() : productModel.getCode());
							entry.getProduct().setRawPartNumber(resolvedItem.getPartNumber());
							final List<DistrubtionCenterData> distrubtionCenters = new ArrayList<DistrubtionCenterData>();
							boolean insufficientInventoryFound = false;
							if (resolvedItem.hasProblemItem())
							{
								String partProblem = null;
								if (problemBOList != null && !problemBOList.isEmpty())
								{
									for (final ProblemBO problem : problemBOList)
									{
										LOG.info("checkInventory(...): resolvedItem: " + resolvedItem.getPartNumber() + ", message:  " 
													+ problem.getMessageKey());
										LOG.info("checkInventory(...): resolvedItem: " + resolvedItem.getPartNumber() + ", Status: "
													+ problem.getStatusCategory().name());

										String issues = null;
										if (null != problem.getStatusCategory()
												&& !"PART_FOUND".equalsIgnoreCase(problem.getStatusCategory().name()))
										{
											final String[] parms = { resolvedItem.getPartNumber() };
											issues = getMessageSource().getMessage("NO_INVENTORY_MSG", parms,
														getI18nService().getCurrentLocale());
										}

										if (MessageResourceConstants.NO_INVENTORY.equals(problem.getMessageKey())
												|| MessageResourceConstants.DISCONTINUED_AND_NO_INVENTORY.equals(problem.getMessageKey()))
										{
											final String[] parms = { resolvedItem.getPartNumber() };
											issues = getMessageSource().getMessage("NO_INVENTORY_MSG", parms,
														getI18nService().getCurrentLocale());
										}

										if (MessageResourceConstants.INSUFFICIENT_INVENTORY.equals(problem.getMessageKey())
												|| MessageResourceConstants.DISCONTINUED_AND_INSUFFICIENT_INVENTORY.equals(problem
														.getMessageKey()))
										{
											final String[] parms = { resolvedItem.getPartNumber() };
											issues = getMessageSource().getMessage("NO_INVENTORY_MSG", parms,
														getI18nService().getCurrentLocale());
										}

										if (issues != null)
										{
											if (partProblem == null)
											{
												partProblem = issues;
											}
											else
											{
												partProblem = partProblem + "~" + issues;
											}
										}
									}
								}

								if (partProblem != null)
								{
									entry.setErrorMessage(partProblem);
								}
							}

							model.addAttribute("invFlag", insufficientInventoryFound);
							LOG.info("checkInventory(...): Inventory for resolvedItem :: " + resolvedItem.getInventory());
							String rootString=null;
							if (resolvedItem.getInventory() != null && resolvedItem.getInventory().size() > 0)
							{
								LOG.info("checkInventory(...): resolvedItem.getInventory().size(): " + resolvedItem.getInventory().size());
								final List<InventoryBO> inventorys = resolvedItem.getInventory();
								int count = 0;
								for (final InventoryBO inventory : inventorys)
								{
									LOG.info("checkInventory(...): insufficientInventoryFound Flag :: " + insufficientInventoryFound);
									final FMDCCenterModel dcdetails = fmDistrubtionCenterFacade.getCutOffTimeForDC(inventory
																		.getDistributionCenter().getCode());

									final String cutoffTime = getCutoffTime(dcdetails);

									LOG.info("checkInventory(...): DC Code and Name: " + inventory.getDistributionCenter().getCode() + " - " +
											inventory.getDistributionCenter().getName());
									LOG.info("checkInventory(...): Cut-off Time (for DC Code " + 
												inventory.getDistributionCenter().getCode() + "): " + cutoffTime);
									final int availableQty = inventory.getAvailableQty();
									final int requestedQty = Long.valueOf(entry.getQuantity()).intValue();
									int backQTY = 0;
									int backQTYAll = 0;
									int dcQty = 0;
									String backorderFlag = "available";
									if (availableQty >= requestedQty)
									{
										dcQty = requestedQty;
										insufficientInventoryFound = false;
										LOG.info("checkInventory(...): Available QTY is Greater Than the Requested QTY");
									}
									else
									{
										dcQty = availableQty;
										backQTY = requestedQty - availableQty;
										backQTYAll = requestedQty;
										if (availableQty <= 0)
										{
											backorderFlag = "nothing";
											insufficientInventoryFound = true;
										}
										else
										{
											backorderFlag = "partial";
											insufficientInventoryFound = false;
										}
										LOG.info("checkInventory(...): Available QTY is Lesser Than the Requested QTY. So Backorder logic starts");
									}
									LOG.info("checkInventory(...): Quantity Info :: dcQty :: " + dcQty + ", backQTY ::" + backQTY + ", backQTYAll ::" + backQTYAll
											+ ", backorderFlag ::" + backorderFlag);

									final DistrubtionCenterData distrubtionCenter = new DistrubtionCenterData();
									rootString="DS-" + cartData.getCode() + "-" + entry.getEntryNumber();
									distrubtionCenter.setCode("DS-" + cartData.getCode() + "-" + entry.getEntryNumber() + "-" + count);
									distrubtionCenter.setDistrubtionCenterDataCode(inventory.getDistributionCenter().getCode());
									distrubtionCenter.setDistrubtionCenterDataName(dcdetails.getDcName() + ", " + dcdetails.getState());
									distrubtionCenter.setAvailableQTY(String.valueOf(dcQty).toString());
									distrubtionCenter.setRawpartNumber(entry.getProduct().getCode());
									distrubtionCenter.setBackorderQTY(String.valueOf(backQTY).toString());
									distrubtionCenter.setBackorderQTYAll(String.valueOf(backQTYAll).toString());
									distrubtionCenter.setBackorderFlag(backorderFlag);
									distrubtionCenter.setCutOffTime(cutoffTime);
									distrubtionCenter.setAvailableDate(inventory.getDistributionCenter().getAvailabilityDate());
									distrubtionCenter.setLatitude(dcdetails.getLatitude());
									distrubtionCenter.setLongitude(dcdetails.getLongitude());
									//createDistrubtionCenter(distrubtionCenter, cartModel, carrierAccCode);

									if (gatpDcCodesMap != null && gatpDcCodesMap.size() > 0) {
										LOG.debug("checkInventory(...): gatpDcCodesMap.size(): " + gatpDcCodesMap.size());
										mapKey = resolvedItem.getDisplayPartNumber() + "-" + distrubtionCenter.getDistrubtionCenterDataCode();
										LOG.debug("checkInventory(...): Searching 'gatpDcCodesMap' for Key: " + mapKey);
										LOG.debug("checkInventory(...): gatpDcCodesMap.containsKey(" + mapKey + "): " + 
													gatpDcCodesMap.containsKey(mapKey));
										if (gatpDcCodesMap.containsKey(mapKey)) { // Display Part # - DC Code combination found in the GATP Proposed DC Codes map
											LOG.debug("checkInventory(...): gatpDcCodesMap contains the key");
											String dcCode = gatpDcCodesMap.get(mapKey);
											if (dcCode.equalsIgnoreCase(distrubtionCenter.getDistrubtionCenterDataCode())) {
												// Set the 'GATP Proposed' flag to TRUE.
												distrubtionCenter.setGatpProposed(true);
											} else {
												distrubtionCenter.setGatpProposed(false);
											}
										} else {
											LOG.debug("checkInventory(...): gatpDcCodesMap does NOT contain the key");
											// Set the 'GATP Proposed' flag to FALSE.
											distrubtionCenter.setGatpProposed(false); // Display Part # NOT found in the GATP Proposed DC Codes map
										}
									} else { // This condition should not occur. But just in case...
										LOG.debug("checkInventory(...): gatpDcCodesMap is NULL or empty");
										// Set the 'GATP Proposed' flag to FALSE.
										distrubtionCenter.setGatpProposed(false);
									}
									
									// Reset the map key for the next iteration.
									mapKey = "";

									if (cartModel.getFmordertype().equalsIgnoreCase(OrderType.EMERGENCY.name())) {
										if (!insufficientInventoryFound) {
											distrubtionCenters.add(distrubtionCenter);
											count++;
										}
									} else { // Order Type = Stock
										distrubtionCenters.add(distrubtionCenter);
										count++;
									}
								}
							}
							else
							{
								insufficientInventoryFound = true;
							}

							if (insufficientInventoryFound && cartModel.getFmordertype().equalsIgnoreCase(OrderType.EMERGENCY.name()))
							{
								final String[] parms = { resolvedItem.getPartNumber() };
								entry.setErrorMessage(getMessageSource().getMessage("NO_INVENTORY_MSG", parms, getI18nService().getCurrentLocale()));
								entry.setDistrubtionCenter(null);
							}
							else
							{
								//sort and set here begin --- 
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
								List<DistrubtionCenterData> dcCenterDataList=sortDistributionCenterData(zipCode,distrubtionCenters);
								Collections.sort(dcCenterDataList,new DistributionCenterDataComparator());
								//sort and set here end ---set the sorted list to entries 
								for(int i=0;i<dcCenterDataList.size();i++){
									dcCenterDataList.get(i).setCode(rootString+"-"+i);
									createDistrubtionCenter(dcCenterDataList.get(i), cartModel, carrierAccCode);
								}
								entry.setDistrubtionCenter(dcCenterDataList);

								//wom design
								if (!resolvedItem.isFoundInMultipleLocations() && distrubtionCenters.size() == 1
										&& !cartModel.getFmordertype().equalsIgnoreCase(OrderType.EMERGENCY.name()))
								{
									final DistrubtionCenterData distrubtionCenter = distrubtionCenters.get(0);
									setDistrubtionCenter(distrubtionCenter.getCode(), cartData.getCode(), entry.getEntryNumber(),
											distrubtionCenter.getBackorderFlag());
								}
								entry.setErrorMessage(null);
							}
							entryies.add(entry);
						}
					}
				}
				cartData.setEntries(entryies);
				LOG.info("checkInventory(...): Processing 'nonGatpItemList' - ** End **");
			} // if (nonGatpItemList != null)
			
			model.addAttribute("cartData", cartData);
			return cartData;

		}
		return cartData;
	}

	private ItemBO createDuplicateItem(ItemBO sourceItem, OrderEntryData entry) {
		
		final ItemBO duplicateItem = new PartBO();
		if (sourceItem.getExternalSystem().equals(ExternalSystem.NABS))
		{
			duplicateItem.setDisplayPartNumber(sourceItem.getDisplayPartNumber());
			duplicateItem.setPartNumber(sourceItem.getPartNumber());
			duplicateItem.setLineNumber(entry.getEntryNumber() + 1);
			duplicateItem.setExternalSystem(sourceItem.getExternalSystem());
			duplicateItem.setProductFlag(sourceItem.getProductFlag());
			duplicateItem.setBrandState(sourceItem.getBrandState());
			duplicateItem.setItemQty(getDefaultQuanity(entry.getQuantity()));
		}
		else
		{
			duplicateItem.setDisplayPartNumber(sourceItem.getDisplayPartNumber());
			duplicateItem.setPartNumber(sourceItem.getPartNumber());
			duplicateItem.setAaiaBrand(sourceItem.getAaiaBrand());
			duplicateItem.setLineNumber(entry.getEntryNumber() + 1);
			duplicateItem.setScacCode(sourceItem.getScacCode());
			duplicateItem.setItemQty(getDefaultQuanity(entry.getQuantity()));
			duplicateItem.setExternalSystem(sourceItem.getExternalSystem());
			duplicateItem.setDeliveryMethod(SapConstants.SHIP_TYPE_SHIPPING);
		}
		
		return duplicateItem;
	}

	public void createPriceRowForUser(final Double price, final FMPartModel part)
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
		}
		final PriceRowModel priceRowModel = modelService.create(PriceRowModel.class);
		priceRowModel.setProduct(part);
		priceRowModel.setCatalogVersion(part.getCatalogVersion());
		//if (price != 0.0)
		if (Double.doubleToLongBits(price) != 0)
		{
			priceRowModel.setPrice(price);
		}
		else
		{
			priceRowModel.setPrice(0.0);
		}
		priceRowModel.setCurrency(commonI18NService.getBaseCurrency());
		priceRowModel.setUser(user);
		partPrices.add(priceRowModel);
		part.setEurope1Prices(partPrices);
		modelService.save(part);
	}

	private void createDistrubtionCenter(final DistrubtionCenterData dc, final CartModel cartModel, final String carrierAccCode)
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
		dcModel.setDistrubtionCenterDataCode(dc.getDistrubtionCenterDataCode());
		dcModel.setDistrubtionCenterDataName(dc.getDistrubtionCenterDataName());
		dcModel.setRawpartNumber(dc.getRawpartNumber());
		dcModel.setAvailableDate(dc.getAvailableDate());
		dcModel.setAvailableQTY(dc.getAvailableQTY());
		dcModel.setBackorderQTY(dc.getBackorderQTY());
		dcModel.setBackorderQTYAll(dc.getBackorderQTYAll());
		dcModel.setBackorderFlag(dc.getBackorderFlag());
		dcModel.setCarrierAccountCode(null != carrierAccCode ? carrierAccCode : "NA");
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

	@RequestMapping(method = RequestMethod.GET, value = "/adddc/{dc}/{entrynumber}/{backorderflag}")
	public String setDistrubtionCenter(final Model model, @PathVariable("dc") final String dccode,
			@PathVariable("entrynumber") final String entrynumber, @PathVariable("backorderflag") String backorderflag)
	{
		LOG.info("Inside public setDistrubtionCenter(...) method");
		LOG.info("setDistrubtionCenter(...): dccode: " + dccode + ", entrynumber: " + entrynumber + ", backorderflag: " + backorderflag);
		final CartData cartData = cartFacade.getSessionCart();
		if (backorderflag.isEmpty())
		{
			backorderflag = "available";
		}
		setDistrubtionCenter(dccode, cartData.getCode(), Integer.parseInt(entrynumber), backorderflag);
		return "pages/fm/ajax/categoryDetails";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/removedc/{dc}/{entrynumber}")
	public String removeDistrubtionCenter(final Model model, @PathVariable("dc") final String dccode,
			@PathVariable("entrynumber") final String entrynumber)
	{
		LOG.info("Inside public removeDistrubtionCenter(...) method");
		LOG.info("removeDistrubtionCenter(...): dccode: " + dccode + ", entrynumber: " + entrynumber);
		final CartData cartData = cartFacade.getSessionCart();
		removeDistrubtionCenter(dccode, cartData.getCode(), Integer.parseInt(entrynumber));
		return "pages/fm/ajax/categoryDetails";
	}

	private void clearDCInfo(final String orderCode)
	{
		final AbstractOrderModel abstractOrderModel = b2bOrderService.getAbstractOrderForCode(orderCode);
		for (final AbstractOrderEntryModel abstractOrderEntry : abstractOrderModel.getEntries())
		{
			abstractOrderEntry.setDistrubtionCenter(null);
			modelService.save(abstractOrderEntry);
			modelService.refresh(abstractOrderEntry);
		}
	}

	private void removeDistrubtionCenter(final String dccode, final String orderCode, final Integer entrynumber)
	{
		LOG.info("Inside private removeDistrubtionCenter(...) method");
		//final AbstractOrderModel abstractOrderModel = getAbstractOrderForCode(orderCode);
		final AbstractOrderModel abstractOrderModel = b2bOrderService.getAbstractOrderForCode(orderCode);
		final String orderType = abstractOrderModel.getFmordertype();
		final FMDistrubtionCenterModel dcModel = fmDistrubtionCenterFacade.getDistrubtionCenterData(dccode);
		validateParameterNotNull(abstractOrderModel, String.format("Order %s does not exist", orderCode));
		boolean dcContains = false;
		for (final AbstractOrderEntryModel abstractOrderEntry : abstractOrderModel.getEntries())
		{
			if (entrynumber.intValue() == abstractOrderEntry.getEntryNumber().intValue())
			{
				final List<FMDistrubtionCenterModel> removeDCmodel = new ArrayList(abstractOrderEntry.getDistrubtionCenter());
				final List<FMDistrubtionCenterModel> dcmodels = new ArrayList<FMDistrubtionCenterModel>();
				if (!removeDCmodel.isEmpty() && orderType.equalsIgnoreCase(OrderType.EMERGENCY.name()))
				{
					abstractOrderEntry.setDistrubtionCenter(null);
					modelService.save(abstractOrderEntry);
					modelService.refresh(abstractOrderEntry);
					//dcmodels.clear();
				}
				for (final FMDistrubtionCenterModel dcs : removeDCmodel)
				{
					if (!dcs.getCode().equalsIgnoreCase(dcModel.getCode()))
					{
						dcContains = true;
						dcmodels.add(dcs);
					}
				}
				if (dcContains)
				{
					//dcmodels.add(dcModel);
					abstractOrderEntry.setDistrubtionCenter(dcmodels);
					modelService.save(abstractOrderEntry);
				}
			}
		}
		modelService.save(abstractOrderModel);
	}


	private void setDistrubtionCenter(final String dccode, final String orderCode, final Integer entrynumber,
			final String backorderflag)
	{
		LOG.info("Inside private setDistrubtionCenter(...) method");
		//final AbstractOrderModel abstractOrderModel = getAbstractOrderForCode(orderCode);
		final AbstractOrderModel abstractOrderModel = b2bOrderService.getAbstractOrderForCode(orderCode);
		final String orderType = abstractOrderModel.getFmordertype();
		final FMDistrubtionCenterModel dcModel = fmDistrubtionCenterFacade.getDistrubtionCenterData(dccode);
		dcModel.setBackorderFlag(backorderflag);
		modelService.save(dcModel);
		validateParameterNotNull(abstractOrderModel, String.format("Order %s does not exist", orderCode));
		boolean dcContains = false;
		for (final AbstractOrderEntryModel abstractOrderEntry : abstractOrderModel.getEntries())
		{
			if (entrynumber.intValue() == abstractOrderEntry.getEntryNumber().intValue())
			{
				final List<FMDistrubtionCenterModel> dcmodels = new ArrayList(abstractOrderEntry.getDistrubtionCenter());
                //Removing the orderType check to accomodate new implementation
				if (!dcmodels.isEmpty())
				{
					abstractOrderEntry.setDistrubtionCenter(null);
					modelService.save(abstractOrderEntry);
					modelService.refresh(abstractOrderEntry);
					dcmodels.clear();
				}
				for (final FMDistrubtionCenterModel dcs : dcmodels)
				{
					if (dcs.getCode().equalsIgnoreCase(dcModel.getCode()))
					{
						dcContains = true;
					}
				}
				if (!dcContains)
				{
					dcmodels.add(dcModel);
					abstractOrderEntry.setDistrubtionCenter(dcmodels);
					modelService.save(abstractOrderEntry);
				}
			}
		}
		modelService.save(abstractOrderModel);
	}

	private QuantityBO getDefaultQuanity(final long quantity)
	{
		final int iquantity = Long.valueOf(quantity).intValue();
		final QuantityBO qty = new QuantityBO();
		qty.setRequestedQuantity(iquantity);
		return qty;
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
	
	private List<DistrubtionCenterData> sortDistributionCenterData(String zipCode,List<DistrubtionCenterData> dcCenterData) throws IOException{
		String latlng = FMUtils.getLocationByLocationQuery(zipCode);
		final double latitude = Double.parseDouble(FMUtils.extractMiddle(latlng, "\"lat\" :", ","));
		final double longitude = Double.parseDouble(FMUtils.extractMiddle(latlng, "\"lng\" :"));
		List<DistrubtionCenterData> dcCenterDataList=new ArrayList<DistrubtionCenterData>();
		for (final DistrubtionCenterData dcCenter:dcCenterData)
		{
			final double distance = FMUtils.distanceTo(dcCenter.getLatitude(),dcCenter.getLongitude(),latitude,longitude);
			LOG.info("distance :: " + distance + "location :: " + dcCenter.getDistrubtionCenterDataName());	
			LOG.info("The closest TSC Location is at: " + dcCenter.getDistrubtionCenterDataName());
			LOG.info("The distance is " + distance + " Miles");
			dcCenter.setDistance((int)distance);
			dcCenterDataList.add(dcCenter);		
		}
		return dcCenterDataList;
		
	}
}
