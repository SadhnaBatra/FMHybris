/**
 * 
 */
package com.federalmogul.hybris.facades.order;

import de.hybris.platform.cms2.servicelayer.services.CMSSiteService;
import de.hybris.platform.commercefacades.order.data.CCPaymentInfoData;
import de.hybris.platform.commercefacades.order.data.CardTypeData;
import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.order.data.DeliveryModeData;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.order.impl.DefaultCheckoutFacade;
import de.hybris.platform.commercefacades.product.data.PriceData;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.data.RegionData;
import de.hybris.platform.commerceservices.delivery.dao.CountryZoneDeliveryModeDao;
import de.hybris.platform.commerceservices.order.CommerceCheckoutService;
import de.hybris.platform.commerceservices.order.impl.DefaultCommerceCheckoutService;
import de.hybris.platform.core.Registry;
import de.hybris.platform.core.model.c2l.RegionModel;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.core.model.order.delivery.DeliveryModeModel;
import de.hybris.platform.core.model.order.payment.CreditCardPaymentInfoModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.core.model.user.UserModel;
import de.hybris.platform.order.InvalidCartException;
import de.hybris.platform.orderprocessing.model.OrderProcessModel;
import de.hybris.platform.payment.dto.TransactionStatus;
import de.hybris.platform.payment.enums.PaymentTransactionType;
import de.hybris.platform.payment.model.PaymentTransactionEntryModel;
import de.hybris.platform.payment.model.PaymentTransactionModel;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.store.services.BaseStoreService;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import com.federalmogul.core.services.FMCommonServices;
import com.federalmogul.core.services.impl.FMCommerceCheckoutService;
import com.federalmogul.hybris.core.payment.CyberSourceConstants;



/**
 * @author SU276498
 * 
 */
public class FMCheckoutFacade extends DefaultCheckoutFacade
{
	@Autowired
	private SessionService sessionService;

	@Autowired
	private CMSSiteService cmsSiteService;

	@Autowired
	private FMCommerceCheckoutService checkoutService;

	private FMCommonServices fmCommonServices;



	/**
	 * @return the fmCommonServices
	 */
	public FMCommonServices getFmCommonServices()
	{
		return fmCommonServices;
	}

	/**
	 * @param fmCommonServices
	 *           the fmCommonServices to set
	 */
	public void setFmCommonServices(final FMCommonServices fmCommonServices)
	{
		this.fmCommonServices = fmCommonServices;
	}



	private Converter<CartModel, CartData> cartConverter;

	/**
	 * @return the cartConverter
	 */
	public Converter<CartModel, CartData> getCartConverter()
	{
		return cartConverter;
	}

	/**
	 * @param cartConverter
	 *           the cartConverter to set
	 */
	public void setCartConverter(final Converter<CartModel, CartData> cartConverter)
	{
		this.cartConverter = cartConverter;
	}

	/**
	 * @return the checkoutService
	 */
	public DefaultCommerceCheckoutService getCheckoutService()
	{
		return checkoutService;
	}


	/*
	 * @Autowired private FlexibleSearchService flexibleSearchService;
	 */

	@Autowired
	private CommonI18NService commonI18NService;

	/*
	 * @Autowired private UserFacade userFacade;
	 */


	@Autowired
	private CountryZoneDeliveryModeDao countryZoneDeliveryModeDao;
	@Autowired
	private BaseStoreService baseStoreService;


	@Autowired
	private ModelService modelService;

	@Autowired
	private ConfigurationService configurationService;


	private static final Map<String, String> CYBERSOURCE_HOP_CARDTYPES = new HashMap<String, String>();

	static
	{
		// Map hybris card type to Cybersource HOP credit card
		CYBERSOURCE_HOP_CARDTYPES.put("VISA", "001");
		CYBERSOURCE_HOP_CARDTYPES.put("MASTER", "002");
		CYBERSOURCE_HOP_CARDTYPES.put("AMEX", "003");
		CYBERSOURCE_HOP_CARDTYPES.put("DINERS", "005");
		CYBERSOURCE_HOP_CARDTYPES.put("MAESTRO", "024");
	}

