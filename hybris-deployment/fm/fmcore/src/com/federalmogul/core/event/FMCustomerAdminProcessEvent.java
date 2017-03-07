/**
 * 
 */
package com.federalmogul.core.event;

import de.hybris.platform.commerceservices.event.AbstractCommerceUserEvent;

import org.apache.log4j.Logger;

import com.federalmogul.core.model.FMCustomerAdminProcessModel;


/**
 * @author su244261
 * 
 */
public class FMCustomerAdminProcessEvent extends AbstractCommerceUserEvent
{

	private final FMCustomerAdminProcessModel process;

	private static final Logger LOG = Logger.getLogger(FMCustomerAdminProcessEvent.class);

	public FMCustomerAdminProcessEvent(final FMCustomerAdminProcessModel process)
	{
		LOG.info("--------cccccccccccccccc -In adminprocess even one arg contruct-------");
		this.process = process;

	}


	public FMCustomerAdminProcessModel getProcess()
	{
		return process;
	}

}
