package com.federalmogul.core.event;

import de.hybris.platform.commerceservices.event.AbstractCommerceUserEvent;

import com.federalmogul.core.model.FMBatchLoadErrorEmailModel;


@SuppressWarnings("serial")
public class FMBatchLoadErrorEmailEvent extends AbstractCommerceUserEvent
{

	private final FMBatchLoadErrorEmailModel process;


	public FMBatchLoadErrorEmailEvent(final FMBatchLoadErrorEmailModel process)
	{
		this.process = process;

	}


	public FMBatchLoadErrorEmailModel getProcess()
	{
		return process;
	}
}
