/**
 * 
 */
package com.federalmogul.core.dao.impl;

import de.hybris.platform.core.model.c2l.CountryModel;
import de.hybris.platform.core.model.c2l.RegionModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.payment.model.PaymentTransactionModel;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.federalmogul.core.dao.FMCommonDAO;
import com.federalmogul.core.model.BrandInformationModel;


/**
 * @author SU276498
 * 
 */
public class DefaultFMCommonDAO implements FMCommonDAO
{

	@Autowired
	private FlexibleSearchService flexibleSearchService;

	@Autowired
	private CommonI18NService commonI18NService;

	@Override
	public List<RegionModel> getStateForCountry(final String country)
	{
		List<RegionModel> states = new ArrayList<RegionModel>();
		final Map<String, Object> params = new HashMap<String, Object>();
		final CountryModel countryModel = commonI18NService.getCountry(country);
		final FlexibleSearchQuery query = new FlexibleSearchQuery(
				"select {pk} from {Region} where {country}=?country and {active}=?active");
		params.put("country", countryModel);
		params.put("active", Boolean.TRUE);
		query.addQueryParameters(params);
		final SearchResult<RegionModel> result = flexibleSearchService.search(query);
		states = result.getResult();
		return states;
	}

	@Override
	public List<CustomerModel> getCustomerForEmailId(final String email)
	{
		List<CustomerModel> customerModel = new ArrayList<CustomerModel>();
		final Map<String, Object> params = new HashMap<String, Object>();
		final FlexibleSearchQuery query = new FlexibleSearchQuery("select {pk} from {Customer} where {uid}=?email");
		params.put("email", email);
		query.addQueryParameters(params);
		final SearchResult<CustomerModel> result = flexibleSearchService.search(query);
		customerModel = result.getResult();
		return customerModel;
	}

	/**
 * 
 */
	@Override
	public List<PaymentTransactionModel> getPaymentTransactionForRequestId(final List<String> requestId)
	{
		final StringBuilder builder = new StringBuilder();
		builder.append("SELECT {").append(PaymentTransactionModel.PK).append("} ");
		builder.append("FROM {").append(PaymentTransactionModel._TYPECODE).append("}");
		builder.append("WHERE {").append(PaymentTransactionModel.REQUESTID).append("} IN (?requestID)");

		final Map<String, Object> params = new HashMap<String, Object>();
		params.put("requestID", requestId);

		final FlexibleSearchQuery query = new FlexibleSearchQuery(builder.toString(), Collections.singletonMap("requestID",
				requestId));
		//flexibleSearchService = new DefaultFlexibleSearchService();
		final SearchResult<PaymentTransactionModel> orderResults = flexibleSearchService.search(query);
		if (orderResults != null && orderResults.getCount() > 0)
		{
			return orderResults.getResult();
		}

		return null;
	}

	@Override
	public List<BrandInformationModel> getBrandInformationByName(String brand) {
		final FlexibleSearchQuery query = new FlexibleSearchQuery("select {pk} from {BrandInformation} where {brand}=?brand");
		final Map<String, Object> params = new HashMap<String, Object>();
		params.put("brand", brand);
		query.addQueryParameters(params);
		SearchResult<BrandInformationModel> result = null;
		try{
		  result =flexibleSearchService.search(query);
		}catch(Exception e){
			e.printStackTrace();
		}
		  List<BrandInformationModel> brandInformationModelList= result.getResult();
		  return brandInformationModelList;
	}


}
