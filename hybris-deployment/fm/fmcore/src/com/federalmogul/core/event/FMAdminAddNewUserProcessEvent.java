/**
 * 
 */
package com.federalmogul.core.event;

import de.hybris.platform.commerceservices.event.AbstractCommerceUserEvent;

import org.apache.log4j.Logger;

import com.federalmogul.core.model.FMAdminAddNewUserProcessModel;


/**
 * @author di278272
 * 
 */
public class FMAdminAddNewUserProcessEvent extends AbstractCommerceUserEvent
{

	private final FMAdminAddNewUserProcessModel process;

	private static final Logger LOG = Logger.getLogger(FMAdminAddNewUserProcessEvent.class);

	public FMAdminAddNewUserProcessEvent(final FMAdminAddNewUserProcessModel process)
	{
		LOG.info("--------cccccccccccccccc -In adminprocess even one arg contruct-------");
		this.process = process;

	}


	public FMAdminAddNewUserProcessModel getProcess()
	{
		return process;
	}

}
