/**
 * 
 */
package com.federalmogul.core.event;

import de.hybris.platform.commerceservices.event.AbstractCommerceUserEvent;

import org.apache.log4j.Logger;

import com.federalmogul.core.model.FMCustomerReviewProcessModel;


/**
 * @author SR279690
 * 
 */
public class FMCustomerReviewProcessEvent extends AbstractCommerceUserEvent
{
	private final FMCustomerReviewProcessModel process;

	private static final Logger LOG = Logger.getLogger(FMCustomerReviewProcessEvent.class);

	public FMCustomerReviewProcessEvent(final FMCustomerReviewProcessModel process)
	{
		LOG.info("EVENT CONSTRUCTOR CALLED");
		this.process = process;
	}

	/**
	 * @return the process
	 */
	public FMCustomerReviewProcessModel getProcess()
	{
		return process;
	}

}
