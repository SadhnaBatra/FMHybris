/**
 * 
 */
package com.federalmogul.storefront.forms;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 * @author SU276498
 * 
 */
public class HopPaymentDetailsForm
{
	private String amount;
	private String billTo_city;
	private String billTo_country;
	private String billTo_customerID;
	private String billTo_email;
	private String billTo_firstName;
	private String billTo_lastName;
	private String billTo_phoneNumber;
	private String billTo_postalCode;
	private String billTo_state;
	private String billTo_street1;
	private String billTo_street2;
	private String card_accountNumber;
	private String card_cardType;
	private String card_startMonth;
	private String card_startYear;
	private String card_issueNumber;
	private String card_cvNumber;
	private String card_expirationMonth;
	private String card_expirationYear;
	private String card_nameOnCard;
	private String comments;
	private String currency;
	private String shipTo_city;
	private String shipTo_country;
	private String shipTo_firstName;
	private String shipTo_lastName;
	private String shipTo_phoneNumber;
	private String shipTo_postalCode;
	private String shipTo_shippingMethod;
	private String shipTo_state;
	private String shipTo_street1;
	private String shipTo_street2;
	private String taxAmount;
	private boolean savePaymentInfo;
	private boolean useDeliveryAddress;

	private Map<String, String> parameters;
	private Map<String, String> unSignedParams;


	/**
	 * @return the card_nameOnCard
	 */
	public String getCard_nameOnCard()
	{
		return card_nameOnCard;
	}

	/**
	 * @param card_nameOnCard
	 *           the card_nameOnCard to set
	 */
	public void setCard_nameOnCard(final String card_nameOnCard)
	{
		this.card_nameOnCard = card_nameOnCard;
	}

	public String getAmount()
	{
		return amount;
	}

	public void setAmount(final String amount)
	{
		this.amount = amount;
	}

	public String getBillTo_city()
	{
		return billTo_city;
	}

	public void setBillTo_city(final String billTo_city)
	{
		this.billTo_city = billTo_city;
	}

	public String getBillTo_country()
	{
		if (billTo_country != null)
		{
			return billTo_country.toUpperCase(Locale.US);
		}
		return billTo_country;
	}

	public void setBillTo_country(final String billTo_country)
	{
		this.billTo_country = billTo_country;
	}

	public String getBillTo_customerID()
	{
		return billTo_customerID;
	}

	public void setBillTo_customerID(final String billTo_customerID)
	{
		this.billTo_customerID = billTo_customerID;
	}

	public String getBillTo_email()
	{
		return billTo_email;
	}

	public void setBillTo_email(final String billTo_email)
	{
		this.billTo_email = billTo_email;
	}

	public String getBillTo_firstName()
	{
		return billTo_firstName;
	}

	public void setBillTo_firstName(final String billTo_firstName)
	{
		this.billTo_firstName = billTo_firstName;
	}

	public String getBillTo_lastName()
	{
		return billTo_lastName;
	}

	public void setBillTo_lastName(final String billTo_lastName)
	{
		this.billTo_lastName = billTo_lastName;
	}

	public String getBillTo_phoneNumber()
	{
		return billTo_phoneNumber;
	}

	public void setBillTo_phoneNumber(final String billTo_phoneNumber)
	{
		this.billTo_phoneNumber = billTo_phoneNumber;
	}

	public String getBillTo_postalCode()
	{
		return billTo_postalCode;
	}

	public void setBillTo_postalCode(final String billTo_postalCode)
	{
		this.billTo_postalCode = billTo_postalCode;
	}

	public String getBillTo_state()
	{
		return billTo_state;
	}

	public void setBillTo_state(final String billTo_state)
	{
		this.billTo_state = billTo_state;
	}

	public String getBillTo_street1()
	{
		return billTo_street1;
	}

	public void setBillTo_street1(final String billTo_street1)
	{
		this.billTo_street1 = billTo_street1;
	}

	public String getBillTo_street2()
	{
		return billTo_street2;
	}

	public void setBillTo_street2(final String billTo_street2)
	{
		this.billTo_street2 = billTo_street2;
	}

	public String getCard_accountNumber()
	{
		return card_accountNumber;
	}

	public void setCard_accountNumber(final String card_accountNumber)
	{
		this.card_accountNumber = card_accountNumber;
	}

	public String getCard_startMonth()
	{
		return card_startMonth;
	}

	public void setCard_startMonth(final String card_startMonth)
	{
		this.card_startMonth = card_startMonth;
	}

	public String getCard_startYear()
	{
		return card_startYear;
	}

	public void setCard_startYear(final String card_startYear)
	{
		this.card_startYear = card_startYear;
	}

	public String getCard_issueNumber()
	{
		return card_issueNumber;
	}

