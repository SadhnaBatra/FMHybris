/**
 * 
 */
package com.federalmogul.storefront.controllers.pages.loyalty;


import de.hybris.platform.b2bacceleratorfacades.api.cart.CartFacade;
import de.hybris.platform.b2bacceleratorfacades.product.data.CartEntryData;
import de.hybris.platform.commercefacades.order.data.CartModificationData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.product.ProductFacade;
import de.hybris.platform.commercefacades.product.ProductOption;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;
import de.hybris.platform.util.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.federalmogul.storefront.controllers.AbstractController;
import com.federalmogul.storefront.controllers.ControllerConstants;
import com.federalmogul.storefront.controllers.util.GlobalMessages;
import com.federalmogul.storefront.forms.AddToCartForm;
import com.google.common.collect.Lists;



/**
 * @author SR279690
 * 
 */
@Controller
@Scope("tenant")
//@RequestMapping(value = "/**/loyaltycart")
public class LoyaltyAddToCartController extends AbstractController
{

	private static final String TYPE_MISMATCH_ERROR_CODE = "typeMismatch";
	private static final String ERROR_MSG_TYPE = "errorMsg";
	private static final String QUANTITY_INVALID_BINDING_MESSAGE_KEY = "basket.error.quantity.invalid.binding";
	private static final String SHOWN_PRODUCT_COUNT = "storefront.minicart.shownProductCount";
	public static final String SUCCESSFUL_MODIFICATION_CODE = "success";

	protected static final Logger LOG = Logger.getLogger(LoyaltyAddToCartController.class);

	@Resource(name = "cartFacade")
	private CartFacade cartFacade;

	@Resource(name = "accProductFacade")
	private ProductFacade productFacade;


	@Autowired
	private UserService userService;

	@Resource(name = "sessionService")
	private SessionService sessionService;

	@RequestMapping(value = "/loyaltycart/add", method = RequestMethod.POST, produces = "application/json")
	public String addToCart(@RequestParam("productCodePost") final String code, final Model model,
			@Valid final AddToCartForm form, final BindingResult bindingErrors)
	{
		LOG.info("inside add to cart controller");

		if (form.getQty() == 0)
		{
			form.setQty(1);
		}


		final OrderEntryData orderEntryData = getOrderEntryData(form.getQty(), code, null);
		LOG.info("inside add to cart controller--------------" + code);

		LOG.info("inside add to cart controller------form.getQty()--------" + form.getQty());

		final CartModificationData modification = cartFacade.addOrderEntry(orderEntryData);

		model.addAttribute("numberShowing", Config.getInt(SHOWN_PRODUCT_COUNT, 3));
		model.addAttribute("modifications", (modification != null ? Lists.newArrayList(modification) : Collections.emptyList()));

		addStatusMessages(model, modification);


		model.addAttribute("product", productFacade.getProductForCodeAndOptions(code, Arrays.asList(ProductOption.BASIC,
				ProductOption.CATEGORIES, ProductOption.VARIANT_MATRIX_BASE, ProductOption.VARIANT_MATRIX_PRICE,
				ProductOption.VARIANT_MATRIX_MEDIA, ProductOption.VARIANT_MATRIX_STOCK)));

		//return ControllerConstants.Views.Fragments.Cart.LoyaltyAddToCartPopup;

		//return "pages/cart/loyalty/testLoyaltyCartPage";
		return ControllerConstants.Views.Fragments.Cart.LoyaltyAddToCartPopup;
	}

	protected void addStatusMessages(final Model model, final CartModificationData modification)
	{
		final boolean hasMessage = StringUtils.isNotEmpty(modification.getStatusMessage());
		if (hasMessage)
		{
			if (SUCCESSFUL_MODIFICATION_CODE.equals(modification.getStatusCode()))
			{
				GlobalMessages.addMessage(model, GlobalMessages.CONF_MESSAGES_HOLDER, modification.getStatusMessage(), null);
			}
			else if (!model.containsAttribute(ERROR_MSG_TYPE))
			{
				GlobalMessages.addMessage(model, GlobalMessages.ERROR_MESSAGES_HOLDER, modification.getStatusMessage(), null);
			}
		}
	}

	protected OrderEntryData getOrderEntryData(final long quantity, final String productCode, final Integer entryNumber)
	{

		final OrderEntryData orderEntry = new OrderEntryData();
		orderEntry.setQuantity(quantity);
		orderEntry.setProduct(new ProductData());
		orderEntry.getProduct().setCode(productCode);
		orderEntry.setEntryNumber(entryNumber);
		LOG.info("inside add to cart controller--------------" + orderEntry.getProduct().getCode());


		return orderEntry;
	}


	protected List<OrderEntryData> getOrderEntryData(final List<CartEntryData> cartEntries)
	{
		final List<OrderEntryData> orderEntries = new ArrayList<>();

		for (final CartEntryData entry : cartEntries)
		{
			final Integer entryNumber = entry.getEntryNumber() != null ? entry.getEntryNumber().intValue() : null;
			orderEntries.add(getOrderEntryData(entry.getQuantity(), entry.getSku(), entryNumber));
		}
		return orderEntries;
	}

	protected String getViewWithBindingErrorMessages(final Model model, final BindingResult bindingErrors)
	{
		for (final ObjectError error : bindingErrors.getAllErrors())
		{
			if (isTypeMismatchError(error))
			{
				model.addAttribute(ERROR_MSG_TYPE, QUANTITY_INVALID_BINDING_MESSAGE_KEY);
			}
			else
			{
				model.addAttribute(ERROR_MSG_TYPE, error.getDefaultMessage());
			}
		}
		return ControllerConstants.Views.Fragments.Cart.LoyaltyAddToCartPopup;
	}

	protected boolean isTypeMismatchError(final ObjectError error)
	{
		return error.getCode().equals(TYPE_MISMATCH_ERROR_CODE);
	}
}
