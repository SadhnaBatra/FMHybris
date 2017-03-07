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
package com.federalmogul.storefront.controllers.pages.checkout;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNull;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.federalmogul.core.model.FMCustomerAccountModel;
import com.federalmogul.core.model.FMCustomerModel;
import com.federalmogul.core.model.TSCLocationModel;
import com.federalmogul.core.payment.utils.FMDigestUtils;
import com.federalmogul.core.product.PartService;
import com.federalmogul.core.util.FMUtils;
import com.federalmogul.facades.account.FMCustomerFacade;
import com.federalmogul.facades.order.FMDistrubtionCenterFacade;
import com.federalmogul.facades.order.data.DistrubtionCenterData;
import com.federalmogul.facades.order.data.FMTempPdfViewData;
import com.federalmogul.facades.user.data.FMCustomerData;
import com.federalmogul.falcon.core.model.FMCorporateModel;
import com.federalmogul.falcon.core.model.FMDCCenterModel;
import com.federalmogul.falcon.core.model.FMDCShippingModel;
import com.federalmogul.falcon.core.model.FMDistrubtionCenterModel;
import com.federalmogul.falcon.core.model.FMPartModel;
import com.federalmogul.hybris.facades.order.FMCheckoutFacade;
import com.federalmogul.storefront.annotations.RequireHardLogIn;
import com.federalmogul.storefront.constants.WebConstants;
import com.federalmogul.storefront.controllers.ControllerConstants;
import com.federalmogul.storefront.controllers.util.GlobalMessages;
import com.federalmogul.storefront.forms.AddressForm;
import com.federalmogul.storefront.forms.HopPaymentDetailsForm;
import com.federalmogul.storefront.forms.UPSForm;
import com.federalmogul.storefront.security.B2BUserGroupProvider;
import com.fm.falcon.webservices.soap.ups.wsdl.axis.RateServiceStub.AddressType;
import com.fm.falcon.webservices.soap.ups.wsdl.axis.RateServiceStub.CodeDescriptionTypeE;
import com.fm.falcon.webservices.soap.ups.wsdl.axis.RateServiceStub.PackageType;
import com.fm.falcon.webservices.soap.ups.wsdl.axis.RateServiceStub.PackageWeightType;
import com.fm.falcon.webservices.soap.ups.wsdl.axis.RateServiceStub.RateRequest;
//import com.fm.falcon.webservices.soap.ups.wsdl.axis.RateServiceStub.RateRequest;
import com.fm.falcon.webservices.soap.ups.wsdl.axis.RateServiceStub.RateResponse;
import com.fm.falcon.webservices.soap.ups.wsdl.axis.RateServiceStub.RatedShipmentType;
import com.fm.falcon.webservices.soap.ups.wsdl.axis.RateServiceStub.RequestType;
import com.fm.falcon.webservices.soap.ups.wsdl.axis.RateServiceStub.ServiceAccessToken_type0;
//import com.fm.falcon.webservices.soap.ups.wsdl.axis.RateServiceStub.RequestType;
//import com.fm.falcon.webservices.soap.ups.wsdl.axis.RateServiceStub.ServiceAccessToken_type0;
import com.fm.falcon.webservices.soap.ups.wsdl.axis.RateServiceStub.ShipAddressType;
import com.fm.falcon.webservices.soap.ups.wsdl.axis.RateServiceStub.ShipFromType;
import com.fm.falcon.webservices.soap.ups.wsdl.axis.RateServiceStub.ShipToAddressType;
import com.fm.falcon.webservices.soap.ups.wsdl.axis.RateServiceStub.ShipToType;
import com.fm.falcon.webservices.soap.ups.wsdl.axis.RateServiceStub.ShipmentType;
import com.fm.falcon.webservices.soap.ups.wsdl.axis.RateServiceStub.ShipperType;
import com.fm.falcon.webservices.soap.ups.wsdl.axis.RateServiceStub.UPSSecurity;
import com.fm.falcon.webservices.soap.ups.wsdl.axis.RateServiceStub.UsernameToken_type0;
import com.fmo.wom.business.as.OrderASUSCanImpl;
import com.fmo.wom.common.dao.AuditInterceptor;
import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.domain.AddressBO;
import com.fmo.wom.domain.BillToAcctBO;
import com.fmo.wom.domain.CountryBO;
import com.fmo.wom.domain.CreditCheckBO;
import com.fmo.wom.domain.CustomerSalesOrgBO;
import com.fmo.wom.domain.DistributionCenterBO;
import com.fmo.wom.domain.InventoryBO;
import com.fmo.wom.domain.ItemBO;
import com.fmo.wom.domain.ManualShipToAddressBO;
import com.fmo.wom.domain.NabsShippingCodeBO;
import com.fmo.wom.domain.OrderBO;
import com.fmo.wom.domain.PartBO;
import com.fmo.wom.domain.PriceBO;
import com.fmo.wom.domain.QuantityBO;
import com.fmo.wom.domain.SalesOrganizationBO;
import com.fmo.wom.domain.SapAcctBO;
import com.fmo.wom.domain.SapShippingCodeBO;
import com.fmo.wom.domain.ShipToAcctBO;
import com.fmo.wom.domain.ShippingCodeBO;
import com.fmo.wom.domain.UserAccountBO;
import com.fmo.wom.domain.enums.BackOrderPolicy;
import com.fmo.wom.domain.enums.ExternalSystem;
import com.fmo.wom.domain.enums.OrderSource;
import com.fmo.wom.domain.enums.OrderType;
import com.fmo.wom.domain.enums.UsageType;
import com.fmo.wom.integration.SAPService;
import com.fmo.wom.integration.nabs.action.GetMasterOrderNumberAction;

import de.hybris.platform.acceleratorservices.config.SiteConfigService;
import de.hybris.platform.acceleratorservices.controllers.page.PageType;
import de.hybris.platform.b2b.services.B2BOrderService;
import de.hybris.platform.b2bacceleratorfacades.company.B2BCommerceUserFacade;
import de.hybris.platform.b2bacceleratorfacades.company.CompanyB2BCommerceFacade;
import de.hybris.platform.b2bacceleratorfacades.order.data.B2BUnitData;
import de.hybris.platform.b2bacceleratorservices.company.CompanyB2BCommerceService;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.order.CartFacade;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.order.data.CartModificationData;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.product.ProductFacade;
import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.user.UserFacade;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.data.CountryData;
import de.hybris.platform.commercefacades.user.data.RegionData;
import de.hybris.platform.commerceservices.customer.CustomerAccountService;
import de.hybris.platform.core.Registry;
import de.hybris.platform.core.enums.CreditCardType;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.order.payment.CreditCardPaymentInfoModel;
import de.hybris.platform.core.model.security.PrincipalGroupModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.enumeration.EnumerationService;
import de.hybris.platform.order.CartService;
import de.hybris.platform.order.InvalidCartException;
import de.hybris.platform.orderprocessing.model.OrderProcessModel;
import de.hybris.platform.payment.model.PaymentTransactionEntryModel;
import de.hybris.platform.payment.model.PaymentTransactionModel;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.util.Config;

/**
 * MultiStepCheckoutController
 */
@Controller
@Scope("tenant")
@RequestMapping(value = "/checkout/multi")
@SessionAttributes("dc")
public class MultiStepCheckoutController extends AbstractCheckoutController {

	private static final Logger LOG = Logger.getLogger(MultiStepCheckoutController.class);

	private static final String MULTI_STEP_CHECKOUT_CMS_PAGE_LABEL = "multiStepCheckout";
	private static final String REDIRECT_URL_CART = FORWARD_PREFIX + "/cart";
	private static final String REDIRECT_URL_CHECKOUT_CONFIRMATION = REDIRECT_PREFIX
			+ "/checkout/multi/reviewPlaceOrder";
	private static final String CARRIER_CODE_UPS = "UPS";
	private static final String CARRIER_ACCOUNTCODE_DUMMY = "dummy";
	private static final String CARRIER_ACCOUNTCODE_NA = "NA";
	private static final String CARRIER_NAME_COLLECT = "Collect";
	private static final String LOWER_SHIP_METHOD_NAME_COLLECT = "collect";
	private static final String CARRIER_NAME_FEDXCOLLECT = "FedEx Collect";
	private static final String CARRIER_CODE_FED = "FED";
	private static final String DISTANCE_NOT_AVAILABLE = "DistanceNotAvailable";

	private static final String LICENSE_NUMBER = "accessKey";
	private static final String USER_NAME = "username";
	private static final String PASSWORD = "password";
	private static final String ENDPOINT_URL="ENDPOINT_URL";

	private static final String TOOL_OR_WEB_SERVICE_NAME = "tool_or_webservice_name";

	private static final String CARRIER_UPS_1DA = "01";
	private static final String CARRIER_UPS_2DA = "02";
	private static final String CARRIER_UPS_GROUND = "03";
	private static final String CARRIER_UPS_3DS = "12";
	private static final String CARRIER_UPS_NDA_SAVER = "13";
	private static final String CARRIER_UPS_NDA_EARLY = "14";
	private static final String CARRIER_UPS_2DA_AM = "59";
	
	int linenumber = 1;

	FMTempPdfViewData globalTempDataForSavePdf = new FMTempPdfViewData();

	@Autowired
	private FMDistrubtionCenterFacade fmDistrubtionCenterFacade;

	@Resource(name = "b2bUserGroupProvider")
	private B2BUserGroupProvider b2bUserGroupProvider;

	@Resource(name = "b2bProductFacade")
	private ProductFacade productFacade;

	/*
	 * @Autowired private CustomerFacade customerFacade;
	 */

	@Autowired
	private CommonI18NService commonI18NService;

	@Autowired
	private UserService userService;

	@Autowired
	private ModelService modelService;

	@Autowired
	private EnumerationService enumerationService;

	@Resource(name = "userFacade")
	protected UserFacade userFacade;

	@Autowired
	private CartService cartService;

	@Resource
	protected FMCustomerFacade fmCustomerFacade;

	@Autowired
	private FMCheckoutFacade checkoutFacade;

	@Autowired
	protected B2BCommerceUserFacade b2bCommerceUserFacade;

	@Deprecated
	@Resource(name = "cartFacade")
	private CartFacade cartFacade;

	@Resource(name = "cartFacade")
	private de.hybris.platform.b2bacceleratorfacades.api.cart.CartFacade b2bCartFacade;

	@Autowired
	private SessionService service;

	/**
	 * @return the service
	 */
	public SessionService getService() {
		return service;
	}

	/**
	 * @param service
	 *            the service to set
	 */
	public void setService(final SessionService service) {
		this.service = service;
	}

	@Resource(name = "partService")
	private PartService partService;

	@Resource(name = "b2bCommerceFacade")
	protected CompanyB2BCommerceFacade companyB2BCommerceFacade;

	@Resource
	private CustomerAccountService customerAccountService;

	@Resource
	protected CompanyB2BCommerceService companyB2BCommerceService;

	@Resource(name = "siteConfigService")
	private SiteConfigService siteConfigService;

	@Resource
	private B2BOrderService b2bOrderService;

	/**
	 * @return the customerAccountService
	 */
	public CustomerAccountService getCustomerAccountService() {
		return customerAccountService;
	}

	/**
	 * @param customerAccountService
	 *            the customerAccountService to set
	 */
	public void setCustomerAccountService(final CustomerAccountService customerAccountService) {
		this.customerAccountService = customerAccountService;
	}

