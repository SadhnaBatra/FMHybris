/**
 * 
 */
package com.federalmogul.core.event;

import de.hybris.platform.commerceservices.event.AbstractCommerceUserEvent;

import org.apache.log4j.Logger;

import com.federalmogul.core.model.FMB2SBTaxAdminApprovalProcessModel;


/**
 * @author su244261
 * 
 */
public class FMB2SBTaxAdminApprovalProcessEvent extends AbstractCommerceUserEvent
{

	private final FMB2SBTaxAdminApprovalProcessModel process;

	private static final Logger LOG = Logger.getLogger(FMB2SBTaxAdminApprovalProcessEvent.class);

	public FMB2SBTaxAdminApprovalProcessEvent(final FMB2SBTaxAdminApprovalProcessModel process)
	{
		LOG.info("--------cccccccccccccccc -In adminprocess even one arg contruct-------");
		this.process = process;

	}


	public FMB2SBTaxAdminApprovalProcessModel getProcess()
	{
		return process;
	}

}
