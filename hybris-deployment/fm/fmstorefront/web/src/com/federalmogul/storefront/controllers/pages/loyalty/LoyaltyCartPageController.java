/**
 * 
 */
package com.federalmogul.storefront.controllers.pages.loyalty;

import static de.hybris.platform.servicelayer.util.ServicesUtil.validateParameterNotNull;

import de.hybris.platform.acceleratorservices.config.SiteConfigService;
import de.hybris.platform.acceleratorservices.controllers.page.PageType;
import de.hybris.platform.b2b.services.B2BOrderService;
import de.hybris.platform.b2bacceleratorfacades.company.B2BCommerceUnitFacade;
import de.hybris.platform.b2bacceleratorfacades.company.B2BCommerceUserFacade;
import de.hybris.platform.b2bacceleratorfacades.company.CompanyB2BCommerceFacade;
import de.hybris.platform.b2bacceleratorfacades.order.data.B2BUnitData;
import de.hybris.platform.b2bacceleratorservices.company.CompanyB2BCommerceService;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.commercefacades.i18n.I18NFacade;
import de.hybris.platform.commercefacades.order.CartFacade;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.order.data.CartModificationData;
import de.hybris.platform.commercefacades.order.data.CartRestorationData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.product.ProductFacade;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commerceservices.customer.DuplicateUidException;
import de.hybris.platform.commerceservices.order.CommerceCartModificationException;
import de.hybris.platform.commerceservices.order.CommerceCartService;
import de.hybris.platform.commerceservices.service.data.CommerceCartParameter;
import de.hybris.platform.core.model.order.AbstractOrderEntryModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.europe1.model.PriceRowModel;
import de.hybris.platform.order.CartService;
import de.hybris.platform.order.exceptions.CalculationException;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.federalmogul.core.model.FMCustomerAccountModel;
import com.federalmogul.core.product.PartService;
import com.federalmogul.facades.account.FMCustomerFacade;
import com.federalmogul.facades.flow.impl.SessionOverrideB2BCheckoutFlowFacade;
import com.federalmogul.facades.user.data.FMCustomerData;
import com.federalmogul.falcon.core.model.FMCorporateModel;
import com.federalmogul.falcon.core.model.FMPartModel;
import com.federalmogul.storefront.annotations.RequireHardLogIn;
import com.federalmogul.storefront.breadcrumb.ResourceBreadcrumbBuilder;
import com.federalmogul.storefront.constants.WebConstants;
import com.federalmogul.storefront.controllers.ControllerConstants;
import com.federalmogul.storefront.controllers.pages.AbstractPageController;
import com.federalmogul.storefront.controllers.pages.CartPageController;
import com.federalmogul.storefront.controllers.util.GlobalMessages;
import com.federalmogul.storefront.forms.UpdateQuantityForm;
import com.fm.falcon.webservices.dto.LoyaltyHistoryRequestDTO;
import com.fm.falcon.webservices.dto.LoyaltyHistoryResponseDTO;
import com.fm.falcon.webservices.soap.helper.LoyaltyHistoryHelper;
import com.fmo.wom.business.as.ItemASUSCanImpl;
import com.fmo.wom.business.as.WOMServices;
import com.fmo.wom.common.exception.WOMBusinessDataException;
import com.fmo.wom.common.exception.WOMExternalSystemException;
import com.fmo.wom.common.exception.WOMTransactionException;
import com.fmo.wom.common.exception.WOMValidationException;
import com.fmo.wom.domain.AccountBO;
import com.fmo.wom.domain.BillToAcctBO;
import com.fmo.wom.domain.CustomerSalesOrgBO;
import com.fmo.wom.domain.ItemBO;
import com.fmo.wom.domain.PartBO;
import com.fmo.wom.domain.PriceBO;
import com.fmo.wom.domain.ProblemBO;
import com.fmo.wom.domain.QuantityBO;
import com.fmo.wom.domain.SalesOrganizationBO;
import com.fmo.wom.domain.SapAcctBO;
import com.fmo.wom.domain.ShipToAcctBO;
import com.fmo.wom.domain.enums.ExternalSystem;
import com.fmo.wom.integration.util.MessageResourceUtil;


/**
 * @author SR279690
 * 
 */
@Controller
@Scope("tenant")
@RequestMapping(value = "/loyaltycart")
@SessionAttributes(
{ "freightPrice" })
public class LoyaltyCartPageController extends AbstractPageController
{
	private static final String TYPE_MISMATCH_ERROR_CODE = "typeMismatch";
	private static final String ERROR_MSG_TYPE = "errorMsg";
	private static final String QUANTITY_INVALID_BINDING_MESSAGE_KEY = "basket.error.quantity.invalid.binding";

	private static final Logger LOG = Logger.getLogger(CartPageController.class);

	private static final String CART_CMS_PAGE = "LoyaltyCartPage";

	private static final String CONTINUE_URL = "continueUrl";
	public static final String SUCCESSFUL_MODIFICATION_CODE = "success";



	@Resource(name = "cartFacade")
	private de.hybris.platform.b2bacceleratorfacades.api.cart.CartFacade b2bCartFacade;

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

	/*
	 * @Resource(name = "variantSortStrategy") private VariantSortStrategy variantSortStrategy;
	 */

	@Resource(name = "productService")
	private ProductService productService;

	@Resource(name = "b2bProductFacade")
	private ProductFacade productFacade;

	@Autowired
	private UserService userService;


	@Resource(name = "cartFacade")
	private CartFacade cartFacade;

	@Resource
	protected FMCustomerFacade fmCustomerFacade;

	@Resource(name = "b2bCommerceFacade")
	protected CompanyB2BCommerceFacade companyB2BCommerceFacade;

	@Autowired
	protected B2BCommerceUserFacade b2bCommerceUserFacade;

	@Resource(name = "siteConfigService")
	private SiteConfigService siteConfigService;

	@Resource(name = "b2bCommerceUnitFacade")
	protected B2BCommerceUnitFacade b2bCommerceUnitFacade;

	@Resource(name = "partService")
	private PartService partService;

	@Resource
	protected CompanyB2BCommerceService companyB2BCommerceService;

