package com.federalmogul.core.event;

import de.hybris.platform.commerceservices.event.AbstractCommerceUserEvent;

import com.federalmogul.core.model.FMBatchLoadSuccessEmailModel;


@SuppressWarnings("serial")
public class FMBatchLoadSuccessEmailEvent extends AbstractCommerceUserEvent
{

	private final FMBatchLoadSuccessEmailModel process;


	public FMBatchLoadSuccessEmailEvent(final FMBatchLoadSuccessEmailModel process)
	{
		this.process = process;

	}


	public FMBatchLoadSuccessEmailModel getProcess()
	{
		return process;
	}
}
