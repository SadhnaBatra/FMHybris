package com.federalmogul.facades.fmpopulator;

import de.hybris.platform.commercefacades.product.converters.populator.CustomerReviewPopulator;
import de.hybris.platform.commercefacades.product.data.ReviewData;
import de.hybris.platform.customerreview.model.CustomerReviewModel;
import org.apache.log4j.Logger;

public class FMCustomerReviewPopulator extends CustomerReviewPopulator {
	private static final Logger LOG = Logger.getLogger(FMCustomerReviewPopulator.class);
	
	@Override
	public void populate(final CustomerReviewModel source, final ReviewData target)
	{
		LOG.info("INSIDE FMCustomerReviewPopulator POPULATOR");
		super.populate(source, target);
		target.setFmAdminResponse(source.getFmAdminResponse());
		target.setFmAdminResponseDate(source.getFmAdminResponseDate());
	}
}