	//Not used anymore after cybersource integration, new placeOrder method is below
	@Override
	public OrderData placeOrder() throws InvalidCartException
	{
		final CartModel cartModel = getCart();
		if (cartModel != null)
		{
			final UserModel currentUser = getUserService().getCurrentUser();
			if (cartModel.getUser().equals(currentUser))
			{
				if (cartModel.getDeliveryMode() != null && !"GRND".equals(cartModel.getDeliveryMode().getCode()))
				{
					final CreditCardPaymentInfoModel creditCardPaymentInfoModel = (CreditCardPaymentInfoModel) cartModel
							.getPaymentInfo();
					if (creditCardPaymentInfoModel != null)
					{
						//creditCardPaymentInfoModel.setNumber(getMaskedCardNumber(creditCardPaymentInfoModel.getNumber()));
						//creditCardPaymentInfoModel.setSubscriptionId(value)
						getModelService().saveAll(creditCardPaymentInfoModel);

					}
				}
				else
				{
					cartModel.setPaymentInfo(null);
					getModelService().save(cartModel);
				}

				/*
				 * final OrderModel orderModel = getCheckoutService().placeOrder(cartModel);
				 * 
				 * if (orderModel != null) { try { // Send out order received confirmation email
				 * 
				 * //orderReceivedEmailProcess | orderConfirmationEmailProcess final OrderProcessModel orderProcessModel =
				 * (OrderProcessModel) getBusinessProcessService().createProcess( "orderReceivedEmailProcess" +
				 * System.currentTimeMillis(), "orderReceivedEmailProcess"); //final OrderModel orderModel =
				 * ((PoAcknowledgementModel) process).getOrder(); orderProcessModel.setOrder(orderModel);
				 * getModelService().save(orderProcessModel); getBusinessProcessService().startProcess(orderProcessModel); }
				 * catch (final Exception e) { // }
				 * 
				 * // Remove cart getCartService().removeSessionCart(); //orderModel.setStatus(OrderStatus.COMPLETED);
				 * return getOrderConverter().convert(orderModel); }
				 */
			}
		}

		return null;
	}

	public OrderData placeOrder(final Map<String, String> resultMap, final PaymentTransactionModel transactionModel)
			throws InvalidCartException
	{
		final CartModel cartModel = getCart();
		if (cartModel != null)
		{
			final UserModel currentUser = getUserService().getCurrentUser();
			if (cartModel.getUser().equals(currentUser))
			{
				if (cartModel.getDeliveryMode() != null && !"GRND".equals(cartModel.getDeliveryMode().getCode()))
				{
					if (TransactionStatus.REVIEW.name().equals(resultMap.get("decision")))
					{
						cartModel.setStatusInfo("InReview");
					}
					final CreditCardPaymentInfoModel creditCardPaymentInfoModel = (CreditCardPaymentInfoModel) cartModel
							.getPaymentInfo();

					if (creditCardPaymentInfoModel != null)
					{
						//creditCardPaymentInfoModel.setNumber(getMaskedCardNumber(creditCardPaymentInfoModel.getNumber()));
						creditCardPaymentInfoModel.setSubscriptionId(resultMap.get("payment_token"));

						getModelService().saveAll(creditCardPaymentInfoModel);

					}
				}
				else
				{
					cartModel.setPaymentInfo(null);
					getModelService().save(cartModel);
				}

				/*
				 * final OrderModel orderModel = getCheckoutService().placeOrder(cartModel);
				 * 
				 * if (orderModel != null) { try { // Send out order received confirmation email
				 * 
				 * //orderReceivedEmailProcess | orderConfirmationEmailProcess final OrderProcessModel orderProcessModel =
				 * (OrderProcessModel) getBusinessProcessService().createProcess( "orderReceivedEmailProcess" +
				 * System.currentTimeMillis(), "orderReceivedEmailProcess"); //final OrderModel orderModel =
				 * ((PoAcknowledgementModel) process).getOrder(); orderProcessModel.setOrder(orderModel);
				 * getModelService().save(orderProcessModel); getBusinessProcessService().startProcess(orderProcessModel); }
				 * catch (final Exception e) { // }
				 * 
				 * // Remove cart getCartService().removeSessionCart(); //orderModel.setStatus(OrderStatus.COMPLETED);
				 * return getOrderConverter().convert(orderModel); }
				 */
			}
		}

		return null;
	}

