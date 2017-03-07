package com.federalmogul.core.event;

import de.hybris.platform.commerceservices.event.AbstractCommerceUserEvent;

import com.federalmogul.core.model.FMCustomerRegistrationProcessModel;


@SuppressWarnings("serial")
public class FMCustomerRegistrationProcessEvent extends AbstractCommerceUserEvent
{

	private final FMCustomerRegistrationProcessModel process;

	public FMCustomerRegistrationProcessEvent(final FMCustomerRegistrationProcessModel process)
	{
		this.process = process;

	}


	public FMCustomerRegistrationProcessModel getProcess()
	{
		return process;
	}
}
