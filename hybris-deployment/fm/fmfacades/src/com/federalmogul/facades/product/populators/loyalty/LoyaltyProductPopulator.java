/**
 * 
 */
package com.federalmogul.facades.product.populators.loyalty;

import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.commercefacades.search.converters.populator.SearchResultProductPopulator;
import de.hybris.platform.commerceservices.search.resultdata.SearchResultValueData;

import org.apache.log4j.Logger;


/**
 * @author mamud
 * 
 */
public class LoyaltyProductPopulator extends SearchResultProductPopulator
{
	private static final Logger LOG = Logger.getLogger(LoyaltyProductPopulator.class);


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hybris.platform.commercefacades.search.converters.populator.SearchResultProductPopulator#populate(de.hybris
	 * .platform.commerceservices.search.resultdata.SearchResultValueData,
	 * de.hybris.platform.commercefacades.product.data.ProductData)
	 */
	@Override
	public void populate(final SearchResultValueData source, final ProductData target)
	{
		// YTODO Auto-generated method stub
		LOG.info("################### LoyaltyProductPopulator ######################");
		super.populate(source, target);
		target.setLoyaltyPoints(this.<String> getValue(source, "loyaltyPoints"));



	}
}
