/**
 * 
 */
package com.federalmogul.core.event;

import de.hybris.platform.commerceservices.event.AbstractCommerceUserEvent;

import org.apache.log4j.Logger;

import com.federalmogul.core.model.LoyaltyOrderConfirmationProcessModel;


/**
 * @author su244261
 * 
 */
public class LoyaltyOrderConfirmationProcessEvent extends AbstractCommerceUserEvent
{

	private final LoyaltyOrderConfirmationProcessModel process;

	private static final Logger LOG = Logger.getLogger(LoyaltyOrderConfirmationProcessEvent.class);

	public LoyaltyOrderConfirmationProcessEvent(final LoyaltyOrderConfirmationProcessModel process)
	{
		LOG.info("--------cccccccccccccccc -In adminprocess even one arg contruct-------");
		this.process = process;

	}


	public LoyaltyOrderConfirmationProcessModel getProcess()
	{
		return process;
	}

}
