/**
 * 
 */
package com.federalmogul.facades.process.email.context;

import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel;
import de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.customerreview.model.CustomerReviewModel;
import de.hybris.platform.util.Config;

import org.apache.log4j.Logger;

import com.federalmogul.core.model.FMCustomerReviewProcessModel;


/**
 * @author SR279690
 * 
 */
public class FMCustomerReviewContext extends AbstractEmailContext<FMCustomerReviewProcessModel>
{
	private static final Logger LOG = Logger.getLogger(FMCustomerReviewContext.class);
	private CustomerReviewModel customerReview;

	/**
	 * @return the customerReview
	 */
	public CustomerReviewModel getCustomerReview()
	{
		return customerReview;
	}

	/**
	 * @param customerReview
	 *           the customerReview to set
	 */
	public void setCustomerReview(final CustomerReviewModel customerReview)
	{
		this.customerReview = customerReview;
	}

	@Override
	public void init(final FMCustomerReviewProcessModel businessProcessModel, final EmailPageModel emailPageModel)
	{
		LOG.info("INSIDE CONTEXT INIT");
		super.init(businessProcessModel, emailPageModel);
		customerReview = businessProcessModel.getCustomerReview();
		LOG.info("CTX uid ::::" + customerReview.getUser().getUid());
		LOG.info("CTX title ::::" + customerReview.getHeadline());
		LOG.info("CTX comment ::::" + customerReview.getComment());
                put(EMAIL, Config.getParameter("toreviews"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext#getSite(de.hybris.platform.
	 * processengine.model.BusinessProcessModel)
	 */
	@Override
	protected BaseSiteModel getSite(final FMCustomerReviewProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext#getEmailLanguage(de.hybris.platform
	 * .processengine.model.BusinessProcessModel)
	 */
	@Override
	protected LanguageModel getEmailLanguage(final FMCustomerReviewProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext#getCustomer(de.hybris.platform
	 * .processengine.model.BusinessProcessModel)
	 */
	@Override
	protected CustomerModel getCustomer(final FMCustomerReviewProcessModel businessProcessModel)
	{
		// YTODO Auto-generated method stub
		return (CustomerModel) businessProcessModel.getCustomerReview().getUser();
	}



}
