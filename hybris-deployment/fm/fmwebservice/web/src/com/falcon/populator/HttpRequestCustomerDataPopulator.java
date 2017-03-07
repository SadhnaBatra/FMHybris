/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2014 hybris AG
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of hybris
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with hybris.
 */
package com.falcon.populator;

import de.hybris.platform.commercefacades.storesession.data.CurrencyData;
import de.hybris.platform.commercefacades.storesession.data.LanguageData;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.converters.impl.AbstractPopulatingConverter;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.federalmogul.core.model.FMBrandCampaignModel;
import com.federalmogul.facades.user.data.FMCustomerData;
import com.federalmogul.facades.user.data.FMBrandCampaignData;
/**
 * Populates {@link CustomerData} instance based on http request parameters:<br>
 * Does not populate address {@link AddressData} information
 * <ul>
 * <li>titleCode</li>
 * <li>firstName</li>
 * <li>lastName</li>
 * <li>language</li>
 * <li>currency</li>
 * </ul>
 * 
 */
@Component("httpRequestCustomerDataPopulator")
public class HttpRequestCustomerDataPopulator extends AbstractPopulatingConverter<HttpServletRequest, FMCustomerData>
{
	private static final Logger LOG = Logger.getLogger(HttpRequestCustomerDataPopulator.class);
	private static final String TITLECODE = "titleCode";
	private static final String FIRSTNAME = "firstName";
	private static final String LASTNAME = "lastName";
	private static final String LANGUAGE = "language";
	private static final String CURRENCY = "currency";
	private static final String BRANDCAMPAIGNID = "BrandCampaignId";
	private static final String BRANDCAMPAIGNINFO = "BrandCampaignInfo";
	

	@Override
	protected FMCustomerData createTarget()
	{
		return new FMCustomerData();
	}

	@Override
	public void populate(final HttpServletRequest source, final FMCustomerData target) throws ConversionException
	{
		Assert.notNull(source, "Parameter source cannot be null.");
		Assert.notNull(target, "Parameter target cannot be null.");

		target.setTitleCode(StringUtils.defaultString(source.getParameter(TITLECODE), target.getTitleCode()));
		target.setFirstName(StringUtils.defaultString(source.getParameter(FIRSTNAME), target.getFirstName()));
		target.setLastName(StringUtils.defaultString(source.getParameter(LASTNAME), target.getLastName()));
		LOG.info("Request Parameters - "+"BrandCampaignID: "+source.getParameter(BRANDCAMPAIGNID)+" BrandCampaignInfo: "+source.getParameter(BRANDCAMPAIGNINFO));
		List<FMBrandCampaignData> fmBrandCampaignDataList=target.getFmBrandCampaignList();
		FMBrandCampaignData fmBrandCampaignData = new FMBrandCampaignData();
		fmBrandCampaignData.setUserid(target.getUid());
		fmBrandCampaignData.setFmBrandCampaignID(source.getParameter(BRANDCAMPAIGNID));
		fmBrandCampaignData.setFmBrandCampaignInfo(source.getParameter(BRANDCAMPAIGNINFO));
		LOG.info("BrandCampaignID from Model:"+fmBrandCampaignData.getFmBrandCampaignID()+" BrandCampaignInfo:"+fmBrandCampaignData.getFmBrandCampaignInfo());

		if(fmBrandCampaignDataList != null) {
			LOG.info("Number of prior brand campaigns associated with user "+target.getUid()+" is "+fmBrandCampaignDataList.size());
			fmBrandCampaignDataList=updateBrandCamapaignifExists(fmBrandCampaignDataList,fmBrandCampaignData);
		}
		else {
			/*
			 * if user is not tied to any brand campaign prior to this request then initialize the fmBrandCampaignDataList to a new list (to avoid nullpointer exception)
			 */
			fmBrandCampaignDataList = new ArrayList<FMBrandCampaignData>();
			// add requested brand campaign to brand campaign list
			fmBrandCampaignDataList.add(fmBrandCampaignData);
		}
		target.setFmBrandCampaignList(fmBrandCampaignDataList);
		
		if (target.getFmBrandCampaignList() != null) {
			LOG.info("Total number of brand campaigns after adding brand campaign from current request is "+target.getFmBrandCampaignList().size());
		}
		if (source.getParameter(CURRENCY) != null)
		{
			final CurrencyData currency = new CurrencyData();
			currency.setIsocode(source.getParameter(CURRENCY));
			target.setCurrency(currency);
		}

		if (source.getParameter(LANGUAGE) != null)
		{
			final LanguageData language = new LanguageData();
			language.setIsocode(source.getParameter(LANGUAGE));
			target.setLanguage(language);
		}

	}
	
	List<FMBrandCampaignData> updateBrandCamapaignifExists(List<FMBrandCampaignData> fMBrandCampaignDataList, FMBrandCampaignData fMBrandCampaignDatafromRequest)
	{
		Map<String,FMBrandCampaignData> fmBrandCampaignDataMap = new HashMap<String,FMBrandCampaignData>();
		for(FMBrandCampaignData fMBrandCampaignData : fMBrandCampaignDataList) {
			String brandCampaignkey = makeKey(fMBrandCampaignData.getUserid(),fMBrandCampaignData.getFmBrandCampaignID());
			LOG.info("fMBrandCampaignDataList - brandCampaignkey:"+brandCampaignkey+"	brandCampaiginfo:"+fMBrandCampaignData.getFmBrandCampaignInfo());
			fmBrandCampaignDataMap.put(brandCampaignkey, fMBrandCampaignData);
		}
		LOG.info("fMBrandCampaignDatafromRequest Object values - fMBrandCampaignDatafromRequestKey:"+makeKey(fMBrandCampaignDatafromRequest.getUserid(),fMBrandCampaignDatafromRequest.getFmBrandCampaignID())+"	fMBrandCampaignDatafromRequestKeyInfo:"+fMBrandCampaignDatafromRequest.getFmBrandCampaignInfo());
		fmBrandCampaignDataMap.put(makeKey(fMBrandCampaignDatafromRequest.getUserid(),fMBrandCampaignDatafromRequest.getFmBrandCampaignID()),fMBrandCampaignDatafromRequest);
		List<FMBrandCampaignData> listOfBrandCampaignValues = new ArrayList<FMBrandCampaignData>(fmBrandCampaignDataMap.values());	
		return listOfBrandCampaignValues;
	}
	
	String makeKey(String user, String brandCampaignId) {
		return user+"-"+brandCampaignId;
	}
	

}
