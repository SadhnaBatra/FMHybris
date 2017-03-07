/**
 * 
 */
package com.federalmogul.core.services;

import de.hybris.platform.core.model.c2l.RegionModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.payment.model.PaymentTransactionModel;

import java.util.List;
import java.util.Map;

import com.federalmogul.core.model.BrandInformationModel;


/**
 * @author SU276498
 * 
 */
public interface FMCommonServices
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

	public Map<String, Object> modifyOrderStatus(List<PaymentTransactionModel> transactions, Map<String, String> requestMap);
	
	public List<BrandInformationModel> getBrandInformationByName(String Brand);

}