	/**
	 * 
	 * This method is used when placing an order with PayPal
	 */

	public OrderData placeOrder(final PaymentTransactionModel transactionModel) throws InvalidCartException
	{
		final CartModel cartModel = getCart();
		if (cartModel != null)
		{
			final UserModel currentUser = getUserService().getCurrentUser();
			if (cartModel.getUser().equals(currentUser))
			{
				if (cartModel.getDeliveryMode() != null && !"GRND".equals(cartModel.getDeliveryMode().getCode()))
				{
					final CreditCardPaymentInfoModel creditCardPaymentInfoModel = (CreditCardPaymentInfoModel) cartModel
							.getPaymentInfo();
					if (creditCardPaymentInfoModel != null)
					{
						//creditCardPaymentInfoModel.setNumber(getMaskedCardNumber(creditCardPaymentInfoModel.getNumber()));
						//creditCardPaymentInfoModel.setSubscriptionId(value)
						creditCardPaymentInfoModel.setCode("PayPal " + creditCardPaymentInfoModel.getCode());
						getModelService().saveAll(creditCardPaymentInfoModel);

					}

				}
				else
				{
					cartModel.setPaymentInfo(null);
					getModelService().save(cartModel);
				}
				getModelService().save(cartModel);
				final OrderModel orderModel = getCheckoutService().placeOrder(cartModel);
				if (orderModel != null)
				{
					try
					{
						// Send out order received confirmation email

						//orderReceivedEmailProcess  | orderConfirmationEmailProcess
						final OrderProcessModel orderProcessModel = (OrderProcessModel) getBusinessProcessService().createProcess(
								"orderReceivedEmailProcess" + System.currentTimeMillis(), "orderReceivedEmailProcess");
						//final OrderModel orderModel = ((PoAcknowledgementModel) process).getOrder();
						orderProcessModel.setOrder(orderModel);
						getModelService().save(orderProcessModel);
						getBusinessProcessService().startProcess(orderProcessModel);
					}
					catch (final Exception e)
					{
						// 				
					}

					// Remove cart
					getCartService().removeSessionCart();
					sessionService.removeAttribute("payPalPaymentData");
					//orderModel.setStatus(OrderStatus.COMPLETED);
					return getOrderConverter().convert(orderModel);
				}
			}
		}

		return null;
	}



	@Override
	public boolean authorizePayment(final String securityCode)
	{
		final CartModel cartModel = getCart();
		if (cartModel != null)
		{
			final UserModel currentUser = getUserService().getCurrentUser();
			if (cartModel.getUser().equals(currentUser))
			{
				final CreditCardPaymentInfoModel creditCardPaymentInfoModel = (CreditCardPaymentInfoModel) cartModel.getPaymentInfo();
				if (creditCardPaymentInfoModel != null)
				{
					final PaymentTransactionEntryModel paymentTransactionEntryModel = getCommerceCheckoutService().authorizePayment(
							cartModel, securityCode, getPaymentProvider());

					if (paymentTransactionEntryModel != null)
					{
						return TransactionStatus.ACCEPTED.name().equals(paymentTransactionEntryModel.getTransactionStatus());
					}
				}
			}
		}

		return false;
	}

	/**
	 * Method to perform payment authorization
	 * 
	 * @param securityCode
	 * @return error code if authorization fails else empty
	 */
	public String performPaymentAuthorization(final String securityCode)
	{
		final String txnStatus = "ERROR";
		final CartModel cartModel = getCart();
		if (cartModel != null)
		{
			final UserModel currentUser = getUserService().getCurrentUser();
			if (cartModel.getUser().equals(currentUser))
			{
				final CreditCardPaymentInfoModel creditCardPaymentInfoModel = (CreditCardPaymentInfoModel) cartModel.getPaymentInfo();
				if (creditCardPaymentInfoModel != null)
				{
					final PaymentTransactionEntryModel paymentTransactionEntryModel = getCommerceCheckoutService().authorizePayment(
							cartModel, securityCode, getPaymentProvider());

					if (paymentTransactionEntryModel != null)
					{
						if (TransactionStatus.ACCEPTED.name().equals(paymentTransactionEntryModel.getTransactionStatus())
								|| TransactionStatus.REVIEW.name().equals(paymentTransactionEntryModel.getTransactionStatus()))
						{
							return "";
						}
						else
						{
							return paymentTransactionEntryModel.getTransactionStatus();
						}
					}
				}
			}
		}

		return txnStatus;
	}