	public void setCard_issueNumber(final String card_issueNumber)
	{
		this.card_issueNumber = card_issueNumber;
	}

	public String getCard_cardType()
	{
		return card_cardType;
	}

	public void setCard_cardType(final String card_cardType)
	{
		this.card_cardType = card_cardType;
	}

	public String getCard_cvNumber()
	{
		return card_cvNumber;
	}

	public void setCard_cvNumber(final String card_cvNumber)
	{
		this.card_cvNumber = card_cvNumber;
	}

	public String getCard_expirationMonth()
	{
		return card_expirationMonth;
	}

	public void setCard_expirationMonth(final String card_expirationMonth)
	{
		this.card_expirationMonth = card_expirationMonth;
	}

	public String getCard_expirationYear()
	{
		return card_expirationYear;
	}

	public void setCard_expirationYear(final String card_expirationYear)
	{
		this.card_expirationYear = card_expirationYear;
	}

	public String getComments()
	{
		return comments;
	}

	public void setComments(final String comments)
	{
		this.comments = comments;
	}

	public String getCurrency()
	{
		return currency;
	}

	public void setCurrency(final String currency)
	{
		this.currency = currency;
	}

	public String getShipTo_city()
	{
		return shipTo_city;
	}

	public void setShipTo_city(final String shipTo_city)
	{
		this.shipTo_city = shipTo_city;
	}

	public String getShipTo_country()
	{
		if (shipTo_country != null)
		{
			return shipTo_country.toUpperCase(Locale.US);
		}
		return shipTo_country;
	}

	public void setShipTo_country(final String shipTo_country)
	{
		this.shipTo_country = shipTo_country;
	}

	public String getShipTo_firstName()
	{
		return shipTo_firstName;
	}

	public void setShipTo_firstName(final String shipTo_firstName)
	{
		this.shipTo_firstName = shipTo_firstName;
	}

	public String getShipTo_lastName()
	{
		return shipTo_lastName;
	}

	public void setShipTo_lastName(final String shipTo_lastName)
	{
		this.shipTo_lastName = shipTo_lastName;
	}

	public String getShipTo_phoneNumber()
	{
		return shipTo_phoneNumber;
	}

	public void setShipTo_phoneNumber(final String shipTo_phoneNumber)
	{
		this.shipTo_phoneNumber = shipTo_phoneNumber;
	}

	public String getShipTo_postalCode()
	{
		return shipTo_postalCode;
	}

	public void setShipTo_postalCode(final String shipTo_postalCode)
	{
		this.shipTo_postalCode = shipTo_postalCode;
	}

	public String getShipTo_shippingMethod()
	{
		return shipTo_shippingMethod;
	}

	public void setShipTo_shippingMethod(final String shipTo_shippingMethod)
	{
		this.shipTo_shippingMethod = shipTo_shippingMethod;
	}

	public String getShipTo_state()
	{
		return shipTo_state;
	}

	public void setShipTo_state(final String shipTo_state)
	{
		this.shipTo_state = shipTo_state;
	}

	public String getShipTo_street1()
	{
		return shipTo_street1;
	}

	public void setShipTo_street1(final String shipTo_street1)
	{
		this.shipTo_street1 = shipTo_street1;
	}

	public String getShipTo_street2()
	{
		return shipTo_street2;
	}

	public void setShipTo_street2(final String shipTo_street2)
	{
		this.shipTo_street2 = shipTo_street2;
	}

	public String getTaxAmount()
	{
		return taxAmount;
	}

	public void setTaxAmount(final String taxAmount)
	{
		this.taxAmount = taxAmount;
	}

	public boolean isSavePaymentInfo()
	{
		return savePaymentInfo;
	}

	public void setSavePaymentInfo(final boolean savePaymentInfo)
	{
		this.savePaymentInfo = savePaymentInfo;
	}

	public boolean isUseDeliveryAddress()
	{
		return useDeliveryAddress;
	}

	public void setUseDeliveryAddress(final boolean useDeliveryAddress)
	{
		this.useDeliveryAddress = useDeliveryAddress;
	}

	public Map<String, String> getParameters()
	{
		return parameters;
	}

	public void setParameters(final Map<String, String> parameters)
	{
		this.parameters = parameters;
	}

	/**
	 * @param unSignedParams
	 *           the unSignedParams to set
	 */
	public void setUnSignedParams(final Map<String, String> unSignedParams)
	{
		this.unSignedParams = unSignedParams;
	}

	/**
	 * @return the unSignedParams
	 */
	public Map<String, String> getUnSignedParams()
	{
		return unSignedParams;
	}

