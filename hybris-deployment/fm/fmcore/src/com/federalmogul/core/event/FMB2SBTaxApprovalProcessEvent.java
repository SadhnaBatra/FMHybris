/**
 * 
 */
package com.federalmogul.core.event;

import de.hybris.platform.commerceservices.event.AbstractCommerceUserEvent;

import org.apache.log4j.Logger;

import com.federalmogul.core.model.FMB2SBTaxApprovalProcessModel;


/**
 * @author su244261
 * 
 */
public class FMB2SBTaxApprovalProcessEvent extends AbstractCommerceUserEvent
{

	private final FMB2SBTaxApprovalProcessModel process;

	private static final Logger LOG = Logger.getLogger(FMB2SBTaxApprovalProcessEvent.class);

	public FMB2SBTaxApprovalProcessEvent(final FMB2SBTaxApprovalProcessModel process)
	{
		LOG.info("--------cccccccccccccccc -In adminprocess even one arg contruct-------");
		this.process = process;

	}


	public FMB2SBTaxApprovalProcessModel getProcess()
	{
		return process;
	}

}