	public List<RegionData> getStateForCountry()
	{
		final List<RegionData> result = new ArrayList<RegionData>();
		final List<RegionModel> statesForCountry = getStateForCountry("us");
		for (final RegionModel state : statesForCountry)
		{
			final RegionData stateData = new RegionData();
			stateData.setIsocode(state.getIsocode());
			stateData.setName(state.getName());
			result.add(stateData);
		}

		return result;
	}

	/**
	 * This method will return States for currentCountry
	 * 
	 * @param country
	 * @return list of RegionModel
	 */
	public List<RegionModel> getStateForCountry(final String country)
	{
		final List<RegionModel> states = fmCommonServices.getStateForCountry(country);
		return states;
	}

	/**
	 * This method wll return the CustomerModels for the Same uid
	 * 
	 * @param email
	 * @return list of customerModel
	 */
	public List<CustomerModel> getCustomerForEmailId(final String email)
	{
		final List<CustomerModel> fmCustomerModel = fmCommonServices.getCustomerForEmailId(email);
		return fmCustomerModel;
	}


	public Collection<CardTypeData> getHopCardTypes()
	{
		final Collection<CardTypeData> hopCardTypes = new ArrayList<CardTypeData>();

		final List<CardTypeData> supportedCardTypes = getSupportedCardTypes();
		for (final CardTypeData supportedCardType : supportedCardTypes)
		{
			// Add credit cards for all supported cards that have mappings for cybersource HOP
			if (CYBERSOURCE_HOP_CARDTYPES.containsKey(supportedCardType.getCode()))
			{
				hopCardTypes.add(createCardTypeData(CYBERSOURCE_HOP_CARDTYPES.get(supportedCardType.getCode()),
						supportedCardType.getName()));
			}
		}

		return hopCardTypes;
	}

	protected CardTypeData createCardTypeData(final String code, final String name)
	{
		final CardTypeData cardTypeData = new CardTypeData();
		cardTypeData.setCode(code);
		cardTypeData.setName(name);
		return cardTypeData;
	}