	@Resource
	private B2BOrderService b2bOrderService;

	@Autowired
	private SessionService service;


	@Autowired
	private CartService cartService;

	@Autowired
	private CommerceCartService commerceCartService;


	@Resource(name = "i18NFacade")
	private I18NFacade i18NFacade;

	/**
	 * @return the service
	 */
	public SessionService getService()
	{
		return service;
	}



	/**
	 * @param service
	 *           the service to set
	 */
	public void setService(final SessionService service)
	{
		this.service = service;
	}



	@RequestMapping(method = RequestMethod.GET)
	@RequireHardLogIn
	public String showCart(final Model model) throws CMSItemNotFoundException
	{

		LOG.info("inside loyalty cartpage controller");


		prepareDataForPage(model);


		//		 final String pickUpStore = getSessionService().getAttribute("pickUpStore"); final AddressData storeAddress =
		//		 (AddressData) getSessionService().getAttribute("tscaddress"); model.addAttribute("storePickupAddress",
		//		 storeAddress); model.addAttribute("pickUpStore", pickUpStore); //FreeFreight amount change Rahul
		//		 
		//		 final FMCustomerAccountModel fmCustomerAccModel = (FMCustomerAccountModel) companyB2BCommerceService
		//		 .getUnitForUid("0010000022"); final Integer freeFrieghtAmt = fmCustomerAccModel.getFreeFreightAmt();
		//		 LOG.info("freeFrieghtAmt" + freeFrieghtAmt); model.addAttribute("freeFrieghtAmt", freeFrieghtAmt); //Mahaveer
		//		 final Credit Block model.addAttribute(WebConstants.FM_CREDIT_BLOCK,
		//		 getSessionService().getAttribute(WebConstants.FM_CREDIT_BLOCK));
		//		 model.addAttribute(WebConstants.FM_ORDER_BLOCK, getSessionService().getAttribute(WebConstants.FM_ORDER_BLOCK));
		LOG.info("######################### showCart #######################");
		return ControllerConstants.Views.Pages.Cart.FMLoyaltyCartPage;
	}

	@RequestMapping(value = "/setStoreLocation/{sname}/{addline1}/{addline2}/{town}/{region}/{country}/{pcode}", method = RequestMethod.GET)
	public String addItemToCart(@PathVariable("sname") final String sname, @PathVariable("addline1") final String addline1,
			@PathVariable("addline2") final String addline2, @PathVariable("town") final String town,
			@PathVariable("region") final String region, @PathVariable("country") final String country,
			@PathVariable("pcode") final String pcode, final Model model) throws ClassNotFoundException, SQLException,
			DuplicateUidException, CMSItemNotFoundException, IOException
	{

		LOG.info("Inside setStorePickupLocation method inside Controller");
		final AddressData storeAddress = new AddressData();
		storeAddress.setFirstName(sname);
		storeAddress.setLine1(addline1);
		if (addline2 != null && !"d".equalsIgnoreCase(addline2))
		{
			storeAddress.setLine2(addline2);
		}
		storeAddress.setTown(town);
		//getI18NFacade().getCountryForIsocode
		storeAddress.setRegion(i18NFacade.getRegion(country, region));
		storeAddress.setCountry(i18NFacade.getCountryForIsocode(country));
		storeAddress.setPostalCode(pcode);
		getService().setAttribute("pickUpStore", "true");
		getService().setAttribute("tscaddress", storeAddress);
		model.addAttribute("storePickupAddress", storeAddress);


		return "pages/fm/ajax/summaryDetails";
	}


	@RequestMapping(value = "/checkout", method = RequestMethod.GET)
	@RequireHardLogIn
	public String cartCheck(final Model model, final RedirectAttributes redirectModel) throws CommerceCartModificationException
	{
		SessionOverrideB2BCheckoutFlowFacade.resetSessionOverrides();
		LOG.info("######################### cartCheck ####################### 11");

		if (!cartFacade.hasSessionCart() || cartFacade.getSessionCart().getEntries().isEmpty())
		{
			LOG.info("######################### cartCheck ####################### 22");
			LOG.info("Missing or empty cart");

			// No session cart or empty session cart. Bounce back to the cart page.
			return REDIRECT_PREFIX + "/loyaltycart";
		}
		LOG.info("######################### cartCheck ####################### 33");
		if (validateCart(redirectModel))
		{
			LOG.info("######################### cartCheck ####################### 44");
			return REDIRECT_PREFIX + "/loyaltycart";
		}
		LOG.info("######################### cartCheck ####################### 55");
		// Redirect to the start of the checkout flow to begin the checkout process
		// We just redirect to the generic '/checkout' page which will actually select the checkout flow
		// to use. The customer is not necessarily logged in on this request, but will be forced to login
		// when they arrive on the '/checkout' page.
		return REDIRECT_PREFIX + "/checkout";
	}

	@RequestMapping(value = "/getProductVariantMatrix", method = RequestMethod.GET)
	@RequireHardLogIn
	public String getProductVariantMatrix(@RequestParam("productCode") final String productCode, final Model model)
	{
		final ProductModel productModel = productService.getProductForCode(productCode);

		final ProductData productData = productFacade.getProductForOptions(productModel, Arrays.asList(ProductOption.BASIC,
				ProductOption.CATEGORIES, ProductOption.VARIANT_MATRIX_BASE, ProductOption.VARIANT_MATRIX_PRICE,
				ProductOption.VARIANT_MATRIX_MEDIA, ProductOption.VARIANT_MATRIX_STOCK));

		model.addAttribute("product", productData);

		return ControllerConstants.Views.Fragments.Cart.ExpandGridInCart;
	}

