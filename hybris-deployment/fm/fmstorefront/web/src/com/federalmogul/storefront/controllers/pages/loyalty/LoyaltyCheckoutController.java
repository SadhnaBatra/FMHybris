/**
 * 
 */
package com.federalmogul.storefront.controllers.pages.loyalty;

import de.hybris.platform.b2b.services.B2BOrderService;
import de.hybris.platform.b2bacceleratorfacades.company.CompanyB2BCommerceFacade;
import de.hybris.platform.b2bacceleratorfacades.order.data.B2BUnitData;
import de.hybris.platform.b2bacceleratorservices.company.CompanyB2BCommerceService;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.order.CheckoutFacade;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.product.ProductFacade;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.data.CountryData;
import de.hybris.platform.commercefacades.user.data.RegionData;
import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.order.InvalidCartException;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.store.services.BaseStoreService;
import de.hybris.platform.core.Registry;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.xml.soap.SOAPException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.federalmogul.core.event.LoyaltyOrderConfirmationProcessEvent;
import com.federalmogul.core.model.LoyaltyOrderConfirmationProcessModel;
import com.federalmogul.core.network.dao.FMUserSearchDAO;
import com.federalmogul.facades.account.FMCustomerFacade;
import com.federalmogul.facades.order.data.FMTempLoyaltyPdfViewData;
import com.federalmogul.storefront.breadcrumb.impl.LoyaltyHomeBreadcrumbBuilder;
import com.federalmogul.storefront.constants.WebConstants;
import com.federalmogul.storefront.controllers.ControllerConstants;
import com.federalmogul.storefront.controllers.pages.checkout.AbstractCheckoutController;
import com.federalmogul.storefront.forms.FMAddressForm;
import com.fm.falcon.webservices.dto.LoyaltyOrderConfirmRequestDTO;
import com.fm.falcon.webservices.dto.LoyaltyOrderConfirmResponseDTO;
import com.fm.falcon.webservices.dto.LoyaltyOrderRedemptionProductInfobean;
import com.fm.falcon.webservices.soap.helper.LoyaltyOrderConfirmHelper;
import com.federalmogul.facades.account.FMCustomerFacade;

/**
 * @author SR279690
 * 
 */

@Controller
@RequestMapping(value = "/loyalty/checkout")
public class LoyaltyCheckoutController extends AbstractCheckoutController
{
	@Autowired
	private ModelService modelService;

	@Autowired
	FMUserSearchDAO fmUserSearchDAO;

	@Resource
	protected UserService userService;

	@Resource
	private BaseStoreService baseStoreService;

	@Resource(name = "checkoutFacade")
	private CheckoutFacade checkoutFacade;

	@Resource
	private BaseSiteService baseSiteService;
	private CommonI18NService commonI18NService;


	protected BaseStoreService getBaseStoreService()
	{
		return this.baseStoreService;
	}

	@Resource
	protected FMCustomerFacade fmCustomerFacade;

	@Autowired
	protected CompanyB2BCommerceService companyB2BCommerceService;

	public void setBaseStoreService(final BaseStoreService service)
	{
		this.baseStoreService = service;
	}

	protected BaseSiteService getBaseSiteService()
	{
		return this.baseSiteService;
	}

	public void setBaseSiteService(final BaseSiteService siteService)
	{
		this.baseSiteService = siteService;
	}

	protected CommonI18NService getCommonI18NService()
	{
		return this.commonI18NService;
	}

	public void setCommonI18NService(final CommonI18NService commonI18NService)
	{
		this.commonI18NService = commonI18NService;
	}

    @Resource(name = "loyaltyBreadcrumbBuilder")
	protected LoyaltyHomeBreadcrumbBuilder loyaltyBreadcrumbBuilder;

	@Resource(name = "accProductFacade")
	private ProductFacade productFacade;

	@Resource(name = "b2bCommerceFacade")
	protected CompanyB2BCommerceFacade companyB2BCommerceFacade;
	@Resource(name = "productService")
	private ProductService productService;