	/**
	 * This is the entry point (first page) for the the multi-step checkout
	 * process. The page returned by this call acts as a template landing page
	 * and an example for actual implementation.
	 * 
	 * @return - the multi-step checkout page if the basket contains any items
	 *         or the cart page otherwise.
	 * @throws CMSItemNotFoundException
	 *             - when a CMS page is not found
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String nextPageBasedOnUser() {
		final UserModel user = userService.getCurrentUser();

		final boolean isUserAnonymous = user == null
				|| userService.isAnonymousUser(user);

		final Set<PrincipalGroupModel> groupss = user.getGroups();
		final Iterator groupsiterator = groupss.iterator();

		while (groupsiterator.hasNext()) {
			final PrincipalGroupModel groupmodel = (PrincipalGroupModel) groupsiterator
					.next();

			LOG.info("nextPageBasedOnUser(): Groups: " + groupmodel.getUid());
			if (!isUserAnonymous) {

				if ("FMB2BB".equals(groupmodel.getUid())) {
					LOG.info("nextPageBasedOnUser(): Hitting B2B");
					return REDIRECT_PREFIX + "/checkout/multi/deliverymethod";
				} else if ("FMB2SB".equals(groupmodel.getUid())) {
					LOG.info("nextPageBasedOnUser(): Hitting B2b");
					return REDIRECT_PREFIX + "/checkout/multi/deliverymethod";
				} else if ("FMB2C".equals(groupmodel.getUid())) {
					LOG.info("nextPageBasedOnUser(): Hitting B2C");
					return REDIRECT_PREFIX + "/checkout/multi/shipto";
				} else if ("FMCSR".equals(groupmodel.getUid())) {
					LOG.info("nextPageBasedOnUser(): Hitting B2B");
					return REDIRECT_PREFIX + "/checkout/multi/deliverymethod";
				}
			}
		}
		return REDIRECT_PREFIX + "/checkout/multi/deliverymethod";
	}

	@RequestMapping(value = "/shipto", method = { RequestMethod.POST, RequestMethod.GET})
	@RequireHardLogIn
	public String gotoShipingAddress(final Model model)
			throws CMSItemNotFoundException {
		if (!b2bUserGroupProvider.isCurrentUserAuthorizedToCheckOut()) {
			GlobalMessages.addErrorMessage(model, "checkout.error.invalid.accountType");
			return FORWARD_PREFIX + "/cart";
		}

		// will through error as we need to take address from b2c cutomer
		B2BUnitData shipToUnit = null;
		AddressData shipToAddress = new AddressData();
		final B2BUnitData parentUnit = null;
		AddressData soldToAddress = new AddressData();
		B2BUnitData soldToUnit = null;
		shipToUnit = companyB2BCommerceFacade
				.getUnitForUid((String) getSessionService().getAttribute(
						WebConstants.SHIPTO_ACCOUNT_ID));

		for (final AddressData billToAddresss : shipToUnit.getAddresses()) {
			shipToAddress = billToAddresss;
		}

		if (hasItemsInCart()) {
			final CartData cartData = getCheckoutFlowFacade().getCheckoutCart();

			soldToUnit = companyB2BCommerceFacade
					.getUnitForUid((String) getSessionService().getAttribute(
							WebConstants.SOLDTO_ACCOUNT_ID));
			if (soldToUnit != null
					&& !"16427113580".equalsIgnoreCase(soldToUnit.getUid())) {
				// companyB2BCommerceFacade.getb
				// soldToUnit = getB2BUnitConverter().convert(soldToUnitModel);
				if (soldToUnit.getAddresses() != null) {
					for (final AddressData soldToAddresss : soldToUnit
							.getAddresses()) {
						soldToAddress = soldToAddresss;
					}
				}
			} else {
				soldToAddress = shipToAddress;
			}

			if (soldToAddress != null && soldToUnit != null
					&& !"16427113580".equalsIgnoreCase(soldToUnit.getUid())) {
				model.addAttribute("soldToAddress", soldToAddress);
				model.addAttribute("soldToUnit", soldToUnit);
			} else {
				model.addAttribute("soldToAddress", shipToAddress);
				model.addAttribute("soldToUnit", shipToUnit);
			}
			model.addAttribute("billToAddress", shipToAddress);
			model.addAttribute("soldToUnit", parentUnit);
			model.addAttribute("shipToAddress", shipToAddress);
			model.addAttribute("shipToUnit", shipToUnit);
			model.addAttribute("allItems", cartData.getEntries());
			model.addAttribute("metaRobots", "no-index,no-follow");
			storeCmsPageInModel(
					model,
					getContentPageForLabelOrId(MULTI_STEP_CHECKOUT_CMS_PAGE_LABEL));
			setUpMetaDataForContentPage(
					model,
					getContentPageForLabelOrId(MULTI_STEP_CHECKOUT_CMS_PAGE_LABEL));
			return ControllerConstants.Views.Pages.MultiStepCheckout.checkoutShiptoAddress;
		}

		LOG.info("gotoShipingAddress(...): Missing or empty cart");
		return FORWARD_PREFIX + "/cart";
	}

	@RequestMapping(value = "/deliverymethod", method = { RequestMethod.POST, RequestMethod.GET })
	@RequireHardLogIn
	public String gotoDeliverymethod(@ModelAttribute("addressForm") final AddressForm addressForm,
			final Model model, final HttpServletRequest request)
			throws CMSItemNotFoundException {
		LOG.info("");

		String isEmergency = request.getParameter("isEmergency");
		LOG.info(isEmergency);
		CartModel cartModel = cartService.getSessionCart();
		//set order Type as Emergency for Express Checkout
		if (StringUtils.isNotBlank(isEmergency) && Boolean.parseBoolean(isEmergency)) {
			cartModel.setFmordertype(OrderType.EMERGENCY.name());
			modelService.save(cartModel);
		}

		final String pickUpStore = getSessionService().getAttribute("pickUpStore");
		final AddressData storeAddress = (AddressData) getSessionService().getAttribute("tscaddress");
		if (!b2bUserGroupProvider.isCurrentUserAuthorizedToCheckOut()) {
			GlobalMessages.addErrorMessage(model, "checkout.error.invalid.accountType");
			return FORWARD_PREFIX + "/cart";
		}

		if (hasItemsInCart()) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("gotoDeliverymethod(...): hasItemsInCart is true");
			}

			LOG.info("addressForm.getFirstName(): " + addressForm.getFirstName());

			CartData cartData = getCheckoutFlowFacade().getCheckoutCart();

			final UserModel user = userService.getCurrentUser();
			final boolean isUserAnonymous = user == null || userService.isAnonymousUser(user);
			AddressData shipToAddress = new AddressData();
			AddressData soldToAddress = new AddressData();
			B2BUnitData shipToUnit = null;
			B2BUnitData soldToUnit = null;
			String carrierAccCode = null;
			if (!isUserAnonymous) {
				shipToUnit = companyB2BCommerceFacade.getUnitForUid((String) getSessionService()
								.getAttribute(WebConstants.SHIPTO_ACCOUNT_ID));

				for (final AddressData shipToAddresss : shipToUnit.getAddresses())
				{
					shipToAddress = shipToAddresss;
				}

				final AddressData manualShipToAddress = (AddressData) getSessionService().getAttribute("shiptToAddress");
				if (manualShipToAddress != null) {
					shipToAddress = manualShipToAddress;
					// Check if Country is missing.
					if (shipToAddress.getCountry() == null) {
						CountryData shipToCntry = new CountryData();
						shipToCntry.setIsocode("US");
						shipToAddress.setCountry(shipToCntry);
					} else if (shipToAddress.getCountry().getIsocode() == null) {
						shipToAddress.getCountry().setIsocode("US");
					}
					shipToAddress.getRegion().setCountryIso("US");
					model.addAttribute("manualShipTo", true);
					shipToUnit.setName(manualShipToAddress.getFirstName());
				}
				final String pkupShippingOption = getSessionService().getAttribute("shipingOption");

				if (shipToAddress != null && (null == pickUpStore || !"pickup".equalsIgnoreCase(pkupShippingOption))) {
					LOG.info("gotoDeliverymethod(...): Inside Delivery address");
					final AddressData deliveryAddres = new AddressData();
					deliveryAddres.setFirstName(shipToUnit.getName());
					deliveryAddres.setPostalCode(shipToAddress.getPostalCode());
					deliveryAddres.setTown(shipToAddress.getTown());
					deliveryAddres.setLine1(shipToAddress.getLine1());
					deliveryAddres.setLine2(shipToAddress.getLine2());
					deliveryAddres.setCountry(shipToAddress.getCountry());
					deliveryAddres.setRegion(shipToAddress.getRegion());
					deliveryAddres.setShippingAddress(true);
					deliveryAddres.setBillingAddress(false);
					deliveryAddres.setDefaultAddress(Boolean.TRUE.equals(shipToAddress.isDefaultAddress()));
					userFacade.addAddress(deliveryAddres);
					getCheckoutFlowFacade().setDeliveryAddress(deliveryAddres);
				}

				if (null != pickUpStore && "true".equalsIgnoreCase(pickUpStore) && null != storeAddress) {
					model.addAttribute("storeInvItemBO", getSessionService().getAttribute("tscInventoryListItemBO"));
					if ("pickup".equalsIgnoreCase(pkupShippingOption)) {
						storeAddress.setShippingAddress(true);
						storeAddress.setBillingAddress(false);
						storeAddress.setDefaultAddress(Boolean.TRUE.equals(shipToAddress.isDefaultAddress()));
						userFacade.addAddress(storeAddress);
						getCheckoutFlowFacade().setDeliveryAddress(storeAddress);
					}
				}

				// rahul carrier Acc Code
				soldToUnit = companyB2BCommerceFacade.getUnitForUid((String) getSessionService()
								.getAttribute(WebConstants.SOLDTO_ACCOUNT_ID));
				final FMCustomerAccountModel fmCustomerAccModel = (FMCustomerAccountModel) companyB2BCommerceService
						.getUnitForUid(soldToUnit.getUid());
				carrierAccCode = fmCustomerAccModel.getCarrierAccountCode();
				getSessionService().setAttribute("carrierAccCode", carrierAccCode);
				LOG.info("gotoDeliverymethod(...): carrierAccCode: " + carrierAccCode);

				// mahaveer DC List...
				getDistrubtionCenter(cartData, model, shipToAddress.getPostalCode());
				cartData = getCheckoutFlowFacade().getCheckoutCart();
				if (!"16427113580".equalsIgnoreCase(soldToUnit.getUid())) {
					if (soldToUnit.getAddresses() != null) {
						for (final AddressData soldToAddresss : soldToUnit.getAddresses()) {
							soldToAddress = soldToAddresss;
						}
					}
				} else {
					soldToAddress = shipToAddress;
				}

				// Get Estimated Shipping Cost. 
				getShippingCost(shipToAddress, cartData);
				
				cartData = getCheckoutFlowFacade().getCheckoutCart();
			}

			if (soldToAddress != null && soldToUnit != null && !"16427113580".equalsIgnoreCase(soldToUnit.getUid())) {
				model.addAttribute("soldToAddress", soldToAddress);
				model.addAttribute("soldToUnit", soldToUnit);
			} else {
				model.addAttribute("soldToAddress", shipToAddress);
				model.addAttribute("soldToUnit", shipToUnit);
			}
			model.addAttribute("cartData", cartData);
			model.addAttribute("carrierAccCode", carrierAccCode);
			model.addAttribute("allItems", cartData.getEntries());
			model.addAttribute("metaRobots", "no-index,no-follow");
			model.addAttribute("storePickupAddress", storeAddress);
			storeCmsPageInModel(model, getContentPageForLabelOrId(MULTI_STEP_CHECKOUT_CMS_PAGE_LABEL));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId(MULTI_STEP_CHECKOUT_CMS_PAGE_LABEL));

			final HopPaymentDetailsForm hopPaymentDetailsForm = new HopPaymentDetailsForm();

			try
			{
				setupHostOrderPostPage(hopPaymentDetailsForm, model, shipToAddress, soldToAddress, shipToUnit, request, cartData);
			}
			catch (final Exception e)
			{
				LOG.error("gotoDeliverymethod(...): Failed to build beginCreateSubscription request", e);
				GlobalMessages.addErrorMessage(model, "checkout.multi.paymentMethod.addPaymentDetails.generalError");
				model.addAttribute("hopPaymentDetailsForm", hopPaymentDetailsForm);
			}
			model.addAttribute("pickUpStore", pickUpStore);
			final List<ItemBO> itemBOSession = getSessionService().getAttribute("itemBO");
			model.addAttribute("itemBO", itemBOSession);
			if (getSessionService().getAttribute("logedUserType").equals("ShipTo")) {
				model.addAttribute("logedUserType", "ShipTo");
			}

			return ControllerConstants.Views.Pages.MultiStepCheckout.checkoutMultiDeliveryMethod;
		}

		LOG.info("gotoDeliverymethod(...): Missing or empty cart");
		return FORWARD_PREFIX + "/cart";
	}

	@RequestMapping(value = "/paymentDetails", method = { RequestMethod.POST, RequestMethod.GET })
	@RequireHardLogIn
	public String gotoPaymentDetails(final Model model)
			throws CMSItemNotFoundException {
		if (!b2bUserGroupProvider.isCurrentUserAuthorizedToCheckOut()) {
			GlobalMessages.addErrorMessage(model, "checkout.error.invalid.accountType");
			return FORWARD_PREFIX + "/cart";
		}
		final String pickUpStore = getSessionService().getAttribute("pickUpStore");
		final AddressData storeAddress = (AddressData) getSessionService().getAttribute("tscaddress");
		model.addAttribute("pickUpStore", pickUpStore);
		model.addAttribute("storePickupAddress", storeAddress);

		if (hasItemsInCart()) {
			final CartData cartData = getCheckoutFlowFacade().getCheckoutCart();

			model.addAttribute("cartData", cartData);
			model.addAttribute("allItems", cartData.getEntries());
			model.addAttribute("metaRobots", "no-index,no-follow");
			final List<ItemBO> itemBOSession = getSessionService().getAttribute("itemBO");
			model.addAttribute("itemBO", itemBOSession);
			storeCmsPageInModel(model, getContentPageForLabelOrId(MULTI_STEP_CHECKOUT_CMS_PAGE_LABEL));
			setUpMetaDataForContentPage(model, getContentPageForLabelOrId(MULTI_STEP_CHECKOUT_CMS_PAGE_LABEL));

			return ControllerConstants.Views.Pages.MultiStepCheckout.checkoutMultiPaymentDetails;
		}
		LOG.info("gotoPaymentDetails(...): Missing or empty cart");
		return FORWARD_PREFIX + "/cart";
	}

	@RequestMapping(value = "/reviewPlaceOrder", method = { RequestMethod.POST, RequestMethod.GET })
	@RequireHardLogIn
	public String gotoReviewPlaceOrder(final Model model,
			@RequestParam("poNumber") final String poNumber,
			@RequestParam("orderInstruction") final String orderInstruction)
			throws CMSItemNotFoundException {
		LOG.info("gotoReviewPlaceOrder(...): ordercode :: ");
		//final OrderModel orderModel = getCustomerAccountService().getOrderForCode((CustomerModel) userService.getCurrentUser(),
		//ordercode, baseStoreService.getCurrentBaseStore());
		if (!b2bUserGroupProvider.isCurrentUserAuthorizedToCheckOut())
		{
			GlobalMessages.addErrorMessage(model, "checkout.error.invalid.accountType");
			return FORWARD_PREFIX + "/cart";
		}

		// final CartModel cartModel = cartService.getSessionCart();

		final String pickUpStore = getSessionService().getAttribute("pickUpStore");
		final AddressData storeAddress = (AddressData) getSessionService().getAttribute("tscaddress");
		model.addAttribute("pickUpStore", pickUpStore);
		model.addAttribute("storePickupAddress", storeAddress);
		final List<ItemBO> ineventoryList = (List<ItemBO>) getSessionService().getAttribute("tscInventoryListItemBO");
		if (null != pickUpStore && "true".equalsIgnoreCase(pickUpStore) && null != storeAddress && null != ineventoryList
				&& ineventoryList.size() > 0)
		{
			partModification(ineventoryList);
		}

		final UserModel user = userService.getCurrentUser();
		final boolean isUserAnonymous = user == null || userService.isAnonymousUser(user);
		AddressData shipToAddress = new AddressData();
		AddressData soldToAddress = new AddressData();
		B2BUnitData shipToUnit = null;
		B2BUnitData soldToUnit = null;
		if (hasItemsInCart()) {
			CartData cartData = getCheckoutFlowFacade().getCheckoutCart();
			if (!isUserAnonymous) {
				shipToUnit = companyB2BCommerceFacade
						.getUnitForUid((String) getSessionService()
								.getAttribute(WebConstants.SHIPTO_ACCOUNT_ID));

				for (final AddressData shipToAddresss : shipToUnit.getAddresses()) {
					shipToAddress = shipToAddresss;
				}

				soldToUnit = companyB2BCommerceFacade
						.getUnitForUid((String) getSessionService()
								.getAttribute(WebConstants.SOLDTO_ACCOUNT_ID));
				if (soldToUnit != null
						&& !"16427113580".equalsIgnoreCase(soldToUnit.getUid())) {
					if (soldToUnit.getAddresses() != null) {
						for (final AddressData soldToAddresss : soldToUnit
								.getAddresses()) {
							soldToAddress = soldToAddresss;
						}
					}
				} else {
					soldToAddress = shipToAddress;
					soldToUnit = shipToUnit;
				}
			}
			if (soldToAddress != null && soldToUnit != null
					&& !"16427113580".equalsIgnoreCase(soldToUnit.getUid())) {
				model.addAttribute("soldToAddress", soldToAddress);
				model.addAttribute("soldToUnit", soldToUnit);
				model.addAttribute("billToAddress", soldToAddress);
			} else {
				model.addAttribute("soldToAddress", shipToAddress);
				model.addAttribute("soldToUnit", shipToUnit);
				model.addAttribute("billToAddress", shipToAddress);
			}
			model.addAttribute("carrierAccCode", getSessionService()
					.getAttribute("carrierAccCode"));
			// manual Shipping address
			final AddressData manualShipToAddress = (AddressData) getSessionService()
					.getAttribute("shiptToAddress");
			if (manualShipToAddress != null) {
				shipToAddress = manualShipToAddress;
				model.addAttribute("manualShipTo", true);
				shipToUnit.setName(manualShipToAddress.getFirstName());
				shipToUnit.setUid(null);
			}
			model.addAttribute("shipToAddress", shipToAddress);
			model.addAttribute("shipToUnit", shipToUnit);

			if (cartData != null && cartData.getEntries() != null && !cartData.getEntries().isEmpty()) {
				model.addAttribute("allItems", cartData.getEntries());
			}

			// CREDIT check

			final FMCustomerAccountModel fmCustomerAccModel = (FMCustomerAccountModel) companyB2BCommerceService
					.getUnitForUid((String) getSessionService().getAttribute(
							WebConstants.SOLDTO_ACCOUNT_ID));
			final SalesOrganizationBO salesOrg = new SalesOrganizationBO();
			salesOrg.setCode(fmCustomerAccModel.getSalesorg());
			try {
				final CreditCheckBO creditCheckBO = SAPService.doCreditCheck(
						fmCustomerAccModel.getUid(), salesOrg);
				LOG.info("gotoReviewPlaceOrder(...): Check out Page creditCheckBO .. getCreditBlock :: "
						+ creditCheckBO.getCreditBlock());
				LOG.info("gotoReviewPlaceOrder(...): Checkout Page :  Order Blocked ... :: "
						+ creditCheckBO.getOrderBlock());
				model.addAttribute(WebConstants.FM_CREDIT_BLOCK,
						creditCheckBO.getCreditBlock());
				model.addAttribute(WebConstants.FM_ORDER_BLOCK,
						creditCheckBO.getOrderBlock());
			} catch (final WOMExternalSystemException e) {
				model.addAttribute(WebConstants.FM_CREDIT_BLOCK, "false");
				model.addAttribute(WebConstants.FM_ORDER_BLOCK, "false");
			} catch (final Exception e) {
				model.addAttribute(WebConstants.FM_CREDIT_BLOCK, "false");
				model.addAttribute(WebConstants.FM_ORDER_BLOCK, "false");
			}

			setOrderPODetails(cartData.getCode(), poNumber, orderInstruction);
			cartData = getCheckoutFlowFacade().getCheckoutCart();
			model.addAttribute("cartData", cartData);
			model.addAttribute("pickUpStore", pickUpStore);
			final List<ItemBO> itemBOSession = getSessionService().getAttribute("itemBO");
			model.addAttribute("itemBO", itemBOSession);
			model.addAttribute("metaRobots", "no-index,no-follow");

			storeCmsPageInModel(
					model,
					getContentPageForLabelOrId(MULTI_STEP_CHECKOUT_CMS_PAGE_LABEL));
			setUpMetaDataForContentPage(
					model,
					getContentPageForLabelOrId(MULTI_STEP_CHECKOUT_CMS_PAGE_LABEL));

			if (getSessionService().getAttribute("logedUserType").equals("ShipTo")) {
				model.addAttribute("logedUserType", "ShipTo");
			}

			return ControllerConstants.Views.Pages.MultiStepCheckout.checkoutMultiReviewPlaceOrder;

		}
		LOG.info("gotoReviewPlaceOrder(...): Missing or empty cart");
		return REDIRECT_PREFIX + "/cart";
	}

	@RequestMapping(value = "/orderConfirmation", method = {RequestMethod.POST, RequestMethod.GET})
	@RequireHardLogIn
	public String gotoOrderConfirmation(final Model model,
			@RequestParam("poNumber") final String poNumber,
			@RequestParam("orderInstruction") final String orderInstruction)
			throws CMSItemNotFoundException {

		if (!b2bUserGroupProvider.isCurrentUserAuthorizedToCheckOut()) {
			GlobalMessages.addErrorMessage(model, "checkout.error.invalid.accountType");
			return FORWARD_PREFIX + "/cart";
		}

		OrderData orderData = null;
		final UserModel user = userService.getCurrentUser();
		final boolean isUserAnonymous = user == null || userService.isAnonymousUser(user);
		AddressData shipToAddress = new AddressData();
		AddressData soldToAddress = new AddressData();
		B2BUnitData shipToUnit = null;
		B2BUnitData soldToUnit = null;

		if (hasItemsInCart()) {
			
			CartModel cartModel = getSessionService().getAttribute("cart");

			CartData cartData = getCheckoutFlowFacade().getCheckoutCart();
			setOrderPODetails(cartData.getCode(), poNumber, orderInstruction);
			cartData = getCheckoutFlowFacade().getCheckoutCart();

			if (!isUserAnonymous) {
				final String pickUpStore = getSessionService().getAttribute("pickUpStore");
				final AddressData storeAddress = (AddressData) getSessionService().getAttribute("tscaddress");
				model.addAttribute("pickUpStore", pickUpStore);
				model.addAttribute("storePickupAddress", storeAddress);

				shipToUnit = companyB2BCommerceFacade
						.getUnitForUid((String) getSessionService()
								.getAttribute(WebConstants.SHIPTO_ACCOUNT_ID));

				for (final AddressData shipToAddresss : shipToUnit.getAddresses()) {
					shipToAddress = shipToAddresss;
				}

				soldToUnit = companyB2BCommerceFacade
						.getUnitForUid((String) getSessionService()
								.getAttribute(WebConstants.SOLDTO_ACCOUNT_ID));
				if (soldToUnit != null
						&& !"16427113580".equalsIgnoreCase(soldToUnit.getUid())) {
					if (soldToUnit.getAddresses() != null) {
						for (final AddressData soldToAddresss : soldToUnit.getAddresses()) {
							soldToAddress = soldToAddresss;
						}
					}
				} else {
					soldToAddress = shipToAddress;
					soldToUnit = shipToUnit;
				}
			}

			if (soldToAddress != null && soldToUnit != null
					&& !"16427113580".equalsIgnoreCase(soldToUnit.getUid())) {
				model.addAttribute("soldToAddress", soldToAddress);
				getSessionService().setAttribute("soldToAddress", soldToAddress);
				model.addAttribute("soldToUnit", soldToUnit);
				getSessionService().setAttribute("soldToUnit", soldToUnit);
				model.addAttribute("billToAddress", soldToAddress);
				getSessionService().setAttribute("billToAddress", soldToAddress);
			} else {
				model.addAttribute("soldToAddress", shipToAddress);
				getSessionService().setAttribute("soldToAddress", shipToAddress);
				model.addAttribute("soldToUnit", shipToUnit);
				getSessionService().setAttribute("soldToUnit", shipToUnit);
				model.addAttribute("billToAddress", shipToAddress);
				getSessionService().setAttribute("billToAddress", shipToAddress);
			}
			// manual Shipping address

			final AddressData manualShipToAddress = (AddressData) getSessionService().getAttribute("shiptToAddress");
			if (manualShipToAddress != null) {
				shipToAddress = manualShipToAddress;
				// Check if Country is missing.
				if (shipToAddress.getCountry() == null) {
					CountryData shipToCntry = new CountryData();
					shipToCntry.setIsocode("US");
					shipToAddress.setCountry(shipToCntry);
				} else if (shipToAddress.getCountry().getIsocode() == null) {
					shipToAddress.getCountry().setIsocode("US");
				}
				shipToAddress.getRegion().setCountryIso("US");
				model.addAttribute("manualShipTo", true);
				shipToUnit.setName(manualShipToAddress.getFirstName());
				shipToUnit.setUid(null);
			}

			model.addAttribute("shipToAddress", shipToAddress);
			getSessionService().setAttribute("shipToAddress", shipToAddress);
			model.addAttribute("shipToUnit", shipToUnit);
			getSessionService().setAttribute("shipToUnit", shipToUnit);

			try {
				womProcessOrder(model, soldToUnit, shipToUnit, soldToAddress, shipToAddress);
			} catch (final Exception e) {
				LOG.error("gotoOrderConfirmation(...): " + e.getMessage());
				final String[] parms = { e.getMessage() };
				GlobalMessages.addErrorMessage(
						model,
						getMessageSource().getMessage(
								"fm.checkout.externalsystem.error", parms,
								getI18nService().getCurrentLocale()));
				return FORWARD_PREFIX
						+ "/checkout/multi/deliverymethod?poNumber="
						+ cartModel.getCustponumber() + "&orderInstruction="
						+ cartModel.getOrdercomments();
			}

			model.addAttribute("carrierAccCode", getSessionService().getAttribute("carrierAccCode"));

			model.addAttribute("shipToAddress", shipToAddress);
			getSessionService().setAttribute("shipToAddress", shipToAddress);
			model.addAttribute("shipToUnit", shipToUnit);
			getSessionService().setAttribute("shipToUnit", shipToUnit);
			model.addAttribute("cartData", cartData);
			model.addAttribute("allItems", cartData.getEntries());
			final List<ItemBO> itemBOSession = getSessionService().getAttribute("itemBO");
			model.addAttribute("itemBO", itemBOSession);
			model.addAttribute("metaRobots", "no-index,no-follow");

			storeCmsPageInModel(
					model,
					getContentPageForLabelOrId(MULTI_STEP_CHECKOUT_CMS_PAGE_LABEL));
			setUpMetaDataForContentPage(
					model,
					getContentPageForLabelOrId(MULTI_STEP_CHECKOUT_CMS_PAGE_LABEL));

			try {
				orderData = getCheckoutFlowFacade().placeOrder();
				final List<String> groupUID = new ArrayList<String>();
				final Set<PrincipalGroupModel> groupss = user.getGroups();
				for (final PrincipalGroupModel groupModel : groupss)
				{
					final String groupId = groupModel.getUid();
					groupUID.add(groupId);
				}
				final OrderModel orderModel = b2bOrderService.getOrderByCode(orderData.getCode());
				if (groupUID.contains("FMCSR")){
					final FMCustomerAccountModel CustomerAccModel = (FMCustomerAccountModel) companyB2BCommerceService
					.getUnitForUid(soldToUnit.getUid());
					orderModel.setUnit(CustomerAccModel);
					modelService.save(orderModel);
					
				}
				getSessionService().setAttribute("orderData", orderData);

				boolean noDeliveryCreatedFlag = getSessionService().getAttribute("orderSavedButNoDeliveryCreatedFlag");
				if (noDeliveryCreatedFlag) {
					LOG.info("gotoOrderConfirmation(...): *** noDeliveryCreatedFlag = TRUE. So, Order Confirmation Email will NOT be sent!! ***");
				} else {
					LOG.info("gotoOrderConfirmation(...): *** noDeliveryCreatedFlag = FALSE. So, Order Confirmation Email WILL be sent!! ***");
					LOG.info("gotoOrderConfirmation(...): ******ORDER EMAIL START****************");
	
					final OrderProcessModel orderProcessModel = (OrderProcessModel) getBusinessProcessService()
							.createProcess(
									"b2bOrderConfirmationEmailProcess"
											+ System.currentTimeMillis(),
									"b2bOrderConfirmationEmailProcess");
	
					orderProcessModel.setOrder(orderModel);
					LOG.info("gotoOrderConfirmation(...): ************orderProcessModel: " + orderProcessModel);
					modelService.save(orderProcessModel);
					getBusinessProcessService().startProcess(orderProcessModel);
	
					LOG.info("gotoOrderConfirmation(...): ******ORDER EMAIL END*****");
				}
			} catch (final InvalidCartException e) {
				LOG.error("gotoOrderConfirmation(...): in InvalidCartException:: " + e.getMessage());
			}

			if (getSessionService().getAttribute("logedUserType").equals("ShipTo")) {
				model.addAttribute("logedUserType", "ShipTo");
				globalTempDataForSavePdf.setShipToLogin("yes");
			}

			model.addAttribute("orderData", orderData);
			model.addAttribute("pageType", PageType.ORDERCONFIRMATION.name());

			globalTempDataForSavePdf.setOrderData(orderData);
			globalTempDataForSavePdf.setShipToAddress((AddressData) getSessionService().getAttribute("shipToAddress"));
			globalTempDataForSavePdf.setSoldToAddress((AddressData) getSessionService().getAttribute("soldToAddress"));
			globalTempDataForSavePdf.setShipToUnit((B2BUnitData) getSessionService().getAttribute("shipToUnit"));
			globalTempDataForSavePdf.setSoldToUnit((B2BUnitData) getSessionService().getAttribute("soldToUnit"));
			globalTempDataForSavePdf.setCartData(setPriceType(cartData));

			return ControllerConstants.Views.Pages.MultiStepCheckout.checkoutMultiOrderConfirmation;
		}

		LOG.info("gotoOrderConfirmation(...): Missing or empty cart");
		return FORWARD_PREFIX + "/cart";
	}

	protected void setupHostOrderPostPage(
			final HopPaymentDetailsForm hopPaymentDetailsForm,
			final Model model, final AddressData shipToAddress,
			final AddressData soldToAddress, final B2BUnitData shipToUnit,
			final HttpServletRequest request, final CartData cartData) {
		try {
			hopPaymentDetailsForm.setParameters(new HashMap<String, String>());
			hopPaymentDetailsForm.setUnSignedParams(new HashMap<String, String>());

			hopPaymentDetailsForm.getParameters().put("access_key", checkoutFacade.getAccessKey());
			hopPaymentDetailsForm.getParameters().put("reference_number", checkoutFacade.getReferenceNumber());
			hopPaymentDetailsForm.getParameters().put("profile_id", checkoutFacade.getProfileId());

			hopPaymentDetailsForm.getParameters().put("signed_date_time", String.valueOf(checkoutFacade.getUTCDateTime()));

			hopPaymentDetailsForm.getParameters().put("currency", commonI18NService.getCurrentCurrency().getIsocode());
			hopPaymentDetailsForm.getParameters().put("transaction_uuid", UUID.randomUUID().toString());
			hopPaymentDetailsForm.getParameters().put("locale", "en");

			hopPaymentDetailsForm.getParameters().put("signed_field_names", checkoutFacade.getSignedFieldNames());
			hopPaymentDetailsForm.getParameters().put("unsigned_field_names", "");

			hopPaymentDetailsForm.getParameters().put("amount",
					cartData.getTotalPrice().getValue().add(checkoutFacade.getExtraAuthAmount(cartData)).toString());

			hopPaymentDetailsForm.getParameters().put("transaction_type", checkoutFacade.getTransactionType());

			hopPaymentDetailsForm.getParameters().put("bill_to_forename", shipToUnit.getName());
			hopPaymentDetailsForm.getParameters().put("bill_to_surname", shipToUnit.getName());
			hopPaymentDetailsForm.getParameters().put("bill_to_address_city", soldToAddress.getTown());
			hopPaymentDetailsForm.getParameters().put("bill_to_address_country", soldToAddress.getCountry().getIsocode());
			hopPaymentDetailsForm.getParameters().put("bill_to_address_state", soldToAddress.getRegion().getIsocodeShort());
			hopPaymentDetailsForm.getParameters().put("bill_to_address_line1", soldToAddress.getLine1());
			hopPaymentDetailsForm.getParameters().put("bill_to_address_postal_code", soldToAddress.getPostalCode());
			hopPaymentDetailsForm.getParameters().put("bill_to_email", "TESOM@fm.com");

			//Shipping Address
			hopPaymentDetailsForm.getParameters().put("ship_to_forename", shipToUnit.getName());
			hopPaymentDetailsForm.getParameters().put("ship_to_surname", shipToUnit.getName());
			hopPaymentDetailsForm.getParameters().put("ship_to_address_city", soldToAddress.getTown());
			hopPaymentDetailsForm.getParameters().put("ship_to_address_country", soldToAddress.getCountry().getIsocode());
			hopPaymentDetailsForm.getParameters().put("ship_to_address_state", soldToAddress.getRegion().getIsocodeShort());
			hopPaymentDetailsForm.getParameters().put("ship_to_address_line1", soldToAddress.getLine1());
			hopPaymentDetailsForm.getParameters().put("ship_to_address_postal_code", soldToAddress.getPostalCode());
			hopPaymentDetailsForm.getParameters().put("ship_to_email", "TESOM@fm.com");

			try {
				hopPaymentDetailsForm.getParameters().put(
						"signature",
						FMDigestUtils.getPublicDigest(checkoutFacade
								.buildDataToSign(hopPaymentDetailsForm
										.getParameters()), checkoutFacade
								.getSharedSecret()));
			} catch (final InvalidKeyException e) {
				LOG.error("setupHostOrderPostPage(...): Invalid Cybersource Key:");
				LOG.error(e.getMessage());
			} catch (final NoSuchAlgorithmException e) {
				LOG.error("setupHostOrderPostPage(...): No Such Algorithm");
				LOG.error(e.getMessage());
			}

			model.addAttribute("paymentFormUrl", checkoutFacade.getPostUrl());
		} catch (final IllegalArgumentException e) {
			LOG.warn("setupHostOrderPostPage(...): Failed to set up Host order post page " + e.getMessage());
			GlobalMessages.addErrorMessage(model, "HOP Error");
		}

		model.addAttribute("hostOrderPostForm", new AddressForm());
		model.addAttribute("hopPaymentDetailsForm", hopPaymentDetailsForm);
		model.addAttribute("paymentInfos", getUserFacade().getCCPaymentInfos(true));
	}

	@RequestMapping(value = "/hop-response", method = RequestMethod.POST)
	public String doHandleSopResponse(final HttpServletRequest request,
			@Valid final HopPaymentDetailsForm hopPaymentDetailsForm,
			final BindingResult bindingResult, final Model model)
			throws CMSItemNotFoundException, InvalidCartException {
		getSessionService().getAttribute("customertype");
		final CartData cartData = getCheckoutFlowFacade().getCheckoutCart();
		final AbstractOrderModel abstractOrderModel = b2bOrderService.getAbstractOrderForCode(cartData.getCode());
		final Map<String, String> resultMap = checkoutFacade.getRequestParameterMap(request);
		LOG.info("doHandleSopResponse(...): Entered hop-respnse handler " + resultMap);
		if (LOG.isDebugEnabled()) {
			LOG.debug("doHandleSopResponse(...): Hop-Response Result map: " + resultMap);
		}

		final PaymentTransactionModel transaction = (PaymentTransactionModel) this.modelService
				.create(PaymentTransactionModel.class);
		transaction.setCode(resultMap.get("req_reference_number"));
		transaction.setRequestId(resultMap.get("transaction_id"));
		transaction.setRequestToken(resultMap.get("payment_token"));
		transaction.setPaymentProvider("cybersource");
		transaction.setOrder(abstractOrderModel);
		if ("ERROR".equalsIgnoreCase(resultMap.get("decision")) || "DECLINE".equalsIgnoreCase(resultMap.get("decision")))
		{
			if ("DECLINE".equalsIgnoreCase(resultMap.get("decision")) && "200".equals(resultMap.get("reason_code")))
			{
				GlobalMessages.addErrorMessage(model, "checkout.placeOrder.payment.failed.AVS_FAILED");
			}
			else
			{
				GlobalMessages.addErrorMessage(model, "checkout.placeOrder.payment.failed." + resultMap.get("decision"));
			}
			return FORWARD_PREFIX + "/cart";
		} else if ("ACCEPT".equalsIgnoreCase(resultMap.get("decision"))
				|| "REVIEW".equalsIgnoreCase(resultMap.get("decision"))) {
			// final OrderData orderData = checkoutFacade.placeOrder(resultMap,
			// transaction);

			final CreditCardPaymentInfoModel cardPaymentInfoModel = modelService
					.create(CreditCardPaymentInfoModel.class);

			cardPaymentInfoModel.setCode(UUID.randomUUID().toString());
			cardPaymentInfoModel.setNumber(resultMap.get("req_card_number"));
			final UserModel userModel = abstractOrderModel.getUser();
			cardPaymentInfoModel.setUser(userModel);
			cardPaymentInfoModel.setCcOwner(userModel.getName());
			final String ccmmyear = resultMap.get("req_card_expiry_date");
			if (ccmmyear != null) {
				final String[] cc = ccmmyear.split("-");
				cardPaymentInfoModel.setValidToMonth(cc[0]);
				cardPaymentInfoModel.setValidToYear(cc[1]);
			}
			final String ccType = resultMap.get("req_card_type");
			String ccTypeName = null;

			if ("001".equalsIgnoreCase(ccType)) {
				ccTypeName = "VISA";
			} else if ("002".equalsIgnoreCase(ccType)) {
				ccTypeName = "MASTER";
			} else if ("003".equalsIgnoreCase(ccType)) {
				ccTypeName = "AMEX";
			} else if ("004".equalsIgnoreCase(ccType)) {
				ccTypeName = "DISCOVER";
			} else if ("005".equalsIgnoreCase(ccType)) {
				ccTypeName = "DINERS CLUB";
			} else if ("006".equalsIgnoreCase(ccType)) {
				ccTypeName = "VISA";
			} else if ("007".equalsIgnoreCase(ccType)) {
				ccTypeName = "VISA";
			} else {
				ccTypeName = "VISA";
			}

			final CreditCardType cardType = (CreditCardType) enumerationService
					.getEnumerationValue(CreditCardType.class.getSimpleName(),
							ccTypeName);
			cardPaymentInfoModel.setType(cardType);
			modelService.save(cardPaymentInfoModel);
			abstractOrderModel.setPaymentInfo(cardPaymentInfoModel);
			transaction.setInfo(cardPaymentInfoModel);

			// Create Payment transaction model and set transaction status
			final PaymentTransactionEntryModel transactionEntry = checkoutFacade.setTransactionStatus(resultMap, transaction);
			modelService.save(transaction);
			modelService.save(transactionEntry);
			final List<PaymentTransactionModel> paymentTransactions = abstractOrderModel.getPaymentTransactions();
			final List<PaymentTransactionModel> paymentTransactionsList = new ArrayList<PaymentTransactionModel>();
			final Iterator<PaymentTransactionModel> paymentIterator = paymentTransactions.iterator();
			while (paymentIterator.hasNext()) {
				paymentTransactionsList.add(paymentIterator.next());
			}
			paymentTransactionsList.add(transaction);
			abstractOrderModel.setPaymentTransactions(paymentTransactionsList);
			modelService.save(abstractOrderModel);
			return REDIRECT_URL_CHECKOUT_CONFIRMATION + "?poNumber=123456789012345&orderInstruction=test";
		}
		return FORWARD_PREFIX + "/cart";
	}

	public void womProcessOrder(final Model model,
			final B2BUnitData soldToUnit, final B2BUnitData shipToUnit,
			final AddressData soldToAddress, final AddressData shipToAddress)
			throws Exception {

		final CartModel cartModel = cartService.getSessionCart();
		globalTempDataForSavePdf.setCartModel(cartModel);
		Date deliveryDate = null;
		linenumber = 1;
		final String shipToCountry = (null != shipToAddress && null != shipToAddress
				.getCountry().getIsocode()) ? shipToAddress.getCountry()
				.getIsocode() : "US";

		if (cartModel.getEntries() != null && !cartModel.getEntries().isEmpty()) {
			final List<ItemBO> itemsList = new ArrayList<ItemBO>();
			final List<ItemBO> itemBOSession = getSessionService().getAttribute("itemBO");

			for (final ItemBO items : itemBOSession) {
				for (final AbstractOrderEntryModel entry : cartModel.getEntries()) {
					String code = entry.getProduct().getCode();
					boolean catalogProduct = true;
					final FMPartModel productModel = partService.getPartForCode(entry.getProduct().getCode());
					for (final FMCorporateModel corp : productModel
							.getCorporate()) {
						if ("dummy".equalsIgnoreCase(corp.getCorpcode())
								|| "FELPF".equalsIgnoreCase(corp.getCorpcode())
								|| "SPDPF".equalsIgnoreCase(corp.getCorpcode())
								|| "SLDPF".equalsIgnoreCase(corp.getCorpcode())) {
							catalogProduct = false;
						}
					}
					if (!catalogProduct && null != productModel.getFlagcode()) {
						code = productModel.getFlagcode().toUpperCase() + productModel.getRawPartNumber();
					}

					deliveryDate = entry.getNamedDeliveryDate();
					String displayPartNumber = items.getDisplayPartNumber();
					if ((items.getExternalSystem() != null && items.getExternalSystem().equals(ExternalSystem.NABS))
							|| (null != items.getProductFlag())) {
						displayPartNumber = items.getProductFlag() + items.getDisplayPartNumber();
					}

					if (items.getPartNumber().equalsIgnoreCase(entry.getProduct().getCode())
							|| items.getDisplayPartNumber().equalsIgnoreCase(
									entry.getProduct().getCode())
							|| displayPartNumber.equalsIgnoreCase(entry
									.getProduct().getCode())
							|| (code.equalsIgnoreCase(displayPartNumber))) {

						if (items.getExternalSystem().equals(ExternalSystem.EVRST)
								&& "ecc".equalsIgnoreCase(siteConfigService.getString("wom.ecc", "ecc"))) {
							LOG.info("womProcessOrder(...): Calling 'getECCItemsList(...)' and adding all ECC items to the list");
							itemsList.addAll(getECCItemsList(entry, items, shipToCountry, cartModel));
						}
						else if (items.getExternalSystem().equals(ExternalSystem.NABS)
								&& "nabs".equalsIgnoreCase(siteConfigService.getString("wom.nabs", "nabs"))) {
							LOG.info("womProcessOrder(...): Calling 'getNABSItemsList(...)' and adding all NABS items to the list");
							itemsList.addAll(getNABSItemsList(entry, items, shipToCountry));
						}
					}
				}
			}

			final OrderBO order = new OrderBO();
			final OrderASUSCanImpl processOrderService = new OrderASUSCanImpl();

			if (cartModel.getFmordertype() == null) {
				cartModel.setFmordertype("STOCK");
			}

			if ("EMERGENCY".equalsIgnoreCase(cartModel.getFmordertype())) {
				order.setOrderType(OrderType.EMERGENCY);
			} else if ("REGULAR".equalsIgnoreCase(cartModel.getFmordertype())) {
				order.setOrderType(OrderType.REGULAR);
			} else if ("STOCK".equalsIgnoreCase(cartModel.getFmordertype())) {
				order.setOrderType(OrderType.STOCK);
			} else if ("PICKUP".equalsIgnoreCase(cartModel.getFmordertype())) {
				final String pkupShippingOption = getSessionService()
						.getAttribute("shipingOption");
				LOG.info("womProcessOrder(...): shippingOption ::"
						+ pkupShippingOption);
				order.setPkupOrderType(null != pkupShippingOption ? pkupShippingOption
						: "pickup");
				order.setOrderType(OrderType.PICKUP);
			}

			if (cartModel.getCustponumber() != null) {
				order.setCustPoNbr(cartModel.getCustponumber());
			} else {
				order.setCustPoNbr(getPONumber());
			}

			order.setOrderSource(OrderSource.HYBRIS);
			order.setUsageType(UsageType.PURCHASE);

			final String soldToAcc = (String) getSessionService().getAttribute(WebConstants.SOLDTO_ACCOUNT_ID);
			final FMCustomerAccountModel fmCustomerAccModel = (FMCustomerAccountModel) companyB2BCommerceService
					.getUnitForUid(soldToAcc);
			double freeFreightAmt = 0.0;
			if (fmCustomerAccModel != null
					&& fmCustomerAccModel.getFreeFreightAmt() != null
					&& fmCustomerAccModel.getFreeFreightAmt() > 0) {
				double sessionFreightAmt = 0.0;
				if (getSessionService().getAttribute("freightPrice") != null) {
					sessionFreightAmt = Double.valueOf(getSessionService().getAttribute("freightPrice").toString());
					LOG.info("womProcessOrder(...): sessionFreightAmt :: " + sessionFreightAmt);
				}
				freeFreightAmt = (fmCustomerAccModel.getFreeFreightAmt()
						.doubleValue() - sessionFreightAmt);
				LOG.info("womProcessOrder(...): freeFreightAmt :: "
						+ freeFreightAmt + "O/P"
						+ (freeFreightAmt <= 0 ? true : false));
			}
			/**
			 * Start changes for ECD-200
			 */
			if ("EMERGENCY".equalsIgnoreCase(cartModel.getFmordertype())) {
				order.setReceivesFreeFreight(false);
			}
			else {
				order.setReceivesFreeFreight(freeFreightAmt <= 0 ? true : false);
			}
			LOG.info("womProcessOrder(...): Order Type: " + cartModel.getFmordertype() + " :: " +
						"Free Freight Flag: " + order.receivesFreeFreight());
			/**
			 * End changes for ECD-200
			 */
			order.setMarketCode(processOrderService.getMarket());
			if (deliveryDate != null && "STOCK".equalsIgnoreCase(cartModel.getFmordertype())) {
				order.setFutureDate(deliveryDate);
				LOG.info("womProcessOrder(...): STOCK deliveryDate: " + deliveryDate);
			} else {
				order.setFutureDate(null);
			}
			final String moNumber = getmasterOrderNumber();
			order.setMstrOrdNbr(moNumber != null ? moNumber : cartModel.getCode());
			final FMCustomerModel user = (FMCustomerModel) cartModel.getUser();
			LOG.info("womProcessOrder(...): userName {}" + user.getLastName());
			order.setOrderedBy(user.getLastName());
			order.setUserAccount(getUserAccount(user));
			order.setBillToAcct(getBillTOAccount());
			order.setShipToAcct(getShiptoAccount(shipToUnit, order.getPkupOrderType()));