	public Map<String, String> getSubscriptionSignatureParams()
	{
		/*
		 * if (parameters != null) { final Map<String, String> subscriptionSignatureParams = new HashMap<String,
		 * String>(); subscriptionSignatureParams.put("recurringSubscriptionInfo_amount",
		 * parameters.get("recurringSubscriptionInfo_amount"));
		 * subscriptionSignatureParams.put("recurringSubscriptionInfo_numberOfPayments",
		 * parameters.get("recurringSubscriptionInfo_numberOfPayments"));
		 * subscriptionSignatureParams.put("recurringSubscriptionInfo_frequency",
		 * parameters.get("recurringSubscriptionInfo_frequency"));
		 * subscriptionSignatureParams.put("recurringSubscriptionInfo_automaticRenew",
		 * parameters.get("recurringSubscriptionInfo_automaticRenew"));
		 * subscriptionSignatureParams.put("recurringSubscriptionInfo_startDate",
		 * parameters.get("recurringSubscriptionInfo_startDate"));
		 * subscriptionSignatureParams.put("recurringSubscriptionInfo_signaturePublic",
		 * parameters.get("recurringSubscriptionInfo_signaturePublic")); return subscriptionSignatureParams; }
		 */
		return null;
	}

	public Map<String, String> getSignatureParams()
	{
		if (parameters != null)
		{
			final Map<String, String> signatureParams = new HashMap<String, String>();
			signatureParams.put("signature", parameters.get("signature"));
			//signatureParams.put("merchantID", parameters.get("merchantID"));
			signatureParams.put("amount", parameters.get("amount"));
			signatureParams.put("currency", parameters.get("currency"));
			signatureParams.put("signed_date_time", parameters.get("signed_date_time"));
			signatureParams.put("transaction_type", parameters.get("transaction_type"));
			//signatureParams.put("orderPage_version", parameters.get("orderPage_version"));
			signatureParams.put("access_key", parameters.get("access_key"));
			signatureParams.put("transaction_uuid", parameters.get("transaction_uuid"));
			signatureParams.put("profile_id", parameters.get("profile_id"));
			signatureParams.put("reference_number", parameters.get("reference_number"));
			signatureParams.put("signed_field_names", parameters.get("signed_field_names"));
			signatureParams.put("locale", parameters.get("locale"));
			signatureParams.put("unsigned_field_names", parameters.get("unsigned_field_names"));

			signatureParams.put("payment_method", parameters.get("payment_method"));
			signatureParams.put("card_type", parameters.get("card_type"));
			signatureParams.put("card_number", parameters.get("card_number"));
			signatureParams.put("card_expiry_date", parameters.get("card_expiry_date"));
			signatureParams.put("card_cvn", parameters.get("card_cvn"));

			signatureParams.put("bill_to_forename", parameters.get("bill_to_forename"));
			signatureParams.put("bill_to_surname", parameters.get("bill_to_surname"));
			signatureParams.put("bill_to_email", parameters.get("bill_to_email"));
			signatureParams.put("bill_to_phone", parameters.get("bill_to_phone"));
			signatureParams.put("bill_to_address_line1", parameters.get("bill_to_address_line1"));
			signatureParams.put("bill_to_address_city", parameters.get("bill_to_address_city"));
			signatureParams.put("bill_to_address_state", parameters.get("bill_to_address_state"));
			signatureParams.put("bill_to_address_country", parameters.get("bill_to_address_country"));
			signatureParams.put("bill_to_address_postal_code", parameters.get("bill_to_address_postal_code"));

			signatureParams.put("ship_to_forename", parameters.get("ship_to_forename"));
			signatureParams.put("ship_to_surname", parameters.get("ship_to_surname"));
			signatureParams.put("ship_to_email", parameters.get("ship_to_email"));
			signatureParams.put("ship_to_phone", parameters.get("ship_to_phone"));
			signatureParams.put("ship_to_address_line1", parameters.get("ship_to_address_line1"));

			if (parameters.get("ship_to_address_line2") != null)
			{
				signatureParams.put("ship_to_address_line2", parameters.get("ship_to_address_line2"));
			}
			signatureParams.put("ship_to_address_city", parameters.get("ship_to_address_city"));
			signatureParams.put("ship_to_address_state", parameters.get("ship_to_address_state"));
			signatureParams.put("ship_to_address_country", parameters.get("ship_to_address_country"));
			signatureParams.put("ship_to_address_postal_code", parameters.get("ship_to_address_postal_code"));

			signatureParams.put("override_custom_receipt_page", parameters.get("override_custom_receipt_page"));
			signatureParams.put("customer_ip_address", parameters.get("customer_ip_address"));
			signatureParams.put("tax_amount", parameters.get("tax_amount"));

			return signatureParams;
		}
		return null;
	}

}