	@Resource
	private B2BOrderService b2bOrderService;

	@Autowired
	private EventService eventService;


	private static final String FM_LOYALTY_CHECKOUT_SHIPPING_PAGE = "loyaltycheckoutShippingPage";
	private static final String FM_LOYALTY_CHECKOUT_REVIEW_PAGE = "loyaltycheckoutreviewplace";
	private static final String FM_LOYALTY_CHECKOUT_CONFIRM_PAGE = "loyaltycheckoutorderconfirm";
	private static final Logger LOG = Logger.getLogger(LoyaltyCheckoutController.class);
	FMTempLoyaltyPdfViewData fmTempLoyaltyPdfViewData = new FMTempLoyaltyPdfViewData();

	@RequestMapping(method = RequestMethod.GET)
	public String getLoyaltyShippingAddress(final Model model) throws CMSItemNotFoundException
	{
		LOG.info("shipping page");


		//final boolean isUserAnonymous = user == null || userService.isAnonymousUser(user);
		final boolean isUserAnonymous = false;

		AddressData shipToAddress = new AddressData();
		AddressData soldToAddress = new AddressData();
		B2BUnitData soldToUnit = null;
		if (!isUserAnonymous)
		{
			LOG.info("inside loyalty usernot anonymous check -1");
			//shipToUnit = companyB2BCommerceFacade.getUnitForUid("0010013195");

			final String soldToUid = getSessionService().getAttribute(WebConstants.LOYALTY_SOLDTO_ACCOUNT_ID);
			//final String soldToUid = (String) getSessionService().getAttribute(WebConstants.SOLDTO_ACCOUNT_ID);


			shipToAddress = fmCustomerFacade.getCurrentFMCustomer().getDefaultShippingAddress();
			fmTempLoyaltyPdfViewData.setFmCustomerData(fmCustomerFacade.getCurrentFMCustomer());

			//soldToUnit = companyB2BCommerceFacade.getUnitForUid("0010013195");

			LOG.info("SLOD Account ID :1: " + soldToUid);

			if (soldToUid != null && soldToUid != "")
			{
				soldToUnit = companyB2BCommerceFacade.getUnitForUid(soldToUid);

				if (soldToUnit != null && !"16427113580".equalsIgnoreCase(soldToUnit.getUid()))
				{
					if (soldToUnit.getAddresses() != null)
					{
						for (final AddressData soldToAddresss : soldToUnit.getAddresses())
						{
							soldToAddress = soldToAddresss;

						}
					}
				}

			}

			LOG.info("SLOD Account ID :2: " + soldToUid);

		}
		if (soldToAddress != null && soldToUnit != null && !"16427113580".equalsIgnoreCase(soldToUnit.getUid()))
		{
			LOG.info("inside loyalty soldToAddress!=null");
			model.addAttribute("soldToAddress", soldToAddress);
			model.addAttribute("soldToUnit", soldToUnit);
			model.addAttribute("billToAddress", soldToAddress);
		}
		/*
		 * else { LOG.info("inside loyalty soldToAddress!=null else part"); model.addAttribute("soldToAddress",
		 * shipToAddress); model.addAttribute("soldToUnit", shipToUnit); model.addAttribute("billToAddress",
		 * shipToAddress); } //manual final AddressData manualShipToAddress = (AddressData)
		 * getSessionService().getAttribute("shiptToAddress");
		 */
		/*
		 * if (manualShipToAddress != null) { LOG.info("inside loyalty manualshiptoaddress!=null"); shipToAddress =
		 * manualShipToAddress; model.addAttribute("manualShipTo", true);
		 * //shipToUnit.setName(manualShipToAddress.getFirstName() + " " + manualShipToAddress.getLastName();
		 * shipToUnit.setName(manualShipToAddress.getFirstName()); }
		 */
		model.addAttribute("shipToAddress", shipToAddress);

		getSessionService().setAttribute("shipToAddress", shipToAddress);


		int totalReedemPoints = 0;


		if (hasItemsInCart())
		{
			final CartData cartData = getCheckoutFlowFacade().getCheckoutCart();
			if (cartData.getEntries() != null && !cartData.getEntries().isEmpty())
			{
				for (final OrderEntryData entry : cartData.getEntries())
				{

					if (entry.getEntries() != null && !entry.getEntries().isEmpty())
					{
						for (final OrderEntryData variantEntry : entry.getEntries())
						{
							final String productCode = variantEntry.getProduct().getCode();
							final ProductData product = productFacade.getProductForCodeAndOptions(productCode,
									Arrays.asList(ProductOption.BASIC, ProductOption.PRICE));
							product.setLoyaltyPoints(getLoyaltyPointsForProduct(productCode));
							totalReedemPoints = totalReedemPoints
									+ (int) ((Double.parseDouble(product.getLoyaltyPoints())) * variantEntry.getQuantity());
							LOG.info("Varient totalReedemPoints " + totalReedemPoints);

							entry.setProduct(product);
						}
					}
					else
					{

						final String productCode = entry.getProduct().getCode();
						final ProductData product = productFacade.getProductForCodeAndOptions(productCode,
								Arrays.asList(ProductOption.BASIC, ProductOption.PRICE));
						product.setLoyaltyPoints(getLoyaltyPointsForProduct(productCode));
						totalReedemPoints = totalReedemPoints
								+ (int) ((Double.parseDouble(product.getLoyaltyPoints())) * entry.getQuantity());
						LOG.info("totalReedemPoints {{{{{{{{{{{{{{{{{{{{" + totalReedemPoints);

						entry.setProduct(product);
					}
				}

			}
			model.addAttribute("cartData", cartData);
			getSessionService().setAttribute("loyaltyCartData", cartData);
			getSessionService().setAttribute("totalredeemedpoints", totalReedemPoints);
		}
		final List<CountryData> countries = new ArrayList<CountryData>();
		countries.add(checkoutFacade.getCountryForIsocode("US"));
		countries.add(checkoutFacade.getCountryForIsocode("CA"));
		model.addAttribute("countryData", countries);
		model.addAttribute("regionsdatas", getI18NFacade().getRegionsForAllCountries().values());
		model.addAttribute("newAddress", new FMAddressForm());
		model.addAttribute("totalReedemPoints", totalReedemPoints);
        model.addAttribute("breadcrumbs", loyaltyBreadcrumbBuilder.getCheckoutBreadcrumbs("Checkout"));
		storeCmsPageInModel(model, getContentPageForLabelOrId(FM_LOYALTY_CHECKOUT_SHIPPING_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FM_LOYALTY_CHECKOUT_SHIPPING_PAGE));
		return ControllerConstants.Views.Pages.Account.loyaltycheckoutShippingPage;
	}

