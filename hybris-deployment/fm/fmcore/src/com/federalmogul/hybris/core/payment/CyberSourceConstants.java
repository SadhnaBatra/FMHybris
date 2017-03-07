/**
 * 
 */
package com.federalmogul.hybris.core.payment;

/**
 * @author SU276498
 * 
 */
public interface CyberSourceConstants
{
	interface HopProperties extends PaymentConstants.PaymentProperties
	{
		String MERCHANT_ID = "hop.cybersource.merchantID";
		String SHARED_SECRET = "hop.cybersource.sharedSecret";
		String SERIAL_NUMBER = "hop.cybersource.serialNumber";
		String HOP_SETUP_FEE = "hop.cybersource.setupFee";
		String PROFILE_ID = "hop.cybersource.profileID";
		String SIGNED_FIELDS = "hop.cybersource.signedFields";
		String UNSIGNED_FIELDS = "hop.cybersource.unSignedFields";
		String HOP_POST_URL = "hop.post.url";
		String TRANSACTION_TYPE = "hop.transaction.type";
		String CUSTOM_RECEIPT_URL = "hop.cybersource.customReceiptURL";
		String UNSIGNED_FIELDS_AGENT_RMA = "hop.cybersource.unSignedFieldsAgentRMA";
		String UNSIGNED_FIELDS_ADVANCE_RMA = "hop.cybersource.unSignedFieldsAdvanceRMA";
	}
}
