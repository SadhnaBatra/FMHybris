/**
 * 
 */
package com.federalmogul.core.services.impl;

import de.hybris.platform.cms2.servicelayer.services.CMSSiteService;
import de.hybris.platform.commerceservices.i18n.CommerceCommonI18NService;
import de.hybris.platform.commerceservices.order.impl.DefaultCommerceCheckoutService;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.order.payment.CreditCardPaymentInfoModel;
import de.hybris.platform.core.model.order.payment.PaymentInfoModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.order.InvalidCartException;
import de.hybris.platform.order.exceptions.CalculationException;
import de.hybris.platform.payment.dto.CardInfo;
import de.hybris.platform.payment.dto.TransactionStatus;
import de.hybris.platform.payment.model.PaymentTransactionEntryModel;
import de.hybris.platform.payment.model.PaymentTransactionModel;
import de.hybris.platform.tx.Transaction;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.federalmogul.core.constants.FmCoreConstants;


/**
 * @author SU276498
 * 
 */
public class FMCommerceCheckoutService extends DefaultCommerceCheckoutService
{


	@Autowired
	private CMSSiteService cmsSiteService;

	@Autowired
	private CommerceCommonI18NService commerceCommonI18NService;


	@Override
	public OrderModel placeOrder(final CartModel cartModel) throws InvalidCartException
	{
		if (!Boolean.TRUE.equals(cartModel.getCalculated()))
		{
			throw new IllegalArgumentException("Cart model must be calculated");
		}
		final CustomerModel customer = (CustomerModel) cartModel.getUser();

		//wrap this in a transaction
		final OrderModel orderModel = getOrderService().createOrderFromCart(cartModel);

		final Transaction tx = Transaction.current();
		tx.begin();

		if (orderModel != null)
		{
			// Store the current site and store on the order
			orderModel.setSite(getBaseSiteService().getCurrentBaseSite());
			orderModel.setStore(getBaseStoreService().getCurrentBaseStore());
			orderModel.setDate(new Date());
			orderModel.setCode(FmCoreConstants.ORDER_PREFIX + orderModel.getCode());
			//orderModel.setEmailAddress(customer.getEmail());
			//Adding status to order as "Submitted"
			//TO DO  CYBERSOURCE
			//orderModel.setStatus(OrderStatus.SUBMITTED);
			//Based on Payment, can change the the status to inreview
			if (cartModel.getStatusInfo() != null && ("InReview").equals(cartModel.getStatusInfo()))
			{
				//orderModel.setStatus(OrderStatus.INREVIEW);
			}

			orderModel.setCurrency(commerceCommonI18NService.getCurrentCurrency());
			orderModel.setLocale(commerceCommonI18NService.getCurrentLocale().getCountry());
			//orderModel.setLanguage(commerceCommonI18NService.getCurrentLanguage().getIsocode());

			getModelService().saveAll(customer, orderModel);


			if (cartModel.getPaymentInfo() != null)
			{
				orderModel.setPaymentAddress(cartModel.getPaymentInfo().getBillingAddress());
				orderModel.getPaymentInfo().setBillingAddress(cartModel.getPaymentInfo().getBillingAddress());
				getModelService().save(orderModel.getPaymentInfo());
			}
			getModelService().save(orderModel);

			// Calculate the order now that it has been copied
			try
			{
				getCalculationService().calculateTotals(orderModel, false);
			}
			catch (final CalculationException ex)
			{
				ex.getStackTrace();
			}

			getModelService().refresh(orderModel);
			getModelService().refresh(customer);
			getOrderService().submitOrder(orderModel);

		}

		return orderModel;
	}



	@SuppressWarnings("boxing")
	@Override
	public PaymentTransactionEntryModel authorizePayment(final CartModel cartModel, final String securityCode,
			final String paymentProvider)
	{

		final PaymentInfoModel paymentInfo = cartModel.getPaymentInfo();
		if (paymentInfo instanceof CreditCardPaymentInfoModel)
		{
			final Currency currency = getI18nService().getBestMatchingJavaCurrency(cartModel.getCurrency().getIsocode());
			String merchantTransactionCode = cartModel.getUser().getUid() + "-" + UUID.randomUUID();

			if (cartModel.getCode() != null && !cartModel.getCode().isEmpty())
			{
				merchantTransactionCode += "~_~" + cartModel.getCode() + "~_~";
				//cartModel.setPaypalComment2(cartModel.getCode());
				getModelService().save(cartModel);
			}
			//Only Auth non GC amount
			double totalPrice = cartModel.getTotalPrice() != null ? cartModel.getTotalPrice().doubleValue() : 0d;
			if (totalPrice > 0.00)
			{
				totalPrice = (Math.round(totalPrice * 100D)) / 100D;
			}
			final CardInfo cardInfo = new CardInfo();
			cardInfo.setCardNumber(((CreditCardPaymentInfoModel) paymentInfo).getNumber());
			cardInfo.setExpirationMonth(Integer.parseInt(((CreditCardPaymentInfoModel) paymentInfo).getValidToMonth()));
			cardInfo.setExpirationYear(Integer.parseInt(((CreditCardPaymentInfoModel) paymentInfo).getValidToYear()));
			cardInfo.setCardHolderFullName(((CreditCardPaymentInfoModel) paymentInfo).getCcOwner());
			cardInfo.setCardType(((CreditCardPaymentInfoModel) paymentInfo).getType());
			cardInfo.setCv2Number(securityCode);
			//cardInfo.setPaymentProvider(paymentProvider);

			final PaymentTransactionEntryModel transactionEntryModel = getPaymentService().authorize(merchantTransactionCode,
					BigDecimal.valueOf(totalPrice).setScale(2, BigDecimal.ROUND_UP), currency, cartModel.getDeliveryAddress(),
					((CreditCardPaymentInfoModel) paymentInfo).getBillingAddress(), cardInfo);
			if (transactionEntryModel != null
					&& (TransactionStatus.ACCEPTED.name().equals(transactionEntryModel.getTransactionStatus()) || TransactionStatus.REVIEW
							.name().equals(transactionEntryModel.getTransactionStatus())))
			{
				final PaymentTransactionModel paymentTransaction = transactionEntryModel.getPaymentTransaction();
				paymentTransaction.setOrder(cartModel);
				getModelService().saveAll(cartModel, paymentTransaction);
				if (TransactionStatus.REVIEW.name().equals(transactionEntryModel.getTransactionStatus()))
				{
					cartModel.setStatusInfo("InReview");
				}
			}

			return transactionEntryModel;
		}

		return null;
	}




	protected CMSSiteService getCMSSiteService()
	{
		return cmsSiteService;
	}


	public void setCMSSiteService(final CMSSiteService cmsSiteService)
	{
		this.cmsSiteService = cmsSiteService;
	}



	public void calculateTotals(final CartModel orderModel)
	{
		try
		{
			getCalculationService().calculateTotals(orderModel, false);
		}
		catch (final CalculationException e)
		{
			//LOG.error("Failed to calculate order [" + orderModel + "]", e);
		}
	}


}
