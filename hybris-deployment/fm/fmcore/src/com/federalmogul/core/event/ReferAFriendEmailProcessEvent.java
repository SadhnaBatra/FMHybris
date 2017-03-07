package com.federalmogul.core.event;

import de.hybris.platform.commerceservices.event.AbstractCommerceUserEvent;

import com.federalmogul.core.model.ReferAFriendEmailProcessModel;


@SuppressWarnings("serial")
public class ReferAFriendEmailProcessEvent extends AbstractCommerceUserEvent
{

	private final ReferAFriendEmailProcessModel process;

	public ReferAFriendEmailProcessEvent(final ReferAFriendEmailProcessModel process)
	{
		this.process = process;

	}


	public ReferAFriendEmailProcessModel getProcess()
	{
		return process;
	}
}
