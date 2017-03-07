/**
 * 
 */
package com.federalmogul.core.dao;

import de.hybris.platform.core.model.c2l.RegionModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.payment.model.PaymentTransactionModel;

import java.util.List;

import com.federalmogul.core.model.BrandInformationModel;


/**
 * @author SU276498
 * 
 */
public interface FMCommonDAO
{
	/**
	 * This method will return States for currentCountry
	 * 
	 * @param country
	 * @return list of RegionModel
	 */
	public List<RegionModel> getStateForCountry(final String country);

	/**
	 * This method will return customer model based on his email ID
	 * 
	 * @param email
	 * @return list of CustomerModel
	 */
	public List<CustomerModel> getCustomerForEmailId(final String email);

	/**
	 * This Method returns all the subscription groups , configured for ExactTarget
	 * 
	 */
	public List<PaymentTransactionModel> getPaymentTransactionForRequestId(List<String> requestId);
	
	public List<BrandInformationModel> getBrandInformationByName(String brand);
}