	/**
	 * Method to set payment information on order
	 * 
	 * @param paymentInfoData
	 * @return
	 */
	public boolean setPaymentInfo(final CCPaymentInfoData paymentInfoData)
	{
		final CCPaymentInfoData newPaymentSubscription = createPaymentSubscription(paymentInfoData);
		if (newPaymentSubscription != null)
		{
			setPaymentDetails(newPaymentSubscription.getId());
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * Method to set delivery address on the cart
	 * 
	 * @param deliveryAddressData
	 * @param associateToUser
	 * @return boolean
	 */
	public boolean setDeliveryAddress(final AddressData deliveryAddressData, final boolean associateToUser)
	{
		//final CustomerModel customerModel = (CustomerModel) getUserService().getCurrentUser();

		if (associateToUser)
		{

			return setDeliveryAddress(deliveryAddressData);
		}
		else
		{
			final AddressModel newAddress = getModelService().create(AddressModel.class);
			final CartModel cartModel = getCart();
			newAddress.setOwner(cartModel);
			getModelService().save(newAddress);
			getModelService().refresh(newAddress);
			cartModel.setDeliveryAddress(newAddress);
			getModelService().save(cartModel);
			getModelService().refresh(cartModel);
			if (newAddress.getPk() != null)
			{
				deliveryAddressData.setId(newAddress.getPk().toString());
			}
			return true;
		}
	}

	/**
	 * This method will associate address model to the customer
	 * 
	 * @param addressData
	 *           AddressData
	 */
	public void detachAddressFromCart(final AddressData addressData)
	{
		final CartModel cart = getCart();
		AddressModel address;
		if (cart != null)
		{
			if (addressData.isBillingAddress() && cart.getPaymentInfo() != null)
			{
				address = cart.getPaymentInfo().getBillingAddress();
			}
			else
			{
				address = cart.getDeliveryAddress();
			}
			if (address != null)
			{
				getModelService().remove(address);
			}
		}

	}

	/**
	 * Method to validate if selected delivery mode is valid for the country
	 * 
	 * @return deliverymodevalid
	 */
	public boolean validateDeliveryMode()
	{
		final CartModel cartModel = getCart();
		if (cartModel != null)
		{
			getCommerceCheckoutService().validateDeliveryMode(cartModel);
			return cartModel.getDeliveryMode() != null;
		}
		return false;
	}

	/**
	 * Method to validate credit card number.
	 * 
	 * @return validateCreditcardlength
	 */
	public boolean validateCreditCardLength(final String cardNumber)
	{
		boolean ccLengthRet;
		final int length = cardNumber.length();
		if (length < 15 || length > 16)
		{
			ccLengthRet = false;
			//return false;
		}
		else
		{
			ccLengthRet = true;
			//return true;
		}

		return ccLengthRet;

	}

	/**
	 * Method to validate if Expiry data is not in the past
	 * 
	 * @return expirydatevalid
	 */
	public boolean validateExpiryDate(final String month, final String year)
	{


		final int iMonth = Integer.valueOf(month);
		final int iYear = Integer.valueOf(year);

		final GregorianCalendar now = new GregorianCalendar();
		if (iYear < now.get(Calendar.YEAR))
		{
			return false;
		}
		else if (iYear == now.get(Calendar.YEAR))
		{
			if (iMonth < (now.get(Calendar.MONTH) + 1))
			{
				return false;
			}
		}
		return true;
	}


	/**
	 * Method to return all shipping methods available in the system
	 * 
	 * @return List<DeliveryModeData>
	 */
	public Collection<DeliveryModeData> getAllShippingMethods()
	{
		final Collection<DeliveryModeModel> deliveryModes = countryZoneDeliveryModeDao.findDeliveryModesByCountryAndCurrency(
				baseStoreService.getCurrentBaseStore().getDeliveryCountries().iterator().next(),
				commonI18NService.getCurrentCurrency(), Boolean.valueOf(getBaseStoreService().getCurrentBaseStore().isNet()));

		//sortDeliveryModes(deliveryModes, abstractOrder);
		final List<DeliveryModeData> result = new ArrayList<DeliveryModeData>();
		for (final DeliveryModeModel deliveryModeModel : deliveryModes)
		{
			if (deliveryModeModel != null && deliveryModeModel.getActive().booleanValue())
			{
				result.add(getDeliveryModeConverter().convert(deliveryModeModel));
			}
		}

		return result;
	}

	@Override
	protected CommerceCheckoutService getCommerceCheckoutService()
	{
		return checkoutService;
	}



	/**
	 * @param cmsSiteService
	 *           the cmsSiteService to set
	 */
	public void setCmsSiteService(final CMSSiteService cmsSiteService)
	{
		this.cmsSiteService = cmsSiteService;
	}


	/**
	 * @return the cmsSiteService
	 */
	public CMSSiteService getCmsSiteService()
	{
		return cmsSiteService;
	}



	public void setDefaultTax()
	{
		final CartModel cartModel = getCart();
		cartModel.setTotalTax(0.00);
	}



	/**
	 * @param securityCode
	 * @param remoteAddr
	 * @return
	 */
	public String performInitialPaymentAuthorization(final String securityCode)
	{
		final String txnStatus = "ERROR";
		final CartModel cartModel = getCart();
		if (cartModel != null)
		{
			final UserModel currentUser = getUserService().getCurrentUser();
			if (cartModel.getUser().equals(currentUser))
			{
				final CreditCardPaymentInfoModel creditCardPaymentInfoModel = (CreditCardPaymentInfoModel) cartModel.getPaymentInfo();
				if (creditCardPaymentInfoModel != null)
				{
					final Double totalPrice = cartModel.getTotalPrice();
					cartModel.setTotalPrice(Double.valueOf(0));
					final PaymentTransactionEntryModel paymentTransactionEntryModel = getCommerceCheckoutService().authorizePayment(
							cartModel, securityCode, getPaymentProvider());
					cartModel.setTotalPrice(totalPrice);
					getModelService().saveAll(cartModel);
					if (paymentTransactionEntryModel != null)
					{
						//final String dtl = paymentTransactionEntryModel.getTransactionStatusDetails();
						if (TransactionStatus.ACCEPTED.name().equals(paymentTransactionEntryModel.getTransactionStatus())
								|| TransactionStatus.REVIEW.name().equals(paymentTransactionEntryModel.getTransactionStatus()))
						{
							return "";
						}
						else
						{
							return paymentTransactionEntryModel.getTransactionStatus();
						}
					}
				}
			}
		}

		return txnStatus;
	}

	public String getMaskedCardNumber(final String cardNumber)
	{
		final int cardNumberLength = cardNumber.length();
		final int subStringToMask = cardNumberLength - 4;
		final String subString = cardNumber.substring(subStringToMask, cardNumberLength);
		String maskedCardNumber = "";
		for (int i = 0; i < subStringToMask; i++)
		{
			maskedCardNumber = maskedCardNumber.concat("X");
		}

		return maskedCardNumber.concat(subString);
	}

	public void ifBillingUsedAsShipping(final AddressData billingAddressData, final AddressData shippingAddressData)
	{
		final String billingAddress = convertToString(billingAddressData);
		final String shippingAddress = convertToString(shippingAddressData);
		if (billingAddress.equals(shippingAddress))
		{
			final CartModel cart = getCart();
			if (cart != null && cart.getPaymentInfo() != null && cart.getPaymentInfo().getBillingAddress() != null)
			{
				final AddressModel billingAddressModel = cart.getPaymentInfo().getBillingAddress();
				shippingAddressData.setId(billingAddressModel.getPk().toString());
				billingAddressModel.setShippingAddress(Boolean.valueOf(shippingAddressData.isShippingAddress()));
				getModelService().save(billingAddressModel);
				getModelService().refresh(billingAddressModel);
			}

		}
	}


	/**
	 * This method converts the AddressData into String. It considers following properties - </br>
	 * <ul>
	 * <li>First Name</li>
	 * <li>Last Name</li>
	 * <li>line1</li>
	 * <li>lin2</li>
	 * <li>Town</li>
	 * <li>Region</li>
	 * <li>Country IsoCode</li>
	 * <li>Postal Code</li>
	 * <li>Phone</li>
	 * </ul>
	 * 
	 * @param addressData
	 * @return String
	 */
	protected String convertToString(final AddressData addressData)
	{
		final StringBuilder address = new StringBuilder();
		address.append(addressData.getFirstName());
		address.append(addressData.getLastName());
		address.append(addressData.getLine1());
		address.append(addressData.getLine2());
		address.append(addressData.getTown());
		address.append(addressData.getRegion());
		address.append(addressData.getCountry().getIsocode());
		address.append(addressData.getPostalCode());
		address.append(addressData.getPhone());
		return address.toString();
	}




	@Override
	public CartData getCheckoutCart()
	{

		final CartModel cart = getCartService().getSessionCart();
		//	cartData = getCartConverter().convert(cart);

		final CartData cartData = cartConverter.convert(cart);
		if (cartData != null)
		{
			cartData.setDeliveryAddress(getDeliveryAddress());
			cartData.setDeliveryMode(getDeliveryMode());
			cartData.setPaymentInfo(getPaymentDetails());
		}

		return cartData;
	}



	/**
	 * @return the countryZoneDeliveryModeDao
	 */
	public CountryZoneDeliveryModeDao getCountryZoneDeliveryModeDao()
	{
		return countryZoneDeliveryModeDao;
	}


	/**
	 * @param countryZoneDeliveryModeDao
	 *           the countryZoneDeliveryModeDao to set
	 */
	public void setCountryZoneDeliveryModeDao(final CountryZoneDeliveryModeDao countryZoneDeliveryModeDao)
	{
		this.countryZoneDeliveryModeDao = countryZoneDeliveryModeDao;
	}


	/**
	 * @return the baseStoreService
	 */
	@Override
	public BaseStoreService getBaseStoreService()
	{
		return baseStoreService;
	}


	/**
	 * @param baseStoreService
	 *           the baseStoreService to set
	 */
	@Override
	public void setBaseStoreService(final BaseStoreService baseStoreService)
	{
		this.baseStoreService = baseStoreService;
	}

	public BusinessProcessService getBusinessProcessService()
	{
		return (BusinessProcessService) Registry.getApplicationContext().getBean("businessProcessService");
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


	public PriceData getCorrectedSubTotalBeforePromo(final OrderData orderData)
	{
		double total = 0;
		final List<OrderEntryData> entries = new ArrayList(orderData.getEntries());
		// special case of buy x get y free promotion, for example if buy 1 get 1 free, subtotal
		for (final OrderEntryData result : entries)
		{

			total += result.getBasePrice().getValue().doubleValue() * result.getQuantity().doubleValue();


		}

		return getPriceDataFactory().create(null, (BigDecimal.valueOf(total)),
				getCommonI18NService().getCurrentCurrency().getIsocode());

	}



	/**
	 * @param resultMap
	 * @return
	 */
	public PaymentTransactionEntryModel setTransactionStatus(final Map<String, String> resultMap,
			final PaymentTransactionModel transaction)
	{
		//this.modelService.save(transaction);
		final PaymentTransactionEntryModel entry = (PaymentTransactionEntryModel) this.modelService
				.create(PaymentTransactionEntryModel.class);
		entry.setAmount(new BigDecimal(resultMap.get("auth_amount")));
		entry.setTransactionStatus("Authorization Successful");
		entry.setTransactionStatusDetails("Customer Credit Card Authorized");
		if (this.commonI18NService.getCurrentCurrency() != null)

		{
			entry.setCurrency(this.commonI18NService.getCurrentCurrency());
		}
		entry.setType(PaymentTransactionType.AUTHORIZATION);
		try
		{
			entry.setTime(resultMap.get("auth_time") == null ? new Date() : new SimpleDateFormat("MMMM d, yyyy hh:mm:ss",
					Locale.ENGLISH).parse(resultMap.get("auth_time")));
		}
		catch (final ParseException e)
		{
			// YTODO Auto-generated catch block
			entry.setTime(new Date());
		}
		entry.setPaymentTransaction(transaction);
		entry.setRequestId(resultMap.get("transaction_id"));

		if (TransactionStatus.REVIEW.name().equals(resultMap.get("decision")))
		{
			entry.setTransactionStatus(TransactionStatus.REVIEW.name());
		}
		else if ("accept".equalsIgnoreCase(resultMap.get("decision")))
		{
			entry.setTransactionStatus(TransactionStatus.ACCEPTED.name());
		}

		if (resultMap.get("message") != null)
		{
			entry.setTransactionStatusDetails(resultMap.get("message"));
		}
		entry.setCode(resultMap.get("transaction_id"));

		if (resultMap.get("payment_token") != null)

		{
			entry.setSubscriptionID(resultMap.get("payment_token"));
		}
		//this.modelService.save(entry);
		//this.modelService.refresh(transaction);

		return entry;

	}


	/**
	 * @return
	 */
	public String getTransactionType()
	{
		return configurationService.getConfiguration().getString(CyberSourceConstants.HopProperties.TRANSACTION_TYPE);
	}

	/**
	 * @return
	 */
	public Object getPostUrl()
	{
		return configurationService.getConfiguration().getString(CyberSourceConstants.HopProperties.HOP_POST_URL);
	}

	/**
	 * @return
	 */
	public String getUnSignedFieldNames()
	{
		return configurationService.getConfiguration().getString(CyberSourceConstants.HopProperties.UNSIGNED_FIELDS);
	}

	/**
	 * @return
	 */
	public String getSignedFieldNames()
	{
		return configurationService.getConfiguration().getString(CyberSourceConstants.HopProperties.SIGNED_FIELDS);
	}

	/**
	 * @return
	 */
	public String getUTCDateTime()
	{
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		return sdf.format(new Date());
	}

	/**
	 * @return
	 */
	public String getProfileId()
	{
		return configurationService.getConfiguration().getString(CyberSourceConstants.HopProperties.PROFILE_ID);
	}

	/**
	 * @return
	 */
	public String getReferenceNumber()
	{
		final long dateTime = new Date().getTime();
		return Long.toString(dateTime);
	}


	/**
	 * @return
	 */
	public String getSharedSecret()
	{
		return configurationService.getConfiguration().getString(CyberSourceConstants.HopProperties.SHARED_SECRET);
	}

	/**
	 * @return
	 */
	public String getAccessKey()
	{
		return configurationService.getConfiguration().getString(CyberSourceConstants.HopProperties.SERIAL_NUMBER);
	}

	/**
	 * @return
	 */
	public String getMerchantId()
	{
		return configurationService.getConfiguration().getString(
				cmsSiteService.getCurrentSite() + "." + CyberSourceConstants.HopProperties.MERCHANT_ID);
	}


	public String getCustomReceiptUrl(final HttpServletRequest request)
	{
		final String countrySpecific = cmsSiteService.getCurrentSite() + "/checkout/multi/hop-response";
		return configurationService.getConfiguration().getString(CyberSourceConstants.HopProperties.CUSTOM_RECEIPT_URL)
				+ countrySpecific;
	}


	public String getAddressFromRequest(final HttpServletRequest request)
	{
		String ip = request.getHeader("X-Forwarded-For");

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * @param parameters
	 * @return
	 */
	public String buildDataToSign(final Map<String, String> parameters)
	{
		final String[] signedFieldNames = String.valueOf(parameters.get("signed_field_names")).split(",");
		final ArrayList<String> dataToSign = new ArrayList<String>();
		for (final String signedFieldName : signedFieldNames)
		{
			dataToSign.add(signedFieldName + "=" + String.valueOf(parameters.get(signedFieldName)));
		}
		return commaSeparate(dataToSign);
	}

	public String commaSeparate(final ArrayList<String> dataToSign)
	{
		final StringBuilder csv = new StringBuilder();
		for (final Iterator<String> it = dataToSign.iterator(); it.hasNext();)
		{
			csv.append(it.next());
			if (it.hasNext())
			{
				csv.append(",");
			}
		}
		return csv.toString();
	}

	public Map<String, String> getRequestParameterMap(final HttpServletRequest request)
	{
		final Map<String, String> map = new HashMap<String, String>();

		final Enumeration myEnum = request.getParameterNames();
		while (myEnum.hasMoreElements())
		{
			final String paramName = (String) myEnum.nextElement();
			final String paramValue = request.getParameter(paramName);
			map.put(paramName, paramValue);
		}

		return map;
	}


	/**
	 * @return
	 */
	public String getUnSignedFieldNamesAgentRMA()
	{
		return configurationService.getConfiguration().getString(CyberSourceConstants.HopProperties.UNSIGNED_FIELDS_AGENT_RMA);

	}


	/**
	 * @return
	 */
	public String getUnSignedFieldNamesAdvanceRMA()
	{
		return configurationService.getConfiguration().getString(CyberSourceConstants.HopProperties.UNSIGNED_FIELDS_ADVANCE_RMA);
	}


	/**
	 * @return
	 */
	public BigDecimal getExtraAuthAmount()
	{
		//adding 2 cents extra to authorization amount so that  
		final BigDecimal extraAuthAmount = new BigDecimal("00.02");
		return extraAuthAmount;
	}

	public BigDecimal getExtraAuthAmount(final CartData cartData)
	{
		int count = 0;
		for (final OrderEntryData entry : cartData.getEntries())
		{
			count += entry.getQuantity().intValue();
		}

		if (count % 2 != 0)
		{
			count += 1;
		}

		final double amount = count * 0.05;
		final BigDecimal extraAuthAmount = BigDecimal.valueOf(amount).setScale(2, BigDecimal.ROUND_UP);//new BigDecimal(amount, MathContext.DECIMAL32);
		return extraAuthAmount;
	}


}