	@RequestMapping(method = {RequestMethod.GET,RequestMethod.POST}, value = "/reviewplaceorder")
	public String getLoyaltyReviewPlaceOrder(@ModelAttribute("newAddress") final FMAddressForm fmAddressForm, final Model model)
			throws CMSItemNotFoundException
	{
		LOG.info("reviewplaceorder page");
		//	final Double totalReedemPoints = 0;


		/*
		 * if (hasItemsInCart()) { final CartData cartData = getCheckoutFlowFacade().getCheckoutCart(); if
		 * (cartData.getEntries() != null && !cartData.getEntries().isEmpty()) { for (final OrderEntryData entry :
		 * cartData.getEntries()) { final String productCode = entry.getProduct().getCode(); final ProductData product =
		 * productFacade.getProductForCodeAndOptions(productCode, Arrays.asList(ProductOption.BASIC,
		 * ProductOption.PRICE)); product.setLoyaltyPoints(getLoyaltyPointsForProduct(productCode)); totalReedemPoints =
		 * ((totalReedemPoints + Double.parseDouble(product.getLoyaltyPoints())) * entry.getQuantity());
		 * LOG.info("totalReedemPoints {{{{{{{{{{{{{{{{{{{{" + totalReedemPoints);
		 * 
		 * entry.setProduct(product); }
		 * 
		 * }
		 * 
		 * }
		 */
		model.addAttribute("cartData", getSessionService().getAttribute("loyaltyCartData"));
		final AddressData shipToAddress = new AddressData();
		B2BUnitData shipToUnit = null;
		LOG.info("FIRST NAME" + fmAddressForm.getFirstName());
		LOG.info("LAST NAME" + fmAddressForm.getLastName());
		LOG.info("FIRST NAME" + fmAddressForm.getFirstName());
		LOG.info("LINE1" + fmAddressForm.getLine1());
		LOG.info("line2" + fmAddressForm.getLine2());
		LOG.info("FIRST NAME" + fmAddressForm.getFirstName());
		shipToAddress.setFirstName(fmAddressForm.getFirstName());
		shipToAddress.setLastName(fmAddressForm.getLastName());
		final CountryData country = new CountryData();
		country.setIsocode(fmAddressForm.getCountryIso());
		shipToAddress.setCountry(country);
		final RegionData region = new RegionData();
		region.setIsocode(fmAddressForm.getRegion());
		shipToAddress.setRegion(region);
		shipToAddress.setLine1(fmAddressForm.getLine1());
		shipToAddress.setLine2(fmAddressForm.getLine2());
		shipToAddress.setPostalCode(fmAddressForm.getPostcode());
		shipToAddress.setTown(fmAddressForm.getTownCity());
		shipToAddress.setPhone(fmAddressForm.getContactNo());
		//shipToAddress = getSessionService().getAttribute("shipToAddress");
		model.addAttribute("totalReedemPoints", getRedeemedPointsForOrder());
		getSessionService().setAttribute("loayltyManualShipToAddress", shipToAddress);
		shipToUnit = getSessionService().getAttribute("shipToUnit");
		model.addAttribute("TotalPoints", getSessionService().getAttribute(WebConstants.BALANCE_LOYALTY_POINTS));
		model.addAttribute("shipToAddress", shipToAddress);
		model.addAttribute("shipToUnit", shipToUnit);
  		model.addAttribute("breadcrumbs", loyaltyBreadcrumbBuilder.getCheckoutBreadcrumbs("Checkout"));
		storeCmsPageInModel(model, getContentPageForLabelOrId(FM_LOYALTY_CHECKOUT_REVIEW_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FM_LOYALTY_CHECKOUT_REVIEW_PAGE));
		return ControllerConstants.Views.Pages.Account.loyaltycheckoutreviewplace;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/orderconfirm")
	public String getLoyaltyOrderConfirm(final Model model) throws CMSItemNotFoundException
	{
		LOG.info("order confirm page");
		int totalReedemPoints = 0;
		OrderData orderData = null;
		/*
		 * if (hasItemsInCart()) { final CartData cartData = getCheckoutFlowFacade().getCheckoutCart(); if
		 * (cartData.getEntries() != null && !cartData.getEntries().isEmpty()) { for (final OrderEntryData entry :
		 * cartData.getEntries()) {
		 * 
		 * final String productCode = entry.getProduct().getCode(); final ProductData product =
		 * productFacade.getProductForCodeAndOptions(productCode, Arrays.asList(ProductOption.BASIC,
		 * ProductOption.PRICE)); product.setLoyaltyPoints(getLoyaltyPointsForProduct(productCode)); totalReedemPoints =
		 * ((totalReedemPoints + Double.parseDouble(product.getLoyaltyPoints())) * entry.getQuantity());
		 * LOG.info("totalReedemPoints {{{{{{{{{{{{{{{{{{{{" + totalReedemPoints);
		 * 
		 * entry.setProduct(product); }
		 * 
		 * }
		 * 
		 * }
		 */


		model.addAttribute("cartData", getSessionService().getAttribute("loyaltyCartData"));
		final CartData loyaltyCart = getSessionService().getAttribute("loyaltyCartData");
		fmTempLoyaltyPdfViewData.setCartData(loyaltyCart);
		//totalReedemPoints = getSessionService().getAttribute("totalredeemedpoints");

		fmTempLoyaltyPdfViewData.setRedeemPoints(getSessionService().getAttribute("totalredeemedpoints").toString());
		final String loyaltyPoints = getSessionService().getAttribute(WebConstants.BALANCE_LOYALTY_POINTS);
		fmTempLoyaltyPdfViewData.setAvailablePoints(loyaltyPoints);
		AddressData shipToAddress = null;
		B2BUnitData shipToUnit = null;
		LOG.info("final totalReedemPoints {{{{{{{{{{{{{{{{{{{{" + totalReedemPoints);
		shipToAddress = getSessionService().getAttribute("loayltyManualShipToAddress");
		LOG.info("INSIDE LOYALTY CONFIRM FIRST NAME " + shipToAddress.getFirstName());
		LOG.info("INSIDE LOYALTY CONFIRM last NAME " + shipToAddress.getLastName());
		LOG.info("INSIDE LOYALTY CONFIRM line1 " + shipToAddress.getLine1());
		LOG.info("INSIDE LOYALTY CONFIRM line2 " + shipToAddress.getLine2());

		LOG.info("INSIDE LOYALTY CONFIRM FIRST NAME " + shipToAddress.getFirstName());
		try
		{
			final LoyaltyOrderConfirmHelper orderHelper = new LoyaltyOrderConfirmHelper();
			final LoyaltyOrderConfirmRequestDTO orderRequest = new LoyaltyOrderConfirmRequestDTO();
			final List<LoyaltyOrderRedemptionProductInfobean> requestProducts = new ArrayList<LoyaltyOrderRedemptionProductInfobean>();
			final int count = 0;
			if (hasItemsInCart())
			{
				final CartData cartData = getCheckoutFlowFacade().getCheckoutCart();
				if (cartData.getEntries() != null && !cartData.getEntries().isEmpty())
				{
					for (final OrderEntryData entry : cartData.getEntries())
					{

						if (entry.getEntries() != null && !entry.getEntries().isEmpty())
						{
							for (final OrderEntryData variantEntry : entry.getEntries())
							{
								final String productCode = variantEntry.getProduct().getCode();
								final ProductData product = productFacade.getProductForCodeAndOptions(productCode,
										Arrays.asList(ProductOption.BASIC, ProductOption.PRICE));
								product.setLoyaltyPoints(getLoyaltyPointsForProduct(productCode));
								totalReedemPoints = totalReedemPoints
										+ (int) ((Double.parseDouble(product.getLoyaltyPoints())) * variantEntry.getQuantity());
								LOG.info("Varient totalReedemPoints " + totalReedemPoints);
								entry.setProduct(product);
								final LoyaltyOrderRedemptionProductInfobean requestproduct = new LoyaltyOrderRedemptionProductInfobean();
								requestproduct.setBrandCode(entry.getProduct().getCode());
								requestproduct.setQty(variantEntry.getQuantity().toString());
								requestproduct.setPointsRedeemed(getLoyaltyPointsForProduct(productCode));
								requestProducts.add(requestproduct);
							}
						}
						else
						{
							final String productCode = entry.getProduct().getCode();
							final ProductData product = productFacade.getProductForCodeAndOptions(productCode,
									Arrays.asList(ProductOption.BASIC, ProductOption.PRICE));
							product.setLoyaltyPoints(getLoyaltyPointsForProduct(productCode));
							totalReedemPoints = totalReedemPoints
									+ (int) ((Double.parseDouble(product.getLoyaltyPoints())) * entry.getQuantity());
							LOG.info("totalReedemPoints {{{{{{{{{{{{{{{{{{{{" + totalReedemPoints);
							entry.setProduct(product);
							final LoyaltyOrderRedemptionProductInfobean requestproduct = new LoyaltyOrderRedemptionProductInfobean();
							requestproduct.setBrandCode(entry.getProduct().getCode());
							requestproduct.setQty(entry.getQuantity().toString());
							requestproduct.setPointsRedeemed(getLoyaltyPointsForProduct(productCode));
							requestProducts.add(requestproduct);
						}
					}

				}

			}










			LOG.info("TOTAL NUMBER OF PRODUCTS IN CART " + count);
			LOG.info("TOTAL PRODUCTS IN REQUEST" + requestProducts.size());

			LOG.info("shipToAddress.getRegion().getIsocodeShort() :: " + shipToAddress.getRegion().getIsocodeShort());
			LOG.info("shipToAddress.getTown() :: " + shipToAddress.getTown());
			LOG.info("Loyalty MemberShip ID  :: " + fmCustomerFacade.getCurrentFMCustomer().getB2cLoyaltyMembershipId());
			orderRequest.setMemberShipId(fmCustomerFacade.getCurrentFMCustomer().getB2cLoyaltyMembershipId());
			orderRequest.setSapAccountCode(fmCustomerFacade.getCurrentFMCustomer().getCrmContactId());
			orderRequest.setSapAccCodeAddress1(shipToAddress.getLine1());
			orderRequest.setSapAccCodeAddress2(shipToAddress.getLine2());
			orderRequest.setSapAccCodeCity(shipToAddress.getTown());
			orderRequest.setSapAccCodeCountry(shipToAddress.getCountry().getIsocode());

			LOG.info("shipToAddress.getRegion().getIsocode() :$$$$:: " + shipToAddress.getRegion().getIsocode());
			orderRequest.setSapAccCodeState(shipToAddress.getRegion().getIsocode());

			orderRequest.setSapAccCodeZipCode(shipToAddress.getPostalCode());
			orderRequest.setProductInfo(requestProducts);
			try
			{
				final LoyaltyOrderConfirmResponseDTO response = (LoyaltyOrderConfirmResponseDTO) orderHelper
						.sendSOAPMessage(orderRequest);
				LOG.info("ORDER CODE FROM CRM" + response.getObj_Id());
				LOG.info("desc " + response.getDesc());
				model.addAttribute("crmordercode", response.getObj_Id());
				getSessionService().setAttribute("loyaltyCRMOrderno", response.getObj_Id());
				fmTempLoyaltyPdfViewData.setCrmOrderConfNo(response.getObj_Id());
			}
			catch (final UnsupportedOperationException e)
			{
				// YTODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (final ClassNotFoundException e)
			{
				// YTODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (final SOAPException e)
			{
				// YTODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (final IOException e)
			{
				// YTODO Auto-generated catch block
				e.printStackTrace();
			}


			final AbstractOrderModel abstractOrderModel = b2bOrderService.getAbstractOrderForCode(loyaltyCart.getCode());
			if (getSessionService().getAttribute("loyaltyCRMOrderno") != null)
			{
				abstractOrderModel.setPurchaseOrderNumber(getSessionService().getAttribute("loyaltyCRMOrderno").toString());
			}
			modelService.save(abstractOrderModel);

			orderData = getCheckoutFlowFacade().placeOrder();
			getSessionService().setAttribute("orderData", orderData);
			//model.addAttribute("orderCode", orderData.getCode());
			fmTempLoyaltyPdfViewData.setOrderData(orderData);

			//LOG.info("******ORDER EMAIL START****************");
			//final OrderModel orderModel = b2bOrderService.getOrderByCode(orderData.getCode());

			final OrderModel orderModel = b2bOrderService.getOrderByCode(orderData.getCode());
		final List<AbstractOrderEntryModel> entries = orderModel.getEntries();
			for (final AbstractOrderEntryModel entry : entries)
			{
				LOG.info("PRODUCT CODE" + entry.getProduct().getCode());
				LOG.info("PRODUCT CODE" + entry.getQuantity());
				final List<MediaModel> models = (List<MediaModel>) entry.getProduct().getThumbnails();
				LOG.info("NUMBER OF THUMNAILS" + models.size());
				if (!models.isEmpty())
				{
					for (final MediaModel mm : models)
					{
						LOG.info("MEDIA FORMAT" + mm.getMediaFormat().getName());
					}
				}
				//LOG.info("MEDIA FORMAT 2 single entry " + entry.getProduct().getThumbnail().getMediaFormat().getName());
			}
			final LoyaltyOrderConfirmationProcessModel loyaltyConfirmationProcessModel = new LoyaltyOrderConfirmationProcessModel(); // loyaltyConfirmationProcessModel.setOrder(orderModel);
			if (getSessionService().getAttribute("loyaltyCRMOrderno") != null)
			{
				loyaltyConfirmationProcessModel.setOrderNumber(getSessionService().getAttribute("loyaltyCRMOrderno").toString());
			}

			loyaltyConfirmationProcessModel.setCustomer(fmUserSearchDAO.getUserForUID(fmCustomerFacade.getCurrentFMCustomer()
					.getUid()));
			loyaltyConfirmationProcessModel.setOrder(orderModel);
			LOG.info("B4 SAVING EMAIL IFD"
					+ fmUserSearchDAO.getUserForUID(fmCustomerFacade.getCurrentFMCustomer().getUid()).getUid());
			//modelService.save(loyaltyConfirmationProcessModel);
			//modelService.refresh(loyaltyConfirmationProcessModel);
			LOG.info("EMAIL ID AFTER SAVING IN MODEL " + loyaltyConfirmationProcessModel.getCustomer().getUid());
			final LoyaltyOrderConfirmationProcessEvent loyaltyorderconfirmProcessEvent = new LoyaltyOrderConfirmationProcessEvent(
					loyaltyConfirmationProcessModel);

			eventService.publishEvent(initializeEvent(loyaltyorderconfirmProcessEvent,
					fmUserSearchDAO.getUserForUID(fmCustomerFacade.getCurrentFMCustomer().getUid())));


			/*
			 * final LoyaltyOrderConfirmationProcessModel loyaltyConfirmationProcessModel = new
			 * LoyaltyOrderConfirmationProcessModel(); loyaltyConfirmationProcessModel.setOrder(orderModel);
			 * 
			 * final LoyaltyOrderConfirmationProcessEvent loyaltyorderconfirmProcessEvent = new
			 * LoyaltyOrderConfirmationProcessEvent( loyaltyConfirmationProcessModel); LOG.info("user" +
			 * userService.getUserForUID("sai.v27@wipro.com").getUid());
			 * 
			 * eventService.publishEvent(initializeEvent(loyaltyorderconfirmProcessEvent, (CustomerModel)
			 * userService.getUserForUID("sai.v27@wipro.com")));
			 */
		}
		catch (final InvalidCartException e)
		{
			LOG.error("in InvalidCartException::" + e.getMessage());
		}


		fmTempLoyaltyPdfViewData.setShipToAddress(shipToAddress);
		model.addAttribute("totalReedemPoints", totalReedemPoints);

		model.addAttribute("CurrentCustomerData", fmCustomerFacade.getCurrentFMCustomer());
		LOG.info("UID ************ " + fmCustomerFacade.getCurrentFMCustomer().getUid());
		LOG.info("EMAIL  ************ " + fmCustomerFacade.getCurrentFMCustomer().getEmail());

		model.addAttribute("TotalPoints", getSessionService().getAttribute(WebConstants.BALANCE_LOYALTY_POINTS));
		shipToUnit = getSessionService().getAttribute("shipToUnit");
		model.addAttribute("shipToAddress", shipToAddress);
		model.addAttribute("shipToUnit", shipToUnit);
		model.addAttribute("breadcrumbs", loyaltyBreadcrumbBuilder.getCheckoutBreadcrumbs("Checkout"));
		storeCmsPageInModel(model, getContentPageForLabelOrId(FM_LOYALTY_CHECKOUT_CONFIRM_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(FM_LOYALTY_CHECKOUT_CONFIRM_PAGE));
		return ControllerConstants.Views.Pages.Account.loyaltycheckoutorderconfirm;
	}


	public LoyaltyOrderConfirmationProcessEvent initializeEvent(final LoyaltyOrderConfirmationProcessEvent event,
			final CustomerModel customerModel)
	{
		LOG.info("Inside initialise event");
		try
		{
			LOG.info("inside try");
			event.setBaseStore(getBaseStoreService().getCurrentBaseStore());
			LOG.info("BASE STORE" + getBaseStoreService().getCurrentBaseStore().getName());
			event.setSite(getBaseSiteService().getCurrentBaseSite());
			LOG.info("BASE SITE" + getBaseSiteService().getCurrentBaseSite().getName());
			event.setCustomer(customerModel);
			LOG.info("FROM EVEN CUSTOMER ID" + event.getCustomer().getUid());
			//event.setLanguage(getCommonI18NService().getCurrentLanguage());
			//event.setCurrency(getCommonI18NService().getCurrentCurrency());
			LOG.info("b4 returning event");
			return event;
		}
		catch (final Exception e)
		{
			LOG.error(e.getMessage());
		}
		return null;
	}



	@RequestMapping(value = "/loyaltyorderConfirmationExport", method = RequestMethod.GET)
	public ModelAndView downloadPdf(final Model model) throws CMSItemNotFoundException
	{

		return new ModelAndView("loyaltyOrderConfirmPDFView", "globalData", fmTempLoyaltyPdfViewData);
	}

	private String getLoyaltyPointsForProduct(final String productCode)
	{

		LOG.info("productCode{{{{{{{{{{{{{{{{{{{{" + productCode);


		String loyaltyPoints = "";
		final ProductModel productModel = productService.getProductForCode(productCode);
		if (productModel != null)
		{
			loyaltyPoints = productModel.getLoyaltyPoints();
		}
		return loyaltyPoints;
	}


	private int getRedeemedPointsForOrder()
	{
		LOG.info("######## getRedeemedPointsForOrder Called #########");
		int totalReedemPoints = 0;

		if (hasItemsInCart())
		{

			final CartData cartData = getCheckoutFlowFacade().getCheckoutCart();
			if (cartData.getEntries() != null && !cartData.getEntries().isEmpty())
			{
				for (final OrderEntryData entry : cartData.getEntries())
				{

					if (entry.getEntries() != null && !entry.getEntries().isEmpty())
					{
						for (final OrderEntryData variantEntry : entry.getEntries())
						{
							final String productCode = variantEntry.getProduct().getCode();
							final ProductData product = productFacade.getProductForCodeAndOptions(productCode,
									Arrays.asList(ProductOption.BASIC, ProductOption.PRICE));
							product.setLoyaltyPoints(getLoyaltyPointsForProduct(productCode));
							totalReedemPoints = totalReedemPoints
									+ (int) ((Double.parseDouble(product.getLoyaltyPoints())) * variantEntry.getQuantity());
							LOG.info("Varient totalReedemPoints " + totalReedemPoints);

							entry.setProduct(product);
						}
					}
					else
					{

						final String productCode = entry.getProduct().getCode();
						final ProductData product = productFacade.getProductForCodeAndOptions(productCode,
								Arrays.asList(ProductOption.BASIC, ProductOption.PRICE));
						product.setLoyaltyPoints(getLoyaltyPointsForProduct(productCode));
						totalReedemPoints = totalReedemPoints
								+ (int) ((Double.parseDouble(product.getLoyaltyPoints())) * entry.getQuantity());
						LOG.info("totalReedemPoints :: " + totalReedemPoints);

						entry.setProduct(product);
					}
				}

			}

		}
		LOG.info("Final totalReedemPoints :: " + totalReedemPoints);
		return totalReedemPoints;

	}

	public BusinessProcessService getBusinessProcessService()
	{
		return (BusinessProcessService) Registry.getApplicationContext().getBean("businessProcessService");
	}

}