			// Manual Address
			final AddressData manualShipToAddress = (AddressData) getSessionService().getAttribute("shiptToAddress");
			if (manualShipToAddress != null) {
				order.setManualShipTo(true);
				order.setManualShipToAddress(getManualShipToAddress(manualShipToAddress));
			}

			LOG.info("womProcessOrder(...): Print :: ItemsList " + itemsList);
			if (null == itemsList || itemsList.isEmpty()) {
				throw new Exception("womProcessOrder(...): Item List is Empty");
			}
			order.setItemList(itemsList);
			LOG.info("womProcessOrder(...): PLace Order :: cartModel.getOrdercomments() {}"
					+ cartModel.getOrdercomments());
			if (cartModel.getOrdercomments() != null && !cartModel.getOrdercomments().isEmpty()) {
				order.setComment1(cartModel.getOrdercomments());
			} else {
				order.setComment1("No Comments");
			}
			final OrderBO result = processOrderService.processOrder(order);
			LOG.info("womProcessOrder(...): Order Result BO :: " + result);
			LOG.info("womProcessOrder(...): Order Result: SAP Confirmation #: " + result.getSapConfirmationNumber()
					+ ", Master Order Number: " + result.getMstrOrdNbr());
			LOG.info("womProcessOrder(...): Order Result: orderSavedButNoDeliveryCreatedFlag: " + result.isOrderSavedButNoDeliveryCreatedFlag());
			setOrderDetails(cartModel.getCode(), result.getSapConfirmationNumber(), moNumber);
			model.addAttribute("sapOrderCode", result.getSapConfirmationNumber());
			model.addAttribute("orderSavedButNoDeliveryCreatedFlag", result.isOrderSavedButNoDeliveryCreatedFlag());
			getSessionService().setAttribute("sapOrderCode", result.getSapConfirmationNumber());
			getSessionService().setAttribute("orderSavedButNoDeliveryCreatedFlag", result.isOrderSavedButNoDeliveryCreatedFlag());
			globalTempDataForSavePdf.setSapOrderCode(result.getMstrOrdNbr());
		}
	}

	private UserAccountBO getUserAccount(final UserModel user) {
		final String soldToAcc = (String) getSessionService().getAttribute(
				WebConstants.SOLDTO_ACCOUNT_ID);
		FMCustomerAccountModel fmCustomerAccModel = (FMCustomerAccountModel) companyB2BCommerceService
				.getUnitForUid(soldToAcc);
		final String shipToAcc = (String) getSessionService().getAttribute(
				WebConstants.SHIPTO_ACCOUNT_ID);
		final FMCustomerAccountModel sfmCustomerAccModel = (FMCustomerAccountModel) companyB2BCommerceService
				.getUnitForUid(shipToAcc);

		final UserAccountBO userAcct = new UserAccountBO();
		final String loggedUserType = (String) getSessionService()
				.getAttribute("logedUserType");
		LOG.info("getUserAccount(...): loggedUserType ==>" + loggedUserType);
		if (loggedUserType != null && "ShipTo".equalsIgnoreCase(loggedUserType)) {
			fmCustomerAccModel = sfmCustomerAccModel;
			userAcct.setLoggedInAsBillto(false);
		} else {
			userAcct.setLoggedInAsBillto(true);
		}
		LOG.info("getUserAccount(...): user.getName() -->" + user.getName());
		userAcct.setUserID(user.getName());
		userAcct.setAccountCode(fmCustomerAccModel.getNabsAccountCode());
		AuditInterceptor.setUserAccount(userAcct);
		return userAcct;
	}

	private BillToAcctBO getBillTOAccount() {
		final BillToAcctBO billToAcct = new BillToAcctBO();

		final String soldToAcc = (String) getSessionService().getAttribute(
				WebConstants.SOLDTO_ACCOUNT_ID);
		final FMCustomerAccountModel fmCustomerAccModel = (FMCustomerAccountModel) companyB2BCommerceService
				.getUnitForUid(soldToAcc);

		billToAcct.setAccountCode(fmCustomerAccModel.getNabsAccountCode());// soldToUnit.getNabsAccountCode());
		final SapAcctBO sapAccount = new SapAcctBO();
		sapAccount.setSapAccountCode(fmCustomerAccModel.getUid());

		final CustomerSalesOrgBO custSalesOrg = new CustomerSalesOrgBO();
		custSalesOrg.setDistributionChannel(fmCustomerAccModel
				.getDistributionChannel());
		custSalesOrg.setDivision(fmCustomerAccModel.getDivision());

		final SalesOrganizationBO salesOrg = new SalesOrganizationBO();
		salesOrg.setCode(fmCustomerAccModel.getSalesorg());
		custSalesOrg.setSalesOrganization(salesOrg);
		sapAccount.setCustomerSalesOrganization(custSalesOrg);
		billToAcct.setSapAccount(sapAccount);
		return billToAcct;
	}

	private ShipToAcctBO getShiptoAccount(final B2BUnitData shipToUnit,
			final String pkupShippingType) {
		final String shipToAcc = (String) getSessionService().getAttribute(
				WebConstants.SHIPTO_ACCOUNT_ID);
		final FMCustomerAccountModel fmCustomerAccModel = (FMCustomerAccountModel) companyB2BCommerceService
				.getUnitForUid(shipToAcc);

		final ShipToAcctBO aShipTo = new ShipToAcctBO();
		aShipTo.setAccountName(shipToUnit.getName());
		aShipTo.setAccountCode(fmCustomerAccModel.getNabsAccountCode());// shipToUnit.getUid());

		final SapAcctBO sapAccount = new SapAcctBO();
		sapAccount.setSapAccountCode(fmCustomerAccModel.getUid());
		aShipTo.setSapAccount(sapAccount);

		AddressModel addres = fmCustomerAccModel.getBillingAddress();
		for (final AddressModel address : fmCustomerAccModel.getAddresses()) {
			addres = address;
		}

		final String pickUpStore = getSessionService().getAttribute(
				"pickUpStore");
		final AddressData storeAddress = (AddressData) getSessionService()
				.getAttribute("tscaddress");

		LOG.info("getShiptoAccount(...): pickUpStore Ship to :: Flag"
				+ pickUpStore);
		if (pickUpStore != null && "true".equalsIgnoreCase(pickUpStore)
				&& storeAddress != null
				&& pkupShippingType.equalsIgnoreCase("pickup")) {
			LOG.info("getShiptoAccount(...): Store Pickup Started Ship to");
			final AddressBO tscAddress = new AddressBO();
			tscAddress.setCity(storeAddress.getTown());
			tscAddress.setAddrLine1(storeAddress.getLine1());
			tscAddress.setAddrLine2(storeAddress.getLine2());
			tscAddress.setZipOrPostalCode(storeAddress.getPostalCode());
			tscAddress.setStateOrProv(storeAddress.getRegion().getName());
			final CountryBO country = new CountryBO();
			country.setIsoCountryCode(storeAddress.getCountry().getIsocode());
			tscAddress.setCountry(country);
			aShipTo.setAddress(tscAddress);
		} else {
			final AddressBO anAddress = new AddressBO();
			anAddress.setCity(addres.getTown());
			anAddress.setAddrLine1(addres.getLine1());
			anAddress.setAddrLine2(addres.getLine2());
			anAddress.setZipOrPostalCode(addres.getPostalcode());
			anAddress.setStateOrProv(addres.getRegion().getName());
			final CountryBO country = new CountryBO();
			country.setIsoCountryCode(addres.getCountry().getIsocode());
			anAddress.setCountry(country);
			aShipTo.setAddress(anAddress);
		}

		return aShipTo;
	}

	private ManualShipToAddressBO getManualShipToAddress(
			final AddressData manualShipToAddress) {
		final ManualShipToAddressBO manualAddress = new ManualShipToAddressBO();
		manualAddress.setName(manualShipToAddress.getFirstName());
		final AddressBO manualShipTo = new AddressBO();
		manualShipTo.setCity(manualShipToAddress.getTown());
		manualShipTo.setAddrLine1(manualShipToAddress.getLine1());
		manualShipTo.setAddrLine2(manualShipToAddress.getLine2());
		manualShipTo.setZipOrPostalCode(manualShipToAddress.getPostalCode());
		manualShipTo.setStateOrProv(manualShipToAddress.getRegion()
				.getIsocode().split("-")[1]);
		final CountryBO country = new CountryBO();
		country.setIsoCountryCode(manualShipToAddress.getRegion().getIsocode()
				.split("-")[0]);
		manualShipTo.setCountry(country);
		manualAddress.setAddress(manualShipTo);
		return manualAddress;
	}

	private QuantityBO getDefaultQuanity(final int quantity) {
		final QuantityBO qty = new QuantityBO();
		qty.setRequestedQuantity(quantity);
		return qty;
	}

	private List<ItemBO> getECCItemsList(final AbstractOrderEntryModel entry,
			final ItemBO item, final String shipToCountry,
			final CartModel cartModel) {

		LOG.info("Entering 'getECCItemsList(...)'");
		final List<ItemBO> eccItemsList = new ArrayList<ItemBO>();

		if (entry.getDistrubtionCenter() != null
				&& entry.getDistrubtionCenter().size() > 0
				&& !"PICKUP".equalsIgnoreCase(cartModel.getFmordertype())) {

			LOG.info("getECCItemsList(...): Creating Header record for Part # " + item.getPartNumber());
			final ItemBO anHeader = new PartBO();
			anHeader.setDisplayPartNumber(item.getDisplayPartNumber());
			anHeader.setPartNumber(item.getPartNumber());
			anHeader.setComment("This is The Header Record :: " + item.getPartNumber());
			((PartBO) anHeader).setHeader(true);
			((PartBO) anHeader).setProcessOrderLineNumber(entry.getEntryNumber());
			anHeader.setItemQty(getDefaultQuanity(entry.getQuantity().intValue()));
			anHeader.setExternalSystem(ExternalSystem.EVRST);
			anHeader.setLineNumber(linenumber++);

			LOG.info("getECCItemsList(...): Setting Inventory for Header record for Part # " + item.getPartNumber());

			// *** Set Inventory for Header record - BEGIN: ***
			// This is needed for Emergency Orders because GATP Proposal may have been overridden.
			// SAP looks for the Plant Id (i.e. DC Code) in the Header record in that case.
			// Also note that more than one Inventory Location may be selected from GATP Proposal,
			// while ONLY ONE Inventory Location can be selected when GATP Proposal is overridden.
			// Let's select the Inventory Location only when the Inventory Location list size is one,
			// to be safe!!
			List<FMDistrubtionCenterModel> dcModels = entry.getDistrubtionCenter();
			if (dcModels != null && dcModels.size() == 1) {
				FMDistrubtionCenterModel dcModel = dcModels.get(0);
				List<InventoryBO> hdrInventoryList = new ArrayList<InventoryBO>();
				InventoryBO hdrInventory = new InventoryBO();
				DistributionCenterBO hdrDc = new DistributionCenterBO();
				hdrDc.setCode(dcModel.getDistrubtionCenterDataCode());
				hdrDc.setAvailabilityDate(dcModel.getAvailableDate());
				hdrDc.setEmergencyCutoffTime(getCutoffTime(
						dcModel.getDistrubtionCenterDataCode()).getTime());

				hdrInventory.setDistributionCenter(hdrDc);
				hdrInventory.setAvailableQty(Integer.parseInt(dcModel.getAvailableQTY()));
				hdrInventory.setSelectedLocation(true);
				hdrInventoryList.add(hdrInventory);
				anHeader.setInventory(hdrInventoryList);
			}
			// *** Set Inventory for Header record - END ***

			LOG.info("getECCItemsList(...): Adding Header record for Part # "
					+ item.getPartNumber() + " to eccItemsList");
			eccItemsList.add(anHeader);

			for (final FMDistrubtionCenterModel dcs : entry.getDistrubtionCenter()) {

				final ItemBO anItem = new PartBO();
				anItem.setDisplayPartNumber(item.getDisplayPartNumber());
				anItem.setPartNumber(item.getPartNumber());
				anItem.setAaiaBrand(item.getAaiaBrand());
				anItem.setLineNumber(linenumber);
				anItem.setScacCode("UPS-REG");
				anItem.setExternalSystem(ExternalSystem.EVRST);
				((PartBO) anItem).setProcessOrderLineNumber(entry.getEntryNumber());

				if (dcs.getBackorderFlag() != null
						&& "nothing".equalsIgnoreCase(dcs.getBackorderFlag())) {
					anItem.setItemQty(getDefaultQuanity(entry.getQuantity().intValue()));
					anItem.setBackorderPolicy(BackOrderPolicy.BACKORDER_ALL);
				} else if (dcs.getBackorderFlag() != null
						&& "partial".equalsIgnoreCase(dcs.getBackorderFlag())) {
					anItem.setItemQty(getDefaultQuanity(entry.getQuantity().intValue()));
					anItem.setBackorderPolicy(BackOrderPolicy.SHIP_AND_BACKORDER);
				} else {
					anItem.setItemQty(getDefaultQuanity(Integer.parseInt(dcs.getAvailableQTY())));
				}

				final InventoryBO inventory = new InventoryBO();
				final DistributionCenterBO dc = new DistributionCenterBO();
				dc.setCode(dcs.getDistrubtionCenterDataCode());
				dc.setAvailabilityDate(dcs.getAvailableDate());
				dc.setEmergencyCutoffTime(getCutoffTime(dcs.getDistrubtionCenterDataCode()).getTime());
				dc.setFreightCost(dcs.getFreightCost());
				dc.setFreightCostCurrencyCode(dcs.getFreightCostCurrencyCode());

				inventory.setDistributionCenter(dc);
				inventory.setAvailableQty(Integer.parseInt(dcs.getAvailableQTY()));
				// inventory.setAssignedQty(Integer.parseInt(dcs.getAvailableQTY()));
				inventory.setAssignedQty(0);
				inventory.setSelectedLocation(true);

				LOG.info("getECCItemsList(...): Getting FMDCShippingModel for "
						+ "DC Code '" + dcs.getDistrubtionCenterDataCode()
						+ "', Carrier Name: '" + dcs.getCarrierName()
						+ "', Shipping Method: '" + dcs.getShippingMethod()
						+ "', shipToCountry: '" + shipToCountry.toUpperCase()
						+ "'");
				final FMDCShippingModel shipMethod = fmDistrubtionCenterFacade
						.getShippingMethod(dcs.getDistrubtionCenterDataCode(),
								dcs.getCarrierName(), dcs.getShippingMethod(),
								shipToCountry.toUpperCase());

				final ShippingCodeBO shippingCode = new ShippingCodeBO();
				final SapShippingCodeBO sapShippingCode = new SapShippingCodeBO();

				LOG.info("getECCItemsList(...): else Shipmethod Carrier Code :: " + shipMethod.getCARRIER_CD());
				LOG.info("getECCItemsList(...): else Shipmethod Carrier name :: " + shipMethod.getCARRIER_NAME());
				sapShippingCode.setCarrierCode(shipMethod.getSAP_CARRIER_CD());

				sapShippingCode.setIncoTerms(shipMethod.getINCO_TERMS());
				sapShippingCode.setRoute(shipMethod.getROUTE());
				shippingCode.setSapShippingCode(sapShippingCode);
				inventory.setShippingCode(shippingCode);

				anItem.addInventory(inventory);
				eccItemsList.add(anItem);
				linenumber++;
			}
		} else { // Order Type = 'Pickup' OR entry.getDistrubtionCenter() is null or empty
			final String pickUpStore = getSessionService().getAttribute("pickUpStore");
			if (pickUpStore != null && "true".equalsIgnoreCase(pickUpStore)) {
				final TSCLocationModel store = (TSCLocationModel) getSessionService().getAttribute("storeDetail");

				LOG.info("getECCItemsList(...): pickUpStore :: Flag " + pickUpStore);
				LOG.info("getECCItemsList(...): Store Pickup Started");
				final String tscCode = store.getCode();
				final ItemBO anHeader = new PartBO();
				anHeader.setDisplayPartNumber(item.getDisplayPartNumber());
				anHeader.setPartNumber(item.getPartNumber());
				((PartBO) anHeader).setHeader(true);
				anHeader.setComment("This is The Header Record :: " + item.getPartNumber());
				((PartBO) anHeader).setProcessOrderLineNumber(entry.getEntryNumber());
				anHeader.setItemQty(getDefaultQuanity(entry.getQuantity().intValue()));
				anHeader.setExternalSystem(ExternalSystem.EVRST);
				anHeader.setLineNumber(linenumber++);

				if (tscCode != null && (!tscCode.isEmpty())) {
					final InventoryBO anInventory = new InventoryBO();
					anInventory.setAvailableQty(Long.valueOf(entry.getQuantity()).intValue());
					anInventory.setAssignedQty(Long.valueOf(entry.getQuantity()).intValue());
					anInventory.setSelectedLocation(true);

					final DistributionCenterBO distCenter = new DistributionCenterBO();
					distCenter.setCode(tscCode);

					anInventory.setDistributionCenter(distCenter);
					// anInventory.setShippingCode(getTSCPickUpShippingCode());
					anHeader.addInventory(anInventory);
				}

				eccItemsList.add(anHeader);

				final ItemBO anItem = new PartBO();
				anItem.setDisplayPartNumber(item.getDisplayPartNumber());
				anItem.setPartNumber(item.getPartNumber());
				anItem.setAaiaBrand(item.getAaiaBrand());
				anItem.setLineNumber(linenumber);
				anItem.setScacCode("UPS-REG");
				((PartBO) anItem).setProcessOrderLineNumber(entry.getEntryNumber());
				anItem.setItemQty(getDefaultQuanity(Long.valueOf(entry.getQuantity()).intValue()));
				anItem.setExternalSystem(ExternalSystem.EVRST);

				final InventoryBO inventory = new InventoryBO();
				inventory.setAvailableQty(Long.valueOf(entry.getQuantity()).intValue());
				inventory.setAssignedQty(Long.valueOf(entry.getQuantity()).intValue());
				LOG.info("getECCItemsList(...): TSC Code :: " + tscCode);
				final DistributionCenterBO tscCenter = new DistributionCenterBO();
				tscCenter.setCode(tscCode);
				tscCenter.setAvailabilityDate(GregorianCalendar.getInstance().getTime());
				tscCenter.setEmergencyCutoffTime(GregorianCalendar.getInstance().getTime());

				inventory.setDistributionCenter(tscCenter);

				// Mahaveer - Added for 3 more shipping
				final String pkupShippingOption = getSessionService().getAttribute("shipingOption");
				final String tscShipingMethod = ((null != pkupShippingOption && pkupShippingOption
						.equalsIgnoreCase("delivery")) ? "PKDELI"
						: (null != pkupShippingOption && pkupShippingOption
								.equalsIgnoreCase("shipfrom")) ? "FMSTD"
								: "PKUP");
				LOG.info("getECCItemsList(...): TSC Shipping Method Option : " + pkupShippingOption);

				final FMDCShippingModel tscShipMethod = fmDistrubtionCenterFacade
						.getShippingMethod(tscCode, "OTH", tscShipingMethod,
								shipToCountry.toUpperCase());

				final FMDistrubtionCenterModel availableDC = fmDistrubtionCenterFacade
						.getDistrubtionCenterData("DS-" + cartModel.getCode()
								+ "-" + entry.getEntryNumber() + "-"
								+ linenumber);
				if (availableDC != null) {
					modelService.remove(availableDC.getPk());
				}

				final FMDistrubtionCenterModel dcModel = modelService.create(FMDistrubtionCenterModel.class);
				dcModel.setCode("DS-" + cartModel.getCode() + "-" + entry.getEntryNumber() + "-" + linenumber);
				dcModel.setDistrubtionCenterDataCode(tscShipMethod.getDIST_CNTR_CD());
				dcModel.setDistrubtionCenterDataName(tscShipMethod.getDIST_CNTR_NAME());
				dcModel.setRawpartNumber(item.getPartNumber());
				dcModel.setAvailableQTY(String.valueOf(entry.getQuantity()).toString());
				dcModel.setCarrierName(siteConfigService.getString("fm.pickup.carrier.code", "OTH"));
				dcModel.setCarrierDispName(siteConfigService.getString(
						"fm.pickup.carrier.name", "Others"));
				if (null != pkupShippingOption
						&& pkupShippingOption.equalsIgnoreCase("delivery")) {
					dcModel.setShippingMethod(siteConfigService.getString(
							"fm.pickup.shipmthd.delivery.code", "PKDELI"));
					dcModel.setShippingMethodName(siteConfigService.getString(
							"fm.pickup.shipmthd.delivery.name",
							"Truck Delivery"));
				} else if (null != pkupShippingOption
						&& pkupShippingOption.equalsIgnoreCase("shipfrom")) {
					dcModel.setShippingMethod(siteConfigService.getString(
							"fm.pickup.shipmthd.shipfrom.code", "FMSTD"));
					dcModel.setShippingMethodName(siteConfigService.getString(
							"fm.pickup.shipmthd.shipfrom.name",
							"FM Standard Shipping"));
				} else {
					dcModel.setShippingMethod(siteConfigService.getString(
							"fm.pickup.shipmthd.pkup.code", "PKUP"));
					dcModel.setShippingMethodName(siteConfigService.getString(
							"fm.pickup.shipmthd.pkup.name", "Customer Pickup"));
				}

				modelService.save(dcModel);
				setDistrubtionCenter(dcModel.getCode(), cartModel.getCode(), entry.getEntryNumber());

				final ShippingCodeBO tscShippingCode = new ShippingCodeBO();
				final SapShippingCodeBO tscSAPShippingCode = new SapShippingCodeBO();
				LOG.info("getECCItemsList(...): TSC Shipmethod Carrier Code :: "
						+ tscShipMethod.getSAP_CARRIER_CD());
				tscSAPShippingCode.setCarrierCode(tscShipMethod.getSAP_CARRIER_CD());
				tscSAPShippingCode.setIncoTerms(tscShipMethod.getINCO_TERMS());
				tscSAPShippingCode.setRoute(tscShipMethod.getROUTE());
				tscShippingCode.setSapShippingCode(tscSAPShippingCode);
				inventory.setShippingCode(tscShippingCode);
				inventory.setSelectedLocation(true);
				anItem.addInventory(inventory);
				eccItemsList.add(anItem);
				linenumber++;
			}
		}
		return eccItemsList;
	}

	private List<ItemBO> getNABSItemsList(final AbstractOrderEntryModel entry,
			final ItemBO item, final String shipToCountry) {
		final List<ItemBO> nabsItemsList = new ArrayList<ItemBO>();
		if (entry.getDistrubtionCenter() != null
				&& entry.getDistrubtionCenter().size() > 0) {
			for (final FMDistrubtionCenterModel dcs : entry
					.getDistrubtionCenter()) {
				final ItemBO anItem = new PartBO();
				anItem.setDisplayPartNumber(item.getDisplayPartNumber());
				anItem.setPartNumber(item.getPartNumber());
				anItem.setLineNumber(linenumber);
				anItem.setExternalSystem(ExternalSystem.NABS);
				anItem.setProductFlag(item.getProductFlag());
				anItem.setBrandState(item.getBrandState());

				if (dcs.getBackorderFlag() != null
						&& "nothing".equalsIgnoreCase(dcs.getBackorderFlag())) {
					anItem.setItemQty(getDefaultQuanity(entry.getQuantity()
							.intValue()));
					anItem.setBackorderPolicy(BackOrderPolicy.BACKORDER_ALL);
				} else if (dcs.getBackorderFlag() != null
						&& "partial".equalsIgnoreCase(dcs.getBackorderFlag())) {
					anItem.setItemQty(getDefaultQuanity(entry.getQuantity()
							.intValue()));
					anItem.setBackorderPolicy(BackOrderPolicy.SHIP_AND_BACKORDER);
				} else {
					anItem.setItemQty(getDefaultQuanity(Integer.parseInt(dcs
							.getAvailableQTY())));
				}

				final FMDCShippingModel shipMethod = fmDistrubtionCenterFacade
						.getShippingMethod(dcs.getDistrubtionCenterDataCode(),
								dcs.getCarrierName(), dcs.getShippingMethod(),
								shipToCountry.toUpperCase());

				final DistributionCenterBO distrCenter = new DistributionCenterBO();
				distrCenter.setDistCenterId(Long.parseLong(shipMethod
						.getDIST_CNTR_ID()));
				distrCenter.setCode(dcs.getDistrubtionCenterDataCode());
				distrCenter.setName(shipMethod.getDIST_CNTR_NAME());
				distrCenter.setAvailabilityDate(dcs.getAvailableDate());
				distrCenter.setEmergencyCutoffTime(getCutoffTime(
						dcs.getDistrubtionCenterDataCode()).getTime());

				final InventoryBO inventory = new InventoryBO();
				inventory.setDistributionCenter(distrCenter);
				inventory.setSelectedLocation(true);
				inventory.setDistCntrId(distrCenter.getDistCenterId());
				inventory.setAvailableQty(Integer.parseInt(dcs
						.getAvailableQTY()));
				inventory
						.setAssignedQty(Integer.parseInt(dcs.getAvailableQTY()));
				inventory.setSelectedLocation(true);

				final ShippingCodeBO shippingCode = new ShippingCodeBO();
				final NabsShippingCodeBO nabsShippingCode = new NabsShippingCodeBO();
				nabsShippingCode.setStockShippingCode(shipMethod
						.getSTOCK_SHIP_CD());
				nabsShippingCode.setEmergencyShippingCode(shipMethod
						.getEMERG_SHIP_CD());

				shippingCode.setNabsShippingCode(nabsShippingCode);
				inventory.setShippingCode(shippingCode);

				anItem.addInventory(inventory);
				nabsItemsList.add(anItem);
				linenumber++;
			}
		}

		return nabsItemsList;
	}

	private void setOrderPODetails(final String orderCode,
			final String poNumber, final String orderInstruction) {
		// final AbstractOrderModel abstractOrderModel =
		// getAbstractOrderForCode(orderCode);
		final AbstractOrderModel abstractOrderModel = b2bOrderService
				.getAbstractOrderForCode(orderCode);
		// validateParameterNotNull(abstractOrderModel,
		// String.format("Order %s does not exist", orderCode));
		abstractOrderModel.setCustponumber(poNumber);
		abstractOrderModel.setOrdercomments(orderInstruction);
		modelService.save(abstractOrderModel);
	}

	private void setOrderDetails(final String orderCode, final String sapOrder,
			final String nabsOrder) {
		final AbstractOrderModel abstractOrderModel = b2bOrderService
				.getAbstractOrderForCode(orderCode);
		abstractOrderModel.setSapordernumber(sapOrder);
		abstractOrderModel.setPurchaseOrderNumber(nabsOrder);
		modelService.save(abstractOrderModel);
	}

	private void getDistrubtionCenter(final CartData cartData,
			final Model model, String shipToZipOrPostalCode) {
		LOG.info("getDistrubtionCenter(...): shipToZipOrPostalCode: "
				+ shipToZipOrPostalCode);
		String shipToLatLong = "";
		double shipToLatitude = 0.00;
		double shipToLongitude = 0.00;
		try {
			shipToLatLong = FMUtils
					.getLocationByLocationQuery(shipToZipOrPostalCode);
			shipToLatitude = Double.parseDouble(FMUtils.extractMiddle(
					shipToLatLong, "\"lat\" :", ","));
			shipToLongitude = Double.parseDouble(FMUtils.extractMiddle(
					shipToLatLong, "\"lng\" :"));
			LOG.info("getDistrubtionCenter(...): shipToLatLong: "
					+ shipToLatLong);
			LOG.info("getDistrubtionCenter(...): shipToLatitude: "
					+ shipToLatitude + ", shipToLongitude: " + shipToLongitude);
		} catch (IOException ioe) {
			// ???
		}

		final List<String> distrubtionCenterData = new ArrayList<String>();
		Map<Integer, Long> entryNumberToQtyMap = new HashMap<Integer, Long>();
		Long totalQtyAcrossDCsForAndEntry = 0L;
		if (cartData.getEntries() != null && !cartData.getEntries().isEmpty()) {
			for (final OrderEntryData entry : cartData.getEntries()) {
				totalQtyAcrossDCsForAndEntry = 0L;
				if (entry.getDistrubtionCenter() != null
						&& !entry.getDistrubtionCenter().isEmpty()) {
					for (final DistrubtionCenterData dcentry : entry
							.getDistrubtionCenter()) {
						LOG.info("getDistrubtionCenter(...): DC Entry - Code and Name: "
								+ dcentry.getDistrubtionCenterDataCode() + " - " 
								+ dcentry.getDistrubtionCenterDataName());
						final FMDCCenterModel dcdetails = fmDistrubtionCenterFacade
								.getCutOffTimeForDC(dcentry
										.getDistrubtionCenterDataCode());
						LOG.info("getDistrubtionCenter(...): dcdetails.getLatitude(): "
								+ dcdetails.getLatitude()
								+ ", dcdetails.getLongitude(): "
								+ dcdetails.getLongitude());
						final double distance = round(FMUtils.distanceTo(
								dcdetails.getLatitude(),
								dcdetails.getLongitude(), shipToLatitude,
								shipToLongitude));
						dcentry.setLatitude(dcdetails.getLatitude());
						dcentry.setLongitude(dcdetails.getLongitude());
						dcentry.setDistance((new Double(distance)).intValue());
						distrubtionCenterData.add(dcentry
								.getDistrubtionCenterDataCode()
								+ "-"
								// + getDCDateformat(dcentry.getAvailableDate()
								// != null ? dcentry.getAvailableDate() : new
								// Date()) + "_"
								+ getDCDateformat(new Date())
								+ "_"
								+ dcentry.getDistrubtionCenterDataName()
								+ "|"
								+ (distance > 0 ? distance
										: DISTANCE_NOT_AVAILABLE));
						totalQtyAcrossDCsForAndEntry += Long.parseLong(dcentry.getAvailableQTY());
					}
					entryNumberToQtyMap.put(entry.getEntryNumber(), totalQtyAcrossDCsForAndEntry);
				}
			}
		}
		
		// For Emergency Order Type, update Requested Qty based on Total Available Qty for selected DCs.
		LOG.info("getDistrubtionCenter(...): Calling 'updateRequestedQtyBasedOnTotalAvailableQty(...)'");
		updateRequestedQtyBasedOnTotalAvailableQty(entryNumberToQtyMap);

		final ArrayList<String> distrubtionCenterList = removeDuplicates(distrubtionCenterData);
		model.addAttribute("dc", distrubtionCenterList);
	}

	/**
	 * For Emergency Order Type, if Total Available Qty across selected DCs is less than the Requested Qty
	 * for an item, set the Requested Qty to the Total Available Qty.
	 * @param cartData
	 * @param entryNumberToQtyMap
	 */
	private void updateRequestedQtyBasedOnTotalAvailableQty(Map<Integer, Long> entryNumberToQtyMap) {
		CartModel cartModel = cartService.getSessionCart();
		if (cartModel.getEntries() != null && !cartModel.getEntries().isEmpty() 
				&& OrderType.EMERGENCY.name().equals(cartModel.getFmordertype().toUpperCase()) 
				&& entryNumberToQtyMap != null && entryNumberToQtyMap.size() > 0) {
			for (final AbstractOrderEntryModel entry : cartModel.getEntries()) {
				Long totalAvailableQty = entryNumberToQtyMap.get(entry.getEntryNumber());
				if (totalAvailableQty != null && entry.getQuantity() > totalAvailableQty) {
					LOG.info("updateRequestedQtyBasedOnTotalAvailableQty(...): Updating Requested Qty for Cart Entry # " 
								+ entry.getEntryNumber() + " from " + entry.getQuantity() + " to "
								+ totalAvailableQty);
					entry.setQuantity(totalAvailableQty);
				}
			}
			modelService.save(cartModel);
		}
	}
	
	public ArrayList<String> removeDuplicates(final List<String> list) {
		final ArrayList<String> result = new ArrayList<>();

		final HashSet<String> set = new HashSet<>();

		for (final String item : list) {
			if (!set.contains(item)) {
				result.add(item);
				set.add(item);
			}
		}
		return result;
	}

	public String getDCDateformat(final Date date) {
		final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String rdate = null;
		try {
			rdate = sdf.format(date);
		} catch (final Exception e) {
			rdate = sdf.format(new Date());
		}
		return rdate;
	}

	private String getPONumber() {
		final GetMasterOrderNumberAction masterOrderNumAction = new GetMasterOrderNumberAction();
		masterOrderNumAction.setCallingSystem(null);
		final String poNumber = masterOrderNumAction.executeAction();
		return poNumber;
	}

	private String getmasterOrderNumber() {
		final OrderASUSCanImpl processOrderService = new OrderASUSCanImpl();
		return processOrderService.getMasterOrderNumber();
	}

	public BusinessProcessService getBusinessProcessService() {
		return (BusinessProcessService) Registry.getApplicationContext()
				.getBean("businessProcessService");
	}

	@RequestMapping(method = RequestMethod.GET, value = "/carrierAccCode/{carrierAccCode}")
	public void setcarrierAccountCode(final Model model,
			@PathVariable("carrierAccCode") final String carrierAccCode) {
		LOG.info("Inside setcarrierAccountCode Method");
		final FMCustomerData fmCustomerData = fmCustomerFacade
				.getCurrentFMCustomer();
		LOG.info("UNIT ID" + fmCustomerData.getFmunit().getUid());
		final FMCustomerAccountModel fmCustomerAccModel = (FMCustomerAccountModel) companyB2BCommerceService
				.getUnitForUid(fmCustomerData.getFmunit().getUid());
		fmCustomerAccModel.setCarrierAccountCode(carrierAccCode);
		final String newCarrierAccCode = fmCustomerAccModel
				.getCarrierAccountCode();
		LOG.info("new carrierAccCode" + newCarrierAccCode);
		getSessionService().setAttribute("carrierAccCode", newCarrierAccCode);
		modelService.save(fmCustomerAccModel);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/carrier/{dc}")
	public String getCarrierName(final Model model,
			@PathVariable("dc") final String dccode) {
		LOG.info("Inside Add getCarrierName Method");
		final List<String> carrier = fmDistrubtionCenterFacade
				.getCarrierName(dccode);
		model.addAttribute("carrierSize", carrier.size());
		model.addAttribute("carrier", carrier);
		model.addAttribute("defaultCarrierCollect", siteConfigService
				.getString("fm.default.carrier.collect", "UPS"));
		model.addAttribute("defaultCACarrierCollect", siteConfigService
				.getString("fm.default.ca.carrier.collect", "UPS"));
		return "pages/fm/ajax/orderDetails";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/shipmethod/{dc}/{carriercode}/{shipToCountry}")
	public String getShipMethod(final Model model,
			@PathVariable("carriercode") final String carriercode,
			@PathVariable("dc") final String dccode,
			@PathVariable("shipToCountry") final String shipToCountry) {
		LOG.info("Inside Add getShipMethod Method");
		final List<String> shipMethod = fmDistrubtionCenterFacade
				.getShippingMethodName(carriercode, dccode, shipToCountry);
		model.addAttribute("shipMethodSize", shipMethod.size());
		model.addAttribute("shipmethod", shipMethod);
		return "pages/fm/ajax/orderDetails";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/shippingmethod/{dc}/{carriercode}/{shipcode}/{carrieracccode}/{carriername}/{shipname}")
	public String saveShipMethod(final Model model,
			@PathVariable("dc") final String dccode,
			@PathVariable("carriercode") final String carriercode,
			@PathVariable("shipcode") final String shipcode,
			@PathVariable("carrieracccode") final String carrieracccode,
			@PathVariable("carriername") final String carriername,
			@PathVariable("shipname") final String shipname) {
		LOG.info("Inside 'saveShipMethod' Method");
		LOG.info("saveShipMethod(...): dccode: " + dccode + ", carriercode: "
				+ carriercode + ", shipcode: " + shipcode
				+ ", carrieracccode: " + carrieracccode);
		LOG.info("saveShipMethod(...): carriername: " + carriername
				+ ", shipname: " + shipname);

		final CartModel cartModel = cartService.getSessionCart();
		if (cartModel.getEntries() != null && !cartModel.getEntries().isEmpty()) {
			for (final AbstractOrderEntryModel entry : cartModel.getEntries()) {
				if (entry.getDistrubtionCenter() != null
						&& !entry.getDistrubtionCenter().isEmpty()) {
					for (final FMDistrubtionCenterModel dcentry : entry
							.getDistrubtionCenter()) {
						if (dccode.equalsIgnoreCase(dcentry
								.getDistrubtionCenterDataCode())) {
							LOG.info("Inside the DC Model For saveShipMethod()");
							dcentry.setCarrierName(carriercode);
							dcentry.setShippingMethod(shipcode);
							dcentry.setCarrierAccountCode(carrieracccode);
							dcentry.setCarrierDispName(carriername);
							dcentry.setShippingMethodName(shipname);
							modelService.save(dcentry);
						}
					}
				}
			}
		}

		model.addAttribute("saveShippingMethod", "Saved");

		// Recalculate Estimated Shipping Cost.
		recalculateEstimatedShippingCost();
		
		// Check if the logged-in user is of type 'ShipTo'.
		if (getSessionService().getAttribute("logedUserType").equals("ShipTo")) {
			model.addAttribute("loggedInUserType", "ShipTo");
		} else {
			model.addAttribute("loggedInUserType", "SoldTo");
		}

		// Add the DC Code as a model attribute.
		model.addAttribute("dcCode", dccode);

		Map<String, BigDecimal> costTypeToCostMap = getUpdatedCostsMap(dccode);
		// Add the contents of the map as model attributes.
		Set<String> costTypeKeys = costTypeToCostMap.keySet();
		for (String costTypeKey : costTypeKeys) {
			model.addAttribute(costTypeKey, costTypeToCostMap.get(costTypeKey));
		}

		return "pages/fm/ajax/deliveryMethodCosts";
	}

	private Calendar getCutoffTime(final String code) {
		final Calendar calculationTime = GregorianCalendar.getInstance();
		final FMDCCenterModel dcdetails = fmDistrubtionCenterFacade
				.getCutOffTimeForDC(code);
		final Date dcCutoffTime = dcdetails.getEmergencyCutOffTime();
		final Calendar dcCutoffTimeCalendar = GregorianCalendar.getInstance();
		dcCutoffTimeCalendar.setTime(dcCutoffTime);
		final int cutoffHourOfDay = dcCutoffTimeCalendar
				.get(Calendar.HOUR_OF_DAY);
		final int cutoffMinute = dcCutoffTimeCalendar.get(Calendar.MINUTE);

		LOG.info("getCutoffTime(...): cutoffHourOfDay :: " + cutoffHourOfDay);
		LOG.info("getCutoffTime(...): cutoffMinute :: " + cutoffMinute);
		final Calendar cutoffTimeCal = new GregorianCalendar(
				calculationTime.get(GregorianCalendar.YEAR),
				calculationTime.get(GregorianCalendar.MONTH),
				calculationTime.get(GregorianCalendar.DAY_OF_MONTH),
				cutoffHourOfDay, cutoffMinute);
		LOG.info("getCutoffTime(...): DC CutoffTimeCal :: "
				+ cutoffTimeCal.getTime());
		return cutoffTimeCal;
	}

	protected OrderEntryData getOrderEntryData(final long quantity,
			final String productCode, final Integer entryNumber) {
		final OrderEntryData orderEntry = new OrderEntryData();
		orderEntry.setQuantity(quantity);
		orderEntry.setProduct(new ProductData());
		orderEntry.getProduct().setCode(productCode);
		orderEntry.setEntryNumber(entryNumber);

		return orderEntry;
	}

	private CartData setPriceType(final CartData cartData) {
		final List<ItemBO> itemBOSession = getSessionService().getAttribute(
				"itemBO");
		final List<OrderEntryData> entryies = new ArrayList<OrderEntryData>();
		boolean nabsPart = true;
		String nabsCode = null;
		String displayPartNumber = null;
		for (final OrderEntryData entry : cartData.getEntries()) {
			final FMPartModel productModel = partService.getPartForCode(entry
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

			for (final ItemBO resolvedItem : itemBOSession) {
				displayPartNumber = resolvedItem.getDisplayPartNumber();
				if ((resolvedItem.getExternalSystem() != null && resolvedItem
						.getExternalSystem().equals(ExternalSystem.NABS))
						|| (null != resolvedItem.getProductFlag())) {
					displayPartNumber = resolvedItem.getProductFlag()
							+ resolvedItem.getDisplayPartNumber();
				}
				if (resolvedItem.getPartNumber().equalsIgnoreCase(
						entry.getProduct().getCode())
						|| resolvedItem.getDisplayPartNumber()
								.equalsIgnoreCase(entry.getProduct().getCode())
						|| (displayPartNumber.equalsIgnoreCase(nabsCode))
						|| displayPartNumber.equalsIgnoreCase(entry
								.getProduct().getCode())) {
					if (resolvedItem.getItemPrice() != null) {
						final PriceBO priceBO = resolvedItem.getItemPrice();
						if (priceBO != null && priceBO.getPriceType() != null) {
							if ("WD1".equalsIgnoreCase(priceBO.getPriceType()
									.toString())) {
								entry.setPriceType("D");
							} else if ("JBR".equalsIgnoreCase(priceBO
									.getPriceType().toString())) {
								entry.setPriceType("J");
							} else {
								entry.setPriceType(priceBO.getPriceType()
										.toString());
							}
						}
					}
				}
			}
			entryies.add(entry);
		}

		cartData.setEntries(entryies);
		return cartData;
	}

	@RequireHardLogIn
	@RequestMapping(value = "/orderConfirmationExport", method = RequestMethod.GET)
	public ModelAndView downloadPdf(final Model model)
			throws CMSItemNotFoundException {
		LOG.info("PURCHASE ORDER NUMBER"
				+ globalTempDataForSavePdf.getOrderData()
						.getPurchaseOrderNumber());
		LOG.info("PURCHASE ORDER NUMBER"
				+ globalTempDataForSavePdf.getOrderData().getFmordertype());
		// List a = new ArrayList();
		LOG.info("FROM GLOBAL ordercode"
				+ globalTempDataForSavePdf.getOrderData().getCode());
		LOG.info("FROM GLOBAL soldtounit"
				+ globalTempDataForSavePdf.getSoldToUnit().getUid());
		LOG.info("FROM GLOBAL shiptounit"
				+ globalTempDataForSavePdf.getShipToUnit().getUid());
		LOG.info("FROM GLOBAL shiptoaddress"
				+ globalTempDataForSavePdf.getShipToAddress().getLine1());
		LOG.info("FROM GLOBAL shiptoaddress"
				+ globalTempDataForSavePdf.getShipToAddress().getCompanyName());
		LOG.info("FROM GLOBAL soldtoaddress"
				+ globalTempDataForSavePdf.getSoldToAddress().getLine1());
		LOG.info("FROM GLOBAL soldtoaddress"
				+ globalTempDataForSavePdf.getSoldToAddress().getCompanyName());
		// return new ModelAndView("orderConfirmPDFView",
		// "shippedOrderDetailsBO", globalOrderData);
		return new ModelAndView("orderConfirmPDFView", "globalData",
				globalTempDataForSavePdf);
	}

	private boolean partModification(final List<ItemBO> ineventoryList) {
		boolean isPartModified = false;
		try {
			for (final ItemBO resolvedItem : ineventoryList) {
				CartData cartData = cartFacade.getSessionCart();
				if (cartData.getEntries() != null
						&& !cartData.getEntries().isEmpty()) {
					for (final OrderEntryData entry : cartData.getEntries()) {
						isPartModified = false;
						String code = entry.getProduct().getCode();
						boolean catalogProduct = true;
						final FMPartModel productModel = partService
								.getPartForCode(entry.getProduct().getCode());
						String dispPart = productModel.getPartNumber();
						for (final FMCorporateModel corp : productModel
								.getCorporate()) {
							// dummy(NABS) Product is not allowed for TSC orders
							// so we are deleting from the cart if its contain
							// product flag is null.
							if (("FELPF".equalsIgnoreCase(corp.getCorpcode())
									|| "SPDPF".equalsIgnoreCase(corp
											.getCorpcode()) || "SLDPF"
										.equalsIgnoreCase(corp.getCorpcode()))
									|| ("dummy".equalsIgnoreCase(corp
											.getCorpcode()) && null != productModel
											.getFlagcode())) {
								catalogProduct = false;
								entry.setQuantity((long) (0));
								final CartModificationData cartModification = b2bCartFacade
										.updateOrderEntry(entry);
								LOG.info("partModification(...): Cart Modification message"
										+ cartModification.getStatusMessage());
								isPartModified = true;

							}

						}

						if (!catalogProduct
								&& productModel.getFlagcode() != null) {
							code = productModel.getFlagcode().toUpperCase()
									+ productModel.getRawPartNumber();
							dispPart = productModel.getFlagcode().toUpperCase()
									+ productModel.getPartNumber();
						}

						String displayPartNumber = resolvedItem
								.getDisplayPartNumber();
						if ((resolvedItem.getExternalSystem() != null && resolvedItem
								.getExternalSystem()
								.equals(ExternalSystem.NABS))
								|| (null != resolvedItem.getProductFlag())) {
							displayPartNumber = resolvedItem.getProductFlag()
									+ resolvedItem.getDisplayPartNumber();
						}
						if (resolvedItem.getPartNumber().equalsIgnoreCase(
								entry.getProduct().getCode())
								|| displayPartNumber.equalsIgnoreCase(entry
										.getProduct().getCode())
								|| (code.equalsIgnoreCase(displayPartNumber))
								|| (dispPart
										.equalsIgnoreCase(displayPartNumber))) {
							if (resolvedItem.getInventory() != null
									&& resolvedItem.getInventory().size() > 0
									&& !isPartModified) {
								final List<InventoryBO> inventorys = resolvedItem
										.getInventory();
								for (final InventoryBO inventory : inventorys) {
									if (inventory.getAvailableQty() < resolvedItem
											.getItemQty()
											.getRequestedQuantity()) {
										entry.setQuantity((long) (inventory
												.getAvailableQty()));
										final CartModificationData cartModification = b2bCartFacade
												.updateOrderEntry(entry);
										LOG.info("Cart Modification message"
												+ cartModification
														.getStatusMessage());
										isPartModified = true;
									}
								}
							}
						}
					}
				}

			}
		} catch (final Exception e) {
			LOG.error("partModification(...): Exception::" + e.getMessage());
			e.printStackTrace();
		}
		LOG.info("partModification(...): removePart :: isTSCPartModified ==>" + isPartModified);
		return isPartModified;
	}

	private void setDistrubtionCenter(final String dccode,
			final String orderCode, final Integer entrynumber) {
		final AbstractOrderModel abstractOrderModel = b2bOrderService.getAbstractOrderForCode(orderCode);
		final FMDistrubtionCenterModel dcModel = fmDistrubtionCenterFacade.getDistrubtionCenterData(dccode);
		validateParameterNotNull(abstractOrderModel,
				String.format("Order %s does not exist", orderCode));
		boolean dcContains = false;
		for (final AbstractOrderEntryModel abstractOrderEntry : abstractOrderModel.getEntries()) {
			if (entrynumber.intValue() == abstractOrderEntry.getEntryNumber()
					.intValue()) {
				final List<FMDistrubtionCenterModel> dcmodels = new ArrayList<FMDistrubtionCenterModel>(
						abstractOrderEntry.getDistrubtionCenter());

				for (final FMDistrubtionCenterModel dcs : dcmodels) {
					if (dcs.getCode().equalsIgnoreCase(dcModel.getCode())) {
						dcContains = true;
					}
				}
				if (!dcContains) {
					dcmodels.add(dcModel);
					abstractOrderEntry.setDistrubtionCenter(dcmodels);
					modelService.save(abstractOrderEntry);
				}
			}
		}
		modelService.save(abstractOrderModel);
	}

	private void getShippingCost(AddressData shipToAddress, CartData cartData) {

		/**
		 * Start UPS Shipping Webservice invocation code
		 */

		try {
			Map<String, UPSForm> UPSMap = (HashMap<String, UPSForm>) populateUPSForm(shipToAddress, cartData);
			Map<String, List<String>> mapOfDCShippingCost = calculateEstShippingCost(UPSMap);

			updateCartModelWithEstShippingCost(mapOfDCShippingCost);
			
		} catch (final Exception e) {
			LOG.error("getShippingCost(...): While calculating shipping cost received UPS WebService Error:: " + e.getMessage());
			e.printStackTrace();
		}

		/**
		 * End UPS Shipping Webservice invocation code
		 */
	}

	public static double round(double d) {
		int r = (int) Math.round(d * 100);
		double f = r / 100.0;
		return f;
	}

	protected Map<String, UPSForm> populateUPSForm(AddressData shipToAddress, CartData cartData) {
		final List<ItemBO> items = getSessionService().getAttribute("itemBO");

		LOG.info("populateUPSForm(...): --------------------------------------------------------");
		LOG.info("populateUPSForm(...): ");
		Map<String, UPSForm> upsDataMap = new HashMap<String, UPSForm>();
		
		for (OrderEntryData orderData : cartData.getEntries()) {
			for (ItemBO item : items) {
				LOG.info("populateUPSForm(...): orderData.getProduct().getRawPartNumber(): " + orderData.getProduct().getRawPartNumber());
				if (items != null && item.getDisplayPartNumber().equals(orderData.getProduct().getRawPartNumber())) {
					LOG.info("populateUPSForm(...): item.getDisplayPartNumber(): " + item.getDisplayPartNumber());
					if (orderData != null && orderData.getDistrubtionCenter() != null && orderData.getDistrubtionCenter().size() > 0) {
						for (DistrubtionCenterData dcData : orderData.getDistrubtionCenter()) {
							LOG.info("populateUPSForm(...): ********** DC Code: " + dcData.getDistrubtionCenterDataCode() + 
										" - " + dcData.getDistrubtionCenterDataName() +
										", Carrier Name: " + dcData.getCarrierName() + " **********");
							if (CARRIER_CODE_UPS.equals(dcData.getCarrierName()) && (!dcData.getShippingMethodName().trim().toLowerCase().contains(LOWER_SHIP_METHOD_NAME_COLLECT))) {

								UPSForm upsForm = new UPSForm();
								upsForm.setShipFromAddress(getShipFromAddress(dcData.getDistrubtionCenterDataCode()));
								upsForm.setShipToAddress(shipToAddress);
								upsForm.setShippingMethod(dcData.getShippingMethod());
								LOG.info("populateUPSForm(...): Ship TO Address::: address line 1: " + shipToAddress.getLine1()
										+ ", city: " + shipToAddress.getTown()
										+ ", state: " + shipToAddress.getRegion().getIsocodeShort()
										+ ", postalCode: " + shipToAddress.getPostalCode()
										+ ", country: " + shipToAddress.getRegion().getCountryIso());

								Long qty = orderData.getQuantity();
								double singleItemweight = item.getItemWeight().getWeight();
								double totalItemWeight = qty * singleItemweight;
								LOG.info("populateUPSForm(...): totalItemWeight: " + totalItemWeight);

								if (upsDataMap.containsKey(dcData.getDistrubtionCenterDataCode())) { // DC already present in the map via some other item!!
									LOG.info("populateUPSForm(...): Inside 'if' block");
									Double weight = upsDataMap.get(dcData.getDistrubtionCenterDataCode()).getItemWeight();
									weight = weight + totalItemWeight;
									upsForm.setItemWeight(weight);
									upsForm.setDcCode(dcData.getDistrubtionCenterDataCode());
									upsForm.setDcName(dcData.getDistrubtionCenterDataName());
									upsDataMap.put(dcData.getDistrubtionCenterDataCode(), upsForm);

								} else { // DC not present in the map
									LOG.info("populateUPSForm(...): Inside 'else' block");
									upsForm.setDcCode(dcData.getDistrubtionCenterDataCode());
									upsForm.setDcName(dcData.getDistrubtionCenterDataName());
									upsForm.setItemWeight(totalItemWeight);
									upsDataMap.put(dcData.getDistrubtionCenterDataCode(), upsForm);

								}
							}
						}
					} else {
						LOG.info("populateUPSForm(...): orderData is null OR orderData.getDistrubtionCenter() is null or empty");
					}
					LOG.info("populateUPSForm(...): upsDataMap size: " + upsDataMap.size());
					LOG.info("populateUPSForm(...): ");
				}
			}
		}
		LOG.info("populateUPSForm(...): --------------------------------------------------------");
		return upsDataMap;
	}

	protected AddressData getShipFromAddress(String dcCode) {
		AddressData addressData = new AddressData();
		RegionData region = new RegionData();
		CountryData country = new CountryData();
		final FMDCCenterModel dcdetails = fmDistrubtionCenterFacade.getCutOffTimeForDC(dcCode);
		addressData.setLine1(dcdetails.getAddressLine1());
		addressData.setTown(dcdetails.getCity());
		region.setIsocodeShort(dcdetails.getState());
		region.setCountryIso(dcdetails.getIsoCountryCode());
		country.setIsocode(dcdetails.getIsoCountryCode());
		addressData.setRegion(region);
		addressData.setPostalCode(dcdetails.getZipCode().toString());
		LOG.info("getShipFromAddress(): Ship From Address::: address line 1: " + addressData.getLine1() + ", city: " + addressData.getTown()
				+ ", state: " + addressData.getRegion().getIsocodeShort() + ", postalCode: " + addressData.getPostalCode() +
				", country: " + addressData.getRegion().getCountryIso());
		return addressData;
	}

	private Map<String, List<String>> calculateEstShippingCost(Map<String, UPSForm> UPSMap) {
		LOG.info("calculateEstShippingCost(...): --------------------------------------------------------");
		LOG.info("calculateEstShippingCost(...): ");
		String estimatedShippingCost = null;
		double finalEstimatedShippingCost = 0;
		Map<String, List<String>> mapOfDCShippingCost = new HashMap<String, List<String>>();
		List<String> shipValues = null;
		String currencyCode = "USD";

		for (String key : UPSMap.keySet()) {
			UPSForm upsForm = UPSMap.get(key);
			LOG.info("calculateEstShippingCost(...): ********** DC Code - Name: " + key + " - " + upsForm.getDcName() + " - BEGIN: **********");
			LOG.info("calculateEstShippingCost(...): UPS Ship FROM Address: " + upsForm.getShipFromAddress().getLine1()
					+ ", city: " + upsForm.getShipFromAddress().getTown()
					+ ", state: " + upsForm.getShipFromAddress().getRegion().getIsocodeShort()
					+ ", postalCode: " + upsForm.getShipFromAddress().getPostalCode()
					+ ", country: " + upsForm.getShipFromAddress().getRegion().getCountryIso());

			LOG.info("calculateEstShippingCost(...): ");
			LOG.info("calculateEstShippingCost(...): UPS Ship TO Address: " + upsForm.getShipToAddress().getLine1() 
					+ ", city:" + upsForm.getShipToAddress().getTown()  
					+ ", state:" + upsForm.getShipToAddress().getRegion().getIsocodeShort() 
					+ ", postalCode: " + upsForm.getShipToAddress().getPostalCode() 
					+ ", country: " + upsForm.getShipToAddress().getRegion().getCountryIso());

			RateRequest rateRequest = populateRateRequest(upsForm);

			UPSSecurity upsSecurity = populateUPSSecurity();
			RateResponse rateResponse = null;
			LOG.info("calculateEstShippingCost(...): just before calling fmDistrubtionCenterFacade");
			rateResponse = fmDistrubtionCenterFacade.getEstimatedShippingCost(rateRequest, upsSecurity);
			if (rateResponse == null) {
				LOG.info("calculateEstShippingCost(...): rateResponse is: " + rateResponse);
				return mapOfDCShippingCost;
			}

			RatedShipmentType[] ratedShipment = rateResponse.getRatedShipment();
			LOG.info("calculateEstShippingCost(...): rated shipment size: " + ratedShipment.length);
			LOG.info("calculateEstShippingCost(...): Total Shipment Charges: "
					+ ratedShipment[0].getTotalCharges().getMonetaryValue());
			estimatedShippingCost = ratedShipment[0].getTotalCharges().getMonetaryValue();
			LOG.info("calculateEstShippingCost(...): Currency Code: " + ratedShipment[0].getTotalCharges().getCurrencyCode());
			currencyCode = ratedShipment[0].getTotalCharges().getCurrencyCode();
			String serviceCode = getShippingServiceCode(upsForm.getShippingMethod());
			if (serviceCode.equals(CARRIER_UPS_1DA) || serviceCode.equals(CARRIER_UPS_2DA)
					|| serviceCode.equals(CARRIER_UPS_NDA_SAVER) || serviceCode.equals(CARRIER_UPS_NDA_EARLY)
					|| serviceCode.equals(CARRIER_UPS_2DA_AM)) {
				double shippingCost = Double.valueOf(estimatedShippingCost);
				double FMdiscount = (shippingCost * 40) / 100;
				LOG.info("calculateEstShippingCost(...): 40% FMdiscount applied: " + FMdiscount);
				finalEstimatedShippingCost = shippingCost - FMdiscount;
				finalEstimatedShippingCost = round(finalEstimatedShippingCost);
				LOG.info("calculateEstShippingCost(...): Final Shipping Cost after FM discount: "
						+ finalEstimatedShippingCost);
			} else {
				double shippingCost = Double.valueOf(estimatedShippingCost);
				double FMdiscount = (shippingCost * 20) / 100;
				LOG.info("calculateEstShippingCost(...): 20% FMdiscount applied:" + FMdiscount);
				finalEstimatedShippingCost = shippingCost - FMdiscount;
				finalEstimatedShippingCost = round(finalEstimatedShippingCost);
				LOG.info("calculateEstShippingCost(...): Final Shipping Cost after FM discount: "
						+ finalEstimatedShippingCost);
			}
			if (getDayofWeek() == 7) {
				finalEstimatedShippingCost = finalEstimatedShippingCost + 16.0;
				LOG.info("calculateEstShippingCost(...): Today is Saturday. So, adding $16.0 to final shipping cost: "
						+ finalEstimatedShippingCost);
			}
			LOG.info("calculateEstShippingCost(...): DC Code - Name: " + key + " - " + upsForm.getDcName()
					+ ":  Final estimatedShippingCost: " + finalEstimatedShippingCost);
			shipValues = new ArrayList<String>();
			shipValues.add(currencyCode);
			shipValues.add(Double.toString(finalEstimatedShippingCost));
			mapOfDCShippingCost.put(key, shipValues);
			LOG.info("calculateEstShippingCost(...): ********** DC Code - Name: " + key + " - " + upsForm.getDcName() + " - END **********");
			LOG.info("calculateEstShippingCost(...): ");
		}
		LOG.info("calculateEstShippingCost(...): --------------------------------------------------------");

		return mapOfDCShippingCost;
	}

	public RateRequest populateRateRequest(UPSForm form) {
		RateRequest rateRequest = new RateRequest();
		RequestType request = new RequestType();
		String[] requestOption = { "rate" };
		request.setRequestOption(requestOption);
		rateRequest.setRequest(request);

		ShipmentType shpmnt = new ShipmentType();

		/** *******Shipper *********************/
		ShipperType shipper = new ShipperType();
		AddressType shipperAddress = new AddressType();
		String[] addressLines = { form.getShipFromAddress().getLine1() };
		shipperAddress.setAddressLine(addressLines);
		shipperAddress.setCity(form.getShipFromAddress().getTown());
		shipperAddress.setPostalCode(form.getShipFromAddress().getPostalCode());
		shipperAddress.setStateProvinceCode(form.getShipFromAddress().getRegion().getIsocodeShort());
		shipperAddress.setCountryCode("US");
		shipper.setAddress(shipperAddress);
		shpmnt.setShipper(shipper);
		/** ******Shipper **********************/

		/** ************ShipFrom *******************/
		ShipFromType shipFrom = new ShipFromType();
		ShipAddressType shipFromAddress = new ShipAddressType();
		shipFromAddress.setAddressLine(addressLines);

		shipFromAddress.setCity(form.getShipFromAddress().getTown());
		shipFromAddress.setPostalCode(form.getShipFromAddress().getPostalCode());
		shipFromAddress.setStateProvinceCode(form.getShipFromAddress().getRegion().getIsocodeShort());
		shipFromAddress.setCountryCode(form.getShipFromAddress().getRegion().getCountryIso());
		shipFrom.setAddress(shipFromAddress);
		shpmnt.setShipFrom(shipFrom);
		/** ***********ShipFrom **********************/

		/** ************ShipTo *******************/
		ShipToType shipTo = new ShipToType();
		ShipToAddressType shipToAddress = new ShipToAddressType();
		String[] shipToAddressLines = { form.getShipToAddress().getLine1() };
		shipToAddress.setAddressLine(shipToAddressLines);
		shipToAddress.setCity(form.getShipToAddress().getTown());
		shipToAddress.setPostalCode(form.getShipToAddress().getPostalCode());
		shipToAddress.setStateProvinceCode(form.getShipToAddress().getRegion().getIsocodeShort());
		shipToAddress.setCountryCode(form.getShipToAddress().getRegion().getCountryIso());
		shipTo.setAddress(shipToAddress);
		shpmnt.setShipTo(shipTo);
		/** ***********ShipTo **********************/

		/********** Service********************** */
		CodeDescriptionTypeE service = new CodeDescriptionTypeE();
		LOG.info("populateRateRequest(...): Shipping Service Method: "
				+ getShippingServiceCode(form.getShippingMethod()) + " "
				+ form.getShippingMethod());
		service.setCode(getShippingServiceCode(form.getShippingMethod().trim().toUpperCase()));
		shpmnt.setService(service);
		/** ********Service ***********************/

		/******************** Package***************** */
		PackageType pkg1 = new PackageType();
		CodeDescriptionTypeE pkgingType = new CodeDescriptionTypeE();
		pkgingType.setCode("02");
		pkgingType.setDescription("Package");
		pkg1.setPackagingType(pkgingType);
		PackageWeightType pkgWeight = new PackageWeightType();
		CodeDescriptionTypeE UOMType = new CodeDescriptionTypeE();
		UOMType.setCode("lbs");
		UOMType.setDescription("Pounds");
		pkgWeight.setUnitOfMeasurement(UOMType);
		LOG.info("populateRateRequest(...): Shipping Weight: " + form.getItemWeight().toString());
		pkgWeight.setWeight(form.getItemWeight().toString());
		pkg1.setPackageWeight(pkgWeight);
		PackageType[] pkgArray = { pkg1 };
		shpmnt.setPackage(pkgArray);
		/******************** Package ******************/
		rateRequest.setShipment(shpmnt);
		return rateRequest;
	}

	public String getShippingServiceCode(String shippingMthod) {
		switch (shippingMthod) {
			case "1DA":
				return CARRIER_UPS_1DA;
			case "2DA":
				return CARRIER_UPS_2DA;
			case "GRD":
				return CARRIER_UPS_GROUND;
			case "3DS":
				return CARRIER_UPS_3DS;
			case "1DP":
				return CARRIER_UPS_NDA_SAVER;
			case "1DM":
				return CARRIER_UPS_NDA_EARLY;
			case "2DM":
				return CARRIER_UPS_2DA_AM;
		}
		// default is ground
		return CARRIER_UPS_GROUND;
	}

	private static UPSSecurity populateUPSSecurity() {

		UPSSecurity upss = new UPSSecurity();
		ServiceAccessToken_type0 upsSvcToken = new ServiceAccessToken_type0();
		upsSvcToken.setAccessLicenseNumber(Config.getParameter("accessKey"));
		upss.setServiceAccessToken(upsSvcToken);
		UsernameToken_type0 upsSecUsrnameToken = new UsernameToken_type0();
		upsSecUsrnameToken.setUsername(Config.getParameter("username"));
		// System.out.println("user:"+props.getProperty(USER_NAME));
		upsSecUsrnameToken.setPassword(Config.getParameter("password"));
		upss.setUsernameToken(upsSecUsrnameToken);

		return upss;
	}

	private int getDayofWeek() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		LOG.info("getDayofWeek(): dateFormat.format(date): " + dateFormat.format(date));
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day_of_week = c.get(Calendar.DAY_OF_WEEK);
		LOG.info("getDayofWeek(): c.getTime(): " + c.getTime());
		LOG.info("getDayofWeek(): day_of_week: " + day_of_week);
		return day_of_week;
	}

	private void recalculateEstimatedShippingCost() {
		
		CartData cartData = getCheckoutFlowFacade().getCheckoutCart();
		final UserModel user = userService.getCurrentUser();
		final boolean isUserAnonymous = user == null || userService.isAnonymousUser(user);
		AddressData shipToAddress = new AddressData();
		B2BUnitData shipToUnit = null;
		if (!isUserAnonymous) {
			shipToUnit = companyB2BCommerceFacade
					.getUnitForUid((String) getSessionService()
							.getAttribute(WebConstants.SHIPTO_ACCOUNT_ID));

			for (final AddressData shipToAddresss : shipToUnit.getAddresses()) {
				shipToAddress = shipToAddresss;
			}

			final AddressData manualShipToAddress = (AddressData) getSessionService().getAttribute("shiptToAddress");
			if (manualShipToAddress != null) {
				shipToAddress = manualShipToAddress;
			}

			getShippingCost(shipToAddress, cartData);
			cartData = getCheckoutFlowFacade().getCheckoutCart();
		}
	}

	/**
	 * Set the Estimated Shipping Cost and Currency for each DC in Cart Model.
	 */
	private void updateCartModelWithEstShippingCost(Map<String, List<String>> mapOfDCShippingCost) {

		CartModel cartModel = getSessionService().getAttribute("cart");
		List<String> freightCurrencyAndCostList = null;

		for (AbstractOrderEntryModel entry : cartModel.getEntries()) {
			List<FMDistrubtionCenterModel> dcModels = new ArrayList<FMDistrubtionCenterModel>();

			for (FMDistrubtionCenterModel dc : entry.getDistrubtionCenter()) {
				if (!mapOfDCShippingCost.isEmpty()) {
					if (mapOfDCShippingCost.containsKey(dc.getDistrubtionCenterDataCode())) {
						freightCurrencyAndCostList = mapOfDCShippingCost.get(dc.getDistrubtionCenterDataCode());
						dc.setFreightCostCurrencyCode(freightCurrencyAndCostList.get(0));
						dc.setFreightCost(new BigDecimal(freightCurrencyAndCostList.get(1)));
					} else {
						dc.setFreightCostCurrencyCode("USD");
						dc.setFreightCost(new BigDecimal(0.00));
					}
				} else {
					dc.setFreightCostCurrencyCode("USD");
					dc.setFreightCost(new BigDecimal(0.00));
				}
				modelService.save(dc);
				dcModels.add(dc);
			}
			entry.setDistrubtionCenter(dcModels);
			modelService.save(entry);
		}
		modelService.save(cartModel);			
	}
	
	private Map<String, BigDecimal> getUpdatedCostsMap(String dcCode) {

		BigDecimal totalItemPriceForDC = BigDecimal.ZERO;
		BigDecimal totalFreightCostForDC = BigDecimal.ZERO;
		BigDecimal totalDCValue = BigDecimal.ZERO;
		BigDecimal totalItemPriceForAllDCs = BigDecimal.ZERO;
		BigDecimal totalFreightCostForAllDCs = BigDecimal.ZERO;
		BigDecimal totalOrderValue = BigDecimal.ZERO;

		int qty = 0;
		BigDecimal qtyBD = BigDecimal.ZERO;
		BigDecimal basePrice = BigDecimal.ZERO;
		BigDecimal itemValue = BigDecimal.ZERO;
		
		totalFreightCostForDC.setScale(2, BigDecimal.ROUND_HALF_UP);
		totalDCValue.setScale(2, BigDecimal.ROUND_HALF_UP);
		totalItemPriceForAllDCs.setScale(2, BigDecimal.ROUND_HALF_UP);
		totalFreightCostForAllDCs.setScale(2, BigDecimal.ROUND_HALF_UP);
		totalOrderValue.setScale(2, BigDecimal.ROUND_HALF_UP);

		Set<String> dcCodesSet = new HashSet<String>();
		
		CartData cartData = getCheckoutFlowFacade().getCheckoutCart();
		
		for (OrderEntryData orderData : cartData.getEntries()) {
			if (orderData != null && orderData.getDistrubtionCenter() != null && orderData.getDistrubtionCenter().size() > 0) {
				for (DistrubtionCenterData dcData : orderData.getDistrubtionCenter()) {
					if ("nothing".equals(dcData.getBackorderFlag())) {
						qty = Integer.parseInt(dcData.getBackorderQTYAll());
					} else if ("partial".equals(dcData.getBackorderFlag())) {
						qty = Integer.parseInt(dcData.getAvailableQTY()) + Integer.parseInt(dcData.getBackorderQTY());
					} else {
						qty = Integer.parseInt(dcData.getAvailableQTY());
					}
					
					qtyBD = new BigDecimal(qty);
					basePrice = orderData.getBasePrice().getValue();
					itemValue = basePrice.multiply(qtyBD);
					itemValue = itemValue.setScale(2, BigDecimal.ROUND_HALF_UP);
					totalItemPriceForAllDCs = totalItemPriceForAllDCs.add(itemValue);
					totalItemPriceForAllDCs = totalItemPriceForAllDCs.setScale(2, BigDecimal.ROUND_HALF_UP);
					
					if (dcCode.equals(dcData.getDistrubtionCenterDataCode())) {
						totalItemPriceForDC = totalItemPriceForDC.add(itemValue);
						totalItemPriceForDC = totalItemPriceForDC.setScale(2, BigDecimal.ROUND_HALF_UP);
						// Multiple Parts may come from a single DC. So, count the Freight Cost for the passed-in DC only ONCE!!
						if (!dcCodesSet.contains(dcCode)) {
							dcCodesSet.add(dcCode);
							totalFreightCostForDC = dcData.getFreightCost();
							totalFreightCostForDC = totalFreightCostForDC.setScale(2, BigDecimal.ROUND_HALF_UP);
						}
					}
				}
			}
		}
		
		totalFreightCostForAllDCs = cartData.getDeliveryCost().getValue();
		totalFreightCostForAllDCs = totalFreightCostForAllDCs.setScale(2, BigDecimal.ROUND_HALF_UP);
		totalDCValue = totalItemPriceForDC.add(totalFreightCostForDC);
		totalDCValue = totalDCValue.setScale(2, BigDecimal.ROUND_HALF_UP);
		totalOrderValue = totalItemPriceForAllDCs.add(totalFreightCostForAllDCs);
		totalOrderValue = totalOrderValue.setScale(2, BigDecimal.ROUND_HALF_UP);

		// Put various costs into a map.
		Map<String, BigDecimal> costTypeToCostMap = new HashMap<String, BigDecimal>();

		costTypeToCostMap.put("totalItemPriceForDC",       totalItemPriceForDC);
		costTypeToCostMap.put("totalFreightCostForDC",     totalFreightCostForDC);
		costTypeToCostMap.put("totalDCValue",              totalDCValue);
		costTypeToCostMap.put("totalItemPriceForAllDCs",   totalItemPriceForAllDCs);
		costTypeToCostMap.put("totalFreightCostForAllDCs", totalFreightCostForAllDCs);
		costTypeToCostMap.put("totalOrderValue",           totalOrderValue);
		
		LOG.info("getUpdatedShippingCosts(...): ---------------------------------------------");
		LOG.info("getUpdatedShippingCosts(...): dcCode: " + dcCode);
		LOG.info("getUpdatedShippingCosts(...): totalItemPriceForDC: $" + totalItemPriceForDC.doubleValue());
		LOG.info("getUpdatedShippingCosts(...): totalFreightCostForDC: $" + totalFreightCostForDC.doubleValue());
		LOG.info("getUpdatedShippingCosts(...): totalDCValue: $" + totalDCValue.doubleValue());
		LOG.info("getUpdatedShippingCosts(...): totalItemPriceForAllDCs: $" + totalItemPriceForAllDCs.doubleValue());
		LOG.info("getUpdatedShippingCosts(...): totalFreightCostForAllDCs: $" + totalFreightCostForAllDCs.doubleValue());
		LOG.info("getUpdatedShippingCosts(...): totalOrderValue: $" + totalOrderValue.doubleValue());
		LOG.info("getUpdatedShippingCosts(...): ---------------------------------------------");
		
		return costTypeToCostMap;
	}
}