	protected boolean validateCart(final RedirectAttributes redirectModel) throws CommerceCartModificationException
	{
		// Validate the cart
		final List<CartModificationData> modifications = cartFacade.validateCartData();
		if (!modifications.isEmpty())
		{
			redirectModel.addFlashAttribute("validationData", modifications);

			// Invalid cart. Bounce back to the cart page.
			return true;
		}
		return false;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateCartQuantities(@RequestParam("entryNumber") final Integer entryNumber,
			@RequestParam("productCode") final String productCode, final Model model, @Valid final UpdateQuantityForm form,
			final BindingResult bindingErrors) throws CMSItemNotFoundException, CommerceCartModificationException
	{
		LOG.info("################3 updateCartQuantities  #####################33");
		if (bindingErrors.hasErrors())
		{
			LOG.info("Errororrrrrrrrrrrrrr");
			getViewWithBindingErrorMessages(model, bindingErrors);
		}
		else
		{
			LOG.info("################3 updateCartQuantities  #####################form.getQuantity() " + form.getQuantity());
			LOG.info("################3 updateCartQuantities  #####################form.getQuantity() " + productCode);
			LOG.info("################3 updateCartQuantities  #####################entryNumber " + entryNumber);

			/*
			 * final CartModificationData cartModification =
			 * b2bCartFacade.updateOrderEntry(getOrderEntryData(form.getQuantity(), productCode, entryNumber));
			 */

			final CartModificationData cartModification = cartFacade.updateCartEntry(entryNumber, form.getQuantity().longValue());

			if (cartModification.getStatusCode().equals(SUCCESSFUL_MODIFICATION_CODE))
			{
				GlobalMessages.addMessage(model, GlobalMessages.CONF_MESSAGES_HOLDER, cartModification.getStatusMessage(), null);
			}
			else if (!model.containsAttribute(ERROR_MSG_TYPE))
			{
				GlobalMessages.addMessage(model, GlobalMessages.ERROR_MESSAGES_HOLDER, cartModification.getStatusMessage(), null);
			}
		}

		/*
		 * final CartData cartData = cartFacade.getSessionCart(); model.addAttribute("cartData", cartData); return
		 * ControllerConstants.Views.Pages.Cart.FMLoyaltyCartPage;
		 */

		return showCart(model);
		//loyaltycart
	}

	@SuppressWarnings("null")
	protected void createProductList(final Model model) throws CMSItemNotFoundException
	{


		LOG.info("inside loyalty createProductList method");
		int totalReedemPoints = 0;
		final FMCustomerData currentUser = fmCustomerFacade.getCurrentFMCustomer();
		final String pointsFlag = "P";
		boolean availablePointsCheck = false;

		//final CartData cartData = cartFacade.getSessionCartWithEntryOrdering(true);
		final CartData cartData = cartFacade.getSessionCart();


		reverseCartProductsOrder(cartData.getEntries());
		if (cartData.getEntries() != null && !cartData.getEntries().isEmpty())
		{
			LOG.info("inside loyalty cartData entry !null");

			for (final OrderEntryData entry : cartData.getEntries())
			{
				LOG.info("inside loyalty cartData entry !null Entry Number :: " + entry.getEntryNumber());

				entry.getProduct().setLoyaltyPoints(getLoyaltyPointsForProduct(entry.getProduct().getCode()));
				LOG.info("inside loyalty createProductList metho}}}}}" + entry.getProduct().getCode());
				LOG.info("entry.getProduct().getLoyaltyPoints()}}}}" + entry.getProduct().getLoyaltyPoints());
				totalReedemPoints = totalReedemPoints
						+ (int) ((Double.parseDouble(entry.getProduct().getLoyaltyPoints())) * entry.getQuantity());

				LOG.info("quantity" + entry.getQuantity());
				LOG.info("totalReedemPoints {{{{{{{{{{{{{{{{{{{{" + totalReedemPoints);


				final UpdateQuantityForm uqf = new UpdateQuantityForm();
				uqf.setQuantity(entry.getQuantity());
				model.addAttribute("updateQuantityForm" + entry.getEntryNumber(), uqf);
			}
		}
		availablePointsCheck = validateUserPointsToBuyProducts(currentUser, pointsFlag, totalReedemPoints, model);

		model.addAttribute("availablePointsCheck", availablePointsCheck);
		model.addAttribute("cartData", cartData);
		model.addAttribute("totalReedemPoints", totalReedemPoints);
		storeCmsPageInModel(model, getContentPageForLabelOrId(CART_CMS_PAGE));
		setUpMetaDataForContentPage(model, getContentPageForLabelOrId(CART_CMS_PAGE));
	}

	private String getLoyaltyPointsForProduct(final String productCode)
	{

		String loyaltyPoints = "";
		final ProductModel productModel = productService.getProductForCode(productCode);
		if (productModel != null)
		{
			loyaltyPoints = productModel.getLoyaltyPoints();
		}
		return loyaltyPoints;
	}

	protected boolean validateUserPointsToBuyProducts(final FMCustomerData currentUser, final String pointsFlag,
			final int redeemtionPoints, final Model model)
	{

		final LoyaltyHistoryHelper loyaltyHelper = new LoyaltyHistoryHelper();
		final LoyaltyHistoryRequestDTO requestDto = new LoyaltyHistoryRequestDTO();
		requestDto.setMembership_Id(currentUser.getCrmContactId());
		requestDto.setFlag(pointsFlag);
		LoyaltyHistoryResponseDTO responseDto = null;
		boolean userPointsAvailablityCheck = false;

		try
		{
			responseDto = (LoyaltyHistoryResponseDTO) loyaltyHelper.sendSOAPMessage(requestDto);
			final String userAvailablePoints = responseDto.getBalancePoints();
			if (userAvailablePoints != null && userAvailablePoints != "")
			{
				final int availablePoints = Integer.parseInt(userAvailablePoints);
				if (redeemtionPoints < availablePoints)
				{
					model.addAttribute("availablePoints", availablePoints);
					userPointsAvailablityCheck = true;
				}
			}

		}
		catch (final Exception ex)
		{
			userPointsAvailablityCheck = false;
			LOG.info("Exception Occured :: " + ex.getMessage());
		}



		return userPointsAvailablityCheck;
	}

	protected void reverseCartProductsOrder(final List<OrderEntryData> entries)
	{
		if (entries != null)
		{
			Collections.reverse(entries);
		}
	}

	protected void prepareDataForPage(final Model model) throws CMSItemNotFoundException
	{
		LOG.info("inside loyalty prepareDataforPage method");

		final String continueUrl = (String) getSessionService().getAttribute(WebConstants.CONTINUE_URL);
		model.addAttribute(CONTINUE_URL, (continueUrl != null && !continueUrl.isEmpty()) ? continueUrl : ROOT);


		final CartRestorationData restorationData = (CartRestorationData) sessionService
				.getAttribute(WebConstants.CART_RESTORATION);
		model.addAttribute("restorationData", restorationData);


		createProductList(model);

		//setupCartPageRestorationData(model);
		//clearSessionRestorationData();


		model.addAttribute(WebConstants.BREADCRUMBS_KEY, resourceBreadcrumbBuilder.getBreadcrumbs("Shopping Cart"));
		model.addAttribute("pageType", PageType.CART.name());

	}

	protected void setupCartPageRestorationData(final Model model)
	{
		if (getSessionService().getAttribute(WebConstants.CART_RESTORATION) != null)
		{
			if (getSessionService().getAttribute(WebConstants.CART_RESTORATION_ERROR_STATUS) != null)
			{
				model.addAttribute("restorationErrorMsg", getSessionService()
						.getAttribute(WebConstants.CART_RESTORATION_ERROR_STATUS));
			}
			else
			{
				model.addAttribute("restorationData", getSessionService().getAttribute(WebConstants.CART_RESTORATION));
			}
		}
		model.addAttribute("showModifications", Boolean.TRUE);
	}

	protected void clearSessionRestorationData()
	{
		getSessionService().removeAttribute(WebConstants.CART_RESTORATION);
		getSessionService().removeAttribute(WebConstants.CART_RESTORATION_ERROR_STATUS);
	}

	protected String getViewWithBindingErrorMessages(final Model model, final BindingResult bindingErrors)
	{
		for (final ObjectError error : bindingErrors.getAllErrors())
		{
			if (error.getCode().equals(TYPE_MISMATCH_ERROR_CODE))
			{
				model.addAttribute(ERROR_MSG_TYPE, QUANTITY_INVALID_BINDING_MESSAGE_KEY);
			}
			else
			{
				model.addAttribute(ERROR_MSG_TYPE, error.getDefaultMessage());
			}
		}
		return ControllerConstants.Views.Fragments.Cart.AddToCartPopup;
	}


	protected OrderEntryData getOrderEntryData(final long quantity, final String productCode, final Integer entryNumber)
	{

		final OrderEntryData orderEntry = new OrderEntryData();
		orderEntry.setQuantity(quantity);
		orderEntry.setProduct(new ProductData());
		orderEntry.getProduct().setCode(productCode);
		orderEntry.setEntryNumber(entryNumber);

		return orderEntry;
	}

	protected void partResolution(final Model model, final CartData cartData, final B2BUnitData shipToUnit,
			final B2BUnitData soldToUnit, final AddressData soldToAddress)
	{
		//WOM Code Integration for Part Resolution --> Mahaveer A --Start

		final ItemASUSCanImpl partResolutionservice = WOMServices.getPartResolutionService();
		if (cartData.getEntries() != null && !cartData.getEntries().isEmpty())
		{
			final List<ItemBO> multiMatchItemBO = (List<ItemBO>) getSessionService().getAttribute("multiMatchPart");
			final List<ItemBO> items = new ArrayList<ItemBO>();
			{
				for (final OrderEntryData entry : cartData.getEntries())
				{


					ItemBO multiItem = null;
					if (multiMatchItemBO != null && !multiMatchItemBO.isEmpty())
					{
						for (final ItemBO item : multiMatchItemBO)
						{
							final String displayPartNumber = (null != item.getExternalSystem() && item.getExternalSystem().equals(
									ExternalSystem.NABS)) ? item.getProductFlag() + item.getDisplayPartNumber() : item
									.getDisplayPartNumber();
							if (item.getDisplayPartNumber().equalsIgnoreCase(entry.getProduct().getCode())
									|| item.getPartNumber().equalsIgnoreCase(entry.getProduct().getCode())
									|| displayPartNumber.equalsIgnoreCase(entry.getProduct().getCode()))
							{
								multiItem = item;
								multiItem.setLineNumber(entry.getEntryNumber() + 1);
								multiItem.setItemQty(getDefaultQuanity(entry.getQuantity()));
							}
						}
					}

					boolean catalogProduct = true;
					boolean isDummyPart = false;
					final FMPartModel productModel = partService.getPartForCode(entry.getProduct().getCode());
					for (final FMCorporateModel corp : productModel.getCorporate())
					{
						if ("dummy".equalsIgnoreCase(corp.getCorpcode()) || "FELPF".equalsIgnoreCase(corp.getCorpcode())
								|| "SPDPF".equalsIgnoreCase(corp.getCorpcode()) || "SLDPF".equalsIgnoreCase(corp.getCorpcode()))
						{
							catalogProduct = false;
						}
						if ("dummy".equalsIgnoreCase(corp.getCorpcode()))
						{
							isDummyPart = true;
						}
					}
					final ItemBO anItem = new PartBO();
					String displayPartNumber = productModel.getPartNumber();
					if (isDummyPart && productModel.getProductFlag() != null && null == multiItem)
					{
						displayPartNumber = displayPartNumber.substring(1);
						System.err.println("displayPartNumber :: " + displayPartNumber);
						anItem.setProductFlag(productModel.getProductFlag());
						anItem.setExternalSystem(ExternalSystem.NABS);
					}
					else
					{
						anItem.setExternalSystem(ExternalSystem.EVRST);
					}

					anItem.setDisplayPartNumber(!catalogProduct ? displayPartNumber : productModel.getCode());
					anItem.setPartNumber(!catalogProduct ? productModel.getRawPartNumber() : productModel.getCode());
					anItem.setAaiaBrand(siteConfigService.getString("wom.item.aaiabrand", "DZH"));
					anItem.setLineNumber(entry.getEntryNumber() + 1);
					anItem.setScacCode(siteConfigService.getString("wom.item.scaccode", "UPS-REG"));
					anItem.setComment(siteConfigService.getString("wom.item.Comment", "This final is a comment"));
					anItem.setItemQty(getDefaultQuanity(entry.getQuantity()));

					if (multiItem != null)
					{
						LOG.info("multiItem ==>" + multiItem);
						items.add(multiItem);
					}
					else
					{
						items.add(anItem);
					}
				}
			}

			final String soldToAcc = (String) getSessionService().getAttribute(WebConstants.SOLDTO_ACCOUNT_ID);
			final FMCustomerAccountModel fmCustomerAccModel = (FMCustomerAccountModel) companyB2BCommerceService
					.getUnitForUid(soldToAcc);
			final String shipToAcc = (String) getSessionService().getAttribute(WebConstants.SHIPTO_ACCOUNT_ID);
			final FMCustomerAccountModel sfmCustomerAccModel = (FMCustomerAccountModel) companyB2BCommerceService
					.getUnitForUid(shipToAcc);

			//Mahaveer JOBBER Price change
			//final String loggedUserType = (String) getSessionService().getAttribute("logedUserType");
			//if (loggedUserType != null && "ShipTo".equalsIgnoreCase(loggedUserType))
			//{
			//	fmCustomerAccModel = sfmCustomerAccModel;
			//}

			final AccountBO billToAcct = new BillToAcctBO();
			billToAcct.setAccountCode(fmCustomerAccModel.getNabsAccountCode());//"28073");//Nabs Sold to account code

			final SapAcctBO sapAccount = new SapAcctBO();
			sapAccount.setSapAccountCode(fmCustomerAccModel.getUid());
			billToAcct.setSapAccount(sapAccount);

			final AccountBO shipToAcct = new ShipToAcctBO();
			shipToAcct.setAccountCode(sfmCustomerAccModel.getNabsAccountCode());//"28073");

			final CustomerSalesOrgBO custSalesOrg = new CustomerSalesOrgBO();
			custSalesOrg.setDistributionChannel(fmCustomerAccModel.getDistributionChannel());//siteConfigService.getString("wom.custSalesOrg.distributionchannel", "02"));
			custSalesOrg.setDivision(fmCustomerAccModel.getDivision());//siteConfigService.getString("wom.custSalesOrg.Division", "00"));

			final SalesOrganizationBO salesOrg = new SalesOrganizationBO();
			salesOrg.setCode(fmCustomerAccModel.getSalesorg());
			custSalesOrg.setSalesOrganization(salesOrg);
			sapAccount.setCustomerSalesOrganization(custSalesOrg);
			List<ItemBO> itemsOrderable = new ArrayList<ItemBO>();

			try
			{
				itemsOrderable = partResolutionservice.checkOrderable(cartData.getCode(), items, billToAcct, shipToAcct);
				if (itemsOrderable != null)
				{
					getSessionService().setAttribute("itemBO", itemsOrderable);
					for (final OrderEntryData entry : cartData.getEntries())
					{
						for (final ItemBO resolvedItem : itemsOrderable)
						{
							String displayPartNumber = resolvedItem.getDisplayPartNumber();
							if ((resolvedItem.getExternalSystem() != null && resolvedItem.getExternalSystem()
									.equals(ExternalSystem.NABS)) || null != resolvedItem.getProductFlag())
							{
								model.addAttribute("isNABSProduct", true);
								displayPartNumber = resolvedItem.getProductFlag() + resolvedItem.getDisplayPartNumber();
							}
							final List<ProblemBO> problemBOList = resolvedItem.getProblemItemList();
							if (resolvedItem.getPartNumber().equalsIgnoreCase(entry.getProduct().getCode())
									|| displayPartNumber.equalsIgnoreCase(entry.getProduct().getCode()))
							{
								LOG.info("Item price = " + resolvedItem.getItemPrice());
								if (resolvedItem.getItemPrice() != null)
								{
									final PriceBO priceBO = resolvedItem.getItemPrice();
									final FMPartModel productModel = partService.getPartForCode(entry.getProduct().getCode());
									createPriceRowForUser(priceBO.getDisplayPrice(), productModel);
								}
								if (resolvedItem.hasProblemItem())
								{
									for (final ProblemBO problem : problemBOList)
									{
										if (MessageResourceUtil.SOLD_IN_MULTIPLES.equals(problem.getMessageKey()))
										{

											final int qty = roundUpToMultiplesQuantity(resolvedItem.getItemQty().getRequestedQuantity(),
													resolvedItem.getItemQty().getSoldInMultipleQuantity());
											final CartModificationData cartModification = b2bCartFacade.updateOrderEntry(getOrderEntryData(
													Long.valueOf(qty).longValue(), entry.getProduct().getCode(), entry.getEntryNumber()));
											LOG.info("cartModification ==>" + cartModification);
											//entry.setQuantity(Long.valueOf(qty).longValue());
										}
									}
									boolean isPartdeleted = false;
									LOG.info("resolvedItem.getPartNumber() : " + resolvedItem.getPartNumber());
									for (final ProblemBO problem : problemBOList)
									{
										LOG.info("problem" + problem);
										if (MessageResourceUtil.NO_INVENTORY.equals(problem.getMessageKey()) && !isPartdeleted)
										{
											final CartModificationData cartModification = b2bCartFacade.updateOrderEntry(getOrderEntryData(
													Long.valueOf(0).longValue(), entry.getProduct().getCode(), null));
											LOG.info("cartModification ==>" + cartModification);
											isPartdeleted = true;
										}
										if (MessageResourceUtil.NOT_ALLOWED_TO_ORDER.equals(problem.getMessageKey()) && !isPartdeleted)
										{
											final CartModificationData cartModification = b2bCartFacade.updateOrderEntry(getOrderEntryData(
													Long.valueOf(0).longValue(), entry.getProduct().getCode(), null));
											LOG.info("cartModification ==>" + cartModification);
											isPartdeleted = true;
										}
										if (MessageResourceUtil.OBSOLETE.equals(problem.getMessageKey()) && !isPartdeleted)
										{
											final CartModificationData cartModification = b2bCartFacade.updateOrderEntry(getOrderEntryData(
													Long.valueOf(0).longValue(), entry.getProduct().getCode(), null));
											LOG.info("cartModification ==>" + cartModification);
											isPartdeleted = true;
										}
										if (MessageResourceUtil.OBSOLETE_NO_RETURN_ALLOWED.equals(problem.getMessageKey())
												&& !isPartdeleted)
										{
											final CartModificationData cartModification = b2bCartFacade.updateOrderEntry(getOrderEntryData(
													Long.valueOf(0).longValue(), entry.getProduct().getCode(), null));
											LOG.info("cartModification ==>" + cartModification);
											isPartdeleted = true;
										}
										if (MessageResourceUtil.OBSOLETE_RETURN_ALLOWED.equals(problem.getMessageKey()) && !isPartdeleted)
										{
											final CartModificationData cartModification = b2bCartFacade.updateOrderEntry(getOrderEntryData(
													Long.valueOf(0).longValue(), entry.getProduct().getCode(), null));
											LOG.info("cartModification ==>" + cartModification);
											isPartdeleted = true;
										}
										/*
										 * if (MessageResourceUtil.NO_RETURN.equals(problem.getMessageKey()) && !isPartdeleted) {
										 * final CartModificationData cartModification =
										 * b2bCartFacade.updateOrderEntry(getOrderEntryData( Long.valueOf(0).longValue(),
										 * entry.getProduct().getCode(), null)); LOG.info("cartModification ==>" +
										 * cartModification); isPartdeleted = true; }
										 */
										if (MessageResourceUtil.PART_BEING_DISCONTINUED.equals(problem.getMessageKey()) && !isPartdeleted)
										{
											final CartModificationData cartModification = b2bCartFacade.updateOrderEntry(getOrderEntryData(
													Long.valueOf(0).longValue(), entry.getProduct().getCode(), null));
											LOG.info("cartModification ==>" + cartModification);
											isPartdeleted = true;
										}
										if (MessageResourceUtil.SOLD_IN_SETS_ONLY.equals(problem.getMessageKey()) && !isPartdeleted)
										{
											final CartModificationData cartModification = b2bCartFacade.updateOrderEntry(getOrderEntryData(
													Long.valueOf(0).longValue(), entry.getProduct().getCode(), null));
											LOG.info("cartModification ==>" + cartModification);
											isPartdeleted = true;
										}
										if (MessageResourceUtil.PART_IS_PRERELEASE.equals(problem.getMessageKey()) && !isPartdeleted)
										{
											final CartModificationData cartModification = b2bCartFacade.updateOrderEntry(getOrderEntryData(
													Long.valueOf(0).longValue(), entry.getProduct().getCode(), null));
											LOG.info("cartModification ==>" + cartModification);
											isPartdeleted = true;
										}
										if (MessageResourceUtil.NO_ITEM_CATEGORY_AVAILABLE.equals(problem.getMessageKey())
												&& !isPartdeleted)
										{
											final CartModificationData cartModification = b2bCartFacade.updateOrderEntry(getOrderEntryData(
													Long.valueOf(0).longValue(), entry.getProduct().getCode(), null));
											LOG.info("cartModification ==>" + cartModification);
											isPartdeleted = true;
										}
										if (MessageResourceUtil.MISSING_PRICE.equals(problem.getMessageKey()) && !isPartdeleted)
										{
											final CartModificationData cartModification = b2bCartFacade.updateOrderEntry(getOrderEntryData(
													Long.valueOf(0).longValue(), entry.getProduct().getCode(), null));
											LOG.info("cartModification ==>" + cartModification);
											isPartdeleted = true;
										}
										if (null != problem.getStatusCategory()
												&& !"PART_FOUND".equalsIgnoreCase(problem.getStatusCategory().name()) && !isPartdeleted)
										{

											final CartModificationData cartModification = b2bCartFacade.updateOrderEntry(getOrderEntryData(
													Long.valueOf(0).longValue(), entry.getProduct().getCode(), null));
											LOG.info("cartModification ==>" + cartModification);
											isPartdeleted = true;
										}

									}
								}
								if (resolvedItem.isKit())
								{
									final CartModificationData cartModification = b2bCartFacade.updateOrderEntry(getOrderEntryData(Long
											.valueOf(0).longValue(), entry.getProduct().getCode(), null));
									LOG.info("cartModification ==>" + cartModification);
								}
							}
						}
					}

				}
			}
			catch (WOMExternalSystemException | WOMValidationException e)
			{
				LOG.error(e.getMessage());
			}
			catch (final WOMTransactionException e)
			{
				LOG.error(e.getMessage());
			}
			catch (final WOMBusinessDataException e)
			{
				LOG.error(e.getMessage());
			}
			catch (final Exception e)
			{
				model.addAttribute(ERROR_MSG_TYPE, "External Systems Are All Down. Kindly TRy After Sometime");
				GlobalMessages.addErrorMessage(model, "External System Exception :" + e.getMessage());
				LOG.error("Exception::" + e.getMessage());
			}
			recalculate();
			problemDispaly(model, itemsOrderable);

		}
		//WOM Code Integration for Part Resolution --> Mahaveer A -- End
	}

	private void problemDispaly(final Model model, final List<ItemBO> problems)
	{
		recalculate();
		final CartData cartData = cartFacade.getSessionCart();
		double freightPrice = 0.0;
		final List<OrderEntryData> entryies = new ArrayList<OrderEntryData>();
		for (final OrderEntryData entry : cartData.getEntries())
		{
			boolean catalogProduct = true;
			boolean isDummyPart = false;
			final FMPartModel productModel = partService.getPartForCode(entry.getProduct().getCode());
			for (final FMCorporateModel corp : productModel.getCorporate())
			{
				if ("dummy".equalsIgnoreCase(corp.getCorpcode()) || "FELPF".equalsIgnoreCase(corp.getCorpcode())
						|| "SPDPF".equalsIgnoreCase(corp.getCorpcode()) || "SLDPF".equalsIgnoreCase(corp.getCorpcode()))
				{
					catalogProduct = false;
				}
				if ("dummy".equalsIgnoreCase(corp.getCorpcode()))
				{
					isDummyPart = true;
				}
			}
			final String partFlag = isDummyPart ? productModel.getProductFlag() : productModel.getFlagcode();
			for (final ItemBO resolvedItem : problems)
			{
				String displayPartNumber = resolvedItem.getDisplayPartNumber();
				if (resolvedItem.getExternalSystem() != null && resolvedItem.getExternalSystem().equals(ExternalSystem.NABS))
				{
					displayPartNumber = resolvedItem.getProductFlag() + resolvedItem.getDisplayPartNumber();
				}
				final List<ProblemBO> problemBOList = resolvedItem.getProblemItemList();
				if (resolvedItem.getPartNumber().equalsIgnoreCase(entry.getProduct().getCode())
						|| displayPartNumber.equalsIgnoreCase(entry.getProduct().getCode())
						|| (!catalogProduct && productModel.getPartNumber().equalsIgnoreCase(resolvedItem.getPartNumber()) && resolvedItem
								.getProductFlag().equalsIgnoreCase(partFlag)))
				{
					if (resolvedItem.getItemPrice() != null)
					{
						final PriceBO priceBO = resolvedItem.getItemPrice();
						if (priceBO != null && priceBO.getPriceType() != null)
						{
							entry.setPriceType(priceBO.getPriceType().toString());
							freightPrice += (priceBO.getFreightPrice() * entry.getQuantity().intValue());
						}
					}

					entry.getProduct().setPartNumber(!catalogProduct ? productModel.getRawPartNumber() : productModel.getCode());
					//entry.getProduct().setRawPartNumber(resolvedItem.getPartNumber());
					if (resolvedItem.hasProblemItem() || resolvedItem.isKit())
					{
						String partProblem = null;
						if (resolvedItem.isKit())
						{
							partProblem = getMessageSource().getMessage(MessageResourceUtil.SOLD_IN_SETS_ONLY, null,
									getI18nService().getCurrentLocale());
							LOG.info("Inside isKit Con Problem::" + partProblem);
						}
						if (problemBOList != null && !problemBOList.isEmpty())
						{
							for (final ProblemBO problem : problemBOList)
							{
								LOG.info("resolvedItem : " + resolvedItem.getPartNumber() + "message :" + problem.getMessageKey());
								LOG.info("resolvedItem : " + resolvedItem.getPartNumber() + "Status :"
										+ problem.getStatusCategory().name());
								String issues = null;
								if (MessageResourceUtil.SOLD_IN_MULTIPLES.equals(problem.getMessageKey()))
								{
									final String[] parms =
									{ resolvedItem.getPartNumber(),
											String.valueOf(resolvedItem.getItemQty().getSoldInMultipleQuantity()).toString() };
									issues = getMessageSource().getMessage(problem.getMessageKey(), parms,
											getI18nService().getCurrentLocale());
								}
								if (MessageResourceUtil.MULITPLE_PARTS_FOUND.equals(problem.getMessageKey()))
								{

									issues = getMessageSource().getMessage(problem.getMessageKey(), null,
											getI18nService().getCurrentLocale());
								}
								if (MessageResourceUtil.PART_SUPERCEDED.equals(problem.getMessageKey()))
								{
									final String[] parms =
									{ resolvedItem.getDisplayPartNumber(), resolvedItem.getPartNumber() };

									issues = getMessageSource().getMessage(problem.getMessageKey(), parms,
											getI18nService().getCurrentLocale());
								}
								if (MessageResourceUtil.REASONABLE_QUANTITY_THRESHOLD.equals(problem.getMessageKey()))
								{
									issues = getMessageSource().getMessage("REASONABLE_QUANTITY_THRESHOLD_CART", null,
											getI18nService().getCurrentLocale());
								}
								if (MessageResourceUtil.NO_INVENTORY.equals(problem.getMessageKey()))
								{
									issues = getMessageSource().getMessage(problem.getMessageKey(), null,
											getI18nService().getCurrentLocale());
								}
								if (MessageResourceUtil.OBSOLETE.equals(problem.getMessageKey()))
								{
									issues = getMessageSource().getMessage(problem.getMessageKey(), null,
											getI18nService().getCurrentLocale());
								}

								if (MessageResourceUtil.OBSOLETE_NO_RETURN_ALLOWED.equals(problem.getMessageKey()))
								{
									issues = getMessageSource().getMessage(problem.getMessageKey(), null,
											getI18nService().getCurrentLocale());
								}
								if (MessageResourceUtil.OBSOLETE_RETURN_ALLOWED.equals(problem.getMessageKey()))
								{
									issues = getMessageSource().getMessage(problem.getMessageKey(), null,
											getI18nService().getCurrentLocale());
								}
								if (MessageResourceUtil.NO_RETURN.equals(problem.getMessageKey()))
								{
									issues = getMessageSource().getMessage(problem.getMessageKey(), null,
											getI18nService().getCurrentLocale());
								}
								if (MessageResourceUtil.PART_BEING_DISCONTINUED.equals(problem.getMessageKey()))
								{
									issues = getMessageSource().getMessage(problem.getMessageKey(), null,
											getI18nService().getCurrentLocale());
								}
								if (MessageResourceUtil.SOLD_IN_SETS_ONLY.equals(problem.getMessageKey()))
								{
									issues = getMessageSource().getMessage(problem.getMessageKey(), null,
											getI18nService().getCurrentLocale());
								}
								if (MessageResourceUtil.NO_ITEM_CATEGORY_AVAILABLE.equals(problem.getMessageKey()))
								{
									issues = getMessageSource().getMessage(problem.getMessageKey(), null,
											getI18nService().getCurrentLocale());
								}
								if (MessageResourceUtil.MISSING_PRICE.equals(problem.getMessageKey()))
								{
									issues = getMessageSource().getMessage(problem.getMessageKey(), null,
											getI18nService().getCurrentLocale());
								}
								if (MessageResourceUtil.PART_IS_PRERELEASE.equals(problem.getMessageKey()))
								{
									issues = getMessageSource().getMessage(problem.getMessageKey(), null,
											getI18nService().getCurrentLocale());
								}
								if (null != problem.getStatusCategory()
										&& !"PART_FOUND".equalsIgnoreCase(problem.getStatusCategory().name()))
								{

									issues = getMessageSource().getMessage(problem.getMessageKey(), null,
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

				}
			}
			entryies.add(entry);
		}
		cartData.setEntries(entryies);
		model.addAttribute("cartData", cartData);
		model.addAttribute("futureDate", getFutureDate(cartData.getCode()));
		getSessionService().setAttribute("freightPrice", freightPrice);
		model.addAttribute("freightPrice", freightPrice);

		if (cartData.getEntries() != null && !cartData.getEntries().isEmpty())
		{

			for (final OrderEntryData entry : cartData.getEntries())
			{
				final UpdateQuantityForm uqf = new UpdateQuantityForm();
				uqf.setQuantity(entry.getQuantity());
				model.addAttribute("updateQuantityForm" + entry.getEntryNumber(), uqf);
			}
		}

	}

	private QuantityBO getDefaultQuanity(final long quantity)
	{
		final int iquantity = Long.valueOf(quantity).intValue();
		final QuantityBO qty = new QuantityBO();
		qty.setRequestedQuantity(iquantity);
		return qty;
	}

	public void recalculate()
	{
		LOG.info("cart refresh");
		final UserModel user = userService.getCurrentUser();
		final CartModel cart = user.getCarts().iterator().next();
		final CommerceCartParameter parameter = new CommerceCartParameter();
		parameter.setEnableHooks(true);
		parameter.setCart(cart);
		try
		{
			commerceCartService.recalculateCart(parameter);
			LOG.info("cart refreshed");
		}
		catch (final CalculationException e)
		{
			LOG.error("CalculationException ::" + e.getMessage());
		}
	}

	public void createPriceRowForUser(final Double price, final FMPartModel part)
	{
		final UserModel user = userService.getCurrentUser();
		boolean diffPrice = true;
		final Collection<PriceRowModel> partPrices = new LinkedList<PriceRowModel>();
		final Collection<PriceRowModel> pricerowModels = part.getEurope1Prices();
		for (final PriceRowModel pricerowModel : pricerowModels)
		{
			if (pricerowModel.getUser() != null && pricerowModel.getUser().getUid().equalsIgnoreCase(user.getUid()))
			{
				if (!pricerowModel.getPrice().equals(price))
				{
					modelService.remove(pricerowModel.getPk());
				}
				else
				{
					diffPrice = false;
					LOG.info("Same Price");
				}
			}
			partPrices.add(pricerowModel);
		}
		modelService.refresh(part);
		if (diffPrice)
		{
			final PriceRowModel priceRowModel = modelService.create(PriceRowModel.class);
			priceRowModel.setProduct(part);
			priceRowModel.setCatalogVersion(part.getCatalogVersion());
			//			if (price != 0.0)
			//			{
			//				priceRowModel.setPrice(price);
			//			}
			//			else
			//			{
			//				priceRowModel.setPrice(0.0);
			//			}
			if (Double.doubleToLongBits(price) != 0)
			{
				priceRowModel.setPrice(price);
			}
			else
			{
				priceRowModel.setPrice(0.0);
			}
			priceRowModel.setNet(true);
			priceRowModel.setCurrency(commonI18NService.getBaseCurrency());
			priceRowModel.setUser(user);
			partPrices.add(priceRowModel);
			part.setEurope1Prices(partPrices);
			modelService.save(part);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/ordertype/{ordertypecode}")
	public String setOrderType(final Model model, @PathVariable("ordertypecode") final String ordertypecode)
	{
		LOG.info("Inside Add ordertype Method Method");
		final CartData cartData = cartFacade.getSessionCart();
		setOrderType(ordertypecode, cartData.getCode());
		return "pages/fm/ajax/categoryDetails";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/futuredelivery/{deliveryDate}")
	public String setDeliveryDate(final Model model, @PathVariable("deliveryDate") final String deliveryDate)
	{
		LOG.info("Inside Add ordertype Method Method");
		final CartData cartData = cartFacade.getSessionCart();
		setDeliveryDate(deliveryDate, cartData.getCode());
		return "pages/fm/ajax/categoryDetails";
	}

	private void setOrderType(final String ordertypecode, final String orderCode)
	{
		//final AbstractOrderModel abstractOrderModel = getAbstractOrderForCode(orderCode);
		final AbstractOrderModel abstractOrderModel = b2bOrderService.getAbstractOrderForCode(orderCode);
		validateParameterNotNull(abstractOrderModel, String.format("Order %s does not exist", orderCode));
		abstractOrderModel.setFmordertype(ordertypecode);
		modelService.save(abstractOrderModel);
		if (!"pickup".equalsIgnoreCase(orderCode))
		{
			getSessionService().setAttribute("pickUpStore", null);
			getSessionService().setAttribute("tscaddress", null);
		}
	}

	private void setDeliveryDate(final String deliveryDate, final String orderCode)
	{
		final AbstractOrderModel abstractOrderModel = b2bOrderService.getAbstractOrderForCode(orderCode);
		validateParameterNotNull(abstractOrderModel, String.format("Order %s does not exist", orderCode));
		try
		{
			final SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
			final Date converteddeliveryDate = sdf.parse(deliveryDate);
			for (final AbstractOrderEntryModel entry : abstractOrderModel.getEntries())
			{
				entry.setNamedDeliveryDate(converteddeliveryDate);
				modelService.save(entry);
			}
		}
		catch (final ParseException e)
		{
			LOG.error("ParseException ::" + e.getMessage());
		}
	}

	private Date getFutureDate(final String orderCode)
	{
		final AbstractOrderModel abstractOrderModel = b2bOrderService.getAbstractOrderForCode(orderCode);
		validateParameterNotNull(abstractOrderModel, String.format("Order %s does not exist", orderCode));
		try
		{
			//final SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
			for (final AbstractOrderEntryModel entry : abstractOrderModel.getEntries())
			{
				final Date deliveryDate = entry.getNamedDeliveryDate();
				if (deliveryDate != null)
				{
					return deliveryDate;
				}
			}
		}
		catch (final Exception e)
		{
			LOG.error("Exception ::" + e.getMessage());
			return null;
		}
		return null;
	}

	private int roundUpToMultiplesQuantity(final int inputQuantity, final int soldInMultiplesQuantity)
	{

		final int upMultiple = (int) Math.ceil((double) inputQuantity / (double) soldInMultiplesQuantity);
		return upMultiple * soldInMultiplesQuantity;
	}

}
