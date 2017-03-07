/**
 * 
 */
package com.federalmogul.core.event;

import de.hybris.platform.commerceservices.event.AbstractCommerceUserEvent;

import org.apache.log4j.Logger;

import com.federalmogul.core.model.FMB2BBRegistrationApprovalProcessModel;


/**
 * @author su244261
 * 
 */
public class FMB2BBRegistrationApprovalProcessEvent extends AbstractCommerceUserEvent
{

	private final FMB2BBRegistrationApprovalProcessModel process;

	private static final Logger LOG = Logger.getLogger(FMB2BBRegistrationApprovalProcessEvent.class);

	public FMB2BBRegistrationApprovalProcessEvent(final FMB2BBRegistrationApprovalProcessModel process)
	{
		LOG.info("--------cccccccccccccccc -In adminprocess even one arg contruct-------");
		this.process = process;

	}


	public FMB2BBRegistrationApprovalProcessModel getProcess()
	{
		return process;
	}

}
